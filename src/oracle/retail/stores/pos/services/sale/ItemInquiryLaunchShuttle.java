/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/ItemInquiryLaunchShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:01 mszekely Exp $
 * ===========================================================================
 * NOTES
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    abondala  01/03/10 - update header date
 *    mchellap  09/30/08 - Added generated serialVersionUID
 *    mchellap  09/29/08 - QW-IIMO Updates for code review comments
 *    mchellap  09/19/08 - QW-IIMO
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale;

// Foundation imports
import oracle.retail.stores.pos.services.common.FinancialCargoShuttle;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.services.inquiry.iteminquiry.ItemInquiryCargo;

//--------------------------------------------------------------------------
/**
 Transfers the sale cargo to the item inquiry cargo. <P>
 **/
//--------------------------------------------------------------------------
public class ItemInquiryLaunchShuttle extends FinancialCargoShuttle implements ShuttleIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    private static final long serialVersionUID = 2388237183904798964L;

    // The calling service's cargo
    protected SaleCargo saleCargo = null;

    //----------------------------------------------------------------------
    /**
     Loads the sale cargo.
     @param  bus  Service Bus to copy cargo from.
     **/
    //----------------------------------------------------------------------
    public void load(BusIfc bus)
    {
        // load sale cargo
        super.load(bus);

        saleCargo = (SaleCargo) bus.getCargo();
    }

    //----------------------------------------------------------------------
    /**
     Sets the register and transaction type for the item inquiry service.
     <P>
     @param  bus Service Bus to copy cargo to.
     **/
    //----------------------------------------------------------------------
    public void unload(BusIfc bus)
    {

        // unload cargo
        super.unload(bus);

        ItemInquiryCargo itemInquiryCargo = (ItemInquiryCargo) bus.getCargo();
        itemInquiryCargo.setRegister(saleCargo.getRegister());

        //Skip general price inquiry to directly goto advanced inquiry
        itemInquiryCargo.setSkipPriceInquiryFlag(true);

        itemInquiryCargo.setTransaction(saleCargo.getTransaction());
    }

    //----------------------------------------------------------------------
    /**
     Returns the revision number of the class.
     <P>
     @return String representation of revision number
     **/
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return (Util.parseRevisionNumber(revisionNumber));
    }

}
