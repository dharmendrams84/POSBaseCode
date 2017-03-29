/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/pickup/ConfirmSelectionSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:32 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    nkgautam  10/12/10 - fixed missing discount amount totals
 *    acadar    06/10/10 - use default locale for currency display
 *    acadar    06/09/10 - XbranchMerge acadar_tech30 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    6    360Commerce 1.5         7/12/2007 3:11:11 PM   Anda D. Cadar   call
 *         toFormattedString(locale)
 *    5    360Commerce 1.4         4/25/2007 8:52:19 AM   Anda D. Cadar   I18N
 *         merge
 *
 *    4    360Commerce 1.3         1/22/2006 11:45:14 AM  Ron W. Haight
 *         removed references to com.ibm.math.BigDecimal
 *    3    360Commerce 1.2         3/31/2005 4:27:30 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:20:22 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:10:11 PM  Robert Pearse
 *
 *   Revision 1.3  2004/02/12 16:51:26  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:37  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:19  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:03:48   CSchellenger
 * Initial revision.
 *
 *    Rev 1.1   Aug 26 2002 11:05:20   jriggins
 * Now make calls to CurrencyIfc.toFormattedString() instead of CurrencyIfc.toString().  Replaced concat of customer name in favor of formatting the text from the CustomerAddressSpec.CustomerName bundle in customerText.
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.0   Apr 29 2002 15:11:50   msg
 * Initial revision.
 *
 *    Rev 1.1   25 Apr 2002 18:52:04   pdd
 * Removed unnecessary BigDecimal instantiations.
 * Resolution for POS SCR-1610: Remove inefficient instantiations of BigDecimal
 *
 *    Rev 1.0   Mar 18 2002 11:41:38   msg
 * Initial revision.
 *
 *    Rev 1.4   Feb 06 2002 05:46:38   mpm
 * Modified to use IBM BigDecimal.
 * Resolution for POS SCR-1121: Employ IBM BigDecimal
 *
 *    Rev 1.3   05 Feb 2002 18:02:16   cir
 * Set the totals in the totals bean
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.2   29 Jan 2002 15:06:34   sfl
 * Clone the whole order to support the ESC in the
 * Tender Options screen during doing the tender
 * for a special order.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.1   26 Jan 2002 18:49:56   cir
 * Clone the order line items and save them in the cargo
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.0   15 Jan 2002 18:49:48   cir
 * Initial revision.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.1   14 Dec 2001 07:52:08   mpm
 * Handled change of getLineItems() to getOrderLineItems().
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.0   Sep 24 2001 13:01:18   MPM
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:10:40   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.pickup;


import java.util.Vector;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.OrderLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderIfc;

import oracle.retail.stores.domain.utility.LocaleUtilities;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;

import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.LineItemsModel;
import oracle.retail.stores.pos.ui.beans.StatusBeanModel;
import oracle.retail.stores.pos.ui.beans.TotalsBeanModel;
import java.math.BigDecimal;

//------------------------------------------------------------------------------
/**
    Displays the "Edit Item Status" screen.
    <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------

public class ConfirmSelectionSite extends PosSiteActionAdapter
{
    /**
     *
     */
    private static final long serialVersionUID = 4367502582348105887L;
    /** class name constant */
    public static final String SITENAME = "EditItemStatusSite";
    /** revision number for this class */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

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
        Visual presentation for the Edit Item Status screen.
        <p>
        @param bus the bus arriving at this site
    **/
    //--------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        //Initialize Variables
        PickupOrderCargo    cargo       = (PickupOrderCargo)bus.getCargo();
        POSUIManagerIfc     ui          = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        LineItemsModel      model       = new LineItemsModel();
        StatusBeanModel     sbModel     = new StatusBeanModel();

        OrderIfc order = cargo.getOrder();

        Vector itemsVector = new Vector();
        AbstractTransactionLineItemIfc[] allItems = order.getLineItems();

        // Save a copy of the existing order in the cargo
        cargo.setSavedOrder((OrderIfc)order.clone());

        for(int i=0; i<allItems.length; i++)
        {
            int status = ((SaleReturnLineItemIfc)allItems[i]).getOrderItemStatus().getStatus().getStatus();
            if (status == OrderLineItemIfc.ORDER_ITEM_STATUS_PICK_UP || status == OrderLineItemIfc.ORDER_ITEM_STATUS_CANCEL)
            {
                itemsVector.addElement(allItems[i]);
            }
        }

        AbstractTransactionLineItemIfc[] items = new AbstractTransactionLineItemIfc[itemsVector.size()];
        itemsVector.copyInto(items);

        // calculate the totals for the totals bean
        CurrencyIfc totalItemsDue = DomainGateway.getBaseCurrencyInstance();
        CurrencyIfc totalItemsTax = DomainGateway.getBaseCurrencyInstance();
        CurrencyIfc itemsTotal = DomainGateway.getBaseCurrencyInstance();
        BigDecimal totalQuantity = Util.I_BIG_DECIMAL_ZERO;

        for(int i=0; i<items.length; i++)
        {
            SaleReturnLineItemIfc srLineItem = (SaleReturnLineItemIfc)items[i];
            CurrencyIfc deposit = srLineItem.getOrderItemStatus().getDepositAmount();
            CurrencyIfc itemBalance = null;
            BigDecimal quantity = srLineItem.getItemQuantityDecimal();

            if (srLineItem.getOrderItemStatus().getStatus().getStatus() == OrderLineItemIfc.ORDER_ITEM_STATUS_CANCEL)
            {
                itemBalance = deposit.negate();
            }
            else
            {
                itemBalance = srLineItem.getItemPrice().getItemTotal().subtract(deposit);
                itemsTotal = itemsTotal.add(srLineItem.getItemPrice().getItemTotal());
                totalItemsTax = totalItemsTax.add(srLineItem.getItemPrice().getItemTax().getItemTaxAmount());
                totalQuantity = totalQuantity.add(quantity);
            }
            totalItemsDue = totalItemsDue.add(itemBalance);
        }


        // set the totals bean model
        TotalsBeanModel tbm = new TotalsBeanModel();
        //use store locale for display for currency
        tbm.setTaxTotal(totalItemsTax.toFormattedString());
        tbm.setBalanceDue(totalItemsDue.toFormattedString());
        tbm.setQuantityTotal(totalQuantity);
        tbm.setGrandTotal(itemsTotal.toFormattedString());
        tbm.setSubtotal((itemsTotal.subtract(totalItemsTax)).toFormattedString());
        tbm.setDiscountTotal(order.getTotals().getDiscountTotal().toFormattedString());
        model.setTotalsBeanModel(tbm);

        //StatusBeanModel Configure
        CustomerIfc customer = cargo.getOrder().getCustomer();

        UtilityManagerIfc utility =
          (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);
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
        model.setStatusBeanModel(sbModel);

        model.setLineItems(items);
        //Display Screen
        ui.showScreen(POSUIManagerIfc.CONFIRM_SELECTION, model);
    }

} // EditItemStatusSite
