/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/ImageGridBeanModel.java /main/1 2011/03/10 11:12:24 jkoppolu Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    jkoppo 03/02/11 - maxNumberOfItems are now set using the application
 *                      property - maxGridSize
 *    jkoppo 03/02/11 - New bean model for scan sheet screen
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import oracle.retail.stores.domain.stock.ScanSheet;
import oracle.retail.stores.foundation.tour.gate.Gateway;

public class ImageGridBeanModel extends POSBaseBeanModel
{


    private static final long serialVersionUID = -1988044652729552349L;

    private static final String APPLICATION_PROPERTY_GROUP_NAME = "application";

    private ScanSheet scanSheet;

    private String selectedItemID;

    private boolean isSelectedItemaCategory;

    private String categoryID;
    
    private String categoryDescription;
    

    public ImageGridBeanModel getCurrentCategoryModel()
    {
        return currentCategoryModel;
    }

    public void setCurrentCategoryModel(ImageGridBeanModel currentCategoryModel)
    {
        this.currentCategoryModel = currentCategoryModel;
    }

    private ImageGridBeanModel currentCategoryModel;

    public boolean isSelectedItemaCategory()
    {
        return isSelectedItemaCategory;
    }

    public void setSelectedItemaCategory(boolean isSelectedItemaCategory)
    {
        this.isSelectedItemaCategory = isSelectedItemaCategory;
    }

    private int currentPageNo;

    public final int maxNumberOfItems;

    public int noOfPages;

    public String getSelectedItemID()
    {
        return selectedItemID;
    }

    public int getNoOfPages()
    {
        return noOfPages;
    }

    public void setNoOfPages(int noOfPages)
    {
        this.noOfPages = noOfPages;
    }

    public int getCurrentPageNo()
    {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo)
    {
        this.currentPageNo = currentPageNo;
    }

    public void setSelectedItemID(String selectedItemID)
    {
        this.selectedItemID = selectedItemID;
    }

    public ImageGridBeanModel(ScanSheet scanSheet)
    {
        super();
        this.scanSheet = scanSheet;
        int i = Integer.parseInt(Gateway.getProperty(APPLICATION_PROPERTY_GROUP_NAME,"maxGridSize", "4"));
        this.maxNumberOfItems = i * i;
        
    }

    public ScanSheet getScanSheet()
    {
        return scanSheet;
    }

    public void setScanSheet(ScanSheet scanSheet)
    {
        this.scanSheet = scanSheet;
    }

    public void setCategoryID(String categoryName)
    {
        this.categoryID = categoryName;
    }

    public String getCategoryID()
    {
        return categoryID;
    }

    public void setCategoryDescription(String categoryDescription)
    {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryDescription()
    {
        return categoryDescription;
    }


}