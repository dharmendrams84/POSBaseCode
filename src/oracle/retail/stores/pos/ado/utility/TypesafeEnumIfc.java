/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/utility/TypesafeEnumIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:41 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:30:37 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:26:29 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:15:20 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:47:58  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   Nov 07 2003 10:53:58   epd
 * added serializable interface
 * 
 *    Rev 1.0   Nov 04 2003 11:15:08   epd
 * Initial revision.
 * 
 *    Rev 1.0   Oct 17 2003 12:39:08   epd
 * Initial revision.
 *   
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.utility;

import java.io.Serializable;

/**
 * Defines the public contract all typesafe enumerations must implement
 * 
 */
public interface TypesafeEnumIfc extends Serializable
{
    /** fix deserialization */
    public abstract Object readResolve() throws java.io.ObjectStreamException;
}
