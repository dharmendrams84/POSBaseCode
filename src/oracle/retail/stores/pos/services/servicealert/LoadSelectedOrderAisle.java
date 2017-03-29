/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/servicealert/LoadSelectedOrderAisle.java /main/13 2011/02/16 09:13:29 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    abondala  01/03/10 - update header date
 *    jswan     04/24/09 - Modified to ensure that orders created in training
 *                         mode can only retrieve in training mode, and
 *                         non-training mode orders can only be retrieved in
 *                         non-training mode.
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:28:52 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:23:08 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:12:20 PM  Robert Pearse
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
 *    Rev 1.0   Aug 29 2003 16:06:56   CSchellenger
 * Initial revision.
 *
 *    Rev 1.0   Apr 29 2002 15:03:04   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:47:30   msg
 * Initial revision.
 *
 *    Rev 1.1   Jan 17 2002 21:11:54   dfh
 * use new domain/db
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.0   Sep 24 2001 13:05:28   MPM
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:13:26   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.servicealert;

// Foundation imports
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.OrderReadDataTransaction;
import oracle.retail.stores.domain.order.OrderConstantsIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;

/**
 * This aisle retrieves the complete pick up order object when the user selects
 * a pickup order item from the Service Alert List screen and presses the next
 * button.
 * 
 * @version $Revision: /main/13 $
 */
public class LoadSelectedOrderAisle extends PosLaneActionAdapter
{
    private static final long serialVersionUID = 3731303903935680359L;

    public static final String LANENAME = "LoadSelectedOrderAisle";

    /**
     * Retrieve the selected order from the database and load it into cargo.
     * 
     * @param bus the bus traversing this lane
     */
    @Override
    public void traverse(BusIfc bus)
    {
    	 // get utility manager
        UtilityManagerIfc utility =
            (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        ServiceAlertCargo cargo = (ServiceAlertCargo) bus.getCargo();
        Letter letter = new Letter("PrintOrder");

        // Retrieve the selected order and put it in the cargo
        OrderReadDataTransaction ordt = null;

        ordt = (OrderReadDataTransaction) DataTransactionFactory.create(DataTransactionKeys.ORDER_READ_DATA_TRANSACTION);

        // Should get a list of one
        OrderIfc order = null;
        
        try
        {
            order = ordt.readOrder(cargo.getSelectedEntry().getItemID(), OrderConstantsIfc.ORDER_CHANNEL_NOT_APPLICABLE,
                    utility.getRequestLocales(), cargo.getRegister().getWorkstation().isTrainingMode());
            cargo.setOrder(order);
        }
        catch (DataException de)
        {
            cargo.setDataExceptionErrorCode(de.getErrorCode());
            letter = new Letter(CommonLetterIfc.FAILURE);
        }

        bus.mail(letter, BusIfc.CURRENT);
    }
}
