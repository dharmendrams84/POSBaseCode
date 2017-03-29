/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/send/displaysendmethod/AssignTransactionLevelInfoSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:03 mszekely Exp $
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
 *  4    360Commerce 1.3         5/1/2007 12:15:40 PM   Brett J. Larsen CR
 *       26474 - Tax Engine Enhancements for Shipping Carge Tax (for VAT
 *       feature)
 *       
 *  3    360Commerce 1.2         3/31/2005 4:27:14 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:19:40 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:09:31 PM  Robert Pearse   
 *
 * Revision 1.2  2004/09/01 14:47:47  rsachdeva
 * @scr 6791 Transaction Level Send
 *
 * Revision 1.1  2004/08/10 16:47:56  rsachdeva
 * @scr 6791 Transaction Level Send
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.send.displaysendmethod;

import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.financial.ShippingMethodIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionTotalsIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.LetterIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.send.address.SendCargo;

//--------------------------------------------------------------------------
/**
   Updates/ Adds the Shipping to address.
   The Shipping Method display for transaction level send happens
   after you click tender
   @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class AssignTransactionLevelInfoSite extends PosSiteActionAdapter
{
    /**
       revision number of this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
       done letter
    **/
    public static final String DONE = "Done";

    //----------------------------------------------------------------------
    /**
        Updates/ Adds the Shipping to address for transaction level send
        in progress. This site assigns the transaction level send<P>
        @param  bus  Service Bus
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        SendCargo cargo = (SendCargo) bus.getCargo();

       SaleReturnTransactionIfc transaction = cargo.getTransaction();

       TransactionTotalsIfc totals = transaction.getTransactionTotals();
       ShippingMethodIfc shippingMethod = DomainGateway.getFactory().getShippingMethodInstance();

       if (cargo.isItemUpdate())
       {
           //Only single send should reach here
           int index = cargo.getSendIndex();
           if (index > 0)
           {
               transaction.updateSendPackageInfo(index-1, shippingMethod, cargo.getShipToInfo());
           }
       }
       else
       {
           //Add send packages info
           transaction.addSendPackageInfo(shippingMethod,
                                     cargo.getShipToInfo());
           //Assign Send label count on Sale Return Line Items
           SaleReturnLineItemIfc[] items = cargo.getLineItems();
           for (int i = 0; i < items.length; i++)
           {
               items[i].setItemSendFlag(true);
               items[i].setSendLabelCount(totals.getItemSendPackagesCount());
           }
       }
       transaction.getTransactionTotals().setTransactionLevelSendAssigned(true);
       transaction.updateTransactionTotals();	// Must force tax recalculation
       totals.setBalanceDue(totals.getGrandTotal());

       LetterIfc letter = new Letter(DONE);
       bus.mail(letter, BusIfc.CURRENT);

    }
}

