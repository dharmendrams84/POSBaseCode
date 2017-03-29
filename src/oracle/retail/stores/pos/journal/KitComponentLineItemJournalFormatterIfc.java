/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/journal/KitComponentLineItemJournalFormatterIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:35 mszekely Exp $
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

/**
 * Interface for journaling KitComponentLineItemIfc instances.
 * $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface KitComponentLineItemJournalFormatterIfc extends LineItemJournalFormatterIfc
{
    
    public String toComponentJournalString();
}
