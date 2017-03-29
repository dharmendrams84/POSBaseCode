/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/printing/ReceiptOptionsUISite.java /main/7 2011/02/16 09:13:29 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    jswan     10/22/10 - Fixed issue with cancelling transaction on screen
 *                         timeout.
 *    rsnayak   10/07/10 - Bill Pay E-Receipt
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    arathore  02/02/09 - Removed Receipt Options for Layaway and Order
 *                         Transactions.
 *    arathore  11/17/08 - updated for ereceipt feature
 *    arathore  11/17/08 - Site to display receipt options.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.printing;

import oracle.retail.stores.common.parameter.ParameterConstantsIfc;
import oracle.retail.stores.domain.transaction.BillPayTransactionIfc;
import oracle.retail.stores.domain.transaction.LayawayTransactionIfc;
import oracle.retail.stores.domain.transaction.OrderTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonActionsIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DataInputBeanModel;

/**
 * Display the printing options screen if configured.
 * 
 * @version $Revision: /main/7 $
 */
public class ReceiptOptionsUISite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = -7064815812151744479L;
    /** revision number */
    public static final String revisionNumber = "$Revision: /main/7 $";

    /**
     * Display the printing options screen if configured.
     * 
     * @param bus the bus arriving at this site
     */
    @Override
    public void arrive(BusIfc bus) 
    {
        PrintingCargo cargo = (PrintingCargo) bus.getCargo();
        ParameterManagerIfc pm = (ParameterManagerIfc) bus.getManager(ParameterManagerIfc.TYPE);
        Letter letter = new Letter(CommonLetterIfc.CONTINUE);
        
        // See if printing is configured
        boolean printConfig = true;
        try
        {
            printConfig = pm.getBooleanValue(ParameterConstantsIfc.PRINTING_PrintReceipts);
        }
        catch (ParameterException pe)
        {
            logger.error("Could not determine print setting.", pe);
        }

        // print receipt if configured
        if (printConfig) 
        {
            //See if eReceipt functionality is enabled.
            String printingOptions = cargo.getParameterValue(pm, ParameterConstantsIfc.PRINTING_eReceiptFunctionality);

            //Display ReceiptOptions screen if configured.
            if (ParameterConstantsIfc.YES.equals(printingOptions) 
            		&& cargo.getTransaction() instanceof SaleReturnTransactionIfc
            		&& !(cargo.getTransaction() instanceof LayawayTransactionIfc)
            		&& !(cargo.getTransaction() instanceof OrderTransactionIfc)) 
            {
                DataInputBeanModel model = new DataInputBeanModel();
                
                // Mail the "Print" letter when the screen times out rather than the "Timeout"
                // letter.  This will print the receipt to the physical printer and exit the
                // printing service normally.
                model.getTimerModel().setActionName(CommonActionsIfc.PRINT);

                // get the POS UI manager
                POSUIManagerIfc uiManager = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
                
                // show screen.
                uiManager.showScreen(POSUIManagerIfc.RECEIPT_OPTIONS_SCREEN, model);
                return;
            }
            else if (ParameterConstantsIfc.YES.equals(printingOptions) && cargo.getTransaction() instanceof BillPayTransactionIfc)
            { // Display RECEIPT_OPTIONS_SCREEN for Bill Pay if E-Receipt is
                // enabled
                DataInputBeanModel model = new DataInputBeanModel();

                // Mail the "Print" letter when the screen times out rather than the "Timeout"
                // letter.  This will print the receipt to the physical printer and exit the
                // printing service normally.
                model.getTimerModel().setActionName(CommonActionsIfc.PRINT);

                // get the POS UI manager
                POSUIManagerIfc uiManager = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);

                // show screen.
                uiManager.showScreen(POSUIManagerIfc.RECEIPT_OPTIONS_SCREEN, model);
                return;

            }
            else //mail the print letter if eReceipt is not configured. 
            {
                letter = new Letter(CommonLetterIfc.PRINT);
            }
        }

        //mail the letter
        bus.mail(letter, BusIfc.CURRENT);
    }
}
