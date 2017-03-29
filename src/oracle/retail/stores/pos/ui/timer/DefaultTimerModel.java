/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/timer/DefaultTimerModel.java /rgbustores_13.4x_generic_branch/7 2011/11/11 13:42:55 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   11/11/11 - Fix broken Undo road for IDDI to use new Timeout
 *                         letter
 *    blarsen   10/11/11 - Changed NO_TRANS_LETTER from UNDO to TIMEOUT to
 *                         force POS to logout after one timeout even when
 *                         there is not an active transaction.
 *    cgreene   08/16/11 - implement timeout capability for admin menu
 *    cgreene   08/15/11 - use constant from common class
 *    rsnayak   07/29/11 - Timeout error fix
 *    cgreene   06/17/11 - only initialize model if there is an available bus
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *5    360Commerce 1.4         12/19/2007 8:46:51 AM  Manikandan Chellapan PAPB
 *      FR27 Bulk Checkin-4
 *4    360Commerce 1.3         5/17/2007 5:36:25 PM   Michael P. Barnett Added
 *     a new constructor, which accepts the name of a parameter that defines
 *     the timeout interval.
 *3    360Commerce 1.2         3/31/2005 4:27:43 PM   Robert Pearse
 *2    360Commerce 1.1         3/10/2005 10:20:53 AM  Robert Pearse
 *1    360Commerce 1.0         2/11/2005 12:10:32 PM  Robert Pearse
 *
 Revision 1.3  2004/04/08 20:33:03  cdb
 @scr 4206 Cleaned up class headers for logs and revisions.
 *
 Revision 1.2  2004/03/16 18:30:49  cdb
 @scr 0 Removed tabs from all java source code.
 *
 Revision 1.1  2004/03/15 21:55:15  jdeleau
 @scr 4040 Automatic logoff after timeout
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.timer;

import oracle.retail.stores.common.utility.Util;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.ado.context.ContextFactory;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;

import org.apache.log4j.Logger;

/**
 * Default Implementation of the TimerModel interface, this will cause the UNDO
 * letter to be sent when timeout happens, or a timeout letter gets sent if
 * there is a transaction.
 */
public class DefaultTimerModel implements TimerModelIfc
{
    /** source control revision number */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/7 $";
    /** debug logger. */
    protected static final Logger logger = Logger.getLogger(DefaultTimerModel.class);
    /** Default action class to use on a timeout */
    public static final String DEFAULT_ACTION_CLASS = "oracle.retail.stores.pos.ui.timer.DefaultTimedAction";
    /**
     * Letter to mail when there is no transaction.
     * <p>
     * Historic note: at one time NO_TRANS_LETTER contained UNDO.
     * However, it was determined that there was too much security risk since it could take quite a few
     * timeouts/UNDOs to take the unattended POS back to the login screen.
     * @deprecated as of 13.4. Use {@link CommonLetterIfc#TIMEOUT} instead.
     */
    public static final String NO_TRANS_LETTER = CommonLetterIfc.TIMEOUT;
    /**
     * Letter to mail when there is a transaction
     * @deprecated as of 13.4. Use {@link CommonLetterIfc#TIMEOUT} instead.
     */
    public static final String TRANS_LETTER = CommonLetterIfc.TIMEOUT;
    /**
     * Range is from -1 to 60, with -1 indicating infinity
     */
    private int timerInterval;
    private boolean timerEnabled;
    private String actionClass;
    private String actionName;
    /** @deprecated as of 13.4. Not used. */
    private boolean notifySetModelsSuper;
    private TimedControllerIfc controller;

    /**
     * Default TimerModel which is just a running version with default values.
     */
    public DefaultTimerModel()
    {
        setTimerInterval(TimerModelIfc.NEVER_TIMEOUT);
        setTimerEnabled(false);
        setSetUIModel(true);
        setActionClass(DefaultTimerModel.DEFAULT_ACTION_CLASS);
        setActionName(DefaultTimerModel.NO_TRANS_LETTER);
    }

    /**
     * This is the most often used constructor, which sets timeout values for
     * automatic logout. This is called by the arrive method of
     * ShowSaleScreenSite.
     *
     * @param bus the service bus
     * @param hasTransaction flag indicating if their is currently an active
     *            transaction
     */
    public DefaultTimerModel(BusIfc bus, boolean hasTransaction)
    {
        this(bus, hasTransaction, null);
    }

    /**
     * Constructor used for user inactivity timeout, which sets timeout values
     * for automatic logout. This is called by the POSBaseBeanModel.
     *
     * @param timeoutParamName The name of user inactive timeout parameter
     */
    public DefaultTimerModel(boolean hasTransaction, String timeoutParamName)
    {
        this(null, hasTransaction, timeoutParamName);
    }

    /**
     * This version of the constructor permits the called to explictly define
     * the parameter to check for the timeout interval
     *
     * @param bus the service bus
     * @param hasTransaction flag indicating if their is currently an active
     *            transaction
     * @param parameterName explicitly defined parameter to consult to get
     *            timeout interval
     */
    public DefaultTimerModel(BusIfc bus, boolean hasTransaction, String parameterName)
    {
        String paramName = setParamName(hasTransaction, parameterName);
        // time interval defaults to never timeout
        int timeoutInSeconds = TimerModelIfc.NEVER_TIMEOUT;

        try
        {
            if (bus == null)
            {
                if (ContextFactory.getInstance().getContext() == null)
                {
                    throw new NullPointerException("Unable to initialize model with no current context.");
                }
                bus = ContextFactory.getInstance().getContext().getBus();
            }
            ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
            Integer paramValue = pm.getIntegerValue(paramName);
            // Get the timeout Value in seconds
            if (paramValue.intValue() >= 0)
            {
                timeoutInSeconds = paramValue.intValue() * 60 * 1000;
            }
        }
        catch (ParameterException e)
        {
            logger.warn("Unable to read in parameter:" + paramName + " Exception Details:" + Util.throwableToString(e));
        }
        catch (Exception e)
        {
            logger.warn(e);
        }
        finally
        {
            // Set the other default values
            setActionClass(DefaultTimerModel.DEFAULT_ACTION_CLASS);
            setTimerEnabled(true);
            setTimerInterval(timeoutInSeconds);
            setSetUIModel(true);
            // Set the correct action, depending if there is a transaction or not
            if (hasTransaction)
            {
                setActionName(DefaultTimerModel.TRANS_LETTER);
            }
            else
            {
                setActionName(DefaultTimerModel.NO_TRANS_LETTER);
            }
        }
    }

    /**
     * Returns the name of the parameter to consult to determine the timeout
     * interval.
     *
     * @param hasTransaction true if a transaction is started
     * @param parameterName explicitly defined parameter name, null if not
     *            explicitly defined
     * @return parameter name to check for timeout interval
     */
    protected String setParamName(boolean hasTransaction, String parameterName)
    {
        String paramName; // returned parameter name

        // If parameter name not explicitly defined
        if (parameterName == null)
        {
            // If there is a transaction started
            if (hasTransaction == true)
            {
                paramName = TimerModelIfc.TRANS_WITH;
            }
            // no transaction started
            else
            //
            {
                paramName = TimerModelIfc.TRANS_WITHOUT;
            }
        }
        // parameter explicitly defined - use it
        else
        {
            paramName = parameterName;
        }

        // return the parameter name
        return paramName;

    }

    /**
     * Returns the name of the class for the timer action
     *
     * @see oracle.retail.stores.pos.ui.timer.TimerModelIfc#getActionClass()
     */
    public String getActionClass()
    {
        return this.actionClass;
    }

    /**
     * Returns the name of the letter that the ui timer will mail.
     *
     * @see oracle.retail.stores.pos.ui.timer.TimerModelIfc#getActionName()
     */
    public String getActionName()
    {
        return this.actionName;
    }

    /**
     * Returns whether to notify setModel's method super
     *
     * @see oracle.retail.stores.pos.ui.timer.TimerModelIfc#getSetUIModel()
     * @deprecated as of 13.4. Not used.
     */
    public boolean getSetUIModel()
    {
        return this.notifySetModelsSuper;
    }

    /**
     * Returns the interval period that the timer will wait before mailing a
     * letter.
     *
     * @see oracle.retail.stores.pos.ui.timer.TimerModelIfc#getTimerInterval()
     */
    public int getTimerInterval()
    {
        return this.timerInterval;
    }

    /**
     * Tells whether or not the timer is enabled
     *
     * @see oracle.retail.stores.pos.ui.timer.TimerModelIfc#isTimerEnabled()
     */
    public boolean isTimerEnabled()
    {
        return this.timerEnabled;
    }

    /**
     * Sets the name of the class for the timer action.
     *
     * @see oracle.retail.stores.pos.ui.timer.TimerModelIfc#setActionClass(java.lang.String)
     */
    public void setActionClass(String aValue)
    {
        this.actionClass = aValue;
    }

    /**
     * Sets the letter name that the ui timer will mail.
     *
     * @see oracle.retail.stores.pos.ui.timer.TimerModelIfc#setActionName(java.lang.String)
     */
    public void setActionName(String aValue)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Setting actionName to \"" + aValue + "\"");
        }
        this.actionName = aValue;
    }

    /**
     * Sets the interval period that the timer will wait before mailing a
     * letter.
     *
     * @see oracle.retail.stores.pos.ui.timer.TimerModelIfc#setTimerInterval(int)
     */
    public void setTimerInterval(int aValue)
    {
        this.timerInterval = aValue;
    }

    /**
     * Sets the value of the timer flag.
     *
     * @see oracle.retail.stores.pos.ui.timer.TimerModelIfc#setTimerEnabled(boolean)
     */
    public void setTimerEnabled(boolean aValue)
    {
        this.timerEnabled = aValue;

        // If it was disabled and it was already running, it needs to be stopped
        if (this.timerEnabled == false && getController() != null)
        {
            getController().stopController();
        }

    }

    /**
     * Sets whether to set the UIModel in POSJFCUISubsystem.java
     *
     * @see oracle.retail.stores.pos.ui.timer.TimerModelIfc#setSetUIModel(boolean)
     * @deprecated as of 13.4. Not used.
     */
    public void setSetUIModel(boolean value)
    {
        this.notifySetModelsSuper = value;
    }

    /**
     * Set the TimedControllerIfc that is used for timing
     *
     * @param aController
     * @see oracle.retail.stores.pos.ui.timer.TimerModelIfc#setController(oracle.retail.stores.pos.ui.timer.TimedControllerIfc)
     */
    public void setController(TimedControllerIfc aController)
    {
        this.controller = aController;
    }

    /**
     * Retrieve the TimedControllerIfc that controls this model
     *
     * @return controller
     * @see oracle.retail.stores.pos.ui.timer.TimerModelIfc#getController()
     */
    public TimedControllerIfc getController()
    {
        return this.controller;
    }
}
