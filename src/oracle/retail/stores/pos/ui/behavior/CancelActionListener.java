/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/behavior/CancelActionListener.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:59 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   09/18/09 - set repsonses to null during the updateModel
 *    cgreene   09/17/09 - initial version
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.behavior;

import org.apache.log4j.Logger;

import oracle.retail.stores.foundation.manager.gui.UIException;
import oracle.retail.stores.foundation.manager.gui.UISubsystem;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;

/**
 * An action listener to respond to a "Cancel" button on a popup dialog. 
 * 
 * @author cgreene
 * @since 13.1
 */
public class CancelActionListener extends ConfirmActionListener
{

    private static final Logger logger = Logger.getLogger(CancelActionListener.class);

    /**
     * Get the current model, set it as canceled and set the responses as null.
     */
    @Override
    protected void updateModel()
    {
        UISubsystem ui = UISubsystem.getInstance();
        try
        {
            POSBaseBeanModel model = (POSBaseBeanModel)ui.getModel();
            PromptAndResponseModel parModel = model.getPromptAndResponseModel();
            if (parModel != null)
            {
                parModel.setResponseBytes(null);
                parModel.setResponseText(null);
                parModel.setCanceled(true);
            }
        }
        catch (UIException ex)
        {
            logger.error("Unable to get the dialog's screen model.", ex);
        }
    }
}
