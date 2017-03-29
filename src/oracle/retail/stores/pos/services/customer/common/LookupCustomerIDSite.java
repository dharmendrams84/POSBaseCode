/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/customer/common/LookupCustomerIDSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:27 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    blarsen   11/10/09 - XbranchMerge blarsen_bug9047679-dup-customer-added
 *                         from rgbustores_13.1x_branch
 *    blarsen   10/30/09 - clearing new customer flag after lookup. This
 *                         prevents the looked up costomer from being re-added
 *                         to db as a new customer. This only occurs when
 *                         lookup occurs after a customer is added.
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         12/13/2005 4:42:39 PM  Barry A. Pape
 *         Base-lining of 7.1_LA
 *    3    360Commerce 1.2         3/31/2005 4:28:58 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:23:20 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:12:28 PM  Robert Pearse
 *
 *   Revision 1.7  2004/06/03 14:47:43  epd
 *   @scr 5368 Update to use of DataTransactionFactory
 *
 *   Revision 1.6  2004/04/20 13:11:00  tmorris
 *   @scr 4332 -Sorted imports
 *
 *   Revision 1.5  2004/04/12 18:58:36  pkillick
 *   @scr 4332 -Replaced direct instantiation(new) with Factory call.
 *
 *   Revision 1.4  2004/03/03 23:15:06  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
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
 *    Rev 1.1   May 27 2003 08:48:04   baa
 * rework customer offline flow
 * Resolution for 2387: Deleteing Busn Customer Lock APP- & Inc. Customer.
 *
 *    Rev 1.0   Apr 29 2002 15:34:00   msg
 * Initial revision.
 *
 *    Rev 1.1   Mar 18 2002 23:11:40   msg
 * - updated copyright
 *
 *    Rev 1.0   Mar 18 2002 11:24:28   msg
 * Initial revision.
 *
 *    Rev 1.1   16 Nov 2001 10:32:10   baa
 * Cleanup code & implement new security model on customer
 * Resolution for POS SCR-263: Apply new security model to Customer Service
 *
 *    Rev 1.0   Sep 21 2001 11:14:52   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:06:46   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.customer.common;

// foundation imports
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.arts.CustomerReadDataTransaction;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;

//--------------------------------------------------------------------------
/**
    Lookup the customer ID.
    $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class LookupCustomerIDSite extends PosSiteActionAdapter
{

    private static final long serialVersionUID = -8458849959324199242L;

    /**
        revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
        Checks for a customer with the given customer ID. <p>
        @param bus the bus arriving at this site
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        CustomerCargo cargo = (CustomerCargo)bus.getCargo();
        String letter = CommonLetterIfc.SUCCESS;
        // do the database lookup
        try
        {
            CustomerReadDataTransaction ct = null;

            ct = (CustomerReadDataTransaction) DataTransactionFactory.create(DataTransactionKeys.CUSTOMER_READ_DATA_TRANSACTION);
            cargo.getCustomer().setCustomerIDPrefix(Gateway.getProperty("application", "StoreID", null));
            CustomerIfc customer = cargo.getCustomer();
            UtilityManagerIfc utility = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
            //set the LocaleRequestor in the Customer object
            customer.setLocaleRequestor(utility.getRequestLocales());
            customer = ct.readCustomer(customer);
            cargo.setCustomer(customer);
            
            // HPQC 13_1 4007: duplicate customer added when customer looked up after adding a customer
            // clearing flag so looked-up customer is not considered "new" by SaveCustomerSite
            cargo.setNewCustomer(false);

        }
        catch (DataException e)
        {
            int error = e.getErrorCode();
            cargo.setDataExceptionErrorCode(error);
            letter = CommonLetterIfc.FAILURE;
        }
        bus.mail(new Letter(letter), BusIfc.CURRENT);
    }

}
