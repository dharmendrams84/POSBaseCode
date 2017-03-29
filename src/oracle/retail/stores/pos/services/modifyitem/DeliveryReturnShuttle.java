/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifyitem/DeliveryReturnShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:25 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    nkgautam  03/02/10 - setting item serial to null for delivery transaction
 *                         when item scanned through imei
 *    abondala  01/03/10 - update header date
 *    asinton   05/12/09 - Removed calls to ItemCargo.setRetailTransactionIfc
 *                         and put call to ItemCargo.setTransaction inside of
 *                         null check for
 *                         pickupDeliveryOrderCargo.getOrderTransaction().
 *    jswan     04/14/09 - Added comment per code review.
 *    jswan     04/14/09 - Modified to fix conflict between multi quantity
 *                         items and items that have been marked for Pickup or
 *                         Delivery.
 *    aphulamb  11/22/08 - Checking files after code review by Naga
 *    aphulamb  11/13/08 - Check in all the files for Pickup Delivery Order
 *                         functionality
 *    aphulamb  11/13/08 - Delivery return shuttle
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifyitem;

import java.util.Iterator;

import oracle.retail.stores.pos.services.common.FinancialCargoShuttle;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.KitComponentLineItemIfc;
import oracle.retail.stores.domain.lineitem.KitHeaderLineItemIfc;
import oracle.retail.stores.domain.lineitem.OrderLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.services.pickup.PickupDeliveryOrderCargo;

public class DeliveryReturnShuttle extends FinancialCargoShuttle
{

    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -5891734460808892690L;

    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     Child service's cargo
     **/
    protected PickupDeliveryOrderCargo pickupDeliveryOrderCargo = null;

    //----------------------------------------------------------------------
    /**
     Loads the PickupDeliveryOrderCarog.
     <P>
     @param  bus     Service Bus to copy cargo from.
     **/
    //----------------------------------------------------------------------
    public void load(BusIfc bus)
    {
        pickupDeliveryOrderCargo = (PickupDeliveryOrderCargo)bus.getCargo();
    }

    //----------------------------------------------------------------------
    /**
     Copies the new delivery detail to the cargo for the Modify Item service.
     <P>
     @param  bus     Service Bus to copy cargo to.
     **/
    //----------------------------------------------------------------------
    public void unload(BusIfc bus)
    {
        ItemCargo cargo = (ItemCargo)bus.getCargo();
        if (pickupDeliveryOrderCargo.getOrderTransaction() != null)
        {
            cargo.setTransaction(pickupDeliveryOrderCargo.getOrderTransaction());
            setStatusOnSelectedItems(pickupDeliveryOrderCargo.getOrderTransaction().getItemContainerProxy().getLineItems(),
                    pickupDeliveryOrderCargo.getLineItems());
        }
        cargo.setPickupOrDeliveryExecuted(true);
    }

    /**
     *  Multi-quantity line items can be expanded to individual line items
     *  when a customer is added to the transaction.  As result the delivery
     *  function must manage the update of line items itself.
     *  This method iterates through the line items from ItemContainerProxy
     *  looking for line items that have been selected for Item Modification
     *  and then calls a method update these items with the delivery info.
     * @param lineItems
     * @param models
     */
    protected void setStatusOnSelectedItems(AbstractTransactionLineItemIfc[] lineItems, SaleReturnLineItemIfc[] models)
    {
        for(int i = 0; i < lineItems.length; i++)
        {
            SaleReturnLineItemIfc item = (SaleReturnLineItemIfc)lineItems[i];
            if (item.isSelectedForItemModification())
            {
                // The "models" are the list of items to changes from sale item screen.
                // Each item in this list gets exactly the same delivery data, so all
                // we need is the first one.
                setStatusOnSelectedItem(item, models[0]);
            }
        }
    }

    /**
     * Update the line item with the delivery data from the model line item.
     * @param lineItem
     * @param model
     */
    protected void setStatusOnSelectedItem(SaleReturnLineItemIfc lineItem, SaleReturnLineItemIfc model)
    {
        KitHeaderLineItemIfc parentKitItem = null;
        KitComponentLineItemIfc childKit   = null;
        lineItem.getOrderItemStatus().setDeliveryDate(model.getOrderItemStatus().getDeliveryDate());
        lineItem.getOrderItemStatus().setItemDispositionCode(OrderLineItemIfc.ORDER_ITEM_DISPOSITION_DELIVERY);
        lineItem.getOrderItemStatus().getDeliveryDetails().setDeliveryDetailID(
                model.getOrderItemStatus().getDeliveryDetails().getDeliveryDetailID());
        lineItem.getOrderItemStatus().setDeliveryDetails(model.getOrderItemStatus().getDeliveryDetails());

        if (lineItem.isKitHeader())
        {
            parentKitItem = (KitHeaderLineItemIfc)lineItem;
            Iterator<KitComponentLineItemIfc> childKitItemIter = parentKitItem.getKitComponentLineItems();
            while (childKitItemIter.hasNext())
            {
                childKit = childKitItemIter.next();
                childKit.getOrderItemStatus().setDeliveryDate(model.getOrderItemStatus().getDeliveryDate());
                childKit.getOrderItemStatus().setItemDispositionCode(
                        OrderLineItemIfc.ORDER_ITEM_DISPOSITION_DELIVERY);
                childKit.getOrderItemStatus().getDeliveryDetails().setDeliveryDetailID(
                        model.getOrderItemStatus().getDeliveryDetails().getDeliveryDetailID());
                lineItem.getOrderItemStatus().setDeliveryDetails(
                        model.getOrderItemStatus().getDeliveryDetails());
            }

        }
        if(lineItem.getOrderItemStatus().getStatus().getStatus() == OrderLineItemIfc.ORDER_ITEM_STATUS_UNDEFINED)
        {
            lineItem.setItemSerial(null);
        }
    }

    //----------------------------------------------------------------------
    /**
     Returns a string representation of this object.
     <P>
     @return String representation of object
     **/
    //----------------------------------------------------------------------
    public String toString()
    { // begin toString()
        // result string
        String strResult = new String("Class:  InquiryOptionsReturnShuttle (Revision " + getRevisionNumber() + ")"
                + hashCode());

        // pass back result
        return (strResult);
    } // end toString()

    //----------------------------------------------------------------------
    /**
     Returns the revision number of the class.
     <P>
     @return String representation of revision number
     **/
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    { // begin getRevisionNumber()
        // return string
        return (Util.parseRevisionNumber(revisionNumber));
    } // end getRevisionNumber()

    //----------------------------------------------------------------------
    /**
     Main to run a test..
     <P>
     @param  args    Command line parameters
     **/
    //----------------------------------------------------------------------
    public static void main(String args[])
    { // begin main()
        // instantiate class
        DeliveryReturnShuttle obj = new DeliveryReturnShuttle();

        // output toString()
        System.out.println(obj.toString());
    }

}
