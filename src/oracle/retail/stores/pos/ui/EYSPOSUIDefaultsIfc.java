/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/EYSPOSUIDefaultsIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:37 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:28:09 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:21:35 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:59 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:52:11  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:28  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:21  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:09:20   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 14:44:56   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:51:38   msg
 * Initial revision.
 * 
 *    Rev 1.1   Jan 19 2002 10:28:42   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 * 
 *    Rev 1.0   27 Oct 2001 10:26:28   mpm
 * Initial revision.
 * Resolution for POS SCR-228: Merge VABC, Pier 1 changes
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

//------------------------------------------------------------------------------
/**
 *  Defines ui display constants for colors, fonts, borders,
 *  and other ui characteristics.
 */
//------------------------------------------------------------------------------
public interface EYSPOSUIDefaultsIfc
{
    // basic colors
    static final public Color COLOR_WINE         = new Color(0x912B47);
    static final public Color COLOR_DARK_BLUE    = new Color(0x31309C);
    static final public Color COLOR_LIGHT_BLUE   = new Color(0xc2, 0xd2,0xff);
    static final public Color COLOR_LIGHT_YELLOW = new Color(0xff, 0xff, 0xb4);

    // component and area mappings for colors
    static final public Color HeaderBackground = Color.white;
    static final public Color HeaderLabelBackground = COLOR_LIGHT_YELLOW;
    static final public Color ButtonBackground = COLOR_DARK_BLUE;
    static final public Color ButtonForeground = Color.white;
    static final public Color HighLight = COLOR_LIGHT_YELLOW;
    static final public Color PromptBackground = COLOR_LIGHT_BLUE;
    static final public Color PromptForeground = Color.black;
    static final public Color LabelForeground = Color.black;

    // fonts
    static final public Font PromptAreaFont = new Font("Helvetica", Font.BOLD, 16);
    static final public Font ListFont       = new Font("sansserif", Font.PLAIN, 12);
    // borders
    static final public Border EmptyBorder12   = new EmptyBorder(12, 12, 12, 12);
    static final public Border EmptyBorder0    = new EmptyBorder(0, 0, 0, 0);
    static final public Border NoFocusBorder   = new EmptyBorder(1, 1, 1, 1);
    static final public Border POSEtchedBorder = BorderFactory.createEtchedBorder();
    static final public Border POSBevelBorder  = BorderFactory.createLoweredBevelBorder();
    static final public Border POSButtonBorder = BorderFactory.createRaisedBevelBorder();

    // dimensions
    public static final Dimension VerticalButtonSize   = new Dimension(110,25);
    public static final Dimension HorizontalButtonSize = new Dimension(80,40);
}
