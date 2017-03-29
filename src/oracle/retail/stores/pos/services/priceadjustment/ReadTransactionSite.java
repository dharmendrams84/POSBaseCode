/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/priceadjustment/ReadTransactionSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:03 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:29:34 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:31 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:33 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/08/19 18:33:38  jriggins
 *   @scr 5034 Overrode displayNoTransactionsForNumber() so that a price adjustment specific message could be shown.
 *
 *   Revision 1.1  2004/03/30 00:04:59  jriggins
 *   @scr 3979 Price Adjustment feature dev
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.priceadjustment;

import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

//------------------------------------------------------------------------------
/**
    Given a transaction ID, attempts to retrieve the transaction data from the database.
     
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------

public class ReadTransactionSite extends oracle.retail.stores.pos.services.returns.returnfindtrans.ReadTransactionSite
{

    //----------------------------------------------------------------------
    /**
        Show the PRICE_ADJUST_TRANS_NOT_FOUND screen
        <P>
        @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void displayNoTransactionsForNumber(BusIfc bus)
    {
        // Get the ui manager
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        DialogBeanModel dialogModel = new DialogBeanModel();
        dialogModel.setResourceID("PriceAdjTransactionNotFound");
        dialogModel.setType(DialogScreensIfc.CONFIRMATION);
        dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_YES,"Retry");
        dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_NO,"ReturnItem");
        // display the screen
        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
    }

}
