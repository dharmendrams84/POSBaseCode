/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/factory/TenderFactoryIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:41 mszekely Exp $
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
 *  3    360Commerce 1.2         3/31/2005 4:30:23 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:25:57 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:14:51 PM  Robert Pearse   
 *
 * Revision 1.3.4.1  2004/11/15 22:27:36  bwf
 * @scr 7671 Create tender from rdo instead of class.  This is necessary because ADO's are not 1:1 with RDOs.
 *
 * Revision 1.3  2004/04/08 20:33:03  cdb
 * @scr 4206 Cleaned up class headers for logs and revisions.
 *
 * 
 * Rev 1.1 Dec 16 2003 11:17:56 bwf Create new createTender method per code
 * review.
 * 
 * Rev 1.0 Nov 20 2003 16:55:50 epd Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.factory;
import java.util.HashMap;

import oracle.retail.stores.pos.ado.tender.TenderADOIfc;
import oracle.retail.stores.pos.ado.tender.TenderException;
import oracle.retail.stores.pos.ado.tender.TenderTypeEnum;
import oracle.retail.stores.domain.tender.TenderLineItemIfc;

/**
 *  
 */
public interface TenderFactoryIfc extends ADOFactoryIfc
{
    /**
     * Attempts to create a tender based on the attributes contained in the
     * HashMap
     * 
     * @param tenderAttributes
     *            HashMap containing attributes needed to create a tender
     * @return A TenderADOIfc instance
     * @throws TenderException
     *             Thrown when it's not possible to create a tender due to
     *             invalid attributes.
     */
    TenderADOIfc createTender(HashMap tenderAttributes) throws TenderException;

    //----------------------------------------------------------------------
    /**
        Attempts to create an ADO tender given a corresponding RDO.
        @param rdoObject The RDO tender.
        @return A TenderADOIfc instance
    **/
    //----------------------------------------------------------------------
    TenderADOIfc createTender(TenderLineItemIfc rdoObject);

    //----------------------------------------------------------------------
    /**
     * Attempts to create an ADO tender given a corresponding TenderTypeEnum.
     * 
     * @param tte
     *            TenderTypeEnum
     * @return TenderADOIfc
     */
    //----------------------------------------------------------------------
    TenderADOIfc createTender(TenderTypeEnum tte);
}
