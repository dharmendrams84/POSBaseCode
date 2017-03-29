/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/pickup/GetPickupDateEnteredSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:50 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    mahising  02/26/09 - Rework for PDO functionality
 *    aphulamb  11/25/08 - Checking files after code review by Amrish
 *    aphulamb  11/22/08 - Checking files after code review by Naga
 *    aphulamb  11/18/08 - Pickup Delivery Order
 *    aphulamb  11/13/08 - Check in all the files for Pickup Delivery Order
 *                         functionality
 *    aphulamb  11/13/08 - Get pickup date
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.pickup;
import java.util.Iterator;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.KitComponentLineItemIfc;
import oracle.retail.stores.domain.lineitem.KitHeaderLineItemIfc;
import oracle.retail.stores.domain.lineitem.OrderLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.utility.EYSDate;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;
import oracle.retail.stores.pos.ui.beans.GetPickupDateBeanModel;

public class GetPickupDateEnteredSite extends PosSiteActionAdapter
{
    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    protected EYSDate enteredDate = null;

    protected EYSDate currentSystemDate = null;

    // ----------------------------------------------------------------------
    /**
     * validation for pickup date and set the pickup date into OrderItemStatus
     * Object
     * <P>
     *
     * @param bus Service bus.
     */
    // ----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        PickupDeliveryOrderCargo pickupDeliveryOrderCargo = (PickupDeliveryOrderCargo)bus.getCargo();
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        SaleReturnLineItemIfc[] lineItems = pickupDeliveryOrderCargo.getLineItems();
        GetPickupDateBeanModel model = (GetPickupDateBeanModel)ui.getModel(POSUIManagerIfc.GET_PICKUP_DATE_SCREEN);

        // get the pickup date from model
        enteredDate = model.getSelectedPickupDate();
        currentSystemDate = new EYSDate();
        // set the pickup date into OrderItemStatus if entered pickup date is
        // valid
        KitHeaderLineItemIfc parentKitItem=null;
        KitComponentLineItemIfc childKit=null;
        if (isEnteredDateCorrect())
        {
            for (int i = 0; i < lineItems.length; i++)
            {
                lineItems[i].getOrderItemStatus().setPickupDate(enteredDate);
                lineItems[i].getOrderItemStatus()
                        .setItemDispositionCode(OrderLineItemIfc.ORDER_ITEM_DISPOSITION_PICKUP);
                lineItems[i].getOrderItemStatus().setPickupDate(enteredDate);
                if (lineItems[i].isKitHeader())
                {
                    parentKitItem = (KitHeaderLineItemIfc)lineItems[i];
                    Iterator<KitComponentLineItemIfc> childKitItemIter = parentKitItem.getKitComponentLineItems();
                    while (childKitItemIter.hasNext())
                    {
                        childKit = childKitItemIter.next();
                        childKit.getOrderItemStatus().setPickupDate(enteredDate);
                        childKit.getOrderItemStatus().setItemDispositionCode(
                                OrderLineItemIfc.ORDER_ITEM_DISPOSITION_PICKUP);

                    }

                }
            }
            bus.mail(CommonLetterIfc.CONTINUE);
        }
        else
        {
            dialogForIncorrectDate(bus);
        }
    }

    // ----------------------------------------------------------------------
    /**
     * validate pickup entered date
     *
     * @return result boolean
     */
    // ----------------------------------------------------------------------
    public boolean isEnteredDateCorrect()
    {
        boolean result = false;
        if (enteredDate != null)
        {
            if (enteredDate.after(currentSystemDate)
                    || (enteredDate.getDay() == currentSystemDate.getDay()
                            && enteredDate.getMonth() == currentSystemDate.getMonth() && enteredDate.getYear() == currentSystemDate
                            .getYear()))
            {
                result = true;
            }
            else
            {
                result = false;
            }
        }
        return result;
    }

    // ----------------------------------------------------------------------
    /**
     * Display dialogbox for incorrect entered date
     * <P>
     *
     * @param bus Service bus.
     */
    // ----------------------------------------------------------------------
    public void dialogForIncorrectDate(BusIfc bus)
    {
        // Using "generic dialog bean".
        DialogBeanModel model = new DialogBeanModel();
        model.setResourceID("IncorrectEnteredDate");
        model.setType(DialogScreensIfc.ACKNOWLEDGEMENT);
        // set and display the model
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, model);
    }
}
