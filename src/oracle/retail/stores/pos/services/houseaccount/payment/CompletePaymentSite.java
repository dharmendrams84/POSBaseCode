/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/houseaccount/payment/CompletePaymentSite.java /rgbustores_13.4x_generic_branch/2 2011/07/26 16:57:40 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/26/11 - repacked into houseaccount.payment
 *    ohorne    05/20/11 - created class
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.houseaccount.payment;

import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;

/**
 * Completes House Account Payment
 *
 * @since 13.4
 */
@SuppressWarnings("serial")
public class CompletePaymentSite extends PosSiteActionAdapter
{
    /** Constant for Exit Payment letter */
    public static final String EXIT_PAYMENT = "ExitPayment";

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.application.SiteActionAdapter#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void arrive(BusIfc bus)
    {
        logger.debug("Completed House Acount Payment");
        bus.mail(new Letter(EXIT_PAYMENT), BusIfc.CURRENT);
    }

}
