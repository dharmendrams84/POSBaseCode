/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/EmployeeHeader.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:57 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:57 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:21:19 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:49 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/03/16 17:15:17  build
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 20:56:26  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:22  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:10:24   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 14:51:20   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:54:22   msg
 * Initial revision.
 * 
 *    Rev 1.3   Feb 23 2002 15:04:14   mpm
 * Re-started internationalization initiative.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.2   Jan 19 2002 10:30:08   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 *
 *    Rev 1.1   13 Dec 2001 18:24:14   epd
 * Changed column name
 * Resolution for POS SCR-216: Making POS changes to accommodate OnlineOffice
 *
 *    Rev 1.0   Sep 21 2001 11:37:08   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:17:36   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

//  java imports
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import oracle.retail.stores.pos.ui.EYSPOSColorIfc;

//---------------------------------------------------------------------
/**
 * Contains the visual presentation of the Prescription header
 * @deprecated As of release 5.0.0, rendered unnecessary by changes to {@link ListBean}
 */
//---------------------------------------------------------------------
public class EmployeeHeader extends JPanel
{
    protected static int NAME       = 0;
    protected static int ID         = NAME + 1;
    protected static int ROLE       = ID + 1;
    //protected static int MAX_FIELDS = ID + 1;
    protected static int MAX_FIELDS = ROLE + 1; // add role when availible

    protected static String labelText[] =
    {
      "Employee Name",
      "Employee Login ID",
      "Role"     // add role when availible
    };

    protected JLabel labels[] = new JLabel[MAX_FIELDS];

    //---------------------------------------------------------------------
    /**
     * Default Constructor
     */
    //---------------------------------------------------------------------
    public EmployeeHeader()
    {
      super();
      initialize();
    }
    //---------------------------------------------------------------------
    /**
     * Initialize the class.
     */
    //---------------------------------------------------------------------
    protected void initialize()
    {
        GridBagConstraints gbc = new GridBagConstraints();

        setName("EmployeeHeader");
        setLayout(new GridBagLayout());
        setForeground(Color.black);
        Font font = new Font("sansserif", 0, 12);
        setFont(font);

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.gridy = 0;

        for(int cnt = 0; cnt < MAX_FIELDS; cnt ++)
        {
            labels[cnt] = new JLabel(labelText[cnt], JLabel.CENTER);
            labels[cnt].setName(labelText[cnt]);
            labels[cnt].setFont(font);
            labels[cnt].setOpaque(true);
            labels[cnt].setBorder(BorderFactory.createLoweredBevelBorder());
            labels[cnt].setForeground(Color.black);
            labels[cnt].setBackground(EYSPOSColorIfc.HeaderLabelBackground);
            gbc.weightx = (cnt != NAME) ? 1.0 : 2.0;
            //gbc.weightx = 1.0;
            gbc.gridx = cnt;
            add(labels[cnt], gbc);
        }
    }
    //---------------------------------------------------------------------
    /**
     * main entrypoint - starts the part when it is run as an application
     * @param args java.lang.String[]
     */
    //---------------------------------------------------------------------
    public static void main(java.lang.String[] args)
    {
        java.awt.Frame frame = new java.awt.Frame();
        EmployeeHeader couponHeader = new EmployeeHeader();
        frame.add("Center", couponHeader);
        frame.setSize(couponHeader.getSize());
        frame.setVisible(true);
    }
}
