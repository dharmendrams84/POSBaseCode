/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/postvoid/ReversalReturnShuttle.java /rgbustores_13.4x_generic_branch/1 2011/07/14 16:56:15 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    asinton   07/14/11 - Initial Version
 * 
 * 
 * 
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.postvoid;

import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.ado.context.ContextFactory;
import oracle.retail.stores.pos.ado.context.TourADOContext;


/**
 * Reversals are fire-and-forget.
 * 
 * At this time there is no meaningful information to be returned by the reversal shuttle.
 */
public class ReversalReturnShuttle implements ShuttleIfc
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7479300564024803927L;

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#load(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    public void load(BusIfc bus)
    {
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#unload(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    public void unload(BusIfc bus)
    {
        // create TourADOContext and set it on the context factory
        VoidCargo tenderCargo = (VoidCargo)bus.getCargo();
        TourADOContext context = new TourADOContext(bus);
        context.setApplicationID(tenderCargo.getAppID());
        ContextFactory.getInstance().setContext(context);
    }

}
