/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
* ===========================================================================
* $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/NavigationButtonBean.java /rgbustores_13.4x_generic_branch/3 2011/08/11 14:55:05 cgreene Exp $
* ===========================================================================
* NOTES
* <other useful comments, qualifications, etc.>
*
* MODIFIED (MM/DD/YY)
*    cgreen 08/11/11 - UI tweaks for global button size and prompt area
*                      alignment
*    npoola 10/11/10 - added the map to maintain the state of the button based
*                      on the *uiconfig.xml
*    cgreen 07/14/10 - fix key handling by forwarding from frame to rootpane
*    cgreen 07/02/10 - switch to stringbuilder
*    cgreen 01/05/10 - implement parameter enabled bean spec
*    abonda 01/03/10 - update header date
*    cgreen 12/07/09 - add maxButtons and ability to parse ctrl keyword for
*                      buttons
*    cgreen 09/17/09 - refactored methods in order to subclass with
*                      dialogbutton bean
*    cgreen 04/15/09 - only clear buttons if this is a LocalNavigationPanelSpec
*    cgreen 04/15/09 - clear and disable buttons when this bean is only cached
*                      once and shared with cases where model may be null
*    nkgaut 11/14/08 - Reverted change of Max Horizontal buttons and added
*                      setter method of MAX_HORIZONTAL variable to accomodate
*                      six buttons for browser.
*    nkgaut 09/29/08 - Changed MAX_HORIZONTAL from 5 to 6 to accomodate six
*                      buttons for browserfoundation
* ===========================================================================
     $Log:
      4    360Commerce 1.3         1/25/2006 4:11:32 PM   Brett J. Larsen merge
            7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
      3    360Commerce 1.2         3/31/2005 4:29:08 PM   Robert Pearse
      2    360Commerce 1.1         3/10/2005 10:23:40 AM  Robert Pearse
      1    360Commerce 1.0         2/11/2005 12:12:44 PM  Robert Pearse
     $:
      4    .v700     1.2.1.0     11/15/2005 16:05:47    Jason L. DeLeau 4214:
           Make orientation configurable
      3    360Commerce1.2         3/31/2005 15:29:08     Robert Pearse
      2    360Commerce1.1         3/10/2005 10:23:40     Robert Pearse
      1    360Commerce1.0         2/11/2005 12:12:44     Robert Pearse
     $
     Revision 1.3  2004/03/16 17:15:18  build
     Forcing head revision

     Revision 1.2  2004/02/11 20:56:27  rhafernik
     @scr 0 Log4J conversion and code cleanup

     Revision 1.1.1.1  2004/02/11 01:04:22  cschellenger
     updating to pvcs 360store-current


 *
 *    Rev 1.1   Dec 10 2003 15:09:18   nrao
 * Modified NavigationButtonBean so it allows 3 or more screens of buttons when required.
 *
 *    Rev 1.0   Aug 29 2003 16:11:14   CSchellenger
 * Initial revision.
 *
 *    Rev 1.3   22 Jul 2003 22:18:32   baa
 * create EYSButton instead of JButton
 *
 *    Rev 1.2   Aug 15 2002 17:55:52   baa
 * apply foundation  updates to UISubsystem
 *
 * Resolution for POS SCR-1769: 5.2 UI defects resulting from change to java 1.4
 *
 *    Rev 1.1   Aug 14 2002 18:18:02   baa
 * format currency
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.0   Apr 29 2002 14:56:34   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:56:18   msg
 * Initial revision.
 *
 *    Rev 1.7   Feb 23 2002 15:04:18   mpm
 * Re-started internationalization initiative.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.6   11 Feb 2002 16:10:52   pdd
 * Fixed changedUpdate() bug.
 * Resolution for POS SCR-1017: Mod Effect for ChecksAccepted incorrect
 *
 *    Rev 1.5   11 Feb 2002 09:56:24   pdd
 * Added deactivate to reset savedStates.
 * Resolution for POS SCR-1017: Mod Effect for ChecksAccepted incorrect
 *
 *    Rev 1.4   06 Feb 2002 20:47:36   baa
 * defect fix
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 *
 *    Rev 1.3   01 Feb 2002 14:29:18   KAC
 * Added code from Dave Teagle for addButtonListener,
 * removeButtonListener.
 * Resolution for POS SCR-672: Create List Parameter Editor
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import oracle.retail.stores.foundation.manager.gui.ButtonBarBeanIfc;
import oracle.retail.stores.foundation.manager.gui.ButtonSpec;
import oracle.retail.stores.foundation.manager.gui.UIModelIfc;
import oracle.retail.stores.foundation.manager.ui.jfc.DefaultMailAction;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.pos.ui.behavior.ButtonListener;

import org.apache.log4j.Logger;

/**
 * This class represents a collection of push buttons and associated key stroke
 * equivalents.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/3 $
 */
public class NavigationButtonBean extends BaseBeanAdapter
    implements SwingConstants, DocumentListener, ButtonBarBeanIfc
{
    private static final long serialVersionUID = -2719605005950105529L;

    /** The logger to which log messages will be sent. */
    protected static final Logger logger = Logger.getLogger(NavigationButtonBean.class);

    /** revision number supplied by Team Connection */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/3 $";

    /** Property prefixes for configuring this widget. */
    public static final String PREFIX_BUTTONBAR = "ButtonBar";
    public static final String PREFIX_VERTICALBUTTON = "VerticalButton";
    public static final String PREFIX_HORIZONTALBUTTON = "HorizontalButton";

    /** Initial values for the number of buttons that will fit per orientation. */
    public static final int MAX_HORIZONTAL        = 6;
    public static final int MAX_VERTICAL          = 8;

    public static final String ACTION_NAME_DELIMITER = ",";
    public static final String ACTION_COMMAND_MORE = "More";

    /** the action used to trigger the buttons to switch to the next bar. */
    private final MoreAction moreAction = new MoreAction();

    protected POSBaseBeanModel          baseModel   = null;
    protected NavigationButtonBeanModel buttonModel = null;

    /** Orientation of the button bar defaults to VERTICAL */
    protected int orientation = VERTICAL;
    protected String propertyPrefix = PREFIX_BUTTONBAR;
    protected String buttonPrefix;

    /** actions for the buttons */
    protected UIAction[][] actions;
    protected boolean[][] savedStates;

    /** buttons for the task bar */
    protected JButton[][] buttons = new JButton[0][];

    /** Size of the button index */
    protected int buttonIndexSize;

    /** current active button index */
    protected int currentButtonIndex;

    /** maximum number of buttons that can fit on the bean at once. */
    protected int maxButtons = MAX_VERTICAL;

    /** constraints */
    protected GridBagConstraints constraints;

    /** Button States */
    protected HashMap<String, Boolean> buttonStates = new HashMap<String, Boolean>();    
      

    /**
     * Default constructor.
     */
    public NavigationButtonBean()
    {
        super(new GridBagLayout());
        buttonPrefix = PREFIX_VERTICALBUTTON;
        // set the panel's display aspects
        uiFactory.configureUIComponent(this, propertyPrefix);
    }

    /**
     * Creates an empty NavigationButtonBean.
     * 
     * @param actions two dimensional list of buttons
     */
    public NavigationButtonBean(UIAction[][] actions)
    {
        this();
        initialize(actions);
    }

    /**
     * Initializes the button bar
     * 
     * @param actions the actions to associate with the bar
     */
    protected void initialize(UIAction[][] actions)
    {
        // Remove all previous buttons
        removeAll();

        // set up the constraints object used to layout buttons
        constraints = uiFactory.getConstraints(propertyPrefix);
        this.actions = actions;
        buttons = new JButton[actions.length][actions[0].length];

        // create buttons and add them
        setUIActions(this.actions);

        //set the 1st button bar to visible
        setButtonsVisible(0, true);
    }

    /**
     * Sets the model for the current settings of this bean.
     * 
     * @param model the model for the current values of this bean
     */
    @Override
    public void setModel(UIModelIfc model)
    {
        if (model == null)
        {
            throw new NullPointerException("NavigationButtonBean model must not be set to null");
        }
        if (model instanceof POSBaseBeanModel)
        {
            baseModel = (POSBaseBeanModel)model;
        }
        if (baseModel.getLocalButtonBeanModel() != null)
        {
            buttonModel = baseModel.getLocalButtonBeanModel();

            if (buttonModel.getNewButtons() != null)
            {
                configureButtons(buttonModel.getNewButtons());
            }
            if (buttonModel.getModifyButtons() != null)
            {
                modifyButtons(buttonModel.getModifyButtons());
            }
        }
    }

    /**
     * Calls super then {@link #resetTaskButtonBar()}.
     * 
     * @see oracle.retail.stores.pos.ui.beans.BaseBeanAdapter#activate()
     */
    @Override
    public void activate()
    {
        super.activate();
        resetTaskButtonBar();
    }

    /**
     * Calls super method then sets the saved states to null.
     * 
     * @see oracle.retail.stores.pos.ui.beans.BaseBeanAdapter#deactivate()
     */
    @Override
    public void deactivate()
    {
        super.deactivate();
        savedStates = null;
        if (isReusable())
        {
            setButtonsDisabled();
        }
    }

    /**
     * Get orientation
     * 
     * @return orientation {@link SwingConstants#HORIZONTAL} or {@link SwingConstants#VERTICAL}
     */
    public int getOrientation()
    {
        return orientation;
    }


    /**
     * Set orientation. Resets the {@link #buttonPrefix} and {@link #maxButtons}
     * to their defaults.
     * 
     * @param newOrientation {@link SwingConstants#HORIZONTAL} or {@link SwingConstants#VERTICAL}
     */
    public void setOrientation(int newOrientation)
    {
        orientation = newOrientation;
        switch (orientation)
        {
            case HORIZONTAL:
                buttonPrefix = PREFIX_HORIZONTALBUTTON;
                maxButtons = MAX_HORIZONTAL;
                break;
            default:
                buttonPrefix = PREFIX_VERTICALBUTTON;                
                maxButtons = MAX_VERTICAL;
        }
    }

    /**
     * Set the orientation of the bean from the XML. If
     * <BEANPROPERTY propName="orientation" propValue="Horizontal"/>
     * then its horizontal, otherwise vertical.
     * 
     * @param orientation
     * @since 7.0.3
     */
    public void setOrientation(String orientation)
    {
        if(orientation.equalsIgnoreCase("Horizontal"))
        {
            setOrientation(HORIZONTAL);
        }
        else
        {
            setOrientation(VERTICAL);
        }
    }

    /**
     * @return the maxButtons
     */
    public int getMaxButtons()
    {
        return maxButtons;
    }

    /**
     * @param maxButtons the maxButtons to set
     */
    public void setMaxButtons(int maxButtons)
    {
        this.maxButtons = maxButtons;
    }

    /**
     * Adds a button listener. This adds the button listener as an action
     * listener to each of the buttons.
     * 
     * @param listener the button listener
     */
    public void addButtonListener(ButtonListener listener)
    {
        if (buttons != null)
        {
            for (int i = 0; i < buttons.length; i++)
            {
                for (int j = 0; j < buttons[i].length; j++)
                {
                    buttons[i][j].addActionListener(listener);
                    buttons[i][j].setActionCommand(actions[i][j].getActionName());
                }
            }
        }
    }

    /**
     * Removes a button listener. This removes the button listener as an action
     * listener from each of the buttons.
     * 
     * @param listener the button listener
     */
    public void removeButtonListener(ButtonListener listener)
    {
        if (buttons != null)
        {
            for (int i = 0; i < buttons.length; i++)
            {
                for (int j = 0; j < buttons[i].length; j++)
                {
                    buttons[i][j].removeActionListener(listener);
                }
            }
        }
    }

    /**
     * Modifies buttons using info from the the bean model. Only the button
     * label end enable state may be changed.
     */
    public void modifyButtons(ButtonSpec[] buttonSpecs)
    {
        for (int i = buttonSpecs.length - 1; i >= 0; i--)
        {
            try
            {
                UIAction action = getUIAction(buttonSpecs[i].getActionName());
                if (buttonSpecs[i].getLabel() != null)
                {
                    action.setButtonName(buttonSpecs[i].getLabel());
                }
                if (buttonSpecs[i].getEnabledFlag() != null)
                {
                    action.setEnabled(buttonSpecs[i].getEnabled());
                }
                if (buttonSpecs[i].getKeyName() != null)
                {
                    action.setKeyName(buttonSpecs[i].getKeyName());
                }
            }
            catch (ActionNotFoundException e)
            {
                logger.warn("Failed to Find Action: " + buttonSpecs[i].getActionName());
            }
        }
    }

    /**
     * This method is called by the POSBeanSpec; the ButtonSpec array comes from
     * the <BUTTON> tag in the beans.xml file
     * 
     * @param buttonSpecs each element of this array contains the information to
     *            create a button.
     */
    public void configureButtons(ButtonSpec[] buttonSpecs)
    {
        int barCount    = 1;
        int buttonCount = maxButtons;
        if(orientation == VERTICAL)
        {
            if (buttonSpecs.length > maxButtons)
            {
                // Calculate the number of button bars needed.
                // If there are more than MAX_VERTICAL buttons, then one button
                // per bar will be dedicated to the "More" key;  Therefore, the
                // number of buttons than can be used for other functions is
                // MAX_VERTICAL - 1.
                int funcButtons = maxButtons - 1;
                barCount = buttonSpecs.length / funcButtons;

                // The integer division above may have remainder; if so
                // then we need another button bar for remainder number of
                // buttons.
                int rem  = buttonSpecs.length % funcButtons;
                if (rem > 0)
                {
                    barCount++;
                }
            }
        }
        UIAction[][] actions = createActions(buttonSpecs, barCount, buttonCount);
        initialize(actions);
    }

    /**
     * Creates actions for the button bar.
     * 
     * @param buttonSpecs Element[][] Parsed XML
     * @param indexSize int Number of bars
     * @param buttonSize int Number of buttons
     * @return UIAction[][] actions
     */
    public UIAction[][] createActions(ButtonSpec[] buttonSpecs,
                                      int indexSize, int buttonSize)
    {
        UIAction[][] actions = new UIAction[indexSize][buttonSize];
        ActionListener listener = null;
        buttonIndexSize = indexSize;
        int buttonIndex = 0;

        //loop through the bars
        for(int i = 0; i < indexSize; i++)
        {
            //loop through the buttons
            for(int j = 0; j < buttonSize; j++)
            {
                String actionName    = null;
                boolean enabled      = false;
                String buttonName    = null;
                String labelTag      = null;
                String iconName      = null;
                String keyName       = null;
                String parameterName = null;
                String listenerName  = null;
                ImageIcon icon       = null;

                // Account for the "More key".
                if (indexSize > 1 && j == (buttonSize - 1))
                {
                    actionName                  = ACTION_COMMAND_MORE;
                    enabled                     = true;
                    buttonName                  = "More >>>";
                    labelTag                    = ACTION_COMMAND_MORE;
                    keyName                     = "F9";
                }
                else
                {
                    // If there is a valid button spec left
                    if (buttonIndex < buttonSpecs.length)
                    {
                        // Get the data
                        ButtonSpec buttonSpec   = buttonSpecs[buttonIndex];
                        actionName              = buttonSpec.getActionName();
                        enabled                 = buttonSpec.getEnabled();
                        buttonName              = buttonSpec.getLabel();
                        labelTag                = buttonSpec.getLabelTag();
                        iconName                = buttonSpec.getIconName();
                        keyName                 = buttonSpec.getKeyName();
                        parameterName           = buttonSpec.getParameterName();
                        listenerName            = buttonSpec.getActionListenerName();
                        buttonIndex++;

                        // Check for icon
                        if(buttonSpec.isIcon())
                        {
                            Image image = UIUtilities.getImage(iconName, this);
                            icon = new ImageIcon(image,"actionName");
                        }
                    }
                    else
                    {
                        // otherwise build a dummy action.
                        actionName              = "NullButton";
                        enabled                 = false;
                        keyName                 = "";
                    }
                }

                // Create the key event
                int keyEvent = 0;
                if (keyName != null && keyName.length() > 0)
                {
                    StringBuilder keyNameStr = new StringBuilder("VK_");
                    keyNameStr.append(keyName);
                    keyEvent = KeyTable.getKeyEvent(keyNameStr.toString());
                }

                listener = createButtonListener(actionName, listenerName);
                buttonName = retrieveText(labelTag, buttonName);

                actions[i][j] = new UIAction(actionName,
                                             keyName,
                                             buttonName,
                                             labelTag,
                                             icon,
                                             enabled,
                                             keyEvent,
                                             listener);
                listener = null;
                actions[i][j].setButtonNumber((i*buttonSize)+j);
                actions[i][j].setParameterName(parameterName);
            } // end looping of buttons
        } // end looping of bars

        return actions;
    }

    /**
     * Create the button action listener. Defaults to returning a
     * {@link DefaultMailAction} if <code>listenerName</code> is null. If <code>
     * actionName</code> is null, then a {@link MoreAction} is returned.
     * 
     * @param actionName an {@link ActionEvent} command name. May be null.
     * @param listenerName a class name of a specific {@link ActionListener}. May be null.
     * @return
     */
    protected ActionListener createButtonListener(String actionName, String listenerName)
    {
        ActionListener listener = null;
        if (ACTION_COMMAND_MORE.equals(actionName))
        {
            listener = moreAction;
        }
        else if (listenerName != null)
        {
            try
            {
                Class<?> actClass = Class.forName(listenerName);
                listener = (ActionListener)actClass.newInstance();
            }
            catch(Exception e)
            {
                logger.error("Failed to Create Default Action:" + e);
            }
        }
        return listener;
    }

    /**
     *  Extracts component text from the properties object.
     */
    @Override
    protected void updatePropertyFields()
    {
        int indexSize = actions.length;
        int buttonSize = actions[0].length;
        //loop through the bars
        for(int i = 0; i < indexSize; i++)
        {
            //loop through the buttons
            for(int j = 0; j < buttonSize; j++)
            {
                if (!Util.isEmpty(actions[i][j].getButtonNameTag()))
                {
                    actions[i][j].setButtonName
                      (retrieveText(actions[i][j].getButtonNameTag(),
                                    actions[i][j].getButtonName()));
                }
            }
        }
    }

    /**
     * Associates a JButton with an Action via a PropertyChangeListener. This
     * triplet is stored in a hashtable with the button name as the key. The
     * action is added to the appropriate place in the array of UIActions. If
     * there is already a UIAction with the same name as a, it is replaced, in
     * location. If not, a is simply added to the array.
     * 
     * @param barIndex the index of the current bar
     * @param actionIndex the index of the current action
     * @param action the Action to associate
     */
    protected void addUIAction(int barIndex, int actionIndex, UIAction action)
    {
        String label = (String)action.getValue(UIAction.SHORT_DESCRIPTION);
        Icon icon = (Icon)action.getValue(UIAction.SMALL_ICON);

        // create the button
        EYSButton button = uiFactory.createEYSButton(label, icon, buttonPrefix, false);

        // this prevents the button from getting focus, a problem with
        // the clear action on the sale screen
        button.setRequestFocusEnabled(false);

        button.setEnabled(action.isEnabled());
        button.addActionListener(action);

        // all new buttons are created with visible set to false
        // the first button bar is made visible in the constructor
        button.setVisible(false);

        String keyName = (String)action.getValue(UIAction.KEYSTROKE);

        if(keyName != null && !keyName.equals(""))
        {
            StringBuilder keyNameBuilder = new StringBuilder();
            keyName = keyName.toUpperCase();

            if(keyName.equals("ESC"))
            {
                keyName = "ESCAPE";
            }
            else if (keyName.startsWith("CTRL"))
            {
                keyName = keyName.replace('+', ' ');
                keyName = keyName.replace("CTRL", "");
                keyNameBuilder.append("control ");
            }

            keyNameBuilder.append("released ");
            keyNameBuilder.append(keyName);

            KeyStroke keyStroke = KeyStroke.getKeyStroke(keyNameBuilder.toString());

            button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, action.getActionName());
            button.getActionMap().put(action.getActionName(), action);
        }
        // set the listener
        PropertyChangeListener listener = new ActionChangedListener(button);
        action.addPropertyChangeListener(listener);

        addButton(button, actionIndex);

        // add the button to the button arrays
        buttons[barIndex][actionIndex] = button;
    }

    /**
     * Add the button to the bar at the specified index. Calls
     * {@link #adjustConstraints(int)} first.
     * 
     * @param button
     * @param index
     */
    protected void addButton(EYSButton button, int index)
    {
        adjustConstraints(index);
        add(button,constraints);
    }

    /**
     * Adjusts the constraints object before adding a button.
     * 
     * @param the index of the button being added
     */
    protected void adjustConstraints(int index)
    {
        if (orientation == VERTICAL)
        {
            constraints.gridx = 0;
            constraints.gridy = index;
        }
        else
        {
            constraints.gridx = index;
            constraints.gridy = 0;
            constraints.fill = GridBagConstraints.BOTH;
        }
        Insets i = UIManager.getInsets(buttonPrefix + "Insets");

        if (i != null)
        {
            constraints.insets = i;
        }
    }

    /**
     * Set the next button bar to be active. Increments {@link #currentButtonIndex}
     * by one and sets all those buttons as visible.
     */
    public void activateNextButtonBar()
    {
        // set visible false for current bar
        setButtonsVisible(currentButtonIndex, false);

        // update currentButtonIndex
        if (currentButtonIndex == buttonIndexSize - 1)
        {
            currentButtonIndex = 0;
        }
        else
        {
            currentButtonIndex++;
        }

        // set visible true for the updated index
        setButtonsVisible(currentButtonIndex, true);
    }

    /**
     * Set the button bar index back to zero and sets all the first bar's
     * buttons to visible.
     */
    public void resetTaskButtonBar()
    {
        if (currentButtonIndex != 0)
        {
            // set visible false for current bar
            setButtonsVisible(currentButtonIndex, false);

            // update currentButtonIndex
            currentButtonIndex = 0;

            // set visible true for the updated index
            setButtonsVisible(currentButtonIndex, true);
        }

        // check each action if it has been turn off by a parameter
        for (int i = buttons[currentButtonIndex].length - 1; i >= 0; i--)
        {
            String parameterName = actions[currentButtonIndex][i].getParameterName();
            if (parameterName != null)
            {
                boolean visible = UIUtilities.isParameterDisabled(parameterName);
                buttons[currentButtonIndex][i].setVisible(visible);
            }
        }
    }

    /**
     * Get background color
     * 
     * @return Color background
     */
    public Color getBarBackground()
    {
        return getBackground();
    }

    /**
     * Get foreground color
     * 
     * @return Color foreground
     */
    public Color getBarForeground()
    {
        return getForeground();
    }

    /**
     * Set background color
     * 
     * @param c Color background color
     */
    public void setBarBackground(Color c)
    {
        super.setBackground(c);

        for (int i = buttons.length - 1; i >= 0; i--)
        {
            for (int j = buttons[i].length - 1; j >= 0; j--)
            {
                buttons[i][j].setBackground(c);
            }
        }
    }

    /**
     * Set foreground color
     * 
     * @param c Color Foreground color
     */
    public void setBarForeground(Color c)
    {
        super.setForeground(c);

        for (int i = buttons.length - 1; i >= 0; i--)
        {
            for (int j = buttons[i].length - 1; j >= 0; j--)
            {
                buttons[i][j].setForeground(c);
            }
        }
    }

    /**
     * Set the bar of buttons at the specified index to visible.
     * 
     * @param barIndex
     * @param visible
     */
    protected void setButtonsVisible(int barIndex, boolean visible)
    {
        for (int i = buttons[barIndex].length - 1; i >= 0; i--)
        {
            buttons[barIndex][i].setVisible(visible);
        }
    }

    /**
     * Returns true if this bean is reusable for different {@link #buttonModel}.
     * This default implementation returns true if the {@link #getBeanSpecName()}
     * returns "LocalNavigationPanelSpec".
     * 
     * @return
     */
    protected boolean isReusable()
    {
        return "LocalNavigationPanelSpec".equals(getBeanSpecName());
    }

    /**
     * Set all the current buttons to enabled=false and text="".
     */
    protected void setButtonsDisabled()
    {
        if (buttons != null)
        {
            for (int i = buttons[currentButtonIndex].length - 1; i >= 0; i--)
            {
                buttons[currentButtonIndex][i].setEnabled(false);
                buttons[currentButtonIndex][i].setText("");
            }
        }
    }

    /**
     * Creates a button bar from UIActions. The arrays are looped through in
     * ascending order to ensure subclasses can add buttons correctly.
     * 
     * @param actions UIAction[][] Actions to create button from
     */
    protected void setUIActions(UIAction[][] actions)
    {
        UIAction a = null;

        for (int i = 0; i < actions.length; i++)
        {
            for (int j = 0; j < actions[i].length; j++)
            {
                a = actions[i][j];
                addUIAction(i, j, a);
            }
        }
    }

    /**
     * Set the properties for a button
     * 
     * @param actionName String Name of the action
     * @param action UIAction Action to set
     * @exception ActionNotFoundException if the action is not in the button bar
     */
    public void setUIAction(String actionName, UIAction action)
        throws ActionNotFoundException
    {
        for (int i = actions.length - 1; i >= 0; i--)
        {
            for (int j = actions[i].length - 1; j >= 0; j--)
            {
                if (actions[i][j].getButtonName().equals(actionName))
                {
                    actions[i][j] = action;
                    return ;
                }
            }
        }
        throw new ActionNotFoundException("UIAction with name \"" + actionName + "\" not found");
    }

    /**
     * Retrieves one of the actions in the button bar by name.
     * 
     * @param actionName the name of the action to retrieve
     * @return the desired action
     * @exception ActionNotFoundException if the action is not in the button bar
     */
    public UIAction getUIAction(String actionName)
        throws ActionNotFoundException
    {
        // loop through the bars
        for (int i = actions.length - 1; i >= 0; i--)
        {
            // loop through the actions
            for (int j = actions[i].length - 1; j >= 0; j--)
            {
                UIAction action = actions[i][j];

                // return the action if found
                if (action.getActionName().equals(actionName))
                {
                    return action;
                }
            }
        }
        throw new ActionNotFoundException(actionName + " UIAction not found");
    }

    /**
     * Sets the buttons with the action names listed in the parameter to
     * disabled. This can be called from the BeanSpec by setting a BEANPROPERTY
     * in the XML.
     * 
     * @param actionNames a comma delimited list of action names.
     */
    public void setButtonStates(String actions)
    {
        String[] actionsAndStates =
            UIUtilities.parseDelimitedList(actions, ACTION_NAME_DELIMITER);

        applyEnableProperty(actionsAndStates);
    }

    /**
     * This method set the buttons to the requested state. Each element in the
     * array should look line this, "Next[true]" or "Cancel[false]".
     * 
     * @param actionNames an array of action names.
     */
    protected void applyEnableProperty(String[] actions)
    {
        // Clear the button states from map
        buttonStates.clear();
        
        for(int i = actions.length - 1; i >= 0; i--)
        {
            try
            {
                // Get the star and end of the action and the enable value
                int startName  = 0;
                int endName    = actions[i].indexOf("[");
                int startBool  = endName + 1;
                int endBool    = actions[i].indexOf("]");

                // Get the name, the string value and boolean enable value
                String name    = actions[i].substring(startName, endName);
                String bool    = actions[i].substring(startBool, endBool);


                // Get the action and set the enable value
                UIAction ua    = getUIAction(name);
                ua.setEnabled(UIUtilities.getBooleanValue(bool));
                buttonStates.put(name, UIUtilities.getBooleanValue(bool));
            }
            catch (ActionNotFoundException e)
            {
                logger.warn("Failed to Find Action: " + actions[i]);
            }
        }
    }

    /**
     * Retrieves the Team Connection revision number.
     * 
     * @return String representation of revision number
     */
    public String getRevisionNumber()
    {
        return(Util.parseRevisionNumber(revisionNumber));
    }

    /* (non-Javadoc)
     * @see java.awt.Component#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder strResult = new StringBuilder("Class: NavigationButtonBean (Revision ");
        strResult.append(getRevisionNumber());
        strResult.append(") @");
        strResult.append(hashCode());
        strResult.append(" beanSpecName=");
        strResult.append(getBeanSpecName());
        return strResult.toString();
    }

    /**
     * Disables the buttons if the response field is empty. Enables the buttons
     * when the response field is not empty.
     * 
     * @param evt the document event
     * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
     */
    public void changedUpdate(DocumentEvent evt)
    {
        int len = evt.getDocument().getLength();

        // Enable buttons
        if (len > 0)
        {
            if (savedStates != null)
            {
                for (int i = actions.length - 1; i >= 0; i--)
                {
                    for (int j = actions[i].length - 1; j >= 0; j--)
                    {
                        if (savedStates[i][j])
                        {
                            actions[i][j].setEnabled(true);
                        }
                    }
                }
            }
        }
        else // Disable buttons
        {
            savedStates = new boolean[actions.length][];

            for (int i = actions.length - 1; i >= 0; i--)
            {
                savedStates[i] = new boolean[actions[i].length];

                for (int j = actions[i].length - 1; j >= 0; j--)
                {
                    savedStates[i][j] = actions[i][j].isEnabled();

                    if (!actions[i][j].getActionName().equals(ACTION_COMMAND_MORE))
                    {
                        actions[i][j].setEnabled(false);
                    }
                }
            }
        }
    }

    /**
     * Calls {@link #changedUpdate(DocumentEvent)}
     * 
     * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
     */
    public void insertUpdate(DocumentEvent evt)
    {
        changedUpdate(evt);
    }

    /**
     * Calls {@link #changedUpdate(DocumentEvent)}
     * 
     * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
     */
    public void removeUpdate(DocumentEvent evt)
    {
        changedUpdate(evt);
    }

    // -------------------------------------------------------------------------
    /**
     * Updates a button when properties are changed. Properties supported are
     * "enabled", {@link UIAction#KEYSTROKE}, {@link UIAction#SHORT_DESCRIPTION},
     * {@link UIAction.SMALL_ICON}.
     */
    protected class ActionChangedListener implements PropertyChangeListener
    {
        JButton button;
        KeyStroke stroke        = null;
        boolean strokeNotSet    = true;

        /**
         * Constructor
         * @param b
         */
        ActionChangedListener(JButton b)
        {
            this.button = b;
        }

        /* (non-Javadoc)
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         */
        public void propertyChange(PropertyChangeEvent e)
        {
            String propertyName = e.getPropertyName();
            if (propertyName.equals("enabled"))
            {
                Boolean enabledState = (Boolean)e.getNewValue();
                button.setEnabled(enabledState);
            }
            else if (propertyName.equals(UIAction.KEYSTROKE))
            {
                String oldKey = (String) e.getOldValue();
                String newKey = (String) e.getNewValue();

                if(oldKey != null)
                {
                    KeyStroke oldPress =
                        KeyStroke.getKeyStroke("pressed " + oldKey.toUpperCase());

                    KeyStroke oldRel =
                        KeyStroke.getKeyStroke("released " + oldKey.toUpperCase());

                    button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(oldPress);
                    button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(oldRel);
                }
                if(newKey != null)
                {
                    KeyStroke newPress =
                        KeyStroke.getKeyStroke("pressed " + newKey.toUpperCase());

                    KeyStroke newRel =
                        KeyStroke.getKeyStroke("released " + newKey.toUpperCase());

                    button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(newPress, "pressed");
                    button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(newRel, "released");
                }
            }
            else if (propertyName.equals(UIAction.SHORT_DESCRIPTION))
            {
                String text = (String) e.getNewValue();
                button.setText(text);
            }
            else if (propertyName.equals(UIAction.SMALL_ICON))
            {
                Icon icon = (Icon) e.getNewValue();
                button.setIcon(icon);
            }
        }
    }

    //---------------------------------------------------------------------
    /**
     * An action that calls {@link NavigationButtonBean#activateNextButtonBar()}.
     */
    public class MoreAction extends AbstractAction implements Action
    {
        private static final long serialVersionUID = 4762814008093386181L;

        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent evt)
        {
            activateNextButtonBar();
        }
    }

    /**
     * The main function acts as a test driver.
     * 
     * @param args - command line parameters
     */
    public static void main(String[] args)
    {
        UIUtilities.setUpTest();

        ActionListener l=new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                System.out.println(evt.toString());
            }
        };
        // Make a 4 button bar
        UIAction[][] action4 = new UIAction[1][];
        action4[0]=new UIAction[4];
        for (int i = 0; i < 4; i++)
        {
            action4[0][i] =
                new UIAction("name"+i, "F"+i, "Label "+i, "tag"+i, null, i%2 == 0,
                                         KeyEvent.VK_0+i, l);
        }
        action4[0][1].setEnabled(true);
        action4[0][3].setEnabled(true);

        // Use the button bar of the default constructor
        NavigationButtonBean bbar1 = new NavigationButtonBean(action4);

        UIUtilities.doBeanTest(bbar1);
    }
}
