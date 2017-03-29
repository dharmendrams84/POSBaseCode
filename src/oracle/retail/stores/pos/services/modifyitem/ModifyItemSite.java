/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    rsnayak   10/03/11 - related line item quanity fix
 *    blarsen   01/07/11 - XbranchMerge
 *                         blarsen_bug10624300-discount-flag-change-side-effects
 *                         from rgbustores_13.3x_generic_branch
 *    blarsen   01/06/11 - Changed discount eligibility check to use
 *                         DiscountUtility helper method. SRLI was changed to
 *                         return the simple item eligibility flag.
 *                         DiscountUtility includes additional checks (e.g.
 *                         external pricing).
 *    npoola    09/07/10 - disable the pickup and delivery buttons if the
 *                         Layway Transaction is initiated
 *    abondala  07/15/10 - updated
 *    abondala  07/15/10 - Disable item level and pricing options for external
 *                         order items
 *    abondala  06/21/10 - Disable item level editing for an external order
 *                         line item
 *    npoola    06/02/10 - removed the training mode increment id dependency
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    cgreene   03/22/10 - prevent Components button from appearing when any
 *                         single selection is not a KitHeader
 *    nkgautam  03/18/10 - quantity button fix for layaway transaction
 *    nkgautam  03/02/10 - removed conditions to disable pickup/delivery button
 *                         for a serialised item as per the FS changes
 *    nkgautam  02/18/10 - Pickup/Delivery button needs to be disabled when a
 *                         serialised item is scanned and the serial number is
 *                         captured
 *    nkgautam  02/02/10 - Setting Quantity button state to false for a
 *                         serialised item in order transaction
 *    jswan     01/25/10 - Modified to prevent display serial number button
 *                         when there are multiple items selected.
 *    npoola    01/12/10 - Serial Number Button has to be enabled when the
 *                         selected items doesnot contain item designated for
 *                         Pickup/Delivery
 *    abondala  01/03/10 - update header date
 *    npoola    12/17/09 - merged the changes

 *    npoola    12/16/09 - replaced the constant types
 *    npoola    12/16/09 - disable the services button if the transaction type
 *                         is Layaway*    nkgautam  11/25/09 - Disabling Quantity button code change for a layaway
 *                         transaction
 *    cgreene   06/26/09 - check entire transactions for potential returns
 *                         since a delivery can't be scheduled for any selected
 *                         sale item when there are returns in the transaction.
 *    jswan     06/23/09 - The last several changes to this class have been
 *                         attempts to work around a defect in the
 *                         NavigationButtonBeanModel; blarsen made change to
 *                         fix Gift Cert problems, then I made changes to fix
 *                         other problems. My changes require moving gift cert
 *                         code back to its previous position.
 *    jswan     05/20/09 - Refactored to fix issues with PDO and gift reciept.
 *                         Performed cleanup to remove code for buttons which
 *                         are no longer on this screen.
 *    blarsen   04/22/09 - Several buttons were not being disabled for gift
 *                         cards/certs. Changed code to workaround an issue
 *                         with NavigationButtonBeanModel. Workaround is to
 *                         move gift card/cert handling earlier in method
 *                         getNavigationButtonBeanModel.
 *    mahising  03/04/09 - Fixed send button disable issue when line item mark
 *                         as pick-up or deliver vice versa
 *    mahising  02/25/09 - Fixed block sale transaction at queue issue
 *    mahising  01/19/09 - fix special order issue
 *    aphulamb  01/02/09 - fix delivery issues
 *    aphulamb  12/23/08 - Mock padding fix and PDO flow related changes for
 *                         buttons enable/disable
 *    aphulamb  12/17/08 - bug fixing of PDO
 *    aphulamb  12/10/08 - checked in after ade recover
 *    aphulamb  12/10/08 - returns functionality changes for greying out
 *                         buttons
 *    aphulamb  12/09/08 - Deposite Amount and Cancel Trasaction Fixes.
 *    aphulamb  11/22/08 - Checking files after code review by Naga
 *    aphulamb  11/18/08 - Pickup Delivery Order
 *    aphulamb  11/13/08 - Check in all the files for Pickup Delivery Order
 *                         functionality
 *
 * ===========================================================================
 * $Log:
 *    5    360Commerce 1.4         5/7/2007 4:11:52 PM    Alan N. Sinton  CR
 *         26483 - Modified to use the Gateway.getBooleanProperty() method.
 *    4    360Commerce 1.3         4/30/2007 3:45:30 PM   Alan N. Sinton  Merge
 *          from v12.0_temp.
 *    3    360Commerce 1.2         3/31/2005 4:29:04 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:23:34 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:12:40 PM  Robert Pearse
 *
 *   Revision 1.10  2004/08/23 16:15:58  cdb
 *   @scr 4204 Removed tab characters
 *
 *   Revision 1.9  2004/08/11 15:18:03  rsachdeva
 *   @scr 6791 Transaction Level Send
 *
 *   Revision 1.8  2004/07/20 13:55:09  lzhao
 *   @scr 6359: enable tax button for return item when offline.
 *
 *   Revision 1.7  2004/06/28 17:01:11  aschenk
 *   @scr 5843 and 5844 - Disabled Gift registry button when the item is a retrieved return item.  Disabled the serial button when the item is a kit.
 *
 *   Revision 1.6  2004/06/25 16:25:27  dfierling
 *   @scr 5838 - adjusted Serial_Number button to true for Kit item
 *
 *   Revision 1.5  2004/04/05 19:05:39  pkillick
 *   @scr 4257 -Gift certificates should not have the Serial Number button activated.
 *
 *   Revision 1.4  2004/03/24 16:44:57  aschenk
 *   @scr 4141 and 4142 - qty and tax options disabled for a gift certificate item.
 *
 *   Revision 1.3  2004/02/12 16:51:03  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:39:28  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.3   Jan 27 2004 16:03:10   bwf
 * Allow tax and sales associate for multi items.
 * Resolution for 3765: Modify Item Feature
 *
 *    Rev 1.2   08 Jan 2004 20:28:50   Tim Fritz
 * Fixed SCR 3621
 *
 *    Rev 1.1   Dec 03 2003 13:58:42   Tim Fritz
 * The Quantity button is now enabled for items returned with no receipt.  Resolution for SCR 1973
 *
 *    Rev 1.0   Aug 29 2003 16:01:46   CSchellenger
 * Initial revision.
 *
 *    Rev 1.3   Jul 18 2003 15:28:40   sfl
 * Disable the Send button when the transaction is a layaway transaction.
 * Resolution for POS SCR-2430: Layaway with send transaction issue
 *
 *    Rev 1.2   Mar 05 2003 18:19:08   DCobb
 * Disable Alterations button for return line item.
 * Resolution for POS SCR-1808: Alterations instructions not saved and not printed when trans. suspended
 *
 *    Rev 1.1   Aug 21 2002 11:21:24   DCobb
 * Added Alterations service.
 * Resolution for POS SCR-1753: POS 5.5 Alterations Package
 *
 *    Rev 1.0   Apr 29 2002 15:17:40   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:37:30   msg
 * Initial revision.
 *
 *    Rev 1.12   Mar 10 2002 10:13:38   dfh
 * disable item tax button if transaction tax was set to exempt
 * Resolution for POS SCR-1529: Able to modify item level tax on an Tax Exempt transaction
 *
 *    Rev 1.11   Feb 23 2002 10:35:04   mpm
 * Modified Util.BIG_DECIMAL to Util.I_BIG_DECIMAL, Util.ROUND_HALF to Util.I_ROUND_HALF
 * Resolution for POS SCR-1398: Accept Foundation BigDecimal backward-compatibility changes
 *
 *    Rev 1.10   Jan 19 2002 10:28:18   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 *
 *    Rev 1.8   Dec 10 2001 17:23:36   blj
 * updated per codereview findings.
 * Resolution for POS SCR-237: Gift Receipt Feature
 *
 *    Rev 1.7   Dec 04 2001 20:20:50   dfh
 * updates to disable send, gift receipt and serial number when
 * special order is in progress
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.6   02 Dec 2001 11:10:08   pjf
 * Decomposed getModifyItemBeanModel().
 * Resolution for POS SCR-8: Item Kits
 *
 *    Rev 1.5   Nov 21 2001 12:39:18   blj
 * Updated to allow gift receipt to be printed for kit items, reprint receipt,
 * journals and suspend.
 * Resolution for POS SCR-237: Gift Receipt Feature
 *
 *    Rev 1.4   20 Nov 2001 14:45:16   pjf
 * Kit cleanup, bug fix.
 * Resolution for POS SCR-8: Item Kits
 *
 *    Rev 1.3   19 Nov 2001 14:13:56   sfl
 * Enable Send button in the ModifyItem Service
 * Resolution for POS SCR-287: Send Transaction
 *
 *    Rev 1.2   13 Nov 2001 14:58:00   sfl
 * Make the changes to accept the mutiple items
 * selected from item list in the Sell Item screen
 * and then decide which screen buttons shall
 * be enabled in ModifyItem service based on
 * the number of selected items and the type
 * of items.
 * Resolution for POS SCR-282: Multiple Item Selection
 *
 *    Rev 1.1   26 Oct 2001 09:56:12   pjf
 * Added new service for modifying kit components.
 * Added buttons for Gift Registry and Send to the Item Options menu.
 * Disabled Gift Receipt button.
 * Enabled/Disable Item Options buttons based on item type.
 * Resolution for POS SCR-8: Item Kits
 *
 *    Rev 1.0   Sep 21 2001 11:28:58   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:09:06   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifyitem;

import java.util.HashMap;
import java.util.Iterator;

import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.OrderLineItemIfc;
import oracle.retail.stores.domain.lineitem.ReturnItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderConstantsIfc;
import oracle.retail.stores.domain.stock.GiftCardPLUItemIfc;
import oracle.retail.stores.domain.stock.GiftCertificateItemIfc;
import oracle.retail.stores.domain.stock.ItemClassificationIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.domain.stock.ProductGroupConstantsIfc;
import oracle.retail.stores.domain.tax.TaxIfc;
import oracle.retail.stores.domain.transaction.OrderTransaction;
import oracle.retail.stores.domain.transaction.OrderTransactionIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionConstantsIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.domain.utility.DiscountUtility;
import oracle.retail.stores.foundation.manager.gui.ButtonSpec;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.ListBeanModel;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;

/**
 * This site displays the main menu for the Modify Item service.
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public class ModifyItemSite extends PosSiteActionAdapter
{

    /** serialVersionUID */
    private static final long serialVersionUID = -8689018569917803144L;

    /**
     * Revision Number furnished by TeamConnection.
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
     * Class name constant
     */
    public static final String SITENAME = "ModifyItemSite";

    /**
     * Constant for the Inquiry button action.
     */
    public static final String ACTION_INQUIRY = "Inquiry";

    /**
     * Constant for the Quantity button action.
     */
    public static final String ACTION_QUANTITY = "Quantity";

    /**
     * Constant for the Tax button action.
     */
    public static final String ACTION_TAX = "Tax";

    /**
     * Constant for the Sales Associate button action.
     */
    public static final String ACTION_SALES_ASSOCIATE = "SalesAssociate";

    /**
     * Constant for the Gift Registry button action.
     */
    public static final String ACTION_GIFT_REGISTRY = "GiftRegistry";

    /**
     * Constant for the Gift Registry button action.
     * @since 5.0
     */
    public static final String ACTION_GIFT_RECEIPT = "GiftReceipt";

    /**
     * Constant for the serial number button action.
     */
    public static final String ACTION_SERIAL_NUMBER = "SerialNumber";

    /**
     * Constant for the services button action.
     */
    public static final String ACTION_SERVICES = "Services";

    /**
     * Constant for the kit components button action.
     */
    public static final String ACTION_COMPONENTS = "Components";

    /**
     * Constant for the send button action.
     * @since 5.0
     */
    public static final String ACTION_SEND = "Send";

    /**
     * Constant for the alterations button action.
     * @since 6.0
     */
    public static final String ACTION_ALTERATIONS = "Alterations";

    /**
     * Constant for the pickup button action.
     */
    public static final String ACTION_PICKUP = "Pickup";

    /**
     * Constant for the delivery button action.
     */
    public static final String ACTION_DELIVERY = "Delivery";

    /**
     * Constant for unit of measure UNITS
     */
    public static final String UNITS = "UN";


    /**
     * Displays the ITEM_OPTIONS screen.
     *
     * @param bus Service bus.
     */
    @Override
    public void arrive(BusIfc bus)
    {
        ItemCargo cargo = (ItemCargo)bus.getCargo();
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        int taxMode = TaxIfc.TAX_MODE_STANDARD; // starting default value
        // Initialize the bean model
        SaleReturnLineItemIfc[] lineItemList = cargo.getItems();

        // get tax mode if transaction in progress
        RetailTransactionIfc transaction = cargo.getTransaction();
        if (transaction != null)
        {
            taxMode = transaction.getTransactionTax().getTaxMode();
        }
        ListBeanModel model = getModifyItemBeanModel(lineItemList, transaction, cargo.getTransactionType(), taxMode);

        // if transaction level send has been assigned, then disable item level
        // send(as per reqs.)
        if (transaction != null && transaction.getTransactionTotals().isTransactionLevelSendAssigned())
        {
            model.setLocalButtonBeanModel(replaceAddEnableButton(model.getLocalButtonBeanModel(), ACTION_SEND, false));
        }

        if (transaction != null && transaction instanceof OrderTransaction)
        {
            OrderTransaction orderTransaction = (OrderTransaction)transaction;
            if (orderTransaction.getOrderType() == OrderConstantsIfc.ORDER_TYPE_SPECIAL)
            {
                model.setLocalButtonBeanModel(replaceAddEnableButton(model.getLocalButtonBeanModel(), ACTION_PICKUP, false));
                model.setLocalButtonBeanModel(replaceAddEnableButton(model.getLocalButtonBeanModel(), ACTION_DELIVERY, false));
            }
        }
        
        RegisterIfc register = cargo.getRegister();        
        if(register != null && register.getWorkstation().isTrainingMode())
        {
            model.setLocalButtonBeanModel(replaceAddEnableButton(model.getLocalButtonBeanModel(), ACTION_PICKUP, false));
            model.setLocalButtonBeanModel(replaceAddEnableButton(model.getLocalButtonBeanModel(), ACTION_DELIVERY, false));             
        } 
        
        ui.showScreen(POSUIManagerIfc.ITEM_OPTIONS, model);
    }

    /**
     * Builds the ModifyItemBeanModel; this bean contains the the line item and
     * the model that sets the local navigation buttons to their correct enabled
     * states.
     *
     * @param lineItemList The itemlist to modify.
     * @param transaction the transaction owning the lineitems
     * @param transType Type of transaction (disable Send/Gift Receipt/Serial
     *            Number for special order
     * @return ModifyItemBeanModel
     */
    protected ListBeanModel getModifyItemBeanModel(SaleReturnLineItemIfc[] lineItemList, RetailTransactionIfc transaction, int transType, int taxMode)
    {
        ListBeanModel model = new ListBeanModel();

        if (lineItemList != null)
        {
            model.setListModel(lineItemList);
        }
        model.setLocalButtonBeanModel(getNavigationButtonBeanModel(lineItemList, transaction, transType, taxMode));

        return model;
    }

    /**
     * Builds the NavigationButtonBeanModel; this method sets the local
     * navigation buttons to their correct enabled states.
     *
     * @param lineItemList The itemlist used to determine button states.
     * @param the transation owning the line items.
     * @param transType Type of transaction (disable Send/Gift Receipt/Serial
     *            Number for special order
     * @return NavigationButtonBeanModel
     */
    protected NavigationButtonBeanModel getNavigationButtonBeanModel(SaleReturnLineItemIfc[] lineItemList,
            RetailTransactionIfc transaction, int transType, int taxMode)
    {
        // This hash map provides work around to the issue in the button model
        // where the latest change to the button state is ignored.
        HashMap<String, Boolean> buttonState = new HashMap<String, Boolean>();
        setButtonState(buttonState, ACTION_INQUIRY, true);

        // refresh buttons turned off in previous iterations
        if (transType == TransactionConstantsIfc.TYPE_LAYAWAY_INITIATE
                || transType == TransactionConstantsIfc.TYPE_LAYAWAY_PAYMENT
                || transType == TransactionConstantsIfc.TYPE_LAYAWAY_DELETE
                || transType == TransactionConstantsIfc.TYPE_LAYAWAY_COMPLETE)
        {
            setButtonState(buttonState, ACTION_SERVICES, false);
        }
        else
        {
            setButtonState(buttonState, ACTION_SERVICES, true);
        }

        setButtonState(buttonState, ACTION_GIFT_RECEIPT, true);
        setButtonState(buttonState, ACTION_QUANTITY, true);
        setButtonState(buttonState, ACTION_TAX, true);
        setButtonState(buttonState, ACTION_SALES_ASSOCIATE, true);
        setButtonState(buttonState, ACTION_GIFT_REGISTRY, true);
        setButtonState(buttonState, ACTION_SERIAL_NUMBER, true);
        setButtonState(buttonState, ACTION_SEND, true);
        setButtonState(buttonState, ACTION_COMPONENTS, true);
        setButtonState(buttonState, ACTION_ALTERATIONS, true);
        setButtonState(buttonState, ACTION_PICKUP, true);
        setButtonState(buttonState, ACTION_DELIVERY, true);

        // If there is no item
        if (lineItemList == null)
        { // turn off everything except Inquiry and
            // Services
            setButtonState(buttonState, ACTION_QUANTITY, false);
            setButtonState(buttonState, ACTION_TAX, false);
            setButtonState(buttonState, ACTION_SALES_ASSOCIATE, false);
            setButtonState(buttonState, ACTION_GIFT_REGISTRY, false);
            setButtonState(buttonState, ACTION_SERIAL_NUMBER, false);
            setButtonState(buttonState, ACTION_GIFT_RECEIPT, false);
            setButtonState(buttonState, ACTION_SEND, false);
            setButtonState(buttonState, ACTION_COMPONENTS, false);
            setButtonState(buttonState, ACTION_ALTERATIONS, false);
            setButtonState(buttonState, ACTION_PICKUP, false);
            setButtonState(buttonState, ACTION_DELIVERY, false);
        }
        else
        {
            // If there is more than one item, turn off single item buttons.
            if (lineItemList.length > 1)
            {
                setButtonState(buttonState, ACTION_QUANTITY, false);
                setButtonState(buttonState, ACTION_GIFT_REGISTRY, false);
                setButtonState(buttonState, ACTION_COMPONENTS, false);
                setButtonState(buttonState, ACTION_ALTERATIONS, false);
                setButtonState(buttonState, ACTION_SERIAL_NUMBER, false);

                for (int i = 0; i < lineItemList.length; i++) 
                {
                    SaleReturnLineItemIfc lineItem = lineItemList[i];
                    if(lineItem.isFromExternalOrder())
                    {
                        setButtonState(buttonState, ACTION_SERVICES, false);
                        setButtonState(buttonState, ACTION_PICKUP, false);
                        setButtonState(buttonState, ACTION_DELIVERY, false);
                        boolean hasExternalSend = hasExternalSend(transaction, lineItem);
                        if(hasExternalSend)
                        {
                            setButtonState(buttonState, ACTION_SEND, false);
                        }
                    }
                }        
            }

            // If there is only one item, turn on single item buttons.
            if (lineItemList.length == 1)
            {
                applyOneLineItemSelected(buttonState, transaction, transType, lineItemList[0]);
            }

            // Set button states which depend on there being one or more items in the list.
            // Item tax cannot be modified if transaction is tax exempt
            if (taxMode == TaxIfc.TAX_MODE_EXEMPT)
            {
                setButtonState(buttonState, ACTION_TAX, false);
            }

            // Can't send items in layaway transactions
            if ((transType == TransactionIfc.TYPE_LAYAWAY_INITIATE)
                    || (transType == TransactionIfc.TYPE_LAYAWAY_PAYMENT)
                    || (transType == TransactionIfc.TYPE_LAYAWAY_COMPLETE)
                    || (transType == TransactionIfc.TYPE_LAYAWAY_DELETE))
            {
                setButtonState(buttonState, ACTION_SEND, false);
                setButtonState(buttonState, ACTION_PICKUP, false);
                setButtonState(buttonState, ACTION_DELIVERY, false);                
            }

            // turn off Send, Gift Receipt, Serial Number if special order in
            // progress
            if (transType == TransactionIfc.TYPE_ORDER_INITIATE)
            {
                setButtonState(buttonState, ACTION_SEND, false);
                setButtonState(buttonState, ACTION_GIFT_RECEIPT, false);
            }

            // If InclusiveTaxEnabled is true, then disable the "TAX" buttons for
            // VAT.
            boolean taxInclusiveFlag = Gateway.getBooleanProperty("application", "InclusiveTaxEnabled", false);
            if (taxInclusiveFlag)
            {
                setButtonState(buttonState, ACTION_TAX, false);
            }

            // Check pickup, delivery, gift reciept, send and return interdependancies.
            boolean containsGiftReciept        = false;
            boolean containsReturnItem         = false;
            boolean containsSendItem           = false;
            boolean containsPickupItem         = false;
            boolean containsDeliveryItem       = false;
            for (int i = 0; i < lineItemList.length; i++)
            {
                if (lineItemList[i].isReturnLineItem())
                {
                    containsReturnItem = true;
                }
                if (lineItemList[i].hasSendItem())
                {
                    containsSendItem = true;
                }
                if (lineItemList[i].isGiftReceiptItem())
                {
                    containsGiftReciept = true;
                }
                if (lineItemList[i].getOrderItemStatus().getItemDispositionCode() == OrderLineItemIfc.ORDER_ITEM_DISPOSITION_DELIVERY)
                {
                    containsDeliveryItem = true;
                }
                if (lineItemList[i].getOrderItemStatus().getItemDispositionCode() == OrderLineItemIfc.ORDER_ITEM_DISPOSITION_PICKUP)
                {
                    containsPickupItem = true;
                }
            }
            // check entire transaction for returns
            if (transaction != null)
            {
                for (Iterator<AbstractTransactionLineItemIfc> iter = transaction.getLineItemsIterator(); iter.hasNext();)
                {
                    if (((SaleReturnLineItemIfc)iter.next()).isReturnLineItem())
                    {
                        containsReturnItem = true;
                    }
                }
            }

            if (containsGiftReciept || containsReturnItem || containsSendItem)
            {
                setButtonState(buttonState, ACTION_DELIVERY, false);
                setButtonState(buttonState, ACTION_PICKUP, false);
            }
            if (containsDeliveryItem)
            {
                setButtonState(buttonState, ACTION_PICKUP, false);
            }
            if (containsPickupItem)
            {
                setButtonState(buttonState, ACTION_DELIVERY, false);
            }
            if (containsDeliveryItem || containsPickupItem)
            {
                setButtonState(buttonState, ACTION_SEND, false);
                setButtonState(buttonState, ACTION_GIFT_RECEIPT, false);
                setButtonState(buttonState, ACTION_SERIAL_NUMBER, false);
            }
        } // line item(s)

        // Add the consolidated enabled/disabled button values to the model.
        NavigationButtonBeanModel nbbModel = new NavigationButtonBeanModel();
        Iterator<String> keys = buttonState.keySet().iterator();
        while (keys.hasNext())
        {
            String actionName = keys.next();
            nbbModel.setButtonEnabled(actionName, buttonState.get(actionName).booleanValue());
        }

        // Return the model.
        return nbbModel;
    }
    
    /**
     * Deterimne if the external order item has send package
     * @param transaction
     * @param lineItem
     * @return true if line item has send package
     */
    private boolean hasExternalSend(RetailTransactionIfc transaction, SaleReturnLineItemIfc lineItem)
    {    
        boolean hasExternalSend = false;
        if (transaction instanceof SaleReturnTransactionIfc && lineItem.getItemSendFlag())
        {
            int index = lineItem.getSendLabelCount() - 1;
            if(index != -1)
            {
                hasExternalSend = ((SaleReturnTransactionIfc)transaction).getSendPackages()[index].isExternalSend();
            }
        }
        return hasExternalSend;
    }

    /**
     * Apply button states for only one line item selected.
     * 
     * @param buttonState
     * @param transaction
     * @param lineItem
     */
    private void applyOneLineItemSelected(HashMap<String, Boolean> buttonState,
            RetailTransactionIfc transaction, int transType, SaleReturnLineItemIfc lineItem)
    {
        PLUItemIfc pluItem = lineItem.getPLUItem();
        ItemClassificationIfc item = pluItem.getItemClassification();

        // Alterations button
        String productGroup = pluItem.getProductGroupID();
        if (productGroup != null && productGroup.equals(ProductGroupConstantsIfc.PRODUCT_GROUP_ALTERATION))
        {
            setButtonState(buttonState, ACTION_ALTERATIONS, !lineItem.isReturnLineItem());
        }
        else
        {
            setButtonState(buttonState, ACTION_ALTERATIONS, false);
        }

        // specifically set the component button only if the one selected is a kit header
        setButtonState(buttonState, ACTION_COMPONENTS, lineItem.isKitHeader());
        // Set buttons for Order Items
        if (lineItem instanceof OrderLineItemIfc)
        {
            setButtonState(buttonState, ACTION_QUANTITY, false);
            setButtonState(buttonState, ACTION_TAX, false);
            setButtonState(buttonState, ACTION_SALES_ASSOCIATE, false);
        }
        if (lineItem.isFromExternalOrder())
        {
            setButtonState(buttonState, ACTION_QUANTITY, false);
            setButtonState(buttonState, ACTION_SERVICES, false);
            setButtonState(buttonState, ACTION_COMPONENTS, false);
            setButtonState(buttonState, ACTION_PICKUP, false);
            setButtonState(buttonState, ACTION_DELIVERY, false);

            boolean hasExternalSend = hasExternalSend(transaction, lineItem);
            if(hasExternalSend)
            {
                setButtonState(buttonState, ACTION_SEND, false);
            }
        }        
        else
        {
            if (lineItem.isKitHeader())
            {
                setButtonState(buttonState, ACTION_QUANTITY, false);
                setButtonState(buttonState, ACTION_TAX, false);
                setButtonState(buttonState, ACTION_SERVICES, false);
                setButtonState(buttonState, ACTION_SERIAL_NUMBER, false);
            }
            else
            {
                // Set the actions individually based on the item
                // attributes
                setButtonState(buttonState, ACTION_SALES_ASSOCIATE, !isRetrievedReturnItem(lineItem));

                ReturnItemIfc returnItem = lineItem.getReturnItem();
                if (isRetrievedReturnItem(lineItem) && returnItem != null && returnItem.isFromRetrievedTransaction())
                {
                    setButtonState(buttonState, ACTION_TAX, false);
                }

                boolean quantityModifiable = (Util.isEmpty(lineItem.getItemSerial()) && item
                        .isQuantityModifiable());
               
                // if the item has related items, then the quantity button
                // should be disabled.
                SaleReturnLineItemIfc[] relatedLineItems = lineItem.getRelatedItemLineItems();
                if (relatedLineItems != null && relatedLineItems.length != 0)
                {
                    quantityModifiable = false;
                }
                setButtonState(buttonState, ACTION_QUANTITY, quantityModifiable); 
                
                //Set Quantity button state to false for a serialised item in order transaction
                if (transaction instanceof OrderTransactionIfc ||
                        (transType == TransactionIfc.TYPE_LAYAWAY_INITIATE)
                            || (transType == TransactionIfc.TYPE_LAYAWAY_PAYMENT)
                            || (transType == TransactionIfc.TYPE_LAYAWAY_COMPLETE)
                            || (transType == TransactionIfc.TYPE_LAYAWAY_DELETE))
                {
                    if (item != null && item.isSerializedItem())
                    {
                        setButtonState(buttonState, ACTION_QUANTITY, false);
                    }
                }

                setButtonState(buttonState, ACTION_GIFT_REGISTRY, item.isRegistryEligible());
                boolean isSerializable = (Util.isObjectEqual(lineItem.getItemQuantityDecimal(),
                        Util.I_BIG_DECIMAL_ONE));

                setButtonState(buttonState, ACTION_SERIAL_NUMBER, isSerializable);
            }
        } // not order line item

        // Set button for the sale of gift certificates and giftcards.
        if (lineItem.getPLUItem() instanceof GiftCardPLUItemIfc
                || lineItem.getPLUItem() instanceof GiftCertificateItemIfc)
        {
            setButtonState(buttonState, ACTION_TAX, false);
            // need to add this even though it is stored in the db with
            // the item number
            // if the certificate is add through the menu and not an
            // item number it will not have a
            // FIELD_ITEM_QUANTITY_KEY_PROHIBIT_FLAG
            setButtonState(buttonState, ACTION_QUANTITY, false);
            setButtonState(buttonState, ACTION_SERIAL_NUMBER, false);
        }

        if (lineItem.isReturnLineItem())
        {
            // turn off gift receipt if a return item
            setButtonState(buttonState, ACTION_GIFT_RECEIPT, false);
            if (isRetrievedReturnItem(lineItem))
            {
                setButtonState(buttonState, ACTION_QUANTITY, false);
                setButtonState(buttonState, ACTION_GIFT_REGISTRY, false);
            }
            boolean isSerializable = (Util.isObjectEqual(lineItem.getItemQuantityDecimal(),
                    Util.I_BIG_DECIMAL_ONE.negate()));

            setButtonState(buttonState, ACTION_SERIAL_NUMBER, isSerializable);
        }
    }

    /**
     * Maintain a single instance of expected state of a button action.  This will b
     * on the model when the final state has been determined.
     */
    private void setButtonState(HashMap<String, Boolean> buttonState, String actionName, boolean enabled)
    {
        if (buttonState.containsKey(actionName))
        {
            buttonState.remove(actionName);
        }
        buttonState.put(actionName, new Boolean(enabled));
    }

    /**
     * Replace or add the setting for a button in the button model.
     */
    private NavigationButtonBeanModel replaceAddEnableButton(NavigationButtonBeanModel localButtonBeanModel, String actionName, boolean enabled)
    {
        NavigationButtonBeanModel nbbModel = new NavigationButtonBeanModel();

        ButtonSpec[] specs = localButtonBeanModel.getModifyButtons();
        for(int i = 0; i < specs.length; i++)
        {
            if (!specs[i].getActionName().equals(actionName))
            {
                nbbModel.setButtonEnabled(specs[i].getActionName(), specs[i].getEnabled());
            }
        }
        nbbModel.setButtonEnabled(actionName, enabled);

        return nbbModel;
    }

    /**
     * Verifies that the item is eligible for discounts.
     *
     * @param line item
     * @return true if discounts allowed
     */
    protected boolean isDiscountEligible(SaleReturnLineItemIfc lineItem)
    {
        return DiscountUtility.isDiscountEligible(lineItem);
    }

    /**
     * Verifies the item was retrieved from a transaction (with receipt).
     *
     * @param line item
     * @return true if item retrieved from prior transaction
     */
    protected boolean isRetrievedReturnItem(SaleReturnLineItemIfc lineItem)
    {
        boolean retrievedItem = false;

        if (lineItem.isReturnLineItem())
        {
            retrievedItem = (lineItem.getReturnItem().getOriginalTransactionID() != null);
        }
        return (retrievedItem);
    }
}
