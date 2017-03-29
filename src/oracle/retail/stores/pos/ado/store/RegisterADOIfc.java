/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/store/RegisterADOIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:42 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    1    360Commerce 1.0         5/6/2008 8:33:51 PM    Michael P. Barnett
 *         Initial Revision.  Code reviewed by Anda Cadar.
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.store;

import java.io.Serializable;
import java.util.Locale;

import oracle.retail.stores.common.utility.LocaleRequestor;
import oracle.retail.stores.pos.ado.employee.EmployeeADOIfc;
import oracle.retail.stores.pos.ado.journal.RegisterJournalIfc;
import oracle.retail.stores.pos.ado.transaction.RetailTransactionADOIfc;
import oracle.retail.stores.pos.ado.transaction.TransactionPrototypeEnum;
import oracle.retail.stores.domain.customer.CustomerInfoIfc;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.financial.ShippingMethodIfc;
import oracle.retail.stores.domain.tender.TenderLimitsIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.pos.device.POSDeviceActions;

public interface RegisterADOIfc
{
    /**
     * Gets the current workstation number
     *
     * @return the current workstation number
     */
    public String getWorkstationID();

    /**
     * Returns the ID of the currently active till
     *
     * @return the Current Till ID
     */
    public String getCurrentTillID();

    //--------------------------------------------------------------
    /**
     * Returns the next unique ID
     * @return the next unique ID
     */
    //--------------------------------------------------------------
    public String getNextUniqueID();

    /**
     * Get instance of the RegisterJournalIfc. This method caches the instance,
     * which is then returned on future invocations.
     *
     * @return a RegisterJournalIfc instance.
     */
    public RegisterJournalIfc getRegisterJournal();

    /**
     * Registers can be in different modes at any one time. This method simply
     * returns a boolean for a given mode to test to see if the register is
     * currently in the requested mode.
     *
     * @param mode
     *            The mode for which we want the current status.
     * @return The status of the requested mode.
     */
    public boolean isInMode(RegisterMode mode);

    /**
     * Returns an array of all the currently active RegisterModes.
     *
     * @return
     */
    public RegisterMode[] getRegisterModes();

    /**
     * Initialize a txn with Register information. Encapsulated here due to
     * work that must be done with the RDO objects.
     *
     * @param txn
     *            The transaction to be initialized.
     */
    public void initializeTransaction(RetailTransactionADOIfc txn);

    /**
     * @return
     */
    public StoreADO getStoreADO();

    /**
     * @param storeADO
     */
    public void setStoreADO(StoreADO storeADO);

    /**
     * Attempts to load an ADO transaction from persistent storage
     *
     * @param transactionID
     *            String value of desired transaction ID
     * @param LocaleReq the locales to load the transaction
     * @return a retrieved transaction instance
     */
    public RetailTransactionADOIfc loadTransaction(LocaleRequestor localeReq, String transactionID) throws DataException;

    /**
     * Attempts to load an ADO transaction from persistent storage
     *
     * @param transactionID
     *            String value of desired transaction ID
     * @return a retrieved transaction instance
     * @deprecated As of 13.1 Use {@link RegisterADOIfc#loadTransaction(LocaleRequestor, String)}
     */
    public RetailTransactionADOIfc loadTransaction(String transactionID) throws DataException;


    /**
     * Creates and initializes a new ADO transaction based on input parameters.
     * NOTE: Be sure only to use this when creating a NEW transaction. Do not
     * use this if, for example, an RDO transaction already exists and an ADO
     * txn needs to be built from that RDO because the initialization routine
     * will assign a new transaction number, but the RDO transaction already
     * has one.
     *
     * @param attributes
     *            Map containing information needed to generate transaction
     * @param customerInfoRDO
     *            The customer info information for this transaction.
     * @param operatorRDO
     *            The current cashier/operator.
     * @return a new ADO transaction instance
     */
    public RetailTransactionADOIfc createTransaction(
        TransactionPrototypeEnum type,
        CustomerInfoIfc customerInfoRDO,
        EmployeeIfc operatorRDO);

    /**
     * Add the transaction to the RDO register to make sure totals are updated
     *
     * @param txnADO
     *            The transaction to be added
     */
    public void addTransaction(RetailTransactionADOIfc txnADO);

    /**
     * Establish POSDeviceActions before calling private
     * writeHardTotals(POSDevicActions). This change was made to expose
     * POSDeviceActions for unit testing.
     *
     * @throws DeviceException
     *             Thrown when
     */
    public void writeHardTotals() throws DeviceException;

    /**
     * The register, which contains all the information needed, should be
     * responsible for writing the hard totals
     *
     * @throws DeviceException
     *             Thrown when
     */
    public void writeHardTotals(POSDeviceActions pda) throws DeviceException;

    /**
     * @return TenderLimits reference
     */
    public TenderLimitsIfc getTenderLimits();

    /**
     * @param ifc
     *            reference to TenderLimits
     */
    public void setTenderLimits(TenderLimitsIfc ifc);

    //    ---------------------------------------------------------------------
    /**
     * Sets identifier of last reprintable transaction.
     * <P>
     *
     * @param value
     *            new identifier of last reprintable transaction
     */
    //---------------------------------------------------------------------
    public void setLastReprintableTransactionID(String value);

    /**
     * Returns identifier of last reprintable transaction.
     * <P>
     *
     * @return identifier of last reprintable transaction
     */
    public String getLastReprintableTransactionID();
    /**
     * @return
     */
    public EmployeeADOIfc getOperator();

    /**
     * @param ifc
     */
    public void setOperator(EmployeeADOIfc ifc);

    /**
     * @return data
     */
    public Serializable getHardTotalsStream();

    /**
     * @param data
     */
    public void setHardTotalsStream(Serializable data);
}
