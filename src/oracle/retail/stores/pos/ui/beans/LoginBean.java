/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/LoginBean.java /main/5 2011/02/16 09:13:24 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    cgreene   02/14/11 - move parameterconstants path
 *    mkutiana  02/11/11 - Based on parameter (ManualEntryRequiresPassword) the
 *                         password field/label is hidden
 *    icole     02/10/11 - Removed hack for context being null, unable to
 *                         reproduce error.
 *    blarsen   10/21/10 - login bean
 *    blarsen   05/11/10 - login bean
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import oracle.retail.stores.pos.ado.context.ADOContextIfc;
import oracle.retail.stores.pos.ado.context.ContextFactory;
import oracle.retail.stores.common.parameter.ParameterConstantsIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.pos.ui.UIUtilities;

/**
 * This class is used to display and gather login information (login
 * id/password/fingerprint)
 * 
 * @version $Revision: /main/5 $
 */
public class LoginBean extends ValidatingBean
{

    private static final long serialVersionUID = 7790923844652177038L;
    
    protected JLabel loginIDLabel = null;
    protected JLabel passwordLabel = null;
    protected JLabel andLabel = null;
    protected JLabel fingerprintLabel = null;
    
    protected AlphaNumericTextField loginIDField = null;
    protected AlphaNumericPasswordField passwordField = null;

    /**
     *    Default Constructor.
     */
    public LoginBean()
    {
        initialize();
    }

    /**
     *    Return the POSBaseBeanModel.
     *    @return posBaseBeanModel as POSBaseBeanModel
     */
    @Override
    public POSBaseBeanModel getPOSBaseBeanModel()
    {
       return beanModel;
    }

    /**
     *    Initialize the class.
     */
    protected void initialize()
    {
        setName("LoginBean");
        uiFactory.configureUIComponent(this, UI_PREFIX);

        initializeFields();
        initializeLabels();
        initLayout();
    }
    
    /**
     * Initialize the layout.
     */
    protected void initLayout()
    {
        setLayout(new GridBagLayout());

        String fingerprintLoginOption = ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_NO_FINGERPRINT;
        boolean manualLoginEntryReqPwdParm = true; //Defaulting to "Y"
        
        try
        {
            ADOContextIfc context = ContextFactory.getInstance().getContext();
                        
            if (context != null)
            {
                ParameterManagerIfc pm = (ParameterManagerIfc) context.getManager(ParameterManagerIfc.TYPE);
                fingerprintLoginOption = pm.getStringValue(ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions);
                manualLoginEntryReqPwdParm = pm.getBooleanValue(ParameterConstantsIfc.OPERATORID_ManualEntryRequiresPassword);
             }
            else
            {
                logger.error("Unable to get context for parameter manager: " + ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions + ".  Using default (" + fingerprintLoginOption + ")");
                logger.error("Unable to get context for parameter manager: " + ParameterConstantsIfc.OPERATORID_ManualEntryRequiresPassword + ".  Using default (" + manualLoginEntryReqPwdParm + ")");
           }
        }
        catch (ParameterException e)
        {
            logger.error("Unable to get parameter: " + ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions + ".  Using default (" + fingerprintLoginOption + ")", e);
            logger.error("Unable to get parameter: " + ParameterConstantsIfc.OPERATORID_ManualEntryRequiresPassword + ".  Using default (" + manualLoginEntryReqPwdParm + ")", e);
        }

        if (ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_NO_FINGERPRINT.equals(fingerprintLoginOption))
        {
            UIUtilities.layoutComponent(this, loginIDLabel,  loginIDField,  0, 0, false);
            if	(manualLoginEntryReqPwdParm)
			{
				UIUtilities.layoutComponent(this, passwordLabel, passwordField, 0, 1, false);
			}
        }
        else if (ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_ID_AND_FINGERPRINT.equals(fingerprintLoginOption))
        {
            UIUtilities.layoutComponent(this, loginIDLabel,     loginIDField, 0, 0, false, true);
            UIUtilities.layoutComponent(this, andLabel,         null,         2, 0, false, true);
            UIUtilities.layoutComponent(this, fingerprintLabel, null,         3, 0, false, true);
        }
        else if (ParameterConstantsIfc.OPERATORID_FingerprintLoginOptions_FINGERPRINT_ONLY.equals(fingerprintLoginOption))
        {
            UIUtilities.layoutComponent(this, fingerprintLabel, null,          0, 0, false);
        }
        
        loginIDField.setLabel(loginIDLabel);
        passwordField.setLabel(passwordLabel);

    }

    /**
     * Initializes the fields.
     */
    protected void initializeFields()
    {
        loginIDField = uiFactory.createAlphaNumericField("loginIDField", "1", "10", "22", false);
        loginIDField.setRequired(false);
        passwordField = uiFactory.createAlphaNumericPasswordField("passwordField", "1", "22");
        passwordField.setRequired(false);

    }

    /**
     * Initializes the labels.
     */
    protected void initializeLabels()
    {
        loginIDLabel = uiFactory.createLabel("LoginIDLabel", "LoginID", null, UI_LABEL);
        passwordLabel = uiFactory.createLabel("PasswordLabel", "Password", null, UI_LABEL);

        andLabel = uiFactory.createLabel("AndLabel", "+", null, UI_LABEL);

        Image fingerprintImage = UIUtilities.getImage("images/fingerprint.gif", this);
        ImageIcon fingerprintIcon = new ImageIcon(fingerprintImage);
        fingerprintLabel = uiFactory.createLabel("", "", fingerprintIcon, UI_LABEL);
    }

    /**
     * Updates the model from the screen.
     */
    @Override
    public void updateModel()
    {
        if (beanModel instanceof LoginBeanModel)
        {
            LoginBeanModel model = (LoginBeanModel) beanModel;
            
            model.setLoginID(loginIDField.getText());
            model.setPassword(new String(passwordField.getPassword()));
        }
    }

    /**
     * Updates the information displayed on the screen's if the model's been
     * changed.
     */
    @Override
    protected void updateBean()
    {
        if (beanModel instanceof LoginBeanModel)
        {
            // get model
            LoginBeanModel model = (LoginBeanModel)beanModel;
            
            loginIDField.setText(model.getLoginID());
            setupComponent(loginIDField, true, true);

            passwordField.setText(model.getPassword());
            setupComponent(passwordField, true, true);
        }
    }

    /**
     * Updates the information displayed on the screen's if the model's been
     * changed.
     */
    protected void setupComponent(JComponent field, boolean isEditable, boolean isVisible)
    {
        if (field instanceof ValidatingFieldIfc)
        {
            ((ValidatingFieldIfc)field).getLabel().setVisible(isVisible);
        }

        if (field instanceof JTextField)
        {
            ((JTextField)field).setEditable(isEditable);
        }
        field.setFocusable(isEditable);
        // field.setRequestFocusEnabled(isVisible);
        field.setVisible(isVisible);
    }

    /* (non-Javadoc)
     * @see com.extendyourstore.pos.ui.beans.BaseBeanAdapter#activate()
     */
    @Override
    public void activate()
    {
        super.activate();
        loginIDField.addFocusListener(this);
        passwordField.addFocusListener(this);
    }

    /**
     * Deactivates this bean.
     */
    public void deactivate()
    {
        super.deactivate();
        loginIDField.removeFocusListener(this);
        passwordField.removeFocusListener(this);
    }

    /* (non-Javadoc)
     * @see com.extendyourstore.pos.ui.beans.ValidatingBean#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent event)
    {
        super.actionPerformed(event);
    }

    /**
     * Requests focus on parameter value name field if visible is true.
     *
     * @param visible true if setting visible, false otherwise
     */
    @Override
    public void setVisible(boolean visible)
    {
        super.setVisible(visible);
        if (visible && !errorFound())
        {
            setCurrentFocus(loginIDField);
        }
    }
 
    /**
     * Update property fields.
     */
    protected void updatePropertyFields()
    {
        loginIDLabel.setText(retrieveText("LoginID", loginIDLabel));
        passwordLabel.setText(retrieveText("Password", passwordLabel));
        andLabel.setText(retrieveText("And", andLabel));
        loginIDField.setLabel(loginIDLabel);
        passwordField.setLabel(passwordLabel);
    }

    /**
     * main entrypoint - starts the part when it is run as an application
     *
     * @param args java.lang.String[]
     */
    public static void main(java.lang.String[] args)
    {
        UIUtilities.setUpTest();

        LoginBean bean = new LoginBean();
        bean.loginIDField.setText("testLoginId");
        System.out.println("1: " + bean.loginIDField.getText());
        bean.passwordField.setText("testPassword");
        System.out.println("2: " + bean.passwordField.getPassword().toString());

        UIUtilities.doBeanTest(bean);
    }
}
