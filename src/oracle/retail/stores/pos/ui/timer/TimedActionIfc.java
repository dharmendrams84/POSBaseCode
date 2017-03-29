/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/timer/TimedActionIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:36 mszekely Exp $
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
 *3    360Commerce 1.2         3/31/2005 4:30:31 PM   Robert Pearse   
 *2    360Commerce 1.1         3/10/2005 10:26:16 AM  Robert Pearse   
 *1    360Commerce 1.0         2/11/2005 12:15:08 PM  Robert Pearse   
 *
 Revision 1.1  2004/03/15 21:55:15  jdeleau
 @scr 4040 Automatic logoff after timeout
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.timer;

import java.awt.event.ActionListener;

/**
 * Interface for objects that perform a specific action when a time interval is
 * reached.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface TimedActionIfc extends ActionListener
{
    /** source control revision number */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * Handles the specific behavior that should occur when the controller is
     * interrupted by user activity.
     */
    public void doInterrupt();

    /**
     * Returns the length of this action's time interval.
     * 
     * @return the interval length (in milliseconds)
     */
    public int getTimeInterval();

    /**
     * Returns the name of this timed action.
     * 
     * @return the action name
     */
    public String getTimedActionName();

    /**
     * Sets the length of this action's time interval.
     * 
     * @param aValue
     *            the interval length (in milliseconds)
     */
    public void setTimeInterval(int aValue);

    /**
     * Sets the name of this timed action.
     * 
     * @param aName
     *            the action name
     */
    public void setTimedActionName(String aName);

    /**
     * Attaches a controller to this action.
     * 
     * @param controller
     *            the controller to attach
     */
    public void setController(TimedControllerIfc controller);
}
