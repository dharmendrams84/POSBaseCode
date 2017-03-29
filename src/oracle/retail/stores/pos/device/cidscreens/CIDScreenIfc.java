/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/device/cidscreens/CIDScreenIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:38 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:27:27 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:15 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:00 PM  Robert Pearse   
 *
 *   Revision 1.4  2004/03/25 20:25:15  jdeleau
 *   @scr 4090 Deleted items appearing on Ingenico, I18N, perf improvements.
 *   See the scr for more info.
 *
 *   Revision 1.3  2004/02/12 16:48:35  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:31:30  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:13  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Sep 03 2003 13:11:50   RSachdeva
 * Initial revision.
 * Resolution for POS SCR-3355: Add CIDScreen support
 * ===========================================================================
 */
package oracle.retail.stores.pos.device.cidscreens;

import java.util.Vector;

import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.device.DeviceActionGroupIfc;
/**
 * Interface for all CIDScreens describing various actions all screens
 * can process, providing a method for customized actions to be processed
 * by individual screens, and allowing the controller to be changed.
 *
 * $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface CIDScreenIfc extends DeviceActionGroupIfc
{
    /** revision number supplied by CVS **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * Set the CIDScreenManager for a given screen
     *  
     *  @param cidScreenManager to set
     */
    public void setCIDScreenManager(CIDScreenManager cidScreenManager);

    /**
     * @deprecated 7.0, use the CIDAction constructor
     * to create an action instead
     *  
     *  @param action Action Name
     *  @param parms varies depending ona ction
     *  @return Newly created action
     */
    public CIDAction createAction(String action, Vector parms);

    /**
     * Refresh the display using the underlying model
     */
    public void refresh();

    /**
     * Reset the model this screen uses
     */
    public void reset();

    /**
     * Clear the screen (make it blank).  Part
     * of clearing the screen includes resetting
     * the model.
     *  
     *  @throws DeviceException
     */
    public void clear() throws DeviceException;

    /**
     * Hide the screen (Make it blank) the model
     * does not reset
     *  
     *  @throws DeviceException
     */
    public void hide() throws DeviceException;

    /**
     * Make the screen visible, this is essentially
     * the same as a refresh
     *  
     *  @throws DeviceException
     */
    public void show() throws DeviceException;

    /**
     * For every CIDScreenIfc implementation, custom actions may
     * exist.  For each of those actions, the CIDScreenIfc implementation
     * must know how to handle them to update the display.  When the display
     * needs to be updated, the controller will call this method.
     *  
     *  @param action
     */
    public void processAction(CIDAction action);

    /** @deprecated 7.0, use isVisible() 
     * @return boolean, true if visible false if not **/
    public boolean getVisible();
    
    /**
     * Tell whether or not the given screen is 
     *  currently visible on the device.
     *  @return True if visible, false if not.
     */
    public boolean isVisible();
    
    /**
     * Set the visiblity of the screen
     *  
     * @param val ture or false
     */
    public void setVisible(boolean val);

}
