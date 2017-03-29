/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/utility/tdo/TaxTDO.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:41 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    cgreen 05/26/10 - convert to oracle packaging
 *    abonda 01/03/10 - update header date
 *    cgreen 05/15/09 - optimize performance of
 *                      createTaxGroupTaxRuleMappingForStore
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.utility.tdo;

import java.util.Hashtable;
import java.util.Vector;

import oracle.retail.stores.pos.tdo.TDOAdapter;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.ReadNewTaxRuleTransaction;
import oracle.retail.stores.domain.arts.StoreDirectoryDataTransaction;
import oracle.retail.stores.domain.store.StoreIfc;
import oracle.retail.stores.domain.tax.NewTaxRuleIfc;
import oracle.retail.stores.domain.tax.TaxRulesVO;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.utility.BaseException;

/**
 * @author epd
 */
public class TaxTDO extends TDOAdapter
{
    /**
     * Creates the tax group rule mappings for the supplied storeID.  This method
     * will try and find the state for the supplied storeID.  The assumption is that 
     * the store state is not known.
     * @param storeID
     * @return
     * @throws BaseException
     * @deprecated as of 13.1, use {@link #createTaxGroupTaxRuleMappingForStore(String, String)} instead
     */
    public Hashtable<Integer, Vector<NewTaxRuleIfc>> createTaxGroupTaxRuleMappingForStore(String storeID)
    throws BaseException
    {
        // 1) Get state for provided store ID
        String storeState = getStoreState(storeID);

        // 2) Create tax group tax rule mappings
        return createTaxGroupTaxRuleMappingForStore(storeID, storeState);
    }
    
    /**
     * Creates the tax group rule mappings for the supplied store and store
     * state
     * 
     * @param storeID The store ID of the current store
     * @param storeState The state in which the store resides
     * @return a Hashtable of tag group tag rule mappings
     * @throws BaseException
     */
    public Hashtable<Integer, Vector<NewTaxRuleIfc>> createTaxGroupTaxRuleMappingForStore(String storeID,
            String storeState) throws BaseException
    {
        ReadNewTaxRuleTransaction taxRuleTransaction = null;

        taxRuleTransaction = (ReadNewTaxRuleTransaction) DataTransactionFactory
                .create(DataTransactionKeys.READ_NEW_TAX_RULE_TRANSACTION);

        TaxRulesVO taxRulesVO = null;
        try
        {
            taxRulesVO = taxRuleTransaction.getTaxRulesByStore(storeID);
        }
        catch (DataException e)
        {
            throw new BaseException("Could not read tax rules", e);
        }
        
        Hashtable<Integer, Vector<NewTaxRuleIfc>> taxGroupTaxRulesMapping = new Hashtable<Integer, Vector<NewTaxRuleIfc>>();

        if (taxRulesVO != null && taxRulesVO.hasTaxRules())
        {
            NewTaxRuleIfc[] taxRules = taxRulesVO.getAllTaxRules();
            // Collect the unique tax groups from the result read from database.
            // Use the hashtable to hold these unique tax groups as keys.
            for (int i = taxRules.length - 1; i >= 0; i--)
            {
                int taxGroupKey = taxRules[i].getTaxGroupID();
                Vector<NewTaxRuleIfc> taxRulesByGroup = taxGroupTaxRulesMapping.get(taxGroupKey);
                if (taxRulesByGroup == null)
                {
                    taxRulesByGroup = new Vector<NewTaxRuleIfc>();
                    taxGroupTaxRulesMapping.put(taxGroupKey, taxRulesByGroup);
                }
                taxRulesByGroup.add(taxRules[i]);
            }
        }

        return taxGroupTaxRulesMapping;
    }

    /**
     * Given a storeID, this method attempts to find and return the state in
     * which that store resides
     * 
     * @param storeID
     * @throws BaseException Thrown when we cannot find the store or a database
     *             error occurs.
     * @deprecated as of 13.1, No replacement provided.
     */
    protected String getStoreState(String storeID) throws BaseException
    {
        String state = "";
        try
        {
            StoreDirectoryDataTransaction sdTxn = null;
            
            sdTxn = (StoreDirectoryDataTransaction) DataTransactionFactory.create(DataTransactionKeys.STORE_DIRECTORY_DATA_TRANSACTION);
            
            StoreIfc[] stores = sdTxn.retrieveAllStores();
            // loop through the stores to find the one you want
            StoreIfc store = null;
            for (int i = 0; i < stores.length; i++)
            {
                store = stores[i];
                if (store.getStoreID().equals(storeID))
                {
                    break;
                }
            }
            
            if (store == null)
            {
                String msg = "Store #" + storeID + " does not exist in the database";
                getLogger().warn(msg);
                throw new BaseException(msg);
            }
            
            state = store.getAddress().getState();
        }
        catch (DataException e)
        {
            String msg = "A database error occurred trying to read store data";
            getLogger().warn(msg, e);
            throw new BaseException(msg, e);
        }
        
        return state;
    }

}
