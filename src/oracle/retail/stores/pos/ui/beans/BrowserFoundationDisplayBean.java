/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
* ===========================================================================
* $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/BrowserFoundationDisplayBean.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:58 mszekely Exp $
* ===========================================================================
* NOTES
* <other useful comments, qualifications, etc.>
*
* MODIFIED (MM/DD/YY)
*    abonda 01/03/10 - update header date
*    nkgaut 11/14/08 - Changes for removing JDIC Componnents from POS
*    nkgaut 09/30/08 - Bean class for browser foundation
* ===========================================================================
*/

package oracle.retail.stores.pos.ui.beans;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JLabel;

import oracle.retail.stores.pos.services.browserfoundation.BrowserFoundation;
import oracle.retail.stores.pos.services.browserfoundation.BrowserFoundationDisplayBeanModel;
import oracle.retail.stores.pos.ui.behaviour.BrowserBackActionListener;
import oracle.retail.stores.pos.ui.behaviour.BrowserForwardActionListener;
import oracle.retail.stores.pos.ui.behaviour.BrowserHomeActionListener;
import oracle.retail.stores.pos.ui.behaviour.BrowserRefreshActionListener;
import oracle.retail.stores.pos.ui.behaviour.BrowserStopActionListener;

import oracle.retail.stores.foundation.manager.gui.UIModelIfc;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.pos.ui.beans.BaseBeanAdapter;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;

public class BrowserFoundationDisplayBean extends BaseBeanAdapter implements BrowserBackActionListener,
BrowserForwardActionListener, BrowserHomeActionListener, BrowserRefreshActionListener, BrowserStopActionListener
{

  private static final long serialVersionUID = 1L;

  private int MAX_FIELDS = 1;

  private String[] labelText = { "LaunchBrowser" };

  /** array of labels */
  protected JLabel[] labels = null;

  /** arraylist of components */
  protected ArrayList alcomp = new ArrayList();

  /** The bean model * */
  protected BrowserFoundationDisplayBeanModel beanModel = null;

  protected BrowserFoundation objBrowserFoundation ;
  /**
   * Constructor
   */
  public BrowserFoundationDisplayBean()
  {
    super();
  }

  /**
   * Configure the class.
   */
  public void configure()
  {
    UI_PREFIX = "BrowserFoundationDisplayBean";
    setName("LaunchBrowserDisplayBean");
    uiFactory.configureUIComponent(this, UI_PREFIX);
  }

  /**
   * Initialize the display components.
   */
  protected void initComponents(int maxFields, String[] label)
  {
    labels = new JLabel[maxFields];
    labels[0] = uiFactory.createLabel(label[0] + "label", label[0], null, UI_LABEL);
    labels[0].setVisible(false);

    if(objBrowserFoundation.isWebBrowserInstanceCreated())
    {
    	Component browser = (Component)objBrowserFoundation.getWebBrowserInstance();
    	browser.setVisible(true);
    	alcomp.add(browser);
    }
  }

  /**
   * Initialize the panel
   */
  public void initLayout()
  {
    UIUtilities.layoutDataPanel(this, labels, alcomp);
  }

  /**
   * Resets and sets the layout again
   */
  protected void setupLayout()
  {
    // lay out data panel
    removeAll();
    initComponents(MAX_FIELDS, labelText);
    initLayout();
    invalidate();
    validate();
  }

  /**
   * Update the bean if It's been changed
   */
  protected void updateBean()
  {
    alcomp = new ArrayList();
    setupLayout();
  }

  /**
   * Sets the information to be shown by this bean.
   *
   * @param model the model to be shown. The runtime type should be
   *          BrowserFoundationDisplayBeanModel
   */
  public void setModel(UIModelIfc model)
  {
    if (model == null)
    {
      throw new NullPointerException("Attempt to set BrowserFoundationDisplayBeanModel  to null");
    }
    if (model instanceof BrowserFoundationDisplayBeanModel)
    {
      beanModel = (BrowserFoundationDisplayBeanModel) model;
      objBrowserFoundation = BrowserFoundation.getInstance();
      updateBean();

    }
  }

  /**
   * Gets the POSBeanModel associated with this bean.
   *
   * @return the POSBaseBeanModel associated with this bean
   */
  public POSBaseBeanModel getPOSBaseBeanModel()
  {
    return beanModel;
  }

  /**
   * Activate this screen and listeners.
   */
  public void activate()
  {
    super.activate();
  }

  /**
   * Deactivate this screen and listeners.
   */
  public void deactivate()
  {
    super.deactivate();
  }

  /**
   * This method is called through a connection from the
   * GlobalBrowserNavigationButtonBean. Indicates which event process should be
   * performed based on the ActionCommand
   *
   * @param event the ActionEvent contructed by the caller.
   */
  public void actionPerformed(ActionEvent event)
  {
    if (event.getActionCommand().equalsIgnoreCase("Back"))
    {
    	objBrowserFoundation.performBackAction();
    }
    else if (event.getActionCommand().equalsIgnoreCase("Forward"))
    {
    	objBrowserFoundation.performForwardAction();
    }
    else if (event.getActionCommand().equalsIgnoreCase("Stop"))
    {
    	objBrowserFoundation.performStopAction();
    }
    else if (event.getActionCommand().equalsIgnoreCase("Refresh"))
    {
    	objBrowserFoundation.performRefreshAction();
    }
    else if (event.getActionCommand().equalsIgnoreCase("Home"))
    {
    	objBrowserFoundation.performHomeAction();
    }
  }

}
