/* =============================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * =============================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returnauthorization/ShowReturnItemsSummarySite.java /rgbustores_13.4x_generic_branch/2 2011/08/17 15:15:45 rsnayak Exp $
 * =============================================================================
 * NOTES
 * Created by Lucy Zhao (Oracle Consulting) for POS-RM integration.
 *
 * MODIFIED    (MM/DD/YY)
 *    rsnayak   08/17/11 - Fix for Manager Override for Authorized items
 *    npoola    12/20/10 - action button texts are moved to CommonActionsIfc
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    nkgautam  06/19/09 - fix for enabling/disabling of ManagerOverride Button
 *                         based on Security access parameter
 *                         ReturnManagerOverride
 *    nkgautam  04/11/09 - Fix for able to return deniable items for RM-POS
 *    rkar      12/08/08 - (1) Changes for i18N (2) Removed TODOs, reformat.
 *    rkar      11/07/08 - Additions/changes for POS-RM integration
 *
 * =============================================================================
 */
package oracle.retail.stores.pos.services.returnauthorization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.Iterator;

import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.utility.JournalConstantsIfc;

import oracle.retail.stores.pos.ado.ADOException;
import oracle.retail.stores.pos.ado.utility.Utility;
import oracle.retail.stores.pos.ado.utility.UtilityIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.comparators.Comparators;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.ReturnResponseLineItem;
import oracle.retail.stores.domain.lineitem.ReturnResponseLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.manager.rm.RPIFinalResultIfc;
import oracle.retail.stores.domain.manager.rm.RPIItemResponse;
import oracle.retail.stores.domain.manager.rm.RPIManagerIfc;
import oracle.retail.stores.domain.manager.rm.RPIResponseIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.LetterIfc;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.LineItemsModel;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;
import oracle.retail.stores.pos.ui.timer.DefaultTimerModel;
import oracle.retail.stores.pos.services.common.CommonActionsIfc;
import oracle.retail.stores.pos.utility.RMUtility;

/**
 * Site displays Return Response from RM server.
 */
public class ShowReturnItemsSummarySite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = 4147191980955379482L;

    /**
     * Displays the Return Response from RM server.
     * @param bus the bus arriving at this site
     */
    public void arrive(BusIfc bus)
    {
        boolean isManagerOverrideEnabled = false;
        ReturnAuthorizationCargoIfc cargo = (ReturnAuthorizationCargoIfc) bus.getCargo();

        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);

        LineItemsModel beanModel = null;
        if ( bus.getCurrentLetter().getName().equals(CommonLetterIfc.SUCCESS) ||
             bus.getCurrentLetter().getName().equals(CommonLetterIfc.FAILURE ) )
        {
            //not the first time show the screen, the bean model already exists.
            beanModel = (LineItemsModel)ui.getModel(POSUIManagerIfc.RETURN_RESPONSE_ITEM);
        }
        else
        {   // the first time to show the screen in the return transaction.
            beanModel = new LineItemsModel();
        }

        ArrayList<ReturnResponseLineItemIfc> itemList = new ArrayList<ReturnResponseLineItemIfc>();
        SaleReturnTransactionIfc transaction = (SaleReturnTransactionIfc) cargo.getTransaction();
        ReturnResponseLineItemIfc[] returnResponseLineItems = cargo.getReturnResponseLineItems();
        if (transaction != null)
        {
            if ( returnResponseLineItems == null )
            {
                //create the lineItems based on the return response from RM server
                returnResponseLineItems = createReturnResponseLineItems(cargo);
                cargo.setReturnResponseLineItems(returnResponseLineItems);
            }
            else if (bus.getCurrentLetter().getName().equals(CommonLetterIfc.SUCCESS))
            {
                //got manager override, refresh the screen to display Manager Approved.
                updateReturnResponseLineItems(beanModel,cargo.getReturnResponse(),returnResponseLineItems, cargo.getSelectedRows());
                //updateRPIReturnResponses(cargo.getReturnResponse(), cargo.getSelectedRows());
            }
            itemList.addAll(Arrays.asList(returnResponseLineItems));

            // sort the line items list by line number
            Collections.sort(itemList, Comparators.lineNumberAscending);
        }

        AbstractTransactionLineItemIfc[] lineItems = new AbstractTransactionLineItemIfc[itemList.size()];
        itemList.toArray(lineItems);
        if (lineItems != null && lineItems.length > 0)
        {
            beanModel.setLineItems(lineItems);
        }

        beanModel.setTimerModel(new DefaultTimerModel(bus, transaction != null));
        beanModel.setSelectedRow(1);


        NavigationButtonBeanModel navModel = new NavigationButtonBeanModel();

        try
        {
          UtilityIfc utility = Utility.createInstance();
          String[] paramValues = utility.getParameterValueList("ManagerOverrideForSecurityAccess");
          for(int ctr = 0 ; ctr < paramValues.length; ctr++)
          {
            if(paramValues[ctr].equals("ReturnManagerOverride"))
            {
              isManagerOverrideEnabled = true;
              break;
            }
          }
        }
        catch (ADOException e)
        {
          isManagerOverrideEnabled = true;
        }
        navModel.setButtonEnabled(CommonActionsIfc.MANAGER_OVERRIDE, isManagerOverrideEnabled);        
        beanModel.setLocalButtonBeanModel(navModel);

        ui.setModel(POSUIManagerIfc.RETURN_RESPONSE_ITEM, beanModel);
        ui.showScreen(POSUIManagerIfc.RETURN_RESPONSE_ITEM, beanModel);
    }

    /**
     * Captures user-selected rows, and saves them in the Cargo for later use.
     * @param bus the bus arriving at this site
     */
    public void depart(BusIfc bus)
    {
        ReturnAuthorizationCargoIfc cargo = (ReturnAuthorizationCargoIfc) bus.getCargo();

        LetterIfc letter = bus.getCurrentLetter();

        if ( letter.getName().equals(CommonLetterIfc.UNDO) ||
             letter.getName().equals(CommonLetterIfc.CANCEL) )
        {   // If operator does an "UNDO" or "Cancel"
            // a void Return Result is sent to RM
            RPIFinalResultIfc rpiFinalResult =
                    RMUtility.getInstance().getFinalResult(
                                                    cargo.getTransaction(),
                                                    cargo.getReturnRequest(),
                                                    cargo.getReturnResponse(),
                                                    true);
            cargo.setReturnResult(rpiFinalResult);

            try {
                RPIManagerIfc rpiReturnsManager =
                    (RPIManagerIfc)Gateway.getDispatcher().getManager(RPIManagerIfc.TYPE);

                rpiReturnsManager.sendReturnsFinalResult(rpiFinalResult);
                cargo.getTransaction().setReturnTicket(rpiFinalResult.getTicketID());

                JournalManagerIfc journal;
                journal = (JournalManagerIfc)Gateway.getDispatcher().getManager(JournalManagerIfc.TYPE);
                Object[] messageArgs = {rpiFinalResult.getTicketID()};
                journal.journal( I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                        JournalConstantsIfc.SEND_VOID_RETURN_RESULT, messageArgs) );
            }
            catch (Exception e) {
                logger.error("Unable to send void Return Result, ", e);
            }
        }
        else
        {   // Next, or Manager Override, button clicked.
            POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);

            LineItemsModel beanModel = (LineItemsModel)ui.getModel(POSUIManagerIfc.RETURN_RESPONSE_ITEM);

            cargo.setSelectedRows(beanModel.getSelectedRows());
        }
    }

    /**
     * Create return response line items
     * @param cargo
     * @return
     */
    private ReturnResponseLineItemIfc[] createReturnResponseLineItems(ReturnAuthorizationCargoIfc cargo)
    {
        ArrayList<ReturnResponseLineItemIfc> returnResponseLineItemList = new ArrayList<ReturnResponseLineItemIfc>();

        List<RPIItemResponse> itemResponseList = cargo.getReturnResponse().getItemRefundResponse();
        Iterator<RPIItemResponse> iter= itemResponseList.iterator();

        AbstractTransactionLineItemIfc[] saleReturnLineItems = cargo.getTransaction().getLineItems();

        if ( saleReturnLineItems instanceof SaleReturnLineItemIfc[] )
        {
            for ( int i=0; i< saleReturnLineItems.length; i++ )
            {
                SaleReturnLineItemIfc saleReturnLineItem = (SaleReturnLineItemIfc)saleReturnLineItems[i];

                if ( saleReturnLineItem.getReturnItem() != null )
                {
                    ReturnResponseLineItemIfc returnResponseLineItem = DomainGateway.getFactory().getReturnResponseLineItemInstance();
                    RPIItemResponse rpiItemResponse = iter.next();
                    returnResponseLineItem.initialize(rpiItemResponse.getApproveDenyCode(),
                            rpiItemResponse.getResponseDescription(),
                            saleReturnLineItem.getPLUItem(),
                            saleReturnLineItem.getItemQuantityDecimal(),
                            saleReturnLineItem.getItemTax(),
                            saleReturnLineItem.getSalesAssociate(),
                            saleReturnLineItem.getRegistry(),
                            saleReturnLineItem.getReturnItem());
                    returnResponseLineItem.setSaleReturnLineItemIndex(saleReturnLineItem.getLineNumber());
                    returnResponseLineItemList.add(returnResponseLineItem);
                }
            }
        }

        ReturnResponseLineItemIfc[] returnResponseLineItems = new ReturnResponseLineItemIfc[returnResponseLineItemList.size()];
        returnResponseLineItemList.toArray(returnResponseLineItems);

        return returnResponseLineItems;
    }

    /**
     * Update RPI return response status for denied return items
     * @param returnResponse
     * @param returnResponseLineItems
     * @param selectedIndex
     */
    private void updateReturnResponseLineItems(LineItemsModel beanModel,
                                               RPIResponseIfc returnResponse,
                                               ReturnResponseLineItemIfc[] returnResponseLineItems,
                                               int[] selectedIndex)
    {
        UtilityManagerIfc utility =
            (UtilityManagerIfc)Gateway.getDispatcher().getManager(UtilityManagerIfc.TYPE);
        String overrideMessage = utility.retrieveText(BundleConstantsIfc.COMMON,
                BundleConstantsIfc.RETURNS_BUNDLE_NAME, "ManagerApproved", "Manager Approved");
        boolean bDenialItemPresent = false;

        List<RPIItemResponse> rpiReturnItems = returnResponse.getItemRefundResponse();

        int [] selectedRows = beanModel.getSelectedRows();
        for(int i =0 ; i < selectedRows.length ; i++)
        {
          if(beanModel.getListModel().get(selectedRows[i]) instanceof ReturnResponseLineItemIfc)
          {
            ReturnResponseLineItemIfc returnResponseItem = (ReturnResponseLineItem)beanModel.getListModel().get(selectedRows[i]);
            if(returnResponseItem.getApproveDenyCode().equalsIgnoreCase("Denial"))
            {
              bDenialItemPresent = true;
            }
          }
        }

        for (int i=0; i<returnResponseLineItems.length; i++)
        {
          RPIItemResponse rpiItemResponse = rpiReturnItems.get(i);
          if(isItemSelected(i, selectedIndex) && returnResponseLineItems[i].getApproveDenyCode().equalsIgnoreCase("Denial"))
          {
            returnResponseLineItems[i].setManagerOverride(false);
          }
          if ( rpiItemResponse != null &&
              (rpiItemResponse.getApproveDenyCode()).toLowerCase().contains("authorization"))
          {
            continue; 
          }
          if ( !bDenialItemPresent && isItemSelected(i, selectedIndex) &&  !(returnResponseLineItems[i].getApproveDenyCode().equalsIgnoreCase("Denial")))
          {
            returnResponseLineItems[i].setManagerOverride(true);
            returnResponseLineItems[i].setResponseDescription(overrideMessage);

            if ( rpiItemResponse != null )
            {
              rpiItemResponse.setApproveDenyCode("Authorization");
            }
          }

        }
    }

    /**
     * Checks to see if the indexed item is selected.
     * @param index
     * @param selected
     * @return
     */
    private boolean isItemSelected(int index, int[] selected)
    {
        for (int i=0; i<selected.length; i++)
        {
            if ( selected[i] == index )
                return true;
        }
        return false;
    }
}
