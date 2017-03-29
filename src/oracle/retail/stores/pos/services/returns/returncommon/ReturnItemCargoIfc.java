/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returns/returncommon/ReturnItemCargoIfc.java /main/13 2011/02/16 09:13:34 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    jswan     06/17/10 - Checkin external order integration files for
 *                         refresh.
 *    jswan     05/27/10 - First pass changes to return item for external order
 *                         project.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    6    360Commerce 1.5         5/27/2008 7:37:28 PM   Anil Rathore
 *         Updated to display ITEM_NOT_FOUND dialog. Changes reviewed by Dan.
 *    5    360Commerce 1.4         4/25/2007 8:52:15 AM   Anda D. Cadar   I18N
 *         merge
 *         
 *    4    360Commerce 1.3         1/22/2006 11:45:17 AM  Ron W. Haight
 *         removed references to com.ibm.math.BigDecimal
 *    3    360Commerce 1.2         3/31/2005 4:29:44 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:51 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:53 PM  Robert Pearse   
 *
 *   Revision 1.12  2004/03/25 15:07:15  baa
 *   @scr 3561 returns bug fixes
 *
 *   Revision 1.11  2004/03/18 23:01:56  baa
 *   @scr 3561 returns fixes for gift card
 *
 *   Revision 1.10  2004/03/16 20:16:36  epd
 *   @scr 3561 fixed bug that sets gift receipt selected for retrieved return items
 *
 *   Revision 1.9  2004/03/10 14:16:46  baa
 *   @scr 0 fix javadoc warnings
 *
 *   Revision 1.8  2004/03/08 22:54:54  epd
 *   @scr 3561 Updates for entering detailed return item info
 *
 *   Revision 1.7  2004/03/05 23:27:58  baa
 *   @scr 3561 Retrieve size from scanned items
 *
 *   Revision 1.6  2004/03/04 15:22:55  aarvesen
 *   @scr 0 moved a line of code for aesthetic reasons
 *
 *   Revision 1.5  2004/03/01 19:35:28  epd
 *   @scr 3561 Updates for Returns.  Items now have tax rates applied based on entered store #
 *
 *   Revision 1.4  2004/02/24 22:08:14  baa
 *   @scr 3561 continue returns dev
 *
 *   Revision 1.3  2004/02/12 16:51:46  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:30  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:20  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.2   Feb 09 2004 10:36:48   baa
 * return - item not found
 * 
 *    Rev 1.1   05 Feb 2004 23:16:30   baa
 * returs - multi items
 * 
 *    Rev 1.0   Aug 29 2003 16:05:50   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Feb 16 2003 10:43:30   mpm
 * Merged 5.1 changes.
 * Resolution for POS SCR-2053: Merge 5.1 changes into 6.0
 *
 *    Rev 1.0   Apr 29 2002 15:06:50   msg
 * Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returncommon;

import java.io.Serializable;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.pos.services.common.AbstractFinancialCargoIfc;
import oracle.retail.stores.pos.services.common.PLUCargoIfc;
import java.math.BigDecimal;

import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.lineitem.ItemTaxIfc;
import oracle.retail.stores.domain.lineitem.ReturnItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.SearchCriteriaIfc;
import oracle.retail.stores.domain.utility.CodeListIfc;

import oracle.retail.stores.pos.ui.beans.ReturnItemInfoBeanModel;

/**
 * This interface defines Provides a common interface from the manual item and
 * transaction return return cargos into the sites that manage the return of
 * individual items.
 * 
 * @version $Revision: /main/13 $
 */
public interface ReturnItemCargoIfc
    extends PLUCargoIfc,
        ReturnExternalOrderItemsCargoIfc,
        AbstractFinancialCargoIfc,
        Serializable
{
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
     * Method to retreive the localizedReasonCodes
     * 
     * @return the localizedReasonCodes
     */
    public CodeListIfc getLocalizedReasonCodes();

    /**
     * Method sets the localizedReasonCodes
     * 
     * @param localizedReasonCode the localizedReasonCode to set
     */
    public void setLocalizedReasonCodes(CodeListIfc localizedReasonCodes);

    /**
     * Sets the salesAssociate.
     * 
     * @param value the employee.
     */
    public void setSalesAssociate(EmployeeIfc value);

    /**
     * Gets the salesAssociate.
     * 
     * @return the salesAssociate.
     */
    public EmployeeIfc getSalesAssociate();

    /**
     * Sets the return item.
     * 
     * @param value the return item.
     */
    public void setReturnItem(ReturnItemIfc value);

    /**
     * Gets the return item.
     * 
     * @return the return item.
     */
    public ReturnItemIfc getReturnItem();

    /**
     * Gets the PLUItem from the return array.
     * 
     * @return PLUItem.
     */
    public PLUItemIfc getReturnPLUItem();

    /**
     * Returns the selected item.
     * 
     * @return the item
     */
    public SaleReturnLineItemIfc getSaleLineItem();

    /**
     * Sets the Sales Associate ID.
     * 
     * @param value the Sales Associate ID.
     */
    public void setSalesAssociateID(String value);

    /**
     * Gets the Sales Associate ID.
     * 
     * @return the Sales Associate ID.
     */
    public String getSalesAssociateID();

    /**
     * Returns the original transaction.
     * 
     * @return SaleReturnTransactionIfc
     */
    public SaleReturnTransactionIfc getOriginalTransaction();

    /**
     * Returns the quantity.
     * 
     * @return SaleReturnTransactionIfc
     */
    public BigDecimal getItemQuantity();

    /**
     * Gets the sales associate for the item selected.
     * 
     * @return EmployeeIfc
     */
    public EmployeeIfc getSaleLineItemSalesAssociate();

    /**
     * Returns the price.
     * 
     * @return CurrencyIfc
     */
    public CurrencyIfc getPrice();

    /**
     * Sets the price.
     * 
     * @param price
     */
    public void setPrice(CurrencyIfc price);

    /**
     * Gets the ItemTax object for the return item.
     * 
     * @return ItemTax.
     */
    public ItemTaxIfc getItemTax(); // external tax mgr

    /**
     * Sets the return item info for a kit header.
     * 
     * @param value
     */
    public void setReturnItemInfo(ReturnItemInfoBeanModel value);

    /**
     * Returns return item info for a kit header.
     * 
     * @return ReturnItemInfoBeanModel
     */
    public ReturnItemInfoBeanModel getReturnItemInfo();

    /**
     * Sets the validation failed flag.
     * 
     * @param value the validation failed flag.
     */
    public void setValidationFailed(boolean value);

    /**
     * Gets the validation failed flag.
     * 
     * @return the validation failed flag.
     */
    public boolean getValidationFailed();

    /**
     * Sets the itemScanned flag.
     * 
     * @param value boolean
     */
    public void setItemScanned(boolean value);

    /**
     * Returns the itemScanned flag.
     * 
     * @return boolean
     */
    public boolean isItemScanned();

    /**
     * Only transiently used in EnterReturnItemInformationSite when refilling
     * the bean model. This is persisted into the ReturnItem class later on.
     * 
     * @return Returns the itemSize.
     */
    public String getItemSizeCode();

    /**
     * @param code The itemSize to set.
     */
    public void setItemSizeCode(String code);

    /**
     * The tax rates dialog should only be displayed once per receipt if it was
     * not possible to retrieve the tax rates
     * 
     * @param value flag indicating that we displayed the dialog
     */
    public void setDisplayedTaxRatesUnavailableDialog(boolean value);

    /**
     * The tax rates dialog should only be displayed once per receipt if it was
     * not possible to retrieve the tax rates
     * 
     * @return flag indicating that we displayed the dialog
     */
    public boolean isDisplayedTaxRatesUnavailableDialog();

    /**
     * Returns the search criteria to retrieve transactions
     * 
     * @return SearchCriteriaIfc the search criteria
     */
    public SearchCriteriaIfc getSearchCriteria();

    /**
     * Sets the search criteria to retrieve transactions
     * 
     * @param criteria the search criteria
     */
    public void setSearchCriteria(SearchCriteriaIfc criteria);

    /**
     * sets the transfer cargo flag.
     * 
     * @param value the transfer cargo flag
     */
    public void setGiftReceiptSelected(boolean value);

    /**
     * Gets the transfer cargo flag.
     * 
     * @return boolean the transfer cargo flag
     */
    public boolean isGiftReceiptSelected();

    /**
     * Sets the current item index.
     * 
     * @param value the index
     */
    public void setCurrentItem(int value);

    /**
     * Returns the current item index.
     * 
     * @return int the index
     */
    public int getCurrentItem();

    /**
     * Returns the array of sale items to be returned.
     * 
     * @return SaleReturnLineItemIfc[]
     */
    public SaleReturnLineItemIfc[] getReturnSaleLineItems();

    /**
     * Sets the item selected.
     * 
     * @param value the item
     */
    public void setSaleLineItem(SaleReturnLineItemIfc value);

    /**
     * @return Returns the lastLineItemReturnedIndex.
     */
    public int getLastLineItemReturnedIndex();

    /**
     * @param lastLineItemReturnedIndex The lastLineItemReturnedIndex to set.
     */
    public void setLastLineItemReturnedIndex(int lastLineItemReturnedIndex);

    /**
     * Sets flag to indicate if return items comes from a receipt
     * 
     * @param value boolean flag
     */
    public void setHaveReceipt(boolean value);

    /**
     * Indicates if return items comes from gift receipt
     * 
     * @return boolean flag
     */
    public boolean haveReceipt();

}
