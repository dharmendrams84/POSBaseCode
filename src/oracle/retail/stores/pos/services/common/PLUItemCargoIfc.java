/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/PLUItemCargoIfc.java /rgbustores_13.4x_generic_branch/2 2011/08/09 11:31:52 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   08/09/11 - formatting and removed deprecated code
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:29:21 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:06 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:04 PM  Robert Pearse   
 *
 *   Revision 1.4  2004/03/11 14:32:10  baa
 *   @scr 3561 Add itemScanned get/set methods to PLUItemCargoIfc and add support for changing type of quantity based on the uom
 *
 *   Revision 1.3  2004/02/12 16:49:08  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:38:50  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:54:42   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:35:40   msg
 * Initial revision.
 * 
 *    Rev 1.1   Mar 18 2002 23:10:12   msg
 * - updated copyright
 * 
 *    Rev 1.0   Mar 18 2002 11:23:14   msg
 * Initial revision.
 * 
 *    Rev 1.1   Feb 05 2002 16:42:22   mpm
 * Modified to use IBM BigDecimal.
 * Resolution for POS SCR-1121: Employ IBM BigDecimal
 * 
 *    Rev 1.0   Sep 21 2001 11:14:26   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:06:06   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

import oracle.retail.stores.domain.stock.PLUItemIfc;

/**
 * This interface defines methods used when a cargo works with a PLU.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public interface PLUItemCargoIfc
{
    /**
     * Returns the PLU item.
     * 
     * @return PLU Item
     */
    public PLUItemIfc getPLUItem();

    /**
     * Sets the the PLU item.
     * 
     * @param PLU item as PLUItemIfc
     */
    public void setPLUItem(PLUItemIfc pluItem);

    /**
     * Sets the itemScanned flag.
     * 
     * @param value boolean
     */
    public void setItemScanned(boolean value);

    /**
     * Returns the itemScanned flag.
     * 
     * @return boolean
     */
    public boolean isItemScanned();
}
