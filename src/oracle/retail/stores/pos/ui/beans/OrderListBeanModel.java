/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/OrderListBeanModel.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:57 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import oracle.retail.stores.domain.order.OrderSummaryEntryIfc;
import oracle.retail.stores.foundation.utility.Util;

//--------------------------------------------------------------------------
/**
 * This is the bean model that is used by the OrderListBean. <P>
 * This bean model is used to access arrays of data pertaining to
 * the Orders placed by customers.
 * @see oracle.retail.stores.pos.ui.beans.OrderListBean
 * @version $KW=@(#); $Ver=pos_4.5.0:2; $EKW;
 * @deprecated as of release 5.0.0
 */
//--------------------------------------------------------------------------
public class OrderListBeanModel extends POSBaseBeanModel
{
    /**
        revision number supplied by Team Connection
    **/
    public static final String revisionNumber = "$KW=@(#); $Ver=pos_4.5.0:2; $EKW;";

    /**
        constant for class name
    **/
    public static final String CLASSNAME = "OrderListBeanModel";

    private OrderSummaryEntryIfc[]  orderList       = null;
    private OrderSummaryEntryIfc    selectedOrder   = null;
    private String                  promptArgument  = null;

    //----------------------------------------------------------------------------
    /**
        OrderListBeanModel constructor comment.
    **/
    //----------------------------------------------------------------------------
    public OrderListBeanModel()
    {
    }

    //----------------------------------------------------------------------------
    /**
        OrderListBeanModel constructor comment.
    **/
    //----------------------------------------------------------------------------
    public OrderListBeanModel(OrderSummaryEntryIfc[] orders)
    {
        orderList = orders;
    }

    //----------------------------------------------------------------------------
    /**
        Gets the orderList property value.
        @return orderListIfc[] the orderList property value array.
        @see #setOrderList
    **/
    //----------------------------------------------------------------------------
    public OrderSummaryEntryIfc[] getOrderList()
    {
        return orderList;
    }

    //----------------------------------------------------------------------------
    /**
    * Sets the orderList property value.
    * @param orderList the new value for the property.
    * @see #getOrderList
    */
    //----------------------------------------------------------------------------
    public void setOrderList(OrderSummaryEntryIfc[] orderList)
    {
        this.orderList = orderList;
    }

    //----------------------------------------------------------------------------
    /**
        Gets the selectedOrder property value.
        @return OrderIfc the order property value.
        @see #setSelectedOrder
    **/
    //----------------------------------------------------------------------------
    public OrderSummaryEntryIfc getSelectedSummary()
    {
        return selectedOrder;
    }

    //----------------------------------------------------------------------------
    /**
    * Sets the selectedOrder property value.
    * @param selectedOrder the new value for the property.
    * @see #getSelectedSummary
    */
    //----------------------------------------------------------------------------
    public void setSelectedOrder(OrderSummaryEntryIfc order)
    {
        // copy the selected order obtained from the
        // site into this bean model's selected order value
        selectedOrder = order;

    }

    //----------------------------------------------------------------------
    /**
     * Sets the prompt argument string.
     */
    //----------------------------------------------------------------------
    public void setPromptArgument(String arg)
    {
        promptArgument = arg;
    }

    //----------------------------------------------------------------------
    /**
     * Returns the prompt argument string.
     * @return String argument.
     */
    //----------------------------------------------------------------------
    public String getPromptArgument()
    {
        return promptArgument;
    }
    //---------------------------------------------------------------------
    /**
        Method to default display string function. <P>
        @return String representation of object
    **/
    //---------------------------------------------------------------------
    public String toString()
    {
        // result string
        String strResult = new String("Class: " + CLASSNAME + " (Revision "
                                       + getRevisionNumber() + ")" + hashCode());
        // pass back result
        return(strResult);
    }

    //---------------------------------------------------------------------
    /**
        Retrieves the Team Connection revision number. <P>
        @return String representation of revision number
    **/
    //---------------------------------------------------------------------
    public String getRevisionNumber()
    {
        // return string
        return(Util.parseRevisionNumber(revisionNumber));
    }
}
