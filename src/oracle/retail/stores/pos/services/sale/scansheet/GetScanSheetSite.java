/* ===========================================================================
* Copyright (c) 2008, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    asinto 02/27/12 - refactored the flow so that items added from scan sheet
 *                      doesn't allow for a hang or mismatched letter.
 *    jkoppo 04/19/11 - XbranchMerge jkoppolu_bug11820604-offline_fix from main
 *    jkoppo 03/09/11 - I18N changes.
 *    jkoppo 03/07/11 - Modified the code to take care of the case when there
 *                      are no scan sheet items configured.
 *    jkoppo 03/04/11 - Several code tweaks and performance improvements
 *    jkoppo 03/02/11 - New site in scan sheet tour
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale.scansheet;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import oracle.retail.stores.common.utility.Util;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.ScanSheetTransaction;
import oracle.retail.stores.domain.stock.ScanSheet;
import oracle.retail.stores.domain.stock.ScanSheetComponent;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.LetterIfc;
import oracle.retail.stores.foundation.utility.LocaleMap;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;
import oracle.retail.stores.pos.ui.beans.ImageGridBeanModel;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;
import oracle.retail.stores.pos.ui.plaf.UIFactory;

public class GetScanSheetSite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = -4741565133005749308L;
    private static final String APPLICATION_PROPERTY_GROUP_NAME = "application";

    public void arrive(BusIfc bus)
    {
        ScanSheetCargo cargo = (ScanSheetCargo) bus.getCargo();
        ImageGridBeanModel igbm = null;
        String letterName = bus.getCurrentLetter().getName();
        NavigationButtonBeanModel nModel = new NavigationButtonBeanModel();
        if (cargo.isNewVisitToScanSheet())
        {
            ScanSheet ss = null;
            ScanSheetTransaction scanSheetTransaction = (ScanSheetTransaction) DataTransactionFactory
                    .create(DataTransactionKeys.SCAN_SHEET_TRANSACTION);
            Locale locale = LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE);
            locale = LocaleMap.getBestMatch(locale);
            try
            {
                ss = scanSheetTransaction.getScanSheet(locale.toString());            
           
                int i = Integer.parseInt(Gateway.getProperty(APPLICATION_PROPERTY_GROUP_NAME, "maxGridSize", "4"));
                if (i < 1)
                {
                    logger.error("Unable to configure scan sheet - Application property maxGridSize is less than 1");
                    this.showErrorDialog(bus, "ScanSheetConfigWarning2", "Undo");
                }
                else
                {
                    if (ss.getScItemList().size() != 0)
                    {
                        igbm = new ImageGridBeanModel(ss);
                        cargo.setImgeGridBeanModel(igbm);
                        this.configureImageGridBeanModel(igbm, nModel);
                        igbm.setLocalButtonBeanModel(nModel);
                        cargo.setNewVisitToScanSheet(false);
                        igbm.setCurrentCategoryModel(null);
                        igbm.setCategoryID(null);
                        igbm.setCategoryDescription(null);
                        this.showScreen(igbm, bus);
                    }
                    else
                    {
                        this.showErrorDialog(bus, "ScanSheetConfigWarning", "Undo");
                    }
                }
            }
            catch (DataException de)
            {
                logger.error("Unable to fetch scan sheet - System is offline " + de.getMessage() + "");
                this.showErrorDialog(bus, "ScanSheetOffline", "Undo");                
            }      
            catch (NumberFormatException ex)
            {
                logger.error("Unable to configure scan sheet - Application property maxGridSize is not an integer");
                this.showErrorDialog(bus, "ScanSheetConfigWarning2", "Undo");
            }
        }
        else
        {
            igbm = cargo.getImgeGridBeanModel();
            if (letterName.equals("Return"))
            {
                HashMap<String, ArrayList<ScanSheetComponent>> cMap = igbm.getScanSheet().getCategoryMap();
                HashMap<String, String> parentCategoryMap = igbm.getScanSheet().getCategoryParentMap();
                HashMap<String, String> descCategoryMap = igbm.getScanSheet().getCategroyDescMap();
                ImageGridBeanModel currentModel = igbm.getCurrentCategoryModel();
                String parentCategory = null;
                if (currentModel != null)
                {
                    parentCategory = parentCategoryMap.get(igbm.getCurrentCategoryModel().getCategoryID());
                }
                if (Util.isEmpty(parentCategory))
                {
                    this.configureImageGridBeanModel(igbm, nModel);
                    igbm.setLocalButtonBeanModel(nModel);
                    igbm.setCurrentCategoryModel(null);
                    igbm.setCategoryID(null);
                    igbm.setCategoryDescription(null);
                    this.showScreen(igbm, bus);
                }
                else
                {
                    ImageGridBeanModel cigbm = new ImageGridBeanModel(new ScanSheet(cMap.get(parentCategory), cMap,
                            parentCategoryMap, descCategoryMap));
                    cigbm.setCurrentPageNo(cargo.getReturnPageNoMap().get(parentCategory));
                    this.configureImageGridBeanModel(cigbm, nModel);
                    cigbm.setLocalButtonBeanModel(nModel);
                    cigbm.setCategoryID(parentCategory);
                    cigbm.setCategoryDescription(descCategoryMap.get(parentCategory));
                    igbm.setCurrentCategoryModel(cigbm);
                    this.showScreen(cigbm, bus);
                }
            }
            else if (letterName.equals("CategorySelected"))
            {
                HashMap<String, ArrayList<ScanSheetComponent>> cMap = igbm.getScanSheet().getCategoryMap();
                HashMap<String, String> parentCategoryMap = igbm.getScanSheet().getCategoryParentMap();
                HashMap<String, String> descCategoryMap = igbm.getScanSheet().getCategroyDescMap();
                String selectedItem = null;
                if (igbm.getCurrentCategoryModel() != null)
                {
                    selectedItem = igbm.getCurrentCategoryModel().getSelectedItemID();
                }
                else
                {
                    selectedItem = igbm.getSelectedItemID();
                }
                ArrayList<ScanSheetComponent> itemList = cMap.get(selectedItem);
                // If the selcted category has no items configured, show a
                // warning dialog.
                if (itemList == null)
                {
                    this.showErrorDialog(bus, "ScanSheetCategoryConfigWarning", "Return");
                }
                else
                {
                    ImageGridBeanModel cigbm = new ImageGridBeanModel(new ScanSheet(itemList, cMap, parentCategoryMap,
                            descCategoryMap));
                    this.configureImageGridBeanModel(cigbm, nModel);
                    cigbm.setLocalButtonBeanModel(nModel);
                    cigbm.setCategoryID(selectedItem);
                    cigbm.setCategoryDescription(descCategoryMap.get(selectedItem));
                    igbm.setCurrentCategoryModel(cigbm);
                    this.showScreen(cigbm, bus);
                }
            }
            else
            {
                if (igbm.getCurrentCategoryModel() == null)
                {
                    this.turnPage(igbm, nModel, letterName, bus);
                }
                else
                {
                    this.turnPage(igbm.getCurrentCategoryModel(), nModel, letterName, bus);
                }
            }
        }
    }

    public void depart(BusIfc bus)
    {
        LetterIfc letter = bus.getCurrentLetter();
        if (letter.getName().equals("CategorySelected"))
        {
            POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
            ImageGridBeanModel igbm = (ImageGridBeanModel) ui.getModel(POSUIManagerIfc.SCAN_SHEET);
            String categoryName = igbm.getCategoryID();
            if (!Util.isEmpty(categoryName))
            {
                ((ScanSheetCargo) bus.getCargo()).getReturnPageNoMap().put(categoryName, igbm.getCurrentPageNo());
            }
        }
    }

    private void configureImageGridBeanModel(ImageGridBeanModel igbm, NavigationButtonBeanModel nModel)
    {
        int noOfItems = igbm.getScanSheet().getScItemListSize();
        // Calculate the no. of pages
        igbm.setNoOfPages(0);
        if ((noOfItems % igbm.maxNumberOfItems) == 0)
        {
            igbm.setNoOfPages(noOfItems / igbm.maxNumberOfItems);
        }
        else
        {
            igbm.setNoOfPages((noOfItems / igbm.maxNumberOfItems) + 1);
        }
        int n = igbm.getCurrentPageNo();
        int pageNo = 1;
        if (n == 0)
        {
            igbm.setCurrentPageNo(1);
        }
        else
        {
            pageNo = n;
        }
        if (igbm.getCurrentPageNo() > igbm.noOfPages)
        {
            pageNo = 1;
        }
        if (pageNo == 1)
        {
            if (igbm.noOfPages > 1)
            {
                nModel.setButtonEnabled("FirstPage", false);
                nModel.setButtonEnabled("NextPage", true);
                nModel.setButtonEnabled("LastPage", true);
                nModel.setButtonEnabled("PreviousPage", false);
            }
            else
            {
                nModel.setButtonEnabled("FirstPage", false);
                nModel.setButtonEnabled("NextPage", false);
                nModel.setButtonEnabled("LastPage", false);
                nModel.setButtonEnabled("PreviousPage", false);
            }
        }
        // If in last page
        else if (igbm.noOfPages != 1 && pageNo == igbm.noOfPages)
        {
            nModel.setButtonEnabled("FirstPage", true);
            nModel.setButtonEnabled("LastPage", false);
            nModel.setButtonEnabled("NextPage", false);
            nModel.setButtonEnabled("PreviousPage", true);
        }
        // If in between first and last page
        else
        {
            nModel.setButtonEnabled("FirstPage", true);
            nModel.setButtonEnabled("LastPage", true);
            nModel.setButtonEnabled("NextPage", true);
            nModel.setButtonEnabled("PreviousPage", true);
        }
    }

    private void showScreen(ImageGridBeanModel igbm, BusIfc bus)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        ui.showScreen(POSUIManagerIfc.SCAN_SHEET, igbm);
    }

    private void turnPage(ImageGridBeanModel igbm, NavigationButtonBeanModel nModel, String letterName, BusIfc bus)
    {
        if (letterName.equals("LastPage"))
        {
            igbm.setCurrentPageNo(igbm.getNoOfPages());
            nModel.setButtonEnabled("NextPage", false);
            nModel.setButtonEnabled("LastPage", false);
            nModel.setButtonEnabled("FirstPage", true);
            nModel.setButtonEnabled("PreviousPage", true);
        }
        else if (letterName.equals("FirstPage"))
        {
            igbm.setCurrentPageNo(1);
            nModel.setButtonEnabled("NextPage", true);
            nModel.setButtonEnabled("LastPage", true);
            nModel.setButtonEnabled("FirstPage", false);
            nModel.setButtonEnabled("PreviousPage", false);
        }
        else if (letterName.equals("NextPage"))
        {
            int nextPage = igbm.getCurrentPageNo() + 1;
            if (nextPage == igbm.getNoOfPages())
            {
                nModel.setButtonEnabled("LastPage", false);
                nModel.setButtonEnabled("NextPage", false);
            }
            else
            {
                nModel.setButtonEnabled("LastPage", true);
                nModel.setButtonEnabled("NextPage", true);
            }
            nModel.setButtonEnabled("FirstPage", true);
            nModel.setButtonEnabled("PreviousPage", true);
            igbm.setCurrentPageNo(nextPage);
        }
        else if (letterName.equals("PreviousPage"))
        {
            int previousPage = igbm.getCurrentPageNo() - 1;
            if (previousPage == 1)
            {
                nModel.setButtonEnabled("LastPage", true);
                nModel.setButtonEnabled("NextPage", true);
                nModel.setButtonEnabled("FirstPage", false);
                nModel.setButtonEnabled("PreviousPage", false);
            }
            else
            {
                nModel.setButtonEnabled("LastPage", true);
                nModel.setButtonEnabled("NextPage", true);
                nModel.setButtonEnabled("FirstPage", true);
                nModel.setButtonEnabled("PreviousPage", true);
            }
            igbm.setCurrentPageNo(previousPage);
        }
        igbm.setLocalButtonBeanModel(nModel);
        this.showScreen(igbm, bus);
    }

    private void showErrorDialog(BusIfc bus, String resourceID, String letter)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        DialogBeanModel dialogBean = new DialogBeanModel();
        Color BannerColor = Color.RED;
        String strBannerColor = UIFactory.getInstance().getUIProperty("Color.attention",
                LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE));
        if (!strBannerColor.equals(""))
        {
            BannerColor = Color.decode(strBannerColor);
        }
        dialogBean.setResourceID(resourceID);
        dialogBean.setType(DialogScreensIfc.ACKNOWLEDGEMENT);
        dialogBean.setButtonLetter(DialogScreensIfc.BUTTON_OK, letter);
        dialogBean.setBannerColor(BannerColor);
        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogBean);
    }
}
