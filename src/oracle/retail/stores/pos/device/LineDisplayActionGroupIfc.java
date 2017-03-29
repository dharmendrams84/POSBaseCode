/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/device/LineDisplayActionGroupIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:38 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:28:51 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:05 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:18 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:48:34  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:30:29  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:13  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:51:18   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Jan 13 2003 15:38:56   vxs
 * Assigned value for String TYPE
 * Resolution for POS SCR-1901: Pos Device Action/ActionGroup refactoring
 * 
 *    Rev 1.0   Jan 08 2003 12:23:52   vxs
 * Initial revision.
 * Resolution for POS SCR-1901: Pos Device Action/ActionGroup refactoring
 *Revision: /main/7 $
 * ===========================================================================
 */
package oracle.retail.stores.pos.device;

// jpos imports
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.device.DeviceActionGroupIfc;

public interface LineDisplayActionGroupIfc extends DeviceActionGroupIfc
{

    public static final String TYPE = "LineDisplayActionGroupIfc";

    //---------------------------------------------------------------------
    /**
       Display text on line display device
       @param row start row for text
       @param col start col for text
       @param text String to show in line display device.
    **/
    //---------------------------------------------------------------------
    public void displayTextAt(int row, int col, String text) throws DeviceException;

    //---------------------------------------------------------------------
    /**
       Clears the line display device
    **/
    //---------------------------------------------------------------------
    public void clearText() throws DeviceException;

    //---------------------------------------------------------------------
    /**
       Display text on line display device
       @param item lineitem whose description/price has to be displayed
    **/
    //---------------------------------------------------------------------
    public void lineDisplayItem(SaleReturnLineItemIfc item) throws DeviceException;

    //---------------------------------------------------------------------
    /**
       Display text on line display device
       @param item lineitem whose description/price has to be displayed
    **/
    //---------------------------------------------------------------------
    public void lineDisplayItem(PLUItemIfc item) throws DeviceException;
}
