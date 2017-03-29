/* =============================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * =============================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returnauthorization/CapturePositiveIDSite.java /rgbustores_13.4x_generic_branch/3 2011/09/02 13:05:38 cgreene Exp $
 * =============================================================================
 * NOTES
 * Created by Lucy Zhao (Oracle Consulting) for POS-RM integration.
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   09/02/11 - refactored method names around enciphered objects
 *    rrkohli   07/19/11 - encryption CR
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    mdecama   12/03/08 - POS to RM - Merged to Tip
 *    abondala  12/02/08 - RM-POS integration
 *    mdecama   12/03/08 - POS to RM Integration - Setting the PositiveID and
 *                         CustomerInformation to send to RM
 *    rkar      11/19/08 - (1) Changed call to get positive ID type string -
 *                         required because of i18N changes. (2) Removed fake
 *                         customer info (Peter Curtis)
 *    rkar      11/07/08 - Additions/changes for POS-RM integration
 *
 * =============================================================================
 */
package oracle.retail.stores.pos.services.returnauthorization;

import java.util.Iterator;
import java.util.Vector;

import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.common.utility.Util;
import oracle.retail.stores.domain.customer.CustomerGroupIfc;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.customer.CustomerInfoIfc;
import oracle.retail.stores.domain.discount.DiscountRuleIfc;
import oracle.retail.stores.domain.manager.rm.RPIMoreCustomerInfo;
import oracle.retail.stores.domain.manager.rm.RPIPositiveID;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.utility.AddressIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.domain.utility.PhoneIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;

/**
 * Site captures customer's Positive ID info, required by Returns Management.
 */
public class CapturePositiveIDSite extends PosSiteActionAdapter 
{
    private static final long serialVersionUID = 8837936584064809291L;

    /**
     * This site checks whether the positive id has been captured or not.
     * If not show the screen.
     * @param bus the bus arriving at this site
     */
    public void arrive(BusIfc bus)
    { 
        ReturnAuthorizationCargoIfc cargo = (ReturnAuthorizationCargo)bus.getCargo();

        RPIPositiveID         rpiPositiveID = null;
        RPIMoreCustomerInfo rpiMoreCustomerInfo = new RPIMoreCustomerInfo();

        // Customer information obtained from oracle.retail.stores.pos.services..
        // ..returns.returnoptions.CheckForPersonalIDRequiredSite
        CustomerInfoIfc customerInfo = cargo.getCustomerInfo();
        if ( customerInfo != null && 
             customerInfo.getPersonalID().getEncryptedNumber() != null && 
             customerInfo.getPersonalID().getEncryptedNumber().length() > 0 )
        {
        	rpiPositiveID = new RPIPositiveID();
            rpiPositiveID.setNumber(customerInfo.getPersonalID().getEncryptedNumber());
            rpiPositiveID.setType(customerInfo.getLocalizedPersonalIDType().getCodeName());    
            
            rpiPositiveID.setIssuer(customerInfo.getPersonalIDCountry()+ "_" + customerInfo.getPersonalIDState());
        }

        RetailTransactionIfc transaction = cargo.getTransaction();
        if ( transaction != null )
        {
            CustomerIfc customer = transaction.getCustomer();
            AddressIfc address = null;
            if ( customer != null )
            {
                // If the Customer Name was entered when the Customer Identification was entered
            	// This is the one that goes to RM.
            	if (customerInfo != null &&
            	   (!Util.isEmpty(customerInfo.getFirstName()) || !Util.isEmpty(customerInfo.getLastName())))
                {
                	rpiMoreCustomerInfo.setFirstName(customerInfo.getFirstName());
                	rpiMoreCustomerInfo.setLastName(customerInfo.getLastName());
                }
                else
                {
                rpiMoreCustomerInfo.setFirstName(customer.getFirstName());
                rpiMoreCustomerInfo.setLastName(customer.getLastName());
                }
                rpiMoreCustomerInfo.setAddress1("");
                rpiMoreCustomerInfo.setAddress2("");
                CustomerGroupIfc[] groups = customer.getCustomerGroups();
                DiscountRuleIfc[] rules = null;
                if (groups != null)
                {
                    for (int i = 0; i < groups.length; i++)
                    {
                        rules = groups[i].getDiscountRules();
                        if (rules != null && rules.length > 0)
                        {
                            String discount = rules[0].getName(LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL));
                            if (discount != null && !discount.trim().equals(""))
                            {
                            	cargo.setCustomerType(discount);
                            }
                                
                        }
                    }
                }
                Vector<AddressIfc> addresses2 = customer.getAddresses();
                Vector<AddressIfc> addresses = addresses2;
                if ( addresses != null && addresses.size() > 0 )
                {
                    address = customer.getAddresses().elementAt(0);
                    Iterator<String> iter = address.getLinesIterator();
                    if (iter.hasNext())
                    {
                        rpiMoreCustomerInfo.setAddress1(iter.next());
                        if (iter.hasNext())
                        {
                            rpiMoreCustomerInfo.setAddress2(iter.next());
                        }
                    }
                }
                rpiMoreCustomerInfo.setBirthDate(customer.getBirthDateAsString());

                rpiMoreCustomerInfo.setCity(address.getCity());
                rpiMoreCustomerInfo.setCountry(address.getCountry());
                rpiMoreCustomerInfo.setCustomerID(customer.getCustomerID());
                rpiMoreCustomerInfo.setPostalCode(address.getPostalCode());
                rpiMoreCustomerInfo.setState(address.getState());
                Vector<PhoneIfc> phones = customer.getPhones();
                if ((phones != null) && (phones.size() > 0))
                {
                    PhoneIfc phone = phones.elementAt(0);
                    rpiMoreCustomerInfo.setTelephoneLocalNumber(phone.getPhoneNumber());
                    rpiMoreCustomerInfo.setTelephoneAreaCode(phone.getAreaCode());                    
                }
            }
            else
            {
            	if (customerInfo != null)
                {
                	rpiMoreCustomerInfo.setFirstName(customerInfo.getFirstName());
                	rpiMoreCustomerInfo.setLastName(customerInfo.getLastName());
                }
            	else
            	{
                    // Setting with empty spaces, otherwise, the XML Tag doesn't get created
            		rpiMoreCustomerInfo.setFirstName(" ");
            		rpiMoreCustomerInfo.setLastName(" ");
            	}
                rpiMoreCustomerInfo.setCity("");
                rpiMoreCustomerInfo.setCountry("");
                rpiMoreCustomerInfo.setCustomerID("");
                rpiMoreCustomerInfo.setPostalCode("");
                rpiMoreCustomerInfo.setState("");
                rpiMoreCustomerInfo.setTelephoneAreaCode("");
                rpiMoreCustomerInfo.setTelephoneLocalNumber("");
            }
            cargo.setPositiveID(rpiPositiveID);
            cargo.setMoreCustomerInfo(rpiMoreCustomerInfo);
            bus.mail(CommonLetterIfc.NEXT, BusIfc.CURRENT);  
        }
  
    }
    
}
