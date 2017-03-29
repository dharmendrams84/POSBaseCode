/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/TimedCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:52 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:30:31 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:26:16 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:15:08 PM  Robert Pearse   
 *
 *   Revision 1.1  2004/03/15 21:55:15  jdeleau
 *   @scr 4040 Automatic logoff after timeout
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

/**
 *  If a cargo has a limited duration, and a timeout
 *  could occur, this interface should be implemented.
 * 
 *  @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface TimedCargoIfc
{
    /**
     *  Return whether or not this transaction
     *  has timed out.
     *  
     *  @return
     */
    public boolean isTimeout();
    
    /**
     * Set whether or not this transaction has
     * timed out.
     *  
     *  @param timeout
     */
    public void setTimeout(boolean timeout);
    
}
