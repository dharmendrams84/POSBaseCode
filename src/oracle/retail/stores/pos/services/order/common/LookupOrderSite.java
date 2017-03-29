/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/common/LookupOrderSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:33 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *    jswan     04/24/09 - Code review changes.
 *    jswan     04/24/09 - Modified to ensure that orders created in training
 *                         mode can only retrieve in training mode, and
 *                         non-training mode orders can only be retrieved in
 *                         non-training mode.
 *    npoola    04/04/09 - fix to save the Recipient details of PDO from
 *                         service alerts
 *    aphulamb  11/13/08 - Check in all the files for Pickup Delivery Order
 *                         functionality
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         7/3/2007 2:29:23 PM    Ashok.Mondal    CR
 *         27435 :Print the date format on the receipt based on the store
 *         server locale.
 *    3    360Commerce 1.2         3/31/2005 4:28:58 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:23:21 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:12:28 PM  Robert Pearse
 *
 *   Revision 1.6.2.1  2004/11/05 21:35:40  cdb
 *   @scr 7527 Modified so that order retrieval would make use of customer in setting locale.
 *
 *   Revision 1.6  2004/06/03 14:47:44  epd
 *   @scr 5368 Update to use of DataTransactionFactory
 *
 *   Revision 1.5  2004/04/19 14:53:47  tmorris
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
 *    Rev 1.0   Aug 29 2003 16:03:30   CSchellenger
 * Initial revision.
 *
 *    Rev 1.1   Mar 10 2003 09:45:54   RSachdeva
 * Database Internationalization
 * Resolution for POS SCR-1866: I18n Database  support
 *
 *    Rev 1.0   Apr 29 2002 15:12:58   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:41:04   msg
 * Initial revision.
 *
 *    Rev 1.1   15 Jan 2002 18:36:10   cir
 * Use OrderReadDataTransaction().readOrder(orderID)
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.0   Sep 24 2001 13:00:14   MPM
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:10:26   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.common;

//java imports
import java.util.Locale;

import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.OrderReadDataTransaction;
import oracle.retail.stores.domain.order.OrderConstantsIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.domain.order.OrderStatusIfc;
import oracle.retail.stores.domain.order.OrderSummaryEntryIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.LocaleMap;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.UIUtilities;


//------------------------------------------------------------------------------
/**

    Retrieves an OrderIfc based upon the OrderSummaryEntryIfc reference.

    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------

public class LookupOrderSite extends PosSiteActionAdapter
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6770804092598805798L;

    /**
       class name constant
    **/
    public static final String SITENAME = "LookupOrderSite";

    /**
       revision number for this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //--------------------------------------------------------------------------
    /**
       Retrieves the order detail object based upon the order summary set
       in cargo.

       @param bus the bus arriving at this site
    **/
    //--------------------------------------------------------------------------

    public void arrive(BusIfc bus)
    {
    	// get utility manager
        UtilityManagerIfc utility =
            (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        OrderCargo cargo = (OrderCargo) bus.getCargo();

        Letter      result  = new Letter (CommonLetterIfc.SUCCESS);
        OrderIfc    order  = null;

        //get the selected summary from cargo
        OrderSummaryEntryIfc selectedSummary = cargo.getSelectedSummary();

        OrderReadDataTransaction readDataTransaction = null;

        readDataTransaction = (OrderReadDataTransaction) DataTransactionFactory.create(DataTransactionKeys.ORDER_READ_DATA_TRANSACTION);

        try
        {
            OrderStatusIfc searchOrder =
              DomainGateway.getFactory().getOrderStatusInstance();
            searchOrder.setOrderID(selectedSummary.getOrderID());
            searchOrder.setLocaleRequestor(utility.getRequestLocales());
            searchOrder.setInitiatingChannel(OrderConstantsIfc.ORDER_CHANNEL_NOT_APPLICABLE);
            searchOrder.setTrainingModeFlag(cargo.getRegister().getWorkstation().isTrainingMode());
            order = readDataTransaction.readOrder(searchOrder);
            cargo.setOrder(order);
            // Use customer locale preferrences for the
            // pole display and receipt  subsystems
            Locale customerLocale = order.getCustomer().getPreferredLocale();

            if (customerLocale != null)
            {
                if (!customerLocale.equals(LocaleMap.getLocale(LocaleConstantsIfc.RECEIPT)))
                {
                    //CR 27435 : Do not print date format on the receipt based on the customer locale.
                    //Print the date format as per the store server locale for the picklist order.
                    //LocaleMap.putLocale(LocaleConstantsIfc.RECEIPT, customerLocale);
                    UIUtilities.setUILocaleForCustomer(customerLocale);
                }
            }
        }
        catch (DataException de)
        {
            result = new Letter(CommonLetterIfc.DB_ERROR);
            cargo.setDataExceptionErrorCode(de.getErrorCode());

            logger.error( " DB error: " + de.getMessage() + "");
        }

        bus.mail(result,BusIfc.CURRENT);

    }
}
