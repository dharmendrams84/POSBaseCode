/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/common/StatusSearchEnteredRoad.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:33 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:30:10 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:29 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:24 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:51:22  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:45  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:03:34   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:13:16   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:41:14   msg
 * Initial revision.
 * 
 *    Rev 1.0   27 Dec 2001 16:22:14   cir
 * Initial revision.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * 
 *    Rev 1.0   Sep 24 2001 13:01:10   MPM
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:10:30   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.common;

//foundation imports
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.StatusSearchBeanModel;

//------------------------------------------------------------------------------
/**
    Sets the order search method to Search by order status and sets the clearUIFields
    flag to clear the date range and status from the status search screen.
    <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------

public class StatusSearchEnteredRoad extends PosLaneActionAdapter
{
    /**
       revision number for this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";


    //------------------------------------------------------------------------------
    /**
       Sets the order search method to Search by order status and sets the clearUIFields
       flag to clear the date range and status from the status search screen.
       <P>
       @param bus the bus arriving at this site
    **/
    //------------------------------------------------------------------------------

    public void traverse(BusIfc bus)
    {

        OrderSearchCargoIfc cargo = (OrderSearchCargoIfc) bus.getCargo();

        cargo.setSearchMethod(OrderSearchCargoIfc.SEARCH_BY_ORDER_STATUS);

        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);

        StatusSearchBeanModel model = new StatusSearchBeanModel();
        //model = (StatusSearchBeanModel)ui.getModel(POSUIManagerIfc.STATUS_SEARCH);
        model.setclearUIFields(true); // clear dates/status in ui - status search
        ui.setModel(POSUIManagerIfc.STATUS_SEARCH, model);
    }
}
