/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/dailyoperations/till/tillreconcile/PosCountFloatReturnShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:20 mszekely Exp $
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
 *    4    360Commerce 1.3         5/2/2008 6:14:35 AM    Manas Sahu      When
 *         parameter is set to detail and the float is not enetered by the
 *         operator in that case the Audit Log has to report the summary.
 *    3    360Commerce 1.2         3/31/2005 4:29:22 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:11 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:06 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/09/23 00:07:12  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.2  2004/04/15 20:38:54  dcobb
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

import oracle.retail.stores.domain.financial.FinancialTotalsIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.services.dailyoperations.poscount.PosCountCargo;

//------------------------------------------------------------------------------
/**


    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public class PosCountFloatReturnShuttle implements ShuttleIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = 182435794903939098L;

    /** 
        The logger to which log messages will be sent.
    **/
    protected static Logger logger = Logger.getLogger(oracle.retail.stores.pos.services.dailyoperations.till.tillreconcile.PosCountFloatReturnShuttle.class);

    protected FinancialTotalsIfc posCountTotal;
    
    protected FinancialTotalsIfc financialTotal;

    //--------------------------------------------------------------------------
    /**
       ##COMMENT-LOAD##

       @param bus the bus being loaded
    **/
    //--------------------------------------------------------------------------
    public void load(BusIfc bus)
    {

        PosCountCargo cargo = (PosCountCargo) bus.getCargo();

        posCountTotal = cargo.getUpdateTotals();
        financialTotal = cargo.getFinancialTotals();

    }

    //--------------------------------------------------------------------------
    /**
       ##COMMENT-UNLOAD##

       @param bus the bus being unloaded
    **/
    //--------------------------------------------------------------------------
    public void unload(BusIfc bus)
    {

        TillReconcileCargo cargo = (TillReconcileCargo) bus.getCargo();

        cargo.setFloatTotals(posCountTotal);
        cargo.setFinancialTotals(financialTotal);
    }
}
