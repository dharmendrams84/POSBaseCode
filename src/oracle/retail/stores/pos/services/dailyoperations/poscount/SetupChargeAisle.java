/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/dailyoperations/poscount/SetupChargeAisle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:23 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:29:57 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:15 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:12 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:49:39  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:45:40  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:15  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:56:58   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:30:42   msg
 * Initial revision.
 * 
 *    Rev 1.1   Mar 18 2002 23:14:48   msg
 * - updated copyright
 * 
 *    Rev 1.0   Mar 18 2002 11:27:26   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:17:10   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:11:18   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.dailyoperations.poscount;

// foundation imports
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.LetterIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.ui.ButtonPressedLetter;
import oracle.retail.stores.pos.ui.beans.SummaryCountBeanModel;

//--------------------------------------------------------------------------
/**
    desc.
    <p>
     @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class SetupChargeAisle extends PosLaneActionAdapter
{
    /**
       revision number of this class
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
       Sends a Next letter.
       <p>
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {
        LetterIfc letter = bus.getCurrentLetter();
        int       index  = getIndexFromLetter(letter);

        PosCountCargo cargo = (PosCountCargo)bus.getCargo();
        SummaryCountBeanModel[] scbm = cargo.getCountModels();
        cargo.setCurrentActivityOrCharge(scbm[index].getDescription());

        String letterName = "CountSummary";
        if (!cargo.getSummaryFlag())
        {
            letterName = "CountDetail";
        }

        bus.mail(new Letter(letterName), BusIfc.CURRENT);

    }

    //----------------------------------------------------------------------
    /**
       Returns an integer index which has been drived from the name of
       the letter.
       <P>
       @return String representation of object
    **/
    //----------------------------------------------------------------------
    protected int getIndexFromLetter(LetterIfc letter)
    {
        ButtonPressedLetter bpl = (ButtonPressedLetter)letter;
        int number = bpl.getNumber();

        // Adjust the number to acount for the "More" button
        // on the button bars.
        if (number > 15)
        {
            number = number - 2;
        }
        else
            if (number > 7)
            {
                number = number - 1;
            }

        return (number);
    }

    //----------------------------------------------------------------------
    /**
       Returns a string representation of the object.
       <P>
       @return String representation of object
    **/
    //----------------------------------------------------------------------
    public String toString()
    {                                   // begin toString()
        // result string
        String strResult = new String("Class:  SetupChargeAisle (Revision " +
                                      getRevisionNumber() +
                                      ")" + hashCode());

        return(strResult);
    }                                   // end toString()

    //----------------------------------------------------------------------
    /**
       Returns the revision number of the class.
       <P>
       @return String representation of revision number
    **/
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    {                                   // begin getRevisionNumber()
        // return string
        return(revisionNumber);
    }                                   // end getRevisionNumber()

    //----------------------------------------------------------------------
    /**
       Main to run a test..
       <P>
       @param  args    Command line parameters
    **/
    //----------------------------------------------------------------------
    public static void main(String args[])
    {                                   // begin main()
        // instantiate class
        SetupChargeAisle obj = new SetupChargeAisle();

        // output toString()
        System.out.println(obj.toString());
    }                                   // end main()
}
