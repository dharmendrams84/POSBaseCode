/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/validate/AlterationsErrorSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 16:17:10 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *  1    360Commerce 1.0         5/22/2008 6:00:55 AM   subramanyaprasad gv CR
 *       31423: Added new error site to fix the bug.
 * $
 * 
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale.validate;

import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

public class AlterationsErrorSite extends PosSiteActionAdapter
{
    /**
        revision number supplied by source-code-control system
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
        no link customer screen name
    **/
    private static final String RESOURCE_ID = "NoLinkedCustomer";
    /**
        no link customer argument tag
    **/
    private static final String ARG_TAG = "NoLinkedCustomer.Return";
    /**
        no link customer argument text
    **/
    private static final String ARG_TEXT = "Return";

    //--------------------------------------------------------------------------
    /**
       Show the 'no linked customer' error dialog. <P>
       @param the service bus
    **/
    //--------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        // Get the managers from the bus
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        UtilityManagerIfc utility =
          (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        // set arg strings to alteration
        String args[] = new String[3];
        args[0] = utility.retrieveDialogText(ARG_TAG, ARG_TEXT);
        args[1] = args[0];
        args[2] = args[0];

        // Using "generic dialog bean", display the error dialog.
        DialogBeanModel dialogModel = new DialogBeanModel();
        dialogModel.setResourceID(RESOURCE_ID);
        dialogModel.setType(DialogScreensIfc.CONFIRMATION);
        dialogModel.setArgs(args);
        dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_NO, "Cancel");

        // set and display the model
        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
    }
}

