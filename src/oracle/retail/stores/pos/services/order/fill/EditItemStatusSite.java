/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/fill/EditItemStatusSite.java /main/13 2011/01/14 15:48:01 npoola Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    npoola    12/20/10 - action button texts are moved to CommonActionsIfc
 *    acadar    06/10/10 - use default locale for currency display
 *    acadar    06/09/10 - XbranchMerge acadar_tech30 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    5    360Commerce 1.4         7/3/2007 4:14:01 PM    Anda D. Cadar   call
 *         Currencyifc.toFormattedString(Locale)
 *    4    360Commerce 1.3         4/25/2007 8:52:19 AM   Anda D. Cadar   I18N
 *         merge
 *
 *    3    360Commerce 1.2         3/31/2005 4:27:52 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:21:12 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:10:44 PM  Robert Pearse
 *
 *   Revision 1.6  2004/07/15 01:26:09  jdeleau
 *   @scr 2495 Remove unused import
 *
 *   Revision 1.5  2004/07/15 01:24:03  jdeleau
 *   @scr 2495 Fill up the TotalBeanModel with the correct data
 *   for the special orders service.
 *
 *   Revision 1.4  2004/03/03 23:15:15  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.3  2004/02/12 16:51:23  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:49  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:03:36   CSchellenger
 * Initial revision.
 *
 *    Rev 1.1   Aug 26 2002 08:36:40   jriggins
 * Replaced concat of customer name in favor of formatting the text from the CustomerAddressSpec.CustomerName bundle in customerText.
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.0   Apr 29 2002 15:12:30   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:41:16   msg
 * Initial revision.
 *
 *    Rev 1.9   Mar 11 2002 16:33:24   dfh
 * disable Pickup and Canceled buttons, enable Filled button
 * Resolution for POS SCR-1546: In Order Options, Pickup status button should be disabled in Fill.
 *
 *    Rev 1.8   Mar 10 2002 18:00:34   mpm
 * Externalized text in dialog messages.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.7   Feb 04 2002 09:25:58   dfh
 * fix Filled button
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.6   Feb 04 2002 09:11:46   dfh
 * enable Filled button on Edit Item Status screen
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.5   Jan 25 2002 17:28:56   dfh
 * updates to prevent modifications to canceled, completed, voided orders
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.4   Jan 22 2002 22:12:20   dfh
 * updates for model, getlineitems, clone lineitems
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.3   Jan 18 2002 10:50:34   dfh
 * use edit item status screen model
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.2   Jan 10 2002 21:47:40   dfh
 * uses fill item status screen
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.1   14 Dec 2001 07:52:06   mpm
 * Handled change of getLineItems() to getOrderLineItems().
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.0   Sep 24 2001 13:01:10   MPM
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:10:34   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.fill;

// java imports
import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderConstantsIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.domain.utility.LocaleUtilities;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonActionsIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.order.common.OrderCargoIfc;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;
import oracle.retail.stores.pos.ui.beans.LineItemsModel;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;
import oracle.retail.stores.pos.ui.beans.StatusBeanModel;
import oracle.retail.stores.pos.ui.beans.TotalsBeanModel;

//------------------------------------------------------------------------------
/**

    Displays the edit item status screen for changing the order item
    status. Displays the error dialog screen is order status is Canceled,
    Completed, or Voided.

    @version $Revision: /main/13 $
**/
//------------------------------------------------------------------------------

public class EditItemStatusSite extends PosSiteActionAdapter
{
    /**
     *
     */
    private static final long serialVersionUID = 3056500087866837144L;

    /**
       class name constant
    **/
    public static final String SITENAME = "EditItemStatusSite";

    /**
       revision number for this class
    **/
    public static final String revisionNumber = "$Revision: /main/13 $";

    /**
       Constant for error message argument text.
    **/
    public static final String FILL = "Fill";

    /**
       Constant for error message screen id.
    **/
    public static final String CANNOT_MODIFY_ORDER = "CannotModifyOrder";

    /**
       Constant for error message argument text.
    **/
    public static final String COMPLETED = "Completed";

    /**
       Constant for error message argument text.
    **/
    public static final String CANCELED = "Canceled";

    /**
       Constant for error message argument text.
    **/
    public static final String VOIDED = "Voided";


     /**
       Customer name bundle tag
     **/
     protected static final String CUSTOMER_NAME_TAG = "CustomerName";
     /**
       Customer name default text
     **/
     protected static final String CUSTOMER_NAME_TEXT = "{0} {1}";

    //--------------------------------------------------------------------------
    /**
       Displays the edit item status screen to allow user to select items(s)
       from the order to change their status. Displays the error dialog screen
       is order status is Canceled, Completed, or Voided.

       @param bus the bus arriving at this site
    **/
    //--------------------------------------------------------------------------

    public void arrive(BusIfc bus)
    {
        //Initialize Variables
        OrderCargoIfc       cargo       = (OrderCargoIfc)bus.getCargo();
        POSUIManagerIfc     ui          = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        UtilityManagerIfc utility =
          (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
        LineItemsModel      lineModel   = new LineItemsModel();
        StatusBeanModel     sbModel     = new StatusBeanModel();
        OrderIfc            order       = (OrderIfc)cargo.getOrder();
        int                 status      =  order.getStatus().getStatus().getStatus();
        NavigationButtonBeanModel  localModel = new NavigationButtonBeanModel();

        // if not modifiable display error screen
        if (status == OrderConstantsIfc.ORDER_STATUS_CANCELED ||
            status == OrderConstantsIfc.ORDER_STATUS_COMPLETED ||
            status == OrderConstantsIfc.ORDER_STATUS_VOIDED)
        {
            // setup arg text strings for error screen
            String args[] = new String[4];
            args[0] = utility.retrieveDialogText("CannotModifyOrder.Fill",
                                                 FILL);
            args[1] = utility.retrieveDialogText("CannotModifyOrder.Completed",
                                                 COMPLETED);
            args[2] = args[1];

            // test if order canceled to re-use screen, change ui argument
            if (status == OrderConstantsIfc.ORDER_STATUS_CANCELED)
            {
                args[1] = utility.retrieveDialogText("CannotModifyOrder.Canceled",
                                                     CANCELED);
                args[2] = args[1];
            }
            else if (status == OrderConstantsIfc.ORDER_STATUS_VOIDED)
            {
                args[1] = utility.retrieveDialogText("CannotModifyOrder.Voided",
                                                     VOIDED);
                args[2] = args[1];
            }
            // setup and display the Cannot Modify Dialog screen
            DialogBeanModel model = new DialogBeanModel();
            model.setResourceID(CANNOT_MODIFY_ORDER);
            model.setButtonLetter(DialogScreensIfc.BUTTON_OK,CommonLetterIfc.FAILURE);            
            model.setType(DialogScreensIfc.ERROR);
            model.setArgs(args);
            ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, model);
        }
        else
        {
            //StatusBeanModel Configure
            // Create the string from the bundle.
            CustomerIfc customer = cargo.getOrder().getCustomer();

            Object parms[] = { customer.getFirstName(), customer.getLastName() };
            String pattern =
              utility.retrieveText("CustomerAddressSpec",
                                   BundleConstantsIfc.CUSTOMER_BUNDLE_NAME,
                                   CUSTOMER_NAME_TAG,
                                   CUSTOMER_NAME_TEXT);
            String customerName =
              LocaleUtilities.formatComplexMessage(pattern, parms);

            sbModel.setCustomerName(customerName);

            //LineItemsModel Configure
            lineModel.setStatusBeanModel(sbModel);

            // in order for the original line items statuses not to be altered the array is cloned
            AbstractTransactionLineItemIfc[] lineItems = order.getLineItems();
            AbstractTransactionLineItemIfc[] clonedLineItems = new AbstractTransactionLineItemIfc[lineItems.length];
            for(int i=0; i<lineItems.length; i++)
            {
               clonedLineItems[i] = (SaleReturnLineItemIfc)lineItems[i].clone();
            }

            lineModel.setLineItems(clonedLineItems);
            // enable Filled button
            localModel.setButtonEnabled(CommonActionsIfc.FILLED, true);
            // disable Pickup and Canceled buttons
            localModel.setButtonEnabled(CommonActionsIfc.PICK_UP, false);
            localModel.setButtonEnabled(CommonActionsIfc.CANCELED, false);
            lineModel.setLocalButtonBeanModel(localModel);
            TotalsBeanModel totalsModel = new TotalsBeanModel();
            CurrencyIfc discount = cargo.getOrder().getTotals().getDiscountTotal();
            CurrencyIfc tax = cargo.getOrder().getTotals().getTaxTotal();
            CurrencyIfc grandTotal =cargo.getOrder().getTotals().getGrandTotal();
            CurrencyIfc subTotal = grandTotal.subtract(tax).add(discount);
            //use default locale for currency display
            totalsModel.setDiscountTotal(discount.toFormattedString());
            totalsModel.setTaxTotal(tax.toFormattedString());
            totalsModel.setSubtotal(subTotal.toFormattedString());
            totalsModel.setGrandTotal(grandTotal.toFormattedString());
            totalsModel.setQuantityTotal(cargo.getOrder().getTotals().getQuantityTotal());
            lineModel.setTotalsBeanModel(totalsModel);
            //Display Screen
            ui.showScreen(POSUIManagerIfc.EDIT_ITEM_STATUS, lineModel);
        }
    }  // arrive
}
