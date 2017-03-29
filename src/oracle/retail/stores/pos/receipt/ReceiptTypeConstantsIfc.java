/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/receipt/ReceiptTypeConstantsIfc.java /rgbustores_13.4x_generic_branch/2 2011/06/03 09:46:42 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   06/02/11 - Tweaks to support Servebase chipnpin
 *    nkgautam  06/21/10 - added constants for bill pay receipt
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    vikini    03/19/09 - Incorporating Code review comments
 *    vikini    03/15/09 - added ReturnsDeniedReceipt
 *    cgreene   01/13/09 - multiple send and gift receipt changes. deleted
 *                         SendGiftReceipt
 *    cgreene   12/16/08 - add RebateReceipt
 *    atirkey   12/02/08 - Layaway delete receipt
 *    aphulamb  11/27/08 - checking files after merging code for receipt
 *                         printing by Amrish
 *    cgreene   11/25/08 - added additional receipt types for flexibility in
 *                         receipt copy counts
 *    atirkey   11/21/08 - layway delete
 *    cgreene   11/17/08 - added StoreCreditReceipt
 *    cgreene   11/13/08 - configure print beans into Spring context
 *
 * ===========================================================================
 * $Log:
 *  2    360Commerce 1.1         5/15/2007 4:03:09 PM   Alan N. Sinton  CR
 *       26481 - Phase one for VAT modifications to ORPOS <ARG> Summary
 *       Reports.
 *  1    360Commerce 1.0         4/30/2007 7:00:39 PM   Alan N. Sinton  CR
 *       26485 - Merge from v12.0_temp.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.receipt;

/**
 * Collection of constants for receipt types.
 * $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public interface ReceiptTypeConstantsIfc
{
    /** Document type for blueprint equals "AlterationReceipt" */
    public static final String ALTERATION                      = "AlterationReceipt";

    /** Document type for blueprint equals "CanceledReceipt" */
    public static final String CANCELED                        = "CanceledReceipt";

    /** Document type for blueprint equals "CreditSignatureSlipReceipt" */
    public static final String CREDIT_SIGNATURE                = "CreditSignatureSlipReceipt";

    /** Document type for blueprint equals "DebitSlipReceipt" */
    public static final String DEBIT_SLIP                      = "DebitSlipReceipt";

    /** Document type for blueprint equals "ECheckSignatureSlipReceipt" */
    public static final String ECHECK_SIGNATURE                = "ECheckSignatureSlipReceipt";

    /** Document type for blueprint equals "EmployeeDiscountReceipt" */
    public static final String EMPLOYEE_DISCOUNT               = "EmployeeDiscountReceipt";

    /** Document type for blueprint equals "ExchangeReceipt" */
    public static final String EXCHANGE                        = "ExchangeReceipt";

    /** Document type for blueprint equals "GiftCardInquirySlip" */
    public static final String GIFTCARD_INQUIRY                = "GiftCardInquirySlip";

    /** Document type for blueprint equals "GiftReceipt" */
    public static final String GIFT                            = "GiftReceipt";

    /** Document type for blueprint equals "HousePaymentReceipt" */
    public static final String HOUSE_PAYMENT                   = "HousePaymentReceipt";

    /**
     * Document type for blueprint equals "ICCDetails"
     * @since 13.4
     */
    public static final String ICC_DETAILS                     = "ICCDetails";

    /** Document type for blueprint equals "InstantCreditInquiryInfoSlip" */
    public static final String INSTANTCREDIT_INQUIRY           = "InstantCreditInquiryInfoSlip";

    /** Document type for blueprint equals "InventoryInquirySlip" */
    public static final String INVENTORY_INQUIRY               = "InventoryInquirySlip";

    /** Document type for blueprint equals "LayawayReceipt" */
    public static final String LAYAWAY                         = "LayawayReceipt";

    /** Document type for blueprint equals "LayawayDeleteReceipt" */
    public static final String LAYAWAY_DELETE                  = "LayawayDeleteReceipt";

    /** Document type for blueprint equals "LayawayPaymentReceipt" */
    public static final String LAYAWAY_PAYMENT                 = "LayawayPaymentReceipt";

    /** Document type for blueprint equals "LayawayPickupReceipt" */
    public static final String LAYAWAY_PICKUP                  = "LayawayPickupReceipt";

    /** Document type for blueprint equals "NameVerificationSlip" */
    public static final String NAME_VERIFICATION               = "NameVerificationSlip";

    /** Document type for blueprint equals "NoSaleSlip" */
    public static final String NO_SALE                         = "NoSaleSlip";

    /** Document type for blueprint equals "OrderReceipt" */
    public static final String ORDER                           = "OrderReceipt";

    /** constant for pickup delivery receipt type */
    public static final String PICKUP_DELIVERY_ORDER           = "PickupDeliveryOrderReceipt";

    /** Document type for blueprint equals "RebateReceipt" */
    public static final String REBATE                          = "RebateReceipt";

    /** Document type for blueprint equals "RedeemReceipt" */
    public static final String REDEEM                          = "RedeemReceipt";

    /** Document type for blueprint equals "ReturnReceipt" */
    public static final String RETURN                          = "ReturnReceipt";

    /** constant for Returns Denied Receipt type */
    public static final String RETURNS_DENIED                  = "ReturnDeniedReceipt";
    
    /** Document type for blueprint equals "SaleReceipt" */
    public static final String SALE                            = "SaleReceipt";

    /** Document type for blueprint equals "SpecialOrderReceipt" */
    public static final String SPECIAL_ORDER                   = "SpecialOrderReceipt";

    /** Document type for blueprint equals "SpecialOrderCancelReceipt" */
    public static final String SPECIAL_ORDER_CANCEL            = "SpecialOrderCancelReceipt";

    /** Document type for blueprint equals "SpecialOrderCompleteReceipt" */
    public static final String SPECIAL_ORDER_COMPLETE          = "SpecialOrderCompleteReceipt";

    /** Document type for blueprint equals "StoreCreditReceipt" */
    public static final String STORE_CREDIT                    = "StoreCreditReceipt";

    /** Document type for blueprint equals "SuspendReceipt" */
    public static final String SUSPEND                         = "SuspendReceipt";

    /** Document type for blueprint equals "TaxExemptReceipt" */
    public static final String TAX_EXEMPT                      = "TaxExemptReceipt";

    /** Document type for blueprint equals "TempShoppingPass" */
    public static final String TEMPSHOPPINGPASS                = "TempShoppingPass";
    
    /** Document type for blueprint equals "TillLoanReceipt" */
    public static final String TILLLOAN                       = "TillLoanReceipt";

    /** Document type for blueprint equals "TillPayInReceipt" */
    public static final String TILLPAYIN                       = "TillPayInReceipt";

    /** Document type for blueprint equals "TillPayOutReceipt" */
    public static final String TILLPAYOUT                      = "TillPayOutReceipt";

    /** Document type for blueprint equals "TillPayrollPayOutReceipt" */
    public static final String TILLPAYOUT_PAYROLL              = "TillPayrollPayOutReceipt";

    /** Document type for blueprint equals "TillPickupReceipt" */
    public static final String TILLPICKUP                      = "TillPickupReceipt";

    /** Document type for blueprint equals "VoidHousePaymentReceipt" */
    public static final String VOID_HOUSE_PAYMENT              = "VoidHousePaymentReceipt";

    /** Document type for blueprint equals "VoidLayawayReceipt" */
    public static final String VOID_LAYAWAY                    = "VoidLayawayReceipt";

    /** Document type for blueprint equals "VoidRedeemReceipt" */
    public static final String VOID_REDEEM                     = "VoidRedeemReceipt";

    /** Document type for blueprint equals "VoidSaleReceipt" */
    public static final String VOID_SALE                       = "VoidSaleReceipt";

    /** Document type for blueprint equals "VoidSpecialOrderReceipt" */
    public static final String VOID_SPECIAL_ORDER              = "VoidSpecialOrderReceipt";

    /** Document type for blueprint equals "VoidTillAdjustmentsReceipt" */
    public static final String VOID_TILL_ADJUSTMENTS           = "VoidTillAdjustmentsReceipt";
    
    /** Document type for blueprint equals "BillPayReceipt" */
    public static final String BILL_PAY                        = "BillPayReceipt";
    
    /** Document type for blueprint equals "VoidBillPayReceipt" */
    public static final String VOID_BILL_PAY                   = "VoidBillPayReceipt";
    
}
