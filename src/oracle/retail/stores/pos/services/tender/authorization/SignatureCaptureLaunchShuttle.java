/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/authorization/SignatureCaptureLaunchShuttle.java /rgbustores_13.4x_generic_branch/4 2011/09/21 14:28:24 jswan Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    jswan     09/21/11 - Fixed failure to display signature on signature
 *                         verification screen after 1st time.
 *    ohorne    08/09/11 - APF:foreign currency support
 *    jswan     06/22/11 - Modified to support signature capture in APF.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.authorization;

import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.services.signaturecapture.SignatureCaptureCargo;

/**
 *  Transfers the tender amount 

    @version $Revision: /rgbustores_13.4x_generic_branch/4 $
**/
public class SignatureCaptureLaunchShuttle implements ShuttleIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -7039998886733837781L;


    /**
       Cargo carrying the tender amount 
    **/
    protected AuthorizationCargo tenderCargo = null;

    //----------------------------------------------------------------------
    /**
        
        @param bus
        @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#load(oracle.retail.stores.foundation.tour.ifc.BusIfc)
    **/
    //----------------------------------------------------------------------
    public void load(BusIfc bus)
    {
        tenderCargo = (AuthorizationCargo)bus.getCargo();
    }

    //----------------------------------------------------------------------
    /**
        
        @param bus
        @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#unload(oracle.retail.stores.foundation.tour.ifc.BusIfc)
    **/
    //----------------------------------------------------------------------
    public void unload(BusIfc bus)
    {
        SignatureCaptureCargo sigCargo = (SignatureCaptureCargo)bus.getCargo();
        sigCargo.setAuthAmount(tenderCargo.getCurrentResponse().getBaseAmount());
        sigCargo.setVerifySignature(true);
    }

}
