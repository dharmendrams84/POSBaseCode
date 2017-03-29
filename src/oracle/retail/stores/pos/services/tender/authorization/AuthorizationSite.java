/* ===========================================================================
* Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/authorization/AuthorizationSite.java /rgbustores_13.4x_generic_branch/37 2012/02/29 13:30:53 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    asinton   02/29/12 - allow call referral for credit when offline response
 *                         received.
 *    asinton   02/22/12 - allow call referral for gift card when offline
 *    blarsen   01/30/12 - Change default credit floor limit to $50.00
 *    blarsen   01/30/12 - Fixed floor limit comparison. If amount == floor
 *                         limit, then floor limit auth is eligible.
 *    asinton   01/11/12 - Use FloorLimit ResponseCode for improved handling of
 *                         this response.
 *    asinton   01/06/12 - changes to make credit floor limit authorizations
 *                         synchronous
 *    asinton   12/28/11 - fixed logic to not allow floor limit approvals with
 *                         Debit
 *    asinton   12/22/11 - Get Offline Credit Floor Limit and set in the
 *                         request
 *    cgreene   10/26/11 - set base amount when it is null
 *    sgu       10/20/11 - check floor limit for call referral response
 *    jswan     10/07/11 - Fixed positive ID issue with Check tender.
 *    icole     10/07/11 - Don't allow referral for DEBIT.
 *    jswan     10/04/11 - Fixed offline issues with SAFTOR.
 *    icole     09/29/11 - Treat CPOI timeout as OFFLINE. DefectID 937.
 *    blarsen   09/27/11 - Ending scrolling receipt session for
 *                         AuthorizeCardRefund requests.
 *    jswan     09/26/11 - Modified to force the check tender to execute the
 *                         referral path when the response code is equal to
 *                         Offline.
 *    icole     09/15/11 - Moved ending scrolling receipt from
 *                         CardAuthConnector to AuthorizationSite using
 *                         CPOIUtility.
 *    blarsen   09/13/11 - Debit with cash back always prompting operator with
 *                         partial approval. Old logic did not consider cash
 *                         back in response and assumed if request/response
 *                         auth amounts were not equal, there was a partial
 *                         approval. Fixed this.
 *    asinton   08/24/11 - set the POSGiftCardEntryRequired flag on the request
 *    ohorne    08/09/11 - APF:foreign currency support
 *    cgreene   07/29/11 - check for referral before offline
 *    cgreene   07/14/11 - fix tendering and reload gift cards
 *    blarsen   06/30/11 - Setting ui's financial network status flag based on
 *                         payment manager response. This will update the
 *                         online/offline indicator on POS UI.
 *    blarsen   06/21/11 - Added handling for configuration error and the
 *                         default 'unknown' error case.
 *    blarsen   06/10/11 - Displaying generic 'please wait: authorizing...'
 *                         message when sending request to payment service.
 *    blarsen   06/06/11 - Added cancel-card-inquiry-by-customer flow.
 *    cgreene   05/27/11 - move auth response objects into domain
 *    cgreene   05/20/11 - implemented enums for reponse code and giftcard
 *                         status code
 *    ohorne    05/12/11 - added offline floor limit logic
 *    sgu       05/05/11 - refactor payment technician commext framework
 *    asinton   03/25/11 - Moved APF request and response objects to common
 *                         module.
 *    asinton   03/21/11 - new tender authorization service
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.authorization;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.manager.ifc.PaymentManagerIfc;
import oracle.retail.stores.domain.manager.payment.AuthorizationConstantsIfc;
import oracle.retail.stores.domain.manager.payment.AuthorizationConstantsIfc.TenderType;
import oracle.retail.stores.domain.manager.payment.AuthorizeTransferRequestIfc;
import oracle.retail.stores.domain.manager.payment.AuthorizeTransferResponseIfc;
import oracle.retail.stores.domain.manager.payment.PaymentServiceRequestIfc;
import oracle.retail.stores.domain.manager.payment.PaymentServiceResponseIfc.ResponseCode;
import oracle.retail.stores.domain.store.WorkstationIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.ado.tender.TenderTypeEnum;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CPOIPaymentUtility;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;

/**
 * This site calls the Payment Manager for authorization.
 *
 * @author asinton
 * @since 13.4
 */
@SuppressWarnings("serial")
public class AuthorizationSite extends PosSiteActionAdapter
{
    /** Constant for application property group */
    public static final String APPLICATION_PROPERTY_GROUP = "application";

    /** Constant for manual entry gift card property name */
    public static final String POS_GFCARD_TENDER_ENTRY_REQUIRED = "POSGFCardTenderEntryRequired";

    /** Constant for Offline Check Floor Limit Parameter */
    public static final String OFFLINE_CHECK_FLOOR_LIMIT_PARAMETER = "OfflineCheckFloorLimit";

    /** Constant for Offline Credit Floor Limit Parameter */
    public static final String OFFLINE_CREDIT_FLOOR_LIMIT_PARAMETER = "OfflineCreditFloorLimit";

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.application.SiteActionAdapter#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void arrive(BusIfc bus)
    {
        AuthorizationCargo cargo = (AuthorizationCargo)bus.getCargo();
        if (cargo.getCurrentRequest().getRequestType().equals(PaymentServiceRequestIfc.RequestType.AuthorizeCard) ||
                        cargo.getCurrentRequest().getRequestType().equals(PaymentServiceRequestIfc.RequestType.AuthorizeCardRefund))
        {
        	CPOIPaymentUtility cpoiPaymentUtility = CPOIPaymentUtility.getInstance();
        	WorkstationIfc workstation = cargo.getRegister().getWorkstation();
        	cpoiPaymentUtility.endScrollingReceipt(workstation);
        }
        PaymentManagerIfc paymentManager = (PaymentManagerIfc)bus.getManager(PaymentManagerIfc.TYPE);
        AuthorizeTransferRequestIfc request = cargo.getCurrentRequest();
        setOfflineFloorLimit(bus, request);
        boolean posGFCardEntryRequired = Gateway.getBooleanProperty(APPLICATION_PROPERTY_GROUP, POS_GFCARD_TENDER_ENTRY_REQUIRED, false);
        request.setPosGFCardEntryRequired(posGFCardEntryRequired);

        AuthorizeTransferResponseIfc response = null;
        String letter = CommonLetterIfc.ERROR;

        displayAuthorizationUI(bus);

        try
        {
            response = (AuthorizeTransferResponseIfc)paymentManager.authorize(request);
        }
        catch(Exception e)
        {
            logger.error("Exception caught while calling the PaymentManagerIfc.authorize()", e);
        }
        if (response != null)
        {
            cargo.addResponse(response);
            // set the base amount if the response wasn't able to
            if (response.getBaseAmount() == null)
            {
                response.setBaseAmount(request.getBaseAmount());
            }
            ResponseCode responseCode = response.getResponseCode();
            if (ResponseCode.Approved.equals(responseCode))
            {
                // The authorized amount for a refund must come back from the
                // authorization service with a negative amount in order to balance
                // the transaction.  However, the request is always expressed as a
                // positive number; therefore, make this comparison to the absolute
                // value of the response (authorized) amount.

                // Note that the base amount in the response might be more than the base amount in the request
                // for Debit card tenders when cash back is selected by the customer.
                if ( response.getBaseAmount().abs().compareTo(request.getBaseAmount()) == CurrencyIfc.LESS_THAN)
                {
                    letter = CommonLetterIfc.PARTIALLY_APPROVED;
                }
                else
                {
                    letter = CommonLetterIfc.APPROVED;
                }
            }
            else if (ResponseCode.PositiveIDRequired.equals(responseCode))
            {
                // Already have the ID for Check tenders.
                if ((TenderType.CHECK.equals(response.getTenderType())))
                {
                    letter = CommonLetterIfc.APPROVED;
                }
                else
                {
                    letter = CommonLetterIfc.POSITIVE_ID;
                }
            }
            else if (ResponseCode.Declined.equals(responseCode))
            {
                letter = CommonLetterIfc.DECLINED;
            }
            else if (ResponseCode.ConfigurationError.equals(responseCode))
            {
                letter = CommonLetterIfc.CONFIGURATION_ERROR;
            }
            else if (ResponseCode.InquiryForTenderCanceledByCustomer.equals(responseCode))
            {
                letter = CommonLetterIfc.CANCELED_BY_CUSTOMER;
            }
            else if (ResponseCode.Timeout.equals(responseCode))
            {
                letter = CommonLetterIfc.TIMEOUT;
            }
            else if (ResponseCode.DeviceTimeout.equals(responseCode))
            {
            	letter = CommonLetterIfc.OFFLINE;
            }
            else if (ResponseCode.Referral.equals(responseCode))
            {
                // DEBIT is not allowed to do Call Referral
                if (TenderType.DEBIT.equals(response.getTenderType()))
                {
                    letter = CommonLetterIfc.DECLINED;
                }
                else
                {
                    letter = CommonLetterIfc.REFERRAL;
                }
            }
            else if(ResponseCode.FloorLimit.equals(responseCode))
            {
                letter = CommonLetterIfc.FLOOR_LIMIT;
            }
            else if (ResponseCode.Offline.equals(responseCode) || AuthorizationConstantsIfc.ONLINE != response.getFinancialNetworkStatus())
            {
                // Either the application has the reference to the tender ID, or it can get
                // the reference.  Do not do Referrals for DEBIT
                if (TenderType.CHECK.equals(response.getTenderType()) ||
                        TenderType.GIFT_CARD.equals(response.getTenderType()) ||
                        TenderType.CREDIT.equals(response.getTenderType()))
                {
                    letter = CommonLetterIfc.REFERRAL;
                }
                else
                {
                    letter = CommonLetterIfc.OFFLINE;
                }
            }
        }

        UIUtilities.setFinancialNetworkUIStatus(response, (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE));

        bus.mail(new Letter(letter), BusIfc.CURRENT);
    }

    /**
     * Fetches the offline floor limit from parameters and sets it in the request
     * @param bus
     * @param request
     */
    protected void setOfflineFloorLimit(BusIfc bus, AuthorizeTransferRequestIfc request)
    {
        CurrencyIfc floorLimit = null;
        try
        {
            ParameterManagerIfc parameterManager = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
            // if it's a check, we know it's a check, then use the check floor limit parameter
            if(TenderType.CHECK.equals(request.getRequestTenderType()))
            {
                floorLimit = DomainGateway.getBaseCurrencyInstance(parameterManager.getStringValue(OFFLINE_CHECK_FLOOR_LIMIT_PARAMETER));
            }
            // else use the credit floor limit parameter.
            else
            {
                floorLimit = DomainGateway.getBaseCurrencyInstance(parameterManager.getStringValue(OFFLINE_CREDIT_FLOOR_LIMIT_PARAMETER));
            }
        }
        catch(ParameterException pe)
        {
            logger.warn("Could not get parameter", pe);
            floorLimit = DomainGateway.getBaseCurrencyInstance("50.00");
        }
        request.setFloorLimit(floorLimit);
    }

    /**
     * This method displays the authorize screen.
     *
     * This instructs the operator to wait for authorization to complete.
     *
     * @param bus
     */
    protected void displayAuthorizationUI(BusIfc bus)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        UtilityManagerIfc utility = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);

        PromptAndResponseModel parModel = new PromptAndResponseModel();

        String promptText = utility.retrieveText(POSUIManagerIfc.PROMPT_AND_RESPONSE_SPEC,
                BundleConstantsIfc.COMMON_BUNDLE_NAME, "AuthorizationPrompt", "AuthorizationPrompt");

        parModel.setPromptText(promptText);

        POSBaseBeanModel baseModel = new POSBaseBeanModel();
        baseModel.setPromptAndResponseModel(parModel);
        ui.showScreen(POSUIManagerIfc.AUTHORIZATION, baseModel);
    }

}
