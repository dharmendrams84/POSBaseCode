/*===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
* ===========================================================================
* $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/InstantCreditInquiryCriteriaBeanModel.java /rgbustores_13.4x_generic_branch/2 2011/06/03 10:23:37 sgu Exp $
* ===========================================================================
* NOTES
* <other useful comments, qualifications, etc.>
*
* MODIFIED    (MM/DD/YY)
* sgu         05/20/11 - refactor instant credit inquiry flow
* sgu         05/18/11 - add new class
* sgu         05/18/11 - Creation
* ===========================================================================
*/

package oracle.retail.stores.pos.ui.beans;

// foundation imports

//----------------------------------------------------------------------------
/**
    Data transport between the bean and the application for instant credit data
    $Revision: /rgbustores_13.4x_generic_branch/2 $
**/
//----------------------------------------------------------------------------
public class InstantCreditInquiryCriteriaBeanModel extends ReasonBeanModel
{
    // customer information fields
    protected String postalCode;
    protected String ssn;
    protected String homePhone;
    protected String referenceNumber;
    protected boolean referenceNumberSearch = true;
    protected boolean firstRun = true;

    //---------------------------------------------------------------------
    /**
        Gets the postal code attribute. <P>
    **/
    //---------------------------------------------------------------------
    public String getPostalCode()
    {
        return postalCode;
    }

    //---------------------------------------------------------------------
    /**
        Sets the postal code attribute. <P>
        @param zipCode  string to set zip code

    **/
    //---------------------------------------------------------------------
    public void setPostalCode(String zipCode)
    {
        this.postalCode = zipCode;
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
        Sets the application reference attribute. <P>
        @param appReference  string to set application reference

    **/
    //---------------------------------------------------------------------
    public String getReferenceNumber()
    {
        return referenceNumber;
    }

    //---------------------------------------------------------------------
    /**
        Sets the application reference attribute. <P>
        @param appReference  string to set application reference

    **/
    //---------------------------------------------------------------------
    public void setReferenceNumber(String referenceNumber)
    {
        this.referenceNumber = referenceNumber;
    }

    //---------------------------------------------------------------------
    /**
         Reset all fields
    **/
    //---------------------------------------------------------------------
    public void reset()
    {
        postalCode = "";
        ssn = "";
        homePhone = "";
        referenceNumber = "";
    }

    //---------------------------------------------------------------------
    /**
        Gets the first run attribute. <P>

    **/
    //---------------------------------------------------------------------
    public boolean isFirstRun()
    {
        return firstRun;
    }

    //---------------------------------------------------------------------
    /**
        Sets the first run attribute. <P>
        @param firstRun  string to set first run.

    **/
    //---------------------------------------------------------------------
    public void setFirstRun(boolean firstRun)
    {
        this.firstRun = firstRun;
    }

    public boolean isReferenceNumberSearch()
    {
        return referenceNumberSearch;
    }

    public void setReferenceNumberSearch(boolean referenceNumberSearch)
    {
        this.referenceNumberSearch = referenceNumberSearch;
    }
                                  // end geteditableFields()
}