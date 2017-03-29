/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/lookup/StatusSearchSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:34 mszekely Exp $
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
 *   Revision 1.4  2004/03/03 23:15:07  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.3  2004/02/12 16:51:24  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:48  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:19  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:03:44   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Mar 14 2003 09:25:38   RSachdeva
 * Using StatusSearchBeanModel getStatusIndex to 
 * set Status in OrderSearchCargo (from STATUS_SEARCH Screen)
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.0   Apr 29 2002 15:12:26   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:41:30   msg
 * Initial revision.
 * 
 *    Rev 1.1   Jan 28 2002 10:12:44   dfh
 * use new orderconstantsifc
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *   *    Rev 1.0   Sep 24 2001 13:01:14   MPM  * Initial revision.  * 
 *    Rev 1.1   Sep 17 2001 13:10:34   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.lookup;

//foundation imports
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.order.common.OrderSearchCargoIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.StatusSearchBeanModel;


//------------------------------------------------------------------------------
/**
    Displays the status search screen to set the begin date, end date, and
    status to search for orders.
    <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------

public class StatusSearchSite extends PosSiteActionAdapter
{

    /**
       class name constant
    **/
    public static final String SITENAME = "StatusSearchSite";

    /**
       revision number for this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //--------------------------------------------------------------------------
    /**
       Displays the Status Search screen if the search method is by order
       status, otherwise mails a SUCCESS letter to continue.
       <P>
       @param bus the bus arriving at this site
    **/

    //--------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {

        OrderSearchCargoIfc cargo = (OrderSearchCargoIfc) bus.getCargo();
        StatusSearchBeanModel ssModel = new StatusSearchBeanModel();

        if (cargo.getSearchMethod() == OrderSearchCargoIfc.SEARCH_BY_ORDER_STATUS)
        {
            POSUIManagerIfc ui =
                (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
            ui.showScreen(POSUIManagerIfc.STATUS_SEARCH, ssModel);
        }
        else
        {
            bus.mail(new Letter (CommonLetterIfc.SUCCESS), BusIfc.CURRENT);
        }

    }

    //--------------------------------------------------------------------------
    /**
       Retrieves the order status, start and end dates from the status
       search screen for order searching. Sets the order cargo with these
       values.
       <P>
       @param bus the bus departing from this site
    **/
    //--------------------------------------------------------------------------
    public void depart(BusIfc bus)
    {
        int status = -1;

        OrderSearchCargoIfc      cargo   = (OrderSearchCargoIfc)bus.getCargo();

        if (cargo.getSearchMethod() == OrderSearchCargoIfc.SEARCH_BY_ORDER_STATUS)
        {
            //retrieve the status from the model
            POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
            StatusSearchBeanModel model = (StatusSearchBeanModel)ui.getModel(POSUIManagerIfc.STATUS_SEARCH);
            // update the order cargo as per the ValidatingComboBox Index Selected
            if (model.getStatusIndex() >= 0)
            {
                cargo.setStatus(model.getStatusIndex());
            }
            cargo.setStartDate(model.getStartDate());
            cargo.setEndDate(model.getEndDate());
            cargo.setDateRange(true);
        }
    }
}
