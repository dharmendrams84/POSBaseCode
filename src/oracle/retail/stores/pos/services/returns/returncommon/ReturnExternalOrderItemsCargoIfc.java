/*===========================================================================
* Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved. 
* ===========================================================================
* $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returns/returncommon/ReturnExternalOrderItemsCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:58 mszekely Exp $
* ===========================================================================
* NOTES
* <other useful comments, qualifications, etc.>
*
* MODIFIED    (MM/DD/YY)
*    jswan     06/17/10 - Checkin external order integration files for refresh.
*    jswan     06/01/10 - Checked in for refresh to latest lable.
*    jswan     05/27/10 - First pass changes to return item for external order
*                         project.
*    jswan     05/14/10 - ExternalOrder mods checkin for refresh to tip.
* ===========================================================================
*/
package oracle.retail.stores.pos.services.returns.returncommon;

import java.util.ArrayList;
import java.util.List;

import oracle.retail.stores.commerceservices.externalorder.ExternalOrderItemIfc;

/**
 * This interface defines the methods that are required by a cargo for
 * external order item returns.
 */
public interface ReturnExternalOrderItemsCargoIfc
{
    /**
     * @return Returns the externalOrderItems.
     */
    public ArrayList<ExternalOrderItemReturnStatusElement> getExternalOrderItemReturnStatusElements();

    /**
     * This method should only be called by the launch shuttle that
     * starts up the returns process.
     * @param externalOrderItems The externalOrderItems to set.
     */
    public void setExternalOrderItemReturnStatusElements(ArrayList<ExternalOrderItemReturnStatusElement> externalOrderItems);

    /**
     * This method should only be called by the launch shuttle that
     * starts up the returns process.
     * @param externalOrderItems The externalOrderItems to set.
     */
    public void setExternalOrderItems(List<ExternalOrderItemIfc> externalOrderItems);

    /**
     * @return Returns the externalOrder.
     */
    public boolean isExternalOrder();

    /**
     * @param externalOrder The externalOrder to set.
     */
    public void setExternalOrder(boolean externalOrder);

    /**
     * @return Returns the externalOrderItems.
     */
    public ExternalOrderItemReturnStatusElement getExternalOrderItemeReturnStatusElement(int index);

    /**
     * @return Returns the currentExternalOrderItemReturnStatusElement.
     */
    public ExternalOrderItemReturnStatusElement getCurrentExternalOrderItemReturnStatusElement();

    /**
     * @param currentExternalOrderItemReturnStatusElement The currentExternalOrderItemReturnStatusElement to set.
     */
    public void setCurrentExternalOrderItemReturnStatusElement(
            ExternalOrderItemReturnStatusElement currentExternalOrderItemReturnStatusElement);
    
    /**
     * This method finds the external order item status element associated
     * with the external order item parameter and updates the returned status.
     * @param externalOrderItem
     * @param returned
     */
    public void setAssociatedExternalOrderItemReturnedStatus(ExternalOrderItemIfc externalOrderItem, 
            boolean returned);

    /**
     * This method resets all elements in the list of ExternalOrderItemReturnStatusElement which
     * have not been returned to not select for return, so that they can be processes again.
     */
    public void resetExternalOrderItemsSelectForReturn();
}
