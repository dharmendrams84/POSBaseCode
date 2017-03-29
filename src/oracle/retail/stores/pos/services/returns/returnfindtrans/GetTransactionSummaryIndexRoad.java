/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returns/returnfindtrans/GetTransactionSummaryIndexRoad.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:56 mszekely Exp $
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
 *  3    360Commerce 1.2         3/31/2005 4:28:15 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:21:51 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:11:12 PM  Robert Pearse   
 * $
 * Revision 1.4  2004/02/23 14:58:52  baa
 * @scr 0 cleanup javadocs
 *
 * Revision 1.3  2004/02/12 16:51:48  mcs
 * Forcing head revision
 *
 * Revision 1.2  2004/02/11 21:52:28  rhafernik
 * @scr 0 Log4J conversion and code cleanup
 *
 * Revision 1.1.1.1  2004/02/11 01:04:20  cschellenger
 * updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Dec 29 2003 15:43:20   baa
 * Initial revision.
 *------------------------------------------------------------------------------
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returnfindtrans;

import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.ListBeanModel;


/**
 * Handles the selection of a sale transaction from a list of possible transactions presented to 
 * a user.
 * <p></p>
 */
public class GetTransactionSummaryIndexRoad extends PosLaneActionAdapter {

    /**
     * Raw revision number string for the site.
     * <p></p>
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * Provides the cargo with the index of the selected transaction from the UI.
     * <p></p>
     * @param bus  provides the cargo & managers to handle the mission of the road.
     */
    public void traverse(BusIfc bus) {
        // Get the index of the selected item
         POSUIManagerIfc ui;
         ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
         ListBeanModel model = (ListBeanModel)ui.getModel(POSUIManagerIfc.RETURN_LINKED_TRANS);

         // Update the cargo
        ReturnFindTransCargo cargo = (ReturnFindTransCargo) bus.getCargo();
        cargo.setSelectedSummaryIndex(model.getSelectedRow());
    }


}
