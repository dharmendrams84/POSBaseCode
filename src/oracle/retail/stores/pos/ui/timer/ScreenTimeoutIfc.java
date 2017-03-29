/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/timer/ScreenTimeoutIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:36 mszekely Exp $
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
 *3    360Commerce 1.2         3/31/2005 4:29:51 PM   Robert Pearse   
 *2    360Commerce 1.1         3/10/2005 10:25:06 AM  Robert Pearse   
 *1    360Commerce 1.0         2/11/2005 12:14:05 PM  Robert Pearse   
 *
 Revision 1.1  2004/03/15 21:55:15  jdeleau
 @scr 4040 Automatic logoff after timeout
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.timer;

/**
 * This class should be implemented by any UI bean to implement timeout and
 * automatic logout for that screen. Simply implement this interface. Default
 * implementations are provided in this package. To see a reference
 * implementation of the automatic logout feature, see LineItemsModel.java
 * 
 * @see oracle.retail.stores.pos.ui.beans.LineItemsModel
 * @see TimerModelIfc
 * @see DefaultTimerModel 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface ScreenTimeoutIfc
{
    /** source control revision number */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * Get the timerModel an implementing class is using
     *  
     * @return timerModel
     */
    public TimerModelIfc getTimerModel();

    /**
     * Set the timerModel the implementing class should use
     *  
     *  @param model Timer model to use
     */
    public void setTimerModel(TimerModelIfc model);
}
