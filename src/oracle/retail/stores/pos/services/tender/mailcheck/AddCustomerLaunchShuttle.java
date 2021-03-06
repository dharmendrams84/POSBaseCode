/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/mailcheck/AddCustomerLaunchShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:48 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    acadar    02/11/10 - removed journaling from the shuttle
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *  3    360Commerce 1.2         3/31/2005 4:27:09 PM   Robert Pearse
 *  2    360Commerce 1.1         3/10/2005 10:19:31 AM  Robert Pearse
 *  1    360Commerce 1.0         2/11/2005 12:09:23 PM  Robert Pearse
 * $
 * Revision 1.3  2004/09/23 00:07:16  kmcbride
 * @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 * Revision 1.2  2004/07/28 19:54:29  dcobb
 * @scr 6355 Can still search on original business name after it was changed
 * Modified JdbcSelectBusiness to search for name from pa_cnct table.
 *
 * Revision 1.1  2004/04/05 15:44:13  epd
 * @scr 4263 Moved Mail Bank Check to new sub tour
 *
 * Revision 1.6  2004/03/16 18:30:41  cdb
 * @scr 0 Removed tabs from all java source code.
 *
 * Revision 1.5  2004/02/27 23:17:44  bjosserand
 * @scr 0 Mail Bank Check
 * Revision 1.4 2004/02/17 18:36:03 epd @scr 0 Code cleanup. Returned unused
 * local variables.
 *
 * Revision 1.3 2004/02/12 16:48:22 mcs Forcing head revision
 *
 * Revision 1.2 2004/02/11 21:22:51 rhafernik @scr 0 Log4J conversion and code cleanup
 *
 * Revision 1.1.1.1 2004/02/11 01:04:12 cschellenger updating to pvcs 360store-current
 *
 *
 *
 * Rev 1.1 Feb 05 2004 14:25:08 bjosserand Mail Bank Check.
 *
 * Rev 1.0 Feb 04 2004 16:24:16 bjosserand Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.mailcheck;

import org.apache.log4j.Logger;

import oracle.retail.stores.pos.services.tender.TenderCargo;
import oracle.retail.stores.domain.employee.RoleFunctionIfc;
import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.services.customer.common.CustomerCargo;


//------------------------------------------------------------------------------
/**
 * Launch CustomerAdd from Tender.
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @deprecated Since POS 7.0, use tenderADO service
 */
//------------------------------------------------------------------------------
public class AddCustomerLaunchShuttle implements ShuttleIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = 1766224939014980969L;

    /**
     * The logger to which log messages will be sent.
     */
    protected static Logger logger =
        Logger.getLogger(oracle.retail.stores.pos.services.tender.mailcheck.AddCustomerLaunchShuttle.class);
    ;

    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
     * shuttle name constant
     */
    public static final String SHUTTLENAME = "AddCustomerLaunchShuttle";
    /**
     * tender cargo reference
     */
    protected TenderCargo tenderCargo;

    //--------------------------------------------------------------------------
    /**
     * Loads TenderCargo into the shuttle for use in unload().
     *
     * @param bus
     *            the bus being loaded
     */
    //--------------------------------------------------------------------------

    public void load(BusIfc bus)
    {
        tenderCargo = (TenderCargo) bus.getCargo();
    }

    //--------------------------------------------------------------------------
    /**
     * Makes a CustomerAddCargo and populates it. Copies known customer information to the new Customer object.
     *
     * @param bus
     *            the bus being unloaded
     */
    //--------------------------------------------------------------------------

    public void unload(BusIfc bus)
    {
        CustomerCargo customerCargo = (CustomerCargo) bus.getCargo();
        TenderableTransactionIfc trans = tenderCargo.getTransaction();

        // set role function ID
        customerCargo.setAccessFunctionID(RoleFunctionIfc.CUSTOMER_ADD_FIND);

        // set the transaction ID
        customerCargo.setTransactionID(trans.getTransactionID());
        customerCargo.setHistoryMode(false);
        customerCargo.setRegister(tenderCargo.getRegister());
        // set the access employee
        customerCargo.setOperator(tenderCargo.getOperator());

        // set the linkDoneSwitch
        customerCargo.setLinkDoneSwitch(CustomerCargo.LINK);

    }

    //----------------------------------------------------------------------
    /**
     * Returns a string representation of this object.
     *
     * @return String representation of object
     */
    //----------------------------------------------------------------------
    public String toString()
    { // begin toString()
        // result string
        String strResult =
            new String("Class:  AddCustomerLaunchShuttle (Revision " + getRevisionNumber() + ")" + hashCode());

        // pass back result
        return (strResult);
    } // end toString()

    //----------------------------------------------------------------------
    /**
     * Returns the revision number of the class.
     *
     * @return String representation of revision number
     */
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    { // begin getRevisionNumber()
        // return string
        return (revisionNumber);
    } // end getRevisionNumber()
}
