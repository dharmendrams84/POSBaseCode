/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/timer/EventMonitorIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:36 mszekely Exp $
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
 *3    360Commerce 1.2         3/31/2005 4:28:07 PM   Robert Pearse   
 *2    360Commerce 1.1         3/10/2005 10:21:31 AM  Robert Pearse   
 *1    360Commerce 1.0         2/11/2005 12:10:57 PM  Robert Pearse   
 *
 Revision 1.1  2004/03/15 21:55:15  jdeleau
 @scr 4040 Automatic logoff after timeout
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.timer;

/**
 * Interface for objects that will listen to an event source and activate a
 * timer when the source has no activity. The monitor will also send an
 * interrupt message to the monitor if user activity is detected.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface EventMonitorIfc
{
    /** source control revision number */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /** default time between checks of event source */
    public static final int DEFAULT_PEEK_TIME = 250;

    /**
     * Activates this monitor.
     */
    public void activate();

    /**
     * Deactivates this monitor.
     */
    public void deactivate();

    /**
     * Sets the time between checks of the event source.
     * 
     * @param aTime
     *            the time (in milliseconds) between event checks
     */
    public void setPeekTime(int aTime);

    /**
     * Attaches a specific controller to this monitor.
     * 
     * @param controller
     *            the time controller that this monitor will notify
     */
    public void setController(TimedControllerIfc controller);
}
