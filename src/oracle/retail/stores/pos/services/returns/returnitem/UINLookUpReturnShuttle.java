/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    rsnayak   09/28/11 - copied plu item from uin cargo to return item cargo
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    mchellap  12/15/09 - Serialisation changes
 *    mchellap  12/15/09 - Return shuttle for UINLookup service
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returnitem;

import java.util.HashMap;

import oracle.retail.stores.pos.services.common.UINLookUpCargo;

import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.domain.transaction.SearchCriteriaIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;

public class UINLookUpReturnShuttle implements ShuttleIfc
{

    /**
     * UIN Lookup Cargo class
     */
    protected UINLookUpCargo uinCargo;

    /**
     * Loads the UIN LookUp cargo.
     *
     * @param bus
     */
    public void load(BusIfc bus)
    {
        uinCargo = (UINLookUpCargo) bus.getCargo();
    }

    /**
     * Transfers the UIN Lookup cargo to the Return Item cargo.
     *
     * @param bus
     */
    public void unload(BusIfc bus)
    {
        ReturnItemCargo returnItemCargo = (ReturnItemCargo) bus.getCargo();

        SearchCriteriaIfc criteria = uinCargo.getInquiry();
        String serialInput = criteria.getItemID();
        returnItemCargo.setItemSerial(serialInput);
        if(uinCargo.getPluItem() != null)
        {
            returnItemCargo.setPLUItem(uinCargo.getPluItem());
        }
        // Clear the PLU Item ID
        returnItemCargo.setPLUItemID(null);

    }
}
