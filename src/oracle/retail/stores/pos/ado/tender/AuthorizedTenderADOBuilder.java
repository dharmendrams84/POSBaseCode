/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/tender/AuthorizedTenderADOBuilder.java /rgbustores_13.4x_generic_branch/44 2011/10/08 12:06:33 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    asinton   10/08/11 - capture prepaid remaining balance for credit and
 *                         debit
 *    asinton   10/06/11 - transfer the credit disclosure data into the credit
 *                         tender line item.
 *    jswan     09/12/11 - Modifications for reversals of Gift Cards when
 *                         escaping from the Tender Tour.
 *    cgreene   09/12/11 - added support for setting balance left on prepaid
 *                         card
 *    cgreene   09/12/11 - revert aba number encryption, which is not sensitive
 *    blarsen   08/26/11 - Adding parameter tender limit check for sig cap
 *                         required attribute
 *    ohorne    08/18/11 - PersonalID cleanup
 *    ohorne    08/09/11 - APF:foreign currency support
 *    blarsen   08/02/11 - Renamed token to accountNumberToken to be
 *                         consistent.
 *    cgreene   07/28/11 - added support for manager override for card decline
 *    cgreene   07/28/11 - added non-list oriented method
 *    cgreene   07/20/11 - added support for requiring signature for icc cards
 *    sgu       07/20/11 - set entry method and conversion code to tender
 *                         attributes
 *    masahu    07/20/11 - Encryption CR: Fix POS and CO build issues
 *    rrkohli   07/19/11 - encryption cr
 *    rrkohli   07/04/11 - Encryption CR
 *    rrkohli   06/29/11 - encryption CR
 *    blarsen   07/15/11 - Renamed rawJournalKey to journalKey.
 *    blarsen   07/14/11 - Added tender attribs required for reversals
 *    blarsen   07/12/11 - Added getRetrievalReferenceNumber() to tender ADO.
 *    asinton   07/12/11 - fixed some entry method coding
 *    cgreene   07/07/11 - convert entryMethod to an enum
 *    asinton   07/01/11 - Fixed NullPointerException when EntryMethod is null.
 *    asinton   06/29/11 - Fixed Gift Card Tender.
 *    cgreene   06/29/11 - add token column and remove encrypted/hashed account
 *                         number column in credit-debit tender table.
 *    jswan     06/22/11 - Modified to support signature capture in APF.
 *    blarsen   06/16/11 - renamed TOKEN to PAYMENT_SERVICE_TOKEN
 *    cgreene   06/13/11 - don't set icc details unless there are icc details
 *    ohorne    05/27/11 - fixed potential NPE in buildTenderADOs()
 *    ohorne    05/27/11 - fixed potential NPE in buildTenderADOs()
 *    cgreene   05/27/11 - move auth response objects into domain
 *    blarsen   05/24/11 - Removed dependency on expiry. Expiry no longer
 *                         returned by payment service.
 *    cgreene   05/20/11 - implemented enums for reponse code and giftcard
 *                         status code
 *    blarsen   05/20/11 - Changed TenderType from int constants to a real
 *                         enum.
 *    blarsen   05/12/11 - Added TOKEN for card authorizations.
 *    ohorne    05/09/11 - Check and journaling enhancements
 *    asinton   04/05/11 - Add tender builder for authorized tenders.
 *    asinton   03/31/11 - Add tender builder for authorized tenders.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.tender;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.common.parameter.ParameterConstantsIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.manager.payment.AuthorizationConstantsIfc;
import oracle.retail.stores.domain.manager.payment.AuthorizationConstantsIfc.TenderType;
import oracle.retail.stores.domain.manager.payment.AuthorizeTransferResponseIfc;
import oracle.retail.stores.domain.manager.payment.PaymentServiceResponseIfc.ResponseCode;
import oracle.retail.stores.domain.tender.AuthorizableTenderIfc;
import oracle.retail.stores.domain.tender.TenderChargeIfc;
import oracle.retail.stores.domain.tender.TenderLineItemConstantsIfc;
import oracle.retail.stores.domain.utility.EYSDomainIfc;
import oracle.retail.stores.foundation.factory.FoundationObjectFactory;
import oracle.retail.stores.foundation.manager.device.EncipheredCardDataIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.manager.ManagerIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.keystoreencryption.EncryptionServiceException;
import oracle.retail.stores.pos.ado.ADOException;
import oracle.retail.stores.pos.ado.context.ContextFactory;
import oracle.retail.stores.pos.ado.factory.ADOFactoryComplex;
import oracle.retail.stores.pos.ado.factory.TenderFactoryIfc;

import org.apache.log4j.Logger;

/**
 * Implementation of the BuildAuthorizedTenderADOIfc interface.
 * @author asinton
 * @since 13.4
 */
public class AuthorizedTenderADOBuilder implements AuthorizedTenderADOBuilderIfc
{
    /**
     * Logger
     */
    public static final Logger logger = Logger.getLogger(AuthorizedTenderADOBuilder.class);

    /** Date formatter for credit/debit cards */
    protected SimpleDateFormat formatter = new SimpleDateFormat("yymm");

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.ado.tender.BuildAuthorizedTenderADOIfc#buildTenderADOs(java.util.List)
     */
    public List<TenderADOIfc> buildTenderADOs(List<AuthorizeTransferResponseIfc> responses)
    {
        List<TenderADOIfc> tenders = new ArrayList<TenderADOIfc>();
        for(AuthorizeTransferResponseIfc response : responses)
        {

            TenderADOIfc tenderADO = buildTenderADO(response);
            if (tenderADO != null)
            {
                // Load the TenderADOIfc object into the return list
                tenders.add(tenderADO);
            }
        }
        return tenders;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.ado.tender.BuildAuthorizedTenderADOIfc#buildTenderADO(AuthorizeTransferResponseIfc)
     */
    public TenderADOIfc buildTenderADO(AuthorizeTransferResponseIfc response)
    {
        TenderADOIfc tenderADO = null;
        try
        {
            ResponseCode responseCode = response.getResponseCode();
            if (responseCode != null)
            {
                // Load the data from the Tender Authorization Response object into the AuthorizableADOIfc object.
                HashMap<String, Object> tenderAttributes = new HashMap<String, Object>();

                if (ResponseCode.Approved.equals(responseCode) || ResponseCode.PositiveIDRequired.equals(responseCode) ||
                        ResponseCode.ApprovedFloorLimit.equals(responseCode))
                {
                    tenderAttributes.put(TenderConstants.AUTH_STATUS, AuthorizableTenderIfc.AUTHORIZATION_STATUS_APPROVED);
                }
                else if (ResponseCode.Declined.equals(responseCode))
                {
                    tenderAttributes.put(TenderConstants.AUTH_STATUS, AuthorizableTenderIfc.AUTHORIZATION_STATUS_DECLINED);
                }
                else
                {
                    tenderAttributes.put(TenderConstants.AUTH_STATUS, AuthorizableTenderIfc.AUTHORIZATION_STATUS_PENDING);
                }

                tenderAttributes.put(TenderConstants.TENDER_TYPE, formatTenderType(response.getTenderType()));
                tenderAttributes.put(TenderConstants.AUTH_CODE, response.getAuthorizationCode());
                tenderAttributes.put(TenderConstants.AUTH_RESPONSE, response.getResponseMessage());
                if(response.getAuthorizationMethod() != null)
                {
                    tenderAttributes.put(TenderConstants.AUTH_METHOD, response.getAuthorizationMethod().toString());
                }
                tenderAttributes.put(TenderConstants.ACCOUNT_NUMBER_TOKEN, response.getAccountNumberToken());

                // for reversals
                tenderAttributes.put(TenderConstants.JOURNAL_KEY , response.getJournalKey());
                tenderAttributes.put(TenderConstants.AUTH_SEQUENCE_NUMBER , response.getRetrievalReferenceNumber());
                tenderAttributes.put(TenderConstants.LOCAL_TIME, response.getLocalTime());
                tenderAttributes.put(TenderConstants.LOCAL_DATE, response.getLocalDate());
                tenderAttributes.put(TenderConstants.ACCOUNT_DATA_SOURCE, response.getAccountDataSource());
                tenderAttributes.put(TenderConstants.PAYMENT_SERVICE_INDICATOR, response.getPaymentServiceIndicator());
                tenderAttributes.put(TenderConstants.TRANSACTION_ID, response.getTransactionId());
                tenderAttributes.put(TenderConstants.AUTH_RESPONSE_CODE, response.getAuthorizationResponseCode());
                tenderAttributes.put(TenderConstants.VALIDATION_CODE, response.getValidationCode());
                tenderAttributes.put(TenderConstants.AUTH_SOURCE, response.getAuthorizationSource());
                tenderAttributes.put(TenderConstants.HOST_REFERENCE, response.getHostReference());
                tenderAttributes.put(TenderConstants.TRACE_NUMBER, response.getTraceNumber());

                if (response.getBaseAmount() != null)
                {
                    tenderAttributes.put(TenderConstants.AUTH_AMOUNT, response.getBaseAmount().toFormattedString());
                    tenderAttributes.put(TenderConstants.AMOUNT, response.getBaseAmount().toFormattedString());
                }

                CurrencyIfc alternateAmount = response.getAlternateAmount();
                if (alternateAmount != null)
                {
                    tenderAttributes.put(TenderConstants.FOREIGN_CURRENCY, alternateAmount.getType());
                    tenderAttributes.put(TenderConstants.ALTERNATE_CURRENCY_TYPE, alternateAmount.getType());
                    tenderAttributes.put(TenderConstants.ALTERNATE_AMOUNT, alternateAmount.toFormattedString());
                }

                tenderAttributes.put(TenderConstants.FINANCIAL_NETWORK_STATUS,
                                    (response.getFinancialNetworkStatus() == AuthorizationConstantsIfc.ONLINE ?
                                                                             AuthorizableTenderIfc.AUTHORIZATION_NETWORK_ONLINE :
                                                                             AuthorizableTenderIfc.AUTHORIZATION_NETWORK_OFFLINE));
                tenderAttributes.put(TenderConstants.AUTH_DATE_TIME, response.getResponseTime());
                tenderAttributes.put(TenderConstants.SETTLEMENT_DATA, response.getSettlementData());
                tenderAttributes.put(TenderConstants.FLOOR_LIMIT_AMOUNT, response.getFloorLimit());

                String maskedCardNumber = response.getMaskedAccountNumber();
                if (maskedCardNumber != null)
                {
                    EncipheredCardDataIfc cardData = null;
                    try
                    {
                        cardData = FoundationObjectFactory.getFactory()
                                .createEncipheredCardDataInstance(maskedCardNumber.getBytes(),null,null,null);
                    }
                    catch(EncryptionServiceException ese)
                    {
                        ese.printStackTrace();
                    }
                    cardData.setCardName(response.getTenderSubType());
                    tenderAttributes.put(TenderConstants.ENCIPHERED_CARD_DATA, cardData);
                    tenderAttributes.put(TenderConstants.EXPIRATION_DATE, cardData.getEncryptedExpirationDate());
                }

                if (response.getICCDetails().getApplicationID() != null)
                {
                    tenderAttributes.put(TenderConstants.ICC_DETAILS, response.getICCDetails());
                }

                tenderAttributes.put(TenderConstants.PREPAID_REMAINING_BALANCE, response.getPrepaidRemainingBalance());
                tenderAttributes.put(TenderConstants.SIGNATURE_REQUIRED, response.isSignatureRequired());

                // if auth service does not require sig cap, do the parameter limits require it?
                if(!response.isSignatureRequired() && !Util.isEmpty(response.getTenderSubType()) &&
                            response.getBaseAmount().signum() != CurrencyIfc.NEGATIVE)
                {
                    try
                    {
                        // Get Minimum signature capture amount for this card type.
                        ParameterManagerIfc pm = (ParameterManagerIfc)getManager(ParameterManagerIfc.TYPE);
                        String parmValue = pm.getStringValue(ParameterConstantsIfc.
                                TENDERAUTHORIZATION_MinimumSigCapFor_PREFIX + response.getTenderSubType());
                        CurrencyIfc signatureAmount = DomainGateway.getBaseCurrencyInstance(parmValue);
                        // and if amount is positive but not greater then MinimumSigCapFor+<credit type> parameter
                        if(response.getBaseAmount().compareTo(signatureAmount) == CurrencyIfc.GREATER_THAN)
                        {
                            tenderAttributes.put(TenderConstants.SIGNATURE_REQUIRED, true);
                        }
                    }
                    catch(ParameterException pe)
                    {
                        logger.error("Unable to obtain parameter value for " + ParameterConstantsIfc.TENDERAUTHORIZATION_MinimumSigCapFor_PREFIX + response.getTenderSubType(), pe);
                    }
                }

                tenderAttributes.put(TenderConstants.PHONE_NUMBER, response.getPhoneNumber());
                tenderAttributes.put(TenderConstants.LOCALIZED_ID_TYPE, response.getPersonalIDType());
                tenderAttributes.put(TenderConstants.ID_STATE, response.getPersonalIDAuthority());
                tenderAttributes.put(TenderConstants.ID_ENTRY_METHOD, response.getPersonalIDEntryMethod());
                tenderAttributes.put(TenderConstants.ID_TRACK_1_DATA, response.getPersonalIDTrack1Data());
                tenderAttributes.put(TenderConstants.ID_TRACK_2_DATA, response.getPersonalIDTrack2Data());
                tenderAttributes.put(TenderConstants.ENCIPHERED_DATA_ID_NUMBER, response.getPersonalIDEncipheredData());
                tenderAttributes.put(TenderConstants.ENCIPHERED_DATA_MICR_NUMBER, response.getMicrEncipheredData());
                tenderAttributes.put(TenderConstants.CHECK_NUMBER, response.getTenderSequenceNumber());
                tenderAttributes.put(TenderConstants.ABA_NUMBER, response.getABANumber());
                tenderAttributes.put(TenderConstants.ENCIPHERED_DATA_ACCOUNT_NUMBER, response.getAccountNumberEncipheredData());
                tenderAttributes.put(TenderConstants.ENTRY_METHOD, response.getEntryMethod());
                tenderAttributes.put(TenderConstants.CONVERSION_CODE, response.getConversionCode());
                if (response.getGiftCardAccountType() != null)
                {
                    tenderAttributes.put(TenderConstants.GIFT_CARD_ACCOUNT_TYPE, response.getGiftCardAccountType());
                }
                if (response.getRequestCode() != null)
                {
                    tenderAttributes.put(TenderConstants.REQUEST_CODE, response.getRequestCode());
                }
                if(response.getCurrentBalance() != null)
                {
                    tenderAttributes.put(TenderConstants.REMAINING_BALANCE, response.getCurrentBalance().toFormattedString());
                }
                if(response.getReferenceCode() != null)
                {
                    tenderAttributes.put(TenderConstants.REFERENCE_CODE, response.getReferenceCode());
                }
                if (ResponseCode.ApprovedFloorLimit.equals(responseCode))
                {
                    // In the floor limit approval case, a e-check is treated as a deposited check
                    tenderAttributes.put(TenderConstants.CHECK_AUTH_TYPE, TenderLineItemConstantsIfc.TENDER_LINEDISPLAY_DESC[TenderLineItemConstantsIfc.TENDER_TYPE_CHECK]);
                    // The conversion code of a deposited check should always be null.
                    tenderAttributes.put(TenderConstants.CONVERSION_CODE, null);
                }

                // Determine which AuthorizableADOIfc implementation to instantiate (TenderCreditADO, TenderDebitADO, TenderGiftCardADO or TenderCheckADO).
                TenderFactoryIfc factory = (TenderFactoryIfc) ADOFactoryComplex.getFactory(TENDER_FACTORY);
                tenderADO = factory.createTender(tenderAttributes);
                EYSDomainIfc legacy = tenderADO.toLegacy();
                if(legacy instanceof TenderChargeIfc)
                {
                    TenderChargeIfc tenderCharge = (TenderChargeIfc)legacy;
                    tenderCharge.setCardType(response.getTenderSubType());
                    tenderCharge.setSignatureData(response.getSignature());
                    // set the Credit Card Accountability Responsibility and Discloser Action of 2009 data
                    tenderCharge.setAccountAPR(response.getAccountAPR());
                    tenderCharge.setAccountAPRType(response.getAccountAPRType());
                    tenderCharge.setPromotionAPR(response.getPromotionAPR());
                    tenderCharge.setPromotionAPRType(response.getPromotionAPRType());
                    tenderCharge.setPromotionDescription(response.getPromotionDescription());
                    tenderCharge.setPromotionDuration(response.getPromotionDuration());
                    // save the prepaid card amount
                    tenderCharge.setPrepaidRemainingBalance(response.getPrepaidRemainingBalance());
                }
            }
        }
        catch(ADOException ade)
        {
            logger.error("ADOException caught while trying to create the tender", ade);
        }
        catch(TenderException te)
        {
            logger.error("TenderException caught while trying to create the tender", te);
        }
        return tenderADO;
    }

    /**
     * Returns the TenderTypeEnum for the give authorizer tender type.
     * @param tenderType
     * @return the TenderTypeEnum for the give authorizer tender type.
     */
    private TenderTypeEnum formatTenderType(TenderType tenderType)
    {
        TenderTypeEnum returnType = null;

        if (TenderType.CREDIT.equals(tenderType))
        {
            returnType = TenderTypeEnum.CREDIT;
        }
        else if (TenderType.CHECK.equals(tenderType))
        {
            returnType = TenderTypeEnum.CHECK;
        }
        else if (TenderType.DEBIT.equals(tenderType))
        {
            returnType = TenderTypeEnum.DEBIT;
        }
        else if (TenderType.GIFT_CARD.equals(tenderType))
        {
            returnType = TenderTypeEnum.GIFT_CARD;
        }
        else if (TenderType.HOUSE_ACCOUNT.equals(tenderType))
        {
            returnType = TenderTypeEnum.CREDIT;
        }
        else
        {
            returnType = TenderTypeEnum.CREDIT;
            logger.error("Unknown tender type: " + tenderType);
        }
        return returnType;
    }

    /**
     * Returns the desired Manager
     *
     * @param managerType The desired Manager TYPE
     * @return the requested Manager
     */
    protected ManagerIfc getManager(String managerType)
    {
        return ContextFactory.getInstance().getContext().getManager(managerType);
    }

}
