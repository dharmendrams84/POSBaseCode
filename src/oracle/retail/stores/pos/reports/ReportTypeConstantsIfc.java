/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/reports/ReportTypeConstantsIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:35 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    nkgautam  07/02/10 - bill pay report changes
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    atirkey   01/07/09 - Till reconcile
 *    cgreene   11/13/08 - configure print beans into Spring context
 *    cgreene   09/11/08 - update header
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.reports;

/**
 * Collection of constants for report types.
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @since 13.1
 */
public interface ReportTypeConstantsIfc
{
    /** constant for Associate Productivity Report */
    public static final String ASSOCPRODUCTIVITY_REPORT = "AssociateProductivityReport";

    /** constant for Department Sales Report */
    public static final String DEPARTMENTSALES_REPORT   = "DepartmentSalesReport";

    /** constant for Hourly Productivity Report */
    public static final String HOURLYPROD_REPORT        = "HourlyProductivityReport";

    /** constant for Orders Summary Report */
    public static final String ORDERSSUMMARY_REPORT     = "OrdersSummaryReport";

    /** constant for Order Status Report */
    public static final String ORDERSTTATUS_REPORT      = "OrderStatusReport";

    /** constant for Queued Transactions Report */
    public static final String QUEUEDTXNS_REPORT        = "QueuedTransactionsReport";

    /** constant for report summary */
    public static final String SUMMARY_REPORT           = "SummaryReport";
    
    /** constant for report summary */
    public static final String TILL_SUMMARY_REPORT           = "TillSummaryReport";

    

    /** constant for Suspended Transactions Report */
    public static final String SUSPENDEDTXNS_REPORT     = "SuspendedTransactionsReport";

    /** constant for till count report */
    public static final String TILLCOUNT_REPORT         = "TillCountReport";
    
    /** constant for bill payments report */
    public static final String BILLPAYMENT_REPORT         = "BillPaymentReport";
}
