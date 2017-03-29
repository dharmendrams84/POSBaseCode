/* ===========================================================================
* Copyright (c) 2006, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/utility/tools/RetrievalGeneratorServiceIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:37 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/25/10 - extend tag service interface
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *  1    360Commerce 1.0         12/1/2006 9:54:57 AM   Rohit Sachdeva  
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.utility.tools;

import oracle.retail.stores.commerceservices.ServiceIfc;

/**
 * This class is used by TransactionGeneratorTool. See txntool.bat in pos bin
 * for info.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface RetrievalGeneratorServiceIfc extends ServiceIfc
{
    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * Retrieve Transaction and Create Subsequent Transactions
     * 
     * @param transactionSequenceNumberToRetrieve transaction sequence number to
     *            retrieve
     * @param numberOfTransactions number of subsequent transactions to create
     *            based on this
     * @param storeId store id got from properties file
     * @param workstationId workstation id got from properties file
     */
    public void generateSubsequentTransactions(long transactionSequenceNumberToRetrieve, int numberOfTransactions,
            String storeId, String workstationId);
}
