/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/customer/common/SaveCustomerSite.java /rgbustores_13.4x_generic_branch/6 2011/09/21 09:54:24 mchellap Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    mchellap  09/21/11 - Journal masked TaxId
 *    masahu    09/07/11 - Fix to NullPointerException in journaling tax ID
 *    masahu    09/02/11 - Customer Tax ID in EJournal
 *    rrkohli   12/10/10 - Fixed updated customer info in EJ
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    abondala  01/03/10 - update header date
 *    mchellap  06/05/09 - Defect#3756 Fixed additional line in customer EJ
 *    acadar    03/23/09 - check for null pricing group id
 *    mahising  02/25/09 - Fixed e-journal issue for displaying business
 *                         customer lable
 *    vchengeg  02/11/09 - modified to journal the new customer add fields
 *                         namely Tax Certificate and Tax Exemption Reason
 *    vchengeg  02/10/09 - Checked the EJournal entry fields for a null value
 *                         and assigned them a blank string when null.
 *    vchengeg  02/09/09 - modified for i18n of customer information
 *    vchengeg  02/05/09 - Made changes to format EJournal for Discount and
 *                         Markdowns
 *    mahising  01/30/09 - revart back EJ issue for Business customer
 *    mahising  01/27/09 - fixed EJ issue of business customer
 *    mahising  01/20/09 - fix ejournal issue for customer
 *    mahising  12/23/08 - fix base issue
 *    vchengeg  12/08/08 - EJ I18n formatting
 *    deghosh   11/27/08 - EJ i18n changes
 *    mahising  11/26/08 - fixed merge issue
 *    mahising  11/25/08 - updated due to merge
 *    sswamygo  11/05/08 - Checkin after merges
 *
 * ===========================================================================
 * $Log:
 *    8    360Commerce 1.7         4/15/2008 10:27:13 AM  Maisa De Camargo CR
 *         31123 - Using Resource Bundle to retrieve the message for the
 *         CustomerError Dialog.
 *         Replaced StringBuffer with StringBuilder and updated Copyright.
 *    7    360Commerce 1.6         2/11/2007 3:26:59 PM   Charles D. Baker CR
 *         24011 - fix merged from .v8x.
 *    6    360Commerce 1.5         8/7/2006 3:05:31 PM    Brett J. Larsen CR
 *         10796 - adding customer details to ejournal string
 *    5    360Commerce 1.4         1/25/2006 4:11:45 PM   Brett J. Larsen merge
 *          7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
 *    4    360Commerce 1.3         12/13/2005 4:42:39 PM  Barry A. Pape
 *         Base-lining of 7.1_LA
 *    3    360Commerce 1.2         3/31/2005 4:29:49 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:25:02 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:14:03 PM  Robert Pearse
 *:
 *
 *    6    .v7x      1.4.1.0     4/27/2006 5:12:59 AM   Dinesh Gautam   CR
 *         10796: Updated to add new customer details into EJ
 *
 *    5    .v710     1.2.2.0     9/21/2005 13:40:19     Brendan W. Farrell
 *         Initial Check in merge 67.
 *    4    .v700     1.2.3.0     12/23/2005 17:17:51    Rohit Sachdeva  8203:
 *         Null Pointer Fix for Business Customer Info
 *    3    360Commerce1.2         3/31/2005 15:29:49     Robert Pearse
 *    2    360Commerce1.1         3/10/2005 10:25:02     Robert Pearse
 *    1    360Commerce1.0         2/11/2005 12:14:03     Robert Pearse
 *
 *   Revision 1.7  2004/08/05 16:16:15  jdeleau
 *   @scr 6782 Use Factory when creating CustomerWriteDataTransaction
 *
 *   Revision 1.6  2004/06/29 13:37:47  kll
 *   @scr 4400: usage of JournalManager's entry type to dictate whether Customer addition belongs inside or outside the context of a transaction
 *
 *   Revision 1.5  2004/04/12 18:58:36  pkillick
 *   @scr 4332 -Replaced direct instantiation(new) with Factory call.
 *
 *   Revision 1.4  2004/03/03 23:15:06  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.3  2004/02/12 16:49:25  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:40:12  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 15:55:28   CSchellenger
 * Initial revision.
 *
 *    Rev 1.6   May 27 2003 09:20:56   baa
 * cleanup
 * Resolution for 2483: MBC Customer Sceen
 *
 *    Rev 1.5   Apr 04 2003 17:07:04   baa
 * refactoring
 * Resolution for POS SCR-2098: Refactoring of Customer Service Screens
 *
 *    Rev 1.4   Mar 20 2003 18:18:46   baa
 * customer screens refactoring
 * Resolution for POS SCR-2098: Refactoring of Customer Service Screens
 *
 *    Rev 1.3   Mar 03 2003 16:16:28   RSachdeva
 * Clean Up Code Conversion
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.2   Oct 09 2002 15:29:40   RSachdeva
 * Code Conversion
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.1   Aug 07 2002 19:33:56   baa
 * remove hard coded date formats
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.0   Apr 29 2002 15:33:42   msg
 * Initial revision.
 *
 *    Rev 1.1   Mar 18 2002 23:11:48   msg
 * - updated copyright
 *
 *    Rev 1.0   Mar 18 2002 11:24:34   msg
 * Initial revision.
 *
 *    Rev 1.9   31 Jan 2002 14:13:26   baa
 * journal email address changes
 * Resolution for POS SCR-769: Changing email address on Customer Contact during Customer Find does not journal
 *
 *    Rev 1.8   30 Jan 2002 22:29:24   baa
 * customer ui fixex
 * Resolution for POS SCR-965: Add Customer screen UI defects
 *
 *    Rev 1.7   14 Jan 2002 17:24:28   baa
 * fix for updating customer offline
 * Resolution for POS SCR-657: Updating Customer offline does not update Customer record when back online
 *
 *    Rev 1.6   11 Jan 2002 18:08:18   baa
 * update phone field
 * Resolution for POS SCR-561: Changing the Type on Add Customer causes the default to change
 * Resolution for POS SCR-567: Customer Select Add, Find, Delete display telephone type as Home/Work
 *
 *    Rev 1.5   07 Jan 2002 13:20:48   baa
 * fix journal problems and adding offline
 * Resolution for POS SCR-506: Customer Find prints 'Add Custumer: ' in EJ
 * Resolution for POS SCR-507: Updates to Customer Info during Find use case does not journal
 * Resolution for POS SCR-519: Unable to add more than 1 customer offline
 *
 *    Rev 1.4   03 Jan 2002 15:39:42   vxs
 * Added check at very beginning to not execute code if in training mode.
 * Resolution for POS SCR-521: Customer package training mode updates
 *
 *    Rev 1.3   16 Nov 2001 10:32:14   baa
 * Cleanup code & implement new security model on customer
 * Resolution for POS SCR-263: Apply new security model to Customer Service
 *
 *    Rev 1.2   05 Nov 2001 17:36:42   baa
 * Code Review changes. Customer, Customer history Inquiry Options
 * Resolution for POS SCR-244: Code Review  changes
 *
 *    Rev 1.1   23 Oct 2001 16:53:02   baa
 * updates for customer history and for getting rid of CustomerMasterCargo.
 * Resolution for POS SCR-209: Customer History
 *
 *    Rev 1.0   Sep 21 2001 11:14:54   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:06:44   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.customer.common;

import java.text.DecimalFormat;
import java.util.Locale;

import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.common.utility.LocalizedCodeIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.arts.CustomerWriteDataTransaction;
import oracle.retail.stores.domain.arts.DataManagerMsgIfc;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.customer.CustomerGroupIfc;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.discount.DiscountRuleIfc;
import oracle.retail.stores.domain.utility.AddressIfc;
import oracle.retail.stores.domain.utility.EmailAddressIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.domain.utility.PhoneConstantsIfc;
import oracle.retail.stores.domain.utility.PhoneIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.journal.JournalableIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.utility.JournalConstantsIfc;

/**
 * Site to save customer information to the database.
 *
 * $Revision: /rgbustores_13.4x_generic_branch/6 $
 */
public class SaveCustomerSite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = 1529068405858001412L;

    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/6 $";

    /**
     * customer sequence number property value
     */
    public static final String CUSTOMER_SEQUENCE_NUMBER_LENGTH_PROPERTY_NAME = "CustomerIDSequenceNumberLength";

    /**
     * customer sequence number default lenght
     */
    public static final int CUSTOMER_DEFAULT_SEQUENCE_NUMBER_LENGTH = 6;

    /**
     * customer sequence number format pattern
     */
    protected static final String FORMAT_PATTERN_SOURCE = "0000000000000000000000000000000000000000";

    /**
     * customer sequence number default lenght
     */
    protected static int sequenceNumberLength = CUSTOMER_DEFAULT_SEQUENCE_NUMBER_LENGTH;

    private static Locale journalLocale = LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL);

    /**
     * customer sequence number formatter
     */
    protected DecimalFormat formatter = null;

    /**
     * customer sequence number
     */
    protected int sequenceNumber = 0;

    /**
     * Saves customer data to the database.
     *
     * @param bus Service Bus
     */
    @Override
    public void arrive(BusIfc bus)
    {
        String letterName = CommonLetterIfc.CONTINUE;
        boolean noErrors = true;
        // get the customer to save to the database, don't save in training mode.
        CustomerCargo cargo = (CustomerCargo)bus.getCargo();
        if (cargo.getRegister().getWorkstation().isTrainingMode() == false)
        {
            CustomerIfc customer = cargo.getCustomer();
            CustomerIfc originalCustomer = cargo.getOriginalCustomer();

            // attempt to do the database update
            try
            {
                String transactionName = null;
                StringBuilder jString = new StringBuilder();
                Object[] dataArgs = new Object[2];

                // This is an add. There is no original customer in cargo.
                if (cargo.isNewCustomer())
                {
                    transactionName = CustomerWriteDataTransaction.dataAddName;
                }
                else
                // This is an update.
                {
                    transactionName = CustomerWriteDataTransaction.dataUpdateName;
                }

                // We need to save the customer details first.
                // In order to get CustID, save the customer.
                CustomerWriteDataTransaction ct = (CustomerWriteDataTransaction)DataTransactionFactory
                        .create(DataTransactionKeys.CUSTOMER_WRITE_DATA_TRANSACTION);
                ct.setTransactionName(transactionName);

                if (cargo.isNewCustomer())
                {
                    // if new customer then only generate a new customer id
                    String storeId = cargo.getRegister().getWorkstation().getStoreID();
                    String workStationId = cargo.getRegister().getWorkstation().getWorkstationID();
                    sequenceNumber = cargo.getRegister().getNextCustomerSequenceNumber();
                    cargo.getRegister().setLastCustomerSequenceNumber(sequenceNumber);
                    // generate a unique customer id
                    String customerId = storeId + workStationId + getFormattedCustomerSequenceNumber();
                    customer.setCustomerID(customerId);

                    // Journal Customer information
                    dataArgs[0] = getNewCustomerJournalString(customer);
                    jString.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                            JournalConstantsIfc.ADD_CUSTOMER_LABEL, dataArgs));
                    letterName = "NewCustomerAdded";

                }
                else
                // This is an update.
                {
                    if (originalCustomer != null)
                    {
                        dataArgs[0] = customer.getCustomerID().trim();
                        jString.append(Util.EOL);
                        jString.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                                JournalConstantsIfc.FIND_CUSTOMER_LABEL, dataArgs));
                        jString.append(Util.EOL);
                        jString.append(CustomerUtilities.getChangedCustomerData(originalCustomer, customer));
                    }
                }

                ct.saveCustomer(customer);
                cargo.setCustomer(customer);

                // if new customer update customer sequence number
                if (cargo.isNewCustomer())
                {
                    ct.updateCustomerSequenceNumber(cargo.getRegister());
                }

                // get the Journal manager
                JournalManagerIfc jmi = (JournalManagerIfc)Gateway.getDispatcher().getManager(JournalManagerIfc.TYPE);
                if (jmi != null)
                {
                    jmi.setEntryType(JournalableIfc.ENTRY_TYPE_CUST);
                    jmi.journal(cargo.getEmployeeID(), cargo.getTransactionID(), jString.toString());

                }
                else
                {
                    logger.error("No journal manager found!");
                }

            }
            catch (DataException e)
            {
                logger.error("Unable to save customer \"" + customer.getCustomerID() + "\".", e);

                // check for database connection error
                int errorCode = e.getErrorCode();
                noErrors = false;
                // cannot link if customer was not added to the database
                cargo.setLink(false);
                cargo.setDataExceptionErrorCode(errorCode);
                showErrorDialog(bus, errorCode);
            }

        }// end (isTrainingMode() == false)
        if (noErrors)
        {
          bus.mail(new Letter(letterName), BusIfc.CURRENT);
        }
    }


    /**
     * Get formatted customer sequence number string.
     *
     * @return formatted customer sequence number string
     */
    public String getFormattedCustomerSequenceNumber()
    {
        // format to required number of digits
        DecimalFormat df = getFormatter(getSequenceNumberFormatPattern());
        String strSequence = df.format(sequenceNumber);
        return (strSequence);
    }

    /**
     * Get pattern for customer sequence number string.
     *
     * @return formatted customer sequence number length
     */
    protected String getSequenceNumberFormatPattern()
    {
        return (getFormatPattern(getSequenceNumberLength()));
    }

    /**
     * Get decimal formatter for customer sequence number string.
     *
     * @return formatter for customer sequence number
     */
    protected DecimalFormat getFormatter(String pattern)
    {
        if (formatter == null)
        {
            formatter = new DecimalFormat(pattern);
        }
        else
        {
            formatter.applyPattern(pattern);
        }
        return (formatter);
    }

    /**
     * Get customer sequence number length.
     *
     * @return sequence number length
     */
    public static int getSequenceNumberLength()
    {
        setFormattingSpecifications();
        return (sequenceNumberLength);
    }

    /**
     * Get formatter pattern for customer sequence number.
     *
     * @return formatted customer sequence number string
     */
    protected String getFormatPattern(int len)
    {
        return (FORMAT_PATTERN_SOURCE.substring(0, len));
    }

    /**
     * Get formatted specification for customer sequence number.
     *
     * @return formatted customer sequence number string
     */
    protected static void setFormattingSpecifications()
    {
        String sequenceNumberLengthProperty = DomainGateway.getProperty(CUSTOMER_SEQUENCE_NUMBER_LENGTH_PROPERTY_NAME,
                Integer.toString(CUSTOMER_DEFAULT_SEQUENCE_NUMBER_LENGTH));
        setSequenceNumberLength(Integer.parseInt(sequenceNumberLengthProperty));
    }

    /**
     * Get customer sequence number length.
     *
     * @return customer sequenceNumberLength number length
     */
    protected static void setSequenceNumberLength(int value)
    {
        sequenceNumberLength = value;
    }

    /**
     * Returns all changes made to customer object
     *
     * @param customer the new customer information
     * @return string with data
     */
    public String getNewCustomerJournalString(CustomerIfc customer)
    {
        StringBuilder jString = new StringBuilder("");
        Object[] dataArgs = new Object[2];

        // test every field
        if (customer.getCustomerID() != null && !customer.getCustomerID().equals(""))
        {
            dataArgs[0] = customer.getCustomerID();
            jString.append(Util.EOL).append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.CUSTOMERID_LABEL, dataArgs));
        }
        if (customer.getEmployeeID() != null && !customer.getEmployeeID().equals(""))
        {
            dataArgs[0] = customer.getEmployeeID();
            jString.append(Util.EOL).append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.EMPLOYEE_ID_LABEL, dataArgs));
        }
        if (customer.getFirstName() != null && !customer.getFirstName().equals(""))
        {
            dataArgs[0] = customer.getFirstName();
            jString.append(Util.EOL).append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.FIRST_NAME_LABEL, dataArgs));
        }
        if (customer.isBusinessCustomer() && customer.getLastName() != null && !customer.getLastName().equals(""))
        {
            dataArgs[0] = customer.getLastName();
            jString.append(Util.EOL).append(
                    I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                            JournalConstantsIfc.BUSINESS_CUSTOMER_NAME_LABEL, dataArgs));
        }
        else if (customer.getLastName() != null && !customer.getLastName().equals(""))
        {
            dataArgs[0] = customer.getLastName();
            jString.append(Util.EOL)
                    .append(
                            I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.LAST_NAME_LABEL,
                                    dataArgs));
        }
        if (customer.getAddresses() != null)
        {
            for (AddressIfc address : customer.getAddresses())
            {
                int lineCount = 0;
                // loop through all address lines
                for (String addrLine : address.getLines())
                {
                    lineCount++;
                    if (addrLine != null && !addrLine.trim().equals(""))
                    {
                        dataArgs[0] = lineCount;
                        dataArgs[1] = addrLine;
                        jString
                                .append(Util.EOL)
                                .append(
                                        I18NHelper
                                                .getString(
                                                        I18NConstantsIfc.EJOURNAL_TYPE,
                                                        JournalConstantsIfc.ADDRESS_LINE_TAG_LABEL,
                                                        dataArgs));
                    }
                }
                dataArgs[0] = address.getCity();
                jString.append(Util.EOL).append(
                        I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                                JournalConstantsIfc.CITY_LABEL, dataArgs));
                dataArgs[0] = address.getCountry();
                jString.append(Util.EOL).append(
                        I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                                JournalConstantsIfc.COUNTRY_LABEL, dataArgs));
                dataArgs[0] = address.getState();
                jString.append(Util.EOL).append(
                        I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                                JournalConstantsIfc.STATE_LABEL, dataArgs));
                dataArgs[0] = address.getPostalCode();
                jString.append(Util.EOL).append(
                        I18NHelper
                                .getString(I18NConstantsIfc.EJOURNAL_TYPE,
                                        JournalConstantsIfc.POSTAL_CODE_LABEL,
                                        dataArgs));
                if (address.getPostalCodeExtension() != null
                        && !address.getPostalCodeExtension().trim().equals(""))
                    jString.append(" - " + address.getPostalCodeExtension());

            }
        }

        // find customer tax id
        if (customer.getEncipheredTaxID() != null
            && customer.getEncipheredTaxID().getMaskedNumber() != null
            && !customer.getEncipheredTaxID().getMaskedNumber().equals(""))
        {

            dataArgs[0] = customer.getEncipheredTaxID().getMaskedNumber();
            jString.append(Util.EOL)
                    .append(
                            I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.CUSTOMER_TAX_ID,
                                    dataArgs));

        }

        // set customer pricing group
        if (customer.getPricingGroupID() != null)
        {
            dataArgs[0] = customer.getPricingGroupID();
            jString.append(Util.EOL)
                    .append(
                            I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.CUSTOMER_PRICING_GROUP,
                                    dataArgs));
        }

        // loop through all the phone numbers
        for (int i = 0; i < PhoneConstantsIfc.PHONE_TYPE_DESCRIPTOR.length; i++)
        {
            PhoneIfc phone = customer.getPhoneByType(i);
            if (phone != null)
            {
                jString.append(phone.toJournalString(LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL)));
            }
        }

        // find email address
        EmailAddressIfc emailAddress = customer.getEmailAddress(0);
        if ((emailAddress != null)&& !(emailAddress.getEmailAddress().trim()).equals(""))
        {
            String email = emailAddress.getEmailAddress();
            if (email != null && !email.trim().equals(""))
                dataArgs[0] = email;
            jString.append(Util.EOL).append(
                    I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
                            JournalConstantsIfc.EMAIL_ADDRESS_LABEL, dataArgs));
        }

        // Get associated discount
        CustomerGroupIfc[] groups = customer.getCustomerGroups();
        DiscountRuleIfc[] rules = null;
        if (groups != null)
        {
            for (int i = 0; i < groups.length; i++)
            {
                rules = groups[i].getDiscountRules();
                if (rules != null && rules.length > 0)
                {
                    String discount = rules[0].getName(LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL));
                    if (discount != null && !discount.trim().equals(""))
                    {
                        dataArgs[0] = discount;
                        jString.append(Util.EOL).append(
                                I18NHelper.getString(
                                        I18NConstantsIfc.EJOURNAL_TYPE,
                                        JournalConstantsIfc.DISCOUNT_LABEL,
                                        dataArgs));
                    }
                }
            }
        }

        // set tax certificate
        if (customer.getEncipheredTaxCertificate() != null && !customer.getEncipheredTaxCertificate().getDecryptedNumber().equals(""))
        {
            dataArgs[0] = customer.getEncipheredTaxCertificate().getMaskedNumber();
            jString.append(Util.EOL)
                    .append(
                            I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.CUSTOMER_TAX_CERTF,
                                    dataArgs));
        }

        // set tax exemption reason
        LocalizedCodeIfc taxExemptReason = customer.getTaxExemptionReason();
        if (taxExemptReason != null && !taxExemptReason.getText(journalLocale).equals(""))
        {
            if(taxExemptReason.getText(journalLocale) !=null && !taxExemptReason.getText(journalLocale).equals(""))
            {
                dataArgs[0] = taxExemptReason.getText(journalLocale) ;
                jString.append(Util.EOL)
                .append(
                        I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.CUSTOMER_EXEMPTION_REASON,
                                dataArgs));
            }
        }

        return (jString.toString());
    }

    /**
     * Show error screen
     *
     * @param bus the bus
     * @param error the error code
     */
    public void showErrorDialog(BusIfc bus, int error)
    {

        String msg[] = new String[2];
        UtilityManagerIfc utility =
          (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
        msg[0] = utility.getErrorCodeString(error);
        msg[1] = utility.retrieveDialogText("DATABASE_ERROR.Contact", DataManagerMsgIfc.CONTACT);

        // display dialog
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        // set  model and display error msg
        UIUtilities.setDialogModel(ui,DialogScreensIfc.ERROR,"CustomerError",msg,CommonLetterIfc.CANCEL);
    }
}