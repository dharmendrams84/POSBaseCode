/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/main/tdo/MainMenuTDO.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:12 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    npoola    08/10/10 - removed the training register object
 *    cgreene   05/26/10 - convert to oracle packaging
 *    acadar    03/22/10 - add additional check for drawer status before
 *                         setting the training mode to true
 *    abondala  01/03/10 - update header date
 *    blarsen   04/27/09 - Training button should be disabled when in reentry
 *                         mode. Changed logic for training button
 *                         enable/disable to work around bug in
 *                         setButtonEnable() method.
 *
 * ===========================================================================
 * $Log:
 *  4    360Commerce 1.3         5/17/2007 5:31:59 PM   Michael P. Barnett
 *       Instantiate a MainMenuBeanModel for the main menu, which now includes
 *        a timer.
 *  3    360Commerce 1.2         3/31/2005 4:28:59 PM   Robert Pearse
 *  2    360Commerce 1.1         3/10/2005 10:23:25 AM  Robert Pearse
 *  1    360Commerce 1.0         2/11/2005 12:12:31 PM  Robert Pearse
 * $
 * Revision 1.20  2004/07/28 19:56:06  rsachdeva
 * @scr 5820 TransactionReentryMode Bundle Filename
 *
 * Revision 1.19  2004/07/23 22:17:25  epd
 * @scr 5963 (ServicesImpact) Major update.  Lots of changes to fix RegisterADO singleton references and fix training mode
 *
 * Revision 1.18  2004/07/12 18:09:22  rsachdeva
 * @scr 3976 Online Listener was not being set in the correct tdo (Installer 4690 issues)
 *
 * Revision 1.17  2004/07/06 13:19:22  jriggins
 * @scr 5421 Removed unecessary import which will cause build problems under our Eclipse settings
 *
 * Revision 1.16  2004/07/02 20:00:37  dcobb
 * @scr 5503 Training Mode button should be disabled when store / register / till are not open.
 *
 * Revision 1.15  2004/05/20 22:54:58  cdb
 * @scr 4204 Removed tabs from code base again.
 *
 * Revision 1.14  2004/05/01 14:57:16  tfritz
 * @scr 4414 Disable Re-Entry button when in Training Mode
 *
 * Revision 1.13  2004/04/16 14:39:39  bjosserand
 * @scr 4093 Transaction Reentry
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.main.tdo;

import java.util.HashMap;

import oracle.retail.stores.domain.financial.AbstractStatusEntityIfc;
import oracle.retail.stores.domain.financial.DrawerIfc;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.financial.StoreStatusIfc;
import oracle.retail.stores.domain.financial.TillIfc;
import oracle.retail.stores.foundation.manager.data.DataManager;
import oracle.retail.stores.foundation.manager.ifc.DataManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.ado.store.RegisterADO;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.common.CommonActionsIfc;
import oracle.retail.stores.pos.services.common.OnlineListener;
import oracle.retail.stores.pos.services.main.MainCargo;
import oracle.retail.stores.pos.tdo.TDOAdapter;
import oracle.retail.stores.pos.tdo.UITDOIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.MainMenuBeanModel;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.ui.beans.StatusBeanModel;

/**
 * @author rwh
 *
 * This class controls creation of the UI bean model for the main menu.
 */
public class MainMenuTDO extends TDOAdapter implements UITDOIfc
{
    protected final String TRANS_REENTRY_BUTTON_NAME = "TransReentry";
    protected final String MAIN_OPS_BUTTONS_ID = "MainOptionsButtonSpec";
    protected final String TRANS_REENTRY_ON_KEY = "TransReentryOn";
    protected final String TRANS_REENTRY_ON_DEFAULT_TEXT = "Re-entry On";
    protected final String TRANS_REENTRY_OFF_KEY = "TransReentryOff";
    protected final String TRANS_REENTRY_OFF_DEFAULT_TEXT = "Re-entry Off";
    /**
     * Training Off Label tag
     */
    public static final String TRAINING_OFF_LABEL_TAG = "TrainingOffLabel";
    /**
     * Training Off Label default text
     */
    public static final String TRAINING_OFF_LABEL_TEXT = "Training Off";
    /**
     * Training On Label tag
     */
    public static final String TRAINING_ON_LABEL_TAG = "TrainingOnLabel";
    /**
     * Training On Label default text
     */
    public static final String TRAINING_ON_LABEL_TEXT = "Training On";

    /**
     * Number of seconds the Data Manager waits before attempting the next
     * transaction.
     */
    protected static final int TRANSACTION_INTERVAL = 5;


    /**
     * Build UI bean model for main menu.
     *
     * @param HashMap
     * @return MainMenuBeanModel
     */
    public POSBaseBeanModel buildBeanModel(HashMap attributeMap)
    {
        BusIfc bus = (BusIfc) attributeMap.get("BUS");

        MainCargo cargo = (MainCargo) bus.getCargo();
        boolean trainingModeOn = cargo.isTrainingMode();
        UtilityManagerIfc utility = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        // Ask the UI Manager to display the main menu
        MainMenuBeanModel pModel = new MainMenuBeanModel();

        StatusBeanModel sModel = new StatusBeanModel();
        sModel.setSalesAssociateName("");
        sModel.setCashierName("");
        checkAddOnlineListener(bus);

        RegisterADO registerADO = cargo.getRegisterADO();
        RegisterIfc reg = (RegisterIfc) registerADO.toLegacy();
        if (cargo.isTrainingMode())
        {
            reg.getWorkstation().setTrainingMode(true);
        }
        else
        {
            reg.getWorkstation().setTrainingMode(false);
        }
        StoreStatusIfc storeStatus = (StoreStatusIfc)registerADO.getStoreADO().toLegacy();
        boolean reentryMode = reg.getWorkstation().isTransReentryMode();

        // Training Mode is enabled only if the store / register / till are open
        boolean enableTrainingModeButton = false;
        if (storeStatus.getStatus() == AbstractStatusEntityIfc.STATUS_OPEN)
        {
            if (reg.getStatus() ==  AbstractStatusEntityIfc.STATUS_OPEN)
            {
                TillIfc till = reg.getCurrentTill();
                if (till != null)
                {
                    if (till.getStatus() == AbstractStatusEntityIfc.STATUS_OPEN)
                    {
                        int drawerStatus = reg.getDrawer(DrawerIfc.DRAWER_PRIMARY).getDrawerStatus();
                        if (drawerStatus == AbstractStatusEntityIfc.DRAWER_STATUS_OCCUPIED)
                        {
                            enableTrainingModeButton = true;
                        }
                    }
                }
            }
        }

        NavigationButtonBeanModel localModel = getNavigationButtonBeanModel(utility, trainingModeOn, reentryMode, enableTrainingModeButton);

        String transReentryText;
        if (reentryMode)
        {
            transReentryText =
                utility.retrieveText(
                    MAIN_OPS_BUTTONS_ID,
                    BundleConstantsIfc.MAIN_BUNDLE_NAME,
                    TRANS_REENTRY_OFF_KEY,
                    TRANS_REENTRY_OFF_DEFAULT_TEXT);
        }
        else
        {
            transReentryText =
                utility.retrieveText(
                    MAIN_OPS_BUTTONS_ID,
                    BundleConstantsIfc.MAIN_BUNDLE_NAME,
                    TRANS_REENTRY_ON_KEY,
                    TRANS_REENTRY_ON_DEFAULT_TEXT);
        }

        localModel.setButtonLabel(TRANS_REENTRY_BUTTON_NAME, transReentryText); //"TransReentry"

        // If training mode is turned on, then put Training Mode
        // indication in status panel. Otherwise, return status
        // to online/offline status.
        sModel.setStatus(POSUIManagerIfc.TRAINING_MODE_STATUS, trainingModeOn);
        pModel.setInTraining(trainingModeOn);

        sModel.setRegisterId(reg.getWorkstation().getWorkstationID());
        pModel.setStatusBeanModel(sModel);
        pModel.setLocalButtonBeanModel(localModel);
        return pModel;
    }
    /**
     * Returns a navigation button bean model for the main menu screen
     *
     * @param utility
     *            UtilityManager to retrieve the training button text from
     * @param trainingModeOn
     *            true for training mode
     * @param transReentryMode
     *            true for transaction reentry mode
     * @return NavigationButtonBeanModel
     */
    protected NavigationButtonBeanModel getNavigationButtonBeanModel(
        UtilityManagerIfc utility,
        boolean trainingModeOn,
        boolean transReentryMode,
        boolean enableTrainingModeButton)
    {
        NavigationButtonBeanModel localModel = new NavigationButtonBeanModel();

        String trainingButtonText = null;
        if (trainingModeOn)
        {
            trainingButtonText =
                utility.retrieveText(
                    "Common",
                    BundleConstantsIfc.POS_BUNDLE_NAME,
                    TRAINING_OFF_LABEL_TAG,
                    TRAINING_OFF_LABEL_TEXT);
        }
        else
        {

            trainingButtonText =
                utility.retrieveText(
                    "Common",
                    BundleConstantsIfc.POS_BUNDLE_NAME,
                    TRAINING_ON_LABEL_TAG,
                    TRAINING_ON_LABEL_TEXT);
        }

        // disable training mode if in transaction reentry mode
        if (enableTrainingModeButton)
        {
            if (transReentryMode)
            {
                enableTrainingModeButton = false;
            }
        }

        localModel.setButtonEnabled(CommonActionsIfc.TRAINING_ON_OFF, trainingButtonText, enableTrainingModeButton);




        if (trainingModeOn)
        {
            localModel.setButtonEnabled(CommonActionsIfc.DAILY_OPS, false);
            localModel.setButtonEnabled(CommonActionsIfc.CLOCK, false);
            localModel.setButtonEnabled(CommonActionsIfc.SERVICE_ALERT, false);
            localModel.setButtonEnabled(CommonActionsIfc.ONLINE_OFFICE, false);
            localModel.setButtonEnabled(CommonActionsIfc.TRANS_REENTRY, false);
            localModel.setButtonEnabled(CommonActionsIfc.POS, true);
            localModel.setButtonEnabled(CommonActionsIfc.ADMINISTRATION, true);
        }
        else
        {
            localModel.setButtonEnabled(CommonActionsIfc.DAILY_OPS, true);
            localModel.setButtonEnabled(CommonActionsIfc.CLOCK, true);
            localModel.setButtonEnabled(CommonActionsIfc.SERVICE_ALERT, true);
            localModel.setButtonEnabled(CommonActionsIfc.ONLINE_OFFICE, true);
            localModel.setButtonEnabled(CommonActionsIfc.TRANS_REENTRY, true);
            localModel.setButtonEnabled(CommonActionsIfc.POS, true);
            localModel.setButtonEnabled(CommonActionsIfc.ADMINISTRATION, true);
        }

        return localModel;
    }

    /**
     * Checks if Online Listener has to be added
     * @param bus  Bus reference
     */
    protected void checkAddOnlineListener(BusIfc bus)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        MainCargo cargo = (MainCargo) bus.getCargo();
        if (!cargo.getOnlineListenerHasBeenSet())
        {
            DataManagerIfc dm = (DataManagerIfc) bus.getManager(DataManagerIfc.TYPE);
            if (dm != null)
            {
                OnlineListener ol = new OnlineListener(ui);
                dm.addOnlineListener(ol);
                if (dm instanceof DataManager)
                   {
                    ((DataManager) dm).setQueueMonitorInterval(TRANSACTION_INTERVAL);
                }
                cargo.setOnlineListenerHasBeenSet(true);
            }
        }
    }
}
