/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/utility/tdo/ManagerOverrideTDOIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:41 mszekely Exp $
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
 *    1    360Commerce 1.0         12/13/2005 4:47:03 PM  Barry A. Pape   
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.utility.tdo;

import oracle.retail.stores.pos.tdo.TDOIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;

//------------------------------------------------------------------------------
/**
    This TDO checks for ManagerOverrideForSecurityAccess parameter role functions
    from the application properties file.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public interface ManagerOverrideTDOIfc extends TDOIfc
{    
    /**
     * revision number
     */
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    
    /**
     * Checks for the function id if it is overridable
     * @param bus reference for bus
     * @param functionId function id to check
     * @return boolean true implies overridable
     */
    public boolean isOverridable(BusIfc bus, int functionId);    
}
