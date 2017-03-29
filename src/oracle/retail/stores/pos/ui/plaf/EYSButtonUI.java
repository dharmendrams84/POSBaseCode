/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/plaf/EYSButtonUI.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:07:01 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:28:08 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:21:33 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:59 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:52:13  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:30  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:23  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   Sep 08 2003 17:30:54   DCobb
 * Migration to jvm 1.4.1
 * Resolution for 3361: New Feature:  JVM 1.4.1_03 (Windows) Migration
 * 
 *    Rev 1.0   Aug 29 2003 16:13:22   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 14:45:28   msg
 * Initial revision.
 * 
 *    Rev 1.1   10 Apr 2002 13:59:36   baa
 * make code compliant with coding guidelines
 * Resolution for POS SCR-1590: PLAF code does not meet the coding standards
 *
 *    Rev 1.0   Mar 18 2002 11:58:40   msg
 * Initial revision.
 *
 *    Rev 1.1   Dec 19 2001 11:07:00   vxs
 * Removed BasicHTML import statement because this class is not public in the interface and cannot be imported from outside.
 * Resolution for POS SCR-63: Susp, Rtrv, Susp an Exchg.  Disct changes.  Ret item has SA
 *
 *    Rev 1.0   Sep 21 2001 11:34:04   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:18:26   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.plaf;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

//------------------------------------------------------------------------------
/**
 *      Implements a button ui that draws two lines of text on a button
 *      if the button text contains a newline character ('\n').
 *
 *      NOTE: It is not really a safe assumption to extend the Metal ui,
 *      but without a specific EYS plaf, there is not much of a choice.
 *      @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 *      @deprecated as of release 5.1.0 replaced by @link oracle.retail.stores.pos.ui.plaf.eys.EYSButtonUI
 */
//------------------------------------------------------------------------------

public class EYSButtonUI extends BasicButtonUI
{
    /** revision number supplied by PVCS **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    // color object for disabled button text
    private static ColorUIResource disabledColor =  new ColorUIResource(153, 153, 153);

    // static objects that all buttons can share
    // this eliminates the need to reinstantiate things
    private static Rectangle viewRect = new Rectangle(0,0,0,0);
    private static Rectangle iconRect = new Rectangle(0,0,0,0);
    private static Rectangle textRect = new Rectangle(0,0,0,0);
    private static String topLine = null;
    private static String botLine = null;
    private static FontMetrics fm = null;

    //------------------------------------------------------------------------------
    /**
     *      Overrides the paint method to check for a newline character.
     *      @param g the button's graphics object
     *      @param c the button as a JComponent
     */
    //------------------------------------------------------------------------------
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
            String text = SwingUtilities.layoutCompoundLabel(
                c, fm, temp, b.getIcon(),
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

            paintTwoLineText(g, b);

            if (b.isFocusPainted() && b.getFocusTraversalKeysEnabled())
            {
               // paint UI specific focus
               paintFocus(g,b,viewRect,textRect,iconRect);
            }
        }
    }

//------------------------------------------------------------------------------
/**
 *
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
                        g.setColor(disabledColor);
                }
                // draw the lines
                BasicGraphicsUtils.drawString(g, topLine, model.getMnemonic(),
                        getStartPoint(topLine, b), textRect.y + fm.getAscent());

                BasicGraphicsUtils.drawString(g, botLine, model.getMnemonic(),
                        getStartPoint(botLine, b), textRect.y + fm.getHeight()
                        + fm.getAscent());
    }

//------------------------------------------------------------------------------
/**
 *
 */
        protected boolean checkText(String theText)
        {
                int pos = theText.indexOf('\n');
                boolean result = false;

                if(pos != -1)
                {
                    topLine = theText.substring(0, pos);
                        botLine = theText.substring(pos+1, theText.length());
                        result = true;
                }
                return result;
        }

//------------------------------------------------------------------------------
/**
 *
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

//------------------------------------------------------------------------------
/**
 *
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

//------------------------------------------------------------------------------
/**
 *
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

        protected void paintButtonPressed(Graphics g, AbstractButton b)
        {
        if (b.isContentAreaFilled())
        {
            Dimension size = b.getSize();
                g.setColor(b.getBackground().darker());
                g.fillRect(0, 0, size.width, size.height);
            }
    }
}
