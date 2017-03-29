/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/giftcard/GiftCardConstantsIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:28 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    mpbarnet  03/16/10 - Added dialog id
 *                         RETURN_GIFT_CARD_NOT_FOUND_ERROR_DIALOG_ID to
 *                         display a dialog when the gift card information is
 *                         not returned from ISD.
 *    abondala  01/03/10 - update header date
 *    mchellap  06/19/09 - Added constants for invalid gift card return
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         5/9/2008 10:31:30 AM   Jack G. Swan
 *         Changes made to provide a user message when a card is not found
 *         during an inquiry from item inquiry or tendering a gift card.
 *    3    360Commerce 1.2         3/31/2005 4:28:16 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:21:53 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:11:13 PM  Robert Pearse
 *
 *   Revision 1.4  2004/03/22 23:59:08  lzhao
 *   @scr 3872 - add gift card redeem (initial)
 *
 *   Revision 1.3  2004/03/16 18:30:48  cdb
 *   @scr 0 Removed tabs from all java source code.
 *
 *   Revision 1.2  2004/02/12 16:50:20  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:16  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.5   Jan 30 2004 14:12:36   lzhao
 * remove activation error dialog, add three new dialog based on req. changes.
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 *
 *    Rev 1.4   Dec 18 2003 09:43:56   lzhao
 * add decline and unknown error handle
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 *
 *    Rev 1.3   Dec 08 2003 09:15:06   lzhao
 * remove gift card activation error
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 *
 *    Rev 1.2   Nov 26 2003 09:19:14   lzhao
 * use methods in utility, cleanup.
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 *
 *    Rev 1.1   Nov 21 2003 14:46:58   lzhao
 * refactory gift card using sale, gift option service, etc.
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 *
 *    Rev 1.0   Nov 07 2003 16:31:40   lzhao
 * Initial revision.
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.giftcard;

//--------------------------------------------------------------------------
/**
    This class  is the container for the dialog id, default text strings used
    in the gift card issue, reload, inquiry, etc.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
//--------------------------------------------------------------------------
public interface GiftCardConstantsIfc
{
    /**
     * gift card tag
     */
    public static final String GIFT_CARD_TAG = "GiftCard";
    /**
     * gift card text
     */
    public static final String GIFT_CARD = "Gift Card";

    // The following are all dialog IDs for the gift card service.
    // They are all specified in dialogText properties file
    /**
     * less than minimun amount dialog id
     */
    public static final String LESS_THAN_MIM_AMOUNT_DIALOG_ID = "LessThanMinimumAmount";
    /**
     * more than manimun amount dialog id
     */
    public static final String MORE_THAN_MAX_AMOUNT_DIALOG_ID = "MoreThanMaximumAmount";
    /**
       resource ID for Bad MSR Read Error dialog ID
    **/
    public static final String BAD_MSR_READ_ERROR_DIALOG_ID = "BadMSRReadError";
    /**
     * Duplication GiftCard ID Screen ID
     */
    public static final String DUPLICATE_GIFT_CARD_DIALOG_ID = "DuplicateGiftCard";
    /**
     * Reenter or Cancel GiftCard ID Screen ID
     */
    public static final String REENTER_OR_CANCEL_GIFT_CARD_DIALOG_ID = "GiftCardReenterOrCancel";
    /**
     * Gift Card activation Error, Activation returns Decline, Invalid Trans. No More Loads Allowed.
     * Wrong PIN, Gift Card Number Error, or Term ID Error
     */
    public static final String GIFT_CARD_ACTIVATION_ERROR_DIALOG_ID = "GiftCardActivationError";
    /**
     * Gift Card issue activation Error, Activation returns Decline, Invalid Trans.
     * Gift Card Number Error
     */
    public static final String GIFT_CARD_ISSUE_ERROR_DIALOG_ID = "GiftCardIssueError";
    /**
     * Gift Card reload activation Error, Activation returns Decline, Invalid Trans.
     */
    public static final String RELOAD_GIFT_CARD_ERROR_DIALOG_ID = "ReloadGiftCardError";
    /**
     * Gift Card reload activation Error, Activation returns Decline, Invalid Trans.
     */
    public static final String INQUIRY_GIFT_CARD_ERROR_DIALOG_ID = "InquiryGiftCardError";
    /**
     * Gift Card reload activation Error, Activation returns card number error
     */
    public static final String RELOAD_GIFT_CARD_NUMB_ERROR_DIALOG_ID = "ReloadGiftCardNumbError";
    
    /**
     * Gift Card return not found Error, Inquiry return card info error
     */
    public static final String RETURN_GIFT_CARD_NOT_FOUND_ERROR_DIALOG_ID = "GiftCardReturnNotFound";

    /**
     * Gift Card return invalid Error
     */
    public static final String GIFT_CARD_RETURN_INVALID_DIALOG_ID = "GiftCardReturnInvalid";

    /**
     * Gift Card Processor Offline Error Dialog ID, the response code is offline or timeout
     */
    public static final String GIFT_CARD_PROCESSOR_OFFLINE_ERRIR_DIALOG_ID = "GiftCardProcessorOfflineError";
    /**
        processor error tag
    **/
    public static String ERROR_UNKNOWN_TAG = new StringBuffer().append(GIFT_CARD_ACTIVATION_ERROR_DIALOG_ID).append(".UnknownError").toString();
    /**
        default processor error
    **/
    public static String ERROR_UNKNOWN = "unknown error.";
    /**
        processor error tag
    **/
    public static String ERROR_DECLINED_TAG = (new StringBuffer().append(GIFT_CARD_ACTIVATION_ERROR_DIALOG_ID).append(".Declined")).toString();
    /**
        default processor error
    **/
    public static String ERROR_DECLINED = "The request has been declined.";
    /**
        processor error tag
    **/
    public static String ERROR_OFFLINE_TAG = new StringBuffer().append(GIFT_CARD_ACTIVATION_ERROR_DIALOG_ID).append(".Offline").toString();
    /**
        default processor error
    **/
    public static String ERROR_OFFLINE = "Gift card processor is offline";
    /**
        processor error tag
    **/
    public static String ERROR_TIMEOUT_TAG = new StringBuffer().append(GIFT_CARD_ACTIVATION_ERROR_DIALOG_ID).append(".Timeout").toString();
    /**
        default processor error
    **/
    public static String ERROR_TIMEOUT = "The request has timed out";
    /**
        processor error tag
    **/
    public static String ERROR_INVALID_REQUEST_TAG = new StringBuffer().append(GIFT_CARD_ACTIVATION_ERROR_DIALOG_ID).append(".InvalidRequest").toString();
    /**
        default processor error
    **/
    public static String ERROR_INVALID_REQUEST = "Invalid request";
    /**
        processor error tag
    **/
    public static String ERROR_UNKNOWN_CARD_TAG = new StringBuffer().append(GIFT_CARD_ACTIVATION_ERROR_DIALOG_ID).append(".UnknownCard").toString();
    /**
        default processor error
    **/
    public static String ERROR_UNKNOWN_CARD = "Unknown card";
    /**
     * processor error tag
     */
    public static String ERROR_DUPLICATE_TAG = new StringBuffer().append(GIFT_CARD_ACTIVATION_ERROR_DIALOG_ID).append(".Duplicate").toString();
    /**
        default processor error
    **/
    public static String ERROR_DUPLICATE = "Duplicate card";

    /**
     * Gift card modified
     */
    public static String ERROR_MODIFIED_TAG = new StringBuilder().append(GIFT_CARD_RETURN_INVALID_DIALOG_ID).append(".ModifiedGiftCard").toString();

    /**
     * Modified gift card error
     */
    public static String ERROR_MODIFIED = "modified";
    /**
     * processor error tag
     */
    public static String ZERO_BALANCE_TAG = new StringBuffer().append(GIFT_CARD_ACTIVATION_ERROR_DIALOG_ID).append(".ZeroBalance").toString();
    /**
     default processor error
     **/
    public static String ZERO_BALANCE = "zero balance";
    /**
     * Print is offline dialog id
     */
    public static final String PRINT_OFFLINE_DIALOG_ID = "RetryContinue";
    /**
     * Generic Database Error dialog id
     */
    public static final String DATABASE_ERROR_DIALOG_ID = "DATABASE_ERROR";

    /**
       Invalid Gift Card Number Error dialog, not pass bin range or check digit.
    **/
    public static final String INVALID_NUMBER_ERROR_DIALOG_ID = "InvalidGiftCard";
    /**
       resource ID for Invalid Number Error dialog  // make sure which one InvalidGiftCard or InvalidNumberError we are going to use.
    **/
    public static final String INVALID_GIFT_CARD_NUMBER_ERROR = "InvalidGiftCardNumberError";


    //The follows are the tags in Prompt and Respose area, which are
    //specified in giftcardText property file
    /**
     * gift card inquiry spec tag
     */
    public static final String GIFT_CARD_INQUIRY_SPEC_TAG = "GiftCardInquirySpec";
    /**
        request prompt tag
    **/
    public static String REQUEST_PROMPT_TAG = "GiftCardRequestPrompt";
    /**
        Gift card activation prompt bundle tag
    **/
    public static final String CONNECTING_PROMPT_TAG = "ConnectingPrompt";
    /**
        Gift card activation prompt default text
    **/
    public static final String CONNECTING_PROMPT = "Please wait: {0} gift card {1} ...";
    /**
        request prompt
    **/
    public static String REQUEST_PROMPT = "Please wait:  Request sent to the gift card processor ...";
    /**
        request screen name tag
    **/
    public static String REQUEST_SCREEN_NAME_TAG = "GiftCardRequestScreenName";
    /**
        request screen name
    **/
    public static String REQUEST_SCREEN_NAME = "GC Inquiry";
    /**
        Gift card activation screen name bundle tag
    **/
    public static final String ACTIVATION_SCREEN_NAME_TAG = "ActivatingScreenName";
    /**
        Gift card activation screen name default text
    **/
    public static final String ACTIVATION_SCREEN_NAME = "Activating";
    /**
        Gift card activation screen name bundle tag
    **/
    public static final String RELOADING_SCREEN_NAME_TAG = "ReloadingScreenName";
    /**
        Gift card activation screen name default text
    **/
    public static final String RELOADING_SCREEN_NAME = "Reloading";
    /**
        Gift card deactivation screen name bundle tag
    **/
    public static final String DEACTIVATION_SCREEN_NAME_TAG = "DeactivatingScreenName";
    /**
        Gift card deactivation screen name default text
    **/
    public static final String DEACTIVATION_SCREEN_NAME = "Deactivating";
    /**
        remaining balance label bundle tag
    **/
    /** Suffix of the key for amount denominators */
    public static final String GIFT_CARD_RELOAD_AMOUNT =  "GiftCardReloadAmount";
    /**
        amount button spec
    **/
    public static final String GIFT_CARD_RELOAD_AMOUNT_BUTTON_SPEC = "GiftCardAmountButtonSpec";
    /**
        amount button format message default text
    **/
    public static final String AMOUNT_BUTTON_FORMAT_MESSAGE_TAG = "ButtonAmountMessageFormat";
    /**
        customer name bundle tag
    **/
    public static final String AMOUNT_BUTTON_FORMAT_MESSAGE_TEXT = "{0}{1}";
    /**
        remaining balance label text
    **/
    public static final String REMAINING_BALANCE_LABEL_TAG = "RemainingBalanceLabel";
    /**
        remaining balance label default text
    **/
    public static final String REMAINING_BALANCE_LABEL_TEXT = "Remaining Balance:";
    /**
     * gift card inquiry fail letter
     */
    public static final String INQUIRY_FAIL_LETTER = "InquiryFailed";
    /**
     * retry activation letter
     */
    public static final String ACTIVATION_RETRY_LETTER = "ActivationRetry";
    /**
     * reload declined
     */
    public static final String RELOAD_DECLINED_LETTER = "ReloadDeclined";
    /**
     * cancel activation letter
     */
    public static final String ACTIVATION_CANCEL_LETTER = "ActivationCancelled";
    /**
     * invalid gift card number letter
     */
    public static final String INVALID_CARD_NUM_LETTER = "InvalidCardNumber";

    // following are parameters ids in application.xml
    /**
     * default gift card item id
     */
    public static final String DEFAULT_GIFT_CARD_ITEM_ID = "DefaultGiftCardItemID";
    /**
     * Default item id backup if the item id not found in parameter file
     */
    public static final String DEFAULT_ITEM_ID = "70071000";

    /**
     * Prefix of the keys for amount denominators
     */
    public static final String ButtonLabelKeys[] =  new String[]{
        "First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh" };

    // following are parameter configuration tags
    /**
     * two argument tag
     */
    public static final String TWO_ARG_TAG = "TwoArg";
    /**
     * two argument text
     */
    public static final String TWO_ARG_TEXT = "{0} {1}";
    /**
     * number tag
     */
    public static final String NUMBER_TAG = "Number";
    /**
     * number text
     */
    public static final String NUMBER = "Number";

}
