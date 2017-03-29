/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/AgeRestrictionBean.java /rgbustores_13.4x_generic_branch/2 2011/05/27 10:03:05 rsnayak Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    rsnayak   05/27/11 - forward port for birth date fix
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
 *    acadar    04/22/09 - translate the date/time labels
 *    nkgautam  03/13/09 - Changes to take input DOB as two separate fields,
 *    mkochumm  02/23/09 - show localized date format
 *
 * ===========================================================================
 * $Log:
 1    360Commerce 1.0         12/13/2005 4:47:07 PM  Barry A. Pape
 *
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

//java imports
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.swing.JLabel;

import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.utility.EYSDate;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.domain.utility.LocaleUtilities;
import oracle.retail.stores.foundation.manager.gui.UIModelIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.ui.UIUtilities;

//---------------------------------------------------------------------
/**
 This bean is used to capture the dob of the customer. <P>
 @version $$
 */
//---------------------------------------------------------------------
public class AgeRestrictionBean extends ValidatingBean
{
  /**
      Fields and labels that contain check enter ID data
   */
  protected JLabel BirthdateLabel = null;
  protected JLabel BirthYearLabel = null;
  protected EYSDateField BirthdateField = null;
  protected NumericTextField BirthYearField = null;


  /**
      Revision number supplied by source-code control system
   **/
  public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

  //---------------------------------------------------------------------
  /**
     Default Constructor.
   */
  //---------------------------------------------------------------------
  public AgeRestrictionBean()
  {
    super();
    initialize();
  }

  //---------------------------------------------------------------------
  /**
     Initializes the fields.
   */
  //---------------------------------------------------------------------
  protected void initializeFields()
  {
    BirthdateField = uiFactory.createEYSDateField("BirthdateField");
    BirthdateField.setFormat(DateDocument.MONTH_DAY);
    BirthdateField.setColumns(15);
    BirthYearField = uiFactory.createNumericField("BirthYearField", "4", "4");
    BirthYearField.setColumns(15);
  }

  //---------------------------------------------------------------------
  /**
     Initializes the labels.
   */
  //---------------------------------------------------------------------
  protected void initializeLabels()
  {
    BirthdateLabel         = uiFactory.createLabel("Birthday", "Birthday ({0}):", null, UI_LABEL);
    BirthYearLabel         = uiFactory.createLabel("Birth Year", "Birth Year (YYYY):", null, UI_LABEL);
  }

  //---------------------------------------------------------------------
  /**
      Updates the model from the screen.
   */
  //---------------------------------------------------------------------
  public void updateModel()
  {
    if (beanModel instanceof AgeRestrictionBeanModel)
    {
      AgeRestrictionBeanModel model = (AgeRestrictionBeanModel) beanModel;
      EYSDate DOBDate = null;
      if (BirthdateField.isValid())
      {
        if(!Util.isEmpty(BirthYearField.getText()))
        {
          int year = (int)(BirthYearField.getLongValue());
          DateDocument bdayDoc = (DateDocument)(BirthdateField.getDocument());
          bdayDoc.setSeparateYear(year);
        }
        model.setBirthdate(BirthdateField.getDate());
        DOBDate = BirthdateField.getDate();
      }

      if(!Util.isEmpty(BirthYearField.getText()))
      {
        model.setBirthYear(BirthYearField.getLongValue());
        DOBDate.setYear(Integer.parseInt(BirthYearField.getText()));
      }
      model.setDateOfBirth(DOBDate);

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
     Sets the model property  value.<P>
     @param model UIModelIfc the new value for the property.
   */
  //---------------------------------------------------------------------
  public void setModel(UIModelIfc model)
  {
    if(model == null)
    {
      throw new NullPointerException("Attempt to set AgeRestrictionBeanModel" +
      "model to null");
    }
    else
    {
      if (model instanceof  AgeRestrictionBeanModel)
      {
        beanModel = (AgeRestrictionBeanModel)model;
        updateBean();
      }
    }
  }

  //---------------------------------------------------------------------
  /**
      Updates the information displayed on the screen's if the model's
      been changed.
   */
  //---------------------------------------------------------------------
  protected void updateBean()
  {
    if (beanModel instanceof AgeRestrictionBeanModel)
    {
      AgeRestrictionBeanModel model = (AgeRestrictionBeanModel)beanModel;
      if (model.getBirthdate()!= null && model.getBirthdate().isValid())//TODO
      {

        BirthdateField.setDate(model.getBirthdate());
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
    }
    BirthdateField.setRequired(true);
    BirthYearField.setRequired(true);
  }

  //---------------------------------------------------------------------
  /**
      Initialize the class.
   */
  //---------------------------------------------------------------------
  protected void initialize()
  {
    setName("IDNumberBean");
    uiFactory.configureUIComponent(this, UI_PREFIX);

    initializeFields();
    initializeLabels();

    initLayout();
  }

  /**
   *  Set the properties to be used by this bean
      @param props the propeties object
   */
  //---------------------------------------------------------------------
  public void setProps(Properties props)
  {
    super.setProps(props);
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


  }

  protected void initLayout()
  {
    setLayout(new GridBagLayout());

    UIUtilities.layoutComponent(this,BirthdateLabel,BirthdateField,0,0,false);
    UIUtilities.layoutComponent(this,BirthYearLabel,BirthYearField,0,1,false);

  }

  //---------------------------------------------------------------------
  /**
     Retrieves the Team Connection revision number. <P>
     @return String representation of revision number
   */
  //---------------------------------------------------------------------
  public String getRevisionNumber()
  {
    return(revisionNumber);
  }
}
