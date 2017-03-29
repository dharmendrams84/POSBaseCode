/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/rtlog/RegenerateRTLogSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:11 mszekely Exp $
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
 *  3    360Commerce 1.2         7/23/2007 1:44:29 PM   Maisa De Camargo
 *       Removed Unused Import
 *  2    360Commerce 1.1         7/23/2007 1:09:32 PM   Maisa De Camargo
 *       Updated regenerateRTLog output
 *  1    360Commerce 1.0         5/1/2007 10:12:08 AM   Jack G. Swan    Changes
 *        for merge to trunk.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.rtlog;

import java.util.Date;

import oracle.retail.stores.exportfile.ExportFileResultAuditLogIfc;

import org.apache.log4j.Logger;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.manager.rtlog.RTLogExportDaemonManagerIfc;
import oracle.retail.stores.domain.manager.rtlog.RTLogRegenerationCriteriaIfc;
import oracle.retail.stores.domain.manager.rtlog.RTLogRegenerationCriteriaLoader;
import oracle.retail.stores.foundation.tour.conduit.Dispatcher;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;

/**
 * This site is responsible for reading the RTLog regeneration criteria property
 * file and for finding and using the RTLogExportDaemonManager.
 */
public class RegenerateRTLogSite extends PosSiteActionAdapter
{
    /** Generate Serial Version UID */
    private static final long serialVersionUID = -2840819504976847297L;

    /** Logger */
    private static Logger logger = Logger.getLogger(RegenerateRTLogSite.class);

    /**
     * This method reads the criteria properties file and invokes the
     * RTLogExportDaemonManager.
     * The RTLogExportDaemonManager will invoke the regenerateRTLog Process.
     * When the process is completed, this method will send the results to the System.out.
     * 
     * @param bus BusIfc
     * @see oracle.retail.stores.foundation.tour.application.SiteActionAdapter#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    public void arrive(BusIfc bus)
    {
        try
        {
            logger.info("Starting RTLogRegeneration");
            Date startDate = new Date();

            RTLogRegenerationCriteriaLoader loader = new RTLogRegenerationCriteriaLoader();
            RTLogRegenerationCriteriaIfc rtLogRegenerationCriteria = loader.load();

            RTLogExportDaemonManagerIfc rtLogManager = (RTLogExportDaemonManagerIfc) Dispatcher.getDispatcher()
                    .getManager(RTLogExportDaemonManagerIfc.TYPE);

            ExportFileResultAuditLogIfc[] results = rtLogManager.regenerateRTLogBatch(rtLogRegenerationCriteria);

            Date endDate = new Date();
            long totalTime = (endDate.getTime() - startDate.getTime()) / 1000L;

            logger.info("RTLogRegeneration Successful. Total Time: " + totalTime + " seconds");

            if (results != null && results.length > 0)
            {
                for (int i=0; i < results.length; i++)
                {
                    System.out.println("RTLogRegeneration Successful");
                    System.out.println("Total Time: " + totalTime + " seconds");
                    System.out.println("Results: ");
                    System.out.println(results[i].toString());
                }
            }
            else
            {
                logger.info ("RTLogRegeneration Failed. No transactions were found matching the search criteria.");
                System.out.println("RTLogRegeneration Failed");
                System.out.println("No transactions were found matching the search criteria.");
            }
        }
        catch (Exception e)
        {
            logger.error("RTLogRegeneration Failed: " + e.getMessage());
            System.out.println("RTLogRegeneration Failed");
            System.out.println("Error: " + e.getMessage());
            System.out.println("See log files for more details");
        }

        bus.mail(CommonLetterIfc.NEXT, BusIfc.CURRENT);
    }
}
