/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/inquiry/iteminquiry/ItemDetailDisplayedRoad.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:44 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:28:30 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:22:24 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:11:36 PM  Robert Pearse   
 *
 *   Revision 1.5  2004/03/18 22:47:42  aschenk
 *   @scr 4079 and 4080 - Items were cleared after a help or cancelled cancel for an item inquiry.
 *
 *   Revision 1.4  2004/02/27 17:07:09  lzhao
 *   @scr 3841 Inquiry Options Enhancement
 *   Item will not be added unless Add button clicked.
 *
 *   Revision 1.3  2004/02/12 16:50:30  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:11  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:16  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:00:14   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Jun 12 2003 11:21:06   RSachdeva
 * setModifiedFlag true
 * Resolution for POS SCR-2658: Inquiry Options on item inventory is automatically adding item to sale
 * 
 *    Rev 1.0   Apr 29 2002 15:22:24   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:33:52   msg
 * Initial revision.
 * 
 *    Rev 1.1   28 Jan 2002 22:44:06   baa
 * ui fixes
 * Resolution for POS SCR-230: Cross Store Inventory
 * Resolution for POS SCR-824: Application crashes on Customer Add screen after selecting Enter
 *
 *    Rev 1.0   Sep 21 2001 11:29:44   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:08:10   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.inquiry.iteminquiry;

// foundation imports
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.LaneActionAdapter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.ItemListBeanModel;

//--------------------------------------------------------------------------
/**
    This road is traveled when the user enters the item
    number. It stores the item number in the cargo.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class ItemDetailDisplayedRoad extends LaneActionAdapter
{
    /**
        revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
        Stores the item number in the cargo.
        @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {
        // Initialize bean model values
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        ItemListBeanModel model = (ItemListBeanModel) ui.getModel();

        PLUItemIfc item = (PLUItemIfc)model.getSelectedItem();

        // update cargo
        ItemInquiryCargo cargo = (ItemInquiryCargo)bus.getCargo();
        cargo.setPLUItem(item);
    }
}
