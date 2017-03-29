/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/register/VirtualRegisterADOIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:42 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.register;

import org.apache.log4j.Logger;

import oracle.retail.stores.pos.ado.ADOIfc;
import oracle.retail.stores.pos.ado.employee.EmployeeADOIfc;
import oracle.retail.stores.pos.ado.journal.RegisterJournalIfc;
import oracle.retail.stores.pos.ado.store.StoreADOIfc;
import oracle.retail.stores.pos.ado.transaction.RetailTransactionADOIfc;
import oracle.retail.stores.pos.ado.transaction.TransactionPrototypeEnum;
import oracle.retail.stores.domain.tender.TenderLimitsIfc;
import oracle.retail.stores.domain.utility.EYSDomainIfc;

/**
 * Interface for VirtualRegisterADO. Specifies the methods the application uses
 * to interact with the register
 */
public interface VirtualRegisterADOIfc extends ADOIfc
{
    /**
     * @see oracle.retail.stores.ado.ADOIfc#fromLegacy(oracle.retail.stores.domain.utility.EYSDomainIfc)
     */
    public void fromLegacy(EYSDomainIfc rdo);

    /**
     * Returns the underlying rdo RegisterIfc
     * 
     * @return RegisterIfc
     */
    public EYSDomainIfc toLegacy();

    /**
     * @param type
     *            The RDO object corresponding to the desired type.
     * @return an RDO.
     */
    public EYSDomainIfc toLegacy(Class type);

    /**
     * Reads the financial information from persistent storage and refreshes
     * the internal register state
     * 
     * @exception RegisterException
     *                is thrown on error
     */
    public void readFinancials() throws RegisterException;

    /**
     * Saves the financial information to persistent storage.
     * 
     * @exception RegisterException
     *                thrown on error
     */
    public void saveFinancials() throws RegisterException;

    /**
     * Returns a reference to the associated store
     * 
     * @return reference to store
     */
    public StoreADOIfc getStore();

    /**
     * Returns a reference to the associated journal
     * 
     * @return journal
     */
    public RegisterJournalIfc getJournal();

    /**
     * Returns a reference to the associated journal
     * 
     * @return journal
     */

    /**
     * Sets the current operator
     * 
     * @param operator
     *            current operator of register
     */
    public void setOperator(EmployeeADOIfc operator);

    /**
     * Returns a reference to the current operator
     */
    public EmployeeADOIfc getOperator();

    /**
     * Returns the status of the till for the current operator
     * 
     * @return TillStatus
     */
    public TillStatus getOperatorTillStatus() throws RegisterException;

    /**
     * Factory method for creating transactions based on the specified
     * prototype
     * 
     * @param tranType
     *            specifies the type of the transaction
     * @return reference to created transaction
     */
    public RetailTransactionADOIfc createTransaction(TransactionPrototypeEnum tranType)
        throws RegisterException;

    /**
     * Sets the Tender limits
     * 
     * @param TenderLimitsIfc
     *            object
     */
    public void setTenderLimits(TenderLimitsIfc limits);
    //throws RegisterException;

    /**
     * Returns the Tender limits
     * 
     * @return TenderLimitsIfc object
     */
    public TenderLimitsIfc getTenderLimits(); //throws RegisterException;

    /**
     * Sets identifier of last reprintable transaction.
     * <P>
     * 
     * @param value
     *            new identifier of last reprintable transaction
     */
    public void setLastReprintableTransactionID(String value);

    /**
     * Returns identifier of last reprintable transaction.
     * <P>
     * 
     * @return identifier of last reprintable transaction
     */
    public String getLastReprintableTransactionID();

    /**
     * Returns Logger instance. FIX THIS - shouldn't return a logger from an
     * interface with log4j!
     * <P>
     * 
     * @return logger (from super class)
     */
    public Logger getLogger();
}
