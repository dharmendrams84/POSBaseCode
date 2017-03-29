/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/ServiceItemListBean.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:53 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    jswan     11/03/10 - Fixed issues with displaying text and drop down
 *                         fields on screen with a single lable.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    ddbaker   11/20/08 - Updates for clipping problems
 *
 * ===========================================================================
 * $Log:
 *  4    I18N_P2    1.2.1.0     1/4/2008 5:00:24 PM    Maisa De Camargo CR
 *       29826 - Setting the size of the combo boxes. This change was
 *       necessary because the width of the combo boxes used to grow according
 *        to the length of the longest content. By setting the size, we allow
 *       the width of the combo box to be set independently from the width of
 *       the dropdown menu.
 *  3    360Commerce 1.2         3/31/2005 4:29:56 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:25:12 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:14:10 PM  Robert Pearse   
 *
 * Revision 1.4  2004/07/17 19:21:23  jdeleau
 * @scr 5624 Make sure errors are focused on the beans, if an error is found
 * during validation.
 *
 * Revision 1.3  2004/03/16 17:15:18  build
 * Forcing head revision
 *
 * Revision 1.2  2004/02/11 20:56:26  rhafernik
 * @scr 0 Log4J conversion and code cleanup
 *
 * Revision 1.1.1.1  2004/02/11 01:04:22  cschellenger
 * updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   Sep 16 2003 17:53:12   dcobb
 * Migrate to JVM 1.4.1
 * Resolution for 3361: New Feature:  JVM 1.4.1_03 (Windows) Migration
 * 
 *    Rev 1.0   Aug 29 2003 16:12:08   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Aug 14 2002 18:18:40   baa
 * format currency 
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.0   Apr 29 2002 14:48:26   msg
 * Initial revision.
 * 
 *    Rev 1.1   15 Apr 2002 09:36:04   baa
 * make call to setLabel() from the updatePropertyFields() method
 * Resolution for POS SCR-1599: Field name labels on dialog screens use default text instead of text from bundles
 *
 *    Rev 1.0   Mar 18 2002 11:57:38   msg
 * Initial revision.
 *
 *    Rev 1.4   Mar 01 2002 22:35:46   mpm
 * Made changes for modifyitem internationalization.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.3   16 Feb 2002 19:20:30   baa
 * add default selection to combo box
 * Resolution for POS SCR-1292: Tab not functioning on Non Merchandise item drop down box
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

// java import
import javax.swing.JComponent;
import javax.swing.JLabel;

import oracle.retail.stores.foundation.manager.gui.UIModelIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.ui.UIUtilities;

//--------------------------------------------------------------------------
/**
    This bean allows the user to select a non-merchandise item from
    a dropdown list. <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class ServiceItemListBean extends ValidatingBean
{
    /**
        revision number supplied by source-code-control system
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    protected JLabel serviceItemListLabel = null;
    protected ValidatingComboBox serviceItemListField = null;
    protected ServiceItemListBeanModel beanModel = new ServiceItemListBeanModel();

    //----------------------------------------------------------------------
    /**
        Constructs bean.
    **/
    //----------------------------------------------------------------------
    public ServiceItemListBean()
    {
        super();
        initialize();
    }

    //---------------------------------------------------------------------
    /**
        Returns ValidatingComboBox value.
        Instantiates field if necessary. <P>
        @return ValidatingComboBox panel
    **/
    //---------------------------------------------------------------------
    protected ValidatingComboBox getServiceItemListField()
    {                                   // begin getServiceItemListField()
        return serviceItemListField;
    }                                   // end getServiceItemListField()

    //---------------------------------------------------------------------
    /**
        Returns service item list label. Instantiates label component,
        if necessary. <P>
        @return label
    **/
    //---------------------------------------------------------------------
    protected JLabel getServiceItemListLabel()
    {
        return serviceItemListLabel;
    }

    /**
     * Returns the bean model
     * @return model object
     */
    public POSBaseBeanModel getPOSBaseBeanModel()
    {
        return beanModel;
    }

    //---------------------------------------------------------------------
    /**
        Sets the model data into the bean fields. <P>
        @param model the bean model
     */
    //---------------------------------------------------------------------
    public void setModel(UIModelIfc model)
    {                                   // begin setModel()
        if (model == null)
        {
            throw new NullPointerException("An attempt was made to set the ServiceItemListBean model to null.");
        }

        if (model instanceof ServiceItemListBeanModel)
        {
            beanModel = (ServiceItemListBeanModel) model;
            updateBean();

        }

    }                                   // end setModel()

    //------------------------------------------------------------------------
    /**
     * Updates the model for the current settings of this bean.
     */
    //------------------------------------------------------------------------
    public void updateModel()
    {
        beanModel.setSelectedIndex(serviceItemListField.getSelectedIndex());
    }

    //------------------------------------------------------------------------
    /**
     * Updates the bean for the current settings of this bean.
     */
    //------------------------------------------------------------------------
    public void updateBean()
    {
            // nmw updated on 7/28 to use string array not a vector
            if (beanModel.getNonMerchandiseList() != null)
            {
                String[] list = beanModel.getNonMerchandiseList();
                ValidatingComboBoxModel listModel  = new ValidatingComboBoxModel();
                listModel.removeAllElements();
                for (int i = 0; i < list.length; i++)
                {
                    listModel.addElement(list[i]);
                }
                serviceItemListField.setModel(listModel);
            }

            serviceItemListField.setSelectedIndex(beanModel.getSelectedIndex());

            // Make sure something is selected
            if (serviceItemListField.getSelectedIndex() < 0)
            {
                serviceItemListField.setSelectedIndex(0);
            }
    }

    //---------------------------------------------------------------------
    /**
        Initializes the class.
    **/
    //---------------------------------------------------------------------
    protected void initialize()
    {
        setName("ServiceItemListBean");

        uiFactory.configureUIComponent(this, UI_PREFIX);

        serviceItemListLabel = uiFactory.createLabel("Non-Merchandise Item:", null, UI_LABEL);
        serviceItemListField = uiFactory.createValidatingComboBox("ServiceItemListField", "false", "30");

        UIUtilities.layoutDataPanel(
            this,
            new JLabel[]{serviceItemListLabel},
            new JComponent[]{serviceItemListField}, false);
    }
    
    //---------------------------------------------------------------------
    /**
        Overrides JPanel setVisible() method to request focus. <P>
    **/
    //---------------------------------------------------------------------
    public void setVisible(boolean aFlag)
    {
        super.setVisible(aFlag);
        if (aFlag && !errorFound())
        {
            setCurrentFocus(serviceItemListField);
        }
    }
    
    //---------------------------------------------------------------------
    /**
       Activates this bean.
    **/
    //---------------------------------------------------------------------
    public void activate()
    {
        super.activate();
        serviceItemListField.addFocusListener(this);
    }

    //--------------------------------------------------------------------------
    /**
       Deactivates this bean.
    **/
    //---------------------------------------------------------------------
    public void deactivate()
    {
        super.deactivate();
        serviceItemListField.removeFocusListener(this);
    }

    //---------------------------------------------------------------------
    /**
       Updates property-based fields.
    **/
    //---------------------------------------------------------------------
    protected void updatePropertyFields()
    {                                   // begin updatePropertyFields()
        serviceItemListLabel.setText(retrieveText("ServiceItemListLabel",
                                                  serviceItemListLabel));
    }                                   // end updatePropertyFields()


    //---------------------------------------------------------------------
    /**
       Returns default display string. <P>
       @return String representation of object
    */
    //---------------------------------------------------------------------
    public String toString()
    {
        String strResult = new String("Class: ServiceItemListBean (Revision "+
                                      getRevisionNumber() + ") @" +
                                      hashCode());
        return(strResult);
    }

    //---------------------------------------------------------------------
    /**
        Main entrypoint - starts the part when it is run as an application
        @param args java.lang.String[]
    **/
    //---------------------------------------------------------------------
    public static void main(java.lang.String[] args)
    {
        UIUtilities.setUpTest();
        UIUtilities.doBeanTest(new ServiceItemListBean());
    }

    //----------------------------------------------------------------------
    /**
        Returns the revision number of the class. <P>
        @return String representation of revision number
    **/
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return(Util.parseRevisionNumber(revisionNumber));
    }
}
