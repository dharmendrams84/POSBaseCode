/* ===========================================================================
* Copyright (c) 2008, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/ImageGridBean.java /rgbustores_13.4x_generic_branch/3 2012/02/27 18:16:36 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    asinto 02/27/12 - Fixed handling of mouse and focus events, switched to
 *                      single click instead of double click for scan sheet
 *                      actions.
 *    vtemke 10/03/11 - Fixed image resize issue
 *    jkoppo 04/01/11 - Removed text 'Empty' for empty items
 *    jkoppo 03/09/11 - I18N changes.
 *    jkoppo 03/04/11 - Modified to support return to original page, images
 *                      from url and other improvements.
 *    jkoppo 03/02/11 - New ui bean for scan sheet screen
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import oracle.retail.stores.domain.stock.ScanSheetComponent;
import oracle.retail.stores.foundation.manager.gui.UISubsystem;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.pos.ui.behavior.EnableButtonListener;
import oracle.retail.stores.pos.ui.behavior.GlobalButtonListener;
import oracle.retail.stores.pos.ui.behavior.LocalButtonListener;

public class ImageGridBean extends CycleRootPanel implements ItemListener
{
    class CustomImageIcon extends ImageIcon
    {
        private static final long serialVersionUID = 5192800383637703184L;

        public CustomImageIcon(byte[] image)
        {
            super(image);
        }

        public CustomImageIcon(Image image)
        {
            super(image);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y)
        {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            int imageWidth = c.getWidth() - 50;
            int imageHeight = c.getHeight() - 50;
            int xOrigin = (c.getWidth() - imageWidth)/2;
            int yOrigin = (c.getHeight() - imageHeight)/2;
            ((Graphics2D) g).drawImage(getImage(), xOrigin, yOrigin, imageWidth, imageHeight, c);
            ((Graphics2D) g).dispose();
        }
    };

    class CustomLabel extends JLabel
    {
        private static final long serialVersionUID = 1350888305300597898L;
        String customText;

        public CustomLabel(String text)
        {
            super();
            this.customText = text;
        }

        public void setCustomText(String text)
        {
            this.customText = text;
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            smoothenFonts(g);
            if (this.getIcon() != null)
            {
                ((Graphics2D) g).drawString(this.customText, ((this.getWidth() / 2) - (customText.length() * 3)), 20);
            }
            else
            {
                ((Graphics2D) g).drawString(this.customText, ((this.getWidth() / 2) - (customText.length() * 3)),
                        this.getHeight() / 2);
            }
        }

        public void smoothenFonts(Graphics g)
        {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
    };

    class ImageGridMouseListener implements MouseListener
    {
        private JLabel jlbl;
        private ScanSheetComponent ssComponent;
        
        boolean letterSent = false;

        public ImageGridMouseListener(ScanSheetComponent ssComponent, JLabel jlbl)
        {
            super();
            this.ssComponent = ssComponent;
            this.jlbl = jlbl;
        }

        public void mouseClicked(MouseEvent e)
        {
        }

        public void mousePressed(MouseEvent e)
        {
        }

        public void mouseReleased(MouseEvent e)
        {
            if(!letterSent && this.jlbl.hasFocus())
            {
                letterSent = true;
                UISubsystem ui = UISubsystem.getInstance();
                ui.mail(new Letter("AddItem"), false);
            }
            else
            {
                if (ssComponent.isCategory())
                {
                    igbm.setSelectedItemID(ssComponent.getCategoryID());
                    igbm.setSelectedItemaCategory(true);
                  
                }
                else
                {
                    igbm.setSelectedItemID(ssComponent.getItemID());
                    igbm.setSelectedItemaCategory(false);
                 
                }
                this.jlbl.requestFocusInWindow();
                if (globalButtonListener != null)
                {
                    globalButtonListener.enableButton("AddItem", true);
                }
            }
        }

        public void mouseEntered(MouseEvent e)
        {
            this.jlbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        public void mouseExited(MouseEvent e)
        {
            this.jlbl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    class ImageGridFocusListener implements FocusListener
    {
        JLabel jlbl;

        public ImageGridFocusListener(JLabel jlbl)
        {
            super();
            this.jlbl = jlbl;
        }

        public void focusGained(FocusEvent e)
        {
            this.jlbl.setBorder(new LineBorder(Color.BLUE, 2));
            if (globalButtonListener != null)
            {
                globalButtonListener.enableButton("AddItem", true);
            }
        }

        public void focusLost(FocusEvent e)
        {
            jlbl.setBorder(new LineBorder(Color.BLACK, 1));
            if (globalButtonListener != null)
            {
                globalButtonListener.enableButton("AddItem", false);
            }
        }
    }

    public ImageGridBean()
    {
        super();
    }

    /** serialVersionUID */
    private static final long serialVersionUID = -7772199341953908442L;
    JPanel scanSheet;
    JPanel pageComboBoxPane;
    JComboBox cb;
    protected EnableButtonListener globalButtonListener = null;
    protected EnableButtonListener localButtonListener = null;
    protected static final String NEXT_ACTION = "Next";
    protected static final String RETURN_ACTION = "Return";
    protected static final String FIRST_ACTION = "First";
    protected static final String LAST_ACTION = "Last";
    protected static final String PREVIOUS_ACTION = "Previous";
    ImageGridBeanModel igbm;

    /**
     * Configures the bean.
     */
    @Override
    public void configure()
    {
        uiFactory.configureUIComponent(this, UI_PREFIX);
        this.setLayout(new BorderLayout());
        scanSheet = new JPanel(new CardLayout());
        pageComboBoxPane = new JPanel();
        pageComboBoxPane.setLayout(new BoxLayout(pageComboBoxPane, BoxLayout.X_AXIS));
    }

    private void sortScItems()
    {
        if (igbm != null)
        {
            Collections.sort(igbm.getScanSheet().getScItemList());
        }
        for (String s : igbm.getScanSheet().getCategoryMap().keySet())
        {
            Collections.sort(igbm.getScanSheet().getCategoryMap().get(s));
        }
    }

    public JPanel prepareImagePane(int pageNo)
    {
        // Calculate start item index in the scan sheet item arraylist that
        // should be displayed in this page
        int startIndex = igbm.maxNumberOfItems * (pageNo - 1);
        // Calculate end item index in the scan sheet item arraylist that
        // should be displayed in this page
        int endIndex = (pageNo * igbm.maxNumberOfItems) - 1;
        ArrayList<ScanSheetComponent> scItems = igbm.getScanSheet().getScItemList();
        if (endIndex > (scItems.size() - 1))
        {
            endIndex = scItems.size() - 1;
        }
        JPanel page = null;
        // Calculate/ decide grid size
        int gridSize;
        int noOfItems = (endIndex - startIndex) + 1;
        if (noOfItems >= igbm.maxNumberOfItems)
        {
            gridSize = (int) Math.sqrt(igbm.maxNumberOfItems);
        }
        else
        {
            gridSize = (int) Math.ceil(Math.sqrt(noOfItems));
        }
        page = new JPanel(new GridLayout(gridSize, gridSize));
        for (int i = startIndex; i <= endIndex; i++)
        {
            ScanSheetComponent ssc = scItems.get(i);
            CustomLabel jlbl = new CustomLabel(ssc.getDesc());
            prepareGridItem(jlbl, true, ssc.isCategory() ? Color.BLUE : Color.BLACK);
            byte[] image = ssc.getImage();
            if (image == null || image.length == 0)
            {
                try
                {
                    BufferedImage bufImage = ImageIO.read(new URL(ssc.getImageLocation()));
                    jlbl.setIcon(new CustomImageIcon(bufImage));
                }
                catch (Exception e)
                {
                    logger.error("Unable to read the image from the specified location - " + e.getMessage());
                }
            }
            else
            {
                jlbl.setIcon(new CustomImageIcon(image));
            }
            jlbl.addFocusListener(new ImageGridFocusListener(jlbl));
            jlbl.addMouseListener(new ImageGridMouseListener(ssc, jlbl));
            page.add(jlbl);
        }
        for (int j = noOfItems; j < (gridSize * gridSize); j++)
        {
            CustomLabel jlbl = new CustomLabel("");
            prepareGridItem(jlbl, false, Color.RED);
            page.add(jlbl);
        }
        return page;
    }

    public void prepareGridItem(JLabel jlbl, boolean focus, Color color)
    {
        jlbl.setForeground(color);
        jlbl.setHorizontalAlignment(JLabel.CENTER);
        jlbl.setFocusable(focus);
        Font f = jlbl.getFont();
        jlbl.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
        jlbl.setBorder(new LineBorder(Color.BLACK, 1));
        jlbl.setRequestFocusEnabled(true);
    }

    public void itemStateChanged(ItemEvent e)
    {
        if (e.getStateChange() == ItemEvent.SELECTED)
        {
            CardLayout cl = (CardLayout) (scanSheet.getLayout());
            cl.show(scanSheet, (String) e.getItem());
            int pageNo = Integer.parseInt((String) e.getItem());
            igbm.setCurrentPageNo(pageNo);
            if (localButtonListener != null)
            {
                if (pageNo == 1)
                {
                    if (this.igbm.noOfPages > 1)
                    {
                        localButtonListener.enableButton("FirstPage", false);
                        localButtonListener.enableButton("NextPage", true);
                        localButtonListener.enableButton("LastPage", true);
                        localButtonListener.enableButton("PreviousPage", false);
                    }
                    else
                    {
                        localButtonListener.enableButton("FirstPage", false);
                        localButtonListener.enableButton("NextPage", false);
                        localButtonListener.enableButton("LastPage", false);
                        localButtonListener.enableButton("PreviousPage", false);
                    }
                }
                // If in last page
                else if (this.igbm.noOfPages != 1 && pageNo == this.igbm.noOfPages)
                {
                    localButtonListener.enableButton("FirstPage", true);
                    localButtonListener.enableButton("LastPage", false);
                    localButtonListener.enableButton("NextPage", false);
                    localButtonListener.enableButton("PreviousPage", true);
                }
                // If in between first and last page
                else
                {
                    localButtonListener.enableButton("FirstPage", true);
                    localButtonListener.enableButton("LastPage", true);
                    localButtonListener.enableButton("NextPage", true);
                    localButtonListener.enableButton("PreviousPage", true);
                }
            }
        }
    }

    @Override
    protected void updateBean()
    {
        if (beanModel instanceof ImageGridBeanModel)
        {
            igbm = (ImageGridBeanModel) beanModel;
            sortScItems();
            scanSheet.removeAll();
            ArrayList<JPanel> pages = new ArrayList<JPanel>();
            for (int i = 1; i <= igbm.noOfPages; i++)
            {
                pages.add(prepareImagePane(i));
            }
            String comboBoxItems[] = new String[pages.size()];
            for (int i = 0; i < pages.size(); i++)
            {
                String pageName = "" + (i + 1);
                scanSheet.add(pages.get(i), pageName);
                comboBoxItems[i] = pageName;
            }
            JLabel jlbl = new JLabel("Page");
            cb = new JComboBox(comboBoxItems);
            cb.setRequestFocusEnabled(true);
            cb.requestFocusInWindow();
            cb.addItemListener(this);
            cb.setSelectedIndex(igbm.getCurrentPageNo() - 1);
            cb.setEditable(false);
            cb.setBorder(new LineBorder(Color.BLACK, 1));
            cb.setPreferredSize(new Dimension(80, 25));
            cb.setMaximumSize(new Dimension(80, 25));
            JLabel categoryName = new JLabel(igbm.getCategoryDescription());
            Font f = categoryName.getFont();
            categoryName.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
            categoryName.setForeground(Color.BLUE);
            pageComboBoxPane.removeAll();
            pageComboBoxPane.add(categoryName);
            pageComboBoxPane.add(Box.createHorizontalGlue());
            pageComboBoxPane.add(jlbl);
            pageComboBoxPane.add(cb);
            pageComboBoxPane.add(Box.createHorizontalGlue());
            this.add(pageComboBoxPane, BorderLayout.PAGE_START);
            this.add(scanSheet, BorderLayout.CENTER);
        }
    }

    public void setVisible(boolean aFlag)
    {
        super.setVisible(aFlag);
        if (aFlag)
        { // make sure initial focus goes to the first field
            setCurrentFocus(cb);
        }
    }

    @Override
    public void updateModel()
    {
    }

    // ---------------------------------------------------------------------
    /**
     * Adds (actually sets) the enable button listener on the bean.
     * 
     * @param listener
     */
    // ---------------------------------------------------------------------
    public void addGlobalButtonListener(GlobalButtonListener listener)
    {
        globalButtonListener = listener;
    }

    @Override
    public void activate()
    {
        super.activate();
    }

    public void addLocalButtonListener(LocalButtonListener listener)
    {
        localButtonListener = listener;
        if (localButtonListener != null)
        {
            if (igbm.getCategoryID() != null)
            {
                localButtonListener.enableButton("Return", true);
            }
            else
            {
                localButtonListener.enableButton("Return", false);
            }
        }
    }

    public void removeLocalButtonListener(LocalButtonListener listener)
    {
        localButtonListener = null;
    }

    // ---------------------------------------------------------------------
    /**
     * Removes the enable button listener from the bean.
     * 
     * @param listener
     */
    // ---------------------------------------------------------------------
    public void removeGlobalButtonListener(GlobalButtonListener listener)
    {
        globalButtonListener = null;
    }
}
