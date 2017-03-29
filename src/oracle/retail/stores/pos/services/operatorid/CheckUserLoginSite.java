/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/operatorid/CheckUserLoginSite.java /rgbustores_13.4x_generic_branch/1 2011/04/11 11:48:40 abondala Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    abondala  04/11/11 - XbranchMerge abondala_bug11827952-salting_passwords
 *                         from main
 *    abondala  03/25/11 - implement salting for the passwords
 *    abondala  03/23/11 - Implemented salting for the passwords
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    7    360Commerce 1.6         11/12/2007 2:14:22 PM  Tony Zgarba
 *         Deprecated all existing encryption APIs and migrated the code to
 *         the new encryption API.
 *    6    360Commerce 1.5         2/6/2007 2:48:36 PM    Edward B. Thorne
 *         Merge from CheckUserLoginSite.java, Revision 1.3.1.0 
 *    5    360Commerce 1.4         12/18/2006 4:15:50 PM  Rohit Sachdeva
 *         24006: Tender Check Hash Password for Manager Override
 *    4    360Commerce 1.3         10/12/2006 8:17:50 AM  Christian Greene
 *         Adding new functionality for PasswordPolicy.  Employee password
 *         will now be persisted as a byte[] in hexadecimal.  Updates include
 *         UI changes, persistence changes, and AppServer configuration
 *         changes.  A database rebuild with the new SQL scripts will be
 *         required.
 *    3    360Commerce 1.2         3/31/2005 4:27:27 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:14 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:00 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:51:19  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:48  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Oct 17 2003 12:59:30   epd
 * Initial revision.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.operatorid;

// Java imports
import java.util.Map;

import javax.security.auth.login.LoginException;

import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.EmployeeTransaction;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.manager.ifc.SecurityManagerIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.KeyStoreEncryptionManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.keystoreencryption.EncryptionServiceException;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;

//--------------------------------------------------------------------------
/**
    This site validates the employee ID.
    <p>
     @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class CheckUserLoginSite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = 1L;

    /**
       revision number of this class
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
       This site confirms that the supplied username and password
       are valid.  If valid, it takes the returned EmployeeIfc and
       saves it in cargo.
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        /*
         * Set local variables
         */
        OperatorIdCargo cargo = (OperatorIdCargo) bus.getCargo();
        String letter = CommonLetterIfc.SUCCESS;


        // Use SecurityManager to determine if user is logged in
        SecurityManagerIfc securityManager = null;
        securityManager = (SecurityManagerIfc) Gateway.getDispatcher().getManager(SecurityManagerIfc.TYPE);
        
        EmployeeIfc employee = null;
        try
        {
            EmployeeTransaction empTransaction = null;
            
            empTransaction = (EmployeeTransaction) DataTransactionFactory.create(DataTransactionKeys.EMPLOYEE_TRANSACTION);
            employee = empTransaction.getEmployee(cargo.getEmployeeID());
        }
        catch (DataException de)
        {
            // log the error; set the error code in the cargo for future use.
            logger.error(
                         "EmployeeID '" + cargo.getEmployeeID() + "' error: " + de.getMessage() + "\n\t Error code = " +
                         Integer.toString(de.getErrorCode()) + "");
            cargo.setDataExceptionErrorCode(de.getErrorCode());
            bus.mail(new Letter("Failure"), BusIfc.CURRENT);
        }

        byte[] enteredPassword = null;
        String pwdSalt = "";
        try
        {
            if(employee != null && employee.getEmployeePasswordSalt() != null)
            {
                pwdSalt = employee.getEmployeePasswordSalt();
            }
            
            enteredPassword = hashEnteredPassword(bus, cargo.getEmployeePasswordBytes(), pwdSalt);
        }
        catch (LoginException le)
        {
            logger.warn(
                "CheckUserLoginSite.arrive(): LoginException "
                    + le.getMessage()
                    + "");
        }


        Map result = securityManager.checkLoginForUser(cargo.getAppID(),
                                                       cargo.getEmployeeID(),
                                                       enteredPassword);

        // Check to see if we got a logged in user
        if (((Boolean)result.get(SecurityManagerIfc.VALID_USER)).equals(Boolean.TRUE))
        {
            // get the employee and save it in cargo
            cargo.setSelectedEmployee((EmployeeIfc)result.get(SecurityManagerIfc.EMPLOYEE));
        }
        else
        {
            letter = CommonLetterIfc.FAILURE;
        }

        bus.mail(new Letter(letter), BusIfc.CURRENT);
    }

    //--------------------------------------------------------------------------
    /**
     * Hashing entered password
     * @param bus reference to bus
     * @param enteredPassword entered password bytes
     * String salt
     * @throws LoginException login exception
     */
    //--------------------------------------------------------------------------
    private byte[] hashEnteredPassword(BusIfc bus, byte[] enteredPassword, String salt) throws LoginException
    {
        OperatorIdCargo cargo = (OperatorIdCargo) bus.getCargo();
        if (!cargo.isPasswordEncrypted() && enteredPassword != null)
        {
            try
            {
                KeyStoreEncryptionManagerIfc cryptoManager = (KeyStoreEncryptionManagerIfc)bus.getManager(KeyStoreEncryptionManagerIfc.TYPE);
                enteredPassword = cryptoManager.superHash(enteredPassword, salt, false);

            }
            catch (EncryptionServiceException ve)
            {
                logger.error("Could not hash entered password", ve);
                throw new LoginException(ve.getMessage());
            }
        }
        return enteredPassword;
    }
}
