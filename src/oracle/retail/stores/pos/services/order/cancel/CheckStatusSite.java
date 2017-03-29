/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/cancel/CheckStatusSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:34 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:26 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:13 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:09:59 PM  Robert Pearse   
 *
 *   Revision 1.4  2004/03/03 23:15:12  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.3  2004/02/12 16:51:21  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:46  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:03:24   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:13:24   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:40:46   msg
 * Initial revision.
 * 
 *    Rev 1.2   Mar 10 2002 18:00:34   mpm
 * Externalized text in dialog messages.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.1   Jan 25 2002 17:28:56   dfh
 * updates to prevent modifications to canceled, completed, voided orders
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *
 *    Rev 1.0   Sep 24 2001 13:00:10   MPM
 *
 * Initial revision.
 *
 *
 *    Rev 1.1   Sep 17 2001 13:10:20   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.cancel;

// foundation imports
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.order.OrderConstantsIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.order.common.OrderCargo;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

//--------------------------------------------------------------------------
/**
    This site tests the current order status. If the order status is Completed
    or Canceled then displays the Cannot Modify error screen, otherwise
    mails a Success letter.
    <p>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class CheckStatusSite extends PosSiteActionAdapter
{

    /**
       revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
       Constant for error message screen id.
    **/
    public static final String CANNOT_MODIFY_ORDER = "CannotModifyOrder";

    /**
       Constant for error message argument text.
    **/
    public static final String COMPLETED = "Completed";

    /**
       Constant for error message argument text.
    **/
    public static final String CANCELED = "Canceled";

    /**
       Constant for error message argument text.
    **/
    public static final String VOIDED = "Voided";

    //----------------------------------------------------------------------
    /**
       Determines the current order status. If the order status is Completed,
       Canceled, or VOIDED then displays the Cannot Modify dialog screen,
       otherwise mails a Success letter to continue with cancelling the order.
       <p>
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------

    public void arrive(BusIfc bus)
    {

        // get the current order from cargo
        OrderCargo cargo = (OrderCargo)bus.getCargo();
        OrderIfc order = cargo.getOrder();
        int      status = order.getStatus().getStatus().getStatus();

        // get the ui manager
        POSUIManagerIfc       ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        UtilityManagerIfc utility =
          (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        // setup arg text strings for error screen
        String args[] = new String[4];
        args[0] = cargo.getServiceName();
        args[1] = utility.retrieveDialogText("CannotModifyOrder.Completed",
                                             COMPLETED);
        args[2] = args[1];

        // test the order status - if Completed or Canceled do NOT allow cancel, display error
        if (status == OrderConstantsIfc.ORDER_STATUS_COMPLETED ||
            status == OrderConstantsIfc.ORDER_STATUS_CANCELED ||
            status == OrderConstantsIfc.ORDER_STATUS_VOIDED)
        {
            // test if order canceled to re-use screen, change ui argument
            if (status == OrderConstantsIfc.ORDER_STATUS_CANCELED)
            {
                args[1] = utility.retrieveDialogText("CannotModifyOrder.Canceled",
                                                     CANCELED);
                args[2] = args[1];
            }
            else if (status == OrderConstantsIfc.ORDER_STATUS_VOIDED)
            {
                args[1] = utility.retrieveDialogText("CannotModifyOrder.Voided",
                                                     VOIDED);
                args[2] = args[1];
            }
            // setup and display the Cannot Modify Dialog screen
            DialogBeanModel model = new DialogBeanModel();
            model.setResourceID(CANNOT_MODIFY_ORDER);
            model.setButtonLetter(DialogScreensIfc.BUTTON_OK,CommonLetterIfc.FAILURE);
            model.setType(DialogScreensIfc.ERROR);
            model.setArgs(args);
            ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, model);
        }
        else
        {
            // mail the Success letter
            bus.mail(new Letter (CommonLetterIfc.SUCCESS),BusIfc.CURRENT);
        }

    }                           // end getRevisionNumber()

} // CheckStatusSite
