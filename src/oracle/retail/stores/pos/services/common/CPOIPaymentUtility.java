/*===========================================================================
* Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved. 
* ===========================================================================
* $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/CPOIPaymentUtility.java /rgbustores_13.4x_generic_branch/14 2012/02/22 13:40:27 asinton Exp $
* ===========================================================================
* NOTES
* <other useful comments, qualifications, etc.>
*
* MODIFIED    (MM/DD/YY)
*    asinton   02/22/12 - allow setting the scrollingReceiptInSession flag for
*                         cases when we swipe a card to obtain token for
*                         return, without clearing this flag subsequant
*                         scrolling receipt operations fail.
*    icole     09/13/11 - Show Change Due
*    blarsen   09/12/11 - Moved displayChangeDueScreen() from
*                         ChangeDueOptionsUISite. Now all calls to
*                         PaymentManger for CustomerInteraction are in this
*                         utility.
*    cgreene   08/24/11 - create interfaces for customerinteraction objects
*    icole     07/15/11 - Correct Balance displayed on CPOI for Layaway tender,
*                         Bug 12747530
*    icole     07/08/11 - Remove DeviceExceptions related to Payment CPOI to be
*                         consistent with other Payment methods.
*    blarsen   06/28/11 - Renamed CustomerInteractionRequest.RequestType to
*                         RequestSubType.
*    icole     06/16/11 - Updated javadoc
*    icole     06/16/11 - Changes for CurrencyIfc, Sardine refresh items list,
*                         other simulted changes.
*    cgreene   06/15/11 - implement gift card for servebase and training mode
*    blarsen   06/15/11 - Integrated change to CustomerIneractionRequest.
*                         registerID was renamed to workstationID.
*    blarsen   06/14/11 - Added storeID to all interfaces. Moved isSwipeAhead
*                         flag to technician. Moved swipe ahead methods to
*                         PaymentManager. Added logic to prevent addTenders
*                         from happening when isSwipeAhead is true.
*    icole     06/14/11 - Restore CurrencyIfc
*    icole     06/13/11 - Add curly braces
*    icole     06/13/11 - Correct NPE
*    icole     06/10/11 - One Payment CPOI Method
*    icole     06/09/11 - Cleanup
*    icole     06/09/11 - Removed System outs
*    icole     06/09/11 - APF
*
* ===========================================================================
*/
package oracle.retail.stores.pos.services.common;

import java.util.ArrayList;
import java.util.Locale;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.common.utility.Util;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.PriceAdjustmentLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.manager.ifc.PaymentManagerIfc;
import oracle.retail.stores.domain.manager.payment.CustomerInteractionRequestIfc;
import oracle.retail.stores.domain.manager.payment.CustomerInteractionRequestIfc.LineItem;
import oracle.retail.stores.domain.manager.payment.CustomerInteractionRequestIfc.RequestSubType;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.domain.store.WorkstationIfc;
import oracle.retail.stores.domain.tax.TaxConstantsIfc;
import oracle.retail.stores.domain.tender.TenderChargeIfc;
import oracle.retail.stores.domain.tender.TenderLineItemIfc;
import oracle.retail.stores.domain.transaction.LayawayTransactionIfc;
import oracle.retail.stores.domain.transaction.OrderTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.tour.service.SessionBusIfc;
import oracle.retail.stores.foundation.utility.LocaleMap;
import oracle.retail.stores.pos.ado.context.ContextFactory;

import org.apache.log4j.Logger;
/**
 * @author icole
 * @since 13.4
 */
public class CPOIPaymentUtility
{
    protected static final Logger logger = Logger.getLogger(CPOIPaymentUtility.class);

    protected static CPOIPaymentUtility instance;

    // Note that scrollingReceiptInSession is not 100% accurate.
    // The payment technician (CardAuthConnector) closes the session when a card auth is requested.
    // Hopefully this will not impact correct behavior of CPOIPaymentUtility.
    protected boolean scrollingReceiptInSession = false;

    public synchronized static CPOIPaymentUtility getInstance()
    {
        if(instance == null)
        {
            instance = new CPOIPaymentUtility();
        }
        return instance;
    }

    /**
     * @param workstation
     * @param item
     * @param transaction
     */
    public void addItem(WorkstationIfc workstation, AbstractTransactionLineItemIfc item, SaleReturnTransactionIfc transaction)
    {
        if(!scrollingReceiptInSession)
        {
            beginScrollingReceipt(workstation, true);
        }
        addSingleItemToRequest(workstation, item, transaction);
    }

    /**
     * Adds single item information to the request object.
     * @param workstation
     * @param item
     * @param transaction
     */
    protected void addSingleItemToRequest(WorkstationIfc workstation, AbstractTransactionLineItemIfc item, SaleReturnTransactionIfc transaction)
    {
        if(item instanceof SaleReturnLineItemIfc)
        {
            Locale locale = LocaleMap.getBestMatch(LocaleMap.getLocale(LocaleConstantsIfc.DEVICES));

            if(transaction != null &&
               transaction.getCustomer() != null &&
               transaction.getCustomer().getPreferredLocale() != null)
            {
                locale = transaction.getCustomer().getPreferredLocale();
            }
            SaleReturnLineItemIfc saleReturnLineItem = (SaleReturnLineItemIfc)item;
            addItem(workstation,
                    locale,
                    getItemDescription(saleReturnLineItem, transaction),
                    item.getExtendedDiscountedSellingPrice(),
                    TaxConstantsIfc.TAX_MODE_CHAR[saleReturnLineItem.getItemTax().getTaxMode()]);
        }
        else if(item instanceof PriceAdjustmentLineItemIfc)
        {
           // price adjustment is handled separately
           logger.warn("Price Adjustment Line Item unexpected.");
        }
    }

    /**
     * Adds item information to the request object.
     * @param workstation
     * @param item
     * @param transaction
     * @param request
     */
    protected void addItemToRequest(WorkstationIfc workstation,
                                    AbstractTransactionLineItemIfc item,
                                    SaleReturnTransactionIfc transaction,
                                    CustomerInteractionRequestIfc request)
    {
        if(item instanceof SaleReturnLineItemIfc)
        {
            SaleReturnLineItemIfc saleReturnLineItem = (SaleReturnLineItemIfc)item;

            Locale defaultLocale = LocaleMap.getBestMatch(LocaleMap.getLocale(LocaleConstantsIfc.DEVICES));
            Locale locale = defaultLocale;
            if(transaction != null &&
               transaction.getCustomer() != null &&
               transaction.getCustomer().getPreferredLocale() != null)
            {
                locale = transaction.getCustomer().getPreferredLocale();
            }
            request.setWorkstation(workstation);
            request.setLocale(locale);
            LineItem lineItem = new LineItem();
            lineItem.description = getItemDescription(saleReturnLineItem, transaction);
            lineItem.price = item.getExtendedDiscountedSellingPrice();
            lineItem.taxableIndicator = TaxConstantsIfc.TAX_MODE_CHAR[saleReturnLineItem.getItemTax().getTaxMode()];
            request.getLineItemsToShow().add(lineItem);

        }
        else if(item instanceof PriceAdjustmentLineItemIfc)
        {
           // price adjustment is handled separately
           logger.warn("Price Adjustment Line Item unexpected.");
        }
    }

    /**
     * Method to add an item on the items screen on the CPOI device.
     * @param description
     * @param locale
     * @param price
     * @param taxIndicator
     */
    public void addItem(WorkstationIfc workstation, Locale locale, String description, CurrencyIfc price, String taxIndicator)
    {
        SessionBusIfc bus = (SessionBusIfc) ContextFactory.getInstance().getContext().getBus();
        PaymentManagerIfc paymentManager = (PaymentManagerIfc)bus.getManager(PaymentManagerIfc.TYPE);
        CustomerInteractionRequestIfc request = DomainGateway.getFactory().getCustomerInteractionRequestInstance(RequestSubType.ShowItems);
        request.setWorkstation(workstation);
        request.setClearLineItems(true);
        request.setLocale(locale);
        LineItem lineItem = new LineItem();
        lineItem.description = description;
        lineItem.price = price;
        lineItem.taxableIndicator = taxIndicator;
        request.getLineItemsToShow().add(lineItem);
        paymentManager.show(request);
    }

    /**
     * Adds the tender information to the request object.
     * @param tender
     * @param locale
     * @param request
     */
    protected void addTenderToRequest(WorkstationIfc workstation,Locale locale, TenderLineItemIfc tender)
    {
        String description;
        CurrencyIfc price;
        String taxableIndicator;
        if(tender instanceof TenderChargeIfc)
        {
            TenderChargeIfc tenderCharge = (TenderChargeIfc)tender;
            description = tender.getTypeCodeString() + " (" + tenderCharge.getCardNumberSnippet() + ")";
        }
        else
        {
            description = tender.getTypeCodeString();
        }
        price = tender.getAmountTender();
        taxableIndicator = "";
        addItem(workstation, locale, description, price, taxableIndicator);
    }

    /**
     * @param workstation
     * @param item
     * @param transaction
     */
    public void addItem(WorkstationIfc workstation, PLUItemIfc item, SaleReturnTransactionIfc transaction)
    {
        if(!scrollingReceiptInSession)
        {
            beginScrollingReceipt(workstation, true);
        }
        else
        {
            Locale defaultLocale = LocaleMap.getBestMatch(LocaleMap.getLocale(LocaleConstantsIfc.DEVICES));
            Locale locale = defaultLocale;
            if(transaction != null &&
               transaction.getCustomer() != null &&
               transaction.getCustomer().getPreferredLocale() != null)
            {
                locale = transaction.getCustomer().getPreferredLocale();
            }
            String description = getItemDescription(item, transaction);
            String taxIndicator;
            if (item.getTaxable())
            {
                taxIndicator = TaxConstantsIfc.TAX_MODE_CHAR[TaxConstantsIfc.TAX_MODE_STANDARD];
            }
            else
            {
                taxIndicator = TaxConstantsIfc.TAX_MODE_CHAR[TaxConstantsIfc.TAX_MODE_NON_TAXABLE];
            }
            addItem(workstation,
                    locale,
                    description,
                    item.getPrice(),
                    taxIndicator);
        }
    }

    /**
     * Returns the item short description.
     * @param srli
     * @param bus
     * @return The item's short description.
     */
    protected String getItemDescription(SaleReturnLineItemIfc srli, SaleReturnTransactionIfc transaction)
    {
        return getItemDescription(srli.getPLUItem(), transaction);
    }

    /**
     * Returns the item short description.
     * @param item
     * @param transaction
     * @return The item's short description.
     */
    protected String getItemDescription(PLUItemIfc item, SaleReturnTransactionIfc transaction)
    {
        Locale defaultLocale = LocaleMap.getBestMatch(LocaleMap.getLocale(LocaleConstantsIfc.DEVICES));
        Locale locale = defaultLocale;
        if(transaction != null &&
           transaction.getCustomer() != null &&
           transaction.getCustomer().getPreferredLocale() != null)
        {
                locale = transaction.getCustomer().getPreferredLocale();
        }
        // get the short description which could be for the customer's preferred language
        String description = item.getShortDescription(locale);
        if(Util.isEmpty(description))
        {
            // if description is empty, try again with the default locale
            description = item.getShortDescription(defaultLocale);
        }
        return description;
    }

    /**
     * Method to refresh the list of items.
     * @param workstation
     * @param itemList
     * @param transaction
     */
    public void refreshItems(WorkstationIfc workstation, ArrayList<AbstractTransactionLineItemIfc> itemList, SaleReturnTransactionIfc transaction)
    {
        Locale defaultLocale = LocaleMap.getBestMatch(LocaleMap.getLocale(LocaleConstantsIfc.DEVICES));
        Locale locale = defaultLocale;
        if(transaction != null &&
           transaction.getCustomer() != null &&
           transaction.getCustomer().getPreferredLocale() != null)
        {
                locale = transaction.getCustomer().getPreferredLocale();
        }
        if(!scrollingReceiptInSession)
        {
            beginScrollingReceipt(workstation, true);
        }
        else
        {
            SessionBusIfc bus = (SessionBusIfc) ContextFactory.getInstance().getContext().getBus();
            PaymentManagerIfc paymentManager = (PaymentManagerIfc)bus.getManager(PaymentManagerIfc.TYPE);
            CustomerInteractionRequestIfc request = DomainGateway.getFactory().getCustomerInteractionRequestInstance(RequestSubType.RefreshItems);
            request.setClearLineItems(true);
            request.setLocale(locale);

            for (AbstractTransactionLineItemIfc item : itemList)
            {
                addItemToRequest(workstation, item, transaction, request);
            }
            paymentManager.show(request);
        }
    }

    /**
     * Method to prepare for the scrolling receipt
     * @param workstation
     * @param swipeAhead
     */
    public void beginScrollingReceipt(WorkstationIfc workstation, boolean swipeAhead)
    {
        if(!scrollingReceiptInSession)
        {
            SessionBusIfc bus = (SessionBusIfc) ContextFactory.getInstance().getContext().getBus();
            PaymentManagerIfc paymentManager = (PaymentManagerIfc)bus.getManager(PaymentManagerIfc.TYPE);
            CustomerInteractionRequestIfc request = DomainGateway.getFactory().getCustomerInteractionRequestInstance(RequestSubType.BeginScrollingReceipt);
            request.setSwipeAhead(swipeAhead);
            request.setWorkstation(workstation);
            paymentManager.show(request);
            scrollingReceiptInSession = true;
        }
        else
        {
            if(logger.isInfoEnabled())
                logger.info("In beginScrollingReceipt: Scrolling Receipt session already in progress");
        }
    }

    /**
     * End the scrolling receipt session
     * @param workstation
     */
    public void endScrollingReceipt(WorkstationIfc workstation)
    {
        if(scrollingReceiptInSession)
        {
            SessionBusIfc bus = (SessionBusIfc) ContextFactory.getInstance().getContext().getBus();
            PaymentManagerIfc paymentManager = (PaymentManagerIfc)bus.getManager(PaymentManagerIfc.TYPE);
            CustomerInteractionRequestIfc request = DomainGateway.getFactory().getCustomerInteractionRequestInstance(RequestSubType.EndScrollingReceipt);
            request.setSwipeAhead(false);
            request.setWorkstation(workstation);
            scrollingReceiptInSession = false;
            paymentManager.show(request);
        }
        else
        {
            if(logger.isInfoEnabled())
                logger.info("In endScrollingReceipt: Scrolling Receipt session not active");
        }
    }

    /**
     * Update the totals (just balance due) on the device
     * @param workstation
     * @param locale
     * @param units
     * @param discountTotal
     * @param taxTotal
     * @param balanceDue
     */
    public void updateTotals(WorkstationIfc workstation, Locale locale, int units, CurrencyIfc discountTotal, CurrencyIfc taxTotal, CurrencyIfc balanceDue)
    {
        if(scrollingReceiptInSession)
        {
            SessionBusIfc bus = (SessionBusIfc) ContextFactory.getInstance().getContext().getBus();
            PaymentManagerIfc paymentManager = (PaymentManagerIfc)bus.getManager(PaymentManagerIfc.TYPE);
            CustomerInteractionRequestIfc request = DomainGateway.getFactory().getCustomerInteractionRequestInstance(RequestSubType.UpdateTotals);
            request.setTotalItemsQuantity(units);
            request.setTotalDiscount(discountTotal);
            request.setTotalTax(taxTotal);
            request.setTotal(balanceDue);
            request.setWorkstation(workstation);
            request.setLocale(locale);
            paymentManager.show(request);
        }
        else
        {
            logger.error("In updateTotals(): scrolling receipt not in session.");
        }
    }

    /**
     * @param workstation
     * @param transaction
     */
    public void addTenders(WorkstationIfc workstation, TenderableTransactionIfc transaction)
    {
        Locale locale = LocaleMap.getBestMatch(LocaleMap.getLocale(LocaleConstantsIfc.DEVICES));
        if(transaction != null &&
           transaction.getCustomer() != null &&
           transaction.getCustomer().getPreferredLocale() != null)
        {
            locale = transaction.getCustomer().getPreferredLocale();
        }
        endScrollingReceipt(workstation);
        SessionBusIfc bus = (SessionBusIfc) ContextFactory.getInstance().getContext().getBus();
        PaymentManagerIfc paymentManager = (PaymentManagerIfc)bus.getManager(PaymentManagerIfc.TYPE);
        boolean isSwipeAhead = paymentManager.isSwipeAhead(workstation);

        if (!isSwipeAhead)
        {
            beginScrollingReceipt(workstation, false);
            TenderLineItemIfc[] tenders = transaction.getTenderLineItems();
            for(TenderLineItemIfc tender : tenders)
            {
                addTenderToRequest(workstation, locale, tender);
            }
            String description = "BALANCE DUE";
            CurrencyIfc balanceDue;
            if(transaction instanceof LayawayTransactionIfc ||
               transaction instanceof OrderTransactionIfc)
            {
                balanceDue = transaction.getTenderTransactionTotals().getBalanceDue();
            }
            else
            {
                balanceDue = transaction.getTransactionTotals().getBalanceDue();
            }
            addItem(workstation, locale, description, balanceDue, "");
        }
    }


    /**
     * Get the state of the scrolling receipt session
     * @return the scrollingReceiptInSession
     */
    public boolean isScrollingReceiptInSession()
    {
        return scrollingReceiptInSession;
    }

    /**
     * Sets the scrollingReceiptInSession to the given inSession value.
     * @param inSession
     */
    public void setScrollingReceiptInSession(boolean inSession)
    {
        this.scrollingReceiptInSession = inSession;
    }

    /**
     * This method displays the change due screen.
     *
     * @param workstation - which workstation to route the request to
     * @param changeDue - the amount of change due
     */
    public void displayChangeDueScreen(WorkstationIfc workstation, CurrencyIfc changeDue)
    {
    	Locale locale = LocaleMap.getBestMatch(LocaleMap.getLocale(LocaleConstantsIfc.DEVICES));
    	String description = "Change Due";
    	CurrencyIfc changeDueAmount = changeDue.abs();
    	String taxableIndicator = "";
        addItem(workstation, locale, description, changeDueAmount, taxableIndicator);
    }

 }

