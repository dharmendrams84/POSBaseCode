/* ===========================================================================
* Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/CustomerLaunchShuttle.java /rgbustores_13.4x_generic_branch/2 2012/02/07 13:49:37 blarsen Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   02/06/12 - Creating the transaction before entering the
 *                         CustomerStation. CustomerStation logs many EJs which
 *                         require a new sequence number to avoid being
 *                         associated with previous transaction and confusing
 *                         the EJ concatenator.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    asinton   05/29/09 - Set the offline indicator to OFFLINE_ADD instead of
 *                         OFFLINE_LINK as orders require the attached customer
 *                         object.
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:37 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:20:40 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:10:22 PM  Robert Pearse
 *
 *   Revision 1.6  2004/09/23 00:07:11  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.5  2004/04/09 16:56:01  cdb
 *   @scr 4302 Removed double semicolon warnings.
 *
 *   Revision 1.4  2004/02/12 21:18:58  kll
 *   @scr 0: added comment
 *
 *   Revision 1.3  2004/02/12 16:48:17  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:22:50  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.1   Nov 07 2003 12:36:28   baa
 * use SaleCargoIfc
 * Resolution for 3430: Sale Service Refactoring
 *
 *    Rev 1.0   Nov 05 2003 14:13:52   baa
 * Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale;

import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.customer.common.CustomerCargo;
import oracle.retail.stores.pos.services.customer.common.CustomerUtilities;
import oracle.retail.stores.pos.services.customer.main.CustomerMainCargo;

import org.apache.log4j.Logger;

//--------------------------------------------------------------------------
/**
    Transfer necessary data from the POS service to the Customer service.
    <p>
    $Revision: /rgbustores_13.4x_generic_branch/2 $
**/
//--------------------------------------------------------------------------
public class CustomerLaunchShuttle implements ShuttleIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = 3017429145000038504L;

    /**
        The logger to which log messages will be sent.
    **/
    protected static Logger logger = Logger.getLogger(oracle.retail.stores.pos.services.sale.CustomerLaunchShuttle.class);

    /**
       revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    protected SaleCargoIfc saleCargo = null;


    //----------------------------------------------------------------------
    /**
       ##COMMENT-LOAD##
       <P>
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void load(BusIfc bus)
    {
        UtilityManagerIfc utility = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);

        saleCargo= (SaleCargoIfc)bus.getCargo();

        // create transaction before entering CustomerStation
        // CustomerStation logs EJs which need a valid trans/sequence id
        if (saleCargo.getTransaction() == null)
        {
            SaleReturnTransactionIfc transaction = DomainGateway.getFactory().getSaleReturnTransactionInstance();
            transaction.setCashier(saleCargo.getOperator());
            transaction.setSalesAssociate(saleCargo.getEmployee());
            utility.initializeTransaction(transaction, bus, UtilityManagerIfc.GENERATE_SEQUENCE_NUMBER, null);
            saleCargo.setTransaction(transaction);
        }

    }

    //----------------------------------------------------------------------
    /**
       ##COMMENT-UNLOAD##
       <P>
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void unload(BusIfc bus)
    {
        CustomerMainCargo cargo = (CustomerMainCargo)bus.getCargo();
        String transactionID = null;
        EmployeeIfc operador = saleCargo.getOperator();


        if (saleCargo.getTransaction() != null)
        {
            transactionID = saleCargo.getTransaction().getTransactionID();
            if (saleCargo.getTransaction().getCustomer() != null)
            {
                cargo.setCustomerLink(true);
                cargo.setOriginalCustomer(saleCargo.getTransaction().getCustomer());
            }
        }
        else
        {
          CustomerUtilities.journalCustomerEnter(operador.getEmployeeID(), transactionID);
        }



        // test comment

        cargo.setRegister(saleCargo.getRegister());
        cargo.setTransactionID(transactionID);
        cargo.setEmployee(operador);
        cargo.setOperator(operador);
        cargo.setOfflineIndicator(CustomerCargo.OFFLINE_ADD);
        cargo.setOfflineExit(false);

     }

    //----------------------------------------------------------------------
    /**
       Returns a string representation of this object.
       <P>
       @return String representation of object
    **/
    //----------------------------------------------------------------------
    public String toString()
    {
        String strResult = new String("Class:  getClass().getName() (Revision " +
                                      getRevisionNumber() +
                                      ")" + hashCode());
        return(strResult);
    }

    //----------------------------------------------------------------------
    /**
       Returns the revision number of the class.
       <P>
       @return String representation of revision number
    **/
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return(revisionNumber);
    }
}
