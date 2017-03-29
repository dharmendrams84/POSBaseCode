/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/printing/PrintingIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:12 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:29:29 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:23 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:26 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:51:40  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:19  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:05:30   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:07:36   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:44:32   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:22:50   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:11:52   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.printing;

import java.text.SimpleDateFormat;

//------------------------------------------------------------------------------
/**
    Methods and Constants for use in the printing service.

    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public interface PrintingIfc
{
    /**
        revision number supplied by source-code-control system
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
        Date format for receipts and endorsements
    **/
    public static SimpleDateFormat DATE_LINE_FORMAT =
        new SimpleDateFormat("MM/dd/yyyy        hh:mm:ss a z");
    /**
       ESC sequence for right justification on the slip printer
    **/
    public static String PRINTER_RIGHT_JUSTIFY = new String("\u001b|rA");
    /**
       Slip printer line size
    **/
    public static int SLIP_PRINTER_LINE_SIZE = 33;
}
