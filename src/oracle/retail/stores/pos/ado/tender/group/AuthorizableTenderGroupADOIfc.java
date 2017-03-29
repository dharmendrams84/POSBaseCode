/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/tender/group/AuthorizableTenderGroupADOIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:44 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:27:15 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:19:43 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:09:32 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/08/31 19:12:35  blj
 *   @scr 6855 - cleanup gift card credit code and fix defects found by PBY
 *
 *   Revision 1.2  2004/02/12 16:47:56  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.2   Feb 05 2004 13:20:36   rhafernik
 * No change.
 * 
 *    Rev 1.1   Nov 11 2003 16:18:20   epd
 * Updates made to accommodate tender deletion/reversal
 * 
 *    Rev 1.0   Nov 04 2003 11:13:52   epd
 * Initial revision.
 * 
 *    Rev 1.1   Oct 30 2003 20:39:44   epd
 * removed authorize() method
 * 
 *    Rev 1.0   Oct 27 2003 20:49:34   epd
 * Initial revision.
 * 
 *    Rev 1.0   Oct 17 2003 12:34:28   epd
 * Initial revision.
 *   
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.tender.group;

import java.util.List;

/**
 * Marker interface that marks a group as containing
 * authorizable tenders.
 */
public interface AuthorizableTenderGroupADOIfc
{
    /**
     * Indicates whether this group aggregates a tender type that
     * requires a reversal when removed from the transaction.
     * @return
     */
    public boolean isReversible();
    
    /**
     * Returns an array of all tenders still requiring authorization.
     * 
     * @return returns a list of void tenders needing authorization.
     */
    public List getVoidAuthPendingTenderLineItems();
}
