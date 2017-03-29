/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/inquiry/iteminquiry/ItemSelectedRoad.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:45 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    acadar    11/16/09 - remove signal and properly set the modified flag in
 *                         the cargo
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:28:33 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:22:31 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:11:41 PM  Robert Pearse
 *
 *   Revision 1.6  2004/03/18 22:47:42  aschenk
 *   @scr 4079 and 4080 - Items were cleared after a help or cancelled cancel for an item inquiry.
 *
 *   Revision 1.5  2004/03/10 00:09:03  lzhao
 *   @scr 3840 InquiryOptions: Inventory Inquiry
 *
 *   Revision 1.4  2004/02/27 17:07:09  lzhao
 *   @scr 3841 Inquiry Options Enhancement
 *   Item will not be added unless Add button clicked.
 *
 *   Revision 1.3  2004/02/12 16:50:31  mcs
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
 *    Rev 1.0   Aug 29 2003 16:00:16   CSchellenger
 * Initial revision.
 *
 *    Rev 1.1   Jun 12 2003 11:19:24   RSachdeva
 * setModifiedFlag true
 * Resolution for POS SCR-2658: Inquiry Options on item inventory is automatically adding item to sale
 *
 *    Rev 1.0   Apr 29 2002 15:22:30   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:33:58   msg
 * Initial revision.
 *
 *    Rev 1.1   28 Jan 2002 22:44:12   baa
 * ui fixes
 * Resolution for POS SCR-230: Cross Store Inventory
 * Resolution for POS SCR-824: Application crashes on Customer Add screen after selecting Enter
 *
 *    Rev 1.0   Sep 21 2001 11:29:46   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:08:08   msg
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
    This road is traveled when the user selects an item
    It stores the item  in the cargo.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class ItemSelectedRoad extends LaneActionAdapter
{
    /**
        revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
        Stores the item info and dept list  in the cargo.
        @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {
        // Initialize bean model values
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        ItemListBeanModel model = (ItemListBeanModel) ui.getModel();

        // Store item selected from item list
        PLUItemIfc item = (PLUItemIfc)model.getSelectedItem();
        ItemInquiryCargo cargo = (ItemInquiryCargo)bus.getCargo();
        cargo.setPLUItem(item);
        cargo.setModifiedFlag(true);
    }
}
