/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/inquiry/iteminquiry/ItemInfoEnteredRoad.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:45 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *    ddbaker   10/23/08 - Final updates for localized item description support
 *    mchellap  09/30/08 - Updated copy right comment
 *
 *     $Log:
 *      5    360Commerce 1.4         8/7/2006 1:36:51 PM    Brett J. Larsen CR
 *           17286 - fix issues with advance price search - next button not
 *           enabled when it should be
 *
 *           v7x->360Commerce
 *      4    360Commerce 1.3         12/13/2005 4:42:41 PM  Barry A. Pape
 *           Base-lining of 7.1_LA
 *      3    360Commerce 1.2         3/31/2005 4:28:31 PM   Robert Pearse
 *      2    360Commerce 1.1         3/10/2005 10:22:27 AM  Robert Pearse
 *      1    360Commerce 1.0         2/11/2005 12:11:38 PM  Robert Pearse
 *     $
 *
 *      5    .v7x      1.3.1.0     6/2/2006 8:09:33 AM    Dinesh Gautam
 *           CR17286-fix for enabling enter/Next button on Adv search screen
 *
 *     Revision 1.11  2004/07/17 16:03:04  lzhao
 *     @scr 6319: clone searchCriteria for search
 *
 *     Revision 1.10  2004/06/23 23:39:17  lzhao
 *     @scr 5650: parse bar code.
 *
 *     Revision 1.9  2004/06/23 23:14:55  lzhao
 *     @scr 5650: parse bar code for item id and item size for item inquiry
 *
 *     Revision 1.8  2004/06/21 22:20:06  lzhao
 *     @scr 5650: price inquiry investigation.
 *
 *     Revision 1.7  2004/06/17 20:59:22  lzhao
 *     @scr 5650: Dump information for finding the problem
 *
 *     Revision 1.6  2004/05/03 18:30:29  lzhao
 *     @scr 4544, 4556: keep user entered info when back to the page.
 *
 *     Revision 1.5  2004/03/16 18:30:46  cdb
 *     @scr 0 Removed tabs from all java source code.
 *
 *     Revision 1.4  2004/02/16 22:41:16  lzhao
 *     @scr 3841:Inquiry Option Enhancement
 *     add gift code and add multiple inquiry.
 *
 *     Revision 1.3  2004/02/12 16:50:30  mcs
 *     Forcing head revision
 *
 *     Revision 1.2  2004/02/11 21:51:11  rhafernik
 *     @scr 0 Log4J conversion and code cleanup
 *
 *     Revision 1.1.1.1  2004/02/11 01:04:16  cschellenger
 *     updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:00:14   CSchellenger
 * Initial revision.
 *
 *    Rev 1.1   Sep 05 2002 14:44:56   jriggins
 * ReplaceStar() now pulls the wildcard characters from the bundle instead of being hardcoded.
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.0   Apr 29 2002 15:22:24   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:33:54   msg
 * Initial revision.
 *
 *    Rev 1.0   Sep 21 2001 11:29:44   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:08:10   msg
 * header update
 * ===========================================================================
 */

package oracle.retail.stores.pos.services.inquiry.iteminquiry;

// foundation imports
import java.util.Locale;

import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.LaneActionAdapter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.ItemInquiryBeanModel;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;
import oracle.retail.stores.pos.utility.PLUItemUtility;

//--------------------------------------------------------------------------
/**
    This road is traveled when the user enters the item
    number. It stores the item number in the cargo.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class ItemInfoEnteredRoad extends LaneActionAdapter
{
    /**
        revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";


    //----------------------------------------------------------------------
    /**
        Stores the item info and dept list  in the cargo.
        @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {

        ItemInquiryCargo cargo = (ItemInquiryCargo)bus.getCargo();
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        UtilityManagerIfc utility = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);

        Locale uiLocale = LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE);
        // Initialize bean model values
        if ( ui.getModel() instanceof ItemInquiryBeanModel )
        {
            ItemInquiryBeanModel model = (ItemInquiryBeanModel) ui.getModel();
            // Change '*' for '%' before storing data to cargo
            // should not change it here, should change it before database inquiry
            //cargo.setInquiry(replaceStar(model.getItemNumber().trim()),
            //                 replaceStar(model.getItemDesc().trim()),
            //                 model.getSelectedDept());


            String itemNumber = null;
            String itemDesc = null;
            String geoCode = null;
            String manufacturer = null;
            if (model.getItemNumber() != null && model.getItemNumber().trim().length() > 0)
            {
                itemNumber = model.getItemNumber().trim();
            }

            if (model.getItemDesc() != null && model.getItemDesc().trim().length() > 0)
            {
                itemDesc = model.getItemDesc().trim();
            }

            if (model.getManufacturer() != null && model.getManufacturer().trim().length() > 0)
            {
                manufacturer = model.getManufacturer().trim();
            }

            cargo.setInquiry(uiLocale,
                             itemNumber,
                             itemDesc,
                             model.getSelectedDept(),
                             geoCode,
                             manufacturer,
                             model.getSelectedType(),
                             model.getSelectedUOM(),
                             model.getSelectedStyle(),
                             model.getSelectedColor(),
                             model.getSelectedSize());
        }
        else
        {
            // for the search by entering item id on prompt area.
            POSBaseBeanModel model = (POSBaseBeanModel) ui.getModel();
            PromptAndResponseModel parModel = model.getPromptAndResponseModel();
            String itemID = ui.getInput().trim();
            String itemNumber = itemID;
            if ( parModel != null )
            {
                boolean isScanned = parModel.isScanned();
                if (isScanned)
                {
                    itemNumber = processScannedItemNumber(bus, itemID);
                }
            }
            cargo.setInquiry(uiLocale, itemNumber,null, "-1");
        }
    }

    /**
     * Extract item size info from scanned item
     * @param itemID scanned item number
     * @return the item number
     */
    protected String processScannedItemNumber(BusIfc bus, String itemID)
    {
        ItemInquiryCargo cargo = (ItemInquiryCargo) bus.getCargo();
        String itemNumber = itemID;
        String [] parser = PLUItemUtility.getInstance().parseItemString(itemID);
        if (parser != null)
        {
            itemNumber = parser[0];  // parser[1] is item size
        }
        return itemNumber;
    }
    }
