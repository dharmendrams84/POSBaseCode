/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/journal/JournalFormatterFactoryIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:35 mszekely Exp $
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
 *  2    360Commerce 1.1         5/14/2007 2:32:57 PM   Alan N. Sinton  CR
 *       26486 - EJournal enhancements for VAT.
 *  1    360Commerce 1.0         5/8/2007 5:23:34 PM    Alan N. Sinton  CR
 *       26486 - Refactor of some EJournal code.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.journal;

/**
 * Factory to create instances of the JournalFormatter.
 * $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface JournalFormatterFactoryIfc
{
    /**
     * Create method for JournalFormatterIfc instances.
     *
     * @param keys
     * @return
     */
    public JournalFormatterIfc createJournalFormatter(String key);
}
