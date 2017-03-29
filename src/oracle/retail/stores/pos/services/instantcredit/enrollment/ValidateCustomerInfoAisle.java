/* ===========================================================================
* Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/instantcredit/enrollment/ValidateCustomerInfoAisle.java /rgbustores_13.4x_generic_branch/2 2011/10/06 16:31:03 mkutiana Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    mkutiana  10/06/11 - Using MinAgeForEnroll parameter for min age and
 *                         removed check for year of birth
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    cgreene   03/24/10 - switch NO button to mail Cancel letter instead of
 *                         frank
 *    abondala  01/03/10 - update header date
 *    mkochumm  12/17/08 - remove business phone
 *
 * ===========================================================================
 * $Log:
 * 7    360Commerce 1.6         5/29/2008 4:07:26 PM   Deepti Sharma   CR-31672
 *       changes for instant credit enrollment. Code reviewed by Alan Sinton
 * 6    360Commerce 1.5         8/24/2007 10:55:06 AM  Mathews Kochummen fix
 *      dob
 * 5    360Commerce 1.4         2/6/2007 2:48:14 PM    Edward B. Thorne Merge
 *      from ValidateCustomerInfoAisle.java, Revision 1.2.1.0 
 * 4    360Commerce 1.3         12/17/2006 4:08:50 PM  Brett J. Larsen CR 21298
 *       - country code appearing where country name should appear
 * 3    360Commerce 1.2         3/31/2005 4:30:41 PM   Robert Pearse   
 * 2    360Commerce 1.1         3/10/2005 10:26:39 AM  Robert Pearse   
 * 1    360Commerce 1.0         2/11/2005 12:15:28 PM  Robert Pearse   
 *
 *Revision 1.4.4.1  2004/10/22 20:59:21  cdb
 *@scr 7435 Updated to validate zip code by country, Brenda Acosta's implementation.
 *Merging trunk 1.5 with branch.
 *
 *Revision 1.5  2004/10/22 20:29:47  cdb
 *@scr 7435 Updated to validate zip code by country, Brenda Acosta's implementation.
 *
 *Revision 1.4  2004/03/03 23:15:15  bwf
 *@scr 0 Fixed CommonLetterIfc deprecations.
 *
 *Revision 1.3  2004/02/12 16:50:42  mcs
 *Forcing head revision
 *
 *Revision 1.2  2004/02/11 21:51:22  rhafernik
 *@scr 0 Log4J conversion and code cleanup
 *
 *Revision 1.1.1.1  2004/02/11 01:04:17  cschellenger
 *updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.3   Nov 24 2003 19:50:04   nrao
 * Code Review Changes.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.instantcredit.enrollment;

import java.util.Calendar;
import java.util.Vector;
import java.util.zip.DataFormatException;

import org.apache.log4j.Logger;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.utility.AddressConstantsIfc;
import oracle.retail.stores.domain.utility.AddressIfc;
import oracle.retail.stores.domain.utility.EYSDate;
import oracle.retail.stores.domain.utility.PhoneConstantsIfc;
import oracle.retail.stores.domain.utility.PhoneIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.application.LaneActionAdapter;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.instantcredit.InstantCreditCargo;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;
import oracle.retail.stores.pos.ui.beans.InstantCreditCustomerBeanModel;
import oracle.retail.stores.pos.utility.ValidationUtility;

/**
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public class ValidateCustomerInfoAisle extends LaneActionAdapter
{
    private static final long serialVersionUID = -6305327706347692104L;

    /** The logger to which log messages will be sent */
    protected static final Logger logger = Logger.getLogger(ValidateCustomerInfoAisle.class);

    /** revision number */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";
    
    public static final String INVALID_DATE_OF_BIRTH = "InvalidDOB.InvalidDateOfBirth";

    public static final String INVALID_POSTAL = "InvalidPostalCode";

    public static final String POSTAL_FIELD = "zipCodeField";

    public static final String DOB_FIELD = "dobField";
    
    public static final String INVALID_DOB = "InvalidDOB";
    
    public static final String REDO = "Redo";
    
    public static final String AGE_REQUIREMENT = "InvalidDOB.AgeRequirement";
    
    public static final String INVALID_SSN = "InvalidSSN";
    
    public static final String SSN_FIELD = "ssnField";
    
    public static final String FRANK = "Frank";
    
    public static final int DEFAULT_MIN_AGE_FOR_ENROLL = 18;
    
    public static final String MIN_AGE_FOR_ENROLL = "MinAgeForEnroll";

    /**
     * @param bus Service Bus
     */
    @Override
    public void traverse(BusIfc bus)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        UtilityManagerIfc utility = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);
        ParameterManagerIfc pm = (ParameterManagerIfc) bus.getManager(ParameterManagerIfc.TYPE);
        InstantCreditCustomerBeanModel model = (InstantCreditCustomerBeanModel)ui.getModel();
        InstantCreditCargo cargo = (InstantCreditCargo)bus.getCargo();
        cargo.setFirstRun(false);

        boolean valid = true;

        // validate DOB
        EYSDate dob = model.getDob();
        //I18N - the UI for birthdate now accepts a 2 digits year, meaning that
        //by default years like 19 get translated by Java as 2019, and we actually want a value of 1919
        // so a check has been added : if year is greater than current year, we subtract 100 years from the java created year
        // thus a value of 2019 becomes 1919
        EYSDate now = new EYSDate();
        if (dob != null && dob.getYear() > now.getYear())
        {
        	dob.setYear(dob.getYear()-100);
        }
        EYSDate dob18 = (EYSDate)dob.clone();

        int minAgeForEnroll = DEFAULT_MIN_AGE_FOR_ENROLL;
        try
        {
            minAgeForEnroll = pm.getIntegerValue(MIN_AGE_FOR_ENROLL).intValue();
        }
        catch(ParameterException pe)
        {
            logger.warn("Unable to retrieve parameter " + MIN_AGE_FOR_ENROLL 
                    + " Using default age of " + DEFAULT_MIN_AGE_FOR_ENROLL + "\n"
                    + pe.getStackTraceAsString());
        }
        
        dob18.add(Calendar.YEAR, minAgeForEnroll);
        EYSDate today = DomainGateway.getFactory().getEYSDateInstance();
        
        if(today.before(dob18))
        {
            valid = false;
            String argText = utility.retrieveDialogText(AGE_REQUIREMENT,
                                                        "The applicant does not meet the age requirement.");
            String args[] =
            {
                argText
            };
            model.setFieldInErrorName(DOB_FIELD);
            displayErrorDialog(ui, INVALID_DOB, REDO, DialogScreensIfc.CONFIRMATION, args);
        }
        else
        // validate SSN
        if (!ValidationUtility.checkSSN(model.getSsn()))
        {
            valid = false;
            model.setFieldInErrorName(SSN_FIELD);
            displayErrorDialog(ui, INVALID_SSN, REDO, DialogScreensIfc.ERROR, null);
        }

        //Validate PostalCode
        if (!validatePostalCode(model.getZipCode(), model.getCountry()))
        {
            valid = false;
            model.setFieldInErrorName(POSTAL_FIELD);
            displayErrorDialog(ui, INVALID_POSTAL, REDO, DialogScreensIfc.ERROR, null); 
        }
        if(valid)
        {
            // Populate customer with screen data
            CustomerIfc cust = buildCustomer(model);
            cargo.setSsn(model.getSsn());
            cargo.setCustomer(cust);

            bus.mail(new Letter(CommonLetterIfc.SUCCESS), BusIfc.CURRENT);
        }
    }

    /**
     * Display the specified Error Dialog
     * 
     * @param String name of the Error Dialog to display
     * @param POSUIManagerIfc UI Manager to handle the IO
     */
    protected void displayErrorDialog(POSUIManagerIfc ui, String name,
                                      String button, int dialogType, String[] args)
    {
        DialogBeanModel dialogModel = new DialogBeanModel();
        dialogModel.setResourceID(name);
        dialogModel.setType(dialogType);
        if (args != null)
        {
            dialogModel.setArgs(args);
        }
        dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_OK, button);
        dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_YES, button);
        dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_NO, CommonLetterIfc.CANCEL);
        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
    }

    /**
     * Builds customer from screen data
     * 
     * @param InstantCreditCustomerBeanModel model
     * @return CustomerIfc cust
     */
    protected CustomerIfc buildCustomer(InstantCreditCustomerBeanModel model)
    {
        CustomerIfc cust = DomainGateway.getFactory().getCustomerInstance();
        AddressIfc address = DomainGateway.getFactory().getAddressInstance();
        PhoneIfc phone = DomainGateway.getFactory().getPhoneInstance();

        // name
        cust.setFirstName(model.getFirstName());
        cust.setLastName(model.getLastName());

        // address
        Vector<String> lines = new Vector<String>(3);
        lines.add(model.getStreet1());
        lines.add(model.getStreet2());
        lines.add(model.getStreet3());
        address.setAddressType(AddressConstantsIfc.ADDRESS_TYPE_HOME);
        address.setLines(lines);
        address.setCity(model.getCity());
        address.setState(model.getState());
        address.setCountry(model.getCountry());
        address.setPostalCode(model.getZipCode());
        address.setPostalCodeExtension(model.getExtZipCode());
        Vector<AddressIfc> addresses = new Vector<AddressIfc>(1);
        addresses.add(address);
        cust.setAddresses(addresses);
        EYSDate eys = new EYSDate(model.getDob().dateValue());
        cust.setBirthdate(eys);

        // home phone
        phone.parseString(model.getHomePhone());
        phone.setPhoneType(PhoneConstantsIfc.PHONE_TYPE_HOME);

        // phones vector
        Vector<PhoneIfc> phones = new Vector<PhoneIfc>(1);
        phones.add(phone);
        cust.setPhones(phones);
        
        // set income, app signed and reference number on the customer object
        cust.setYearlyIncome(model.getYearlyIncome());
        cust.setAppReferenceNumber(model.getAppReference());
        cust.setAppSigned(model.getAppSigned());

        return cust;
    }

    /**
     * Validates postal code according to country
     * 
     * @param String zipCode
     * @param String country
     * @return boolean true if valid false otherwise
     */
    protected boolean validatePostalCode(String zipCode, String country)
    {
      boolean isValid = true;
      try
      {
        AddressIfc address = DomainGateway.getFactory().getAddressInstance();
        address.validatePostalCode(zipCode, country);
      }
      catch (DataFormatException e)
      {
         isValid = false; 
      }
      return isValid;
    }
}
