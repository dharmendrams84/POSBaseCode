/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/behavior/DefaultMailAction.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:07:00 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:27:43 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:52 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:32 PM  Robert Pearse   
 *
 *   Revision 1.5  2004/09/23 00:07:14  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.4  2004/04/09 16:56:01  cdb
 *   @scr 4302 Removed double semicolon warnings.
 *
 *   Revision 1.3  2004/02/12 16:52:12  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:29  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:23  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:13:16   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   23 May 2002 17:44:26   vxs
 * Removed unneccessary concatenations in logging statements.
 * Resolution for POS SCR-1632: Updates for Gap - Logging
 *
 *    Rev 1.0   Apr 29 2002 14:46:52   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:58:26   msg
 * Initial revision.
 *
 *    Rev 1.1   Jan 19 2002 10:32:50   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 *
 *    Rev 1.0   Sep 21 2001 11:34:02   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:18:22   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.behavior;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.apache.log4j.Logger;

import oracle.retail.stores.foundation.manager.gui.UISubsystem;
import oracle.retail.stores.pos.ui.ButtonPressedLetter;
import oracle.retail.stores.pos.ui.beans.UIAction;

public class DefaultMailAction extends AbstractAction implements Action
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = 6626462288811890699L;

    /**
        The logger to which log messages will be sent.
    **/
    protected static Logger logger = Logger.getLogger(oracle.retail.stores.pos.ui.behavior.DefaultMailAction.class);

    public void actionPerformed(ActionEvent evt)
    {
        String name = evt.getActionCommand();
        int  number = 0;
        if (evt.getSource() instanceof UIAction)
        {
            UIAction source = (UIAction)evt.getSource();
            number           = source.getButtonNumber();
        }
        if (logger.isInfoEnabled()) logger.info( "" + name + " is being sent to engine.");

        UISubsystem.getInstance().mail(new ButtonPressedLetter(name, number), true);
    }
}
