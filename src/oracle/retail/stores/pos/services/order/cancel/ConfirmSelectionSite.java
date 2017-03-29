/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/cancel/ConfirmSelectionSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:34 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    nkgautam  10/27/10 - fixed missing discount amount totals
 *    acadar    06/10/10 - use default locale for currency display
 *    acadar    06/09/10 - XbranchMerge acadar_tech30 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    nkgautam  04/17/09 - Changes to display correct transaction info(total,
 *                         sub-total, tax, qty) for cancel order
 *    mchellap  04/13/09 - Showing negated total amounts in Confirm Selection
 *                         screen
 *    mahising  03/16/09 - Fixed issue to display total, sub-total, qty and tax
 *                         at status panel for cancel PDO transaction
 *                         confirmation
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:30 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:20:22 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:10:11 PM  Robert Pearse
 *
 *   Revision 1.4  2004/10/06 02:44:24  mweis
 *   @scr 7012 Special and Web Orders now have Inventory.
 *
 *   Revision 1.3  2004/02/12 16:51:21  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:46  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:03:24   CSchellenger
 * Initial revision.
 *
 *    Rev 1.1   Aug 22 2002 13:51:00   jriggins
 * Replaced concat of customer name in favor of formatting the text from the CustomerAddressSpec.CustomerName bundle in customerText.
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.0   Apr 29 2002 15:13:26   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:40:46   msg
 * Initial revision.
 *
 *    Rev 1.2   31 Jan 2002 11:04:06   sfl
 * Cloned the OrderIfc object to support ESC from
 * Tender, and, put conditional checking so that
 * the transaction ID will not be incremented until
 * the tender is actually done.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.1   Jan 17 2002 21:24:06   dfh
 * add own label
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.0   Jan 17 2002 21:23:14   dfh
 * Initial revision.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.cancel;

import java.math.BigDecimal;
import java.util.Vector;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.ItemPriceIfc;
import oracle.retail.stores.domain.lineitem.OrderLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.domain.utility.LocaleUtilities;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.order.pickup.PickupOrderCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.LineItemsModel;
import oracle.retail.stores.pos.ui.beans.StatusBeanModel;
import oracle.retail.stores.pos.ui.beans.TotalsBeanModel;

//------------------------------------------------------------------------------
/**
 * Displays the confirm selection screen.
 * <P>
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 **/
//------------------------------------------------------------------------------
public class ConfirmSelectionSite extends PosSiteActionAdapter
{
    /**
     *
     */
    private static final long serialVersionUID = -7456995849548850344L;

    /** class name constant */
    public static final String SITENAME = "ConfirmSelectionSite";

    /** revision number for this class */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * Customer name bundle tag
     **/
    protected static final String CUSTOMER_NAME_TAG = "CustomerName";

    /**
     * Customer name default text
     **/
    protected static final String CUSTOMER_NAME_TEXT = "{0} {1}";

    //--------------------------------------------------------------------------
    /**
     * Visual presentation for the confirm selection screen.
     * <p>
     *
     * @param bus the bus arriving at this site
     **/
    //--------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        // Initialize Variables
        PickupOrderCargo cargo = (PickupOrderCargo)bus.getCargo();
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        LineItemsModel model = new LineItemsModel();
        StatusBeanModel sbModel = new StatusBeanModel();

        CurrencyIfc SubTotal = null;
        CurrencyIfc GrandTotal = null;
        CurrencyIfc discount = null;
        CurrencyIfc tax = null;
        BigDecimal qty = new BigDecimal("0.0");

        OrderIfc order = cargo.getOrder();
        cargo.setSavedOrder((OrderIfc)order.clone());

        Vector itemsVector = new Vector();
        AbstractTransactionLineItemIfc[] allItems = order.getLineItems();

        for (int i = 0; i < allItems.length; i++)
        {
            int status = ((SaleReturnLineItemIfc)allItems[i]).getOrderItemStatus().getStatus().getStatus();
            if (status == OrderLineItemIfc.ORDER_ITEM_STATUS_CANCEL)
            {
                itemsVector.addElement(allItems[i]);
                SaleReturnLineItemIfc saleReturnLineItem = (SaleReturnLineItemIfc)allItems[i];
                ItemPriceIfc itemPrice = saleReturnLineItem.getItemPrice();
                if(SubTotal != null)
                {
                  SubTotal = SubTotal.add(itemPrice.getExtendedSellingPrice());
                }
                else
                {
                  SubTotal = itemPrice.getExtendedSellingPrice();
                }
                if(tax != null)
                {
                   tax = tax.add(itemPrice.getItemTaxAmount());
                }
                else
                {
                  tax = itemPrice.getItemTaxAmount();
                }

                qty = qty.add(saleReturnLineItem.getQuantityReturnable());
            }

        }

        discount = order.getTotals().getDiscountTotal();
        GrandTotal = SubTotal.subtract(discount).add(tax);
        AbstractTransactionLineItemIfc[] items = new AbstractTransactionLineItemIfc[itemsVector.size()];
        itemsVector.copyInto(items);

        // StatusBeanModel Configure
        CustomerIfc customer = order.getCustomer();

        UtilityManagerIfc utility = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);
        Object parms[] = { customer.getFirstName(), customer.getLastName() };
        String pattern = utility.retrieveText("CustomerAddressSpec", BundleConstantsIfc.CUSTOMER_BUNDLE_NAME,
                CUSTOMER_NAME_TAG, CUSTOMER_NAME_TEXT);
        String customerName = LocaleUtilities.formatComplexMessage(pattern, parms);
        // display total,sub-total,tax and quantity detail at status panel
        TotalsBeanModel totalsModel = new TotalsBeanModel();



        //use store default locale for display of currency
        totalsModel.setTaxTotal(tax.negate().toFormattedString());
        totalsModel.setDiscountTotal(discount.toFormattedString());
        totalsModel.setSubtotal(SubTotal.negate().toFormattedString());
        totalsModel.setGrandTotal(GrandTotal.negate().toFormattedString());
        totalsModel.setQuantityTotal(qty);
        model.setTotalsBeanModel(totalsModel);
        sbModel.setCustomerName(customerName);

        // LineItemsModel Configure
        model.setStatusBeanModel(sbModel);

        model.setLineItems(items);
        // Display Screen
        ui.showScreen(POSUIManagerIfc.CONFIRM_SELECTION, model);
    }
}
