/* =============================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * =============================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returnauthorization/ReturnAuthorizationCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:50 mszekely Exp $
 * =============================================================================
 * NOTES
 * Created by Lucy Zhao (Oracle Consulting) for POS-RM integration.
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    abondala  12/02/08 - RM-POS integration
 *    rkar      11/07/08 - Additions/changes for POS-RM integration
 *
 * =============================================================================
 */
package oracle.retail.stores.pos.services.returnauthorization;

import oracle.retail.stores.pos.services.common.AbstractFinancialCargoIfc;
import oracle.retail.stores.domain.lineitem.ReturnResponseLineItemIfc;
import oracle.retail.stores.domain.manager.rm.RPIFinalResultIfc;
import oracle.retail.stores.domain.manager.rm.RPIMoreCustomerInfo;
import oracle.retail.stores.domain.manager.rm.RPIPositiveID;
import oracle.retail.stores.domain.manager.rm.RPIRequestIfc;
import oracle.retail.stores.domain.manager.rm.RPIResponseIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;

/**
 *  Carge interface, that carries information to Returns Management server
 */
public interface ReturnAuthorizationCargoIfc extends AbstractFinancialCargoIfc
{
    public SaleReturnTransactionIfc[] getOriginalReturnTransactions();
    public void setOriginalReturnTransactions(SaleReturnTransactionIfc[] originalReturnTransactions);
    public RetailTransactionIfc getTransaction();
    public void setTransaction(RetailTransactionIfc transaction);

    public RPIPositiveID getPositiveID();
    public void setPositiveID(RPIPositiveID positiveID);

    public RPIMoreCustomerInfo getMoreCustomerInfo();
    public void setMoreCustomerInfo(RPIMoreCustomerInfo moreCustomerInfo);

    public RPIResponseIfc getReturnResponse();
    public void setReturnResponse(RPIResponseIfc returnResponse);

    public RPIRequestIfc getReturnRequest();
    public void setReturnRequest(RPIRequestIfc returnRequest);

    public RPIFinalResultIfc getReturnResult();
    public void setReturnResult(RPIFinalResultIfc returnResult);

    public ReturnResponseLineItemIfc[] getReturnResponseLineItems();
    public void setReturnResponseLineItems(ReturnResponseLineItemIfc[] returnResponseLineItems);

    public int[] getSelectedRows();
    public void setSelectedRows(int[] selectedRows);
    public String getCustomerType() ;
	public void setCustomerType(String customerType) ;
}
