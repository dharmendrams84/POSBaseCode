/* =============================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * =============================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returnauthorization/AuthorizeReturnItemsSite.java /rgbustores_13.4x_generic_branch/6 2011/09/29 10:13:06 abondala Exp $
 * =============================================================================
 * NOTES
 * Created by Lucy Zhao (Oracle Consulting) for POS-RM integration.
 *
 * MODIFIED    (MM/DD/YY)
 *    abondala  09/29/11 - updated
 *    abondala  09/28/11 - add house account option in RM application
 *    abondala  09/14/11 - send the item price per unit after applying price
 *                         overrides and discounts
 *    abondala  09/13/11 - send the correct price of item to RM for
 *                         authorization
 *    abondala  09/12/11 - pos return date is stored in dc_dy_bsn_rtn column
 *                         and modifed the xsd to send the return date from pos
 *                         in request xml message
 *    abondala  09/02/11 - get the return quantity from the saleReturnLineItem
 *    cgreene   07/07/11 - convert entryMethod to an enum
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   03/19/09 - refactoring changes
 *    rkar      02/27/09 - Changed to handle POS Tx Reentry mode, like RM
 *                         Offline case
 *    vikini    12/18/08 - Adding TicketId to Return Receipt
 *    mdecama   12/16/08 - Sending the Reason Description instead of the Reason
 *                         Code to RM
 *    rkar      12/08/08 - (1) Changes for i18N (2) Removed TODOs, reformat.
 *    abondala  12/02/08 - RM-POS integration
 *    rkar      11/07/08 - Additions/changes for POS-RM integration
 *
 * =============================================================================
 */
package oracle.retail.stores.pos.services.returnauthorization;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.ReturnItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.manager.rm.RPIItemResponse;
import oracle.retail.stores.domain.manager.rm.RPIManagerIfc;
import oracle.retail.stores.domain.manager.rm.RPIRequest;
import oracle.retail.stores.domain.manager.rm.RPIRequestIfc;
import oracle.retail.stores.domain.manager.rm.RPIResponseIfc;
import oracle.retail.stores.domain.manager.rm.RPIReturnItemInfo;
import oracle.retail.stores.domain.manager.rm.RPITenderType;
import oracle.retail.stores.domain.manager.rm.RPITransactionInfo;
import oracle.retail.stores.domain.stock.ItemIfc;
import oracle.retail.stores.domain.stock.ReturnMessageDTO;
import oracle.retail.stores.domain.tender.TenderCharge;
import oracle.retail.stores.domain.tender.TenderLineItemIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionConstantsIfc;
import oracle.retail.stores.domain.transaction.TransactionIDIfc;
import oracle.retail.stores.domain.utility.EYSDate;
import oracle.retail.stores.domain.utility.EntryMethod;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;
import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.utility.JournalConstantsIfc;

/**
 * Site that creates return request and gets return response from RM server
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/6 $
 */
public class AuthorizeReturnItemsSite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = 8084146964622523302L;
    /** site name constant **/
    public static final String SITENAME = "RMAuthorizeReturnItemSite";
    /** revision number supplied by source-code-control system **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/6 $";

    /**
     * This site creates return request for RM server and gets returns response
     * from RM server
     * 
     * @param bus the bus arriving at this site
     */
    @Override
    public void arrive(BusIfc bus)
    {
        // Get the cargo
        ReturnAuthorizationCargo cargo = (ReturnAuthorizationCargo)bus.getCargo();

        RPIRequestIfc rpiRequest = createRPIRequest(cargo);
        cargo.setReturnRequest(rpiRequest);

        JournalManagerIfc journal;
        journal = (JournalManagerIfc)Gateway.getDispatcher().getManager(JournalManagerIfc.TYPE);
        journal.journal(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.CREATE_RETURN_REQUEST,
                null));

        RPIResponseIfc rpiResponse = null;
        boolean isTransReentryMode = cargo.getRegister().getWorkstation().isTransReentryMode();
        // (Except in transaction re-entry mode) Call RM server, get return
        // response
        if (!isTransReentryMode)
        {
            rpiResponse = getRPIResponse(rpiRequest);
        }

        if (rpiResponse != null)
        {
            cargo.setReturnResponse(rpiResponse);

            Object[] messageArgs = { rpiResponse.getApproveDenyCode() };
            journal.journal(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                    JournalConstantsIfc.GET_RM_SERVER_RESPONSE, messageArgs));

            AbstractTransactionLineItemIfc[] lineItem = (cargo.getTransaction()).getLineItems();

            List<RPIItemResponse> itemResponseList = rpiResponse.getItemRefundResponse();
            ReturnMessageDTO rmMessageDTO = null;
            rmMessageDTO = new ReturnMessageDTO();
            for (int lineItmCtr = 0; lineItmCtr < lineItem.length; lineItmCtr++)
            {
                SaleReturnLineItemIfc saleReturnLineItem = (SaleReturnLineItemIfc)lineItem[lineItmCtr];
                for (int itmRespCtr = 0; itmRespCtr < itemResponseList.size(); itmRespCtr++)
                {
                    if ((saleReturnLineItem.getPLUItemID()).equalsIgnoreCase((itemResponseList.get(itmRespCtr))
                            .getItemID()))
                    {
                        rmMessageDTO = null;
                        rmMessageDTO = new ReturnMessageDTO();
                        rmMessageDTO.setMessageID((itemResponseList.get(itmRespCtr)).getReceiptMessageNumber());
                        rmMessageDTO
                                .setReturnMessage((itemResponseList.get(itmRespCtr)).getReceiptMessageDescription());
                        rmMessageDTO.setReturnTicketID(rpiResponse.getTicketID());
                        saleReturnLineItem.setReturnMessage(rmMessageDTO);
                    }
                }
            }
            if (rpiResponse.getApproveDenyCode().contains("Authorization"))
            {
                bus.mail(new Letter("Approved"), BusIfc.CURRENT);
            }
            else
            {
                bus.mail(new Letter("ManagerOverride"), BusIfc.CURRENT);
            }
        }
        else
        {
            if (isTransReentryMode)
                logger.debug("Transaction reentry mode, hence using RM offline mode");
            else
                logger.info("Unable to get RPIResponse from RM, using RM offline mode");

            // goOfflineFlow
            DialogBeanModel dialogModel = new DialogBeanModel();
            dialogModel.setResourceID("ORRM_OFFLINE");
            dialogModel.setType(DialogScreensIfc.ACKNOWLEDGEMENT);
            dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_OK, CommonLetterIfc.OFFLINE);

            // display dialog
            POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
            ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
        }
    }

    /**
     * Create return request from the cargo based on the return items and the
     * transaction information
     * 
     * @param cargo
     * @return RPIRequest
     */
    public RPIRequestIfc createRPIRequest(ReturnAuthorizationCargo cargo)
    {
        RPIRequestIfc request = new RPIRequest();

        String storeID = cargo.getTransaction().getWorkstation().getStoreID();
        String workstationID = cargo.getTransaction().getWorkstation().getWorkstationID();
        String txnType = TransactionConstantsIfc.TYPE_DESCRIPTORS[cargo.getTransaction().getTransactionType()];
        request.setReturnStoreID(storeID);
        request.setReturnWorkstationID(workstationID);
        request.setReturnDate(cargo.getTransaction().getBusinessDay().toGregorianCalendar());
        request.setTransactionType(txnType);
        request.setCustomerType(cargo.getCustomerType());

        request.setPositiveID(cargo.getPositiveID());

        request.setMoreCustomerInfo(cargo.getMoreCustomerInfo());
        request.setEmployeeID(cargo.getOperator().getEmployeeID());

        List<RPIReturnItemInfo> rpiReturnItemList = getRPIReturnItemList(cargo.getTransaction().getLineItems(),
                cargo.getOriginalReturnTransactions());
        request.setReturnItemList(rpiReturnItemList);

        return request;
    }

    /**
     * Create RPI return items
     * 
     * @param saleReturnLineItems
     * @param originalTransactions
     * @return
     */
    private List<RPIReturnItemInfo> getRPIReturnItemList(AbstractTransactionLineItemIfc[] saleReturnLineItems,
            SaleReturnTransactionIfc[] originalTransactions)
    {
        
        Locale locale = LocaleMap.getLocale(LocaleConstantsIfc.DEFAULT_LOCALE);
        
        List<RPIReturnItemInfo> rpiReturnItemInfoList = new ArrayList<RPIReturnItemInfo>();

        if (saleReturnLineItems instanceof SaleReturnLineItemIfc[])
        {
            for (int i = 0; i < saleReturnLineItems.length; i++)
            {
                SaleReturnLineItemIfc saleReturnLineItem = (SaleReturnLineItemIfc)saleReturnLineItems[i];
                
                ReturnItemIfc returnItem = saleReturnLineItem.getReturnItem();
                
                if (returnItem != null)
                {
                    RPIReturnItemInfo rpiReturnItemInfo = new RPIReturnItemInfo();

                    ItemIfc item = saleReturnLineItem.getPLUItem().getItem();

                    rpiReturnItemInfo.setItemID(item.getItemID());
                    rpiReturnItemInfo.setItemDescription(item.getDescription(locale));
                    if (returnItem.getReason() != null)
                    {
                        rpiReturnItemInfo.setReturnReason(returnItem.getReason().getText(locale));
                    }

                    rpiReturnItemInfo.setQuantity(saleReturnLineItem.getItemQuantityDecimal().abs());
                    
                    if(saleReturnLineItem.getExtendedDiscountedSellingPrice() != null 
                            && saleReturnLineItem.getItemQuantityDecimal() != null 
                            && saleReturnLineItem.getItemQuantityDecimal().abs().intValue() > 0)
                    {
                        rpiReturnItemInfo.setAmountPaidPerUnit(
                                (saleReturnLineItem.getExtendedDiscountedSellingPrice().abs().divide
                                        (saleReturnLineItem.getItemQuantityDecimal().abs())).getDecimalValue());
                    }

                    if (returnItem.getSerialNumber() == null || returnItem.getSerialNumber().length() == 0)
                    {
                        rpiReturnItemInfo.setSerialNumber("");
                    }
                    else
                    {
                        rpiReturnItemInfo.setSerialNumber(returnItem.getSerialNumber());
                    }
                    if (returnItem.getEntryMethod() == EntryMethod.Scan)
                    {
                        rpiReturnItemInfo.setManuallyEntered(false);
                    }
                    else
                    {
                        rpiReturnItemInfo.setManuallyEntered(true);
                    }

                    RPITransactionInfo originalTxnInfo = getRPITransactionInfo(returnItem, originalTransactions);
                    rpiReturnItemInfo.setOriginalTransactionInfo(originalTxnInfo);

                    if (originalTxnInfo != null)
                    {
                        rpiReturnItemInfo.setReceipted(true);
                    }
                    else
                    {
                        rpiReturnItemInfo.setReceipted(false);
                    }

                    rpiReturnItemInfoList.add(rpiReturnItemInfo);
                }
            }
        }

        return rpiReturnItemInfoList;
    }

    /**
     * Get return item's original transaction info
     * 
     * @param transaction
     * @return
     */
    private RPITransactionInfo getRPITransactionInfo(ReturnItemIfc returnItem,
            SaleReturnTransactionIfc[] originalTransactions)
    {
        RPITransactionInfo rpiTransactionInfo = null;

        EYSDate businessDate = returnItem.getOriginalTransactionBusinessDate();
        TransactionIDIfc transactionID = returnItem.getOriginalTransactionID();
        if (businessDate != null && transactionID != null) // has original
                                                           // transaction
        {
            rpiTransactionInfo = new RPITransactionInfo();

            rpiTransactionInfo.setGiftReceipt(returnItem.isFromGiftReceipt());
            rpiTransactionInfo.setPurchaseDate(businessDate.toGregorianCalendar());
            rpiTransactionInfo.setReceipted(returnItem.haveReceipt());

            for (int i = 0; i < originalTransactions.length; i++)
            {
                if (originalTransactions[i].getTransactionID().equals(transactionID))
                {
                    rpiTransactionInfo.setGiftReceipt(originalTransactions[i].hasGiftReceiptItems());
                    break;
                }
            }
            rpiTransactionInfo.setFound(true);

            rpiTransactionInfo.setStoreID(transactionID.getFormattedStoreID());
            rpiTransactionInfo.setWorkstationID(transactionID.getFormattedWorkstationID());
            rpiTransactionInfo.setSequenceNumber(new BigInteger(transactionID.getFormattedTransactionSequenceNumber()));
            rpiTransactionInfo.setBusinessDate(returnItem.getOriginalTransactionBusinessDate().toGregorianCalendar());
            rpiTransactionInfo.setTransactionID(transactionID.getTransactionIDString());

            rpiTransactionInfo.setValidAtPointofReturn(true);

            HashMap<String, String> tenderList = returnItem.getTenderList();

            if (tenderList.size() == 0)
            {
                // this is a retrieved transaction which was previously
                // suspended.
                for (int i = 0; i < originalTransactions.length; i++)
                {
                    if (originalTransactions[i].getWorkstation().getStore().getStoreID()
                            .equals(transactionID.getFormattedStoreID())
                            && originalTransactions[i].getWorkstation().getWorkstationID()
                                    .equals(transactionID.getWorkstationID())
                            && originalTransactions[i].getTransactionSequenceNumber() == transactionID
                                    .getSequenceNumber()
                            && originalTransactions[i].getBusinessDay().getDay() == returnItem
                                    .getOriginalTransactionBusinessDate().getDay()
                            && originalTransactions[i].getBusinessDay().getMonth() == returnItem
                                    .getOriginalTransactionBusinessDate().getMonth()
                            && originalTransactions[i].getBusinessDay().getYear() == returnItem
                                    .getOriginalTransactionBusinessDate().getYear())
                    {
                        TenderLineItemIfc[] tenderLineItems = originalTransactions[i].getTenderLineItems();
                        for (int tenderIndex = 0; tenderIndex < tenderLineItems.length; tenderIndex++)
                        {
                            if(tenderLineItems[tenderIndex] instanceof  TenderCharge)
                            {
                                TenderCharge tenderCharge = (TenderCharge)tenderLineItems[tenderIndex];
                                String cardType = tenderCharge.getEncipheredCardData().getCardType(); //CRDT
                                String cardName = tenderCharge.getEncipheredCardData().getCardName(); //HouseCard
                                tenderList.put(cardType, cardName);
                            } 
                            else
                            {
                                tenderList.put(tenderLineItems[tenderIndex].getTypeCodeString(), null);
                            }                
                        }
                    }
                }
            }
            
            for (Iterator<String> iterator = tenderList.keySet().iterator(); iterator.hasNext();) 
            {
                String tenderType = (String) iterator.next(); //CRDT
                String tenderName = tenderList.get(tenderType); //HouseCard
                if(tenderName != null && tenderName.equals("HouseCard"))
                {
                    rpiTransactionInfo.getTenderTypes().add(new RPITenderType("HOUSECARD"));
                }
                else
                {
                    rpiTransactionInfo.getTenderTypes().add(new RPITenderType(tenderType));    
                }
            }


            // There is a problem in 13.0.0,
            // originalTransactions[i].getTransactionSequenceNumber() returns 0
            // if there are more then one return transaction in a retrieved
            // transaction which is previously suspended.
            // To handle this case, we treat it as no receipt return.
            if (tenderList.size() == 0)
                rpiTransactionInfo = null;

        }
        return rpiTransactionInfo;
    }

    /**
     * Get returns response from RM server
     * 
     * @param request
     * @return Return Response from RM server. Null if exception caught.
     */
    private RPIResponseIfc getRPIResponse(RPIRequestIfc request)
    {
        RPIResponseIfc response = null;
        try
        {
            RPIManagerIfc rpiReturnsManager = (RPIManagerIfc)Gateway.getDispatcher().getManager(RPIManagerIfc.TYPE);
            response = rpiReturnsManager.getReturnsResponse(request);
        }
        catch (Exception e)
        {
            logger.error("Communication Exception with RPI Technician: ", e);
        }
        return response;
    }

}
