/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifytransaction/retrieve/DisplaySuspendedCancelAisle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:30 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:27:50 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:21:06 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:40 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:51:12  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:45  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:02:34   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:15:46   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:39:06   msg
 * Initial revision.
 * 
 *    Rev 1.1   Jan 19 2002 10:28:22   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 * 
 *    Rev 1.0   Sep 21 2001 11:31:02   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:09:46   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifytransaction.retrieve;
// Foundation imports
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.ListBeanModel;

//------------------------------------------------------------------------------
/**
    Retrieves selected transaction summary from user interface. <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public class DisplaySuspendedCancelAisle extends PosLaneActionAdapter
{                                       // begin class DisplaySuspendedCancelAisle
    /**
       revision number supplied by source-code control system
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
       lane name constant
    **/
    public static final String LANENAME = "DisplaySuspendedCancelAisle";

    //--------------------------------------------------------------------------
    /**
       Retrieves selected reason code from user interface. <P>
       @param bus the bus traversing this lane
    **/
    //--------------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {                                   // begin traverse()

        // selected row from ui
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        ListBeanModel listModel = 
          (ListBeanModel) ui.getModel(POSUIManagerIfc.SUSPEND_LIST);
        int selected = listModel.getSelectedRow();
        // set selected transaction summary in cargo
        ModifyTransactionRetrieveCargo cargo = 
          (ModifyTransactionRetrieveCargo) bus.getCargo();
        cargo.setSelectedSummary(selected);
        if (logger.isInfoEnabled()) logger.info( 
                    "Selected index: " + Integer.toString(selected) + "");

        bus.mail(new Letter("DoCancel"), BusIfc.CURRENT);

    }                                   // end traverse()

}                                       // end class DisplaySuspendedCancelAisle
