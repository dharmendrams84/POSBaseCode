/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/printing/CustomerSurveyRewardIfc.java /main/12 2011/02/16 09:13:31 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   11/13/08 - deprecate getSurveyText in favor of isSurveyExpected
 *                         and editing Survey.bpt
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:38 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:42 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:24 PM  Robert Pearse   
 *
 *   Revision 1.1  2004/06/14 17:44:56  mweis
 *   @scr 5578 Customer Survey / Reward needs to have an interface
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.printing;

import oracle.retail.stores.pos.appmanager.ApplicationManagerIfc;
import oracle.retail.stores.common.parameter.ParameterConstantsIfc;
import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
import oracle.retail.stores.foundation.tour.service.SessionBusIfc;

/**
 * Determines if it is time to enable the printing of the customer survey/reward
 * text on the (customer's) receipt.
 * <p>
 * If the {@link #getSurvey(SessionBusIfc, TenderableTransactionIfc) getSurvey}
 * method returns a non-null and non-empty survey, then it is time to print the
 * survey as part of the receipt.
 * <p>
 * The actual printing of the survey/reward is left to the receipt class itself.
 * For example, the {@link oracle.retail.stores.pos.receipt.SaleReceipt} is
 * capable of printing a survey/reward on its receipt. $Revision: /main/12 $
 */
public interface CustomerSurveyRewardIfc extends ApplicationManagerIfc
{
    // Public fields
    /**
     * Name of this manager. Used as a key to ManagerFactory.
     */
    public static final String MANAGER_NAME = "CustomerSurveyReward";

    /**
     * Parameter indicating whether we are even allowing any surveys/rewards.
     */
    public static final String CUSTOMER_SURVEY_REWARD_AVAILABLE = ParameterConstantsIfc.CUSTOMER_CustomerSurveyRewardAvailable;

    /**
     * Parameter indicating the starting date for the survey/reward. The date is
     * assumed to be YYYY-MM-DD format.
     */
    public static final String CUSTOMER_SURVEY_REWARD_START = ParameterConstantsIfc.CUSTOMER_CustomerSurveyRewardStart;

    /**
     * Parameter indicating the ending date for the survey/reward. The date is
     * assumed to be YYYY-MM-DD format.
     */
    public static final String CUSTOMER_SURVEY_REWARD_END = ParameterConstantsIfc.CUSTOMER_CustomerSurveyRewardEnd;

    /**
     * Parameter indicating the method used to determine when a survey/reward is
     * offered.
     * <ul>
     * <li>{@link #METHOD_TRANS_AMOUNT} <li>{@link #METHOD_N_NUMBER_TRANS}
     * </ul>
     */
    public static final String CUSTOMER_SURVEY_REWARD_METHOD = ParameterConstantsIfc.CUSTOMER_CustomerSurveyRewardMethod;

    /**
     * The survey/reward method based on the grand total amount of the current
     * transaction. The minimum amount is indicated by the
     * {@link #CUSTOMER_SURVEY_REWARD_TRANS_AMOUNT} parameter.
     */
    public static final String METHOD_TRANS_AMOUNT = ParameterConstantsIfc.CUSTOMER_CustomerSurveyRewardTransactionAmount_TRANSACTIONAMOUNT;

    /**
     * The survey/reward method based on every 'n' number of (sale)
     * transactions.
     */
    public static final String METHOD_N_NUMBER_TRANS = "nNumberOfTransactions";

    /**
     * Parameter indicating the minimum grand total before a customer
     * survey/reward is offered. Only used if the survey/reward method is
     * {@link #METHOD_TRANS_AMOUNT}.
     */
    public static final String CUSTOMER_SURVEY_REWARD_TRANS_AMOUNT = ParameterConstantsIfc.CUSTOMER_CustomerSurveyRewardTransactionAmount;

    /**
     * Parameter indicating how many (sale) transactions must take place before
     * a customer survey/reward is offered. Only used if the survey/reward
     * method is {@link #METHOD_N_NUMBER_TRANS}.
     */
    public static final String CUSTOMER_SURVEY_REWARD_N_TRANS = ParameterConstantsIfc.CUSTOMER_CustomerSurveyReward_n_Transactions;

    /**
     * Returns <code>true</code> if a survey should be printed on the receipt.
     * Otherwise <code>false</code> value is returned. Any problems
     * are simply logged, but not thrown back to the caller.
     * 
     * @param bus The bus holding the parameter and utility managers.
     * @param trans The transaction.
     * @return <code>true</code> if the survey blueprint should be printed.
     */
    public boolean isSurveyExpected(SessionBusIfc bus, TenderableTransactionIfc trans);
}
