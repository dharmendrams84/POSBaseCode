/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/manager/registerreports/RegisterReportsCargo.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:11 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    nkgautam  06/29/10 - bill pay report changes
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 * 3    360Commerce 1.2         3/31/2005 4:29:38 PM   Robert Pearse   
 * 2    360Commerce 1.1         3/10/2005 10:24:39 AM  Robert Pearse   
 * 1    360Commerce 1.0         2/11/2005 12:13:39 PM  Robert Pearse   
 *
 *Revision 1.4  2004/09/27 22:32:05  bwf
 *@scr 7244 Merged 2 versions of abstractfinancialcargo.
 *
 *Revision 1.3  2004/02/12 16:50:59  mcs
 *Forcing head revision
 *
 *Revision 1.2  2004/02/11 21:51:46  rhafernik
 *@scr 0 Log4J conversion and code cleanup
 *
 *Revision 1.1.1.1  2004/02/11 01:04:17  cschellenger
 *updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   Nov 04 2003 13:51:12   rrn
 * Removed getAccessFunctionID( ).  Calls to getAccessFunctionID( ) will now be serviced by parent class, UserAccessCargo.
 * 
 *    Rev 1.0   Aug 29 2003 16:01:22   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Aug 07 2002 19:33:58   baa
 * remove hard coded date formats
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.0   Apr 29 2002 15:19:06   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:36:38   msg
 * Initial revision.
 * 
 *    Rev 1.1   16 Nov 2001 14:50:58   pdd
 * Cleaned up and added getAccessFunctionID().
 * Resolution for POS SCR-291: Device/DB Status updates
 * 
 *    Rev 1.0   Sep 21 2001 11:24:04   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:12:10   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.manager.registerreports;

// Foundation imports
import oracle.retail.stores.pos.services.common.AbstractFinancialCargo;
import oracle.retail.stores.foundation.tour.application.tourcam.ObjectRestoreException;
import oracle.retail.stores.foundation.tour.application.tourcam.SnapshotIfc;
import oracle.retail.stores.foundation.tour.application.tourcam.TourCamSnapshot;
import oracle.retail.stores.pos.reports.RegisterReport;

//------------------------------------------------------------------------------
/**
    Stores the register reports data.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public class RegisterReportsCargo 
extends AbstractFinancialCargo 
implements RegisterReportsCargoIfc
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
                @deprecated as of release 5.5 formats should be retrieve from the locale
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
    
    /*
     * Bill Pay Transactions Report Constant
     */
    public static final int REPORT_BILL_PAY_TRANSACTION = REPORT_SUSPENDED_TRANSACTION + 1;
    
    /**
            current report type
    **/
    protected int currentReportType                      = REPORT_UNDEFINED;
    /**
                register report reference
        **/
    protected RegisterReport report = null;
    
    //--------------------------------------------------------------------------
    /**
        Set the current report type <P>
        @param type int value (ex: REPORT_SUMMARY, REPORT_DEPTSALES)
    */    
    //--------------------------------------------------------------------------
    public void setReportType(int value)
    {
        currentReportType = value;
    }

    //--------------------------------------------------------------------------
    /**
        Get the current report type <P>
        @return int type value (ex: REPORT_UNDEFINED, REPORT_SUMMARY)
    */    
    //--------------------------------------------------------------------------
    public int getReportType()
    {
        return(currentReportType);
    }

    //--------------------------------------------------------------------------
    /**
        Set the report <P>
        @param rr RegisterReport object
    */    
    //--------------------------------------------------------------------------
    public void setReport(RegisterReport value)
    {
        report = value;
    }

    //--------------------------------------------------------------------------
    /**
        Get the current report <P>
        @return RegisterReport value 
    */    
    //--------------------------------------------------------------------------
    public RegisterReport getReport()
    {
        return report;
    }

    //--------------------------------------------------------------------------
    /**
        Reset the cargo data using the snapshot passed in. <P>
        <B>Pre-Condition(s)</B>
        <UL>
        <LI>The snapshot represents the state of the cargo, possibly relative
        to the existing state of the cargo.
        </UL>
        <B>Post-Condition(s)</B>
        <UL>
        <LI>The cargo state has been restored with the contents of the snapshot.
        </UL>
        @param snapshot is the SnapshotIfc which contains the desired state 
            of the cargo.
        @exception ObjectRestoreException is thrown when the cargo cannot
            be restored with this snapshot 
    */
    //--------------------------------------------------------------------------
    public void restoreSnapshot(SnapshotIfc snapshot)
        throws ObjectRestoreException
    {
    }

    //--------------------------------------------------------------------------
    /**
        Create a SnapshotIfc which can subsequently be used to restore
            the cargo to its current state. <P>
        <B>Pre-Condition(s)</B>
        <UL>
        <LI>The cargo is able to make a snapshot.
        </UL>
        <B>Post-Condition(s)</B>
        <UL>
        <LI>A snapshot is returned which contains enough data to restore the 
            cargo to its current state.
        </UL>
        @return an object which stores the current state of the cargo.
        @see oracle.retail.stores.foundation.tour.application.tourcam.SnapshotIfc
    */
    //--------------------------------------------------------------------------
    public SnapshotIfc makeSnapshot()
    {
        return new TourCamSnapshot(this);
    }
}
