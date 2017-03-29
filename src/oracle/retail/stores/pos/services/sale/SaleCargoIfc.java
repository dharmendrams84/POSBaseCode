/* ===========================================================================
* Copyright (c) 2008, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/SaleCargoIfc.java /rgbustores_13.4x_generic_branch/5 2012/02/27 18:16:37 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    asinto 02/27/12 - refactored the flow so that items added from scan sheet
 *                      doesn't allow for a hang or mismatched letter.
 *    asinto 02/13/12 - prompt for serial numbers when entering tender if items
 *                      are missing this data
 *    tkshar 10/10/11 - Added skipUOMCheckFlag
 *    cgreen 08/09/11 - formatting and removed deprecated code
 *    kelesi 12/03/10 - Multiple gift card reversals
 *    nkgaut 09/20/10 - refractored code to use a single class for checking
 *                      cash in drawer
 *    acadar 06/08/10 - changes for signature capture, disable txn send, and
 *                      discounts
 *    cgreen 05/26/10 - convert to oracle packaging
 *    abonda 01/03/10 - update header date
 *    rkar   11/12/08 - Adds/changes for POS-RM integration
 *    rkar   11/07/08 - Additions/changes for POS-RM integration
 *    nkgaut 09/18/08 - Added two methods(getter/setter) for cash drawer warning
 *                      boolean variable

 * ===========================================================================

     $Log:
      5    360Commerce 1.4         5/27/2008 7:37:28 PM   Anil Rathore
           Updated to display ITEM_NOT_FOUND dialog. Changes reviewed by Dan.
      4    360Commerce 1.3         1/22/2006 11:45:02 AM  Ron W. Haight
           removed references to com.ibm.math.BigDecimal
      3    360Commerce 1.2         3/31/2005 4:29:48 PM   Robert Pearse
      2    360Commerce 1.1         3/10/2005 10:24:58 AM  Robert Pearse
      1    360Commerce 1.0         2/11/2005 12:14:00 PM  Robert Pearse
     $
     Revision 1.14  2004/07/28 15:04:01  rsachdeva
     @scr 4865 Transaction Sales Associate

     Revision 1.13  2004/07/27 22:29:28  jdeleau
     @scr 6485 Make sure the undo button on the sell item screen does
     not force the operator to re-enter the users zip of phone.

     Revision 1.12  2004/07/14 15:40:19  jdeleau
     @scr 5025 Persist the item selection on the sale screen across services, such that
     when it returns to the sale screen the same items are selected, if possible.

     Revision 1.11  2004/06/07 23:06:43  bwf
     @scr 5421 Removed unused imports.

     Revision 1.10  2004/06/07 19:59:00  mkp1
     @scr 2775 Put correct header on files

     Revision 1.9  2004/06/02 19:06:51  lzhao
     @scr 4670: add ability to delete send items, modify shipping and display shipping method.

     Revision 1.8  2004/04/07 17:50:55  tfritz
     @scr 3884 - Training Mode rework

     Revision 1.7  2004/03/22 17:26:43  blj
     @scr 3872 - added redeem security, receipt printing and saving redeem transactions.

     Revision 1.6  2004/03/17 16:00:15  epd
     @scr 3561 Bug fixing and refactoring

     Revision 1.5  2004/03/11 20:03:21  blj
     @scr 3871 - added/updated shuttles to/from redeem, to/from tender, to/from completesale.
     also updated sites cargo for new redeem transaction.

     Revision 1.4  2004/02/20 15:51:43  baa
     @scr 3561  size enhancements

     Revision 1.3  2004/02/12 16:48:17  mcs
     Forcing head revision

     Revision 1.2  2004/02/11 21:22:50  rhafernik
     @scr 0 Log4J conversion and code cleanup

     Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
     updating to pvcs 360store-current


 *
 *    Rev 1.2   Nov 26 2003 09:12:30   lzhao
 * remove tendering.
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 *
 *    Rev 1.1   08 Nov 2003 01:16:40   baa
 * cleanup -sale refactoring
 *
 *    Rev 1.0   Nov 05 2003 22:55:20   cdb
 * Initial revision.
 * Resolution for 3430: Sale Service Refactoring

 * ===================================================
 */
package oracle.retail.stores.pos.services.sale;

import java.math.BigDecimal;

import oracle.retail.stores.domain.externalorder.LegalDocumentIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.manager.rm.RPIFinalResultIfc;
import oracle.retail.stores.domain.manager.rm.RPIRequestIfc;
import oracle.retail.stores.domain.manager.rm.RPIResponseIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.LetterIfc;
import oracle.retail.stores.pos.services.common.AbstractFinancialCargoIfc;
import oracle.retail.stores.pos.services.common.EmployeeCargoIfc;
import oracle.retail.stores.pos.services.common.PLUItemCargoIfc;
import oracle.retail.stores.pos.services.common.RetailTransactionCargoIfc;

/**
 * The cargo needed by the POS service.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/5 $
 */
public interface SaleCargoIfc extends AbstractFinancialCargoIfc, EmployeeCargoIfc, PLUItemCargoIfc,
        RetailTransactionCargoIfc
{
    /**
     * The operator ID prompt text tag for this service
     */
    public static final String operatorIdPromptTag = "SaleAssociateIdPrompt";

    /**
     * The operator ID prompt text for this service
     */
    public static final String operatorIdPromptText = "Enter sales associate ID.";

    /**
     * Static index value indicating no selected row
     */
    public static final int NO_SELECTION = -1;
    
    
    /**
     * Sets the transaction.
     *
     * @param transaction The transaction
     */
    public void setTransaction(SaleReturnTransactionIfc transaction);

    /**
     * Returns the transaction.
     *
     * @return The transaction
     */
    public SaleReturnTransactionIfc getTransaction();

    /**
     * Sets the transaction.
     *
     * @param transaction a RetailTransactionIfc
     */
    public void setRetailTransactionIfc(RetailTransactionIfc transaction);

    /**
     * Returns the current item index.
     *
     * @return The current item index
     */
    public int getIndex();

    /**
     * Sets the current item index.
     *
     * @param index The current item index
     */
    public void setIndex(int index);

    /**
     * Get the send indices
     * 
     * @return selected rows
     */
    public int[] getIndices();

    /**
     * Get the send indices
     * 
     * @param indices selected
     */
    public void setIndices(int[] indices);

    /**
     * Returns the itemModifiedIndex.
     *
     * @return The itemModifiedIndex
     */
    public int getItemModifiedIndex();

    /**
     * Sets the itemModifiedIndex.
     *
     * @param index The itemModifiedIndex
     */
    public void setItemModifiedIndex(int index);

    /**
     * Sets the current item index.
     *
     * @param index The current item index
     */
    public void setIndex(Integer index);

    /**
     * Returns the item number last entered.
     *
     * @return The item number last entered
     */
    public String getPLUItemID();

    /**
     * Sets the item number last entered.
     *
     * @param itemNumber The item number last entered
     */
    public void setPLUItemID(String itemNumber);

    /**
     * Returns the maximum item number length.
     *
     * @return The maximum item number length
     */
    public int getMaxPLUItemIDLength();

    /**
     * Sets the maximum item number length.
     *
     * @param itemNumber The maximum item number length
     */
    public void setMaxPLUItemIDLength(int maxPLUItemIDLength);

    /**
     * Returns the item serial number last entered.
     *
     * @return The item serial number last entered
     */
    public String getItemSerial();

    /**
     * Sets the item serial number last entered.
     *
     * @param itemSerialNumber The item serial number last entered
     */
    public void setItemSerial(String newItemSerialNumber);

    /**
     * Returns the department name for items not found.
     *
     * @return The department name
     */
    public String getDepartmentName();

    /**
     * Sets the department name for items not found.
     *
     * @param dept The department name
     */
    public void setDepartmentName(String dept);

    /**
     * Returns the current line item.
     *
     * @return The current line item
     */
    public SaleReturnLineItemIfc getLineItem();

    /**
     * Sets the current line item.
     *
     * @param item The current line item
     */
    public void setLineItem(SaleReturnLineItemIfc item);

    /**
     * From a whole line items list in the cargo, a subset of highlighted ones
     * are put into an array lineItems.
     */
    public void setLineItems(SaleReturnLineItemIfc[] items);

    /**
     * Returns the highlighted line items
     */
    public SaleReturnLineItemIfc[] getLineItems();

    /**
     * Gets the item quantity value.
     *
     * @return long value
     */
    public BigDecimal getItemQuantity();

    /**
     * Sets the Item quantity value.
     *
     * @return "1"
     */
    public void setItemQuantity(BigDecimal value);

    /**
     * Returns the error code returned with a DataException.
     *
     * @return the integer value
     */
    public int getDataExceptionErrorCode();

    /**
     * Sets the error code returned with a DataException.
     *
     * @param the integer value
     */
    public void setDataExceptionErrorCode(int value);

    /**
     * Returns the till status.
     *
     * @return the integer value
     */
    public int getTillStatus();

    /**
     * Sets the till status.
     *
     * @param the integer value
     */
    public void setTillStatus(int value);

    /**
     * Returns the waitForNext flag
     *
     * @return the waitForNext boolean flag
     */
    public boolean isWaitForNext();

    /**
     * Sets the waitForNext flag
     *
     * @param boolean value for the flag
     */
    public void setWaitForNext(boolean value);

    /**
     * Returns the letter to exit the service with.
     *
     * @return exit letter
     */
    public LetterIfc getExitLetter();

    /**
     * Sets the letter to exit the service with.
     *
     * @param letter the exit letter
     */
    public void setExitLetter(LetterIfc letter);

    /**
     * sets the array of transactions on which items have been returned. This
     * cargo does not track this data.
     * 
     * @param origTxns The retrieved original txns.
     */
    public void setOriginalReturnTransactions(SaleReturnTransactionIfc[] origTxns);

    /**
     * Retrieve the array of transactions on which items have been returned.
     * This cargo does not track this data.
     * 
     * @return SaleReturnTransactionIfc[]
     */
    public void resetOriginalReturnTransactions();

    /**
     * Add a transaction to the vector of transactions on which items have been
     * returned. This cargo does not track this data.
     * 
     * @param SaleReturnTransactionIfc
     */
    public void addOriginalReturnTransaction(SaleReturnTransactionIfc transaction);

    /**
     * Add the item created by the cashier to the transaction and journal the
     * item number, description, price, quantity, sales associate, and tax mode.
     */
    public void completeItemNotFound();

    /**
     * Check to see if the customer button should be enabled.
     *
     * @return String representation of object
     */
    public boolean isCustomerEnabled();

    /**
     * Initializes a transaction and sets it in this cargo.
     */
    public void initializeTransaction(BusIfc bus);

    /**
     * Returns the passwordRequired flag
     *
     * @return the passwordRequired flag
     */
    public boolean isPasswordRequired();

    /**
     * Sets the passwordRequired flag
     *
     * @param value the value for the flag
     */
    public void setPasswordRequired(boolean value);

    /**
     * Sets the refreshNeeded flag.
     * 
     * @param value boolean
     */
    public void setRefreshNeeded(boolean value);

    /**
     * Returns the refreshNeeded flag.
     * 
     * @return boolean
     */
    public boolean isRefreshNeeded();

    /**
     * Sets the firstSale flag.
     * 
     * @param value boolean
     */
    public void setFirstSale(boolean value);

    /**
     * Returns the firstSale flag.
     * 
     * @return boolean
     */
    public boolean isFirstSale();

    /**
     * Returns the send index.
     *
     * @return the integer value
     */
    public int getSendIndex();

    /**
     * Sets the send index.
     *
     * @param value the integer value
     */
    public void setSendIndex(int value);

    /**
     * Set whether to prevent the prompt asking for the customer to enter phone
     * or zip code information when attempting to exit the service.
     * 
     * @param value to set
     */
    public void setCanSkipCustomerPrompt(boolean value);

    /**
     * Get whether or not the customer prompt can be skiped
     * 
     * @return true or false
     */
    public boolean getCanSkipCustomerPrompt();

    /**
     * This is to keep track if sales associate set using transaction options
     * 
     * @param boolean value true if being set first time
     */
    public void setAlreadySetTransactionSalesAssociate(boolean value);

    /**
     * Already set sales associate using transaction options return true is
     * sales associate is already set
     */
    public boolean isAlreadySetTransactionSalesAssociate();

    /**
     * Setter
     * 
     * @param returnResponse (from Returns Management)
     */
    public void setReturnResponse(RPIResponseIfc returnResponse);

    /**
     * Getter
     * 
     * @return Return Response (from Returns Management)
     */
    public RPIResponseIfc getReturnResponse();

    /**
     * Getter
     * 
     * @return Return Request (for Returns Management)
     */
    public RPIRequestIfc getReturnRequest();

    /**
     * Setter
     * 
     * @param returnRequest (for Returns Management)
     */
    public void setReturnRequest(RPIRequestIfc returnRequest);

    /**
     * Getter
     * 
     * @return Return Result (for Returns Management)
     */
    public RPIFinalResultIfc getReturnResult();

    /**
     * Setter
     * 
     * @param returnResult (for Returns Management)
     */
    public void setReturnResult(RPIFinalResultIfc returnResult);

    /**
     * @return the beginIterationOverLegalDocuments
     */
    public boolean isBeginIterationOverLegalDocuments();

    /**
     * @param beginIterationOverLegalDocuments the
     *            beginIterationOverLegalDocuments to set
     */
    public void setBeginIterationOverLegalDocuments(boolean beginIterationOverLegalDocuments);

    /**
     * @return the legalDocument
     */
    public LegalDocumentIfc getLegalDocument();

    /**
     * @param legalDocument the legalDocument to set
     */
    public void setLegalDocument(LegalDocumentIfc legalDocument);

    /**
     * @return the nextLegalDocumentRecord
     */
    public int getNextLegalDocumentRecord();

    /**
     * @param nextLegalDocumentRecord the nextLegalDocumentRecord to set
     */
    public void setNextLegalDocumentRecord(int nextLegalDocumentRecord);

    /**
     * @return boolean indicating reversals required
     */
    public boolean isReverseGiftCard();

    /**
     * @param value sets boolean indicating reversals required
     */
    public void setReverseGiftCard(boolean value);

    /**
     * @param value number of items to be reversed
     */
    public void setReverseCount(int value);

    /**
     * @return number of items to be reversed
     */
    public int getReverseCount();
    
    /**
     * @return true if UOMCheck skip is true
     */
    public boolean isSkipUOMCheck();
    
    /**
     * @param skipUOMCheckFlag
     */
    public void skipUOMCheck(boolean skipUOMCheckFlag);

    /**
     * Returns the serialized item index.
     * @return the serialized item index.
     */
    public int getSerializedItemIndex();

    /**
     * Sets the serialized item index.
     * @param index
     */
    public void setSerializedItemIndex(int index);

    /**
     * Sets the selected scan sheet item.
     * @param itemID
     */
    public void setSelectedScanSheetItemID(String itemID);

    /**
     * Gets the selected scan sheet item.
     * @return the selected scan sheet item.
     */
    public String getSelectedScanSheetItemID();

}