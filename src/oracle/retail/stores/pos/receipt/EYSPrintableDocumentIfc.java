/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/receipt/EYSPrintableDocumentIfc.java /main/12 2011/02/27 20:37:37 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/18/11 - refactor printing for switching character sets
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   11/10/08 - deprecate methods replaced by BPT framework
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.receipt;

import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.device.ReceiptPrinterIfc;

/**
 * This module represents the interface for EYSPrintableDocument.
 * @version $Revision: /main/12 $
 */
public interface EYSPrintableDocumentIfc
{
    /**
     * revision number supplied by source-code-control system
     */
    public static String revisionNumber = "$Revision: /main/12 $";

    /**
     * Sets the printer for the Receipt.
     * 
     * @param p receipt printer
     */
    public void setPrinter(ReceiptPrinterIfc p);

    /**
     * Returns whether a printer has been provided.
     * 
     * @return true if a printer has been provided, false otherwise.
     */
    public boolean isPrinterInitialized();

    /**
     * This method prints the document.
     * 
     * @throws DeviceException if any problems occur
     */
    public void printDocument() throws DeviceException;

    /**
     * Set whether or not to print taxes on a per jurisdiction/ per rule basis.
     * 
     * @param printItemTax
     */
    public void setPrintItemTax(boolean printItemTax);

    /**
     * Return whether or not to print taxes on a per jurisdiction/ per rule
     * basis.
     * 
     * @return
     */
    public boolean getPrintItemTax();

    /**
     * Return the transaction
     * 
     * @return
     */
    public TenderableTransactionIfc getTransaction();

    /**
     * Sets the options (header, footer, PATFooter, etc.) from the
     * PrintableDocumentParameterBean instance.
     * 
     * @param parameterBean The parameterBean to set.
     */
    public void setParameterBean(PrintableDocumentParameterBeanIfc parameterBean);
}
