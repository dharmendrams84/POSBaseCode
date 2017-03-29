/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/journal/TransactionJournalFormatterIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:35 mszekely Exp $
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
 *  5    360Commerce 1.4         7/31/2007 9:36:26 AM   Alan N. Sinton  CR
 *       27855 Fixed EJournaling of shipping information.
 *  4    360Commerce 1.3         7/10/2007 4:32:27 PM   Alan N. Sinton  CR
 *       27623 - Modified calls to SaleReturnTransaction.journalLineItems() to
 *        use the JournalFormatterManager instead.
 *  3    360Commerce 1.2         5/21/2007 9:16:19 AM   Anda D. Cadar   EJ
 *       changes
 *  2    360Commerce 1.1         5/14/2007 2:32:57 PM   Alan N. Sinton  CR
 *       26486 - EJournal enhancements for VAT.
 *  1    360Commerce 1.0         5/8/2007 5:23:34 PM    Alan N. Sinton  CR
 *       26486 - Refactor of some EJournal code.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.journal;
//extendyourstore imports
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionTaxIfc;
import oracle.retail.stores.domain.transaction.TransactionTotalsIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;

/**
 * Super interface for all Journal Formatter instances.
 * $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface TransactionJournalFormatterIfc extends JournalFormatterIfc
{
    /**
     * Method to set the handle to the ParameterManagerIfc.
     * 
     * @param pm
     */
    public void setParameterManager(ParameterManagerIfc pm);

    /**
     * Method to be implemented for all Journal Formatter instances.
     *
     * @return
     */
    public String toJournalString();

    /**
     * Method to return the journal footer of the transaction.
     *
     * @return The journal footer of the transaction.
     */
    public String journalFooter();

    /**
     * Method to generate the journal header for the SaleReturnTransaction instance.
     *
     * @return The journal header for the SaleReturnTransaction instance.
     */
    public String journalHeader();

    /**
     * Journals the order for the given OrderIfc.
     *
     * @param order
     * @return The journal string for the given OrderIfc instance.
     * @see oracle.retail.stores.pos.journal.AbstractTransactionJournalFormatter#journalCancelOrder(oracle.retail.stores.domain.order.OrderIfc)
     */
    public String journalOrder(OrderIfc order);

    /**
     *  This method is used to print information related to
     *  the subtotal, tax and total of order
     *  @param order the order item
     *  @param serviceType of the calling service
     *  @param pm Parameter Manager
     *  @return String Is the string to append to the journal
     */
     public String journalOrderTotals(OrderIfc order, int serviceType);

     /**
     * Method to generate the journal transaction totals for the SaleReturnTransaction instance.
     *
     * @return The totals journal for the SaleReturnTransaction instance.
     */
    public String journalTotals();

    /**
     * Journal sub totals for transaction level send. Journalling for Subtotals
     * is being done now since now we have the total shipping charges for
     * transaction level send
     * 
     * @return String journal string
     */
    public String journalSubTotalsForTransactionLevelSend();

    /**
     * Formats the totals for the given SaleReturnTransactionIfc, TransactionTotalsIfc, and TransactionTaxIfc.
     * The totals are added to the given StringBuffer.
     *
     * @param sb
     * @param totals
     * @param tax
     * @see oracle.retail.stores.pos.journal.AbstractTransactionJournalFormatter#formatTotals(java.lang.StringBuffer, oracle.retail.stores.domain.transaction.TransactionTotalsIfc, oracle.retail.stores.domain.transaction.TransactionTaxIfc)
     */
    public void formatTotals(StringBuffer sb, TransactionTotalsIfc totals, TransactionTaxIfc tax);

    /**
     * Sets the transaction.
     * @param transaction The transaction to set.
     */
    public void setTransaction(TransactionIfc transaction);

    /**
     * Journals the canceling of the retrieved suspended transaction.
     *
     * @param transaction
     * @return
     */
    public String journalCanceledSuspendedTransaction();

    /**
     * Journals the canceling of the transaction.
     * 
     * @return The journaling of the canceling of the transaction.
     */
    public String journalCanceledTransaction();

    /**
     * Journals the transaction's line items.
     *
     * @return
     */
    public String journalLineItems();

    /**
     * Journals the shipping info.
     *
     * @param items
     * @return The journal of the shipping info.
     */
    public String journalShippingInfo(SaleReturnLineItemIfc[] items);
}
