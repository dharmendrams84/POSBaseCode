/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/cancel/CreateTransactionFromOrderSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:34 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - updating deprecated names
 *    aphulamb  12/17/08 - bug fixing of PDO
 *    aphulamb  12/09/08 - Deposite Amount and Cancel Trasaction Fixes.
 *
 * ===========================================================================
 * $Log:
 *    7    360Commerce 1.6         8/9/2007 5:53:42 PM    Maisa De Camargo
 *         Updated Cancel Transaction E-Journal Format.
 *    6    360Commerce 1.5         5/8/2007 5:22:00 PM    Alan N. Sinton  CR
 *         26486 - Refactor of some EJournal code.
 *    5    360Commerce 1.4         4/25/2007 8:52:20 AM   Anda D. Cadar   I18N
 *         merge
 *         
 *    4    360Commerce 1.3         1/22/2006 11:45:14 AM  Ron W. Haight
 *         removed references to com.ibm.math.BigDecimal
 *    3    360Commerce 1.2         3/31/2005 4:27:32 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:26 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:14 PM  Robert Pearse   
 *
 *   Revision 1.10.2.1  2004/10/27 16:21:25  cdb
 *   @scr 7527 Merged trunk revision 1.11 with branch.
 *
 *   Revision 1.11  2004/10/27 15:56:00  cdb
 *   @scr 7527 Updated to initialize cancel transaction with the customer (and customer locale) info.
 *
 *   Revision 1.10  2004/10/06 02:44:24  mweis
 *   @scr 7012 Special and Web Orders now have Inventory.
 *
 *   Revision 1.9  2004/09/30 20:21:51  jdeleau
 *   @scr 7263 Make printItemTax apply to e-journal as well as receipts.
 *
 *   Revision 1.8  2004/09/29 16:30:24  mweis
 *   @scr 7012 Special Order and Inventory integration -- canceling the entire order.
 *
 *   Revision 1.7  2004/09/23 21:17:56  mweis
 *   @scr 7012 Special Order and Web Order parameters for POS Inventory
 *
 *   Revision 1.6  2004/06/29 22:03:30  aachinfiev
 *   Merge the changes for inventory & POS integration
 *
 *   Revision 1.5.2.2  2004/06/24 13:06:00  aachinfiev
 *   Moved inventory location/state setting into Order.createOrderTransaction
 *
 *   Revision 1.5.2.1  2004/06/14 17:48:08  aachinfiev
 *   Inventory location/state related modifications
 *
 *   Revision 1.5  2004/03/15 21:43:30  baa
 *   @scr 0 continue moving out deprecated files
 *
 *   Revision 1.4  2004/03/03 23:15:12  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.3  2004/02/12 16:51:21  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:46  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:03:24   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.2   Aug 28 2002 10:08:20   jriggins
 * Introduced the OrderCargo.serviceType property complete with accessor and mutator methods.  Replaced places where service names were being compared (via String.equals()) to String constants in OrderCargoIfc with comparisons to the newly-created serviceType constants which are ints.
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.1   25 Jun 2002 17:07:24   jbp
 * set payment date to business date
 * Resolution for POS SCR-1620: Special order payments inserted with wrong business date
 * 
 *    Rev 1.0   Apr 29 2002 15:13:28   msg
 * Initial revision.
 * 
 *    Rev 1.1   02 Apr 2002 15:14:08   dfh
 * updates to better journal discounts for order pickup, order cancel, and return by customer (no receipt)
 * Resolution for POS SCR-1567: Picked up Orders/ return of picked up orders missing discounts in EJ
 * 
 *    Rev 1.0   Mar 18 2002 11:40:48   msg
 * Initial revision.
 * 
 *    Rev 1.4   Feb 01 2002 09:53:54   dfh
 * updates to fix reverting item status on cancel order that is voided, reverts back to values on edit item status screen
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * 
 *    Rev 1.3   31 Jan 2002 11:05:44   sfl
 * Adding a conditional check to make sure the transaction
 * ID is not increamented until the Tender is actually done.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * 
 *    Rev 1.2   Jan 23 2002 21:13:30   dfh
 * removed setting the transaction type to order cancel
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * 
 *    Rev 1.1   Jan 17 2002 21:24:06   dfh
 * add own label
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * 
 *    Rev 1.0   Jan 17 2002 21:23:14   dfh
 * Initial revision.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *      
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.cancel;

// java import
import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.pos.journal.JournalFormatterManagerIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.discount.DiscountRuleConstantsIfc;
import oracle.retail.stores.domain.discount.DiscountRuleIfc;
import oracle.retail.stores.domain.lineitem.OrderLineItemIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.domain.transaction.OrderTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionTotalsIfc;
import oracle.retail.stores.domain.utility.CodeConstantsIfc;
import oracle.retail.stores.domain.utility.CodeListMapIfc;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.order.common.OrderCargoIfc;
import oracle.retail.stores.pos.services.order.common.OrderUtilities;
import oracle.retail.stores.pos.services.order.pickup.PickupOrderCargo;
import java.math.BigDecimal;

//------------------------------------------------------------------------------
/**
    This creates an order cancel transaction using order line items for the tender service.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class CreateTransactionFromOrderSite extends PosSiteActionAdapter
{                                       // begin class CreateTransactionFromOrderSite
    /**
       site name constant
    **/
    public static final String SITENAME = "CreateTransactionFromOrderSite";
    /**
       revision number supplied by source-code-control system
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
       Constant for generating the Transaction Sequence number.
    **/
    public static final long GENERATE_SEQUENCE_NUMBER = -1;

    //--------------------------------------------------------------------------
    /**
       This creates an order cancel transaction using order line items for the tender service.
       <P>
       @param bus the bus arriving at this site
    **/
    //--------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {                                   // begin arrive()
        ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
        UtilityManagerIfc utility = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
        PickupOrderCargo cargo = (PickupOrderCargo)bus.getCargo();
        
        // get the order
        OrderIfc order = cargo.getOrder();
        
        // set up initial transaction tax on the order
        order.setTransactionTax(utility.getInitialTransactionTax(bus));

        if (cargo.getTransaction() != null)
        {
            cargo.setTransaction((OrderTransactionIfc)cargo.getTransaction());
        }
        else
        {
            OrderTransactionIfc transaction = order.createOrderTransaction(true);
            transaction.setOrderType(order.getOrderType());
            transaction.setSalesAssociate(cargo.getOperator());
            transaction.getPayment().setBusinessDate(cargo.getRegister().getBusinessDate());
            utility.initializeTransaction(transaction, bus, GENERATE_SEQUENCE_NUMBER);
            cargo.setTransaction(transaction);
            writeToJournal(cargo, transaction, bus);
        }
        
        // mail a Continue letter
        bus.mail(new Letter(CommonLetterIfc.CONTINUE), BusIfc.CURRENT);
    }                                   // end arrive()

    //----------------------------------------------------------------------
    /**
       Writes a transaction containing Pickup Order items to the Journal.
       <P>
       @param  cargo        the cargo
       @param  transaction  the newly create sale return transaction containing
                            order line items
       @param  bus          the bus
    **/
    //----------------------------------------------------------------------
    public void writeToJournal(PickupOrderCargo cargo,
                               OrderTransactionIfc transaction,
                               BusIfc bus)
    {                                   // begin writeToJournal()
        OrderIfc order = cargo.getOrder();
        JournalManagerIfc journal =
            (JournalManagerIfc)Gateway.getDispatcher().
                getManager(JournalManagerIfc.TYPE);
        JournalFormatterManagerIfc formatter =
            (JournalFormatterManagerIfc)Gateway.getDispatcher().
                getManager(JournalFormatterManagerIfc.TYPE);
        if(journal != null && formatter != null)
        {
            ParameterManagerIfc pm = (ParameterManagerIfc) bus.getManager(ParameterManagerIfc.TYPE);
            journal.journal(transaction.getCashier().getLoginID(),
                    transaction.getTransactionID(),
                    formatter.journalOrder(transaction, order, pm));
        }
    }     // end writeToJournal()

} // end class CreateTransactionFromOrderSite
