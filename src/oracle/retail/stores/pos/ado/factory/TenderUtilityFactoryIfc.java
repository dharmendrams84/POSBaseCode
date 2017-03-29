/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/factory/TenderUtilityFactoryIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:42 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:30:27 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:26:05 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:58 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/04/06 20:45:10  blj
 *   @scr 4301 - cleaned up javadoc's.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.factory;

import oracle.retail.stores.pos.ado.tender.CertificateValidatorIfc;
import oracle.retail.stores.domain.tender.TenderCertificateIfc;

/**
 * This class is used for creating tender utility classes.
 */
public interface TenderUtilityFactoryIfc extends ADOFactoryIfc
{
    public CertificateValidatorIfc createCertificateValidator(TenderCertificateIfc tenderRDO);
}
