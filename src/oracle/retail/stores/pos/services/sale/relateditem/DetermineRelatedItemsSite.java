/* ===========================================================================
* Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/relateditem/DetermineRelatedItemsSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 16:17:11 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
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

import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.stock.RelatedItemGroupIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;

//--------------------------------------------------------------------------
/**
     This site determines if there are related items available.
     $Revision: /rgbustores_13.4x_generic_branch/1 $
 **/
//--------------------------------------------------------------------------
public class DetermineRelatedItemsSite extends PosSiteActionAdapter
{
    
    //----------------------------------------------------------------------
    /**
        This method determines if there are related items available.
        @param bus
        @see oracle.retail.stores.foundation.tour.ifc.SiteActionIfc#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        RelatedItemCargo cargo = (RelatedItemCargo) bus.getCargo();
        String letter = "Done";
        boolean done = false;
        
        SaleReturnLineItemIfc item = (SaleReturnLineItemIfc) cargo.getTransaction().getLineItems()[cargo.getPrimaryItemSequenceNumber()];
        // determine what related item to handle next
        // or to finish
        while(!done &&
              item.getPLUItem().getRelatedItemContainer() != null)
        {
            // if the group hasnt been set yet
            if (cargo.getRelatedItemGroup() == null)
            {
                letter = RelatedItemGroupIfc.AUTOMATIC;
                // re-initialize item count
                cargo.setNextRelatedItem(0);
            }
            else if (cargo.getRelatedItemGroup().equals(RelatedItemGroupIfc.AUTOMATIC))
            {
                letter = RelatedItemGroupIfc.PICK_SOME;
            }
            else if (cargo.getRelatedItemGroup().equals(RelatedItemGroupIfc.PICK_SOME))
            {
                letter = RelatedItemGroupIfc.PICK_ONE;
            }
            else // we have come to the end and there are no more related items
            {
                letter = "Done";
                done = true;
            }
            
            cargo.setRelatedItemGroup(letter);            
            // make sure there are related items of that type before moving on
            if (!done &&
                item.getPLUItem().getRelatedItemContainer().getRelatedItemGroups(letter).length != 0)
            {
                done = true;
                
                // if automatic get all the items for use
                // in the tour
                if (letter.equals(RelatedItemGroupIfc.AUTOMATIC))
                {
                    cargo.setRelatedItems(
                            item.getPLUItem().getRelatedItemContainer().getRelatedItems(RelatedItemGroupIfc.AUTOMATIC));
                }
                // reset next related item
                cargo.setNextRelatedItem(0);
            }        
        }
        
        bus.mail(new Letter(letter), BusIfc.CURRENT);                     
    }
}
