/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/scansheet/ScanSheetItemEnteredAisle.java /main/1 2011/03/10 11:12:36 jkoppolu Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    jkoppo 03/02/11 - New aisle in scan sheet tour
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale.scansheet;

import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.ImageGridBeanModel;

public class ScanSheetItemEnteredAisle extends PosLaneActionAdapter
{

    /**
     * 
     */
    private static final long serialVersionUID = -5639337116524461565L;

    public void traverse(BusIfc bus)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        ImageGridBeanModel imgbm = (ImageGridBeanModel) ui.getModel(POSUIManagerIfc.SCAN_SHEET);
        if (imgbm != null)
        {
            if (imgbm.getSelectedItemID() != null)
            {
                if (!imgbm.isSelectedItemaCategory())
                {
                    bus.mail("Next");

                }
                else
                {
                    bus.mail("CategorySelected");

                }
            }

        }

    }

}
