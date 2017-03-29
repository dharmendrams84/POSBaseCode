/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/reprintreceipt/DisplayReprintSelectSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:29 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    npoola    12/20/10 - action button texts are moved to CommonActionsIfc
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    abondala  01/03/10 - update header date
 *    jswan     07/02/09 - Code review changes.
 *    jswan     06/30/09 - Fixed issues with reprint/gift receipt and order
 *                         line items.
 *
 * ===========================================================================
 * $Log:
 *4    360Commerce 1.3         4/30/2007 4:56:45 PM   Alan N. Sinton  CR 26484
 *     - Merge from v12.0_temp.
 *3    360Commerce 1.2         3/31/2005 4:27:49 PM   Robert Pearse   
 *2    360Commerce 1.1         3/10/2005 10:21:05 AM  Robert Pearse   
 *1    360Commerce 1.0         2/11/2005 12:10:40 PM  Robert Pearse   
 *
 Revision 1.4  2004/08/20 21:04:58  dcobb
 @scr 6857 Reprint Receipt -  Does not display transaction with Kits correctly.
 Display the kit header as in the sale transaction.
 *
 Revision 1.3  2004/04/27 22:25:53  dcobb
 @scr 4452 Feature Enhancement: Printing
 Code review updates.
 *
 Revision 1.2  2004/04/26 19:51:14  dcobb
 @scr 4452 Feature Enhancement: Printing
 Add Reprint Select flow.
 *
 Revision 1.1  2004/04/22 17:39:00  dcobb
 @scr 4452 Feature Enhancement: Printing
 Added REPRINT_SELECT screen and flow to Reprint Receipt use case..
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.reprintreceipt;

import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.OrderLineItemIfc;
import oracle.retail.stores.domain.stock.ItemKitConstantsIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.LineItemsModel;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;
import oracle.retail.stores.pos.services.common.CommonActionsIfc;
import oracle.retail.stores.pos.ui.beans.TotalsBeanModel;

//--------------------------------------------------------------------------
/**
    This site displays the REPRINT_SELECT screen.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class DisplayReprintSelectSite extends PosSiteActionAdapter
{                                       // begin class DisplayReprintSelectSite
    private static final long serialVersionUID = 2368754677469650171L;

    /** revision number of this class */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
        This method displays the REPRINT_SELECT screen. The Print Original
        button is enabled or disabled according to the Enable Reprint Original 
        Receipt parameter.
        @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {                                   // begin arrive()
        // get uiManager and Cargo from bus
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        ReprintReceiptCargo cargo = (ReprintReceiptCargo) bus.getCargo();
                
        // get original sale transaction
        SaleReturnTransactionIfc saleTransaction = (SaleReturnTransactionIfc)cargo.getRetailTransaction();

        //get items from the original sale
        AbstractTransactionLineItemIfc[] itemsArray = saleTransaction.getLineItemsExcluding(ItemKitConstantsIfc.ITEM_KIT_CODE_COMPONENT); 

        //initialize bean model
        LineItemsModel beanModel = new LineItemsModel();
        TotalsBeanModel tbm = new TotalsBeanModel();

        // Before display taxTotals, need to convert the longer precision
        // calculated total tax amount back to shorter precision tax total
        // amount for UI display.
        saleTransaction.getTransactionTotals().setTaxTotal(saleTransaction.getTransactionTotals().getTaxTotalUI());

        // Now, display on the UI.
        tbm.setTotals(saleTransaction.getTransactionTotals());

        beanModel.setTotalsBeanModel(tbm);
        beanModel.setLineItems(itemsArray);
        beanModel.setSelectedRows(cargo.getSelectedIndexes());
        beanModel.setMoveHighlightToTop(false);
                       
        // Instantiate button bean model
        NavigationButtonBeanModel nModel = new NavigationButtonBeanModel();
        
        // Enable/disable ACTION_PRINT_ORIGINAL
        boolean enableButton = isPrintOriginalEnabled(bus);
        nModel.setButtonEnabled(CommonActionsIfc.PRINT_ORIGINAL, enableButton);
        
        // Enable/disable ACTION_GIFT_RECEIPT
        enableButton = isGiftReceiptEnabled(itemsArray);
        nModel.setButtonEnabled(CommonActionsIfc.GIFT_RECEIPT, enableButton);

        // Set the button model on the parent bean model
        beanModel.setLocalButtonBeanModel(nModel);                        
               
        // display the screen
        ui.showScreen(getScreenID(), beanModel);       
    }                                   // end arrive()

    /**
     * Determine if Print Original button should be enabled
     * @param BusIfc
     * @return true if the gift receipt button should be enabled
     */
    protected boolean isPrintOriginalEnabled(BusIfc bus)
    {
        boolean enabled = true;
        try
        {
            ParameterManagerIfc pm = (ParameterManagerIfc) bus.getManager(ParameterManagerIfc.TYPE);
            if (!pm.getBooleanValue("EnableReprintOriginalReceipt").booleanValue())
            {           
                enabled = false;
            }
        }
        catch (ParameterException e)
        {
            logger.error(Util.throwableToString(e));
        }

        return enabled;
    }

    /**
     * Determine if Gift Receipt button should be enabled
     * @param AbstractTransactionLineItemIfc[]
     * @return true if the gift receipt button should be enabled
     */
    protected boolean isGiftReceiptEnabled(AbstractTransactionLineItemIfc[] itemsArray)
    {
        boolean enabled = true;

        for(int i = 0; i < itemsArray.length && enabled == true; i++)
        {
            if (itemsArray[i] instanceof OrderLineItemIfc)
            {
                OrderLineItemIfc oli = (OrderLineItemIfc)itemsArray[i];
                if (oli.getOrderItemStatus().getStatus().getStatus() != OrderLineItemIfc.ORDER_ITEM_STATUS_PICKED_UP)
                {
                    enabled = false;
                }
            }
        }
        
        return enabled;
    }

    /**
     * Returns the screen ID REPRINT_SELECT.
     *
     * @return
     */
    protected String getScreenID()
    {
        return POSUIManagerIfc.REPRINT_SELECT;
    }

}                                       // end class DisplayReprintSelectSite
