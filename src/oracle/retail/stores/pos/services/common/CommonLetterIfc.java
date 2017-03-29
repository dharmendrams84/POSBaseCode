/* ===========================================================================
* Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/CommonLetterIfc.java /rgbustores_13.4x_generic_branch/3 2012/01/31 16:08:55 mjwallac Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    mjwallac  01/27/12 - Forward port: SQL Exception when trying to save a
 *                         resumed order transaction that had been linked to a
 *                         customer, but customer was deleted before resuming.
 *    cgreene   07/21/11 - remove ability to show separate Debit button
 *    cgreene   07/14/11 - add common authoriztion letters
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  05/19/10 - search flow update
 *    acadar    05/14/10 - initial version for external order processing
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech11_techissueseatel from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   03/26/10 - added UNKNOWN_EXCEPTION letter
 *    abondala  01/03/10 - update header date
 *    mchellap  12/16/09 - Merge checkin
 *    mchellap  12/16/09 - Added CheckSize letter
 *
 * ===========================================================================
 * $Log:
 *  9    360Commerce 1.8         3/12/2008 11:18:34 PM  Manikandan Chellapan
 *       CR#30670 Modified redeem service to save transaction after timeout
 *  8    360Commerce 1.7         2/22/2008 10:30:34 AM  Pardee Chhabra  CR
 *       30191: Tender Refund options are not displayed as per specification
 *       for Special Order Cancel feature.
 *  7    360Commerce 1.6         2/12/2008 4:40:30 AM   Naveen Ganesh   Added
 *       new constant for Item not found in local database
 *  6    360Commerce 1.5         1/10/2008 4:34:54 AM   Naveen Ganesh
 *       Constant INVENTORY_DISCLAIMER added
 *  5    360Commerce 1.4         11/22/2007 10:58:58 PM Naveen Ganesh   PSI
 *       Code checkin
 *  4    360Commerce 1.3         11/2/2006 10:43:32 AM  Keith L. Lesikar
 *       OracleCustomer parameter update.
 *  3    360Commerce 1.2         3/31/2005 4:27:29 PM   Robert Pearse
 *  2    360Commerce 1.1         3/10/2005 10:20:19 AM  Robert Pearse
 *  1    360Commerce 1.0         2/11/2005 12:10:06 PM  Robert Pearse
 * $
 * Revision 1.10  2004/07/14 18:55:18  dcobb
 * @scr 6014 Sale: POS not prompting to insert till
 * Modified tillsuspend to not prompt to remove till under register accountability.
 *
 * Revision 1.9  2004/07/09 15:11:22  dcobb
 * @scr 2009 Password for user ID is not prompted for after an employee logs off POS while in Register Accountability mode.
 * Logoff letter is now mailed upon successful logoff.
 *
 * Revision 1.8  2004/05/19 20:34:41  crain
 * @scr 5080 Tender Redeem_Disc. Applied Alt Flow not Called from Foreign Gift Cert Alt Flow
 *
 * Revision 1.7  2004/03/16 18:30:49  cdb
 * @scr 0 Removed tabs from all java source code.
 *
 * Revision 1.6  2004/03/11 20:03:27  blj
 * @scr 3871 - added/updated shuttles to/from redeem, to/from tender, to/from completesale.
 * also updated sites cargo for new redeem transaction.
 *
 * Revision 1.5  2004/02/27 19:51:16  baa
 * @scr 3561 Return enhancements
 *
 * Revision 1.4  2004/02/24 23:41:48  bjosserand
 * @scr 0 Mail Bank Check
 * Revision 1.3 2004/02/24 15:15:34 baa @scr 3561 returns enter item
 *
 * Revision 1.2 2004/02/12 16:48:00 mcs Forcing head revision
 *
 * Revision 1.1.1.1 2004/02/11 01:04:11 cschellenger updating to pvcs 360store-current
 *
 * Rev 1.0 Nov 04 2003 19:00:00 cdb Initial revision. Resolution for 3430: Sale Service Refactoring
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

/**
 * This interface lists the constants for commonly used letters.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/3 $
 */
public interface CommonLetterIfc
{
    /**
     * string for security access denied
     */
    public static final String ACCESS_DENIED = "AccessDenied";

    /**
     * string for Add letter
     */
    public static final String ADD = "Add";

    /**
     * Constant for Approval letter
     */
    public static final String APPROVED = "Approved";

    /**
     * string for Cancel letter
     */
    public static final String CANCEL = "Cancel";

    /**
     * Letter used to continue flow from CancelOrderReturnItemInfoAisle to next
     * site
     */
    public static final String CANCEL_ORDER_NEXT = "CancelOrderNext";
    
    /**
     * Letter used to continue flow from LookupTransactionLinkedCustomerNotFoundAisle
     */
    public static final String LINKED_CUSTOMER_NOT_FOUND = "LinkedCustomerNotFoundError";

    /**
     * Constant for CanceledByCustomer letter
     */
    public static final String CANCELED_BY_CUSTOMER = "CanceledByCustomer";

    /**
     * cash drawer closed letter
     */
    public static final String CASH_DRAWER_CLOSED = "CashDrawerClosed";

    /**
     * cash drawer complete letter
     */
    public static final String CASH_DRAWER_COMPLETE = "CashDrawerComplete";

    /**
     * cash drawer offline letter
     */
    public static final String CASH_DRAWER_OFFLINE = "CashDrawerOffline";

    /**
     * string for CashierError letter
     */
    public static final String CASHIER_ERROR = "CashierError";

    /**
     * Check access letter
     */
    public static final String CHECK_ACCESS = "CheckAccess";

    /**
     * Check item size letter
     */
    public static final String CHECK_SIZE = "CheckSize";

    /**
     * Clear letter
     */
    public static final String CLEAR = "Clear";

    /**
     * Constant for configuration error letter
     */
    public static final String CONFIGURATION_ERROR = "ConfigurationError";

    /**
     * string for Continue letter
     */
    public static final String CONTINUE = "Continue";

    /**
     * Customer letter
     */
    public static final String CUSTOMER = "Customer";

    /**
     * Database offline error
     */
    public static final String DATABASE_OFFLINE_MESSAGE = "DatabaseError";

    /**
     * DateRange letter
     */
    public static final String DATERANGE = "DateRange";

    /**
     * string for DbError letter
     */
    public static final String DB_ERROR = "DbError";

    /**
     * Constant for Declined letter
     */
    public static final String DECLINED = "Declined";

    /**
     * Detail letter
     */
    public static final String DETAIL = "Detail";

    /**
     * string for Done letter
     */
    public static final String DONE = "Done";

    /**
     * string for Error letter
     */
    public static final String ERROR = "Error";

    /**
     * string for Failure letter
     */
    public static final String FAILURE = "Failure";

    /**
     * Constant for Offline Floor Limit letter
     */
    public static final String FLOOR_LIMIT = "FloorLimit";

    /**
     * Foreign
     */
    public static final String FOREIGN = "Foreign";

    /**
     * string for GiftCard letter
     */
    public static final String GIFTCARD = "GiftCard";

    /**
     * string for HardTotalsError letter
     */
    public static final String HARD_TOTALS_ERROR = "HardTotalsError";

    /**
     * Info not found error
     */
    public static final String INFO_NOT_FOUND_ERROR_MESSAGE = "INFO_NOT_FOUND_ERROR";

    /**
     * Info not found notice
     */
    public static final String INFO_NOT_FOUND_NOTICE_MESSAGE = "INFO_NOT_FOUND_NOTICE";

    /**
     * string for InquiryError letter
     */
    public static final String INQUIRY_ERROR = "InquiryError";

    /**
     * string for Invalid letter
     */
    public static final String INVALID = "Invalid";

    /**
     * Database offline error
     */
    public static final String INVENTORY_DISCLAIMER = "InverntoryResultsWorkPanelSpec.InventoryDisclaimer";

    /**
     * Item Information not found in Local Database
     */
    public static final String ITEM_NOT_FOUND_MESSAGE = "ItemNotFoundError";

    /**
     * Link letter
     */
    public static final String LINK = "Link";

    /**
     * string for IDError letter
     */
    public static final String LOGIN_ERROR = "IDError";

    /**
     * Logoff
     */
    public static final String LOGOFF = "Logoff";

    /**
     * string for multiple matches letter
     */
    public static final String MULTIPLE_MATCHES = "MultipleMatches";

    /**
     * Database offline error
     */
    public static final String NEW_SEARCH = "NewSearch";

    /**
     * string for Next letter
     */
    public static final String NEXT = "Next";

    /**
     * string for No letter
     */
    public static final String NO = "No";

    /**
     * string for NoMoreTillsError letter
     */
    public static final String NO_MORE_TILLS_ERROR = "NoMoreTillsError";

    /**
     * No Serialized Item Letter
     */
    public static final String NO_SERIALISED_ITEM = "NoSerialisedItem";

    /**
     * NoMatch letter
     */
    public static final String NOMATCH = "NoMatch";

    /**
     * Not Sellable Letter
     */
    public static final String NOT_AVAILABLE = "NotAvailable";

    /**
     * NotFound letter
     */
    public static final String NOT_FOUND = "NotFound";

    /**
     * Not Sellable Letter
     */
    public static final String NOT_SELLABLE = "NotSellable";

    /**
     * Constant for Offline letter
     */
    public static final String OFFLINE = "Offline";

    /**
     * string for Ok letter
     */
    public static final String OK = "Ok";

    /**
     * OneFound letter
     */
    public static final String ONE_FOUND = "OneFound";

    /**
     * One Time Customer Capture (OracleCustomer=N)
     */
    public static final String ONE_TIME_CUSTOMER_CAPTURE = "OneTimeCustomerCapture";

    /**
     * OneMatch letter
     */
    public static final String ONEMATCH = "OneMatch";

    /**
     * open-cash-drawer letter
     */
    public static final String OPEN_CASH_DRAWER = "OpenCashDrawer";

    /**
     * string for Options letter
     */
    public static final String OPTIONS = "Options";

    /**
     * Override letter
     */
    public static final String OVERRIDE = "Override";

    /**
     * string for ParameterError letter
     */
    public static final String PARAMETER_ERROR = "ParameterError";

    /** 
     * Constant for Partially Approval letter
     */
    public static final String PARTIALLY_APPROVED = "PartiallyApproved";

    /** 
     * Constant for PositiveID letter
     */
    public static final String POSITIVE_ID = "PositiveID";

    /**
     * Print letter
     */
    public static final String PRINT = "Print";

    /**
     * Prompt letter
     */
    public static final String PROMPT = "Prompt";

    /**
     * Redeem
     */
    public static final String REDEEM = "Redeem";

    /**
     * constant for referral letter
     */
    public static final String REFERRAL = "Referral";

    /**
     * string for RegisterClosedError letter
     */
    public static final String REGISTER_CLOSED_ERROR = "RegisterClosedError";

    /**
     * string for RegisterOpenError letter
     */
    public static final String REGISTER_OPEN_ERROR = "OpenRegisterError";

    /**
     * rendezvous letter (meet up at the other end)
     */
    public static final String RENDEZVOUS = "RendezVous";

    /**
     * Retry letter
     */
    public static final String RETRY = "Retry";

    /**
     * String constant for Return letter
     */
    public static final String RETURN = "Return";

    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/3 $";

    /**
     * String constant for Sale letter
     */
    public static final String SALE = "Sale";

    /**
     * string for Search letter
     */
    public static final String SEARCH = "Search";

    /**
     * string for Select letter
     */
    public static final String SELECT = "Select";

    /**
     * Sellable Letter
     */
    public static final String SELLABLE = "Sellable";

    public static final String SERIAL_NUMBER = "SerialNumber";

    /**
     * Size letter
     */
    public static final String SIZE = "Size";

    /**
     * Skip letter
     */
    public static final String SKIP = "Skip";

    /**
     * Status letter
     */
    public static final String STATUS = "Status";

    /**
     * StoreNumber letter
     */
    public static final String STORENUMBER = "StoreNumber";

    /**
     * string for Success letter
     */
    public static final String SUCCESS = "Success";

    /**
     * string for TillIdError letter
     */
    public static final String TILL_ID_ERROR = "TillIdError";

    /**
     * string for TillAlreadyOpenError letter
     */
    public static final String TILL_OPEN_ERROR = "TillAlreadyOpenError";

    /**
     * string for Timeout letter
     */
    public static final String TIMEOUT = "Timeout";

    /**
     * string for too many records retrieved
     */
    public static final String TOO_MANY = "TooMany";

    /**
     * Too Many matches
     */
    public static final String TOO_MANY_MATCHES_MESSAGE = "TooManyMatches";

    /**
     * string for Training letter
     */
    public static final String TRAINING = "Training";

    /**
     * string for Undo letter
     */
    public static final String UNDO = "Undo";

    /**
     * Constant for unknown error letter
     */
    public static final String UNKNOWN_ERROR = "UnknownError";

    /**
     * string for UnknownException letter
     */
    public static final String UNKNOWN_EXCEPTION = "UnknownException";

    /**
     * string for UpdateError letter
     */
    public static final String UPDATE_ERROR = "UpdateError";

    /**
     * Write Hard Totals letter
     */
    public static final String UPDATE_HARD_TOTALS = "UpdateHardTotals";

    /**
     * string for Valid letter
     */
    public static final String VALID = "Valid";

    /**
     * string for Validate letter
     */
    public static final String VALIDATE = "Validate";

    /**
     * string for Yes letter
     */
    public static final String YES = "Yes";
}
