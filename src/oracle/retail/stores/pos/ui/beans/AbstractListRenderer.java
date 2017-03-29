/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/AbstractListRenderer.java /rgbustores_13.4x_generic_branch/2 2011/12/12 10:04:56 rsnayak Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    rsnayak   12/09/11 - Added Row highlight color
 *    cgreene   05/28/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *    cgreene   05/01/09 - string pooling performance aenhancements
 *    acadar    02/25/09 - override the getDefaultLocale from JComponent
 *    ddbaker   01/08/09 - Update to layout of item image screens to account
 *                         for I18N clipping issues.
 *    ddbaker   11/10/08 - Updated based on new requirements
 *
 * ===========================================================================
 * $Log:
 *   3    360Commerce 1.2         3/31/2005 4:27:07 PM   Robert Pearse
 *   2    360Commerce 1.1         3/10/2005 10:19:27 AM  Robert Pearse
 *   1    360Commerce 1.0         2/11/2005 12:09:20 PM  Robert Pearse
 *
 *  Revision 1.8  2004/09/14 19:39:59  mweis
 *  @scr 7012 Cleanup POS item inquiry renderers.
 *
 *  Revision 1.7  2004/07/14 19:48:00  crain
 *  @scr 6265 Regression: Column aligment or screen sizes changes between ptrunk-196 and 199
 *
 *  Revision 1.6  2004/07/09 23:12:43  bvanschyndel
 *  @scr 5268 Employee maximum character width too short.
 *
 *  Revision 1.5  2004/04/01 00:11:33  cdb
 *  @scr 4206 Corrected some header foul ups caused by Eclipse auto formatting.
 *
 *  Revision 1.4  2004/03/16 17:15:22  build
 *  Forcing head revision
 *
 *  Revision 1.3  2004/03/16 17:15:16  build
 *  Forcing head revision
 *
 *  Revision 1.2  2004/02/11 20:56:27  rhafernik
 *  @scr 0 Log4J conversion and code cleanup
 *
 *  Revision 1.1.1.1  2004/02/11 01:04:21  cschellenger
 *  updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:09:30   CSchellenger
 * Initial revision.
 *
 *    Rev 1.3   Feb 12 2003 18:52:34   DCobb
 * Added Log tag
 * Added Revision 1.4  2004/03/16 17:15:22  build
 * Added Forcing head revision
 * Added
 * Added Revision 1.3  2004/03/16 17:15:16  build
 * Added Forcing head revision
 * Added
 * Added Revision 1.2  2004/02/11 20:56:27  rhafernik
 * Added @scr 0 Log4J conversion and code cleanup
 * Added
 * Added Revision 1.1.1.1  2004/02/11 01:04:21  cschellenger
 * Added updating to pvcs 360store-current
 * Added
 * Added
 * Resolution for POS SCR-1867: POS 6.0 Floating Till
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.utility.LocaleMap;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.pos.ui.plaf.UIFactory;

/**
 *  This is the Abstract renderer that is used for lists in the POS
 *  application. It lays out label objects in a GridBagLayout, using
 *  a set of provided weights. Specific renderers need to extend from
 *  this class and implement the abstract functions.
 *  @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public abstract class AbstractListRenderer extends JPanel
                                           implements ListCellRenderer
{
    private static final long serialVersionUID = 2968686357829523446L;

    /** revision number supplied by Team Connection */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    /** property prefixes for ui configuration */
    public static String UI_PREFIX = "List.renderer";
    public static String LABEL_PREFIX = UI_PREFIX + ".label";
    public static String FOCUS_BORDER = UI_PREFIX + ".focusBorder";
    public static String NO_FOCUS_BORDER = UI_PREFIX + ".noFocusBorder";

    /** base line width for initial measuring */
    public static int DEFAULT_WIDTH = 450;

    /** the labels that display the renderer data */
    protected JLabel[] labels = null;

    /** first line label weights */
    protected int[] firstLineWeights = null;

    /** second line label weights */
    protected int[] secondLineWeights = null;

    /** first line label weights */
    protected int[] firstLineWidths = null;

    /** second line label weights */
    protected int[] secondLineWidths = null;

    /** first line label heights */
    protected int[]  firstLineHeights  = null;

    /** second line label heights */
    protected int[]  secondLineHeights = null;

    /** the width of a display line */
    protected int lineWidth = DEFAULT_WIDTH;

    /** the height of a display line */
    protected int lineHeight = 0;

    /** the last label on the first display line */
    protected int lineBreak;

    /** the last label on the first display line */
    protected int secondLineBreak;

    /** the number of fields being displayed */
    protected int fieldCount = 0;

    /** The properties object which contains local specific text **/
    protected Properties props = null;

    /** the ui factory for ui components */
    protected UIFactory uiFactory = UIFactory.getInstance();

    /** Highlight Rows in List */
    protected Color altBGColor = null;
    
    /** Highlight Rows in List */
    protected Color altFGColor = null;
    
    /**
     *    Default constructor.
     */
    public AbstractListRenderer()
    {
        super();
        altBGColor = UIManager.getColor("List.altRowBackground");
        altFGColor = UIManager.getColor("List.altRowForeground");
    }

    /**
     *  Initializes this renderer.
     */
    protected void initialize()
    {
        uiFactory.configureUIComponent(this, UI_PREFIX);

        // set up the line width
        if(getWidth() != 0)
        {
            lineWidth = getWidth();
        }
        initLabels();
        initOptions();
        setPropertyFields();
    }

    /**
     *  Initializes this renderer's components and formats the first line.
     */
    protected void initLabels()
    {
        removeAll();
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = uiFactory.getConstraints("Renderer");

        labels = new JLabel[fieldCount];

        int secondCount = 0;
        int labelHeight = lineHeight;
        // create the labels
        for(int i=0; i<fieldCount; i++)
        {
            labels[i] = uiFactory.createLabel("", "", null, LABEL_PREFIX);

            // set the line height if it hasn't already been set
            if(lineHeight == 0)
            {
                lineHeight = labels[i].getFont().getSize() + 4;
            }
            // if the label is on the first line, size it based on
            // weight and do the layout
            if(i <= lineBreak
                    && (firstLineHeights == null || firstLineHeights[i] >= 0))
            {
                if (firstLineHeights != null)
                {
                    labelHeight = firstLineHeights[i] * lineHeight;
                }
                else
                {
                    labelHeight = lineHeight;
                }

                Dimension dim =
                    UIUtilities.sizeFromWeight(lineWidth,
                                               firstLineWeights[i],
                                               labelHeight);

                if(dim != null)
                {
                    labels[i].setPreferredSize(dim);
                }
                constraints.gridy = 0;
                constraints.weightx = firstLineWeights[i] * .01;
                if (firstLineWidths != null)
                {
                    constraints.gridwidth = firstLineWidths[i];
                }
                if (firstLineHeights != null)
                {
                    constraints.gridheight = firstLineHeights[i];
                }
                add(labels[i], constraints);
                constraints.gridwidth = 1;
                constraints.gridheight = 1;
            }
            secondCount = i - lineBreak - 1;
            if(i > lineBreak && i <= secondLineBreak
                    && (secondLineHeights == null || secondLineHeights[secondCount] >= 0))
            {
                Dimension dim =
                    UIUtilities.sizeFromWeight(lineWidth,
                                               secondLineWeights[secondCount],
                                               lineHeight);

                if(dim != null)
                {
                    labels[i].setPreferredSize(dim);
                }
                constraints.gridy = 1;
                constraints.weightx = secondLineWeights[secondCount] * .01;
                if (secondLineWidths != null)
                {
                    constraints.gridwidth = secondLineWidths[secondCount];
                }
                if (secondLineHeights != null)
                {
                    constraints.gridheight = secondLineHeights[secondCount];
                }
                add(labels[i], constraints);
                constraints.gridwidth = 1;
                constraints.gridheight = 1;
            }
        }
    }

    //--------------------------------------------------------------------------
    /**
     *  Initializes optional components.
     */
    protected void initOptions()
    {
    }

    //--------------------------------------------------------------------------
    /**
     *    @see javax.swing.ListCellRenderer
     */
    public Component getListCellRendererComponent(JList list,
                                                  Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus)
    {
        // if the item is selected, use the selected colors
        if (isSelected && list.isEnabled())
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            setOpaque(true);
        }
        // otherwise, set the background to the unselected colors
        else
        {
            Color bgColor = list.getBackground();
            Color fgColor = list.getForeground();
            boolean isAltRow = (index % 2 == 1);

            setBackground(isAltRow ? altBGColor : bgColor);
            setForeground(isAltRow ? altFGColor : fgColor);
            setOpaque(isAltRow);
        }
        // set the color of the label foregrounds
        for(int i=0; i<labels.length; i++)
        {
            labels[i].setForeground(getForeground());
        }
        // draw the border if the cell has focus
        if(cellHasFocus)
        {
            setBorder(UIManager.getBorder(FOCUS_BORDER));
        }
        else
        {
            setBorder(UIManager.getBorder(NO_FOCUS_BORDER));
        }

        setData(value);

        return this;
    }

    //--------------------------------------------------------------------------
    /**
     *  Set the weights that layout the first display line.
     *    @param prefix the property prefix
     */
    public void setFirstLineWeights(String prefix)
    {
        // create the weight array from string property
        String weightString = UIManager.getString(prefix);
        int[] w = UIUtilities.getIntArrayFromString(weightString);

        // if the array is not null, set the first line weights
        if(w != null)
        {
            firstLineWeights = w;
        }
    }

    //--------------------------------------------------------------------------
    /**
     *  Set the weights that layout the second display line.
     *    @param prefix the property prefix
     */
    public void setSecondLineWeights(String prefix)
    {
        // create the weight array from string property
        String weightString = UIManager.getString(prefix);
        int[] w = UIUtilities.getIntArrayFromString(weightString);

        // if the array is not null, set the first line weights
        if(w != null)
        {
            secondLineWeights = w;
        }
    }

    //--------------------------------------------------------------------------
    /**
     *  Set the weights that layout the first display line.
     *    @param prefix the property prefix
     */
    public void setFirstLineWidths(String prefix)
    {
        // create the weight array from string property
        String weightString = UIManager.getString(prefix);
        int[] w = UIUtilities.getIntArrayFromString(weightString);

        // if the array is not null, set the first line weights
        if(w != null)
        {
            firstLineWidths = w;
        }
    }

    //--------------------------------------------------------------------------
    /**
     *  Set the weights that layout the second display line.
     *    @param prefix the property prefix
     */
    public void setSecondLineWidths(String prefix)
    {
        // create the weight array from string property
        String weightString = UIManager.getString(prefix);
        int[] w = UIUtilities.getIntArrayFromString(weightString);

        // if the array is not null, set the first line weights
        if(w != null)
        {
            secondLineWidths = w;
        }
    }

    //--------------------------------------------------------------------------
    /**
     *  Set the heights that layout the first display line.
     *    @param prefix the property prefix
     */
    public void setFirstLineHeights(String prefix)
    {
        // create the weight array from string property
        String heightString = UIManager.getString(prefix);
        int[] h = UIUtilities.getIntArrayFromString(heightString);

        // if the array is not null, set the first line weights
        if(h != null)
        {
            firstLineHeights = h;
        }
    }

    //--------------------------------------------------------------------------
    /**
     *  Set the heights that layout the second display line.
     *    @param prefix the property prefix
     */
    public void setSecondLineHeights(String prefix)
    {
        // create the weight array from string property
        String heightString = UIManager.getString(prefix);
        int[] h = UIUtilities.getIntArrayFromString(heightString);

        // if the array is not null, set the first line weights
        if(h != null)
        {
            secondLineHeights = h;
        }
    }

    //--------------------------------------------------------------------------
    /**
     *  Set the properties to be used by this bean.
     *    @param props the propeties object
     */
    public void setProps(Properties props)
    {
        this.props = props;
        setPropertyFields();
    }



    //--------------------------------------------------------------------------
    /**
     *  Update the fields based on the properties. Subclasses must implement
     *    this method for specific property values.
     */
    protected abstract void setPropertyFields();

    //--------------------------------------------------------------------------
    /**
     *    Applies data to the visual components in the renderer. Subclasses
     *    must implement this method for specific data objects.
     *
     *    @param data the data object to render
     */
    public abstract void setData(Object data);

    //--------------------------------------------------------------------------
    /**
     *  Gets the locale for the user interface subsystem
     *  properties object.
     *  @returns locale for the user interface subsystem
     */
    public Locale getLocale()
    {
        return (LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE));
    }

    /**
     * Gets the default application locale
     * This is to override the  JComponenent getDefaultLocale() which returns the jvm's locale
     */
    public static Locale getDefaultLocale()
    {
        return (LocaleMap.getLocale(LocaleMap.DEFAULT));
    }

    //--------------------------------------------------------------------------
    /**
     *    Creates a prototype data object used to size the renderer. If
     *    the renderer does not have a variable height (optional display
     *    lines), assigning a prototype will make rendering more efficient.
     *    Objects returned by this method should have all values set to their
     *    maximum length.
     *
     *    @return a populated data object
     */
    public abstract Object createPrototype();

}

