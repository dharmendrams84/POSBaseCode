/* ===========================================================================
* Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/operatorid/LoginSite.java /rgbustores_13.4x_generic_branch/5 2012/02/23 14:58:14 mkutiana Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    mkutiana  02/23/12 - In manager override scenario required to protect
 *                         against npe
 *    mkutiana  02/21/12 - disable the fingerprintreader (at login) when
 *                         parameter is set to NoFingerprint
 *    icole     02/09/12 - Ensure CPOI is cleared at login site when in
 *                         accountability mode.
 *    yiqzhao   10/07/11 - hide OnScreenKeyboard on loginSite
 *    cgreene   06/07/11 - update to first pass of removing pospal project
 *    mkutiana  02/22/11 - Modified to handle multiple password policies
 *                         (introduction of biometrics)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    blarsen   05/12/10 - login (enter uername and password) site
 *    
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.operatorid;

import oracle.retail.stores.commerceservices.security.EmployeeStatusEnum;
import oracle.retail.stores.common.parameter.ParameterConstantsIfc;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.manager.ifc.PaymentManagerIfc;
import oracle.retail.stores.domain.store.WorkstationIfc;
import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.gui.UIException;
import oracle.retail.stores.foundation.manager.gui.UISubsystem;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.service.SessionBusIfc;
import oracle.retail.stores.pos.ado.context.ADOContextIfc;
import oracle.retail.stores.pos.ado.context.ContextFactory;
import oracle.retail.stores.pos.ado.utility.Utility;
import oracle.retail.stores.pos.ado.utility.tdo.PasswordPolicyTDOIfc;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;
import oracle.retail.stores.pos.device.POSDeviceActions;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonActionsIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.pos.ui.beans.LoginBeanModel;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;

/**
 * This site displays the LOGIN screen.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/5 $
 */
public class LoginSite extends PosSiteActionAdapter
{

    private static final long serialVersionUID = -4415995541495667236L;

    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/5 $";

    private static final String CHANGE_PASSWORD_BUTTON = "ChangePassword";

    private static final String SET_FINGERPRINT_BUTTON = "SetFingerprint";

    /**
     * Displays the LOGIN screen.
     * 
     * @param bus Service Bus
     */
    @Override
    public void arrive(BusIfc bus)
    {
        resetCargo(bus);

        LoginBeanModel model = new LoginBeanModel();    

        NavigationButtonBeanModel nbbModel = getNavButtons(bus);    	
    	model.setLocalButtonBeanModel(nbbModel);
    	
    	NavigationButtonBeanModel globalModel = getGlobalNavigationButtonBeanModel(); 
        model.setGlobalButtonBeanModel(globalModel);

    	PromptAndResponseModel parModel = getPromptAndResponseModel();
    	model.setPromptAndResponseModel(parModel);
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        
        ui.showScreen(POSUIManagerIfc.OPERATOR_LOGIN, model);
        
        // Deactivate the fingerprint reader based the fingerprint login options parameter
        POSDeviceActions deviceActions = new POSDeviceActions((SessionBusIfc) bus);
        if (! isFingerprintAllowed() && isFingerprintReaderOnline(deviceActions)){
            try
            {
                deviceActions.deactivateFingerprintReader();
            }
            catch (DeviceException de)
            {
                logger.error("Device exception deactivating the fingerprintReader " + de);
            }
        }        
        
        UISubsystem uiSubSys = UISubsystem.getInstance();
        
        OperatorIdCargo cargo = (OperatorIdCargo) bus.getCargo();
        RegisterIfc register = cargo.getRegister();
        
        //In a manager override scenario the register object is null - besides 
        //protecting against npe; cpoi should not be cleared during man override
        if (register != null)
        {
            WorkstationIfc workstation = register.getWorkstation();
            PaymentManagerIfc paymentManager = (PaymentManagerIfc)bus.getManager(PaymentManagerIfc.TYPE);
            paymentManager.clearSwipeAheadData(workstation);
        }
        
        try
        {
        	uiSubSys.showOnScreenKeyboard(false);
        }
        catch (UIException e)
        {
            logger.error("Unable to hide popup keyboard dialog.", e);
        }
    }

    /**
     * Checks and returns if the fingerprint reader is online
     * @param deviceActions
     * @return boolean isFingerprintReaderOnline
     */
    private Boolean isFingerprintReaderOnline(POSDeviceActions deviceActions) 
    {   
        boolean isFingerprintReaderOnline = false;
        try
        {
            isFingerprintReaderOnline = deviceActions.isFingerprintReaderOnline();
        }
        catch (DeviceException e)
        {
            // implies fingerprint reader is offline
        }
        return isFingerprintReaderOnline;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void depart(BusIfc bus)
    {
        OperatorIdCargo cargo = (OperatorIdCargo)bus.getCargo();
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        LoginBeanModel loginModel = (LoginBeanModel) ui.getModel(POSUIManagerIfc.OPERATOR_LOGIN);
            
        cargo.setEmployeeID(loginModel.getLoginID());
        cargo.setEmployeePassword(loginModel.getPassword()); // I disagree with this method being deprecated.  It appears to have simply forced the code inside the method out to each caller.  It's unclear what should really be done here to avoid this warning.

    }
    
   private PromptAndResponseModel getPromptAndResponseModel()
    {
        PromptAndResponseModel parModel = new PromptAndResponseModel();
        
        String fingerprintLoginOption = getFingerprintOption();
        
        String parText = null;            
        if (ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_NO_FINGERPRINT.equals(fingerprintLoginOption))
        {
            parText = UIUtilities.retrieveText("PromptAndResponsePanelSpec", BundleConstantsIfc.OPERATORID_BUNDLE_NAME, "OperatorLoginPrompt", "OperatorLoginPrompt");
        }
        else if (ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_ID_AND_FINGERPRINT.equals(fingerprintLoginOption))
        {
            parText = UIUtilities.retrieveText("PromptAndResponsePanelSpec", BundleConstantsIfc.OPERATORID_BUNDLE_NAME, "OperatorLoginPromptWithFinger", "OperatorLoginPromptWithFinger");
        }
        else if (ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_FINGERPRINT_ONLY.equals(fingerprintLoginOption))
        {
            parText = UIUtilities.retrieveText("PromptAndResponsePanelSpec", BundleConstantsIfc.OPERATORID_BUNDLE_NAME, "OperatorLoginPromptFingerOnly", "OperatorLoginPromptFingerOnly");
        }

        parModel.setPromptText(parText);

        return parModel;
    }

   /**
    * Get the Navigation Button Bean Model with the "Next" button enabled appropriately for biometrics.
    */
   private NavigationButtonBeanModel getGlobalNavigationButtonBeanModel()
    {
        NavigationButtonBeanModel globalNavModel = new NavigationButtonBeanModel();
            
        String fingerprintLoginOption = getFingerprintOption();
        
        if (ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_NO_FINGERPRINT.equals(fingerprintLoginOption))
        {
            globalNavModel.setButtonEnabled(CommonActionsIfc.NEXT, true);
        }
        else if (ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_ID_AND_FINGERPRINT.equals(fingerprintLoginOption))
        {
            globalNavModel.setButtonEnabled(CommonActionsIfc.NEXT, false);
        }
        else if (ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_FINGERPRINT_ONLY.equals(fingerprintLoginOption))
        {
            globalNavModel.setButtonEnabled(CommonActionsIfc.NEXT, false);
        }

        return globalNavModel;
    }

   /**
    * get the fingerprint option selected via parameter
    * 
    *
    * @return
    */
    private String getFingerprintOption()
    {
        String fingerprintLoginOption = ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_NO_FINGERPRINT;
        try
        {
            ADOContextIfc context = ContextFactory.getInstance().getContext();
            if (context != null)
            {
                ParameterManagerIfc pm = (ParameterManagerIfc) context.getManager(ParameterManagerIfc.TYPE);
                fingerprintLoginOption = pm.getStringValue(ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions);
             }
            else
            {
                logger.error("Unable to get parameter: " + ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions + ".  Using default value: fingerprintLoginOption");
           }
        }
        catch (ParameterException e)
        {
            logger.error("Unable to get parameter: " + ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions + ".  Using default value: fingerprintLoginOption", e);
        }
        return fingerprintLoginOption;
    }

    /**
     * Are fingerprints allowed?
     * 
     * @return
     */
    private boolean isFingerprintAllowed()
    {
        return !ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_NO_FINGERPRINT.equals(getFingerprintOption());
    }

    /**
     * This resets cargo for settings that will be set again
     * @param bus reference to bus
     */
	private void resetCargo(BusIfc bus) 
	{
		OperatorIdCargo cargo = (OperatorIdCargo) bus.getCargo();
    	cargo.setLoginValidationChangePassword(false);
    	cargo.setLockOut(false);
        cargo.setEmployeeID("");
        cargo.setEmployeePasswordBytes(null);
        cargo.setSelectedEmployee(null);
        cargo.setEvaluateStatusEnum(EmployeeStatusEnum.ACTIVE);
	}
 
    /**
     * This checks if Change Password and Set Fingerprint buttons need to be enabled
     * @param bus reference to bus
     * @return  NavigationButtonBeanModel navigation bean model
     */
    private NavigationButtonBeanModel getNavButtons(BusIfc bus)
    {
        NavigationButtonBeanModel nbbModel = new NavigationButtonBeanModel();
        nbbModel.setButtonEnabled(CHANGE_PASSWORD_BUTTON, isChangePasswordAllowed(bus));
        nbbModel.setButtonEnabled(SET_FINGERPRINT_BUTTON, isFingerprintAllowed());
        
        return nbbModel;
    }

    /**
     * Is user allowed to reset the password?
     *
     * @param bus
     * @return
     */
    private boolean isChangePasswordAllowed(BusIfc bus)
    {
        boolean enableChangePasswordButton = true;
        PasswordPolicyTDOIfc tdo = getPasswordPolicyTDO();
        boolean employeeComplianceAllowed = tdo.checkEmployeeComplianceEvaluationAllowed(bus);
        if (!employeeComplianceAllowed)
        {
            enableChangePasswordButton = false;
        }
        boolean passwordRequired = tdo.checkPasswordParameter(bus);
        if(!passwordRequired)
        {
            enableChangePasswordButton = false;
        }
        return enableChangePasswordButton;
    }

    /**
     * Calls <code>arrive</code>
     * 
     * @param bus Service Bus
     */
    @Override
    public void reset(BusIfc bus)
    {
        arrive(bus);
    }

    /**
     * Creates Instance of Password Policy TDO.
     * 
     * @return PasswordPolicyTDOIfc instance of Password Policy TDO
     */
    private PasswordPolicyTDOIfc getPasswordPolicyTDO()
    {
        return Utility.getUtil().getPasswordPolicyTDO();
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
