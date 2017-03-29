/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/utility/UtilityIfc.java /rgbustores_13.4x_generic_branch/2 2011/07/20 04:33:39 rrkohli Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    rrkohli   07/19/11 - encryption CR
 *    blarsen   06/22/11 - Added isOvertenderAllowed() method.
 *    blarsen   06/22/11 - Added another (overloaded) isStringListed method.
 *    mkutiana  02/22/11 - Added logic to get the correct password policy and
 *                         if fingerprints for login are allowed
 *    mchellap  07/30/10 - Added validation for customer id expiration date
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    mdecama   11/04/08 - I18N - Deprecated Methods

     $Log:
      4    360Commerce 1.3         3/29/2007 4:00:27 PM   Michael Boyd    CR
           26172 - v8x merge to trunk


           4    .v8x      1.2.1.0     3/11/2007 4:51:18 PM   Brett J. Larsen
           CR 4530
           - adding support for retrieval of devault code list value -
           several site/model/beans do not work direclty with CodeList
      3    360Commerce 1.2         3/31/2005 4:30:41 PM   Robert Pearse
      2    360Commerce 1.1         3/10/2005 10:26:38 AM  Robert Pearse
      1    360Commerce 1.0         2/11/2005 12:15:27 PM  Robert Pearse
     $
     Revision 1.4  2004/07/14 18:47:09  epd
     @scr 5955 Addressed issues with Utility class by making constructor protected and changing all usages to use factory method rather than direct instantiation

     Revision 1.3  2004/07/12 21:30:34  bwf
     @scr 6125 Made available expiration validation of debit before pin.

     Revision 1.2  2004/02/12 16:47:58  mcs
     Forcing head revision

     Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
     updating to pvcs 360store-current


 *
 *    Rev 1.1   Feb 05 2004 13:24:22   rhafernik
 * log4j conversion
 *
 *    Rev 1.0   Dec 17 2003 14:27:14   epd
 * Initial revision.

* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package oracle.retail.stores.pos.ado.utility;
import java.util.Vector;

import oracle.retail.stores.pos.ado.tender.TenderException;
import oracle.retail.stores.pos.ado.utility.tdo.PasswordPolicyTDOIfc;
import oracle.retail.stores.domain.utility.EYSDate;
/**
 *
 */
public interface UtilityIfc
{
    /**
     * Retrieves a parameter value from the parameter subsystem
     * @param parameterName The parameter to retrieve
     * @param defaultValue A default in case the parameter cannot be read
     * @return The retrieved parameter value
     */
    String getParameterValue(String parameterName, String defaultValue);

    /**
     * Retrieves a list of values given a parameter name.
     * @param paramName The parameter to retrieve.
     * @return an array of values for the given parameter.
     */
    String[] getParameterValueList(String paramName);

    /**
       Returns true if the specified string is found in the list, false
       otherwise<P>
       @return boolean if string exists in list
    **/
    boolean isStringListed(String parameterValue, Object[] parameterList);

    /**
     * Returns true if the specified value is is found in the parameter's list of values.
     * 
     * @return boolean if string exists in list
     * 
     **/
    boolean isStringListed(String parameterValue, String parameterName);

    /**
       Gets the ID types from the config file

       @return Vector of ID types retrieved from the config file
     * @deprecated as of 13.1 Use {@link #UtilityManager.getReasonCodes}
     ***/
    public Vector getIDTypes();

    /**
     * Gets the mail bank check ID types from the config file
     *
     * @return Vector of ID types retrieved from the config file
     * @deprecated as of 13.1 Use {@link #UtilityManager.getReasonCodes}
     **/
    public Vector getIDTypes(String codeConstant);

    /**
     * Gets the default mail bank check ID type from the config file
     *
     * @return String containg default ID type retrieved from the config file
     * @deprecated as of 13.1 Use {@link #UtilityManager.getReasonCodes}
     **/
    public String getDefaultIDType(String codeConstant);

    /**
        This validates the drivers license.
        @param state
        @param license
        @param country
        @throws TenderException
    **/
    void validateDriversLicense(byte[] license, String state, String country) throws TenderException;

    /**
     * Takes an expiration date as entered from the UI and converts it into an
     * EYS date
     *
     * @param format
     *            A date format describing the formatting of expirationDateStr
     * @param expirationDateStr
     *            a String to convert
     * @return an EYSDate representative of the expirationDate string
     */
    public EYSDate parseExpirationDate(String format, String expirationDateStr) throws TenderException;

    //----------------------------------------------------------------------
    /**
        This method validates the expiration date.
        @param expirationDate
        @throws TenderException if not a valid expiration date
    **/
    //----------------------------------------------------------------------
    public void validateExpirationDate(String expirationDate) throws TenderException;

    /**
     This method checks if the entered expiry date of ID is valid.Returns false
     if entered expiry date is before the current date. True otherwise.
     @param expirationDate
     @throws TenderException if not a valid expiration date
     **/
    public boolean isValidExpirationDate(String expirationDate) throws TenderException;
    
    /**
     * This method returns the appropriate password policy system to use,
     * for eg, if fingerprinting is turned on via parameter then that specific policy will be returned.  
     * @return PasswordPolicyTDOIfc
     */
    public PasswordPolicyTDOIfc getPasswordPolicyTDO();
    
    /**
     * Checks parameters and returns true if fingerprinting for logging in is turned on.
     * @return boolean - is fingerprint for login allowed
     */
    public boolean isFingerprintAllowed();
    
    /**
     * Checks parameters and returns true if overtender is allowed for the specified tender type.
     * 
     * @param tenderType
     * 
     * @return boolean - is overtender allowed?
     */
    public boolean isOvertenderAllowed(String tenderType);
    
}