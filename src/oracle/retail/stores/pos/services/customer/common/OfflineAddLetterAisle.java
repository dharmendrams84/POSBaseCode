/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/customer/common/OfflineAddLetterAisle.java /main/10 2011/02/16 09:13:26 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:29:10 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:45 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:48 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:49:25  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:40:12  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:55:28   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   May 27 2003 09:17:16   baa
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:34:26   msg
 * Initial revision.
 * 
 *    Rev 1.1   Mar 18 2002 23:08:16   msg
 * - updated copyright
 * 
 *    Rev 1.0   Mar 18 2002 11:22:04   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:13:26   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:06:32   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.customer.common;

import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;

/**
 * This aisle sends a Cancel Letter.
 * 
 * @version $Revision: /main/10 $
 */
public class OfflineAddLetterAisle extends PosLaneActionAdapter
{
    private static final long serialVersionUID = 8223415808136748777L;
    /**
     * revision number of this class
     **/
    public static final String revisionNumber = "$Revision: /main/10 $";

    /**
     * Sends a Offline letter.
     * 
     * @param bus Service Bus
     */
    @Override
    public void traverse(BusIfc bus)
    {
        bus.mail(new Letter("OfflineAdd"), BusIfc.CURRENT);
    }
}