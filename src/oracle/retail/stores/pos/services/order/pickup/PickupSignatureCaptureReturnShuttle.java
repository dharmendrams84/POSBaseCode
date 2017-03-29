/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/pickup/PickupSignatureCaptureReturnShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:33 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    aphulamb  11/24/08 - Checking files after code review by amrish
 *    aphulamb  11/23/08 - Set the signature capture on the pickedup item
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.pickup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import oracle.retail.stores.pos.ado.ADO;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.financial.PaymentIfc;
import oracle.retail.stores.domain.order.OrderDeliveryDetailIfc;
import oracle.retail.stores.domain.order.OrderRecipientIfc;
import oracle.retail.stores.domain.tender.TenderChargeIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.services.order.pickup.PickupOrderCargo;
import oracle.retail.stores.pos.services.signaturecapture.SignatureCaptureCargo;

public class PickupSignatureCaptureReturnShuttle implements ShuttleIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = 5657604093747456851L;

    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";;

    /** signature capture cargo */
    SignatureCaptureCargo sigCargo = null;

    /*
     * load Signature Capture cargo
     *
     * @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#load(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    public void load(BusIfc bus)
    {
        sigCargo = (SignatureCaptureCargo)bus.getCargo();
    }

    /*
     * load signature capture cargo data into pickup order cargo
     *
     * @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#unload(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    public void unload(BusIfc bus)
    {
        PickupOrderCargo cargo = (PickupOrderCargo)bus.getCargo();
        OrderRecipientIfc orderRecipient = DomainGateway.getFactory().getOrderRecipientInstance();
        orderRecipient.setCustomerSignature(sigCargo.getSignature());
        Collection<OrderRecipientIfc> orderRecipientsCollection = new ArrayList<OrderRecipientIfc>();
        orderRecipientsCollection.add(orderRecipient);
        cargo.getTransaction().setOrderRecipient(orderRecipientsCollection);
    }
}
