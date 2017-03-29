/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/lineitem/GiftCardLineItemADOIfc.java /rgbustores_13.4x_generic_branch/2 2011/07/26 11:52:18 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/26/11 - removed tenderauth and giftcard.activation tours and
 *                         financialnetwork interfaces.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *  3    360Commerce 1.2         3/31/2005 4:28:17 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:21:54 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:11:14 PM  Robert Pearse   
 *
 * Revision 1.4  2004/07/28 22:21:58  lzhao
 * @scr 6592: change for fit ISD.
 *
 * Revision 1.3  2004/04/08 20:33:02  cdb
 * @scr 4206 Cleaned up class headers for logs and revisions.
 *
 * 
 * Rev 1.0 Jan 21 2004 14:41:58 epd Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.lineitem;

import oracle.retail.stores.domain.utility.GiftCardIfc;

/**
 * Provides contract for Gift Card line items
 */
public interface GiftCardLineItemADOIfc extends LineItemADOIfc
{

    /**
     * If the current balance is different from the initial balance then this
     * card has been used.
     * 
     * @param currentAmount
     *            The current amount of this giftCard
     * @return boolean flag indicating usage
     */
    public abstract boolean isUsed(GiftCardIfc giftCard);
}
