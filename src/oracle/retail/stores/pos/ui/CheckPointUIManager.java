/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/CheckPointUIManager.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:36 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   02/04/09 - initial version
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui;

import java.util.ArrayList;
import java.util.List;

import oracle.retail.stores.foundation.manager.gui.UIModelIfc;
import oracle.retail.stores.foundation.naming.MailboxAddress;

/**
 * This class overrides methods in its parent class in order to stop any
 * screen display request from proceeding.
 * 
 * @author cgreene
 * @since 13.1
 */
public class CheckPointUIManager extends POSUIManager
{
    protected boolean checkPointBlocked;
    protected List<MailboxAddress> blockedListeners;

    /**
     * Default constructor. Sets {@link #checkPointBlocked} to true.
     */
    public CheckPointUIManager()
    {
        checkPointBlocked = true;
        blockedListeners = new ArrayList<MailboxAddress>(1);
    }

    /**
     * Block adding any bus addresses from registering as a listener of the UI
     * until the check point is cleared. If {@link #setCheckPointBlocked(boolean)}
     * is called with <code>true</code> then the blocked addresses will then
     * be registered.
     * 
     * @see oracle.retail.stores.pos.ui.POSUIManager#addLetterListener(oracle.retail.stores.foundation.naming.MailboxAddress)
     */
    public void addLetterListener(MailboxAddress address)
    {
        if (!isCheckPointBlocked())
        {
            super.addLetterListener(address);
        }
        else
        {
            blockedListeners.add(address);
        }
    }

    /**
     * There will lots of status changes that set new models. Allow this to
     * proceed and not be blocked.
     * 
     * @see oracle.retail.stores.pos.ui.POSUIManager#setModel(java.lang.String, oracle.retail.stores.foundation.manager.gui.UIModelIfc)
     */
    @Override
    public void setModel(String screenId, UIModelIfc beanModel)
    {
        super.setModel(screenId, beanModel);
    }

    /**
     * Showing any screen should block in this tier until the application tier
     * allows it. Calls {@link #stopAtCheckPoint()}.
     * 
     * @see oracle.retail.stores.pos.ui.POSUIManager#showScreen(java.lang.String)
     */
    @Override
    public void showScreen(String screenID)
    {
        stopAtCheckPoint();
        super.showScreen(screenID);
    }

    /**
     * Showing any screen should block in this tier until the application tier
     * allows it. Calls {@link #stopAtCheckPoint()}.
     * 
     * @see oracle.retail.stores.pos.ui.POSUIManager#showScreen(java.lang.String, oracle.retail.stores.foundation.manager.gui.UIModelIfc)
     */
    @Override
    public void showScreen(String screenID, UIModelIfc beanModel)
    {
        stopAtCheckPoint();
        super.showScreen(screenID, beanModel);
    }

    /**
     * Returns whether this UI manager should block method calls.
     * 
     * @return true if check point is blocked
     */
    public synchronized boolean isCheckPointBlocked()
    {
        return checkPointBlocked;
    }

    /**
     * Sets whether this UI manager should block method calls. Calls
     * {@link #notify()} and {@link #addLetterListener(MailboxAddress)} after
     * setting variable to <code>true</code>.
     * 
     * @param checkPointBlocked
     */
    public synchronized void setCheckPointBlocked(boolean checkPointBlocked)
    {
        this.checkPointBlocked = checkPointBlocked;
        if (!checkPointBlocked)
        {
            while (blockedListeners.size() > 0)
            {
                addLetterListener(blockedListeners.remove(0));
            }
            notify(); // wake up that thread
        }
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.manager.gui.UIManager#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder strResult = new StringBuilder("CheckPointUIManager@");
        strResult.append(hashCode());
        strResult.append("[address=").append(address);
        strResult.append(",bus=").append(getBus());
        strResult.append(",uiTechnician=").append(uiTechnician);
        strResult.append("]");
        return strResult.toString();
    }

    /**
     * If {@link #checkPointBlocked} is true, then this method will make the
     * current thread wait until notified.
     */
    protected synchronized void stopAtCheckPoint()
    {
        while (isCheckPointBlocked())
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
            }
        }
    }
}
