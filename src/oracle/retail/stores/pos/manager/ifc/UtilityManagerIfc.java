/* ===========================================================================
* Copyright (c) 2008, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/manager/ifc/UtilityManagerIfc.java /rgbustores_13.4x_generic_branch/1 2012/02/07 13:49:37 blarsen Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   02/06/12 - Moving GENERATE_SEQUENCE_NUMBER into
 *                         UtilityManagerIfc.
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    jswan     11/05/10 - Modified to prevent returns with employee discounts
 *                         from printing the Employee Discount Store Receipt.
 *    acadar    09/07/10 - externalize supported localesz
 *    abhayg    08/13/10 - STOPPING POS TRANSACTION IF REGISTER HDD IS FULL
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    nkgautam  12/16/09 - Added utility methods for serialisation
 *    cgreene   05/15/09 - generic performance improvements
 *    ohorne    03/30/09 - added methods to obtain regex validation patterns
 *    abondala  03/05/09 - ReasonCodes are retrieved from the database not from
 *                         the bundles.
 *    ranojha   11/05/08 - Fixed Customer
 *    ranojha   11/05/08 - Fixed Tax Exempt Reason Code for Customer
 *    acadar    10/24/08 - localization of post void reason codes
 *    mdecama   10/20/08 - Deprecated methods that use CodeListMap

     $Log:
      13   360Commerce 1.12        4/18/2008 3:03:27 PM   Alan N. Sinton  CR
           31133: Undid fix for 30247 and fixed the code for employee card
           swipe.  Code was reviewed by Tony Zgarba.
      12   360Commerce 1.11        4/16/2008 3:14:27 AM   Anil Kandru
           account number getter method is added.
      11   360Commerce 1.10        1/17/2008 5:24:06 PM   Alan N. Sinton  CR
           29954: Refactor of EncipheredCardData to implement interface and be
            instantiated using a factory.
      10   360Commerce 1.9         12/14/2007 8:59:59 AM  Alan N. Sinton  CR
           29761: Removed non-PABP compliant methods and modified card RuleIfc
            to take an instance of EncipheredCardData.
      9    360Commerce 1.8         12/12/2007 6:47:38 PM  Alan N. Sinton  CR
           29761: FR 8: Prevent repeated decryption of PAN data.
      8    360Commerce 1.7         6/13/2007 12:12:08 PM  Alan N. Sinton  CR
           26485 - Changes per code review.
      7    360Commerce 1.6         6/4/2007 6:01:32 PM    Alan N. Sinton  CR
           26486 - Changes per review comments.
      6    360Commerce 1.5         5/8/2007 5:22:00 PM    Alan N. Sinton  CR
           26486 - Refactor of some EJournal code.
      5    360Commerce 1.4         4/30/2007 7:01:38 PM   Alan N. Sinton  CR
           26485 - Merge from v12.0_temp.
      4    360Commerce 1.3         12/13/2005 4:42:37 PM  Barry A. Pape
           Base-lining of 7.1_LA
      3    360Commerce 1.2         3/31/2005 4:30:41 PM   Robert Pearse
      2    360Commerce 1.1         3/10/2005 10:26:38 AM  Robert Pearse
      1    360Commerce 1.0         2/11/2005 12:15:27 PM  Robert Pearse
     $
     Revision 1.15  2004/07/28 18:28:41  jdeleau
     @scr 6539 Gift Receipts set on a transaction level need to print
     all line items on the same gift receipt.

     Revision 1.14  2004/07/15 22:31:02  cdb
     @scr 1673 Removed all deprecation warnings from log and manager packages.
     Moved LogArchiveStrategies to deprecation tree.

     Revision 1.13  2004/06/29 17:05:38  cdb
     @scr 4205 Removed merging of money orders into checks.
     Added ability to count money orders at till reconcile.

     Revision 1.12  2004/06/25 17:02:57  cdb
     @scr 4479 Added Alterations Print Control parameter.

     Revision 1.11  2004/06/22 00:13:23  cdb
     @scr 4205 Updated to merge money orders into checks during till reconcile.

     Revision 1.10  2004/05/19 15:25:01  lzhao
     @scr 3693: make journal read, write none transaction message.

     Revision 1.9  2004/04/28 23:32:20  cdb
     @scr 4456 Updated Post Void Signature Lines parameter to match requirements.

     Revision 1.8  2004/04/21 21:15:24  cdb
     @scr 4452 Enforced prohibiting gift receipts for damaged items.

     Revision 1.6  2004/04/21 19:11:50  dcobb
     @scr 4452 Feature Enhancement: Printing
     Added printGiftReceipt() method.

     Revision 1.5  2004/03/17 20:29:32  mweis
     @scr 4025 Customer Survey/Reward enablement

     Revision 1.4  2004/03/15 18:39:08  mweis
     @scr 0 Javadoc cleanup

     Revision 1.3  2004/02/12 16:48:37  mcs
     Forcing head revision

     Revision 1.2  2004/02/11 21:33:57  rhafernik
     @scr 0 Log4J conversion and code cleanup

     Revision 1.1.1.1  2004/02/11 01:04:13  cschellenger
     updating to pvcs 360store-current


 *
 *    Rev 1.6   Feb 03 2004 16:49:38   bwf
 * Added getEmployeeFromModel.
 *
 *    Rev 1.5   Jan 26 2004 16:04:56   epd
 * Updates for void printing
 *
 *    Rev 1.4   Jan 22 2004 14:05:28   epd
 * renamed parameter constant
 *
 *    Rev 1.3   Jan 06 2004 10:59:48   epd
 * added method to determine card type
 *
 *    Rev 1.2   Dec 18 2003 19:16:16   blj
 * changed cardtypeIfc to cardtype for backwards compatibility
 *
 *    Rev 1.1   Dec 18 2003 11:14:22   epd
 * Updated method to return interface type
 *
 *    Rev 1.0   Aug 29 2003 15:51:36   CSchellenger
 * Initial revision.
 *
 *    Rev 1.17   Jul 18 2003 14:07:54   baa
 * Rename print control parameters to match requirements
 * Resolution for 3089: Layaway Pickup Receipt Print Control missing from POS
 *
 *    Rev 1.16   Jul 02 2003 09:56:42   RSachdeva
 * getConfiguredCardTypeInstance already implemented in UtilityManager is being added here
 * Resolution for POS SCR-2572: Tender Totals are incorrect.
 *
 *    Rev 1.15   May 22 2003 16:55:42   jgs
 * Added methods and Journal data required by the new Journal Manager.
 * Resolution for 2543: Modify EJournal to put entries into a JMS Queue on the store server.
 *
 *    Rev 1.14   Apr 04 2003 17:29:40   sfl
 * Added new data attribute to hold tax rule data for local store.
 * Resolution for POS SCR-1749: POS 6.0 Tax Package
 *
 *    Rev 1.13   Mar 07 2003 17:11:02   baa
 * code review changes for I18n
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.12   Feb 21 2003 09:35:28   baa
 * Changes for contries.properties refactoring
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.11   Jan 30 2003 16:59:48   baa
 * add employe locale preference for offline flow
 * Resolution for POS SCR-1843: Multilanguage support
 *
 *    Rev 1.10   Jan 02 2003 12:06:54   crain
 * Added code list map set and get methods
 * Resolution for 1875: Adding a business customer offline crashes the system
 *
 *    Rev 1.9   Dec 18 2002 17:40:18   baa
 * add employee preferred locale support
 * Resolution for POS SCR-1843: Multilanguage support
 *
 *    Rev 1.8   Oct 09 2002 14:58:30   jriggins
 * Added getReasonCodeTextEntries() methods
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.7   Sep 18 2002 17:15:16   baa
 * country/state changes
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.6   Sep 04 2002 15:20:58   jriggins
 * Added getErrorCodeString()
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.5   Sep 04 2002 10:01:24   baa
 * replace calls to InternationalTextSupport with  calls to ResourceBundleUtil which allow us to change the locale
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.4   Sep 03 2002 16:03:10   baa
 * externalize domain  constants and parameter values
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.3   Jul 26 2002 10:48:56   pjf
 * Add check digit support to POS.
 * Resolution for 1768: Check Digit support
 *
 *    Rev 1.2   Jul 05 2002 17:58:28   baa
 * code conversion and reduce number of color settings
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.1   Jun 24 2002 09:18:22   baa
 * modify retrieve text to add subsystem notion
 * Resolution for POS SCR-1624: Localization Support
 *
 *    Rev 1.0   Apr 29 2002 15:44:18   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:14:44   msg
 * Initial revision.
 *
 *    Rev 1.8   Mar 12 2002 18:28:12   mpm
 * Externalized line-display text.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.7   Mar 12 2002 14:09:08   mpm
 * Externalized text in receipts and documents.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.6   Mar 10 2002 18:00:02   mpm
 * Externalized text in dialog messages.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.5   Feb 27 2002 17:27:22   mpm
 * Restructured end-of-transaction processing.
 * Resolution for POS SCR-1440: Enhance end-of-transaction processing for performance reasons
 *
 *    Rev 1.4   Feb 25 2002 10:50:50   mpm
 * Internationalization
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.3   Feb 24 2002 13:44:52   mpm
 * Externalized text for default, common and giftcard config files.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.2   29 Jan 2002 09:54:14   epd
 * Deprecated all methods using accumulate parameter and added new methods without this parameter.  Also removed all reference to the parameter wherever used.
 * (The behavior is to accumulate totals)
 * Resolution for POS SCR-770: Remove the accumulate parameter and all references to it.
 *
 *    Rev 1.1   26 Nov 2001 16:06:22   jbp
 * moved receipt logic form printTransactionReceipt Aisle to Utility Manager
 * Resolution for POS SCR-221: Receipt Design Changes
 *
 *    Rev 1.0   Sep 21 2001 11:09:58   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:05:08   msg
 * header update
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package oracle.retail.stores.pos.manager.ifc;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;

import oracle.retail.stores.common.utility.LocaleRequestor;
import oracle.retail.stores.domain.arts.FinancialTotalsDataTransaction;
import oracle.retail.stores.domain.arts.StoreDataTransaction;
import oracle.retail.stores.domain.arts.TransactionWriteDataTransaction;
import oracle.retail.stores.domain.financial.FinancialTotalsIfc;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.financial.StoreStatusIfc;
import oracle.retail.stores.domain.financial.TillIfc;
import oracle.retail.stores.domain.tax.NewTaxRuleIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionTaxIfc;
import oracle.retail.stores.domain.utility.CardType;
import oracle.retail.stores.domain.utility.CodeListIfc;
import oracle.retail.stores.domain.utility.CountryIfc;
import oracle.retail.stores.domain.utility.RelatedItemTransactionInfoIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.device.EncipheredCardDataIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.manager.ManagerIfc;
import oracle.retail.stores.pos.ado.tender.CreditTypeEnum;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;

/**
 * This class defines the interface to the UtilityManager.
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface UtilityManagerIfc extends ManagerIfc
{
    /**
     * Comma delimeter
     */
    public static final String COMMA_DELIMITER = ",";

    /**
     * application property files
     */
    public static final String APPLICATION_PROPERTIES = "application.properties";

    /**
     * supported locales property
     */
    public static final String SUPPORTED_LOCALES = "supported_locales";

    /**
     * Close Register DataTransaction name
     */
    public static final String CLOSE_REGISTER_TRANSACTION_NAME = FinancialTotalsDataTransaction.notQueuedSaveName;

    /**
     * Close Store DataTransaction name
     */
    public static final String CLOSE_STORE_TRANSACTION_NAME = StoreDataTransaction.notQueuedSaveName;

    /**
     * Close Store and Register DataTransaction name
     */
    public static final String CLOSE_STORE_REGISTER_TRANSACTION_NAME = TransactionWriteDataTransaction.notQueuedSaveName;

    /**
     * line display bundles
     */
    public static final String[] LINE_DISPLAY_BUNDLES = { BundleConstantsIfc.COMMON_BUNDLE_NAME,
            BundleConstantsIfc.LINE_DISPLAY_BUNDLE_NAME };


    /**
     * generate a new sequence number (rather than using one supplied by the caller)
     */
    public static final long GENERATE_SEQUENCE_NUMBER = -1;


    /**
     * Name used to access the parameter manager from within a bus.
     */
    public static final String TYPE = "UtilityManager";

    /**
     * Initializes a new transaction and writes a journal entry.
     *
     * @param trans Transaction to initialize
     * @param bus The bus interface
     * @param seq A supplied sequence number, -1 if none
     * @param custID The customer id, null if none
     */
    public void initializeTransaction(TransactionIfc trans, BusIfc bus, long seq, String custID);

    /**
     * Initializes a new transaction and writes a journal entry.
     *
     * @param trans Transaction to initialize
     * @param bus The bus interface
     * @param seq A supplied sequence number, -1 if none
     */
    public void initializeTransaction(TransactionIfc trans, BusIfc bus, long seq);

    /**
     * Saves the transaction to the database.
     *
     * @param trans The transaction to save to persistent storage
     * @exception DataException thrown if error occurs processing the
     *                transaction
     */
    public void saveTransaction(TransactionIfc trans) throws DataException;

    /**
     * Saves the transaction to the database.
     *
     * @param trans The transaction to save to persistent storage
     * @param endTransactionJournaling if true, forces end of transaction
     *            journaling.
     * @exception DataException thrown if error occurs processing the
     *                transaction
     */
    public void saveTransaction(TransactionIfc trans, boolean endTransactionJournaling) throws DataException;

    /**
     * Saves the transaction to the database.
     *
     * @param trans The transaction to save to persistent storage
     * @param totals financial totals for the transaction
     * @param till till which processed the transaction
     * @param register register which processed the transaction
     * @exception DataException thrown if error occurs processing the
     *                transaction
     */
    public void saveTransaction(TransactionIfc trans, FinancialTotalsIfc totals, TillIfc till, RegisterIfc register)
            throws DataException;

    /**
     * Saves the transaction to the database.
     *
     * @param trans The transaction to save to persistent storage
     * @param totals financial totals for the transaction
     * @param till till which processed the transaction
     * @param register register which processed the transaction
     * @param endTransactionJournaling if true, forces end of transaction
     *            journaling.
     * @exception DataException thrown if error occurs processing the
     *                transaction
     */
    public void saveTransaction(TransactionIfc trans, FinancialTotalsIfc totals, TillIfc till, RegisterIfc register,
            boolean endTransactionJournaling) throws DataException;

    /**
     * Saves the transaction to the database.
     *
     * @param trans The transaction to save to persistent storage
     * @param till The till
     * @param register The register
     * @throws DataException upon errors
     */
    public void saveTransaction(TransactionIfc trans, TillIfc till, RegisterIfc register) throws DataException;

    /**
     * Performs journaling housekeeping for a completed transaction
     *
     * @param trans The transaction
     */
    public void completeTransactionJournaling(TransactionIfc trans);

    /**
     * Updates the store status from the database.
     *
     * @param status The last known store status
     * @return The store status
     * @exception DataException upon error
     */
    public StoreStatusIfc refreshStoreStatus(StoreStatusIfc status) throws DataException;

    /**
     * Write to hard totals, using data in cargo.
     *
     * @param bus BusIfc
     * @exception DeviceException if hard totals cannot be written
     */
    public void writeHardTotals(BusIfc bus) throws DeviceException;

    /**
     * Creates a TransactionTax object and initializes it with default values
     * retrieved from parameters.
     *
     * @param bus The service bus
     * @return The TransactionTax object
     */
    public TransactionTaxIfc getInitialTransactionTax(BusIfc bus);

    /**
     * Indexes transaction in journal file using string transaction ID.
     *
     * @param tid transaction ID
     */
    public void indexTransactionInJournal(String tid);

    /**
     * Retrieves the list of supported countries and its Administrative Regions
     * as specified on the application.xml
     *
     * @param pm Parameter Manager
     * @return array of countries
     */
    public CountryIfc[] getCountriesAndStates(ParameterManagerIfc pm);

    /**
     * Retrieve translation for supported Locales
     * @param localeKey
     * @param defaultValue
     * @param locale
     * @return
     */
    public String getLocaleDisplayName(String localeKey, String defaultValue, Locale locale);

    /**
     * Retrieves the index in the country array given the country code
     *
     * @param code the country code
     * @param pm parameter manager reference
     * @return index in the country array
     */
    public int getCountryIndex(String code, ParameterManagerIfc pm);

    /**
     * Retrieves the index in the state array given the state code
     *
     * @param countryIndex country index
     * @param code the state code
     * @param pm parameter manager reference
     * @return index in the state array
     */
    public int getStateIndex(int countryIndex, String code, ParameterManagerIfc pm);

    /**
     * Retrieves text through international text support facility for specified
     * spec name, bundle name and property. Implements default if property not
     * found.
     *
     * @param specName bean specification name
     * @param bundleName bundle in which to search for answer
     * @param propName property key
     * @param defaultValue default value
     * @return text from support facility
     */
    public String retrieveText(String specName, String bundleName, String propName, String defaultValue);

    /**
     * Retrieves text through international text support facility for specified
     * spec name, bundle name and property. Implements default if property not
     * found.
     *
     * @param specName bean specification name
     * @param bundleName bundle in which to search for answer
     * @param propName property key
     * @param defaultValue default value
     * @param subsystem subsystem for which to retrieve bundle
     * @return text from support facility
     */
    public String retrieveText(String specName, String bundleName, String propName, String defaultValue,
            String subsystem);

    /**
     * Retrieves text through international text support facility for specified
     * spec name, bundle name and property. Implements default if property not
     * found.
     *
     * @param specName bean specification name
     * @param bundleName bundle in which to search for answer
     * @param propName property key
     * @param defaultValue default value
     * @param locale locale for which to retrieve bundle
     * @return text from support facility
     */
    public String retrieveText(String specName, String bundleName, String propName, String defaultValue, Locale locale);

    /**
     * Retrieves dialog text through international text support facility.
     *
     * @param propName property key
     * @param defaultValue default value
     * @return text from support facility
     */
    public String retrieveDialogText(String propName, String defaultValue);

    /**
     * Retrieves journal text through international text support facility.
     *
     * @param propName property key
     * @param defaultValue default value
     * @return text from support facility
     */
    public String retrieveJournalText(String propName, String defaultValue);

    /**
     * Retrieves report text through international text support facility.
     *
     * @param propName property key
     * @param defaultValue default value
     * @return text from support facility
     */
    public String retrieveReportText(String propName, String defaultValue);

    /**
     * Retrieves common text through international text support facility.
     *
     * @param propName property key
     * @return text from support facility
     */
    public String retrieveCommonText(String propName);

    /**
     * Retrieves common text through international text support facility.
     *
     * @param propName property key
     * @param defaultValue default value
     * @return text from support facility
     */
    public String retrieveCommonText(String propName, String defaultValue);

    /**
     * Retrieves common text through international text support facility.
     *
     * @param propName property key
     * @param defaultValue default value
     * @param locale locale used to retrieve the bundle
     * @return text from support facility
     */
    public String retrieveCommonText(String propName, String defaultValue, Locale locale);

    /**
     * Retrieves common text through international text support facility.
     *
     * @param propName property key
     * @param defaultValue default value
     * @param subsystem subsystem used to retrieve the locale for the bundle
     * @return text from support facility
     */
    public String retrieveCommonText(String propName, String defaultValue, String subsystem);

    /**
     * Retrieves line-display text through international text support facility.
     *
     * @param propName property key
     * @param defaultValue default value
     * @return text from support facility
     */
    public String retrieveLineDisplayText(String propName, String defaultValue);

    /**
     * Validates the check digit (last element in String number) according to
     * the algorithm mapped to posFunction. If posFunction is not mapped to a
     * CheckDigitStrategy in the CheckDigitUtility, this method returns true and
     * no validation is performed.
     *
     * @param posFunction - the name of the pos function requesting validation
     * @param number - a numeric String, with last element containing the check
     *            digit
     * @return boolean true if the check digit is valid for the given function
     *         or if the function is not configured in the CheckDigitUtility
     * @see oracle.retail.stores.domain.utility.checkdigit.CheckDigits
     * @see oracle.retail.stores.pos.utility.CheckDigitUtility
     */
    public boolean validateCheckDigit(String posFunction, String number);

    /**
     * Validates the check digit (last element in String number) according to
     * the algorithm mapped to posFunction. If posFunction is not mapped to a
     * CheckDigitStrategy in the CheckDigitUtility, this method returns true and
     * no validation is performed.
     *
     * @param posFunction - the name of the pos function requesting validation
     * @param cardData - a numeric String, with last element containing the
     *            check digit
     * @return boolean true if the check digit is valid for the given function
     *         or if the function is not configured in the CheckDigitUtility
     * @see oracle.retail.stores.domain.utility.checkdigit.CheckDigits
     * @see oracle.retail.stores.pos.utility.CheckDigitUtility
     */
    public boolean validateCheckDigit(String posFunction, EncipheredCardDataIfc cardData);

    /**
     * Retrieves the string corresponding to the error represented by errorCode
     * translated into the Locale specified for the user interface.
     *
     * @param errorCode int representing the error that has occurred. The
     *            {@link oracle.retail.stores.foundation.manager.data.DataException}
     *            class has the corresponding error code constants.
     * @return String containing the corresponding error message for the given
     *         errorCode and translated per the Locale for the user interface.
     * @see oracle.retail.stores.foundation.manager.data.DataException
     */
    public String getErrorCodeString(int errorCode);

    /**
     * Retrieves a handle to the bundle properties
     *
     * @param tag bean key name
     * @param bundle bundle in which to search for answer
     * @param locale the locale used to retrieve the bundle
     * @return Properties handle to the bundle
     */
    public Properties getBundleProperties(String tag, String bundle, Locale locale);

    /**
     * Retrieves a handle to the bundle properties
     *
     * @param tag bean key name
     * @param bundles bundles in which to search for answer
     * @param locale the locale used to retrieve the bundle
     * @return Properties handle to the bundle
     */
    public Properties getBundleProperties(String tag, String[] bundles, Locale locale);

    /**
     * Get the hashtable keyed with tax group, and have vector of tax rules
     *
     * @return Hashtable
     */
    public Hashtable<Integer,Vector<NewTaxRuleIfc>> getTaxGroupTaxRulesMapping();

    /**
     * Set the tax group tax rules mapping hashtable.
     *
     * @param ht Hashtable keyed with tax group, and have vector of tax rules
     */
    public void setTaxGroupTaxRulesMapping(Hashtable<Integer, Vector<NewTaxRuleIfc>> ht);

    /**
     * Return a configured CardType through the CardType utility.
     *
     * @return a configured CardType object
     */
    public CardType getConfiguredCardTypeInstance();

    /**
     * Given a card number, attempt to find out what type of credit (VISA, MC,
     * etc)
     *
     * @param cardData The EncipheredCardData instance to test
     * @return The appropriate enumerated credit type.
     */
    public CreditTypeEnum determineCreditType(EncipheredCardDataIfc cardData);

    /**
     * This method returns an employee id string after being given a prompt and
     * response model.
     *
     * @param pAndRModel
     * @return employeeID string
     */
    public String getEmployeeFromModel(PromptAndResponseModel pAndRModel);

    /**
     * This method gets the related item transacion info.
     *
     * @return Returns the relatedItemTransInfo.
     */
    public RelatedItemTransactionInfoIfc getRelatedItemTransInfo();

    /**
     * This method sets the related Item transaction info.
     *
     * @param relatedItemTransInfo The relatedItemTransInfo to set.
     */
    public void setRelatedItemTransInfo(RelatedItemTransactionInfoIfc relatedItemTransInfo);

    /**
     * Determine if transaction has any employee discounts applied.
     *
     * @param trans Sale Return Transaction with potential Employee discounts
     * @return true if employee discounts applied
   * @deprecated in version 13.3. Use
   *   {@link oracle.retail.stores.pos.manager.utility.UtilityManagerIfc.getEmployeeIDForEmployeeDiscountReceipt()}
     */
    public String hasEmployeeDiscounts(SaleReturnTransactionIfc transaction);

    /**
     * Determine if transaction has any employee discounts applied to items being sold.
     * This method is used by the reciept generating logic to get the employee ID and
     * determine if an Employee discount receipt should be printed.
     *
     * @param trans Sale Return Transaction with potential Employee discounts
     * @return null if no employee discount found
     */
    public String getEmployeeIDForEmployeeDiscountReceipt(SaleReturnTransactionIfc trans);

    /**
     * Returns reason code text given a CodeListIfc instance and int reason
     * code.
     *
     * @param list The CodeListIfc instance.
     * @param int reasonCode value
     * @return reason code text
     * @see oracle.retail.stores.domain.utility.CodeConstantsIfc
     */
    public String getReasonCodeText(CodeListIfc list, int reasonCode);

    /**
     * Returns the a Locale Requestor with all potentially required locales
     *
     * @return A locale requestor object with all required locales.
     */
    public LocaleRequestor getRequestLocales();

    /**
     * Gets the reason codess for a code list type from the CodeListManager
     *
     * @param String storeId
     * @param String codeListType
     * @return CodeListIfc
     */
    public CodeListIfc getReasonCodes(String storeId, String codeListType);

    /**
     * Returns postalcode format for a country defined in domain.properties
     *
     * @param String countryCode
     * @return PostalCodeMask corresponding to countryCode
     */
    public String getPostalCodeFormat(String countryCode);

    /**
     * Returns regular expression validation string for a country defined in
     * domain.properties
     *
     * @param String countryCode
     * @return PostalCodeValidationRegexp corresponding to countryCode
     */
    public String getPostalCodeValidationRegexp(String countryCode);

    /**
     * Returns phone format for a country defined in domain.properties
     *
     * @param String countryCode
     * @return PhoneMask corresponding to countryCode
     */
    public String getPhoneFormat(String countryCode);

    /**
     * Returns regular expression validation string for a country defined in
     * domain.properties
     *
     * @param String countryCode
     * @return PhoneValidationRegexp corresponding to countryCode
     */
    public String getPhoneValidationRegexp(String countryCode);

    /**
     * Returns the regular expression used for email address format validation,
     * defined in domain.properties
     *
     * @return the regex pattern
     */
    public String getEmailValidationRegexp();

    /**
     * Gets the IMEI Enabled/Disabled property
     * @return boolean
     */
    public boolean getIMEIProperty();

    /**
     * Gets the Serialisation Enabled/Disabled property
     * @return boolean
     */
    public boolean getSerialisationProperty();

    /**
     * Gets the IMEI Field Length Property
     * @return String
     */
    public String getIMEIFieldLengthProperty();

    /**
     * This method is used to create dialog box specific for certain data/queue exceptions
     * @param ex DataException
     * @return DialogBeanModel
     */
    public DialogBeanModel createErrorDialogBeanModel(DataException ex);

    /**
     * This method is used to create dialog box specific for certain data/queue exceptions
     * @param ex DataException
     * @param defaultModel boolean
     * @return DialogBeanModel
     */
    public DialogBeanModel createErrorDialogBeanModel(DataException ex, boolean defaultModel);

}
