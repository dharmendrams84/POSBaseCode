/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/UserAccessOverrideNoticeAisle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:53 mszekely Exp $
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
 *    4    360Commerce 1.3         1/25/2006 4:11:54 PM   Brett J. Larsen merge
 *          7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
 *    3    360Commerce 1.2         3/31/2005 4:30:41 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:26:37 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:15:27 PM  Robert Pearse   
 *:
 *    4    .v700     1.2.1.0     11/17/2005 16:39:22    Jason L. DeLeau 4345:
 *         Replace any uses of Gateway.log() with the log4j.
 *    3    360Commerce1.2         3/31/2005 15:30:41     Robert Pearse
 *    2    360Commerce1.1         3/10/2005 10:26:37     Robert Pearse
 *    1    360Commerce1.0         2/11/2005 12:15:27     Robert Pearse
 *
 *   Revision 1.4  2004/09/23 00:07:11  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.3  2004/02/12 16:49:08  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:38:50  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 15:54:54   CSchellenger
 * Initial revision.
 *
 *    Rev 1.0   Apr 29 2002 15:35:28   msg
 * Initial revision.
 *
 *    Rev 1.1   Mar 18 2002 23:10:44   msg
 * - updated copyright
 *
 *    Rev 1.0   Mar 18 2002 11:23:32   msg
 * Initial revision.
 *
 *    Rev 1.0   Sep 21 2001 11:13:58   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:06:00   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.LaneActionAdapter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.LaneActionIfc;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

//------------------------------------------------------------------------------

/**
     The UserAccessOverrideNoticeAisle is traversed when the operator attempts
     access a function the she does have the authority to use.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/

//------------------------------------------------------------------------------
public class UserAccessOverrideNoticeAisle extends LaneActionAdapter implements LaneActionIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = 4488452278805026847L;

    /**
        revision number supplied by Team Connection
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
        class name constant
    **/
    public static final String LANENAME = "UserAccessOverrideNoticeAisle";

    //--------------------------------------------------------------------------
    /**
        The UserAccessOverrideNoticeAisle is traversed when the operator attempts
        access a function she not does have the authority to use.
         @param bus the bus traversing this lane

    **/
    //--------------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {
    	if (logger.isDebugEnabled()) logger.debug(LANENAME + ".traverse starting...");

        POSUIManagerIfc ui= (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);

        // Using "generic dialog bean".
        DialogBeanModel model = new DialogBeanModel();

        // Set model to same name as dialog in config\posUI.properties
        // Set button and arugments
        // set and display the model
        model.setResourceID("SecurityErrorNotice");
        model.setType(DialogScreensIfc.CONFIRMATION);

        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, model);

        if (logger.isDebugEnabled()) logger.debug(LANENAME + ".traverse ending...");
    }

    //----------------------------------------------------------------------
    /**
        Returns a string representation of this object.
        <P>
        @param none
        @return String representation of object
    **/
    //----------------------------------------------------------------------
    public String toString()
    {                                   // begin toString()
        // result string
        String strResult = new String("Class:  UserAccessOverrideNoticeAisle (Revision " +
                                      getRevisionNumber() +
                                      ")" + hashCode());

        // pass back result
        return(strResult);
    }                                   // end toString()

    //----------------------------------------------------------------------
    /**
        Returns the revision number of the class.
        <P>
        @param none
        @return String representation of revision number
    **/
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    {                                   // begin getRevisionNumber()
        // return string
        return(revisionNumber);
    }                                   // end getRevisionNumber()

} // end class UserAccessOverrideNoticeAisle
