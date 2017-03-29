/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/taximport/TaxImportSite.java /main/12 2011/02/16 09:13:29 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.taximport;

// java imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import oracle.retail.stores.commerceservices.taximport.TaxImportResults;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.SaveTaxMaintenanceTransaction;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;

/**
 *    This class provides the selection of an XML Tax File for importing into
 *    the database.  The tax file is passed to SaveTaxMaintenanceTransaction.
 *    SaveTaxMaintenanceTransaction calls the DataOperation JdbcSaveTaxMaintenance
 *    which handles the XML parsing and database updates.
 *    <P>
 *    @version $Revision: /main/12 $
 * @deprecated as of 13.3. Tax import is done through DIMP.
 */
public class TaxImportSite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = 3355919750693281338L;

    /**
     *    class name constant
     */
    public static final String SITENAME = "TaxImportSite";

    /**
     *  revision number supplied by source-code-control system
     */
    public static String revisionNumber = "$Revision: /main/12 $";

    /**
     *  static strings for messages
     */
    private static final String MSG_TITLE =
        "Tax Import Site Message";
    private static final String MSG_COMPLETED =
        "The Tax File Import has completed, check the TaxImportConduit log file for details!";
    private static final String MSG_ERROR =
        "This file does not conform to the TaxMaintenance.xsd (XML Schema).  Aborting import!";
    private static final String FILE_DIALOG_TITLE =
        "Tax File Import";
    private static final String FILE_DIALOG_BUTTON_TOOLTIP_TEXT =
        "Select a Tax File to import.";

    /**
     *  Selects the tax file and runs the SaveTaxMaintenanceTransaction. <P>
     *  @param bus the bus arriving at this site.
     */
    public void arrive(BusIfc bus)
    {

        JFrame frame = new JFrame();

        try
        {

            // This line is needed to fix a bug in jdk1.3 that causes a pop-up
            // entitled "java.exe - No Disk" when nothing is in the a: drive.
            // Since this program only runs as a stand-alone it will not affect
            // POS or any other application.  Remove after upgrade to jdk1.4.
            System.setSecurityManager(null);

            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            chooser.setDialogTitle(FILE_DIALOG_TITLE);
            chooser.setApproveButtonToolTipText(FILE_DIALOG_BUTTON_TOOLTIP_TEXT);
            chooser.addChoosableFileFilter(new XMLFileFilter());

            int option = chooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION)
            {
                File taxFile = chooser.getSelectedFile();
                if (taxFile.exists() && taxFile.isFile())
                {
                    // if it looks like a real tax maintenance xml file then run the
                    // SaveTaxMaintenanceTransaction
                    if (isGoodTaxFile(taxFile))
                    {
                        SaveTaxMaintenanceTransaction dt = null;
                        
                        dt = (SaveTaxMaintenanceTransaction) DataTransactionFactory.create("persistence_SaveTaxMaintenanceTransaction");
                        
                        TaxImportResults results = dt.saveTaxMaintenance(taxFile); 
                        System.out.println(SaveTaxMaintenanceTransaction.getResultString(results));
                        JOptionPane.showMessageDialog(frame, MSG_COMPLETED, MSG_TITLE,
                                                  JOptionPane.INFORMATION_MESSAGE);                        
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame, MSG_ERROR, MSG_TITLE,
                                                  JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }

        }
        catch (Exception e)
        {
            logger.error(e);
            JOptionPane.showMessageDialog(frame, e.toString(), MSG_TITLE,
                                          JOptionPane.ERROR_MESSAGE);
        }

        // In Windows 95/98 the foundation does not exit correctly when the
        // top service exits.
        System.exit(0);

        bus.mail(new Letter(CommonLetterIfc.NEXT), BusIfc.CURRENT);
    }

    /**
     * This method checks the tax file for minimal conformance to the
     * TaxMaintenance.xsd XML Schema.
     * 
     * @param File the tax file.
     * @return boolean true when minimal file conformance found.
     */
    private boolean isGoodTaxFile(File file)
    {

        try
        {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line = null;
            int xmlTagFound = 0;
            int taxMaintenanceTagFound = 0;
            boolean hasXMLTag = false;
            boolean hasTaxMaintenanceTag = false;
            while ((line = in.readLine()) != null)
            {
                if (hasXMLTag && hasTaxMaintenanceTag)
                {
                    return true;
                }

                if (!hasXMLTag)
                {
                    xmlTagFound = line.indexOf("<?xml");
                    if (xmlTagFound > -1)
                    {
                        hasXMLTag = true;
                    }
                }

                if (!hasTaxMaintenanceTag)
                {
                    taxMaintenanceTagFound = line.indexOf("<TaxMaintenance ");
                    if (taxMaintenanceTagFound > -1)
                    {
                        hasTaxMaintenanceTag = true;
                    }
                }

            }
        }
        catch (Exception e)
        {
            logger.error(e);
        }

        return false;

    }

}

