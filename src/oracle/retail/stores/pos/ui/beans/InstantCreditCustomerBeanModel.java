/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/InstantCreditCustomerBeanModel.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:43 mszekely Exp $
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
 *    mkochumm  11/19/08 - cleanup based on i18n changes
 *
 * ===========================================================================
 * $Log:
 *  4    360Commerce 1.3         5/29/2008 4:07:26 PM   Deepti Sharma
 *       CR-31672 changes for instant credit enrollment. Code reviewed by Alan
 *        Sinton
 *  3    360Commerce 1.2         3/31/2005 4:28:23 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:22:08 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:11:25 PM  Robert Pearse   
 * $
 * 
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

// foundation imports
import oracle.retail.stores.domain.utility.EYSDate;

//----------------------------------------------------------------------------
/**
    Data transport between the bean and the application for instant credit data
    $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//----------------------------------------------------------------------------
public class InstantCreditCustomerBeanModel extends ReasonBeanModel
{
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    // customer information fields
    protected String firstName = "";
    protected String lastName = "";
    protected String street1;
    protected String street2;
    protected String street3;
    protected String city;
    protected String zipCode;
    protected String extZipCode;
    protected String country;
    protected String state;
    protected String ssn;
    protected String homePhone;
    protected String busPhone;
    protected EYSDate dob;
    protected String yearlyIncome;
    protected String appSigned;
    protected String appReferenceNumber;
    protected boolean firstRun = true;

    /**   editable indicator   **/
    protected boolean editableFields = true;
    
    /**  3 line address indicator **/   
    protected boolean threeLineAddress = false;

    //---------------------------------------------------------------------
    /**
        Gets the first name attribute. <P>
    **/
    //---------------------------------------------------------------------
    public String getFirstName()
    {
        return firstName;
    }

    //---------------------------------------------------------------------
    /**
        Sets the first name attribute. <P>
        @param firstname  string to set first name

    **/
    //---------------------------------------------------------------------
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    //---------------------------------------------------------------------
    /**
        Gets the last name attribute. <P>
    **/
    //---------------------------------------------------------------------
    public String getLastName()
    {
        return lastName;
    }

    //---------------------------------------------------------------------
    /**
        Sets the last name attribute. <P>
        @param lastName  string to set last name

    **/
    //---------------------------------------------------------------------
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    //---------------------------------------------------------------------
    /**
        Gets the 1st line of address attribute. <P>
    **/
    //---------------------------------------------------------------------
    public String getStreet1()
    {
        return street1;
    }

    //---------------------------------------------------------------------
    /**
        Sets the 1st line of address attribute. <P>
        @param street1  string to set street1

    **/
    //---------------------------------------------------------------------
    public void setStreet1(String street1)
    {
        this.street1 = street1;
    }

    //---------------------------------------------------------------------
    /**
        Gets the 2nd line of address attribute. <P>
    **/
    //---------------------------------------------------------------------   
    public String getStreet2()
    {
        return street2;
    }

    //---------------------------------------------------------------------
    /**
        Sets the 2nd line of address attribute. <P>
        @param street2  string to set street2

    **/
    //---------------------------------------------------------------------
    public void setStreet2(String street2)
    {
        this.street2 = street2;
    }
    
    //---------------------------------------------------------------------
    /**
        Gets the 3rd line of address attribute. <P>
    **/
    //---------------------------------------------------------------------    
    public String getStreet3()
    {
        return street3;
    }

    //---------------------------------------------------------------------
    /**
        Sets the 3rd line of address attribute. <P>
        @param street3  string to set street3

    **/
    //---------------------------------------------------------------------
    public void setStreet3(String street3)
    {
        this.street3 = street3;
    }

    //---------------------------------------------------------------------
    /**
        Gets the postal code attribute. <P>
    **/
    //---------------------------------------------------------------------    
    public String getZipCode()
    {
        return zipCode;
    }

    //---------------------------------------------------------------------
    /**
        Sets the postal code attribute. <P>
        @param zipCode  string to set zip code

    **/
    //---------------------------------------------------------------------
    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    //---------------------------------------------------------------------
    /**
        Gets the ssn attribute. <P>
    **/
    //---------------------------------------------------------------------
    public String getSsn()
    {
        return ssn;
    }

    //---------------------------------------------------------------------
    /**
        Sets the ssn attribute. <P>
        @param ssn  string to set ssn

    **/
    //---------------------------------------------------------------------
    public void setSsn(String ssn)
    {
        this.ssn = ssn;
    }

    //---------------------------------------------------------------------
    /**
        Gets the home phone attribute. <P>
    **/
    //---------------------------------------------------------------------
    public String getHomePhone()
    {
        return homePhone;
    }

    //---------------------------------------------------------------------
    /**
        Sets the home phone attribute. <P>
        @param homePhone  string to set homePhone

    **/
    //---------------------------------------------------------------------
    public void setHomePhone(String homePhone)
    {
        this.homePhone = homePhone;
    }

    //---------------------------------------------------------------------
    /**
        Gets the business phone attribute. <P>
    **/
    //---------------------------------------------------------------------
    public String getBusPhone()
    {
        return busPhone;
    }

    //---------------------------------------------------------------------
    /**
        Sets the business phone attribute. <P>
        @param busPhone  string to set busPhone

    **/
    //---------------------------------------------------------------------
    public void setBusPhone(String busPhone)
    {
        this.busPhone = busPhone;
    }
    //---------------------------------------------------------------------
    /**
        Gets the date of birth attribute. <P>
    **/
    //---------------------------------------------------------------------
    public EYSDate getDob()
    {
        return dob;
    }

    //---------------------------------------------------------------------
    /**
        Sets the date of birth attribute. <P>
        @param dob  string to set date of birth

    **/
    //---------------------------------------------------------------------
    public void setDob(EYSDate dob)
    {
        this.dob = dob;
    }
    
    //---------------------------------------------------------------------
    /**
        Sets the yearly income attribute. <P>
        @param yearlyIncome  string to set yearly income
     **/
    //---------------------------------------------------------------------
    
    public String getYearlyIncome() {
		return yearlyIncome;
	}
    
    //---------------------------------------------------------------------
    /**
        Sets the yearly income attribute. <P>
        @ @param yearlyIncome  string to set yearly income
     **/
    //---------------------------------------------------------------------
	public void setYearlyIncome(String yearlyIncome) {
		this.yearlyIncome = yearlyIncome;
	}

	//---------------------------------------------------------------------
    /**
        Sets the application reference attribute. <P>
        @param appReference  string to set application reference

    **/
    //---------------------------------------------------------------------
	public String getAppReference() {
		return appReferenceNumber;
	}
	
	//---------------------------------------------------------------------
    /**
        Sets the application reference attribute. <P>
        @param appReference  string to set application reference

    **/
    //---------------------------------------------------------------------
	public void setAppReference(String appReference) {
		this.appReferenceNumber = appReference;
	}
	
	//---------------------------------------------------------------------
    /**
        Sets the application signed attribute. <P>
        @param appSigned  string to set application reference

    **/
    //---------------------------------------------------------------------
	public String getAppSigned() {
		return appSigned;
	}
	
	//---------------------------------------------------------------------
    /**
        Sets the application reference signed. <P>
        @param appReference  string to set application reference

    **/
    //---------------------------------------------------------------------
	public void setAppSigned(String appSigned) {
		this.appSigned = appSigned;
	}
	public void reset()
    {
        firstName = "";
        lastName = "";
        street1 = "";
        street2 = "";
        street3 = "";
        country = "";
        state = "";
        zipCode = "";
        extZipCode = "";
        ssn = "";
        homePhone = "";
        busPhone = "";
        dob = null;
        yearlyIncome = "";
        appSigned = "";
        appReferenceNumber = "";
    }

    //---------------------------------------------------------------------
    /**
        Gets the first run attribute. <P>

    **/
    //---------------------------------------------------------------------
    public boolean isFirstRun() {
        return firstRun;
    }

    //---------------------------------------------------------------------
    /**
        Sets the first run attribute. <P>
        @param firstRun  string to set first run.

    **/
    //---------------------------------------------------------------------
    public void setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
    }

    /**
     * Gets the city attribute
     * @return returns city attribute
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Sets the city attribute.
     * @param city string to set city.
     */
    public void setCity(String city)
    {
        this.city = city;
    }
    
    //---------------------------------------------------------------------
    /**
        Sets the editableFields attribute. <P>
        @param enabled  boolean to set editableFields

    **/
    //---------------------------------------------------------------------
    public void setEditableFields(boolean value)
    {                                   // begin seteditableFields()
        editableFields = value;
    }                                  // end seteditableFields()

    //---------------------------------------------------------------------
    /**
        Get the editableFields attribute. <P>
        @return boolean  editableFields returned

    **/
    //---------------------------------------------------------------------
    public boolean getEditableFields()
    {                                   // begin geteditableFields()
        return(editableFields);
    }   
                                   // end geteditableFields()

    /**
     * Returns the attribute for 3 line address.
     * @return boolean threeLineAddress
     */
    public boolean isThreeLineAddress()
    {
        return threeLineAddress;
    }

    /**
     * sets the boolean for 3 lines address.
     * @param b boolean to see if address is 3 lines
     */
    public void setThreeLineAddress(boolean b)
    {
        threeLineAddress = b;
    }

    /**
     * Gets the attribute for zip code extension
     * @return String zip code extension
     * @deprecated since v13.1
     */
    public String getExtZipCode()
    {
        return extZipCode;
    }

    /**
     * Sets the attribute for zip code extension.
     * @param ext zip code extension
     * @deprecated since v13.1
     */
    public void setExtZipCode(String ext)
    {
        extZipCode = ext;
    }

}
