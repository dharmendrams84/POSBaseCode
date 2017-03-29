/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/ItemInfoBean.java /main/28 2011/03/02 18:29:11 ohorne Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    ohorne 02/22/11 - ItemNumber can be ItemID or PosItemID
 *    nkgaut 10/15/10 - forward port : pos hangs with the change of some of the
 *                      item parameter values
 *    acadar 06/10/10 - use default locale for currency display
 *    acadar 06/09/10 - XbranchMerge acadar_tech30 from
 *                      st_rgbustores_techissueseatel_generic_branch
 *    cgreen 05/28/10 - convert to oracle packaging
 *    cgreen 05/27/10 - convert to oracle packaging
 *    cgreen 05/27/10 - convert to oracle packaging
 *    cgreen 05/26/10 - convert to oracle packaging
 *    abonda 01/03/10 - update header date
 *    cgreen 09/03/09 - XbranchMerge cgreene_bug8394467-timer from
 *                      rgbustores_13.1x_branch
 *    cgreen 09/03/09 - correct mem leak in Time object and refactored this
 *                      class to use a SwingWorker instead
 *    mchell 04/15/09 - Resize the item image
 *    vikini 04/08/09 - Merged changes after refreshed view to tip
 *    vikini 04/08/09 - refectored the code for image loading
 *    vikini 04/07/09 - added code to load image from file
 *    cgreen 04/06/09 - mark bean as invalid before updating all its widget's
 *                      text and use revalidate to let Swing EDT validate the
 *                      contents for performance reasons
 *    cgreen 03/30/09 - removed item name column from item image table
 *    vikini 03/18/09 - Changed the Item Image loading process
 *    nkgaut 12/30/08 - Fix for item images
 *    nkgaut 12/02/08 - Changes to include ILRM on Item Info Screen
 *    atirke 10/23/08 - changes for error logging
 *    atirke 10/01/08 - added image path
 *    atirke 10/01/08 - modified for item images
 *    atirke 09/29/08 - added logic to display images
 * ===========================================================================
 $Log:
 8    360Commerce 1.7         5/15/2008 5:54:52 AM   Neeraj Gautam
 Clipped description to required length
 7    360Commerce 1.6         10/11/2007 4:33:23 PM  Leona R. Slepetis
 update labels from resource bundle
 6    360Commerce 1.5         7/11/2007 11:07:31 AM  Anda D. Cadar
 removed ISO currency code when using base currency
 5    360Commerce 1.4         1/22/2006 11:45:25 AM  Ron W. Haight
 removed references to com.ibm.math.BigDecimal
 4    360Commerce 1.3         12/13/2005 4:42:45 PM  Barry A. Pape
 Base-lining of 7.1_LA
 3    360Commerce 1.2         3/31/2005 4:28:31 PM   Robert Pearse
 2    360Commerce 1.1         3/10/2005 10:22:26 AM  Robert Pearse
 1    360Commerce 1.0         2/11/2005 12:11:37 PM  Robert Pearse
 $
 Revision 1.5  2004/04/22 17:36:37  lzhao
 @scr 4383: show/hide size info based on parameter setting.

 Revision 1.4  2004/04/12 15:35:34  lzhao
 @scr 3840: show size label.

 Revision 1.3  2004/03/16 17:15:17  build
 Forcing head revision

 Revision 1.2  2004/02/11 20:56:26  rhafernik
 @scr 0 Log4J conversion and code cleanup

 Revision 1.1.1.1  2004/02/11 01:04:22  cschellenger
 updating to pvcs 360store-current


 *
 *    Rev 1.1   Dec 17 2003 11:21:44   baa
 * return enhancements
 * Resolution for 3561: Feature Enhacement: Return Search by Tender
 *
 *    Rev 1.0   Aug 29 2003 16:10:48   CSchellenger
 * Initial revision.
 *
 *    Rev 1.4   Mar 10 2003 09:06:00   baa
 * code review changes for I18n
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.3   Oct 08 2002 09:14:10   RSachdeva
 * Code Conversion
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.2   Aug 14 2002 18:17:48   baa
 * format currency
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.1   21 May 2002 17:33:32   baa
 * ils
 * Resolution for POS SCR-1624: Localization Support
 *
 *    Rev 1.1   07 May 2002 22:49:48   baa
 * ils
 * Resolution for POS SCR-1624: Spanish translation
 *
 *    Rev 1.0   Mar 18 2002 11:55:32   msg
 * Initial revision.
 *
 *    Rev 1.3   Mar 05 2002 19:34:30   mpm
 * Text externalization for inquiry UI artifacts.
 * Resolution for POS SCR-351: Internationalization
 * ===========================================================================
 */

package oracle.retail.stores.pos.ui.beans;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import oracle.retail.stores.gui.utility.SwingWorker;

import org.apache.log4j.Logger;

import oracle.retail.stores.domain.stock.ItemImageIfc;
import oracle.retail.stores.foundation.manager.gui.UIModelIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;
import oracle.retail.stores.pos.ui.UIUtilities;

/**
 * This class shows information for a single item.
 * 
 * @version $Revision: /main/28 $
 */
public class ItemInfoBean extends BaseBeanAdapter
{
    private static final long serialVersionUID = -4044972339909865770L;

    protected static final Logger logger = Logger.getLogger(ItemInfoBean.class);

    /**
     * Revision Number furnished by TeamConnection.
     */
    public static final String revisionNumber = "$Revision: /main/28 $";

    public static String YES = null;
    public static String NO = null;

    // label and field placeholder constants
    public static final int ITEM_NUMBER = 0;
    public static final int DESCRIPTION = 1;
    public static final int MANUFACTURER = 2;
    public static final int DEPARTMENT = 3;
    public static final int PRICE = 4;
    public static final int SIZE = 5;
    public static final int MEASURE = 6;
    public static final int TAX = 7;
    public static final int DISCOUNT = 8;
    public static final int PLANOGRAMID = 9;
    public static final int ITEM_LEVEL_MESSAGE = 10;
    public static final int IMAGE = 11;

    public static final int MAX_FIELDS = 12;

    public static final int IMAGE_MAX_HEIGHT = 200;
    public static final int IMAGE_MAX_WIDTH = 250;

    // Display Length of Item Description in the item inquiry screen
    public static final int MAX_ITM_DESC_DISPLAY_LENGTH = 16;

    /** Number of characters in display line before inserting a line break. */
    protected static final int MAX_CHARS_BEFORE_LINE_BREAK = 35;

    public static final String[] labelText = { "Item Number:", "Description:", "Manufacturer:", "Department:",
            "Price:", "Size:", "Unit Of Measure:", "Taxable:", "Discountable:", "Planogram ID:", "Item Messages:", "" };

    public static final String[] labelTags = { "ItemIDLabel", "DescriptionLabel", "ManufacturerLabel",
            "DepartmentLabel", "PriceLabel", "PriceInquiryItemSizeLabel", "UnitOfMeasureLabel", "TaxableLabel",
            "DiscountableLabel", "PlanogramIDLabel", "Item Messages:", "" };

    public static final String[] labelTextNoSizePlanogram = { "Item Number:", "Description:", "Manufacturer:",
            "Department:", "Price:", "Unit Of Measure:", "Taxable:", "Discountable:", "Planogram ID:",
            "Item Messages:", "" };

    public static final String[] labelTagsNoSizePlanogram = { "ItemIDLabel", "DescriptionLabel",
            "ManufacturerLabel", "DepartmentLabel", "PriceLabel", "UnitOfMeasureLabel", "TaxableLabel",
            "DiscountableLabel", "PlanogramIDLabel", "Item Messages:", "" };

    public static final String[] labelTextSizeNoPlanogram = { "Item Number:", "Description:", "Manufacturer:",
            "Department:", "Price:", "Size:", "Unit Of Measure:", "Taxable:", "Discountable:", "Item Messages:", "" };

    public static final String[] labelTagsSizeNoPlanogram = { "ItemIDLabel", "DescriptionLabel",
            "ManufacturerLabel", "DepartmentLabel", "PriceLabel", "PriceInquiryItemSizeLabel", "UnitOfMeasureLabel",
            "TaxableLabel", "DiscountableLabel", "Item Messages:", "" };

    public static final String[] labelTextNoSizeNoPlanogram = { "Item Number:", "Description:", "Manufacturer:",
            "Department:", "Price:", "Unit Of Measure:", "Taxable:", "Discountable:", "Item Messages:", "" };

    public static final String[] labelTagsNoSizeNoPlanogram = { "ItemIDLabel", "DescriptionLabel",
            "ManufacturerLabel", "DepartmentLabel", "PriceLabel", "UnitOfMeasureLabel", "TaxableLabel",
            "DiscountableLabel", "Item Messages:", "" };

    /** array of labels */
    protected JLabel[] labels = null;

    /** array of display fields */
    protected JLabel[] fields = null;

    /** the bean model */
    protected ItemInfoBeanModel beanModel = null;

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.ui.beans.BaseBeanAdapter#activate()
     */
    @Override
    public void activate()
    {
        loadImage(beanModel);
    }

    /**
     * Configure the class.
     */
    @Override
    public void configure()
    {
        setName("ItemInfoBean");
        uiFactory.configureUIComponent(this, UI_PREFIX);
    }

    /**
     * Initialize the display components.
     */
    protected void initComponents(int maxFields, String[] label)
    {
        labels = new JLabel[maxFields];
        fields = new JLabel[maxFields];

        for (int i = 0; i < maxFields; i++)
        {
            labels[i] = uiFactory.createLabel(label[i] + "label", label[i], null, UI_LABEL);
            fields[i] = uiFactory.createLabel(label[i] + "field", "", null, UI_LABEL);
            if (i == MANUFACTURER || i == PLANOGRAMID)
            {
                labels[i].setVisible(false);
                fields[i].setVisible(false);
            }
        }
    }

    /**
     * Layout the components.
     */
    public void initLayout()
    {
        UIUtilities.layoutDataPanel(this, labels, fields);
    }

    /**
     * Sets the information to be shown by this bean.
     *
     * @param model the model to be shown. The runtime type should be
     *            ItemInfoBeanModel
     */
    @Override
    public void setModel(UIModelIfc model)
    {
        if (model == null)
        {
            throw new NullPointerException("Attempt to set ItemInfoBean " + "model to null");
        }
        if (model instanceof ItemInfoBeanModel)
        {
            beanModel = (ItemInfoBeanModel)model;
            updateBean();
        }
    }

    /**
     * Update the bean if It's been changed. Changes all text then calls
     * {@link #setupLayout()}.
     */
    @Override
    protected void updateBean()
    {
        // re-add everything
        setupLayout();
        // mark as not needed validation up to parent
        invalidate();

        fields[ITEM_NUMBER].setText(beanModel.getItemNumber());
        fields[DESCRIPTION].setText(makeSafeStringForDisplay(beanModel.getItemDescription(),
                MAX_ITM_DESC_DISPLAY_LENGTH));
        fields[DEPARTMENT].setText(beanModel.getItemDept());
        // I18N change: remoe ISO currency code from base currency
        fields[PRICE].setText(getCurrencyService().formatCurrency(beanModel.getPrice(), getDefaultLocale()));
        // ILRM CR : Display Item Level Information if present
        String itemLevelMessage = addLineBreaks(beanModel.getItemLevelMessage(), MAX_CHARS_BEFORE_LINE_BREAK);
        StringBuilder screenMessage = new StringBuilder();
        screenMessage.append("<html><b><body>").append(itemLevelMessage).append("</body></b></html>");

        if(beanModel.isItemSizeRequired() && beanModel.isUsePlanogramID())
        {
            fields[ITEM_LEVEL_MESSAGE].setText(screenMessage.toString());
            if (itemLevelMessage.equals(""))
            {
                labels[ITEM_LEVEL_MESSAGE].setVisible(false);
            }
            else
            {
                labels[ITEM_LEVEL_MESSAGE].setVisible(true);
            }
            fields[IMAGE].setIcon(getLoadingImage());
        }
        else if(beanModel.isItemSizeRequired() || beanModel.isUsePlanogramID())
        {
            fields[ITEM_LEVEL_MESSAGE - 1].setText(screenMessage.toString());
            if (itemLevelMessage.equals(""))
            {
                labels[ITEM_LEVEL_MESSAGE - 1].setVisible(false);
            }
            else
            {
                labels[ITEM_LEVEL_MESSAGE - 1].setVisible(true);
            }
            fields[IMAGE - 1].setIcon(getLoadingImage());
        }
        else
        {
            fields[ITEM_LEVEL_MESSAGE - 2].setText(screenMessage.toString());
            if (itemLevelMessage.equals(""))
            {
                labels[ITEM_LEVEL_MESSAGE - 2].setVisible(false);
            }
            else
            {
                labels[ITEM_LEVEL_MESSAGE - 2].setVisible(true);
            }
            fields[IMAGE - 2].setIcon(getLoadingImage());
        }


        // Added to display manufacturer
        if (beanModel.isSearchItemByManufacturer())
        {
            fields[MANUFACTURER].setText(beanModel.getItemManufacturer());
            fields[MANUFACTURER].setVisible(true);
            labels[MANUFACTURER].setVisible(true);
        }
        else
        {
            fields[MANUFACTURER].setVisible(false);
            labels[MANUFACTURER].setVisible(false);
        }

        if (beanModel.isItemSizeRequired())
        {
            if (beanModel.getItemSize() != null)
            {
                fields[SIZE].setText(beanModel.getItemSize());
            }
            fields[MEASURE].setText(beanModel.getUnitOfMeasure());

            if (beanModel.isTaxable())
                fields[TAX].setText(YES);
            else
                fields[TAX].setText(NO);

            if (beanModel.isDiscountable())
                fields[DISCOUNT].setText(YES);
            else
                fields[DISCOUNT].setText(NO);

            if (beanModel.isUsePlanogramID())
            {
                if (beanModel.getPlanogramID() != null)
                {
                    String[] planogram = beanModel.getPlanogramID();
                    int index = planogram.length;
                    if (index > 0)
                    {
                        StringBuffer sbDispPlanogram = new StringBuffer("<html>");

                        int i = 0;
                        for (i = 0; i < index; i++)
                        {
                            sbDispPlanogram.append(planogram[i] + "<p>");
                        }
                        sbDispPlanogram.append("</html>");
                        String dispPlanogram = sbDispPlanogram.toString();

                        fields[PLANOGRAMID].setText(dispPlanogram);
                        fields[PLANOGRAMID].setVisible(true);
                        labels[PLANOGRAMID].setVisible(true);
                    }
                    else
                    {
                        fields[PLANOGRAMID].setText("");
                        fields[PLANOGRAMID].setVisible(true);
                        labels[PLANOGRAMID].setVisible(true);
                    }
                }
                else
                {
                    fields[PLANOGRAMID].setText("");
                    fields[PLANOGRAMID].setVisible(true);
                    labels[PLANOGRAMID].setVisible(true);
                }

                for (int i = 0; i < labelText.length; i++)
                {
                    labels[i].setText(retrieveText(labelTags[i], labelText[i]));
                }
            }
            else
            {
                for (int i = 0; i < labelTextSizeNoPlanogram.length; i++)
                {
                    labels[i].setText(retrieveText(labelTagsSizeNoPlanogram[i], labelTextSizeNoPlanogram[i]));
                }
            }

        }
        else
        {
            fields[MEASURE - 1].setText(beanModel.getUnitOfMeasure());

            if (beanModel.isTaxable())
                fields[TAX - 1].setText(YES);
            else
                fields[TAX - 1].setText(NO);

            if (beanModel.isDiscountable())
                fields[DISCOUNT - 1].setText(YES);
            else
                fields[DISCOUNT - 1].setText(NO);
            if (beanModel.isUsePlanogramID())
            {
                if (beanModel.getPlanogramID() != null)
                {
                    String[] planogram = beanModel.getPlanogramID();
                    int index = planogram.length;
                    if (index > 0)
                    {
                        StringBuffer sbDispPlanogram = new StringBuffer("<html>");

                        int i = 0;
                        for (i = 0; i < index; i++)
                        {
                            sbDispPlanogram.append(planogram[i] + "<p>");
                        }
                        sbDispPlanogram.append("</html>");
                        String dispPlanogram = sbDispPlanogram.toString();

                        fields[PLANOGRAMID - 1].setText(dispPlanogram);
                        fields[PLANOGRAMID - 1].setVisible(true);
                        labels[PLANOGRAMID - 1].setVisible(true);
                    }
                    else
                    {
                        fields[PLANOGRAMID - 1].setText("");
                        fields[PLANOGRAMID - 1].setVisible(true);
                        labels[PLANOGRAMID - 1].setVisible(true);
                    }
                }
                else
                {
                    fields[PLANOGRAMID - 1].setText("");
                    fields[PLANOGRAMID - 1].setVisible(true);
                    labels[PLANOGRAMID - 1].setVisible(true);
                }

                for (int i = 0; i < labelTextNoSizePlanogram.length; i++)
                {
                    labels[i].setText(retrieveText(labelTagsNoSizePlanogram[i], labelTextNoSizePlanogram[i]));
                }
            }
            else
            {
                for (int i = 0; i < labelTextNoSizeNoPlanogram.length; i++)
                {
                    labels[i].setText(retrieveText(labelTagsNoSizeNoPlanogram[i], labelTextNoSizeNoPlanogram[i]));
                }
            }
        }

        revalidate();
    }

    /**
     * If the item description text string is too wide to fit within the
     * available space allocated in the work panel, specific number characters
     * and "..." will be displayed instead.
     *
     * @param args Item description text string
     * @param displayLength Specified length of description string to be
     *            displayed in the screen
     * @return {@link String} Truncated description string suffixed with "..."
     */
    private String makeSafeStringForDisplay(String args, int displayLength)
    {
        String clipString = "...";
        args = args.trim();
        if (args.length() > displayLength)
        {
            StringBuffer buffer = new StringBuffer(args.substring(0, displayLength));
            return buffer.append(clipString).toString();
        }
        return args;
    }

    /**
     * Updates property-based fields.
     */
    @Override
    protected void updatePropertyFields()
    {
        if (beanModel != null)
        {
            if (beanModel.isItemSizeRequired())
            {
                if (beanModel.isUsePlanogramID())
                {
                    for (int i = 0; i < labelText.length; i++)
                    {
                        labels[i].setText(retrieveText(labelTags[i], labelText[i]));
                    }
                }
                else
                {
                    for (int i = 0; i < labelTextSizeNoPlanogram.length; i++)
                    {
                        labels[i].setText(retrieveText(labelTagsSizeNoPlanogram[i], labelTextSizeNoPlanogram[i]));
                    }
                }
            }
            else
            {
                if (beanModel.isUsePlanogramID())
                {
                    for (int i = 0; i < labelTextNoSizePlanogram.length; i++)
                    {
                        labels[i].setText(retrieveText(labelTagsNoSizePlanogram[i], labelTextNoSizePlanogram[i]));
                    }
                }
                else
                {
                    for (int i = 0; i < labelTextNoSizeNoPlanogram.length; i++)
                    {
                        labels[i].setText(retrieveText(labelTagsNoSizeNoPlanogram[i], labelTextNoSizeNoPlanogram[i]));
                    }
                }
            }
        }

        YES = retrieveText("YesItemLabel", "Yes");
        NO = retrieveText("NoItemLabel", "No");
    }

    /**
     * Re-do the layout by removing all widgets, re-initializing them and
     * triggering the layout manager. Calls {@link #revalidate()} afterwards.
     * <p>
     * The layout will change based on the size and planogram parameter
     * specified. The parameter can be changed at runtime.
     *
     * @see #initLayout()
     * @see #initComponents(int, String[])
     */
    protected void setupLayout()
    {
        // lay out data panel
        removeAll();
        if (beanModel == null)
        {
            initComponents(MAX_FIELDS, labelText);
        }
        else
        {
            if (beanModel.isItemSizeRequired())
            {
                if (beanModel.isUsePlanogramID())
                {
                    initComponents(MAX_FIELDS, labelText);
                }
                else
                {
                    initComponents(MAX_FIELDS - 1, labelTextSizeNoPlanogram);
                }
            }
            else
            {
                if (beanModel.isUsePlanogramID())
                {
                    initComponents(MAX_FIELDS - 1, labelTextNoSizePlanogram);
                }
                else
                {
                    initComponents(MAX_FIELDS - 2, labelTextNoSizeNoPlanogram);
                }

            }
        }
        initLayout();
        revalidate();
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#toString()
     */
    @Override
    public String toString()
    {
        String strResult = new String("Class: ItemInfoBean (Revision " + getRevisionNumber() + ") @" + hashCode());
        return (strResult);
    }

    /**
     * Retrieves the Team Connection revision number.
     *
     * @return String representation of revision number
     */
    public String getRevisionNumber()
    {
        return (Util.parseRevisionNumber(revisionNumber));
    }

    /**
     * main entrypoint - starts the part when it is run as an application
     *
     * @param args command line arguments. None are needed.
     */
    public static void main(String[] args)
    {
        UIUtilities.setUpTest();

        ItemInfoBeanModel model = new ItemInfoBeanModel();
        model.setItemDescription("Chess Set");
        model.setItemNumber("20020012");
        model.setPrice(new BigDecimal(49.99));

        ItemInfoBean bean = new ItemInfoBean();
        bean.setModel(model);

        UIUtilities.doBeanTest(bean);
    }

    /**
     * Adds line breaks to the given string so the tokens don't exceed the
     * specified length.
     *
     * @param itemLevelMessage
     * @param length
     * @return
     */
    protected String addLineBreaks(String itemLevelMessage, int length)
    {
        String linebrkItemMessage = null;

        if (itemLevelMessage != null && !itemLevelMessage.equals(""))
        {
            StringBuffer lineBrkItemMessage = new StringBuffer();
            if (itemLevelMessage.length() > 0)
            {
                List<String> subStringList = new ArrayList<String>();

                while (itemLevelMessage.length() > length)
                {
                    String sub = itemLevelMessage.substring(0, length);
                    int indexOfSpace = sub.lastIndexOf(" ");
                    if (indexOfSpace == -1)
                    {
                        subStringList.add(itemLevelMessage.substring(0, length));
                        itemLevelMessage = itemLevelMessage.substring(length, itemLevelMessage.length());
                    }
                    else
                    {
                        subStringList.add(itemLevelMessage.substring(0, indexOfSpace));
                        itemLevelMessage = itemLevelMessage.substring(indexOfSpace + 1, itemLevelMessage.length());
                    }
                }
                subStringList.add(itemLevelMessage);

                for (int msgctr = 0; msgctr < subStringList.size(); msgctr++)
                {
                    lineBrkItemMessage.append(subStringList.get(msgctr));
                    if (msgctr != subStringList.size())
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
     * Load the specified item image into the model by using a {@link SwingWorker}
     *
     * @param beanModel
     */
    protected void loadImage(final ItemInfoBeanModel beanModel)
    {
        beanModel.setLoadingImage(true);
        // constructing the worker starts it
        new SwingWorker(beanModel.toString())
        {
            @Override
            public Object construct()
            {
                Image image = null;
                String location = beanModel.getImageLocation();
                Dimension imageSize = getImageMaximumSize();

                try
                {
                    // set blob
                    if (beanModel.isBlobImage())
                    {
                        image = Toolkit.getDefaultToolkit().createImage(beanModel.getImageBlob());
                        image = image.getScaledInstance(imageSize.width, imageSize.width, Image.SCALE_FAST);
                    }

                    // set url
                    else if (!Util.isEmpty(location))
                    {
                        URL url = new URL(location);
                        // using ImageIO lets us catch exceptions, but Toolkit would have cached image for us
                        image = ImageIO.read(url);//Toolkit.getDefaultToolkit().getImage(url);
                        image = image.getScaledInstance(imageSize.width, imageSize.width, Image.SCALE_FAST);
                    }

                    // if neither of blob, file or url the set null
                    else
                    {
                        beanModel.setEmptyImage(true);
                    }

                    if (image != null)
                    {
                        beanModel.setImage(new ImageIcon(image));
                    }
                }
                catch (Exception ex)
                {
                    logger.warn("Unable to load item image for " + beanModel.getItemNumber(), ex);
                    beanModel.setImageError(true);
                }
                finally
                {
                    // since we have the image now
                    beanModel.setLoadingImage(false);

                    displayImage(beanModel);

                    repaint();
                }
                return beanModel;
            }
        };
    }

    /**
     * Return the maximum width and height for displaying the item image.
     *
     * @return
     */
    protected Dimension getImageMaximumSize()
    {
        return new Dimension(IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT);
    }

    /**
     * Create the loading animated gif with the parent of this renderer as its
     * observer. This renderer can't be the observer because its not permanently
     * in the container hierarchy.
     *
     * @return
     */
    protected ImageIcon getLoadingImage()
    {
        ImageIcon buzy = new ImageIcon(ItemInfoBean.class.getResource(ItemImageIfc.BUSY_LOADING_IMAGE));
        buzy.setImageObserver(this);
        return buzy;
    }

    /**
     * Display the image info into the UI.
     *
     * @param beanModel
     */
    protected void displayImage(ItemInfoBeanModel beanModel)
    {
        if (beanModel.getImage() != null)
        {
            if(beanModel.isItemSizeRequired() && beanModel.isUsePlanogramID())
            {
                fields[IMAGE].setIcon(beanModel.getImage());
            }
            else if(beanModel.isItemSizeRequired() || beanModel.isUsePlanogramID())
            {
                fields[IMAGE - 1].setIcon(beanModel.getImage());
            }
            else
            {
                fields[IMAGE - 2].setIcon(beanModel.getImage());
            }
        }
        else
        {
            if(beanModel.isItemSizeRequired() && beanModel.isUsePlanogramID())
            {
                fields[IMAGE].setIcon(null);
            }
            else if(beanModel.isItemSizeRequired() || beanModel.isUsePlanogramID())
            {
                fields[IMAGE - 1].setIcon(null);
            }
            else
            {
                fields[IMAGE - 2].setIcon(null);
            }
        }

        if (beanModel.isImageError())
        {
            if(beanModel.isItemSizeRequired() && beanModel.isUsePlanogramID())
            {
                fields[IMAGE].setIcon(null);
            }
            else if(beanModel.isItemSizeRequired() || beanModel.isUsePlanogramID())
            {
                fields[IMAGE - 1].setIcon(null);
            }
            else
            {
                fields[IMAGE - 2].setIcon(null);
            }
            String message = UIUtilities.retrieveText("ItemLocationSpec", BundleConstantsIfc.POS_BUNDLE_NAME,
                    "ErrorMessageLabel", "Error");
            if(beanModel.isItemSizeRequired() && beanModel.isUsePlanogramID())
            {
                fields[IMAGE].setText(message);
            }
            else if(beanModel.isItemSizeRequired() || beanModel.isUsePlanogramID())
            {
                fields[IMAGE - 1].setText(message);
            }
            else
            {
                fields[IMAGE - 2].setText(message);
            }
        }
    }
}
