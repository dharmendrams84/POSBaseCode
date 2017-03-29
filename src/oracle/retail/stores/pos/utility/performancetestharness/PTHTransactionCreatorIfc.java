/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/utility/performancetestharness/PTHTransactionCreatorIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:37 mszekely Exp $
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
 *  1    360Commerce 1.0         5/1/2007 10:53:05 AM   Jack G. Swan    Changes
 *        for merge to trunk.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.utility.performancetestharness;

import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.financial.TillIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;


/**
 *  Interface that defines a method createNewSimilarTransaction that can be implemented 
 *  used to create different type of transactions.
 */
public interface PTHTransactionCreatorIfc
{
    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * Create a New Similar Transaction.
     * 
     * @param retailTransaction retail transaction reference
     * @param till till reference
     * @param register register reference
     * @param sequenceNumber the already incremented transaction sequence number
     *            for the register in the store.
     */
    public void createNewSimilarTransaction(RetailTransactionIfc retailTransaction, TillIfc till,
            RegisterIfc register, long sequenceNumber);
}
