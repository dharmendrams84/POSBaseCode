/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/StatusSearchBean.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:43 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/28/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    acadar    04/22/09 - translate date/time labels
 *    mkochumm  02/12/09 - use default locale for dates
 *
 * ===========================================================================
 * $Log:
 *    5    I18N_P2    1.2.1.1     1/7/2008 3:52:27 PM    Maisa De Camargo CR
 *         29826 - Setting the size of the combo boxes. This change was
 *         necessary because the width of the combo boxes used to grow
 *         according to the length of the longest content. By setting the
 *         size, we allow the width of the combo box to be set independently
 *         from the width of the dropdown menu.
 *    4    I18N_P2    1.2.1.0     1/4/2008 5:00:24 PM    Maisa De Camargo CR
 *         29826 - Setting the size of the combo boxes. This change was
 *         necessary because the width of the combo boxes used to grow
 *         according to the length of the longest content. By setting the
 *         size, we allow the width of the combo box to be set independently
 *         from the width of the dropdown menu.
 *    3    360Commerce 1.2         3/31/2005 4:30:10 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:25:29 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:14:24 PM  Robert Pearse
 *
 *   Revision 1.5  2004/07/17 19:21:23  jdeleau
 *   @scr 5624 Make sure errors are focused on the beans, if an error is found
 *   during validation.
 *
 *   Revision 1.4  2004/07/12 16:17:13  awilliam
 *   @scr 2793 void is not a status to be searched for
 *
 *   Revision 1.3  2004/03/16 17:15:18  build
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 20:56:27  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:22  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.2   Sep 16 2003 17:53:22   dcobb
 * Migrate to JVM 1.4.1
 * Resolution for 3361: New Feature:  JVM 1.4.1_03 (Windows) Migration
 *
 *    Rev 1.1   Sep 09 2003 10:48:18   RSachdeva
 * Start Date/ End Date Format for Labels
 * Resolution for POS SCR-2467: Status Search screen: need change "MM/dd/yyyy"  to "MM/DD/YYYY"
 *
 *    Rev 1.0   Aug 29 2003 16:12:28   CSchellenger
 * Initial revision.
 *
 *    Rev 1.2   Mar 14 2003 09:11:46   RSachdeva
 * Initialize Order Status Descriptiors as per the User Interface Locale (for STATUS_SEARCH Screen)
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.1   Aug 07 2002 19:34:26   baa
 * remove hard coded date formats
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.0   Apr 29 2002 14:51:44   msg
 * Initial revision.
 *
 *    Rev 1.2   15 Apr 2002 09:36:18   baa
 * make call to setLabel() from the updatePropertyFields() method
 * Resolution for POS SCR-1599: Field name labels on dialog screens use default text instead of text from bundles
 *
 *    Rev 1.1   05 Apr 2002 16:01:06   dfh
 * updates to improve date validation, cleanup
 * Resolution for POS SCR-178: CR/Order, incomplete date range search, dialog text erroneous
 *
 *    Rev 1.0   Mar 18 2002 11:57:40   msg
 * Initial revision.
 *
 *    Rev 1.2   Mar 02 2002 17:58:58   mpm
 * Internationalized order UI.
 * Resolution for POS SCR-351: Internationalization
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;
// java imports
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.utility.LocaleUtilities;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.ui.UIUtilities;

//----------------------------------------------------------------------------
/**
   Contains the visual presentation for Status Search screen<p>
   @version $Revision: /rgbustores_13.4x_generic_branch/1 $
*/
//----------------------------------------------------------------------------
public class StatusSearchBean extends ValidatingBean implements DocumentListener
{
    // Define constants for grid bay layout field positioning on the screen.
    // Max fields is a count of the total number of rows.
    protected static final int START_DATE_ROW = 0;
    protected static final int END_DATE_ROW   = START_DATE_ROW + 1;
    protected static final int STATUS_ROW     = END_DATE_ROW + 1;
    protected static final int MAX_FIELDS     = STATUS_ROW + 1; //add one because of 0 index!

    //hold the void status string
    protected static final String VOID_STATUS = "Voided";

    protected static String labelText[] =
    {
        "Start Date ({0}):",
        "End Date ({0}):",
        "Status:"
    };

    protected static String labelTags[] =
    {
        "StartDateLabel",
        "EndDateLabel",
        "StatusLabel"
    };

    protected JLabel[] fieldLabels = new JLabel[MAX_FIELDS];
    protected EYSDateField startDateField = null;
    protected EYSDateField endDateField   = null;
    /**
        Status Field ValidatingComboBox
    **/
    protected ValidatingComboBox statusField = null;

    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------------
    /**
     * Default Constructor
     */
    //----------------------------------------------------------------------------
    public StatusSearchBean()
    {
        super();
    }
    //----------------------------------------------------------------------------
    /**
     * Activate any settings made by this bean to external entities
     */
    //----------------------------------------------------------------------------
    public void activate()
    {
        updateBean();
        super.activate();
        startDateField.getDocument().addDocumentListener(this);
        startDateField.addFocusListener(this);
        endDateField.getDocument().addDocumentListener(this);
    }


    //--------------------------------------------------------------------------
    /**
     *  Overrides the inherited setVisible().
     *  @param value boolean
     */
    //----------------------------------------------------------------------------
    public void setVisible(boolean value)
    {
        super.setVisible(value);
        setRequiredFields();
        if (value && !errorFound())
        {
            setCurrentFocus(startDateField);
        }
    }

    //----------------------------------------------------------------------------
    /**
     * Configures the class.
     */
    //----------------------------------------------------------------------------
    public void configure()
    {
        setName("StatusSearchBean");
        uiFactory.configureUIComponent(this, UI_PREFIX);


        for(int cnt = 0; cnt < MAX_FIELDS; cnt++)
        {
            fieldLabels[cnt] =
                uiFactory.createLabel(labelText[cnt], null, UI_LABEL);
        }
        startDateField = uiFactory.createEYSDateField("startDateField");


        endDateField = uiFactory.createEYSDateField("endDateField");

        statusField = uiFactory.createValidatingComboBox("statusField", "false", "10");
        statusField.setEditable(false);

        UIUtilities.layoutDataPanel(this, fieldLabels,
                                    new JComponent[]
                                    {
                                        startDateField,
                                        endDateField,
                                        statusField
                                    });
    }

    //------------------------------------------------------------------------
    /**
     * Gets the model for the current settings of this bean.
     * statusField is an StatusComboBox derived from JComboBox
     */
    //------------------------------------------------------------------------
    public void updateModel()
    {
        if (beanModel instanceof StatusSearchBeanModel)
        {
            StatusSearchBeanModel model = (StatusSearchBeanModel)beanModel;

            if (startDateField.isInputValid() && startDateField.getText() != "")
            {
                model.setStartDate(startDateField.getEYSDate());
            }
            if (endDateField.isInputValid() && endDateField.getText() != "")
            {
                model.setEndDate(endDateField.getEYSDate());
            }
        //Index selected in Validating ComboBox
        model.setStatusIndex(statusField.getSelectedIndex());
        }
    }

    //---------------------------------------------------------------------
    /**
       Update the model that was passed into setModel as the new model to use
    **/
    //---------------------------------------------------------------------
    protected void updateBean()
    {
        if (beanModel instanceof StatusSearchBeanModel)
        {
            StatusSearchBeanModel model = (StatusSearchBeanModel)beanModel;
            startDateField.setText("");
            endDateField.setText("");

            String[] descriptions = initializeOrderStatusDescriptors(model.getOrderStatusDescriptors());
            ValidatingComboBoxModel smodel = new ValidatingComboBoxModel(descriptions);
            statusField.setModel (smodel);
        }
        setCurrentFocus(startDateField);
    }

    //---------------------------------------------------------------------
    /**
       Initialize Order Status Descriptiors as per the User Interface Locale
       in the ValidatingComboBox <P>
       @param orderStatusDesc Order Status Descriptors Array
    **/
    //---------------------------------------------------------------------
    protected String[]  initializeOrderStatusDescriptors(String[] orderStatusDesc)
    {
        String[] localizedStatusDescriptions = null;
        if (orderStatusDesc != null)
        {
            localizedStatusDescriptions = new String[orderStatusDesc.length];
            for (int i = 0; i < orderStatusDesc.length; i++)
            {
                if (!(orderStatusDesc[i].equalsIgnoreCase(VOID_STATUS)))
                {
                    localizedStatusDescriptions[i] = UIUtilities.retrieveCommonText(orderStatusDesc[i],orderStatusDesc[i]);
                }

            }
        }
        return localizedStatusDescriptions;
    }
    //---------------------------------------------------------------------
    /**
        Updates fields based on properties.
    **/
    //---------------------------------------------------------------------
    protected void updatePropertyFields()
    {                                   // begin updatePropertyFields()
        for (int i = 0; i < labelText.length; i++)
        {
            fieldLabels[i].setText(retrieveText(labelTags[i],
                                                fieldLabels[i]));
        }
        String translatedLabel = getTranslatedDatePattern();
        fieldLabels[START_DATE_ROW].setText(LocaleUtilities.formatComplexMessage(fieldLabels[START_DATE_ROW].getText(),translatedLabel));
        fieldLabels[END_DATE_ROW].setText(LocaleUtilities.formatComplexMessage(fieldLabels[END_DATE_ROW].getText(), translatedLabel));
        endDateField.setLabel(fieldLabels[END_DATE_ROW]);
        startDateField.setLabel(fieldLabels[START_DATE_ROW]);
    }                                   // end updatePropertyFields()

    //----------------------------------------------------------------------------
    /**
     * Deactivate any settings made by this bean to external entities
     */
    //----------------------------------------------------------------------------
    public void deactivate()
    {
        super.deactivate();

        startDateField.getDocument().removeDocumentListener(this);
        startDateField.removeFocusListener(this);
        endDateField.getDocument().removeDocumentListener(this);
    }

    //------------------------------------------------------------------------------
    /**
    *   Implementation of DocumentListener interface.
    *   @param e a document event
    */
    public void changedUpdate(DocumentEvent e)
    {
        setRequiredFields();
    }

    //------------------------------------------------------------------------------
    /**
    *   Implementation of DocumentListener interface.
    *   @param e a document event
    */
    public void insertUpdate(DocumentEvent e)
    {
        setRequiredFields();
    }

    //------------------------------------------------------------------------------
    /**
    *   Implementation of DocumentListener interface.
    *   @param e a document event
    */
    public void removeUpdate(DocumentEvent e)
    {
        setRequiredFields();
    }

    //---------------------------------------------------------------------
    /**
       Marks the start date field and end date field to be required if data
       is entered in either field (start/end), otherwise the fields are
       marked as optional.
    **/
    //---------------------------------------------------------------------
    protected void setRequiredFields()
    {
        if (!startDateField.getText().equals("") ||
            !endDateField.getText().equals(""))
        {
            setFieldRequired(startDateField,true);
            setFieldRequired(endDateField,true);
        }
        else
        {
            setFieldRequired(startDateField,false);
            setFieldRequired(endDateField,false);
        }
    }

    //---------------------------------------------------------------------
    /**
       Returns default display string. <P>
       @return String representation of object
    */
    //---------------------------------------------------------------------
    public String toString()
    {
        String strResult = new String("Class: StatusSearchBean (Revision " +
                                      getRevisionNumber() + ") @" +
                                      hashCode());
        return(strResult);
    }

    //---------------------------------------------------------------------
    /**
       Retrieves the Team Connection revision number. <P>
       @return String representation of revision number
    */
    //---------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return(Util.parseRevisionNumber(revisionNumber));
    }

    //----------------------------------------------------------------------------
    /**
     * main entrypoint - starts the part when it is run as an application
     * @param args String[]
     */
    //----------------------------------------------------------------------------
    public static void main(String[] args)
    {
        UIUtilities.setUpTest();

        StatusSearchBeanModel
            beanModel = new StatusSearchBeanModel();
            beanModel.setStartDate(DomainGateway.getFactory().getEYSDateInstance());
            beanModel.setEndDate(DomainGateway.getFactory().getEYSDateInstance());
            beanModel.setStatus((String)new StatusComboBox().getItemAt(0)); // first drop down list element

        StatusSearchBean
            bean = new StatusSearchBean();
            bean.configure();
            bean.setModel(beanModel);
            bean.activate();

        UIUtilities.doBeanTest(bean);
    }
}   ///:~ end class StatusSearchBean
