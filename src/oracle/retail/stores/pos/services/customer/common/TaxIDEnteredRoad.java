/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/customer/common/TaxIDEnteredRoad.java /rgbustores_13.4x_generic_branch/2 2011/07/20 04:32:03 rrkohli Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    rrkohli   07/01/11 - Encryption CR
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    mahising  11/13/08 - Added for Customer module for both ORPOS and ORCO
 *    mahising  11/12/08 - added for customer
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.customer.common;

import oracle.retail.stores.common.data.JdbcUtilities;
import oracle.retail.stores.common.utility.StringUtils;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.arts.ARTSCustomer;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.foundation.factory.FoundationObjectFactory;
import oracle.retail.stores.foundation.manager.device.EncipheredData;
import oracle.retail.stores.foundation.manager.ifc.KeyStoreEncryptionManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.LaneActionAdapter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.keystoreencryption.EncryptionServiceException;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;

// --------------------------------------------------------------------------
/**
 * Traversed if tax ID is entered
 */
// --------------------------------------------------------------------------
public class TaxIDEnteredRoad extends LaneActionAdapter
{

    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    // ----------------------------------------------------------------------
    /**
     * Stores Tax ID entered by the user.
     * <p>
     *
     * @param bus the bus traversing this lane
     */
    // ----------------------------------------------------------------------
  public void traverse(BusIfc bus)
  {
    // get the user input
    POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
    String taxID = ui.getInput();
    // build customer
    CustomerIfc customer = DomainGateway.getFactory().getCustomerInstance();
    customer.setTaxID(getHashedCustomerTaxID(taxID));
    
    // customer.setTaxID(encryptionUtil.getEncryptedData(taxID.getBytes()));

    // store the tax ID in the cargo
    CustomerCargo cargo = (CustomerCargo) bus.getCargo();
    cargo.setCustomer(customer);
    cargo.setTaxID(taxID);
  }
  
  public String getHashedCustomerTaxID(String taxId)
  {
    String hashedTaxId = "";
    
    if(StringUtils.isNotBlank(taxId))
      try
      {
        KeyStoreEncryptionManagerIfc encryptionManager = (KeyStoreEncryptionManagerIfc) Gateway.getDispatcher()
            .getManager(KeyStoreEncryptionManagerIfc.TYPE);
        hashedTaxId = new String(JdbcUtilities.base64encode(encryptionManager.hash(taxId.getBytes())));
      }
      catch (Exception e)
      {
        logger.error("Couldn't create hash tax id", e);
      }
      return hashedTaxId;
    
  }
}
