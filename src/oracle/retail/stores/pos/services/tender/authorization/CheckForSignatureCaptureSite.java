/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/authorization/CheckForSignatureCaptureSite.java /rgbustores_13.4x_generic_branch/15 2011/10/24 15:45:17 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   10/24/11 - fixed possible setting sig-req to false after
 *                         Serverbase set to true
 *    cgreene   10/24/11 - check for null amount before testing signum
 *    asinton   10/06/11 - prevent the signature capture when credit disclosure
 *                         data is present because a credit slip will be
 *                         printed
 *    jswan     09/28/11 - Modified to force signature capture for manually
 *                         approved credit tenders.
 *    jswan     09/27/11 - Fixed an issue with printing a manual signature slip
 *                         when the CPOI sig cap fails.
 *    jswan     09/22/11 - Rework to prevent gift cards and debit cards from
 *                         requiring a signature.
 *    jswan     09/21/11 - Fix failure to request signature capture.
 *    cgreene   09/09/11 - check sigcap requirement from response first, then
 *                         check if params require it.
 *    blarsen   08/26/11 - Added check for case when the auth service says sig
 *                         cap is required.
 *    jswan     08/26/11 - Modified to prevent signature capture for Re-entry
 *                         transactionsactions.
 *    sgu       08/12/11 - check null pointer for tender sub type
 *    ohorne    08/09/11 - APF:foreign currency support
 *    blarsen   07/15/11 - Removing reversal check since reversals no longer
 *                         use the authorization service.
 *    blarsen   07/12/11 - prevent sig caps for credit reversal requests (they
 *                         come through the auth service too)
 *    jswan     06/29/11 - Added to support signature capture in APF.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.authorization;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.common.parameter.ParameterConstantsIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.manager.payment.AuthorizationConstantsIfc.TenderType;
import oracle.retail.stores.domain.manager.payment.AuthorizeTransferResponseIfc;
import oracle.retail.stores.domain.manager.payment.AuthorizeTransferResponseIfc.AuthorizationMethod;
import oracle.retail.stores.domain.utility.CardTypeCodesIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;

import org.apache.commons.lang.StringUtils;

/**
 * This site checks to see the amount charged to the card type triggers
 * signature capture.
 *
 * @author jswan
 * @since 13.4
 */
@SuppressWarnings("serial")
public class CheckForSignatureCaptureSite extends PosSiteActionAdapter
{
    /** Constant for Approval letter */
    public static final String REQUIRED_LETTER = "Required";

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.application.SiteActionAdapter#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void arrive(BusIfc bus)
    {
        AuthorizationCargo cargo = (AuthorizationCargo)bus.getCargo();

        String letter = CommonLetterIfc.SKIP;
        AuthorizeTransferResponseIfc response = cargo.getCurrentResponse();

        boolean signatureRequired = false;
        CurrencyIfc amount = response.getBaseAmount();

        // If this is a valid ICC Details object, get the signature required value
        // from that object.
        if (response.getICCDetails().getApplicationID() != null)
        {
            signatureRequired = response.getICCDetails().isSignatureRequired();
        }
        else
        // If the tender type is credit AND the tender amount is zero or greater AND
        // the register is NOT in re-entry mode, check the signature capture
        // minimum amount parameter.
        if (TenderType.CREDIT.equals(response.getTenderType()) &&
             (amount != null && amount.signum() > CurrencyIfc.NEGATIVE) &&
             !cargo.getRegister().getWorkstation().isTransReentryMode())
        {
            String tenderSubType = response.getTenderSubType();
            if (AuthorizationMethod.Manual.equals(response.getAuthorizationMethod()))
            {
                signatureRequired = true;
            }
            else if(CardTypeCodesIfc.HOUSE_CARD.equals(tenderSubType) == false)
            {
                signatureRequired = isSignatureRequiredFromParameters(bus, response, amount, tenderSubType);
            }
            // House card implied, need signature capture unless promotional
            // details are present in which case a signature slip will be printed.
            else if(isHouseAccountWithoutPromotion(response))
            {
                signatureRequired = true;
            }

            // Resetting this value in case the CPOI signature capture fails.
            if (signatureRequired && !response.isSignatureRequired())
            {
                response.setSignatureRequired(true);
                // otherwise setting to false here might step on Chip and PIN code that set this to true
            }
        }

        // If the signature is required, mail the letter.
        if (signatureRequired)
        {
            letter = REQUIRED_LETTER;
        }

        bus.mail(new Letter(letter), BusIfc.CURRENT);
    }

    /**
     * Calculates if signature required based on parameters.
     * @param bus
     * @param response
     * @param signatureRequired
     * @param amount
     * @param tenderSubType
     * @return boolean value for signature required based on parameters
     */
    protected boolean isSignatureRequiredFromParameters(BusIfc bus, AuthorizeTransferResponseIfc response, CurrencyIfc amount, String tenderSubType)
    {
        boolean signatureRequired = false;
        ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
        if (StringUtils.isNotEmpty(tenderSubType))
        {
            try
            {
                // Get Minimum signature capture amount for this card type.
                String parmValue = pm.getStringValue(ParameterConstantsIfc.
                        TENDERAUTHORIZATION_MinimumSigCapFor_PREFIX + tenderSubType);
                CurrencyIfc signatureAmount = DomainGateway.getBaseCurrencyInstance(parmValue);
                // and if tender amount is great than or equal to the MinimumSigCapFor+<credit type>
                // parameter amount, the signature is required.
                if(amount.compareTo(signatureAmount) != CurrencyIfc.LESS_THAN)
                {
                    signatureRequired = true;
                }
            }
            catch(ParameterException pe)
            {
                // If there is no parameter associated with the card type, the
                // signature is required.
                signatureRequired = true;
                logger.warn("Unable to obtain parameter value for " + ParameterConstantsIfc.TENDERAUTHORIZATION_MinimumSigCapFor_PREFIX + response.getTenderSubType(), pe);
            }
        }
        else
        {
            // If there is no tender sub type associated with the credit tender, the
            // signature is required.
            signatureRequired = true;
        }
        return signatureRequired;
    }

    /**
     * Returns true if the response indicates the tender sub type is HouseCard
     * and the response contains promotion disclosure data.
     *
     * @param response
     * @return true if the response indicates the tender sub type is HouseCard
     * and the response contains promotion disclosure data.
     */
    protected boolean isHouseAccountWithoutPromotion(AuthorizeTransferResponseIfc response)
    {
        boolean isHouseCard = CardTypeCodesIfc.HOUSE_CARD.equals(response.getTenderSubType());
        boolean doesNotPromotionalData = StringUtils.isEmpty(response.getPromotionDescription());
        return isHouseCard && doesNotPromotionalData;
    }
}
