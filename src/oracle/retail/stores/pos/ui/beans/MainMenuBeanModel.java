/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/MainMenuBeanModel.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:43 mszekely Exp $
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
 */
package oracle.retail.stores.pos.ui.beans;

import oracle.retail.stores.pos.ui.timer.ScreenTimeoutIfc;
import oracle.retail.stores.pos.ui.timer.TimerModelIfc;
import oracle.retail.stores.pos.ui.timer.DefaultTimerModel;

//--------------------------------------------------------------------------
/**
 * This is the bean model used by the SaleBean. <P>
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @see oracle.retail.stores.pos.ui.beans.SaleBean
**/
//--------------------------------------------------------------------------
public class MainMenuBeanModel extends POSBaseBeanModel implements ScreenTimeoutIfc
{
    /** revision number **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    
    public static final long serialVersionUID = -1L;

    /**
     * TimerModel used for automatic logoff after a timeout
     */
    protected TimerModelIfc timerModel = null;

    //----------------------------------------------------------------------
    /**
     * LineItemsModel constructor comment.
     */
    //----------------------------------------------------------------------
    public MainMenuBeanModel()
    {
      
        super();
        
        timerModel = new DefaultTimerModel();
        
    }

    /**
     * Sets the timerModel to be used in this class, the default
     * value is the DefaultTimerModel class.
     * @param timerModel TimerModel to use
     */
    public void setTimerModel(TimerModelIfc timerModel)
    {
        this.timerModel = timerModel;
    }
    
    /**
     * Return the timerModel this LineItemsModel is using
     * @return timerModel
     */
    public TimerModelIfc getTimerModel()
    {
        return timerModel;
    }
}
