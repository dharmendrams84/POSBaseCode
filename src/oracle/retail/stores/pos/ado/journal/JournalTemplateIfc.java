/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/journal/JournalTemplateIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:42 mszekely Exp $
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
 *  3    360Commerce 1.2         3/31/2005 4:28:48 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:22:59 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:12:11 PM  Robert Pearse   
 *
 * Revision 1.3  2004/04/08 20:33:02  cdb
 * @scr 4206 Cleaned up class headers for logs and revisions.
 *
 * 
 * Rev 1.0 Nov 04 2003 11:11:14 epd Initial revision.
 * 
 * Rev 1.0 Oct 17 2003 12:31:20 epd Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.journal;

/**
 *  
 */
public interface JournalTemplateIfc
{
    /**
     * Get the configured width of the journal line
     * 
     * @return
     */
    public int getJournalLineWidth();

    /**
     * Get the localized end of line string
     * 
     * @return
     */
    public String getEndOfLine();
}
