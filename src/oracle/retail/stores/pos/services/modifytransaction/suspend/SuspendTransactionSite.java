/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifytransaction/suspend/SuspendTransactionSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:31 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    abhayg    08/13/10 - STOPPING POS TRANSACTION IF REGISTER HDD IS FULL
 *    acadar    06/10/10 - use default locale for currency display
 *    acadar    06/09/10 - XbranchMerge acadar_tech30 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    acadar    04/05/10 - use default locale for currency and date/time
 *                         display
 *    abondala  01/03/10 - update header date
 *    mchellap  04/29/09 - Set deposit amounts to zero for suspended orders
 *    mchellap  04/03/09 - Setting order and line item status to undefined for
 *                         suspended orders
 *    mahising  03/06/09 - fixed issue for suspended transaction
 *    jswan     02/21/09 - Modified to initialize OrderLineItems with Order
 *                         Reference IDs and status.
 *    mahising  02/18/09 - fixed reasone code issue in suspend transaction
 *    deghosh   12/02/08 - EJ i18n changes
 *    mdecama   10/24/08 - I18N updates for Suspend Transaction Reason Codes.
 *
 * ===========================================================================

     $Log:
      5    360Commerce 1.4         5/21/2007 9:16:21 AM   Anda D. Cadar   EJ
           changes
      4    360Commerce 1.3         4/25/2007 8:52:22 AM   Anda D. Cadar   I18N
           merge

      3    360Commerce 1.2         3/31/2005 4:30:17 PM   Robert Pearse
      2    360Commerce 1.1         3/10/2005 10:25:42 AM  Robert Pearse
      1    360Commerce 1.0         2/11/2005 12:14:36 PM  Robert Pearse
     $
     Revision 1.4  2004/02/24 16:21:27  cdb
     @scr 0 Remove Deprecation warnings. Cleaned code.

     Revision 1.3  2004/02/12 16:51:16  mcs
     Forcing head revision

     Revision 1.2  2004/02/11 21:51:47  rhafernik
     @scr 0 Log4J conversion and code cleanup

     Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
     updating to pvcs 360store-current


 *
 *    Rev 1.0   Aug 29 2003 16:02:48   CSchellenger
 * Initial revision.
 *
 *    Rev 1.0   Apr 29 2002 15:15:28   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:39:40   msg
 * Initial revision.
 *
 *    Rev 1.1   11 Mar 2002 11:41:10   jbp
 * Journal before saveing transaction
 * Resolution for POS SCR-1450: Date/Timestamp appears in wrong place for a suspended sale that has advanced pricing items on it
 *
 *    Rev 1.0   Sep 21 2001 11:31:24   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:09:52   msg
 * header update
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package oracle.retail.stores.pos.services.modifytransaction.suspend;
// java imports
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.utility.JournalConstantsIfc;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.domain.transaction.TransactionConstantsIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.OrderLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderConstantsIfc;
import oracle.retail.stores.domain.order.OrderDeliveryDetailIfc;
import oracle.retail.stores.domain.order.OrderStatusIfc;
import oracle.retail.stores.domain.transaction.OrderTransactionIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

//------------------------------------------------------------------------------
/**
    Write suspended transaction to database. <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public class SuspendTransactionSite extends PosSiteActionAdapter
{                                                                               // begin class SuspendTransactionSite

    /**
     * Generated SerialVersionUID
     */
    private static final long serialVersionUID = -2167640182116841495L;

    /**
       revision number supplied by source-code control system
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
       site name constant
    **/
    public static final String SITENAME = "SuspendTransactionSite";

    //--------------------------------------------------------------------------
    /**
       Write suspended transaction to database. <P>
       @param bus the bus arriving at this site
    **/
    //--------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {                                                                   // begin arrive()

        String letterName = CommonLetterIfc.SUCCESS;
        // save transaction
        ModifyTransactionSuspendCargo cargo =
            (ModifyTransactionSuspendCargo) bus.getCargo();
        RetailTransactionIfc trans = cargo.getTransaction();
        
        boolean saveTransSuccess = true;
    	POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);

    	if (trans instanceof OrderTransactionIfc)
        {
            OrderTransactionIfc orderTransaction = (OrderTransactionIfc)cargo.getTransaction();
            ArrayList<Integer> deliveryDetailsID = new ArrayList<Integer>();
            ArrayList<OrderDeliveryDetailIfc> deliveryItemCollection = new ArrayList<OrderDeliveryDetailIfc>();
            //avoiding duplicate entry of Delivery details ID in collection
            if (orderTransaction.getOrderType() == OrderConstantsIfc.ORDER_TYPE_ON_HAND)
            {
                AbstractTransactionLineItemIfc[] lineItems = orderTransaction.getLineItems();
                for (int i = 0; i < lineItems.length; i++)
                {
                    SaleReturnLineItemIfc item = (SaleReturnLineItemIfc)lineItems[i];
                    if (item.getOrderItemStatus().getItemDispositionCode() == OrderLineItemIfc.ORDER_ITEM_DISPOSITION_DELIVERY)
                    {
                        int detailId = item.getOrderItemStatus().getDeliveryDetails().getDeliveryDetailID();
                        if (!(deliveryDetailsID.contains(new Integer(detailId))))
                        {
                            deliveryItemCollection.add(item.getOrderItemStatus().getDeliveryDetails());
                            deliveryDetailsID.add(new Integer(detailId));
                        }
                    }
                }
                orderTransaction.setDeliveryDetails(deliveryItemCollection);
            }

        }

        // set reason code, status
        trans.setTransactionStatus(TransactionIfc.STATUS_SUSPENDED);
        trans.setSuspendReason (cargo.getSelectedLocalizedReason());
        trans.setTimestampEnd();

        // print journal
        JournalManagerIfc journal;
        journal = (JournalManagerIfc)
          Gateway.getDispatcher().getManager(JournalManagerIfc.TYPE);

        Locale locale =  LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL);
        if (journal != null)
        {
            String transactionID = trans.getTransactionID();
            StringBuffer endSuspend = new StringBuffer();
            Object[] dataArgs = new Object[2];

            // Journal total best deal discounts
            if (trans instanceof SaleReturnTransactionIfc)
            {
                CurrencyIfc advancedPricingDiscount =
                            ((SaleReturnTransactionIfc)trans).getAdvancedPricingDiscountTotal();
                if (advancedPricingDiscount.signum() > 0)
                {
                    endSuspend.append(Util.EOL);
                    dataArgs[0] = advancedPricingDiscount.negate().toGroupFormattedString();
                    endSuspend. append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.DEAL_DISCOUNT_TAG_LABEL, dataArgs));
                    endSuspend.append(Util.EOL);
                }
            }

            Vector items = trans.getLineItemsVector();

            if (items != null)
            {
                for (int i = 0; i < items.size(); i++)
                {
                     if (((SaleReturnLineItemIfc)items.elementAt(i)).getItemSendFlag() &&
                         !((SaleReturnLineItemIfc)items.elementAt(i)).isReturnLineItem())

                     {
                    	 dataArgs[0] = ((SaleReturnLineItemIfc)items.elementAt(i)).getItemID().trim();
                    	 endSuspend.append(Util.EOL)
                                   .append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ITEM_LABEL, dataArgs))
                                   .append(Util.EOL)
                                   .append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.SEND_TAG_LABEL, dataArgs)). append(Util.EOL);

                     }
                }
            }

            endSuspend.append(Util.EOL).append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.TRANS_SUSPENDED_LABEL, null));
            String suspendedReason = cargo.getSelectedLocalizedReason().getText(locale);
            endSuspend.append(Util.EOL);
            dataArgs[0] = suspendedReason;
            if (!(suspendedReason.trim().equals("")))
            {
                endSuspend.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                        JournalConstantsIfc.SUSPEND_RSN_LABEL, dataArgs));
            }

            journal.journal(trans.getCashier().getLoginID(),
                            trans.getTransactionID(),
                            endSuspend.toString());
            if (logger.isInfoEnabled()) logger.info( "Transaction " + transactionID + " suspended");
        }
        else
        {
            logger.error( "No JournalManager found");
        }
        UtilityManagerIfc utility =
            (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        // Save the transaction to persistent storage
        try
        {
            if (trans instanceof OrderTransactionIfc)
            {
                initializeOrderLineItems((OrderTransactionIfc)trans);
            }
            utility.saveTransaction(trans);
            // set flag to reprint ID
            cargo.setLastReprintableTransactionID(trans.getTransactionID());
            // write hard totals
            utility.writeHardTotals(bus);
        }
        catch (DataException e)
        {
        	if (e.getErrorCode() == DataException.QUEUE_FULL_ERROR ||
        			e.getErrorCode() == DataException.STORAGE_SPACE_ERROR ||
        			e.getErrorCode() == DataException.QUEUE_OP_FAILED)
        	{
        		saveTransSuccess = false;
        		DialogBeanModel dialogModel = utility.createErrorDialogBeanModel(e, false);
                // display dialog
                ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE,dialogModel);
        	}
        	else
        	{
        		cargo.setDataExceptionErrorCode(e.getErrorCode());
        		letterName = CommonLetterIfc.DB_ERROR;
        	}
        }
        catch (DeviceException e)
        {
            logger.error( "" + Util.throwableToString(e) + "");
            letterName = CommonLetterIfc.HARD_TOTALS_ERROR;
        }
        if (saveTransSuccess)
        {
        	// mail letter
        	bus.mail(new Letter(letterName), BusIfc.CURRENT);
        }

    }                                                                   // end arrive()

    /*
     * Set the order status, order line item status and order line item reference id.
     * @param  orderTransaction
     */
    private void initializeOrderLineItems(OrderTransactionIfc orderTransaction)
    {
        if (orderTransaction.getTransactionType() == TransactionConstantsIfc.TYPE_ORDER_INITIATE)
        {
            // Set Order status
            OrderStatusIfc orderStatus = orderTransaction.getOrderStatus();
            // Set the suspended order's status to undefined
            orderStatus.getStatus().changeStatus(OrderConstantsIfc.ORDER_STATUS_UNDEFINED);
            orderStatus.getStatus().setPreviousStatus(OrderConstantsIfc.ORDER_STATUS_UNDEFINED);
            orderStatus.getStatus().setPreviousStatusChange(orderStatus.getStatus().getLastStatusChange());
            // Since the order is suspended, set the deposit amounts to zero
            orderStatus.setDepositAmount(DomainGateway.getBaseCurrencyInstance());
            orderStatus.setMinimumDepositAmount(DomainGateway.getBaseCurrencyInstance());

            // set line item status and order line item reference id.
            AbstractTransactionLineItemIfc[] lineitems = orderTransaction.getLineItems();
            for (int i = 0; i < lineitems.length; i++)
            {
                int itemDispositionCode = ((SaleReturnLineItemIfc)lineitems[i]).getOrderItemStatus().getItemDispositionCode();

                if (orderTransaction.getOrderType() == OrderConstantsIfc.ORDER_TYPE_ON_HAND
                        && itemDispositionCode == OrderLineItemIfc.ORDER_ITEM_DISPOSITION_SALE)
                {

                    ((SaleReturnLineItemIfc)lineitems[i]).getOrderItemStatus().getStatus().changeStatus(
                            OrderLineItemIfc.ORDER_ITEM_STATUS_PICKED_UP);
                    ((SaleReturnLineItemIfc)lineitems[i]).getOrderItemStatus().setQuantityPicked(
                            ((SaleReturnLineItemIfc)lineitems[i]).getItemQuantityDecimal());
                    ((SaleReturnLineItemIfc)lineitems[i]).setOrderLineReference(i + 1);
                }
                else
                {
                    // Set the suspended order's line item status to undefined
                    ((SaleReturnLineItemIfc)lineitems[i]).getOrderItemStatus().getStatus().changeStatus(
                            OrderLineItemIfc.ORDER_ITEM_STATUS_UNDEFINED);
                    ((SaleReturnLineItemIfc)lineitems[i]).setOrderLineReference(i + 1);
                }
            }

        }
    }

    //----------------------------------------------------------------------
    /**
       Returns a string representation of this object. <P>
       @return String representation of object
    **/
    //----------------------------------------------------------------------
    public String toString()
    {                                   // begin toString()
        // result string
        StringBuffer strResult = new StringBuffer("Class:  ");
        strResult.append("SuspendTransactionSite (Revision ")
            .append(getRevisionNumber())
            .append(") @").append(hashCode());
        // pass back result
        return(strResult.toString());
    }                                   // end toString()

    //----------------------------------------------------------------------
    /**
       Returns the revision number of the class. <P>
       @return String representation of revision number
    **/
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    {                                   // begin getRevisionNumber()
        // return string
        return(revisionNumber);
    }                                   // end getRevisionNumber()
}                                                                               // end class class SuspendTransactionSite
