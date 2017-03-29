/* ===========================================================================
* Copyright (c) 2008, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/scansheet/ScanSheetCargo.java /rgbustores_13.4x_generic_branch/1 2012/02/27 18:16:36 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    asinto 02/27/12 - refactored the flow so that items added from scan sheet
 *                      doesn't allow for a hang or mismatched letter.
 *    jkoppo 03/04/11 - Introduced returnPageNoMap for supporting 'return' to
 *                      original page rather than the first page.
 *    jkoppo 03/02/11 - New Cargo for scan sheet service
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale.scansheet;

import java.io.Serializable;
import java.util.HashMap;

import oracle.retail.stores.foundation.tour.ifc.CargoIfc;
import oracle.retail.stores.pos.services.sale.SaleCargoIfc;
import oracle.retail.stores.pos.ui.beans.ImageGridBeanModel;

public class ScanSheetCargo implements CargoIfc, Serializable
{

    private static final long serialVersionUID = -4047305408354225643L;

    private ImageGridBeanModel imgeGridBeanModel;

    private boolean isNewVisitToScanSheet;
    
    /* This map holds the return page numbers for the categories 
     * Required during return from one category to other */
    
    private HashMap<String,Integer> returnPageNoMap = new HashMap<String,Integer>();

    /**
     * selected scan sheet item ID.
     */
    private String selectedScanSheetItemID;

    public HashMap<String, Integer> getReturnPageNoMap()
    {
        return returnPageNoMap;
    }

    public void setReturnPageNoMap(HashMap<String, Integer> returnPageNoMap)
    {
        this.returnPageNoMap = returnPageNoMap;
    }

    public ImageGridBeanModel getImgeGridBeanModel()
    {
        return imgeGridBeanModel;
    }

    public void setImgeGridBeanModel(ImageGridBeanModel imgeGridBeanModel)
    {
        this.imgeGridBeanModel = imgeGridBeanModel;
    }

    public boolean isNewVisitToScanSheet()
    {
        return isNewVisitToScanSheet;
    }

    public void setNewVisitToScanSheet(boolean isNewVisitToScanSheet)
    {
        this.isNewVisitToScanSheet = isNewVisitToScanSheet;
    }

    /**
     * Sets the selected scan sheet item.
     * @param itemID
     */
    public void setSelectedScanSheetItemID(String itemID)
    {
        this.selectedScanSheetItemID = itemID;
    }

    /**
     * Gets the selected scan sheet item.
     * @return the selected scan sheet item.
     */
    public String getSelectedScanSheetItemID()
    {
        return this.selectedScanSheetItemID;
    }
}
