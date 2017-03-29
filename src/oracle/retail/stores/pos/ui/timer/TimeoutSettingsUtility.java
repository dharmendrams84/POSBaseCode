/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/timer/TimeoutSettingsUtility.java /rgbustores_13.4x_generic_branch/2 2011/09/01 11:50:28 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   08/16/11 - initial version
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.timer;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;

/**
 * Utility to contain specific global options for what should happen when the
 * current timer expires.
 *
 * @author cgreene
 * @since 13.4
 */
public final class TimeoutSettingsUtility
{

    /**
     * If <code>true</code>, indicates the timer should consider a sensitive
     * operation is in effect, such as ringing a transaction or traversing the
     * Admin menus and mail an {@link CommonLetterIfc#TIMEOUT} letter.
     * <p>
     * If <code>false</code>, the timer should mail an
     * {@link CommonLetterIfc#UNDO} letter to cause the bus to go back one site.
     */
    private static boolean transactionActive;

    /**
     * Hidden constructor.
     */
    private TimeoutSettingsUtility()
    {
    }

    /**
     * @return the transactionActive
     */
    public static boolean isTransactionActive()
    {
        return transactionActive;
    }

    /**
     * @param transactionActive the transactionActive to set
     */
    public static void setTransactionActive(boolean transactionActive)
    {
        TimeoutSettingsUtility.transactionActive = transactionActive;
    }
    
}
