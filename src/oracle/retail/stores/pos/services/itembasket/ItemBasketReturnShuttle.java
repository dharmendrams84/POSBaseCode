/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/itembasket/ItemBasketReturnShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:11 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    cgreen 05/26/10 - convert to oracle packaging
 *    abonda 01/03/10 - update header date
 *    aariye 04/02/09 - For Serial Number check of multiple quantities in
 *                      basket
 *    aariye 04/01/09 - For Item Basket
 *    mchell 04/01/09 - Setting alteration item flag for alteration items
 *    aariye 03/09/09 - FOr error in ItemBasket
 *    aariye 02/28/09 - For ejournal of ItemBasket
 *    aariye 02/19/09 - Added capapbility for Size check and items not
 *                      authorized for sale in basket
 *    vikini 02/03/09 - Including Code Review Comments
 *    aariye 02/02/09 - Added files for Item Basket feature
 *    aariye 01/28/09 - Adding elemts for Item Basket Feature
 *    vikini 01/23/09 - Formatting changes
 *    vikini 01/23/09 - Updating line items in basket Transaction
 *    vikini 01/21/09 - Creation of ITembasket return Shuttle
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.itembasket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.modifytransaction.ModifyTransactionCargo;

/*
 * The return shuttle is used to create the transaction, before going to Modify
 * Transaction
 *
 */

public class ItemBasketReturnShuttle implements ShuttleIfc
{
  protected static Logger logger = Logger
      .getLogger(oracle.retail.stores.pos.services.itembasket.ItemBasketReturnShuttle.class);

  ItemBasketCargo itemBsktCargo = null;

  public void load(BusIfc bus)
  {
    logger.debug("In the Item Basket Return Shuttle");
    itemBsktCargo = (ItemBasketCargo) bus.getCargo();
    SaleReturnTransactionIfc transaction = itemBsktCargo.getTransaction();

    if (transaction != null)
    {
      setBasketDetails(transaction);
    }
  }

  public void unload(BusIfc bus)
  {
    ModifyTransactionCargo cargo = (ModifyTransactionCargo) bus.getCargo();
    logger.debug(bus.getCurrentLetter().getName());
    SaleReturnTransactionIfc trn = itemBsktCargo.getTransaction();

    if (trn == null)
    {
      trn = initializeTransaction(bus);
      setBasketDetails(trn);
    }

    trn.setIsItemBasketTransactionComplete(true);
    cargo.setBasketDTO(itemBsktCargo.getBasket());
    cargo.setTransaction(trn);
  }

  private void setBasketDetails(SaleReturnTransactionIfc trn)
  {
    HashMap itemsMap = itemBsktCargo.getPLUItems();
    BasketDTO basket = itemBsktCargo.getBasket();

    ArrayList<String> itemIDs = itemBsktCargo.getItemIdList();
    SaleReturnLineItemIfc srli = null;

    if (basket != null)
    {
      ArrayList basketItems = basket.getBasketItems();
      BigDecimal qty = new BigDecimal(1.0);
      basket.setPLUItemMap(itemsMap);

      if (itemsMap != null)
      {
        for (int itmCtr = 0; itmCtr < itemsMap.size(); itmCtr++)
        {
          PLUItemIfc item = (PLUItemIfc) itemsMap.get(itemIDs.get(itmCtr));

          if (item != null)
          {
            for (int basketItmCtr = 0; basketItmCtr < basketItems.size(); basketItmCtr++)
            {
              BasketLineItemDTO basketLineItem = (BasketLineItemDTO) basketItems.get(basketItmCtr);
              if (basketLineItem.getItemID() != null && (basketLineItem.getItemID()).equals(item.getItemID()))
              {
                qty = basketLineItem.getItemQuantity();
              }

            }
            String srliItemId = null;
            BigDecimal oneQty = new BigDecimal(1.0);

            if (item.isSerializedItem() && qty.compareTo(oneQty) > 0)
            {
              for (int i = 0; i < qty.intValue(); i++)
              {
                srli = trn.addPLUItem(item, oneQty);
                srliItemId = srli.getItemID();
                basket.setSaleReturnItemInList(srli);
                if (item.isAlterationItem())
                {
                  srli.setAlterationItemFlag(true);
                }

              }
            }
            else
            {
              srli = trn.addPLUItem(item, qty);
              srliItemId = srli.getItemID();
              basket.setSaleReturnItemInList(srli);
              if (item.isAlterationItem())
              {
                srli.setAlterationItemFlag(true);
              }

            }



            // If the plu item is an alteration item then set the alteration item flag

            for (int basketItmCtr = 0; basketItmCtr < basketItems.size(); basketItmCtr++)
            {
              BasketLineItemDTO basketLineItem = (BasketLineItemDTO) basketItems.get(basketItmCtr);
              if (basketLineItem.getItemID().equals(srliItemId))
              {
                srli.setItemSizeCode(basketLineItem.getItemSizeCode());
              }

            }

            //basket.setSaleReturnItemInList(srli);
            qty = new BigDecimal(1.0);
          }
        }

        basket.setTransaction(trn);
      }
    }
  }

  private SaleReturnTransactionIfc initializeTransaction(BusIfc bus)
  {
    ModifyTransactionCargo cargo = (ModifyTransactionCargo) bus.getCargo();
    SaleReturnTransactionIfc transaction = DomainGateway.getFactory().getSaleReturnTransactionInstance();
    transaction.setCashier(cargo.getOperator());
    transaction.setSalesAssociate(cargo.getSalesAssociate());

    UtilityManagerIfc utility = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
    utility.initializeTransaction(transaction, bus, -1);
    return transaction;
  }
}
