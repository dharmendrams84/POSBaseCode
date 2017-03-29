/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifytransaction/ModifyTransactionTaxLaunchShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:31 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:29:05 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:36 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:41 PM  Robert Pearse   
 *
 *   Revision 1.4  2004/02/24 16:21:30  cdb
 *   @scr 0 Remove Deprecation warnings. Cleaned code.
 *
 *   Revision 1.3  2004/02/12 16:51:09  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:48  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:02:16   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:14:24   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:38:34   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:30:22   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:09:28   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifytransaction;
// java imports
import java.util.Vector;

import org.apache.log4j.Logger;

import oracle.retail.stores.pos.services.common.FinancialCargoShuttle;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionTaxIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.modifytransaction.tax.ModifyTransactionTaxCargo;

//------------------------------------------------------------------------------
/**
    Launch shuttle class for ModifyTransactionTax service. <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public class ModifyTransactionTaxLaunchShuttle extends FinancialCargoShuttle
{
    /**
        The logger to which log messages will be sent.
    **/
    protected static Logger logger = Logger.getLogger(oracle.retail.stores.pos.services.modifytransaction.ModifyTransactionTaxLaunchShuttle.class);

    /**
        vector of items from retail transaction
     **/
    protected Vector lineItems;

    /**
        transaction tax object
     **/
    protected TransactionTaxIfc transactionTax =null;
    /**
       Flag to determine whether a transaction can be created by the
       child service
    **/
    protected boolean createTransaction = false;

    /**
        Flag to determine whether a customer has been previouly linked
     **/

     protected boolean customerPreviouslyLinked = false;

    /**
        transaction
     **/
     protected RetailTransactionIfc transaction = null;

    /**
       revision number of this class
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
       modify transaction cargo
    **/
    protected ModifyTransactionCargo modifyTransactionCargo = null;

    //---------------------------------------------------------------------
    /**
       Loads parent (ModifyTransaction) cargo class. <P>
       @param b  bus interface
    **/
    //---------------------------------------------------------------------
    public void load(BusIfc bus)
    {

        // load financial cargo
        super.load(bus);

        // retrieve cargo
        modifyTransactionCargo = (ModifyTransactionCargo) bus.getCargo();
        transaction = modifyTransactionCargo.getTransaction();
        if (transaction != null)
        {
            lineItems = transaction.getLineItemsVector();
            transactionTax = (TransactionTaxIfc)transaction.getTransactionTax().clone();
            CustomerIfc customer = modifyTransactionCargo.getTransaction().getCustomer();
            if (customer != null)
            {
               customerPreviouslyLinked = true;
            }

        }
        else
        {
            UtilityManagerIfc utility =
              (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
            transactionTax = utility.getInitialTransactionTax(bus);
            lineItems = new Vector();
            createTransaction = true;
        }
    }

    //---------------------------------------------------------------------
    /**
       Unloads to child (ModifyTransactionTax) cargo class. <P>
       @param b  bus interface
    **/
    //---------------------------------------------------------------------
    public void unload(BusIfc bus)
    {
        // unload financial cargo
        super.unload(bus);

        // pull out transaction tax object, line items, etc.


        // retrieve cargo
        ModifyTransactionTaxCargo cargo = (ModifyTransactionTaxCargo)bus.getCargo();

        // update child cargo
        cargo.initialize(lineItems, transactionTax);
        cargo.setSalesAssociate(modifyTransactionCargo.getSalesAssociate());
        cargo.setCreateTransaction(createTransaction);

            // if the transaction exist pass it along to the child service
        if (customerPreviouslyLinked)
        {
           cargo.setCustomer(transaction.getCustomer());
           cargo.setCustomerPreviouslyLinked(true);
        }
            if (!createTransaction)
            {
              cargo.setTransaction(transaction);
            }
    }

}   // end class ModifyTransactionTaxLaunchShuttle

