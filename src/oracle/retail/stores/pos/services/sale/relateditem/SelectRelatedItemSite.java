/* ===========================================================================
* Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/relateditem/SelectRelatedItemSite.java /rgbustores_13.4x_generic_branch/2 2011/08/24 17:14:39 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreen 08/24/11 - if pos item id is not available, use item id
 *    cgreen 05/26/10 - convert to oracle packaging
 *    abonda 01/03/10 - update header date
 *    dwfung 12/22/09 - Use POS item id to search for related items
 *
 * ===========================================================================
 * $Log:
 1    360Commerce 1.0         12/13/2005 4:47:05 PM  Barry A. Pape   
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale.relateditem;

import org.apache.commons.lang.StringUtils;

import oracle.retail.stores.domain.stock.RelatedItemGroupIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;

/**
 * This site determines if there are any related items left that have been
 * chosen or are automatic. If there are send the next one to the item lookup
 * service.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
@SuppressWarnings("serial")
public class SelectRelatedItemSite extends PosSiteActionAdapter
{
    /**
     * Check the related items left, get the next one to process or send done
     * letter. If there are any left, set that item to the sale line item and
     * send to lookup station.
     * 
     * @param bus
     * @see oracle.retail.stores.foundation.tour.ifc.SiteActionIfc#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void arrive(BusIfc bus)
    {
        RelatedItemCargo cargo = (RelatedItemCargo)bus.getCargo();
        int currentRelatedItem = cargo.getNextRelatedItem();
        String letter = "Continue";
        if (currentRelatedItem >= cargo.getRelatedItems().length)
        {
            // if we are in the pick one, we must go back and check that there
            // are no more groups
            if (cargo.getRelatedItemGroup().equals(RelatedItemGroupIfc.PICK_ONE))
            {
                letter = "One";
            }
            else
            {
                letter = "Done";
            }

        }
        else
        {
            // set the chosen related item
            cargo.setRelatedItem(cargo.getRelatedItems()[currentRelatedItem]);
            // set the item id so it can be looked up
            String itemId = cargo.getRelatedItem().getRelatedItemSummary().getPosItemID();
            if (StringUtils.isEmpty(itemId))
            {
                itemId = cargo.getRelatedItem().getRelatedItemSummary().getItemID();
            }
            cargo.setPLUItemID(itemId);
            currentRelatedItem++;
            cargo.setNextRelatedItem(currentRelatedItem);
        }
        bus.mail(new Letter(letter), BusIfc.CURRENT);
    }

}
