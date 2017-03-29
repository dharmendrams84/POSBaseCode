/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/customer/common/LookupCustomerSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:26 mszekely Exp $
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
 *    2    360Commerce 1.1         3/10/2005 10:23:20 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:12:28 PM  Robert Pearse
 *
 *   Revision 1.9  2004/06/03 14:47:43  epd
 *   @scr 5368 Update to use of DataTransactionFactory
 *
 *   Revision 1.8  2004/04/20 13:11:00  tmorris
 *   @scr 4332 -Sorted imports
 *
 *   Revision 1.7  2004/04/12 18:58:36  pkillick
 *   @scr 4332 -Replaced direct instantiation(new) with Factory call.
 *
 *   Revision 1.6  2004/03/11 20:26:41  aschenk
 *   @scr 3932 - Phone number was being assigned null while looking up a customer.  Added a check for a null phone number and reassigned the phone number after the customer lookup.
 *
 *   Revision 1.5  2004/03/03 23:15:06  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.4  2004/02/17 16:08:08  kll
 *   @scr 3844: exclude phones from search query
 *
 *   Revision 1.3  2004/02/12 16:49:25  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:40:12  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 15:55:24   CSchellenger
 * Initial revision.
 *
 *    Rev 1.0   Apr 29 2002 15:34:00   msg
 * Initial revision.
 *
 *    Rev 1.2   21 Mar 2002 11:12:12   baa
 * Add Too Many Customers dialog
 * Resolution for POS SCR-568: Too Many Matches screen name and text errors not to spec
 *
 *    Rev 1.1   Mar 18 2002 23:11:42   msg
 * - updated copyright
 *
 *    Rev 1.0   Mar 18 2002 11:24:28   msg
 * Initial revision.
 *
 *    Rev 1.1   16 Nov 2001 10:32:12   baa
 * Cleanup code & implement new security model on customer
 * Resolution for POS SCR-263: Apply new security model to Customer Service
 *
 *    Rev 1.0   Sep 21 2001 11:15:10   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:06:46   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.customer.common;

// foundation imports
import java.util.Vector;

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
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.UIUtilities;

//--------------------------------------------------------------------------
/**
    Query the database for customers based on the search
    criteria entered by the user.
    <p>
    $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class LookupCustomerSite extends PosSiteActionAdapter
{

    /**
       revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
       Sends a customer lookup inquiry to the database manager.
       <P>
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        String letterName = CommonLetterIfc.SUCCESS;
        boolean mailLetter = true;

        CustomerCargo cargo = (CustomerCargo)bus.getCargo();

        // exclude phones from the search query
        Vector tempPhones = cargo.getCustomer().getPhones();
        cargo.getCustomer().setPhones(null);

        // attempt to do the database lookup
        try
        {
            CustomerReadDataTransaction ct = null;

            ct = (CustomerReadDataTransaction) DataTransactionFactory.create(DataTransactionKeys.CUSTOMER_READ_DATA_TRANSACTION);
            UtilityManagerIfc utility = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
            //set the LocaleRequestor in the Customer object
            CustomerIfc customer = cargo.getCustomer();
            customer.setLocaleRequestor(utility.getRequestLocales());

            CustomerIfc[] customerArray = ct.selectCustomers(customer);
            cargo.setCustomerList(customerArray);
            ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
            int maximumMatches = CustomerUtilities.getMaximumMatches(pm);

            if (customerArray.length > maximumMatches)
            {
                POSUIManagerIfc uiManager = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
                UIUtilities.setDialogModel(uiManager,DialogScreensIfc.ERROR,cargo.getDialogName(),null,CommonLetterIfc.RETRY);
                mailLetter = false;
            }

        }
        catch (DataException e)
        {
            logger.warn( "" + e + "");
            cargo.setDataExceptionErrorCode(e.getErrorCode());
            letterName = CommonLetterIfc.FAILURE;

        }
        //reassign the phone numbers
        cargo.getCustomer().setPhones(tempPhones);

        if (mailLetter)
        {
          bus.mail(new Letter(letterName), BusIfc.CURRENT);
        }

    }

}
