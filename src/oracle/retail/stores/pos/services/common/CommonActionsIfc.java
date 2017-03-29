/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/CommonActionsIfc.java /rgbustores_13.4x_generic_branch/2 2011/10/07 12:02:37 rsnayak Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    rsnayak   10/05/11 - ReEntry Mode Search button fix
 *    rsnayak   04/08/11 - request ticket button fix
 *    jkoppolu  03/02/11 - Added SCANSHEET action name.
 *    rsnayak   03/08/11 - pos lat integration for label batch
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    npoola    01/13/11 - added comments to the button names
 *    npoola    01/13/11 - all the button texts are moved to this constants
 *                         file
 *
 *    Rev 1.0   Dec 20 2010 20:46:08   
 * Initial revision.
 *   
 * ===========================================================================
 */

package oracle.retail.stores.pos.services.common;

/**
 * This interface lists the constants for commonly used Action Buttons.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public class CommonActionsIfc
{
    /**
     * string for Add Button
     */
    public static final String ADD = "Add";

    /**
     * Constant string for Add Business button
     */
    public static final String ADDBUS = "AddBusiness";

    /**
     * Constant for ADMINISTRATION button action name
     */
    public static final String ADMINISTRATION = "Administration";

    /**
     * Constant for Alternate button action name
     */
    public static final String ALTERNATE = "Alternate";

    /**
     * Action Identified for Bill Pay
     */
    public static final String BILL_PAY = "BillPay";

    /**
     * string for Cancel Button
     */
    public static final String CANCEL = "Cancel";

    /**
     * Constant for Canceled button action name
     */
    public static final String CANCELED = "Canceled";

    /**
     * Constant for Cash button action name
     */
    public static final String CASH = "Cash";

    /**
     * Change Password Button
     */
    public static final String CHANGE_PASSWORD = "ChangePassword";

    /**
     * check button
     */
    public static final String CHECK = "Check";

    /**
     * String constant for Clear Button
     */
    public static final String CLEAR = "Clear";

    /**
     * Constant for CLOCK button action name
     */
    public static final String CLOCK = "Clock";

    /**
     * Constant for Common button action name
     */
    public static final String COMMON = "Common";

    /**
     * string for Continue Button
     */
    public static final String CONTINUE = "Continue";

    /**
     * Constant for Coupon button action name
     */
    public static final String COUPON = "Coupon";

    /**
     * Credit button
     */
    public static final String CREDIT = "Credit";

    /**
     * Constant for Credit Debit button action name
     */
    public static final String CREDIT_DEBIT = "CreditDebit";

    /**
     * Customer Button
     */
    public static final String CUSTOMER = "Customer";

    /**
     * Constant for DAILY_OPS button action name
     */
    public static final String DAILY_OPS = "DailyOps";

    /**
     * Constant for Damage button action name
     */
    public static final String DAMAGE = "Damage";

    /**
     * Debit button
     */
    public static final String DEBIT = "Debit";

    /**
     * String constant for Delete Button
     */
    public static final String DELETE = "Delete";

    /**
     * Constant for DELIVERY button action name
     */
    public static final String DELIVERY = "Delivery";

    /**
     * Detail Button
     */
    public static final String DETAIL = "Detail";

    /**
     * Constant for Discount button action name
     */
    public static final String DISCOUNT = "Discount";

    /**
     * Constant for the Discount Amount button action.
     */
    public static final String DISCOUNT_AMOUNT = "DiscountAmount";

    /**
     * Constant for the Discount Percent button action.
     */
    public static final String DISCOUNT_PERCENT = "DiscountPercent";

    /**
     * string for Done Button
     */
    public static final String DONE = "Done";

    /**
     * Constant for Employee button action name
     */
    public static final String EMPLOYEE = "Employee";

    /**
     * Constant for ENROLL button action name
     */
    public static final String ENROLL = "Enroll";

    /**
     * string for Failure Button
     */
    public static final String ERROR = "Error";

    /**
     * action identifier for external order
     */
    public static final String EXTERNAL_ORDER = "ExternalOrder";

    /**
     * string for Failure Button
     */
    public static final String FAILURE = "Failure";

    /**
     * Constant for Filled button action name
     */
    public static final String FILLED = "Filled";

    /**
     * Constant for FIND button action name
     */
    public static final String FIND = "Find";

    /**
     * String constant for First Button
     */
    public static final String FIRST = "First";

    /**
     * Foreign
     */
    public static final String FOREIGN = "Foreign";

    /**
     * Gift Card button
     */
    public static final String GIFT_CARD = "GiftCard";

    /**
     * Constant for Gift Card Cert button action name
     */
    public static final String GIFT_CARD_CERT = "GiftCardCert";

    /**
     * gift certificate button
     */
    public static final String GIFT_CERT = "GiftCert";

    /**
     * gift certificate label tag
     */
    public static final String GIFT_CERTIFICATE = "GiftCertificate";

    /**
     * action identifier for gift receipt
     */
    public static final String GIFT_RECEIPT = "GiftReceipt";

    /**
     * action identifier for layaway action
     */
    public static final String GIFT_REGISTRY = "GiftRegistry";

    /**
     * string for GiftCard Button
     */
    public static final String GIFTCARD = "GiftCard";

    /**
     * Constant for HELP button action name
     */
    public static final String HELP = "Help";

    /**
     * Constant for HISTORY button action name
     */
    public static final String HISTORY = "History";

    /**
     * Constant for House Account button action name
     */
    public static final String HOUSEACCOUNT = "HouseAccount";

    /**
     * Constant for INQUIRY button action name
     */
    public static final String INQUIRY = "Inquiry";

    /**
     * Constant for Instant Credit button action name
     */
    public static final String INSTANT_CREDIT = "InstantCredit";

    /**
     * string for Invalid Button
     */
    public static final String INVALID = "Invalid";

    /**
     * Constant string for Inventory Text button
     */
    public static final String INVENTORY = "Inventory";

    /**
     * action identifier for gift receipt
     */
    public static final String ITEM_BASKET = "ItemBasket";

    /**
     * Constant for Item Discount Amount button action name
     */
    public static final String ITEM_DISC_AMT = "ItemDiscAmt";

    /**
     * Constant for Item Discount Percent button action name
     */
    public static final String ITEM_DISC_PER = "ItemDiscPer";

    /**
     * String constant for Last Button
     */
    public static final String LAST = "Last";

    /**
     * action identifier for layaway action
     */
    public static final String LAYAWAY = "Layaway";

    /**
     * Link Button
     */
    public static final String LINK = "Link";

    /**
     * cash button
     */
    public static final String MAIL_CHECK = "MailCheck";

    /**
     * Constant for Damage button action name
     */
    public static final String MALL_CERT = "MallCert";

    public static final String MANAGER_OVERRIDE = "ManagerOverride";

    /**
     * Constant for Markdown button action name
     */
    public static final String MARKDOWN = "Markdown";

    /**
     * Constant for Markdown Amount button action name
     */
    public static final String MARKDOWN_AMT = "MarkdownAmt";

    /**
     * Constant for Markdown Discount Percent button action name
     */
    public static final String MARKDOWN_PER = "MarkdownPer";

    /**
     * Constant for MGC As Check button action name
     */
    public static final String MGC_AS_CHECK = "MGCAsCheck";

    /**
     * Constant for MGC As Purchase Order button action name
     */
    public static final String MGC_AS_PO = "MGCAsPO";

    /**
     * Constant for Money Order button action name
     */
    public static final String MONEY_ORDER = "MoneyOrder";

    /**
     * string for Next Button
     */
    public static final String NEXT = "Next";

    /**
     * string for No Button
     */
    public static final String NO = "No";

    /**
     * String constant for Reset Button
     */
    public static final String NO_DEFAULT = "NoDefault";

    /**
     * Constant for nosale button action name
     */
    public static final String NOSALE = "NoSale";

    /**
     * string for Ok Button
     */
    public static final String OK = "Ok";

    /**
     * Constant for the ON/OFF button action.
     */
    public static final String ON_OFF = "OnOff";

    /**
     * Constant for ONLINE_OFFICE button action name
     */
    public static final String ONLINE_OFFICE = "OnlineOffice";

    /**
     * Constant for the Override button action.
     */
    public static final String OVERRIDE = "Override";

    /**
     * Constant for the Override Tax Amount button action.
     */
    public static final String OVERRIDE_TAX_AMOUNT = "OverrideTaxAmount";

    /**
     * Constant for the Override tax Rate button action.
     */
    public static final String OVERRIDE_TAX_RATE = "OverrideTaxRate";

    /**
     * Constant for parameters operations button action name
     */
    public static final String PARAMETERS = "Parameters";

    /**
     * Constant for PAYMENT button action name
     */
    public static final String PAYMENT = "Payment";

    /**
     * Constant for PICKUP button action name
     */
    public static final String PICK_UP = "Pick up";

    /**
     * Constant for PICKUP button action name
     */
    public static final String PICKUP = "Pickup";

    /**
     * Constant for POS button action name
     */
    public static final String POS = "POS";

    /**
     * String constant for Previous Button
     */
    public static final String PREVIOUS = "Previous";

    /**
     * Constant for Price Adjustment button action name
     */
    public static final String PRICE_ADJUSTMENT = "PriceAdjustment";

    /**
     * Constant for Price Override button action name
     */
    public static final String PRICE_OVERRIDE = "PriceOverride";

    /**
     * Constant for the Print button action.
     */
    public static final String PRINT = "Print";

    /**
     * Constant for the Gift Receipt button action.
     */
    public static final String PRINT_ORIGINAL = "PrintOriginal";

    /**
     * Constant for Purchase Order button action name
     */
    public static final String PURCHASE_ORDER = "PurchaseOrder";

    /**
     * Constant for reason codes button action name
     */
    public static final String REASON_CODES = "ReasonCodes";

    /**
     * Constant string for Reconcile button
     */
    public static final String RECONCILE = "Reconcile";

    /**
     * Constant string for Reconcile Text button
     */
    public static final String RECONCILE_TEXT = "ReconcileText";

    /**
     * Redeem
     */
    public static final String REDEEM = "Redeem";

    /**
     * Constant for REFERENCE button action name
     */
    public static final String REFERENCE = "Reference";

    /**
     * Constant string for Reply Text button
     */
    public static final String REPLY = "Reply";

    /**
     * Constant for reprint receipt button action name
     */
    public static final String REPRINT_RECEIPT = "ReprintReceipt";

    /**
     * String constant for Reset Button
     */
    public static final String RESET = "Reset";

    /**
     * action identifier for retrieve action
     */
    public static final String RETRIEVE = "Retrieve";

    /**
     * Retry Button
     */
    public static final String RETRY = "Retry";

    /**
     * String constant for Return Button
     */
    public static final String RETURN = "Return";

    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    /**
     * Constant for the Roles button action name
     */
    public static final String ROLES = "Roles";

    /**
     * String constant for Sale Button
     */
    public static final String SALE = "Sale";

    /**
     * string for Search Button
     */
    public static final String SEARCH = "Search";

    /**
     * string for Select Button
     */
    public static final String SELECT = "Select";

    /**
     * action identifier for send action
     */
    public static final String SEND = "Send";

    /**
     * Constant for SERVICE_ALERT button action name
     */
    public static final String SERVICE_ALERT = "ServiceAlert";

    /**
     * action identifier for special order action
     */
    public static final String SPECIAL_ORDER = "SpecialOrder";

    /**
     * Store Credit button
     */
    public static final String STORE_CREDIT = "StoreCredit";

    /**
     * action identifier for suspend action
     */
    public static final String SUSPEND = "Suspend";

    /**
     * action identifier for send action
     */
    public static final String TAX = "Tax";

    /**
     * Constant for TEMP_PASS button action name
     */
    public static final String TEMP_PASS = "TempPass";

    /**
     * Constant for tender button action name
     */
    public static final String TENDER = "Tender";

    /**
     * Constant string for Till Reconcile button
     */
    public static final String TILL_RECONCILE = "TillReconcile";

    /**
     * Constant for till options button action name
     */
    public static final String TILLFUNCTIONS = "TillFunctionsContinue";

    /**
     * Constant for TRAINING_ON_OFF button action name
     */
    public static final String TRAINING_ON_OFF = "TrainingOnOff";

    /**
     * Constant for TRANS_REENTRY button action name
     */
    public static final String TRANS_REENTRY = "TransReentry";

    /**
     * travel check button
     */
    public static final String TRAVEL_CHECK = "TravelCheck";

    /**
     * string for Undo Button
     */
    public static final String UNDO = "Undo";

    /**
     * Constant for Update button action name
     */
    public static final String UPDATE = "Update";

    /**
     * action identifier for void action
     */
    public static final String VOID = "Void";

    /**
     * Constant for Web Store button action name
     */
    public static final String WEB_STORE = "WebStore";

    /**
     * string for Yes Button
     */
    public static final String YES = "Yes";
    
    /* Scan sheet button action name */
    public static final String SCANSHEET = "ScanSheet";

    /**
     * string for Request Ticket Button
     */
    public static final String REQUEST_TICKET = "RequestTicket"; 
    
    /**
     * string for No Receipt Button 
     */
    public static final String NO_RECEIPT = "NoReceipt"; 
    
    

}
