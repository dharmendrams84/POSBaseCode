/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/customer/common/LookupTaxIDCustomersSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:26 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    mahising  11/19/08 - Updated for review comments
 *    mahising  11/13/08 - Added for Customer module for both ORPOS and ORCO
 *    mahising  11/12/08 - added for customer
 *
 * ===========================================================================
 */

package oracle.retail.stores.pos.services.customer.common;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.arts.CustomerReadDataTransaction;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.UIUtilities;

// --------------------------------------------------------------------------
/**
 * Look up customer on the basis of tax ID.
 */
// --------------------------------------------------------------------------
public class LookupTaxIDCustomersSite extends PosSiteActionAdapter
{
    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    // ----------------------------------------------------------------------
    /**
     * Look up for customer by tax ID
     * <p>
     *
     * @param bus the bus traversing this lane
     */
    // ----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        String letterName = CommonLetterIfc.SUCCESS;
        CustomerCargo cargo = (CustomerCargo)bus.getCargo();
        boolean mailLetter = true;
        // attempt to do the database lookup
        try
        {
            // creating a transaction
            CustomerReadDataTransaction ct = null;
            ct = (CustomerReadDataTransaction)DataTransactionFactory
                    .create(DataTransactionKeys.CUSTOMER_READ_DATA_TRANSACTION);
            // array of customers found in db
            // calling method on transaction object
            CustomerIfc[] customerArray = ct.readCustomerbyTaxID(cargo.getCustomer());
            cargo.setCustomerList(customerArray);
            ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
            int maximumMatches = CustomerUtilities.getMaximumMatches(pm);
            if (customerArray.length > maximumMatches)
            {
                POSUIManagerIfc uiManager = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
                UIUtilities.setDialogModel(uiManager, DialogScreensIfc.ERROR, cargo.getDialogName(), null,
                        CommonLetterIfc.RETRY);
                mailLetter = false;
            }
        }
        catch (DataException e)
        {
            cargo.setDataExceptionErrorCode(e.getErrorCode());
            letterName = CommonLetterIfc.FAILURE;
        }
        if (mailLetter)
        {
            bus.mail(new Letter(letterName), BusIfc.CURRENT);
        }
    }
}
