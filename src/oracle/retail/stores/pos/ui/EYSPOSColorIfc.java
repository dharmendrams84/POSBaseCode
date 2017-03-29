/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/EYSPOSColorIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:36 mszekely Exp $
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
 *    2    360Commerce 1.1         3/10/2005 10:21:34 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:59 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:52:11  mcs
 *   Forcing head revision
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
 *    Rev 1.0   Mar 18 2002 11:51:36   msg
 * Initial revision.
 * 
 *    Rev 1.1   Jan 19 2002 10:28:42   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 * 
 *    Rev 1.0   Sep 21 2001 11:33:38   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:16:04   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui;

import java.awt.Color;

public interface EYSPOSColorIfc
{
  static final public Color HeaderLabelBackground = new Color(0xff, 0xff, 0xb4);
  static final public Color ButtonBackground = new Color(0x33, 0x33, 0x99);
  static final public Color HighLight = new Color(0xc2, 0xd2,0xff);
  static final public Color PromptBackground = new Color(204, 204, 255); // light blue
}
