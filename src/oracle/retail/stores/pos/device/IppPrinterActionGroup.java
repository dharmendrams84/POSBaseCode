/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/device/IppPrinterActionGroup.java /main/14 2011/02/27 20:37:37 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/18/11 - refactor printing for switching character sets
 *    cgreene   10/26/10 - refactor alwaysPrintLineFeed into
 *                         AbstractPrinterGroup
 *    rsnayak   10/26/10 - Bill Pay ereceipt changes
 *    rsnayak   10/07/10 - Bill Pay E-Receipt fix
 *    
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    acadar    02/05/10 - merged with Jack's changes for ereceipt printing
 *    jswan     02/04/10 - Fixing two defects (HPQC 261 and 680), issue with
 *                         gift reciepts and returns, email not sent for sales
 *                         when configured for network printer.
 *    abondala  01/03/10 - update header date
 *    acadar    12/16/09 - cleanup
 *    acadar    12/15/09 - cleanup and refactoring of PdfReceiptPrinter's use
 *                         in POSPrinterActionGroup
 *    acadar    12/14/09 - cleanup
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.device;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import oracle.retail.stores.common.context.BeanLocator;
import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.common.utility.Util;

import oracle.retail.stores.domain.transaction.BillPayTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;

import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.device.ReceiptPrinterIfc;
import oracle.retail.stores.foundation.tour.dtd.DeviceScriptIfc;


import oracle.retail.stores.pos.receipt.EYSPrintableDocumentIfc;
import oracle.retail.stores.pos.receipt.ReceiptParameterBeanIfc;
import oracle.retail.stores.pos.receipt.blueprint.BlueprintedReceipt;


import oracle.retail.stores.printing.PrintRequestAttributes;
import oracle.retail.stores.printing.PrintRequestAttributesIfc;
import oracle.retail.stores.manager.device.FoReceiptPrinter;

/**
 * IppPrinterActionGroup defines the IppPrinter specific device operations
 * available to POS applications.
 */
public class IppPrinterActionGroup extends AbstractPrinterActionGroup
{
    private static final long serialVersionUID = 6862257207552684288L;

    /** PrintService instance*/
    private String printService;

    /** printer mine type*/
    private String printMimeType = "application/pdf";

    /** Printer locale*/
    private Locale locale;

    /** Paper orientation*/
    private String orientation;

    /** Number of copies*/
    private int copies = 1;

    /** Media*/
    private String media;

    /**Sheet Collate flag */
    private boolean sheetCollate = false;

    /**Sides */
    private String sides;

    /** Quality*/
    private String quality;

    /**configuration file for fop */
    private String fopConfig;

    /** Configuration file for xml*/
    private String xmlConfig;

    /** Data files location*/
    private String dataLocation;

    /** boolean that indicates if data files will be deleted
     * @deprecated as of 13.4. See configuration in DeviceContext.xml instead.
     */
    private boolean dataDelete;

    /** A map of blueprint ereceipt document types to xml file names.*/
    private Map<String,String> docTypeXMLMapping = new HashMap<String,String>();

    /** A map of blueprint ereceipt document types to fop file names.*/
    private Map<String,String> docTypeFOPMapping = new HashMap<String,String>();

    /**
     * Formats a document and sends it to the printer
     * @param EYSPrintableDocumentIfc
     * @throws DeviceException if error occurs
     */
    public void printDocument(EYSPrintableDocumentIfc document) throws DeviceException
    {
        boolean isCleanReceipt = false;
        if(document instanceof BlueprintedReceipt && ((BlueprintedReceipt)document).getParameterBean() instanceof ReceiptParameterBeanIfc)
        {
            ReceiptParameterBeanIfc parameters = (ReceiptParameterBeanIfc)((BlueprintedReceipt)document).getParameterBean();
            if(parameters.getTransaction() instanceof SaleReturnTransactionIfc)
            {
                if(parameters.isEreceipt())
                {
                    isCleanReceipt = true;
                }

            }
            else if (parameters.getTransaction() instanceof BillPayTransactionIfc)
            { // Added for bill pay transactions to print e-receipts
                    if (parameters.isEreceipt())
                    {
                        isCleanReceipt = true;
                    }
            }

        }
        // If eReceipt then create PdfReceiptPrinter object instead of FoReceiptPrinter.
        ReceiptPrinterIfc receiptPrinter = getReceiptPrinter(document, isCleanReceipt);

        // create new Receipt
        applyReceiptPrinter(document, receiptPrinter);

        // print Receipt
        document.printDocument();
    }

    /**
     * PrintNormal
     *
     * @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#printNormal
     */
    public void printNormal(int station, String data) throws DeviceException
    {
        ReceiptPrinterIfc receiptPrinter = getReceiptPrinter();
        receiptPrinter.printJob(data);
    }

    /**
     * Creates an instance of the XMLReceiptPrinter
     * @param EYSPrintableDocumentIfc document
     * @return XmlReceiptPrinter
     */
    protected ReceiptPrinterIfc getReceiptPrinter() throws DeviceException
    {
        if (fopReceiptPrinter == null)
        {
            fopReceiptPrinter = (FoReceiptPrinter)BeanLocator.getDeviceBean(FoReceiptPrinter.BEAN_KEY_IPP);
        }
        fopReceiptPrinter.setCleanReceipt(false);

        // for reports the filename consists of the blueprintName + registerid + timestamp
        // for all the rest, filename is the transaction id
        String blueprintName = "Default";

        String fileName = blueprintName + System.currentTimeMillis();
        fopReceiptPrinter.setOutputFileName(fileName);

        //set the blueprint name in the IppPrinter so we can  locate the associated fop
        //load the xml configuration file
        fopReceiptPrinter.setXmlDocument(getXMLTemplate(blueprintName, this.docTypeXMLMapping));
        fopReceiptPrinter.setFopTemplate(getFOPTemplate(blueprintName, this.docTypeFOPMapping));

        //initializeIppPrinter();
        return fopReceiptPrinter;
    }

    /**
     * Creates an instance of the XMLReceiptPrinter
     * @param EYSPrintableDocumentIfc document
     * @return XmlReceiptPrinter
     */
    protected ReceiptPrinterIfc getReceiptPrinter(EYSPrintableDocumentIfc document, boolean isCleanReceipt) throws DeviceException
    {
        if(fopReceiptPrinter == null)
        {
            fopReceiptPrinter = (FoReceiptPrinter)BeanLocator.getDeviceBean(FoReceiptPrinter.BEAN_KEY_IPP);
        }
        fopReceiptPrinter.setCleanReceipt(isCleanReceipt);

        // for reports the filename consists of the blueprintName + registerid + timestamp
        // for all the rest, filename is the transaction id
        String blueprintName = (((BlueprintedReceipt)document).getParameterBean().getDocumentType());

        String fileName = blueprintName + System.currentTimeMillis();
        if ( ((BlueprintedReceipt)document).getParameterBean() instanceof ReceiptParameterBeanIfc)
        {
            ReceiptParameterBeanIfc rpBean = ((ReceiptParameterBeanIfc)((BlueprintedReceipt)document).getParameterBean());
            if(rpBean.getTransaction() != null)
            {
                fileName = rpBean.getTransaction().getTransactionID();
            }
            if (!Util.isEmpty(rpBean.getEReceiptFileNameAddition()))
            {
                fileName = fileName + rpBean.getEReceiptFileNameAddition();
            }
        }

        //set the blueprint name in the IppPrinter so we can  locate the associated fop
        fopReceiptPrinter.setOutputFileName(fileName);

        //load the xml configuration file
        if(isCleanReceipt)
        {
            fopReceiptPrinter.setXmlDocument(getXMLTemplate(blueprintName, getEReceiptDocTypeXMLMapping()));
            fopReceiptPrinter.setFopTemplate(getFOPTemplate(blueprintName, getEReceiptDocTypeFOPMapping()));
        }
        else
        {
            fopReceiptPrinter.setXmlDocument(getXMLTemplate(blueprintName, this.docTypeXMLMapping));
            fopReceiptPrinter.setFopTemplate(getFOPTemplate(blueprintName, this.docTypeFOPMapping));
        }

        return fopReceiptPrinter;
    }

    /**
     * Gets the Printer attributes
     * @return
     * @deprecated as of 13.4. Use configurations in DeviceContext.xml instead.
     */
    public PrintRequestAttributesIfc getSettings()
    {
        if(settings == null)
        {
            settings = new PrintRequestAttributes();
        }

        settings.setCopies(1);
        settings.setMedia(getMedia());
        settings.setOrientation(getOrientation());
        settings.setQuality(getQuality());
        settings.setSheetCollate(this.isSheetCollate());
        settings.setSides(this.getSides());
        settings.setLocale(this.getLocale());
        settings.setPrintMimeType(this.getPrintMimeType());
        settings.setFoMimeType(this.getFoMimeType());
        settings.setFoFileExtension(this.getFoFileExtension());
        settings.setPrintServiceName(getPrintService());
        settings.setDataLocation(this.getDataLocation());
        settings.setFactoryInstance(this.getFactoryInstance());
        return settings;
    }

    /**
     * Gets the configuration for the fop
     * @return
     */
    public String getFopConfig()
    {
        return fopConfig;
    }

    /**
     *
     * @param fopConfig
     */
    public void setFopConfig(String fopConfig)
    {
        this.fopConfig = fopConfig;
        if (fopConfig != null && !fopConfig.equals(""))
        {
            configure(fopConfig, this.docTypeFOPMapping, DeviceScriptIfc.ELEM_RECEIPT);
        }
    }

    /**
     * gets the xml configuration
     * @return
     */
    public String getXmlConfig()
    {
        return xmlConfig;
    }

    /**
     * Sets the xmlConfiguration file
     * @param xmlConfig
     */
    public void setXmlConfig(String xmlConfig)
    {
        this.xmlConfig = xmlConfig;
        if (xmlConfig != null && !xmlConfig.equals(""))
        {
            configure(xmlConfig, this.docTypeXMLMapping, DeviceScriptIfc.ELEM_RECEIPT);
        }
    }

    /**
     * Gets the Printer Locale
     * @return
     */
    public Locale getLocale()
    {
        return locale;
    }

    /**
     * Sets the printer locale
     * @param locale
     */
    public void setLocale(String locale)
    {
        //initialize to the default  locale
        if( Util.isEmpty(locale))
        {
            this.locale = LocaleMap.getLocale(LocaleMap.DEFAULT);
        }
        else
        {
            String[] localeArray = {"","",""};
            StringTokenizer tokenizer = new StringTokenizer(locale, "_");
            int i = 0;

            while (tokenizer.hasMoreTokens())
            {
                localeArray[i] = tokenizer.nextToken();
                i = i +1 ;
            }
            this.locale = new Locale(localeArray[0], localeArray[1], localeArray[2]);
        }
    }

    /**
     * Gets the Print Service
     * @return
     */
    public String getPrintService()
    {
        return printService;
    }
    /**
     * Sets the print service
     * @param printService
     */
    public void setPrintService(String printService)
    {
        this.printService = printService;
    }

    /**
     * Indicates if generated xml and fop data can be deleted
     * @return boolean
     * @deprecated as of 13.4. See configuration in DeviceContext.xml instead.
     */
    public boolean isDataDelete()
    {
        return dataDelete;
    }

    /**
     * Sets the data delete
     * @param dataDelete
     * @deprecated as of 13.4. See configuration in DeviceContext.xml instead.
     */
    public void setDataDelete(boolean dataDelete)
    {
        this.dataDelete = dataDelete;
    }

    /**
     * Gets the data location
     * @return
     */
    public String getDataLocation()
    {
        return dataLocation;
    }

    /**
     * Sets the data location
     * @param dataLocation
     */
    public void setDataLocation(String dataLocation)
    {
        this.dataLocation = dataLocation;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.printing.PrintRequestAttributesIfc#getOrientation()
     */
    public String getOrientation()
    {
        return orientation;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.printing.PrintRequestAttributesIfc#setOrientation(java.lang.String)
     */
    public void setOrientation(String orientation)
    {
        this.orientation = orientation;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.printing.PrintRequestAttributesIfc#getCopies()
     */
    public int getCopies()
    {
        return copies;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.printing.PrintRequestAttributesIfc#setCopies(int)
     */
    public void setCopies(int copies)
    {
        this.copies = copies;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.printing.PrintRequestAttributesIfc#getMedia()
     */
    public String getMedia()
    {
        return media;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.printing.PrintRequestAttributesIfc#setMedia(java.lang.String)
     */
    public void setMedia(String media)
    {
        this.media = media;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.printing.PrintRequestAttributesIfc#isSheetCollate()
     */
    public boolean isSheetCollate()
    {
        return sheetCollate;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.printing.PrintRequestAttributesIfc#setSheetCollate(boolean)
     */
    public void setSheetCollate(boolean sheetCollate)
    {
        this.sheetCollate = sheetCollate;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.printing.PrintRequestAttributesIfc#getSides()
     */
    public String getSides()
    {
        return sides;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.printing.PrintRequestAttributesIfc#setSides(java.lang.String)
     */
    public void setSides(String sides)
    {
        this.sides = sides;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.printing.PrintRequestAttributesIfc#getQuality()
     */
    public String getQuality()
    {
        return quality;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.printing.PrintRequestAttributesIfc#setQuality(java.lang.String)
     */
    public void setQuality(String quality)
    {
        this.quality = quality;
    }

    /**
     * @return the printMimeType
     */
    public String getPrintMimeType()
    {
        return printMimeType;
    }

    /**
     * @param printMimeType the printMimeType to set
     */
    public void setPrintMimeType(String printMimeType)
    {
        this.printMimeType = printMimeType;
    }
}