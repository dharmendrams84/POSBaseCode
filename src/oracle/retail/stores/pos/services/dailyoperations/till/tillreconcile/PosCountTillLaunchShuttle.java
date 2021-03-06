/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/dailyoperations/till/tillreconcile/PosCountTillLaunchShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:19 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:29:22 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:11 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:06 PM  Robert Pearse   
 *
 *   Revision 1.4  2004/09/23 00:07:12  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.3  2004/04/15 20:38:54  dcobb
 *   @scr 4205 Feature Enhancement: Till Options
 *   Till reconcile service is now separate from till close.
 *
 *   Revision 1.2  2004/04/15 20:29:16  dcobb
 *   @scr 4205 Feature Enhancement: Till Options
 *   Till reconcile service is now separate from till close.
 *
 *   Revision 1.1  2004/04/15 18:57:00  dcobb
 *   @scr 4205 Feature Enhancement: Till Options
 *   Till reconcile service is now separate from till close.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.dailyoperations.till.tillreconcile;

import org.apache.log4j.Logger;

import oracle.retail.stores.domain.financial.FinancialCountIfc;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.services.dailyoperations.poscount.PosCountCargo;

//------------------------------------------------------------------------------
/**


    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------

public class PosCountTillLaunchShuttle implements ShuttleIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = 7345344591508057702L;

    /** 
        The logger to which log messages will be sent.
    **/
    protected static Logger logger = Logger.getLogger(oracle.retail.stores.pos.services.dailyoperations.till.tillreconcile.PosCountTillLaunchShuttle.class);

    public static final String SHUTTLENAME = "PosCountTillLaunchShuttle";

    protected TillReconcileCargo closecargo;

    //--------------------------------------------------------------------------
    /**
       ##COMMENT-LOAD##

       @param bus the bus being loaded
    **/
    //--------------------------------------------------------------------------
    public void load(BusIfc bus)
    {

        closecargo = (TillReconcileCargo) bus.getCargo();

    }

    //--------------------------------------------------------------------------
    /**
       ##COMMENT-UNLOAD##

       @param bus the bus being unloaded
    **/
    //--------------------------------------------------------------------------
    public void unload(BusIfc bus)
    {

        PosCountCargo cargo = (PosCountCargo) bus.getCargo();

        // Setup poscount cargo
        RegisterIfc register = (RegisterIfc) closecargo.getRegister();

        cargo.setRegister(register);

        cargo.setTillID(closecargo.getTillID());

        cargo.setCountType(PosCountCargo.TILL);

        if (closecargo.getTillCountType() == FinancialCountIfc.COUNT_TYPE_SUMMARY)
        {
            cargo.setSummaryFlag(true);
        }
        else
        {
            cargo.setSummaryFlag(false);
        }

    }
}
