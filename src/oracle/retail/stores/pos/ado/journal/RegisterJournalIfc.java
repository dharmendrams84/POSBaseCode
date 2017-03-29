/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/journal/RegisterJournalIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:42 mszekely Exp $
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
 *  4    360Commerce 1.3         7/18/2007 8:43:35 AM   Alan N. Sinton  CR
 *       27651 - Made Post Void EJournal entries VAT compliant.
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
public interface RegisterJournalIfc
{
    /**
     * Call this to journal information. The JournalableIfc object should be
     * passed in and a formatter will be used to format the Journal entry.
     * 
     * @param journalable
     *            The object containing the information to be journalled
     * @param family
     *            The category to which this journal entry belongs. Used to
     *            determine which formatter will be used.
     * @param action
     *            The action that we are journalling.
     */
    public void journal(
        JournalableADOIfc journalable,
        JournalFamilyEnum family,
        JournalActionEnum action);

    /**
     * Sets the ParmaeterManager instance.
     * @param pm
     */
    public void setParameterManager(ParameterManagerIfc pm);
}
