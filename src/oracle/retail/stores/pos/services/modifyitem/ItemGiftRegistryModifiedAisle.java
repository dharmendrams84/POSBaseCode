/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifyitem/ItemGiftRegistryModifiedAisle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:25 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:28:31 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:22:26 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:11:37 PM  Robert Pearse
 *
 *   Revision 1.6  2004/06/03 14:47:43  epd
 *   @scr 5368 Update to use of DataTransactionFactory
 *
 *   Revision 1.5  2004/04/20 13:17:05  tmorris
 *   @scr 4332 -Sorted imports
 *
 *   Revision 1.4  2004/04/14 15:17:09  pkillick
 *   @scr 4332 -Replaced direct instantiation(new) with Factory call.
 *
 *   Revision 1.3  2004/02/12 16:51:02  mcs
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
 *    Rev 1.0   Aug 29 2003 16:01:38   CSchellenger
 * Initial revision.
 *
 *    Rev 1.3   Apr 11 2003 12:47:08   bwf
 * Deprecation Fix for Gift Registry.
 * Resolution for 2103: Remove uses of deprecated items in POS.
 *
 *    Rev 1.2   Mar 05 2003 10:46:48   RSachdeva
 * Clean Up Code Conversion
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.1   Oct 10 2002 08:22:42   RSachdeva
 * Code Conversion
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.0   Apr 29 2002 15:16:58   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:37:08   msg
 * Initial revision.
 *
 *    Rev 1.3   12 Mar 2002 16:52:54   pdd
 * Modified to use the factory.
 * Resolution for POS SCR-1332: Ensure domain objects are created through factory
 *
 *    Rev 1.2   Mar 09 2002 18:36:40   mpm
 * Externalized text.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.1   01 Feb 2002 15:21:38   sfl
 * Took away the unnecessary gift registry id logging
 * from the E-Journal.
 * Resolution for POS SCR-962: Entering invalid Gift Registry numbers journals like valid numbers
 *
 *    Rev 1.0   Sep 21 2001 11:28:46   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:09:14   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifyitem;

//java imports
import java.lang.reflect.Field;

import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.utility.JournalConstantsIfc;

import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.RegistryDataTransaction;
import oracle.retail.stores.domain.registry.RegistryIDIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.FinalLetter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

//------------------------------------------------------------------------------
/**

    Update the modified item's Gift Registry
    Put it into cargo
    Mail a final letter
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public class ItemGiftRegistryModifiedAisle extends PosLaneActionAdapter
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

        POSUIManagerIfc ui;
        ui=(POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);

        ItemCargo cargo = (ItemCargo)bus.getCargo();
        String itemID = cargo.getItem().getItemID();
        String currentGiftId = new String("");

        String newGiftId=ui.getInput();
        StringBuffer buf =
            new StringBuffer("ItemGiftRegistryModifiedAisle Received input:");
        buf.append(newGiftId);
        if (logger.isInfoEnabled()) logger.info("" + buf.toString() + "");

        // if there is a current gift registry, update the ID.  If not,
        // create a new gift registry object with the new ID.
        RegistryIDIfc giftRegistry = null;

        StringBuffer sb = new StringBuffer();

        // if the string is > 0, set the gift registry to new id.
        if( newGiftId.length() > 0 )
        {
            if (cargo.getItem().getRegistry() != null)  // one exists already
            {
                giftRegistry  = cargo.getItem().getRegistry();
                // Save existing gift reg ID to journal it as having been removed,
                // then set the new ID.
                currentGiftId = giftRegistry.getID();
                giftRegistry.setID(newGiftId);

                // If gift reg ID changed, then reflect this in the ejournal
                // along with the item # on which the gift ID was changed
                if (!(currentGiftId.equals(newGiftId)) && !(currentGiftId.equals("")))
                {
//                    sb.append(Util.EOL)
//                        .append("ITEM: ") .append(itemID.trim())
//                        .append(Util.EOL)
//                        .append("  Gift Reg.: ") .append(currentGiftId)
//                        .append(" Removed")
//                        .append(Util.EOL);
                    Object dataArgs[]={itemID.trim()};
                    String itemData=I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ITEM, dataArgs);

                    //
                    Object giftRegdataArgs[]={currentGiftId};
                    String giftReg=I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.TRANSACTION_GIFTREG_REMOVED, giftRegdataArgs);

                    sb.append(Util.EOL)
                      .append(itemData)
                      .append(Util.EOL)
                      .append(giftReg)
                      .append(Util.EOL);


                }
            }
            else
            {
                giftRegistry = (RegistryIDIfc) DomainGateway.getFactory().getGiftRegistryInstance();
                giftRegistry.setID(newGiftId);
            }

            // attempt to do the database lookup
            RegistryDataTransaction registryTr = null;

            registryTr = (RegistryDataTransaction) DataTransactionFactory.create(DataTransactionKeys.REGISTRY_DATA_TRANSACTION);

            try
            {

                giftRegistry = registryTr.readRegistryID(newGiftId);

                if ( giftRegistry != null)
                {
                    //And, move along.
                    bus.mail(new FinalLetter("Next"), BusIfc.CURRENT);

//                    sb.append(Util.EOL)
//                    .append("ITEM: ") .append(itemID.trim())
//                    .append(Util.EOL)
//                    .append("  Gift Reg.: ") .append(newGiftId);

                    Object dataArgs[]={itemID.trim()};
                    String itemData=I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ITEM, dataArgs);

                    //
                    Object giftRegdataArgs[]={newGiftId};
                    String giftReg=I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.GIFT_REGISTER_LABEL, giftRegdataArgs);

                    sb.append(Util.EOL)
                      .append(itemData)
                      .append(Util.EOL)
                      .append(giftReg);

                }
            }
            catch (DataException e)
            {
                logger.warn( "" + e + "");
                cargo.setDataExceptionErrorCode(e.getErrorCode());
                //letterName = CommonLetterIfc.FAILURE;
                String[] args = new String[1];
                UtilityManagerIfc utility =
                  (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
                args[0] = utility.getErrorCodeString(DataException.CONNECTION_ERROR);

                if (e.getErrorCode() == 6)
                {
                    DialogBeanModel dialogModel = new DialogBeanModel();
                    dialogModel.setResourceID("INFO_NOT_FOUND_ERROR");
                    dialogModel.setType(DialogScreensIfc.ERROR);

                    giftRegistry.setID("");

                    //display dialog
                    ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
                }
                else
                {

                    DialogBeanModel dialogModel = new DialogBeanModel();
                    dialogModel.setResourceID("GiftRegConfirm");
                    dialogModel.setType(DialogScreensIfc.CONFIRMATION);
                    dialogModel.setArgs(args);

                    // display dialog
                    ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
                }
            }
        }
        // otherwise, if no id is entered, reset the gift registry to null.
        else
        {
            cargo.getItem().modifyItemRegistry((RegistryIDIfc) null, false);
            newGiftId = " ";
        }

        //then set the item to the new value in the modified giftregistry.
        cargo.getItem().modifyItemRegistry(giftRegistry, true);

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
        String strResult = new String("Class:  ItemGiftRegistryModifiedAisle (Revision " +
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
