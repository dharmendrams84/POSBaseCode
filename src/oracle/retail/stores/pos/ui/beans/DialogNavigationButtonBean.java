/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/DialogNavigationButtonBean.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:59 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/02/10 - override activate in order to set defaul tbutton
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   12/07/09 - set maxButtons value
 *    cgreene   09/17/09 - initial version
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import oracle.retail.stores.foundation.manager.gui.ButtonSpec;
import oracle.retail.stores.pos.ui.behavior.ConfirmActionListener;

/**
 * A bean intended to display buttons in a horizontal panel in a popup dialog.
 * 
 * @author cgreene
 * @since 13.1
 */
public class DialogNavigationButtonBean extends NavigationButtonBean
{
    private static final long serialVersionUID = 8186003753860984985L;

    protected ConfirmActionListener confirmActionListener = new ConfirmActionListener();

    /**
     * Constructor. Sets orientation to {@link SwingConstants#HORIZONTAL}, the
     * buttonPrefix to "HorizontalButton" and a center-justified
     * {@link FlowLayout}.
     */
    public DialogNavigationButtonBean()
    {
        setOrientation(HORIZONTAL);
        setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    /**
     * Constructor. Calls this() then {@link #initialize(UIAction[][])}.
     * 
     * @param actions
     */
    public DialogNavigationButtonBean(UIAction[][] actions)
    {
        this();
        initialize(actions);
    }

    /**
     * Overridden to call {@link JRootPane#setDefaultButton(javax.swing.JButton)}
     * on the OK button if its the only button in this panel.
     * 
     * @see oracle.retail.stores.pos.ui.beans.NavigationButtonBean#activate()
     */
    @Override
    public void activate()
    {
        super.activate();
        // if there is only one button, set it as the default button
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                if (getComponentCount() == 1 && getComponent(0) instanceof JButton)
                {
                    JButton defaultButton = (JButton)getComponent(0);
                    JRootPane rootPane = SwingUtilities.getRootPane(DialogNavigationButtonBean.this);
                    if (rootPane != null)
                    {
                        rootPane.setDefaultButton(defaultButton);
                    }
                    else
                    {
                        logger.warn("Could not find rootPane of dialog in order to set defaultButton.");
                    }
                }
            }
        });
    }

    /**
     * Overridden to use a {@link ConfirmActionListener} if the listenerName
     * is null.
     * 
     * @see oracle.retail.stores.pos.ui.beans.NavigationButtonBean#createButtonListener(java.lang.String, java.lang.String)
     */
    @Override
    protected ActionListener createButtonListener(String actionName, String listenerName)
    {
        if (listenerName != null)
        {
            return super.createButtonListener(actionName, listenerName);
        }
        return confirmActionListener;
    }

    /**
     * Overridden to always set the {@link #maxButtons} to the exact length
     * of the incoming buttonSpecs array.
     *
     * @see oracle.retail.stores.pos.ui.beans.NavigationButtonBean#configureButtons(oracle.retail.stores.foundation.manager.gui.ButtonSpec[])
     */
    @Override
    public void configureButtons(ButtonSpec[] buttonSpecs)
    {
        maxButtons = buttonSpecs.length;
        super.configureButtons(buttonSpecs);
    }

    /**
     * Overridden to simply add the buttons to the panel at the specified index.
     *
     * @see oracle.retail.stores.pos.ui.beans.NavigationButtonBean#addButton(oracle.retail.stores.pos.ui.beans.EYSButton, int)
     */
    @Override
    protected void addButton(EYSButton button, int index)
    {
        add(button, index);
    }
}
