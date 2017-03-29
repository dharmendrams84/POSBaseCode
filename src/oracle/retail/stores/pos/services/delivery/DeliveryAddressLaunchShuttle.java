/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/delivery/DeliveryAddressLaunchShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:49 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    abondala  01/03/10 - update header date
 *    aphulamb  11/22/08 - Checking files after code review by Naga
 *    aphulamb  11/13/08 - Check in all the files for Pickup Delivery Order
 *                         functionality
 *    aphulamb  11/13/08 - Delivery Address launch shuttle
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.delivery;

import oracle.retail.stores.pos.services.common.FinancialCargoShuttle;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.services.pickup.PickupDeliveryOrderCargo;

public class DeliveryAddressLaunchShuttle extends FinancialCargoShuttle implements ShuttleIfc
{

    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = 2171402395207560507L;

    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
       item cargo
    **/
    protected PickupDeliveryOrderCargo pickupDeliveryOrderCargo = null;
    //----------------------------------------------------------------------
    /**
       Loads PickupDeliveryOrderCargo cargo from modify item delivery service. <P>
       @param  bus Service Bus
    **/
    //----------------------------------------------------------------------
    public void load(BusIfc bus)
    {                                   // begin load()
        pickupDeliveryOrderCargo = (PickupDeliveryOrderCargo)bus.getCargo();
    }                                   // end load()

    //----------------------------------------------------------------------
    /**
       Loads data into deliveryAddress service. <P>
       @param  bus Service Bus
    **/
    //----------------------------------------------------------------------
    public void unload(BusIfc bus)
    {                                   // begin unload()

    	PickupDeliveryOrderCargo cargo = (PickupDeliveryOrderCargo)bus.getCargo();
    	cargo.setOrderTransaction(pickupDeliveryOrderCargo.getOrderTransaction());
    	cargo.setTransaction(pickupDeliveryOrderCargo.getTransaction());
    	cargo.setLineItems(pickupDeliveryOrderCargo.getLineItems());
    	cargo.setCustomer(pickupDeliveryOrderCargo.getTransaction().getCustomer());

    }                                   // end unload()

    //----------------------------------------------------------------------
    /**
       Returns a string representation of this object.  <P>
       @return String representation of object
    **/
    //----------------------------------------------------------------------
    public String toString()
    {                                   // begin toString()
        // result string
        String strResult = new String("Class:  ShippingAddressLaunchShuttle (Revision " +
                                      getRevisionNumber() +
                                      ") @" + hashCode());
        // pass back result
        return(strResult);
    }                                   // end toString()

}
