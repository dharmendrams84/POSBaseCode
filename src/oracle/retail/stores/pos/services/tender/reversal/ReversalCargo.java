/* =============================================================================
* Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved. 
 * =============================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/reversal/ReversalCargo.java /rgbustores_13.4x_generic_branch/11 2011/09/14 10:16:02 jswan Exp $
 * =============================================================================
 * NOTES
 *
 *
 * MODIFIED    (MM/DD/YY)
 *    jswan     09/12/11 - Modifications for reversals of Gift Cards when
 *                         escaping from the Tender Tour.
 *    cgreene   09/13/11 - corrected comparing of enums
 *    cgreene   09/12/11 - revert aba number encryption, which is not sensitive
 *    blarsen   09/12/11 - Added error logging for should-never-happen cases.
 *    ohorne    08/18/11 - APF: check cleanup
 *    jswan     08/15/11 - Added original journal to reversal request for gift
 *                         cards.
 *    ohorne    08/09/11 - APF:foreign currency support
 *    blarsen   08/02/11 - Misc cleanup. Renamed token to accountNumberToken.
 *    blarsen   07/22/11 - Changed cargo to hold reversal request list. Added
 *                         build-request helper methods which are called from
 *                         shuttles.
 *    blarsen   07/19/11 - Initial version.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.reversal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.manager.payment.AuthorizeTransferRequestIfc;
import oracle.retail.stores.domain.manager.payment.AuthorizeTransferResponseIfc;
import oracle.retail.stores.domain.manager.payment.ReversalRequestIfc;
import oracle.retail.stores.domain.manager.payment.AuthorizationConstantsIfc.TenderType;
import oracle.retail.stores.domain.manager.payment.PaymentServiceRequestIfc.RequestType;
import oracle.retail.stores.domain.store.WorkstationIfc;
import oracle.retail.stores.foundation.tour.application.tourcam.ObjectRestoreException;
import oracle.retail.stores.foundation.tour.application.tourcam.SnapshotIfc;
import oracle.retail.stores.foundation.tour.application.tourcam.TourCamSnapshot;
import oracle.retail.stores.pos.ado.lineitem.TenderLineItemCategoryEnum;
import oracle.retail.stores.pos.ado.tender.TenderADOIfc;
import oracle.retail.stores.pos.ado.tender.TenderConstants;
import oracle.retail.stores.pos.ado.tender.TenderTypeEnum;
import oracle.retail.stores.pos.ado.transaction.RetailTransactionADOIfc;
import oracle.retail.stores.pos.services.common.AbstractFinancialCargo;
import oracle.retail.stores.pos.services.tender.AuthorizationLaunchShuttle;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

/**
 * Data required by the reversal service.
 */
public class ReversalCargo extends AbstractFinancialCargo implements Serializable
{

    /**
     * Logger
     */
    public static Logger logger = Logger.getLogger(ReversalCargo.class);

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 786917546472092592L;

    /** List of Request objects */
    protected List<ReversalRequestIfc> requestList;

    /**
     * Default constructor for ReversalCargo
     */
    public ReversalCargo()
    {
    }

    /**
     * Get the request list.
     * @returnrequestList
     */
    public List<ReversalRequestIfc> getRequestList()
    {
        return requestList;
    }

    /**
     * Set the request list.
     * @param requestList
     */
    public void setRequestList(List<ReversalRequestIfc> requestList)
    {
        this.requestList = requestList;
    }

    /**
     * General toString function
     *
     * @return the String representation of this class
     */
    public String toString()
    {
        super.toString();
        ToStringBuilder builder = new ToStringBuilder(this);
        appendToString(builder);
        return builder.toString();
    }

    /**
     * Add printable objects to the builder. Overriding methods should also
     * call super.appendToString(ToStringBuilder).
     *
     * @param builder
     * @see #toString()
     */
    protected void appendToString(ToStringBuilder builder)
    {
        for (ReversalRequestIfc request : requestList)
        {
            builder.append(request.toString());
        }
    }

    /**
     * Create a SnapshotIfc which can subsequently be used to restore the cargo to its current state.
     *
     * @return an object which stores the current state of the cargo.
     * @see oracle.retail.stores.foundation.tour.application.tourcam.SnapshotIfc
     */
    public SnapshotIfc makeSnapshot()
    {
        return new TourCamSnapshot(this);
    }

    /**
     * Reset the cargo data using the snapshot passed in.
     *
     * @param snapshot
     *            is the SnapshotIfc which contains the desired state of the cargo.
     * @exception ObjectRestoreException
     *                is thrown when the cargo cannot be restored with this snapshot
     */
    public void restoreSnapshot(SnapshotIfc snapshot) throws ObjectRestoreException
    {
    }

    /**
     * Builds a reversal request list from the reversal tenders in the cargo's transactionADO.
     *
     * @param bus
     * @param cargo
     */
    public static List<ReversalRequestIfc> buildRequestList(
                    WorkstationIfc workstation,
                    int transactionType,
                    RetailTransactionADOIfc currentTransactionADO)
    {
        List<ReversalRequestIfc> requestList = new ArrayList<ReversalRequestIfc>();
        String transactionID = currentTransactionADO.getTransactionID();

        TenderADOIfc[] reversalTenders =
            currentTransactionADO.getTenderLineItems(TenderLineItemCategoryEnum.REVERSAL_PENDING);

        for (TenderADOIfc tender: reversalTenders)
        {
            HashMap<String, Object> tenderAttributes = tender.getTenderAttributes();

            ReversalRequestIfc reversalRequest = buildRequest(
                            workstation,
                            transactionID,
                            transactionType,
                            tenderAttributes);

            requestList.add(reversalRequest);
        }

        return requestList;
    }

    /**
     * Build a reversal request for the specified
     * tender when Post Voiding a transaction.
     *
     * @param workstation the workstation
     * @param transactionID the ID of the transaction being reversed
     * @param transactionType the transaction type
     * @param tenderAttributes the tender attributes describing the tender to reverse
     * @return the reversal request
     */
    public static ReversalRequestIfc buildRequest(
                    WorkstationIfc workstation,
                    String transactionID,
                    int transactionType,
                    HashMap<String, Object> tenderAttributes)
    {
        ReversalRequestIfc request = DomainGateway.getFactory().getReversalRequestInstance();

        // fill in the fields which are common between the auth and reversal request
        AuthorizationLaunchShuttle.buildAuthRequest(
                        request,
                        workstation,
                        transactionID,
                        transactionType,
                        tenderAttributes);

        // fill the additional fields required for reversals
        TenderTypeEnum tenderType = (TenderTypeEnum)tenderAttributes.get(TenderConstants.TENDER_TYPE);
        if (TenderTypeEnum.CREDIT.equals(tenderType) || TenderTypeEnum.DEBIT.equals(tenderType))
        {
            request.setRequestType(RequestType.ReverseCard);
            String token = (String)tenderAttributes.get(TenderConstants.ACCOUNT_NUMBER_TOKEN);
            request.setAccountNumberToken(token);
            request.setJournalKey((String)tenderAttributes.get(TenderConstants.JOURNAL_KEY));
            request.setRetrievalReferenceNumber((String)tenderAttributes.get(TenderConstants.AUTH_SEQUENCE_NUMBER));
            request.setAuthorizationTime((String)tenderAttributes.get(TenderConstants.LOCAL_TIME));
            request.setAuthorizationDate((String)tenderAttributes.get(TenderConstants.LOCAL_DATE));
            request.setAccountDataSource((String)tenderAttributes.get(TenderConstants.ACCOUNT_DATA_SOURCE));
            request.setPaymentServiceIndicator((String)tenderAttributes.get(TenderConstants.PAYMENT_SERVICE_INDICATOR));
            request.setTransactionID((String)tenderAttributes.get(TenderConstants.TRANSACTION_ID));
            request.setAuthorizationCode((String)tenderAttributes.get(TenderConstants.AUTH_CODE));
            request.setAuthResponseCode((String)tenderAttributes.get(TenderConstants.AUTH_RESPONSE_CODE));
            request.setValidationCode((String)tenderAttributes.get(TenderConstants.VALIDATION_CODE));
            request.setAuthorizationSource((String)tenderAttributes.get(TenderConstants.AUTH_SOURCE));
            request.setHostReference((String)tenderAttributes.get(TenderConstants.HOST_REFERENCE));
            request.setTraceNumber((String)tenderAttributes.get(TenderConstants.TRACE_NUMBER));

        }
        else if (TenderTypeEnum.GIFT_CARD.equals(tenderType))
        {
            request.setRequestType(RequestType.ReverseGiftCard);
            request.setJournalKey((String)tenderAttributes.get(TenderConstants.JOURNAL_KEY));
            request.setAuthorizationTime((String)tenderAttributes.get(TenderConstants.LOCAL_TIME));
            request.setAuthorizationDate((String)tenderAttributes.get(TenderConstants.LOCAL_DATE));
            request.setGiftCardAccountType((String)tenderAttributes.get(TenderConstants.GIFT_CARD_ACCOUNT_TYPE));
            // Message sequence is stored in reference code.
            request.setReferenceCode((String)tenderAttributes.get(TenderConstants.REFERENCE_CODE));
            // The payment application specific request code is stored in this element.
            request.setRequestCode((String)tenderAttributes.get(TenderConstants.REQUEST_CODE));
        }
        else if (TenderTypeEnum.CHECK.equals(tenderType))
        {
            request.setRequestType(RequestType.ReverseECheck);
        }
        else
        {
            logger.error("Unable to populate reveral request. Unexpected tender type encountered: " + tenderType);
        }

        return request;
    }

    /**
     * Build a reversal request for the specified
     * tender when Canceling a transaction.
     *
     * @param authRequest the original request
     * @param authResponse the original response
     * @return the reversal request
     */
    public static ReversalRequestIfc buildRequest(AuthorizeTransferRequestIfc authRequest, AuthorizeTransferResponseIfc authResponse)
    {
        ReversalRequestIfc reversalRequest = DomainGateway.getFactory().getReversalRequestInstance();

        // fields from the original request
        reversalRequest.setWorkstation(authRequest.getWorkstation());
        reversalRequest.setTransactionType(authRequest.getTransactionType());
        reversalRequest.setRequestTenderType(authRequest.getRequestTenderType());
        reversalRequest.setAccountNumberEncipheredData(authRequest.getAccountNumberEncipheredData());
        reversalRequest.setCardData(authRequest.getCardData());
        reversalRequest.setEntryMethod(authRequest.getEntryMethod());
        reversalRequest.setABANumber(authRequest.getABANumber());
        reversalRequest.setTenderSequenceNumber(authRequest.getTenderSequenceNumber());
        reversalRequest.setMicrEncipheredData(authRequest.getMicrEncipheredData());
        reversalRequest.setPersonalIDEncipheredData(authRequest.getPersonalIDEncipheredData());
        reversalRequest.setPersonalIDType(authRequest.getPersonalIDType());
        reversalRequest.setPersonalIDAuthority(authRequest.getPersonalIDAuthority());
        reversalRequest.setPersonalIDEntryMethod(authRequest.getPersonalIDEntryMethod());
        reversalRequest.setPersonalIDTrack1Data(authRequest.getPersonalIDTrack1Data());
        reversalRequest.setPersonalIDTrack2Data(authRequest.getPersonalIDTrack2Data());
        reversalRequest.setPhoneNumber(authRequest.getPhoneNumber());
        reversalRequest.setConversionCode(authRequest.getConversionCode());
        reversalRequest.setFloorLimit(authRequest.getFloorLimit());
        reversalRequest.setRequestSubType(authRequest.getRequestSubType());
        reversalRequest.setAlternateAmount(authRequest.getAlternateAmount());
        reversalRequest.setAuthorizationTransactionType(authRequest.getAuthorizationTransactionType());

        // fields from the original response
        reversalRequest.setBaseAmount(authResponse.getBaseAmount());

        TenderType tenderType = authResponse.getTenderType();
        if (TenderType.CREDIT.equals(tenderType) || TenderType.DEBIT.equals(tenderType))
        {
            reversalRequest.setRequestType(RequestType.ReverseCard);
            reversalRequest.setAccountNumberToken(authResponse.getAccountNumberToken());
            reversalRequest.setJournalKey(authResponse.getJournalKey());
            reversalRequest.setRetrievalReferenceNumber(authResponse.getRetrievalReferenceNumber());
            reversalRequest.setAuthorizationTime(authResponse.getLocalTime());
            reversalRequest.setAuthorizationDate(authResponse.getLocalDate());
            reversalRequest.setAccountDataSource(authResponse.getAccountDataSource());
            reversalRequest.setPaymentServiceIndicator(authResponse.getPaymentServiceIndicator());
            reversalRequest.setTransactionID(authResponse.getTransactionId());
            reversalRequest.setAuthorizationCode(authResponse.getAuthorizationCode());
            reversalRequest.setAuthResponseCode(authResponse.getAuthResponseCode());
            reversalRequest.setValidationCode(authResponse.getValidationCode());
            reversalRequest.setAuthorizationSource(authResponse.getAuthorizationSource());
            reversalRequest.setHostReference(authResponse.getHostReference());
            reversalRequest.setTraceNumber(authResponse.getTraceNumber());
        }
        else if (TenderType.GIFT_CARD.equals(tenderType))
        {
            reversalRequest.setRequestType(RequestType.ReverseGiftCard);
            reversalRequest.setJournalKey(authResponse.getJournalKey());
            reversalRequest.setAuthorizationTime(authResponse.getLocalTime());
            reversalRequest.setAuthorizationDate(authResponse.getLocalDate());
            reversalRequest.setGiftCardAccountType(authResponse.getGiftCardAccountType());
            // Message sequence is stored in reference code
            reversalRequest.setReferenceCode(authResponse.getReferenceCode());
            reversalRequest.setRequestCode(authResponse.getRequestCode());
        }
        else if (TenderType.CHECK.equals(tenderType))
        {
            reversalRequest.setRequestType(RequestType.ReverseECheck);
        }
        else
        {
            logger.error("Unable to populate reveral request. Unexpected tender type encountered: " + tenderType);
        }

        return reversalRequest;
    }
}