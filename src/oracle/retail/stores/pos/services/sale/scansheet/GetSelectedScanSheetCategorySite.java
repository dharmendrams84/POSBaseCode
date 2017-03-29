/* ===========================================================================
* Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/scansheet/GetSelectedScanSheetCategorySite.java /rgbustores_13.4x_generic_branch/1 2012/02/27 18:16:37 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    asinton   02/27/12 - refactored the flow so that items added from scan
 *                         sheet doesn't allow for a hang or mismatched letter.
 *
 * ===========================================================================
 */

package oracle.retail.stores.pos.services.sale.scansheet;

import oracle.retail.stores.common.utility.Util;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.ImageGridBeanModel;

/**
 * This site retrieves the selected category from the scan sheet UI and
 * stores it in the cargo. 
 * @author asinton
 * @since 13.4.1
 */
@SuppressWarnings("serial")
public class GetSelectedScanSheetCategorySite extends PosSiteActionAdapter
{

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.application.SiteActionAdapter#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void arrive(BusIfc bus)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        ImageGridBeanModel igbm = (ImageGridBeanModel) ui.getModel(POSUIManagerIfc.SCAN_SHEET);
        String categoryName = igbm.getCategoryID();
        if (!Util.isEmpty(categoryName))
        {
            ((ScanSheetCargo) bus.getCargo()).getReturnPageNoMap().put(categoryName, igbm.getCurrentPageNo());
        }
        bus.mail(CommonLetterIfc.CONTINUE, BusIfc.CURRENT);
    }

}
