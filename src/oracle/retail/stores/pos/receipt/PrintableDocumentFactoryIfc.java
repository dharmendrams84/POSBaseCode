/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/receipt/PrintableDocumentFactoryIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:40 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   11/13/08 - configure print beans into Spring context
 *
 * ===========================================================================
 * $Log:
 *  2    360Commerce 1.1         5/15/2007 4:03:09 PM   Alan N. Sinton  CR
 *       26481 - Phase one for VAT modifications to ORPOS <ARG> Summary
 *       Reports.
 *  1    360Commerce 1.0         4/30/2007 7:00:39 PM   Alan N. Sinton  CR
 *       26485 - Merge from v12.0_temp.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.receipt;

import oracle.retail.stores.pos.reports.SummaryReport;

/**
 * Interface for PrintableDocumentFactory.
 * $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @deprecated as of 13.1 since blueprints are used instead of printable documents.
 */
public interface PrintableDocumentFactoryIfc
{
    /**
     * Method for creating an appropriate instance of the EYSPrintableDocumentIfc for the given parameters.
     *
     * @param parameters
     * @return An appropriate instance of the EYSPrintableDocumentIfc for the given parameters.
     */
    public EYSPrintableDocumentIfc createPrintableDocument(PrintableDocumentParameterBeanIfc parameters);

    /**
     * Method for creating an instance of the Report Summary
     *
     * @param key
     * @return
     */
    public SummaryReport createReportSummary(String key);
}
