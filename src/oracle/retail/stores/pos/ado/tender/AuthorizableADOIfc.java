/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/tender/AuthorizableADOIfc.java /rgbustores_13.4x_generic_branch/2 2011/07/26 11:52:18 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/26/11 - removed tenderauth and giftcard.activation tours and
 *                         financialnetwork interfaces.
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:15 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:19:43 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:09:32 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/03/16 18:30:45  cdb
 *   @scr 0 Removed tabs from all java source code.
 *
 *   Revision 1.2  2004/02/12 16:47:55  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   Nov 12 2003 13:23:50   bwf
 * Added hashmap to authorize and reverse.
 * 
 *    Rev 1.0   Nov 04 2003 11:13:08   epd
 * Initial revision.
 * 
 *    Rev 1.1   Oct 28 2003 14:16:34   epd
 * Added method to return whether tender is already authorized
 * 
 *    Rev 1.0   Oct 17 2003 12:33:42   epd
 * Initial revision.
 *   
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.tender;

/**
 * All tenders that require a 3rd party authorization should implement this
 * interface.
 */
public interface AuthorizableADOIfc
{

    /**
     * This method lets the caller know whether a tender is already validated
     */
    public boolean isAuthorized();
}
