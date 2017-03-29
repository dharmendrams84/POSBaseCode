/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifyitem/serialnumber/SerialisedDeliveryReturnShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:25 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    abondala  01/03/10 - update header date
 *    nkgautam  12/15/09 - new return shuttle for serialised item delivery
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifyitem.serialnumber;

import java.util.Iterator;

import oracle.retail.stores.pos.services.common.FinancialCargoShuttle;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.KitComponentLineItemIfc;
import oracle.retail.stores.domain.lineitem.KitHeaderLineItemIfc;
import oracle.retail.stores.domain.lineitem.OrderLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.services.modifyitem.serialnumber.SerializedItemCargo;
import oracle.retail.stores.pos.services.pickup.PickupDeliveryOrderCargo;

public class SerialisedDeliveryReturnShuttle extends FinancialCargoShuttle implements ShuttleIfc
{

    /**
     * pickup delivery cargo
     */
    protected PickupDeliveryOrderCargo pickupDeliveryOrderCargo = null;

    /**
     * Loads the item cargo.
     * @param bus Service Bus to copy cargo from.
     */
    public void load(BusIfc bus)
    {
        pickupDeliveryOrderCargo = (PickupDeliveryOrderCargo) bus.getCargo();
    }


    /**
      Copies the pickup delivery order info to the cargo for the Modify Item service.
      @param  bus     Service Bus to copy cargo to.
     **/
    public void unload(BusIfc bus)
    {
        SerializedItemCargo cargo = (SerializedItemCargo) bus.getCargo();
        if (pickupDeliveryOrderCargo.getOrderTransaction() != null)
        {
            cargo.setTransaction(pickupDeliveryOrderCargo.getTransaction());
            setStatusOnSelectedItems(pickupDeliveryOrderCargo.getOrderTransaction().getItemContainerProxy().getLineItems(),
                    pickupDeliveryOrderCargo.getLineItems());
        }
        cargo.setPickupOrDeliveryExecuted(true);
    }

    /**
     *  Multi-quantity line items can be expanded to individual line items
     *  when a customer is added to the transaction.  As result the pickup
     *  function must manage the update of line items itself.
     *  This method iterates through the line items from ItemContainerProxy
     *  looking for line items that have been selected for Item Modification
     *  and then calls a method update these items with the pickup info.
     * @param lineItems
     * @param models
     */
    protected void setStatusOnSelectedItems(AbstractTransactionLineItemIfc[] lineItems, SaleReturnLineItemIfc[] models)
    {
        for(int i = 0; i < lineItems.length; i++)
        {
            SaleReturnLineItemIfc lineItem = (SaleReturnLineItemIfc)lineItems[i];
            if (lineItem.isSelectedForItemModification())
            {
                // The "models" are the list of items to changes from sale item screen.
                // Each item in this list gets exactly the same pickup data, so all
                // we need is the first one.
                setStatusOnSelectedItem(lineItem, models[0]);
            }
        }
    }

    /**
     * Update the line item with the pickup data from the model line item.
     * @param lineItem
     * @param model
     */
    protected void setStatusOnSelectedItem(SaleReturnLineItemIfc lineItem, SaleReturnLineItemIfc model)
    {
        KitHeaderLineItemIfc parentKitItem = null;
        KitComponentLineItemIfc childKit   = null;
        lineItem.getOrderItemStatus().setPickupDate(model.getOrderItemStatus().getPickupDate());
        lineItem.getOrderItemStatus().setItemDispositionCode(OrderLineItemIfc.ORDER_ITEM_DISPOSITION_PICKUP);
        if (lineItem.isKitHeader())
        {
            parentKitItem = (KitHeaderLineItemIfc)lineItem;
            Iterator<KitComponentLineItemIfc> childKitItemIter = parentKitItem.getKitComponentLineItems();
            while (childKitItemIter.hasNext())
            {
                childKit = childKitItemIter.next();
                childKit.getOrderItemStatus().setPickupDate(model.getOrderItemStatus().getPickupDate());
                childKit.getOrderItemStatus().setItemDispositionCode(
                        OrderLineItemIfc.ORDER_ITEM_DISPOSITION_PICKUP);

            }

        }
    }


}
