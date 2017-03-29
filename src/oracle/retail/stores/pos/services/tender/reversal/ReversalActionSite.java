/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/reversal/ReversalActionSite.java /rgbustores_13.4x_generic_branch/5 2011/07/25 10:22:07 blarsen Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   07/22/11 - Move the request building into ReversalCargo which
 *                         is called by the shuttles.
 *    cgreene   07/21/11 - remove DebitBinFileLookup and DebitCardsAccepted
 *                         parameters for APF
 *    blarsen   07/19/11 - Enhancing to consider reversals from post-void
 *                         (VOID_AUTH_PENDING). Updated to use new reversal
 *                         service cargo (ReversalCargo)
 *    blarsen   07/15/11 - Renamed rawJournalKey to journalKey.
 *    blarsen   07/15/11 - Initial version.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.reversal;

import oracle.retail.stores.domain.manager.ifc.PaymentManagerIfc;
import oracle.retail.stores.domain.manager.payment.ReversalRequestIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;


/**
 * Send reversals to payment manager.
 */
@SuppressWarnings("serial")
public class ReversalActionSite extends PosSiteActionAdapter
{

    /**
     * Send reversals to the payment manager.
     */
    @Override
    public void arrive(BusIfc bus)
    {
        String letter = "Success";

        ReversalCargo cargo = (ReversalCargo)bus.getCargo();

        for (ReversalRequestIfc request: cargo.getRequestList())
        {
            PaymentManagerIfc paymentManager = (PaymentManagerIfc)bus.getManager(PaymentManagerIfc.TYPE);
            paymentManager.reversal(request);
        }

        if (letter != null)
        {
            bus.mail(new Letter(letter), BusIfc.CURRENT);
        }
    }

}
