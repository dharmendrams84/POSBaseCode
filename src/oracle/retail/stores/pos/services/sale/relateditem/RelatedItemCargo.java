/* ===========================================================================
* Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/relateditem/RelatedItemCargo.java /rgbustores_13.4x_generic_branch/1 2011/05/05 16:17:11 mszekely Exp $
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

import oracle.retail.stores.pos.services.sale.SaleCargo;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.stock.RelatedItemContainerIfc;
import oracle.retail.stores.domain.stock.RelatedItemIfc;

//--------------------------------------------------------------------------
/**
     This cargo extends the sale cargo and contains the necessary information
     for completing the related item work.
     $Revision: /rgbustores_13.4x_generic_branch/1 $
 **/
//--------------------------------------------------------------------------
public class RelatedItemCargo extends SaleCargo
{    
    // related items chosen from container
    RelatedItemIfc[] relatedItems;
    
    // current related item performing lookup on
    RelatedItemIfc relatedItem;
    
    // current related item group (auto, some, one)
    String relatedItemGroup;    
    
    // primary item sequence number 
    int primaryItemSequenceNumber = 0;    
    
    // next related item number
    int nextRelatedItem = 0;
    
    // number of the group of pick one items
    int groupNumber = 0;

    //----------------------------------------------------------------------
    /**
         This method gets the primary item's sequence number.
         @return Returns the primaryItemSequenceNumber.
     **/
    //----------------------------------------------------------------------
    public int getPrimaryItemSequenceNumber()
    {
        return primaryItemSequenceNumber;
    }
    //----------------------------------------------------------------------
    /**
         This method sets the primary items sequence number.
         @param primaryItemSequenceNumber The primaryItemSequenceNumber to set.
     **/
    //----------------------------------------------------------------------
    public void setPrimaryItemSequenceNumber(int primaryItemSequenceNumber)
    {
        this.primaryItemSequenceNumber = primaryItemSequenceNumber;
    }
    //----------------------------------------------------------------------
    /**
         This method gets the related item container
         @return Returns the relatedItemContainer.
     **/
    //----------------------------------------------------------------------
    public RelatedItemContainerIfc getRelatedItemContainer()
    {
        return ((SaleReturnLineItemIfc)transaction.getLineItems()[primaryItemSequenceNumber]).getPLUItem().getRelatedItemContainer();
    }

    //----------------------------------------------------------------------
    /**
         This method gets the current related item.
         @return Returns the relatedItem.
     **/
    //----------------------------------------------------------------------
    public RelatedItemIfc getRelatedItem()
    {
        return relatedItem;
    }
    //----------------------------------------------------------------------
    /**
         This method sets the current related item.
         @param relatedItem The relatedItem to set.
     **/
    //----------------------------------------------------------------------
    public void setRelatedItem(RelatedItemIfc relatedItem)
    {
        this.relatedItem = relatedItem;
    }
    //----------------------------------------------------------------------
    /**
         This method gets the related item group type string
         @return Returns the relatedItemGroup.
     **/
    //----------------------------------------------------------------------
    public String getRelatedItemGroup()
    {
        return relatedItemGroup;
    }
    //----------------------------------------------------------------------
    /**
         This method sets the related item group type string.
         @param relatedItemGroup The relatedItemGroup to set.
     **/
    //----------------------------------------------------------------------
    public void setRelatedItemGroup(String relatedItemGroup)
    {
        this.relatedItemGroup = relatedItemGroup;
    }
    //----------------------------------------------------------------------
    /**
         This method gets the related Item array.
         @return Returns the relatedItems.
     **/
    //----------------------------------------------------------------------
    public RelatedItemIfc[] getRelatedItems()
    {
        return relatedItems;
    }
    //----------------------------------------------------------------------
    /**
         This method sets the related items array.
         @param relatedItems The relatedItems to set.
     **/
    //----------------------------------------------------------------------
    public void setRelatedItems(RelatedItemIfc[] relatedItems)
    {
        this.relatedItems = relatedItems;
    }
    //----------------------------------------------------------------------
    /**
         This method gets the next related item.
         @return Returns the nextRelatedItem.
     **/
    //----------------------------------------------------------------------
    public int getNextRelatedItem()
    {
        return nextRelatedItem;
    }
    //----------------------------------------------------------------------
    /**
         This sets the next related item.
         @param nextRelatedItem The nextRelatedItem to set.
     **/
    //----------------------------------------------------------------------
    public void setNextRelatedItem(int nextRelatedItem)
    {
        this.nextRelatedItem = nextRelatedItem;
    }
    //----------------------------------------------------------------------
    /**
         This method gets the group number.
         @return Returns the groupNumber.
     **/
    //----------------------------------------------------------------------
    public int getGroupNumber()
    {
        return groupNumber;
    }
    //----------------------------------------------------------------------
    /**
         This sets the group number.
         @param groupNumber The groupNumber to set.
     **/
    //----------------------------------------------------------------------
    public void setGroupNumber(int groupNumber)
    {
        this.groupNumber = groupNumber;
    }
}
