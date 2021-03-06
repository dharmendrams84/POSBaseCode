/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returns/returncommon/AcceptRestockingFeeSite.java /main/10 2011/02/16 09:13:29 cgreene Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:27:08 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:19:29 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:09:22 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:51:45  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:30  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:20  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:05:46   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:06:30   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:45:02   msg
 * Initial revision.
 * 
 *    Rev 1.1   20 Feb 2002 14:22:00   cir
 * Clean the comments and the code
 * Resolution for POS SCR-671: Gift card - multiple item return with expended gift card error
 * 
 *    Rev 1.0   17 Jan 2002 17:35:44   baa
 * Initial revision.
 * Resolution for POS SCR-714: Roles/Security 5.0 Updates
 *
 *    Rev 1.0   Sep 21 2001 11:25:08   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:12:46   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returncommon;

import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;

/**
 * This site mails the Success letter.
 * 
 * @version $Revision: /main/10 $
 */
public class AcceptRestockingFeeSite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = -5948446698939898216L;
    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /main/10 $";

    /**
     * Mails the Success letter.
     * 
     * @param bus Service Bus
     */
    @Override
    public void arrive(BusIfc bus)
    {
        bus.mail(new Letter(CommonLetterIfc.SUCCESS), BusIfc.CURRENT);
    }
}
