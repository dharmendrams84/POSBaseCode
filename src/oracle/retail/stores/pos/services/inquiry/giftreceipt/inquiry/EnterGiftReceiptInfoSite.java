/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/inquiry/giftreceipt/inquiry/EnterGiftReceiptInfoSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:45 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:28:01 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:21:25 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:54 PM  Robert Pearse   
 *
 *   Revision 1.6  2004/09/23 00:07:14  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.5  2004/02/19 18:06:25  lzhao
 *   @scr 3841 Inquiry Options Enhancement.
 *   Modify comments.
 *
 *   Revision 1.4  2004/02/16 22:41:05  lzhao
 *   @scr 3841:Inquiry Option Enhancement
 *   add gift code and add multiple inquiry.
 *
 *   Revision 1.3  2004/02/12 16:50:28  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:10  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:16  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:59:56   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:22:54   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:33:24   msg
 * Initial revision.
 * 
 *    Rev 1.3   Dec 10 2001 17:23:32   blj
 * updated per codereview findings.
 * Resolution for POS SCR-237: Gift Receipt Feature
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.inquiry.giftreceipt.inquiry;

import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.SiteActionAdapter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.SiteActionIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
//------------------------------------------------------------------------------
/**
     
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------

public class EnterGiftReceiptInfoSite extends SiteActionAdapter implements SiteActionIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -7162827539335009529L;


    //--------------------------------------------------------------------------
    /**             
            This site displays the gift code inquiry screen.
            @param bus the bus arriving at this site
    **/
    //--------------------------------------------------------------------------

    public void arrive(BusIfc bus)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);

        // display gift code inquiry screen
        ui.showScreen(POSUIManagerIfc.GIFT_CODE_INQUIRY, new POSBaseBeanModel());
    }

    //--------------------------------------------------------------------------
    /**
         Reset    
         @param bus the bus being reset
    **/
    //--------------------------------------------------------------------------
    public void reset(BusIfc bus)
    {
        arrive(bus);
    }

}
