/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/authorization/AuthorizationReferralApprovedRoad.java /rgbustores_13.4x_generic_branch/6 2011/10/14 14:26:44 ohorne Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    ohorne    10/14/11 - fix for NPE when over tendering after call referral
 *    jswan     09/28/11 - Modified to force signature capture for manually
 *                         approved credit tenders.
 *    cgreene   09/22/11 - negate call referral amount if the transtype is
 *                         refund or void.
 *    blarsen   08/12/11 - Added support for CallReferralApprovedAmount. This
 *                         is the now editable amount on the call referral
 *                         screen.
 *    asinton   06/27/11 - Added new UI for Call Referral and application flow
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.authorization;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.domain.manager.payment.AuthorizationConstantsIfc;
import oracle.retail.stores.domain.manager.payment.AuthorizeTransferResponseIfc;
import oracle.retail.stores.domain.manager.payment.AuthorizeTransferResponseIfc.AuthorizationMethod;
import oracle.retail.stores.domain.manager.payment.PaymentServiceResponseIfc.ResponseCode;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;

/**
 * This road captures the approval code from the UI and stores in into the
 * response. Also, the response's ResponseCode attribute is set to Approved.
 *
 * @author asinton
 * @since 13.4
 */
@SuppressWarnings("serial")
public class AuthorizationReferralApprovedRoad extends PosLaneActionAdapter
{

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.application.LaneActionAdapter#traverse(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void traverse(BusIfc bus)
    {
        AuthorizationCargo authorizationCargo = (AuthorizationCargo)bus.getCargo();
        // retrieve the call referral approval code
        String approvalCode = authorizationCargo.getCallReferralApprovalCode();
        // retrieve the call referral approval code
        CurrencyIfc approvedAmount = authorizationCargo.getCallReferralApprovedAmount();
        // reset the callReferralApprovalCode
        authorizationCargo.setCallReferralApprovalCode(null);
        // reset the callReferralApprovedAmount
        authorizationCargo.setCallReferralApprovedAmount(null);
        // get the most recently added response
        AuthorizeTransferResponseIfc response = null;
        if (authorizationCargo.getResponseList().size() > 0)
        {
            response = authorizationCargo.getResponseList().get(authorizationCargo.getResponseList().size() - 1);
        }
        if (response != null)
        {
            // set values entered on referral screen
            response.setAuthorizationCode(approvalCode);
            response.setResponseMessage(approvalCode);
            response.setAuthorizationMethod(AuthorizationMethod.Manual);
            // set the amount that was approved onto the response.
            int authTransactionType = authorizationCargo.getCurrentRequest().getAuthorizationTransactionType();
            if (authTransactionType == AuthorizationConstantsIfc.TRANS_VOID ||
                authTransactionType == AuthorizationConstantsIfc.TRANS_CREDIT)
            {
                approvedAmount = approvedAmount.negate();
            }
            response.setBaseAmount(approvedAmount);
            // set the response approved
            response.setResponseCode(ResponseCode.Approved);
        }
        else
        {
            logger.warn("Response is null");
        }
    }

}
