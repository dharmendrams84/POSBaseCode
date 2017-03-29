/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/dailyoperations/register/registeropen/StoreOpenInTrainingModeErrorAisle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:17 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:30:13 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:35 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:29 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/04/07 17:50:56  tfritz
 *   @scr 3884 - Training Mode rework
 *
 *   Revision 1.1  2004/03/26 20:06:27  tfritz
 *   @scr 4136 - An error dialog now comes up when trying to open a store while in training mode
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.dailyoperations.register.registeropen;

import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.LaneActionAdapter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

//--------------------------------------------------------------------------
/**
    This aisle displays the store cannot open in training mode error dialog
    $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class StoreOpenInTrainingModeErrorAisle extends LaneActionAdapter
{
    /**
     revision number of this class
     **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * store prompt tag
     */
    protected static String STORE_PROMPT_TAG = "StoreRegisterTillTrainingModeOpenError.Store";
    /**
     * store prompt
     */
    protected static String STORE_PROMPT = "store";

    //--------------------------------------------------------------------------
    /**
     Displays error message.<P>
     @param bus the bus traversing this site
     **/
    //--------------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {
        // get ui handle
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        UtilityManagerIfc utility =
            (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
        
        // set bean model
        DialogBeanModel model = new DialogBeanModel();
        String args[] = new String[1];
        args[0] = utility.retrieveDialogText(STORE_PROMPT_TAG,
                STORE_PROMPT);
        model.setResourceID("StoreRegisterTillTrainingModeOpenError");
        model.setButtonLetter(DialogScreensIfc.BUTTON_OK, "Cancel");
        model.setType(DialogScreensIfc.ERROR);
        model.setArgs(args);
        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, model);
    }

    //----------------------------------------------------------------------
    /**
     Returns the revision number of the class. <P>
     @return String representation of revision number
     **/
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return(revisionNumber);
    }
}
