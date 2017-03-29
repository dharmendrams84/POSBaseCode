/* ===========================================================================
* Copyright (c) 2001, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returns/returntransaction/CheckForExternalOrderSite.java /main/2 2011/02/16 09:13:29 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    jswan     06/30/10 - Checkin for first promotion of External Order
 *                         integration.
 *    jswan     06/17/10 - Checkin external order integration files for
 *                         refresh.
 *    jswan     06/01/10 - Checked in for refresh to latest lable.
 *    jswan     05/27/10 - Added for external order feature.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returntransaction;

import oracle.retail.stores.common.utility.Util;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.returns.returncommon.ReturnUtilities;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

/**
 * Checks for external order processing.
 */
public class CheckForExternalOrderSite extends PosSiteActionAdapter
{
    /** serialVersionUID */
    private static final long serialVersionUID = 8631045206289033939L;

    public final static String RETURN_UNAVAILABLE_EXT_ORDER = "ReturnUnavailableExtOrder";

    /**
     * Check for external order processing and mail letter.
     * 
     * @param bus Service Bus
     */
    @Override
    public void arrive(BusIfc bus)
    {
        ReturnTransactionCargo cargo = (ReturnTransactionCargo) bus.getCargo();
        UtilityManagerIfc utility = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
        SaleReturnTransactionIfc saleTransaction = cargo.getOriginalTransaction();

        // if there is not a customer linked to the current transaction
        // and there is a customer linked to the original transaction
        // link the customer of the original transaction.
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        if ((cargo.getTransaction() != null && cargo.getTransaction().getCustomer() == null)
            && (saleTransaction != null && saleTransaction.getCustomer() != null))
        {
            CustomerIfc customer = saleTransaction.getCustomer();
            ReturnUtilities.displayLinkedCustomer(ui, utility, customer);
        }

        //get the non-kit header items from the original sale
        SaleReturnLineItemIfc[] itemsArray = ReturnUtilities.processNonKitCodeHeaderItems(utility, saleTransaction);
        cargo.setOriginalSaleLineItems(itemsArray);
        cargo.setTransactionDetailsDisplayed(false);

        //save the header items for later comparison
        //if all component items for a kit are returned,
        //their header item must be added to the transaction to increment the
        //header inventory count
        cargo.setKitHeaderItems(saleTransaction.getKitHeaderLineItems());

        // Before display taxTotals, need to convert the longer precision
        // calculated total tax amount back to shorter precision tax total
        // amount for UI display.
        saleTransaction.getTransactionTotals().setTaxTotal(saleTransaction.getTransactionTotals().getTaxTotalUI());

        // Determine whether to process items as external order items.
        if (cargo.isExternalOrder())
        {
            bus.mail(new Letter("ExternalOrder"), BusIfc.CURRENT);
        }
        else
        {
            // If the transaction has an external order, the user must begin
            // the return process in the order entry system.
            if (!Util.isEmpty(cargo.getOriginalTransaction().getExternalOrderID()))
            {
                DialogBeanModel model = new DialogBeanModel();
                model.setResourceID(RETURN_UNAVAILABLE_EXT_ORDER);
                model.setType(DialogScreensIfc.ACKNOWLEDGEMENT);
                model.setButtonLetter(0, CommonLetterIfc.FAILURE);
                ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, model);
            }
            else
            {
                bus.mail(new Letter(CommonLetterIfc.NEXT), BusIfc.CURRENT);
            }
        }
    }

}
