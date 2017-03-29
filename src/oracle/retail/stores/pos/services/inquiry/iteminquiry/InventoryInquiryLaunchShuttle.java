/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/inquiry/iteminquiry/InventoryInquiryLaunchShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:45 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    cgreen 05/26/10 - convert to oracle packaging
 *    cgreen 04/26/10 - XbranchMerge cgreene_tech43 from
 *                      st_rgbustores_techissueseatel_generic_branch
 *    cgreen 04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abonda 01/03/10 - update header date
 *    djenni 03/18/09 - remove string version of currency from iteminfo and
 *                      iteminfoifc
 *    djenni 02/18/09 - pass seachbymanufacturer flag from item tour to
 *                      inventory tour
 *    ddbake 10/22/08 - Updating to use localized item descriptoins
 *    abonda 10/17/08 - I18Ning manufacturer name
 *    ddbake 10/16/08 - Updated with code review
 *    ddbake 10/15/08 - Implementing I18N Item Description for ItemInfo class.
 * 
     $Log:
      1    360Commerce 1.0         11/22/2007 10:57:24 PM Naveen Ganesh
     $
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package oracle.retail.stores.pos.services.inquiry.iteminquiry;

import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.stock.ItemInfoIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.services.inventoryinquiry.InventoryInquiryCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.ItemInfoBeanModel;
import oracle.retail.stores.pos.ui.beans.ItemListBeanModel;

//------------------------------------------------------------------------------
/**
     Shuttle for accessing Oracle Retail's Store Inventory Management (SIM) system 
     
**/
//------------------------------------------------------------------------------
public class InventoryInquiryLaunchShuttle implements ShuttleIfc
{

    /**
        revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    // The calling service's cargo
    protected ItemInquiryCargo itemInquiryCargo = null;

    protected String letter = null;

    protected PLUItemIfc item = null;
    protected boolean searchByManufacturer=false;

    //--------------------------------------------------------------------------
    /**


            @param bus the bus being loaded
    **/
    //--------------------------------------------------------------------------

    public void load(BusIfc bus)
    {
        itemInquiryCargo = (ItemInquiryCargo) bus.getCargo();
        letter = bus.getCurrentLetter().getName();

        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);

        if(ui.getModel() instanceof ItemListBeanModel)
        {
            ItemListBeanModel model = (ItemListBeanModel) ui.getModel();
            item = (PLUItemIfc)model.getSelectedItem();
        }
        if(ui.getModel() instanceof ItemInfoBeanModel)
        {
            item = itemInquiryCargo.getPLUItem();
            searchByManufacturer=itemInquiryCargo.isSearchItemByManufacturer();
        }
    }

    //--------------------------------------------------------------------------
    /**


            @param bus the bus being unloaded
    **/
    //--------------------------------------------------------------------------

    public void unload(BusIfc bus)
    {
        InventoryInquiryCargo inventoryInquiryCargo = (InventoryInquiryCargo) bus.getCargo();
        inventoryInquiryCargo.setLetterName(letter);
        inventoryInquiryCargo.setRegister(itemInquiryCargo.getRegister());
        inventoryInquiryCargo.setOperator(itemInquiryCargo.getOperator());
        inventoryInquiryCargo.setSearchItemByManufacturer(searchByManufacturer);

        ItemInfoIfc itemInfo = DomainGateway.getFactory().getItemInfoInstance();
        itemInfo.setItemID(item.getItemID());
        itemInfo.setLocalizedItemDescriptions(item.getLocalizedDescriptions());
        itemInfo.setLocalizedManufacturerName(item.getLocalizedManufacturer());
        itemInfo.setCurrentPrice(item.getSellingPrice());
        inventoryInquiryCargo.setItemInfo(itemInfo);
    }
}
