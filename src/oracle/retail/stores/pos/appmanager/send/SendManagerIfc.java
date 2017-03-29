/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/appmanager/send/SendManagerIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:34 mszekely Exp $
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
 *    4    360Commerce 1.3         4/25/2007 8:52:47 AM   Anda D. Cadar   I18N
 *         merge
 *         
 *    3    360Commerce 1.2         3/31/2005 4:29:55 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:10 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:09 PM  Robert Pearse   
 *
 *   Revision 1.8  2004/08/09 14:34:47  rsachdeva
 *   @scr 6791 Transaction Level Send
 *
 *   Revision 1.7  2004/06/02 19:06:51  lzhao
 *   @scr 4670: add ability to delete send items, modify shipping and display shipping method.
 *
 *   Revision 1.6  2004/05/26 16:37:47  lzhao
 *   @scr 4670: add capture customer and bill addr. same as shipping for send
 *
 *   Revision 1.5  2004/05/06 19:43:35  rsachdeva
 *   @scr 4670 Send: Multiple Sends At One Time Selected
 *
 *   Revision 1.4  2004/02/13 21:30:18  epd
 *   @scr 0
 *   Send refactoring
 *
 *   Revision 1.3  2004/02/13 21:10:51  epd
 *   @scr 0
 *   Refactoring to the Send Application Manager
 *
 *   Revision 1.2  2004/02/13 14:05:00  baa
 *   @scr 0 remove unused imports
 *
 *   Revision 1.1  2004/02/12 21:54:44  epd
 *   @scr 0
 *   Updates for Send Tour
 *
 *   Revision 1.1  2004/02/12 21:36:29  epd
 *   @scr 0
 *   These files comprise all new/modified files that make up the refactored send service
 *
 *   
 * ===========================================================================
 */
package oracle.retail.stores.pos.appmanager.send;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.pos.services.sale.SaleCargo;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.pos.services.modifyitem.ItemCargo;

/**
 * @author epd
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface SendManagerIfc
{
    /** Name of this manager. Used as key to ManagerFactory */
    public static final String MANAGER_NAME = "SendManager";
    
    
    /**
     * Checks for Maximum Sends
     * @param maxSends maximum sends
     * @param cargo The Item Cargo
     * @exception SendException send exception thrown when exceeding maximum sends
     */
    public void checkForMaximumSends(int maxSends, ItemCargo cargo) throws SendException;
    
    /**
     * This method contains the business logic used to determine whether all items
     * in a supplied list are elligible for Send.
     * @param cargo The Item Cargo
     * @throws SendException thrown when at least 1 item is not sendable.
     */
    public void validateItemsForSend(ItemCargo cargo) throws SendException;
    
    /**
     * Check if any sends have been previously applied to the items selected
     * @param cargo The Item Cargo
     * @exception SendException send exception
     */
    public void checkItemAlreadyInSend(ItemCargo cargo) throws SendException;
    
    /**
     * Checks for Items from Multiple Sends
     * @param cargo The Item Cargo
     * @exception SendException send exception thrown items from multiple sends
     */
    public void checkItemsFromMultipleSends(ItemCargo cargo) throws SendException;
    
    /**
     * Checks for Items from Multiple Sends
     * @param cargo The Sale Cargo
     * @param allSelected All the selected item index for delete
     * @exception SendException send exception thrown items from multiple sends
     */
    public void checkItemsFromMultipleSends(SaleCargo cargo, int[] allSelected) throws SendException;
    
    /**
     * Marks an array of items as Send items and updates the taxes accordingly
     * @param cargo The Item Cargo
     */
    public void processItemsForSend(ItemCargo cargo);
    
    /**
     * This method determines whether a customer is linked to a transaction
     * @param cargo The Item Cargo
     * @return boolean indicating customer link status.
     */
    public boolean isCustomerLinked(ItemCargo cargo);
    
    /**
     * Performs function of linking a customer to a transaction
     * @param cargo The Item Cargo
     * @param customer The customer to be linked
     */
    public void linkCustomer(ItemCargo cargo);
  
    /**
     *   Returns CurrencyIfc, the total shipping charge calculated by Weight
     *   @param sendItems  the list of items to be send
     *   @return CurrencyIfc calculation
     */
    public CurrencyIfc calculateShippingByWeight(AbstractTransactionLineItemIfc[] sendItems);
    
    /**
     *   Returns CurrencyIfc, the total shipping charge calculated by Dollar Amount
     *   @param sendItems  the list of items to be send
     *   @return CurrencyIfc calculation
     */
    public CurrencyIfc calculateShippingByDollarAmount(AbstractTransactionLineItemIfc[] sendItems);
    
    /**
     *   Returns an String, the shipping calculation method from
     *   the parameter file.
     *   @param bus
     *   @return String calculation type
     */
    public String getShippingCalculationType(ParameterManagerIfc pm);
    
    /**
     *   Gets shipping change 
     *   @param String  shipping calculation
     *   @param AbstractTransactionLineItemIfc[] lineItems 
     *   @return CurrencyIfc  shipping charge
     */
    public CurrencyIfc getShippingCharge(String shippingCalculation,
                                         AbstractTransactionLineItemIfc[] lineItems);
    //----------------------------------------------------------------------
    /**
     *   Checks for Valid Send Item
     *   @param item Sale return line item reference
     *   @return boolean true if valid send item
     */
    //----------------------------------------------------------------------
    public boolean checkValidSendItem(SaleReturnLineItemIfc item);
    
   }
