/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/pickup/CheckAndUpdateOrderAisle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:32 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    nkgautam  12/28/09 - Removed checks for Pickup and Delivery Transaction
 *                         to prompt the serial numbers for serialized item
 *    mahising  03/13/09 - Fixed display dialog issue for PDO if transaction
 *                         canceled, completed or partial
 *    npoola    02/11/09 - fix kit item issue
 *    cgreene   02/04/09 - updated UIManager call to showScreen
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:23 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:20:04 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:09:53 PM  Robert Pearse
 *
 *   Revision 1.4  2004/03/03 23:15:07  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.3  2004/02/12 16:51:26  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:37  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:19  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:03:48   CSchellenger
 * Initial revision.
 *
 *    Rev 1.0   Apr 29 2002 15:11:50   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:41:36   msg
 * Initial revision.
 *
 *    Rev 1.7   29 Jan 2002 18:33:16   cir
 * Added check for serialized items
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.6   26 Jan 2002 18:52:14   cir
 * Removed setOrderStatus
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.5   Jan 23 2002 21:14:32   dfh
 * updates to better determine order status
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.4   17 Jan 2002 18:08:52   cir
 * Set the quantity picked
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.3   15 Jan 2002 19:24:36   cir
 * Changed oldStatus with getStatus()
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.2   15 Jan 2002 18:46:46   cir
 * Use item_status_cancel
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.1   14 Dec 2001 07:52:08   mpm
 * Handled change of getLineItems() to getOrderLineItems().
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.0   Sep 24 2001 13:01:16   MPM
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:10:42   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.pickup;

import java.util.Vector;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.OrderLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;
import oracle.retail.stores.pos.ui.beans.LineItemsModel;

/**
 * This aisle gets the order from the UI, makes sure that at least one item has
 * a status of "Pickup", and upates the status of the order based on the status
 * of all the itmes in the order.
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public class CheckAndUpdateOrderAisle extends PosLaneActionAdapter
{
    private static final long serialVersionUID = -8266306575068866661L;

    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * Constant for error message.
     */
    public static final String NO_ITEM_PICKED_UP = "NoItemPickedUp";

    /**
     * Constant for error message.
     */
    public static final String NO_PARTIAL_PICK_UP = "NoPartialPickup";

    /**
     * This gets all the item return data from the UI and continues on to the
     * next site. If no items are in Pick Up status, displays the NoItemPickedUp
     * screen. If partial pickups are not allowed and there are non-pickup
     * status items on the order, displays the NoPartialPickup screen.
     *
     * @param bus Service Bus
     */
    @Override
    public void traverse(BusIfc bus)
    {
        ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);

        // get the cargo; get order line items from the ui
        PickupOrderCargo cargo = (PickupOrderCargo)bus.getCargo();
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        LineItemsModel model = (LineItemsModel)ui.getModel(POSUIManagerIfc.EDIT_ITEM_STATUS);
        AbstractTransactionLineItemIfc[] uiOli = model.getLineItems();
        Letter letter = null;

        // Verify that there is at least one "Pickup" item in the order.
        boolean aPickupItemExists = false;

        // count total of number of pick ups,cancel,picked ups
        int pickupCnt = 0;

        for (int i = 0; i < uiOli.length; i++)
        {
            int status = ((SaleReturnLineItemIfc)uiOli[i]).getOrderItemStatus().getStatus().getStatus();

            if (status == OrderLineItemIfc.ORDER_ITEM_STATUS_PICK_UP
                    || status == OrderLineItemIfc.ORDER_ITEM_STATUS_CANCEL
                    || status == OrderLineItemIfc.ORDER_ITEM_STATUS_PICKED_UP)
            {
                aPickupItemExists = true;
                pickupCnt++;
            }
        }

        // If there is a pickup item, check if partial pickups allowed
        if (aPickupItemExists)
        {
            boolean pickupPartial = true; // default

            try
            {
                pickupPartial = pm.getStringValue("OrderPartialPickup").equalsIgnoreCase("Y");
            }
            catch (ParameterException pe)
            {
                logger.warn("Error retrieving OrderPartialPickup parameter using default parameter value of Y.\n ["
                        + pe.getMessage() + "]");
            }

            if (!pickupPartial && (pickupCnt != uiOli.length))
            {
                // display dialog screen no partial pick up
                displayDialog(NO_PARTIAL_PICK_UP, ui);
            }
            else
            {
                // Loop through the ui line items to update status
                OrderIfc order = cargo.getOrder();
                AbstractTransactionLineItemIfc[] cOli = order.getLineItems();

                // serialized items vector
                Vector<SaleReturnLineItemIfc> serializedItemsVector = new Vector<SaleReturnLineItemIfc>();

                for (int i = 0; i < uiOli.length; i++)
                {
                    int newStatus = ((SaleReturnLineItemIfc)uiOli[i]).getOrderItemStatus().getStatus().getStatus();
                    int oldStatus = ((SaleReturnLineItemIfc)cOli[i]).getOrderItemStatus().getStatus().getStatus();

                    // Set status for those items that have changed
                    if (newStatus != oldStatus)
                    {
                        ((SaleReturnLineItemIfc)cOli[i]).getOrderItemStatus().getStatus().changeStatus(newStatus);
                    }

                    // Set the quantity picked
                    if (newStatus == OrderLineItemIfc.ORDER_ITEM_STATUS_PICK_UP)
                    {
                        ((SaleReturnLineItemIfc)cOli[i]).getOrderItemStatus().setQuantityPicked(
                                ((SaleReturnLineItemIfc)cOli[i]).getItemQuantityDecimal());
                        if (((SaleReturnLineItemIfc)cOli[i]).isSerializedItem())
                        {
                            serializedItemsVector.addElement(((SaleReturnLineItemIfc)cOli[i]));
                        }
                    }
                }

                // update order line items with ui line items
                order.setLineItems(cOli);

                int vectorSize = serializedItemsVector.size();
                if (vectorSize != 0)
                {
                    AbstractTransactionLineItemIfc[] serializedItems = new AbstractTransactionLineItemIfc[vectorSize];
                    serializedItemsVector.copyInto(serializedItems);
                    cargo.setSerializedItems(serializedItems);
                    cargo.setSerializedItemsCounter(0);
                    letter = new Letter("SerialNumber");
                }
                else
                {
                    letter = new Letter(CommonLetterIfc.CONTINUE);
                }
            }
        }
        else
        // no pick item exists, display the error dialog.
        {
            // display dialog screen no item picked up error
            displayDialog(NO_ITEM_PICKED_UP, ui);
        }

        if (letter != null)
        {
            // mail the letter, if any
            bus.mail(letter, BusIfc.CURRENT);
        }

    } // traverse

    /**
     * Displays the Dialog screen passed in as the argument.
     *
     * @param screen the screen name
     * @param ui the POS UI manager
     */
    protected void displayDialog(String screen, POSUIManagerIfc ui)
    {
        // Using "generic dialog bean".
        DialogBeanModel dModel = new DialogBeanModel();
        dModel.setResourceID(screen);
        dModel.setType(DialogScreensIfc.ERROR);

        // set and display the model
        ui.setModel(POSUIManagerIfc.DIALOG_TEMPLATE, dModel);
        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE);
    }
}