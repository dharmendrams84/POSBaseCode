/* ===========================================================================
* Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/ItemNumberEnteredAisle.java /rgbustores_13.4x_generic_branch/5 2012/02/27 18:16:36 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    asinton   02/27/12 - refactored the flow so that items added from scan
 *                         sheet doesn't allow for a hang or mismatched letter.
 *    tksharma  10/11/11 - skipUOMCheck introduced in cargo
 *    tksharma  10/10/11 - Added skipUOMCheckFlag
 *    vtemker   07/25/11 - Code cleanup on the Item quantity logic
 *    vtemker   07/25/11 - Added quantity check for items - minimum is 1
 *    rrkohli   06/01/11 - added error messages if Item id/qty exceeds max
 *                         limit
 *    cgreene   03/18/11 - XbranchMerge cgreene_124_receipt_quick_wins from
 *                         main
 *    cgreene   03/16/11 - implement You Saved feature on reciept and
 *                         AllowMultipleQuantity parameter
 *    jkoppolu  03/09/11 - Scan sheet modifications, set category description
 *                         to null.
 *    jkoppolu  03/02/11 - Modified the aisle to consider the items entered
 *                         through scan sheet.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    cgreene   01/27/10 - Check for null when setting itemID from parModel
 *                         input
 *    abondala  01/03/10 - update header date
 *    ohorne    04/03/09 - as of 13.1 we only support uppercase POS ID
 *    ranojha   02/18/09 - Fixed NullPointerException cases
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         10/5/2006 8:17:44 AM   Keith L. Lesikar
 *         Merging from BBY. Removing logic parsing first digit based upon
 *         Gap-specific business rules.
 *    3    360Commerce 1.2         3/31/2005 4:28:32 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:22:29 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:11:39 PM  Robert Pearse
 *
 *   Revision 1.6  2004/07/20 15:03:09  aachinfiev
 *   @scr 5833 - Disabled check digit for item number in training mode
 *
 *   Revision 1.5  2004/03/05 23:27:58  baa
 *   @scr 3561 Retrieve size from scanned items
 *
 *   Revision 1.4  2004/03/03 23:15:12  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.3  2004/02/12 16:48:17  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:22:50  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.1   Nov 07 2003 12:37:20   baa
 * use SaleCargoIfc
 * Resolution for 3430: Sale Service Refactoring
 *
 *    Rev 1.0   Nov 05 2003 14:14:16   baa
 * Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale;

import java.math.BigDecimal;
import java.util.Locale;

import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.LocaleMap;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.common.ItemSizeCargoIfc;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;
import oracle.retail.stores.pos.ui.beans.ImageGridBeanModel;
import oracle.retail.stores.pos.ui.beans.MultipleQuantityDocument;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;
import oracle.retail.stores.pos.utility.CheckDigitUtility;
import oracle.retail.stores.pos.utility.PLUItemUtility;

/**
 * This aisle is traveled when the user has entered an item number in the
 * SELL_ITEM screen.
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/5 $
 */
public class ItemNumberEnteredAisle extends PosLaneActionAdapter
{
    private static final long serialVersionUID = -3313806442216147309L;

    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/5 $";

    /**
     * resource ID for Invalid Number Error dialog
     */
    public static final String INVALID_NUMBER_ERROR = "InvalidNumberError";

    /**
     * tag for Item Number in resource bundle
     */
    public static final String ITEM_NUMBER_TAG = "ItemNumber";

    /**
     * default text for Item Number in resource bundle
     */
    public static final String ITEM_NUMBER_TEXT = "Item Number";
    
    /**
     * resource ID for Maximum Qty Error dialog
     */
    public static final String QTY_EXCEEDS_MAXIMUM = "QtyExceedMaximumError";
    
    /**
     * resource ID for Minimum Qty Error dialog
     */
    public static final String QTY_LESSTHAN_MINIMUM = "QtyLessthanMinimumError";
    
    /**
     * resource ID for Maximum Length of Item ID Error dialog
     */
    public static final String ITEM_ID_TOO_LONG = "ItemIDTooLongError";
    
    /**
     * resource ID for Invalid Quantity Error dialog
     */
    public static final String QTY_INVALID_ERROR = "InvalidNumberError";
    
    /**
     * Item Field Max Length
     */
    public static final int ITEM_FIELD_MAX_LENGTH = 14;
    
    /**
     * Maximum quantity
     */
    private static final int MAX_ITEM_QUANTITY = 999;
    
    /**
     * Flag indicating if the quantity is valid. 
     */
    private boolean isValidQuantity = true;
    
    /**
     * Stores the item number in the cargo.
     *
     * @param bus Service Bus
     */
    @Override
    public void traverse(BusIfc bus)
    {
        BigDecimal quantity = null;
        String itemID = null;
        boolean isScanned = false;

        UtilityManagerIfc utility = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        // Get the user input
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        POSBaseBeanModel posBase = (POSBaseBeanModel) ui.getModel(POSUIManagerIfc.SELL_ITEM);
        SaleCargoIfc cargo = (SaleCargoIfc) bus.getCargo();
        cargo.skipUOMCheck(false);
        if (posBase != null)
        {
            PromptAndResponseModel parModel = posBase.getPromptAndResponseModel();
            if (parModel != null)
            {
                itemID = parModel.getResponseText();
                if (itemID != null)
                {
                    // as of 13.1 we only support uppercase POS ID, BugDB 8295250
                    itemID = itemID.toUpperCase();
                    int indexOfDelimiter = itemID.indexOf(MultipleQuantityDocument.DELIMITER);
                    // If the input has item quantity
                    if (indexOfDelimiter > 0)
                    {
                        // Extract the item quantity
                        try
                        {
                            quantity = new BigDecimal(itemID.substring(0, indexOfDelimiter));
                        }
                        catch (Exception e)
                        {
                            logger.error("Could not determine quantity from \"" + itemID + "\"", e);
                        }
                        // Check for valid quantity
                        if (quantity == null || quantity.intValue() > MAX_ITEM_QUANTITY || quantity.intValue() <= 0)
                        {
                            isValidQuantity = false;
                        }

                        // Extract the itemID
                        itemID = itemID.substring(indexOfDelimiter + 1);
                        cargo.skipUOMCheck(true);
                    }

                }
                else
                {
                    itemID = cargo.getSelectedScanSheetItemID();
                }
                isScanned = parModel.isScanned();
            }
        }

        

        // Check Digit if not in training mode
        if (!cargo.getRegister().getWorkstation().isTrainingMode() &&
            !utility.validateCheckDigit(CheckDigitUtility.CHECK_DIGIT_FUNCTION_ITEMNUMBER, itemID))
        {
            showInvalidNumberDialog(utility, ui);
        }
        else
        {
            if (!isValidQuantity || itemID.length() > ITEM_FIELD_MAX_LENGTH)
            {
                DialogBeanModel dialogModel = new DialogBeanModel();
                
                // Display the various error messages -
                // Could not parse the quantity
                if (quantity == null && !isValidQuantity)
                    dialogModel.setResourceID(QTY_INVALID_ERROR);
                // Quantity greater than Max quantity
                else if (!isValidQuantity && quantity.intValue() > MAX_ITEM_QUANTITY)
                    dialogModel.setResourceID(QTY_EXCEEDS_MAXIMUM);
                // Quantity is Zero or negative
                else if (!isValidQuantity && quantity.intValue() <= 0)
                    dialogModel.setResourceID(QTY_LESSTHAN_MINIMUM);
                // Item ID too long
                else
                    dialogModel.setResourceID(ITEM_ID_TOO_LONG);
                
                dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_OK, "Ok");
                dialogModel.setType(DialogScreensIfc.ERROR);

                //Reset
                isValidQuantity = true;
                ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
            }
            else
            {
                // Store the item number in the cargo
                cargo.setPLUItemID(itemID);
                cargo.setItemQuantity(quantity);
                cargo.setItemScanned(isScanned);

                bus.mail(CommonLetterIfc.VALID, BusIfc.CURRENT);
            }
        }
    }

    /**
     * Extract item size info from scanned item
     * @param itemID scanned item number
     * @return the item number
     */
    protected String processScannedItemNumber(BusIfc bus, String itemID)
    {
        // Store the item size in the cargo
        ItemSizeCargoIfc cargo = (ItemSizeCargoIfc) bus.getCargo();
        String itemNumber = itemID;
        String [] parser = PLUItemUtility.getInstance().parseItemString(itemID);
        if (parser != null)
        {
          String itemSize = null;
          itemNumber = parser[0];
          itemSize = parser[1];
          cargo.setItemSizeCode(itemSize);
        }

        return itemNumber;
    }

    /**
     * Shows the invalid item number dialog screen.
     *
     * @param the utility manager
     * @param the UI manager
     */
    protected void showInvalidNumberDialog(UtilityManagerIfc utility, POSUIManagerIfc ui)
    {
        DialogBeanModel dialogModel = new DialogBeanModel();
        dialogModel.setResourceID(INVALID_NUMBER_ERROR);
        dialogModel.setType(DialogScreensIfc.ERROR);
        String[] args = new String[1];
        String arg = utility.retrieveDialogText(ITEM_NUMBER_TAG, ITEM_NUMBER_TEXT);
        Locale locale = LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE);
        args[0] = arg.toLowerCase(locale);
        dialogModel.setArgs(args);
        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
    }
}
