/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifyitem/ModifyItemGiftRegistrySite.java /main/11 2011/02/16 09:13:33 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:29:04 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:34 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:40 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:51:03  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:39:28  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:17  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:01:42   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:17:30   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:37:24   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:29:26   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:09:06   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifyitem;

import oracle.retail.stores.domain.registry.RegistryIDIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;

/**
 * This shows the Gift Registry screen.
 * 
 * @version $Revision: /main/11 $
 */
public class ModifyItemGiftRegistrySite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = -392583059741727103L;
    /**
     * Revision Number furnished by TeamConnection.
     */
    public static final String revisionNumber = "$Revision: /main/11 $";

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.application.SiteActionAdapter#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void arrive(BusIfc bus)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);

        ItemCargo cargo = (ItemCargo) bus.getCargo();
        RegistryIDIfc gr = cargo.getItem().getRegistry();
        String defaultRegistry = new String("");
        if (gr != null)
        {
            defaultRegistry = gr.getID();
        }
        // set the POSBaseBeanModel
        POSBaseBeanModel baseModel = new POSBaseBeanModel();
        PromptAndResponseModel beanModel = new PromptAndResponseModel();
        beanModel.setResponseText(defaultRegistry);
        baseModel.setPromptAndResponseModel(beanModel);

        ui.showScreen(POSUIManagerIfc.ITEM_GIFT_REGISTRY, baseModel);
    }

    /**
     * Depart method (a no-op in this case).
     * 
     * @param bus Service Bus
     */
    @Override
    public void depart(BusIfc bus)
    {
    }
}