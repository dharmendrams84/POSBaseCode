/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/timer/TimerModelIfc.java /rgbustores_13.4x_generic_branch/2 2011/08/16 13:50:22 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   08/16/11 - implement timeout capability for admin menu
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 * 3    360Commerce 1.2         3/31/2005 4:30:32 PM   Robert Pearse   
 * 2    360Commerce 1.1         3/10/2005 10:26:18 AM  Robert Pearse   
 * 1    360Commerce 1.0         2/11/2005 12:15:10 PM  Robert Pearse   
 *
 * Revision 1.1  2004/03/15 21:55:15  jdeleau
 * @scr 4040 Automatic logoff after timeout
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.timer;

/**
 * The interface for a TimerModel used in the automatic logout of an inactive
 * screen. DefaultTimerModel provides the default implementation of this
 * interface.
 * 
 * @see DefaultTimerModel
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public interface TimerModelIfc
{
    /**
     * Name of the parameter indicating number of minutes an operator may be
     * inactive before automatic log out. This parameter is for timeout <bold>
     * with </bold> a transaction pending.
     */
    public static final String TRANS_WITH = "TimeoutInactiveWithTransaction";
    /**
     * Name of the parameter indicating number of minutes an operator may be
     * inactive before automatic log out. This parameter is for timeout <bold>
     * without </bold> a transaction pending.
     */
    public static final String TRANS_WITHOUT = "TimeoutInactiveWithoutTransaction";
    /**
     * Value to set timeoutInterval to so automatic logout never takes place.
     */
    public static final int NEVER_TIMEOUT = -1;
    /** revision number supplied by PVCS */
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    /**
     * Returns the name of the class for the timer action.
     * 
     * @return the action class name
     */
    public String getActionClass();

    /**
     * Returns the name of the letter that the ui timer will mail.
     * 
     * @return the action name
     */
    public String getActionName();

    /**
     * Returns the interval period that the timer will wait before mailing a
     * letter.
     * 
     * @return the timer interval
     */
    public int getTimerInterval();

    /**
     * Returns the value of the timer flag.
     * 
     * @return true if timer should be enabled, false otherwise
     */
    public boolean isTimerEnabled();

    /**
     * Sets the name of the class for the timer action.
     * 
     * @param aValue
     *            the action class name
     */
    public void setActionClass(String aValue);

    /**
     * Sets the letter name that the ui timer will mail.
     * 
     * @param aValue
     *            the letter name
     */
    public void setActionName(String aValue);

    /**
     * Sets the interval period that the timer will wait before mailing a
     * letter.
     * 
     * @param aValue
     *            the timer interval
     */
    public void setTimerInterval(int aValue);

    /**
     * Sets the value of the timer flag.
     * 
     * @param aValue
     *            true to enable timer, false if not
     */
    public void setTimerEnabled(boolean aValue);

    /**
     * Sets whether to set the UI model in POSJFCUISubsystem
     * 
     * @param value true to set UI model, false do not
     * @deprecated as of 13.4. Not used.
     */
    public void setSetUIModel(boolean value);

    /**
     * Returns whether to set the UI model in POSJFCUISubsystem
     * 
     * @return whether to set the UI model
     * @deprecated as of 13.4. Not used.
     */
    public boolean getSetUIModel();
    
    /**
     * Set the TimedControllerIfc this model uses
     *  
     *  @param aController
     */
    public void setController(TimedControllerIfc aController);
    
    /**
     * Get the TimedControllerIfc this model uses
     *  
     *  @return timedControllerIfc
     */
    public TimedControllerIfc getController();
}
