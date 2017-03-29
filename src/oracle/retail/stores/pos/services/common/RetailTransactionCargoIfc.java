/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/RetailTransactionCargoIfc.java /rgbustores_13.4x_generic_branch/2 2011/08/09 11:31:52 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   08/09/11 - formatting and removed deprecated code
 *    cgreene   05/27/10 - convert to oracle packaging
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;

/**
 * Methods common to the RetailTransactionCargo's
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public interface RetailTransactionCargoIfc extends TenderableTransactionCargoIfc
{
    /**
     * revision number of this class
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    /**
     * Retrieves the saved transaction
     * 
     * @return the RetailTransactionIfc that is being printed
     */
    public RetailTransactionIfc getRetailTransaction();

    /**
     * Retrieve the array of transactions on which items have been returned.
     * 
     * @return SaleReturnTransaction[]
     */
    public SaleReturnTransactionIfc[] getOriginalReturnTransactions();

}
