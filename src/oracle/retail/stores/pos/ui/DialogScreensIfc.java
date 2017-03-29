/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/DialogScreensIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:38 mszekely Exp $
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
 *    4    360Commerce 1.3         5/17/2007 5:33:48 PM   Michael P. Barnett
 *         Defined a NO_RESPONSE dialog screen, which does not permit a
 *         response from the operator.
 *    3    360Commerce 1.2         3/31/2005 4:27:45 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:57 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:35 PM  Robert Pearse   
 *
 *   Revision 1.7  2004/06/14 23:33:27  lzhao
 *   @scr change "Change" button to "Update" button.
 *
 *   Revision 1.6  2004/06/09 19:45:13  lzhao
 *   @scr 4670: add customer present dialog and the flow.
 *
 *   Revision 1.5  2004/05/26 16:37:47  lzhao
 *   @scr 4670: add capture customer and bill addr. same as shipping for send
 *
 *   Revision 1.4  2004/05/07 01:51:57  dcobb
 *   @scr 4702 Tax Override - When selecting multiple items and some but not all are non-taxable, the wrong message appears
 *
 *   Revision 1.3  2004/03/16 18:30:40  cdb
 *   @scr 0 Removed tabs from all java source code.
 *
 *   Revision 1.2  2004/02/12 16:52:11  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:21  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   Oct 31 2003 12:43:24   nrao
 * Added labels for new Dialog Screen for Instant Credit Enrollment.
 * 
 *    Rev 1.0   Aug 29 2003 16:09:18   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 14:44:54   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:51:36   msg
 * Initial revision.
 * 
 *    Rev 1.2   Jan 19 2002 10:28:42   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 * 
 *    Rev 1.1   29 Oct 2001 18:12:40   pdd
 * Added button IDs and label strings.
 * Resolution for POS SCR-219: Add Tender Limit Override
 * 
 *    Rev 1.0   Sep 21 2001 11:33:38   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:16:04   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui;

//---------------------------------------------------------------------
/**
 *    Holds constants that define the dialog types and the button labels.  <P>
 *    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
//---------------------------------------------------------------------
public interface DialogScreensIfc
{
    static public final int CONFIRMATION          = 0;
    static public final int ERROR                 = 1;
    static public final int RETRY_CONTINUE_CANCEL = 2;
    static public final int RETRY_CONTINUE        = 3;
    static public final int RETRY_CANCEL          = 4;
    static public final int CONTINUE_CANCEL       = 5;
    static public final int RETRY                 = 6;
    static public final int ACKNOWLEDGEMENT       = 7;
    static public final int INVALID_FIELD         = 8;
    static public final int SIGNATURE             = 9;
    static public final int NOW_LATER             = 10;
    static public final int UPDATE_CANCEL         = 11;
    static public final int YES_NO                = 12;
    static public final int NO_RESPONSE           = 13;
    
    //this is temporary to allow for dynamic dialog screens
    static public final int MASTER                = -1;

    // Dialog button IDs
    public static final int BUTTON_OK       = 0;
    public static final int BUTTON_YES      = 1;
    public static final int BUTTON_NO       = 2;
    public static final int BUTTON_CONTINUE = 3;
    public static final int BUTTON_RETRY    = 4;
    public static final int BUTTON_CANCEL   = 5;
    public static final int BUTTON_NOW      = 6;
    public static final int BUTTON_LATER    = 7;
    public static final int BUTTON_ADD      = 8;
    public static final int BUTTON_UPDATE   = 9;
    
    // Dialog button labels
    public static final String[] DIALOG_BUTTON_LABELS = {"Ok", "Yes", "No", "Continue", "Retry", 
                                                         "Cancel", "Now", "Later", "Add", "Update"};

}
