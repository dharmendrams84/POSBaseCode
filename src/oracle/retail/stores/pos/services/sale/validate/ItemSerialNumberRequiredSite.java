/* ===========================================================================
* Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/validate/ItemSerialNumberRequiredSite.java /rgbustores_13.4x_generic_branch/1 2012/02/14 13:24:39 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    asinton   02/13/12 - prompt for serial numbers when entering tender if
 *                         items are missing this data
 *
 * ===========================================================================
 */

package oracle.retail.stores.pos.services.sale.validate;

import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonActionsIfc;
import oracle.retail.stores.pos.services.sale.SaleCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;

/**
 * Prompts the operator for the serial number of the item at index
 * SaleCargoIfc.getSerializedItemIndex().
 * @author asinton
 * @since 13.4.1
 */
@SuppressWarnings("serial")
public class ItemSerialNumberRequiredSite extends PosSiteActionAdapter
{
    /**
     * Default UIN Label
     */
    protected static final String DEFAULT_UINLABEL = "Serial Number";

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.application.SiteActionAdapter#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void arrive(BusIfc bus)
    {
        SaleCargo cargo = (SaleCargo)bus.getCargo();
        SaleReturnTransactionIfc transaction = cargo.getTransaction();
        int index = cargo.getSerializedItemIndex();
        SaleReturnLineItemIfc item = (SaleReturnLineItemIfc)transaction.getLineItems()[index];
        
        // Display the screen and get the data
        POSBaseBeanModel beanModel = new POSBaseBeanModel();
        PromptAndResponseModel parModel = new PromptAndResponseModel();
        String itemSerial = item.getItemSerial();
        String uinLabel = item.getPLUItem().getItemClassification().getUINLabel();
        if(uinLabel == null || uinLabel.equals(""))
        {
            uinLabel = DEFAULT_UINLABEL;
        }
        if(itemSerial == null)
        {
            parModel.setResponseText("");
        }
        else
        {
            parModel.setResponseText(itemSerial);
        }
        String[] args = new String[3];
        args[0] = item.getItemID();
        args[1] = item.getItemDescription(LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE));
        args[2] = uinLabel;
        parModel.setArguments(args);
        beanModel.setPromptAndResponseModel(parModel);
        // turn off local buttons
        NavigationButtonBeanModel localButtons = new NavigationButtonBeanModel();
        localButtons.setButtonEnabled(CommonActionsIfc.PICKUP, false);
        localButtons.setButtonEnabled(CommonActionsIfc.DELIVERY, false);
        beanModel.setLocalButtonBeanModel(localButtons);
        // turn off cancel button
        NavigationButtonBeanModel globalButtons = new NavigationButtonBeanModel();
        globalButtons.setButtonEnabled(CommonActionsIfc.CANCEL, false);
        // turn on undo button
        globalButtons.setButtonEnabled(CommonActionsIfc.UNDO, true);
        beanModel.setGlobalButtonBeanModel(globalButtons);

        // show the screen
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        ui.showScreen(POSUIManagerIfc.ITEM_SERIAL_INPUT, beanModel);
    }

}
