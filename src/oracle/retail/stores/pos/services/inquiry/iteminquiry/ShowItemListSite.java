/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/inquiry/iteminquiry/ShowItemListSite.java /rgbustores_13.4x_generic_branch/2 2011/10/07 12:19:59 rsnayak Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    rsnayak   10/07/11 - Enable disable Request ticket button
 *    npoola    12/20/10 - action button texts are moved to CommonActionsIfc
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    5    360Commerce 1.4         2/26/2008 6:33:59 AM   Naveen Ganesh
 *         unnecessary SOPs have been removed.
 *    4    360Commerce 1.3         11/22/2007 10:59:06 PM Naveen Ganesh   PSI
 *         Code checkin
 *    3    360Commerce 1.2         3/31/2005 4:30:00 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:25:18 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:14:13 PM  Robert Pearse
 *
 *   Revision 1.4  2004/03/18 22:47:42  aschenk
 *   @scr 4079 and 4080 - Items were cleared after a help or cancelled cancel for an item inquiry.
 *
 *   Revision 1.3  2004/02/12 16:50:31  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:11  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:16  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.1   Jan 26 2004 12:05:02   kll
 * attach department description to the item in question
 * Resolution for 3120: Item Inquiry is looking at the incorrect Column in the Tables for the Department
 *
 *    Rev 1.0   Aug 29 2003 16:00:18   CSchellenger
 * Initial revision.
 *
 *    Rev 1.0   Apr 29 2002 15:22:36   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:34:00   msg
 * Initial revision.
 *
 *    Rev 1.1   28 Jan 2002 22:44:14   baa
 * ui fixes
 * Resolution for POS SCR-230: Cross Store Inventory
 * Resolution for POS SCR-824: Application crashes on Customer Add screen after selecting Enter
 *
 *    Rev 1.0   Sep 21 2001 11:29:48   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:08:08   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.inquiry.iteminquiry;

// foundation imports
import oracle.retail.stores.domain.employee.RoleFunctionIfc;
import oracle.retail.stores.domain.manager.ifc.SecurityManagerIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonActionsIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.ItemListBeanModel;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;

//--------------------------------------------------------------------------
/**
    This site displays the ITEM_INFO screen.
    @version $Revision: /rgbustores_13.4x_generic_branch/2 $
**/
//--------------------------------------------------------------------------
public class ShowItemListSite extends PosSiteActionAdapter
{
    /**
        revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    protected static final String PSI_ENABLED_PROPERTY = "PSIEnabled";

    protected boolean latEnabled = false;
    //----------------------------------------------------------------------
    /**
        Displays the ITEMS_LIST screen.
        @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {

        ItemInquiryCargo cargo = (ItemInquiryCargo)bus.getCargo();
        PLUItemIfc[] items = (PLUItemIfc[])cargo.getItemList();

        // attach Dept Names retrieved from the cargo to the Item fo routput to the UI
        for (int i = 0; i < items.length; i++ )
        {
            items[i].getDepartment().setLocalizedDescriptions(cargo.getLocalizedDeptName(items[i].getDepartmentID()));
        }
        
        // enable/disable Request Ticket Button
        if (isLatEnabled(bus))
        {
            latEnabled = true;
        }
        else
        {
            latEnabled = false;
        }

        // update bean model with matching items list
        ItemListBeanModel model = new ItemListBeanModel();
        model.setItemList(items);

        // enable/disable Inventory Inquiry button
        NavigationButtonBeanModel navModel = new NavigationButtonBeanModel();
        navModel.setButtonEnabled(CommonActionsIfc.INVENTORY,isIventoryInquirySupported(bus));
        //enable/disable Request Ticket Button
        navModel.setButtonEnabled(CommonActionsIfc.REQUEST_TICKET, latEnabled);
        model.setLocalButtonBeanModel(navModel);

        // Display the screen
        POSUIManagerIfc  ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        ui.showScreen(POSUIManagerIfc.ITEMS_LIST,model);

    }

    //----------------------------------------------------------------------
    /**
        Calls <code>arrive</code>
        @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void reset(BusIfc bus)
    {
        arrive(bus);
    }

    /**
		This method checks whether the Inventory Inquiry is
		supported or not.
	*/
	public boolean isIventoryInquirySupported(BusIfc bus)
	{
		boolean supported = false;

		try
		{
			ItemInquiryCargo cargo = (ItemInquiryCargo) bus.getCargo();

			//1. Check whether user has access to Inventory Inquiry Function
			SecurityManagerIfc securityManager = (SecurityManagerIfc) Gateway.getDispatcher(). getManager(SecurityManagerIfc.TYPE);
			boolean access = securityManager.checkAccess(cargo.getAppID(), RoleFunctionIfc.INVENTORY_INQUIRY);

			//2. Check wthether Inventory Inquiry is Enabled or not
			Boolean installedFlag = new Boolean(Gateway.getProperty("application", PSI_ENABLED_PROPERTY, "false"));

			//3. Check whether the Reentry option is on or off
			boolean isReentryMode = cargo.getRegister().getWorkstation().isTransReentryMode();

			if(access && installedFlag.booleanValue() && !isReentryMode)
			{
				supported = true;
			}
		}
		catch (Exception e)
		{
			logger.warn("Error while getting Inventory Inquiry Supported Flags");
			supported = false;
		}

		return supported;
    }
	
	 public boolean isLatEnabled(BusIfc bus)
	 {
	        
	        boolean supported = false;

	        ItemInquiryCargo cargo = (ItemInquiryCargo)bus.getCargo();
	        // 1. Check wthether LAT is installed or not
	        Boolean installedFlag = new Boolean(Gateway.getProperty("application", "LatWebServiceEnabled", "false"));
	        // 2. Check whether the Training mode is on or off
	        boolean isTrainingMode = cargo.getRegister().getWorkstation().isTrainingMode();
	        // 3. Check whether the Reentry option is on or off
	        boolean isReentryMode = cargo.getRegister().getWorkstation().isTransReentryMode();
	        if (installedFlag.booleanValue() && !isTrainingMode && !isReentryMode)
	        {
	            supported = true;
	        }
	        else if (isTrainingMode)
	        {
	            supported = false;
	        }
	        else if (isReentryMode)
	        {
	            supported = false;
	        }
	        return supported; 
	    }

}
