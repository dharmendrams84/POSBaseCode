/* ===========================================================================
* Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/instantcredit/InquirySelectedRoad.java /rgbustores_13.4x_generic_branch/2 2011/05/05 16:17:11 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:28:23 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:22:07 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:11:24 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:50:40  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:16  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   Nov 24 2003 19:19:44   nrao
 * Changed Copyright message.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.instantcredit;

// foundation imports
import oracle.retail.stores.foundation.tour.application.LaneActionAdapter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;

//--------------------------------------------------------------------------
/**
    This road is traveled when Instant Credit Inquiry is selected.
    @version $Revision: /rgbustores_13.4x_generic_branch/2 $
**/
//--------------------------------------------------------------------------
public class InquirySelectedRoad extends LaneActionAdapter
{
    /**
       revision number supplied by version control
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    //----------------------------------------------------------------------
    /**
       Sets the type of operation to be Instant Credit Inquiry
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {
        InstantCreditCargo cargo = (InstantCreditCargo) bus.getCargo();
        cargo.setProcess(InstantCreditCargo.PROCESS_INQUIRY);
    }
}
