/* ===========================================================================
* Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/relateditem/GetPickManyRelatedItemsSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:01 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    sgu       06/08/10 - fix item interactive screen prompts to include item
 *                         # and description
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 1    360Commerce 1.0         12/13/2005 4:47:04 PM  Barry A. Pape
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale.relateditem;

import java.util.HashMap;
import java.util.Locale;

import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.domain.stock.RelatedItemGroupIfc;
import oracle.retail.stores.domain.stock.RelatedItemIfc;
import oracle.retail.stores.domain.stock.RelatedItemSummary;
import oracle.retail.stores.domain.stock.RelatedItemSummaryIfc;
import oracle.retail.stores.domain.utility.CodeConstantsIfc;
import oracle.retail.stores.domain.utility.CodeEntryIfc;
import oracle.retail.stores.domain.utility.CodeListIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.LocaleMap;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;
import oracle.retail.stores.pos.ui.beans.RelatedItemListBeanModel;

//--------------------------------------------------------------------------
/**
     This site will display the screen asking the associate to pick
     related items to add to the transaction.
     $Revision: /rgbustores_13.4x_generic_branch/1 $
 **/
//--------------------------------------------------------------------------
public class GetPickManyRelatedItemsSite extends PosSiteActionAdapter
{
    //----------------------------------------------------------------------
    /**
        This method displays the pick some screen.
        @param bus
        @see oracle.retail.stores.foundation.tour.ifc.SiteActionIfc#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        UtilityManagerIfc utility = (UtilityManagerIfc)  Gateway.getDispatcher().getManager(UtilityManagerIfc.TYPE);
        RelatedItemCargo cargo = (RelatedItemCargo)bus.getCargo();
        // add default to department list hash tables
        // retrieve department code list from reason codes.
        CodeListIfc deptMap=utility.getReasonCodes(cargo.getStoreID(),CodeConstantsIfc.CODE_LIST_DEPARTMENT);
        Locale locale = LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE);
        // retrieve department entries
        CodeEntryIfc[] deptEntries = deptMap.getEntries();
        HashMap map = new HashMap();
        for(int i=0; i < deptEntries.length; i++)
        {
            map.put(deptEntries[i].getCode(), deptEntries[i].getText(locale));
        }
        RelatedItemSummary.setDepartmentHash(map);

        // get the related item summaries from the related items

        SaleReturnLineItemIfc srli = (SaleReturnLineItemIfc) cargo.getTransaction().getLineItems()[cargo.getPrimaryItemSequenceNumber()];
        PLUItemIfc pluItem = srli.getPLUItem();
        RelatedItemIfc[] relatedItems = sortRelatedItems(pluItem.getRelatedItemContainer().getRelatedItems(RelatedItemGroupIfc.PICK_SOME));
        RelatedItemSummaryIfc sampleItems[] = new RelatedItemSummaryIfc[relatedItems.length];
        for (int i = 0; i < relatedItems.length; i++)
        {
            sampleItems[i] = relatedItems[i].getRelatedItemSummary();
        }

        // update bean model with matching items list
        RelatedItemListBeanModel relatedModel = new RelatedItemListBeanModel();
        PromptAndResponseModel responseModel = new PromptAndResponseModel();
        String[] args = new String[2];
        args[0] = pluItem.getPosItemID();
        args[1] = pluItem.getDescription(locale);
        responseModel.setArguments(args);
        relatedModel.setItemList(sampleItems);
        relatedModel.setPromptAndResponseModel(responseModel);

        // Display the screen
        POSUIManagerIfc  uimgr = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        uimgr.showScreen(POSUIManagerIfc.RELATED_ITEMS, relatedModel);
//        uimgr.showScreen(POSUIManagerIfc.PICK_ONE_RELATED_ITEM,relatedModel);
    }

    //----------------------------------------------------------------------
    /**
        This method captures the related items that have been chosen from the
        pick some screen.
        @param bus
        @see oracle.retail.stores.foundation.tour.ifc.SiteActionIfc#depart(oracle.retail.stores.foundation.tour.ifc.BusIfc)
    **/
    //----------------------------------------------------------------------
    public void depart(BusIfc bus)
    {
        POSUIManagerIfc  ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        RelatedItemListBeanModel relatedModel =
            (RelatedItemListBeanModel) ui.getModel(POSUIManagerIfc.RELATED_ITEMS);
        RelatedItemSummaryIfc ri[] = relatedModel.getSelectedItems();
        if(ri != null)
        {
            RelatedItemIfc[] relatedItems = new RelatedItemIfc[ri.length];
            RelatedItemCargo cargo = (RelatedItemCargo)bus.getCargo();
            SaleReturnLineItemIfc item = (SaleReturnLineItemIfc) cargo.getTransaction().getLineItems()[cargo.getPrimaryItemSequenceNumber()];
            RelatedItemIfc[] allRelatedItems = item.getPLUItem().getRelatedItemContainer().getRelatedItems(RelatedItemGroupIfc.PICK_SOME);
            for (int i = 0; i < ri.length; i++)
            {
                for (int j = 0; j < allRelatedItems.length; j++)
                {
                    if (allRelatedItems[j].getRelatedItemSummary().equals(ri[i]))
                    {
                        relatedItems[i] = allRelatedItems[j];
                        break;
                    }
                }
            }
            cargo.setRelatedItems(relatedItems);
        }
    }

    //----------------------------------------------------------------------
    /**
        Standard insertion sort by related item display priorities.
        @param relatedItems
        @return
    **/
    //----------------------------------------------------------------------
    RelatedItemIfc[] sortRelatedItems(RelatedItemIfc[] relatedItems)
    {
        int priority;
        RelatedItemIfc priorityItem;
        int j;
        for (int i = 1; i < relatedItems.length; i++)
        {
            priorityItem = relatedItems[i];
            priority = relatedItems[i].getDisplayPriority();
            j = i-1;
            while (j > -1 && relatedItems[j].getDisplayPriority() > priority)
            {
                relatedItems[j+1] = relatedItems[j];
                j = j - 1;
            }
            relatedItems[j+1] = priorityItem;
        }

        return relatedItems;
    }
}
