/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifytransaction/ModifyTransactionRetrieveReturnShuttle.java /rgbustores_13.4x_generic_branch/2 2011/08/12 10:28:11 mchellap Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    mchellap  08/12/11 - BUG#11854626 Customer Information not send to RM for
 *                         retrieved transactions
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         9/25/2007 9:34:12 AM   Bret Courtney
 *         setting sales associate retrieving transaction as the sales
 *         associate in the transaction itself
 *    3    360Commerce 1.2         3/31/2005 4:29:05 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:35 AM  Robert Pearse   
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
 *    Rev 1.0   Aug 29 2003 16:02:14   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Aug 08 2002 08:36:40   jriggins
 * Replaced concat of customer name in favor of formatting the text from the CustomerAddressSpec.CustomerName bundle in customerText.
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.0   Apr 29 2002 15:14:18   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:38:28   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:30:16   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:09:28   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifytransaction;
// foundation imports
import org.apache.log4j.Logger;

import oracle.retail.stores.pos.services.common.FinancialCargoShuttle;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.utility.LocaleUtilities;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.modifytransaction.retrieve.ModifyTransactionRetrieveCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;

//--------------------------------------------------------------------------
/**
    Return shuttle class for ModifyTransactionRetrieve service. <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/2 $
**/
//--------------------------------------------------------------------------
public class ModifyTransactionRetrieveReturnShuttle extends FinancialCargoShuttle
{                                                                               // begin class ModifyTransactionRetrieveReturnShuttle
    /**
        The logger to which log messages will be sent.
    **/
    protected static Logger logger = Logger.getLogger(oracle.retail.stores.pos.services.modifytransaction.ModifyTransactionRetrieveReturnShuttle.class);

    /**
       revision number of this class
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    /**
        Customer name bundle tag
    **/
    public static final String CUSTOMER_NAME_TAG = "CustomerName";
    /**
        Customer name default text
    **/
    public static final String CUSTOMER_NAME_TEXT = "{0} {1}";
    
    /**
       transaction to be retrieved
    **/
    protected RetailTransactionIfc transaction;

    /**
       This array contains a list of SaleReturnTransacions on which
       returns have been completed.  It will be used if a transaction with
       returned lineitems is retrieved.
    **/
    protected SaleReturnTransactionIfc[] originalReturnTransactions;

    //---------------------------------------------------------------------
    /**
       Loads from child (ModifyTransactionRetrieve) cargo class. <P>
       @param b  bus interface
    **/
    //---------------------------------------------------------------------
    public void load(BusIfc bus)
    {

        // load financial cargo
        super.load(bus);

        ModifyTransactionRetrieveCargo cargo =
            (ModifyTransactionRetrieveCargo) bus.getCargo();
        transaction = cargo.getTransaction();

        originalReturnTransactions = cargo.getOriginalReturnTransactions();

    }

    //---------------------------------------------------------------------
    /**
       Unloads to parent (ModifyTransaction) cargo class. <P>
       @param b  bus interface
    **/
    //---------------------------------------------------------------------
    public void unload(BusIfc bus)
    {

        // unload financial cargo
        super.unload(bus);

        ModifyTransactionCargo cargo =
            (ModifyTransactionCargo) bus.getCargo();
        if (transaction != null)
        {
        	// current sales associate always overrides any existing sales associate in the transaction
        	transaction.setSalesAssociate(cargo.getSalesAssociate());

        	cargo.setTransaction(transaction);
            cargo.setUpdateParentCargoFlag(true);
            // set status according to customer setting
            POSUIManagerIfc ui =
                    (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
            CustomerIfc customer = transaction.getCustomer();
            // if customer is null, no change required
            // (status field should already be blank since no
            // transaction has been initiated)
            if (customer != null)
            {
                Object[] parms = { customer.getFirstName(), customer.getLastName() };
                UtilityManagerIfc utility = 
                  (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);
                String pattern = 
                  utility.retrieveText("CustomerAddressSpec",
                                       BundleConstantsIfc.CUSTOMER_BUNDLE_NAME,
                                       CUSTOMER_NAME_TAG,
                                       CUSTOMER_NAME_TEXT);
                String customerName = 
                  LocaleUtilities.formatComplexMessage(pattern, parms);
                
                cargo.setCustomerInfo(transaction.getCustomerInfo());
                
                // set the customer's name in the status area
                ui.customerNameChanged(customerName);
            }
            //Refresh the sales associate name on the sell item screen.
            EmployeeIfc salesAssociate = transaction.getSalesAssociate();
            if(salesAssociate != null)
            {
                ui.salesAssociateNameChanged(salesAssociate.getPersonName().getFirstLastName());
            }
        }

        // add all original return transactions to the parent cargo list
        if (originalReturnTransactions != null)
        {
            for (int i=0; i<originalReturnTransactions.length; i++)
            {
                cargo.addOrignalReturnTransaction(originalReturnTransactions[i]);
            }
        }


    }

    //---------------------------------------------------------------------
    /**
       Returns the string representation of the object. <P>
       @return String representation of object
    **/
    //---------------------------------------------------------------------
    public String toString()
    {
        // result string
        StringBuffer strResult = new StringBuffer();
        strResult.append("Class:  ModifyTransactionRetrieveReturnShuttle")
            .append(" (Revision ").append(getRevisionNumber())
            .append(")").append(hashCode());
        return(strResult.toString());
    }

    //---------------------------------------------------------------------
    /**
       Returns the revision number. <P>
       @return String representation of revision number
    **/
    //---------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return(revisionNumber);
    }
}                                                                       // end class ModifyTransactionRetrieveReturnShuttle

