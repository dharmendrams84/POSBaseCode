/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/security/OverridableIfc.java /rgbustores_13.4x_generic_branch/3 2011/07/28 21:02:31 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/28/11 - deprecated
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *  3    360Commerce 1.2         3/31/2005 4:29:15 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:23:55 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:12:56 PM  Robert Pearse   
 *
 * Revision 1.3  2004/04/08 20:33:03  cdb
 * @scr 4206 Cleaned up class headers for logs and revisions.
 *
 * 
 * Rev 1.1 Nov 05 2003 18:42:56 epd updates for authorization
 * 
 * Rev 1.0 Nov 04 2003 11:12:04 epd Initial revision.
 * 
 * Rev 1.0 Oct 17 2003 12:32:24 epd Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.security;

import oracle.retail.stores.domain.employee.EmployeeIfc;

/**
 * All entities that require a behavior modification when a security override
 * is performed should implement this interface.
 * 
 * @deprecated as of 13.4. Seems no code is using this (via ADOs) since APF projct.
 */
public interface OverridableIfc
{
    /**
     * An override for a particular function will be attempted using the
     * override employee. Note that the function to override is not an
     * argument. It is assumed that the type implementing this interface will
     * know it's own function that needs to be overriden.
     * 
     * @param overrideEmployee
     *            The employee for which access will be tested
     * @return The result of the override attempt.
     */
    public boolean override(EmployeeIfc overrideEmployee, int roleFunction);

}
