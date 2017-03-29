/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/lookup/LookupSummarySite.java /main/11 2011/02/16 09:13:31 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    jswan     04/24/09 - Modified to ensure that orders created in training
 *                         mode can only retrieve in training mode, and
 *                         non-training mode orders can only be retrieved in
 *                         non-training mode.
 *
 * ===========================================================================
 * $Log:
 *  3    360Commerce 1.2         3/31/2005 4:28:58 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:23:22 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:12:29 PM  Robert Pearse   
 * $
 * Revision 1.7  2004/06/03 14:47:44  epd
 * @scr 5368 Update to use of DataTransactionFactory
 *
 * Revision 1.6  2004/04/20 13:17:06  tmorris
 * @scr 4332 -Sorted imports
 *
 * Revision 1.5  2004/04/14 15:17:10  pkillick
 * @scr 4332 -Replaced direct instantiation(new) with Factory call.
 *
 * Revision 1.4  2004/03/03 23:15:07  bwf
 * @scr 0 Fixed CommonLetterIfc deprecations.
 *
 * Revision 1.3  2004/02/12 16:51:24  mcs
 * Forcing head revision
 *
 * Revision 1.2  2004/02/11 21:51:48  rhafernik
 * @scr 0 Log4J conversion and code cleanup
 * Revision 1.1.1.1 2004/02/11 01:04:18
 * cschellenger updating to pvcs 360store-current
 * 
 * 
 * 
 * Rev 1.0 Aug 29 2003 16:03:40 CSchellenger Initial revision.
 * 
 * Rev 1.0 Apr 29 2002 15:12:16 msg Initial revision.
 * 
 * Rev 1.0 Mar 18 2002 11:41:24 msg Initial revision.
 * 
 * Rev 1.0 Sep 24 2001 13:01:14 MPM Initial revision.
 * 
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.lookup;
//foundation imports
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.common.parameter.ParameterConstantsIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.OrderReadDataTransaction;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.emessage.EMessageIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.domain.order.OrderSummaryEntryIfc;
import oracle.retail.stores.domain.utility.EYSDate;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.order.common.OrderCargo;
import oracle.retail.stores.pos.services.order.common.OrderSearchCargoIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.StatusSearchBeanModel;

//------------------------------------------------------------------------------
/**
 * 
 * Retrieves order summarie(s) based upon the search method specified in cargo.
 * 
 * @version $Revision: /main/11 $
 */
//------------------------------------------------------------------------------

public class LookupSummarySite extends PosSiteActionAdapter
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7166563720931808130L;

    /**
     * class name constant
     */
    public static final String SITENAME = "LookupSummarySite";

    /**
     * revision number for this class
     */
    public static final String revisionNumber = "$Revision: /main/11 $";

    /**
     * order maximum matches parameter default value if retrieval fails
     */
    protected static final int ORDER_MAXIMUM_MATCHES_DEFAULT = 50;

    /**
     * Determines which type of order retrieval to perform and calls the
     * appropriate method. Mails SUCCESS, DB_ERROR, TOO_MANY,or NOT_FOUND
     * letters based upon the search results.
     * 
     * @param bus the bus arriving at this site
     */
    @Override
    public void arrive(BusIfc bus)
    {
        Letter result = new Letter(CommonLetterIfc.DB_ERROR); // default value
        OrderCargo cargo = (OrderCargo) bus.getCargo();
        int[] statusNew = new int[1]; // order summary stati
        statusNew[0] = cargo.getStatus(); // from calling service
        String storeID = cargo.getStoreStatus().getStore().getStoreID();

        ParameterManagerIfc pm1 =
            (ParameterManagerIfc) bus.getManager(ParameterManagerIfc.TYPE);

        OrderReadDataTransaction orderRTransaction = null;
        
        orderRTransaction = (OrderReadDataTransaction) DataTransactionFactory.create(DataTransactionKeys.ORDER_READ_DATA_TRANSACTION);

        switch (cargo.getSearchMethod())
        {
            // picklist
            case OrderSearchCargoIfc.SEARCH_FOR_NEW_ORDERS :
                result =
                    searchNewOrders(
                        statusNew,
                        storeID,
                        cargo,
                        orderRTransaction,
                        bus.getServiceName());
                break;

                // status search
            case OrderSearchCargoIfc.SEARCH_BY_ORDER_STATUS :
                result =
                    searchOrderStatus(
                        pm1,
                        statusNew,
                        cargo,
                        bus,
                        storeID,
                        orderRTransaction,
                        bus.getServiceName());
                break;

                // order id search
            case OrderSearchCargoIfc.SEARCH_BY_ORDER_ID :
                result =
                    searchOrderID(
                        pm1,
                        cargo,
                        storeID,
                        orderRTransaction,
                        bus.getServiceName());
                break;

                // customer search
            case OrderSearchCargoIfc.SEARCH_BY_CUSTOMER :
                result =
                    searchOrderCustomer(
                        pm1,
                        cargo,
                        storeID,
                        orderRTransaction,
                        bus.getServiceName());
                break;

                // emessage search
            case OrderSearchCargoIfc.SEARCH_BY_EMESSAGE :
                result =
                    searchOrderEMessage(
                        pm1,
                        cargo,
                        storeID,
                        orderRTransaction,
                        bus.getServiceName());
                break;
        }

        bus.mail(result, BusIfc.CURRENT);

    } // end arrive

    //--------------------------------------------------------------------------
    /**
     * Performs a search for all orders with status New. Used by pick list
     * service. Sets cargo for order summaries found or db error upon failure.
     * <P>
     * 
     * @param status,
     *            store id, cargo, order data transaction
     * @param serviceName
     *            service name (for logging)
     * @return SUCCESS, DB_ERROR, or NOT_FOUND letters
     */
    //--------------------------------------------------------------------------

    Letter searchNewOrders(
        int[] statusNew,
        String storeID,
        OrderCargo cargo,
        OrderReadDataTransaction orderRTransaction,
        String serviceName)
    {
        Letter result = new Letter(CommonLetterIfc.SUCCESS);
        OrderSummaryEntryIfc[] orderSumList = null;
        EYSDate beginDate = null;
        EYSDate endDate = null;
        boolean trainingMode = cargo.getRegister().getWorkstation().isTrainingMode();

        try
        { // begin lookup summary try block
            orderSumList =
                orderRTransaction.retrieveOrderSummaryByStatus(
                    statusNew,
                    storeID,
                    beginDate,
                    endDate,
                    trainingMode);
            cargo.setOrderSummaries(orderSumList);
            // copy all summaries to cargo
        }
        catch (DataException de) // handle data base exceptions
        { // begin data base exception catch
            if (de.getErrorCode() == DataException.NO_DATA) // non found
            {
                result = new Letter(CommonLetterIfc.NOT_FOUND);
                logger.warn(
                    "" + SITENAME + ".searchNewOrders - No MATCHES !!!");
            }
            else
            { // take care of fatal database errors
                result = new Letter(CommonLetterIfc.DB_ERROR);
                logger.error(
                    ""
                        + SITENAME
                        + ".searchNewOrders - DB error: "
                        + de.getMessage()
                        + "");
                cargo.setDataExceptionErrorCode(de.getErrorCode());
            }
        } // end database error catch

        return (result);
    } // end searchNewOrders

    //--------------------------------------------------------------------------
    /**
     * Performs a search for orders with a specified status, within a date
     * range . if provided, with a store id. Sets cargo for order summaries
     * found or db error upon failure. Determines if too many or no orders have
     * been returned. Sets cargo for order summaries found or db error upon
     * failure.
     * <P>
     * 
     * @param parameter
     *            manager, status, cargo, bus, store id, order data transaction
     * @param serviceName
     *            service name (for logging)
     * @return SUCCESS, DB_ERROR, TOO_MANY, or NOT_FOUND letters
     */
    //--------------------------------------------------------------------------

    Letter searchOrderStatus(
        ParameterManagerIfc pm1,
        int[] statusNew,
        OrderCargo cargo,
        BusIfc bus,
        String storeID,
        OrderReadDataTransaction orderRTransaction,
        String serviceName)
    {
        Letter result = new Letter(CommonLetterIfc.SUCCESS);
        Integer numMaxOrders = new Integer(ORDER_MAXIMUM_MATCHES_DEFAULT);
        // default value
        int maxMatchesInt = 0;
        EYSDate beginDate = null;
        EYSDate endDate = null;
        OrderSummaryEntryIfc[] orderSumList = null;

        try
        {
            numMaxOrders = pm1.getIntegerValue(ParameterConstantsIfc.ORDER_OrderMaximumMatches);
            maxMatchesInt = numMaxOrders.intValue();
        }
        catch (ParameterException pe)
        {
            logger.warn(
                serviceName
                    + ": Parameter Exception: "
                    + pe.getMessage()
                    + " using default value : "
                    + Integer.toString(ORDER_MAXIMUM_MATCHES_DEFAULT));
            maxMatchesInt = ORDER_MAXIMUM_MATCHES_DEFAULT;
        }

        statusNew[0] = cargo.getStatus(); // 1 status so far...

        // determine if user entered dates in status search screen
        POSUIManagerIfc ui =
            (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        StatusSearchBeanModel model =
            (StatusSearchBeanModel) ui.getModel(POSUIManagerIfc.STATUS_SEARCH);

        if (!model.getEmptyDate()) // get from cargo - valid
        {
            beginDate = cargo.getStartDate();
            endDate = cargo.getEndDate();
        }

        boolean trainingMode = cargo.getRegister().getWorkstation().isTrainingMode();

        try
        { // begin lookup summary try block
            orderSumList =
                orderRTransaction.retrieveOrderSummaryByStatus(
                    statusNew,
                    storeID,
                    beginDate,
                    endDate,
                    trainingMode);
            if (orderSumList.length > maxMatchesInt)
            {
                result = new Letter(CommonLetterIfc.TOO_MANY);
                // too many for status search
                logger.warn(
                    ""
                        + SITENAME
                        + ".searchOrderStatus - Too Many MATCHES > "
                        + Integer.toString(maxMatchesInt)
                        + "");
            }
            else // have enough summaries
                {
                cargo.setOrderSummaries(orderSumList);
                // copy all summaries to cargo
            }
        }
        catch (DataException de) // handle data base exceptions
        { // no matches found
            if (de.getErrorCode() == DataException.NO_DATA)
            {
                result = new Letter(CommonLetterIfc.NOT_FOUND);
                // Not Found letter
                logger.warn(
                    "" + SITENAME + ".searchOrderStatus - No MATCHES !!!");
            }
            else
            { // take care of fatal database errors
                result = new Letter(CommonLetterIfc.DB_ERROR);
                logger.error(
                    ""
                        + SITENAME
                        + ".searchOrderStatus - DB error: "
                        + de.getMessage()
                        + "");
                cargo.setDataExceptionErrorCode(de.getErrorCode());
            }
        } // end database error catch

        return (result);
    } // end searchOrderStatus

    //--------------------------------------------------------------------------
    /**
     * Performs a search for orders with a specified order id, within a date
     * range . if provided, with a store id. Sets cargo for order summaries
     * found or db error upon failure. Determines if too many or no orders have
     * been returned. Sets cargo for order summaries found or db error upon
     * failure.
     * <P>
     * 
     * @param parameter
     *            manager, cargo, store id, order data transaction
     * @param serviceName
     *            service name (for logging)
     * @return SUCCESS, DB_ERROR, TOO_MANY, or NOT_FOUND letters
     */
    //--------------------------------------------------------------------------

    Letter searchOrderID(
        ParameterManagerIfc pm1,
        OrderCargo cargo,
        String storeID,
        OrderReadDataTransaction orderRTransaction,
        String serviceName)
    {
        Letter result = new Letter(CommonLetterIfc.SUCCESS);
        Integer numMaxOrders = new Integer(ORDER_MAXIMUM_MATCHES_DEFAULT);
        // default value
        EYSDate beginDate = null;
        EYSDate endDate = null;
        OrderSummaryEntryIfc[] orderSumList = null;
        String orderSumID = null;
        OrderIfc order = DomainGateway.getFactory().getOrderInstance();
        int maxMatchesInt = 0;

        try
        {
            numMaxOrders = pm1.getIntegerValue(ParameterConstantsIfc.ORDER_OrderMaximumMatches);
            maxMatchesInt = numMaxOrders.intValue();
        }
        catch (ParameterException pe)
        {
            logger.warn(
                serviceName
                    + ": Parameter Exception: "
                    + pe.getMessage()
                    + " using default value :  "
                    + Integer.toString(ORDER_MAXIMUM_MATCHES_DEFAULT));
            maxMatchesInt = ORDER_MAXIMUM_MATCHES_DEFAULT;
        }

        orderSumID = cargo.getOrderID();
        order.setOrderID(orderSumID);

        if (cargo.getDateRange() == true)
        {
            beginDate = cargo.getStartDate();
            endDate = cargo.getEndDate();
        }

        boolean trainingMode = cargo.getRegister().getWorkstation().isTrainingMode();

        try
        { // begin lookup summary try block
            orderSumList =
                orderRTransaction.retrieveOrderSummaryByOrderID(
                    order,
                    storeID,
                    beginDate,
                    endDate,
                    trainingMode);

            if (orderSumList.length > maxMatchesInt)
            {
                result = new Letter(CommonLetterIfc.TOO_MANY); // too many
                logger.warn(
                    ""
                        + SITENAME
                        + ".searchOrderID - Too Many MATCHES > "
                        + Integer.toString(maxMatchesInt)
                        + "");
            }
            else
            {
                cargo.setOrderSummaries(orderSumList);
                // copy all summaries to cargo
            }
        }
        catch (DataException de) // handle data base exceptions
        { // no matches found
            if (de.getErrorCode() == DataException.NO_DATA)
            {
                result = new Letter(CommonLetterIfc.NOT_FOUND);
                // Not Found letter
                logger.warn("" + SITENAME + ".searchOrderID - No MATCHES !!!");
            }
            else
            { // take care of fatal database errors
                result = new Letter(CommonLetterIfc.DB_ERROR);
                logger.error(
                    ""
                        + SITENAME
                        + ".searchOrderID - DB error: "
                        + de.getMessage()
                        + "");
                cargo.setDataExceptionErrorCode(de.getErrorCode());
            }
        } // end database error catch

        return (result);
    } // end searchOrderID

    //--------------------------------------------------------------------------
    /**
     * Performs a search for orders with a specified customer, within a date
     * range . if provided, with a store id. Sets cargo for order summaries
     * found or db error upon failure. Determines if too many or no orders have
     * been returned. Sets cargo for order summaries found or db error upon
     * failure.
     * <P>
     * 
     * @param parameter
     *            manager, cargo, store id, order data transaction
     * @param serviceName
     *            service name (for logging)
     * @return SUCCESS, DB_ERROR, TOO_MANY, or NOT_FOUND letters
     */
    //--------------------------------------------------------------------------

    Letter searchOrderCustomer(
        ParameterManagerIfc pm1,
        OrderCargo cargo,
        String storeID,
        OrderReadDataTransaction orderRTransaction,
        String serviceName)
    {
        Letter result = new Letter(CommonLetterIfc.SUCCESS);
        Integer numMaxOrders = new Integer(ORDER_MAXIMUM_MATCHES_DEFAULT);
        // default value
        EYSDate beginDate = null;
        EYSDate endDate = null;
        OrderSummaryEntryIfc[] orderSumList = null;
        CustomerIfc customer = DomainGateway.getFactory().getCustomerInstance();
        int maxMatchesInt = 0;

        try
        {
            numMaxOrders = pm1.getIntegerValue(ParameterConstantsIfc.ORDER_OrderMaximumMatches);
            maxMatchesInt = numMaxOrders.intValue();
        }
        catch (ParameterException pe)
        {
            logger.warn(
                serviceName
                    + "Parameter Exception: "
                    + pe.getMessage()
                    + " using default value : "
                    + Integer.toString(ORDER_MAXIMUM_MATCHES_DEFAULT));
            maxMatchesInt = ORDER_MAXIMUM_MATCHES_DEFAULT;
        }

        customer = cargo.getSelectedCustomer();
        if (cargo.getDateRange() == true)
        {
            beginDate = cargo.getStartDate();
            if (beginDate != null)
            {
                beginDate.initialize(
                    beginDate.getYear(),
                    beginDate.getMonth(),
                    beginDate.getDay(),
                    00,
                    00,
                    00);
            }
            endDate = cargo.getEndDate();
        }

        boolean trainingMode = cargo.getRegister().getWorkstation().isTrainingMode();

        try
        { // begin lookup summary try block
            orderSumList =
                orderRTransaction.retrieveOrderSummaryByCustomer(
                    customer,
                    storeID,
                    beginDate,
                    endDate,
                    trainingMode);

            if (orderSumList.length > maxMatchesInt)
            {
                result = new Letter(CommonLetterIfc.TOO_MANY);
                // too many for status search
                logger.warn(
                    ""
                        + SITENAME
                        + ".searchOrderCustomer - Too Many MATCHES > "
                        + Integer.toString(maxMatchesInt)
                        + "");
            }
            else
            {
                cargo.setOrderSummaries(orderSumList);
                // copy all summaries to cargo
            }
        }
        catch (DataException de) // handle data base exceptions
        {
            // no matches found
            if (de.getErrorCode() == DataException.NO_DATA)
            {
                result = new Letter(CommonLetterIfc.NOT_FOUND);
                // Not Found letter
                logger.warn(
                    "" + SITENAME + ".searchOrderCustomer - No MATCHES !!!");
            }
            else
            { // take care of fatal database errors
                result = new Letter(CommonLetterIfc.DB_ERROR);
                logger.error(
                    ""
                        + SITENAME
                        + ".searchOrderCustomer - DB error: "
                        + de.getMessage()
                        + "");
                cargo.setDataExceptionErrorCode(de.getErrorCode());
            }
        } // end database error catch

        return (result);
    } // end searchOrderCustomer

    //--------------------------------------------------------------------------
    /**
     * Performs a search for orders with a specified emessage, within a date
     * range . if provided, with a store id. Sets cargo for order summaries
     * found or db error upon failure. Determines if too many or no orders have
     * been returned. Sets cargo for order summaries found or db error upon
     * failure.
     * <P>
     * 
     * @param parameter
     *            manager, cargo, store id, order data transaction
     * @param serviceName
     *            service name (for logging)
     * @return SUCCESS, DB_ERROR, TOO_MANY, or NOT_FOUND letters
     */
    //--------------------------------------------------------------------------

    Letter searchOrderEMessage(
        ParameterManagerIfc pm1,
        OrderCargo cargo,
        String storeID,
        OrderReadDataTransaction orderRTransaction,
        String serviceName)
    {
        Letter result = new Letter(CommonLetterIfc.SUCCESS);
        Integer numMaxOrders = new Integer(ORDER_MAXIMUM_MATCHES_DEFAULT);
        // default value
        EYSDate beginDate = null;
        EYSDate endDate = null;
        OrderSummaryEntryIfc[] orderSumList = null;
        EMessageIfc emessage = cargo.getEMessage(); // retrieve the EMessage
        int maxMatchesInt = 0;

        try
        {
            numMaxOrders = pm1.getIntegerValue(ParameterConstantsIfc.ORDER_OrderMaximumMatches);
            maxMatchesInt = numMaxOrders.intValue();
        }
        catch (ParameterException pe)
        {
            logger.warn(
                serviceName
                    + ": Parameter Exception: "
                    + pe.getMessage()
                    + "using default value : "
                    + Integer.toString(ORDER_MAXIMUM_MATCHES_DEFAULT));
            maxMatchesInt = ORDER_MAXIMUM_MATCHES_DEFAULT;
        }

        if (cargo.getDateRange() == true)
        {
            beginDate = cargo.getStartDate();
            endDate = cargo.getEndDate();
        }

        boolean trainingMode = cargo.getRegister().getWorkstation().isTrainingMode();

        try
        { // begin lookup summary try block
            orderSumList =
                orderRTransaction.retrieveOrderSummaryByOrderID(
                    emessage,
                    storeID,
                    beginDate,
                    endDate,
                    trainingMode);

            if (orderSumList.length > maxMatchesInt)
            {
                result = new Letter(CommonLetterIfc.TOO_MANY); // too many
                logger.warn(
                    ""
                        + SITENAME
                        + ".searchOrderEMessage - Too Many MATCHES > "
                        + Integer.toString(maxMatchesInt)
                        + "");
            }
            else
            {
                cargo.setOrderSummaries(orderSumList);
                // copy all summaries to cargo
            }
        }
        catch (DataException de) // handle data base exceptions
        { // no matches found
            if (de.getErrorCode() == DataException.NO_DATA)
            {
                result = new Letter(CommonLetterIfc.NOT_FOUND);
                // Not Found letter
                logger.warn(
                    "" + SITENAME + ".searchOrderEMessage - No MATCHES !!!");
            }
            else
            { // take care of fatal database errors
                result = new Letter(CommonLetterIfc.DB_ERROR);
                logger.error(
                    ""
                        + SITENAME
                        + ".searchOrderEMessage - DB error: "
                        + de.getMessage()
                        + "");
                cargo.setDataExceptionErrorCode(de.getErrorCode());
            }
        } // end database error catch

        return (result);
    } // end searchOrderEMessage

    //----------------------------------------------------------------------
    /**
     * Returns a string representation of this object.
     * <P>
     * 
     * @return String representation of object
     */
    //----------------------------------------------------------------------

    public String toString()
    { // begin toString()
        // result string
        String strResult =
            new String(
                "Class:  LookupSummarySite (Revision "
                    + getRevisionNumber()
                    + ")"
                    + hashCode());
        // pass back result
        return (strResult);
    } // end toString()

    //----------------------------------------------------------------------
    /**
     * Returns the revision number of the class.
     * <P>
     * 
     * @return String representation of revision number
     */
    //----------------------------------------------------------------------

    public String getRevisionNumber()
    { // begin getRevisionNumber()
        // return string
        return (revisionNumber);
    } // end getRevisionNumber()

} // LookupSummarySite
