/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/manager/registerreports/RegisterReportsCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:11 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:29:38 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:39 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:39 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:50:59  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:46  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:17  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:01:22   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:19:06   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:36:38   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:24:16   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:12:10   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.manager.registerreports;
// foundation imports
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.financial.StoreStatusIfc;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;
import oracle.retail.stores.pos.reports.RegisterReport;

//------------------------------------------------------------------------------
/**
    Provides an interface to RegisterReports cargo.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public interface RegisterReportsCargoIfc extends CargoIfc
{
        /**
                invalid register message
        **/
    public static final String INVALID_REGISTER = "INVALID_REGISTER";
        /**
                invalid till message
        **/
    public static final String INVALID_TILL = "INVALID_TILL";
        /**
                invalid business day message
        **/
    public static final String INVALID_BUSINESS_DAY = "INVALID_BUSINESS_DAY";
        /**
                invalid date range message
        **/
    public static final String INVALID_DATE_RANGE = "INVALID_DATE_RANGE";
        /**
                database error message
        **/
    public static final String DATABASE_ERROR = "DATABASE_ERROR";
        /**
                date format string
        **/
    public static final String UI_DATE_FORMAT = "MM/dd/yyyy";
    /**
                report constant base
        **/
    public static final int REPORT_UNDEFINED = 0;
        /**
                summary report constant
        **/
    public static final int REPORT_SUMMARY               = REPORT_UNDEFINED + 1;
        /**
                department sales report constant
        **/
    public static final int REPORT_DEPTSALES             = REPORT_SUMMARY + 1;
        /**
                hourly sales report constant
        **/
    public static final int REPORT_HOURSALES             = REPORT_DEPTSALES + 1;
        /**
                associate productivity report constant
        **/
    public static final int REPORT_ASSOCPROD             = REPORT_HOURSALES + 1;
        /**
                queue transaction report constant
        **/
    public static final int REPORT_QUETRANS              = REPORT_ASSOCPROD + 1;
        /**
                orders summary report constant
        **/
    public static final int REPORT_ORDERS_SUMMARY        = REPORT_QUETRANS + 1;
        /**
                orders status report constant
        **/
    public static final int REPORT_ORDER_STATUS          = REPORT_ORDERS_SUMMARY + 1;
        /**
                suspended transaction report constant
        **/
        public static final int REPORT_SUSPENDED_TRANSACTION = REPORT_ORDER_STATUS + 1;
    
    //--------------------------------------------------------------------------
    /**
        Set the current report type <P>
        @param type int value (ex: REPORT_SUMMARY, REPORT_DEPTSALES)
    */    
    //--------------------------------------------------------------------------
    public void setReportType(int value);

    //--------------------------------------------------------------------------
    /**
        Get the current report type <P>
        @return int type value (ex: REPORT_UNDEFINED, REPORT_SUMMARY)
    */    
    //--------------------------------------------------------------------------
    public int getReportType();

    //--------------------------------------------------------------------------
    /**
        Set the report <P>
        @param rr RegisterReport object
    */    
    //--------------------------------------------------------------------------
    public void setReport(RegisterReport value);

    //--------------------------------------------------------------------------
    /**
        Get the current report <P>
        @return RegisterReport value 
    */    
    //--------------------------------------------------------------------------
    public RegisterReport getReport();

    //----------------------------------------------------------------------
    /**
        Returns the store status. <P>
        @return The store status.
    **/
    //----------------------------------------------------------------------
    public StoreStatusIfc getStoreStatus();

    //----------------------------------------------------------------------
    /**
        Returns the register object. <P>
        @return The register object.
    **/
    //----------------------------------------------------------------------
    public RegisterIfc getRegister();

    //----------------------------------------------------------------------
    /**
        Returns the operator object. <P>
        @return The operator object.
    **/
    //----------------------------------------------------------------------
    public EmployeeIfc getOperator();

}
