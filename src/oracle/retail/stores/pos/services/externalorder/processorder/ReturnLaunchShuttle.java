/* ===========================================================================
* Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/externalorder/processorder/ReturnLaunchShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:59 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    jswan     06/30/10 - Merge from refresh.
 *    jswan     06/30/10 - Checkin for first promotion of External Order
 *                         integration.
 *    acadar    06/21/10 - changes for return flow
 *    cgreene   05/26/10 - convert to oracle packaging
 *    acadar    05/14/10 - initial version for external order processing
 *    acadar    05/14/10 - initial version
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.externalorder.processorder;

import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.returns.returnoptions.ReturnOptionsCargo;

/**
 * This shuttle transfers data from the process order service to
 * the return options service
 * @author acadar
 *
 */
public class ReturnLaunchShuttle extends oracle.retail.stores.pos.services.sale.ReturnLaunchShuttle
{
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -5136627269400306888L;



    /**
     * Process order cargo
     */
    ProcessOrderCargo processOrderCargo = null;

   /**
    * Load the data
    */
    public void load(BusIfc bus)
    {
        // Call load on FinancialCargoShuttle
        super.load(bus);

        // retrieve cargo from the parent(Sales Cargo)
        processOrderCargo = (ProcessOrderCargo)bus.getCargo();
    }

   /**
    * Unload the data from the bus
    */
    public void unload(BusIfc bus)
    {
        // Call unload on oracle.retail.stores.pos.services.sale.ReturnLaunchShuttle
        super.unload(bus);

        // retrieve cargo from the child(ReturnOptions Cargo)
        ReturnOptionsCargo cargo = (ReturnOptionsCargo) bus.getCargo();
        // set the array of external order items in the return options cargo
        cargo.setExternalOrderItems(processOrderCargo.getExternalOrderReturnItems());
        cargo.setExternalOrder(true);
    }

}
