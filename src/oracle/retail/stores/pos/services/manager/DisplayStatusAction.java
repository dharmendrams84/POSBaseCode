/* ===========================================================================
* Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/manager/DisplayStatusAction.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:11 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/02/10 - initial version
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import oracle.retail.stores.foundation.manager.gui.UIException;
import oracle.retail.stores.foundation.manager.gui.UISubsystem;
import oracle.retail.stores.foundation.manager.ifc.TierTechnicianIfc;
import oracle.retail.stores.foundation.tour.conduit.Dispatcher;
import oracle.retail.stores.foundation.tour.gate.TechnicianNotFoundException;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DetailStatusBeanModel;

import org.apache.log4j.Logger;

/**
 * An action that will display the {@link DisplayRegisterStatusSite} in a popup
 * dialog. This action expects that the {@link TierTechnicianIfc} is running
 * as one of the {@link Dispatcher}'s local technicians.
 *
 * @author cgreene
 * @since 13.3
 */
public class DisplayStatusAction implements ActionListener
{
    private static final Logger logger = Logger.getLogger(DisplayStatusAction.class);

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            TierTechnicianIfc tierTechnician = (TierTechnicianIfc)Dispatcher.getDispatcher().getLocalTechnician("APPLICATION");
            if (tierTechnician != null)
            {
                BusIfc[] buses = tierTechnician.getBuses();
                if (buses.length > 0)
                {
                    // Setup bean model information for the UI to display
                    DetailStatusBeanModel beanModel = new DisplayStatusSite().buildStatusModel(buses[0]);
    
                    UISubsystem ui = UISubsystem.getInstance();
                    try
                    {
                        ui.showDialog(POSUIManagerIfc.DEVICE_STATUS_DIALOG, beanModel);
                    }
                    catch (UIException ex)
                    {
                        logger.error("Unable to display popup system status dialog.", ex);
                    }
                }
                else
                {
                    logger.warn("Could not find an active bus to display system status with.");
                }
            }
            else
            {
                logger.error("TierTechnician is not configured for \"APPLICATION\" for the Dispatcher.");
            }
        }
        catch (TechnicianNotFoundException ex)
        {
            logger.error("TierTechnician is not configured for \"APPLICATION\" for the Dispatcher.", ex);
        }
    }

}
