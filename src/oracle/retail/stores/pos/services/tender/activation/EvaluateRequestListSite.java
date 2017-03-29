/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/activation/EvaluateRequestListSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 11:02:38 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    asinton   05/04/11 - New activation service for APF.
 *    asinton   05/03/11 - New activation service for APF.
 *    asinton   05/03/11 - New activation service for APF.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.activation;

import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;

/**
 * This site evaluates the request list to see if more activations
 * are needed.
 * @author asinton
 * @since 13.4
 */
@SuppressWarnings("serial")
public class EvaluateRequestListSite extends PosSiteActionAdapter
{
    /** constant string for Activate letter. */ 
    public static final String ACTIVATE_LETTER = "Activate";

    /*
     * (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.application.SiteActionAdapter#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void arrive(BusIfc bus)
    {
        ActivationCargo cargo = (ActivationCargo)bus.getCargo();
        cargo.incrementCurrentIndex();
        String letter = CommonLetterIfc.SUCCESS;
        if(cargo.getCurrentRequest() != null)
        {
            letter = ACTIVATE_LETTER;
        }
        bus.mail(new Letter(letter), BusIfc.CURRENT);
    }

}
