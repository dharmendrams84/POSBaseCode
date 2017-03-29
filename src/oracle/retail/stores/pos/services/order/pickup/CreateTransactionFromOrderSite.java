/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/pickup/CreateTransactionFromOrderSite.java /rgbustores_13.4x_generic_branch/2 2011/09/09 17:41:55 sgu Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    sgu       09/08/11 - add house account as a refund tender
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    asinton   06/05/09 - Added original tenders from the order initiate to
 *                         the pickup transaction if canceled order items
 *                         exist.
 *    aphulamb  11/13/08 - Check in all the files for Pickup Delivery Order
 *                         functionality
 *
 * ===========================================================================
 * $Log:
 *    7    360Commerce 1.6         4/25/2007 8:52:19 AM   Anda D. Cadar   I18N
 *         merge
 *
 *    6    360Commerce 1.5         3/29/2007 6:47:52 PM   Michael Boyd    CR
 *         26172 - v8x merge to trunk
 *
 *         7    .v8x      1.4.1.1     3/9/2007 9:36:03 AM    Maisa De Camargo
 *         Removed Duplicated code.
 *
 *         6    .v8x      1.4.1.0     1/18/2007 5:17:49 AM   Manas Sahu
 *         CR
 *         23349:
 *         EJournal is missing the customer link and the transaction
 *         information at the top (just after the header)
 *         Expected Results:
 *         There is transaction information in the EJournal before the PICKUP
 *         ORDER header.
 *
 *         Link customer 2345
 *         ITEM: 1234                       10.00 T
 *         CoolBox
 *         Qty: 1 @ 10.00
 *         Link customer: 23456
 *         06/15/2005               06:30:29 PM CDT
 *         Trans.: 0229                Store: 04241
 *         Reg.: 129                      Till: 001
 *         Cashier: 20027              Sales: 20027
 *
 *         The above section should be added just Before the header line
 *         PICKUP ORDER.
 *
 *         Changed in Method writeToJournal() from Line numbers 224 to 258
 *    5    360Commerce 1.4         8/9/2006 6:14:07 PM    Brett J. Larsen CR
 *         19562 - incorrect special order previous-status in poslog
 *
 *         CR 4170 - incorrect extra information at top of special order
 *         pickup in ejournal
 *
 *         v7x->360commerce merge
 *    4    360Commerce 1.3         1/22/2006 11:45:14 AM  Ron W. Haight
 *         removed references to com.ibm.math.BigDecimal
 *    3    360Commerce 1.2         3/31/2005 4:27:32 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:20:26 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:10:14 PM  Robert Pearse
 *
 *
 *    6    .v7x      1.3.1.1     7/20/2006 7:49:59 AM   Deepanshu       CR
 *         19562: Defect fixed, item status is corrected for special order
 *         line items in PosLog
 *    5    .v7x      1.3.1.0     6/30/2006 7:03:09 AM   Dinesh Gautam   CR
 *         4170: Defect fixed, redundent info removed from EJoornal.
 *
 *   Revision 1.8.2.1  2004/10/26 23:21:18  cdb
 *   @scr Updated to initialize transaction with the customer (and customer locale) info.
 *
 *   Revision 1.8  2004/10/06 21:28:09  mweis
 *   @scr 7012 Ensure Post Void of Order works w.r.t. Inventory.  Use getPreviousStatus() to get the old status.  Doh!
 *
 *   Revision 1.7  2004/10/06 02:44:24  mweis
 *   @scr 7012 Special and Web Orders now have Inventory.
 *
 *   Revision 1.6  2004/10/01 17:24:04  mweis
 *   @scr 7243 Fix NPE crash when writing tax stuff.
 *
 *   Revision 1.5  2004/03/15 21:43:29  baa
 *   @scr 0 continue moving out deprecated files
 *
 *   Revision 1.4  2004/03/03 23:15:07  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.3  2004/02/12 16:51:26  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:37  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:19  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:03:50   CSchellenger
 * Initial revision.
 *
 *    Rev 1.2   Aug 28 2002 10:08:34   jriggins
 * Introduced the OrderCargo.serviceType property complete with accessor and mutator methods.  Replaced places where service names were being compared (via String.equals()) to String constants in OrderCargoIfc with comparisons to the newly-created serviceType constants which are ints.
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.1   25 Jun 2002 17:07:22   jbp
 * set payment date to business date
 * Resolution for POS SCR-1620: Special order payments inserted with wrong business date
 *
 *    Rev 1.0   Apr 29 2002 15:11:52   msg
 * Initial revision.
 *
 *    Rev 1.1   02 Apr 2002 15:14:10   dfh
 * updates to better journal discounts for order pickup, order cancel, and return by customer (no receipt)
 * Resolution for POS SCR-1567: Picked up Orders/ return of picked up orders missing discounts in EJ
 *
 *    Rev 1.0   Mar 18 2002 11:41:38   msg
 * Initial revision.
 *
 *    Rev 1.5   Feb 01 2002 09:54:32   dfh
 * updates to fix reverting item status on cancel order that was voided,
 * status back to edit item status screen value
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.4   29 Jan 2002 15:10:08   sfl
 * Do not create a new order transaction when
 * the special order is escaping out of the Tender
 * Options screen during special order tender.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.3   Jan 28 2002 12:33:18   dfh
 * fix journalling.....
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *
 *    Rev 1.2   21 Jan 2002 19:02:56   cir
 *
 * Removed the comment
 *
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *
 *
 *    Rev 1.1   15 Jan 2002 18:44:30   cir
 *
 * Use createOrderTransaction
 *
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *
 *
 *    Rev 1.0   Sep 24 2001 13:01:16   MPM
 *
 * Initial revision.
 *
 *
 *    Rev 1.1   Sep 17 2001 13:10:40   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.pickup;
// java import
import oracle.retail.stores.pos.journal.JournalFormatterManagerIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.domain.returns.ReturnTenderDataElementIfc;
import oracle.retail.stores.domain.tender.TenderChargeIfc;
import oracle.retail.stores.domain.tender.TenderLineItemIfc;
import oracle.retail.stores.domain.transaction.OrderTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;

//------------------------------------------------------------------------------
/**
    This creates a transaction using order line items for the pos service.
    @version $Revision: /rgbustores_13.4x_generic_branch/2 $
**/
//--------------------------------------------------------------------------
public class CreateTransactionFromOrderSite extends PosSiteActionAdapter
{                                       // begin class CreateTransactionFromOrderSite
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3129451243280849777L;
    /**
       site name constant
    **/
    public static final String SITENAME = "CreateTransactionFromOrderSite";
    /**
       revision number supplied by source-code-control system
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";
    /**
       Constant for generating the Transaction Sequence number.
    **/
    public static final long GENERATE_SEQUENCE_NUMBER = -1;

    //--------------------------------------------------------------------------
    /**
       This creates a transaction using order line items for the pos service.
       <P>
       @param bus the bus arriving at this site
    **/
    //--------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {                                   // begin arrive()
        PickupOrderCargo            cargo = (PickupOrderCargo)bus.getCargo();
        OrderIfc order = cargo.getOrder();

        UtilityManagerIfc utility =
          (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        // set up initial transaction tax on the order
        order.setTransactionTax(utility.getInitialTransactionTax(bus));

        OrderTransactionIfc transaction = null;
        if (cargo.getTransaction() != null)
        {
            transaction = cargo.getTransaction();
            cargo.setTransaction(transaction);
        }
        else
        {
            transaction = order.createOrderTransaction(true);
            transaction.setOrderType(order.getOrderType());
            transaction.setSalesAssociate(cargo.getOperator());
            transaction.getPayment().setBusinessDate(cargo.getRegister().getBusinessDate());
            utility.initializeTransaction(transaction, bus, GENERATE_SEQUENCE_NUMBER);
            cargo.setTransaction(transaction);
            writeToJournal(cargo, transaction, bus);
        }

        /*
         * In the case of canceled items where a refund might be given then in order to make
         * the refund options work the original tenders need to be available in the order transaction.
         */
        if(transaction.containsCanceledOrderLineItems())
        {
            SaleReturnTransactionIfc originalTransaction = (SaleReturnTransactionIfc)order.getOriginalTransaction();
            transaction.setReturnTenderElements(getOriginalTenders(originalTransaction.getTenderLineItems()));
        }

        // mail a Continue letter
        bus.mail(new Letter(CommonLetterIfc.CONTINUE), BusIfc.CURRENT);
    }                                   // end arrive()

    // ----------------------------------------------------------------------
    /**
     * Retrieve tenders from original transaction
     *
     * @param tenderList
     * @return ReturnTenderDataElement[] list of tenders
     */
    // ----------------------------------------------------------------------
    protected ReturnTenderDataElementIfc[] getOriginalTenders(
            TenderLineItemIfc[] tenderList)
    {
        ReturnTenderDataElementIfc[] tenders = new ReturnTenderDataElementIfc[tenderList.length];
        for (int i = 0; i < tenderList.length; i++)
        {
            tenders[i] = DomainGateway.getFactory()
                    .getReturnTenderDataElementInstance();
            tenders[i].setTenderType(tenderList[i].getTypeCode());
            if (tenderList[i].getTypeCode() == TenderLineItemIfc.TENDER_TYPE_CHARGE)
            {
                tenders[i].setCardType(((TenderChargeIfc)tenderList[i]).getCardType());
            }
            tenders[i].setAccountNumber(tenderList[i].getNumber());
            tenders[i].setTenderAmount(tenderList[i].getAmountTender());
        }
        return tenders;
    }

    //----------------------------------------------------------------------
    /**
       Writes a transaction containing Pickup Order items to the Journal.
       <P>
       @param  cargo        The pickup order cargo.
       @param  transaction  The newly create sale return transaction containing.
       @param  bus          The bus.
       order line items
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
    } // end writeToJournal
} // end class CreateTransactionFromOrderSite
