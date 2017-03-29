/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/journal/RegisterJournalFormatterIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:42 mszekely Exp $
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
 *  5    360Commerce 1.4         7/18/2007 8:43:35 AM   Alan N. Sinton  CR
 *       27651 - Made Post Void EJournal entries VAT compliant.
 *  4    360Commerce 1.3         5/8/2007 5:22:00 PM    Alan N. Sinton  CR
 *       26486 - Refactor of some EJournal code.
 *  3    360Commerce 1.2         3/31/2005 4:29:37 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:24:38 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:13:38 PM  Robert Pearse   
 *
 * Revision 1.3  2004/04/08 20:33:02  cdb
 * @scr 4206 Cleaned up class headers for logs and revisions.
 *
 * 
 * Rev 1.0 Nov 04 2003 11:11:16 epd Initial revision.
 * 
 * Rev 1.0 Oct 17 2003 12:31:22 epd Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.journal;

import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;

/**
 *  
 */
public interface RegisterJournalFormatterIfc
{
    /**
     * Takes the JournalableIfc object and formats it according to the
     * specified action and returns a formatted Journal entry.
     * 
     * @param template
     *            The template providing some configuration rules common to all
     *            formatters.
     * @param journalable
     *            The object containing information to be journalled.
     * @param action
     *            The action specifies which journal entry to be made and
     *            should map to a method internally.
     * @return A formatted Journal String.
     */
    public String format(
        JournalTemplateIfc template,
        JournalableADOIfc journalable,
        JournalActionEnum action);

    /**
     * Sets the ParmaeterManager instance.
     * @param pm
     */
    public void setParameterManager(ParameterManagerIfc pm);
}
