/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/factory/TenderGroupFactoryIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:41 mszekely Exp $
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
 *  3    360Commerce 1.2         3/31/2005 4:30:24 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:25:59 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:14:53 PM  Robert Pearse   
 *
 * Revision 1.3  2004/04/08 20:33:03  cdb
 * @scr 4206 Cleaned up class headers for logs and revisions.
 *
 * 
 * Rev 1.0 Nov 20 2003 17:11:54 epd Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.factory;
import oracle.retail.stores.pos.ado.tender.TenderTypeEnum;
import oracle.retail.stores.pos.ado.tender.group.TenderGroupADOIfc;
/**
 *  
 */
public interface TenderGroupFactoryIfc extends ADOFactoryIfc
{
    /**
     * Attempt to create a new TenderGroup given a tender type enum instance.
     * 
     * @param tenderType
     *            The type of the desired tender group
     * @return a new TenderGroup instance or null.
     */
    TenderGroupADOIfc createTenderGroup(TenderTypeEnum tenderType);
}
