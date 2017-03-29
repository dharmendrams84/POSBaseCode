/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/fill/EditItemStatusUpdateAisle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:32 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:52 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:21:12 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:44 PM  Robert Pearse   
 *
 *   Revision 1.4  2004/03/03 23:15:15  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.3  2004/02/12 16:51:23  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:49  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:03:36   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:12:30   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:41:18   msg
 * Initial revision.
 * 
 *    Rev 1.4   Jan 22 2002 22:12:20   dfh
 * updates for model, getlineitems, clone lineitems
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * 
 *    Rev 1.3   Jan 18 2002 10:50:32   dfh
 * use edit item status screen model
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * 
 *    Rev 1.2   Jan 10 2002 21:47:40   dfh
 * uses fill item status screen
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * 
 *    Rev 1.1   14 Dec 2001 07:58:38   mpm
 * Changed getLineItems() to getOrderLineItems().
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.0   Sep 24 2001 13:01:08   MPM
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:10:34   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.fill;

// foundation imports
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.services.order.common.OrderCargoIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.LineItemsModel;

//------------------------------------------------------------------------------
/**
    Sets the order status based upon the order line items status.
    <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------

public class EditItemStatusUpdateAisle extends PosLaneActionAdapter
{
    /**
       class name constant
    **/
    public static final String LANENAME = "EditItemStatusUpdateAisle";

    /**
       revision number for this class
    **/
    public static final String revisionNumber = "$KW=@(#); $Ver=pos_4.5.0:14; $EKW:";

    //----------------------------------------------------------------------
    /**
       Sets the order status based upon the changed item statuses. Updates the
       order status based upon the combination of order line item statuses.
       <P>
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------

    public void traverse(BusIfc bus)
    {
        // retrieve order cargo and order line items
        OrderCargoIfc      cargo   = (OrderCargoIfc)bus.getCargo();
        OrderIfc order = cargo.getOrder();
        AbstractTransactionLineItemIfc[] orderItems = order.getLineItems();

        //retrieve the updated order from the model - order line items
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        LineItemsModel model = (LineItemsModel)ui.getModel(POSUIManagerIfc.EDIT_ITEM_STATUS);
        AbstractTransactionLineItemIfc[] modelItems = model.getLineItems();
        
        for (int i = 0; i < modelItems.length; i++)
        {
            int newStatus = ((SaleReturnLineItemIfc)modelItems[i]).getOrderItemStatus().getStatus().getStatus();
            int oldStatus = ((SaleReturnLineItemIfc)orderItems[i]).getOrderItemStatus().getStatus().getStatus();
            
            // Set status for those items that have changed
            if (newStatus != oldStatus)
            {
                ((SaleReturnLineItemIfc)orderItems[i]).getOrderItemStatus().getStatus().changeStatus(newStatus);
            }
        }
        
        // update order line items with ui line items
        order.setLineItems(orderItems);
        order.setOrderStatus(); // set the order status based upon order line items
        
        bus.mail(new Letter(CommonLetterIfc.SUCCESS), BusIfc.CURRENT);
    }   // traverse
}
