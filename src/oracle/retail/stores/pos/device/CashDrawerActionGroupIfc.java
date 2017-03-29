/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/device/CashDrawerActionGroupIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:39 mszekely Exp $
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
 *   3    360Commerce 1.2         3/31/2005 4:27:21 PM   Robert Pearse   
 *   2    360Commerce 1.1         3/10/2005 10:20:01 AM  Robert Pearse   
 *   1    360Commerce 1.0         2/11/2005 12:09:49 PM  Robert Pearse   
 *
 *  Revision 1.4  2004/03/31 20:19:01  bjosserand
 *  @scr 4093 Transaction Reentry
 *
 *  Revision 1.3  2004/02/12 16:48:34  mcs
 *  Forcing head revision
 *
 *  Revision 1.2  2004/02/11 21:30:29  rhafernik
 *  @scr 0 Log4J conversion and code cleanup
 *
 *  Revision 1.1.1.1  2004/02/11 01:04:13  cschellenger
 *  updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:51:12   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Jan 13 2003 15:38:56   vxs
 * Assigned value for String TYPE
 * Resolution for POS SCR-1901: Pos Device Action/ActionGroup refactoring
 * 
 *    Rev 1.0   Jan 07 2003 17:57:26   vxs
 * Initial revision.
 * Resolution for POS SCR-1901: Pos Device Action/ActionGroup refactoring
 *Revision: /main/7 $
 * ===========================================================================
 */
package oracle.retail.stores.pos.device;

//foundation imports
import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.device.DeviceActionGroupIfc;
import oracle.retail.stores.foundation.manager.ifc.device.DrawerClosedListenerIfc;

//--------------------------------------------------------------------------
/**
    The <code>CashDrawerActionGroupIfc</code> defines the POS specific
    device operations available to POS applications.
    <p>
    @author  $KW=@(#); $Own=mmann; $EKW;
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface CashDrawerActionGroupIfc extends DeviceActionGroupIfc
{

    public static final String TYPE = "CashDrawerActionGroupIfc";

    //--------------------------------------------------------------------------
    /**
        This operation opens the cash drawer and returns immediately.
        When the drawer is closed, the DrawerClosedListener is invoked.
        If the cash drawer is not present, this operation waits for a
        pre-configured delay interval prior to invoking the DrawerClosedListener.
        <P>
       <B>Pre-conditions</B>
       <UL>
       <LI>DeviceTechnician is configured.
       </UL>
       <B>Post-conditions</B>
       <UL>
       <LI>The cash drawer has been opened and subsequently closed.
       <LI>The drawerClosedListener has been invoked.
       </UL>
       @param drawerClosedListener is the callback that is invoked when the
       drawer is closed.
       @exception DeviceException is thrown the cash drawer cannot be opened.
    **/
    //--------------------------------------------------------------------------
    public void openCashDrawer(DrawerClosedListenerIfc drawerClosedListener) throws DeviceException;
    //---------------------------------------------------------------------
    /**
       Pop open the cash drawer
    **/
    //---------------------------------------------------------------------
    public void openCashDrawer() throws DeviceException;
    //---------------------------------------------------------------------
    /**
       Check to see if cash drawer is open.
    **/
    //---------------------------------------------------------------------
    public Boolean isOpen() throws DeviceException;
    //---------------------------------------------------------------------
    /**
       Wait for the cash drawer to close
    **/
    //---------------------------------------------------------------------
    public void waitForDrawerClose() throws DeviceException;
    //---------------------------------------------------------------------
    /**
       Enable alert beep.
    **/
    //---------------------------------------------------------------------
    public void alertBeepOn() throws DeviceException;
    //---------------------------------------------------------------------
    /**
       Disable alert beep.
    **/
    //---------------------------------------------------------------------
    public void alertBeepOff() throws DeviceException;
    //---------------------------------------------------------------------
    /**
       Reset cashdrawer
    **/
    //---------------------------------------------------------------------
    public void resetCashDrawer() throws DeviceException;
    //---------------------------------------------------------------------
    /**
       Switch the current cashdrawer session
    **/
    //---------------------------------------------------------------------
    public void switchCashDrawer(String newCashDrawerSession) throws DeviceException;
    //---------------------------------------------------------------------
    /**
       Sets the time to wait in milliseconds before sending an audible
       signal to the user that the cash drawer is open.
       @param value cash-drawer-open-alert beep timeout
    **/
    //---------------------------------------------------------------------
    public void setBeepTimeout(int value);

    //---------------------------------------------------------------------
    /**
       Retrieves the time to wait in milliseconds before sending an
       audible signal to the user that the cash drawer is open.
       @return cash-drawer-open-alert beep timeout
    **/
    //---------------------------------------------------------------------
    public int getBeepTimeout();

    //---------------------------------------------------------------------
    /**
       Sets the frequency in Hertz of the audible cash drawer open alert signal.
       @param value cash-drawer-open-alert beep frequency
    **/
    //---------------------------------------------------------------------
    public void setBeepFrequency(int value);

    //---------------------------------------------------------------------
    /**
       Gets the frequency in Hertz of the audible cash drawer open
       alert signal.
       @return cash-drawer-open-alert beep frequency
    **/
    //---------------------------------------------------------------------
    public int getBeepFrequency();

    //---------------------------------------------------------------------
    /**
       Sets the duration in milliseconds of the audible cash drawer open
       alert signal.
       @param value cash-drawer-open-alert beep duration
    **/
    //---------------------------------------------------------------------
    public void setBeepDuration(int value);
    //---------------------------------------------------------------------
    /**
       Gets the duration in milliseconds of the audible cash drawer open
       alert signal.
       @return cash-drawer-open-alert beep duration
    **/
    //---------------------------------------------------------------------
    public int getBeepDuration();
    //---------------------------------------------------------------------
    /**
       Sets the delay in milliseconds between successive audible cash
       drawer open alert signals.
       @param value cash-drawer-open-alert beep delay
    **/
    //---------------------------------------------------------------------
    public void setBeepDelay(int value);

    //---------------------------------------------------------------------
    /**
       Gets the delay in milliseconds between successive audible cash
       drawer open alert signals.
       @return cash-drawer-open-alert beep delay
    **/
    //---------------------------------------------------------------------
    public int getBeepDelay();

    //---------------------------------------------------------------------
    /**
       Gets the delay to wait before invoking the cashDrawerClosedListener,
       in case the cash drawer is not present.
       @return cash-drawer-not-present-signal delay
    **/
    //---------------------------------------------------------------------
    public long getCashDrawerDelay();

    //---------------------------------------------------------------------
    /**
       Sets the delay to wait before invoking the cashDrawerClosedListener,
       in case the cash drawer is not present.
       @param value cash-drawer-not-present-signal delay
    **/
    //---------------------------------------------------------------------
    public void setCashDrawerDelay(long value);

    //---------------------------------------------------------------------
    /**
       Returns the beep-on flag.
       @return beep-on flag
    **/
    //---------------------------------------------------------------------
    public boolean getBeepOn();

    //---------------------------------------------------------------------
    /**
       Sets the beep-on flag.
       @param value Beep On flag
    **/
    //---------------------------------------------------------------------
    public void setBeepOn(boolean value);

    //---------------------------------------------------------------------
    /**
       Gets the delay to wait when openning the cash drawer
       @return open-drawer wait
    **/
    //---------------------------------------------------------------------
    public long getOpenDrawerWait();

    //---------------------------------------------------------------------
    /**
       Sets the delay to wait when openning the cash drawer
       @return value open-drawer wait
    **/
    //---------------------------------------------------------------------
    public void setOpenDrawerWait(long value);

    /**
     * @return Returns the cashDrawerEnabled state as a Boolean (for transport).
     */
    public Boolean isCashDrawerEnabled();

    /**
     * @param cashDrawerEnabled The cashDrawerEnabled to set.
     */
    public void setCashDrawerEnabled(boolean cashDrawerEnabled);
}
