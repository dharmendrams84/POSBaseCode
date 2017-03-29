/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/EmployeeCargoIfc.java /main/13 2011/02/25 15:24:53 hyin Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    hyin      02/25/11 - add check for fingerprint change status
 *    blarsen   02/23/11 - added accessors for fingerprint enrollment template
 *                         which is stored on the cargo by
 *                         FingerprintEnrollAisle and later used by
 *                         FingerprintVerifyAisle.
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *4    360Commerce 1.3         10/12/2006 8:17:49 AM  Christian Greene Adding
 *     new functionality for PasswordPolicy.  Employee password will now be
 *     persisted as a byte[] in hexadecimal.  Updates include UI changes,
 *     persistence changes, and AppServer configuration changes.  A database
 *     rebuild with the new SQL scripts will be required.
 *3    360Commerce 1.2         3/31/2005 4:27:56 PM   Robert Pearse   
 *2    360Commerce 1.1         3/10/2005 10:21:17 AM  Robert Pearse   
 *1    360Commerce 1.0         2/11/2005 12:10:48 PM  Robert Pearse   
 *
 Revision 1.2  2004/02/12 16:48:00  mcs
 Forcing head revision
 *
 Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   08 Nov 2003 01:04:44   baa
 * cleanup -sale refactoring
 * 
 *    Rev 1.0   Nov 04 2003 19:00:02   cdb
 * Initial revision.
 * Resolution for 3430: Sale Service Refactoring
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

import oracle.retail.stores.domain.employee.EmployeeIfc;

/**
 * This interface defines methods used when a cargo works with an employee.
 * 
 * @version $Revision: /main/13 $
 */
public interface EmployeeCargoIfc extends DBErrorCargoIfc
{
    /**
     * Returns the employee's ID.
     * 
     * @return The employee's ID.
     */
    public String getEmployeeID();

    /**
     * Sets the employee's ID.
     * 
     * @param employeeID The employee's ID.
     */
    public void setEmployeeID(String employeeID);

    /**
     * Returns the employee
     * 
     * @return The employee
     */
    public EmployeeIfc getEmployee();

    /**
     * Sets the employee.
     * 
     * @param employee The employee.
     */
    public void setEmployee(EmployeeIfc employee);

    /**
     * Returns the fingerprint enrollment template
     * <P>
     * The template is stored on the cargo until it is considered valid.
     * <br>
     * After it is validated, it is assigned to the employee being edited.
     * <P>
     * @return The fingerprint enrollment template
     */
    public byte[] getFingerprintEnrollmentTemplate();

    /**
     * Sets the fingerprint enrollment template.
     * <P>
     * The template is stored on the cargo until it is considered valid.
     * <br>
     * After it is validated, it is assigned to the employee being edited.
     * <P> 
     * @param fingerprintEnrollmentTemplate The fingerprint enrollment template.
     */
    public void setFingerprintEnrollmentTemplate(byte[] fingerprintEnrollmentTemplate);
    
    /**
     * When a new fingerprint is enrolled, we set this
     * value to true.
     * <p>
     * Returns enrolledNewFingerprint
     * @return the enrolledNewFingerprint
     */
    public boolean isEnrolledNewFingerprint(); 

    /**
     * When a new fingerprint is enrolled, we set this
     * value to true.
     * <p>
     * Sets enrolledNewFingerprint
     * @param enrolledNewFingerprint the enrolledNewFingerprint to set
     */
    public void setEnrolledNewFingerprint(boolean enrolledNewFingerprint); 
    

}
