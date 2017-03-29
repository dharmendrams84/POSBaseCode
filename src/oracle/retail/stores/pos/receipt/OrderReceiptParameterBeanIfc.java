/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/receipt/OrderReceiptParameterBeanIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:40 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   12/11/08 - add set/getServiceType to api for printing
 *
 * ===========================================================================
 * $Log:
 *  1    360Commerce 1.0         4/30/2007 7:00:39 PM   Alan N. Sinton  CR
 *       26485 - Merge from v12.0_temp.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.receipt;

import oracle.retail.stores.domain.order.OrderIfc;

/**
 * Interface for the order parameter bean.
 * $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface OrderReceiptParameterBeanIfc extends ReceiptParameterBeanIfc
{
    /**
     * Sets the order.
     *
     * @param order
     */
    public void setOrder(OrderIfc order);
    /**
     * Returns the order.
     *
     * @return The order.
     */
    public OrderIfc getOrder();

    /**
     * Sets the service type for which the report is being generated for. This
     * value can be used for printing footer message like "PRINT ORDER",
     * "VIEW ORDER", "FILL ORDER" or "ORDER CANCELLATION".
     * 
     * @param serviceType short indicating the service type. OrderCargoIfc has a
     *            list of service type constants.
     * @see oracle.retail.stores.pos.services.order.common.OrderCargoIfc
     */
    public void setServiceType(int serviceType);

    /**
     * Gets the type of a service which the cargo will be used for. This
     * value can be used for printing footer message like "PRINT ORDER",
     * "VIEW ORDER", "FILL ORDER" or "ORDER CANCELLATION".
     * 
     * @return short constant indicating the type of service
     * @see oracle.retail.stores.pos.services.order.common.OrderCargoIfc
     */
    public int getServiceType();
}
