/* ===========================================================================
* Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returns/returncommon/CheckForReturnableItemSite.java /rgbustores_13.4x_generic_branch/7 2012/01/27 11:13:49 mjwallac Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    mjwallac  01/27/12 - prevent returns from non-completed transactions
 *    jswan     08/17/11 - Modified to prevent the return of Gift Cards as
 *                         items and part of a transaction. Also cleaned up
 *                         references to gift cards objects in the return
 *                         tours.
 *    blarsen   07/28/11 - Auth timeout parameters delete in 13.4. These were
 *                         moved into the payment technician layer.
 *    cgreene   07/26/11 - removed tenderauth and giftcard.activation tours and
 *                         financialnetwork interfaces.
 *    asinton   06/29/11 - Refactored to use EntryMethod and
 *                         AuthorizationMethod enums.
 *    cgreene   05/27/11 - move auth response objects into domain
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    mpbarnet  03/17/10 - Checkin
 *    mpbarnet  03/17/10 - Removed comment identifying BugDB bug number.
 *    mpbarnet  03/16/10 - In arrive(), display error dialog if no gift card
 *                         information is returned from the authorizer for a
 *                         gift card sold while offline.
 *    dwfung    03/10/10 - Handling Training Mode Requests
 *    abondala  01/03/10 - update header date
 *    asinton   05/22/09 - Added comments to the newly added code.
 *    asinton   05/22/09 - Changed the logic to iterate thru line items in an
 *                         order to verify that items are picked up.
 *    cgreene   03/20/09 - keep kit components off receipts by implementing new
 *                         method getLineItemsExceptExclusions
 *    cgreene   02/05/09 - removed bad unused import
 *
 * ===========================================================================
 * $Log:
 *    6    360Commerce 1.5         6/11/2008 6:09:52 PM   Deepti Sharma
 *         CR-32029 Added checks for offline/timeout for gift card inquiry
 *         response.Code reviewed by Maisa Camargo
 *    5    360Commerce 1.4         4/28/2008 7:12:34 PM   Alan N. Sinton  CR
 *         31502: Fixed many gift card flows with respect to ISD
 *         authorization.  code was reviewed by Christian Greene.
 *    4    360Commerce 1.3         4/25/2007 8:52:15 AM   Anda D. Cadar   I18N
 *         merge
 *
 *    3    360Commerce 1.2         3/31/2005 4:27:25 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:20:08 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:09:56 PM  Robert Pearse
 *
 *   Revision 1.10.2.1  2004/10/22 17:08:37  cdb
 *   @scr 7442 Corrected for instance where only one transaction for the customer was retrieved.
 *
 *   Revision 1.10  2004/07/27 16:10:18  mweis
 *   @scr 1934 Pressing Enter from Invalid Return opens wrong screen
 *
 *   Revision 1.9  2004/07/24 03:41:34  blj
 *   @scr 0 no change
 *
 *   Revision 1.8  2004/07/07 18:17:16  blj
 *   @scr 5966 - resolution
 *
 *   Revision 1.7  2004/03/15 15:16:52  baa
 *   @scr 3561 refactor/clean item size code, search by tender changes
 *
 *   Revision 1.6  2004/02/27 19:51:16  baa
 *   @scr 3561 Return enhancements
 *
 *   Revision 1.5  2004/02/17 20:40:28  baa
 *   @scr 3561 returns
 *
 *   Revision 1.4  2004/02/12 20:41:40  baa
 *   @scr 0 fixjavadoc
 *
 *   Revision 1.3  2004/02/12 16:51:45  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:30  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:20  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:05:46   CSchellenger
 * Initial revision.
 *
 *    Rev 1.1   Aug 14 2002 15:40:22   jriggins
 * Replaced situation in which we were swapping phrases in part of a common dialog.  Instead, two distinct dialogs have been created.
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.0   Apr 29 2002 15:06:32   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:45:04   msg
 * Initial revision.
 *
 *    Rev 1.4   Mar 10 2002 18:01:14   mpm
 * Externalized text in dialog messages.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.3   10 Mar 2002 11:48:20   pjf
 * Maintain kit inventory at header level.
 * Resolution for POS SCR-1444: Selling then returning a kit does not upadate the inventory count
 * Resolution for POS SCR-1503: When all kit items are returned and attempt to retrieve trans no error displays
 *
 *    Rev 1.2   Feb 05 2002 16:43:12   mpm
 * Modified to use IBM BigDecimal.
 * Resolution for POS SCR-1121: Employ IBM BigDecimal
 *
 *    Rev 1.1   10 Dec 2001 12:29:38   jbp
 * keeps customer from current transaction when returning from an origional transaction.
 * Resolution for POS SCR-418: Return Updates
 *
 *    Rev 1.0   Sep 21 2001 11:24:30   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:12:28   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returncommon;

import java.util.Vector;

import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.OrderLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderConstantsIfc;
import oracle.retail.stores.domain.transaction.OrderTransaction;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionConstantsIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.ado.ADOException;
import oracle.retail.stores.pos.ado.utility.Utility;
import oracle.retail.stores.pos.ado.utility.UtilityIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.returns.returncustomer.ReturnCustomerCargo;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

/**
 * This site checks the retrieved transaction to determine if it contains any
 * items that can be returned.
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/7 $
 */
public class CheckForReturnableItemSite extends PosSiteActionAdapter implements NoTransactionsErrorIfc
{

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -6915898128842290841L;

    /**
       revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/7 $";

    /**
     * Check the retrived trans action to determine if it contains any items
     * that can be returned.
     *
     * @param bus Service Bus
     */
    @Override
    public void arrive(BusIfc bus)
    {

        // Get the cargo
        ReturnableItemCargoIfc cargo = (ReturnableItemCargoIfc)bus.getCargo();

        // Loop through the line items to see if there are returnable items.
        boolean returnableItems    = false;
        boolean saleItemsAvailable = false;
        SaleReturnTransactionIfc tran = cargo.getOriginalTransaction();

        // Get the list of items from the transaction that are return candidates.  Order
        // Line Items must have a particular state to qualify.  All Sale Return Line Items
        // are added to the initial list.
        Vector<AbstractTransactionLineItemIfc> items = new Vector<AbstractTransactionLineItemIfc>();
        OrderTransaction orderTransaction = null;
        int orderType = 0;
        int transactionType = 0;
        if (cargo.getOriginalTransaction() instanceof OrderTransaction)
        {
            orderTransaction = (OrderTransaction)cargo.getOriginalTransaction();
            orderType = orderTransaction.getOrderType();
            transactionType = orderTransaction.getTransactionType();
            /*
             * For order transactions the line items are returnable only if the item's status indicates that
             * the customer has taken possession of the item.  For TYPE_ORDER_INITIATE transactions an item
             * was taken if it's disposition code is ORDER_ITEM_DISPOSITION_SALE (sometimes called a "carry item").
             * For other order transactions, partial and complete, need to verify that the item's status is picked up.
             */
            if (orderType == OrderConstantsIfc.ORDER_TYPE_ON_HAND && transactionType == TransactionConstantsIfc.TYPE_ORDER_INITIATE)
            {
                // When the transaction type is TYPE_ORDER_INITIATE check each item's disposition to verify
                // that the customer has taken the item in this transaction. If so, then add to the list.
                AbstractTransactionLineItemIfc[] lineItems = cargo.getOriginalTransaction().getLineItems();
                for (int i = 0; i < lineItems.length; i++)
                {
                    SaleReturnLineItemIfc item = (SaleReturnLineItemIfc)lineItems[i];
                    if (item.getOrderItemStatus().getItemDispositionCode() == OrderLineItemIfc.ORDER_ITEM_DISPOSITION_SALE)
                    {
                        items.add(item);
                    }
                }
            }
            else if(transactionType != TransactionConstantsIfc.TYPE_ORDER_INITIATE)
            {
                // For other order transaction types verify that the status the items is
                // ORDER_ITEM_STATUS_PICKED_UP If so, then add to the list.
                AbstractTransactionLineItemIfc[] lineItems = cargo.getOriginalTransaction().getLineItems();
                for (int i = 0; i < lineItems.length; i++)
                {
                    SaleReturnLineItemIfc item = (SaleReturnLineItemIfc)lineItems[i];
                    if (item.getOrderItemStatus().getStatus().getStatus() == OrderLineItemIfc.ORDER_ITEM_STATUS_PICKED_UP)
                    {
                        items.add(item);
                    }
                }
            }
        }
        else
        {
        	items = tran.getLineItemsVector();
        }
        
        // Iterate throu the list to see if there are any valid return items.
        for(int i = 0; i < items.size(); i++)
        {
            SaleReturnLineItemIfc srli = (SaleReturnLineItemIfc)items.elementAt(i);

            // Only Kit Component Items can be returned.
            if (!srli.isKitHeader())
            {
                // Only items from completed transactions can be returned
                if(!(tran.getTransactionStatus()==TransactionIfc.STATUS_COMPLETED))
                {
                    returnableItems = false;
                    break;
                }
                                
                // If This transaction was retrieved by tender type
                // make sure that the item used for search is returnable
                if (cargo.getSearchCriteria() != null && cargo.isSearchByTender() &&
                    srli.getPLUItemID().equals(cargo.getSearchCriteria().getItemID()))
                {
                   String itemSize = cargo.getSearchCriteria().getItemSizeCode();
                   boolean isSizeRequired = srli.getPLUItem().isItemSizeRequired();

                   // check If the exact  item/size used for searching the transaction is returnable
                   if ((!isSizeRequired ||  (isSizeRequired && srli.getItemSizeCode().equals(itemSize))) &&
                      srli.isReturnable())
                   {
                       returnableItems = true;
                       break;
                   }
                }
                else
                {
                   if (srli.isReturnable())
                   {
                      returnableItems = true;
                      break;
                   }
                }
                    
                // Check to see if this item has quantities sold.  If the so, this will
                // affect the error text shown to the operator in case there are no
                // return items.
                if (srli.getItemQuantityDecimal().signum() > 0)
                {
                    saleItemsAvailable = true;
                }
            }
        }

        if (returnableItems)
        {
            bus.mail(new Letter(CommonLetterIfc.SUCCESS), BusIfc.CURRENT);
        }
        else
        {
            // Get the ui manager
            POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);

            // Use the "generic dialog bean".
            DialogBeanModel model = new DialogBeanModel();

            // Display no returnable items error
            if (saleItemsAvailable)
            {
                model.setResourceID(INVALID_TRANSACTION_NO_QUANTITIES);
                model.setButtonLetter(DialogScreensIfc.BUTTON_OK, "Ok");
            }
            else if (cargo instanceof ReturnCustomerCargo &&
                    ((ReturnCustomerCargo)cargo).getTransactionSummary().length > 1 )
            {
                model.setResourceID(INVALID_RETURN_ITEMS);
                model.setButtonLetter(DialogScreensIfc.BUTTON_OK, "Failure2");
            }
            else
            {
                model.setResourceID(INVALID_RETURN_ITEMS);
                model.setButtonLetter(DialogScreensIfc.BUTTON_OK, "Ok");
            }

            model.setType(DialogScreensIfc.ERROR);

            // set and display the model
            ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, model);
        }

    }

    /**
     * Returns the appropriate UtilityIfc implementation
     *
     * @return
     */
    protected UtilityIfc getUtility()
    {
        UtilityIfc util = null;
        try
        {
            util = Utility.createInstance();
        }
        catch (ADOException e)
        {
            logger.error(e);
            throw new RuntimeException("Configuration problem: could not create instance of UtilityIfc");
        }
        return util;
    }
}
