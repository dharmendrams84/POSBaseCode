/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/common/OrderViewCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:33 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:29:15 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:54 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:54 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:51:22  mcs
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
 *    Rev 1.0   Aug 29 2003 16:03:34   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:13:12   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:41:12   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 24 2001 13:01:06   MPM
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:10:24   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.common;

// Java imports
import java.io.Serializable;

import oracle.retail.stores.foundation.tour.application.tourcam.TourCamIfc;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;


//------------------------------------------------------------------------------
/**
    This ifc provides get/set methods for a boolean flag that indicates to the
    print order service whether or not it should display the order detail screen 
    before printing.

    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public interface OrderViewCargoIfc extends CargoIfc, TourCamIfc, Serializable

{
    /**
        revision number supplied by source-code-control system
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
 
    //--------------------------------------------------------------------------
    /**
        Sets the flag that indicates to the PrintOrder service whether an Order
        detail screen should be displayed. 
    **/
    //--------------------------------------------------------------------------
    public void setViewOrder(boolean value);
    
    //--------------------------------------------------------------------------
    /**
        Returns the flag that indicates to the PrintOrder service whether an Order
        detail screen should be displayed. 
    **/
    //--------------------------------------------------------------------------
    public boolean viewOrder();
    
} ///:~ end OrderCargoIfc
