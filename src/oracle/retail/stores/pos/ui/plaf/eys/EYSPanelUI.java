/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/plaf/eys/EYSPanelUI.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:07:01 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   03/11/09 - use method isRoundedBorder in EYSBorderFactory
 *    cgreene   02/16/09 - XbranchMerge cgreene_bug7462232-profiling from
 *                         rgbustores_13.0.1_branch
 *
 * ===========================================================================
 * $Log:
 *   3    360Commerce 1.2         3/31/2005 4:28:09 PM   Robert Pearse   
 *   2    360Commerce 1.1         3/10/2005 10:21:34 AM  Robert Pearse   
 *   1    360Commerce 1.0         2/11/2005 12:10:59 PM  Robert Pearse   
 *  $
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
 *    Rev 1.0   Aug 29 2003 16:13:24   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Apr 03 2003 14:34:24   jgs
 * Fixed timing of background image display.
 * Resolution for 2110: POS Version 6.0 performance enhansements
 * 
 *    Rev 1.0   Apr 29 2002 14:46:08   msg
 * Initial revision.
 * 
 *    Rev 1.1   10 Apr 2002 13:59:52   baa
 * make code compliant with coding guidelines
 * Resolution for POS SCR-1590: PLAF code does not meet the coding standards
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.plaf.eys;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;

import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.ui.beans.ImageBeanIfc;

/**
 * Implements a panel UI
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public class EYSPanelUI extends BasicPanelUI
{
    /** revision number supplied by PVCS **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * Creates panel UI object
     * 
     * @param c the panel as a JComponent
     * @returns the panel UI
     */
    public static ComponentUI createUI(JComponent c)
    {
        return new EYSPanelUI();
    }

    /**
     * Overrides the paint method to display a background image if available.
     * 
     * @param g the panels's graphics object
     * @param c the panel as a JComponent
     */
    @Override
    public void paint(Graphics g, JComponent c)
    {
        Graphics2D g2d = (Graphics2D)g;

        if (c instanceof ImageBeanIfc && ((ImageBeanIfc)c).getBackgroundImage() != null)
        {
            g2d.drawImage(((ImageBeanIfc)c).getBackgroundImage(), null, c);

            /* Some systems have trouble displaying the background image in a
             * timely fashion. I have not been able to Isolate the reason.
             * My guess is that the AWT thread is not getting enough of time
             * slice to display this object. This short sleep seems alleviate
             * the problem.
             */
            try
            {
                Thread.sleep(30);
            }
            catch (java.lang.InterruptedException ie)
            {
            }
        }
        super.paint(g, c);
    }

    /**
     * Repaints the panel
     * 
     * @param g the panel's graphics object
     * @param c the panel as a JComponent
     */
    @Override
    public void update(Graphics g, JComponent c)
    {

        Border b = c.getBorder();

        if (!EYSBorderFactory.isRoundedBorder(b))
        {
            super.update(g, c);
        }
        else
        {
            // paint panel's background with round borders
            ((EYSBorderFactory.RoundedBorder)b).paintBackground(g, c, c.getBackground());
            paint(g, c);
        }
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
}