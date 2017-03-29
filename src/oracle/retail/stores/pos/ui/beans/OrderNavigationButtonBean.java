/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/OrderNavigationButtonBean.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:42 mszekely Exp $
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

import java.awt.event.ActionListener;

import org.apache.log4j.Logger;


//-------------------------------------------------------------------------
/**
   This class contains one constant that forces the button bar to be
   horizontal.
   @version $KW=@(#); $Ver=pos_4.5.0:5; $EKW;
*/
//-------------------------------------------------------------------------
public class OrderNavigationButtonBean extends NavigationButtonBean 
{
    /**
        revision number supplied by Team Connection
    **/
    public static final String revisionNumber = "$KW=@(#); $Ver=pos_4.5.x:2; $EKW;";
    /**
        Constants for button names.
    **/                 
    public static final String FILLED   = "Filled";
    public static final String PENDING  = "Pending";
    public static final String PICKUP   = "Pick Up";
    public static final String CANCELED = "Canceled";
    
    //------------------------------------------------------------------------------
    /**
    *   Default constructor.
    */
    //------------------------------------------------------------------------------
    public OrderNavigationButtonBean()
    {
        super();
    }
    
    //--------------------------------------------------------------------------
    /**
       Creates an empty NavigationButtonBean. 
       @param actions two dimensional list of buttions 
    */
    //--------------------------------------------------------------------------
    public OrderNavigationButtonBean(UIAction[][] actions)
    {
        this();
        initialize(actions);
    }

    //---------------------------------------------------------------------
    /**
        Adds (actually sets) the listener on the Next button.
        @Param listener the Action Listener
    **/
    //---------------------------------------------------------------------
    public void addActionListener(ActionListener listener)
    {
        try
        {
            getUIAction(FILLED).setActionListener(listener); 
            getUIAction(PENDING).setActionListener(listener);
            getUIAction(PICKUP).setActionListener(listener);
            getUIAction(CANCELED).setActionListener(listener);
        }
        catch(ActionNotFoundException e)
        {
            Logger logger = Logger.getLogger(oracle.retail.stores.pos.ui.beans.OrderNavigationButtonBean.class);
            logger.warn( "OrderNavigationButtonBean.addActionListener() did not find the NEXT action.");
        }
    }
    
    //---------------------------------------------------------------------
    /**
        Removes (actually resets) the listener on the Next button.
        @Param listener the Action Listener
    **/
    //---------------------------------------------------------------------
    public void removeActionListener(ActionListener listener)
    {
        try
        {
            getUIAction(FILLED).resetActionListener(); 
            getUIAction(PENDING).resetActionListener();
            getUIAction(PICKUP).resetActionListener();
            getUIAction(CANCELED).resetActionListener();
               
        }
        catch(ActionNotFoundException e)
        {
            Logger logger = Logger.getLogger(oracle.retail.stores.pos.ui.beans.OrderNavigationButtonBean.class);
            logger.warn( "OrderNavigationButtonBean.removeActionListener() did not find the NEXT action.");
        }
    }
}
