/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/ApplicationFrame.java /rgbustores_13.4x_generic_branch/2 2011/09/22 13:24:34 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    asinton   09/21/11 - Fixed training mode and transaction reentry mode
 *                         screens.
 *    cgreene   05/28/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   12/07/09 - dont setresizable false unless in prod mode
 *    cgreene   10/30/09 - added productionMode to call maximizeFrame
 *
 * ===========================================================================
 * $Log:
 *  4    360Commerce 1.3         12/19/2007 7:05:42 AM  Manikandan Chellapan
 *       PAPB FR 34 Implementation
 *  4    I18N_P2    1.2.1.0     12/17/2007 5:35:43 PM  Maisa De Camargo
 *       Externalized POS Window Title.
 *  3    360Commerce 1.2         3/31/2005 4:27:13 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:19:38 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:09:30 PM  Robert Pearse   
 * $
 * Revision 1.7  2004/09/23 00:07:11  kmcbride
 * @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 * Revision 1.6  2004/03/26 21:18:19  cdb
 * @scr 4204 Removing Tabs.
 *
 * Revision 1.5  2004/03/21 16:34:28  bjosserand
 * @scr 4093 Transaction Reentry
 * Revision 1.4 2004/03/16 17:15:22 build Forcing head revision
 * 
 * Revision 1.3 2004/03/16 17:15:16 build Forcing head revision
 * 
 * Revision 1.2 2004/02/11 20:56:26 rhafernik @scr 0 Log4J conversion and code cleanup
 * 
 * Revision 1.1.1.1 2004/02/11 01:04:21 cschellenger updating to pvcs 360store-current
 * 
 * 
 * 
 * Rev 1.1 Sep 08 2003 17:30:34 DCobb Migration to jvm 1.4.1 Resolution for 3361: New Feature: JVM 1.4.1_03 (Windows)
 * Migration
 * 
 * Rev 1.0 Aug 29 2003 16:09:36 CSchellenger Initial revision.
 * 
 * Rev 1.3 May 09 2003 11:14:10 RSachdeva props null check Resolution for POS SCR-2320: Application locks at loging
 * when runing without bundles.
 * 
 * Rev 1.2 05 Jun 2002 22:02:38 baa support for opendrawerfortrainingmode parameter Resolution for POS SCR-1645:
 * Training Mode Enhancements
 * 
 * Rev 1.1 14 May 2002 18:29:54 baa training mode enhancements Resolution for POS SCR-1645: Training Mode Enhancements
 * 
 * Rev 1.0 Apr 29 2002 14:47:42 msg Initial revision.
 * 
 * Rev 1.1 10 Apr 2002 13:59:30 baa make code compliant with coding guidelines Resolution for POS SCR-1590: PLAF code
 * does not meet the coding standards
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import oracle.retail.stores.domain.utility.PropertiesUtil;
import oracle.retail.stores.foundation.manager.gui.ApplicationMode;
import oracle.retail.stores.foundation.manager.gui.DisplaySpec;
import oracle.retail.stores.foundation.manager.gui.EYSRootPaneContainer;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.pos.ui.plaf.UIFactory;

/**
 * This is the controlling frame for the POS application.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public class ApplicationFrame extends JFrame implements EYSRootPaneContainer
{
    // This id is used to tell the compiler not to generate a new serialVersionUID.
    static final long serialVersionUID = 3053510313690468562L;

    /** revision number supplied by PVCS * */
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    /** The UI prefix to use when configuring this frame with the {@link UIFactory}. */
    public static final String UI_PREFIX = "ApplicationFrame";

    /** The UIManager color for "trainingModeBackground". */
    protected static Color trainingModeOnColor = null;
    /** The UIManager color for "appBackground". */
    protected static Color trainingModeOffColor = null;
    /** The UIManager color for "transReentryBackground". */
    protected static Color transReentryModeColor = null;

    /** A reference to the content pane of this frame. */
    protected static JPanel pane = null;

    /** The DisplaySpec configured width for display when not in PROD mode. */
    protected int displayWidth = 0;

    /** The DisplaySpec configured height for display when not in PROD mode. */
    protected int displayHeight = 0;
    
    /** The DisplaySpec configured title to display in the titlebar (if shown). */
    protected String defaultTitle = DEFAULT_TITLE;
    
    /** The DisplaySpec configured titleTag used to get the resourced title. */
    protected String titleTag = null;

    /** The DisplaySpec configured resource bundle used to get the title. */
    protected String resourceBundleFilename = null;
   
    /**
     * The DisplaySpec configured value of whether the titlebar close button
     * should be enable.
     * @see CLOSE_BUTTON_ENABLED
     * @see CLOSE_BUTTON_DISABLED
     */
    protected String closeButton = CLOSE_BUTTON_ENABLED;

    /** The name of the {@link DisplaySpec} that configured this frame. */
    protected String displaySpecName = null;

    /** Whether or not in training mode. */
    protected boolean trainingMode = false;

    /** Whether or not in transaction re-entry mode. */
    protected boolean transReentryMode = false;

    /** Whether or not in production mode. */
    protected boolean productionMode = true;

    /**
     * Default constructor. Calls {@link #initialize()} and sets the keyboard
     * focus manager to {@link POSFocusManager}.
     */
    public ApplicationFrame()
    {
        super();
        initialize();

        // the next two lines are to support wedge keyboards
        POSFocusManager pfm = new POSFocusManager();
        java.awt.DefaultKeyboardFocusManager.setCurrentKeyboardFocusManager(pfm);
    }

    /**
     * Initializes the frame. If application property "env" is set to "PROD"
     * then this frame is locked down.
     * 
     * @see #setUndecorated(boolean);
     * @see #setDefaultLookAndFeelDecorated(boolean);
     * @see #setAlwaysOnTop(boolean);
     */
    protected void initialize()
    {
        pane = (JPanel) getRootPane().getContentPane();
        UIFactory.getInstance().configureUIComponent(pane, UI_PREFIX);
        trainingModeOnColor = UIManager.getColor("trainingModeBackground");
        trainingModeOffColor = UIManager.getColor("appBackground");
        transReentryModeColor = UIManager.getColor("transReentryBackground");

        String env = Gateway.getProperty("application", "env", DEFAULT_POS_ENVIRONMENT);
        productionMode = DEFAULT_POS_ENVIRONMENT.equalsIgnoreCase(env);

        // PCI: If pos is not in dev mode remove all window controls
        if (productionMode)
        {
            // remove all buttons
            setUndecorated(true);
            setDefaultLookAndFeelDecorated(true);
            // set always on top
            setAlwaysOnTop(true);
            // don't allow the frame to be resized by the user
            setResizable(false);
        }
    }

    /**
     * Centers the frame in the display.
     */
    public void centerFrame()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = this.getSize();

        //  Calculate the new starting point for the window
        int newX = (screenSize.width - windowSize.width) / 2;
        int newY = (screenSize.height - windowSize.height) / 2;

        //  Move frame
        setLocation(newX, newY);
    }

    /**
     * Sets the display width from a string value.
     * 
     * @param w
     *            contains the width as a string
     */
    public void setDisplayWidth(String w)
    {
        displayWidth = Integer.valueOf(w).intValue();
    }

    /**
     * Sets the display height from a string value.
     * 
     * @param w
     *            contains the height as a string
     */
    public void setDisplayHeight(String h)
    {
        displayHeight = Integer.valueOf(h).intValue();
    }

    /**
     * Configures the frame from a properties object.
     * 
     * @param props
     *            the properties object
     */
    public void configure(Properties props)
    {
        if (productionMode)
        {
            maximizeFrame();
        }
        else
        {
            int width = displayWidth;
            int height = displayHeight;

            if (width == 0 || height == 0)
            {
                width = PropertiesUtil.getInt(props, "displayWidth", 800);
                height = PropertiesUtil.getInt(props, "displayHeight", 600);

            }
            if (getSize().width != width || getSize().height != height)
            {
                setSize(new Dimension(width, height));
            }
        }

        setTitle();

        if(closeButton.equals(CLOSE_BUTTON_DISABLED))
        {
            super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }        
    }

    /**
     * Gets current mode. Returns true for training mode false otherwise @returns boolean the training mode
     */
    public boolean getTrainingMode()
    {
        return trainingMode;
    }

    /**
     * Sets current mode. True for training mode false otherwise @returns boolean the training mode
     */
    public void setTrainingMode(boolean value)
    {
        if (trainingMode != value)
        {
            trainingMode = value;
            switchTrainingModeImage();
        }
    }

    protected void switchTrainingModeImage()
    {
        Color background = null;
        if (trainingMode)
        {
            background = trainingModeOnColor;
        }
        else
        {
            background = trainingModeOffColor;
        }

        if (background != null)
        {
            pane.setBackground(background);
            repaint();
        }
    }

    protected void switchTransReentryModeImage()
    {
        Color background = null;
        if (transReentryMode)
        {
            background = transReentryModeColor;
        }
        else
        {
            background = trainingModeOffColor;
        }

        if (background != null)
        {
            pane.setBackground(background);
            repaint();
        }
    }

    /**
     * Sets the size of the frame to the full size of the screen.
     */
    public void maximizeFrame()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //  Calculate the new starting point for the window
        int newX = screenSize.width;
        int newY = screenSize.height;

        //  set size
        setSize(newX, newY);

        // move frame
        setLocation(0, 0);
    }

    /**
     * Retrieves the PVCS revision number.
     * 
     * @return String representation of revision number
     */
    public String getRevisionNumber()
    {
        // return string
        return (Util.parseRevisionNumber(revisionNumber));
    }

    /**
     * Static main() test method.
     * 
     * @param command
     *            line args
     */
    public static void main(String[] args)
    {
        ApplicationFrame af = new ApplicationFrame();
        af.configure(new Properties());
        af.setSize(800, 600);
        af.setVisible(true);
    }

    /**
     * @return Returns the transReentryMode.
     */
    public boolean isTransReentryMode()
    {
        return transReentryMode;
    }

    /**
     * @param transReentryMode
     *            The transReentryMode to set.
     */
    public void setTransReentryMode(boolean value)
    {
        if (transReentryMode != value)
        {
            transReentryMode = value;
            switchTransReentryModeImage();
        }
    }

	/**
     * @return the defaultTitle
     */
    public String getDefaultTitle()
    {
        return defaultTitle;
    }

    /**
     * @param defaultTitle the defaultTitle to set
     */
    public void setDefaultTitle(String defaultTitle)
    {
        this.defaultTitle = defaultTitle;
    }

    /**
     * @return the titleTag
     */
    public String getTitleTag()
    {
        return titleTag;
    }

    /**
     * @param titleTag the titleTag to set
     */
    public void setTitleTag(String titleTag)
    {
        this.titleTag = titleTag;
    }
    
    /**
     * Set the Application Frame Window Title based on the resource bundle value
     *
     */
    void setTitle()
    {
        String i18NTitle = retrieveText(titleTag, defaultTitle, resourceBundleFilename);

        // set the title and screen size, if they have changed
        if (getTitle() == null || !getTitle().equals(i18NTitle))
        {
            setTitle(i18NTitle);
        }
    }

    
    /**
     * @return the displaySpecName
     */
    public String getDisplaySpecName()
    {
        return displaySpecName;
    }

    /**
     * @param displaySpecName the displaySpecName to set
     */
    public void setDisplaySpecName(String displaySpecName)
    {
        this.displaySpecName = displaySpecName;
    }

    /** 
     * Retrieve Title from Resource Bundle
     * 
     * @param titleTag
     * @param titleText
     * @param resourceBundleFilename
     * @return
     */
    private String retrieveText(String titleTag, String titleText, String resourceBundleFilename)
    {
        return UIUtilities.retrieveText(displaySpecName, resourceBundleFilename, titleTag, titleText);
    }

    /**
     * @return the resourceBundleFilename
     */
    public String getResourceBundleFilename()
    {
        return resourceBundleFilename;
    }

    /**
     * @param resourceBundleFilename the resourceBundleFilename to set
     */
    public void setResourceBundleFilename(String resourceBundleFilename)
    {
        this.resourceBundleFilename = resourceBundleFilename;
    }

    /**
     * @return the closeButton
     */
    public String getCloseButton()
    {
        return closeButton;
    }

    /**
     * @param closeButton the closeButton to set
     */
    public void setCloseButton(String closeButton)
    {
        this.closeButton = closeButton;
    }

    /**
     * Sets the mode for this ApplicationFrame.  Can be one of:
     * ApplicationMode.TRANSACTION_REENTRY_MODE, ApplicationMode.TRAINING_MODE,
     * or ApplicationMode.NORMAL_MODE.
     * 
     * @param applicationMode
     * @see oracle.retail.stores.foundation.manager.gui.ApplicationMode
     */
    public void setApplicationMode(ApplicationMode applicationMode)
    {
        switch(applicationMode)
        {
            case TRANSACTION_REENTRY_MODE :
                setTransReentryMode(true);
                break;
            case TRAINING_MODE :
                setTrainingMode(true);
                break;
            case NORMAL_MODE :
            default :
                setTransReentryMode(false);
                setTrainingMode(false);
                break;
        }
    }
}
