/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/common/OrderSearchCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:33 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:29:14 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:52 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:53 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:51:22  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:45  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:03:32   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:13:04   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:41:08   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 24 2001 13:00:14   MPM
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:10:24   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.common;

import java.io.Serializable;

import oracle.retail.stores.pos.services.common.DBErrorCargoIfc;
import oracle.retail.stores.domain.utility.EYSDate;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

/**
 * Interface defines constants and method signatures for processing search
 * criteria common to the order services. This interface is provided to enable
 * the Order Lookup service.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface OrderSearchCargoIfc extends CustomerSelectCargoIfc, DBErrorCargoIfc, CargoIfc, Serializable
{
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    // ------- Constants for defining search type ---------//
    public static final int SEARCH_BY_ORDER_ID = 0;
    public static final int SEARCH_BY_ORDER_STATUS = 1;
    public static final int SEARCH_BY_CUSTOMER = 2;
    public static final int SEARCH_FOR_NEW_ORDERS = 3;
    public static final int SEARCH_BY_EMESSAGE = 4;

    // ----------------------------------------------------------------------
    /**
     * Sets the search method which the cargo will be used for.
     * 
     * @param name int value of the searchMethod
     **/
    // --------------------------------------------------------------------------
    public void setSearchMethod(int name);

    // --------------------------------------------------------------------------
    /**
     * Gets the name of a search method which the cargo will be used for.
     * 
     * @return int representation of the searchMethod
     **/
    // --------------------------------------------------------------------------
    public int getSearchMethod();

    // ----------------------------------------------------------------------
    /**
     * Sets the status of orders to be retrieved in a search. This should match
     * one of the status constants defined in OrderIfc.
     * 
     * @param name int value of the searchStatus
     **/
    // --------------------------------------------------------------------------
    public void setStatus(int name);

    // --------------------------------------------------------------------------
    /**
     * Gets the value of searchStatus.
     * 
     * @return int representation of the searchStatus
     **/
    // --------------------------------------------------------------------------
    public int getStatus();

    // ----------------------------------------------------------------------
    /**
     * Sets the summaryStart search date which the cargo will be used for.
     * 
     * @param name int value of the summaryStatus
     **/
    // --------------------------------------------------------------------------
    public void setStartDate(EYSDate date);

    // --------------------------------------------------------------------------
    /**
     * Gets the value of summaryStart, search date method which the cargo will
     * be used for.
     * 
     * @return int representation of the summaryStatus
     **/
    // --------------------------------------------------------------------------
    public EYSDate getStartDate();

    // ----------------------------------------------------------------------
    /**
     * Sets the orderID search criteria which the cargo will be used for.
     * 
     * @param name int value of the summaryStatus
     **/
    // --------------------------------------------------------------------------
    public void setOrderID(String id);

    // --------------------------------------------------------------------------
    /**
     * Gets the value of orderID, search orderID method which the cargo will be
     * used for.
     * 
     * @return int representation of the summaryStatus
     **/
    // --------------------------------------------------------------------------
    public String getOrderID();

    // ----------------------------------------------------------------------
    /**
     * Sets the summaryEnd search date which the cargo will be used for.
     * 
     * @param name int value of the summaryStatus
     **/
    // --------------------------------------------------------------------------
    public void setEndDate(EYSDate date);

    // --------------------------------------------------------------------------
    /**
     * Gets the value of summaryEnd, search date method which the cargo will be
     * used for.
     * 
     * @return int representation of the summaryStatus
     **/
    // --------------------------------------------------------------------------
    public EYSDate getEndDate();

    // ----------------------------------------------------------------------
    /**
     * Sets the useDateRange flag for date range searches.
     * 
     * @param name boolean flag valud of useDateRange
     **/
    // --------------------------------------------------------------------------
    public void setDateRange(boolean flag);

    // --------------------------------------------------------------------------
    /**
     * Gets the value of useDateRange, whether to use date range in searches.
     * 
     * @return boolean representation of the useDateRange
     **/
    // --------------------------------------------------------------------------
    public boolean getDateRange();

}
