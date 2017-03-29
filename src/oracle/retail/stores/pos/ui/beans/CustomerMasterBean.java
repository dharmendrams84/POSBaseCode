/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/CustomerMasterBean.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:45 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/28/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *    acadar    04/22/09 - refactoring
 *    acadar    04/22/09 - refactoring changes
 *    acadar    04/22/09 - translate date/time labels
 *    nkgautam  02/16/09 - Changed fullName Constrained Field to a JLabel
 *    acadar    02/09/09 - use default locale for display of date and time
 *
 * ===========================================================================
 * $Log:
 *    8    I18N_P2    1.5.1.1     1/8/2008 2:56:48 PM    Sandy Gu        Set
 *         max length of constraied text field.
 *    7    I18N_P2    1.5.1.0     1/4/2008 5:00:24 PM    Maisa De Camargo CR
 *         29826 - Setting the size of the combo boxes. This change was
 *         necessary because the width of the combo boxes used to grow
 *         according to the length of the longest content. By setting the
 *         size, we allow the width of the combo box to be set independently
 *         from the width of the dropdown menu.
 *    6    360Commerce 1.5         10/8/2007 11:36:46 AM  Anda D. Cadar   UI
 *         changes to not allow double bytes chars in some cases
 *    5    360Commerce 1.4         8/3/2007 5:35:14 PM    Mathews Kochummen
 *         validate date when year is on separate field
 *    4    360Commerce 1.3         5/21/2007 10:04:16 PM  Mathews Kochummen
 *         format label
 *    3    360Commerce 1.2         3/31/2005 4:27:37 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:20:40 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:10:23 PM  Robert Pearse
 *
 *   Revision 1.5  2004/07/16 18:05:42  bvanschyndel
 *   @scr 5995 made the model Validating for yes and no combo boxes
 *
 *   Revision 1.4  2004/03/16 17:15:22  build
 *   Forcing head revision
 *
 *   Revision 1.3  2004/03/16 17:15:17  build
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 20:56:26  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:21  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.1   Dec 01 2003 14:01:32   baa
 * yes/no combo box issues
 * Resolution for 3468: Drop down boxes display incorrect data on Customer Details during Customer Search
 *
 *    Rev 1.0.1.1   Dec 01 2003 13:55:16   baa
 * cleanup system outs
 *
 *    Rev 1.0   Aug 29 2003 16:10:00   CSchellenger
 * Initial revision.
 *
 *    Rev 1.7   Aug 27 2003 17:20:54   baa
 * remove call to apply pattern
 * Resolution for 3330: Out of Memory Error- App Crashes.
 *
 *    Rev 1.6   May 09 2003 12:50:48   baa
 * more fixes to business customer
 * Resolution for POS SCR-2366: Busn Customer - Tax Exempt- Does not display Tax Cert #
 *
 *    Rev 1.5   Apr 02 2003 17:50:46   baa
 * customer and screen changes
 * Resolution for POS SCR-2098: Refactoring of Customer Service Screens
 *
 *    Rev 1.4   Mar 24 2003 10:08:18   baa
 * remove reference to foundation.util.EMPTY_STRING
 * Resolution for POS SCR-2101: Remove uses of  foundation constant  EMPTY_STRING
 *
 *    Rev 1.3   Mar 20 2003 18:18:58   baa
 * customer screens refactoring
 * Resolution for POS SCR-2098: Refactoring of Customer Service Screens
 *
 *    Rev 1.2   Aug 07 2002 19:34:16   baa
 * remove hard coded date formats
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.1   21 May 2002 17:29:54   baa
 * ils
 * Resolution for POS SCR-1624: Localization Support
 *
 *    Rev 1.0   13 May 2002 14:12:00   baa
 * Initial revision.
 * Resolution for POS SCR-1624: Spanish translation
 *
 *    Rev 1.1   15 Apr 2002 09:33:38   baa
 * make call to setLabel() from the updatePropertyFields() method
 * Resolution for POS SCR-1599: Field name labels on dialog screens use default text instead of text from bundles
 *
 *    Rev 1.0   Mar 18 2002 11:52:54   msg
 * Initial revision.
 *
 *    Rev 1.2   Feb 28 2002 19:21:06   mpm
 * Internationalization
 * Resolution for POS SCR-351: Internationalization
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

//java imports
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.swing.JLabel;

import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.domain.utility.LocaleUtilities;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.ui.UIUtilities;
//----------------------------------------------------------------------------
/**
   This is the class that display main Customer information.
   It is used with the CustomerMasterBeanModel class. <p>
   @version $Revision: /rgbustores_13.4x_generic_branch/1 $
*/
//----------------------------------------------------------------------------
public class CustomerMasterBean extends ValidatingBean
{
    /**
        revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

     /**
        Fields  and labels
    **/
    protected JLabel BirthdateLabel = null;
    protected JLabel BirthYearLabel = null;
    protected JLabel CustomerIdLabel = null;
    protected JLabel EmailLabel = null;
    protected JLabel EmployeeNoLabel = null;

    protected JLabel FullNameLabel = null;
    protected JLabel SalutationLabel = null;

    protected JLabel GenderLabel = null;
    protected JLabel MailLabel = null;
    protected JLabel PrivacyIssuesLabel = null;

    protected JLabel TelephoneLabel = null;
    /** @deprecated as of release 6.0 move to customerInfoBean **/
    protected JLabel PreferredCustomerLabel = null;
    protected JLabel PreferredLanguageLabel = null;
    protected JLabel CustomerIdField = null;
    protected EYSDateField BirthdateField = null;
    protected NumericTextField BirthYearField = null;
    protected YesNoComboBox MailField = null;
    protected YesNoComboBox TelephoneField = null;
    protected YesNoComboBox EmailField = null;
    protected JLabel LanguageField = null;

    // general purpose dataModel object reference for the YesNoComboBoxes
    protected BooleanComboModel mailModel = null;
    protected BooleanComboModel telephoneModel = null;
    protected BooleanComboModel emailModel = null;
    protected JLabel FullNameField = null;
    protected ConstrainedTextField SalutationField = null;
    protected ValidatingComboBox GenderField = null;

    protected ValidatingComboBox PreferredLanguageField = null;


    /** @deprecated as of release 6.0 removed from this screen **/
    protected AlphaNumericTextField EmployeeNoField = null;
    protected ConstrainedTextField FirstNameField = null;
    protected ConstrainedTextField LastNameField = null;
    protected ConstrainedTextField MiddleNameField = null;
    protected ConstrainedTextField SuffixField = null;
    protected JLabel FirstNameLabel = null;
    protected JLabel LastNameLabel = null;
    protected JLabel SuffixLabel = null;
    protected JLabel MiddleNameLabel = null;
    protected ValidatingComboBox PreferredCustomerField = null;
    /**
        flag that indicates if the bean model has been changed
        @deprecated as of release 5.5 dead code
    **/
    protected boolean dirtyModel = true;

    //----------------------------------------------------------------------------
    /**
     * Constructor. Call setTabOrder() to override the
       default focus manager where needed.  This allows the bean to control
       which field receives the focus each time the TAB key is pressed.

     */
    //----------------------------------------------------------------------------
    public CustomerMasterBean()
    {
        super();
        initialize();
        setTabOrder();
    }


    //----------------------------------------------------------------------------
    /**
        Returns the base bean model
    */
    //----------------------------------------------------------------------------
    public POSBaseBeanModel getPOSBaseBeanModel()
    {
        return beanModel;
    }

    //----------------------------------------------------------------------------
    /**
        Initializes the fields
    */
    //----------------------------------------------------------------------------
    protected void initializeFields()
    {

        BirthdateField = uiFactory.createEYSDateField("BirthdateField");
        BirthdateField.setFormat(DateDocument.MONTH_DAY);
        BirthdateField.setColumns(15);

        BirthYearField = uiFactory.createNumericField("BirthYearField", "0", "4");
        BirthYearField.setColumns(15);
        CustomerIdField = uiFactory.createLabel("", null, UI_LABEL);
        FullNameField = uiFactory.createLabel("Full Name:", null, UI_LABEL);
        GenderField = uiFactory.createValidatingComboBox("GenderField", "false", "10");
        SalutationField = uiFactory.createConstrainedField("SalutationField", "2", "30", "30");
        PreferredLanguageField = uiFactory.createValidatingComboBox("PreferredLanguageField", "false", "20");
        PreferredLanguageField.setEditable(false);
        MailField = uiFactory.createYesNoComboBox("MailField", 10);
        MailField.setEditable(false);
        TelephoneField = uiFactory.createYesNoComboBox("TelephoneField", 10);
        TelephoneField.setEditable(false);
        EmailField = uiFactory.createYesNoComboBox("EMailField", 10);
        EmailField.setEditable(false);

    }

    //----------------------------------------------------------------------------
    /**
        Initializes the labels
    */
    //----------------------------------------------------------------------------
    protected void initializeLabels()
    {
        BirthdateLabel         = uiFactory.createLabel("Birthday ({0}):", null, UI_LABEL);
        BirthYearLabel         = uiFactory.createLabel("Birth Year (YYYY):", null, UI_LABEL);
        CustomerIdLabel        = uiFactory.createLabel("Customer ID:", null, UI_LABEL);
        FullNameLabel          = uiFactory.createLabel("Full Name:", null, UI_LABEL);
        GenderLabel            = uiFactory.createLabel("Gender:", null, UI_LABEL);

        PrivacyIssuesLabel     = uiFactory.createLabel("Privacy Issues", null, UI_LABEL);
        SalutationLabel        = uiFactory.createLabel("Salutation:", null, UI_LABEL);
        MailLabel              = uiFactory.createLabel("Mail:", null, UI_LABEL);
        TelephoneLabel         = uiFactory.createLabel("Telephone:", null, UI_LABEL);
        EmailLabel             = uiFactory.createLabel("E-Mail:", null, UI_LABEL);
        PreferredLanguageLabel = uiFactory.createLabel("Language:", null, UI_LABEL);
    }

    //----------------------------------------------------------------------------
    /**
     * Return the dataModel being used by the MailField which is a YesNoComboBox
     * @return BooleanComboModel
     */
    //----------------------------------------------------------------------------
     protected BooleanComboModel getMailModel()
     {
        // explicit cast from MailComboModel to BooleanComboModel
        if (mailModel == null)
        {
            mailModel = (BooleanComboModel) MailField.getModel();
        }

        return mailModel;
     }

     //----------------------------------------------------------------------------
     /**
     * Return the dataModel being used by the TelephoneField which is a YesNoComboBox
     * @return BooleanComboModel
     */
     //----------------------------------------------------------------------------
     protected BooleanComboModel getTelephoneModel()
     {
        // explicit cast from ComboBoxModel to BooleanComboModel
        if (telephoneModel == null)
        {
            telephoneModel = (BooleanComboModel) TelephoneField.getModel();
        }

        return telephoneModel;

     }

     //----------------------------------------------------------------------------
     /**
     * Return the dataModel being used by the EmailField which is a YesNoComboBox
     * @return BooleanComboModel
     */
     //----------------------------------------------------------------------------
     protected BooleanComboModel getEmailModel()
     {
        // explicit cast from ComboBoxModel to BooleanComboModel
        if (emailModel == null)
        {
            emailModel = (BooleanComboModel) EmailField.getModel();
        }

        return emailModel;
     }

     //----------------------------------------------------------------------------
     /**
     * Override the tab key ordering scheme of the default focus manager where
     * appropriate.  The default is to move in a zig-zag pattern from left to right
     * across the screen. In some cases, however, it makes more sense to move down
     * column one on the screen then start at the top of column 2.
     * @deprecated as of release 6.0 no longer needed
     */
     //----------------------------------------------------------------------------
     protected void setTabOrder()
     {
     }   // end method setTabOrder

    //--------------------------------------------------------------------------
    /**
     *    Initialize the class.
     */
    protected void initialize()
    {
        setName("CustomerMasterBean");
        uiFactory.configureUIComponent(this, UI_PREFIX);

        initializeFields();
        initializeLabels();
        initLayout();

    }

    //--------------------------------------------------------------------------
    /**
     *    Initializes the layout and lays out the components.
     */
    protected void initLayout()
    {
        setLayout(new GridBagLayout());

        UIUtilities.layoutComponent(this,CustomerIdLabel,CustomerIdField,0,0,false);
        UIUtilities.layoutComponent(this,FullNameLabel,FullNameField,0,1,false);
        UIUtilities.layoutComponent(this,SalutationLabel,SalutationField,0,2,false);

        UIUtilities.layoutComponent(this,BirthdateLabel,BirthdateField,0,3,false);
        UIUtilities.layoutComponent(this,BirthYearLabel,BirthYearField,0,4,false);
        UIUtilities.layoutComponent(this,GenderLabel,GenderField,0,5,false);
        UIUtilities.layoutComponent(this,PrivacyIssuesLabel,null,0,7,false);

        UIUtilities.layoutComponent(this,MailLabel,MailField,0,8,false);
        UIUtilities.layoutComponent(this,TelephoneLabel,TelephoneField,0,9,false);
        UIUtilities.layoutComponent(this,EmailLabel,EmailField,0,10,false);
        UIUtilities.layoutComponent(this,PreferredLanguageLabel,PreferredLanguageField,0,11,false);
    }

    //------------------------------------------------------------------------
    /**
       Updates the model for the current settings of this bean.

    */
    //------------------------------------------------------------------------
    public void updateModel()
    {
        if (beanModel instanceof CustomerInfoBeanModel)
        {
            CustomerInfoBeanModel model = (CustomerInfoBeanModel)beanModel;
            if (BirthdateField.isValid())
            {
              if(!Util.isEmpty(BirthYearField.getText()))
              {
            	  //since year occurs in a separate ui field, set value in DateDocument
            	  int year = (int)(BirthYearField.getLongValue());
                  DateDocument bdayDoc = (DateDocument)(BirthdateField.getDocument());
                  bdayDoc.setSeparateYear(year);
              }
              model.setBirthdate(BirthdateField.getDate());
            }

            if(!Util.isEmpty(BirthYearField.getText()))
            {
                model.setBirthYear(BirthYearField.getLongValue());
            }

            model.setMailPrivacy(getMailModel().valueOf((String)MailField.getSelectedItem()));
            model.setTelephonePrivacy(getTelephoneModel().valueOf((String)TelephoneField.getSelectedItem()));
            model.setEmailPrivacy(getEmailModel().valueOf((String)EmailField.getSelectedItem()));

            model.setCustomerName(FullNameField.getText());

            model.setGenderIndex(GenderField.getSelectedIndex());
            model.setSalutation(SalutationField.getText());

            model.setSelectedLanguage(PreferredLanguageField.getSelectedIndex());
        }
    }


    /**
     * validate fields
     */
    protected boolean validateFields()
    {
        if(!Util.isEmpty(BirthYearField.getText()))
        {
      	    int year = (int)(BirthYearField.getLongValue());
            DateDocument bdayDoc = (DateDocument)(BirthdateField.getDocument());
            bdayDoc.setSeparateYear(year);
        }
    	return (super.validateFields());
    }



    //---------------------------------------------------------------------
    /**
     * Update the bean if It's been changed
     */
    //---------------------------------------------------------------------
    protected void updateBean()
    {
       if (beanModel instanceof CustomerInfoBeanModel)
        {
            CustomerInfoBeanModel model = (CustomerInfoBeanModel)beanModel;
            if (model.isBirthdateValid())
            {

                BirthdateField.setDate(model.getBirthMonthAndDay());
            }
            else
            {
                BirthdateField.setText("");
            }

            if (model.isBirthYearValid())
            {
                BirthYearField.setLongValue(model.getBirthYear());
            }
            else
            {
                BirthYearField.setText("");
            }

            GenderField.setModel(new ValidatingComboBoxModel (model.getGenderTypes()));
            GenderField.setSelectedIndex(model.getGenderIndex());

            CustomerIdField.setText(model.getCustomerID());



            String mailSetting = getMailModel().valueOf(model.getMailPrivacy());
            MailField.setModel(new ValidatingComboBoxModel(getMailModel().getValues()));
            MailField.setSelectedItem(mailSetting);

            String phoneSetting = getTelephoneModel().valueOf(model.getTelephonePrivacy());
            TelephoneField.setModel(new ValidatingComboBoxModel(getTelephoneModel().getValues()));
            TelephoneField.setSelectedItem(phoneSetting);

            String emailSetting = getEmailModel().valueOf(model.getEmailPrivacy());
            EmailField.setModel(new ValidatingComboBoxModel(getEmailModel().getValues()));
            EmailField.setSelectedItem(emailSetting);

            FullNameField.setText(model.getCustomerName());

            SalutationField.setText(model.getSalutation());

            if (model.getLanguages() != null)
            {
                 PreferredLanguageField.setModel(new ValidatingComboBoxModel(model.getLanguages()));
                 PreferredLanguageField.setSelectedIndex(model.getSelectedLanguage());
            }

        }
    }

    //---------------------------------------------------------------------
    /**
     *  Set the properties to be used by this bean
        @param props the propeties object
     */
    //---------------------------------------------------------------------
    public void setProps(Properties props)
    {
        super.setProps(props);
        getMailModel().setProps(props);
        getTelephoneModel().setProps(props);
        getEmailModel().setProps(props);
        updatePropertyFields();
    }

    //---------------------------------------------------------------------------
    /**
     *  Update property fields.
     */
    //---------------------------------------------------------------------------
    protected void updatePropertyFields()
    {
        DateDocument    doc         =  (DateDocument)BirthdateField.getDocument();
        String          dobLabel    =  retrieveText("BirthdateLabel", BirthdateLabel);
        String          yearLabel   =  retrieveText("BirthYearLabel", BirthYearLabel);

        //Retrieve the localized pattern for the Full year
        SimpleDateFormat dateFormat = DomainGateway.getSimpleDateFormat(getDefaultLocale(), LocaleConstantsIfc.DEFAULT_YEAR_FORMAT);
        String translatedLabel = getTranslatedDatePattern(dateFormat.toPattern());

        BirthYearLabel.setText(LocaleUtilities.formatComplexMessage(yearLabel,translatedLabel));

        // Retrieve bundle text for month/day label
        String monthDayPatternChars = ((SimpleDateFormat)(doc.getDateFormat())).toPattern();

        translatedLabel = getTranslatedDatePattern(monthDayPatternChars);
        BirthdateLabel.setText(LocaleUtilities.formatComplexMessage(dobLabel, translatedLabel));

        CustomerIdLabel.setText(retrieveText("CustomerIDLabel",CustomerIdLabel));
        FullNameLabel.setText(retrieveText("FullNameLabel",  FullNameLabel));
        GenderLabel.setText(retrieveText("GenderLabel", GenderLabel));
        PrivacyIssuesLabel.setText(retrieveText("PrivacyIssuesLabel",PrivacyIssuesLabel));
        SalutationLabel.setText(retrieveText("SalutationLabel", SalutationLabel));

        MailLabel.setText(retrieveText("MailLabel", MailLabel));
        TelephoneLabel.setText(retrieveText("TelephoneLabel", TelephoneLabel));
        EmailLabel.setText(retrieveText("EmailLabel", EmailLabel));
        PreferredLanguageLabel.setText(retrieveText("PreferredLanguageLabel", PreferredLanguageLabel));
        // Associate labels with fields
        SalutationField.setLabel(SalutationLabel);

        GenderField.setLabel(GenderLabel);
        BirthdateField.setLabel(BirthdateLabel);
        BirthYearField.setLabel(BirthYearLabel);

        MailField.setLabel(MailLabel);
        TelephoneField.setLabel(TelephoneLabel);
        EmailField.setLabel(EmailLabel);
        PreferredLanguageField.setLabel(PreferredCustomerLabel);
    }

    //---------------------------------------------------------------------
    /**
       Returns default display string. <P>
       @return String representation of object
    */
    //---------------------------------------------------------------------
    public String toString()
    {
        String strResult = new String("Class: CustomerMasterBean (Revision " +
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


    //--------------------------------------------------------------------------
    /**
     * main entrypoint - starts the part when it is run as an application
     * @param args java.lang.String[]
     */
    //--------------------------------------------------------------------------
    public static void main(java.lang.String[] args)
    {
        UIUtilities.setUpTest();

        CustomerMasterBean bean = new CustomerMasterBean();

        UIUtilities.doBeanTest(bean);
    }
}
