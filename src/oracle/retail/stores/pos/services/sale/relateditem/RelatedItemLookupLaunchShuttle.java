/* ===========================================================================
* Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/relateditem/RelatedItemLookupLaunchShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 16:17:11 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
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

import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.pos.services.common.FinancialCargoShuttle;
import oracle.retail.stores.domain.DomainGateway;
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
public class RelatedItemLookupLaunchShuttle extends FinancialCargoShuttle implements ShuttleIfc
{
    /**
     revision number
     **/
    public static final String revisionNumber = "$$";
    
    //  Calling service's cargo
    protected RelatedItemCargo relatedItemCargo = null;
    
    //  ----------------------------------------------------------------------
    /**
     Loads the item cargo.
     <P>
     @param  bus     Service Bus to copy cargo from.
     **/
    //  ----------------------------------------------------------------------
    public void load(BusIfc bus)
    {
        // load the financial cargo
        super.load(bus);
        
        relatedItemCargo = (RelatedItemCargo) bus.getCargo();
        
        // set the related item cargo info into the utility manager
        // doing this so that we do not have to pass this information through
        // a dozen or more services
        UtilityManagerIfc utility = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);
        RelatedItemTransactionInfoIfc riTransInfo = 
                       DomainGateway.getFactory().getRelatedItemTransactionInfoInstance();
        riTransInfo.setNextRelatedItem(relatedItemCargo.getNextRelatedItem());
        riTransInfo.setPrimaryItemSequenceNumber(relatedItemCargo.getPrimaryItemSequenceNumber());
        riTransInfo.setRelatedItem(relatedItemCargo.getRelatedItem());
        riTransInfo.setRelatedItemGroup(relatedItemCargo.getRelatedItemGroup());
        riTransInfo.setRelatedItems(relatedItemCargo.getRelatedItems());
        riTransInfo.setRelatedItemGroupNumber(relatedItemCargo.getGroupNumber());
        utility.setRelatedItemTransInfo(riTransInfo);
    }
    
    //  ----------------------------------------------------------------------
    /**
     Transfers the item cargo to the item inquiry cargo for the item inquiry service.
     <P>
     @param  bus     Service Bus to copy cargo to.
     **/
    //  ----------------------------------------------------------------------
    public void unload(BusIfc bus)
    {
        // unload the financial cargo
        super.unload(bus);
        
        ItemInquiryCargo inquiryCargo = (ItemInquiryCargo) bus.getCargo();
        inquiryCargo.setRegister(relatedItemCargo.getRegister());
        inquiryCargo.setTransaction(relatedItemCargo.getTransaction());
        inquiryCargo.setModifiedFlag(true);
        inquiryCargo.setStoreStatus(relatedItemCargo.getStoreStatus());
        inquiryCargo.setRegister(relatedItemCargo.getRegister());
        inquiryCargo.setOperator(relatedItemCargo.getOperator());
        inquiryCargo.setCustomerInfo(relatedItemCargo.getCustomerInfo());
        inquiryCargo.setTenderLimits(relatedItemCargo.getTenderLimits());
        inquiryCargo.setPLUItem(relatedItemCargo.getPLUItem());
        inquiryCargo.setRelatedItem(true);
        
        String geoCode = null;
        if(relatedItemCargo.getStoreStatus() != null &&
                        relatedItemCargo.getStoreStatus().getStore() != null)
        {
            geoCode = relatedItemCargo.getStoreStatus().getStore().getGeoCode();
        }
        
        inquiryCargo.setInquiry(LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE), relatedItemCargo.getPLUItemID(), "", "", geoCode);
        
        inquiryCargo.setIsRequestForItemLookup(true);
    }
    
    //  ----------------------------------------------------------------------
    /**
     Returns a string representation of this object.
     <P>
     @return String representation of object
     **/
    //  ----------------------------------------------------------------------
    public String toString()
    {                         
        return "Class:  RelatedItemLookupShuttle (Revision " +
        getRevisionNumber() +
        ")" + hashCode();
    }                                
    
    //  ----------------------------------------------------------------------
    /**
     Returns the revision number of the class.
     <P>
     @return String representation of revision number
     **/
    //  ----------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return(Util.parseRevisionNumber(revisionNumber));
    } 
}
