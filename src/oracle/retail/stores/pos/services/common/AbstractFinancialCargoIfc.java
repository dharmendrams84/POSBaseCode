/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/AbstractFinancialCargoIfc.java /main/15 2011/02/16 09:13:32 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    nkgautam  09/20/10 - refractored code to use a single class for checking
 *                         cash in drawer
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         1/25/2006 4:10:47 PM   Brett J. Larsen merge
 *          7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
 *    3    360Commerce 1.2         3/31/2005 4:27:06 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:19:26 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:09:19 PM  Robert Pearse   
 *:
 *    4    .v700     1.2.1.0     11/15/2005 14:57:22    Jason L. DeLeau 4204:
 *         Remove duplicate instances of UserAccessCargoIfc
 *    3    360Commerce1.2         3/31/2005 15:27:06     Robert Pearse
 *    2    360Commerce1.1         3/10/2005 10:19:26     Robert Pearse
 *    1    360Commerce1.0         2/11/2005 12:09:19     Robert Pearse
 *
 *   Revision 1.6  2004/09/15 16:34:22  kmcbride
 *   @scr 5881: Deprecating parameter retrieval logic in cargo classes and logging parameter exceptions
 *
 *   Revision 1.5  2004/06/10 23:06:36  jriggins
 *   @scr 5018 Added logic to support replacing PriceAdjustmentLineItemIfc instances in the transaction which happens when shuttling to and from the pricing service
 *
 *   Revision 1.4  2004/06/07 14:58:49  jriggins
 *   @scr 5016 Added logic to persist previously entered transactions with price adjustments outside of the priceadjustment service so that a user cannot enter the same receipt multiple times in a transaction.
 *
 *   Revision 1.3  2004/02/12 16:48:00  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:19:59  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.1   08 Nov 2003 01:00:16   baa
 * cleanup -sale refactoring
 *
 *    Rev 1.0   Nov 05 2003 22:50:40   cdb
 * Initial revision.
 * Resolution for 3430: Sale Service Refactoring
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

import java.io.Serializable;
import java.util.Map;

import oracle.retail.stores.pos.ado.transaction.RetailTransactionADOIfc;
import oracle.retail.stores.pos.services.admin.security.common.UserAccessCargoIfc;
import oracle.retail.stores.domain.customer.CustomerInfoIfc;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.financial.StoreStatusIfc;
import oracle.retail.stores.domain.tender.TenderLimitsIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.tour.application.tourcam.TourCamIfc;
import oracle.retail.stores.pos.services.common.WriteHardTotalsCargoIfc;

/**
 * This abstract class carries the financial data used through much of the
 * application. This consists of the storeStatus class and the Register class,
 * which also includes all the tills and financial totals.
 * 
 * @version $Revision: /main/15 $
 */
public interface AbstractFinancialCargoIfc extends UserAccessCargoIfc, Serializable,
        DBErrorCargoIfc, TourCamIfc, WriteHardTotalsCargoIfc
{
    /**
     * revision number supplied by source-code-control system
     */
    public static final String revisionNumber = "$Revision: /main/15 $";

    /**
     * Name used to look up constant text for the STORE_REGISTER_TILL_CLOSED
     * confirmation screen.
     */
    public static final String STORE_REGISTER_TILL_CLOSED = "StoreRegisterTillClosed";
    public static final String TILL_SUSPENDED             = "TillSuspended";
    public static final String CASHIER_NO_TILL            = "TillCashierHasNoTillAssignedError";
    public static final String TILL_OPEN_DRAWER_EMPTY     = "TillOpenDrawerEmpty";

    /**
     * Sets the store status.
     * 
     * @param value The store status.
     **/
    public void setStoreStatus(StoreStatusIfc value);

    /**
     * Sets the register object.
     * 
     * @param value The register object.
     **/
    public void setRegister(RegisterIfc value);

    /**
     * Sets reference to tender limits interface.
     * 
     * @param value reference to tender limits interface
     **/
    public void setTenderLimits(TenderLimitsIfc value);

    /**
     * Retrieves reference to tender limits interface.
     * 
     * @return reference to tender limits interface
     **/
    public TenderLimitsIfc getTenderLimits();

    /**
     * Gets the prompt Enter ID prompt text for this service. Returning a null
     * will allow the default prompt text to be displayed.
     * 
     * @return the prompt text.
     **/
    public String getOperatorIdPromptText();

    /**
     * Sets the prompt text for this service.
     * 
     * @param value The prompt text.
     **/
    public void setOperatorIdPromptText(String value);

    /**
     * Gets the screen name for this service. Returning a null will allow the
     * default screen name to be displayed.
     * 
     * @return the screen name.
     **/
    public String getOperatorIdScreenName();

    /**
     * Sets identifier of last reprintable transaction.
     * 
     * @param value new identifier of last reprintable transaction
     **/
    public void setLastReprintableTransactionID(String value);

    /**
     * Returns identifier of last reprintable transaction.
     * 
     * @return identifier of last reprintable transaction
     **/
    public String getLastReprintableTransactionID();

    /**
     * Sets the screen name for this service.
     * 
     * @param value The screen name.
     **/
    public void setOperatorIdScreenName(String value);

    /**
     * Returns the customer info.
     * 
     * @return The customer info
     **/
    public CustomerInfoIfc getCustomerInfo();

    /**
     * Sets the customer info.
     * 
     * @param CustomerInfo the customer info
     **/
    public void setCustomerInfo(CustomerInfoIfc customerInfo);

    /**
     * Returns a string representation of the attributes of this object. This
     * will be used by the class extended by this class for their
     * abstractToString() methods.
     * 
     * @return String representation of attributes of this object
     **/
    public String abstractToString();

    /**
     * @return
     */
    public RetailTransactionADOIfc getCurrentTransactionADO();

    /**
     * @param currentTransactionADO
     */
    public void setCurrentTransactionADO(RetailTransactionADOIfc currentTransactionADO);

    /**
     * Returns the originalPriceAdjustmentTransactions Map.
     * 
     * @return The originalPriceAdjustmentTransactions Map.
     */
    public Map<String,SaleReturnTransactionIfc> getOriginalPriceAdjustmentTransactions();

    /**
     * Sets the originalPriceAdjustmentTransactions Map
     * 
     * @param originalPriceAdjustmentTransactions The
     *            originalPriceAdjustmentTransactions to set.
     */
    public void setOriginalPriceAdjustmentTransactions(Map<String,SaleReturnTransactionIfc> originalPriceAdjustmentTransactions);

    /**
     * Resets the originalPriceAdjustmentTransactions Map
     */
    public void resetOriginalPriceAdjustmentTransactions();

    /**
     * Adds a new transaction to the originalPriceAdjustmentTransaction map.
     * 
     * @param transaction the transaction to add
     */
    public void addOriginalPriceAdjustmentTransaction(SaleReturnTransactionIfc transaction);

    /**
     * Removes the original price adjustment transaction from the map that
     * corresponds to the provided transaction ID
     * 
     * @param transactionID Transaction ID to use as a key
     * @return A SaleReturnTransactionIfc which corresponds to the removed
     *         transaction or null if no match was found.
     */
    public SaleReturnTransactionIfc removeOriginalPriceAdjustmentTransaction(String transactionID);

    /**
     * Gets the cash drawer under warning boolean
     * 
     * @return
     */
    public boolean isCashDrawerUnderWarning();

    /**
     * sets the Cash drawer under warning boolean
     * 
     * @param cashDrawerUnderWarning
     */
    public void setCashDrawerUnderWarning(boolean cashDrawerUnderWarning);
}
