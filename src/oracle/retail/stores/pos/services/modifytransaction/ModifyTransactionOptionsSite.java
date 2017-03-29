/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifytransaction/ModifyTransactionOptionsSite.java /main/34 2011/02/16 09:13:33 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    npoola    12/20/10 - action button texts are moved to CommonActionsIfc
 *    acadar    10/27/10 - changes to reset external order status when
 *                         canceling suspended transactions
 *    abondala  08/03/10 - disable external order button if the transaction
 *                         type is Retrun.
 *    acadar    07/30/10 - cleanup
 *    acadar    07/30/10 - disable External order when special order or layaway
 *    ohorne    07/07/10 - external order button is disabled in re-entry mode
 *    mchellap  06/30/10 - Billpay changes
 *    nkgautam  06/21/10 - bill pay changes
 *    acadar    06/08/10 - changes for signature capture, disable txn send, and
 *                         discounts
 *    npoola    06/03/10 - merged with the latest code base
 *    npoola    06/02/10 - removed the training mode increment id dependency
 *    abondala  06/01/10 - Siebel integration changes
 *    abondala  05/28/10 - Installer changes and CO Report changes
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    npoola    12/27/09 - reverted back the Layway button disable logic and
 *                         code cleaup
 *    npoola    12/23/09 - removed the layawayEnabled flag set to false for all
 *                         the transactions
 *    npoola    12/17/09 - merged with latest code
 *    npoola    12/17/09 - added the instance method to check the transaction
 *                         type
 *    npoola    12/17/09 - moved the code to the SaleReturnTransactionIfc
 *                         method hasServiceItems()
 *    npoola    12/16/09 - replaced the constant types
 *    npoola    12/16/09 - disable the layway option button if the line items
 *                         contain non merchandise items
 *    nkgautam  11/25/09 - Disabling the Layaway button when a transaction is
 *                         already in progess
 *    jswan     05/27/09 - Modified to disable the gift registry and gift
 *                         reciept buttons for order intitiate transactions.
 *    blarsen   04/24/09 - disabling more buttons when in re-entry mode
 *    vikini    02/28/09 - Changing to correct reference to FindBasket button
 *    aariyer   02/17/09 - Added internationalization for Find Basket.
 *    aariyer   02/05/09 - Installer changes for Item basket
 *    vikini    02/05/09 - Change in Item Basket install
 *    aariyer   02/04/09 - Files for Item Basket Feature
 *    vikini    02/03/09 - Incorporating Code review Comments
 *    vikini    02/03/09 - Disable the ItemBasket button
 *    mchellap  09/30/08 - Updated copy right header
 *
 *     $Log:
 *      5    360Commerce 1.4         5/7/2007 4:12:05 PM    Alan N. Sinton  CR
 *           26483 - Modified to use the Gateway.getBooleanProperty() method.
 *      4    360Commerce 1.3         4/30/2007 3:45:30 PM   Alan N. Sinton  Merge
 *            from v12.0_temp.
 *      3    360Commerce 1.2         3/31/2005 4:29:05 PM   Robert Pearse
 *      2    360Commerce 1.1         3/10/2005 10:23:35 AM  Robert Pearse
 *      1    360Commerce 1.0         2/11/2005 12:12:41 PM  Robert Pearse
 *     $
 *     Revision 1.7  2004/08/23 18:08:34  rsachdeva
 *     @scr 6791 Transaction Level Send
 *
 *     Revision 1.6  2004/08/20 13:51:36  rsachdeva
 *     @scr 6791 Transaction Level Send
 *
 *     Revision 1.5  2004/06/28 16:53:43  aschenk
 *     @scr 4864 - Added Gift receipt option to Transaction menu
 *
 *     Revision 1.4  2004/06/03 19:01:22  kll
 *     @scr 5321: do not allow gift registration on returned items
 *
 *     Revision 1.3  2004/02/12 16:51:09  mcs
 *     Forcing head revision
 *
 *     Revision 1.2  2004/02/11 21:51:48  rhafernik
 *     @scr 0 Log4J conversion and code cleanup
 *
 *     Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *     updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:02:14   CSchellenger
 * Initial revision.
 *
 *    Rev 1.1   Jul 18 2003 15:26:54   sfl
 * Disable the Layaway button when transaction contains send items.
 * Resolution for POS SCR-2430: Layaway with send transaction issue
 *
 *    Rev 1.0   Apr 29 2002 15:14:16   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:38:26   msg
 * Initial revision.
 *
 *    Rev 1.0   Sep 21 2001 11:30:34   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:09:30   msg
 * header update
 * ===========================================================================
 */

package oracle.retail.stores.pos.services.modifytransaction;

import oracle.retail.stores.domain.employee.RoleFunctionIfc;
import oracle.retail.stores.domain.manager.ifc.SecurityManagerIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;
import oracle.retail.stores.pos.services.common.CommonActionsIfc;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;

/**
 * This site displays the transaction options menu.
 * 
 * @version $Revision: /main/34 $
 */
public class ModifyTransactionOptionsSite extends PosSiteActionAdapter
{
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -4788945885571894144L;

    /**
     * Tax inclusive flag
     */
    protected boolean taxInclusiveFlag = Gateway.getBooleanProperty("application", "InclusiveTaxEnabled", false);

    /**
     * Shows the screen for all the options for ModifyTransaction
     * 
     * @param bus Service Bus
     */
    @Override
    public void arrive(BusIfc bus)
    {
        // get the POS UI manager
        POSUIManagerIfc uiManager = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        ModifyTransactionCargo cargo = (ModifyTransactionCargo)bus.getCargo();

        // This is the void rule:
        //
        //      1. If there is a transaction of any sort, transaction void cannot be perfomed.
        //
        // These are the suspend/retrieve rules:
        //
        //      1. If the system is not running POS (i.e. CrossReach),
        //         the suspend/retrieve buttons will be disabled.
        //
        //      2. If there is a transaction in the cargo, then the suspend
        //         will be enabled and the retrieve disabled.
        //
        //      3. If there is NOT a transaction in the cargo, then the suspend
        //         will be disabled and the retrieve enabled.
        //
        // This code sets the booleans to the correct vales before setting up the
        // models.

        // Initialize the booleans
        boolean voidEnabled     = false;
        boolean suspendEnabled  = false;
        boolean retrieveEnabled = false;
        boolean layawayEnabled  = true;
        boolean giftRegistryEnabled  = true;
        boolean giftReceiptEnabled  = true;
        boolean sendEnabled = true;
        boolean specialOrderEnabled = false;
        boolean itemBasketEnabled = false; // to be changed to True
        boolean externalOrderEnabled = false; // to be changed to True

        RetailTransactionIfc transaction = cargo.getTransaction();

        boolean isReEntryMode = cargo.getRegister().getWorkstation().isTransReentryMode();

        itemBasketEnabled = Gateway.getBooleanProperty("application", "ItemBasketEnabled", false);
        externalOrderEnabled = Gateway.getBooleanProperty("application", "ExternalOrderEnabled", false);
        boolean billPayEnabled = false ;
        if(isBillPaySupported(bus))
        {
            billPayEnabled = true;
        }


        // If there is not a transaction, set void to true
        if (transaction == null)
        {
            voidEnabled = true;
            specialOrderEnabled = true;
        }

        // If there is not a transaction set retrieve to true
        if (transaction == null)
        {
            retrieveEnabled = true;
            giftReceiptEnabled = false;
        }
        // If there is a transaction in progress with atleast one line item present,
        // then set suspend to true
        else if(transaction.getLineItemsVector()!=null && transaction.getLineItemsVector().size()>0)
        {
            suspendEnabled  = true;
        }

        if (transaction != null)
        {
            // If return items, send items, CrossReach items, or layaway in progress
            // disable Layaway button
            if ((transaction.containsOrderLineItems() ||
                transaction.getTransactionType() != TransactionIfc.TYPE_SALE ||
                transaction.getTransactionType() == TransactionIfc.TYPE_LAYAWAY_INITIATE ||
                ((SaleReturnTransactionIfc)transaction).hasSendItems() ||
                transaction.getTransactionTotals().isTransactionLevelSendAssigned() ||
                ((SaleReturnTransactionIfc)transaction).containsReturnLineItems()))
            {
                layawayEnabled = false;
            }

            // do not allow gift registration on returned items or new orders
            if (((SaleReturnTransactionIfc)transaction).containsReturnLineItems() ||
                      transaction.getTransactionType() == TransactionIfc.TYPE_ORDER_INITIATE)
            {
                giftRegistryEnabled = false;
                giftReceiptEnabled = false;
            }

            if ((transaction.getTransactionTotals().isTransactionLevelSendAssigned()) ||
                (transaction.getTransactionType() == TransactionIfc.TYPE_LAYAWAY_INITIATE) ||
                (transaction.getTransactionType() == TransactionIfc.TYPE_LAYAWAY_PAYMENT) ||
                (transaction.getTransactionType() == TransactionIfc.TYPE_LAYAWAY_COMPLETE) ||
                (transaction.getTransactionType() == TransactionIfc.TYPE_LAYAWAY_DELETE) ||
                (transaction.getTransactionType() == TransactionIfc.TYPE_ORDER_INITIATE))
           {
               sendEnabled = false;
           }

            if ((transaction.getTransactionType() == TransactionIfc.TYPE_LAYAWAY_INITIATE) ||
                    (transaction.getTransactionType() == TransactionIfc.TYPE_LAYAWAY_PAYMENT) ||
                    (transaction.getTransactionType() == TransactionIfc.TYPE_LAYAWAY_COMPLETE) ||
                    (transaction.getTransactionType() == TransactionIfc.TYPE_LAYAWAY_DELETE)||
                    (transaction.getTransactionType() == TransactionIfc.TYPE_RETURN)||
                    (transaction.getTransactionType() == TransactionIfc.TYPE_ORDER_INITIATE))
            {
                   externalOrderEnabled = false;
            }

            if(transaction.getIsItemBasketTransactionComplete())
            {
             itemBasketEnabled = false; // it Item basket trn done once, Donot enable the Item Basket Button
            }
           specialOrderEnabled = false;
           layawayEnabled = false;
           billPayEnabled = false;


           // If transaction has already external order, disable the external order button
           if (((SaleReturnTransactionIfc)transaction).hasExternalOrder())
           {
               externalOrderEnabled = false;
               //if externalorder has send package disable send button
               if (((SaleReturnTransactionIfc)transaction).hasExternalSendPackage())
               {
                   sendEnabled = false;
               }
           }
        }

        // limit functionality if in reentry mode
        if(isReEntryMode)
        {
          itemBasketEnabled = false;
          suspendEnabled = false;
          retrieveEnabled = false;
          billPayEnabled = false;
          externalOrderEnabled = false;
        }

        if(cargo.getRegister().getWorkstation().isTrainingMode())
        {
            specialOrderEnabled = false;
            layawayEnabled = false;
        }

        // Setup the models.
        POSBaseBeanModel          pModel = new POSBaseBeanModel();
        NavigationButtonBeanModel nModel = new NavigationButtonBeanModel();
        nModel.setButtonEnabled(CommonActionsIfc.VOID, voidEnabled);
        nModel.setButtonEnabled(CommonActionsIfc.SUSPEND, suspendEnabled);
        nModel.setButtonEnabled(CommonActionsIfc.RETRIEVE, retrieveEnabled);
        nModel.setButtonEnabled(CommonActionsIfc.LAYAWAY, layawayEnabled);
        nModel.setButtonEnabled(CommonActionsIfc.GIFT_REGISTRY, giftRegistryEnabled);
        nModel.setButtonEnabled(CommonActionsIfc.GIFT_RECEIPT, giftReceiptEnabled);
        nModel.setButtonEnabled(CommonActionsIfc.SEND, sendEnabled);
        nModel.setButtonEnabled(CommonActionsIfc.SPECIAL_ORDER, specialOrderEnabled);
        nModel.setButtonEnabled(CommonActionsIfc.ITEM_BASKET, itemBasketEnabled);
        nModel.setButtonEnabled(CommonActionsIfc.EXTERNAL_ORDER, externalOrderEnabled);
        nModel.setButtonEnabled(CommonActionsIfc.BILL_PAY, billPayEnabled);
        // disable the button in the VAT enabled environment
        if(taxInclusiveFlag)
        {
            nModel.setButtonEnabled(CommonActionsIfc.TAX, false);
        }
        pModel.setLocalButtonBeanModel(nModel);

        uiManager.showScreen(POSUIManagerIfc.TRANS_OPTIONS, pModel);
    }

    /**
     * This method checks whether the Billpay feature is supported or not.
     */
    public boolean isBillPaySupported(BusIfc bus)
    {
        boolean supported = false;

        try
        {
            ModifyTransactionCargo cargo = (ModifyTransactionCargo) bus.getCargo();

            // 1. Check whether user has access to billpay function
            SecurityManagerIfc securityManager = (SecurityManagerIfc) Gateway.getDispatcher().getManager(
                    SecurityManagerIfc.TYPE);
            boolean access = securityManager.checkAccess(cargo.getAppID(), RoleFunctionIfc.BILLPAY);

            // 2. Check wthether billpay is enabled or not
            Boolean installedFlag = new Boolean(Gateway.getProperty("application", "BillPayEnabled", "false"));

            // 3. Check whether the training mode option is on or off
            boolean isTrainingMode = cargo.getRegister().getWorkstation().isTrainingMode();

            if (access && installedFlag.booleanValue() && !isTrainingMode)
            {
                supported = true;
            }
        }
        catch (Exception e)
        {
            logger.warn("Error while getting Billpay Supported Flags");
            supported = false;
        }

        return supported;
    }

}
