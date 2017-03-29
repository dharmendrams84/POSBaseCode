/* ===========================================================================
* Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/inquiry/iteminquiry/CreateTransactionRoad.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:44 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    nkgautam  12/14/10 - added restricted dob to transaction after
 *                         initialising
 *    nkgautam  12/01/10 - npe check added for dob skip transactions, where
 *                         transaction is still not initialized
 *    jswan     11/01/10 - Fixed issues with UNDO and CANCEL letters; this
 *                         includes properly canceling transactions when a user
 *                         presses the cancel button in the item inquiry and
 *                         item inquiry sub tours.
 *    jswan     10/29/10 - Moved transaction creation to an aisle.
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.inquiry.iteminquiry;

// foundation imports
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.transaction.SaleReturnTransaction;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.tour.application.LaneActionAdapter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;

//--------------------------------------------------------------------------
/**
    This road is traversed when the user has selected to add an item and it
    has passed all PLUItem requirements.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class CreateTransactionRoad extends LaneActionAdapter
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4914958451723442119L;
    /**
        revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
        This road is traversed when the user has selected to add an item and it
        has passed all PLUItem requirements.
        @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {
        ItemInquiryCargo cargo = (ItemInquiryCargo)bus.getCargo();
        if (cargo.getModifiedFlag() && 
            cargo.getPLUItem() != null &&
            cargo.getTransaction() == null)
        {
            SaleReturnTransactionIfc transaction = DomainGateway.getFactory().getSaleReturnTransactionInstance();
            transaction.setCashier(cargo.getOperator());
            transaction.setSalesAssociate(cargo.getOperator());
            boolean transReentry = cargo.getRegister().getWorkstation().isTransReentryMode();
            ((SaleReturnTransaction)transaction).setReentryMode(transReentry);

            UtilityManagerIfc utility =
            (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
            utility.initializeTransaction(transaction, bus, -1);
            transaction.setAgeRestrictedDOB(cargo.getRestrictedDOB());
            cargo.setTransaction(transaction);
        }
        else if(cargo.getTransaction() != null)
        {
          ((SaleReturnTransactionIfc)cargo.getTransaction()).setAgeRestrictedDOB(
              cargo.getRestrictedDOB());
        }
    }
}
