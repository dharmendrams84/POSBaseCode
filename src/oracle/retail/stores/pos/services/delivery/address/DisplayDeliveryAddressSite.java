/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/delivery/address/DisplayDeliveryAddressSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:49 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    npoola    02/11/09 - fix offline issue for customer
 *    aphulamb  01/02/09 - fix delivery issues
 *    aphulamb  11/22/08 - Checking files after code review by Naga
 *    aphulamb  11/13/08 - Check in all the files for Pickup Delivery Order
 *                         functionality
 *    aphulamb  11/13/08 - Display Delivery address
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.delivery.address;

import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.foundation.manager.ifc.DataManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.customer.common.CustomerUtilities;
import oracle.retail.stores.pos.services.pickup.PickupDeliveryOrderCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.CustomerInfoBeanModel;
import oracle.retail.stores.pos.ui.beans.DeliveryAddressBeanModel;
import oracle.retail.stores.pos.ui.beans.MailBankCheckInfoBeanModel;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;
import oracle.retail.stores.pos.ui.beans.StatusBeanModel;

public class DisplayDeliveryAddressSite extends PosSiteActionAdapter
{
    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * invalid postal code resource id
     */
    public static final String INVALID_POSTAL_CODE = "InvalidPostalCode";

    // --------------------------------------------------------------------------
    /**
     * Displays the delivery address detail screen. if cusotmer is already
     * linked with transaction then show the screen with populated customer
     * info.
     * <P>
     *
     * @param bus the bus arriving at this site
     */
    // --------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        PickupDeliveryOrderCargo pickupDeliveryOrderCargo = (PickupDeliveryOrderCargo)bus.getCargo();
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        UtilityManagerIfc utility = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);
        ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
        DataManagerIfc dm = (DataManagerIfc)bus.getManager(DataManagerIfc.TYPE);
        CustomerIfc customer = pickupDeliveryOrderCargo.getCustomer();
        boolean isOfflineCustomer = false;
        //during offline scenario customerID is set to customerName and lastName ...Nilesh
        if (((customer.getCustomerName().equals(customer.getCustomerID())) && (customer.getLastName().equals(customer
                .getCustomerID())))
                && (dm.getOnlineState() == false))
        {
            isOfflineCustomer = true;
        }
        MailBankCheckInfoBeanModel model = CustomerUtilities.copyCustomerToModel(customer,utility,pm);
        model.setIsDeliveryAddress(true);
        if (customer != null)
        {

            CustomerInfoBeanModel custModel = (CustomerInfoBeanModel)CustomerUtilities.getCustomerInfo(
                    customer, utility, pm);
            if(model.isBusinessCustomer())
            {
                model.setOrgName(custModel.getOrgName());
            }
            else
            {
                model.setFirstName(custModel.getFirstName());
                if (isOfflineCustomer)
                {
                    model.setLastName("");
                }
                else
                {
                model.setLastName(custModel.getLastName());
             }
               
             }
            model.setAddressLine1(custModel.getAddressLine1());
            model.setAddressLine2(custModel.getAddressLine2());
            model.setAddressLine3(custModel.getAddressLine3());
            model.setCountryIndex(custModel.getCountryIndex());
            model.setStateIndex(custModel.getStateIndex());
            model.setCity(custModel.getCity());
            model.setPostalCode(custModel.getPostalCode());
            model.setExtPostalCode(custModel.getExtPostalCode());
            model.setPhoneList(custModel.getPhoneList());
        }
        // set the customer's name in the status area
        CustomerIfc billingCustomer = pickupDeliveryOrderCargo.getCustomer();
        StatusBeanModel statusModel = new StatusBeanModel();
        statusModel.setCustomerName(billingCustomer.getFirstLastName());
        model.setStatusBeanModel(statusModel);
        NavigationButtonBeanModel globalModel = new NavigationButtonBeanModel();
        model.setGlobalButtonBeanModel(globalModel);
        ui.showScreen(POSUIManagerIfc.GET_DELIVERY_ADDRESS_SCREEN, model);
    }
}
