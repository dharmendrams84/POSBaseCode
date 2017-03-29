/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifyitem/AttachItemGiftRegistryAisle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:25 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:27:14 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:19:41 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:09:32 PM  Robert Pearse
 *
 *   Revision 1.3  2004/02/12 16:51:01  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:39:28  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:17  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:01:30   CSchellenger
 * Initial revision.
 *
 *    Rev 1.1   Apr 11 2003 13:14:38   bwf
 * Remove deprecated Gift Registry calls.
 * Resolution for 2103: Remove uses of deprecated items in POS.
 *
 *    Rev 1.0   Apr 29 2002 15:16:38   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:36:50   msg
 * Initial revision.
 *
 *    Rev 1.1   01 Feb 2002 15:20:46   sfl
 * Took away the unnecessary gift registry id logging
 * from the E-Journal.
 * Resolution for POS SCR-962: Entering invalid Gift Registry numbers journals like valid numbers
 *
 *    Rev 1.0   Sep 21 2001 11:28:58   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:09:18   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifyitem;

//java imports
import java.lang.reflect.Field;

import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.utility.JournalConstantsIfc;

import oracle.retail.stores.domain.registry.RegistryIDIfc;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.tour.application.FinalLetter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;

//------------------------------------------------------------------------------
/**

    Update the modified item's Gift Registry
    Put it into cargo
    Mail a final letter
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public class AttachItemGiftRegistryAisle extends PosLaneActionAdapter
{

    /**
       revision number supplied by Team Connection
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //--------------------------------------------------------------------------
    /**
       Get UI input
       Put it into cargo update the modified item.
       Mail a final letter
       @param  BusIfc bus
       @return void
       @exception
    **/
    //--------------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {

        ItemCargo cargo = (ItemCargo)bus.getCargo();
        String itemID = cargo.getItem().getItemID();

        String newGiftId= new String("");
        String currentGiftId = new String("");

        StringBuffer buf =
            new StringBuffer("AttachItemGiftRegistryAisle Received input:");
        buf.append(newGiftId);
        if (logger.isInfoEnabled()) logger.info("" + buf.toString() + "");

        // if there is a current gift registry, update the ID.  If not,
        // create a new gift registry object with the new ID.
        RegistryIDIfc giftRegistry = null;

        StringBuffer sb = new StringBuffer();

        if (cargo.getItem().getRegistry() != null)  // one exists already
        {
            giftRegistry  = cargo.getItem().getRegistry();

            // Save existing gift reg ID to journal it as having been removed,
            // then set the new ID.
            currentGiftId = giftRegistry.getID();
            giftRegistry.setID(currentGiftId);
        }


        //then set the item to the new value in the modified giftregistry.
        cargo.getItem().modifyItemRegistry(giftRegistry, true);

//        sb.append(Util.EOL)
//          .append("ITEM: ") .append(itemID.trim())
//          .append(Util.EOL)
//          .append("  Gift Reg.: ") .append(currentGiftId)
//          .append(Util.EOL);

        Object dataArgs[]={itemID.trim()};
        String itemData=I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ITEM, dataArgs);

        //
        Object giftRegdataArgs[]={currentGiftId};
        String giftReg=I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.GIFT_REGISTER_LABEL, giftRegdataArgs);

        sb.append(Util.EOL)
          .append(itemData)
          .append(Util.EOL)
          .append(giftReg)
          .append(Util.EOL);


        // journal it here
        JournalManagerIfc journal;
        journal = (JournalManagerIfc)Gateway.getDispatcher()
                   .getManager(JournalManagerIfc.TYPE);
        if (journal != null)
        {
            journal.journal(cargo.getCashier().getEmployeeID(),
                            cargo.getTransactionID(),
                            sb.toString());
        }
        else
        {
            logger.warn( "No journal manager found!");
        }

        //And, move along.
        bus.mail(new FinalLetter("Next"), BusIfc.CURRENT);
    }

    //---------------------------------------------------------------------
    /**
       Returns a string representation of the object. <P>
       @return String representation of class
    **/
    //---------------------------------------------------------------------
    public String toString()
    {                                   // begin toString()

        // verbose flag
        boolean bVerbose = false;
        // result string
        String strResult = new String("Class:  AttachItemGiftRegistryAisle (Revision " +
                                      getRevisionNumber() +
                                      ")" +
                                      hashCode());
        // if verbose mode, do inspection gig
        if (bVerbose)
        {                               // begin verbose mode

            // theClass will ascend through the inheritance hierarchy
            Class theClass = getClass();
            // fieldType contains the type of the field currently being examined
            Class fieldType = null;
            // fieldName contains the name of the field currently being examined
            String fieldName = "";
            // fieldValue contains the value of the field currently being examined
            Object fieldValue = null;

            // Ascend through the class hierarchy, capturing field information
            while (theClass != null)
            {                           // begin loop through fields
                // fields contains all noninherited field information
                Field[] fields = theClass.getDeclaredFields();

                // Go through each field, capturing information
                for (int i = 0; i < fields.length; i++)
                {
                    fieldType = fields[i].getType();
                    fieldName = fields[i].getName();

                    // get the field's value, if possible
                    try
                    {
                        fieldValue = fields[i].get(this);
                    }
                    // if the value can't be gotten, say so
                    catch (IllegalAccessException ex)
                    {
                        fieldValue = "*no access*";
                    }

                    // If it is a "simple" field, use the value
                    if (Util.isSimpleClass(fieldType))
                    {
                        strResult += "\n\t" +
                            fieldName +
                            ":\t" +
                            fieldValue;
                    }       // if simple
                    // If it is a null value, say so
                    else if (fieldValue == null)
                    {
                        strResult += "\n\t" +
                            fieldName +
                            ":\t(null)";
                    }
                    // Otherwise, use <type<hashCode>
                    else
                    {
                        strResult += "\n\t" +
                            fieldName +
                            ":\t" +
                            fieldType.getName() +
                            "@" +
                            fieldValue.hashCode();
                    }
                }   // for each field
                theClass = theClass.getSuperclass();
            }                           // end loop through fields
        }                               // end verbose mode
        // pass back result
        return(strResult);

    };                                  // end toString()

    //---------------------------------------------------------------------
    /**
       Retrieves the Team Connection revision number. <P>
       @return String representation of revision number
    **/
    //---------------------------------------------------------------------
    public String getRevisionNumber()
    {                                   // begin getRevisionNumber()

        // return string
        return(revisionNumber);

    };                                  // end getRevisionNumber()

    //---------------------------------------------------------------------
    /**
       Main to run a test.. <P>
       @return void
    **/
    //---------------------------------------------------------------------
    public static void main(String args[])
    {                                   // begin main()

        // instantiate class
        ItemGiftRegistryModifiedAisle clsItemGiftRegistryModifiedAisle = new ItemGiftRegistryModifiedAisle();

        // output toString()
        System.out.println(clsItemGiftRegistryModifiedAisle.toString());

    };                                  // end main()
}
