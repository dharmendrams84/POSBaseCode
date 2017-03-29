/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/authorization/AuthorizationCargo.java /rgbustores_13.4x_generic_branch/7 2011/08/12 15:43:29 blarsen Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   08/12/11 - Added callReferralApprovedAmount. This must be saved
 *                         since it is editable by the operator.
 *    cgreene   07/28/11 - added support for manager override for card decline
 *    cgreene   05/27/11 - move auth response objects into domain
 *    asinton   03/25/11 - Moved APF request and response objects to common
 *                         module.
 *    asinton   03/21/11 - new tender authorization service
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.authorization;

import java.util.ArrayList;
import java.util.List;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.domain.manager.payment.AuthorizeTransferRequestIfc;
import oracle.retail.stores.domain.manager.payment.AuthorizeTransferResponseIfc;
import oracle.retail.stores.pos.services.common.AbstractFinancialCargo;

/**
 * Cargo for the Authorization service.  This cargo maintains the request and response
 * lists for authorization.
 *
 * @author asinton
 * @since 13.4
 */
public class AuthorizationCargo extends AbstractFinancialCargo
{
    private static final long serialVersionUID = -6947857149521681677L;

    /** List of Request objects */
    protected List<AuthorizeTransferRequestIfc> requestList;

    /** List of Response objects */
    protected List<AuthorizeTransferResponseIfc> responseList;

    /** current index of requests */
    protected int currentIndex = -1;

    /** Call referral approval code */
    protected String callReferralApprovalCode;

    /** Call referral approved amount */
    protected CurrencyIfc callReferralApprovedAmount;

    /**
     * Returns the current request object to be processed.
     * @return
     */
    public AuthorizeTransferRequestIfc getCurrentRequest()
    {
        AuthorizeTransferRequestIfc currentReqeust = null;
        if(requestList != null && requestList.size() > currentIndex)
        {
            currentReqeust = requestList.get(currentIndex);
        }
        return currentReqeust;
    }

    /**
     * Returns the current response object.
     * @return the current response object.
     */
    public AuthorizeTransferResponseIfc getCurrentResponse()
    {
        AuthorizeTransferResponseIfc currentResponse = null;
        if(getResponseList().size() > currentIndex)
        {
            currentResponse = getResponseList().get(currentIndex);
        }
        return currentResponse;
    }

    /**
     * Returns the list of response objects.
     * @return the list of response objects.
     */
    public List<AuthorizeTransferResponseIfc> getResponseList()
    {
        if(responseList == null)
        {
            responseList = new ArrayList<AuthorizeTransferResponseIfc>();
        }
        return responseList;
    }

    /**
     * Adds a response object to the list.
     * @param response
     */
    public void addResponse(AuthorizeTransferResponseIfc response)
    {
        getResponseList().add(response);
    }

    /**
     * Sets the list of request objects in this cargo.
     * @param list
     */
    public void setRequestList(List<AuthorizeTransferRequestIfc> list)
    {
        this.requestList = list;
    }

    /**
     * Increments the current index.  This method should only be called by the
     * EvaluateRequestListSite.
     */
    public void incrementCurrentIndex()
    {
        currentIndex++;
    }

    /**
     * Gets the callReferralApprovalCode value.
     * @return the callReferralApprovalCode
     */
    public String getCallReferralApprovalCode()
    {
        return callReferralApprovalCode;
    }

    /**
     * Sets the callReferralApprovalCode value.
     * @param callReferralApprovalCode the callReferralApprovalCode to set
     */
    public void setCallReferralApprovalCode(String callReferralApprovalCode)
    {
        this.callReferralApprovalCode = callReferralApprovalCode;
    }

    /**
     * Gets the callReferralApprovedAmount value.
     * @return the callReferralApprovedAmount
     */
    public CurrencyIfc getCallReferralApprovedAmount()
    {
        return callReferralApprovedAmount;
    }

    /**
     * Sets the callReferralApprovedAmount value.
     * @param callReferralApprovedAmount the callReferralApprovedAmount to set
     */
    public void setCallReferralApprovedAmount(CurrencyIfc callReferralApprovedAmount)
    {
        this.callReferralApprovedAmount = callReferralApprovedAmount;
    }
}
