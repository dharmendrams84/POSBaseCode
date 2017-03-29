/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/SaleLineItemRenderer.java /rgbustores_13.4x_generic_branch/3 2011/11/10 10:37:12 hyin Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    hyin      11/10/11 - revert back using long description per requirement
 *                         team.
 *    hyin      11/07/11 - use item short description
 *    cgreene   11/03/10 - rename ItemLevelMessageConstants
 *    acadar    06/10/10 - use default locale for currency display
 *    acadar    06/09/10 - XbranchMerge acadar_tech30 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    jswan     06/01/10 - Modified to support transaction retrieval
 *                         performance and data requirements improvements.
 *    jswan     05/28/10 - XbranchMerge jswan_hpqc-techissues-73 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   05/28/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    jswan     04/23/10 - Merges due to refresh to label.
 *    jswan     04/23/10 - Refactored CTR to include more data in the
 *                         SaleReturnLineItem class and table to reduce the
 *                         data required in and retveived from the CO database.
 *                         Modified this class to handle item description
 *                         issues associated with this change.
 *    acadar    04/12/10 - use default locale for display of currency
 *    acadar    04/09/10 - optimize calls to LocaleMAp
 *    acadar    04/08/10 - merge to tip
 *    acadar    04/06/10 - use default locale for currency display
 *    acadar    04/05/10 - use default locale for currency and date/time
 *                         display
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    jswan     06/10/09 - Items which have been picked will not display the
 *                         logo.
 *    jswan     05/21/09 - Code Review
 *    jswan     05/21/09 - Modified to prevent the pickup/delivery images from
 *                         displaying for return items.
 *    cgreene   05/01/09 - string pooling performance aenhancements
 *    vikini    03/24/09 - Clear text from Optional Lines pertaining to
 *                         restocking fee
 *    nkgautam  03/06/09 - Associate name length changed for maximum thirty
 *                         multi-byte chinese characters to accomodate on sale
 *                         screen
 *    nkgautam  02/28/09 - Clipped associate name to 50 characters and added a
 *                         method makeSafeString to clip the name to desired
 *                         length
 *    mahising  02/26/09 - Rework for PDO functionality
 *    acadar    02/25/09 - override the getDefaultLocale from JComponent
 *    acadar    02/25/09 - use application default locale instead of jvm locale
 *    ddbaker   02/18/09 - Corrected alignment issue on Sale screen.
 *    mkochumm  02/12/09 - use default locale for dates
 *    ddbaker   02/10/09 - Updated with code review comments.
 *    ddbaker   02/10/09 - Made restocking fee span two lines. This lent itself
 *                         to cleaning up some redundant constants. Also
 *                         removed extraneous icon related code that was made
 *                         obsolete.
 *    ddbaker   02/09/09 - Merged to moving target.
 *    ddbaker   02/06/09 - Rearranged location of pickup and delivery
 *                         information for clearer display on sale screen. Also
 *                         ensure that optional and sale data don't interfere
 *                         with each other.
 *    ddbaker   01/06/09 - Removed duplicate additions of labels to renderer.
 *                         Labels are correctly added by
 *                         AbstractListRenderer.initLabels() only.
 *    aphulamb  01/05/09 - fixed QA issue
 *    aphulamb  01/02/09 - fix delivery issues
 *    vikini    12/23/08 - Made changes to show Return Message instead of Sale
 *                         Message on Return Item Screen
 *    aphulamb  12/17/08 - bug fixing of PDO
 *    aphulamb  12/10/08 - returns functionality changes for greying out
 *                         buttons
 *    aphulamb  11/27/08 - checking files after merging code for receipt
 *                         printing by Amrish
 *    aphulamb  11/24/08 - Checking files after code review by amrish
 *    aphulamb  11/22/08 - Checking files after code review by Naga
 *    aphulamb  11/18/08 - Pickup Delivery Order
 *    aphulamb  11/13/08 - Check in all the files for Pickup Delivery Order
 *                         functionality
 *    vikini    11/14/08 - Fixed the extra line break in each Line Item not
 *                         having ILRM Screen Message
 *    ddbaker   11/13/08 - Eliminate button clipping by adjusting font
 *    ddbaker   11/11/08 - Merge Changes
 *    ddbaker   11/10/08 - Updated based on new requirements
 *    ddbaker   11/06/08 - Update due to merges.
 *    vikini    11/10/08 - Incorporating Code Review findings
 *    vikini    11/08/08 - Line Break in Sale Item Screen for ILRM Messages
 *
 * ===========================================================================
 * $Log:
 *   13   360Commerce 1.12        3/25/2008 4:06:54 AM   Vikram Gopinath CD
 *        #29942, ported code from v12x. Display the sales associate employee
 *        id if the person information is not present.
 *   12   360Commerce 1.11        2/27/2008 3:19:23 PM   Alan N. Sinton  CR
 *        29989: Changed masked to truncated for UI renders of PAN.
 *   11   360Commerce 1.10        12/16/2007 5:57:17 PM  Alan N. Sinton  CR
 *        29598: Fixes for various areas broke from PABP changes.
 *   10   360Commerce 1.9         7/9/2007 3:07:53 PM    Anda D. Cadar   I18N
 *        changes for CR 27494: POS 1st initialization when Server is offline
 *   9    360Commerce 1.8         5/8/2007 11:32:29 AM   Anda D. Cadar
 *        currency changes for I18N
 *   8    360Commerce 1.7         4/25/2007 8:58:29 AM   Anda D. Cadar   I18N
 *        merge
 *   7    360Commerce 1.6         4/24/2007 11:05:23 AM  Ashok.Mondal    CR
 *        4381 :V7.2.2 merge to trunk.
 *   6    360Commerce 1.5         1/25/2006 4:11:44 PM   Brett J. Larsen merge
 *        7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
 *   5    360Commerce 1.4         1/22/2006 11:45:28 AM  Ron W. Haight
 *        removed references to com.ibm.math.BigDecimal
 *   4    360Commerce 1.3         1/21/2006 9:56:27 PM   Kulbhushan Sharma Some
 *         code refactoring
 *   3    360Commerce 1.2         3/31/2005 4:29:48 PM   Robert Pearse
 *   2    360Commerce 1.1         3/10/2005 10:24:58 AM  Robert Pearse
 *   1    360Commerce 1.0         2/11/2005 12:14:00 PM  Robert Pearse
 *:
 *   6    .v700     1.2.1.2     1/4/2006 11:59:25      Deepanshu       CR 6160:
 *        Set quantity as blank for Alterations
 *   5    .v700     1.2.1.1     10/26/2005 11:51:24    Jason L. DeLeau 6087:
 *        Change the scope of layoutOptions from default to protected, for
 *        services extensibility purposes. (Forgot to add comment in previous
 *        version)
 *   4    .v700     1.2.1.0     10/26/2005 11:34:57    Jason L. DeLeau 60
 *   3    360Commerce1.2         3/31/2005 15:29:48     Robert Pearse
 *   2    360Commerce1.1         3/10/2005 10:24:58     Robert Pearse
 *   1    360Commerce1.0         2/11/2005 12:14:00     Robert Pearse
 *
 *  Revision 1.22  2004/07/15 23:00:08  jriggins
 *  @scr 6309 Capture the absolute value of discount values for presentation reasons.
 *
 *  Revision 1.21  2004/07/12 20:13:55  mweis
 *  @scr 6158 "Gift Card ID:" label not appearing correctly
 *
 *  Revision 1.20  2004/06/04 22:35:56  mweis
 *  @scr 4250 Return's restocking fee incorrectly calculated
 *
 *  Revision 1.19  2004/05/21 13:44:56  dfierling
 *  @scr 3987 - updated column widths
 *
 *  Revision 1.18  2004/05/05 14:05:49  rsachdeva
 *  @scr 4670 Send: Multiple Sends
 *
 *  Revision 1.17  2004/04/27 21:30:24  jriggins
 *  @scr 3979 Code review cleanup
 *
 *  Revision 1.16  2004/04/22 20:09:10  mweis
 *  @scr 4507 Deal Item indicator - code review updates
 *
 *  Revision 1.15  2004/04/21 20:35:30  mweis
 *  @scr 4507 Deal Item indicator - initial submission
 *
 *  Revision 1.14  2004/04/16 13:51:34  mweis
 *  @scr 4410 Price Override indicator -- initial submission
 *
 *  Revision 1.13  2004/04/15 15:43:18  jriggins
 *  @scr 3979 Added price adjustment checks using instanceof
 *
 *  Revision 1.12  2004/04/13 16:04:33  mweis
 *  @scr 4206 JavaDoc updates.
 *
 *  Revision 1.11  2004/04/09 16:56:00  cdb
 *  @scr 4302 Removed double semicolon warnings.
 *
 *  Revision 1.10  2004/04/08 20:33:02  cdb
 *  @scr 4206 Cleaned up class headers for logs and revisions.
 *
 *  Revision 1.9  2004/04/03 00:23:38  jriggins
 *  @scr 3979 Price Adjustment feature dev
 *
 *  Revision 1.8  2004/03/26 05:39:05  baa
 *  @scr 3561 Returns - modify flow to support entering price code for not found gift receipt
 *
 *  Revision 1.7  2004/03/16 17:15:18  build
 *  Forcing head revision
 *
 *  Revision 1.6  2004/03/12 21:44:26  rsachdeva
 *  @scr Sale Item Size
 *
 *  Revision 1.5  2004/03/02 16:23:00  rsachdeva
 *  @scr 3906 Whole Number Format
 *
 *  Revision 1.3  2004/03/01 15:39:19  rsachdeva
 *  @scr 3906 Unit of Measure
 *
 *  Revision 1.2  2004/02/11 20:56:27  rhafernik
 *  @scr 0 Log4J conversion and code cleanup
 *
 *  Revision 1.1.1.1  2004/02/11 01:04:22  cschellenger
 *  updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.4   Dec 19 2003 15:21:56   lzhao
 * issue code review follow up
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 *
 *    Rev 1.3   Dec 16 2003 11:41:24   lzhao
 * gift card issue hide/show item id.
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 *
 *    Rev 1.2   Dec 12 2003 14:20:30   lzhao
 * move gift card issue related task to giftcard/issue package.
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 *
 *    Rev 1.1   Sep 23 2003 11:18:46   rsachdeva
 * Suspended Transaction being Retrieved should display Restocking Fee
 * Resolution for POS SCR-2753: Retrieve supended return trans, restocking fee not shows on Sell Item screen
 *
 *    Rev 1.0   Aug 29 2003 16:11:58   CSchellenger
 * Initial revision.
 *
 *    Rev 1.11   Apr 16 2003 13:00:46   pdd
 * Removed reference to DomainUtilities
 * Resolution for 2103: Remove uses of deprecated items in POS.
 *
 *    Rev 1.10   Apr 09 2003 14:03:16   baa
 * data base conversion / plaf cleanup
 * Resolution for POS SCR-1866: I18n Database  support
 *
 *    Rev 1.9   Mar 24 2003 10:08:18   baa
 * remove reference to foundation.util.EMPTY_STRING
 * Resolution for POS SCR-2101: Remove uses of  foundation constant  EMPTY_STRING
 *
 *    Rev 1.8   Mar 07 2003 17:11:10   baa
 * code review changes for I18n
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.7   Jan 15 2003 14:11:02   bwf
 * In sizeOptionalField, check if serial number label.  If it is then override to length of 136.
 *
 *    Rev 1.6   Oct 09 2002 16:20:06   jriggins
 * Pulling the tax mode from the bundle.
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.5   Sep 25 2002 09:52:26   jriggins
 * Retrieving tax mode character from the bundle
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.4   Sep 18 2002 17:15:32   baa
 * country/state changes
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.3   Sep 06 2002 17:25:36   baa
 * allow for currency to be display using groupings
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.2   Aug 23 2002 14:43:04   HDyer
 * Added optional sale line for restocking fee. Added getListCellRendererComponent method so foreground colors would be correct.
 * Resolution for 1774: Restocking Fee not displayed on Sell Item screen when applied
 *
 *    Rev 1.1   Aug 14 2002 18:18:32   baa
 * format currency
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.0   Apr 29 2002 14:57:24   msg
 * Initial revision.
 *
 *    Rev 1.1   27 Mar 2002 17:34:38   dfh
 * removde Dollar Sign from currency format
 * Resolution for POS SCR-365:  appears on several screens, not to specification
 * Resolution for POS SCR-1445: Dollar signs are showing up on the receipt
 *
 *    Rev 1.0   Mar 18 2002 11:57:22   msg
 * Initial revision.
 *
 *    Rev 1.20   13 Mar 2002 17:08:08   pdd
 * Modified to use the domain object factory and ifcs.
 * Resolution for POS SCR-1332: Ensure domain objects are created through factory
 *
 *    Rev 1.19   28 Feb 2002 10:01:08   sfl
 * Expaneded the size of optionFields array to six.
 * Resolution for POS SCR-1421: Newly added a requirement to display Send in the Sell Item screen
 *
 *    Rev 1.18   Feb 27 2002 21:25:56   mpm
 * Continuing work on internationalization
 * Resolution for POS SCR-351: Internationalization
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.common.constants.ItemLevelMessageConstants;
import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.lineitem.ItemPriceIfc;
import oracle.retail.stores.domain.lineitem.KitHeaderLineItemIfc;
import oracle.retail.stores.domain.lineitem.OrderLineItemIfc;
import oracle.retail.stores.domain.lineitem.PriceAdjustmentLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.stock.GiftCardPLUItemIfc;
import oracle.retail.stores.domain.stock.ItemClassificationConstantsIfc;
import oracle.retail.stores.domain.stock.ItemSizeConstantsIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.domain.tax.TaxIfc;
import oracle.retail.stores.domain.utility.CodeConstantsIfc;
import oracle.retail.stores.domain.utility.CodeEntryIfc;
import oracle.retail.stores.domain.utility.CodeListIfc;
import oracle.retail.stores.domain.utility.CodeListMapIfc;
import oracle.retail.stores.domain.utility.DomainUtil;
import oracle.retail.stores.domain.utility.GiftCardIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.domain.utility.LocaleUtilities;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.pos.ui.plaf.UIFactory;
import oracle.retail.stores.pos.utility.PLUItemUtility;

/**
 *  Renderer for SaleReturnLineItems.
 */
public class SaleLineItemRenderer extends LineItemRenderer
    implements CodeConstantsIfc, ItemLevelMessageConstants
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3238897613749845147L;

    /** the default weights that layout the first display line */
    public static int[] SALE_WEIGHTS = { 79, 21 };

    /** the default weights that layout the second display line */
    public static int[] SALE_WEIGHTS2 = { 21, 16, 21, 21, 21 }; //{30,10,20,15,15,10};

    /** the default weights that layout the first display line */
    public static int[] SALE_WIDTHS = { 4, 1 };

    /** the default weights that layout the second display line */
    public static int[] SALE_WIDTHS2 = { 1, 1, 1, 1, 1 };

    /** the description column */
    public static int DESCRIPTION = 0;

    /** the tax column */
    public static int TAX = 1;

    /** the stock column */
    public static int STOCK = 2;

    /** the quantity column */
    public static int QUANTITY = 3;

    /** the price column */
    public static int PRICE = 4;

    /** the discount column */
    public static int DISCOUNT = 5;

    /** the ext_price column */
    public static int EXT_PRICE = 6;

    /** the maximum number of fields */
    public static int MAX_FIELDS = 7;

    /** Default restocking fee label text */
    protected String restockingFeeLabel = "Restocking Fee";

    /** Price Adjustment purchase price label text */
    protected String priceAdjustmentPurchasePriceLabel = "PriceAdjustPurchasePriceLabel";

    /** Price Adjustment current price label text */
    protected String priceAdjustmentCurrentPriceLabel = "PriceAdjustCurrentPriceLabel";

    protected JLabel[] optionalFields;

    // Optional information for the line item, in the same column layout as item
    // labels. To display additional pricing info etc.
    protected JLabel[] optionalSaleLineItemLabels;

    /** Code List for reason code lookup */
    protected CodeListMapIfc map = null;

    protected int optionSlot = 0;

    /** number of labels per line */
    public static int NUMBER_OF_LABELS = 5;

    /** The marker used to indicate if a price was overriden. */
    protected static final String OVERRIDE_MARKER = DomainUtil.retrieveOverrideMarker("panel");

    /** The marker used to indicate if an item is a deal item. */
    protected static final String DEAL_ITEM_MARKER = PLUItemUtility.retrieveDealItemMarker();

    /** Property configured for quantity total incremented using non-merchandise quantity **/
    protected static final String QUANTITY_TOTAL_NONMERCHANDISE = "QuantityTotalNonMerchandise";

    /** Default true value **/
    protected static final String DEFAULT_TRUE_VALUE = "false";

    protected JLabel itemLevelScreenMessageLabel = null;

    protected int leftInset = 30;

    /** Indicates that the Pickup/Delivery images should be displayed */
    protected boolean displayPickupDeliveryImage = true;

    private final int maxCharsBeforeLineBrk = 90;

    /** Defines the Maximum Length of Sales Associate Name field **/
    private final int MAX_ASSOCIATE_SALES_NAME_LENGTH = 30;

    /**
     *  Default constructor.
     */
    public SaleLineItemRenderer()
    {
        super();
        setName("SaleLineItemRenderer");

        // set default in case lookup fails
        firstLineWeights = SALE_WEIGHTS;
        secondLineWeights = SALE_WEIGHTS2;
        firstLineWidths = SALE_WIDTHS;
        secondLineWidths = SALE_WIDTHS2;
        // look up the label weights
        setFirstLineWeights("saleItemRendererWeights");
        setSecondLineWeights("saleItemRendererWeights2");
        setFirstLineWidths("saleItemRendererWidths");
        setSecondLineWidths("saleItemRendererWidths2");

        fieldCount = MAX_FIELDS;
        lineBreak = TAX;
        secondLineBreak = EXT_PRICE;

        initialize();
    }

    /**
     * Over ride to add new Label.
     * This is done to show the Item Message on the screen
     */
    @Override
    protected void initLabels()
    {
        super.initLabels();
        itemLevelScreenMessageLabel = new JLabel();
        itemLevelScreenMessageLabel.setBorder(null);
    }

    /**
     *  Initializes this renderer's components.
     */
    protected void initOptions()
    {
        String prefix = UI_PREFIX + ".label";

        optionalFields = new JLabel[NUMBER_OF_LABELS];

        labels[DESCRIPTION].setHorizontalAlignment(JLabel.LEFT);
        labels[STOCK].setHorizontalAlignment(JLabel.LEFT);
        labels[PRICE].setHorizontalAlignment(JLabel.RIGHT);
        labels[DISCOUNT].setHorizontalAlignment(JLabel.RIGHT);
        labels[EXT_PRICE].setHorizontalAlignment(JLabel.RIGHT);

        // create the optional fields
        for (int i = 0; i < NUMBER_OF_LABELS; i++)
        {
            optionalFields[i] = uiFactory.createLabel("", "", null, prefix);
            optionalFields[i].setHorizontalAlignment(JLabel.LEFT);
        }

        layoutOptions();
    }

    /**
     *  Initializes the layout and lays out the components.
     */
    protected void layoutOptions()
    {
        GridBagConstraints constraints = uiFactory.getConstraints("Renderer");

        // add optional fields by column
        constraints.gridwidth = 3;
        constraints.weightx = 0.0;

        // add optional fields by column
        constraints.gridx = 0;
        constraints.insets.left = leftInset;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        add(optionalFields[0], constraints);
        add(optionalFields[2], constraints);
        add(optionalFields[4], constraints);
        add(optionalFields[1], constraints);
        add(optionalFields[3], constraints);

        // Set the layout of the optional sale line item labels
        layoutOptionalSaleLineItemLabels();

        // add the item level screen text
        layoutItemLevelScreenText();
    }

    /**
     * Lays out the item level screen text component
     */
    protected void layoutItemLevelScreenText()
    {
    	
        // add the optional text
        GridBagConstraints constraints = uiFactory.getConstraints("Renderer");
        // Set position to start of the next line
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = GridBagConstraints.REMAINDER;

        constraints.insets.left = leftInset;
        add(itemLevelScreenMessageLabel, constraints);
        

    }

    /**
     *  Creates and initializes the layout of an optional sale line item
     */
    protected void layoutOptionalSaleLineItemLabels()
    {
        String prefix = UI_PREFIX + ".label";
        GridBagConstraints constraints = uiFactory.getConstraints("Renderer");

        // Set position to start of the next line
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = GridBagConstraints.RELATIVE;

        // Determine max number of fields per line based on the second line
        // Create labels for additional single line, copying alignment and
        // weights from the labels in the sale line item. Create only the
        // number for a single line, so using lineBreak variable for that.
        optionalSaleLineItemLabels = new JLabel[fieldCount];

        int lastLine = lineBreak;
        for (int i = 0; i <= lineBreak; i++)
        {
            optionalSaleLineItemLabels[i] = uiFactory.createLabel("", "", null, prefix);
            optionalSaleLineItemLabels[i].setHorizontalAlignment(labels[i].getHorizontalAlignment());
            constraints.weightx = firstLineWeights[i] * .01;
            if (i == lastLine)
            {
                constraints.gridwidth = GridBagConstraints.REMAINDER;
            }
            else if (firstLineWidths != null)
            {
                constraints.gridwidth = firstLineWidths[i];
            }
            add(optionalSaleLineItemLabels[i], constraints);
        }
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        lastLine = fieldCount - 1;
        for (int i = lineBreak + 1; i < fieldCount; i++)
        {
            int secondCount = i - lineBreak - 1;
            optionalSaleLineItemLabels[i] = uiFactory.createLabel("", "", null, prefix);
            optionalSaleLineItemLabels[i].setHorizontalAlignment(labels[i].getHorizontalAlignment());
            constraints.weightx = secondLineWeights[secondCount] * .01;
            if (i == lastLine)
            {
                constraints.gridwidth = GridBagConstraints.REMAINDER;
            }
            else if (secondLineWidths != null)
            {
               constraints.gridwidth = secondLineWidths[secondCount];
            }
            add(optionalSaleLineItemLabels[i], constraints);
        }
    }

    /**
     *  Sets the optional data.
     *  @param item The line item that supplies the optional data.
     */
    public void setOptionalData(SaleReturnLineItemIfc item)
    {
    	
        // clear the text from all optional fields
        for (int i = 0; i < NUMBER_OF_LABELS; i++)
        {
            optionalFields[i].setText("");
            sizeOptionalField(optionalFields[i]);
        }

        optionSlot = 0;
        String itemText = null;

        // This boolean is set to false in ReturnLineItemRenderer.java to prevent the images from
        // being displayed for returns.
        if (displayPickupDeliveryImage && item.getOrderItemStatus().getStatus().getStatus() != OrderLineItemIfc.ORDER_ITEM_STATUS_PICKED_UP)
        {
            if (item.getOrderItemStatus().getItemDispositionCode() == OrderLineItemIfc.ORDER_ITEM_DISPOSITION_DELIVERY)
            {
                String deliveryLogo = UIFactory.getInstance().getUIProperties(LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE)).
                    getProperty("DeliveryLogo");
                Image deliveryLogoImage = UIUtilities.getImage(deliveryLogo, this);
                ImageIcon receivedIcon = new ImageIcon(deliveryLogoImage);
                if (item.getOrderItemStatus().getDeliveryDate() != null)
                {
                    String logoDate = item.getOrderItemStatus().getDeliveryDate().toFormattedString();
                    setOptionalFieldText(deliveryLabel.concat(" ").concat(logoDate), receivedIcon);
                }
            }
            else if (item.getOrderItemStatus().getItemDispositionCode() == OrderLineItemIfc.ORDER_ITEM_DISPOSITION_PICKUP)

            {
                String pickupLogo = UIFactory.getInstance().getUIProperties(LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE)).
                    getProperty("PickupLogo");
                Image pickupLogoImage = UIUtilities.getImage(pickupLogo, this);

                ImageIcon receivedIcon = new ImageIcon(pickupLogoImage);
                if (item.getOrderItemStatus().getPickupDate() != null)
                {
                    String logoDate = item.getOrderItemStatus().getPickupDate().toFormattedString();
                    setOptionalFieldText(pickupLabel.concat(" ").concat(logoDate), receivedIcon);
                }
            }
        }

        if (item.isPartOfPriceAdjustment())
        {
            if (item.isReturnLineItem())
            {
                String purchasePriceText = UIUtilities.retrieveText("SellItemWorkPanelSpec", "posText",
                        priceAdjustmentPurchasePriceLabel);
                setOptionalFieldText(purchasePriceText);
            }
            else
            {
                String currentPriceText = UIUtilities.retrieveText("SellItemWorkPanelSpec", "posText",
                        priceAdjustmentCurrentPriceLabel);
                setOptionalFieldText(currentPriceText);
            }
        }
        //If the item is a gift card, then sale info field within the line item should
        //display "Gift Card ID: card #"
        if (item.getPLUItem() instanceof GiftCardPLUItemIfc)
        {
            GiftCardIfc giftCard = ((GiftCardPLUItemIfc)(item.getPLUItem())).getGiftCard();

            if (giftCard != null && giftCard.getEncipheredCardData() != null
                    && !Util.isEmpty(giftCard.getEncipheredCardData().getTruncatedAcctNumber()))
            {
                itemText = giftCardLabel + giftCard.getEncipheredCardData().getTruncatedAcctNumber();
                setOptionalFieldText(itemText);
            }
        }
        //If an item has a serial number associated with it, then the sale info field
        //within the line item should display 'Serialized'
        else if (!Util.isEmpty(item.getItemSerial()))
        {
            itemText = serialLabel + item.getItemSerial();
            setOptionalFieldText(itemText);
        }

        // if the item is associated with a gift registry it
        // should display "Gift Reg.#" and the number
        if (item.getRegistry() != null)
        {
            itemText = item.getRegistry().getID().toString();

            if (!itemText.equals(""))
            {
                itemText = giftRegLabel + itemText;
                setOptionalFieldText(itemText);
            }
        }
        if (item.getSalesAssociate() != null)
        {
        	
            if (item.getSalesAssociate().getEmployeeID() != null && item.getSalesAssociateModifiedFlag())
            {
            	
                if (item.getSalesAssociate().getPersonName() != null)
                {
                	
                	
                    itemText = salesAssocLabel +
                                makeSafeStringForDisplay(item.getSalesAssociate().getPersonName().getFirstLastName(),
                                    MAX_ASSOCIATE_SALES_NAME_LENGTH);
                }
                else
                {
                
                    itemText = salesAssocLabel + item.getSalesAssociate().getEmployeeID();
                }
                
                setOptionalFieldText(itemText);
               
               
            }
            
            
        }
        //send item displayed with send label count
        //to show association with particular send
        if (item.getItemSendFlag())
        {
            itemText = sendLabel + item.getSendLabelCount();
            setOptionalFieldText(itemText);
        }

        // Check if Gift receipt and display "Gift Receipt"
        if (item.isGiftReceiptItem())
        {
            itemText = giftReceiptLabel;
            setOptionalFieldText(itemText);
        }

        // Check if item is a return and there is a restocking fee to display
        if (item.isReturnLineItem())
        {
            CurrencyIfc restockingFee = item.getReturnItem().getRestockingFee();
            if (restockingFee == null)
            {
                //Always gets the restocking fee applied.
                restockingFee = item.getItemPrice().getRestockingFee();
            }
            if (restockingFee != null && restockingFee.signum() != CurrencyIfc.ZERO)
            {
                // Set label sizes based on original line labels
                for (int i = 0; i < fieldCount; i++)
                {
                    optionalSaleLineItemLabels[i].setPreferredSize(labels[i].getPreferredSize());
                    optionalSaleLineItemLabels[i].setMinimumSize(labels[i].getMinimumSize());
                }

                // Multiply restockingFee by the quantity
                BigDecimal qty = item.getItemQuantityDecimal().abs(); // Force to be a positive number
                CurrencyIfc extendedRestockingFee = restockingFee.multiply(qty);

                // Restocking fee is not taxed. Get the no tax character
                String tax = TaxIfc.TAX_MODE_CHAR[TaxIfc.TAX_MODE_NON_TAXABLE];

                // Add next line with restocking info with same column layout
                // as the first labels line


                optionalSaleLineItemLabels[DESCRIPTION].setText(restockingFeeLabel);
                optionalSaleLineItemLabels[TAX].setText(UIUtilities.retrieveCommonText("TaxModeChar."
                        + tax, tax));
                optionalSaleLineItemLabels[PRICE].setText(restockingFee.toGroupFormattedString());
                optionalSaleLineItemLabels[EXT_PRICE].setText(extendedRestockingFee
                        .toGroupFormattedString());

            }
            else
            {
              //Clear old values when the Re Stocking Fee is null
              optionalSaleLineItemLabels[DESCRIPTION].setText("");
              optionalSaleLineItemLabels[TAX].setText("");
              optionalSaleLineItemLabels[PRICE].setText("");
              optionalSaleLineItemLabels[EXT_PRICE].setText("");
              // set size to Zero for the ones having zero size text
              for (int i = 0; i < fieldCount; i++)
              {
                  sizeOptionalField(optionalSaleLineItemLabels[i]);
              }
            }
        }
        else
        {
            // Clear the text from the optional sale line.
            for (int i = 0; i < fieldCount; i++)
            {
                optionalSaleLineItemLabels[i].setText("");
                sizeOptionalField(optionalSaleLineItemLabels[i]);
            }

        }
        
   //  System.out.println("item.getSalesAssociate().getName().getFullName() "+item.getSalesAssociate().getName().getFullName());
       //itemText = item.getSalesAssociate().getName().getFullName()+ " ";
     //  itemText = "Remaining Spend"+ " ";
      // System.out.println("before setting ");
      // System.out.println(item.getPLUItemID()+" setOptionalData  "+item.getItemPrice().getItemDiscountAmount().getDecimalValue());
     /*  if(item.getPLUItem().getMaxSpendLimit()!= null){
    	   itemText+=item.getPLUItem().getMaxSpendLimit();
       }else{
    	   itemText+= new BigDecimal(0) ;
       }*/
        
        setOptionalFieldText(itemText);
        
       
    }

    /**
     *  Sizes an optional field based on whether or not it contains
     *  text. An empty field will have a width of 0.
     *  @param label the field to be sized
     */
    protected void sizeOptionalField(JLabel label)
    {
        Dimension oldDim = label.getPreferredSize();
        int w = oldDim.width;
        Dimension newDim;
        int iconWidth = 0;

        if (label.getIcon() != null && label.getIcon().getIconWidth() > 0)
        {
            iconWidth = label.getIcon().getIconWidth();
        }
        if (label.getText().equals(""))
        {
            newDim = new Dimension(0, 0);
        }
        else
        {
            FontMetrics fm = label.getFontMetrics(label.getFont());
            w = SwingUtilities.computeStringWidth(fm, label.getText());
            newDim = new Dimension(w + 1 + iconWidth, lineHeight);
        }
        label.setPreferredSize(newDim);
    }

    /**
     * Sets the map value.
     * @param aValue The new map value.
     */
    public void setMap(CodeListMapIfc aValue)
    {
        map = aValue;
    }

    /**
     * Sets the optional field text in the next available optional slot.
     * @param text The new text for the field.
     */
    protected void setOptionalFieldText(String text)
    {
        if (optionSlot < NUMBER_OF_LABELS)
        {
            optionalFields[optionSlot].setText(text);
            // setIcon() sets the "defult icon", so if there are any other icons in any other
            // line items, then this JLabel will have a default icon. So we null it out instead.
            optionalFields[optionSlot].setIcon(null);
            sizeOptionalField(optionalFields[optionSlot]);
            optionSlot++;
        }
    }

    /**
     * Sets the optional field text in the next available optional slot.
     * @param text The new text for the field.
     */
    protected void setOptionalFieldText(String text, ImageIcon icon)
    {
    	
        if (optionSlot < NUMBER_OF_LABELS)
        {
            optionalFields[optionSlot].setText(text);
            optionalFields[optionSlot].setIcon(icon);
            sizeOptionalField(optionalFields[optionSlot]);
            optionSlot++;
        }
    }

    /**
     *  sets the visual components of the cell
     *  @param value Object
     */
    public void setData(Object value)
    {
    	
        SaleReturnLineItemIfc lineItem = (SaleReturnLineItemIfc)value;
        StringBuilder screenMessage = new StringBuilder();
        PLUItemIfc pluItem = lineItem.getPLUItem();
        String itemMessage = null;

        String description = lineItem.getPLUItem().getDescription(getLocale());
        if (Util.isEmpty(description))
        {
            description = lineItem.getReceiptDescription();
        }
        labels[DESCRIPTION].setText(description);
        if (isEntryByItemID(lineItem))
        {
            String stockText = null;
            if (lineItem.isKitHeader())
            {
                stockText = lineItem.getItemID() + " " + kitLabel + " ";
            }
            else
            {
                String size = lineItem.getItemSizeCode();
                if (Util.isEmpty(size) || size.equalsIgnoreCase(ItemSizeConstantsIfc.ITEM_SIZE_IDENTIFIER_UNSPECIFIED))
                {
                    stockText = lineItem.getItemID();
                }
                else
                {
                    stockText = lineItem.getItemID() + " " + size + " ";
                }
            }
            labels[STOCK].setText(stockText);
        }
        else
        {
            labels[STOCK].setText(lineItem.isKitHeader() ? lineItem.getItemID() + " " + kitLabel + " " : "");
        }

        //      item level screen message
        if (lineItem.isReturnLineItem() || (lineItem.getReturnItem() != null))
        {
            screenMessage = new StringBuilder();
            itemMessage = addLineBreaks(pluItem.getItemLevelMessage(RETURN, SCREEN), maxCharsBeforeLineBrk);
            screenMessage.append("<html><b><body>").append(itemMessage).append("</body></b></html>");
            itemLevelScreenMessageLabel.setText(screenMessage.toString());
        }
        else
        {
            screenMessage = new StringBuilder();
            itemMessage = addLineBreaks(pluItem.getItemLevelMessage(SALE, SCREEN), maxCharsBeforeLineBrk);
            screenMessage.append("<html><b><body>").append(itemMessage).append("</body></b></html>");
            itemLevelScreenMessageLabel.setText(screenMessage.toString());
        }

        String countQuantity = DomainGateway.getProperty(QUANTITY_TOTAL_NONMERCHANDISE, DEFAULT_TRUE_VALUE);
        Boolean incrementNonMerchandiseQuantity = new Boolean(countQuantity);

        if (lineItem.getPLUItem().getItemClassification().getItemType() == ItemClassificationConstantsIfc.TYPE_SERVICE
                && !incrementNonMerchandiseQuantity.booleanValue())
        {
            labels[QUANTITY].setText("");
        }
        else if (lineItem.isUnitOfMeasureItem())
        {
            //since it is not having unit of measure as units, so to be displayed as decimal number
            labels[QUANTITY].setText(LocaleUtilities.formatDecimal(lineItem.getItemQuantityDecimal(), getLocale()));
        }
        else
        {
            //since it is having unit of measure as units, so to be displayed as whole number
            //items having unit of measure as units should not have fractional qtys.
            labels[QUANTITY].setText(LocaleUtilities.formatDecimalForWholeNumber(lineItem.getItemQuantityDecimal(),
                    getLocale()));
        }

        // If we have a deal item, use the deal item marker.
        String dealItemMarker = "";
        if (lineItem.getItemPrice().getBestDealDiscount() != null)
        {
            dealItemMarker = DEAL_ITEM_MARKER;
        }

        // If we have a price override, use the override marker.
        String overrideMarker = "";
        if (lineItem.getItemPrice().isPriceOverride())
        {
            overrideMarker = OVERRIDE_MARKER;
        }

        labels[PRICE].setText(lineItem.getSellingPrice().toGroupFormattedString() + dealItemMarker
                + overrideMarker);
        labels[EXT_PRICE].setText(lineItem.getExtendedDiscountedSellingPrice().toGroupFormattedString());
        String taxMode = lineItem.getTaxStatusDescriptor();
        labels[TAX].setText(UIUtilities.retrieveCommonText("TaxModeChar." + taxMode, taxMode));

        // set the optional fields
        setOptionalData(lineItem);

        // check for any discounts
        CurrencyIfc discountTotal = null;

        if (lineItem.isKitHeader())
        {
            discountTotal = ((KitHeaderLineItemIfc)lineItem).getKitDiscountTotal();
        }
        else
        {
            discountTotal = lineItem.getItemDiscountTotal();
        }

        if (discountTotal.signum() == CurrencyIfc.ZERO)
        {
            labels[DISCOUNT].setText("");
        }
        else
        {
            labels[DISCOUNT].setText(discountTotal.abs().toGroupFormattedString());
        }
    }

    /**
     *  creates the prototype cell to speed updates
     *  @return SaleReturnLineItem the prototype renderer
     */
    public Object createPrototype()
    {
        SaleReturnLineItemIfc cell = DomainGateway.getFactory().getSaleReturnLineItemInstance();

        PLUItemIfc plu = DomainGateway.getFactory().getPLUItemInstance();
        plu.getLocalizedDescriptions().initialize(LocaleMap.getSupportedLocales(), "XXXXXXXXXXXXXXX");
        plu.setItemID("12345678901234");
        cell.setPLUItem(plu);

        CurrencyIfc testPrice = DomainGateway.getBaseCurrencyInstance("888888.88");

        ItemPriceIfc price = DomainGateway.getFactory().getItemPriceInstance();

        price.setSellingPrice(testPrice);
        price.setItemDiscountTotal(testPrice);
        price.setExtendedSellingPrice(testPrice);

        price.setItemQuantity(new BigDecimal("888.88"));
        cell.setItemPrice(price);

        EmployeeIfc emp = DomainGateway.getFactory().getEmployeeInstance();
        cell.setSalesAssociate(emp);

        return ((Object)cell);
    }

    public static Object createSaleItem()
    {
        SaleReturnLineItemIfc cell = DomainGateway.getFactory().getSaleReturnLineItemInstance();

        PLUItemIfc plu = DomainGateway.getFactory().getPLUItemInstance();
        plu.getLocalizedDescriptions().initialize(LocaleMap.getSupportedLocales(), "XXXXXXXXXXXXXXX");
        plu.setItemID("12345678901234");
        cell.setPLUItem(plu);

        CurrencyIfc testPrice = DomainGateway.getBaseCurrencyInstance("0");

        ItemPriceIfc price = DomainGateway.getFactory().getItemPriceInstance();

        price.setSellingPrice(testPrice);
        price.setItemDiscountTotal(testPrice);
        price.setExtendedSellingPrice(testPrice);

        price.setItemQuantity(new BigDecimal("0"));
        cell.setItemPrice(price);

        EmployeeIfc emp = DomainGateway.getFactory().getEmployeeInstance();
        cell.setSalesAssociate(emp);

        return ((Object)cell);
    }

    
    
    /**
     *  Sets the format for printing out currency and quantities.
     */
    protected void setPropertyFields()
    {
        super.setPropertyFields();

        // Get the format string spec from the UI model properties.

        if (props != null)
        {
            /*   currencyFormat =
             props.getProperty("CurrencyIfc.DisplayFormat", CURRENCY_FORMAT);
             */
            quantityFormat = props.getProperty("SaleLineItemRenderer.QuantityFormat",
                    DomainGateway.getNumberFormat(getLocale()).toString());

            // ...just use the one our parent provided for us...
            //giftCardLabel =
            //    props.getProperty("GiftCardLabel", "Gift Card ID:");

            salesAssocLabel = props.getProperty("SalesAssociateLabel", "Sales Assoc:");

            giftRegLabel = props.getProperty("GiftRegistryLabel", "GiftReg.#");

            serialLabel = props.getProperty("SerialLabel", "Serial #");

            sendLabel = props.getProperty("SendLabel", "Send");

            kitLabel = props.getProperty("KitLabel", "Kit");

            giftReceiptLabel = props.getProperty("GiftReceiptLabel", "Gift Receipt");

            restockingFeeLabel = props.getProperty("RestockingFeeLabel", "Restocking Fee");

        }
    }

    /**
     *  Returns the Reason Codes from the DB.
     *  @param listKey  the list key
     *  @param code     the code
     *  @return The reason code
     */
    protected String getReasonCodeValue(String listKey, int code)
    {
        CodeListIfc list = null;
        String desc = "";

        if (map != null)
        {
            list = map.get(listKey);

            if (list != null)
            {
                String str = Integer.toString(code);
                CodeEntryIfc clei = list.findListEntryByCode(str);
                if (clei != null)
                {
                    desc = clei.getText(LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE));
                }
            }
        }
        return desc;
    }

    /**
     * check the line item is price required gift card item
     * @param lineItem
     * @return boolean
     */
    protected boolean isEntryByItemID(SaleReturnLineItemIfc lineItem)
    {
        boolean retCode = true;

        if (lineItem.getPLUItem() instanceof GiftCardPLUItemIfc)
        {
            GiftCardPLUItemIfc item = (GiftCardPLUItemIfc)lineItem.getPLUItem();
            GiftCardIfc giftCard = item.getGiftCard();
            if (giftCard.getIssueEntryType() == GiftCardIfc.BY_DENOMINATION)
            {
                retCode = false;
            }
        }
        return retCode;
    }

    /**
     * This method returns a instance of java.awt.Component, which is configured to display
     *
     * the required value. The component.paint() method is called to render the cell.
     *
     * @param JList
     *
     * @param Object
     *
     * @param int
     *
     * @param boolean
     *
     * @param boolean
     *
     * @return Component
     *
     */
    public Component getListCellRendererComponent(JList jList, Object obj, int index, boolean isSelected,
            boolean isCellHasFocus)
    {
        Component returnCellComponent = this;

        // If this line item is a price adjustment, create a JPanel composed of the
        // return and sale components of the price adjustment item.
        if (obj instanceof PriceAdjustmentLineItemIfc)
        {
            PriceAdjustmentLineItemIfc priceAdjustmentLineItem = (PriceAdjustmentLineItemIfc)obj;

            SaleLineItemRenderer returnComponent = new SaleLineItemRenderer();
            returnComponent = (SaleLineItemRenderer)returnComponent.getListCellRendererComponent(jList,
                    priceAdjustmentLineItem.getPriceAdjustReturnItem(), index, isSelected, false);

            SaleLineItemRenderer saleComponent = new SaleLineItemRenderer();
            saleComponent = (SaleLineItemRenderer)saleComponent.getListCellRendererComponent(jList,
                    priceAdjustmentLineItem.getPriceAdjustSaleItem(), index, isSelected, false);

            JPanel priceAdjustmentCell = new JPanel(new BorderLayout());

            // Set the foreground and background colors and borders

            // if the item is selected, use the selected colors
            if (isSelected && jList.isEnabled())
            {
                priceAdjustmentCell.setBackground(jList.getSelectionBackground());
                priceAdjustmentCell.setForeground(jList.getSelectionForeground());
                priceAdjustmentCell.setOpaque(true);
            }
            // otherwise, set the background to the unselected colors
            else
            {
                priceAdjustmentCell.setBackground(jList.getBackground());
                priceAdjustmentCell.setForeground(jList.getForeground());
                priceAdjustmentCell.setOpaque(false);
            }
            // draw the border if the cell has focus
            if (isCellHasFocus)
            {
                priceAdjustmentCell.setBorder(UIManager.getBorder(FOCUS_BORDER));
            }
            else
            {
                priceAdjustmentCell.setBorder(UIManager.getBorder(NO_FOCUS_BORDER));
            }

            // Add the price adjustment components to the panel
            priceAdjustmentCell.add(returnComponent, BorderLayout.NORTH);
            priceAdjustmentCell.add(saleComponent, BorderLayout.SOUTH);

            returnCellComponent = priceAdjustmentCell;

        }
        else
        {
            // set the color of all label foregrounds making sure to call superclass
            // method as well
            super.getListCellRendererComponent(jList, obj, index, isSelected, isCellHasFocus);
            for (int i = 0; i < optionalFields.length; i++)
            {
                optionalFields[i].setForeground(getForeground());
            }
            for (int i = 0; i < optionalSaleLineItemLabels.length; i++)
            {
                optionalSaleLineItemLabels[i].setForeground(getForeground());
            }

            itemLevelScreenMessageLabel.setMaximumSize(new Dimension(getSize().width - leftInset, 100));
            itemLevelScreenMessageLabel.setBackground(jList.getSelectionBackground());
            itemLevelScreenMessageLabel.setOpaque(true);
        }

        return returnCellComponent;
    }

    /**
     *  main entrypoint - starts the part when it is run as an application
     *  @param args String[]
     */
    public static void main(String[] args)
    {
        UIUtilities.setUpTest();
        SaleLineItemRenderer renderer = new SaleLineItemRenderer();
        renderer.setData(renderer.createPrototype());

        UIUtilities.doBeanTest(renderer);
    }

    private String addLineBreaks(String itemLevelMessage, int length)
    {
    	String linebrkItemMessage = null;

    	if(itemLevelMessage != null && !itemLevelMessage.equals(""))
    	{
			StringBuffer lineBrkItemMessage = new StringBuffer();
			if (itemLevelMessage.length() > 0)
			{
	    		List<String> subStringList = new ArrayList<String>();

	    		while (itemLevelMessage.length() > length)
	    		{
	    			String sub = itemLevelMessage.substring(0, length);
	    			int indexOfSpace = sub.lastIndexOf(" ");
	    			if(indexOfSpace == -1)
	    			{
	    				subStringList.add(itemLevelMessage.substring(0, length));
	    				itemLevelMessage = itemLevelMessage.substring(length, itemLevelMessage.length());
	    			}
	    			else
	    			{
	    				subStringList.add(itemLevelMessage.substring(0,indexOfSpace));
	    				itemLevelMessage = itemLevelMessage.substring(indexOfSpace+1,itemLevelMessage.length());
	    			}
	    		}
	    		subStringList.add(itemLevelMessage);

	    		for(int msgctr = 0 ; msgctr < subStringList.size(); msgctr ++ )
	    		{
	    			lineBrkItemMessage.append(subStringList.get(msgctr));
	    			if(msgctr != subStringList.size())
	    			{
	    				lineBrkItemMessage.append(" <br>");
	    			}
	    		}
			}
			linebrkItemMessage = lineBrkItemMessage.toString();
		}
    	else
    	{
    		linebrkItemMessage = itemLevelMessage;
    	}

		return linebrkItemMessage;
	}
    /**
     * If the sales associate text string is too wide to fit within the available
     * space allocated in the work panel, specific number characters and "..."
     * will be displayed instead.
     * @param args associate name text string
     * @param displayLength Specified length of description string to be displayed
     *          in the screen
     * @return {@link String} Truncated description string suffixed with "..."
     */

    private String makeSafeStringForDisplay(String args, int displayLength)
    {
      String clipString = "...";
      args = args.trim();
      if (args.length() > displayLength)
      {
        StringBuffer buffer = new StringBuffer(args.substring(0, displayLength + 1));
        return buffer.append(clipString).toString();
      }
      return args;
    }

}
