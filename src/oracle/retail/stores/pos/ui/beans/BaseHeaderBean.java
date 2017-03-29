/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/BaseHeaderBean.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:58 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/28/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *
 * ===========================================================================
 * $Log:
 4    360Commerce 1.3         1/25/2006 4:10:50 PM   Brett J. Larsen merge
 *    7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
 3    360Commerce 1.2         3/31/2005 4:27:16 PM   Robert Pearse   
 2    360Commerce 1.1         3/10/2005 10:19:47 AM  Robert Pearse   
 1    360Commerce 1.0         2/11/2005 12:09:34 PM  Robert Pearse   
 *:
 4    .v700     1.2.1.0     9/13/2005 15:37:43     Jason L. DeLeau Ifan
 *    id_itm_pos maps to multiple id_itms, let the user choose which one to
 *    use.
 3    360Commerce1.2         3/31/2005 15:27:16     Robert Pearse
 2    360Commerce1.1         3/10/2005 10:19:47     Robert Pearse
 1    360Commerce1.0         2/11/2005 12:09:34     Robert Pearse
 *
Revision 1.4  2004/03/16 17:15:22  build
Forcing head revision
 *
Revision 1.3  2004/03/16 17:15:16  build
Forcing head revision
 *
Revision 1.2  2004/02/11 20:56:26  rhafernik
@scr 0 Log4J conversion and code cleanup
 *
Revision 1.1.1.1  2004/02/11 01:04:21  cschellenger
updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:09:36   CSchellenger
 * Initial revision.
 *
 *    Rev 1.0   Apr 29 2002 14:47:44   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:52:18   msg
 * Initial revision.
 *
 *    Rev 1.4   Feb 28 2002 19:21:04   mpm
 * Internationalization
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.3   Feb 25 2002 10:51:14   mpm
 * Internationalization
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.2   Feb 23 2002 15:04:10   mpm
 * Re-started internationalization initiative.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.1   30 Jan 2002 16:42:36   baa
 * ui fixes
 * Resolution for POS SCR-965: Add Customer screen UI defects
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

// Java imports
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JPanel;

import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.pos.ui.plaf.UIFactory;

//------------------------------------------------------------------------------
/**
 *  A configurable panel of text labels. This is used mainly for
 *    list headers.
 *  @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
//------------------------------------------------------------------------------
public class BaseHeaderBean extends JPanel
{
    /** revision number **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    public static int DEFAULT_HEIGHT = 19;
    public static int DEFAULT_WIDTH = 300;

    public String UI_PREFIX = "List.header";
    public String UI_LABEL  = UI_PREFIX + ".label";

    /** an array of label alignments */
    protected int[] alignments = null;

    /** an array of labels */
    protected JLabel[] labels = null;

    /** an array of text for the labels */
    protected String[] labelText = null;

    /** an array of property tags for the labels */
    protected String[] labelTags = null;

    /** an array of weights for the labels */
    protected int[] labelWeights = null;

    /** the width of the header */
    protected int headerWidth = DEFAULT_WIDTH;

    /** the height of a label (default is 19)*/
    protected int labelHeight = DEFAULT_HEIGHT;

    /** The properties object which contains local specific text */
    protected Properties props = null;

    protected UIFactory uiFactory = UIFactory.getInstance();

    //--------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public BaseHeaderBean()
    {
        super();
        // configure this bean from the look and feel defaults
        uiFactory.configureUIComponent(this, UI_PREFIX);
    }

    //--------------------------------------------------------------------------
    /**
     *  Activates this bean.
     */
    public void activate()
    {
        // the labels aren't usually set on instantiation,
        // so we initialize here.
        initialize();
        //setVisible(true);
    }

    //--------------------------------------------------------------------------
    /**
     * Initialize this class.
     */
    protected void initialize()
    {
        // set up the header width and label height
        int width = getWidth();

        if(width != 0)
        {
            headerWidth = width;
        }
        initComponents();
    }

    //--------------------------------------------------------------------------
    /**
     *     Initialize the components in this bean. This loops through the
     *    array of label text and creates a label for each one.
     */
    protected void initComponents()
    {
        // if we have label text, create the labels
        if(labelText != null && labelText.length > 0)
        {
            // flag that determines if labels need recreating
            boolean remake =
                (labels == null || labels.length != labelText.length);

            // reinitialize the label array if needed
            if(remake)
            {
                labels = new JLabel[labelText.length];
            }
            for(int i=0; i<labelText.length; i++)
            {
                // make a new label if we need to
                if(remake)
                {
                    labels[i] = makeHeaderLabel(labelText[i], labelWeights[i]);
                }
                // otherwise just set the label text
                else
                {
                    labels[i].setText(labelText[i]);
                }
                // see if the the alignment needs setting
                if(alignments != null && i < alignments.length)
                {
                    labels[i].setHorizontalAlignment(alignments[i]);
                }
            }
            // initialize the layout if necessary
            if(remake)
            {
                initLayout();
            }
        }
    }

    //--------------------------------------------------------------------------
    /**
     *     Create this bean's layout and layout the components. This
     *    will assign a weight to each label that is equivalent to the
     *    integer weights stored in the labelweights array.
     */
    protected void initLayout()
    {
        removeAll();
        setLayout(new GridBagLayout());

        // create a constraints object
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy   = 0;
        constraints.fill    = GridBagConstraints.BOTH;
        constraints.anchor  = GridBagConstraints.CENTER;
        constraints.weighty = 1.0;

        // if we have labels, add each one to the panel
        if(labels != null && labels.length > 0)
        {
            for(int i=0; i<labels.length; i++)
            {
                // the actual grid weight is set to 1/10th of
                // the value stored in the weights array
                constraints.weightx = labelWeights[i]/0.1;
                add(labels[i], constraints);
            }
        }
    }

    //--------------------------------------------------------------------------
    /**
     *    Sets the alignments for the labels. The string parameter is a
     *    comma-delimited list that is parsed into an array of swing
     *  alignment constants.
     *    @param alignString the delimited list of alignments
     */
    public void setAlignments(String alignString)
    {
        // parse the string list into an array
        String[] alignList = UIUtilities.parseDelimitedList(alignString, ",");

        // if the array has values, create the alignment array
        if(alignList != null && alignList.length > 0)
        {
            int len    = alignList.length;
            alignments = new int[len];

            for(int i=0; i<len; i++)
            {
                alignments[i] = UIUtilities.getAlignmentValue(alignList[i]);
            }
        }
    }

    //--------------------------------------------------------------------------
    /**
     *    Sets the property tags to use when looking up localized text
     *  properties in a resource bundle. The string parameter is a
     *    comma-delimited list that is parsed into an array of label tags.
     *    @param tagString the delimited list of label tags
     */
    public void setLabelTags(String tagString)
    {
        // parse the string list into an array
        String[] newTags = UIUtilities.parseDelimitedList(tagString, ",");

        // if the array has values, set the labeltext array
        if(newTags.length > 0)
        {
            labelTags = newTags;
        }
        updatePropertyFields();
    }

    //--------------------------------------------------------------------------
    /**
     *    Sets the text to be used in the labels. The text is a comma-delimited
     *    string that is parsed into an array of label text.
     *    @param textString the delimited list of label text
     */
    public void setLabelText(String textString)
    {
        // parse the string list into an array
        String[] newText = UIUtilities.parseDelimitedList(textString, ",");

        // if the array has values, set the labeltext array
        if (newText != null &&
            newText.length > 0)
        {
            labelText = newText;
        }
    }

    //--------------------------------------------------------------------------
    /**
     *    Sets the space that each label will take up. The weights should be
     *    sent in as a string representing a comma-delimited list of numbers.
     *    The integer given for each label position is approximately the
     *    percentage of the header area that the label will take up. The sum
     *    of the weight list should be 100.
     *    @param weightString the delimited list of label weights
     */
    public void setLabelWeights(String weightString)
    {
        int[] tempList = UIUtilities.getIntArrayFromString(weightString);

        if(tempList != null)
        {
            labelWeights = tempList;
        }
    }

    //--------------------------------------------------------------------------
    /**
     *    Sets the text of one of the labels.
     *    @param index the index of the label
     *    @param text the new text for the label
     */
    public void setOneLabel(int index, String text)
    {
        if(labelText != null && index < labelText.length)
        {
            labelText[index] = text;
        }
        if(labels != null && index < labels.length)
        {
            labels[index].setText(text);
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
        updatePropertyFields();
    }

    //--------------------------------------------------------------------------
    /**
     *  Retrieves a localized text string from the resource bundle.
     *  @param tag a property bundle tag for localized text
     *  @param defaultText a plain text value to use as a default
     *  @return a localized text string
     */
    protected String retrieveText(String tag, String defaultText)
    {
        String result = defaultText;

        if (props != null &&
            tag != null)
        {
            result = props.getProperty(tag, defaultText);
        }

        return result;
    }

    //--------------------------------------------------------------------------
    /**
     *  Update the fields based on the properties.
     */
    protected void updatePropertyFields()
    {
        if (props != null && labelTags != null)
        {
            StringBuffer labelString = new StringBuffer();

            String tag = null;
            String defaultLabelText = null;
            for(int i = 0; i < labelTags.length; i++)
            {
                // append comma to each entry (except the last one)
                if (i > 0)
                {
                    labelString.append(",");
                }

                tag = labelTags[i];
                if (labelText != null &&
                    labelText.length > i)
                {
                    defaultLabelText = labelText[i];
                }
                else
                {
                    defaultLabelText = "";
                }
                // retrieve new tag
                labelString.append(retrieveText(tag, defaultLabelText));
            }
            // set new labels
            setLabelText(labelString.toString());
        }
    }

    //--------------------------------------------------------------------------
    /**
     *  Creates a label from the provided text string.
     *    @param text the text for the label
     */
    protected JLabel makeHeaderLabel(String text, int width)
    {

        // get a label from the UI Factory
        JLabel label = uiFactory.createLabel(text, null, UI_LABEL);

        // set the preferred size based on the weight
        Dimension dim =
            UIUtilities.sizeFromWeight(headerWidth, width, labelHeight);

        label.setPreferredSize(dim);

        return label;
    }

    //--------------------------------------------------------------------------
    /**
     *  Returns default display string.
     *  @return String representation of object
     */
    public String toString()
    {
        return new String("Class: " + Util.getSimpleClassName(this.getClass()) +
                          "(Revision " + getRevisionNumber() +
                          ") @" + hashCode());

    }

    //--------------------------------------------------------------------------
    /**
     *    Retrieves the Team Connection revision number.
     *    @return String representation of revision number
     */
    public String getRevisionNumber()
    {
        return(Util.parseRevisionNumber(revisionNumber));
    }

    //--------------------------------------------------------------------------
    /**
     *    Main entry point for testing.
     *    @param args string arguments required for execution
     */
    public static void main(String[] args)
    {
        UIUtilities.setUpTest();

        BaseHeaderBean bean = new BaseHeaderBean();
        bean.setLabelText("Description/Item #,Qty,Price,Discount,Ext Price");
        bean.setLabelWeights("40,15,15,15,15");
        bean.setAlignments("left,center,right,right,right");
        bean.activate();

        UIUtilities.doBeanTest(bean);
        bean.setVisible(true);
    }

}
