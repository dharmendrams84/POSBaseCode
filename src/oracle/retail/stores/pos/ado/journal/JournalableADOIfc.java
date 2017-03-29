/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/journal/JournalableADOIfc.java /rgbustores_13.4x_generic_branch/2 2011/07/18 16:21:31 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/18/11 - added generics to map
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:28:47 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:22:57 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:10 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/20 17:01:09  bjosserand
 *   @scr 0 Mail Bank Check
 *
 *   Revision 1.2  2004/02/12 16:47:50  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   Feb 04 2004 14:18:10   rhafernik
 * No change.
 * 
 *    Rev 1.0   Nov 04 2003 11:11:08   epd
 * Initial revision.
 * 
 *    Rev 1.0   Oct 17 2003 12:31:18   epd
 * Initial revision.
 *   
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.journal;

import java.util.Map;
import java.io.Serializable;

/**
 *  
 */
public interface JournalableADOIfc extends Serializable
{
    /**
     * Get the information to be journaled. This provides a way to access the
     * information to be journaled without violation the rules of encapsulation.
     * The information put into the Map should be either String or primitive
     * data (using wrapper objects of course).
     * 
     * @return The Map of information to be journaled.
     */
    public Map<String, Object> getJournalMemento();
}
