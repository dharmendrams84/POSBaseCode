/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/popup/PopupMessageManagerIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:36 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   04/01/09 - initial version
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.popup;

import java.awt.Component;

/**
 * A manager of JPopups that display for each string message added to this.
 * 
 * @author cgreene
 * @since 13.1
 */
public interface PopupMessageManagerIfc
{

    /**
     * Key to Spring context. Equals "application_PopupMessageManager".
     */
    public static final String BEAN_KEY = "application_PopupMessageManager";

    /**
     * Return the component that is the parent of the popup.
     * 
     * @return
     */
    public Component getOwner();

    /**
     * Sets the component that is the parent of the popup. This must be set
     * before adding a message.
     * 
     * @return
     */
    public void setOwner(Component owner);

    /**
     * Returns true if there messages pending to display.
     * 
     * @return
     */
    public boolean hasMessage();

    /**
     * Add a message to display to the manager.
     * 
     * @param message
     */
    public void addMessage(String message);
}
