/* ===========================================================================
* Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returns/returnoptions/GetTenderIDTokenSite.java /rgbustores_13.4x_generic_branch/5 2012/02/22 13:40:27 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    asinton   02/22/12 - clear the scrollingReceiptInSession flag on the
 *                         CPOIPaymentUtility so that the added return items
 *                         can appear on the CPOI.
 *    blarsen   11/08/11 - Accepting additional response codes. The PinComm
 *                         request was changes slightly. It now returns
 *                         different success/approved response code.
 *    blarsen   08/02/11 - Renamed token to accountNumberToken to be
 *                         consistent.
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returnoptions;

// Foundation imports
import oracle.retail.stores.common.utility.Util;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.manager.ifc.PaymentManagerIfc;
import oracle.retail.stores.domain.manager.payment.CardTokenRequestIfc;
import oracle.retail.stores.domain.manager.payment.CardTokenResponseIfc;
import oracle.retail.stores.domain.manager.payment.PaymentServiceResponseIfc.ResponseCode;
import oracle.retail.stores.domain.transaction.SearchCriteriaIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CPOIPaymentUtility;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.returns.returnitem.ReturnItemCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;

//------------------------------------------------------------------------------
/**
    Request the card number token from the payment service.

    @version $Revision: /rgbustores_13.4x_generic_branch/5 $
**/
//------------------------------------------------------------------------------
public class GetTenderIDTokenSite extends PosSiteActionAdapter
{
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -6374020570165720199L;

    /**
     * Request the card number token from the payment service.
     *
     * @param bus the bus arriving at this site
     */
    @Override
    public void arrive(BusIfc bus)
    {
        Letter letter = new Letter(CommonLetterIfc.FAILURE);
        ReturnItemCargo cargo = (ReturnItemCargo) bus.getCargo();
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);

        // Set the status on the UI
        ui.statusChanged(POSUIManagerIfc.FINANCIAL_NETWORK_STATUS,
                         POSUIManagerIfc.ONLINE);

        ui.showScreen(POSUIManagerIfc.CREDIT_DEBIT_CARD, new POSBaseBeanModel());


        try
        {
            // Set the CPOIPaymentUtility's scrollingReceiptInSession flag to false since
            // this getCardToken will set the server's state to close the scrolling receipt session.
            CPOIPaymentUtility.getInstance().setScrollingReceiptInSession(false);
            // get access to the payment manager
            PaymentManagerIfc paymentManager = (PaymentManagerIfc)Gateway.getDispatcher().getManager(PaymentManagerIfc.TYPE);
            CardTokenResponseIfc response = (CardTokenResponseIfc)paymentManager.getCardToken(getRequest(bus));

            ResponseCode responseCode = response.getResponseCode();
            if(( ResponseCode.Approved.equals(responseCode) || ResponseCode.InquiryForTenderSucceeded.equals(responseCode)) &&
               (!Util.isEmpty(response.getAccountNumberToken()) || !Util.isEmpty(response.getMaskedAccountNumber())))
            {
                SearchCriteriaIfc searchCriteria = cargo.getSearchCriteria();
                if (searchCriteria == null)
                {
                   searchCriteria = DomainGateway.getFactory().getSearchCriteriaInstance();
                }

                searchCriteria.setAccountNumberToken(response.getAccountNumberToken());
                searchCriteria.setMaskedAccountNumber(response.getMaskedAccountNumber());

                cargo.setSearchCriteria(searchCriteria);
                cargo.setSearchByTender(true);
                cargo.setHaveReceipt(false);
                letter = new Letter(CommonLetterIfc.SUCCESS);
            }
        }
        catch (Exception de)
        {
            logger.warn(
                    "Error while requesting card number token: " + de.getMessage() + "");
            ui.statusChanged(POSUIManagerIfc.FINANCIAL_NETWORK_STATUS,
                    POSUIManagerIfc.OFFLINE);
        }

        bus.mail(letter, BusIfc.CURRENT);
    }

    /*
     * (non-Javadoc)
     * Create the request for the card number token.
     */
    private CardTokenRequestIfc getRequest(BusIfc bus)
    {
        ReturnItemCargo cargo = (ReturnItemCargo) bus.getCargo();
        CardTokenRequestIfc request = DomainGateway.getFactory().getCardTokenRequestInstance();
        request.setWorkstation(cargo.getRegister().getWorkstation());

        return request;
    }

}
