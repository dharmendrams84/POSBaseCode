/* ===========================================================================
* Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/validate/CustomerReturnShuttle.java /rgbustores_13.4x_generic_branch/2 2012/02/07 13:49:37 blarsen Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   02/06/12 - Moving GENERATE_SEQUENCE_NUMBER into
 *                         UtilityManagerIfc.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    asinton   12/11/09 - Implemented EJournaling of Price Promotion for items
 *                         including changes to price by linking a customer.
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:38 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:20:41 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:10:24 PM  Robert Pearse
 *
 *   Revision 1.5  2004/09/23 00:07:16  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.4  2004/04/09 16:56:00  cdb
 *   @scr 4302 Removed double semicolon warnings.
 *
 *   Revision 1.3  2004/02/12 16:48:21  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:22:50  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:12  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.1   08 Nov 2003 01:26:58   baa
 * cleanup -sale refactoring
 *
 *    Rev 1.0   Nov 06 2003 16:23:54   sfl
 * Initial revision.
 * Resolution for POS SCR-3430: Sale Service Refactoring
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale.validate;
// foundation imports
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.utility.LocaleUtilities;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.common.TagConstantsIfc;
import oracle.retail.stores.pos.services.customer.common.CustomerUtilities;
import oracle.retail.stores.pos.services.customer.main.CustomerMainCargo;
import oracle.retail.stores.pos.services.sale.SaleCargoIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.LineItemsModel;
import oracle.retail.stores.pos.ui.beans.StatusBeanModel;

import org.apache.log4j.Logger;
//--------------------------------------------------------------------------
/**
    Transfer necessary data from the Customer service back to the Sale service.
**/
//--------------------------------------------------------------------------
public class CustomerReturnShuttle implements ShuttleIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -6824596291789206449L;

    /**
        The logger to which log messages will be sent.
    **/
    protected static Logger logger = Logger.getLogger(oracle.retail.stores.pos.services.sale.validate.CustomerReturnShuttle.class);

    /**
       the customer main cargo
    **/
     protected CustomerMainCargo customerMainCargo = null;

    //----------------------------------------------------------------------
    /**
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void load(BusIfc bus)
    {

        // save the cargo from the CustomerIfc service in an instance variable
        customerMainCargo = (CustomerMainCargo)bus.getCargo();

    }

    //----------------------------------------------------------------------
    /**
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void unload(BusIfc bus)
    {
        UtilityManagerIfc utility =
          (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
        CustomerIfc customer = customerMainCargo.getCustomer();
        SaleCargoIfc cargo = (SaleCargoIfc)bus.getCargo();
        cargo.setRefreshNeeded(true);
        SaleReturnTransactionIfc transaction = cargo.getTransaction();

        // if the user wanted to link the customer with the transaction
        if (customerMainCargo.isLink() && (customer !=null) )
        {
            // set the CustomerIfc reference within the transaction
            if (transaction == null)
            {
                // Create transaction; the initializeTransaction() method is called
                // on UtilityManager
                transaction = DomainGateway.getFactory().getSaleReturnTransactionInstance();
                transaction.setCashier(cargo.getOperator());
                transaction.setSalesAssociate(cargo.getEmployee());
                CustomerUtilities.journalCustomerExit(customerMainCargo.getEmployeeID(),
                                                      customerMainCargo.getTransactionID());
                utility.initializeTransaction(transaction, bus, UtilityManagerIfc.GENERATE_SEQUENCE_NUMBER, customer.getCustomerID());
                cargo.setTransaction(transaction);
            }
            else
            {
               CustomerUtilities.journalCustomerLink(transaction.getCashier().getEmployeeID(),
                                                     customer.getCustomerID(), transaction.getTransactionID());
            }
            transaction.linkCustomer(customer);
            // journal the customer pricing after customer has been linked
            // to the transaction in order to pickup the correct pricing.
            CustomerUtilities.journalCustomerPricing(transaction, customer.getPricingGroupID());


          // set the customer's name in the status area
          POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
          StatusBeanModel statusModel = new StatusBeanModel();
          String[] vars = {customer.getFirstName(), customer.getLastName()};
          String pattern = utility.retrieveText("CustomerAddressSpec",
                                                BundleConstantsIfc.CUSTOMER_BUNDLE_NAME,
                                                TagConstantsIfc.CUSTOMER_NAME_TAG,
                                                TagConstantsIfc.CUSTOMER_NAME_PATTERN_TAG);
          String customerName = LocaleUtilities.formatComplexMessage(pattern,vars);
          statusModel.setCustomerName(customerName);
          LineItemsModel baseModel = new LineItemsModel();
          baseModel.setStatusBeanModel(statusModel);
          ui.setModel(POSUIManagerIfc.SHOW_STATUS_ONLY, baseModel);

        }
        CustomerUtilities.journalCustomerExit(customerMainCargo.getEmployeeID(),
                                               customerMainCargo.getTransactionID());
     }

}
