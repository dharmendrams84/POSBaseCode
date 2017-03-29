/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/behavior/ThirdPartyBrowserHandlerIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:59 mszekely Exp $
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
 *3    360Commerce 1.2         3/31/2005 4:30:28 PM   Robert Pearse   
 *2    360Commerce 1.1         3/10/2005 10:26:08 AM  Robert Pearse   
 *1    360Commerce 1.0         2/11/2005 12:15:01 PM  Robert Pearse   
 *
 Revision 1.1.2.1  2004/10/18 19:34:37  jdeleau
 @scr 7291 Integrate ibV6 and remove Ib5 from installation procedure
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.behavior;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import org.w3c.dom.events.Event;
import oracle.retail.stores.pos.ui.beans.OnlineOfficeBean;

/**
 *
 * $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface ThirdPartyBrowserHandlerIfc
{

    /**
     * Get the onlineoffice bean, this is the bean which the browser
     * is a part of
     *  
     *  @return
     */
    public OnlineOfficeBean getOnlineOfficeBean();
    
    /**
     * Set the onlineoffice bean
     *  
     * @param bean Bean containing the browser
     */
    public void setOnlineOfficeBean(OnlineOfficeBean bean);

    /**
     * When the bean is activated, it will pass the activate
     * event on to the third party browser.  Any interaction between
     * POS and the thirdparty browser that needs to use classes specific
     * to that implementation should go here.
     */
    public void activate();

    /**
     * When the bean is deactivated, it will pass the deactivate
     * event on to the third party browser.  Any interaction between
     * POS and the thirdparty browser that needs to use classes specific
     * to that implementation should go here.
     */
    public void deactivate();

    /**
     * Whenever a key press event occurs in the browser, it
     * may pass an event to the bean, which is in turn passed to the
     * third party implementation.  Any logic which depends on such events
     * may be implemented here.
     *  
     * @param e event
     */
    public void handleEvent(Event e);

    /**
     * When the beans setVisible method is called, the
     * third party browser may want to take action, if so it
     * should do so here.
     *  
     * @param visible
     */
    public void setVisible(boolean visible);
    
    /**
     * Any property changed events received by the onlineOfficeBean
     * are passed through to the third party handler.
     *  
     *  @param evt
     */
    public void propertyChange(PropertyChangeEvent evt);
    /**
     * An action Event occurred on the browser, take what ever actions are necessary
     *  
     * @param evt
     */
    public void actionPerformed(ActionEvent evt);
    /**
     * Configure Buttons
     *
     */
    public void configureButtons();

    /**
     * This tells you if the 3rd party browser is installed
     *  
     * @return true or false
     */
    public boolean isInstalled();
    
}
