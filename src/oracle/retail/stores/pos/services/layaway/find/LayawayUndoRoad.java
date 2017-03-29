/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/layaway/find/LayawayUndoRoad.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:13 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:28:50 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:04 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:17 PM  Robert Pearse   
 *
 *   Revision 1.4  2004/09/27 22:32:03  bwf
 *   @scr 7244 Merged 2 versions of abstractfinancialcargo.
 *
 *   Revision 1.3  2004/02/12 16:50:52  mcs
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
 *    Rev 1.0   Aug 29 2003 16:00:44   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:20:52   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:35:22   msg
 * Initial revision.
 * 
 *    Rev 1.0   08 Feb 2002 16:35:00   jbp
 * Initial revision.
 * Resolution for POS SCR-995: Escaping from Layaway List causes customer to unlink and disables Find button on Layaway Options
 *
 *    Rev 1.0   Sep 21 2001 11:21:28   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:08:34   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.layaway.find;

//foundation imports
import oracle.retail.stores.pos.services.common.AbstractFinancialCargo;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;

//------------------------------------------------------------------------------
/**
    Displays the menu screen for finding layaway(s).
    If customer is linked the customer is left on the transaction
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------

public class LayawayUndoRoad extends PosLaneActionAdapter
{
    /**
        lane name constant
    **/
    public static final String LANENAME = "LayawayUndoRoad";

    /**
        revision number supplied by source-code-control system
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //--------------------------------------------------------------------------
    /**
            Turns the seed transaction into a Sale Return
            Transaction with linked customer attached.
            <P>
            @param bus the bus arriving at this site
    **/
    //--------------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {
        FindLayawayCargoIfc cargo = (FindLayawayCargoIfc)bus.getCargo();

        if (cargo.getTenderableTransaction() == null &&
            cargo.getSeedLayawayTransaction() != null)
        {
            cargo.setTenderableTransaction(
                instantiateSaleReturnTransaction(cargo, bus));
        }
    }

    //---------------------------------------------------------------------
    /**
       Instantiates an object implementing the SaleReturnTransactionIfc interface
       and sets it as the tendrable transaction in the cargo.. <P>
       @return object implementing LayawayIfc
    **/
    //---------------------------------------------------------------------
    static protected SaleReturnTransactionIfc instantiateSaleReturnTransaction(FindLayawayCargoIfc cargo, BusIfc bus)
    {
        TransactionIfc seedTransaction = cargo.getSeedLayawayTransaction();
        SaleReturnTransactionIfc saleReturnTransaction = DomainGateway.getFactory().getSaleReturnTransactionInstance();

        seedTransaction.setTransactionAttributes(saleReturnTransaction);

        // Not payments, pickups, and deletes,
        // but may be necessary for undo's which need to become sale return
        // transactions.
        saleReturnTransaction.setSalesAssociate(((AbstractFinancialCargo)cargo).getOperator());

        UtilityManagerIfc utility =
            (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
        saleReturnTransaction.
                setTransactionTax(utility.getInitialTransactionTax(bus));

        // journal unlink customer if customer exists...
        if (cargo.getCustomer() != null)
        {
            saleReturnTransaction.setCustomer(cargo.getCustomer());
            saleReturnTransaction.linkCustomer(cargo.getCustomer());
        }

        return saleReturnTransaction;
    }
}
