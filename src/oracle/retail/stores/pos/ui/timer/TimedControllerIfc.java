/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/timer/TimedControllerIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:36 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
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

/**
 * Interface for objects that manage a series of timed actions. This controller
 * will initiate actions when specific time intervals have passed.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface TimedControllerIfc
{
    /** source control revision number */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /** the default monitor, if none is provided */
    public static final String DEFAULT_MONITOR = "oracle.retail.stores.pos.ui.timer.DefaultEventMonitor";

    //--------------------------------------------------------------------------
    /**
     * Adds an timed action to this monitor.
     * 
     * @param action
     *            the timed action object
     */
    public void addTimedAction(TimedActionIfc action);

    /**
     * Clears the action list.
     */
    public void clearActions();

    /**
     * Stops the current interval timer and sets up the next interval.
     */
    public void cycleNextAction();

    /**
     * Processes an interrupt message from the event monitor.
     */
    public void fireInterrupt();

    /**
     * Returns a timed action that matches the given name.
     * 
     * @param aName
     *            the name of the action
     * @return the found action, or null
     */
    public TimedActionIfc getTimedAction(String aName);

    /**
     * Initializes the controller.
     */
    public void initialize();

    /**
     * Determines if the controller is active.
     * 
     * @return true if active, false if inactive
     */
    public boolean isActive();

    /**
     * Resets the controller.
     */
    public void resetController();

    /**
     * Starts the controller.
     */
    public void startController();

    /**
     * Stops the controller.
     */
    public void stopController();
}
