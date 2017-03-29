/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/EmployeeInvalidAisle.java /main/14 2011/02/24 16:44:18 mkutiana Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    mkutiana  02/24/11 - based on login system display appropriate error msgs
 *    nkgautam  08/13/10 - fixed class cast exception
 *    cgreene   05/26/10 - convert to oracle packaging
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;


import oracle.retail.stores.common.parameter.ParameterConstantsIfc;
import oracle.retail.stores.domain.arts.DataManagerMsgIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.services.modifytransaction.salesassociate.ModifyTransactionSalesAssociateCargo;
import oracle.retail.stores.pos.services.operatorid.OperatorIdCargo;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

/**
 * This aisle is traversed when the employee ID entered is invalid. It displays
 * the error screen.
 * 
 * @version $Revision: /main/14 $
 */
public class EmployeeInvalidAisle extends PosLaneActionAdapter
{
    private static final long serialVersionUID = 3833570180464513872L;

    /**
     * revision number supplied by Team Connection
     */
    public static final String revisionNumber = "$Revision: /main/14 $";

    /**
     * No data bundle tag
     */
    public static final String NO_DATA_TAG = "InvalidAssoc.NoDataLogin";
    /**
     * No data bundle tag - for fingerprint only logins
     */
    public static final String NO_DATA_FINGERPRINT_TAG = "InvalidAssoc.NoDataFingerprintLogin";
    /**
     * No data bundle tag - for fingerprint and ID logins
     */
    public static final String NO_DATA_FINGERPRINT_AND_ID_TAG = "InvalidAssoc.NoDataFingerprintOrIDLogin";

    /**
     * Contact bundle tag
     */
    public static final String CONTACT_TAG = "InvalidAssoc.Contact";

    /**
     * Displays the error screen.
     * 
     * @param bus Service Bus
     */
    @Override
    public void traverse(BusIfc bus)
    {

        // Ask the UI Manager to display the error message
        POSUIManagerIfc ui;
        int error = DataException.NO_DATA ;
        ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        if(bus.getCargo() instanceof OperatorIdCargo)
        {
            OperatorIdCargo cargo = (OperatorIdCargo) bus.getCargo();
            error = cargo.getErrorType();
        }
        else if(bus.getCargo() instanceof ModifyTransactionSalesAssociateCargo )
        {
            ModifyTransactionSalesAssociateCargo cargo = (ModifyTransactionSalesAssociateCargo) bus.getCargo();
            error = cargo.getDataExceptionErrorCode();
        }
        
        String msg[] = new String[3];

        UtilityManagerIfc utility = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);

        switch (error)
        {
            case DataException.NO_DATA:
                msg[0] = "";
                msg[1] = retrieveDialogText(bus, utility);
                msg[2] = "";
                break;
    
            default:
                msg[0] = utility.getErrorCodeString(error);
                msg[1] = "";
                msg[2] = utility.retrieveDialogText(CONTACT_TAG, DataManagerMsgIfc.CONTACT);
    
                break;
        }

        DialogBeanModel dialogModel = new DialogBeanModel();
        dialogModel.setResourceID("InvalidAssoc");
        dialogModel.setType(DialogScreensIfc.ERROR);
        dialogModel.setArgs(msg);

        // display dialog
        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);

    }
    
    /*
     * Returns the appropriate message from the localized bundles based on the kind of login system in effect
     * @return String Error message for Dialog
     */
    private String retrieveDialogText(BusIfc bus, UtilityManagerIfc utility)
    {
        ParameterManagerIfc pm = (ParameterManagerIfc) bus.getManager(ParameterManagerIfc.TYPE);
        String fingerprintLoginOption = ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_NO_FINGERPRINT;
        String bundleProperty = NO_DATA_TAG;
        try
        {
           fingerprintLoginOption = pm.getStringValue(ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions);
        }
        catch (ParameterException e)
        {
           logger.error("Could not get value for paramter: " + 
        		   ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions + "using default " + fingerprintLoginOption, e);
        }
        
        if (ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_ID_AND_FINGERPRINT.equals(fingerprintLoginOption))
        {
            bundleProperty = NO_DATA_FINGERPRINT_AND_ID_TAG;
        }
        else if (ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_FINGERPRINT_ONLY.equals(fingerprintLoginOption))
        {
            bundleProperty = NO_DATA_FINGERPRINT_TAG;
        }    	
        
        return utility.retrieveDialogText(bundleProperty, DataManagerMsgIfc.NO_DATA_LOGIN);
    }	
	
    /**
     * Returns the revision number of the class.
     * 
     * @return String representation of revision number
     */
    public String getRevisionNumber()
    {
        return (revisionNumber);
    }
}
