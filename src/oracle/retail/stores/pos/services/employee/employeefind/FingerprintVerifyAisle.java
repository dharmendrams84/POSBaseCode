/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/employee/employeefind/FingerprintVerifyAisle.java /main/4 2011/02/28 18:44:35 blarsen Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   02/28/11 - Changed code to use the new verifyFingerprintMatch
 *                         POSDeviceAction rather than accessing the session
 *                         directly. This is more consistent with other devices
 *                         and avoids saving the session in the model.
 *    hyin      02/25/11 - set attribute in cargo when a new fingeprint is
 *                         enrolled
 *    blarsen   02/25/11 - Using the new/fast get-all-employee-fingerprints
 *                         queury
 *    hyin      02/24/11 - move ej and audit log to different site
 *    hyin      02/24/11 - log ejournal entry
 *    blarsen   02/15/11 - Fingerprint Verify Aisle
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.employee.employeefind;

import oracle.retail.stores.commerceservices.audit.event.AuditLogEventEnum;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.device.FingerprintReaderModel;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.service.SessionBusIfc;
import oracle.retail.stores.pos.device.POSDeviceActions;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.services.common.CheckTrainingReentryMode;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.employee.employeemain.EmployeeCargo;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;
import oracle.retail.stores.pos.utility.FingerprintUtility;


/*
 * FingerprintVerifyAisle performs the verification of the enrolled fingerprint gathered by
 * FingerprintEnrollSite/Aisle. 
 */
public class FingerprintVerifyAisle extends PosLaneActionAdapter
{
    public static final String revisionNumber = "$Revision: /main/4 $";

    //--------------------------------------------------------------------------
    /** 
        The traverse method is called to verify a fingerprint against the
        enrollment template collected in FingerprintEnrollAisle.
        
        The fingerprint is also compared against existing fingerprints in the database.
        
        Fingerprints are considered invalid if they are already associated with a
        different employee in the database.
    **/
    //--------------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {

        EmployeeCargo cargo = (EmployeeCargo) bus.getCargo();
        EmployeeIfc employee = cargo.getEmployee();
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        POSBaseBeanModel model = (POSBaseBeanModel) ui.getModel(POSUIManagerIfc.EMPLOYEE_VERIFY_FINGERPRINT);
        PromptAndResponseModel pAndRModel = model.getPromptAndResponseModel();
        FingerprintReaderModel fingerprintModel = pAndRModel.getFingerprintModel();
        POSDeviceActions pda = new POSDeviceActions((SessionBusIfc) bus);
        
        if (fingerprintModel.getEventType() == FingerprintReaderModel.EVENT_TYPE.VERIFY)
        {
            // if fingerprint doesn't match the enrolled template, ask for a retry
            if (!FingerprintUtility.verifyFingerprintMatch(pda, cargo.getFingerprintEnrollmentTemplate(), fingerprintModel.getFingerprintData()))
            {
                UIUtilities.setDialogModel(ui, DialogScreensIfc.CONFIRMATION, "FingerprintVerifyRetry");
            }
            else
                try
                {
                    if (FingerprintUtility.fingerprintMatchesDifferentEmployee(
                            pda, employee.getEmployeeID(), 
                            fingerprintModel.getFingerprintData()))
                    {
                        if (!CheckTrainingReentryMode.isTrainingRetryOn(cargo.getRegister()))
                        {
                            EmployeeFindUtility.logAuditEvent(false, cargo, AuditLogEventEnum.DUPLICATE_EMPLOYEE_FINGERPRINT, "FingerprintVerifyAisle.traverse");
                        }
                        UIUtilities.setDialogModel(ui, DialogScreensIfc.CONFIRMATION, "FingerprintDuplicateRetry");
                    }
                    
                    // fingerprint enrollment template is now verified, move it into the employee and proceed down happy path
                    else
                    {
                        cargo.getEmployee().setFingerprintBiometrics(cargo.getFingerprintEnrollmentTemplate());
                        cargo.setFingerprintEnrollmentTemplate(null);
                        cargo.setEnrolledNewFingerprint(true);
                        
                        Letter result = new Letter(CommonLetterIfc.CONTINUE);
                        bus.mail(result, BusIfc.CURRENT);
                    }
                }
                // unexpected exception during read-all-fingerprints data query - present retry dialog
                catch (DataException e)
                {
                    UIUtilities.setDialogModel(ui, DialogScreensIfc.CONFIRMATION, "FingerprintVerifyRetry");
                }
        }
        // bad event (probably error event) display retry dialog
        else
        {
            UIUtilities.setDialogModel(ui, DialogScreensIfc.CONFIRMATION, "FingerprintVerifyRetry");
        }

    }



}

