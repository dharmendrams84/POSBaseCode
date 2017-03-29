/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/customer/lookup/LookupBusinessSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:28 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:28:58 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:23:19 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:12:27 PM  Robert Pearse
 *
 *   Revision 1.8  2004/07/28 19:54:29  dcobb
 *   @scr 6355 Can still search on original business name after it was changed
 *   Modified JdbcSelectBusiness to search for name from pa_cnct table.
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.customer.lookup;

import java.util.Vector;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.arts.BusinessReadDataTransaction;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.customer.common.CustomerCargo;

//--------------------------------------------------------------------------
/**
    Query the database for business customers based on the search
    criteria entered by the user.
    <p>
**/
//--------------------------------------------------------------------------
public class LookupBusinessSite extends PosSiteActionAdapter
{

    /**
       revision number
    **/
    public static final String revisionNumber = "";

    //----------------------------------------------------------------------
    /**
       Sends a business customer lookup inquiry to the database manager.
       <P>
       @param  bus Service Bus
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        String letterName = CommonLetterIfc.SUCCESS;

        CustomerCargo cargo = (CustomerCargo)bus.getCargo();
        CustomerIfc customer = cargo.getCustomer();
        // exclude phones from the search query
        Vector tempPhones = customer.getPhones();
        customer.setPhones(null);

        // attempt to do the database lookup
        try
        {

            BusinessReadDataTransaction dataTransaction = null;

            dataTransaction = (BusinessReadDataTransaction) DataTransactionFactory.create(DataTransactionKeys.BUSINESS_READ_DATA_TRANSACTION);

            UtilityManagerIfc utility = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
            //set the LocaleRequestor in the Customer object
            customer.setLocaleRequestor(utility.getRequestLocales());

            CustomerIfc[] customerArray =
                dataTransaction.lookupBusiness(customer);

            cargo.setCustomerList(customerArray);
        }
        catch (DataException e)
        {
            logger.warn( "" + e + "");
            cargo.setDataExceptionErrorCode(e.getErrorCode());
            letterName = CommonLetterIfc.FAILURE;
        }

        //reassign the phone numbers
        customer.setPhones(tempPhones);

        bus.mail(new Letter(letterName), BusIfc.CURRENT);
    }

}
