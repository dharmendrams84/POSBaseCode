/* ===========================================================================
* Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    npoola    12/20/10 - action button texts are moved to CommonActionsIfc
 *    jswan     09/14/10 - Modified to support verification that serial number
 *                         entered by operator are contained in the external
 *                         order.
 *    nkgautam  09/06/10 - serialisation return changes
 *    nkgautam  09/06/10 - initial version
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returnitem;

import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.returns.returncommon.ReturnItemCargoIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.services.common.CommonActionsIfc;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;

public class EnterSerialNumberSite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = -2014348964656529945L;

    /**
     * Default UIN Label
     */
    protected String Default_UINLabel = "Serial Number";
        
    /**
     * Prompts the Serial number in case of returns
     */
    public void arrive(BusIfc bus)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        ReturnItemCargoIfc cargo = (ReturnItemCargoIfc)bus.getCargo();
        
        SaleReturnLineItemIfc srli = DomainGateway.getFactory().getSaleReturnLineItemInstance();
        srli.setPLUItem(cargo.getPLUItem());
        String UINLabel = cargo.getPLUItem().getItemClassification().getUINLabel();
        if(UINLabel == null || UINLabel.equals(""))
        {
            UINLabel = Default_UINLabel;
        }
        
        POSBaseBeanModel beanModel = new POSBaseBeanModel();
        PromptAndResponseModel parModel = new PromptAndResponseModel();
        NavigationButtonBeanModel localModel = new NavigationButtonBeanModel();
        localModel.setButtonEnabled(CommonActionsIfc.PICKUP, false);
        localModel.setButtonEnabled(CommonActionsIfc.DELIVERY, false);        
        String[] args = new String[3];
        args[0] = srli.getItemID();
        args[1] = srli.getItemDescription(LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE));
        args[2] = UINLabel;
        parModel.setArguments(args);
        beanModel.setPromptAndResponseModel(parModel);
        beanModel.setLocalButtonBeanModel(localModel);

        // Set the serial number for the screen and display the screen
        ui.showScreen(POSUIManagerIfc.ITEM_SERIAL_INPUT, beanModel);
        
    }
}
