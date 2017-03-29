/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/CustomerLineItemRenderer.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:54 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/28/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    abondala  01/03/10 - update header date
 *    mkochumm  12/17/08 - format phone number
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:37 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:40 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:23 PM  Robert Pearse   
 *
 *   Revision 1.4  2004/03/16 17:15:22  build
 *   Forcing head revision
 *
 *   Revision 1.3  2004/03/16 17:15:17  build
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 20:56:26  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:21  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:09:58   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.4   Jun 25 2003 16:00:12   baa
 * remove default state setting for customer info lookup
 * 
 *    Rev 1.3   Jun 24 2003 14:05:08   baa
 * show customer name instead of tags on customer select screen
 * 
 *    Rev 1.2   Sep 06 2002 17:25:22   baa
 * allow for currency to be display using groupings
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.1   Aug 14 2002 18:17:06   baa
 * format currency 
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.0   Apr 29 2002 14:56:56   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:52:52   msg
 * Initial revision.
 * 
 *    Rev 1.8   21 Feb 2002 14:00:30   jbp
 * added null check when setting data to renderer.
 * Resolution for POS SCR-1372: Selecting Customer to Delete on Customer Select when search done by Emp ID screen hangs application
 *
 *    Rev 1.7   28 Jan 2002 16:00:32   baa
 * fixing dual list
 * Resolution for POS SCR-824: Application crashes on Customer Add screen after selecting Enter
 *
 *    Rev 1.6   28 Jan 2002 10:39:34   baa
 * working on ui problems
 * Resolution for POS SCR-824: Application crashes on Customer Add screen after selecting Enter
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

// java imports
import java.awt.GridBagConstraints;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JLabel;

import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.utility.AddressConstantsIfc;
import oracle.retail.stores.domain.utility.AddressIfc;
import oracle.retail.stores.domain.utility.LocaleUtilities;
import oracle.retail.stores.domain.utility.PhoneConstantsIfc;
import oracle.retail.stores.domain.utility.PhoneIfc;
import oracle.retail.stores.foundation.tour.conduit.Dispatcher;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.manager.utility.UtilityManager;
import oracle.retail.stores.pos.ui.UIUtilities;
//------------------------------------------------------------------------------
/**
 *    This is the renderer for the Customer list. It formats
 *    customer objects for display as a list entry.
 *    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public class CustomerLineItemRenderer extends AbstractListRenderer
{
    /** revision number supplied by Team Connection */
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    public static int NAME       = 0;
    public static int ADDRESS    = 1;
    public static int HOMEPHONE  = 2;
    public static int ID         = 3;
    public static int CITYSTATE  = 4;
    public static int MAX_FIELDS = 5;

    public static int[] CUSTOMER_WEIGHTS = {30,40,30};

   /** Properties **/
    protected Properties props = null;

    //---------------------------------------------------------------------
    /**
     * Default Constructor.
     */
    //---------------------------------------------------------------------
    public CustomerLineItemRenderer()
    {
        super();
        setName("CustomerLineItemRenderer");
        // set default in case lookup fails
        firstLineWeights =  CUSTOMER_WEIGHTS;
        // look up the label weights
        setFirstLineWeights("labelWeights");

        fieldCount = MAX_FIELDS;
        lineBreak = HOMEPHONE;

        initialize();
    }

    //---------------------------------------------------------------------
    /**
        Initializes the optional components.
     */
    //---------------------------------------------------------------------
     protected void initOptions()
     {
        labels[NAME].setHorizontalAlignment(JLabel.LEFT);
        labels[ADDRESS].setHorizontalAlignment(JLabel.LEFT);
        labels[HOMEPHONE].setHorizontalAlignment(JLabel.CENTER);

        labels[ID].setHorizontalAlignment(JLabel.LEFT);
        labels[CITYSTATE].setHorizontalAlignment(JLabel.LEFT);

        GridBagConstraints constraints = uiFactory.getConstraints("Renderer");

        constraints.gridy = 1;
        constraints.weightx = 0.0;
        add(labels[ID], constraints);
        add(labels[CITYSTATE], constraints);
     }

    //---------------------------------------------------------------------
    /**
     * This sets the fields of this ListCellRenderer.
     * @param customer oracle.retail.stores.domain.customer.Customer
     */
    //---------------------------------------------------------------------
    public void setData(Object data)
    {
        if(data != null)
        {
            CustomerIfc customer    = (CustomerIfc)data;
           
            String customerName     = UIUtilities.retrieveText(BundleConstantsIfc.CUSTOMER_BUNDLE_NAME,
                                                               "Common", 
                                                               "CustomerName");
            Object[] values         = {customer.getFirstName(), customer.getLastName()};


            // fill in the message format with the customer's name
            customerName            = LocaleUtilities.formatComplexMessage(customerName, values);
            
            labels[NAME].setText(customerName);
            labels[ID].setText(customer.getCustomerID());

            StringBuffer cityState=new StringBuffer();
            Vector vectAddr = customer.getAddresses();
            String countryCode = null;


            if(vectAddr != null && !vectAddr.isEmpty())
            {
                  AddressIfc addr = (AddressIfc) vectAddr.elementAt(0);

                if(!addr.getCity().equals(""))
                {
                    cityState.append(addr.getCity()).append(",");
                }

                if(!Util.isEmpty(addr.getState()))
                {
                    cityState.append(addr.getState()).append(" ");
                }
                cityState.append(addr.getPostalCode());
                
                labels[CITYSTATE].setText(cityState.toString());

                Vector vectAddrLn = addr.getLines();
                if(vectAddrLn != null && !vectAddrLn.isEmpty())
                {
                    labels[ADDRESS].setText(vectAddrLn.elementAt(0).toString());
                }
                countryCode = addr.getCountry();
            }
            else
            {
                labels[CITYSTATE].setText("");
                labels[ADDRESS].setText("");
            }

            PhoneIfc hmPhone = customer.getPhoneByType(PhoneConstantsIfc.PHONE_TYPE_HOME);

            if(hmPhone!=null)
            {
            	if (hmPhone.getPhoneNumber() != null && !hmPhone.getPhoneNumber().equals("") && countryCode !=null)
            	{
                    UtilityManager util = (UtilityManager) Dispatcher.getDispatcher().getManager(UtilityManagerIfc.TYPE);
            		String formattedPhoneNumber = util.getFormattedNumber(hmPhone.getPhoneNumber(), countryCode);
            		labels[HOMEPHONE].setText(formattedPhoneNumber);
            	}
               else
               {
                  labels[HOMEPHONE].setText("");
               }
            }
            else
            {
                labels[HOMEPHONE].setText("");
            }
        }
    }

    //---------------------------------------------------------------------
    /**
     *  Update the fields based on the properties
     */
    //---------------------------------------------------------------------
    protected void setPropertyFields() { }

   //---------------------------------------------------------------------
    /**
     *  Set the properties to be used by this bean
        @param props the propeties object
     */
    //---------------------------------------------------------------------
    public void setProps(Properties props)
    {
        this.props = props;
    }

    //---------------------------------------------------------------------
    /**
        Creates the prototype cell to speed updates.
        @return Customer the prototype renderer
     */
    //---------------------------------------------------------------------
    public Object createPrototype()
    {
        CustomerIfc cust = DomainGateway.getFactory().getCustomerInstance();

        cust.setFirstName("XXXXXXXXXXXXXXXX");
        cust.setMiddleName("XXXXXXXXXXXXXXXX");
        cust.setLastName("XXXXXXXXXXXXXXXXXXXX");
        cust.setCustomerID("12345678901234");

        AddressIfc addr = DomainGateway.getFactory().getAddressInstance();

        addr.setAddressType(AddressConstantsIfc.ADDRESS_TYPE_HOME);
        addr.setCity("XXXXXXXXXXXXXXXXXXXX");
        addr.setState("XX");

        Vector lineVect = new Vector();
        String addrLine = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        lineVect.addElement(addrLine);
        addr.setLines(lineVect);
        Vector addrVect = new Vector();
        addrVect.addElement(addr);
        cust.setAddresses(addrVect);

        PhoneIfc homePhone = DomainGateway.getFactory().getPhoneInstance();

        homePhone.setAreaCode("521");
        homePhone.setPhoneNumber("555-1234");

        Vector phoneVect = new Vector();
        phoneVect.addElement(homePhone);
        cust.setPhones(phoneVect);

        return(cust);
    }
    //---------------------------------------------------------------------
    /**
       Retrieves the Team Connection revision number. <P>
       @return String representation of revision number
    */
    //---------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return(Util.parseRevisionNumber(revisionNumber));
    }

    //---------------------------------------------------------------------
    /**
     * main entrypoint - starts the part when it is run as an application
     * @param args java.lang.String[]
     */
    //---------------------------------------------------------------------
    public static void main(java.lang.String[] args)
    {
        UIUtilities.setUpTest();

        CustomerLineItemRenderer bean = new CustomerLineItemRenderer();
        bean.setData(bean.createPrototype());
        UIUtilities.doBeanTest(bean);
    }

}
