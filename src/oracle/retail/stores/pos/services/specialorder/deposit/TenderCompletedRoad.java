/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/specialorder/deposit/TenderCompletedRoad.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:02 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    acadar    06/10/10 - use default locale for currency display
 *    acadar    06/09/10 - XbranchMerge acadar_tech30 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    acadar    04/08/10 - merge to tip
 *    acadar    04/05/10 - use default locale for currency and date/time
 *                         display
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *    npoola    03/01/09 - added the condition check for the ON_HAND for
 *                         complete status
 *    npoola    02/26/09 - moved the logic from JDBCSaveTransaction to the
 *                         TenderCompleteRoad
 *    aphulamb  11/24/08 - Checking files after code review by amrish
 *    aphulamb  11/13/08 - Check in all the files for Pickup Delivery Order
 *                         functionality
 *
 * ===========================================================================
 * $Log:
 *   5    360Commerce 1.4         8/20/2007 5:26:44 PM   Charles D. Baker CR
 *        28436 - Updated alignment of balance due for special orders.
 *   4    360Commerce 1.3         4/25/2007 8:51:33 AM   Anda D. Cadar   I18N
 *        merge
 *   3    360Commerce 1.2         3/31/2005 4:30:23 PM   Robert Pearse
 *   2    360Commerce 1.1         3/10/2005 10:25:55 AM  Robert Pearse
 *   1    360Commerce 1.0         2/11/2005 12:14:49 PM  Robert Pearse
 *
 *  Revision 1.3  2004/02/12 16:52:03  mcs
 *  Forcing head revision
 *
 *  Revision 1.2  2004/02/11 21:52:29  rhafernik
 *  @scr 0 Log4J conversion and code cleanup
 *
 *  Revision 1.1.1.1  2004/02/11 01:04:20  cschellenger
 *  updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:07:22   CSchellenger
 * Initial revision.
 *
 *    Rev 1.0   Apr 29 2002 15:01:54   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:48:20   msg
 * Initial revision.
 *
 *    Rev 1.5   25 Jan 2002 10:34:50   jbp
 * increment line item reference.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.4   Jan 10 2002 17:33:30   dfh
 * sets line items status to new, pro-rates the deposit across
 * the line items
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.3   Dec 21 2001 16:57:08   dfh
 * set created order status to new
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.2   Dec 21 2001 12:39:04   dfh
 * added status change to active
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.1   Dec 04 2001 16:08:58   dfh
 * No change.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.0   Dec 04 2001 15:11:20   dfh
 * Initial revision.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.specialorder.deposit;

// foundation imports
import java.util.Locale;

import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.utility.JournalConstantsIfc;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.OrderLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderConstantsIfc;
import oracle.retail.stores.domain.order.OrderStatusIfc;
import oracle.retail.stores.domain.transaction.OrderTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionTotalsIfc;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.LocaleMap;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.services.specialorder.SpecialOrderCargo;

// ------------------------------------------------------------------------------
/**
 * This class updates the balance due for the special order based upon the
 * deposit amount and sets the cargo.
 * <P>
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
// ------------------------------------------------------------------------------
public class TenderCompletedRoad extends PosLaneActionAdapter
{ // begin class TenderCompletedRoad
    /**
     * lane name constant
     */
    public static final String LANENAME = "TenderCompletedRoad";

    /**
     * revision number supplied by source-code-control system
     */
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    // --------------------------------------------------------------------------
    /**
     * Performs the traversal functionality for the aisle. In this case, The
     * special order balance due is calculated based upon the deposit. Sets the
     * order and item status to new and prorates the deposit amount across the
     * line items. Journals the balance due.
     * <P>
     *
     * @param bus the bus traversing this lane
     */
    // --------------------------------------------------------------------------
    public void traverse(BusIfc bus)
    { // begin traverse()
        SpecialOrderCargo specialOrderCargo = (SpecialOrderCargo)bus.getCargo();

        // Gets special order transaction and its components to update
        OrderTransactionIfc orderTransaction = specialOrderCargo.getOrderTransaction();
        TransactionTotalsIfc totals = orderTransaction.getTransactionTotals();
        CurrencyIfc balanceDue = orderTransaction.getOrderStatus().getBalanceDue();

        orderTransaction.prorateDeposit();

        OrderStatusIfc orderStatus = orderTransaction.getOrderStatus();
        orderStatus.getStatus().changeStatus(OrderConstantsIfc.ORDER_STATUS_NEW);
        orderStatus.getStatus().setPreviousStatus(OrderConstantsIfc.ORDER_STATUS_NEW);
        orderStatus.getStatus().setPreviousStatusChange(orderStatus.getStatus().getLastStatusChange());

        // set lineitems status to New
        AbstractTransactionLineItemIfc[] lineitems = orderTransaction.getLineItems();
        // flag used to see if the all the items are pickedup
        boolean orderCompleted = true;

        for (int i = 0; i < lineitems.length; i++)
        {
            int itemDispositionCode = ((SaleReturnLineItemIfc)lineitems[i]).getOrderItemStatus().getItemDispositionCode();

            if (orderTransaction.getOrderType() == OrderConstantsIfc.ORDER_TYPE_ON_HAND
                    && itemDispositionCode == OrderLineItemIfc.ORDER_ITEM_DISPOSITION_SALE)
            {

                ((SaleReturnLineItemIfc)lineitems[i]).getOrderItemStatus().getStatus().changeStatus(
                        OrderLineItemIfc.ORDER_ITEM_STATUS_PICKED_UP);
                ((SaleReturnLineItemIfc)lineitems[i]).getOrderItemStatus().setQuantityPicked(
                        ((SaleReturnLineItemIfc)lineitems[i]).getItemQuantityDecimal());
                ((SaleReturnLineItemIfc)lineitems[i]).setOrderLineReference(i + 1);
            }
            else
            {
                ((SaleReturnLineItemIfc)lineitems[i]).getOrderItemStatus().getStatus().changeStatus(
                        OrderLineItemIfc.ORDER_ITEM_STATUS_NEW);
                ((SaleReturnLineItemIfc)lineitems[i]).setOrderLineReference(i + 1);
            }

            // set the flag to false if there are order items to be picked up or delivered
            if ((orderTransaction.getOrderType() == OrderConstantsIfc.ORDER_TYPE_ON_HAND )
                    && (itemDispositionCode == OrderLineItemIfc.ORDER_ITEM_DISPOSITION_DELIVERY
                            || itemDispositionCode == OrderLineItemIfc.ORDER_ITEM_DISPOSITION_PICKUP))
            {
                orderCompleted =false;
            }

        }

        // set the Order Status to complete if all the items are picked up
        if ((orderTransaction.getOrderType() == OrderConstantsIfc.ORDER_TYPE_ON_HAND ) && orderCompleted)
        {
            orderTransaction.getOrderStatus().getStatus().setStatus(OrderConstantsIfc.ORDER_STATUS_COMPLETED);
        }


        StringBuffer sb = new StringBuffer();
        int numSpaces = 27;
        String balanceDueString = balanceDue.toGroupFormattedString();

        Object dataObject[] = { balanceDueString };

        String depositAmountPaid = I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                JournalConstantsIfc.SPECIAL_ORDER_BALANCE_DUE, dataObject);

        sb.append(Util.EOL).append(depositAmountPaid).append(Util.EOL);

        JournalManagerIfc jmi = (JournalManagerIfc)Gateway.getDispatcher().getManager(JournalManagerIfc.TYPE);
        jmi.journal(orderTransaction.getCashier().getEmployeeID(), orderTransaction.getTransactionID(), sb.toString());
    } // end traverse()

    /**
     * Gets the locale used for Journaling
     *
     * @return
     */
    public static Locale getJournalLocale()
    {
        // attempt to get instance
        return LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL);
    }

} // end class TenderCompletedRoad
