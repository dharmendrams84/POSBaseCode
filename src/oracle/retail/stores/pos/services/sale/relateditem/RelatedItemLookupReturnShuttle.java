/* ===========================================================================
* Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/relateditem/RelatedItemLookupReturnShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 16:17:11 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 2    360Commerce 1.1         1/22/2006 11:45:02 AM  Ron W. Haight   removed
 *    references to com.ibm.math.BigDecimal
 1    360Commerce 1.0         12/13/2005 4:47:04 PM  Barry A. Pape   
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale.relateditem;

import java.math.BigDecimal;

import oracle.retail.stores.pos.services.common.FinancialCargoShuttle;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.utility.RelatedItemTransactionInfoIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.inquiry.iteminquiry.ItemInquiryCargo;

//--------------------------------------------------------------------------
/**

     $Revision: /rgbustores_13.4x_generic_branch/1 $
 **/
//--------------------------------------------------------------------------
public class RelatedItemLookupReturnShuttle extends FinancialCargoShuttle implements ShuttleIfc
{
    /**
        revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
        Child service's cargo
    **/
    protected ItemInquiryCargo inquiryCargo = null;

    //----------------------------------------------------------------------
    /**
        This shuttle copies information from the Item Inquiry service back
        to the Modify Item service.
        <P>
        @param  bus     Service Bus to copy cargo from.
    **/
    //----------------------------------------------------------------------
    public void load(BusIfc bus)
    {
        inquiryCargo = (ItemInquiryCargo) bus.getCargo();
    }

    //----------------------------------------------------------------------
    /**
        Copies the new item to the cargo for the Modify Item service.
        <P>
        @param  bus     Service Bus to copy cargo to.
    **/
    //----------------------------------------------------------------------
    public void unload(BusIfc bus)
    {
        RelatedItemCargo cargo = (RelatedItemCargo) bus.getCargo();
        if (inquiryCargo.getModifiedFlag())
        {
            PLUItemIfc pluItem = inquiryCargo.getPLUItem();
            BigDecimal itemQuantity = inquiryCargo.getItemQuantity();

            if (pluItem != null)
            {
                cargo.setTransaction((SaleReturnTransactionIfc)inquiryCargo.getTransaction());
                
                if (cargo.getTransaction() == null)
                {
                    cargo.initializeTransaction(bus);
                }
                UtilityManagerIfc utility = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
                RelatedItemTransactionInfoIfc relatedItemTransInfo = utility.getRelatedItemTransInfo();
                
                cargo.setPLUItem(pluItem);
                cargo.setItemQuantity(itemQuantity);
                cargo.setItemSerial(inquiryCargo.getItemSerial());
                SaleReturnLineItemIfc  srli = cargo.getTransaction().addPLUItem(cargo.getPLUItem(), cargo.getItemQuantity());
                srli.setRelatedItemReturnable(relatedItemTransInfo.getRelatedItem().isReturnable());
                srli.setRelatedItemDeleteable(relatedItemTransInfo.getRelatedItem().isDeleteable());
                srli.setRelatedItemSequenceNumber(relatedItemTransInfo.getPrimaryItemSequenceNumber());

                // set the related item cargo
                cargo.setRelatedItemGroup(relatedItemTransInfo.getRelatedItemGroup());
                cargo.setNextRelatedItem(relatedItemTransInfo.getNextRelatedItem());
                cargo.setPrimaryItemSequenceNumber(relatedItemTransInfo.getPrimaryItemSequenceNumber());
                cargo.setRelatedItems(relatedItemTransInfo.getRelatedItems());
                cargo.setGroupNumber(relatedItemTransInfo.getRelatedItemGroupNumber());
                
                // set old sale return line item to have new related item sale return line item in it
                // for use with deletes
                SaleReturnLineItemIfc[] items = (SaleReturnLineItemIfc[])cargo.getTransaction().getLineItems();
                items[cargo.getPrimaryItemSequenceNumber()].addRelatedItemLineItem(srli);
                cargo.getTransaction().setLineItems(items);
                
                if (pluItem.isAlterationItem())
                {
                     srli.setAlterationItemFlag(true);
                }
                cargo.setLineItem(srli);  
            }
        }
        
        // get the transaction if it was created for the date.
        if (inquiryCargo.getTransaction() != null &&
            ((SaleReturnTransactionIfc)inquiryCargo.getTransaction()).getAgeRestrictedDOB() != null)
        {
            cargo.setTransaction((SaleReturnTransactionIfc)inquiryCargo.getTransaction());
        }          
        
    }

    //----------------------------------------------------------------------
    /**
        Returns a string representation of this object.
        <P>
        @return String representation of object
    **/
    //----------------------------------------------------------------------
    public String toString()
    {                                   // begin toString()
        // result string
        String strResult = new String("Class:  RelatedItemLookupReturnShuttle (Revision " +
                                      getRevisionNumber() +
                                      ")" + hashCode());

        // pass back result
        return(strResult);
    }                                   // end toString()

    //----------------------------------------------------------------------
    /**
        Returns the revision number of the class.
        <P>
        @return String representation of revision number
    **/
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    {                                   // begin getRevisionNumber()
        // return string
        return(Util.parseRevisionNumber(revisionNumber));
    }                                  // end getRevisionNumber()
}
