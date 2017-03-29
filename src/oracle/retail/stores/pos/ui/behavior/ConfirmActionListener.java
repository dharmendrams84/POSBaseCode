/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/behavior/ConfirmActionListener.java /rgbustores_13.4x_generic_branch/1 2011/03/25 15:50:32 rsnayak Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    rsnayak   03/25/11 - XbranchMerge rsnayak_bug-11686414 from main
 *    rsnayak   03/25/11 - class cast exception fix
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   09/17/09 - initial version
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.behavior;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import oracle.retail.stores.foundation.manager.gui.UIException;
import oracle.retail.stores.foundation.manager.gui.UISubsystem;
import oracle.retail.stores.pos.ui.beans.UIAction;

/**
 * An action listener used to respond to "OK" or "Yes" buttons on popup dialogs.
 * It causes the dialog to close via {@link Window#dispose()}.
 * 
 * @author cgreene
 * @since 13.1
 */
public class ConfirmActionListener implements ActionListener
{

    private static final Logger logger = Logger.getLogger(ConfirmActionListener.class);

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        UISubsystem ui = UISubsystem.getInstance();
        try
        {
            ui.closeDialog();
        }
        catch (UIException ex)
        {
            logger.error("Unable to close the dialog.", ex);

        }
    }

    /**
     * Dispose of the dialog
     * @param e
     * @deprecated as of 13.4. Replaced by calling {@link UISubsystem#closeDialog()} instead.
     */
    protected void closeDialog(UIAction action)
    {
        Window w = SwingUtilities.getWindowAncestor((Component)action.getSource());
        w.dispose();
    }

    /**
     * Get the current model in order to cause it to be updated 
     * @deprecated as of 13.4. Replaced by calling {@link UISubsystem#closeDialog()} instead.
     */
    protected void updateModel()
    {
        //
        UISubsystem ui = UISubsystem.getInstance();
        try
        {
            ui.getModel();
        }
        catch (UIException ex)
        {
            logger.error("Unable to get the dialog's screen model.", ex);
        }
    }
}
