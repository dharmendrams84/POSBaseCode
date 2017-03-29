/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/cancel/CancelOrderReturnItemInfoAisle.java /rgbustores_13.4x_generic_branch/2 2011/09/09 17:41:55 sgu Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    sgu       09/08/11 - add house account as a refund tender
 *    cgreene   05/26/10 - convert to oracle packaging
 *
 * ===========================================================================
 * $Log:
 *  1    360Commerce 1.0         2/22/2008 10:32:00 AM  Pardee Chhabra  CR
 *       30191: Tender refund options are not displayed as per specification
 *       for Special Order Cancel feature.
 * $
 *
 * Revision 1.0 02/21/2008 12:09:24 PM baa @scr 30191
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.cancel;

// java imports
import java.util.ArrayList;
import java.util.Vector;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.lineitem.ItemTaxIfc;
import oracle.retail.stores.domain.lineitem.ReturnItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.domain.returns.ReturnTenderDataElementIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.domain.tender.TenderChargeIfc;
import oracle.retail.stores.domain.tender.TenderLineItemIfc;
import oracle.retail.stores.domain.transaction.OrderTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.services.order.pickup.PickupOrderCargo;

// --------------------------------------------------------------------------
/**
 * <p>
 *
 * This aisle updates the return info into order transaction for refund tender
 * options
 * </p>
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
// --------------------------------------------------------------------------
public class CancelOrderReturnItemInfoAisle extends PosLaneActionAdapter {

	/**
	 * revision number supplied by Team Connection
	 */
	public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

	/**
	 * Constant for generating the Transaction Sequence number.
	 */
	protected static final long GENERATE_SEQUENCE_NUMBER = -1;

	// ----------------------------------------------------------------------
	/**
	 * This aisle gets the items the user has selected from the UI.
	 * <P>
	 *
	 * @param bus
	 *            Service Bus
	 */
	// ----------------------------------------------------------------------
	public void traverse(BusIfc bus) {

		PickupOrderCargo cargo = (PickupOrderCargo) bus.getCargo();

		OrderTransactionIfc transaction = (OrderTransactionIfc) cargo
				.getTransaction();

		OrderIfc order = cargo.getOrder();

		SaleReturnTransactionIfc originalTransaction = (SaleReturnTransactionIfc) order
				.getOriginalTransaction();

		transaction
				.setReturnTenderElements(getOriginalTenders(originalTransaction
						.getTenderLineItems()));

		cargo.addOriginalReturnTransaction(originalTransaction);

		SaleReturnLineItemIfc[] lineItems = (SaleReturnLineItemIfc[]) transaction
				.getLineItems();

		Vector returnablelineItems = new Vector();

		// get all returnable line items
		if (lineItems != null && lineItems.length > 0) {
			SaleReturnLineItemIfc item;
			for (int i = 0; i < lineItems.length; i++) {
				item = (SaleReturnLineItemIfc) lineItems[i];
				if (item.isReturnable()) {
					returnablelineItems.addElement(item);
				}
			}
		}

		int rowsSelected = returnablelineItems.size();
		SaleReturnLineItemIfc[] tsLineItems = new SaleReturnLineItemIfc[rowsSelected];
		SaleReturnLineItemIfc[] rsLineItems = new SaleReturnLineItemIfc[rowsSelected];
		ReturnItemIfc[] returnItems = new ReturnItemIfc[rowsSelected];
		for (int i = 0; i < rowsSelected; i++) {
			SaleReturnLineItemIfc item = (SaleReturnLineItemIfc) returnablelineItems
					.elementAt(i);
			tsLineItems[i] = item;
			rsLineItems[i] = (SaleReturnLineItemIfc) item.clone();
			returnItems[i] = DomainGateway.getFactory().getReturnItemInstance();
			returnItems[i].setItemQuantity(item.getQuantityReturnable());
			returnItems[i].setFromRetrievedTransaction(true);
		}

		PLUItemIfc[] pluItems = null;
		// get all PLU items
		pluItems = getPLUItems(rsLineItems);
		int numItems = 0;
		if (returnItems != null && returnItems.length != 0) {

			numItems = returnItems.length;
			if (pluItems.length < numItems) {
				numItems = pluItems.length;
			}

			// If there is no transaction ....
			if (transaction == null) {
				UtilityManagerIfc utility = (UtilityManagerIfc) bus
						.getManager(UtilityManagerIfc.TYPE);

				transaction = DomainGateway.getFactory()
						.getOrderTransactionInstance();

				transaction.setSalesAssociate(cargo.getSalesAssociate());
				utility.initializeTransaction(transaction, bus,
						GENERATE_SEQUENCE_NUMBER);
				cargo.setTransaction(transaction);
			}

			// Process each return line item
			for (int i = 0; i < numItems; i++) {
				// Prepare line item.
				SaleReturnLineItemIfc srli = null;
				ReturnItemIfc ri = returnItems[i];
				if (ri != null) {
					// Use the Sale Return item from the transaction
					srli = lineItems[i];
					if (originalTransaction != null) {
						// cargo
						ri.setOriginalTransactionID(originalTransaction
								.getTransactionIdentifier());
						ri.setOriginalLineNumber(srli.getLineNumber());
						ri
								.setOriginalTransactionBusinessDate(originalTransaction
										.getBusinessDay());
						// ri.setHaveReceipt(cargo.haveReceipt());
						ri.setItemTax((ItemTaxIfc) srli.getItemTax().clone());
					}
					srli.setReturnItem(ri);
				}
			}
		}
		bus.mail(new Letter(CommonLetterIfc.CANCEL_ORDER_NEXT), BusIfc.CURRENT);
	}

	/**
	 * Get all PLU items from sales return items
	 *
	 * @param returnSaleLineItems
	 * @return PLUItemIfc[]
	 */
	private PLUItemIfc[] getPLUItems(SaleReturnLineItemIfc[] returnSaleLineItems) {
		ArrayList items = null;
		if (returnSaleLineItems != null) {
			items = new ArrayList();
			for (int i = 0; i < returnSaleLineItems.length; i++) {
				if (returnSaleLineItems[i] != null) {
					items.add(returnSaleLineItems[i].getPLUItem());
				}
			}
		}

		// copy data to array
		PLUItemIfc[] itemList = null;
		if (items != null) {
			itemList = new PLUItemIfc[items.size()];
			items.toArray(itemList);
		}

		return itemList;
	}

	// ----------------------------------------------------------------------
	/**
	 * Retrieve tenders from original transaction
	 *
	 * @param tenderList
	 * @return ReturnTenderDataElement[] list of tenders
	 */
	// ----------------------------------------------------------------------
	protected ReturnTenderDataElementIfc[] getOriginalTenders(
	        TenderLineItemIfc[] tenderList) {
	    ReturnTenderDataElementIfc[] tenders = new ReturnTenderDataElementIfc[tenderList.length];
	    for (int i = 0; i < tenderList.length; i++) {
	        tenders[i] = DomainGateway.getFactory().getReturnTenderDataElementInstance();
	        tenders[i].setTenderType(tenderList[i].getTypeCode());
	        if (tenderList[i].getTypeCode() == TenderLineItemIfc.TENDER_TYPE_CHARGE)
	        {
	            tenders[i].setCardType(((TenderChargeIfc)tenderList[i]).getCardType());
	        }
	        tenders[i].setAccountNumber(tenderList[i].getNumber());
	        tenders[i].setTenderAmount(tenderList[i].getAmountTender());
	    }
	    return tenders;
	}

}// ends class
