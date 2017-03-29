/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/externalorder/sellorderitem/SellOrderItemCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/11 16:05:18 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *
 * ===========================================================================
 */

package oracle.retail.stores.pos.services.externalorder.sellorderitem;

import oracle.retail.stores.commerceservices.externalorder.ExternalOrderIfc;
import oracle.retail.stores.domain.externalorder.ExternalOrderSaleItemIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.pos.services.common.ItemSizeCargoIfc;
import oracle.retail.stores.pos.services.common.PLUCargoIfc;
import oracle.retail.stores.pos.services.common.TimedCargoIfc;
import oracle.retail.stores.pos.services.sale.SaleCargoIfc;

public interface SellOrderItemCargoIfc extends SaleCargoIfc,
											ItemSizeCargoIfc,
											TimedCargoIfc,
											PLUCargoIfc
{
	//----------------------------------------------------------------------
	/**
	 * @return the external order
	 */
	//----------------------------------------------------------------------
	public ExternalOrderIfc getExternalOrder();

	//----------------------------------------------------------------------
	/**
	 * Set the external order
	 * @param externalOrder the external order
	 */
	//----------------------------------------------------------------------
	public void setExternalOrder(ExternalOrderIfc externalOrder);

	//----------------------------------------------------------------------
	/**
	 * @return the currentExternalOrderItem
	 */
	//----------------------------------------------------------------------
	public ExternalOrderSaleItemIfc getExternalOrderItem();

	//----------------------------------------------------------------------
	/**
	 * @param currentExternalOrderItem the currentExternalOrderItem to set
	 */
	//----------------------------------------------------------------------
	public void setExternalOrderItem(ExternalOrderSaleItemIfc currentExternalOrderItem);

	//----------------------------------------------------------------------
	/**
	 * @return the itemList
	 */
	//----------------------------------------------------------------------
	public PLUItemIfc[] getItemList();

	//----------------------------------------------------------------------
	/**
	 * @param itemList the itemList to set
	 */
	//----------------------------------------------------------------------
	public void setItemList(PLUItemIfc[] itemList);

}
