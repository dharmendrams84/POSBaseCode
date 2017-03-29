/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifytransaction/retrieve/CheckForItemsInTransactionAisle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:30 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    jswan     01/22/10 - Code review modifications.
 *    jswan     01/21/10 - Fixed comments.
 *    jswan     01/21/10 - Fix an issue in which a returned gift card can be
 *                         modified during the period in which the transaction
 *                         has been suspended.
 *    jswan     01/21/10 - Add aisle that checks to see if there are items in
 *                         the transaction.
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifytransaction.retrieve;
// java imports
// foundation imports
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;

//--------------------------------------------------------------------------
/**
    Aisle to traverse to check if the removed gift card(s) were only items
    in the transaction. <P>
**/
//--------------------------------------------------------------------------
public class CheckForItemsInTransactionAisle extends PosLaneActionAdapter
{ 
    private static final long serialVersionUID = -8107756821339860666L;

    //----------------------------------------------------------------------
    /**
       Check if the removed gift card(s) were only items in the transaction. <P>
       @param bus Service Bus
    **/
    //----------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {
        // Get the transaction and initialize the letter value
        ModifyTransactionRetrieveCargo cargo = (ModifyTransactionRetrieveCargo) bus.getCargo();
        RetailTransactionIfc retTrans = cargo.getTransaction();
        String letterName =  CommonLetterIfc.SUCCESS;
        
        // If the transaction is the valid type
        if (retTrans instanceof SaleReturnTransactionIfc)
        {
            // Get the line items; if there are none reset the letter to CANCEL
            SaleReturnTransactionIfc trans = (SaleReturnTransactionIfc)retTrans;
            AbstractTransactionLineItemIfc[] items = trans.getItemContainerProxy().getLineItems();
            if (items.length == 0)
            {
                cargo.setCancellingRecreatedTransaction(true);
                letterName = CommonLetterIfc.CANCEL;
            }
        }
        
        bus.mail(new Letter(letterName), BusIfc.CURRENT);
    }
}
