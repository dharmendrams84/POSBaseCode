/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/PrintPreviewBean.java /rgbustores_13.4x_generic_branch/1 2011/05/11 16:05:18 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *
 * ===========================================================================
 */

package oracle.retail.stores.pos.ui.beans;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import oracle.retail.stores.foundation.manager.gui.UIModelIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.ui.UIUtilities;

public class PrintPreviewBean extends BaseBeanAdapter {

    /**
        revision number for this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /** the bean name **/
    protected String beanName = "PrintPreviewBean";

    /** The bean model **/
    protected PrintPreviewBeanModel beanModel = new PrintPreviewBeanModel();

    /** The main panel **/
    protected JScrollPane printPreviewPane = null;

    /** The area to display the text - Preview **/
    protected JTextArea previewTextArea = null;

    /** The scroll listener **/
    protected ScrollListener listener = new ScrollListener();

    //---------------------------------------------------------------------
    /**
       Default class Constructor and initializes its components.
     **/
    //---------------------------------------------------------------------
    public PrintPreviewBean()
    {
        super();
        initialize();
    }
    //---------------------------------------------------------------------
    /**
       Initialize the class and its screen members.
     **/
    //---------------------------------------------------------------------
    protected void initialize()
    {
        // Intialize the panel
        setName("PrintPreviewBean");
        uiFactory.configureUIComponent(this, UI_PREFIX);

        initComponents();
        initLayout();
    }

    //---------------------------------------------------------------------
    /**
       Initializes the components.
     **/
    //---------------------------------------------------------------------
    protected void initComponents()
    {
        printPreviewPane = new JScrollPane();
        uiFactory.configureUIComponent(printPreviewPane, "PrintPreviewPane");

        previewTextArea = createTextArea();
    }

    //---------------------------------------------------------------------
    /**
       Initializes the layout and lays out the components.
     **/
    //---------------------------------------------------------------------
    protected void initLayout()
    {
        setLayout(new BorderLayout(1,1));

        printPreviewPane.setViewportView(previewTextArea);
        add(printPreviewPane, BorderLayout.CENTER);
    }

    /**
     *  Creates the text area to be display
     * @return
     */
    protected JTextArea createTextArea()
    {
        JTextArea area = new JTextArea();
        uiFactory.configureUIComponent(area, "PrintPreviewArea");
        area.setBorder(UIManager.getBorder("PrintPreviewArea.border"));
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEnabled(true);
        return area;
    }

    //---------------------------------------------------------------------
    /**
       Gets the POSBeanModel associated with this bean.
       @return the POSBaseBeanModel associated with this bean
   **/
    //---------------------------------------------------------------------
    public POSBaseBeanModel getPOSBaseBeanModel()
    {
        return beanModel;
    }
    //---------------------------------------------------------------------
    /**
       Activate this screen and listeners.
    **/
    //---------------------------------------------------------------------
    public void activate()
    {
        super.activate();
        updateBean();
        activateListeners();
    }
    //---------------------------------------------------------------------
    /**
       Deactivate this screen and listeners.
    **/
    //---------------------------------------------------------------------
    public void deactivate()
    {
        deactivateListeners();
    }
    //---------------------------------------------------------------------
    /**
       Activate listeners
       NOTE: These keys are handled internally, i.e., this
       bean will not register with anyone else.
     **/
    //---------------------------------------------------------------------
    protected void activateListeners()
    {
        KeyStroke k = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        printPreviewPane.registerKeyboardAction(listener, "DOWN", k, JComponent.WHEN_IN_FOCUSED_WINDOW);

        k = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
        printPreviewPane.registerKeyboardAction(listener, "UP", k, JComponent.WHEN_IN_FOCUSED_WINDOW);

        k = KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0);
        printPreviewPane.registerKeyboardAction(listener, "PAGE_DOWN", k, JComponent.WHEN_IN_FOCUSED_WINDOW);

        k = KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0);
        printPreviewPane.registerKeyboardAction(listener, "PAGE_UP", k, JComponent.WHEN_IN_FOCUSED_WINDOW);

        k = KeyStroke.getKeyStroke(KeyEvent.VK_END, 0);
        printPreviewPane.registerKeyboardAction(listener, "END", k, JComponent.WHEN_IN_FOCUSED_WINDOW);

        k = KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0);
        printPreviewPane.registerKeyboardAction(listener, "HOME", k, JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        printPreviewPane.addFocusListener(this);
    }
    //---------------------------------------------------------------------
    /**
       Deactivate key listeners
     **/
    //---------------------------------------------------------------------
    protected void deactivateListeners()
    {
        printPreviewPane.resetKeyboardActions();
        printPreviewPane.removeFocusListener(this);
    }
    //---------------------------------------------------------------------
    /**
       Listener class that handles the keystrokes for scrolling
     **/
    //---------------------------------------------------------------------
    protected class ScrollListener implements ActionListener
    {

        /**
         *  (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            JScrollBar bar = printPreviewPane.getVerticalScrollBar();
            if (e.getActionCommand().equals("DOWN"))
            {
                bar.setValue(bar.getValue() +
                             bar.getUnitIncrement(-1));
            }
            else if (e.getActionCommand().equals("UP"))
            {
                bar.setValue(bar.getValue() -
                             bar.getUnitIncrement(1));
            }
            else if (e.getActionCommand().equals("PAGE_DOWN"))
            {
                bar.setValue(bar.getValue() +
                             bar.getBlockIncrement(-1));
            }
            else if (e.getActionCommand().equals("PAGE_UP"))
            {
                bar.setValue(bar.getValue() -
                             bar.getBlockIncrement(1));
            }
            else if (e.getActionCommand().equals("END"))
            {
                bar.setValue(bar.getMaximum());
            }
            else if (e.getActionCommand().equals("HOME"))
            {
                bar.setValue(bar.getMinimum());
            }
        }
    }
    //---------------------------------------------------------------------
    /**
       Set the text to be displayed
       @param value
     **/
    //---------------------------------------------------------------------
    public void setPreviewText(String value)
    {
        if (value == null)
            value = new String(" ");

        previewTextArea.setText(value);
    }
    //---------------------------------------------------------------------
    /**
       Sets the current model to the updated model..
       @param model UIModelIfc A DisplayTextBeanModel
       @see oracle.retail.stores.pos.ui.beans.DisplayTextBeanModel
    **/
    //---------------------------------------------------------------------
    public void setModel(UIModelIfc model)
    {
        if(model==null)
        {
            throw new NullPointerException("Attempt to set DisplayTextBean model to null.");
        }
        else
        {
            if (model instanceof PrintPreviewBeanModel)
            {
                beanModel = (PrintPreviewBeanModel)model;
                updateBean();
            }
        }
    }
    //---------------------------------------------------------------------
    /**
       Update the bean with fresh data
    **/
    //---------------------------------------------------------------------
    protected void updateBean()
    {
        setPreviewText(beanModel.getPrintPreviewText());
        previewTextArea.setCaretPosition(0);
    }

    //---------------------------------------------------------------------
    /**
       Set the focus for the screen.
       @param aFlag
    **/
    //---------------------------------------------------------------------
    public void setVisible(boolean aFlag)
    {
        super.setVisible(aFlag);

        if (aFlag)
        {
            previewTextArea.setCaretPosition(0);
            setCurrentFocus(printPreviewPane);
        }
    }
    //---------------------------------------------------------------------
    /**
       Returns default display string.
       <P>
       @return String representation of object
    **/
    //---------------------------------------------------------------------
    public String toString()
    {
        String strResult = new String("Class: PrintPreviewBean (Revision " +
                                      getRevisionNumber() + ") @" +
                                      hashCode());
        return(strResult);
    }
    //----------------------------------------------------------------------
    /**
       Returns the revision number of the class.
       <P>
       @return String representation of revision number
    **/
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return(Util.parseRevisionNumber(revisionNumber));
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        UIUtilities.setUpTest();
        PrintPreviewBean bean = new PrintPreviewBean();

        StringBuffer text = new StringBuffer("This is some text. ");
        text.append("And this is some more text. There is still more text. ");
        text.append("Even more text.");

        bean.setPreviewText(text.toString());

        UIUtilities.doBeanTest(bean);
    }
}
