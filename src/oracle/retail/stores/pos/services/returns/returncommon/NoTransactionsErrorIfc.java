/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returns/returncommon/NoTransactionsErrorIfc.java /rgbustores_13.4x_generic_branch/1 2011/08/18 08:44:04 jswan Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    jswan     08/18/11 - Added to create a common place for invalid return
 *                         item dialog identifiers.
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returncommon;

//--------------------------------------------------------------------------
/**
    This abstract class provides a common method for displaying the
    NO TRANSACTIONS ERROR SCREEN.
    <p>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface NoTransactionsErrorIfc
{
    public final String NO_TRANSACTIONS_FOUND_CUSTOMER = "NoTransactionsFoundCustomer";
    public final String INVALID_RETURN_ITEMS = "InvalidReturnItems";
    public final String INVALID_TRANSACTION_NO_QUANTITIES = "InvalidTransactionNoQuantities";
}
