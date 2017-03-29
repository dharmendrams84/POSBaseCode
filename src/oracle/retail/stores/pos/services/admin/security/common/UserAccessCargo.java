/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/admin/security/common/UserAccessCargo.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:07 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   02/24/10 - clear the function title after using it
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
 * 5    .v700     1.2.1.1     11/15/2005 14:57:23    Jason L. DeLeau 4204:
 *      Remove duplicate instances of UserAccessCargoIfc
 * 4    .v700     1.2.1.0     10/31/2005 11:52:03    Deepanshu       CR 6092:
 *      Implemented methods getSalesAssociate() and setSalesAssociate()
 * 3    360Commerce1.2         3/31/2005 15:30:41     Robert Pearse
 * 2    360Commerce1.1         3/10/2005 10:26:37     Robert Pearse
 * 1    360Commerce1.0         2/11/2005 12:15:26     Robert Pearse
 *
 * Revision 1.3  2004/02/12 16:49:02  mcs
 * Forcing head revision
 *
 * Revision 1.2  2004/02/11 21:37:44  rhafernik
 * @scr 0 Log4J conversion and code cleanup
 *
 * Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 * updating to pvcs 360store-current
 *
 *    Rev 1.1   Oct 17 2003 12:52:58   epd
 * Updates to add an override operator
 *
 *    Rev 1.0   Aug 29 2003 15:53:44   CSchellenger
 * Initial revision.
 *
 *    Rev 1.1   23 Jan 2003 15:44:18   mrm
 * Implement JAAS support
 * Resolution for POS SCR-1958: Implement JAAS Support
 *
 *    Rev 1.0   Apr 29 2002 15:37:24   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:21:42   msg
 * Initial revision.
 *
 *    Rev 1.2   17 Jan 2002 17:30:50   baa
 * update roles/security model
 * Resolution for POS SCR-714: Roles/Security 5.0 Updates
 *
 *    Rev 1.1   22 Oct 2001 16:59:44   pdd
 * Added SCR association.
 * Resolution for POS SCR-219: Add Tender Limit Override
 *
 *    Rev 1.0   22 Oct 2001 15:00:12   pdd
 * Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.admin.security.common;

import oracle.retail.stores.pos.services.admin.security.common.UserAccessCargoIfc;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.employee.RoleFunctionIfc;
import oracle.retail.stores.foundation.utility.Util;

/**
 * Extend or use this cargo when Security Access will be checked or overriden.
 * If extending is not an option, implement UserAccessCargoIfc.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public class UserAccessCargo
    implements UserAccessCargoIfc
{
    /** revision number of this class */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /** Current operator */
    protected EmployeeIfc operator = null;
    /** Access Function ID */
    protected int functionID = RoleFunctionIfc.FUNCTION_UNDEFINED;
    /** Optional Access Function title */
    protected String functionTitle;
    /** Security Error Screen Name */
    protected String resourceID = new String("SecurityError");
    /** This is the application identifier for POS */
    protected String appID = new String("POS");
    /** The override operator */
    protected EmployeeIfc overrideOperator;
    /** The Sales Associate */
    protected EmployeeIfc salesAssociate;

    /**
     * Sets the current operator.
     * 
     * @param value EmployeeIfc
     */
    public void setOperator(EmployeeIfc value)
    {
        operator = value;
    }

    /**
     * Returns the current operator.
     * 
     * @return EmployeeIfc value
     */
    public EmployeeIfc getOperator()
    {
        return operator;
    }

    /**
     * Sets the function ID whose access is to be checked. Resets the function
     * title.
     * 
     * @param value int
     */
    public void setAccessFunctionID(int value)
    {
        functionID = value;
        functionTitle = null;
    }

    /**
     * Returns the function ID whose access is to be checked.
     * 
     * @return int function ID
     */
    public int getAccessFunctionID()
    {
        return functionID;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.services.admin.security.common.UserAccessCargoIfc#getAccessFunctionTitle()
     */
    public String getAccessFunctionTitle()
    {
        return functionTitle;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.services.admin.security.common.UserAccessCargoIfc#setAccessFunctionTitle(java.lang.String)
     */
    public void setAccessFunctionTitle(String title)
    {
        this.functionTitle = title;
    }

    /**
     * Returns the resource ID for the error screen to be display.
     * 
     * @return String the resource ID
     */
    public String getResourceID()
    {
        return resourceID;
    }

    /**
     * Sets the resource ID for the error screen to be display.
     * 
     * @param String the resource ID
     */
    public void setResourceID(String value)
    {
        resourceID = value;
    }

    /**
     * Returns the revision number of the class.
     * 
     * @return String representation of revision number
     */
    public String getRevisionNumber()
    {
        return Util.parseRevisionNumber(revisionNumber);
    }

    /**
     * Get the application ID.
     * 
     * @return String string representing the application ID.
     */
    public String getAppID()
    {
        return appID;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.services.admin.security.common.UserAccessCargoIfc#getOverrideOperator()
     */
    public EmployeeIfc getOverrideOperator()
    {
        EmployeeIfc result = null;
        if (this.overrideOperator == null)
        {
            result = getOperator();
        }
        else
        {
            result = overrideOperator;
        }
        return result;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.services.admin.security.common.UserAccessCargoIfc#setOverrideOperator(oracle.retail.stores.domain.employee.EmployeeIfc)
     */
    public void setOverrideOperator(EmployeeIfc overrideOperator)
    {
        this.overrideOperator = overrideOperator;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.services.admin.security.common.UserAccessCargoIfc#getSalesAssociate()
     */
    public EmployeeIfc getSalesAssociate()
    {
        EmployeeIfc result = null;
        if (this.salesAssociate == null)
        {
            result = getOperator();
        }
        else
        {
            result = salesAssociate;
        }
        return result;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.services.admin.security.common.UserAccessCargoIfc#setSalesAssociate(oracle.retail.stores.domain.employee.EmployeeIfc)
     */
    public void setSalesAssociate(EmployeeIfc salesAssociate)
    {
        this.salesAssociate = salesAssociate;
    }
}