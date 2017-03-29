/* ===========================================================================
 Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/inquiry/iteminquiry/ShowItemSite.java /rgbustores_13.4x_generic_branch/1 2011/04/08 11:37:35 rsnayak Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    rsnaya 04/08/11 - request ticket button fix
 *    rsnaya 03/09/11 - pos lat integtation for label batch
 *    ohorne 02/22/11 - ItemNumber can be ItemID or PosItemID
 *    npoola 12/20/10 - action button texts are moved to CommonActionsIfc
 *    cgreen 11/03/10 - rename ItemLevelMessageConstants
 *    acadar 06/10/10 - use default locale for currency display
 *    acadar 06/09/10 - XbranchMerge acadar_tech30 from
 *                      st_rgbustores_techissueseatel_generic_branch
 *    cgreen 05/26/10 - convert to oracle packaging
 *    cgreen 04/26/10 - XbranchMerge cgreene_tech43 from
 *                      st_rgbustores_techissueseatel_generic_branch
 *    cgreen 04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abonda 01/03/10 - update header date
 *    cgreen 10/12/09 - set manufacturers names if getting PLU a second time
 *    cgreen 09/03/09 - set image icon onto model
 *    cgreen 04/10/09 - fix possible npe when checking for blob by adding
 *                      hasImageBlob to ItemImageIfc
 *    cgreen 03/30/09 - removed item name column from item image table
 *    cgreen 03/19/09 - refactoring changes
 *    deghos 01/07/09 - EJ I18n defect fixes
 *    vcheng 01/07/09 - EJ defect fixes
 *    deghos 01/06/09 - EJ i18n defect fixes
 *    mchell 12/23/08 - Changes for searchForItemByManufacturer parameter
 *    vcheng 12/16/08 - ej defect fixes
 *    nkgaut 12/02/08 - Changes to include ILRM on the item info screen
 *    mchell 12/02/08 - Changes for Item search field parameter update
 *    akandr 10/30/08 - EJ changes
 *    deghos 10/29/08 - EJI18n_changes_ExtendyourStore
 *    ddbake 10/23/08 - Updates due to merge
 *    ddbake 10/22/08 - Updating to use localized item descriptions
 *    ranojh 10/21/08 - Changes for POS for UnitOfMeasure I18N
 *    ranojh 10/17/08 - Changes for code review
 *    mipare 10/17/08 - Deptartment list changes for localized text
 *    mipare 10/16/08 - dept list changes
 *    abonda 10/14/08 - I18Ning manufacturer name
 *    ddbake 10/09/08 - Refactor of reference implementation of POS I18N
 *                      Persistence
 *    atirke 10/01/08 - modified for item images
 *    atirke 09/30/08 -
 *    atirke 09/29/08 - Changes for item images, added blob image to bean model
 *
 *
 * ===========================================================================
 $Log:
 11   360Commerce 1.10        2/26/2008 6:33:59 AM   Naveen Ganesh
 unnecessary SOPs have been removed.
 10   360Commerce 1.9         1/10/2008 4:38:09 AM   Naveen Ganesh
 Handled the item getting added to the transaction problem
 9    360Commerce 1.8         11/22/2007 10:59:07 PM Naveen Ganesh   PSI
 Code checkin
 8    360Commerce 1.7         8/16/2007 2:57:17 PM   Anda D. Cadar   CR
 28345: Display size description for stock items
 7    360Commerce 1.6         7/19/2007 10:29:35 AM  Anda D. Cadar   Call
 CurrencyServiceIfc to format the item price; removed ISO currency
 code
 6    360Commerce 1.5         5/4/2006 5:11:50 PM    Brendan W. Farrell
 Remove inventory.
 5    360Commerce 1.4         1/22/2006 11:45:11 AM  Ron W. Haight
 removed references to com.ibm.math.BigDecimal
 4    360Commerce 1.3         12/13/2005 4:42:41 PM  Barry A. Pape
 Base-lining of 7.1_LA
 3    360Commerce 1.2         3/31/2005 4:30:00 PM   Robert Pearse
 2    360Commerce 1.1         3/10/2005 10:25:18 AM  Robert Pearse
 1    360Commerce 1.0         2/11/2005 12:14:13 PM  Robert Pearse
 $
 Revision 1.14  2004/05/20 22:54:58  cdb
 @scr 4204 Removed tabs from code base again.

 Revision 1.13  2004/05/18 21:49:02  lzhao
 @scr 4292: item inquiry ejournal

 Revision 1.12  2004/05/14 17:28:58  lzhao
 @scr 4292: price inquiry journal info update.

 Revision 1.11  2004/05/03 18:30:29  lzhao
 @scr 4544, 4556: keep user entered info when back to the page.

 Revision 1.10  2004/04/30 18:43:01  lzhao
 @scr 4556: set the value user previously entered.

 Revision 1.9  2004/04/28 22:51:29  lzhao
 @scr 4081,4084: roll item info to inventory screen.

 Revision 1.8  2004/04/22 17:35:52  lzhao
 @scr 4291, 4384 show department and size info.

 Revision 1.7  2004/04/09 17:57:00  lzhao
 @scr 4294: populate item id if it is entered before.

 Revision 1.6  2004/03/16 18:30:46  cdb
 @scr 0 Removed tabs from all java source code.

 Revision 1.5  2004/02/26 22:10:11  lzhao
 @scr 3841 Inquiry Options Enhancement.
 code review follow up.

 Revision 1.4  2004/02/23 21:43:24  lzhao
 @scr 3841 Inquiry Options Enhancement.
 Add showJounal()

 Revision 1.3  2004/02/12 16:50:31  mcs
 Forcing head revision

 Revision 1.2  2004/02/11 21:51:10  rhafernik
 @scr 0 Log4J conversion and code cleanup

 Revision 1.1.1.1  2004/02/11 01:04:16  cschellenger
 updating to pvcs 360store-current


 *
 *    Rev 1.0   Aug 29 2003 16:00:18   CSchellenger
 * Initial revision.
 *
 *    Rev 1.0   Apr 29 2002 15:22:38   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:34:02   msg
 * Initial revision.
 *
 *    Rev 1.4   Feb 05 2002 16:42:32   mpm
 * Modified to use IBM BigDecimal.
 * Resolution for POS SCR-1121: Employ IBM BigDecimal
 *
 *    Rev 1.3   06 Dec 2001 11:15:20   pjf
 * Updates for kits and item inquiry.
 * Resolution for POS SCR-8: Item Kits
 *
 *    Rev 1.2   Nov 07 2001 15:50:04   vxs
 * Modified LineDisplayItem() in POSDeviceActionGroup, so accommodating changes for other files as well.
 * Resolution for POS SCR-208: Line Display
 *
 *    Rev 1.1   Oct 12 2001 15:40:28   vxs
 * Putting line display mechanism in service code.
 * Resolution for POS SCR-208: Line Display
 *
 *    Rev 1.0   Sep 21 2001 11:29:50   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:08:08   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.inquiry.iteminquiry;

import java.awt.Image;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.util.Locale;

import javax.swing.ImageIcon;

import oracle.retail.stores.commerceservices.common.currency.CurrencyServiceLocator;
import oracle.retail.stores.common.constants.ItemLevelMessageConstants;
import oracle.retail.stores.common.utility.Util;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.PLUTransaction;
import oracle.retail.stores.domain.employee.RoleFunctionIfc;
import oracle.retail.stores.domain.manager.ifc.SecurityManagerIfc;
import oracle.retail.stores.domain.stock.ItemImageIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.domain.stock.StockItem;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.manager.ui.jfc.ButtonPressedLetter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.LetterIfc;
import oracle.retail.stores.foundation.tour.service.SessionBusIfc;
import oracle.retail.stores.foundation.utility.LocaleMap;
import oracle.retail.stores.pos.device.POSDeviceActions;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.ItemInfoBeanModel;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;
import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.pos.services.common.CommonActionsIfc;
import oracle.retail.stores.utility.JournalConstantsIfc;

/**
 * This site displays the ITEM_INFO screen.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public class ShowItemSite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = 5957045449801925726L;

    /** revision number */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * item inquiry information for logging
     */
    protected static final String ITEM_DESCRIPTION = "Item Description: ";
    protected static final String ITEM_DEPARTMENT = "Item Department: ";
    protected static final String ITEM_PRICE = "Item Price: ";
    protected static final String ITEM_UNIT = "Item Unit of Measure: ";
    protected static final String ITEM_TAXABLE = "Item Taxable: ";
    protected static final String ITEM_DISCOUNTABLE = "Item Discountable: ";
    protected static final String PRICE_INQUIRY = "Price Inquiry ";
    protected static final String ITEM_NUMBER = "Item Number: ";
    protected static final String ITEM_SIZE = "Item Size: ";

    protected static final String YES = "YES";
    protected static final String NO = "NO";
    protected static final String TRUE = "true";
    protected static final String FALSE = "false";

    // to display manufacturer and planogram
    protected static final String ITEM_MANUFACTURER = "Manufacturer:";
    protected static final String ITEM_PLANOGRAM_ID = "Planogram ID:";

    protected static final String PSI_ENABLED_PROPERTY = "PSIEnabled";

    public static final String SEARCH_ITEM_BY_MANUFACTURER = "SearchForItemByManufacturer";
    public static final String PLANOGRAM_DISPLAY = "PlanogramDisplay";
    public static final String SIZE_INPUT_FIELD = "SizeInputField";
    protected boolean latEnabled = false;
    public Image itemImage;

    /**
     * Displays the ITEM_INFO screen.
     *
     * @param bus Service Bus
     */
    @Override
    public void arrive(BusIfc bus)
    {
        // retrieve item information from cargo
        ItemInquiryCargo cargo = (ItemInquiryCargo)bus.getCargo();
        PLUItemIfc item = cargo.getPLUItem();
        Locale userLocale = LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE);

        // reset the cargo so that item will not be added to transaction
        // cargo.setModifiedFlag(false);

        // Initialize bean model values
        ItemInfoBeanModel model = new ItemInfoBeanModel();
        model.setItemDescription(item.getDescription(userLocale));

        model.setItemNumber(item.getItemID());
        if (item.getDepartment() != null)
        {
            model.setItemDept(item.getDepartment().getDescription(userLocale));
        }
        model.setPrice(new BigDecimal(item.getSellingPrice().getStringValue()));
        model.setUnitOfMeasure(item.getUnitOfMeasure());
        model.setTaxableFlag(item.getTaxable());
        model.setDiscountableFlag(item.isDiscountEligible());
        UtilityManagerIfc utility = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);
        if (item.isItemSizeRequired())
        {
            PLUTransaction pluTransaction = null;

            pluTransaction = (PLUTransaction)DataTransactionFactory.create(DataTransactionKeys.PLU_TRANSACTION);

            try
            {
                PLUItemIfc storeItem = pluTransaction.getPLUItem(item.getItemID(), utility.getRequestLocales());
                if (storeItem != null && storeItem.getLocalizedManufacturer() != null)
                {
                    item.setLocalizedManufacturer(storeItem.getLocalizedManufacturer());
                }
                if (storeItem instanceof StockItem)
                {
                    String sizeDescription = ((StockItem)storeItem).getItemSize().getDescription(userLocale);
                    model.setItemSize(sizeDescription);
                    cargo.setItemSize(sizeDescription);
                }
            }
            catch (DataException e)
            {
                logger.warn("Unable to read item data: " + e.getMessage() + "");
                model.setItemSize("");
            }
        }

        // ILRM_CR : Setting item level Message in the bean model to show it on
        // Item Info Screen
        Locale locale = new Locale(userLocale.getLanguage());
        String itemLevelMessage = item.getItemLevelMessage(ItemLevelMessageConstants.SALE, ItemLevelMessageConstants.SCREEN, locale);
        model.setItemLevelMessage(itemLevelMessage);
        ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);

        boolean searchForItemByManufacturer = false;
        boolean usePlanogramID = false;

        try
        {
            if (pm.getStringValue(SEARCH_ITEM_BY_MANUFACTURER).equalsIgnoreCase("Yes"))
            {
                searchForItemByManufacturer = true;
            }

            if (pm.getStringValue(PLANOGRAM_DISPLAY).equalsIgnoreCase("Yes"))
            {
                usePlanogramID = true;
            }
        }
        catch (ParameterException pe)
        {
            logger.error("Cannot retrive parameter value");
        }
        model.setSearchItemByManufacturer(searchForItemByManufacturer);
        model.setUsePlanogramID(usePlanogramID);

        if (model.isSearchItemByManufacturer())
        {
            model.setItemManufacturer(item.getManufacturer(userLocale));
        }

        if (model.isUsePlanogramID())
        {
            model.setPlanogramID(item.getPlanogramID());
        }

        Boolean sizeInput = new Boolean(false);
        try
        {
            sizeInput = pm.getBooleanValue(SIZE_INPUT_FIELD);
        }
        catch (ParameterException e)
        {
            logger.error("" + Util.throwableToString(e) + "");
        }

        if (sizeInput.booleanValue())
        {
            model.setItemSizeRequired(true);
        }
        else
        {
            model.setItemSizeRequired(false);
        }
        showPriceInquiryJournal(model, item);
        
        
        NavigationButtonBeanModel navModel = new NavigationButtonBeanModel();

        // enable/disable Request Ticket Button
        if (isLatEnabled(bus))
        {
            latEnabled = true;
        }
        else
        {
            latEnabled = false;
        }
        
        navModel.setButtonEnabled(CommonActionsIfc.REQUEST_TICKET, latEnabled);
        // enable/disable Inventory Inquiry button
        navModel.setButtonEnabled(CommonActionsIfc.INVENTORY,isIventoryInquirySupported(bus));        
        model.setLocalButtonBeanModel(navModel);

        // Display the screen
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);

        ItemImageIfc itemImage = item.getItemImage();
        if (!Util.isEmpty(itemImage.getImageLocation()))
        {
            model.setImageLocation(itemImage.getImageLocation());
        }

        // blob logic
        if (itemImage.hasImageBlob())
        {
            model.setImage(new ImageIcon(getImageFromBytes(itemImage)));
        }

        ui.showScreen(POSUIManagerIfc.ITEM_INFO, model);

        // Show item on Line Display device
        POSDeviceActions pda = new POSDeviceActions((SessionBusIfc)bus);
        try
        {
            pda.lineDisplayItem(item);
        }
        catch (DeviceException e)
        {
            logger.warn("Unable to use Line Display: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.application.SiteActionAdapter#depart(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void depart(BusIfc bus)
    {

        LetterIfc letter = bus.getCurrentLetter();

        String letterName = null;
        if (letter instanceof ButtonPressedLetter) // Is ButtonPressedLetter
        {
            // Get the String representation of the letter name
            // from the LetterIfc object
            letterName = letter.getName();
            if (letterName != null && letterName.equals(CommonLetterIfc.UNDO))
            {
                ItemInquiryCargo cargo = (ItemInquiryCargo)bus.getCargo();
                cargo.setInquiry(null);
                cargo.setModifiedFlag(false);
            }
        }
    }

    /**
     * Journal the price inquiry results.
     *
     * @param ItemInfoBeanModel model
     * @param PLUItemIfc item
     */
    protected void showPriceInquiryJournal(ItemInfoBeanModel model, PLUItemIfc item)
    {
        JournalManagerIfc jmi = (JournalManagerIfc)Gateway.getDispatcher().getManager(JournalManagerIfc.TYPE);
        if (jmi != null)
        {
            StringBuffer entry = new StringBuffer();
            Object[] dataArgs = new Object[2];
            entry.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.PRICE_INQUIRY_LABEL,
                    null));
            entry.append(Util.EOL);

            dataArgs[0] = model.getItemNumber();
            entry.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ITEM_NUMBER_LABEL,
                    dataArgs));
            entry.append(Util.EOL);

            dataArgs[0] = model.getItemDescription();
            entry.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                    JournalConstantsIfc.ITEM_DESCRIPTION_LABEL, dataArgs));
            entry.append(Util.EOL);

            dataArgs = new Object[] { model.getItemManufacturer() == null ? "" : model.getItemManufacturer() };
            entry.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.MANUFACTURER_LABEL,
                    dataArgs));
            entry.append(Util.EOL);

            if (item.isItemSizeRequired())
            {
                dataArgs[0] = model.getItemSize();
                entry.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ITEM_SIZE_LABEL,
                        dataArgs));
                entry.append(Util.EOL);
            }

            dataArgs[0] = model.getItemDept();
            entry.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                    JournalConstantsIfc.ITEM_DEPARTMENT_LABEL, dataArgs));
            entry.append(Util.EOL);

            Locale defaultLocale = LocaleMap.getLocale(LocaleMap.DEFAULT);
            String formattedPrice = CurrencyServiceLocator.getCurrencyService()
                    .formatCurrency(model.getPrice(), defaultLocale);
            dataArgs[0] = formattedPrice;
            entry.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ITEM_PRICE_LABEL,
                    dataArgs));
            entry.append(Util.EOL);

            dataArgs[0] = model.getUnitOfMeasure();
            entry.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                    JournalConstantsIfc.ITEM_UNIT_OF_MEASURE_LABEL, dataArgs));
            entry.append(Util.EOL);

            if (model.isTaxable() == true)
            {
                dataArgs[0] = I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.YES_LABEL, null);

            }
            else
            {
                dataArgs[0] = I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.NO_LABEL, null);
            }

            entry.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ITEM_TAXABLE_LABEL,
                    dataArgs));
            entry.append(Util.EOL);

            // change true to YES, false to NO
            if (model.isDiscountable() == true)
            {
                dataArgs[0] = I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.YES_LABEL, null);
                // content.append(ITEM_TAXABLE).append(YES).append("\n");
            }
            else
            {
                dataArgs[0] = I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.NO_LABEL, null);
            }

            entry.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                    JournalConstantsIfc.ITEM_DISCOUNTABLE_LABEL, dataArgs));
            entry.append(Util.EOL);

            if (model.isUsePlanogramID())
            {
                entry.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                        JournalConstantsIfc.PLANOGRAM_ID_LABEL, null));
                if (model.getPlanogramID() != null)
                {
                    int planogram = model.getPlanogramID().length;
                    for (int i = 0; i < planogram; i++)
                    {
                        String planogramID = model.getPlanogramID()[i];
                        entry.append(planogramID).append(Util.EOL);
                        // content.append("\t");
                    }
                }
            }
            jmi.journal(entry.toString());
        }
    }

    /**
     * Calls <code>arrive</code>
     *
     * @param bus Service Bus
     */
    public void reset(BusIfc bus)
    {
        arrive(bus);
    }

    /**
     * This method checks whether the Inventory Inquiry is supported or not.
     */
    public boolean isIventoryInquirySupported(BusIfc bus)
    {
        boolean supported = false;

        try
        {
            ItemInquiryCargo cargo = (ItemInquiryCargo)bus.getCargo();

            // 1. Check whether user has access to Inventory Inquiry Function
            SecurityManagerIfc securityManager = (SecurityManagerIfc)Gateway.getDispatcher().getManager(
                    SecurityManagerIfc.TYPE);
            boolean access = securityManager.checkAccess(cargo.getAppID(), RoleFunctionIfc.INVENTORY_INQUIRY);

            // 2. Check wthether Inventory Inquiry is Enabled or not
            Boolean installedFlag = new Boolean(Gateway.getProperty("application", PSI_ENABLED_PROPERTY, "false"));

            // 3. Check whether the Reentry option is on or off
            boolean isReentryMode = cargo.getRegister().getWorkstation().isTransReentryMode();

            if (access && installedFlag.booleanValue() && !isReentryMode)
            {
                supported = true;
            }
        }
        catch (Exception e)
        {
            logger.warn("Error while getting Inventory Inquiry Supported Flags");
            supported = false;
        }

        return supported;
    }
    
    public boolean isLatEnabled(BusIfc bus){
        
        boolean supported = false;

        ItemInquiryCargo cargo = (ItemInquiryCargo)bus.getCargo();
        // 1. Check wthether LAT is installed or not
        Boolean installedFlag = new Boolean(Gateway.getProperty("application", "LatWebServiceEnabled", "false"));
        // 2. Check whether the Training mode is on or off
        boolean isTrainingMode = cargo.getRegister().getWorkstation().isTrainingMode();
        // 3. Check whether the Reentry option is on or off
        boolean isReentryMode = cargo.getRegister().getWorkstation().isTransReentryMode();
        if (installedFlag.booleanValue() && !isTrainingMode && !isReentryMode)
        {
            supported = true;
        }
        else if (isTrainingMode)
        {
            supported = false;
        }
        else if (isReentryMode)
        {
            supported = false;
        }
        return supported; 
    }

    public Image getItemImage()
    {
        return itemImage;
    }

    public void setItemImage(Image itemImage)
    {
        this.itemImage = itemImage;
    }

    /**
     * Retrieve the Image from the blob in the item info.
     *
     * @param image
     * @return
     */
    private Image getImageFromBytes(ItemImageIfc image)
    {
        byte[] blobBytes = image.getImageBlob();
        Image itemImage = Toolkit.getDefaultToolkit().createImage(blobBytes);
        return itemImage;
    }
}
