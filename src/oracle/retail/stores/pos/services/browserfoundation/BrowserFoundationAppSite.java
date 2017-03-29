/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
* ===========================================================================
* $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/browserfoundation/BrowserFoundationAppSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:02 mszekely Exp $
* ===========================================================================
* NOTES
* <other useful comments, qualifications, etc.>
*
* MODIFIED (MM/DD/YY)
*    cgreen 05/26/10 - convert to oracle packaging
*    abonda 01/03/10 - update header date
*    nkgaut 11/14/08 - Changes for removing JDIC Components from POS Installed
*                      Directories
*    nkgaut 10/01/08 - A new site for browser foundation
* ===========================================================================
*/

package oracle.retail.stores.pos.services.browserfoundation;

import java.net.MalformedURLException;

import oracle.retail.stores.commerceservices.audit.AuditLoggerConstants;
import oracle.retail.stores.commerceservices.audit.AuditLoggerServiceIfc;
import oracle.retail.stores.commerceservices.audit.AuditLoggingUtils;
import oracle.retail.stores.commerceservices.audit.event.AuditLogEventEnum;
import oracle.retail.stores.commerceservices.audit.event.UserEvent;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.store.WorkstationIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.LetterIfc;
import oracle.retail.stores.foundation.tour.ifc.SiteActionIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.ButtonPressedLetter;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

public class BrowserFoundationAppSite extends PosSiteActionAdapter implements SiteActionIfc
{

  private static final long serialVersionUID = 1L;

  /**
   * indicator of the property file to use to get the configured url.
   */
  private String BROWSER_URL_IND = "browserurl";

  /**
   * The BrowserFoundationAppSite site allows the user to access a specific
   * configured URL
   * @param bus
   * the bus arriving at this site
   */
  public void arrive(BusIfc bus)
  {
    boolean bErrorScreenReqd = false;
    String HomeURL = null;
    POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
    BrowserFoundationDisplayBeanModel model = new BrowserFoundationDisplayBeanModel();
    DialogBeanModel dialogBean = new DialogBeanModel();
    BrowserFoundation objBrowserFoundation = null;

    ParameterManagerIfc paramManager = (ParameterManagerIfc) bus.getManager(ParameterManagerIfc.TYPE);

    try
    {
      HomeURL = paramManager.getStringValue(BROWSER_URL_IND);
      if(!HomeURL.startsWith("http"))
      {
        bErrorScreenReqd = true;
        dialogBean.setResourceID("BrowserFoundationURLError");
        dialogBean.setType(DialogScreensIfc.ACKNOWLEDGEMENT);
        dialogBean.setButtonLetter(DialogScreensIfc.BUTTON_OK, "Undo");
        logger.error("Invalid Home URL : Only http or https protocol is supported");
      }
    }
    catch (ParameterException e1)
    {
      logger.error("Exception while getting BrowserUrl from application.xml", e1);
      dialogBean.setResourceID("BrowserFoundationError");
      dialogBean.setType(DialogScreensIfc.ACKNOWLEDGEMENT);
      dialogBean.setButtonLetter(DialogScreensIfc.BUTTON_OK, "Undo");
      bErrorScreenReqd = true;
    }


    if (!bErrorScreenReqd)
    {
      try
      {
    	  objBrowserFoundation = BrowserFoundation.getInstance();
    	  objBrowserFoundation.setWebBrowserInstance(HomeURL);
      }
      catch(NoClassDefFoundError e)
      {
        bErrorScreenReqd = true;
        dialogBean.setResourceID("BrowserFoundationConfigError");
        dialogBean.setType(DialogScreensIfc.ACKNOWLEDGEMENT);
        dialogBean.setButtonLetter(DialogScreensIfc.BUTTON_OK, "Undo");
        logger.error("UnsatisfiedLinkError while loading jdic library" + e);
      }
      catch(UnsatisfiedLinkError e)
      {
          bErrorScreenReqd = true;
          dialogBean.setResourceID("BrowserFoundationConfigError");
          dialogBean.setType(DialogScreensIfc.ACKNOWLEDGEMENT);
          dialogBean.setButtonLetter(DialogScreensIfc.BUTTON_OK, "Undo");
          logger.error("UnsatisfiedLinkError while loading jdic library" + e);
      }
      catch(MalformedURLException e)
      {
        bErrorScreenReqd = true;
        dialogBean.setResourceID("BrowserFoundationURLError");
        dialogBean.setType(DialogScreensIfc.ACKNOWLEDGEMENT);
        dialogBean.setButtonLetter(DialogScreensIfc.BUTTON_OK, "Undo");
        logger.error("MalformedURLException ::" + e);
      }
      catch(Exception e)
      {
        bErrorScreenReqd = true;
        dialogBean.setResourceID("BrowserFoundationConfigError");
        dialogBean.setType(DialogScreensIfc.ACKNOWLEDGEMENT);
        dialogBean.setButtonLetter(DialogScreensIfc.BUTTON_OK, "Undo");
        logger.error("MalformedURLException ::" + e);
      }

    }
    try
    {
      if (!bErrorScreenReqd) //log an audit login event only if url set properly.
      {
        AuditLoggerServiceIfc auditService = AuditLoggingUtils.getAuditLogger();
        UserEvent ev = (UserEvent) AuditLoggingUtils.createLogEvent(UserEvent.class, AuditLogEventEnum.LAUNCH_BROWSER);

        BrowserFoundationCargo cargo = (BrowserFoundationCargo) bus.getCargo();
        ev.setStoreId(cargo.getOperator().getStoreID());
        ev.setUserId(cargo.getOperator().getLoginID());
        ev.setStatus(AuditLoggerConstants.SUCCESS);
        ev.setEventOriginator("BrowserFoundationAppSite.arrive");

        RegisterIfc ri = cargo.getRegister();
        if (ri != null)
        {
          WorkstationIfc wi = ri.getWorkstation();
          if (wi != null)
          {
            ev.setRegisterNumber(wi.getWorkstationID());
          }
        }
        auditService.logStatusSuccess(ev);
      }
    }
    catch (Exception e)
    {
      logger.warn("Error while logging event", e);
    }

    if (bErrorScreenReqd) //if homeurl not set or proper , display a error screen.
    {
      ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogBean);
    }
    else
    {
      ui.showScreen(POSUIManagerIfc.BROWSER_FOUNDATION, model);
    }
  }

  /**
   * This method logs an event departing from this site
   * @param bus
   * the bus arriving at this site
   */
  public void depart(BusIfc bus)
  {
    try
    {
      AuditLoggerServiceIfc auditService = AuditLoggingUtils.getAuditLogger();
      UserEvent ev = (UserEvent) AuditLoggingUtils.createLogEvent(UserEvent.class, AuditLogEventEnum.LOG_OUT);

      BrowserFoundationCargo cargo = (BrowserFoundationCargo) bus.getCargo();
      LetterIfc letter = (LetterIfc) bus.getCurrentLetter();
      if (letter instanceof ButtonPressedLetter) // Is ButtonPressedLetter
      {
        String letterName = letter.getName();
        if (letterName != null && letterName.equals(CommonLetterIfc.UNDO))
        {
          RegisterIfc ri = cargo.getRegister();
          if (ri != null)
          {
            WorkstationIfc wi = ri.getWorkstation();
            if (wi != null)
            {
              ev.setRegisterNumber(wi.getWorkstationID());
            }
          }
          ev.setStoreId(cargo.getOperator().getStoreID());
          ev.setUserId(cargo.getOperator().getLoginID());
          ev.setStatus(AuditLoggerConstants.SUCCESS);
          ev.setEventOriginator("BrowserFoundationAppSite.depart");
          auditService.logStatusSuccess(ev);
        }
      }
    }
    catch (Exception e)
    {
      logger.warn("Error while logging event ", e);
    }
  }
}
