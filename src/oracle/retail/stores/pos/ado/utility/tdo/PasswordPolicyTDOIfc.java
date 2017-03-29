/* ===========================================================================
* Copyright (c) 2006, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/utility/tdo/PasswordPolicyTDOIfc.java /rgbustores_13.4x_generic_branch/1 2011/04/11 11:48:40 abondala Exp $
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
 *    3    360Commerce 1.2         11/2/2006 7:06:30 AM   Rohit Sachdeva
 *         21237: Activating Password Policy Evaluation and Change Password 
 *    2    360Commerce 1.1         10/25/2006 3:12:05 PM  Rohit Sachdeva
 *         21237: Password Policy TDO updates
 *    1    360Commerce 1.0         10/17/2006 4:10:54 PM  Rohit Sachdeva  
 *
 *   
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.utility.tdo;


import oracle.retail.stores.commerceservices.security.EmployeeStatusEnum;
import oracle.retail.stores.commerceservices.security.PasswordEvaluationResultEnum;
import oracle.retail.stores.pos.tdo.TDOIfc;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;

//------------------------------------------------------------------------------
/**
    This TDO is used for Password Policy various evaluations and
    its affects in terms of flow in the application.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public interface PasswordPolicyTDOIfc extends TDOIfc
{    
    /**
     * revision number
     */
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    
    /** 
     * Spring Key used to load this bean
     */ 
    public static final String PASSWORD_POLICY_TDO_BEAN_KEY = "application_PasswordPolicyTDO";
    
   //----------------------------------------------------------------------
    /**
       Checks if Employee Compliance is Allowed.
       @param bus reference to bus
       @return true if employee compliance is allowed, otherwise false
    **/
    //----------------------------------------------------------------------
    public boolean checkEmployeeComplianceEvaluationAllowed(BusIfc bus);
    
   //----------------------------------------------------------------------
    /**
       Checks if employee passes basic checks for applying password policy
       @param employee reference to employee
       @return true if employee passes basic checks, otherwise false
    **/
    //----------------------------------------------------------------------
    public boolean checkEmployeeApplyPolicy(EmployeeIfc employee);
    
   //----------------------------------------------------------------------
    /**
       Checks Manual Entry Requires Password. 
       @param bus reference to bus
       @return true if as per meaning of the parameter password is required
    **/
    //----------------------------------------------------------------------
    public boolean  checkPasswordParameter(BusIfc bus);
    
    //--------------------------------------------------------------------------
    /**
     * Checks Change Password Required based on first time login
     * after password reset. This uses the Password Policy Service
     * through the Security Manager evaluateEmployeeCompliance
     * @param bus reference to bus
     * @return true if a password change is needed; false otherwise
     */
    //--------------------------------------------------------------------------
    public boolean checkPasswordChangeByFirstTime(BusIfc bus);
    
    //----------------------------------------------------------------------
    /**
       This is a Convenience method that  calls employee compliance
       evaluation and further checks for lockout, since at lock out, the 
       Employee cannot proceed further in the Application. 
       @param bus reference to bus
       @param employee reference to employee  
       @param loggedInUser true if this is used for a user who could login
              successfully, otherwise false     
       @return true if logged in user is locked out, false otherwise
    **/
    //----------------------------------------------------------------------
    public boolean employeeComplianceEvaluation(BusIfc bus,
                                                EmployeeIfc employee,
                                                boolean loggedInUser);
    
    //----------------------------------------------------------------------
    /**
       Read Employee Compliance Password Policy for the Employee.
       Currently there is one default Password Policy for all employees.
       @param bus reference to bus
       @param employee reference to employee
    **/
    //----------------------------------------------------------------------
    public void readEmployeeCompliance(BusIfc bus, EmployeeIfc employee);
    
     //----------------------------------------------------------------------
    /**
     * Processes to retrieve Employee Compliance Status.
     * @param employee reference to employee
     * @param securityManager security manager reference
     * @param bus reference to bus
     * @return EmployeeStatusEnum employee status enum
     */
    //----------------------------------------------------------------------
    public EmployeeStatusEnum retrieveEmployeeComplianceStatus(EmployeeIfc employee, 
                                                              BusIfc bus);
    
   //----------------------------------------------------------------------
    /**
     * Processes to retrieve Password Compliance Status
     * @param newPassword new password
     * @param employee reference to employee
     * @param bus reference to bus
     * @return EmployeeStatusEnum employee status enum
     */
    //----------------------------------------------------------------------
    public PasswordEvaluationResultEnum retrievePasswordComplianceStatus(String newPassword,
                                                                         EmployeeIfc employee, 
                                                                         BusIfc bus);
    
   //----------------------------------------------------------------------
    /**
     * Inserts Password History
     * @param employee
     *            reference to employee
     * @throws DataException data exception
     * @return boolean updates were successful
     */
    //----------------------------------------------------------------------
    public boolean updateEmployeeInsertPasswordHistory(EmployeeIfc employee)
                                                          throws DataException;
    
   //----------------------------------------------------------------------
    /**
     * Reads Password History for the employee
     * @param employee reference to employee
     * @return employee with password history
     * @throws DataException data exception
     */
    //----------------------------------------------------------------------
    public EmployeeIfc readPasswordHistory(EmployeeIfc employee)
                                            throws DataException;
    
}
