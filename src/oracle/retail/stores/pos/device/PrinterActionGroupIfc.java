/* ===========================================================================
* Copyright (c) 2009, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/device/PrinterActionGroupIfc.java /main/6 2011/02/27 20:37:37 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/18/11 - refactor printing for switching character sets
 *    cgreene   10/26/10 - refactor alwaysPrintLineFeed into
 *                         AbstractPrinterGroup
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    acadar    12/15/09 - cleanup and refactoring of PdfReceiptPrinter's use
 *                         in POSPrinterActionGroup
 *    acadar    12/14/09 - cleanup
 *    acadar    12/07/09 - updates
 *    acadar    12/03/09 - changes for EYSPRintableDoc
 *    acadar    11/24/09 - enable receipt printing
 *    acadar    11/24/09 - super class for all printer action groups
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.device;


import java.io.Serializable;
import java.util.Map;

import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.device.DeviceActionGroupIfc;
import oracle.retail.stores.pos.receipt.EYSPrintableDocumentIfc;

/**
 * PrinterActionGroupIfc defines printer specific operations
 * available to POS applications.
 */
public interface PrinterActionGroupIfc extends DeviceActionGroupIfc
{

    /**
     * Type key of this action group.
     */
    public static final String TYPE = "PrinterActionGroupIfc";

    /**
     * Prints the receipt
     *
     * @param EYSPrintableDocumentIfc
     * @throws DeviceException if error occurs
     */
    public void printDocument(EYSPrintableDocumentIfc document) throws DeviceException;

    /**
     *
     * @return
     */
    public int getReceiptLineSize();

    /**
     * Sets the number of characters on a receipt print line. This must be set
     * to a valid value for the hardware in use.
     *
     * @param value number of receipt line characters
     */
    public void setReceiptLineSize(int value);

    /**
     * Returns a boolean flag indicating printer is franking-capable
     *
     * @return flag indicating printer is franking-capable
     */
    public Boolean isFrankingCapable() throws DeviceException;

    /**
     * Sets the franking capable flag
     * @param frankingCapable
     */
    public void setFrankingCapable(boolean frankingCapable);

    /**
     * Should the formatter always send line feeds to the printer at the end of lines.
     * (Rather than relying on the printer to wrap properly when, say, a 40 character wide
     * string is sent to a 40 character wide printer.)
     *
     * @return true if the printer should always print line feeds.
     */
    public boolean isAlwaysPrintLineFeeds();

    /**
     * Sets flag to control when line feeds are used.
     *
     * @param true if line feeds should always be sent at the end of lines.
     */
    public void setAlwaysPrintLineFeeds(boolean alwaysPrintLineFeeds);

    /**
     * PrintNormal
     *
     * @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#printNormal
     */
    public void printNormal(int station, String data) throws DeviceException;

    /**
     * Sets the character widths for all fonts supported by the printer which are used by the application.
     *
     * @param character widths for fonts.  This string is parsed into a hashmap
     * and used to format receipts.
     *        Format: <java.lang.Character.UnicodeBlock name>=<width>[,...]
     *        Example: "BASIC_LATIN=1,LATIN_1_SUPPLEMENT=1,GENERAL_PUNCTUATION=1,CJK_UNIFIED_IDEOGRAPHS=2"
     * When a Unicode block is encountered not in this list, the default width (1) is used and an error is logged.
     *
     */
    public void setCharacterWidths(String charWidths);

    /**
     * Retrieves the mapping of all required UnicodeBlocks and their character widths.
     *
     * For instance, Latin (includes english) is width 1.0 and Chinese is width 2.0
     *
     * @return Mapping of UnicodeBlocks and their character widths.
     */
    public Map<String,Integer> getCharWidths();

    /**
     * CutPaper
     *
     * @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#cutPaper
     */
    public void cutPaper(int percentage) throws DeviceException;

}