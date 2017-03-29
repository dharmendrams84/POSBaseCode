/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifytransaction/retrieve/SaveCanceledTransactionSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:30 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    jswan     01/21/10 - Fixed comments.
 *    jswan     01/21/10 - Fix an issue in which a returned gift card can be
 *                         modified during the period in which the transaction
 *                         has been suspended.
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         5/14/2007 2:32:57 PM   Alan N. Sinton  CR
 *         26486 - EJournal enhancements for VAT.
 *    3    360Commerce 1.2         3/31/2005 4:29:49 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:01 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:02 PM  Robert Pearse   
 *
 *   Revision 1.7  2004/06/03 14:47:43  epd
 *   @scr 5368 Update to use of DataTransactionFactory
 *
 *   Revision 1.6  2004/04/20 13:17:06  tmorris
 *   @scr 4332 -Sorted imports
 *
 *   Revision 1.5  2004/04/14 15:17:10  pkillick
 *   @scr 4332 -Replaced direct instantiation(new) with Factory call.
 *
 *   Revision 1.4  2004/02/24 16:21:29  cdb
 *   @scr 0 Remove Deprecation warnings. Cleaned code.
 *
 *   Revision 1.3  2004/02/12 16:51:12  mcs
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
 *    Rev 1.0   Jan 20 2004 16:25:00   DCobb
 * Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifytransaction.retrieve;

// foundation imports
import oracle.retail.stores.pos.journal.JournalFormatterManagerIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.TransactionWriteDataTransaction;
import oracle.retail.stores.domain.financial.FinancialTotalsIfc;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.financial.TillIfc;
import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionTotalsIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;

//------------------------------------------------------------------------------
/**
    Saves the canceled transaction. <P>
**/
//------------------------------------------------------------------------------
public class SaveCanceledTransactionSite extends PosSiteActionAdapter
{
    /* serialVersionUID */
    private static final long serialVersionUID = 4383174734390673341L;

    /**
       site name constant
    **/
    public static final String SITENAME = "SaveCanceledTransactionSite ";

    //--------------------------------------------------------------------------
    /**
       Saves the canceled transaction. <P>
       @param bus the bus arriving at this site
    **/
    //--------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {                                   // begin arrive()

        String letterName = CommonLetterIfc.SUCCESS;

        // retrieve transaction data from cargo
        ModifyTransactionRetrieveCargo cargo =
            (ModifyTransactionRetrieveCargo) bus.getCargo();

        JournalManagerIfc journal = (JournalManagerIfc)
            Gateway.getDispatcher().getManager(JournalManagerIfc.TYPE);
        UtilityManagerIfc utility =
          (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        // get transaction from cargo
        TenderableTransactionIfc trans = cargo.getTransaction();
        
        // reset transaction to null so that it will not be added to transaction
        cargo.setTransaction(null);

        // Save the transaction to persistent storage
        try
        {
            // cancel existing transaction
            cancelTransaction(trans,
                              cargo,
                              journal,
                              bus.getServiceName());

            /*cargo trans. should be null so that it will not be added to transaction
            However, if receipt is required for trans. cancellation, then trans. in
            cargo should be set to retrieveTransaction and retreive.xml adjusted for
            redirection to PrintReceipt site.
            cargo.setTransaction(retrieveTransaction);*/

            utility.writeHardTotals(bus);

        }
        catch (DataException e)
        {
            cargo.setDataExceptionErrorCode(e.getErrorCode());
            letterName = CommonLetterIfc.DB_ERROR;
        }
        catch (DeviceException e)
        {
            letterName = CommonLetterIfc.HARD_TOTALS_ERROR;
        }

        bus.mail(new Letter(letterName), BusIfc.CURRENT);

    }                                                                   // end arrive()

    //---------------------------------------------------------------------
    /**
       Cancel the suspended transaction. <P>
       @param retrieveTransaction retrieved transaction
       @param cargo cargo class
       @param journal JournalManagerIfc reference
       @param serviceName service name used in logging
       @exception DataException thrown if error occurs writing canceled
       transaction to database
    **/
    //---------------------------------------------------------------------
    public void cancelTransaction
    (TenderableTransactionIfc retrieveTransaction,
     ModifyTransactionRetrieveCargo cargo,
     JournalManagerIfc journal,
     String serviceName)
        throws DataException
    {                                   // begin cancelTransaction()
        // update financials
        RegisterIfc register = cargo.getRegister();
        register.addNumberCancelledTransactions(1);
        TransactionTotalsIfc totals = retrieveTransaction.getTransactionTotals();
        register.addAmountCancelledTransactions
            (totals.getSubtotal().subtract(totals.getDiscountTotal()).abs());

        // set search transaction status to canceled and update
        retrieveTransaction.setTransactionStatus
            (TransactionIfc.STATUS_CANCELED);

        // Create a totals object that contains only the cancel
        // transaction numbers.  Set the total on clones of the register
        // and till.  These will be used to accumulate the data
        // in the database.
        TillIfc    till = (TillIfc)register.getCurrentTill().clone();
        RegisterIfc reg = (RegisterIfc)register.clone();
        FinancialTotalsIfc accTotals =
          DomainGateway.getFactory().getFinancialTotalsInstance();
        accTotals.setNumberCancelledTransactions(1);
        accTotals.setAmountCancelledTransactions
          (totals.getSubtotal().subtract(totals.getDiscountTotal()).abs());
        till.setTotals(accTotals);
                reg.setTotals(accTotals);

        TransactionWriteDataTransaction writeTransaction = null;
        
        writeTransaction = (TransactionWriteDataTransaction) DataTransactionFactory.create(DataTransactionKeys.TRANSACTION_WRITE_DATA_TRANSACTION);
        
        // If the transaction which was created from the suspended transaction
        // is being canceled, then the transaction must be saved rather than updated.
        if (cargo.isCancellingRecreatedTransaction())
        {
            writeTransaction.saveTransaction(retrieveTransaction, till, reg);
        }
        else
        {
            writeTransaction.updateTransactionStatus(retrieveTransaction, till, reg);
        }
        
        JournalFormatterManagerIfc formatter =
            (JournalFormatterManagerIfc)Gateway.getDispatcher().getManager(JournalFormatterManagerIfc.TYPE);
        if (journal != null && formatter != null)
        {
            String transactionID = retrieveTransaction.getTransactionID();
            String taxCancel = formatter.journalCanceledSuspendedTransaction(retrieveTransaction);
            journal.journal(cargo.getOperator().getLoginID(),transactionID,taxCancel);
            if (logger.isInfoEnabled()) logger.info("Transaction " + transactionID + " canceled");
        }
        else
        {
            logger.error("No JournalManager found");
        }

    }                                   // end cancelTransaction()

}                                                                               // end class SaveCanceledTransactionSite


