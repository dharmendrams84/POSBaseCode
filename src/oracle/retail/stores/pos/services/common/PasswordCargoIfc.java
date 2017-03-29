/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/PasswordCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:53 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 * $ 1    360Commerce 1.0         10/12/2006 8:17:59 AM  Christian Greene 
 * $$$
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

import oracle.retail.stores.domain.employee.EmployeeIfc;

/**
 * This interface defines methods used when a cargo works with a password.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface PasswordCargoIfc extends DBErrorCargoIfc
{
    /**
     * Because the business domain object model
     * {@link EmployeeIfc#setPasswordBytes(byte[])} requires that the password
     * be hashed before setting to the employee, any need to display the
     * password for personal recording purposes needs to be handled by the UI
     * code.
     * <p>
     * Thus, this method accepts the password text before it is hashed.
     * @see #getPlainTextPassword()
     * 
     * @param password the plain text password.  Usually a temporary password.
     */
    public void setPlainTextPassword(String password);
    
    /**
     * Returns the plain text password that was set.
     * @see #setPlainTextPassword(String)
     * 
     * @return the plain text password.  Usually a temporary password.
     */
    public String getPlainTextPassword();

}
