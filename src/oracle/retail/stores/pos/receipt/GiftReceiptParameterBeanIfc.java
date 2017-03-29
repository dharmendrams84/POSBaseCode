/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/receipt/GiftReceiptParameterBeanIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:40 mszekely Exp $
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
 *  1    360Commerce 1.0         4/30/2007 7:00:39 PM   Alan N. Sinton  CR
 *       26485 - Merge from v12.0_temp.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.receipt;

import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;

/**
 *
 * $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface GiftReceiptParameterBeanIfc extends ReceiptParameterBeanIfc
{
    /**
     * Sets the SaleReturnLineItem for this SendGiftReceiptParameterBean.
     *
     * @param srLineItem
     */
    public void setSaleReturnLineItems(SaleReturnLineItemIfc[] srLineItem);

    /**
     * Returns the SaleReturnLineItem for this SendGiftReceiptParameterBean.
     *
     * @return The SaleReturnLineItem for this SendGiftReceiptParameterBean.
     */
    public SaleReturnLineItemIfc[] getSaleReturnLineItems();

    /**
     * Sets the price code for this SendGiftReceiptParameterBean.
     *
     * @param code
     */
    public void setPriceCode(String code);

    /**
     * Returns the price code for this SendGiftReceiptParameterBean.
     *
     * @return The price code for this SendGiftReceiptParameterBean.
     */
    public String getPriceCode();

    /**
     * Sets the gift receipt header.
     *
     * @param giftReceiptHeader
     */
    public void setGiftReceiptHeader(String[] giftReceiptHeader);

    /**
     * Returns the gift receipt header.
     *
     * @return
     */
    public String[] getGiftReceiptHeader();

    /**
     * Sets the gift receipt footer.
     *
     * @param giftReceiptFooter
     */
    public void setGiftReceiptFooter(String[] giftReceiptFooter);

    /**
     * Returns the gift receipt footer.
     *
     * @return
     */
    public String[] getGiftReceiptFooter();

}
