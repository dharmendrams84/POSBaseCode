/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/common/UpdateOrderSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:34 mszekely Exp $
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
 *    7    360Commerce 1.6         5/21/2007 9:16:21 AM   Anda D. Cadar   EJ
 *         changes
 *    6    360Commerce 1.5         5/12/2006 5:25:30 PM   Charles D. Baker
 *         Merging with v1_0_0_53 of Returns Managament
 *    5    360Commerce 1.4         5/4/2006 5:11:51 PM    Brendan W. Farrell
 *         Remove inventory.
 *    4    360Commerce 1.3         4/27/2006 7:07:08 PM   Brett J. Larsen CR
 *         17307 - inventory functionality removal - stage 2
 *    3    360Commerce 1.2         3/31/2005 4:30:40 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:26:35 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:15:25 PM  Robert Pearse   
 *
 *   Revision 1.11.2.1  2004/10/15 18:50:30  kmcbride
 *   Merging in trunk changes that occurred during branching activity
 *
 *   Revision 1.12  2004/10/11 17:08:53  mweis
 *   @scr 7012 Transistion to use SALEABLE instead of AVAILABLE_TO_SELL
 *
 *   Revision 1.11  2004/10/06 02:44:25  mweis
 *   @scr 7012 Special and Web Orders now have Inventory.
 *
 *   Revision 1.10  2004/09/29 16:30:24  mweis
 *   @scr 7012 Special Order and Inventory integration -- canceling the entire order.
 *
 *   Revision 1.9  2004/09/23 21:17:59  mweis
 *   @scr 7012 Special Order and Web Order parameters for POS Inventory
 *
 *   Revision 1.8  2004/06/29 22:03:32  aachinfiev
 *   Merge the changes for inventory & POS integration
 *
 *   Revision 1.7  2004/06/03 14:47:44  epd
 *   @scr 5368 Update to use of DataTransactionFactory
 *
 *   Revision 1.6  2004/04/20 13:17:06  tmorris
 *   @scr 4332 -Sorted imports
 *
 *   Revision 1.5  2004/04/14 15:17:11  pkillick
 *   @scr 4332 -Replaced direct instantiation(new) with Factory call.
 *
 *   Revision 1.4  2004/03/03 23:15:09  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
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
 *    Rev 1.1   Aug 28 2002 10:08:22   jriggins
 * Introduced the OrderCargo.serviceType property complete with accessor and mutator methods.  Replaced places where service names were being compared (via String.equals()) to String constants in OrderCargoIfc with comparisons to the newly-created serviceType constants which are ints.
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.0   Apr 29 2002 15:13:18   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:41:16   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 24 2001 13:01:04   MPM
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:10:22   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.common;

//foundation imports
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.OrderWriteDataTransaction;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;

//------------------------------------------------------------------------------
/**
    Updates an order based upon order in cargo. Updates the status of the order
    and its order line items. After the order has been successfully updated,
    this site clears the selected summary that was used to retrieve the order from cargo.

    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------

public class UpdateOrderSite extends PosSiteActionAdapter
{
    /**
       class name constant
    **/
    public static final String SITENAME = "UpdateOrderSite";

    /**
       revision number for this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //--------------------------------------------------------------------------
    /**
       Performs a data transaction to save the state of the current order in
       cargo to persistent storage.
       Indirectly updates the inventory for the line items.

       @param bus the bus arriving at this site
    **/
    //--------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        OrderCargo cargo = (OrderCargo) bus.getCargo();
        OrderIfc order   = cargo.getOrder();

        if (order != null)
        {
            Letter result = new Letter(CommonLetterIfc.SUCCESS);

            OrderWriteDataTransaction orderWTransaction =
                (OrderWriteDataTransaction) DataTransactionFactory.create(DataTransactionKeys.ORDER_WRITE_DATA_TRANSACTION);
            ParameterManagerIfc pm = (ParameterManagerIfc) bus.getManager(ParameterManagerIfc.TYPE);
            try
            {
                orderWTransaction.updateOrder(order);

                // Journal the action that was performed on the order (PickList, Fill or Cancel)
                // Pickup service journalling is executed elsewhere and therefore is ignored here.
                // Pass the order, an empty String for transactionID, the service name and cargo.
                if (!(cargo.getServiceType() == OrderCargoIfc.SERVICE_PICKUP_TYPE))
                {
                    OrderUtilities utility = new OrderUtilities();
                    utility.journalOrder(order, "", cargo.getServiceType(), cargo, pm);
                }
            }
            catch (DataException de)
            {                               // begin  data base exception catch
                result = new Letter(CommonLetterIfc.DB_ERROR);
                logger.error( " DB error: " + de.getMessage() + "");
                cargo.setDataExceptionErrorCode(de.getErrorCode());
            }                               // end database error catch

            //clean up cargo, clear Selected Summary and shrink the list
            cargo.clearSelectedSummary(true);

            bus.mail(result,BusIfc.CURRENT);
        }
        else
        {
            throw new NullPointerException("OrderCargo contains null order reference in" +
                                           "UpdateOrderSite.arrive()");
        }
    }
}
