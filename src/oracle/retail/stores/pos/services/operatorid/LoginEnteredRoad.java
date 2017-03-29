/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/operatorid/LoginEnteredRoad.java /main/7 2011/02/28 18:44:36 blarsen Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   02/28/11 - Changed code to use the new verifyFingerprintMatch
 *                         POSDeviceAction rather than accessing the session
 *                         directly. This is more consistent with other devices
 *                         and avoids saving the session in the model.
 *    hyin      02/18/11 - redo the db call part to improve the performance
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    blarsen   02/07/11 - Adding support for login id entry via barcode scan.
 *    blarsen   02/04/11 - a fingerprint reader method was renamed to aid
 *                         clarity.
 *    blarsen   05/17/10 - login entered road
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.operatorid;


import oracle.retail.stores.common.parameter.ParameterConstantsIfc;
import oracle.retail.stores.common.utility.Util;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.EmployeeTransaction;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.transaction.SearchCriteriaIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.device.FingerprintReaderModel;
import oracle.retail.stores.foundation.manager.device.MSRModel;
import oracle.retail.stores.foundation.manager.ifc.KeyStoreEncryptionManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.application.LaneActionAdapter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.service.SessionBusIfc;
import oracle.retail.stores.keystoreencryption.EncryptionServiceException;
import oracle.retail.stores.pos.device.POSDeviceActions;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;
import oracle.retail.stores.pos.utility.FingerprintUtility;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * This road is traveled when the employee ID has been entered. It stores the
 * employee ID in the cargo.
 * 
 * @version $Revision: /main/7 $
 */
public class LoginEnteredRoad extends LaneActionAdapter
{

    private static final long serialVersionUID = -2058715063092291343L;

    public static String revisionNumber = "$Revision: /main/7 $";

    /**
     * Stores the employee ID in the cargo.
     * 
     * @param bus Service Bus
     */
    @Override
    public void traverse(BusIfc bus)
    {
        /*
         * Get the input value from the UI Manager
         */
        POSUIManagerIfc ui;
        ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        POSBaseBeanModel model = (POSBaseBeanModel) ui.getModel(POSUIManagerIfc.OPERATOR_LOGIN);
        PromptAndResponseModel pAndRModel = model.getPromptAndResponseModel();
        POSDeviceActions pda = new POSDeviceActions((SessionBusIfc) bus);
        
        OperatorIdCargo cargo = (OperatorIdCargo)bus.getCargo();
        
        // the following is here only so that testing can be done on 
        // swiping of credit cards as employee login cards.  this 
        // can be removed once we have real employee login cards.
        ParameterManagerIfc pm = (ParameterManagerIfc) bus.getManager(ParameterManagerIfc.TYPE);
        try
        {
            if(pAndRModel.isFingerprintRead()) 
            {
                EmployeeIfc[] employeeArray = null;
                    
                EmployeeTransaction empTransaction = null;
                empTransaction = (EmployeeTransaction) DataTransactionFactory.create(DataTransactionKeys.EMPLOYEE_TRANSACTION);
                SearchCriteriaIfc inquiry = DomainGateway.getFactory().getSearchCriteriaInstance();
                try
                {
                    boolean isLoginIDRequired = 
                        ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_NO_FINGERPRINT.equals(
                                pm.getStringValue(ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions)) ||
                        ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_ID_AND_FINGERPRINT.equals(
                                pm.getStringValue(ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions));
                    if (StringUtils.isEmpty(cargo.getEmployeeID()) && isLoginIDRequired)
                    {
                        logger.warn("Login ID required.  But, no login ID was specified.");
                    }
                    else if (!StringUtils.isEmpty(cargo.getEmployeeID()))
                    {
                        EmployeeIfc employee = empTransaction.getEmployee(cargo.getEmployeeID());
 
                        employeeArray = new EmployeeIfc[1];
                        employeeArray[0] = employee;
                    }
                    else
                    {
                    	inquiry.setFingerprintFullEmployeeListMode(true);
                        employeeArray = empTransaction.selectEmployees(inquiry);
                    }
                }
                catch (DataException e)
                {
                    logger.error("Problem retrieving employees for fingerprint comparison", e);
                }
                
                FingerprintReaderModel fingerprintModel = pAndRModel.getFingerprintModel();
               
                if (employeeArray == null) 
                {
                    logger.error("No employees to compare fingerprint against");
                }
                else if (fingerprintModel == null || fingerprintModel.getFingerprintData() == null || fingerprintModel.getFingerprintData().length == 0)
                {
                    logger.error("No fingerprint to compare against employees");
                }
                else
                {
                    cargo.setEmployeeID("");
                    for (EmployeeIfc employee: employeeArray)
                    {
                        if (FingerprintUtility.verifyFingerprintMatch(pda, employee.getFingerprintBiometrics(), fingerprintModel.getFingerprintData()))
                        {
                            cargo.setEmployeeID(employee.getLoginID());
                            break;
                        }
                    }
                }
            }
            else if (pAndRModel.isScanned())
            {
                cargo.setEmployeeID(pAndRModel.getResponseText());
            }
            else if(pAndRModel.isSwiped()) 
            {
                if(pm.getStringValue("AutomaticEntryID").equals("User"))
                {
                    String loginID = null;
                    UtilityManagerIfc util = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);  
                    loginID = util.getEmployeeFromModel(pAndRModel);
                    cargo.setEmployeeID(loginID);
                }
                else
                {
                    MSRModel msrModel = pAndRModel.getMSRModel();
                    String employeeID = null;
                    try
                    {
                        KeyStoreEncryptionManagerIfc encryptionManager =
                            (KeyStoreEncryptionManagerIfc)Gateway.getDispatcher().getManager(KeyStoreEncryptionManagerIfc.TYPE);
                        byte[] eID = encryptionManager.decrypt(Base64.decodeBase64(msrModel.getEncipheredCardData().getEncryptedAcctNumber().getBytes()));
                        // What if operator mistakenly swipes a credit card on this screen.  We can only
                        // assume that what was swiped is an employee card. Since employee ID are 10 chars
                        // or less we can chop off and throw away the excess.
                        if(eID.length > 10)
                        {
                            // not using System.arraycopy() since this is potentially sensitive data.
                            byte[] tmpID = new byte[10];
                            for(int i = 0; i < tmpID.length; i++)
                            {
                                tmpID[i] = eID[i];
                            }
                            employeeID = new String(tmpID);
                            // clear eID
                            Util.flushByteArray(eID);
                            eID = null;
                            // clear tmpID
                            Util.flushByteArray(tmpID);
                            tmpID = null;
                        }
                        else
                        {
                            employeeID = new String(eID);
                        }
                        cargo.setEmployeeID(employeeID);
                    }
                    catch(EncryptionServiceException ese)
                    {
                        logger.error("Could not decrypt employee ID", ese);
                    }
                }
            }
            
        }
        catch (ParameterException pe)
        {
            logger.error("Error getting parameter AutomaticEntryID", pe);
        }
    }


    /**

     * Returns the revision number of the class.
     * 
     * @return String representation of revision number
     */
    public String getRevisionNumber()
    {
        // return string
        return (revisionNumber);
    }
}