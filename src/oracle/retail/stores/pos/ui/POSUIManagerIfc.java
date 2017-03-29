/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/POSUIManagerIfc.java /rgbustores_13.4x_generic_branch/9 2011/10/27 16:12:06 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    cgreen 10/27/11 - implement popup dialog for customer info
 *    hyin   10/27/11 - fixed sending cancel letter twice problem when user
 *                      double click on cancel button
 *    asinto 09/21/11 - Fixed training mode and transaction reentry mode
 *                      screens.
 *    asinto 09/16/11 - Update ApplicationFrame using UIManager and UISubsystem
 *                      for Transaction Re-Entry Mode.
 *    cgreen 08/18/11 - Refactor code to not unlock screen when setting model
 *                      to avoid unwanted letters.
 *    cgreen 07/21/11 - remove ability to show separate Debit button
 *    blarse 06/30/11 - SIGNATURE_CAPTURE and PIN_PAD status were replaced with
 *                      FINANCIAL_NETWORK status as part of the advance payment
 *                      foundation feature.
 *    sgu    05/23/11 - move inquiry for payment into instantcredit service
 *    sgu    05/20/11 - refactor instant credit inquiry flow
 *    vtemke 03/08/11 - Print Preview Quickwin for Reports
 *    jkoppo 03/02/11 - Added scan_sheet screen name constant
 *    blarse 02/23/11 - Renamed EMPLOYEE_SET_FINGERPRINT to
 *                      EMPLOYEE_ENROLL_FINGERPRINT for consistency.
 *    blarse 02/14/11 - add EMPLOYEE_VERIFY_FINGERPRINT to verify enrolled
 *                      fingerprint works
 *    blarse 06/09/10 - XbranchMerge blarsen_biometrics-poc from
 *                      st_rgbustores_techissueseatel_generic_branch
 *    blarse 05/25/10 - Added overlay screen names for new login w/ fingerprint
 *                      screen and enroll fingerprint via employee edit screen.
 *    cgreen 07/02/10 - added device status dialog screen
 *    cgreen 05/26/10 - convert to oracle packaging
 *    abonda 05/20/10 - updated search flow
 *    abonda 05/17/10 - Siebel search flow
 *    abonda 05/12/10 - External orders Search flow
 *    abonda 05/12/10 - Search external orders flow
 *    jswan  05/11/10 - Pre code reveiw clean up.
 *    jswan  05/11/10 - Returns flow refactor: added screen constant.
 *    acadar 05/03/10 - added logic for searching for external orders by the
 *                      default search criteria
 *    blarse 03/09/10 - Added overloaded statusChanged method that accepts
 *                      boolean unlockContainer param. This param is required
 *                      to prevent the premature unlocking of user input when
 *                      an asynchrounus online/offline update is sent.
 *    abonda 01/03/10 - update header date
 *    mchell 12/10/09 - Serialisation return without receipt changes
 *    cgreen 09/17/09 - added method to be able to show a dialog and block the
 *                      thread
 *    mahisi 02/27/09 - clean up code after code review by jack for PDO
 *    mdecam 02/12/09 - Added LookAndFeel support by Locale
 *    vikini 02/06/09 - Discount is disabled for a return transaction
 *    cgreen 01/30/09 - remove methods deprecated in 4.0
 *    mdecam 12/22/08 - Added constant for RM_REFUND_OPTIONS
 *    aratho 11/17/08 - updated for ereceipt feature
 *    aphula 11/27/08 - fixed merge issue
 *    aphula 11/24/08 - Checking files after code review by amrish
 *    mahisi 11/19/08 - Updated for review comments
 *    aphula 11/17/08 - Pickup Delivery order
 *    aphula 11/13/08 - Check in all the files for Pickup Delivery Order
 *                      functionality
 *    mahisi 11/13/08 - Added for Customer module for both ORPOS and ORCO
 *    rkar   11/12/08 - Adds/changes for POS-RM integration
 *    rkar   11/07/08 - Additions/changes for POS-RM integration
 *    nkgaut 09/29/08 - Added a new screen constant BROWSER_FOUNDATION
 *
 * ===========================================================================
 *
 *    $Log:
 *     11   360Commerce 1.10        1/10/2008 4:38:57 AM   Naveen Ganesh   New
 *          constant HOME_STORE_INVENTORY_RESULTS_LIST_SCREEN been added
 *     10   360Commerce 1.9         11/22/2007 10:59:08 PM Naveen Ganesh   PSI
 *          Code checkin
 *     9    360Commerce 1.8         4/30/2007 4:56:45 PM   Alan N. Sinton  CR
 *          26484 - Merge from v12.0_temp.
 *     8    360Commerce 1.7         11/8/2006 8:49:54 AM   Keith L. Lesikar
 *          Updates to the About screen.
 *     7    360Commerce 1.6         10/8/2006 8:51:10 PM   Rohit Sachdeva
 *          21237: Change Password Updates
 *     6    360Commerce 1.5         8/1/2006 6:17:20 PM    Brett J. Larsen CR
 *          16536 - adding support for returns using gift receipt with
 *          transaction and item not found
 *     5    360Commerce 1.4         1/25/2006 4:11:37 PM   Brett J. Larsen merge
 *           7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
 *     4    360Commerce 1.3         12/13/2005 4:42:43 PM  Barry A. Pape
 *          Base-lining of 7.1_LA
 *     3    360Commerce 1.2         3/31/2005 4:29:27 PM   Robert Pearse
 *     2    360Commerce 1.1         3/10/2005 10:24:18 AM  Robert Pearse
 *     1    360Commerce 1.0         2/11/2005 12:13:21 PM  Robert Pearse
 *    $
 *
 *     6    .v7x      1.4.1.0     5/12/2006 7:29:58 AM   Dinesh Gautam
 *          CR16536-Added Screen Id for ITEM_NOT_FOUND_PRICE_CODE screen
 *
 *     4    .v700     1.2.2.0     9/13/2005 15:37:35     Jason L. DeLeau Ifan
 *          id_itm_pos maps to multiple id_itms, let the user choose which one
 *          to use.
 *     3    360Commerce1.2         3/31/2005 15:29:27     Robert Pearse
 *     2    360Commerce1.1         3/10/2005 10:24:18     Robert Pearse
 *     1    360Commerce1.0         2/11/2005 12:13:21     Robert Pearse
 *
 *     4    .v710     1.2.3.0     9/21/2005 13:40:14     Brendan W. Farrell
 *          Initial Check in merge 67.
 *     3    360Commerce1.2         3/31/2005 15:29:27     Robert Pearse
 *     2    360Commerce1.1         3/10/2005 10:24:18     Robert Pearse
 *     1    360Commerce1.0         2/11/2005 12:13:21     Robert Pearse
 *
 *    Revision 1.53.2.1  2004/11/18 23:14:39  bwf
 *    @scr 6552 Call correct CPOI screens during tender process for swipe anytime.
 *
 *    Revision 1.53  2004/09/29 16:30:24  mweis
 *    @scr 7012 Special Order and Inventory integration -- canceling the entire order.
 *
 *    Revision 1.52  2004/09/17 23:12:43  mweis
 *    @scr 7012 Make Layaway's screens comply to specs for Inventory integration.
 *
 *    Revision 1.51  2004/09/03 16:01:27  mweis
 *    @scr 7012 Use Inventory constants in POS.
 *
 *    Revision 1.50  2004/09/02 18:47:50  bwf
 *    @scr 7139, 6920, 6921, 6922, 6899, 6927, 6934, 6935, 6944, 6925, 6945, 6957, 6961
 *     Updated help screen contents.
 *
 *    Revision 1.49  2004/08/27 18:13:49  bvanschyndel
 *    Added screen names for Iventory Inquiry multi store and single store
 *
 *    Revision 1.48  2004/07/31 18:29:16  bwf
 *    @scr 6551 Enable credit auth charge confirmation.
 *
 *    Revision 1.47  2004/07/26 21:14:55  bwf
 *    @scr 6203 Fixed text on micr screen.
 *
 *    Revision 1.46  2004/07/26 20:16:06  bwf
 *    @scr 6203 Fixed text on micr screen.
 *
 *    Revision 1.45  2004/07/01 13:52:38  jeffp
 *    @scr 5265 Changed name of overlayscreen for temporary employee master screen
 *
 *    Revision 1.44  2004/06/29 22:03:33  aachinfiev
 *    Merge the changes for inventory & POS integration
 *
 *    Revision 1.43  2004/06/07 18:28:37  jdeleau
 *    @scr 2775 Support multiple GeoCodes tax screen
 *
 *    Revision 1.42  2004/06/07 17:01:14  dcobb
 *    @scr 4204 Feature Enhancement: Till Options
 *    Add foreign currency counts.
 *
 *    Revision 1.41  2004/05/26 21:37:01  dcobb
 *    @scr 4204 Feature Enhancement: Till Options
 *    Add ForeignCurrencyCount screen.
 *
 *    Revision 1.40  2004/05/20 22:54:58  cdb
 *    @scr 4204 Removed tabs from code base again.
 *
 *    Revision 1.39  2004/05/18 19:17:53  crain
 *    @scr 4937 Gift Cert_TENDER_NUMBER_prompt text incorrect
 *
 *    Revision 1.38  2004/05/17 23:01:13  blj
 *    @scr 4449 - resolution
 *
 *    Revision 1.37  2004/05/06 05:05:49  tfritz
 *    @scr 4605 Added new CaptureReasonCodeForPriceOverride parameter
 *
 *    Revision 1.36  2004/04/30 21:04:56  crain
 *    @scr 4553 Redeem Gift Certificate
 *
 *    Revision 1.35  2004/04/19 23:15:58  dcobb
 *    @scr 4452 Feature Enhancement: Printing
 *    Add Reprint Select screen.
 *
 *    Revision 1.34  2004/04/19 18:48:56  awilliam
 *    @scr 4374 Reason Code featrure work
 *
 *    Revision 1.33  2004/04/16 18:56:34  tfritz
 *    @scr 4251 - Integer parameters now can except negative and positive integers.
 *
 *    Revision 1.32  2004/04/09 19:26:01  crain
 *    @scr 4105 Foreign Currency
 *
 *    Revision 1.31  2004/04/07 22:49:41  blj
 *    @scr 3872 - fixed problems with foreign currency, fixed ui labels, redesigned to do validation and adding tender to transaction in separate sites.
 *
 *    Revision 1.30  2004/03/30 21:28:13  bwf
 *    @scr 3591 Fixed this bug the correct way.
 *
 *    Revision 1.29  2004/03/26 21:18:20  cdb
 *    @scr 4204 Removing Tabs.
 *
 *    Revision 1.28  2004/03/25 23:01:24  lzhao
 *    @scr #3872 Redeem Gift Card
 *
 *    Revision 1.27  2004/03/24 19:37:10  mweis
 *    @scr 0 JavaDoc cleanup.
 *
 *    Revision 1.26  2004/03/22 23:59:08  lzhao
 *    @scr 3872 - add gift card redeem (initial)
 *
 *    Revision 1.25  2004/03/22 17:26:43  blj
 *    @scr 3872 - added redeem security, receipt printing and saving redeem transactions.
 *
 *    Revision 1.24  2004/03/21 16:34:29  bjosserand
 *    @scr 4093 Transaction Reentry
 *
 *    Revision 1.23  2004/03/19 21:02:56  mweis
 *    @scr 4113 Enable ISO_DATE datetype
 *
 *    Revision 1.22  2004/03/18 21:25:33  crain
 *    @scr 4105 Foreign Currency
 *
 *    Revision 1.21  2004/03/17 23:03:11  dcobb
 *    @scr 3911 Feature Enhancement: Markdown
 *    Code review modifications.
 *
 *    Revision 1.20  2004/03/16 18:30:40  cdb
 *    @scr 0 Removed tabs from all java source code.
 *
 *    Revision 1.19  2004/03/15 20:53:02  lzhao
 *    @scr 3840 Inquiry Options: Inventory Inquiry
 *    add Inventory_Search back in to avoid compile error for a deprecated file.
 *
 *    Revision 1.18  2004/03/12 18:41:52  khassen
 *    @scr 0 Till Pay In/Out use case
 *
 *    Revision 1.17  2004/03/11 23:39:48  epd
 *    @scr 3561 New work to accommodate returning kit items
 *
 *    Revision 1.16  2004/03/11 20:34:36  baa
 *    @scr 3561 add changes to handle transaction variable length id
 *
 *    Revision 1.15  2004/03/10 00:09:03  lzhao
 *    @scr 3840 InquiryOptions: Inventory Inquiry
 *
 *    Revision 1.14  2004/03/09 19:24:08  bwf
 *    @scr 3956 General Tenders work.
 *
 *    Revision 1.13  2004/03/04 16:41:31  cdb
 *    @scr 3588 Removed some additional CVS Merge related tokens.
 *
 *    Revision 1.12  2004/03/03 23:17:16  dcobb
 *    @scr 3911 Feature Enhancement: Markdown
 *    Screen titles. Deprecate old titles.
 *
 *    Revision 1.11  2004/03/03 22:55:40  dcobb
 *    @scr 3911 Feature Enhancement: Markdown
 *    Screen titles.
 *
 *    Revision 1.10  2004/02/27 00:03:09  khassen
 *    @scr 0 Capture Customer Info use-case
 *
 *    Revision 1.9  2004/02/26 22:06:32  lzhao
 *    @scr 3841 Inquiry Options Enhancement.
 *    Add new screens for itiem inventory inquiry.
 *
 *    Revision 1.8  2004/02/26 17:26:40  khassen
 *    @scr 0 Capture Customer Info use-case
 *
 *    Revision 1.7  2004/02/24 20:35:22  blj
 *    @scr 0 add redeem screens.
 *
 *    Revision 1.6  2004/02/22 18:21:57  crain
 *    @scr 3814 Issue Gift Certificate
 *
 *    Revision 1.5  2004/02/19 22:02:37  dcobb
 *    @scr 3870 Feature Enhancement: Damage Discounts
 *
 *    Revision 1.4  2004/02/16 22:42:46  lzhao
 *    @scr 3841:Inquiry Option Enhancement
 *    add advance search and price inquiry screen.
 *
 *    Revision 1.3  2004/02/12 19:36:53  jriggins
 *    @scr 3782 - Added NEW_PASSWORD and REENTER_NEW_PASSWORD identifiers
 *
 *    Revision 1.2  2004/02/12 16:52:11  mcs
 *    Forcing head revision
 *
 *    Revision 1.1.1.1  2004/02/11 01:04:21  cschellenger
 *    updating to pvcs 360store-current
 *
 *    Rev 1.37   Feb 05 2004 14:47:20   cdb
 * Replaced entry screen for employee dicount percent or amount.
 * Resolution for 3588: Discounts/MUPS - Gap Rollback
 *
 *    Rev 1.36   Feb 04 2004 14:16:28   cdb
 * Added employee discount options screen
 * Resolution for 3588: Discounts/MUPS - Gap Rollback
 *
 *    Rev 1.35   Feb 03 2004 14:17:14   cdb
 * Added screen IDs for Markdown options and Discount options.
 * Resolution for 3588: Discounts/MUPS - Gap Rollback
 *
 *    Rev 1.34   Feb 03 2004 14:13:54   DCobb
 * Added new screens for Pickup and Loan.
 * Resolution for 3381: Feature Enhancement:  Till Pickup and Loan
 *
 *    Rev 1.33   Jan 26 2004 16:18:20   jriggins
 * Added EMPLOYEE_FIND_ROLE
 * Resolution for 3597: Employee 7.0 Updates
 *
 *    Rev 1.32   Jan 23 2004 12:37:28   nrao
 * New screen for accepting amount to be applied to Instant Credit account.
 *
 *    Rev 1.31   Dec 22 2003 17:14:44   jriggins
 * Added EMPLOYEE_MASTER_TEMP
 * Resolution for 3597: Employee 7.0 Updates
 *
 *    Rev 1.30   Dec 19 2003 17:39:32   crain
 * Added PO screen
 * Resolution for 3421: Tender redesign
 *
 *    Rev 1.29   Dec 19 2003 13:28:32   baa
 * return enhancements
 * Resolution for 3561: Feature Enhacement: Return Search by Tender
 *
 *    Rev 1.28   Dec 16 2003 13:18:30   crain
 * Added PO screens
 * Resolution for 3421: Tender redesign
 *
 *    Rev 1.27   Dec 16 2003 11:37:52   lzhao
 * rename screen name
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 *
 *    Rev 1.26   Dec 12 2003 14:34:08   jriggins
 * Updates for Add Options usecase in 7.0
 * Resolution for 3597: Employee 7.0 Updates
 *
 *    Rev 1.25   Dec 11 2003 18:47:44   epd
 * new screen definition
 *
 *    Rev 1.24   Dec 11 2003 17:10:36   epd
 * new screen def
 *
 *    Rev 1.23   Dec 10 2003 17:50:38   nrao
 * Added Screen for Redeem Options.
 *
 *    Rev 1.22   07 Dec 2003 20:48:42   baa
 * Add new return screens
 *
 *    Rev 1.21   Dec 04 2003 12:09:04   blj
 * added customer name and id screen for store credit and 3 ids for foreign certificate/credit screens.
 *
 *    Rev 1.20   Dec 04 2003 10:22:06   epd
 * new screen
 *
 *    Rev 1.19   Dec 04 2003 10:18:56   bwf
 * Added mall gift certificate options screen.
 * Resolution for 3538: Mall Certificate Tender
 *
 *    Rev 1.18   Dec 03 2003 15:42:48   crain
 * Added two screens
 * Resolution for 3421: Tender redesign
 *
 *    Rev 1.17   Dec 02 2003 17:31:46   nrao
 * Added screen for House Account Inquiry.
 *
 *    Rev 1.16   Nov 20 2003 15:06:12   crain
 * Added gift certificate screens
 * Resolution for 3421: Tender redesign
 *
 *    Rev 1.15   Nov 11 2003 11:18:32   nrao
 * Added labels for Instant Credit Enter SSN and Instant Credit Inquiry Info screens.
 *
 *    Rev 1.14   Nov 07 2003 16:23:22   bwf
 * Added check screens.
 * Resolution for 3429: Check/ECheck Tender
 *
 *    Rev 1.13   Nov 07 2003 13:50:24   lzhao
 * add GIFT_OPTIONS and GIFT_CARD_OPTIONS
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 *
 *    Rev 1.12   Nov 06 2003 13:54:36   epd
 * added new screen definition
 *
 *    Rev 1.11   Nov 01 2003 15:12:12   epd
 * added screen name
 *
 *    Rev 1.10   Oct 31 2003 16:50:26   epd
 * added screen definition
 *
 *    Rev 1.9   Oct 31 2003 12:46:44   nrao
 * Added lables for Instant Credit Enrollment.
 *
 *    Rev 1.8   Oct 29 2003 18:03:50   lzhao
 * add two screens for gift card reload
 *
 *    Rev 1.7   Oct 22 2003 19:17:32   epd
 * added new screen name
 *
 *    Rev 1.6   Oct 22 2003 12:09:54   epd
 * Updates for Credit/Debit flow
 *
 *    Rev 1.5   Oct 21 2003 17:37:12   sfl
 * Added a new constant for service alert that does not have web store.
 * Resolution for POS SCR-3414: Parameterizing Web Access
 *
 *    Rev 1.4   Oct 20 2003 16:31:18   epd
 * added new screen name
 *
 *    Rev 1.3   Oct 20 2003 15:55:38   rsachdeva
 * Device Status Constants Added
 * Resolution for POS SCR-3411: Feature Enhancement:  Device and Database Status
 *
 *    Rev 1.2   Oct 17 2003 18:52:02   blj
 * added EMPLOYEE_NUMBER screen
 *
 *    Rev 1.1   Sep 29 2003 13:32:04   bwf
 * Added ADMIN_OPTIONS_NO_QUEUE.
 * Resolution for 3334: Feature Enhancement:  Queue Exception Handling
 *
 *    Rev 1.0   Aug 29 2003 16:09:26   CSchellenger
 * Initial revision.
 *
 *    Rev 1.11   Jul 14 2003 12:39:40   baa
 * fix prompt response and screen name for add business screen
 * Resolution for 2256: Add Customer screen has been updated, but its screen text has not
 *
 *    Rev 1.10   May 27 2003 08:49:32   baa
 * rework customer offline flow
 * Resolution for 2387: Deleteing Busn Customer Lock APP- & Inc. Customer.
 *
 *    Rev 1.9   Mar 20 2003 18:18:56   baa
 * customer screens refactoring
 * Resolution for POS SCR-2098: Refactoring of Customer Service Screens
 *
 *    Rev 1.8   Jan 29 2003 11:04:38   mpb
 * SCR #1626
 * Added constants for markdown screens.
 * Resolution for POS SCR-1626: Pricing Feature
 *
 *    Rev 1.7   Dec 13 2002 16:04:58   HDyer
 * Added PERSONALID_ENTRY screen.
 * Resolution for POS-SCR 1854: Return Prompt for ID feature for POS 6.0
 *
 *    Rev 1.6   Nov 01 2002 16:22:44   DCobb
 * Added Mall Gift Certificate.
 * Resolution for POS SCR-1821: POS 6.0 Mall Gift Certificates
 *
 *    Rev 1.5   Sep 19 2002 11:47:16   DCobb
 * Add Purchase Order tender type.
 * Resolution for POS SCR-1799: POS 5.5 Purchase Order Tender Package
 *
 *    Rev 1.4   04 Sep 2002 09:07:04   djefferson
 * added support for Business Customer
 * Resolution for POS SCR-1605: Business Customer
 *
 *    Rev 1.3   Aug 21 2002 11:21:30   DCobb
 * Added Alterations service.
 * Resolution for POS SCR-1753: POS 5.5 Alterations Package
 *
 *    Rev 1.2   Jul 12 2002 16:16:26   RSachdeva
 * Added ITEM_NOT_FOUND_SPEC
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.1   02 May 2002 17:35:56   jbp
 * initial changes for pricing service
 * Resolution for POS SCR-1626: Pricing Feature
 *
 *    Rev 1.0   Apr 29 2002 14:45:16   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:51:50   msg
 * Initial revision.
 *
 *    Rev 1.25   Mar 10 2002 18:01:24   mpm
 * Externalized text in dialog messages.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.24   Mar 09 2002 14:16:50   mpm
 * Text externalization.
 * Resolution for POS SCR-235: Employee clock-in, clock-out
 *
 *    Rev 1.23   Mar 06 2002 07:29:58   mpm
 * Added till status entry.
 * Resolution for POS SCR-235: Employee clock-in, clock-out
 * Resolution for POS SCR-1513: Add Till Status screen
 *
 *    Rev 1.22   25 Jan 2002 09:51:32   KAC
 * Added PARAM_EDIT_LIST_FROM_LIST, PARAM_EDIT_MULTILINE_STRING.
 * Resolution for POS SCR-672: Create List Parameter Editor
 *
 *    Rev 1.21   24 Jan 2002 13:33:56   epd
 * added new constant for new screen
 * Resolution for POS SCR-159: When closing/counting till negative amounts are not accepted.
 *
 *    Rev 1.20   21 Jan 2002 11:07:02   KAC
 * Added PARAM_VALUE_LIST.
 * Resolution for POS SCR-672: Create List Parameter Editor
 *
 *    Rev 1.19   Jan 19 2002 18:37:28   mpm
 * Added register status panel.
 * Resolution for POS SCR-235: Employee clock-in, clock-out
 * Resolution for POS SCR-799: Add register status panel.
 *
 *    Rev 1.18   Jan 19 2002 10:56:28   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-235: Employee clock-in, clock-out
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 *
 *    Rev 1.16   Jan 09 2002 10:21:48   dfh
 * added FILL_ITEM_STATUS
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.15   27 Dec 2001 17:57:40   cir
 * Remove two order search screens
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.14   21 Dec 2001 09:37:54   baa
 * remove offlinescreen for send
 * Resolution for POS SCR-287: Send Transaction
 *
 *    Rev 1.13   Dec 03 2001 17:31:18   dfh
 * added special order screens constants
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.12   03 Dec 2001 17:31:00   baa
 * Updates for send
 * Resolution for POS SCR-287: Send Transaction
 *
 *    Rev 1.11   26 Nov 2001 13:16:44   sfl
 * Added two send related screens SHIPPING_ADDRESS
 * and SHIPPING_METHOD
 * Resolution for POS SCR-287: Send Transaction
 *
 *    Rev 1.10   05 Nov 2001 12:06:14   jbp
 * Added OnlineOffice Screen
 * Resolution for POS SCR-217: Combine CrossReach, POS, and OnlineOffice
 *
 *    Rev 1.9   Oct 29 2001 12:43:42   blj
 * updated with new gift receipt screens.
 * Resolution for POS SCR-237: Gift Receipt Feature
 *
 *    Rev 1.8   28 Oct 2001 13:03:44   mpm
 * Added entry for EMPLOYEE_CLOCK_ENTRY screen.
 *
 *    Rev 1.7   25 Oct 2001 17:43:14   baa
 * cross store inventory feature
 * Resolution for POS SCR-230: Cross Store Inventory
 *
 *    Rev 1.6   23 Oct 2001 15:16:46   pjf
 * Added COMPONENT_OPTIONS.
 * Resolution for POS SCR-8: Item Kits
 *
 *    Rev 1.5   Oct 23 2001 10:48:48   cir
 * Added ENTER_GIFT_CARD_AMOUNT
 * Resolution for POS SCR-224: Open Amount Gift Card
 *
 *    Rev 1.4   19 Oct 2001 11:03:42   baa
 * Add new screens for Customer History
 * Resolution for POS SCR-209: Customer History
 *
 *    Rev 1.3   11 Oct 2001 16:52:20   jbp
 * add proper header
 * Resolution for POS SCR-211: HTML Help Functionality
 *
 *    Rev 1.1   10 Oct 2001 15:24:58   jbp
 * added Customer Info screen
 * Resolution for POS SCR-207: Prompt for Customer Info
 *
 *    Rev 1.0   Sep 21 2001 11:33:34   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:16:02   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui;

import oracle.retail.stores.foundation.manager.gui.ApplicationMode;
import oracle.retail.stores.foundation.manager.gui.UIException;
import oracle.retail.stores.foundation.manager.gui.UIModelIfc;

/**
 * This interface defines the contract for the POSUIManager and any
 * POSUISubsystem. This contract references other documents for details of the
 * screens and the letters that will be sent back.
 * <P>
 * $Revision: /rgbustores_13.4x_generic_branch/9 $
 */
public interface POSUIManagerIfc
{
    // ERROR Message Screens
    /** CANCEL_OK */
    static final public String CANCEL_OK = "CANCEL_OK";

    /** DIALOG_TEMPLATE */
    static final public String DIALOG_TEMPLATE = "DIALOG_TEMPLATE";

    /** VALIDATION_FAILED_ERROR */
    static final public String VALIDATION_FAILED_ERROR = "VALIDATION_FAILED_ERROR";

    // EMPTY LAYOUT SCREENS
    /** TRANSACTION_GIFT_REGISTRY */
    static final public String TRANSACTION_GIFT_REGISTRY = "TRANSACTION_GIFT_REGISTRY";

    /** VOID_TRANSACTION */
    static final public String VOID_TRANSACTION = "VOID_TRANSACTION";

    /** TRANS_OPTIONS */
    static final public String TRANS_OPTIONS = "TRANS_OPTIONS";

    /** TRANSACTION_TAX_OPTIONS */
    static final public String TRANSACTION_TAX_OPTIONS = "TRANSACTION_TAX_OPTIONS";

    /** TRANSACTION_SALES_ASSOCIATE */
    static final public String TRANSACTION_SALES_ASSOCIATE = "TRANSACTION_SALES_ASSOCIATE";

    /** CERTIFICATE_ENTRY */
    static final public String CERTIFICATE_ENTRY = "CERTIFICATE_ENTRY";

    /** MALL_GIFT_TENDER_OPTIONS */
    static final public String MALL_GIFT_TENDER_OPTIONS = "MALL_GIFT_TENDER_OPTIONS";

    /** ITEM_GIFT_REGISTRY */
    static final public String ITEM_GIFT_REGISTRY = "ITEM_GIFT_REGISTRY";

    /** ITEM_SALES_ASSC */
    static final public String ITEM_SALES_ASSC = "ITEM_SALES_ASSC";

    /** ITEM_QUANTITY */
    static final public String ITEM_QUANTITY = "ITEM_QUANTITY";

    /** TRAVELERS_CHECK */
    static final public String TRAVELERS_CHECK = "TRAVELERS_CHECK";

    /** CUSTOMER_OPTIONS */
    static final public String CUSTOMER_OPTIONS = "CUSTOMER_OPTIONS";

    /** FIND_CUSTOMER_BY_TAX_ID */
    static final public String FIND_CUSTOMER_BY_TAX_ID = "FIND_CUSTOMER_BY_TAX_ID";

    /** OFFLINE_CUSTOMER_OPTIONS */
    static final public String OFFLINE_CUSTOMER_OPTIONS = "OFFLINE_CUSTOMER_OPTIONS";

    /** TRANSACTION_CUSTOMER */
    static final public String TRANSACTION_CUSTOMER = "TRANSACTION_CUSTOMER";

    /** ADMIN_OPTIONS */
    static final public String ADMIN_OPTIONS = "ADMIN_OPTIONS";

    /** ABOUT_OPTIONS */
    static final public String ABOUT_OPTIONS = "ABOUT_OPTIONS";

    /** ADMIN_OPTIONS_NO_QUEUE */
    static final public String ADMIN_OPTIONS_NO_QUEUE = "ADMIN_OPTIONS_NO_QUEUE";

    /** RESETTING_HARDTOTALS */
    static final public String RESETTING_HARDTOTALS = "RESETTING_HARDTOTALS";

    /** JOURNAL_DISPLAY */
    static final public String JOURNAL_DISPLAY = "JOURNAL_DISPLAY";

    /** ENTER_TILL_ID */
    static final public String ENTER_TILL_ID = "ENTER_TILL_ID";

    /** UNIT_OF_MEASURE */
    static final public String UNIT_OF_MEASURE = "UNIT_OF_MEASURE";

    /** CUSTOMER_SEARCH_OPTIONS */
    static final public String CUSTOMER_SEARCH_OPTIONS = "CUSTOMER_SEARCH_OPTIONS";

    /** CUSTOMER_FIND_ID */
    static final public String CUSTOMER_FIND_ID = "CUSTOMER_FIND_ID";

    /** FIND_CUSTOMER_EMPLOYEE_ID */
    static final public String FIND_CUSTOMER_EMPLOYEE_ID = "FIND_CUSTOMER_EMPLOYEE_ID";

    /** EMPLOYEE_NUMBER */
    static final public String EMPLOYEE_NUMBER = "EMPLOYEE_NUMBER";

    /** ORDER_OPTIONS */
    static final public String ORDER_OPTIONS = "ORDER_OPTIONS";

    /** ORDER_SEARCH_OPTIONS */
    static final public String ORDER_SEARCH_OPTIONS = "ORDER_SEARCH_OPTIONS";

    /** ORDER_PRINTING */
    static final public String ORDER_PRINTING = "ORDER_PRINTING";

    /** ENTER_ORDER_NUMBER */
    static final public String ENTER_ORDER_NUMBER = "ENTER_ORDER_NUMBER";

    /** QUEUE_OPTIONS */
    static final public String QUEUE_OPTIONS = "QUEUE_OPTIONS";

    /** COUPON_ENTRY */
    static final public String COUPON_ENTRY = "COUPON_ENTRY";
    
    /** COUPON_AMOUNT */
    static final public String COUPON_AMOUNT = "COUPON_AMOUNT";

    /** ENTER_PRICE */
    static final public String ENTER_PRICE = "ENTER_PRICE";

    /** ENTER_ZERO_ALLOWED_PRICE */
    static final public String ENTER_ZERO_ALLOWED_PRICE = "ENTER_ZERO_ALLOWED_PRICE";

    /** ITEM_QUANTITY_UOM */
    static final public String ITEM_QUANTITY_UOM = "ITEM_QUANTITY_UOM";

    /** STORE_NUMBER */
    static final public String STORE_NUMBER = "STORE_NUMBER";

    /** PURCHASE_DATE */
    static final public String PURCHASE_DATE = "PURCHASE_DATE";

    /** STORE_CREDIT_ID_ENTRY */
    static final public String STORE_CREDIT_ID_ENTRY = "STORE_CREDIT_ID_ENTRY";

    /** STORE_CREDIT_INFO */
    static final public String STORE_CREDIT_INFO = "STORE_CREDIT_INFO";

    /** ITEM_SERIAL_INPUT */
    static final public String ITEM_SERIAL_INPUT = "ITEM_SERIAL_INPUT";

    /** INQUIRY_OPTIONS */
    static final public String INQUIRY_OPTIONS = "INQUIRY_OPTIONS";

    /** INVENTORY_SEARCH */
    static final public String INVENTORY_SEARCH = "INVENTORY_SEARCH";

    /** INVENTORY_OPTIONS */
    static final public String INVENTORY_OPTIONS = "INVENTORY_OPTIONS";

    /** INVENTORY_OPTIONS_ITEM_AND_SIZE */
    static final public String INVENTORY_OPTIONS_ITEM_AND_SIZE = "INVENTORY_OPTIONS_ITEM_AND_SIZE";

    /** INVENTORY_MULTI_STORE */
    static final public String INVENTORY_MULTI_STORE = "INVENTORY_MULTI_STORE";

    /** INVENTORY_SPECIFIC_STORE */
    static final public String INVENTORY_SPECIFIC_STORE = "INVENTORY_SPECIFIC_STORE";

    /** ITEM_INVENTORY */
    static final public String ITEM_INVENTORY = "ITEM_INVENTORY";

    /** ITEM_INVENTORY_MULTISTORE_LOCATION */
    static final public String ITEM_INVENTORY_MULTISTORE_LOCATION = "ITEM_INVENTORY_MULTISTORE_LOCATION";

    /** ITEM_INVENTORY_MULTISTORE_NO_LOCATION */
    static final public String ITEM_INVENTORY_MULTISTORE_NO_LOCATION = "ITEM_INVENTORY_MULTISTORE_NO_LOCATION";

    /** ITEM_INVENTORY_STORE_LOCATION */
    static final public String ITEM_INVENTORY_STORE_LOCATION = "ITEM_INVENTORY_STORE_LOCATION";

    /** ITEM_INVENTORY_STORE_NO_LOCATION */
    static final public String ITEM_INVENTORY_STORE_NO_LOCATION = "ITEM_INVENTORY_STORE_NO_LOCATION";

    /** ALTERATION_TYPE */
    static final public String ALTERATION_TYPE = "ALTERATION_TYPE";

    /** PURCHASE_ORDER_NUMBER */
    static final public String PURCHASE_ORDER_NUMBER = "PURCHASE_ORDER_NUMBER";

    /** PURCHASE_ORDER_AMOUNT */
    static final public String PURCHASE_ORDER_AMOUNT = "PURCHASE_ORDER_AMOUNT";

    /** PURCHASE_ORDER_AGENCY_LIST */
    static final public String PURCHASE_ORDER_AGENCY_LIST = "PURCHASE_ORDER_AGENCY_LIST";

    /** PURCHASE_ORDER_AGENCY_NAME */
    static final public String PURCHASE_ORDER_AGENCY_NAME = "PURCHASE_ORDER_AGENCY_NAME";

    /** PURCHASE_ORDER_AGENCY_NAME_360 */
    static final public String PURCHASE_ORDER_AGENCY_NAME_360 = "PURCHASE_ORDER_AGENCY_NAME_360";

    /** KIT_COMPONENTS */
    static final public String KIT_COMPONENTS = "KIT_COMPONENTS";

    // LAYAWAY SCREENS
    /** LAYAWAY_OPTIONS */
    static final public String LAYAWAY_OPTIONS = "LAYAWAY_OPTIONS";

    /** CUSTOMER_LAYAWAY */
    static final public String CUSTOMER_LAYAWAY = "CUSTOMER_LAYAWAY";

    /** FIND_LAYAWAY */
    static final public String FIND_LAYAWAY = "FIND_LAYAWAY";

    /** ENTER_LAYAWAY */
    static final public String ENTER_LAYAWAY = "ENTER_LAYAWAY";

    /** LAYAWAY_ITEM */
    static final public String LAYAWAY_ITEM = "LAYAWAY_ITEM";

    /** REFUND_DETAIL */
    static final public String REFUND_DETAIL = "REFUND_DETAIL";

    /** PAYMENT_DETAIL */
    static final public String PAYMENT_DETAIL = "PAYMENT_DETAIL";

    /** LAYAWAY_LIST */
    static final public String LAYAWAY_LIST = "LAYAWAY_LIST";

    /** LAYAWAY_OFFLINE */
    static final public String LAYAWAY_OFFLINE = "LAYAWAY_OFFLINE";

    // SPECIAL ORDER SCREENS
    /** SPECIAL_ORDER_OPTIONS */
    static final public String SPECIAL_ORDER_OPTIONS = "SPECIAL_ORDER_OPTIONS";

    /** SPECIAL_ORDER_DEPOSIT */
    static final public String SPECIAL_ORDER_DEPOSIT = "SPECIAL_ORDER_DEPOSIT";

    /** SPECIAL_ORDER_SEARCH */
    static final public String SPECIAL_ORDER_SEARCH = "SPECIAL_ORDER_SEARCH";

    /** SELECT_SPECIAL_ORDER */
    static final public String SELECT_SPECIAL_ORDER = "SELECT_SPECIAL_ORDER";

    /** VIEW_SPECIAL_ORDER */
    static final public String VIEW_SPECIAL_ORDER = "VIEW_SPECIAL_ORDER";

    /** CUSTOMER_SPECIAL_ORDER */
    static final public String CUSTOMER_SPECIAL_ORDER = "CUSTOMER_SPECIAL_ORDER";

    // CONFIRMATION LAYOUT SCREENS
    /** CANCEL_CONFIRM */
    static final public String CANCEL_CONFIRM = "CANCEL_CONFIRM";

    // STATUS LAYOUT SCREENS
    /** DEVICE_STATUS */
    static final public String DEVICE_STATUS = "DEVICE_STATUS";
    static final public String DEVICE_STATUS_DIALOG = "DEVICE_STATUS_DIALOG";

    /** REGISTER_STATUS */
    static final public String REGISTER_STATUS = "REGISTER_STATUS";

    /** TILL_STATUS */
    static final public String TILL_STATUS = "TILL_STATUS";

    // HELP SCREENS
    /** HELP_NOT_ENABLED */
    static final public String HELP_NOT_ENABLED = "HELP_NOT_ENABLED";

    /** HELP */
    static final public String HELP = "HELP";

    // CUSTOMER SCREENS
    /** CUSTOMER_INFO */
    static final public String CUSTOMER_INFO = "CUSTOMER_INFO";
    static final public String CUSTOMER_INFO_DIALOG = "CUSTOMER_INFO_DIALOG";

    /** CUSTOMER_LOOKUP */
    static final public String CUSTOMER_LOOKUP = "CUSTOMER_LOOKUP";

    /** CUSTOMER_DELETE */
    static final public String CUSTOMER_DELETE = "CUSTOMER_DELETE";

    /** CUSTOMER_DETAILS */
    static final public String CUSTOMER_DETAILS = "CUSTOMER_DETAILS";

    /** ADD_CUSTOMER */
    static final public String ADD_CUSTOMER = "ADD_CUSTOMER";

    /** CUSTOMER_ADDRESS */
    static final public String CUSTOMER_ADDRESS = "CUSTOMER_ADDRESS";

    /** CUSTOMER_SELECT_ADD */
    static final public String CUSTOMER_SELECT_ADD = "CUSTOMER_SELECT_ADD";

    /** CUSTOMER_SELECT_MODIFY */
    static final public String CUSTOMER_SELECT_MODIFY = "CUSTOMER_SELECT_MODIFY";

    /** CUSTOMER_SELECT_DELETE */
    static final public String CUSTOMER_SELECT_DELETE = "CUSTOMER_SELECT_DELETE";

    /** PROMPT_CUSTOMER_INFO */
    static final public String PROMPT_CUSTOMER_INFO = "PROMPT_CUSTOMER_INFO";

    /** HISTORY_LIST */
    static final public String HISTORY_LIST = "HISTORY_LIST";

    /** HISTORY_DETAIL */
    static final public String HISTORY_DETAIL = "HISTORY_DETAIL";

    /** CUSTOMER_MASTER_FIND */
    static final public String CUSTOMER_MASTER_FIND = "CUSTOMER_MASTER_FIND";

    /** CUSTOMER_ADDRESS_FIND */
    static final public String CUSTOMER_ADDRESS_FIND = "CUSTOMER_ADDRESS_FIND";

    /** CUSTOMER_MASTER */
    static final public String CUSTOMER_MASTER = "CUSTOMER_MASTER";

    /** FIND_CUSTOMER */
    static final public String FIND_CUSTOMER = "FIND_CUSTOMER";

    // FORM LAYOUT SCREENS
    /** POSITIVE_ID */
    static final public String POSITIVE_ID = "POSITIVE_ID";

    /** CREDIT_DEBIT_CARD */
    static final public String CREDIT_DEBIT_CARD = "CREDIT_DEBIT_CARD";

    /** CREDIT_EXP_DATE */
    static final public String CREDIT_EXP_DATE = "CREDIT_EXP_DATE";

    /** DEBIT_CARD */
    static final public String DEBIT_CARD = "DEBIT_CARD";

    /** CONVERT_DEBIT_TO_CREDIT */
    static final public String CONVERT_DEBIT_TO_CREDIT = "CONVERT_DEBIT_TO_CREDIT";

    /** REQUEST_CUSTOMER_VERIFY */
    static final public String REQUEST_CUSTOMER_VERIFY = "REQUEST_CUSTOMER_VERIFY";

    /** CHECK_ENTRY */
    static final public String CHECK_ENTRY = "CHECK_ENTRY";

    /** ENTER_ID */
    static final public String ENTER_ID = "ENTER_ID";

    /** ENTER_ID_NUMBER_SWIPE */
    static final public String ENTER_ID_NUMBER_SWIPE = "ENTER_ID_NUMBER_SWIPE";

    /** ENTER_ID_NUMBER */
    static final public String ENTER_ID_NUMBER = "ENTER_ID_NUMBER";

    /** ENTER_PHONE */
    static final public String ENTER_PHONE = "ENTER_PHONE";

    /** ENTER_CHECK_NUMBER */
    static final public String ENTER_CHECK_NUMBER = "ENTER_CHECK_NUMBER";

    /** ENTER_STATE */
    static final public String ENTER_STATE = "ENTER_STATE";

    /** ENTER_STATE */
    static final public String ENTER_COUNTRY = "ENTER_COUNTRY";

    /** ITEM_DISC_PCNT */
    static final public String ITEM_DISC_PCNT = "ITEM_DISC_PCNT";

    /** ITEM_DISC_AMT */
    static final public String ITEM_DISC_AMT = "ITEM_DISC_AMT";

    /** MARKDOWN_PERCENT */
    static final public String MARKDOWN_PERCENT = "MARKDOWN_PERCENT";

    /** MARKDOWN_AMOUNT */
    static final public String MARKDOWN_AMOUNT = "MARKDOWN_AMOUNT";

    /** ITEM_TAX_OVERRIDE_RATE */
    static final public String ITEM_TAX_OVERRIDE_RATE = "ITEM_TAX_OVERRIDE_RATE";

    /** TRANSACTION_TAX_OVERRIDE_RATE */
    static final public String TRANSACTION_TAX_OVERRIDE_RATE = "TRANSACTION_TAX_OVERRIDE_RATE";

    /** TRANSACTION_TAX_EXEMPT */
    static final public String TRANSACTION_TAX_EXEMPT = "TRANSACTION_TAX_EXEMPT";

    /** ITEM_TAX_ON_OFF */
    static final public String ITEM_TAX_ON_OFF = "ITEM_TAX_ON_OFF";

    /** SELECT_NO_SALE_REASON_CODE */
    static final public String SELECT_NO_SALE_REASON_CODE = "SELECT_NO_SALE_REASON_CODE";

    /** ITEM_NOT_FOUND */
    static final public String ITEM_NOT_FOUND = "ITEM_NOT_FOUND";

    /** ITEM_NOT_FOUND_PRICE_CODE */
    static final public String ITEM_NOT_FOUND_PRICE_CODE = "ITEM_NOT_FOUND_PRICE_CODE";

    /** PRICE_OVERRIDE */
    static final public String PRICE_OVERRIDE = "PRICE_OVERRIDE";

    /** PRICE_OVERRIDE_NOREASON */
    static final public String PRICE_OVERRIDE_NOREASON = "PRICE_OVERRIDE_NOREASON";

    /** UNKNOWN_ITEM */
    static final public String UNKNOWN_ITEM = "UNKNOWN_ITEM";

    /** TRANS_DISC_PCNT */
    static final public String TRANS_DISC_PCNT = "TRANS_DISC_PCNT";

    /** TRANS_DISC_AMT */
    static final public String TRANS_DISC_AMT = "TRANS_DISC_AMT";

    /** VOID_CONFIRM */
    static final public String VOID_CONFIRM = "VOID_CONFIRM";

    /** ITEM_TAX_OVERRIDE_AMOUNT */
    static final public String ITEM_TAX_OVERRIDE_AMOUNT = "ITEM_TAX_OVERRIDE_AMOUNT";

    /** TRANSACTION_TAX_OVERRIDE_AMOUNT */
    static final public String TRANSACTION_TAX_OVERRIDE_AMOUNT = "TRANSACTION_TAX_OVERRIDE_AMOUNT";

    /** FOREIGN_COUNTRY */
    static final public String FOREIGN_COUNTRY = "FOREIGN_COUNTRY";

    /** FOREIGN_CURRENCY */
    static final public String FOREIGN_CURRENCY = "FOREIGN_CURRENCY";

    /** MAIL_BANK_CHECK_INFO */
    static final public String MAIL_BANK_CHECK_INFO = "MAIL_BANK_CHECK_INFO";

    /** CREDIT_REFERRAL */
    static final public String CREDIT_REFERRAL = "CREDIT_REFERRAL";

    /** CHECK_REFERRAL */
    static final public String CHECK_REFERRAL = "CHECK_REFERRAL";

    /** GIFT CARD REFERRAL */
    static final public String GIFT_CARD_REFERRAL = "GIFT_CARD_REFERRAL";

    /** FIND_TRANSACTION */
    static final public String FIND_TRANSACTION = "FIND_TRANSACTION";

    /** REPORT_OPTIONS */
    static final public String REPORT_OPTIONS = "REPORT_OPTIONS";

    /** CR_REPORT_OPTIONS */
    static final public String CR_REPORT_OPTIONS = "CR_REPORT_OPTIONS";

    /** SUMMARY_REPORT */
    static final public String SUMMARY_REPORT = "SUMMARY_REPORT";

    /** DATE_RANGE_REPORT */
    static final public String DATE_RANGE_REPORT = "DATE_RANGE_REPORT";

    /** REPORT_PRINTING */
    static final public String REPORT_PRINTING = "REPORT_PRINTING";

    /** PRINT_REPORT */
    static final public String PRINT_REPORT = "PRINT_REPORT";

    /** PRINT_PREVIEW */
    static final public String PRINT_PREVIEW = "PRINT_PREVIEW";

    /** MANAGER_OPTIONS */
    static final public String MANAGER_OPTIONS = "MANAGER_OPTIONS";

    /** FIND_CUSTOMER_INFO */
    static final public String FIND_CUSTOMER_INFO = "FIND_CUSTOMER_INFO";

    /** ELECTRONIC_JOURNAL_SEARCH_CRITERIA */
    static final public String ELECTRONIC_JOURNAL_SEARCH_CRITERIA = "ELECTRONIC_JOURNAL_SEARCH_CRITERIA";

    /** ELECTRONIC_JOURNAL_OPTIONS */
    static final public String ELECTRONIC_JOURNAL_OPTIONS = "ELECTRONIC_JOURNAL_OPTIONS";

    /** ELECTRONIC_JOURNAL_FIND_RECEIPT */
    static final public String ELECTRONIC_JOURNAL_FIND_RECEIPT = "ELECTRONIC_JOURNAL_FIND_RECEIPT";

    /** STATUS_SEARCH */
    static final public String STATUS_SEARCH = "STATUS_SEARCH";

    /** NARROW_SEARCH */
    static final public String NARROW_SEARCH = "NARROW_SEARCH";

    /** ORDER_REPORT */
    static final public String ORDER_REPORT = "ORDER_REPORT";

    /** ORDER_SUM_RPT */
    static final public String ORDER_SUM_RPT = "ORDER_SUM_RPT";

    /** PRINT_STANDBY */
    static final public String PRINT_STANDBY = "PRINT_STANDBY";

    /** SELECT_SUSPEND_REASON_CODE */
    static final public String SELECT_SUSPEND_REASON_CODE = "SELECT_SUSPEND_REASON_CODE";

    /** NON_MERCHANDISE */
    static final public String NON_MERCHANDISE = "NON_MERCHANDISE";

    /** GIFT_OPTIONS */
    static final public String GIFT_OPTIONS = "GIFT_OPTIONS";

    /** SELL_GIFT_CARD */
    static final public String SELL_GIFT_CARD = "SELL_GIFT_CARD";

    /** GIFT_CARD */
    static final public String GIFT_CARD = "GIFT_CARD";

    /** ENTER_GIFT_CARD_AMOUNT */
    static final public String ENTER_GIFT_CARD_AMOUNT = "ENTER_GIFT_CARD_AMOUNT";

    /** GIFT_CARD_INQUIRY */
    static final public String GIFT_CARD_INQUIRY = "GIFT_CARD_INQUIRY";

    /** GIFT_CARD_OPTIONS */
    static final public String GIFT_CARD_OPTIONS = "GIFT_CARD_OPTIONS";

    /** TILL_FUNCTIONS */
    static final public String TILL_FUNCTIONS = "TILL_FUNCTIONS";

    /** PANTS_ALTERATION */
    static final public String PANTS_ALTERATION = "PANTS_ALTERATION";

    /** SHIRT_ALTERATION */
    static final public String SHIRT_ALTERATION = "SHIRT_ALTERATION";

    /** COAT_ALTERATION */
    static final public String COAT_ALTERATION = "COAT_ALTERATION";

    /** SKIRT_ALTERATION */
    static final public String SKIRT_ALTERATION = "SKIRT_ALTERATION";

    /** DRESS_ALTERATION */
    static final public String DRESS_ALTERATION = "DRESS_ALTERATION";

    /** REPAIRS_ALTERATION */
    static final public String REPAIRS_ALTERATION = "REPAIRS_ALTERATION";

    /** ADD_BUSINESS */
    static final public String ADD_BUSINESS = "ADD_BUSINESS";

    /** BUSINESS_CUSTOMER */
    static final public String BUSINESS_CUSTOMER = "BUSINESS_CUSTOMER";

    /** BUSINESS_SEARCH */
    static final public String BUSINESS_SEARCH = "BUSINESS_SEARCH";

    /** DELETE_BUSINESS */
    static final public String DELETE_BUSINESS = "DELETE_BUSINESS";

    /** PERSONALID_ENTRY */
    static final public String PERSONALID_ENTRY = "PERSONALID_ENTRY";

    // Instant Credit
    /** INSTANT_CREDIT_OPTIONS */
    static final public String INSTANT_CREDIT_OPTIONS = "INSTANT_CREDIT_OPTIONS";

    /** ENTER_CREDIT_INFO */
    static final public String ENTER_CREDIT_INFO = "ENTER_CREDIT_INFO";

    /** INSTANT_CREDIT_CUSTOMER_INFO */
    static final public String INSTANT_CREDIT_CUSTOMER_INFO = "INSTANT_CREDIT_CUSTOMER_INFO";

    /** INSTANT_CREDIT_CALL_CENTER */
    static final public String INSTANT_CREDIT_CALL_CENTER = "INSTANT_CREDIT_CALL_CENTER";

    /** INSTANT_CREDIT_ITEM_SALES_ASSC */
    static final public String INSTANT_CREDIT_ITEM_SALES_ASSC = "INSTANT_CREDIT_ITEM_SALES_ASSC";

    /** ENROLL_RESPONSE */
    static final public String ENROLL_RESPONSE = "ENROLL_RESPONSE";

    /** INSTANT_CREDIT_INQUIRY_INFO */
    static final public String INSTANT_CREDIT_INQUIRY_INFO = "INSTANT_CREDIT_INQUIRY_INFO";

    /** INSTANT_CREDIT_INQUIRY_CRITERIA */
    static final public String INSTANT_CREDIT_INQUIRY_CRITERIA = "INSTANT_CREDIT_INQUIRY_CRITERIA";

    /** ENTER_AMOUNT */
    static final public String ENTER_AMOUNT = "ENTER_AMOUNT";

    // REDEEM
    /** REDEEM_OPTIONS */
    static final public String REDEEM_OPTIONS = "REDEEM_OPTIONS";

    /** GIFT_CARD_REDEEM INQUIRY */
    static final public String GIFT_CARD_REDEEM_INQUIRY = "GIFT_CARD_REDEEM_INQUIRY";

    /** GIFT_CARD_REDEEM */
    static final public String GIFT_CARD_REDEEM = "GIFT_CARD_REDEEM";

    /** REDEEM_NUMBER */
    static final public String REDEEM_NUMBER = "REDEEM_NUMBER";

    /** REDEEM_AMOUNT */
    static final public String REDEEM_AMOUNT = "REDEEM_AMOUNT";

    /** DISCOUNTED_AMOUNT */
    static final public String DISCOUNTED_AMOUNT = "DISCOUNTED_AMOUNT";

    /** REDEEM_REFUND_OPTIONS */
    static final public String REDEEM_REFUND_OPTIONS = "REDEEM_REFUND_OPTIONS";

    /** REDEEM_FOREIGN_NUMBER */
    static final public String REDEEM_FOREIGN_NUMBER = "REDEEM_FOREIGN_NUMBER";

    // ITEM LAYOUT SCREENS
    /** SELL_ITEM */
    static final public String SELL_ITEM = "SELL_ITEM";
    

    /**
     * SELECT ITEM - used when one id_itm_pos resolves to multiple id_itm
     * objects.
     */
    static final public String SELECT_ITEM = "SELECT_ITEM";

    /** ENTER DOB */
    static final public String ENTER_DOB = "ENTER_DOB";

    /** ENTER DOB NO SKIP */
    static final public String ENTER_DOB_NO_SKIP = "ENTER_DOB_NO_SKIP";

    // ITEM OPTION LAYOUT SCREENS
    /** ITEM_OPTIONS */
    static final public String ITEM_OPTIONS = "ITEM_OPTIONS";

    /** ITEM_TAX_OPTIONS */
    static final public String ITEM_TAX_OPTIONS = "ITEM_TAX_OPTIONS";

    /** ITEM_TAX_OVERRIDE_OPTIONS */
    static final public String ITEM_TAX_OVERRIDE_OPTIONS = "ITEM_TAX_OVERRIDE_OPTIONS";

    /** ITEM_INFO */
    static final public String ITEM_INFO = "ITEM_INFO";

    /** PRICE_INQUIRY */
    static final public String PRICE_INQUIRY = "PRICE_INQUIRY";

    /** ITEMS_LIST */
    static final public String ITEMS_LIST = "ITEMS_LIST";

    /** COMPONENT_OPTIONS */
    static final public String COMPONENT_OPTIONS = "COMPONENT_OPTIONS";

    /** ADV_SEARCH */
    static final public String ADV_SEARCH = "ADV_SEARCH";

    /** ITEM_LOCATION for Sale */
    static final public String ITEM_LOCATION = "ITEM_LOCATION";

    /** ITEM_LOCATION when deleting a Layaway */
    static final public String ITEM_LOCATION_LAYAWAY_DELETE = "ITEM_LOCATION_LAYAWAY_DELETE";

    /** ITEM_LOCATION when canceling a (Special or Web) Order */
    static final public String ITEM_LOCATION_ORDER_CANCEL = "ITEM_LOCATION_ORDER_CANCEL";

    // LOGO LAYOUT SCREENS
    /** MAIN_OPTIONS */
    static final public String MAIN_OPTIONS = "MAIN_OPTIONS";

    /** IDENTIFICATION */
    static final public String IDENTIFICATION = "IDENTIFICATION";

    /** PASSWORD */
    static final public String PASSWORD = "PASSWORD";

    /** LOGIN */
    static final public String LOGIN = "LOGIN";

    /** SALES_ASSOCIATE_IDENTIFICATION */
    static final public String SALES_ASSOCIATE_IDENTIFICATION = "SALES_ASSOCIATE_IDENTIFICATION";

    /** OPERATOR_IDENTIFICATION */
    static final public String OPERATOR_IDENTIFICATION = "OPERATOR_IDENTIFICATION";

    /** LOGIN */
    static final public String OPERATOR_LOGIN = "OPERATOR_LOGIN";

    /** OPERATOR_PASSWORD */
    static final public String OPERATOR_PASSWORD = "OPERATOR_PASSWORD";

    /** CHANGE_PASSWORD */
    static final public String CHANGE_PASSWORD = "CHANGE_PASSWORD";

    /** NEW_PASSWORD */
    static final public String NEW_PASSWORD = "NEW_PASSWORD";

    /** REENTER_NEW_PASSWORD */
    static final public String REENTER_NEW_PASSWORD = "REENTER_NEW_PASSWORD";

    /** CASHIER_IDENTIFICATION */
    static final public String CASHIER_IDENTIFICATION = "CASHIER_IDENTIFICATION";

    /** CASHIER_LOGOFF */
    static final public String CASHIER_LOGOFF = "CASHIER_LOGOFF";

    /** CROSS_REACH_MAIN_OPTIONS */
    static final public String CROSS_REACH_MAIN_OPTIONS = "CROSS_REACH_MAIN_OPTIONS";

    // TENDER LAYOUT SCREENS
    /** ISSUE_CHANGE */
    static final public String ISSUE_CHANGE = "ISSUE_CHANGE";

    /** ISSUE_REFUND */
    static final public String ISSUE_REFUND = "ISSUE_REFUND";

    /** MBC_COMPLETE */
    static final public String MBC_COMPLETE = "MBC_COMPLETE";

    /** REFUND_OPTIONS */
    static final public String REFUND_OPTIONS = "REFUND_OPTIONS";

    /** CHANGE_DUE_OPTIONS */
    static final public String CHANGE_DUE_OPTIONS = "CHANGE_DUE_OPTIONS";

    /** CLOSE_DRAWER */
    static final public String CLOSE_DRAWER = "CLOSE_DRAWER";

    /** NOSALE_COMPLETE */
    static final public String NOSALE_COMPLETE = "NOSALE_COMPLETE";

    /** TENDER_OPTIONS */
    static final public String TENDER_OPTIONS = "TENDER_OPTIONS";

    /** TENDER_OPTIONS */
    static final public String TENDER_OPTIONS_CPOI = "TENDER_OPTIONS_CPOI";

    /** OPEN_DRAWER */
    static final public String OPEN_DRAWER = "OPEN_DRAWER";

    /** VOID_COMPLETE */
    static final public String VOID_COMPLETE = "VOID_COMPLETE";

    /** VOID_REFUND */
    static final public String VOID_REFUND = "VOID_REFUND";

    /** PRESS_ENTER */
    static final public String PRESS_ENTER = "PRESS_ENTER";

    /** AUTHORIZATION */
    static final public String AUTHORIZATION = "AUTHORIZATION";

    /** PIN_ENTRY */
    static final public String PIN_ENTRY = "PIN_ENTRY";

    /** PIN_ENTRY_WITH_CREDIT */
    static final public String PIN_ENTRY_WITH_CREDIT = "PIN_ENTRY_WITH_CREDIT";

    /** SIGNATURE_CAPTURE */
    static final public String SIGNATURE_CAPTURE = "SIGNATURE_CAPTURE";

    /** ALT_CURRENCY */
    static final public String ALT_CURRENCY = "ALT_CURRENCY";

    /** ENTER_ID_INFO */
    static final public String ENTER_ID_INFO = "ENTER_ID_INFO";

    /** ENTER_ID_INFO_AUTH */
    static final public String ENTER_ID_INFO_AUTH = "ENTER_ID_INFO_AUTH";

    /** CALL_OCC */
    static final public String CALL_OCC = "CALL_OCC";

    /** ISSUING_STORE_NUMBER */
    static final public String ISSUING_STORE_NUMBER = "ISSUING_STORE_NUMBER";

    /** ISSUE_DATE */
    static final public String ISSUE_DATE = "ISSUE_DATE";

    /** CAPTURE_CUSTOMER_INFO_BANK_CHECK */
    static final public String CAPTURE_CUSTOMER_INFO_BANK_CHECK = "CAPTURE_CUSTOMER_INFO_BANK_CHECK";

    /** CAPTURE_CUSTOMER_INFO_DEFAULT */
    static final public String CAPTURE_CUSTOMER_INFO_DEFAULT = "CAPTURE_CUSTOMER_INFO_DEFAULT";

    /** CAPTURE_CUSTOMER_INFO_SEND */
    static final public String CAPTURE_CUSTOMER_INFO_SEND = "CAPTURE_CUSTOMER_INFO_SEND";

    /** MULTIPLE_GEO_CODES for a postal codes in an item send */
    static final public String MULTIPLE_GEO_CODES = "MULTIPLE_GEO_CODES";

    /** CAPTURE_IRS_CUSTOMER */
    static final public String CAPTURE_IRS_CUSTOMER = "CAPTURE_IRS_CUSTOMER";

    // PRICING SCREENS
    /** PRICING_OPTIONS */
    static final public String PRICING_OPTIONS = "PRICING_OPTIONS";

    /** MARKDOWN_OPTIONS */
    static final public String MARKDOWN_OPTIONS = "MARKDOWN_OPTIONS";

    /** DISCOUNT_OPTIONS */
    static final public String DISCOUNT_OPTIONS = "DISCOUNT_OPTIONS";

    /** RETURN_TRANS_DISCOUNT_OPTIONS */
    static final public String RETURN_TRANS_DISCOUNT_OPTIONS = "RETURN_TRANS_DISCOUNT_OPTIONS";

    /** EMPLOYEE_DISCOUNT_OPTIONS */
    static final public String EMPLOYEE_DISCOUNT_OPTIONS = "EMPLOYEE_DISCOUNT_OPTIONS";

    /** RETURN_TRANS_EMPLOYEE_DISCOUNT_OPTIONS */
    static final public String RETURN_TRANS_EMPLOYEE_DISCOUNT_OPTIONS = "RETURN_TRANS_EMPLOYEE_DISCOUNT_OPTIONS";

    /** ENTER_EMPLOYEE_AMOUNT_DISCOUNT */
    static final public String ENTER_EMPLOYEE_AMOUNT_DISCOUNT = "ENTER_EMPLOYEE_AMOUNT_DISCOUNT";

    /** ENTER_EMPLOYEE_PERCENT_DISCOUNT */
    static final public String ENTER_EMPLOYEE_PERCENT_DISCOUNT = "ENTER_EMPLOYEE_PERCENT_DISCOUNT";

    /** DAMAGE_DISCOUNT_OPTIONS */
    static final public String DAMAGE_DISCOUNT_OPTIONS = "DAMAGE_DISCOUNT_OPTIONS";

    /** DAMAGE_AMOUNT */
    static final public String DAMAGE_AMOUNT = "DAMAGE_AMOUNT";

    /** DAMAGE_PERCENT */
    static final public String DAMAGE_PERCENT = "DAMAGE_PERCENT";

    // PRINTING SCREENS
    /** INSERT_TENDER */
    static final public String INSERT_TENDER = "INSERT_TENDER";

    /** REMOVE_TENDER */
    static final public String REMOVE_TENDER = "REMOVE_TENDER";

    // DAILY OPERATIONS SCREENS
    /** DAILY_OPS_OPTIONS */
    static final public String DAILY_OPS_OPTIONS = "DAILY_OPS_OPTIONS";

    /** REGISTER_OPTIONS */
    static final public String REGISTER_OPTIONS = "REGISTER_OPTIONS";

    /** OTHER_TENDER_DETAIL */
    static final public String OTHER_TENDER_DETAIL = "OTHER_TENDER_DETAIL";

    /** CURRENCY_DETAIL */
    static final public String CURRENCY_DETAIL = "CURRENCY_DETAIL";

    /** CURRENCY_DETAIL_PICKUP */
    static final public String CURRENCY_DETAIL_PICKUP = "CURRENCY_DETAIL_PICKUP";

    /** CURRENCY_DETAIL_LOAN */
    static final public String CURRENCY_DETAIL_LOAN = "CURRENCY_DETAIL_LOAN";

    /** SUMMARY_COUNT */
    static final public String SUMMARY_COUNT = "SUMMARY_COUNT";

    /** SUMMARY_COUNT_PICKUP */
    static final public String SUMMARY_COUNT_PICKUP = "SUMMARY_COUNT_PICKUP";

    /** SUMMARY_COUNT_LOAN */
    static final public String SUMMARY_COUNT_LOAN = "SUMMARY_COUNT_LOAN";

    /** SUMMARY_NEGATIVE_COUNT */
    static final public String SUMMARY_NEGATIVE_COUNT = "SUMMARY_NEGATIVE_COUNT";

    /** DISCREPANCY_CONFIRM */
    static final public String DISCREPANCY_CONFIRM = "DISCREPANCY_CONFIRM";

    /** STORE_CLOSED_REG_ERROR */
    static final public String STORE_CLOSED_REG_ERROR = "STORE_CLOSED_REG_ERROR";

    /** STORE_STATUS_INQUIRY_ERROR */
    static final public String STORE_STATUS_INQUIRY_ERROR = "STORE_STATUS_INQUIRY_ERROR";

    /** STORE_STATUS_NOT_FOUND */
    static final public String STORE_STATUS_NOT_FOUND = "STORE_STATUS_NOT_FOUND";

    /** REGISTER_OFFLINE */
    static final public String REGISTER_OFFLINE = "REGISTER_OFFLINE";

    /** REGISTER_INQUIRY_ERROR */
    static final public String REGISTER_INQUIRY_ERROR = "REGISTER_INQUIRY_ERROR";

    /** REGISTER_NOT_FOUND */
    static final public String REGISTER_NOT_FOUND = "REGISTER_NOT_FOUND";

    /** ENTER_BUSINESS_DATE */
    static final public String ENTER_BUSINESS_DATE = "ENTER_BUSINESS_DATE";

    /** SELECT_BUSINESS_DATE */
    static final public String SELECT_BUSINESS_DATE = "SELECT_BUSINESS_DATE";

    /** TILL_OPTIONS */
    static final public String TILL_OPTIONS = "TILL_OPTIONS";

    /** SELECT_TENDER_TO_COUNT */
    static final public String SELECT_TENDER_TO_COUNT = "SELECT_TENDER_TO_COUNT";

    /** STORE_FINANCIAL_TOTALS_SUMMARY */
    static final public String STORE_FINANCIAL_TOTALS_SUMMARY = "STORE_FINANCIAL_TOTALS_SUMMARY";

    /** SELECT_CHARGE_TO_COUNT */
    static final public String SELECT_CHARGE_TO_COUNT = "SELECT_CHARGE_TO_COUNT";

    /** FOREIGN_CURRENCY_COUNT **/
    static final public String FOREIGN_CURRENCY_COUNT = "FOREIGN_CURRENCY_COUNT";

    /** SELECT_FOREIGN_TENDER_TO_COUNT */
    static final public String SELECT_FOREIGN_TENDER_TO_COUNT = "SELECT_FOREIGN_TENDER_TO_COUNT";

    /** SELECT_CURRENCY_TYPE */
    static final public String SELECT_CURRENCY_TYPE = "SELECT_CURRENCY_TYPE";

    /** PAY_IN */
    static final public String PAY_IN = "PAY_IN";

    /** PAY_OUT */
    static final public String PAY_OUT = "PAY_OUT";

    /** PAYROLL_PAY_OUT */
    static final public String PAYROLL_PAY_OUT = "PAYROLL_PAY_OUT";

    // EMPLOYEE SCREENS
    /** EMPLOYEE_OPTIONS */
    static final public String EMPLOYEE_OPTIONS = "EMPLOYEE_OPTIONS";

    /** EMPLOYEE_FIND */
    static final public String EMPLOYEE_FIND = "EMPLOYEE_FIND";

    /** EMPLOYEE_SELECT_MODIFY */
    static final public String EMPLOYEE_SELECT_MODIFY = "EMPLOYEE_SELECT_MODIFY";

    /** EMPLOYEE_SELECT_ADD */
    static final public String EMPLOYEE_SELECT_ADD = "EMPLOYEE_SELECT_ADD";

    /** EMPLOYEE_MASTER */
    static final public String EMPLOYEE_MASTER = "EMPLOYEE_MASTER";

    /** EMPLOYEE_MASTER_TEMP */
    static final public String EMPLOYEE_MASTER_TEMP = "TEMPORARY_EMPLOYEE_MASTER";

    /** EMPLOYEE_FIND_ID */
    static final public String EMPLOYEE_FIND_ID = "EMPLOYEE_FIND_ID";

    /** EMPLOYEE_FIND_NAME */
    static final public String EMPLOYEE_FIND_NAME = "EMPLOYEE_FIND_NAME";

    /** EMPLOYEE_FIND_ROLE */
    static final public String EMPLOYEE_FIND_ROLE = "EMPLOYEE_FIND_ROLE";

    /** EMPLOYEE_SEARCH_OPTIONS */
    static final public String EMPLOYEE_SEARCH_OPTIONS = "EMPLOYEE_SEARCH_OPTIONS";

    /** EMPLOYEE_CLOCK_ENTRY */
    static final public String EMPLOYEE_CLOCK_ENTRY = "EMPLOYEE_CLOCK_ENTRY";

    /** EMPLOYEE_ADD_OPTIONS */
    static final public String EMPLOYEE_ADD_OPTIONS = "EMPLOYEE_ADD_OPTIONS";

    /** EMPLOYEE_SET_FINGERPRINT */
    static final public String EMPLOYEE_ENROLL_FINGERPRINT = "EMPLOYEE_ENROLL_FINGERPRINT";

     /** EMPLOYEE_VERIFY_FINGERPRINT */
    static final public String EMPLOYEE_VERIFY_FINGERPRINT = "EMPLOYEE_VERIFY_FINGERPRINT";

    // SECURITY SCREENS
    /** SECURITY_OPTIONS */
    static final public String SECURITY_OPTIONS = "SECURITY_OPTIONS";

    // ROLE SCREENS
    /** ROLE_OPTIONS */
    static final public String ROLE_OPTIONS = "ROLE_OPTIONS";

    /** ADD_ROLE */
    static final public String ADD_ROLE = "ADD_ROLE";

    /** FIND_ROLE */
    static final public String FIND_ROLE = "FIND_ROLE";

    /** EDIT_ROLE */
    static final public String EDIT_ROLE = "EDIT_ROLE";

    // TRANSACTION SELECTION LAYOUT
    /** SET_ACCESS */
    static final public String SET_ACCESS = "SET_ACCESS";

    /** SUSPEND_LIST */
    static final public String SUSPEND_LIST = "SUSPEND_LIST";

    /** PROMPT_RETRIEVE_TRANSACTION */
    static final public String PROMPT_RETRIEVE_TRANSACTION = "PROMPT_RETRIEVE_TRANSACTION";

    // RETURN SCREENS
    /** RETURN_OPTIONS */
    static final public String RETURN_OPTIONS = "RETURN_OPTIONS";

    /** RETURNS MANANGEMENT RETURN OPTIONS */
    static final public String RM_RETURN_OPTIONS = "RM_RETURN_OPTIONS";

    /** RETURN_NO_RECEIPT */
    /** @deprecated in 13.3 */
    static final public String RETURN_NO_RECEIPT = "RETURN_NO_RECEIPT";

    /** RETURN_TRANSACTION_SEARCH */
    static final public String RETURN_TRANSACTION_SEARCH = "RETURN_TRANSACTION_SEARCH";

    /** RETURN_RECEIPT */
    static final public String RETURN_RECEIPT = "RETURN_RECEIPT";

    /** RETURN_RECEIPT_NO_TRANS_DATE */
    static final public String RETURN_RECEIPT_NO_TRANS_DATE = "RETURN_RECEIPT_NO_TRANS_DATE";

    /** RETURN_BY_CHECK */
    static final public String RETURN_BY_CHECK = "RETURN_BY_CHECK";

    /** RETURN_BY_CREDIT */
    static final public String RETURN_BY_CREDIT = "RETURN_BY_CREDIT";

    /** RETURN_BY_CREDIT */
    static final public String COMPLETE_RETURN_BY_CREDIT = "COMPLETE_RETURN_BY_CREDIT";

    /** Return By Unique Identification Number */
    static final public String RETURN_BY_UIN = "RETURN_BY_UIN";

    /** RETURN_BY_GIFTCARD */
    static final public String RETURN_BY_GIFTCARD = "RETURN_BY_GIFTCARD";

    /** RETURN_ENTER_ITEM */
    static final public String RETURN_ENTER_ITEM = "RETURN_ENTER_ITEM";

    /** ITEM_SIZE */
    static final public String ITEM_SIZE = "ITEM_SIZE";

    /** RETRIEVE_TRANSACTION */
    static final public String RETRIEVE_TRANSACTION = "RETRIEVE_TRANSACTION";

    /** RETURN_LINKED_TRANS */
    static final public String RETURN_LINKED_TRANS = "RETURN_LINKED_TRANS";

    /** RETURN_SELECT_ITEM */
    static final public String RETURN_SELECT_ITEM = "RETURN_SELECT_ITEM";

    /** RETURN_ITEM_NO_RECEIPT */
    static final public String RETURN_ITEM_NO_RECEIPT = "RETURN_ITEM_NO_RECEIPT";

    /** RETURN_ITEM_NON_RETRIEVED */
    static final public String RETURN_ITEM_NON_RETRIEVED = "RETURN_ITEM_NON_RETRIEVED";

    /** RETURN_TRANSACTION_DETAILS */
    static final public String RETURN_TRANSACTION_DETAILS = "RETURN_TRANSACTION_DETAILS";

    /** RETURN_ITEM_INFO */
    static final public String RETURN_ITEM_INFO = "RETURN_ITEM_INFO";

    /** RETURN_ITEM */
    static final public String RETURN_ITEM = "RETURN_ITEM";

    /** RETURN_RESPONSE_ITEM for RM */
    static final public String RETURN_RESPONSE_ITEM = "RETURN_RESPONSE_ITEM";

    // HOUSE ACCOUNT PAYMENT SCREENS
    /** HOUSE_ACCOUNT */
    static final public String HOUSE_ACCOUNT = "HOUSE_ACCOUNT";

    /** ACCOUNT_INFORMATION */
    static final public String ACCOUNT_INFORMATION = "ACCOUNT_INFORMATION";

    /** INSTANT_CREDIT_PAYMENT_ACCOUNT */
    static final public String INSTANT_CREDIT_PAYMENT_ACCOUNT = "INSTANT_CREDIT_PAYMENT_ACCOUNT";

    // reprint receipt screens
    /** REPRINT_RECEIPT_OPTIONS */
    static final public String REPRINT_RECEIPT_OPTIONS = "REPRINT_RECEIPT_OPTIONS";

    /** REPRINT_RECEIPT_TRANSACTION_SEARCH */
    static final public String REPRINT_RECEIPT_TRANSACTION_SEARCH = "REPRINT_RECEIPT_TRANSACTION_SEARCH";

    /** REPRINT_SELECT */
    static final public String REPRINT_SELECT = "REPRINT_SELECT";

    // PARAMETER SCREENS
    /** PARAM_SELECT_LEVEL */
    static final public String PARAM_SELECT_LEVEL = "PARAM_SELECT_LEVEL";

    /** PARAM_SELECT_STORE */
    static final public String PARAM_SELECT_STORE = "PARAM_SELECT_STORE";

    /** PARAM_SELECT_GROUP */
    static final public String PARAM_SELECT_GROUP = "PARAM_SELECT_GROUP";

    /** PARAM_SELECT_PARAMETER */
    static final public String PARAM_SELECT_PARAMETER = "PARAM_SELECT_PARAMETER";

    /** PARAM_EDIT_CURRENCY */
    static final public String PARAM_EDIT_CURRENCY = "PARAM_EDIT_CURRENCY";

    /** PARAM_EDIT_DECIMAL */
    static final public String PARAM_EDIT_DECIMAL = "PARAM_EDIT_DECIMAL";

    /** PARAM_EDIT_ISO_DATE */
    static final public String PARAM_EDIT_ISO_DATE = "PARAM_EDIT_ISO_DATE";

    /** PARAM_EDIT_DISCRETE */
    static final public String PARAM_EDIT_DISCRETE = "PARAM_EDIT_DISCRETE";

    /** PARAM_EDIT_MULTILINE_STRING */
    static final public String PARAM_EDIT_LIST_FROM_LIST = "PARAM_EDIT_LIST_FROM_LIST";

    /** PARAM_EDIT_MULTILINE_STRING */
    static final public String PARAM_EDIT_MULTILINE_STRING = "PARAM_EDIT_MULTILINE_STRING";

    /** PARAM_EDIT_STRING */
    static final public String PARAM_EDIT_STRING = "PARAM_EDIT_STRING";

    /** PARAM_EDIT_WHOLE */
    static final public String PARAM_EDIT_WHOLE = "PARAM_EDIT_WHOLE";

    /** PARAM_EDIT_INTEGER */
    static final public String PARAM_EDIT_INTEGER = "PARAM_EDIT_INTEGER";

    /** PARAM_VALUE_LIST */
    static final public String PARAM_VALUE_LIST = "PARAM_VALUE_LIST";

    /** PARAM_NONMODIFIABLE_ERROR */
    static final public String PARAM_NONMODIFIABLE_ERROR = "PARAM_NONMODIFIABLE_ERROR";

    /** PARAM_INVALID_NUMERIC_VALUE_ERROR */
    static final public String PARAM_INVALID_NUMERIC_VALUE_ERROR = "PARAM_INVALID_NUMERIC_VALUE_ERROR";

    /** PARAM_UNSAVEABLE_ERROR */
    static final public String PARAM_UNSAVEABLE_ERROR = "PARAM_UNSAVEABLE_ERROR";

    // REASON CODE MANAGER SCREENS
    /** REASON_CODE_SELECT_LEVEL */
    static final public String REASON_CODE_SELECT_LEVEL = "REASON_CODE_SELECT_LEVEL";

    /** REASON_CODE_SELECT_GROUP */
    static final public String REASON_CODE_SELECT_GROUP = "REASON_CODE_SELECT_GROUP";

    /** REASON_CODE_LIST */
    static final public String REASON_CODE_LIST = "REASON_CODE_LIST";

    /** REASON_CODE_LIST_VIEW_ONLY */
    static final public String REASON_CODE_LIST_VIEW_ONLY = "REASON_CODE_LIST_VIEW_ONLY";

    /** REASON_CODE_EDIT_ALPHA */
    static final public String REASON_CODE_EDIT_ALPHA = "REASON_CODE_EDIT_ALPHA";

    /** REASON_CODE_EDIT */
    static final public String REASON_CODE_EDIT = "REASON_CODE_EDIT";

    /** REASON_CODE_GROUP_NONMODIFIABLE_ERROR */
    static final public String REASON_CODE_GROUP_NONMODIFIABLE_ERROR = "REASON_CODE_GROUP_NONMODIFIABLE_ERROR";

    /** REASON_CODE_UNDELETEABLE_ERROR */
    static final public String REASON_CODE_UNDELETEABLE_ERROR = "REASON_CODE_UNDELETEABLE_ERROR";

    /** REASON_CODE_INVALID_VALUE_ERROR */
    static final public String REASON_CODE_INVALID_VALUE_ERROR = "REASON_CODE_INVALID_VALUE_ERROR";

    /** REASON_CODE_UNSAVEABLE_ERROR */
    static final public String REASON_CODE_UNSAVEABLE_ERROR = "REASON_CODE_UNSAVEABLE_ERROR";

    /** REASON_CODE_SELECT_STORE */
    static final public String REASON_CODE_SELECT_STORE = "REASON_CODE_SELECT_STORE";

    /** REASON_CODE_CONFIRM_DELETE */
    static final public String REASON_CODE_CONFIRM_DELETE = "REASON_CODE_CONFIRM_DELETE";

    /** REASON_CODE_NO_DELETE */
    static final public String REASON_CODE_NO_DELETE = "REASON_CODE_NO_DELETE";

    /** PARAMETER_LIST_EDIT */
    static final public String PARAMETER_LIST_EDIT = "PARAMETER_LIST_EDIT";

    /** PARAMETER_VALUE_EDIT */
    static final public String PARAMETER_VALUE_EDIT = "PARAMETER_VALUE_EDIT";

    // CROSSREACH SCREENS
    /** SERVICE_ALERT_LIST */
    static final public String SERVICE_ALERT_LIST = "SERVICE_ALERT_LIST";

    /** SERVICE_ALERT_LIST_NO_WEB_STORE */
    static final public String SERVICE_ALERT_LIST_NO_WEB_STORE = "SERVICE_ALERT_LIST_NO_WEB_STORE";

    /** BROWSER_MAIN */
    static final public String BROWSER_MAIN = "BROWSER_MAIN";

    /** PRINT_ORDER */
    static final public String PRINT_ORDER = "PRINT_ORDER";

    /** ORDER_LIST */
    static final public String ORDER_LIST = "ORDER_LIST";

    /** EDIT_ITEM_STATUS */
    static final public String EDIT_ITEM_STATUS = "EDIT_ITEM_STATUS";

    /** CONFIRM_SELECTION */
    static final public String CONFIRM_SELECTION = "CONFIRM_SELECTION";

    /** ORDER_DETAILS */
    static final public String ORDER_DETAILS = "ORDER_DETAILS";

    /** CANCEL_ORDER */
    static final public String CANCEL_ORDER = "CANCEL_ORDER";

    /** EDIT_LOCATION */
    static final public String EDIT_LOCATION = "EDIT_LOCATION";

    /** ORDER_LOCATION */
    static final public String ORDER_LOCATION = "ORDER_LOCATION";

    /** EMAIL_SEARCH */
    static final public String EMAIL_SEARCH = "EMAIL_SEARCH";

    /** EMAIL_LIST */
    static final public String EMAIL_LIST = "EMAIL_LIST";

    /** EMAIL_DETAIL */
    static final public String EMAIL_DETAIL = "EMAIL_DETAIL";

    /** EMAIL_REPLY */
    static final public String EMAIL_REPLY = "EMAIL_REPLY";

    /** ONLINE_OFFICE */
    static final public String ONLINE_OFFICE = "ONLINE_OFFICE";

    /** FILL_ITEM_STATUS */
    static final public String FILL_ITEM_STATUS = "FILL_ITEM_STATUS";

    // NON-SPECIFIC SCREEN IDENTIFIERS
    /** SHOW_STATUS_ONLY */
    static final public String SHOW_STATUS_ONLY = "SHOW_STATUS_ONLY";

    /** GET_CURRENT_SCREEN */
    static final public String GET_CURRENT_SCREEN = "GET_CURRENT_SCREEN";

    // GIFT RECEIPT SCREENS
    /** GIFT_CODE_INQUIRY */
    static final public String GIFT_CODE_INQUIRY = "GIFT_CODE_INQUIRY";

    /** GIFT_RECEIPT_ITEM */
    static final public String GIFT_RECEIPT_ITEM = "GIFT_RECEIPT_ITEM";

    /** GIFT_PRICE */
    static final public String GIFT_PRICE = "GIFT_PRICE";

    // GIFT CARD SCREENS
    /** GET_AMOUNT_FOR_GIFT_CARD */
    static final public String GET_AMOUNT_FOR_GIFT_CARD = "GET_AMOUNT_FOR_GIFT_CARD";

    /** GET_CARD_NUM_FOR_GIFT_CARD */
    static final public String GET_CARD_NUM_FOR_GIFT_CARD = "GET_CARD_NUM_FOR_GIFT_CARD";

    // Send SCREENS
    /** SHIPPING_ADDRESS */
    static final public String SHIPPING_ADDRESS = "SHIPPING_ADDRESS";

    /** SHIPPING_METHOD */
    static final public String SHIPPING_METHOD = "SHIPPING_METHOD";

    // STORE CREDIT SCREENS
    /** GET_CUSTOMER_NAME_AND_ID */
    static final public String GET_CUSTOMER_NAME_AND_ID = "GET_CUSTOMER_NAME_AND_ID";

    /** STORE_CREDIT_TENDER_NUMBER */
    static final public String STORE_CREDIT_TENDER_NUMBER = "STORE_CREDIT_TENDER_NUMBER";

    // FOREIGN CREDIT/CERTIFICATE SCREENS
    /** COUNTRY_CODE */
    static final public String COUNTRY_CODE = "COUNTRY_CODE";

    /** STORE_CREDIT_NUMBER_ENTRY */
    static final public String STORE_CREDIT_NUMBER_ENTRY = "STORE_CREDIT_NUMBER_ENTRY";

    /** FOREIGN_TENDER_NUMBER */
    static final public String FOREIGN_TENDER_NUMBER = "FOREIGN_TENDER_NUMBER";

    /** PICK ZERO OR MORE RELATED ITEMS **/
    public static final String RELATED_ITEMS = "RELATED_ITEMS";

    /** PICK ONLY ONE RELATED ITEMS **/
    public static final String PICK_ONE_RELATED_ITEM = "PICK_ONE_RELATED_ITEM";

    // TAX INCLUSIVE (VAT) environment screens
    /** VAT_REPRINT_SELECT */
    static final public String VAT_REPRINT_SELECT = "VAT_REPRINT_SELECT";

    // BrowserFoundation Screen
    public static final String BROWSER_FOUNDATION = "BROWSER_FOUNDATION";

    // ReceiptOptions Screen
    public static final String RECEIPT_OPTIONS_SCREEN = "RECEIPT_OPTIONS_SCREEN";

    // eReceiptEmailAddress Screen
    public static final String ERECEIPT_EMAIL_SCREEN = "ERECEIPT_EMAIL_SCREEN";

    /** GET_PICKUP_DATE_SCREEN */
    static final public String GET_PICKUP_DATE_SCREEN = "GET_PICKUP_DATE_SCREEN";

    /** GET_DELIVERY_DATE_SCREEN */
    static final public String GET_DELIVERY_DATE_SCREEN = "GET_DELIVERY_DATE_SCREEN";

    /** GET_DELIVERY_ADDRESS_SCREEN */
    static final public String GET_DELIVERY_ADDRESS_SCREEN = "GET_DELIVERY_ADDRESS_SCREEN";

    /** Returns Management Refund Options */
    static final public String RM_REFUND_OPTIONS = "RM_REFUND_OPTIONS";

    /** Item Basket transaction Option */
    public static final String ITEM_BASKET = "ITEM_BASKET";

    /**
     * SIM POS Constants
     */
    public static final String INVENTORY_ADV_SEARCH = "INVENTORY_ADV_SEARCH";

    public static final String INVENTORY_DETAIL = "INVENTORY_DETAIL";

    public static final String INVENTORY_INQUIRY = "INVENTORY_INQUIRY";

    public static final String MINIMUM_QTY_AVAIL_INPUT_SCREEN = "MINIMUM_QTY_AVAIL_INPUT_SCREEN";

    public static final String INVENTORY_RESULTS_LIST_SCREEN = "INVENTORY_RESULTS_LIST_SCREEN";

    public static final String HOME_STORE_INVENTORY_RESULTS_LIST_SCREEN = "HOME_STORE_INVENTORY_RESULTS_LIST_SCREEN";

    public static final String SHOW_INVENTORY_OPTIONS_SCREEN = "SHOW_INVENTORY_OPTIONS";

    public static final String INVENTORY_ITEMS_LIST = "INVENTORY_ITEMS_LIST";

    public static final String INVENTORY_ITEM_INFO = "INVENTORY_ITEM_INFO";

    public static final String SPECIFIC_STORE_INPUT_SCREEN = "SPECIFIC_STORE_INPUT_SCREEN";

    public static final String STORE_INFO = "STORE_INFO";


    /** External Order Constants **/
    public static final String EXT_ORDER_ADV_SEARCH = "EXT_ORDER_ADV_SEARCH";

    public static final String EXTERNAL_ORDER = "EXTERNAL_ORDER";

    public static final String EXTERNAL_ORDER_LIST = "EXTERNAL_ORDER_LIST";

    public static final String SEARCH_IN_PROGRESS = "SEARCH_IN_PROGRESS";

    /** BILLPAY_OPTIONS */
    public static final String BILLPAY_SEARCH_OPTIONS = "BILLPAY_SEARCH_OPTIONS";

    /**BILLPAY_OFFLINE */
    public static final String BILLPAY_OFFLINE = "BILLPAY_OFFLINE";

    /** BILLPAY_RESULT_SINGLE */
    public static final String BILLPAY_LIST = "BILLPAY_LIST";

    /**BILLPAY_PAYMENT_DETAILS */
    public static final String BILLPAY_PAYMENT_DETAILS = "BILLPAY_PAYMENT_DETAILS";

    /**BILLPAY_MULTIPLE_CUSTOMERS*/
    public static final String BILLPAY_MULTI_CUSTOMER = "BILLPAY_MULTI_CUSTOMER";

    // SYSTEM IDS; used to manage status change, not screen ids
    /** unused status */
    public static final int STATUS_NOT_USED = 0;

    /** printer status */
    public static final int PRINTER_STATUS = 1;

    /** cash drawer status */
    public static final int CASHDRAWER_STATUS = 2;

    /** data manager status */
    public static final int DATA_MANAGER_STATUS = 3;

    /** training mode status */
    public static final int TRAINING_MODE_STATUS = 4;

    /** check status */
    public static final int CHECK_STATUS = 5;

    /** credit status */
    public static final int CREDIT_STATUS = 6;

    /** debit status */
    public static final int DEBIT_STATUS = 7;

    /** MSR status */
    public static final int MSR_STATUS = 8;

    /** scanner status */
    public static final int SCANNER_STATUS = 9;

    /** MICR status */
    public static final int MICR_STATUS = 10;

    /**
     * Signature Capture and Pin Pad Status (11 & 12) were removed as part of
     * Advance Payment Foundation.
     * Getting device status from the Payment Service is not supported.
     */

    /** transaction re-entry status */
    public static final int TRANS_REENTRY_STATUS = 13;

    /** financial network status */
    public static final int FINANCIAL_NETWORK_STATUS = 15;

    // gift certificate screens
    /** GIFT_CERTIFICATE_AMOUNT */
    static final public String GIFT_CERTIFICATE_AMOUNT = "GIFT_CERTIFICATE_AMOUNT";

    // online / offline
    /** boolean indicating we are online */
    public static final boolean ONLINE = true;

    /** boolean indicating we are offline */
    public static final boolean OFFLINE = false;

    // constants for specs specified in engine artifacts (sites, roads)
    /**
     * prompt and response panel
     */
    public static final String PROMPT_AND_RESPONSE_SPEC = "PromptAndResponsePanelSpec";

    /**
     * status panel spec
     */
    public static final String STATUS_SPEC = "StatusPanelSpec";

    /**
     * dialog spec
     */
    public static final String DIALOG_SPEC = "DialogSpec";

    /**
     * Item not Found spec
     */
    public static final String ITEM_NOT_FOUND_SPEC = "ItemNotFoundSpec";

    /*
     * unlock container constants (just to make code more readable)
     */
    public static final boolean UNLOCK_CONTAINER = true;
    public static final boolean DO_NOT_UNLOCK_CONTAINER = false;

    /* SCAN_SHEET */
    public static final String SCAN_SHEET = "SCAN_SHEET";

    /**
     * This method should be called when the cashier name has changed in the
     * SaleReturnTransaction. It sets the string that shows up in the "Cashier"
     * field of the frame.
     *
     * @param emplName the new cashier's name
     */
    public void cashierNameChanged(String emplName);

    /**
     * This method should be called when the customer name has changed in the
     * SaleReturnTransaction. It sets the string that shows up in the "Customer"
     * field of the frame.
     *
     * @param customerName the new customer's name
     */
    public void customerNameChanged(String customerName);
    
    /**
     * This method should be called when the customer name has changed in the
     * SaleReturnTransaction. It sets the string that shows up in the "Customer"
     * field of the frame. The difference is this method provides a way to let 
     * caller to decide whether to unlock container or not.
     *
     * @param customerName the new customer's name
     * @param unlockContainer unlockContainer
     */
    public void customerNameChanged(String customerName, boolean unlockContainer);

    /**
     * This method returns the input in the input area
     *
     * @return returns the string in the input area. If the string is empty,
     *         then this returns an empty string. This method never returns
     *         null.
     */
    public String getInput();

    /**
     * Gets the model for the specified screen.
     *
     * @param screenId the id of the screen whose model we want
     * @return the bean model for the specified screen
     */
    public UIModelIfc getModel(String screenId);

    /**
     * Gets the model for the current screen.
     *
     * @return UIModelIfc model of the current screen
     */
    public UIModelIfc getModel();

    /**
     * Retrieves the active screens ID
     *
     * @return String the active screens ID.
     * @throws UIException if the active screen is not currently defined.
     */
    public String getActiveScreenID() throws UIException;

    /**
     * This method should be called when the workstationID has changed in the
     * SaleReturnTransaction. It sets the string that shows up in the "Register"
     * field of the frame.
     *
     * @param workstationID the new workstationID
     */
    public void registerChanged(String workstationID);

    /**
     * This method should be called when the salesperson name has changed in the
     * SaleReturnTransaction. It sets the string that shows up in the
     * "SalesPerson" field of the frame.
     *
     * @param emplName the new salesperson's name
     */
    public void salesAssociateNameChanged(String emplName);

    /**
     * This displays a pop-up dialog over the regular user interface.
     *
     * @param screenID the ID of the dialog for the UI to display.
     * @param beanModel The model of the screen to use.
     */
    public void showDialog(String screenID, UIModelIfc beanModel);

    /**
     * This displays a pop-up dialog over the regular user interface and blocks
     * the thread until the dialog exits. The beanModel passed will contain
     * the response of the user. If the response is null, the user just closed
     * the dialog.
     * <p>
     * The "AndWait" part of this method follows the naming convention of
     * {@link javax.swing.SwingUtilities#invokeAndWait(Runnable)}.
     *
     * @param screenID the ID of the dialog for the UI to display.
     * @param beanModel The model of the screen to use.
     */
    public void showDialogAndWait(String screenID, UIModelIfc beanModel);

    /**
     * Sets the model for the specified bean.
     *
     * @param screenId the id of the bean whose model is to be set
     * @param beanModel the model to set in the bean specified by
     *            <code>screenId</code>.
     */
    public void setModel(String screenId, UIModelIfc beanModel);

    /**
     * Sets the model for the specified bean.
     *
     * @param screenId the id of the bean whose model is to be set
     * @param beanModel the model to set in the bean specified by
     *            <code>screenId</code>.
     * @param unlockContainer Set to true if the screen should be unlocked for user events.
     */
    public void setModel(String screenId, UIModelIfc beanModel, boolean unlockContainer);

    /**
     * This displays the specified screen
     *
     * @param screenID the ID of the screen to display
     */
    public void showScreen(String screenID);

    /**
     * This displays the specified screen using the specified model.
     *
     * @param screenID the ID of the screen for the UI to display.
     * @param beanModel The new model of the current bean.
     */
    public void showScreen(String screenID, UIModelIfc beanModel);

    /**
     * This method is called to set the status on the UI screen.
     *
     * @param systemId identifies the system to which this status belongs.
     * @param online true means the system is online.
     */
    public void statusChanged(int systemId, boolean online);

    /**
     * This method is called to set the status on the UI screen.
     *
     * @param systemID identifies the system to which this status belongs.
     * @param online true means the system is online.
     * @param unlockContainer should user input be unlocked by this status change?
     *        If the change is asynchronous and not part of a tour, it is a good idea
     *        not to unlock the container out from under the tour and allow the user
     *        to click on unexpected buttons etc.
     */
    public void statusChanged(int systemId, boolean online, boolean unlockContainer);

    /**
     * Sets the Application Look And Feel
     */
    public void setLookAndFeel();

    /**
     * Sets the application mode for the ui.
     * @param applicationMode
     * @see oracle.retail.stores.foundation.manager.gui.ApplicationMode
     * @since 13.4
     */
    public void setApplicationMode(ApplicationMode applicationMode);

}
