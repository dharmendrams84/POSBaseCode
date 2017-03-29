/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/ButtonPressedLetter.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:37 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   01/21/10 - refactor ButtonPressedLetter from pos to
 *                         foundation-client
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:18 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:19:55 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:09:43 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/09/23 00:07:10  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.2  2004/02/12 16:52:11  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:21  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:09:18   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 14:44:48   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:51:32   msg
 * Initial revision.
 * 
 *    Rev 1.1   Jan 19 2002 10:28:40   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 * 
 *    Rev 1.0   Sep 21 2001 11:33:42   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:16:06   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui;

/**
 * @deprecated as of 13.2 use {@link oracle.retail.stores.foundation.manager.ui.jfc.ButtonPressedLetter} instead
 */
public class ButtonPressedLetter extends oracle.retail.stores.foundation.manager.ui.jfc.ButtonPressedLetter
{
    // This id is used to tell the compiler not to generate a new serialVersionUID.
    static final long serialVersionUID = 975653725948291944L;

    /**
     * Constructor.
     * 
     * @param btnName name of the button
     */
    public ButtonPressedLetter(String btnName)
    {
        super(btnName, 0);
    }

    /**
     * Constructor.
     * 
     * @param btnName name of the button
     * @param btnNumber number of the button
     */
    public ButtonPressedLetter(String btnName, int btnNumber)
    {
        super(btnName, btnNumber);
    }
}