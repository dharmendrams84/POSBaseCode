/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/EYSButton.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:44 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   12/16/09 - formatted
 *
 * ===========================================================================
 * $Log:
 * 3    360Commerce 1.2         3/31/2005 4:28:08 PM   Robert Pearse   
 * 2    360Commerce 1.1         3/10/2005 10:21:33 AM  Robert Pearse   
 * 1    360Commerce 1.0         2/11/2005 12:10:59 PM  Robert Pearse   
 *
 *Revision 1.4  2004/03/16 17:15:22  build
 *Forcing head revision
 *
 *Revision 1.3  2004/03/16 17:15:17  build
 *Forcing head revision
 *
 *Revision 1.2  2004/02/11 20:56:27  rhafernik
 *@scr 0 Log4J conversion and code cleanup
 *
 *Revision 1.1.1.1  2004/02/11 01:04:22  cschellenger
 *updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:10:32   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   23 Jul 2003 00:45:42   baa
 * add overwrite traversable method.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * This class allows to overwrite stardard button behavior.
 */
public class EYSButton extends JButton
{
    private static final long serialVersionUID = 4808245602514752443L;

    /** revision number supplied for PVCS */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    protected boolean focusTraversable = false;

    /**
     * Constructor.
     */
    public EYSButton(String text)
    {
        this(text, null, false);
    }

    /**
     * Constructor.
     */
    public EYSButton(String label, Icon icon)
    {
        this(label, icon, false);
    }

    /**
     * Constructor.
     */
    public EYSButton(String label, Icon icon, boolean focusable)
    {
        super(label, icon);
        setFocusTraversable(focusable);
    }

    public boolean isFocusTraversable()
    {
        return focusTraversable;
    }

    public void setFocusTraversable(boolean value)
    {
        focusTraversable = value;
    }
}
