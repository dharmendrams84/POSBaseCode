/* ===========================================================================
* Copyright (c) 2009, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/device/AbstractPrinterActionGroup.java /main/8 2011/02/27 20:37:37 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/18/11 - refactor printing for switching character sets
 *    cgreene   10/26/10 - refactor alwaysPrintLineFeed into
 *                         AbstractPrinterGroup
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    acadar    12/16/09 - cleanup
 *    acadar    12/15/09 - set frankingCapable to false by default
 *    acadar    12/15/09 - cleanup and refactoring of PdfReceiptPrinter's use
 *                         in POSPrinterActionGroup
 *    acadar    12/14/09 - cleanup
 *    acadar    12/14/09 - made printNormal an abstract method
 * ===========================================================================
 */
package oracle.retail.stores.pos.device;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.transform.stream.StreamSource;

import oracle.retail.stores.manager.device.FoReceiptPrinter;
import oracle.retail.stores.printing.DefaultIppFactory;
import oracle.retail.stores.printing.IppFactoryIfc;
import oracle.retail.stores.printing.PrintRequestAttributesIfc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.XMLManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.device.ReceiptPrinterIfc;
import oracle.retail.stores.foundation.manager.xml.InvalidXmlException;
import oracle.retail.stores.foundation.tour.dtd.DeviceScriptIfc;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.utility.ReflectionUtility;
import oracle.retail.stores.foundation.utility.xml.XMLUtility;

import oracle.retail.stores.pos.device.POSDeviceActionGroup;
import oracle.retail.stores.pos.receipt.EYSPrintableDocumentIfc;
import oracle.retail.stores.pos.receipt.blueprint.BlueprintedReceipt;

/**
 * AbstractPrinterActionGroup defines the Printer specific device operations
 * available to POS applications.
 */
public abstract class AbstractPrinterActionGroup extends POSDeviceActionGroup
    implements PrinterActionGroupIfc
{
    // This id is used to tell the compiler not to generate a new serialVersionUID.
    static final long serialVersionUID = -9070542490763838714L;

	/**
	 * number of characters in a receipt print line
     * @deprecated as of 13.4. See configuration in DeviceContext.xml instead.
	 */
    protected int receiptLineSize = 40;

    /**
     * Char widths
     */
    protected Map<String,Integer> characterWidths = new HashMap<String,Integer>();

    /**
     * Always print line feeds at the end of lines (don't rely on the printer's
     * wrap capability to "make" line feeds)
     */
    protected boolean alwaysPrintLineFeeds = false;

    /**
     * franking capable flag
     */
    protected boolean frankingCapable = false;

    /** FO Receipt Printer for clean receipt/IPP */
    protected FoReceiptPrinter fopReceiptPrinter;

    /** Clean Receipt/IPP - A map of blueprint document types to xml file names.*/
    protected Map<String,String> eReceiptDocTypeXMLMapping = new HashMap<String,String>();

    /** A map of blueprint document types to fop file names.*/
    protected Map<String,String> eReceiptDocTypeFOPMapping = new HashMap<String,String>();

    /** Clean receipt/IPP configuration file for fop */
    protected String eReceiptFopConfig;

    /** Clean Receipt/IPP Configuration file for xml*/
    protected String eReceiptXmlConfig;

    /** The directory containing (*.xml) files for clean receipt/ipp templates*/
    protected String xmlFilesDirectory;

    /** The directory containing (*.fo) files for clean receipt/ipp templates*/
    protected String fopFilesDirectory;

    /** EReceipt/IPP attributes
     * @deprecated as of 13.4. Use configurations in DeviceContext.xml instead.
     */
    protected PrintRequestAttributesIfc settings;

    /** Factory Instance used for clean receipt/ipp
     * @deprecated as of 13.4. See configuration in DeviceContext.xml instead.
     */
    private IppFactoryIfc factoryInstance;

    /** factory class name
     * @deprecated as of 13.4. See configuration in DeviceContext.xml instead.
     */
    private String factoryClassName;

    /** Configuration file for the fop fonts
     * @deprecated as of 13.4. See configuration in DeviceContext.xml instead.
     */
    private String fopFontConfigFile;

    /**  Mime type for fo rendering*/
    protected String foMimeType = "application/pdf";

    /** File Extension for fo files used by the ereceipt/ipp **/
    protected String foFileExtension = "pdf";

    /**
     * @return the foFileExtension
     */
    public String getFoFileExtension()
    {
        return foFileExtension;
    }

    /**
     * @param foFileExtension the foFileExtension to set
     */
    public void setFoFileExtension(String foFileExtension)
    {
        this.foFileExtension = foFileExtension;
    }

    /**
     * Prints the document
     *
     * @param EYSPrintableTransactionIfc
     * @throws DeviceException if error occurs
     */
    public abstract void printDocument(EYSPrintableDocumentIfc document) throws DeviceException;

    /**
     * PrintNormal
     *
     * @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#printNormal
     */
    public abstract void printNormal(int station, String data) throws DeviceException;

    /**
     * Gets the Printer attributes
     * @return
     * @deprecated as of 13.4. Use configurations in DeviceContext.xml instead.
     */
    public abstract PrintRequestAttributesIfc getSettings();

    /**
     * @return the fopFontConfigFile
     * @deprecated as of 13.4. See configuration in DeviceContext.xml instead.
     */
    public String getFopFontConfigFile()
    {
        return fopFontConfigFile;
    }

    /**
     * @param fopFontConfigFile the fopFontConfigFile to set
     * @deprecated as of 13.4. See configuration in DeviceContext.xml instead.
     */
    public void setFopFontConfigFile(String fopFontConfigFile)
    {
        this.fopFontConfigFile = fopFontConfigFile;
    }

    /**
     * Creates an IppFactory instance
     * @return
     * @deprecated as of 13.4. See configuration in DeviceContext.xml instead.
     */
     public IppFactoryIfc getFactoryInstance()
    {
       if(factoryInstance == null)
       {
            try
            {
                factoryInstance = (IppFactoryIfc)Class.forName(factoryClassName).newInstance();
                factoryInstance.setFontConfig(this.fopFontConfigFile);

             }
             catch (Exception e)
             {
               logger.error("Cannot create instance for: " + factoryClassName, e);
                //return default factory
               factoryInstance = new DefaultIppFactory();
            }
       }
        return factoryInstance;
    }

    /**
     *  Sets the name of the factory class
     * @param factory
     */
    public void setFactoryClassName(String factory)
    {
        this.factoryClassName = factory;
    }

    /**
     * @param settings the settings to set
     * @deprecated as of 13.4. Use configurations in DeviceContext.xml instead.
     */
    public void setSettings(PrintRequestAttributesIfc settings)
    {
        this.settings = settings;
    }

    /**
     * @return the fopFilesDirectory
     */
    public String getFopFilesDirectory()
    {
        return fopFilesDirectory;
    }

    /**
     * @param fopFilesDirectory the fopFilesDirectory to set
     */
    public void setFopFilesDirectory(String fopFilesDirectory)
    {
        this.fopFilesDirectory = fopFilesDirectory;
    }

    /**
     * @return the xmlFilesDirectory
     */
    public String getXmlFilesDirectory()
    {
        return xmlFilesDirectory;
    }

    /**
     * @param xmlFilesDirectory the xmlFilesDirectory to set
     */
    public void setXmlFilesDirectory(String xmlFilesDirectory)
    {
        this.xmlFilesDirectory = xmlFilesDirectory;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.device.PrinterActionGroupIfc#getCharWidths()
     */
    public Map<String,Integer> getCharWidths()
    {
        return characterWidths;
    }

    /**
     * This method converts the string defined in posdevices.xml into a hash map
     * of UnicodeBlock names and their printed widths.
     * 
     * @see oracle.retail.stores.pos.device.POSPrinterActionGroupIfc#setCharacterWidths
     */
    public void setCharacterWidths(String charWidths)
    {
        parseTokenString(characterWidths, charWidths);
    }

    /**
     * Retrieves the number of characters on a slip print line. This must be set
     * to a valid value for the hardware in use.
     *
     * @return number of receipt line characters
     * @deprecated as of 13.4. See configuration in DeviceContext.xml instead.
     */
    public int getReceiptLineSize()
    {
        return receiptLineSize;
    }

    /**
     * Sets the number of characters on a receipt print line. This must be set
     * to a valid value for the hardware in use.
     *
     * @param value number of receipt line characters
     * @deprecated as of 13.4. See configuration in DeviceContext.xml instead.
     */
    public void setReceiptLineSize(int value)
    {
        receiptLineSize = value;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.device.PrinterActionGroupIfc#isFrankingCapable()
     */
    public Boolean isFrankingCapable() throws DeviceException
    {
        return this.frankingCapable? Boolean.TRUE : Boolean.FALSE;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.device.PrinterActionGroupIfc#setFrankingCapable(boolean)
     */
    public void setFrankingCapable(boolean frankingCapable)
    {
        this.frankingCapable = frankingCapable;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.device.PrinterActionGroupIfc#isAlwaysPrintLineFeeds()
     */
    public boolean isAlwaysPrintLineFeeds()
    {
        return alwaysPrintLineFeeds;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.device.PrinterActionGroupIfc#setAlwaysPrintLineFeeds(boolean)
     */
    public void setAlwaysPrintLineFeeds(boolean alwaysPrintLineFeeds)
    {
        this.alwaysPrintLineFeeds = alwaysPrintLineFeeds;
    }

    /**
     * This is a utility function to parse token paired values from a string specified in the posdevices.xml
     *
     * This is used for the franking start lines  and the character widths specifications
     *
     * @param mappedValues
     * @param tokenizedValues
     */
    protected static void parseTokenString(Map<String, Integer> mappedValues, String tokenizedValues)
    {
        StringTokenizer tokenizer = new StringTokenizer(tokenizedValues, ",");
        while (tokenizer.hasMoreTokens())
        {
            String keyValuePair = tokenizer.nextToken();
            StringTokenizer keyValuePairTokenizer = new StringTokenizer(keyValuePair, "=");

            String key = keyValuePairTokenizer.nextToken();
            String value = keyValuePairTokenizer.nextToken();

            mappedValues.put(key, Integer.valueOf(value));
        }
    }

    /**
     * Prints a report
     *
     * @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#printReceipt
     */
    public  void printReport(String reportType, Serializable rptObj) throws DeviceException
    {
        //do nothing
    }

    /**
     * CutPaper
     *
     * @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#cutPaper
     */
    public void cutPaper(int percentage) throws DeviceException
    {
        //do nothing
    }

    /**
     * @return the fopConfig
     */
    public String getEReceiptFopConfig()
    {
        return eReceiptFopConfig;
    }

    /**
     * @param fopConfig
     */
    public void setEReceiptFopConfig(String fopConfig)
    {
        this.eReceiptFopConfig = fopConfig;
        if (eReceiptFopConfig != null && !eReceiptFopConfig.equals(""))
        {
            configure(eReceiptFopConfig, this.eReceiptDocTypeFOPMapping, DeviceScriptIfc.ELEM_ERECEIPT);
        }
    }

    /**
     * @return the xmlConfig
     */
    public String getEReceiptXmlConfig()
    {
        return eReceiptXmlConfig;
    }

    /**
     * @return the docTypeXMLMapping
     */
    public Map<String, String> getEReceiptDocTypeXMLMapping()
    {
        return eReceiptDocTypeXMLMapping;
    }

    /**
     * @param docTypeXMLMapping the docTypeXMLMapping to set
     */
    public void setEReceiptDocTypeXMLMapping(Map<String, String> docTypeXMLMapping)
    {
        this.eReceiptDocTypeXMLMapping = docTypeXMLMapping;
    }

    /**
     * Sets the xmlConfiguration file
     * @param xmlConfig
     */
    public void setEReceiptXmlConfig(String xmlConfig)
    {
        this.eReceiptXmlConfig = xmlConfig;
        if (eReceiptXmlConfig != null && !eReceiptXmlConfig.equals(""))
        {
            configure(eReceiptXmlConfig, this.eReceiptDocTypeXMLMapping, DeviceScriptIfc.ELEM_ERECEIPT);
        }
    }

    /**
     * @return the foMimeType
     */
    public String getFoMimeType()
    {
        return foMimeType;
    }

    /**
     * @param foMimeType the foMimeType to set
     */
    public void setFoMimeType(String foMimeType)
    {
        this.foMimeType = foMimeType;
    }

    /**
     * Configures this manager per the script file name specified.
     */
    protected void configure(String configScript, Map<String,String> mapping, String elementName)
    {
        //  Local variables
        Document xmlConfig = null;
        Element xmlRoot = null;

        //  Get XMLManager
        XMLManagerIfc xmlMgr =
            (XMLManagerIfc) Gateway.getDispatcher().getManager(
                XMLManagerIfc.TYPE);

        //  If no XMLManager is found, shutdown
        if (xmlMgr == null)
        {
            logger.error(getClass().getName() +  ".configure(String): XMLManager not found.");
        }

        //  Get XML configuration file
        xmlConfig = xmlMgr.getXMLTree(configScript);
        //  If no XML configuration file is found, shutdown
        if (xmlConfig == null)
        {
            logger.error(
                    getClass().getName() +  ".configure(String) config script file not found: " + configScript);
        }

        //  Get the document element for the XML file that will be
        //  used for searching the file structure
        xmlRoot = xmlConfig.getDocumentElement();

        // delegate with element parameter
        configure(xmlRoot, configScript, mapping, elementName);
    }

    /**
     * Configure xml
     * @param xmlRoot
     * @param configScript
     * @param mapping
     */
    protected void configure(Element xmlRoot, String configScript, Map<String,String> mapping, String elemName)
    {
        Element[] dmProps = XMLUtility.getChildElements(xmlRoot, DeviceScriptIfc.ELEM_PROPERTY);

        for (int y = 0; y < dmProps.length; y++)
        {
            try
            {
                String dmPropName = dmProps[y].getAttribute(DeviceScriptIfc.ATTR_PROPNAME);
                String dmPropValue = dmProps[y].getAttribute(DeviceScriptIfc.ATTR_PROPVALUE);
                String dmPropType = dmProps[y].getAttribute(DeviceScriptIfc.ATTR_PROPTYPE);
                if (logger.isDebugEnabled())
                    logger.debug("set prop " + dmPropName + " to " + dmPropValue);
                ReflectionUtility.setProperty(this, dmPropName, dmPropType, dmPropValue);
            }
            catch (Exception e)
            {
                logger.error("Error reading config script " + configScript, e);
            }
        }

        Element[] receiptElements = XMLUtility.getChildElements(xmlRoot, elemName);

        for (int i = 0; i < receiptElements.length; i++)
        {
            String docType = receiptElements[i].getAttribute(DeviceScriptIfc.ATTR_TYPE);
            String fileName = receiptElements[i].getAttribute(DeviceScriptIfc.ATTR_FILENAME);
            if (logger.isDebugEnabled())
                logger.debug("set receipt " + docType + " to " + fileName);
            mapping.put(docType, fileName);
        }
    }

    /**
     * Set the receipt printer onto the document being printed. Optionally
     * apply blueprint-specific settings if the doc is a
     * {@link BlueprintedReceipt}.
     *
     * @param document
     * @param receiptPrinter
     *
     * @see #getReceiptLineSize()
     * @see #alwaysPrintLineFeeds()
     * @see #getCharWidths()
     */
    protected void applyReceiptPrinter(EYSPrintableDocumentIfc document, ReceiptPrinterIfc receiptPrinter)
    {
        document.setPrinter(receiptPrinter);
        if (document instanceof BlueprintedReceipt)
        {
            ((BlueprintedReceipt)document).setAlwaysPrintLineFeeds(isAlwaysPrintLineFeeds());
            ((BlueprintedReceipt)document).setCharWidths(getCharWidths());
        }
    }

    /**
     * Gets the Document
     * @param documentType
     * @return Document
     */
    protected Document getXMLTemplate(String documentType, Map<String,String> xmlMapping )
    {
        logger.debug("getting XML template for: " + documentType);
        // get name of file for bean from resource bundle

        String fileName = xmlMapping.get(documentType);
        File file = new File(this.xmlFilesDirectory + "/" + fileName) ;

        Document doc = null;
        try
        {
            // read a file
            FileInputStream fis = new FileInputStream(file);
            doc = XMLUtility.getDocument(fis, false);
        }
        catch (FileNotFoundException fe)
        {
            logger.error("XML Template not found ", fe);
        }
        catch (InvalidXmlException xmle)
        {
            logger.error("Xml Template invalid ", xmle);
        }
        return doc;
    }

    /**
     * Returns the FOP template
     * @return Source
     */
    protected StreamSource getFOPTemplate(String documentType, Map<String,String> fopMapping )
    {
        logger.debug("getting FOP template for: " + documentType);
        // get name of file for bean from resource bundle

        String fileName = fopMapping.get(documentType);
        File foFile = new File(this.fopFilesDirectory + "/" + fileName) ;
        StreamSource xslFo = new StreamSource(foFile);
        return xslFo;
    }

    /**
     * @return the eReceiptDocTypeFOPMapping
     */
    public Map<String, String> getEReceiptDocTypeFOPMapping()
    {
        return eReceiptDocTypeFOPMapping;
    }

    /**
     * @param receiptDocTypeFOPMapping the eReceiptDocTypeFOPMapping to set
     */
    public void setEReceiptDocTypeFOPMapping(Map<String, String> receiptDocTypeFOPMapping)
    {
        eReceiptDocTypeFOPMapping = receiptDocTypeFOPMapping;
    }
}