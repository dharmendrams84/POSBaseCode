/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/ModifyItemSerialNumberReturnShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 16:17:10 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    abondala  01/03/10 - update header date
 *    nkgautam  11/18/09 - Return Shuttle class for serializedItem tour
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale;

import oracle.retail.stores.pos.services.common.FinancialCargoShuttle;
import oracle.retail.stores.pos.services.sale.SaleCargoIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.services.modifyitem.serialnumber.SerializedItemCargo;

/**
 * Return Shuttle class for serialized Item tour
 * @author nkgautam
 *
 */
public class ModifyItemSerialNumberReturnShuttle extends FinancialCargoShuttle implements ShuttleIfc
{

  /**
   * Serialized Item Cargo
   */
  protected SerializedItemCargo serializedCargo;


  /**
   * Loads the Sale Cargo.
   * @param bus Service Bus to copy cargo from.
   */
  public void load(BusIfc bus)
  {
    serializedCargo = (SerializedItemCargo)bus.getCargo();

  }

  /**
   * Copies the SerializedItemCargo contents info to the Salecargo.
   * @param  bus     Service Bus to copy cargo to.
   */
  public void unload(BusIfc bus)
  {
    SaleCargoIfc cargo = (SaleCargoIfc)bus.getCargo();

    if (serializedCargo.getTransaction() != null)
    {
        cargo.setTransaction((SaleReturnTransactionIfc)serializedCargo.getTransaction());
    }
  }


}
