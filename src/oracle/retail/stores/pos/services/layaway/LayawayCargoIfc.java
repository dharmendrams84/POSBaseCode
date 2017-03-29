/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/layaway/LayawayCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:14 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    nkgautam  09/20/10 - refractored code to use a single class for checking
 *                         cash in drawer
 *    nkgautam  09/20/10 - refractored code to use a single class for checking
 *                         cash in drawer
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    nkgautam  02/01/10 - Added getter and setter methods for cash drawer
 *                         under warnings
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:28:49 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:23:01 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:12:15 PM  Robert Pearse
 *
 *   Revision 1.4  2004/04/08 20:33:03  cdb
 *   @scr 4206 Cleaned up class headers for logs and revisions.
 *
 *   Revision 1.3  2004/02/12 16:50:46  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:22  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:17  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:00:20   CSchellenger
 * Initial revision.
 *
 *    Rev 1.0   Apr 29 2002 15:19:56   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:34:30   msg
 * Initial revision.
 *
 *    Rev 1.0   Sep 21 2001 11:20:52   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:08:22   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.layaway;

import oracle.retail.stores.pos.services.common.DBErrorCargoIfc;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.transaction.LayawayTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

/**
 * Interface defines constants and method signatures for processing search
 * criteria and operations common to the layaway services. This interface is
 * provided for all layaway services.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface LayawayCargoIfc extends DBErrorCargoIfc, CargoIfc
{
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * Gets the layaway customer.
     * 
     * @return Customer
     */
    public CustomerIfc getCustomer();

    /**
     * Sets the layaway customer.
     * 
     * @param Customer
     */
    public void setCustomer(CustomerIfc value);

    /**
     * Sets the initial Layaway Transaction.
     * 
     * @param LayawayTransactionIfc
     */
    public void setInitialLayawayTransaction(LayawayTransactionIfc value);

    /**
     * Retrieves the initial layaway transactoin
     * 
     * @return LayawayTransactoinIfc
     */
    public LayawayTransactionIfc getInitialLayawayTransaction();

    /**
     * Retrieves the seed transactoin
     * 
     * @return TransactionIfc
     */
    public TransactionIfc getSeedLayawayTransaction();

    /**
     * Sets the seed Transaction.
     * 
     * @param LayawayTransactionIfc
     */
    public void setSeedLayawayTransaction(TransactionIfc value);

    /**
     * Sets the tenderable transaction.
     * 
     * @param tenderable transaction
     */
    public void setTenderableTransaction(TenderableTransactionIfc value);

    /**
     * Gets the current tenderable tansaction.
     * 
     * @return tenderable transaction
     */
    public TenderableTransactionIfc getTenderableTransaction();

    /**
     * Gets the current sale tansaction.
     * 
     * @return sale transaction
     */
    public SaleReturnTransactionIfc getSaleTransaction();

    /**
     * Sets the sale transaction.
     * 
     * @param sale transaction
     */
    public void setSaleTransaction(SaleReturnTransactionIfc value);

    /**
     * Gets the sales associate.
     * 
     * @return EmployeeIfc sales associate
     */
    public EmployeeIfc getSalesAssociate();

    /**
     * Resets the layaway cargo attributes to null.
     */
    public void resetLayawayCargo();
    
    /**
     * Gets the cash drawer under warning boolean
     * @return
     */
    public boolean isCashDrawerUnderWarning();
    
    /**
     * sets the Cash drawer under warning boolean
     * @param cashDrawerUnderWarning
     */
    public void setCashDrawerUnderWarning(boolean cashDrawerUnderWarning);

}
