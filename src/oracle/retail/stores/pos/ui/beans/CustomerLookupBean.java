/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/CustomerLookupBean.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:54 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   06/22/09 - ensure that any listeners are removed in deactivate
 *                         method
 *    mkochumm  11/21/08 - fix compilation error
 *    mkochumm  11/05/08 - i18n changes for phone and postalcode fields
 *
 * ===========================================================================
 * $Log:
 *   3    360Commerce 1.2         3/31/2005 4:27:37 PM   Robert Pearse   
 *   2    360Commerce 1.1         3/10/2005 10:20:40 AM  Robert Pearse   
 *   1    360Commerce 1.0         2/11/2005 12:10:23 PM  Robert Pearse   
 *
 *  Revision 1.4  2004/03/16 17:15:22  build
 *  Forcing head revision
 *
 *  Revision 1.3  2004/03/16 17:15:17  build
 *  Forcing head revision
 *
 *  Revision 1.2  2004/02/11 20:56:27  rhafernik
 *  @scr 0 Log4J conversion and code cleanup
 *
 *  Revision 1.1.1.1  2004/02/11 01:04:21  cschellenger
 *  updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:09:58   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.3   Jul 20 2003 10:56:06   sfl
 * Modified the active method for countryField using the same way as in the newly changed CustomerInfoBean.
 * Resolution for POS SCR-1733: build dependencies update
 *
 *    Rev 1.2   Sep 18 2002 17:15:30   baa
 * country/state changes
 * Resolution for POS SCR-1740: Code base Conversions
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import java.awt.event.ActionEvent;

import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.ui.UIUtilities;

/**
 * CustomerLookupBean is used by both the Customer Find Service and the Customer
 * Delete Service.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @deprecated as of release 5.5 replace by CustomerInfoBean
 **/
public class CustomerLookupBean extends CustomerInfoBean
{
    private static final long serialVersionUID = -3878772387809280763L;
    /** revision number */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * Constructor
     */
    public CustomerLookupBean()
    {
        initialize();
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.ui.beans.CustomerInfoBean#activate()
     */
    @Override
    public void activate()
    {
        super.activate();
        countryField.addActionListener(this);
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.ui.beans.CustomerInfoBean#deactivate()
     */
    @Override
    public void deactivate()
    {
        super.deactivate();
        countryField.removeActionListener(this);
    }
    
    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.ui.beans.CustomerInfoBean#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == countryField)
        {
            updateStates();
            setPostalFields();
        }
        else
        {
            super.actionPerformed(event);
        }
    }

    /**
     * Updates the model.
     */
    @Override
    public void updateModel()
    {
        if (beanModel instanceof CustomerInfoBeanModel)
        {
            CustomerInfoBeanModel model = (CustomerInfoBeanModel) beanModel;
            model.setFirstName(firstNameField.getText());
            model.setLastName(lastNameField.getText());
            model.setAddressLine1(addressLine1Field.getText());
            model.setAddressLine2(addressLine2Field.getText());
            model.setCity(cityField.getText());
            if (stateField.getSelectedIndex() >= 0)
            {
                model.setStateIndex(stateField.getSelectedIndex());
            }
            model.setCountryIndex(countryField.getSelectedIndex());
            model.setPostalCode(postalCodeField.getText());
            model.setTelephoneNumber(telephoneField.getFieldValue());
        }
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.ui.beans.CustomerInfoBean#toString()
     */
    @Override
    public String toString()
    {
        return new String("Class: " + Util.getSimpleClassName(this.getClass()) +
                "(Revision " + getRevisionNumber()
                + ") @" + hashCode());

    }

    /**
     * Retrieves the Team Connection revision number.
     * 
     * @return String representation of revision number
     */
    public String getRevisionNumber()
    {
        return (Util.parseRevisionNumber(revisionNumber));
    }

    /**
     * main entrypoint - starts the part when it is run as an application
     * 
     * @param args java.lang.String[]
     */
    public static void main(java.lang.String[] args)
    {
        UIUtilities.setUpTest();

        CustomerLookupBean bean = new CustomerLookupBean();

        bean.telephoneField.setValue("4043865851");
        System.out.println("1: " + bean.telephoneField.getFieldValue());

        bean.telephoneField.setText("4(512)555-1212");
        System.out.println("2: " + bean.telephoneField.getFieldValue());

        UIUtilities.doBeanTest(bean);
    }
}
