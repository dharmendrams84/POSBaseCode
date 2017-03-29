/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/plaf/eys/EYSButtonUI.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:07:01 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   12/17/09 - add support for toggle buttons
 *    cgreene   03/11/09 - fix painting in update method to use method
 *                         isRoundedBorder in EYSBorderFactory and not do any
 *                         painting unless necessary
 *
 * ===========================================================================
 * $Log:
 *   3    360Commerce 1.2         3/31/2005 4:28:08 PM   Robert Pearse   
 *   2    360Commerce 1.1         3/10/2005 10:21:33 AM  Robert Pearse   
 *   1    360Commerce 1.0         2/11/2005 12:10:59 PM  Robert Pearse   
 *
 *  Revision 1.3  2004/02/12 16:52:14  mcs
 *  Forcing head revision
 *
 *  Revision 1.2  2004/02/11 21:52:29  rhafernik
 *  @scr 0 Log4J conversion and code cleanup
 *
 *  Revision 1.1.1.1  2004/02/11 01:04:23  cschellenger
 *  updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   Sep 08 2003 17:30:58   DCobb
 * Migration to jvm 1.4.1
 * Resolution for 3361: New Feature:  JVM 1.4.1_03 (Windows) Migration
 * 
 *    Rev 1.0   Aug 29 2003 16:13:22   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 14:45:56   msg
 * Initial revision.
 * 
 *    Rev 1.2   16 Apr 2002 16:42:56   baa
 * paint disable background on square btns
 * Resolution for POS SCR-1590: PLAF code does not meet the coding standards
 *
 *    Rev 1.1   10 Apr 2002 13:59:42   baa
 * make code compliant with coding guidelines
 * Resolution for POS SCR-1590: PLAF code does not meet the coding standards
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.plaf.eys;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

import oracle.retail.stores.foundation.utility.Util;

/**
 * Implements a button ui that draws two lines of text on a button if the button
 * text contains a newline character ('\n').
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public class EYSButtonUI extends BasicButtonUI
{
    /** revision number supplied by PVCS **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    // static objects that all buttons can share
    // this eliminates the need to reinstantiate things
    static Rectangle viewRect = new Rectangle(0,0,0,0);
    static Rectangle iconRect = new Rectangle(0,0,0,0);
    static Rectangle textRect = new Rectangle(0,0,0,0);

    // The top text line on a button
    static String topLine = null;

    // The bottom text line on a button
    static String botLine = null;

    // The font metrics
    static FontMetrics fm = null;

    // Shared UI object
    private final static ButtonUI buttonUI = new EYSButtonUI();

    /**
     *  Creates button UI object
     *  @param c the button as a JComponent
     *  @returns the button UI
     */
    public static ComponentUI createUI(JComponent c)
    {
        return buttonUI;
    }

    /**
     * Overrides the paint method to check for a newline character.
     * 
     * @param g the button's graphics object
     * @param c the button as a JComponent
     */
    @Override
    public void paint(Graphics g, JComponent c)
    {
        AbstractButton b = (AbstractButton)c;
        ButtonModel model = b.getModel();
        
        // check for newline charater
        // and do a normal paint if it's not there
        if(!checkText(b.getText()))
        {
            super.paint(g, c);
        }
        else
        {
            // get the font metrics
            fm = g.getFontMetrics();

            // get the component insets
            Insets insets = c.getInsets();

            // set up the view rectangle
            viewRect.x = insets.left;
            viewRect.y = insets.top;
            viewRect.width = b.getWidth() - (insets.right + viewRect.x);
            viewRect.height = b.getHeight() - (insets.bottom + viewRect.y);

            // set the icon and text rectangle to 0 values
            textRect.x = textRect.y = textRect.width = textRect.height = 0;
            iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;

            // make sure the graphics font matches the button's
            g.setFont(c.getFont());

            // assume bottom line is the largest string
            String temp = botLine;

            // check for longer top line
            if(topLine.length() > botLine.length())
            {
                temp = topLine;
            }

            // layout the text and icon
            SwingUtilities.layoutCompoundLabel(c, fm, temp, b.getIcon(),
                          b.getVerticalAlignment(), b.getHorizontalAlignment(),
                          b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
                          viewRect, iconRect, textRect,
                          b.getText() == null ? 0 : defaultTextIconGap);
            repositionTextRect(b);
            textRect.height = textRect.height * 2;

            if(textRect.height > viewRect.height)
            {
                resizeButton(b);
            }
            clearTextShiftOffset();

            // perform UI specific press action, e.g. Windows L&F shifts text
            if (model.isArmed() && model.isPressed())
            {
                paintButtonPressed(g,b);
            }

            // Paint the Icon
            if(b.getIcon() != null)
            {
                paintIcon(g,c,iconRect);
            }


            if (b.isFocusPainted() && b.getFocusTraversalKeysEnabled())
            {
                // paint UI specific focus
                paintFocus(g,b,viewRect,textRect,iconRect);
            }

            paintTwoLineText(g, b);
        }
    }

    /**
     * Repaints button. Overridden to allow for rounded buttons to paint their
     * background of the entire clip area.
     * 
     * @param g the button's graphics object
     * @param c the button as a JComponent
     */
	@Override
	public void update(Graphics g, JComponent c)
    {
        Border b = c.getBorder();

        if (EYSBorderFactory.isRoundedBorder(b))
        {
            ButtonModel model = ((AbstractButton)c).getModel();
            if(model.isEnabled())
            {
                  ((EYSBorderFactory.RoundedBorder)b).paintBackground(g, c, c.getBackground());
            }
            else
            {
                ((EYSBorderFactory.RoundedBorder)b).paintDisabledBackground(g, c, c.getBackground());
            }
            paint(g, c);
        }
        else
        {
           super.update(g, c);
        }
    }

    /**
     * Adjusts the rendering hints for a graphics object so that area fills and
     * line drawing uses antialiasing, but text drawing does not.
     * 
     * @param g the graphics object
     */
    protected void setRendering(Graphics g)
    {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                         RenderingHints.VALUE_ANTIALIAS_ON);

        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                         RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
    }

    /**
     *  Paints button text in two lines
     *  @param g the button's graphics object
     *  @param b the button's  object
     */
    protected void paintTwoLineText(Graphics g, AbstractButton b)
    {
        ButtonModel model = b.getModel();

        // set the color based on enabled state
        if(model.isEnabled())
        {
            g.setColor(b.getForeground());
        }
        else
        {
            g.setColor(b.getForeground().darker());
        }
        // draw the lines
        BasicGraphicsUtils.drawString(g, topLine, model.getMnemonic(),
            getStartPoint(topLine, b), textRect.y + fm.getAscent());

        BasicGraphicsUtils.drawString(g, botLine, model.getMnemonic(),
            getStartPoint(botLine, b), textRect.y + fm.getHeight()
            + fm.getAscent());
    }

    /**
     * Checks for a carriage return on the text.
     * 
     * @param value the text to be check
     * @returns boolean result True if a carriage return was found false
     *          otherwise.
     */
    protected boolean checkText(String value)
    {
        int pos = value.indexOf('\n');
        boolean result = false;

        if(pos != -1)
        {
            topLine = value.substring(0, pos);
            botLine = value.substring(pos+1, value.length());
            result = true;
        }
        return result;
    }

    /**
     * Resizes a button component.
     * 
     * @param b the button's object
     */
    protected void resizeButton(AbstractButton b)
    {
        int newHeight = 0;

        if(b.getHorizontalTextPosition() == SwingConstants.CENTER)
        {
            newHeight =
                iconRect.height + defaultTextIconGap + textRect.height;
        }
        else
        {
            newHeight = textRect.height;
        }
        // get the component insets
        Insets insets = b.getInsets();

        Dimension d =
            new Dimension(b.getWidth(), newHeight + insets.top + insets.bottom);

        b.setSize(d);
    }

    /**
     * Repositions the text within a button component.
     * 
     * @param b the button's object
     */
    protected void repositionTextRect(AbstractButton b)
    {
        if(b.getVerticalAlignment() == SwingConstants.CENTER)
        {
            textRect.y = textRect.y - (textRect.height / 2) - 1;
        }
        else if(b.getVerticalAlignment() == SwingConstants.BOTTOM)
        {
            textRect.y = textRect.y - textRect.height;
        }
    }

    /**
     * Retrieves x coordinate of text in button
     * 
     * @param text the text
     * @param b the button's object
     */
    protected int getStartPoint(String text, AbstractButton b)
    {
        int result = textRect.x;

        int width = fm.stringWidth(text);
        int dif = textRect.width - width;

        if(dif > 0)
        {
            if(b.getHorizontalAlignment() == SwingConstants.CENTER)
            {
                result = textRect.x + (dif/2);
            }
            else if(b.getHorizontalAlignment() == SwingConstants.RIGHT)
            {
                result = textRect.x + dif;
            }
        }
        return result;
    }

    /**
     * Paints a darker background for the button object
     * 
     * @param g the button's graphics object
     * @param b the button's object
     */
    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b)
    {

        Border border = b.getBorder();
        Color color = b.getBackground().darker();

        if(border == null || !(border instanceof EYSBorderFactory.RoundedBorder))
        {
            Dimension size = b.getSize();
            g.setColor(color);
            g.fillRect(0, 0, size.width, size.height);
        }
        else
        {
            ((EYSBorderFactory.RoundedBorder)border).paintBackground(g, b, color);
        }
    }

    /**
     * Paints a box around the text of button with focus
     * 
     * @param g the button's graphics object
     * @param b the button's object
     * @param viewRect the objects Rectagle area
     * @param textRect the objects text area
     * @param textRect the objects icon area
     */
    @Override
    protected void paintFocus(Graphics g, AbstractButton b,
                              Rectangle viewRect, Rectangle textRect, Rectangle iconRect)
    {
          Color org_color = g.getColor();
          Color focus_color = Color.lightGray;

          g.setColor(focus_color);

          // draw a box around the text of the button that has focus
          g.drawRect(viewRect.x+5, textRect.y-1, viewRect.width-10, textRect.height+1);

          g.setColor(org_color);


    }

    /**
     * Retrieves the PVCS revision number.
     * 
     * @return String representation of revision number
     */
    public String getRevisionNumber()
    {
        // return string
        return(Util.parseRevisionNumber(revisionNumber));
    }
}
