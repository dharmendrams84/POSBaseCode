/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/layaway/find/EnterSerialNumberSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:13 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    npoola    12/20/10 - action button texts are moved to CommonActionsIfc
 *    sgu       06/08/10 - fix item interactive screen prompts to include item
 *                         # and description
 *    sgu       06/03/10 - add item description to the screen to get item
 *                         serial #
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    nkgautam  12/24/09 - Added default UIN Label
 *    nkgautam  12/15/09 - New site to capture serial number for layaway
 *                         transactions
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.layaway.find;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.layaway.LayawayCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.services.common.CommonActionsIfc;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;

/**
 * This site displays the ui to collect a serial number for a
 * serialized item.
 * @author nkgautam
 */
public class EnterSerialNumberSite extends PosSiteActionAdapter
{

    /**
     * Default UIN Label
     */
    protected String Default_UINLabel = "Serial Number";

    /**
     * Display the ui to collect a serial number for a serialized item.
     */
    public void arrive(BusIfc bus)
    {

        LayawayCargo cargo = (LayawayCargo)bus.getCargo();
        AbstractTransactionLineItemIfc[] lineItems = cargo.getSerializedItems();
        int counter = cargo.getSerializedItemsCounter();

        if (counter < lineItems.length)
        {
            SaleReturnLineItemIfc lineItem = (SaleReturnLineItemIfc)lineItems[counter];
            cargo.setLineItem(lineItem);
            String UINLabel = lineItem.getPLUItem().getItemClassification().getUINLabel();
            if(UINLabel == null || UINLabel.equals(""))
            {
                UINLabel = Default_UINLabel;
            }
            // Get the UI manager
            POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);

            POSBaseBeanModel beanModel = new POSBaseBeanModel();
            PromptAndResponseModel parModel = new PromptAndResponseModel();
            NavigationButtonBeanModel localModel = new NavigationButtonBeanModel();
            localModel.setButtonEnabled(CommonActionsIfc.PICKUP, false);
            localModel.setButtonEnabled(CommonActionsIfc.DELIVERY, false);            
            String[] args = new String[3];
            args[0] = lineItem.getItemID();
            args[1] = lineItem.getItemDescription(LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE));
            args[2] = UINLabel;
            parModel.setArguments(args);
            beanModel.setPromptAndResponseModel(parModel);
            beanModel.setLocalButtonBeanModel(localModel);

            // Set the serial number for the screen and display the screen
            ui.showScreen(POSUIManagerIfc.ITEM_SERIAL_INPUT, beanModel);
        }
        else
        {
            bus.mail(new Letter(CommonLetterIfc.CONTINUE), BusIfc.CURRENT);
        }

    }

    /**
     * Increments the Item counter for which serial number
     * has been captured
     */
    public void depart(BusIfc bus)
    {
        LayawayCargo cargo = (LayawayCargo)bus.getCargo();
        POSUIManagerIfc     ui   = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        AbstractTransactionLineItemIfc[] lineItems = cargo.getSerializedItems();
        int counter = cargo.getSerializedItemsCounter();
        if(counter < lineItems.length)
        {
            ((SaleReturnLineItemIfc)lineItems[counter]).setItemSerial(ui.getInput());
            cargo.setSerializedItemsCounter(counter + 1);
        }
    }

}
