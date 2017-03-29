/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/tender/ReversibleTenderADOIfc.java /rgbustores_13.4x_generic_branch/2 2011/07/26 11:52:18 cgreene Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:29:47 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:55 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:57 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:47:55  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.3   Nov 19 2003 13:46:02   bwf
 * Added void auth method ifcs.
 * 
 *    Rev 1.2   Nov 17 2003 09:54:04   epd
 * removed method
 * 
 *    Rev 1.1   Nov 12 2003 13:23:48   bwf
 * Added hashmap to authorize and reverse.
 * 
 *    Rev 1.0   Nov 11 2003 16:19:12   epd
 * Initial revision.
 *   
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.tender;

/**
 *  
 */
public interface ReversibleTenderADOIfc
{

    /**
     * Indicates whether this particular tender was reversed or not
     * 
     * @return
     */
    public boolean isReversed();

    /**
     * Indicates whether this particular tender was voided or not
     * 
     * @return
     */
    public boolean isVoided();
}
