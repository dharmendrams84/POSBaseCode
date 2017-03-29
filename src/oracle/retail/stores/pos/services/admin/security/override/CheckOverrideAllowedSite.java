/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/admin/security/override/CheckOverrideAllowedSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:07 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *    tzgarba   11/05/08 - Merged with tip
 *
 * ===========================================================================
 * $Log:
 * 4    360Commerce 1.3         12/13/2005 4:42:38 PM  Barry A. Pape
 *      Base-lining of 7.1_LA
 * 3    360Commerce 1.2         3/31/2005 4:27:25 PM   Robert Pearse   
 * 2    360Commerce 1.1         3/10/2005 10:20:11 AM  Robert Pearse   
 * 1    360Commerce 1.0         2/11/2005 12:09:58 PM  Robert Pearse   
 *
 *Revision 1.5  2004/07/27 19:27:28  bwf
 *@scr 5947 Set correct error messages for security.
 *
 *Revision 1.4  2004/03/03 23:15:10  bwf
 *@scr 0 Fixed CommonLetterIfc deprecations.
 *
 *Revision 1.3  2004/02/12 16:49:03  mcs
 *Forcing head revision
 *
 *Revision 1.2  2004/02/11 21:37:44  rhafernik
 *@scr 0 Log4J conversion and code cleanup
 *
 *Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 *updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Jan 30 2004 15:45:58   rrn
 * Initial revision.
 * Resolution for 3769: Parameterize Manager Override behavior
 * 
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.admin.security.override;

// Foundation imports
import java.util.Locale;

import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.pos.ado.utility.tdo.ManagerOverrideTDOIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.tdo.TDOException;
import oracle.retail.stores.pos.tdo.TDOFactory;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.RoleTransaction;
import oracle.retail.stores.domain.employee.Role;
import oracle.retail.stores.domain.employee.RoleFunctionIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ReasonCodeValue;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

//--------------------------------------------------------------------------
/**
    Checks the content of the Manager Override For Security Access parameter
    to determine whether override is allowed for the given function.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class CheckOverrideAllowedSite extends PosSiteActionAdapter
{
    /**
        revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
        manager override tdo
    **/
    protected static final String TDO_MANAGER_OVERRIDE = "tdo.utility.manageroverride";

    //----------------------------------------------------------------------
    /**
        Check the parameter setting.
        @param bus the bus arriving at this site
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        SecurityOverrideCargo cargo = (SecurityOverrideCargo) bus.getCargo();
        boolean overridable = true;
        int fId = cargo.getAccessFunctionID();
        Locale userLocale = LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE);
        try
        {
            ManagerOverrideTDOIfc tdo = (ManagerOverrideTDOIfc)TDOFactory.create(TDO_MANAGER_OVERRIDE);
            overridable = tdo.isOverridable(bus, fId);
        }
        catch (TDOException e)
        {          
            logger.error(" manager override tdo exception", e);
        }
 

        // If the list of functions contains the functionID of the function being accessed,
        //  then that function allows manager override.
        // If the list was not retrieved, the "default" behavior will be to allow override.
        
        if(!overridable)
        {            
            // get correct arg text
            String title = Role.getFunctionTitle(userLocale, fId);
            String args[] = new String[] {title};
            
            // display screen
            POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
            DialogBeanModel dialogModel = new DialogBeanModel();
            dialogModel.setResourceID("SecurityAccess");
            dialogModel.setType(DialogScreensIfc.ERROR);
            dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_OK, "Failure");
            dialogModel.setArgs(args);
            ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
        }
        else
        {
            bus.mail(new Letter(CommonLetterIfc.YES), BusIfc.CURRENT);
        }

        
    }
    
    //----------------------------------------------------------------------
    /**
        Check to see if the functionID being accessed is in the list of 
        functions contained by the ManagerOverrideForSecurityAccess parameter.
        @param rcValues The list of override-able functions.
        @param functionID The ID of the function being accessed.
        @deprecated Deprecated as of 7.0.1 replaced by ManagerOverrideTDOIfc 
                    isOverridable method
    **/
    //----------------------------------------------------------------------
    public boolean containsID(ReasonCodeValue[] rcValues, int functionID)
    {
        boolean retCode = false;
        
        for(int i=0; i<rcValues.length && retCode==false; i++)
        {
            if( rcValues[i].getDatabaseId() == functionID )
            {
                retCode = true;
            }
        }
        
        return retCode;
    }
    
    //--------------------------------------------------------------------------
    /**
       Get an array of role functions to initialize the role object. <P>
       @return RoleFunctionIfc[] array of RoleFunctionIfc objects
       @deprecated As of release 13.1, no replacement as it is not used.       
    **/
    //--------------------------------------------------------------------------
    public RoleFunctionIfc[] getFunctions()
    {
        RoleTransaction trans = null;
        
        trans = (RoleTransaction) DataTransactionFactory.create(DataTransactionKeys.ROLE_TRANSACTION);
        
        RoleFunctionIfc[] funcs;
        try
        {
            funcs = trans.getRoleFunctions();
        }
        catch (DataException e)
        {
            funcs = null;
        }
        return(funcs);
    }

}
