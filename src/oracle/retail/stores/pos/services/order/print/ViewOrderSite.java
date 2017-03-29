/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/print/ViewOrderSite.java /main/12 2011/02/16 09:13:28 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
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
 *    6    360Commerce 1.5         7/12/2007 3:11:12 PM   Anda D. Cadar   call
 *         toFormattedString(locale)
 *    5    360Commerce 1.4         4/25/2007 8:52:19 AM   Anda D. Cadar   I18N
 *         merge
 *
 *    4    360Commerce 1.3         1/22/2006 11:45:15 AM  Ron W. Haight
 *         removed references to com.ibm.math.BigDecimal
 *    3    360Commerce 1.2         3/31/2005 4:30:45 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:26:45 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:15:32 PM  Robert Pearse
 *
 *   Revision 1.5  2004/07/22 00:06:34  jdeleau
 *   @scr 3665 Standardize on I18N standards across all properties files.
 *   Use {0}, {1}, etc instead of remaining <ARG> or #ARG# variables.
 *
 *   Revision 1.4  2004/07/15 01:24:03  jdeleau
 *   @scr 2495 Fill up the TotalBeanModel with the correct data
 *   for the special orders service.
 *
 *   Revision 1.3  2004/02/12 16:51:27  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:48  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:19  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:03:56   CSchellenger
 * Initial revision.
 *
 *    Rev 1.0   Apr 29 2002 15:11:46   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:41:56   msg
 * Initial revision.
 *
 *    Rev 1.1   Jan 16 2002 17:27:58   dfh
 * replacing orderdetailspec with orderspec screens, some cleanup
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.0   Sep 24 2001 13:01:22   MPM
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:10:42   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.print;


import java.math.BigDecimal;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.order.common.OrderCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.LineItemsModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;
import oracle.retail.stores.pos.ui.beans.StatusBeanModel;
import oracle.retail.stores.pos.ui.beans.TotalsBeanModel;

/**
 * Displays the Order Detail screen or mails a Print letter.
 * 
 * @version $Revision: /main/12 $
 */
public class ViewOrderSite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = 2429304739301850947L;

    public static final String SITENAME = "ViewOrderSite";

    /**
     * Check cargo for viewOrder value. If true display the Order Detail screen
     * else mail Print letter.
     * 
     * @param bus the bus arriving at this site
     */
    @Override
    public void arrive(BusIfc bus)
    {
        OrderCargo      cargo = (OrderCargo) bus.getCargo();

        if (cargo.viewOrder())
        {
            //Initialize Variables
            OrderIfc order = cargo.getOrder();
            POSUIManagerIfc         ui      = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
            PromptAndResponseModel  prModel = new PromptAndResponseModel();
            StatusBeanModel         sbModel = new StatusBeanModel();

            LineItemsModel      beanModel       = new LineItemsModel();
            beanModel.setLineItems(cargo.getOrder().getLineItems());

            TotalsBeanModel             tbm = new TotalsBeanModel();
            tbm.setTotals(order.getTotals());

            //PromptAndResponseModel Settings Configured
            prModel.setArguments(cargo.getOrder().getOrderID());

            //StatusBeanModel Settings Configured
            sbModel.setCustomerName(cargo.getOrder().getCustomer().getFirstLastName());

            //OrderBeanModel Configured
            beanModel.setStatusBeanModel(sbModel);
            beanModel.setPromptAndResponseModel(prModel);
            TotalsBeanModel totalsModel = new TotalsBeanModel();
            CurrencyIfc discount = cargo.getOrder().getTotals().getDiscountTotal();
            CurrencyIfc tax = cargo.getOrder().getTotals().getTaxTotal();
            CurrencyIfc grandTotal =cargo.getOrder().getTotals().getGrandTotal();
            CurrencyIfc subTotal = grandTotal.subtract(tax).add(discount);
            BigDecimal quantity = cargo.getOrder().getTotals().getQuantityTotal();
            totalsModel.setDiscountTotal(discount.toFormattedString());
            totalsModel.setTaxTotal(tax.toFormattedString());
            totalsModel.setSubtotal(subTotal.toFormattedString());
            totalsModel.setGrandTotal(grandTotal.toFormattedString());
            totalsModel.setQuantityTotal(quantity);
            beanModel.setTotalsBeanModel(totalsModel);
            //Display Screen
            ui.showScreen(POSUIManagerIfc.PRINT_ORDER, beanModel);
        }
        else
        {
            bus.mail(new Letter(CommonLetterIfc.PRINT), BusIfc.CURRENT);
        }
    }
}
