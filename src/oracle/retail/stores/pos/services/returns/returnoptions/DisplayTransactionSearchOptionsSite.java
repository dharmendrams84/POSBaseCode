/* ===========================================================================
* Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returns/returnoptions/DisplayTransactionSearchOptionsSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:54 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    cgreen 05/26/10 - convert to oracle packaging
 *    jswan  05/11/10 - Pre code reveiw clean up.
 * 
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returnoptions;
// foundation imports
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;

//------------------------------------------------------------------------------
/**
    This site displays the options available for searching for transactions.
**/
//--------------------------------------------------------------------------
public class DisplayTransactionSearchOptionsSite extends PosSiteActionAdapter
{ 
    /** serialVersionUID */
    private static final long serialVersionUID = 9103697407982388375L;
    
    /** site name constant **/
    public static final String SITENAME = "DisplayNoReceiptOptionsSite";

    //--------------------------------------------------------------------------
    /**
       Displays return transaction search options menu.
       @param bus the bus arriving at this site
    **/
    //--------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        // get ui reference and display screen
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        ui.showScreen(POSUIManagerIfc.RETURN_TRANSACTION_SEARCH, new POSBaseBeanModel());
    }
}
