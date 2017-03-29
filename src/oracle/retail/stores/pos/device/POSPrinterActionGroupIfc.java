/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/device/POSPrinterActionGroupIfc.java /main/20 2011/02/27 20:37:36 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/18/11 - refactor printing for switching character sets
 *    cgreene   02/16/11 - move barcode prefix to posdevices.xml
 *    cgreene   10/26/10 - refactor alwaysPrintLineFeed into
 *                         AbstractPrinterGroup
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    acadar    12/15/09 - cleanup and refactoring of PdfReceiptPrinter's use
 *                         in POSPrinterActionGroup
 *    acadar    12/14/09 - cleanup
 *    acadar    12/07/09 - updates
 *    acadar    11/24/09 - changes for network printing
 *    blarsen   03/10/09 - added getter/setter for new slipPrintSize printer
 *                         device property
 *    cgreene   02/26/09 - switch char widths to int. fix plugin to support
 *    blarsen   02/19/09 - added 2 members and supporting methods:
 *                         alwaysPrintLineFeeds and characterWidths
 *    cgreene   11/07/08 - implement buffer in ReceiptPrinter for USB printers
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.device;


import java.util.Map;

import oracle.retail.stores.pos.device.PrinterActionGroupIfc;

import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.device.ReceiptPrinterIfc;


/**
 * POSPrinterActionGroupIfc defines the POSPrinter specific device operations
 * available to POS applications.
 *
 * @version $Revision: /main/20 $
 * @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc
 */
public interface POSPrinterActionGroupIfc extends PrinterActionGroupIfc
{
    /**
     * Default number of chars that print on one line of the printer. Defaults
     * to 42.
     */
    public static final int LINE_LENGTH_DEFAULT = 42;

    /**
     * Default number of lines to skip on a franking slip before printing
     */
    public static final int DEFAULT_START_FRANKING_LINE = 1;

    /**
     * starting lines for the various types of franking
     */
    public static final String PURCHASE_ORDER_START_FRANKING_LINE = "frankingLinePurchaseOrder";
    public static final String GIFT_CERTIFICATE_ISSUED_START_FRANKING_LINE = "frankingLineGiftCertificateIssued";
    public static final String GIFT_CERTIFICATE_VOIDED_START_FRANKING_LINE = "frankingLineGiftCertificateVoided";
    public static final String STORE_CREDIT_ISSUED_START_FRANKING_LINE = "frankingLineStoreCreditIssued";

    /**
     * default number of slip line characters
     * (width of paper)
     */
    public static final int USE_SLIP_LINE_SIZE_DEFAULT = -1;

    /**
     * default number of slip printed characters
     * (width of printed line)
     */
    public static final int USE_SLIP_PRINT_SIZE_DEFAULT = -1;


    /**
     * Retrieves the starting line number for franking of Purchase Order.
     *
     * @return starting line number
     */
    public String getFrankingLinePurchaseOrder();

    /**
     * Gets the starting lines for franking.
     *
     * @return Map<String, Integer>
     */
    public Map<String, Integer> getFrankingLines();

   /**
     * Gets the starting line for a franking type.
     *
     * @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#getFrankingLines(String)
     */
    public int getFrankingLines(String frankingType);

    /**
     * Retrieves the number of characters on a slip print line. This must be set
     * to a valid value for the hardware in use.
     *
     * @return number of slip line characters
     */
    public int getSlipLineSize();

    /**
     * Typically we must print fewer characters than will fit on the slip printer.
     *
     * These are right justified such that they will fit on franked e-checks,
     * coupons, etc.
     *
     * For instance, a slip printer might be capable of printing 29 characters.
     * However, only 24 characters will fit on a slip.
     *
     * @return number of printed slip line characters
     */
    public int getSlipPrintSize();

    /**
     * Set the slip print size.
     *
     * @param slipPrintSize
     * @see #getSlipPrintSize
     */
    public void setSlipPrintSize(int slipPrintSize);

    /**
     * Retrieves the print-buffering-enabled flag.
     *
     * @return print-buffering-enabled flag value
     * @deprecated as of 13.4. Use {@link ReceiptPrinterIfc#isPrintBufferingEnabled()} instead.
     * @see ReceiptPrinter in DeviceContext.xml
     */
    public boolean isPrintBufferingEnabled();

    /**
     * Retrieves the transactional printing enabled flag.
     *
     * @return transactional printing enabled flag.
     * @deprecated as of 13.4. Use {@link ReceiptPrinterIfc#isTransactionalPrinting()} instead.
     * @see DeviceContext.xml
     */
    public boolean isTransactionalPrinting();

    /**
     * Retrieves the print buffer size.
     *
     * @return byte A byte value representing print buffer size
     * @deprecated as of 13.4. See {@link ReceiptPrinterIfc#setPrintBufferSize(int)}
     * @see ReceiptPrinter in DeviceContext.xml
     */
    public int getPrintBufferSize();

    /**
     * Sets the print-buffering-enabled flag.
     *
     * @param value print-buffering-enabled flag value
     * @deprecated as of 13.4. Use {@link ReceiptPrinterIfc#setPrintBufferingEnabled(boolean)} instead.
     * @see ReceiptPrinter in DeviceContext.xml
     */
    public void setPrintBufferingEnabled(boolean value);

    /**
     * Sets the transactional printing enabled flag.
     *
     * @param value transactional printing enabled flag.
     * @deprecated as of 13.4. Use {@link ReceiptPrinterIfc#isTransactionalPrinting()} instead.
     * @see DeviceContext.xml
     */
    public void setTransactionalPrinting(boolean value);

    /**
     * Sets the print buffer size.
     *
     * @param value the print buffer size value
     * @deprecated as of 13.4. See {@link ReceiptPrinterIfc#setPrintBufferSize(int)}
     * @see ReceiptPrinter in DeviceContext.xml
     */
    public void setPrintBufferSize(int value);

    /**
     * Sets the number of lines to skip before franking for a variety of franking types.
     *
     * See <type>_START_FRANKING_LINE constants for supported franking types.
     *
     * @param franking lines.  This string is parsed into a hashmap and used to format receipts.
     *        Format: <franking type>=<width>[,...]
     *        Example: "frankingLineGiftCertificate=1, frankingLinePurchaseOrder=1"
     */
    public void setFrankingLines(String frankingLines);

    /**
     * End slip removal processing
     *
     * @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#beginRemoval
     */
    public void endSlipRemoval() throws DeviceException;

    /**
     * Initiates slip processing
     *
     * @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#beginSlipInsertion
     */
    public void beginSlipInsertion() throws DeviceException;

    /**
     * Initiates form removal processing
     *
     * @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#beginSlipRemoval
     */
    public void beginSlipRemoval() throws DeviceException;

    /**
     * End slip insertion processing
     *
     * @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#endSlipInsertion
     */
    public void endSlipInsertion() throws DeviceException;
}
