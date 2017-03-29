/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/admin/security/common/UserAccessCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:07 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   02/24/10 - added access function title
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 * 4    360Commerce 1.3         1/25/2006 4:11:54 PM   Brett J. Larsen merge
 *      7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
 * 3    360Commerce 1.2         3/31/2005 4:30:41 PM   Robert Pearse   
 * 2    360Commerce 1.1         3/10/2005 10:26:37 AM  Robert Pearse   
 * 1    360Commerce 1.0         2/11/2005 12:15:26 PM  Robert Pearse   
 *:
 * 4    .v700     1.2.1.0     11/15/2005 14:57:24    Jason L. DeLeau 4204:
 *      Remove duplicate instances of UserAccessCargoIfc
 * 3    360Commerce1.2         3/31/2005 15:30:41     Robert Pearse
 * 2    360Commerce1.1         3/10/2005 10:26:37     Robert Pearse
 * 1    360Commerce1.0         2/11/2005 12:15:26     Robert Pearse
 *
 * Revision 1.3  2004/02/12 16:47:59  mcs
 * Forcing head revision
 *
 * Revision 1.2  2004/02/11 21:27:27  rhafernik
 * @scr 0 Log4J conversion and code cleanup
 *
 * Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 * updating to pvcs 360store-current
 *
 *    Rev 1.0   Nov 04 2003 18:58:40   cdb
 * Initial revision.
 * Resolution for 3430: Sale Service Refactoring
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.admin.security.common;

import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

/**
 * Implement this interface when Security access will be checked or overridden.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface UserAccessCargoIfc extends CargoIfc
{
    /**
     * Returns the function ID whose access is to be checked.
     * 
     * @return int function ID
     */
    public int getAccessFunctionID();

    /**
     * Returns the function title. May be null.
     * 
     * @return function title
     */
    public String getAccessFunctionTitle();

    /**
     * Get the application ID.
     * 
     * @return String string representing the application ID.
     */
    public String getAppID();

    /**
     * Returns the current operator.
     * 
     * @return EmployeeIfc Object
     */
    public EmployeeIfc getOperator();

    /**
     * If an override employee has been set, it will be returned. If not, the
     * current operator will be returned.
     * 
     * @return
     */
    public EmployeeIfc getOverrideOperator();

    /**
     * Returns the resource id for the Security Error Screen
     * 
     * @return String the resource id for the Security error screen
     */
    public String getResourceID();

    /**
     * If a sales executive has been set, it will be returned. If not, the
     * current operator will be returned.
     * 
     * @return EmployeeIfc
     */
    public EmployeeIfc getSalesAssociate();

    /**
     * Sets the function ID whose access is to be checked.
     * 
     * @param value int
     */
    public void setAccessFunctionID(int value);

    /**
     * Set the function title. May be set to null.
     * 
     * @param title
     */
    public void setAccessFunctionTitle(String title);

    /**
     * Sets the current operator.
     * 
     * @param value EmployeeIfc
     */
    public void setOperator(EmployeeIfc value);

    /**
     * Sets the override operator when a successful override has occurred.
     * 
     * @param overrideOperator
     */
    public void setOverrideOperator(EmployeeIfc overrideOperator);

    /**
     * Sets the resource id for the Security Error Screen
     * 
     * @param value String
     */
    public void setResourceID(String value);

    /**
     * Sets the sales associate
     * 
     * @param salesAssociate EmployeeIfc
     */
    public void setSalesAssociate(EmployeeIfc salesAssociate);
}
