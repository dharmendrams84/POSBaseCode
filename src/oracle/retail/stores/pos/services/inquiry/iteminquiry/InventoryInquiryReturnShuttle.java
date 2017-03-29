/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/inquiry/iteminquiry/InventoryInquiryReturnShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:44 mszekely Exp $
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
 *    2    360Commerce 1.1         2/26/2008 7:33:26 AM   Naveen Ganesh   Item
 *         getting added to the transaction, has been avoided to get added.
 *    1    360Commerce 1.0         11/22/2007 10:57:24 PM Naveen Ganesh   
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.inquiry.iteminquiry;

import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.services.inventoryinquiry.InventoryInquiryCargo;


//------------------------------------------------------------------------------
/**
     
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------

public class InventoryInquiryReturnShuttle implements ShuttleIfc
{
    protected InventoryInquiryCargo inventoryInquiryCargo = null;
    
    //--------------------------------------------------------------------------
    /**
             

            @param bus the bus being loaded
    **/
    //--------------------------------------------------------------------------

    public void load(BusIfc bus)
    {
        inventoryInquiryCargo = (InventoryInquiryCargo) bus.getCargo();
    }

    //--------------------------------------------------------------------------
    /**
             

            @param bus the bus being unloaded
    **/
    //--------------------------------------------------------------------------

    public void unload(BusIfc bus)
    {
        if(bus.getCargo() instanceof ItemInquiryCargo)
        {
            ItemInquiryCargo cargo = (ItemInquiryCargo) bus.getCargo();
            if(inventoryInquiryCargo.getInquiry() == null)
            	cargo.setInquiry(null);
        }
    }
}
