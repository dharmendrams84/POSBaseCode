/* ===========================================================================
* Copyright (c) 2008, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifytransaction/retrieve/LookupTransactionSite.java /rgbustores_13.4x_generic_branch/4 2012/01/31 16:08:55 mjwallac Exp $
 * ===========================================================================
 * NOTES <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    mjwall 01/31/12 - incorporate review comments.
 *    mjwall 01/27/12 - Forward port: SQL Exception when trying to save a
 *                      resumed order transaction that had been linked to a
 *                      customer, but customer was deleted before resuming.
 *    sgu    09/08/11 - add house account as a refund tender
 *    mchell 08/12/11 - BUG#11854626 Customer Information not send to RM for
 *                      retrieved transactions
 *    cgreen 11/10/10 - cleanup
 *    asinto 09/01/10 - Fixed journaling format of tax modification when
 *                      transaction is retrieved.
 *    acadar 08/23/10 - show external order number in the EJ for retrieved
 *                      transaction
 *    cgreen 05/26/10 - convert to oracle packaging
 *    cgreen 04/28/10 - updating deprecated names
 *    jswan  02/25/10 - Fixed issue where the original transaction for a
 *                      retrieved return transaction has been voided.
 *    blarse 02/10/10 - Setting the retrieved and copied transaction post
 *                      processing flag to unprocesses so it will be processed
 *                      next time. This is a problem when the suspended
 *                      transaction is processed before it is retrived. The new
 *                      tranasction was copying the flag value from the
 *                      processed trans.
 *    blarse 02/09/10 - XbranchMerge shagoyal_bug-8418935 from
 *                      rgbustores_13.0x_branch
 *    abonda 01/03/10 - update header date
 *    vcheng 02/16/09 - Removed multiple occurrances of the string Link
 *                      Customer and retained only one for EJournalling
 *    mchell 01/08/09 - Merge checkin
 *    mchell 01/07/09 - Modified setNewTransaction to reset timestamps for
 *                      retrieved transactions
 *    deghos 01/07/09 - EJ i18n defect fixes
 *    deghos 12/02/08 - EJ i18n changes
 *    ranojh 11/04/08 - Code refreshed to tip
 *    acadar 11/03/08 - localization of transaction tax reason codes
 *    acadar 11/03/08 - localization of reason codes for discounts and merging
 *                      to tip
 *    acadar 11/02/08 - updated as per code review
 *
 * ===========================================================================
 * $Log:
 *  15   360Commerce 1.14        4/14/2008 6:22:03 PM   Deepti Sharma   CR
 *       30936: Changes for EJ and code clean up. Reviewed by Alan Sinton
 *  14   360Commerce 1.13        4/4/2008 10:45:10 AM   Christian Greene 31178
 *       setInitialTransactionBusinessDate(date) to the actual
 *       orderTransaction.getBusinessDay() instead of a new EYSDate instance
 *       so that data purge can find the orders properly.
 *  13   360Commerce 1.12        3/26/2008 11:49:37 PM  Vikram Gopinath
 *       Reverting changes.
 *  12   360Commerce 1.11        3/25/2008 5:50:49 AM   Vikram Gopinath CR
 *       #29903, ported changes from v12x.
 *  11   360Commerce 1.10        3/25/2008 3:58:34 AM   Vikram Gopinath CR
 *       #29903, ported changes from v12x
 *  10   360Commerce 1.9         8/22/2007 9:40:22 AM   Rohit Sachdeva  28173:
 *       Tax Exempt Tax Certificate EJ for Retrieved Suspended Transaction
 *  9    360Commerce 1.8         7/16/2007 4:53:42 PM   Alan N. Sinton  CR
 *       27643 Local file EJournal needs to have Sales Associate and Cashire
 *       linked.
 *  8    360Commerce 1.7         7/12/2007 10:55:19 AM  Anda D. Cadar
 *       replaced $ with Amt.
 *  7    360Commerce 1.6         7/10/2007 4:32:27 PM   Alan N. Sinton  CR
 *       27623 - Modified calls to SaleReturnTransaction.journalLineItems() to
 *        use the JournalFormatterManager instead.
 *  6    360Commerce 1.5         5/17/2007 2:49:36 PM   Owen D. Horne
 *       CR#23450 - Merged fix from v8.0.1
 *       *  6    .v8x       1.4.1.0     5/10/2007 11:38:31 AM  Sujay
 *       Purkayastha Fix
 *       *       for Cr 23,450
 *  5    360Commerce 1.4         1/25/2006 4:11:30 PM   Brett J. Larsen merge
 *       7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
 *  4    360Commerce 1.3         1/22/2006 11:45:13 AM  Ron W. Haight   removed
 *        references to com.ibm.math.BigDecimal
 *  3    360Commerce 1.2         3/31/2005 4:28:59 PM   Robert Pearse
 *  2    360Commerce 1.1         3/10/2005 10:23:22 AM  Robert Pearse
 *  1    360Commerce 1.0         2/11/2005 12:12:29 PM  Robert Pearse
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifytransaction.retrieve;

import java.math.BigDecimal;

import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.common.utility.LocaleRequestor;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.TransactionReadDataTransaction;
import oracle.retail.stores.domain.arts.TransactionWriteDataTransaction;
import oracle.retail.stores.domain.discount.DiscountRuleConstantsIfc;
import oracle.retail.stores.domain.discount.ItemDiscountStrategyIfc;
import oracle.retail.stores.domain.discount.TransactionDiscountStrategyIfc;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.ItemPriceIfc;
import oracle.retail.stores.domain.lineitem.ItemTaxIfc;
import oracle.retail.stores.domain.lineitem.PriceAdjustmentLineItemIfc;
import oracle.retail.stores.domain.lineitem.ReturnItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderStatusIfc;
import oracle.retail.stores.domain.returns.ReturnTenderDataElementIfc;
import oracle.retail.stores.domain.stock.AlterationPLUItemIfc;
import oracle.retail.stores.domain.stock.GiftCardPLUItemIfc;
import oracle.retail.stores.domain.stock.ItemKitConstantsIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.domain.tax.TaxIfc;
import oracle.retail.stores.domain.tender.TenderChargeIfc;
import oracle.retail.stores.domain.tender.TenderLineItemIfc;
import oracle.retail.stores.domain.transaction.LayawayTransactionIfc;
import oracle.retail.stores.domain.transaction.OrderTransaction;
import oracle.retail.stores.domain.transaction.OrderTransactionIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionConstantsIfc;
import oracle.retail.stores.domain.transaction.TransactionIDIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionSummaryIfc;
import oracle.retail.stores.domain.transaction.TransactionTaxIfc;
import oracle.retail.stores.domain.transaction.TransactionTotalsIfc;
import oracle.retail.stores.domain.utility.CodeEntryIfc;
import oracle.retail.stores.domain.utility.CodeListIfc;
import oracle.retail.stores.domain.utility.CodeListMapIfc;
import oracle.retail.stores.domain.utility.EYSDate;
import oracle.retail.stores.domain.utility.GiftCardIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.journal.JournalableIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.journal.JournalFormatterManagerIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.alterations.AlterationsUtilities;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;
import oracle.retail.stores.pos.utility.GiftCardUtility;
import oracle.retail.stores.pos.utility.TransactionUtility;
import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.utility.JournalConstantsIfc;

/**
 * Retrieves list of suspended transactions.
 */
public class LookupTransactionSite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = 4440597550704978581L;

    /**
     * site name constant
     */
    public static final String SITENAME = "LookupTransactionSite";

    /**
     * Retrieves list of suspended transactions.
     *
     * @param bus the bus arriving at this site
     */
    @Override
    public void arrive(BusIfc bus)
    {
        // True if link customer still exists when transaction is
        // retrieved, false otherwise 
        boolean linkedCustomerOk = true;
        // flag showing at least one item not eligible for tender
        boolean allItemsOk = true;

        String letterName = CommonLetterIfc.SUCCESS;
        // pull selected summary from cargo
        ModifyTransactionRetrieveCargo cargo =
            (ModifyTransactionRetrieveCargo) bus.getCargo();
        TransactionSummaryIfc selected = cargo.getSelectedSummary();

        // get journal reference
        JournalManagerIfc journal =
            (JournalManagerIfc) Gateway.getDispatcher().getManager(
                JournalManagerIfc.TYPE);
        // get utility manager
        UtilityManagerIfc utility =
            (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        TransactionIfc searchTransaction =
            DomainGateway.getFactory().getTransactionInstance();
        searchTransaction.initialize(
            selected.getTransactionID().getTransactionIDString());
        if (searchTransaction.getBusinessDay() == null)
        {
        searchTransaction.setBusinessDay(selected.getBusinessDate());
        }
        //Set searchTrans trainingMode according to the current trainingMode
        // so suspended training
        //trans can only be retrieved in trainingMode and vice versa
        boolean trainingModeOn =
            cargo.getRegister().getWorkstation().isTrainingMode();
        searchTransaction.setTrainingMode(trainingModeOn);
        searchTransaction.setTransactionStatus(TransactionIfc.STATUS_SUSPENDED);
        searchTransaction.setLocaleRequestor(utility.getRequestLocales());

        RetailTransactionIfc retrieveTransaction = null;

        // Read the transaction from persistent storage
        try
        {
            // read transaction from database
            TransactionReadDataTransaction readTransaction = null;

            readTransaction = (TransactionReadDataTransaction) DataTransactionFactory.create(DataTransactionKeys.TRANSACTION_READ_DATA_TRANSACTION);

            retrieveTransaction =
                (RetailTransactionIfc) readTransaction.readTransaction(
                    searchTransaction);
            //If this is an order transaction and the linked customer 
            //no longer exists, 
            if (retrieveTransaction instanceof OrderTransaction && 
                retrieveTransaction.getCustomerId() != null && 
                retrieveTransaction.getCustomer() == null)
            { 
                linkedCustomerOk = false;
            }

            // this string buffer stores original transaction information for
            // journal
            StringBuilder retrievedTransactionJournal = new StringBuilder();

            // cancel existing transaction
            cancelSuspendedTransaction(retrieveTransaction, cargo, journal, retrievedTransactionJournal);
            
            // customer linked to suspended transaction still exists
            if(linkedCustomerOk == true)
            {
                // get original transaction for each returned lineitem
                getOriginalTransactions(retrieveTransaction, cargo, utility.getRequestLocales());
    
                // update the Original Transaction line items with the number of
                // items returned
                allItemsOk = setQtyReturned(retrieveTransaction, cargo, bus.getServiceName());
    
                // if an item is ineligible for tendering
                if (allItemsOk == false)
                { // Begin at least one item can't be tendered
                    // display error message, original suspended transaction may
                    // have been modified
                    //therefore suspended transaction is not retrievable
                    displayIneligibleItemError(bus);
    
                } // End at least one item can't be tendered
                else
                { // Begin all items ok to be tendered
                    ParameterManagerIfc pmManager =
                        (ParameterManagerIfc) bus.getManager(
                            ParameterManagerIfc.TYPE);
    
                    // process Gift Card line items
                    processGiftCardLineItems(retrieveTransaction, cargo, pmManager, bus.getServiceName());
                    processPriceAdjustmentLineItems(retrieveTransaction, cargo);
    
                    // set attributes on newly retrieved transaction
                    setNewTransaction(retrieveTransaction, cargo, journal, utility);
    
                    // if postprocessing occurs between suspend and retrieve, the status must be reset
                    retrieveTransaction.setPostProcessingStatus(TransactionConstantsIfc.POST_PROCESSING_STATUS_UNPROCESSED);
    
                    // set transaction in cargo
                    cargo.setTransaction(retrieveTransaction);
    
                    // journal new transaction
                    journalNewTransaction(retrieveTransaction, cargo, journal, retrievedTransactionJournal, bus
                            .getServiceName());
    
                    // write hard totals
                    utility.writeHardTotals(bus);
    
                } // End all items ok to be tendered
            }
        
        }
        catch (DataException e)
        {

            if (e.getErrorCode() == DataException.NO_DATA)
            {
                letterName = CommonLetterIfc.NOT_FOUND;
            }
            else
            {
                cargo.setDataExceptionErrorCode(e.getErrorCode());
                letterName = CommonLetterIfc.DB_ERROR;
            }
        }
        catch (DeviceException e)
        {
            letterName = CommonLetterIfc.HARD_TOTALS_ERROR;
        }

        // mail a letter unless we are displaying an error message
        if (linkedCustomerOk == false)
        {
            // customer linked to suspended transaction was not found
            bus.mail(new Letter(CommonLetterIfc.LINKED_CUSTOMER_NOT_FOUND), BusIfc.CURRENT);
        }
        else if (allItemsOk == true)
        {
            bus.mail(new Letter(letterName), BusIfc.CURRENT);
        }
    } // end arrive()

    /**
     * Cancel the suspended transaction.
     *
     * @param retrieveTransaction retrieved transaction
     * @param cargo cargo class
     * @param journal JournalManagerIfc reference
     * @param journalString retrieved transaction journal fragment
     * @exception DataException thrown if error occurs writing canceled
     *                transaction to database
     */
    public void cancelSuspendedTransaction(RetailTransactionIfc retrieveTransaction,
            ModifyTransactionRetrieveCargo cargo, JournalManagerIfc journal, StringBuilder journalString)
            throws DataException
    {
        // update financials
        RegisterIfc register = cargo.getRegister();
        register.addNumberCancelledTransactions(1);
        TransactionTotalsIfc totals = retrieveTransaction.getTransactionTotals();
        register.addAmountCancelledTransactions(
            totals.getSubtotal().subtract(totals.getDiscountTotal()).abs());

        // set search transaction status to canceled and update
        retrieveTransaction.setTransactionStatus(
            TransactionIfc.STATUS_SUSPENDED_RETRIEVED);

        TransactionWriteDataTransaction writeTransaction = null;

        writeTransaction = (TransactionWriteDataTransaction) DataTransactionFactory.create(DataTransactionKeys.TRANSACTION_WRITE_DATA_TRANSACTION);

        writeTransaction.updateTransactionStatus(retrieveTransaction);

        // note: there is no journal of cancellation of suspended
        // transaction, because we are journaling the retrieval
        // of the transaction right here.
        // build journal string
        journalString
            .append(Util.EOL)
            .append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.TRANS_RETRIEVED_LABEL, null))
            .append(Util.EOL);
        Object[] dataArgs = new Object[2];
        dataArgs[0] = retrieveTransaction.getTransactionID();
        journalString.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ORIGINAL_TRANS_LABEL, dataArgs))
            .append(Util.EOL);
        dataArgs[0] = retrieveTransaction.getTillID();
        journalString.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ORIGINAL_TILL_LABEL, dataArgs))
            .append(Util.EOL);
        dataArgs[0] = retrieveTransaction.getCashier().getEmployeeID();
        journalString.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ORIGINAL_CASHIER_LABEL, dataArgs))
            .append(Util.EOL);
        dataArgs[0] = retrieveTransaction.getSalesAssociate().getEmployeeID();
        journalString.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ORIGINAL_SALES_LABEL, dataArgs));
    } // end cancelSuspendedTransaction()

    /**
     * Sets attributes on newly retrieved transaction based on cargo.
     *
     * @param transaction newly retrieved transaction
     * @param cargo Cargo class
     * @param journal JournalManagerIfc reference
     * @param utility UtilityManagerIfc reference
     */
    protected void setNewTransaction(RetailTransactionIfc transaction, ModifyTransactionRetrieveCargo cargo,
            JournalManagerIfc journal, UtilityManagerIfc utility)
    {
        // set workstation ID, business date, status, etc.
        transaction.setWorkstation(cargo.getRegister().getWorkstation());
        transaction.setTillID(cargo.getRegister().getCurrentTillID());
        transaction.setBusinessDay(cargo.getStoreStatus().getBusinessDate());
        transaction.setTrainingMode(
            cargo.getRegister().getWorkstation().isTrainingMode());
        transaction.setTransactionStatus(TransactionIfc.STATUS_IN_PROGRESS);

        // set cashier on new transaction
        transaction.setCashier(cargo.getOperator());
        // set the TenderLimits object for transaction level checking
        transaction.setTenderLimits(cargo.getTenderLimits());
        // burn new transaction number
        transaction.setTransactionSequenceNumber(
            cargo.getRegister().getNextTransactionSequenceNumber());
        transaction.buildTransactionID();

        // reset the timestamps, otherwise the new transaction will use the old transactions timestamps
        transaction.setTimestampBegin();
        transaction.setTimestampEnd(null);

        switch (transaction.getTransactionType())
        {
            case TransactionIfc.TYPE_LAYAWAY_INITIATE :
                LayawayTransactionIfc layawayTransaction =
                    (LayawayTransactionIfc) transaction;
                // set ID of initial transaction on layaway
                layawayTransaction.getLayaway().setInitialTransactionID(
                    transaction.getTransactionIdentifier());
                // set layaway ID to original value from suspended layaway
                layawayTransaction.getLayaway().setLayawayID(
                    ((LayawayTransactionIfc) transaction)
                        .getLayaway()
                        .getLayawayID());
                break;
            case TransactionIfc.TYPE_ORDER_INITIATE :
                // set order ID to next unique id due to db issues
                OrderTransactionIfc orderTransaction =
                    (OrderTransactionIfc) transaction;
                orderTransaction.setOrderID(
                    cargo.getRegister().getNextUniqueID());
                orderTransaction.setUniqueID(
                    cargo.getRegister().getCurrentUniqueID());

                // update the order status object for the order transaction
                OrderStatusIfc orderStatus = orderTransaction.getOrderStatus();
                orderStatus.setTimestampBegin();
                orderStatus.setTimestampCreated();
                orderStatus.setInitialTransactionBusinessDate(
                    orderTransaction.getBusinessDay());
                orderStatus.setInitialTransactionID(
                    orderTransaction.getTransactionIdentifier());
                orderStatus.setRecordingTransactionID(
                    orderTransaction.getTransactionIdentifier());
                orderStatus.setRecordingTransactionBusinessDate(
                    orderTransaction.getBusinessDay());
                break;
            default :
                break;
        }

        String sequenceNo = journal.getSequenceNumber();
        if (sequenceNo != null && sequenceNo != "")
        {
            utility.indexTransactionInJournal(sequenceNo);
        }

        // set the sequence number in the journal
        journal.setSequenceNumber(transaction.getTransactionID());
        journal.setCashierID(cargo.getOperator().getLoginID());
        journal.setSalesAssociateID(cargo.getSalesAssociate().getLoginID());

    } // end setNewTransaction()

    /**
     * Reads original transaction for each returned lineitem
     *
     * @param transaction newly retrieved transaction
     * @param cargo Cargo class
     */
    protected void getOriginalTransactions(RetailTransactionIfc transaction, ModifyTransactionRetrieveCargo cargo,
            LocaleRequestor localeReq) throws DataException
    {
        AbstractTransactionLineItemIfc[] lineitems;
        // lineitems on retrieved transaction
        SaleReturnLineItemIfc item; // lineitem being processed
        ReturnItemIfc returnItem; // return item associated with lineitem
        TransactionIfc originalTransaction;
        // original trans associated with a returned item

        boolean transAlreadyRead;
        // indicates if an original trans has already been found

        int i; // counter through array of lineitems

        // get lineitems on retrieved transaction
        lineitems = transaction.getLineItems();

        // if there are any lineitems on the retrieved transaction
        if (lineitems != null)
        { // Begin transaction contains lineitems

            // for each lineitem, if the lineitem is a return then get the
            // original transaction
            for (i = 0; i < lineitems.length; i++)
            { // Begin get original transactions

                // if the lineitem is a SaleReturnLineItem
                if (SaleReturnLineItemIfc.class.isInstance(lineitems[i]))
                { // Begin lineitem is a SaleReturnLineItem

                    // get a specific lineitem
                    item = (SaleReturnLineItemIfc) lineitems[i];

                    // get the return item
                    returnItem = item.getReturnItem();

                    // if the return item exists (and was not a no-receipt
                    // entry)
                    if (returnItem != null
                        && returnItem.getOriginalLineNumber() != -1)
                    { // Begin lineitem is a return

                        transAlreadyRead =
                            cargo.isTransactionInList(
                                returnItem.getOriginalTransactionID(),
                                returnItem
                                    .getOriginalTransactionBusinessDate());
                        // if the transaction hasn't already been looked up
                        if (transAlreadyRead == false)
                        { // Begin transaction not yet in list of original
                          // trans

                            originalTransaction =
                                getOriginalTrans(
                                    returnItem.getOriginalTransactionID(),
                                    returnItem
                                        .getOriginalTransactionBusinessDate(),
                                    transaction.isTrainingMode(),
                                    localeReq);

                            // if the transaction is a
                            // SaleReturnTransactionIfc, as it should be.
                            // if not, no big problem, the returned items won't
                            // get updated but
                            // since it's not a srt there aren't returned items
                            // anyway
                            if (SaleReturnTransactionIfc
                                .class
                                .isInstance(originalTransaction))
                            { // Begin add transaction to the list

                                cargo.addOrignalReturnTransaction(
                                    (SaleReturnTransactionIfc) originalTransaction);

                            } // End add transaction to the list

                        } // End transaction not yet in list of original trans

                    } // End lineitem is a return

                } // End lineitem is a SaleReturnLineItem

            } // End get original transactions

        } // End transaction contains lineitems

    } // end getOriginalTransactions()

    /**
     * Get original transaction associated with a returned item.
     * <P>
     * If transaction can't be found, it is assumed the return was entered
     * without a receipt.
     *
     * @param transID original transaction's ID
     * @param businessDay original transaction business day
     * @param trainingModeOn the training mode qualifier value to retrieve the
     *            originalTransaction
     */
    protected TransactionIfc getOriginalTrans(TransactionIDIfc transID, EYSDate businessDay, boolean trainingModeOn,
            LocaleRequestor localeReq) throws DataException
    {
        // returned original transaction
        // return null if error occurs
        TransactionIfc originalTransaction = null;
        // instantiate a transaction with search parameters
        TransactionIfc searchTransaction =
            DomainGateway.getFactory().getTransactionInstance();
        searchTransaction.initialize(transID);
        searchTransaction.setBusinessDay(businessDay);
        searchTransaction.setTrainingMode(trainingModeOn);
        searchTransaction.setLocaleRequestor(localeReq);

        // Read the transaction from persistent storage
        TransactionReadDataTransaction readTransaction = null;

        readTransaction = (TransactionReadDataTransaction) DataTransactionFactory.create(DataTransactionKeys.TRANSACTION_READ_DATA_TRANSACTION);

        originalTransaction =
            readTransaction.readTransaction(searchTransaction);

        return originalTransaction; // return the original transaction
    } // end getOriginalTrans()

    /**
     * For each Gift Card line item, restore the expiration date and the date
     * sold.
     *
     * @param transaction newly retrieved transaction
     * @param cargo ModifyTransactionRetrieveCargo class
     * @param serviceName service name used in log
     */
    protected void processGiftCardLineItems(RetailTransactionIfc transaction, ModifyTransactionRetrieveCargo cargo,
            ParameterManagerIfc pmManager, String serviceName)
    {
        AbstractTransactionLineItemIfc[] lineitems;
        // lineitems on retrieved transaction

        // get lineitems on retrieved transaction
        lineitems = transaction.getLineItems();

        // if there are any lineitems on the retrieved transaction
        if (lineitems != null)
        { // Begin transaction contains lineitems
            for (int i = 0; i < lineitems.length; i++)
            {
                SaleReturnLineItemIfc srli = null;
                PLUItemIfc pluItem = null;
                GiftCardIfc giftCard = null;

                srli = (SaleReturnLineItemIfc) lineitems[i];
                pluItem = srli.getPLUItem();
                if ((pluItem != null))
                {
                    if (pluItem instanceof GiftCardPLUItemIfc)
                    {
                        giftCard = ((GiftCardPLUItemIfc) pluItem).getGiftCard();

                        // set date sold for the gift card
                        EYSDate eysDate =
                            DomainGateway.getFactory().getEYSDateInstance();
                        giftCard.setDateSold(eysDate);

                        // set the expiration date
                        if (giftCard.getExpirationDate() == null)
                        {
                            EYSDate expirationDate =
                                GiftCardUtility.computeExpirationDate(
                                    eysDate,
                                    pmManager,
                                    serviceName);
                            giftCard.setExpirationDate(expirationDate);
                        }
                    }
                }
            }
        } // End transaction contains lineitems
    }

    /**
     * For each item, set the quantity returned in the original transaction
     * object. This is necessary to enable the original lineitem's return
     * quantity to be updated when the retrieved transaction is tendered.
     *
     * Also, check if there are any items on the retrieved transaction that
     * would make it inelgible to tender.
     *
     * At this point, the only type of item that would render the transaction
     * ineligible would be a return item that has already been returned. This
     * would occur if a return is suspended, then on another transaction the
     * item is returned. The suspended transaction, when retrieved, should not
     * be permitted to be completed.
     *
     * This method could be broken up into two - one to set the quantities and
     * one to check for ineligible items. These two functions were coupled due
     * to the complexity and error-prone code required to find an item in the
     * original transaction. Since that must be done for both functions, it was
     * decided to combine both into this one method.
     *
     *
     * @param transaction
     *            newly retrieved transaction
     * @param cargo
     *            ModifyTransactionRetrieveCargo class
     * @param serviceName
     *            service name used in log
     * @return boolean true if all items are eligible, false otherwise
     */
    protected boolean setQtyReturned(
        RetailTransactionIfc transaction,
        ModifyTransactionRetrieveCargo cargo,
        String serviceName)
    {
        // loop through items on retrieved transaction
        // for each SaleReturnLineItem that is a return, add to the original
        // item's qtyRtrned

        AbstractTransactionLineItemIfc[] lineitems;
        // lineitems on retrieved transaction
        SaleReturnLineItemIfc item; // lineitem being processed
        ReturnItemIfc returnItem; // return item associated with lineitem
        SaleReturnTransactionIfc originalTransaction;
        // original trans associated with a returned item
        SaleReturnLineItemIfc originalItem; // lineitem in original transaction
        BigDecimal oldQuantity; // qty returned in original transaction line
        BigDecimal addQuantity; // amount returned in the retrieved transaction
        BigDecimal numberReturnable; // quantity returnable on the lineitem

        int i; // counter through array of lineitems

        boolean allItemsOk = true;
        // flag cleared if a return item has insufficient qty

        // get lineitems on retrieved transaction
        lineitems = transaction.getLineItems();

        // if there are any lineitems on the retrieved transaction
        if (lineitems != null)
        { // Begin transaction contains lineitems

            // for each lineitem, if the lineitem is a return then update the
            // original transaction with the returned amount
            for (i = 0; i < lineitems.length; i++)
            { // Begin get original transactions

                // if the lineitem is a SaleReturnLineItem
                if (SaleReturnLineItemIfc.class.isInstance(lineitems[i]))
                { // Begin lineitem is a SaleReturnLineItem
                    // get a specific lineitem
                    item = (SaleReturnLineItemIfc) lineitems[i];
                    // get the associated return item
                    returnItem = item.getReturnItem();

                    // if the return item exists (and was not a no-receipt
                    // entry)
                    if (returnItem != null
                        && returnItem.getOriginalLineNumber() != -1)
                    { // Begin lineitem is a return

                        // get the original transaction based on the original
                        // transaction ID
                        // and original business date found in the returnItem
                        // object
                        originalTransaction =
                            findOrigTransByID(
                                cargo,
                                returnItem.getOriginalTransactionID(),
                                returnItem
                                    .getOriginalTransactionBusinessDate());

                        if (originalTransaction.getTransactionStatus() ==
                            TransactionConstantsIfc.STATUS_VOIDED)
                        {
                            allItemsOk = false;
                            // not a valid transaction anymore
                            i = lineitems.length; // exit the loop

                            logger.warn("The original transaction for a return " +
                                    "item has been voided; the retrieved return " +
                                    "transaction is no longer valid.");
                        }
                        else
                        {
                            // get the original item from the original transaction
                            // using the
                            // "original line number" found in the returnItem
                            // object
                            originalItem =
                                (SaleReturnLineItemIfc)
                                    (originalTransaction)
                                    .getLineItems()[returnItem
                                    .getOriginalLineNumber()];
                            // calculate qty still available to return
                            numberReturnable =
                                originalItem.getItemQuantityDecimal().subtract(
                                    originalItem.getQuantityReturnedDecimal());

                            // compare number returnable to qty on the retrieved
                            // transaction
                            if (numberReturnable
                                .compareTo(item.getItemQuantityDecimal().abs())
                                < 0)
                            { // Begin no quantity available to return
                                allItemsOk = false;
                                // not a valid transaction anymore
                                i = lineitems.length; // exit the loop

                                logger.warn(
                                    "LookupTransactionSite: setQtyReturned(): NOT ok to return\nnumberReturnable = "
                                        + numberReturnable
                                        + "\nnumber to return = "
                                        + item.getItemQuantityDecimal().abs()
                                        + "");

                            } // End no quantity available to return
                            else
                            { // Begin there is enough qty for a valid return

                                // add the quantity of the retrieved lineitem
                                oldQuantity =
                                    originalItem.getQuantityReturnedDecimal();
                                addQuantity = item.getItemQuantityDecimal().abs();
                                originalItem.setQuantityReturned(
                                    oldQuantity.add(addQuantity));

                            } // End there is enough qty for a valid return
                        } // end the orignial transaction has not been voided
                    } // End lineitem is a return
                } // End lineitem is a SaleReturnLineItem
            } // End get original transactions
        } // End transaction contains lineitems

        return allItemsOk; // return the status code
    } // end setQtyReturned()

    /**
     * Find transaction in the list.
     *
     * @param cargo ModifyTransactionRetrieveCargo class
     * @param transID original transaction's ID
     * @param businessDay original transaction business day
     */
    protected SaleReturnTransactionIfc findOrigTransByID(ModifyTransactionRetrieveCargo cargo,
            TransactionIDIfc transID, EYSDate businessDay)
    {
        SaleReturnTransactionIfc srt; // transaction in the list
        SaleReturnTransactionIfc[] list; // list of original transactions
        SaleReturnTransactionIfc originalTransaction;
        // returned original transaction
        int i; // index into list of original transactions

        originalTransaction = null; // initialize the original transaction

        list = cargo.getOriginalReturnTransactions();
        // get the list of original transactions

        // if the list has been instantiated
        if (list != null)
        { // Begin transaction list exists
            for (i = 0; i < list.length; i++)
            { // Begin search for a matching transaction
                srt = list[i]; // get the current SaleReturnTransaction

                if (Util.isObjectEqual(transID, srt.getTransactionIdentifier())
                    && Util.isObjectEqual(businessDay, srt.getBusinessDay()))
                { // Begin found a match
                    originalTransaction = srt; // set the returned transaction
                    i = list.length; // don't need to search anymore
                } // End found a match
            } // End search for a matching transaction
        } // End transaction list exists

        return originalTransaction; // return the original transaction
    } // end findOrigTransByID

    /**
     * Display an error message showing that at least one item is ineligible
     * for tender. Original suspended transaction may have been modified
     *
     * @param bus the bus arriving at this site
     */
    protected void displayIneligibleItemError(BusIfc bus)
    {
        // get cargo
        ModifyTransactionRetrieveCargo cargo =
            (ModifyTransactionRetrieveCargo) bus.getCargo();
        // Get the ui manager
        POSUIManagerIfc ui =
            (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        // Use the "generic dialog bean".
        DialogBeanModel model = new DialogBeanModel();
        model.setResourceID("InvalidModifiedTransaction");
        if (!(cargo.isVisitedSuspendedListSite()))
        {
            model.setButtonLetter(0, CommonLetterIfc.FAILURE);
        }
        model.setType(DialogScreensIfc.ERROR);

        // set and display the model
        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, model);
    }

    /**
     * Journals newly retrieved transaction.
     *
     * @param transaction newly retrieved transaction
     * @param cargo ModifyTransactionRetrieveCargo class
     * @param journal JournalManagerIfc reference
     * @param journalString journal fragment based on original transaction
     * @param serviceName service name used in log
     */
    protected void journalNewTransaction(RetailTransactionIfc transaction, ModifyTransactionRetrieveCargo cargo,
            JournalManagerIfc journal, StringBuilder journalString, String serviceName)
    {
        JournalFormatterManagerIfc formatter =
            (JournalFormatterManagerIfc)Gateway.getDispatcher().getManager(JournalFormatterManagerIfc.TYPE);
        // cast transaction to get details
        SaleReturnTransactionIfc srt = (SaleReturnTransactionIfc) transaction;


       if (journal != null)
        {
            journal.setEntryType(JournalableIfc.ENTRY_TYPE_START);
            journal.journal(
                cargo.getOperator().getLoginID(),
                transaction.getTransactionID(),
                srt.journalHeader(LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL)));
            journal.setEntryType(JournalableIfc.ENTRY_TYPE_TRANS);
            journal.journal(
                cargo.getOperator().getLoginID(),
                transaction.getTransactionID(),
                journalString.toString());
            //journal the order number
            if (transaction instanceof SaleReturnTransactionIfc && ((SaleReturnTransactionIfc)transaction).hasExternalOrder())
            {
                TransactionUtility.journalExternalOrder(((SaleReturnTransactionIfc)transaction).getExternalOrderNumber());
            }

            journal.journal(
                cargo.getOperator().getLoginID(),
                transaction.getTransactionID(),
                journalTransactionModifiers(transaction, cargo));

            journal.journal(
                cargo.getOperator().getLoginID(),
                transaction.getTransactionID(),
                formatter.journalLineItems(srt));
        }
        else
        {
            logger.error("No JournalManager found");
        }
    } // end journalNewTransaction()

    /**
     * Set the Reason Codes for the Discounts of given Transaction;
     *
     * @return void
     * @deprecated as of 13.1. No longer needed, transaction already has the reason codes
     */
   protected void setTransactionReasonCodeText(
        SaleReturnTransactionIfc transaction,
        CodeListMapIfc codeMap)
    {
        // iterate TransactionDiscounts
        TransactionDiscountStrategyIfc[] tdiscounts =
            transaction.getTransactionDiscounts();
        if (tdiscounts != null)
        {
            for (int i = 0; i < tdiscounts.length; i++)
            {
                setTransactionDiscountReasonCodeText(tdiscounts[i], codeMap);
            }
            transaction.setTransactionDiscounts(tdiscounts);
        }
        // iterate the lineItems and the discounts ReasonCode
        AbstractTransactionLineItemIfc[] lineItems = transaction.getLineItems();
        ItemDiscountStrategyIfc[] idiscounts = null;
        if (lineItems != null)
        {
            for (int i = 0; i < lineItems.length; i++)
            {
                ItemPriceIfc ip =
                    ((SaleReturnLineItemIfc) lineItems[i]).getItemPrice();
                if (ip != null)
                {
                    idiscounts = ip.getItemDiscounts();
                    if (idiscounts != null)
                    {
                        // iterate the discounts and set ReasonCodeText
                        for (int j = 0; j < idiscounts.length; j++)
                        {
                            setItemDiscountReasonCodeText(
                                idiscounts[j],
                                codeMap);
                        }
                        ip.setItemDiscounts(idiscounts);
                    }
                }
            }
        }
    }

    /**
     * Set the Reason Code String for the Tax Items;
     *
     * @return void
     * @deprecated as of 13.1. No callers.
     */
    protected void setSaleReturnLineItemsReasonCodeText(SaleReturnTransactionIfc transaction, CodeListIfc toggleReasonCodes)
    {
        SaleReturnLineItemIfc[] items = transaction.getItemContainerProxy().
                getLineItemsExcluding(ItemKitConstantsIfc.ITEM_KIT_CODE_COMPONENT);

        int code = 0;
        CodeEntryIfc reasonEntry = null;

        if (items != null)
        {
            for (int i = 0; i < items.length; i++)
            {
                ItemPriceIfc ipifc = items[i].getItemPrice();
                if (ipifc != null)
                {
                    ItemTaxIfc itax = ipifc.getItemTax();
                    if (itax != null)
                    {
                        code =  itax.getReasonCode();
                        reasonEntry = toggleReasonCodes.findListEntryByCode(String.valueOf(code));
                        itax.setReasonCodeText(reasonEntry.getText());
                        code = 0;
                        reasonEntry = null;
                    }
                }
            }
        }

    }


    /**
     * Set the reasonCodeText for this TransactionDiscountStrategyIfc;
     *
     * @return void
     * @deprecated as of 13.1. No longer in use.
     */
    protected void setTransactionDiscountReasonCodeText(
        TransactionDiscountStrategyIfc discount,
        CodeListMapIfc codeMap)
    {

        switch (discount.getDiscountMethod())
        {
            case DiscountRuleConstantsIfc.DISCOUNT_METHOD_AMOUNT :
            case DiscountRuleConstantsIfc.DISCOUNT_METHOD_FIXED_PRICE :
                {
                    switch (discount.getAssignmentBasis())
                    {
                        case DiscountRuleConstantsIfc.ASSIGNMENT_CUSTOMER :
                            {
                                discount.setReasonCodeText(
                                    getReasonCodeValue(
                                        codeMap,
                                        CodeListIfc
                                            .CODE_LIST_PREFERRED_CUSTOMER_DISCOUNT,
                                        discount.getReasonCode()));
                                break;
                            }
                        case DiscountRuleConstantsIfc.ASSIGNMENT_MANUAL :
                            {
                                discount.setReasonCodeText(
                                    getReasonCodeValue(
                                        codeMap,
                                        CodeListIfc
                                            .CODE_LIST_TRANSACTION_DISCOUNT_BY_AMOUNT,
                                        discount.getReasonCode()));
                                break;
                            }
                        default :
                            { //unknown, don't touch
                                break;
                            }
                    }
                    break;
                }
            case DiscountRuleConstantsIfc.DISCOUNT_METHOD_PERCENTAGE :
                {
                    switch (discount.getAssignmentBasis())
                    {
                        case DiscountRuleConstantsIfc.ASSIGNMENT_CUSTOMER :
                            {
                                discount.setReasonCodeText(
                                    getReasonCodeValue(
                                        codeMap,
                                        CodeListIfc
                                            .CODE_LIST_PREFERRED_CUSTOMER_DISCOUNT,
                                        discount.getReasonCode()));
                                break;
                            }
                        case DiscountRuleConstantsIfc.ASSIGNMENT_MANUAL :
                            {
                                discount.setReasonCodeText(
                                    getReasonCodeValue(
                                        codeMap,
                                        CodeListIfc
                                            .CODE_LIST_TRANSACTION_DISCOUNT_BY_PERCENTAGE,
                                        discount.getReasonCode()));
                                break;
                            }
                        default :
                            { //unknown, don't touch
                                break;
                            }
                    }
                    break;
                }
            default :
                {
                    // unknown, don't touch
                    break;
                }
        }
    }

    /**
     * Set the reasonCodeText for this ItemDiscountByAmountIfc;
     * @return void
     * @deprecated as of 13.1. No callers.
     */
   protected void setItemDiscountReasonCodeText(
        ItemDiscountStrategyIfc discount,
        CodeListMapIfc codeMap)
    {

        switch (discount.getDiscountMethod())
        {
            case DiscountRuleConstantsIfc.DISCOUNT_METHOD_AMOUNT :
            case DiscountRuleConstantsIfc.DISCOUNT_METHOD_FIXED_PRICE :
                {
                    switch (discount.getAssignmentBasis())
                    {
                        case DiscountRuleConstantsIfc.ASSIGNMENT_CUSTOMER :
                            {
                                discount.setReasonCodeText(
                                    getReasonCodeValue(
                                        codeMap,
                                        CodeListIfc
                                            .CODE_LIST_PREFERRED_CUSTOMER_DISCOUNT,
                                        discount.getReasonCode()));
                                break;
                            }
                        case DiscountRuleConstantsIfc.ASSIGNMENT_MANUAL :
                            {
                                discount.setReasonCodeText(
                                    getReasonCodeValue(
                                        codeMap,
                                        CodeListIfc
                                            .CODE_LIST_ITEM_DISCOUNT_BY_AMOUNT,
                                        discount.getReasonCode()));
                                break;
                            }
                        default :
                            { //unknown, don't touch
                                break;
                            }
                    }
                    break;
                }
            case DiscountRuleConstantsIfc.DISCOUNT_METHOD_PERCENTAGE :
                {
                    switch (discount.getAssignmentBasis())
                    {
                        case DiscountRuleConstantsIfc.ASSIGNMENT_CUSTOMER :
                            {
                                discount.setReasonCodeText(
                                    getReasonCodeValue(
                                        codeMap,
                                        CodeListIfc
                                            .CODE_LIST_PREFERRED_CUSTOMER_DISCOUNT,
                                        discount.getReasonCode()));
                                break;
                            }
                        case DiscountRuleConstantsIfc.ASSIGNMENT_MANUAL :
                            {
                                discount.setReasonCodeText(
                                    getReasonCodeValue(
                                        codeMap,
                                        CodeListIfc
                                            .CODE_LIST_ITEM_DISCOUNT_BY_PERCENTAGE,
                                        discount.getReasonCode()));
                                break;
                            }
                        default :
                            { //unknown, don't touch
                                break;
                            }
                    }
                    break;
                }
            default :
                {
                    // unknown, don't touch
                    break;
                }
        }
    }

    /**
     * Get the Reason Codes from the CodeList;
     *
     * @return null or Code Text
     * @deprecated as of 13.1. No callers.
     */
    protected String getReasonCodeValue( CodeListMapIfc codeMap, String listKey, int code)
    {
        CodeListIfc codeList = null;
        String returnString = null;

        if (codeMap != null)
        {
            codeList = codeMap.get(listKey);
            if (codeList != null)
            {
                String codeStr = Integer.toString(code);
                CodeEntryIfc entry = codeList.findListEntryByCode(codeStr);
                if (entry != null)
                {
                    returnString = entry.getText();
                }
            }
        }
        return returnString;
    }

    /**
     * Creates the journal string for transaction modifications: linked
     * customer, discounts, non-standard tax modifications, and gift registry.
     *
     * @return string to journal
     */
    protected String journalTransactionModifiers(
        RetailTransactionIfc transaction,
        ModifyTransactionRetrieveCargo cargo)
    {
        StringBuilder strResult = new StringBuilder();
        if (transaction.getCustomer() != null)
        {
            strResult.append(Util.EOL);
            Object dataArgs[] = {transaction.getCustomer().getCustomerID().trim()};
            String customer = I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.LINK_CUSTOMER_LABEL, dataArgs);

            strResult.append(customer);

            // journal alteration items
            if (transaction instanceof SaleReturnTransactionIfc)
            {
                SaleReturnTransactionIfc srt =
                    (SaleReturnTransactionIfc) transaction;
                if (srt.hasAlterationItems())
                {
                    strResult.append(Util.EOL);
                    AbstractTransactionLineItemIfc lineItem[] =
                        transaction.getLineItems();
                    for (int j = 0; j < lineItem.length; j++)
                    {
                        if (lineItem[j] instanceof SaleReturnLineItemIfc)
                        {
                            SaleReturnLineItemIfc srli =
                                (SaleReturnLineItemIfc) lineItem[j];
                            if (srli.isAlterationItem())
                            {
                                AlterationPLUItemIfc alterationItem =
                                    (AlterationPLUItemIfc) srli.getPLUItem();
                                strResult.append(
                                    AlterationsUtilities.journalAlterationItem(
                                        alterationItem));
                            }
                        }
                    }
                }
            }

        }

        // journal discounts
        TransactionDiscountStrategyIfc[] discounts =
            transaction.getTransactionDiscounts();
        if (discounts != null && discounts.length > 0)
        {
            for (int i = 0; i < discounts.length; i++)
            {
                strResult.append(discounts[i].toJournalString(LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL)));
            }
        }

        // journal tax modifications
        TransactionTaxIfc tax = transaction.getTransactionTax();
        if (tax.getTaxMode() != TaxIfc.TAX_MODE_STANDARD)
        {
            Object dataArgs[] = {""};
            String reasonCode = I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.REASON_CODE_LABEL, dataArgs);

            StringBuilder message = new StringBuilder();
            String reasonText = "";
            switch (tax.getTaxMode())
            {
                case TaxIfc.TAX_MODE_EXEMPT :
                    {
                        String customer = "";
                        if (transaction.getCustomer() != null)
                        {

                            Object customerDataArgs[] = {transaction.getCustomer().getCustomerID()};
                            customer = I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.CUSTOMER_ID, customerDataArgs);

                        }
                        // To be Modified
                        reasonText = tax.getReason().getText(LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL));

                        // Need to be modified

                        Object taxDataArgs[] = {tax.getTaxExemptCertificateID()};
                        String taxCertificate = I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.TRANSACTION_TAX_CERTIFICATE_LABEL, taxDataArgs);


                        message.append("\n")
                            .append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.TRANSACTION_TAX_EXEMPT, null))
                            .append(customer)
                            .append("\n")
                            .append(taxCertificate)
                            .append("\n")
                            .append(reasonCode)
                            .append(reasonText);

                        break;
                    }
                case TaxIfc.TAX_MODE_OVERRIDE_AMOUNT :
                    {

                        reasonText = tax.getReason().getText(LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL));
                        message.append("\n")
                            .append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.TRANSACTION_TAX_OVERRIDE, null))
                            .append("\n")
                            .append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.TRANSACTION_TAX_OVERRIDE_AMT, null))
                            .append(tax.getOverrideAmount())
                            .append("\n")
                            .append(reasonCode)
                            .append(reasonText);

                        break;
                    }
                case TaxIfc.TAX_MODE_OVERRIDE_RATE :
                    {

                        reasonText = tax.getReason().getText(LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL));

                        message.append("\n")
                            .append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.TRANSACTION_TAX_OVERRIDE, null))
                            .append("\n")
                            .append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.TRANSACTION_TAX_OVERRIDE_PERC, null))
                            .append(String.valueOf(tax.getOverrideRate() * 100))
                            .append("\n")
                            .append(reasonCode)
                            .append(reasonText);

                        break;
                    }
            } // switch tax mode

            strResult.append(message.toString());
        } // standard

        // journal gift registry
        if (transaction instanceof SaleReturnTransactionIfc
            && ((SaleReturnTransactionIfc) transaction).getDefaultRegistry()
                != null)
        {
            strResult
                .append(Util.EOL)
                .append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.TRANSACTION_GIFT_REG, null))
                .append(Util.EOL)
                .append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.GIFT_REG, null))
                .append(((SaleReturnTransactionIfc) transaction).getDefaultRegistry().getID());
        }

        return (strResult.toString());
    } // journalTransactionModifiers

    /**
     * For Price Adjustment line items
     *
     * @param transaction
     *            newly retrieved transaction
     */
    protected void processPriceAdjustmentLineItems(RetailTransactionIfc transaction, ModifyTransactionRetrieveCargo cargo)
    {
        AbstractTransactionLineItemIfc[] lineitems;
        // lineitems on retrieved transaction
        SaleReturnLineItemIfc item; // lineitem being processed
        ReturnItemIfc returnItem; // return item associated with lineitem
        SaleReturnTransactionIfc originalTransaction;
        // original trans associated with a returned item
        SaleReturnLineItemIfc originalLineItem; // lineitem in original transaction
        int i; // counter through array of lineitems
        // get lineitems on retrieved transaction
        lineitems = transaction.getLineItems();
        // if there are any lineitems on the retrieved transaction
        if (lineitems != null)
        { // Begin transaction contains lineitems
            // for each lineitem, if the lineitem is a return then update the
            // original transaction with the returned amount
            for (i = 0; i < lineitems.length; i++)
            { // Begin get original transactions
                // if the lineitem is a SaleReturnLineItem
                if (SaleReturnLineItemIfc.class.isInstance(lineitems[i]))
                { // Begin lineitem is a SaleReturnLineItem
                    // get a specific lineitem
                    item = (SaleReturnLineItemIfc) lineitems[i];
                    // get the associated return item
                    returnItem = item.getReturnItem();
                    // if the return item exists (and was not a no-receipt
                    // entry)
                    if (returnItem != null
                        && returnItem.getOriginalLineNumber() != -1)
                    { // Begin lineitem is a return
                        // get the original transaction based on the original
                        // transaction ID
                        // and original business date found in the returnItem
                        // object
                        originalTransaction =
                            findOrigTransByID(
                                cargo,
                                returnItem.getOriginalTransactionID(),
                                returnItem
                                    .getOriginalTransactionBusinessDate());
                        // get the original item from the original transaction
                        // using the
                        // "original line number" found in the returnItem
                        // object
                        originalLineItem =
                            (SaleReturnLineItemIfc)
                                (originalTransaction)
                                .getLineItems()[returnItem
                                .getOriginalLineNumber()];
                        //Check item for PriceAdjustment
                        if (SaleReturnTransactionIfc.class.isInstance(transaction) && item.isPartOfPriceAdjustment())
                        {
                            SaleReturnTransactionIfc currentTransaction = (SaleReturnTransactionIfc)transaction;
                            SaleReturnLineItemIfc currentLineItem = (SaleReturnLineItemIfc)(transaction).getLineItems()[returnItem.getOriginalLineNumber()];
                            // Save the original line and transaction numbers for use
                            // when updating the
                            // original line item
                            currentLineItem.setOriginalLineNumber(originalLineItem.getLineNumber());
                            currentLineItem.setOriginalTransactionSequenceNumber(originalTransaction.getTransactionSequenceNumber());
                            // Set the sales associate to the current associate
                            currentLineItem.setSalesAssociate(currentTransaction.getSalesAssociate());
                            // The PriceAdjustmentLineItemIfc instance is added for use by the UI and other facilities
                            PriceAdjustmentLineItemIfc priceAdjustmentLineItem = null;
                            priceAdjustmentLineItem = DomainGateway.getFactory().getPriceAdjustmentLineItemInstance();
                            //sale component of price adjustment
                            SaleReturnLineItemIfc saleItem = (SaleReturnLineItemIfc)lineitems[i+1];
                            priceAdjustmentLineItem.initialize(saleItem, item);
                            currentTransaction.addLineItem((AbstractTransactionLineItemIfc)priceAdjustmentLineItem);
                            // get the original tenders used for refund options calculations
                            currentTransaction.setReturnTenderElements(getOriginalTenders(originalTransaction.getTenderLineItems()));
                        } // End of PriceAdjustmentLineItemIfc instance is added
                    } // End there is enough qty for a valid return
                } // End lineitem is a return
            } // End lineitem is a SaleReturnLineItem
        } // End get original transactions
    } // End transaction contains lineitems

    /**
     * Retrieve tenders from original transaction
     * @param tenderList
     * @return ReturnTenderDataElement[] list of tenders
     */
    protected ReturnTenderDataElementIfc[] getOriginalTenders(TenderLineItemIfc[] tenderList)
    {
        ReturnTenderDataElementIfc [] tenders = new ReturnTenderDataElementIfc[tenderList.length];
        for (int i =0; i < tenderList.length; i++)
        {
            tenders[i]=DomainGateway.getFactory().getReturnTenderDataElementInstance();
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
}