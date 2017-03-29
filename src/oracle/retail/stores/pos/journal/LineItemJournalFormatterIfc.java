/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/journal/LineItemJournalFormatterIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:35 mszekely Exp $
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
 *  1    360Commerce 1.0         5/14/2007 2:33:48 PM   Alan N. Sinton  CR
 *       26486 - EJournal enhancements for VAT.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.journal;

import oracle.retail.stores.domain.discount.ItemDiscountStrategyIfc;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.utility.EYSDate;

/**
 * Journal Formatter for line items.
 * $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface LineItemJournalFormatterIfc extends JournalFormatterIfc
{
    /**
     * Returns a journal String for the Line Item.
     *
     * @return
     */
    public String toJournalString();

    /**
     * 
     *
     * @param date
     * @return
     */
    public String toJournalString(EYSDate date);

    /**
     * 
     *
     * @param date
     * @param itemId
     * @return
     */
    public String toJournalString(EYSDate date, String itemId);

    /**
     * 
     *
     * @param discount
     * @param discountRemoved
     * @return
     */
    public String toJournalManualDiscount(ItemDiscountStrategyIfc discount, boolean discountRemoved);

    /**
     * 
     *
     * @return
     */
    public String toJournalRemoveString();

    /**
     * Gets the instance of the Line Item.
     * @return Returns the lineItem.
     */
    public AbstractTransactionLineItemIfc getLineItem();

    /**
     * Sets the instance of the Line Item.
     * @param lineItem The lineItem to set.
     */
    public void setLineItem(AbstractTransactionLineItemIfc lineItem);
}
