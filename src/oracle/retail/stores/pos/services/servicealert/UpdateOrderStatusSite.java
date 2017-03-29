/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/servicealert/UpdateOrderStatusSite.java /main/10 2011/02/16 09:13:29 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         5/8/2007 5:22:00 PM    Alan N. Sinton  CR
 *         26486 - Refactor of some EJournal code.
 *    3    360Commerce 1.2         3/31/2005 4:30:40 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:26:35 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:15:25 PM  Robert Pearse   
 *
 *   Revision 1.6  2004/06/03 14:47:44  epd
 *   @scr 5368 Update to use of DataTransactionFactory
 *
 *   Revision 1.5  2004/04/20 13:17:06  tmorris
 *   @scr 4332 -Sorted imports
 *
 *   Revision 1.4  2004/04/14 15:17:11  pkillick
 *   @scr 4332 -Replaced direct instantiation(new) with Factory call.
 *
 *   Revision 1.3  2004/02/12 16:51:58  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:29  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:20  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:07:02   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:03:28   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:47:42   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 24 2001 13:05:28   MPM
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:13:20   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.servicealert;

import oracle.retail.stores.pos.journal.JournalFormatterManagerIfc;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.OrderWriteDataTransaction;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.domain.transaction.OrderTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;

/**
 * This site updates the status of the order.
 * 
 * @version $Revision: /main/10 $
 */
public class UpdateOrderStatusSite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = -6817101446786164242L;

    public static final String SITENAME = "UpdateOrderStatusSite";

    /**
     * Update the order status in the database, make a journal entry and mail a
     * letter.
     * 
     * @param bus the bus arriving at this site
     */
    @Override
    public void arrive(BusIfc bus)
    {
        ServiceAlertCargo cargo = (ServiceAlertCargo) bus.getCargo();
        Letter letter = new Letter(CommonLetterIfc.SUCCESS);

        OrderWriteDataTransaction owdt = null;

        owdt = (OrderWriteDataTransaction) DataTransactionFactory
                .create(DataTransactionKeys.ORDER_WRITE_DATA_TRANSACTION);

        OrderIfc order = cargo.getOrder();
        SaleReturnTransactionIfc trans = cargo.getTransaction();
        // the service name is really service alert, but Print is the action
        // that needs to get journalled.

        try
        {
            owdt.updateOrder(order);

            // Journal the Print action that was performed on the order, so that
            // current item status and current order status are captured.
            ParameterManagerIfc pm = (ParameterManagerIfc) bus.getManager(ParameterManagerIfc.TYPE);
            JournalFormatterManagerIfc formatter = (JournalFormatterManagerIfc) Gateway.getDispatcher().getManager(
                    JournalFormatterManagerIfc.TYPE);
            formatter.journalOrder((OrderTransactionIfc) trans, order, pm);
        }
        catch (DataException de)
        {
            cargo.setDataExceptionErrorCode(de.getErrorCode());
            letter = new Letter(CommonLetterIfc.FAILURE);
        }

        bus.mail(letter, BusIfc.CURRENT);
    }

}
