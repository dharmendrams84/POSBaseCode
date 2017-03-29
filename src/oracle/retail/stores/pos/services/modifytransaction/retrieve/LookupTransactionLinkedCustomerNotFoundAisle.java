/*===========================================================================
* Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved. 
* ===========================================================================
* $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifytransaction/retrieve/LookupTransactionLinkedCustomerNotFoundAisle.java /rgbustores_13.4x_generic_branch/1 2012/01/31 16:08:55 mjwallac Exp $
* ===========================================================================
* NOTES
* <other useful comments, qualifications, etc.>
*
* MODIFIED    (MM/DD/YY)
* mjwallac    01/31/12 - incorporate code review comments.
* mjwallac    01/27/12 - Forward port: SQL Exception when trying to save a
*                        resumed order transaction that had been linked to a
*                        customer, but customer was deleted before resuming.
* mjwallac    01/27/12 - Creation
* spurkaya    10/25/11 - Modified Not to retrieve order transaction and
*                        display an error dialog when the associated customer
*                        is deleted.
*
* ===========================================================================
*/
package oracle.retail.stores.pos.services.modifytransaction.retrieve;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

public class LookupTransactionLinkedCustomerNotFoundAisle extends PosLaneActionAdapter
{

    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * Display an error message, wait for user acknowledgment.
     * <P>
     * 
     * @param bus Service Bus
     **/
    public void traverse(BusIfc bus)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);

        DialogBeanModel model = new DialogBeanModel();
        model.setResourceID("SuspendTransactionLinkedCustomerNotFound");
        model.setButtonLetter(0, CommonLetterIfc.FAILURE);
        model.setType(DialogScreensIfc.ERROR);

        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, model);
  
    }

    /**
     * Returns the revision number of the class.
     * <P>
     * 
     * @return String representation of revision number
     **/
    public String getRevisionNumber()
    {
        return (revisionNumber);
    }
}
