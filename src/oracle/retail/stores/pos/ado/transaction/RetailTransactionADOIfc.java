/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/transaction/RetailTransactionADOIfc.java /rgbustores_13.4x_generic_branch/5 2011/08/10 16:47:23 mkutiana Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    mkutiana  08/10/11 - Undid incorrect changes for bug 11838309
 *    blarsen   07/28/11 - removed exceedsAuthorizationThreshold(). No longer
 *                         supported as of 13.4 Advanced Payment Foundation
 *                         feature.
 *    sgu       07/11/11 - only check for overtender if not in transaction
 *                         reentry mode
 *    jkoppolu  04/01/11 - Added method isStoreCreditUsed which is used to
 *                         preveent the reuse of store credit in a transaction.
 *    mchellap  03/24/11 - XbranchMerge mchellap_bug-11838309 from main
 *    mchellap  03/24/11 - BUG#11838309 Over tendering allowed for check
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abhayg    05/07/10 - Fixed Transaction status issue
 *    abondala  01/03/10 - update header date
 *    jswan     11/18/09 - Revised method header comments.
 *    jswan     11/18/09 - Forward to fix use of gift cerificate more than once
 *                         in a transaction and making change to gift
 *                         certificate which already been redeemed.
 *    jswan     11/17/09 - XbranchMerge shagoyal_bug-8553074 from
 *                         rgbustores_13.0x_branch
 *    aphulamb  04/14/09 - Fixed issue if Special Order is done by Purchase
 *                         Order
 *    arathore  02/14/09 - Updated to pass Personal Id information to printing
 *                         tour.
 *
 * ===========================================================================
 * $Log:
 *    6    360Commerce 1.5         3/31/2008 1:53:19 PM   Mathews Kochummen
 *         forward port from v12x to trunk
 *    5    360Commerce 1.4         4/25/2007 8:52:48 AM   Anda D. Cadar   I18N
 *         merge
 *
 *    4    360Commerce 1.3         12/13/2005 4:42:34 PM  Barry A. Pape
 *         Base-lining of 7.1_LA
 *    3    360Commerce 1.2         3/31/2005 4:29:42 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:24:47 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:13:47 PM  Robert Pearse
 *
 *   Revision 1.21  2004/08/12 20:46:36  bwf
 *   @scr 6567, 6069 No longer have to swipe debit or credit for return if original
 *                               transaction tendered with one debit or credit.
 *
 *   Revision 1.20  2004/07/21 22:55:33  bwf
 *   @scr 5963 (ServicesImpact) Moved getChangeOptions and calculateMaxCashChange out of
 *                     abstractRetailTransaction and into TenderUtility.  Also made calculateMaxCashChange
 *                     more polymorphic.
 *
 *   Revision 1.19  2004/07/14 22:50:10  cdb
 *   @scr  5421 Unused Import removed.
 *
 *   Revision 1.18  2004/07/14 21:21:55  jriggins
 *   @scr 4401 Added logic to determine when to capture customer info during tender
 *
 *   Revision 1.17  2004/07/13 22:41:12  epd
 *   @scr 5955 (ServicesImpact) Addressed complaints about logger, exceptions, etc
 *
 *   Revision 1.16  2004/07/06 20:15:05  crain
 *   @scr 6004 System crashes when redeeming a gift certificate for Mail Bank Check
 *
 *   Revision 1.15  2004/06/23 00:42:06  blj
 *   @scr 5113 added capture customer capability for store credit redeem.
 *
 *   Revision 1.14  2004/06/19 17:33:33  bwf
 *   @scr 5205 These are the overhaul changes to the Change Due Options
 *                     screen and max change calculations.
 *
 *   Revision 1.13  2004/05/15 21:31:54  crain
 *   @scr 4181 Wrong dialog screen
 *
 *   Revision 1.12  2004/04/27 15:50:29  epd
 *   @scr 4513 Fixing tender change options functionality
 *
 *   Revision 1.11  2004/03/23 17:41:28  bwf
 *   @scr 3956 Code Review
 *
 *   Revision 1.10  2004/03/22 17:26:43  blj
 *   @scr 3872 - added redeem security, receipt printing and saving redeem transactions.
 *
 *   Revision 1.9  2004/03/16 18:30:43  cdb
 *   @scr 0 Removed tabs from all java source code.
 *
 *   Revision 1.8  2004/03/09 20:00:21  bwf
 *   @scr 3956 General Tenders work.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.transaction;

import java.util.HashMap;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.common.utility.LocalizedCodeIfc;
import oracle.retail.stores.domain.customer.CaptureCustomerIfc;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.customer.IRSCustomerIfc;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.tender.TenderStoreCreditIfc;
import oracle.retail.stores.domain.utility.StoreCreditIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.pos.ado.ADOIfc;
import oracle.retail.stores.pos.ado.lineitem.TenderLineItemCategoryEnum;
import oracle.retail.stores.pos.ado.security.OverridableIfc;
import oracle.retail.stores.pos.ado.store.RegisterADO;
import oracle.retail.stores.pos.ado.tender.TenderADOIfc;
import oracle.retail.stores.pos.ado.tender.TenderException;
import oracle.retail.stores.pos.ado.tender.TenderTypeEnum;

/**
 * Public contract for all Transactions
 */
public interface RetailTransactionADOIfc extends Cloneable, ADOIfc
{
    /** PAT Cash Threshold. We report on PAT Cash exceeding this amount */
    public static final String PAT_CASH_THRESHOLD = "10000.00";

    /**
     * Returns the transaction ID for this transaction
     * @return The transaction ID.
     */
    public String getTransactionID();

    /**
     * Save this transaction to persistent storage
     * @param registerADO Information from the register is required by legacy
     *                       persistence machanism.
     */
    public void save(RegisterADO registerADO) throws DataException;

    //----------------------------------------------------------------------
    /**
        This method returns true if PAT Customer has not been collected and
        PAT Cash Tender criteria are met
        @return true if PAT Customer has not been collected and
        PAT Cash Tender criteria are met
    **/
    //----------------------------------------------------------------------
    public boolean capturePATCustomer();

    //---------------------------------------------------------------------
    /**
     * This method preserves the IRS Customer to include in the RDO
     * if required.
     *
     * @param irsCustomer the capture customer info
     */
    //---------------------------------------------------------------------
    public void setIRSCustomer(IRSCustomerIfc irsCustomer);

    //---------------------------------------------------------------------
    /**
     * This method retrieves the IRS Customer.
     *
     * @return IRSCustomerIfc
     */
    //---------------------------------------------------------------------
    public IRSCustomerIfc getIRSCustomer();

    /**
     * Attempt to add a tender.  Invokes validation.
     * @param tender The tender to be added
     * @throws TenderException Thrown when a validation error occurs.
     * @see oracle.retail.stores.pos.ado.transaction.TransactionADOIfc#addTender()
     */
    public void addTender(TenderADOIfc tender) throws TenderException;

    /**
     * Attempt to add a tender. Invokes validation.
     *
     * @param tender
     *            The tender to be added
     */
    public void addValidTender(TenderADOIfc tender);

    /**
     * Attempt to remove a tender.  The tender passed in does not
     * have to be the same object as the tender being deleted.  The idea
     * is to find an identical tender based on type, amount, and any other
     * relavant information.
     * @param tender The tender to be added
     * @see oracle.retail.stores.pos.ado.transaction.TransactionADOIfc#addTender()
     */
    public void removeTender(TenderADOIfc tender);

    /**
     * Used to retrieve organized groups of tenders from the transaction.
     * @param category The category of line items to retrieve
     * @return an array of tenders matching the category.
     */
    public TenderADOIfc[] getTenderLineItems(TenderLineItemCategoryEnum category);

    /**
     * This method calculates the total cash change amount.  Cash change
     * consists of any balance due plus depleted gift card balance amounts
     * @return
     */
    public CurrencyIfc getTotalCashChangeAmount();

    //----------------------------------------------------------------------
    /**
        This method removes all tenders that are either not authorized
        or are unauthorizable.
    **/
    //----------------------------------------------------------------------
    public void deleteNonAuthorizedTenders();

    /**
     * The amount is the first piece of information we have.  We want
     * to validate it first so the user doesn't have to enter additional
     * information if we can figure out that the tender amount is invalid.
     * @param tenderAttributes
     */
    public void validateTenderLimits(HashMap tenderAttributes) throws TenderException;

    /**
     * The amount is the first piece of information we have.  We want
     * to validate it first so the user doesn't have to enter additional
     * information if we can fingure out that the tender amount is invalid.
     * @param tenderAttributes
     * @param hasReceipt Indicates whether the customer has the receipt for
     *                   the transaction that prompted the issuance of change.
     * @parm retrieved Indicates whether the original transaction is succesfully
     *  				retrieved.
     */
    public void validateRefundLimits(HashMap tenderAttributes, boolean hasReceipt, boolean retrieved) throws TenderException;

    //----------------------------------------------------------------------
    /**
     * This method validates the change limits.
     *  @param tenderAttributes
     *  @parm hasReceipt Indicates whether the customer has the receipt for
     *  				the transaction that prompted the issuance of change.
     *  @parm retrieved Indicates whether the original transaction is succesfully
     *  				retrieved.
     *  @throws TenderException
    **/
    //----------------------------------------------------------------------
    public void validateChangeLimits(HashMap tenderAttributes, boolean hasReceipt, boolean retrieved) throws TenderException;

    /**
     * Makes the determination that this transaction is voidable
     * based on multiple criteria including but not limited to
     * things such as: type, contents, modifications made, etc.
     * @param currentTillID the ID of the current Till.
     * @return flag containing voidable status
     * @throws VoidException Thrown when a problem is found with the transaction
     */
    public boolean isVoidable(String currentTillID) throws VoidException;

    /**
     * This method is designed for use by methods that must be
     * updated when they are voided.
     */
    public void updateForVoid() throws DataException;

    /**
     * Gets the String representation of a transaction type
     * @return The transaction type string.
     */
    public TransactionPrototypeEnum getTransactionType();

    /**
     * The purpose of this method is to evaluate the balance of the
     * transaction in connection to the transaction type to determine
     * whether to issue change, take in tenders, issue refund tenders,
     * or to see if the transaction is paid up.
     * @return one of the above values
     */
    public TenderStateEnum evaluateTenderState();

    /**
     * Given the internal state of this transaction
     * return an array of tenders which are valid for
     * accepting as a tender.
     * @return
     */
    public TenderTypeEnum[] getEnabledTenderOptions();

    /**
     * Given the internal state of this transaction
     * return an array of tenders which are valid for
     * accepting as a refund tenders.
     * @return
     */
    public TenderTypeEnum[] getEnabledRefundOptions();

    //----------------------------------------------------------------------
    /**
        Given the internal state of this transaction
        return an array of tenders which are valid for
        change due tenders.
        @return array of tendertypeenum that are the valid tenders for
                change due.
    **/
    //----------------------------------------------------------------------
    public TenderTypeEnum[] getEnabledChangeOptions();

    //----------------------------------------------------------------------
    /**
        This method returns a boolean that says whether cash is
        the only option for change due.
        @return boolean cashOnlyOptionForchange due
    **/
    //----------------------------------------------------------------------
    public boolean isCashOnlyOptionForChangeDue();

    //----------------------------------------------------------------------
    /**
        This method returns a boolean true if the Gift Certificate had already
        been added to the TenderLineItemIfc array.  This indicates that operatator
        is trying to use the same gift certificate twice in a single transaction.
        @param tenderAttributes gift certificate attributes
        @return boolean giftCertificateUsed
    **/
    //----------------------------------------------------------------------
    public boolean isGiftCertificateUsed(HashMap tenderAttributes);

    //----------------------------------------------------------------------
    /**
        This method sets the cash only option.
        @return
    **/
    //----------------------------------------------------------------------
    public void setCashOnlyOptionForChangeDue(boolean cashOnlyOption);

    /**
     * Returns the total amount of the specified tender
     * for this transaction
     * @param type The desired tender type
     * @return The total for that type.
     */
    public CurrencyIfc getTenderTotal(TenderTypeEnum type);

    /**
     * Gets the balance due for this transaction
     * @return CurrencyIfc
     */
    public CurrencyIfc getBalanceDue();

    /**
     * There are some functions (such as tender limits) that
     * when overridden change application behavior.  This is different
     * from the normal usage of functions, which is to control access to
     * application functionality.  This method is for the special cases
     * where behavior modification is needed.  It simply passes control
     * to the {@link OverridableIfc} object.
     * @param overrideEmployee
     * @param function Function to override
     * @param data This field is used as a miscellaneous field to help identify which
     *        overridable object to override.
     * @see oracle.retail.stores.domain.employee.RoleFunctionIfc
     * @return boolean indication override successful or not.
     */
    public boolean overrideFunction(EmployeeIfc overrideEmployee, int function, Object data);

    /**
     * Several factors need to be evaluated to determine whether the cash drawer
     * should be opened for a particular transaction.  This method takes those factors
     * into account and returns a boolean value.  If true, the drawer should be opened.
     * @return
     */
    public boolean openDrawer();

    //---------------------------------------------------------------------
    /**
        This method calls the RDO linkCustomer.
        @param customer CustomerIfc
     **/
    //---------------------------------------------------------------------
    public void linkCustomer(CustomerIfc customer);

    //---------------------------------------------------------------------
    /**
        This method determines whether or not a Business customer is linked
        to the transaction.
        @return linked Boolean
     **/
    //---------------------------------------------------------------------
    public boolean isBusinessCustomerLinked() throws TenderException;

    /*
     * This method returns a boolean flag indicating whether or not
     * this transaction contains Send items.
     */
    public boolean containsSendItems();

    /*
     * This method returns a boolean flag indicating whether or not
     * this transaction contains return items.
     */
    public boolean containsReturnItems();

    //----------------------------------------------------------------------
    /**
        This method returns a boolean flag indicating whether or not
        the customer is present.
        @return
    **/
    //----------------------------------------------------------------------
    public boolean isCustomerPresent();

    //----------------------------------------------------------------------
    /**
        This method saves declined echecks.
        @param check
    **/
    //----------------------------------------------------------------------
    public void saveDeclineECheck(TenderADOIfc check);

    //---------------------------------------------------------------------
    /**
        Set the transaction as exempted from tax.
    **/
    //---------------------------------------------------------------------
     public void setTaxExempt(String certificateNumber, int reasonCode);

    // ---------------------------------------------------------------------
    /**
     * Recalculate the OrderTransaction Object.
     *
     */
    public void updateOrderStatus();

    // ---------------------------------------------------------------------
    /**
     * Returns the deposit amount.
     *
     * @return Currency deposit amount.
     */
    public CurrencyIfc getDepositAmount();

     // ---------------------------------------------------------------------
    /**
        Clear tax exempt.
    **/
    //---------------------------------------------------------------------
    public void clearTaxExempt();

    //---------------------------------------------------------------------
    /**
         Retrieve tenderStoreCreditIfc line item from transaction
     **/
    //---------------------------------------------------------------------
    public TenderStoreCreditIfc getTenderStoreCreditIfcLineItem();

    //----------------------------------------------------------------------
    /**
        This method returns true if already added StoreCredit
        in the TenderLineItemIfc is being used again.
        @return boolean storeCreditUsed
    **/
    //----------------------------------------------------------------------
    public boolean isStoreCreditUsed(String storeCreditNumber, String storeCreditAmount);


    //---------------------------------------------------------------------
    /**
         Determine amount to be used to issue store credit
     **/
    //---------------------------------------------------------------------
    public CurrencyIfc issueStoreCreditAmount(String amount);

    //---------------------------------------------------------------------
    /**
         Reissue store credit from unused portion of original store credit
         or issue new store credit
     **/
    //---------------------------------------------------------------------
    public TenderStoreCreditIfc unusedStoreCreditReissued(StoreCreditIfc storeCredit,
            CurrencyIfc tenderAmount);

    //---------------------------------------------------------------------
    /**
     Set the transaction to nontaxable.
     **/
    //---------------------------------------------------------------------
    public void setNonTaxable();

    /**
     * Attempt to add a redeem tender.
     * @param tender The redeem tender to be added
     * @throws TenderException Thrown when a validation error occurs.
     */
    public void addRedeemTender(TenderADOIfc tender) throws TenderException;

    /**
     * This method updates the customer information for the
     * redeemed store credit.  The customer information isnt
     * captured until the tender service.  The redeemed store
     * credit has already been created and added by this time
     * so we must go back and update the customer information.
     *
     * @param cust
     * @param entryMethod
     * @param idType
     */
    public void updateCustomerForRedeemedStoreCredit(CustomerIfc cust, String entryMethod, LocalizedCodeIfc idType);
    //---------------------------------------------------------------------
    /**
     * This method calls the RDO getCustomer.
     *
     * @return CustomerIfc
     */
    //---------------------------------------------------------------------
    public CustomerIfc getCustomer();

    //---------------------------------------------------------------------
    /**
     * This method calls the RDO setCaptureCustomer.
     *
     * @param CaptureCustomerIfc the capture customer info
     */
    //---------------------------------------------------------------------
    public void setCaptureCustomer(CaptureCustomerIfc customer);

    //---------------------------------------------------------------------
    /**
     * This method calls the RDO getCaptureCustomer.
     *
     * @return CaptureCustomerIfc
     */
    //---------------------------------------------------------------------
    public CaptureCustomerIfc getCaptureCustomer();

    //----------------------------------------------------------------------
    /**
        This method returns the refund options row;
        @return
    **/
    //----------------------------------------------------------------------
    public int getRefundOptionsRow();

    /**
    	This method is used to determine if the transaction is taxable or not
    	@return boolean
    **/

    public boolean isTaxableTransaction();

    /**
     * @return if it is in transaction reentry mode
     */
    public boolean isTransReentryMode();
}
