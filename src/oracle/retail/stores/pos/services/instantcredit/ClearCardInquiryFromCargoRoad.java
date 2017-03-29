/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/instantcredit/ClearCardInquiryFromCargoRoad.java /rgbustores_13.4x_generic_branch/1 2011/09/21 15:44:41 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   09/21/11 - clear inquiry details from cargo is user cancels or
 *                         escapes
 *    cgreene   09/21/11 - initial version
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.instantcredit;

import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;

/**
 * Clear the cargo from search criteria since the user canceled or escaped
 * out of the functionality.
 *
 * @author cgreene
 * @since 13.4
 */
@SuppressWarnings("serial")
public class ClearCardInquiryFromCargoRoad extends PosLaneActionAdapter
{

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.application.LaneActionAdapter#traverse(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void traverse(BusIfc bus)
    {
        InstantCreditCargo cargo = (InstantCreditCargo)bus.getCargo();
        cargo.setSsn(null);
        cargo.setHomePhone(null);
        cargo.setZipCode(null);
    }

}
