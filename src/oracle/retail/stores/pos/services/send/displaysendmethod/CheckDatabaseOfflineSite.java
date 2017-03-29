/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/send/displaysendmethod/CheckDatabaseOfflineSite.java /rgbustores_13.4x_generic_branch/2 2011/07/18 13:00:21 blarsen Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   07/15/11 - Fix misspelled word: retrival
 *    acadar    06/01/10 - meerged to the tip
 *    acadar    05/28/10 - merged with tip
 *    acadar    05/26/10 - refactor shipping code
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         5/7/2007 2:21:04 PM    Sandy Gu
 *         enhance shipping method retrieval and internal tax engine to handle
 *         tax rules
 *    3    360Commerce 1.2         3/31/2005 4:27:24 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:20:06 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:09:54 PM  Robert Pearse
 *
 *   Revision 1.3  2004/09/16 17:22:18  rsachdeva
 *   @scr 6791 Send Level In Progress
 *
 *   Revision 1.2  2004/06/21 13:16:07  lzhao
 *   @scr 4670: cleanup
 *
 *   Revision 1.1  2004/06/16 13:42:07  lzhao
 *   @scr 4670: refactoring Send for 7.0.
 *
 *   Revision 1.2  2004/05/26 20:35:52  rsachdeva
 *   @scr 4670 Send: Multiple Sends Fixed Crash because of Incorrect Merge from shippingAddress.xml (1.5) when CheckAddressStation was
 *   created
 *
 *   Revision 1.1  2004/05/26 16:37:47  lzhao
 *   @scr 4670: add capture customer and bill addr. same as shipping for send
 *
 *   Revision 1.3  2004/02/12 16:51:55  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:29  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:20  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:06:40   CSchellenger
 * Initial revision.
 *
 *    Rev 1.1   May 12 2003 10:42:56   sfl
 * No change
 * Resolution for POS SCR-2371: Send - Shipping Method Offlfine Dialog not displaying
 *
 *    Rev 1.0   May 12 2003 10:41:38   sfl
 * Initial revision.
 * Resolution for POS SCR-2371: Send - Shipping Method Offlfine Dialog not displaying
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.send.displaysendmethod;

//java imports

import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.ReadShippingMethodTransaction;
import oracle.retail.stores.domain.financial.ShippingMethodIfc;
import oracle.retail.stores.domain.shipping.ShippingMethodSearchCriteriaIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.send.address.SendCargo;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;





//------------------------------------------------------------------------------
/**

    Site to check if database is offline
    $Revision: /rgbustores_13.4x_generic_branch/2 $
**/
//------------------------------------------------------------------------------
public class CheckDatabaseOfflineSite extends PosSiteActionAdapter
{
    /**
        A signal to tell if the database is online. Set the default to be true.
    **/
    protected boolean signal = true;
    /**
       revision number supplied by Team Connection
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    //--------------------------------------------------------------------------
    /**
       For good postal code, checks database offline
       @param  BusIfc bus
    **/
    //--------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        POSUIManagerIfc ui=(POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        UtilityManagerIfc utility =
            (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        // A signal to tell if the database is online. Set the default to be true.
        signal = true;
        SendCargo cargo = (SendCargo) bus.getCargo();

        if (cargo.isTransactionLevelSendInProgress())
        {
            bus.mail(new Letter(CommonLetterIfc.NEXT), BusIfc.CURRENT);
            return;
        }
        // Try to do a database read.
        try
        {
            ReadShippingMethodTransaction shippingTransaction = null;

            shippingTransaction = (ReadShippingMethodTransaction) DataTransactionFactory.create(DataTransactionKeys.READ_SHIPPING_METHOD_TRANSACTION);

            // read the shipping mehtods
            ShippingMethodSearchCriteriaIfc searchCriteria = DomainGateway.getFactory().getShippingMethodSearchCriteria();
            searchCriteria.setLocaleRequestor(utility.getRequestLocales());
            searchCriteria.setGeoCode(cargo.getStoreStatus().getStore().getGeoCode());
            ShippingMethodIfc[] methods = shippingTransaction.readShippingMethod(searchCriteria);
        }
        catch (DataException e)
        {
            signal = false;
        }

        // Send letter NEXT to move to next step, the shipping method screen
        if (signal)
        {
            bus.mail(new Letter(CommonLetterIfc.NEXT), BusIfc.CURRENT);
        }
        else
        {
            DialogBeanModel dialogModel = new DialogBeanModel();
            dialogModel.setResourceID("SHIPPING_METHOD_OFFLINE");
            dialogModel.setType(DialogScreensIfc.ACKNOWLEDGEMENT);
            dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_OK, CommonLetterIfc.NEXT);

            //display dialog
            ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
        }
    }
}
