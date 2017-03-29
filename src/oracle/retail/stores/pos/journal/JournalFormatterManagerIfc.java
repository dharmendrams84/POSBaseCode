/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/journal/JournalFormatterManagerIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:35 mszekely Exp $
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
 *  7    360Commerce 1.6         8/9/2007 5:23:26 AM    Sujay Beesnalli Added
 *       new Transaction Journal Formatter to support Redeem options.
 *  6    360Commerce 1.5         7/31/2007 9:36:26 AM   Alan N. Sinton  CR
 *       27855 Fixed EJournaling of shipping information.
 *  5    360Commerce 1.4         7/18/2007 10:59:41 AM  Alan N. Sinton  CR
 *       27672 Added the transaction's VAT breakdown in EJournal.
 *  4    360Commerce 1.3         7/18/2007 8:43:35 AM   Alan N. Sinton  CR
 *       27651 - Made Post Void EJournal entries VAT compliant.
 *  3    360Commerce 1.2         7/10/2007 4:32:27 PM   Alan N. Sinton  CR
 *       27623 - Modified calls to SaleReturnTransaction.journalLineItems() to
 *        use the JournalFormatterManager instead.
 *  2    360Commerce 1.1         5/14/2007 2:32:57 PM   Alan N. Sinton  CR
 *       26486 - EJournal enhancements for VAT.
 *  1    360Commerce 1.0         5/8/2007 5:23:34 PM    Alan N. Sinton  CR
 *       26486 - Refactor of some EJournal code.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.journal;

import oracle.retail.stores.domain.discount.ItemDiscountStrategyIfc;
import oracle.retail.stores.domain.lineitem.KitComponentLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.domain.transaction.LayawayTransactionIfc;
import oracle.retail.stores.domain.transaction.OrderTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionTaxIfc;
import oracle.retail.stores.domain.transaction.TransactionTotalsIfc;
import oracle.retail.stores.domain.transaction.VoidTransactionIfc;
import oracle.retail.stores.domain.utility.EYSDate;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.tour.manager.ManagerIfc;

/**
 * Journal Manager for POS.
 * $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface JournalFormatterManagerIfc extends ManagerIfc
{
    /**
     * TYPE for this POSJournalManagerIfc.
     */
    public static final String TYPE = "JournalFormatterManager";
    /**
     * Key for SaleReturnTransactionJournalFormatter.
     */
    public static final String JFORMAT_SALE_RETURN_TRANSACTION_KEY  = "application_SaleReturnTransactionJournalFormatter";
    /**
     * Key for VATSaleReturnTransactionJournalFormatter.
     */
    public static final String JFORMAT_VAT_SALE_RETURN_TRANSACTION_KEY  = "application_VATSaleReturnTransactionJournalFormatter";
    /**
     * Key for SaleReturnLineItemJournalFormatter.
     */
    public static final String JFORMAT_SALE_RETURN_LINE_ITEM_KEY    = "application_SaleReturnLineItemJournalFormatter";
    /**
     * Key for VATSaleReturnLineItemJournalFormatter.
     */
    public static final String JFORMAT_VAT_SALE_RETURN_LINE_ITEM_KEY= "application_VATSaleReturnLineItemJournalFormatter";
    /**
     * Key for loading the TenderFormatter class
     */
    public static final String JFORMAT_TENDER_FORMATTER_KEY         = "application_TenderFormatter";
    /**
     * Key for loading the VATKitHeaderLineItemJournalFormatter class.
     */
    public static final String JFORMAT_VAT_KIT_HEADER_LINE_ITEM_KEY = "application_VATKitHeaderLineItemJournalFormatter";
    /**
     * Key for loading the KitHeaderLineItemJournalFormatter class.
     */
    public static final String JFORMAT_KIT_HEADER_LINE_ITEM_KEY     = "application_KitHeaderLineItemJournalFormatter";
    /**
     * Key for loading the VATKitComponentLineItemJournalFormatter class.
     */
    public static final String JFORMAT_VAT_KIT_COMPONENT_LINE_ITEM_KEY = "application_VATKitComponentLineItemJournalFormatter";
    /**
     * Key for loading the KitComponentLineItemJournalFormatter.
     */
    public static final String JFORMAT_KIT_COMPONENT_LINE_ITEM_KEY = "application_KitComponentLineItemJournalFormatter";
    /**
     * Key for VoidTransactionJournalFormatter.
     */
    public static final String JFORMAT_VOID_TRANSACTION_KEY  = "application_VoidTransactionJournalFormatter";
    /**
     * Key for VATVoidTransactionJournalFormatter.
     */
    public static final String JFORMAT_VAT_VOID_TRANSACTION_KEY  = "application_VATVoidTransactionJournalFormatter";
    
    /**
     * Key for RedeemTransactionJournalFormatter.
     */
    public static final String JFORMAT_REDEEM_TRANSACTION_KEY  = "application_redeemTransactionJournalFormatter";

    /**
     * Executes the toJournalString() method on the appropriate formatter if one is found.
     * Otherwise it calls the toJournalString() on the given TransactionIfc instance.
     *
     * @param transaction
     * @param parameterManager
     * @return The string representing the journal of the given TransactionIfc instance.
     * @see oracle.retail.stores.pos.journal.JournalFormatterManagerIfc#toJournalString(oracle.retail.stores.domain.transaction.TransactionIfc)
     */
    public String toJournalString(TransactionIfc transaction, ParameterManagerIfc parameterManager);

    /**
     * Method to journal the totals for the given SaleReturnTransactionIfc
     * instance.
     *
     * @param transaction
     * @param parameterManager
     * @return The journal of the totals for the given SaleReturnTransactionIfc
     * instance.
     * @see oracle.retail.stores.pos.journal.JournalFormatterManagerIfc#journalTotals(oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc)
     */
    public String journalTotals(SaleReturnTransactionIfc transaction, ParameterManagerIfc parameterManager);
    
    /**
     * Journals the subtotals for transaction level sends for the given SaleReturnTransactionIfc
     * instance.
     *
     * @param transaction
     * @param parameterManager
     * @return The journal for the subtotals for transaction level sends for the given SaleReturnTransactionIfc.
     * @see oracle.retail.stores.pos.journal.JournalFormatterManagerIfc#journalSubTotalsForTransactionLevelSend(oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc)
     */
    public String journalSubTotalsForTransactionLevelSend(SaleReturnTransactionIfc transaction, ParameterManagerIfc parameterManager);
    
    /**
     * Journals the order for the given OrderTransactionIfc and OrderIfc.
     *
     * @param transaction
     * @param order
     * @param parameterManager
     * @return
     * @see oracle.retail.stores.pos.journal.JournalFormatterManagerIfc#journalOrder(oracle.retail.stores.domain.transaction.OrderTransactionIfc, oracle.retail.stores.domain.order.OrderIfc)
     */
    public String journalOrder(OrderTransactionIfc transaction, OrderIfc order, ParameterManagerIfc parameterManager);
    
    /**
     * Journals the order totals for the given OrderIfc and serviceType.
     *
     * @param order
     * @param serviceType
     * @param parameterManager
     * @return The journal string for the totals of the given OrderIfc and serviceType.
     * @see oracle.retail.stores.pos.journal.JournalFormatterManagerIfc#journalOrderTotals(oracle.retail.stores.domain.order.OrderIfc, int)
     */
    public String journalOrderTotals(OrderIfc order, int serviceType, ParameterManagerIfc parameterManager);
    
    /**
     * Formats the totals for the given SaleReturnTransactionIfc, TransactionTotalsIfc, and TransactionTaxIfc.
     * The totals are added to the given StringBuffer.
     *
     * @param sb
     * @param totals
     * @param tax
     * @param parameterManager
     * @see oracle.retail.stores.pos.journal.JournalFormatterManagerIfc#formatTotals(java.lang.StringBuffer, oracle.retail.stores.domain.transaction.TransactionTotalsIfc, oracle.retail.stores.domain.transaction.TransactionTaxIfc)
     */
    public void formatTotals(SaleReturnTransactionIfc trans,
                             StringBuffer sb,
                             TransactionTotalsIfc totals,
                             TransactionTaxIfc transactionTax,
                             ParameterManagerIfc parameterManager);

    /**
     * Journals the given SaleReturnLineItemIfc instance.
     *
     * @param item
     * @param date
     * @param itemID 
     * @return The journal string for the SaleReturnLineItemIfc instance.
     */
    public String toJournalString(SaleReturnLineItemIfc lineItem, EYSDate dob, String itemID);

    /**
     * Journals the given removal of the given SaleReturnLineItemIfc instance.
     *
     * @param item
     * @return
     */
    public String toJournalRemoveString(SaleReturnLineItemIfc lineItem);

    /**
     * Journals the manual discount for the given SaleReturnLineItemIfc instance.
     *
     * @param item
     * @param ifc
     * @param b
     * @return
     */
    public String toJournalManualDiscount(SaleReturnLineItemIfc lineItem,
                                          ItemDiscountStrategyIfc discount,
                                          boolean discountRemoved);

    /**
     * Journals the kit component.
     *
     * @param object
     * @return The journal entry for the given KitComponentLineItemIfc instance.
     */
    public String journalKitComponent(KitComponentLineItemIfc lineItem);

    /**
     * Journals the canceling of the retrieved suspended transaction.
     *
     * @param transaction
     * @return The journal of the canceled transaction.
     */
    public String journalCanceledSuspendedTransaction(TenderableTransactionIfc transaction);

    /**
     * Journals the canceling of the transaction.
     *
     * @param transaction
     * @return The journal of the canceled transaction.
     */
    public String journalCanceledTransaction(TenderableTransactionIfc transaction);

    /**
     * Journals the line items of the given SaleReturnTransactionIfc instance.
     *
     * @param transaction
     * @return The journal of the line items for the given SaleReturnTransactionIfc. 
     */
    public String journalLineItems(SaleReturnTransactionIfc transaction);

    /**
     * Journals the totals for the given VoidTransactionIfc instance.
     *
     * @param voidTrans
     * @param sb
     * @param parameterManager
     */
    public void formatTotals(VoidTransactionIfc voidTrans, StringBuffer sb, ParameterManagerIfc parameterManager);

    /**
     * Journals the shipping charges for the shippingChargeString.
     *
     * @param transaction
     * @param items
     * @param parameterManager
     * @return The shipping charges for the shippingChargeString.
     */
    public String journalShippingInfo(
            SaleReturnTransactionIfc transaction,
            SaleReturnLineItemIfc[] items,
            ParameterManagerIfc parameterManager);
}
