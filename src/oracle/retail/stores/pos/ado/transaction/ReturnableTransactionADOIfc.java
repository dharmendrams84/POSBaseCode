/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/transaction/ReturnableTransactionADOIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:41 mszekely Exp $
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
 *    4    360Commerce 1.3         3/31/2008 1:54:54 PM   Mathews Kochummen
 *         forward port from v12x to trunk
 *    3    360Commerce 1.2         3/31/2005 4:29:44 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:50 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:52 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:47:57  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Nov 04 2003 11:14:36   epd
 * Initial revision.
 * 
 *    Rev 1.0   Oct 17 2003 12:35:20   epd
 * Initial revision.
 *   
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.transaction;

/**
 *  Not all transaction types are returnable. 
 *  If they are, they should implement this interface.  
 */
public interface ReturnableTransactionADOIfc
{
    /**
     * If the customer had the receipt when this return transction
     * was created, this should return true.
     * @return
     */
    public boolean isReturnWithReceipt();
    /**
     * If the original transaction of a return is successfully 
     * retrieved, return true; otherwise return false.
     */
    public boolean isReturnWithOriginalRetrieved();
}
