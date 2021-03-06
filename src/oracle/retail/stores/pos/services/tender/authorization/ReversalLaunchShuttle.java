/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/authorization/ReversalLaunchShuttle.java /rgbustores_13.4x_generic_branch/1 2011/07/25 10:22:07 blarsen Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   07/22/11 - Initial version.
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.authorization;

import java.util.ArrayList;
import java.util.List;

import oracle.retail.stores.domain.manager.payment.ReversalRequestIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.ado.context.ContextFactory;
import oracle.retail.stores.pos.ado.context.TourADOContext;
import oracle.retail.stores.pos.services.tender.reversal.ReversalCargo;


/**
 * Shuttle the information required by the Reversal Service
 */
public class ReversalLaunchShuttle implements ShuttleIfc
{
    private static final long serialVersionUID = -155925180621171325L;

    protected AuthorizationCargo callingCargo;

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#load(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    public void load(BusIfc bus)
    {
        callingCargo = (AuthorizationCargo)bus.getCargo();
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#unload(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    public void unload(BusIfc bus)
    {
        ReversalCargo childCargo = (ReversalCargo)bus.getCargo();
        List<ReversalRequestIfc> requestList = new ArrayList<ReversalRequestIfc>();

        ReversalRequestIfc reversalRequest = ReversalCargo.buildRequest(
                        callingCargo.getCurrentRequest(),
                        callingCargo.getCurrentResponse());

        requestList.add(reversalRequest);
        childCargo.setRequestList(requestList);

        // Reset ADO context for calling service
        TourADOContext context = new TourADOContext(bus);
        context.setApplicationID(childCargo.getAppID());
        ContextFactory.getInstance().setContext(context);
    }

}
