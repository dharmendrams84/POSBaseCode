/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/TagConstantsIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:52 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:30:17 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:43 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:37 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:49:08  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:54:50   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Feb 21 2003 16:09:34   baa
 * Initial revision.
 * Resolution for POS SCR-1740: Code base Conversions
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;
 

//--------------------------------------------------------------------------
/**
    This class  is the container for the default text strings otherwised 
    store in the bundles
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
//--------------------------------------------------------------------------
public interface TagConstantsIfc 
{
     /**
       Customer name bundle tag
     **/
     public static final String CUSTOMER_NAME_TAG = "CustomerName";
     /**
       Customer name default text
     **/
     public static final String CUSTOMER_NAME_PATTERN_TAG = "{0} {1}"; 
     /**
       Short tax tag
     **/
     public static final String SHORT_TAX_TAG = "TX"; 
     
     /**
       Short total tag
     **/
     public static final String SHORT_TOTAL_TAG = "TL";           
}
