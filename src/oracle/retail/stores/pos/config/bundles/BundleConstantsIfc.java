/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/config/bundles/BundleConstantsIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:34 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    acadar    05/03/10 - added logic for searching for external orders by the
 *                         default search criteria
 *    abondala  01/03/10 - update header date
 *    sgu       02/04/09 - add constant for localization bundle
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         11/22/2007 10:59:02 PM Naveen Ganesh   PSI
 *         Code checkin
 *    3    360Commerce 1.2         3/31/2005 4:27:18 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:19:53 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:09:40 PM  Robert Pearse
 *
 *   Revision 1.6  2004/03/25 20:25:15  jdeleau
 *   @scr 4090 Deleted items appearing on Ingenico, I18N, perf improvements.
 *   See the scr for more info.
 *
 *   Revision 1.5  2004/03/16 18:30:48  cdb
 *   @scr 0 Removed tabs from all java source code.
 *
 *   Revision 1.4  2004/03/09 17:23:47  baa
 *   @scr 3561 Add bin range, check digit and bad swipe dialogs
 *
 *   Revision 1.3  2004/02/27 19:30:38  khassen
 *   @scr 0 Capture Customer Info use-case
 *
 *   Revision 1.2  2004/02/12 16:48:31  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:13  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 15:51:06   CSchellenger
 * Initial revision.
 *
 *    Rev 1.2   Jan 28 2003 15:12:52   mpb
 * SCR #1626
 * Added constant for pricing bundle
 * Resolution for POS SCR-1626: Pricing Feature
 *
 *    Rev 1.1   Aug 21 2002 09:01:24   DCobb
 * Added alterationText .
 * Resolution for POS SCR-1753: POS 5.5 Alterations Package
 *
 *    Rev 1.0   Apr 29 2002 15:44:56   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:12:24   msg
 * Initial revision.
 *
 *    Rev 1.4   Mar 12 2002 18:28:12   mpm
 * Externalized line-display text.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.3   Mar 12 2002 14:09:04   mpm
 * Externalized text in receipts and documents.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.2   Mar 10 2002 18:00:00   mpm
 * Externalized text in dialog messages.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.1   Mar 10 2002 11:06:48   mpm
 * Externalized text.
 *
 *    Rev 1.0   Mar 09 2002 14:13:42   mpm
 * Initial revision.
 * Resolution for POS SCR-351: Internationalization
 * ===========================================================================
 */
package oracle.retail.stores.pos.config.bundles;

//---------------------------------------------------------------------
/**
    These constants are used in conjunction with the resource bundles. <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//---------------------------------------------------------------------
public interface BundleConstantsIfc
{
    /**
    common constant
    **/
    public static String COMMON      = "Common";
    /**
        signaturecapture bundle name constant
    **/
    public static String SIGNATURE_CAPTURE_BUNDLE_NAME       = "signaturecaptureText";
    /**
        servicealert bundle name constant
    **/
    public static String SERVICE_ALERT_BUNDLE_NAME           = "servicealertText";
    /**
        security bundle name constant
    **/
    public static String SECURITY_BUNDLE_NAME                   = "securityText";
    /**
        reprintreceipt bundle name constant
    **/
    public static String REPRINT_RECEIPT_BUNDLE_NAME           = "reprintreceiptText";
    /**
        reasoncode bundle name constant
    **/
    public static String REASON_CODE_BUNDLE_NAME               = "reasoncodeText";
    /**
        queue bundle name constant
    **/
    public static String QUEUE_BUNDLE_NAME                   = "queueText";
    /**
        printing bundle name constant
    **/
    public static String PRINTING_BUNDLE_NAME                   = "printingText";
    /**
        postvoid bundle name constant
    **/
    public static String POSTVOID_BUNDLE_NAME                   = "postvoidText";
    /**
        pricing bundle name constant
    **/
    public static String PRICING_BUNDLE_NAME                   = "pricingText";
    /**
        payhouseaccount bundle name constant
    **/
    public static String PAY_HOUSE_ACCOUNT_BUNDLE_NAME       = "payhouseaccountText";
    /**
        operatorid bundle name constant
    **/
    public static String OPERATORID_BUNDLE_NAME               = "operatoridText";
    /**
        nosale bundle name constant
    **/
    public static String NO_SALE_BUNDLE_NAME                   = "nosaleText";
    /**
        listeditor bundle name constant
    **/
    public static String LIST_EDITOR_BUNDLE_NAME               = "listeditorText";
    /**
        ejournal bundle name constant
    **/
    public static String EJOURNAL_BUNDLE_NAME                   = "ejournalText";
    /**
        common bundle name constant
    **/
    public static String COMMON_BUNDLE_NAME                   = "commonText";
    /**
        browser bundle name constant
    **/
    public static String BROWSER_BUNDLE_NAME                   = "browserText";
    /**
        admin bundle name constant
    **/
    public static String ADMIN_BUNDLE_NAME                   = "adminText";
    /**
        parameter bundle name constant
    **/
    public static String PARAMETER_BUNDLE_NAME               = "parameterText";
    /**
        alterations bundle name constant
    **/
    public static String ALTERATIONS_BUNDLE_NAME               = "alterationsText";
    /**
        businessdate bundle name constant
    **/
    public static String BUSINESS_DATE_BUNDLE_NAME           = "businessdateText";
    /**
        customer bundle name constant
    **/
    public static String CUSTOMER_BUNDLE_NAME                   = "customerText";
    /**
        dailyoperations bundle name constant
    **/
    public static String DAILY_OPERATIONS_BUNDLE_NAME           = "dailyoperationsText";
    /**
        dialog bundle name constant
    **/
    public static String DIALOG_BUNDLE_NAME                   = "dialogText";
    /**
        email bundle name constant
    **/
    public static String EMAIL_BUNDLE_NAME                   = "emailText";
    /**
        employee bundle name constant
    **/
    public static String EMPLOYEE_BUNDLE_NAME                   = "employeeText";
    /**
        giftcard bundle name constant
    **/
    public static String GIFTCARD_BUNDLE_NAME                   = "giftcardText";
    /**
        inquiryoptions bundle name constant
    **/
    public static String INQUIRY_OPTIONS_BUNDLE_NAME           = "inquiryoptionsText";
    /**
        layaway bundle name constant
    **/
    public static String LAYAWAY_BUNDLE_NAME                   = "layawayText";
    /**
        main bundle name constant
    **/
    public static String MAIN_BUNDLE_NAME                       = "mainText";
    /**
        manager bundle name constant
    **/
    public static String MANAGER_BUNDLE_NAME                   = "managerText";
    /**
        modifyitem bundle name constant
    **/
    public static String MODIFYITEM_BUNDLE_NAME               = "modifyitemText";
    /**
        modifytransaction bundle name constant
    **/
    public static String MODIFYTRANSACTION_BUNDLE_NAME       = "modifytransactionText";
    /**
        order bundle name constant
    **/
    public static String ORDER_BUNDLE_NAME                   = "orderText";
    /**
        poscount bundle name constant
    **/
    public static String POSCOUNT_BUNDLE_NAME                   = "poscountText";
    /**
        pos bundle name constant
    **/
    public static String POS_BUNDLE_NAME                       = "posText";
    /**
        reports bundle name constant
    **/
    public static String REPORTS_BUNDLE_NAME                   = "reportsText";
    /**
        returns bundle name constant
    **/
    public static String RETURNS_BUNDLE_NAME                   = "returnsText";
    /**
        role bundle name constant
    **/
    public static String ROLE_BUNDLE_NAME                       = "roleText";
    /**
        send bundle name constant
    **/
    public static String SEND_BUNDLE_NAME                       = "sendText";
    /**
        specialorder bundle name constant
    **/
    public static String SPECIAL_ORDER_BUNDLE_NAME           = "specialorderText";
    /**
        tender bundle name constant
    **/
    public static String TENDER_BUNDLE_NAME                   = "tenderText";
    /**
        till bundle name constant
    **/
    public static String TILL_BUNDLE_NAME                       = "tillText";
    /**
        receipt bundle name constant
    **/
    public static String RECEIPT_BUNDLE_NAME                       = "receiptText";
    /**
        line display bundle name constant
    **/
    public static String LINE_DISPLAY_BUNDLE_NAME               = "linedisplayText";

	/**
		inventory bundle name constant
	**/
    public static String INVENTORY_INQUIRY_BUNDLE_NAME           = "inventoryText";

    /**
		localization bundle name constant
     **/
    public static String LOCALIZATION_BUNDLE_NAME           	= "localizationText";

    /**
        capture customer information name constant
    **/
    public static String CAPTURE_CUSTOMER_INFO_NAME                = "captureCustomerInfo";
    /**
     * Constant for CID Screen device text
     */
    public static String CID_SCREENS_BUNDLE_NAME = "cidscreenText";

    /**
     * Constant for the External Order bundle
     */
    public static String EXTERNAL_ORDER_BUNDLE_NAME = "externalorderText";


    /**
        constant for generic report printing message tag
    **/
    public static String REPORT_PRINTING_MESSAGE_TAG = "ReportPrinting";
    /**
        constant for generic report printing message
    **/
    public static String REPORT_PRINTING_MESSAGE = "Report is printing.";
    /**
        printer offline tag
    **/
    public static String PRINTER_OFFLINE_TAG = "RetryContinue.PrinterOffline";
    /**
        printer offline message
    **/
    public static String PRINTER_OFFLINE = "Printer is offline.";
    /**
        line display spec
    **/
    public static String LINE_DISPLAY_SPEC = "LineDisplay";

}                                       // end interface BundleConstantsIfc
