/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/redeem/SaveCanceledRedeemTransactionSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:10 mszekely Exp $
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
 |   1    360Commerce 1.0         4/24/2008 4:30:03 PM   Kun Lu          File
 |        added to fix CR 31340.
 |  $                                                                  |
 |                                                                           |
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.redeem;

import oracle.retail.stores.pos.journal.JournalFormatterManagerIfc;
import oracle.retail.stores.pos.services.common.AbstractFinancialCargoIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.common.TenderableTransactionCargoIfc;
import oracle.retail.stores.pos.services.sale.SaleCargo;
import oracle.retail.stores.pos.services.sale.SaleCargoIfc;
import oracle.retail.stores.domain.DomainGateway;
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
import oracle.retail.stores.foundation.tour.service.SessionBusIfc;
import oracle.retail.stores.pos.device.POSDeviceActions;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;

//--------------------------------------------------------------------------
/**
    This site saves the current canceled redeem transaction.
**/
//--------------------------------------------------------------------------
public class SaveCanceledRedeemTransactionSite extends PosSiteActionAdapter
{

    /**
       revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
       Saves the current canceled transaction.
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        // retrieve cargo, transaction
        TenderableTransactionCargoIfc tenderableCargo =
          (TenderableTransactionCargoIfc) bus.getCargo();
        AbstractFinancialCargoIfc afCargo = (AbstractFinancialCargoIfc)bus.getCargo();

        TenderableTransactionIfc transaction =
          tenderableCargo.getTenderableTransaction();

        if (transaction != null)
        {
            UtilityManagerIfc utility =
               (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

            // set canceled status, normally this is set in the PrintCancelTransaction
            // site, but in the case of timeout, printCancelTransaction does not
            // get called so the status must be set here.
            transaction.setTransactionStatus(TransactionIfc.STATUS_CANCELED);
            if(transaction.getTimestampEnd() == null)
            {
                transaction.setTimestampEnd();
            }

            RegisterIfc register = afCargo.getRegister();
            register.addNumberCancelledTransactions(1);
            TransactionTotalsIfc totals = transaction.getTransactionTotals();
            register.addAmountCancelledTransactions
                (totals.getSubtotal().subtract(totals.getDiscountTotal()).abs());

            if (afCargo instanceof SaleCargoIfc)
            {
                // The following line is particular to sale service.
                ((SaleCargoIfc)afCargo).setItemModifiedIndex(SaleCargo.NO_SELECTION);
            }

            // write a transaction to the database
            try
            {
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
                  (totals.getSubtotal().subtract
                    (totals.getDiscountTotal()).abs());
                till.setTotals(accTotals);
                reg.setTotals(accTotals);

                // Pass the transaction, a null totals object, the till,
                // register, and false indicating that the transaction has
                // not completed its journaling yet.
                utility.saveTransaction(transaction, null, till, reg, false);
            }
            catch (DataException de)
            {
                // an error message is already logged
            }

            // write the hard totals
            try
            {
                utility.writeHardTotals(bus);
            }
            catch (Exception e)
            {
                logger.error( "Unable to save hard totals.");
                logger.error( "" + e + "");
            }

            // print journal
            JournalManagerIfc journal =
                (JournalManagerIfc)Gateway.getDispatcher().getManager(JournalManagerIfc.TYPE);
            JournalFormatterManagerIfc formatter =
                (JournalFormatterManagerIfc)Gateway.getDispatcher().getManager(JournalFormatterManagerIfc.TYPE);

            if (journal != null && formatter != null)
            {
                String cancelJournal = formatter.journalCanceledTransaction(transaction);
                String transactionID = transaction.getTransactionID();
                // Adding transaction tax total for cancel journal
                journal.journal(afCargo.getOperator().getLoginID(),
                                transactionID,
                                cancelJournal);
                if (logger.isInfoEnabled())
                {
                    logger.info("Transaction " + transactionID + " Canceled");
                }
                utility.completeTransactionJournaling(transaction);
            }
            else
            {
                logger.error( "No JournalManager found");
            }
        }

        //clear line display device
        try
        {
            POSDeviceActions pda = new POSDeviceActions((SessionBusIfc) bus);
            pda.clearText();
        }
        catch (DeviceException e)
        {
            logger.warn(
                        "Unable to use Line Display: " + e.getMessage() + "");
        }

        /*
         * Send a Timeout letter
         */
        bus.mail(new Letter(CommonLetterIfc.TIMEOUT), BusIfc.CURRENT);

    }


}
