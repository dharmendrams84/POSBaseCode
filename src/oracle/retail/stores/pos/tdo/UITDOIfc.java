/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/tdo/UITDOIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:40 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:30:38 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:26:30 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:15:21 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/03/16 18:30:42  cdb
 *   @scr 0 Removed tabs from all java source code.
 *
 *   Revision 1.2  2004/02/12 16:48:28  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:12  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Dec 16 2003 14:53:36   bjosserand
 * Initial revision.
 * 
 *    Rev 1.0   Oct 17 2003 12:40:34   epd
 * Initial revision.
 *   
 * ===========================================================================
 */
package oracle.retail.stores.pos.tdo;

import java.util.HashMap;

import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;

/**
 *
 * 
 */
public interface UITDOIfc
{
    /**
     * Build a UI bean model.  Use the HashMap to pass
     * in attributes needed to construct the bean model
     * @return a fully populated bean model
     */
    public POSBaseBeanModel buildBeanModel(HashMap attributeMap);
}
