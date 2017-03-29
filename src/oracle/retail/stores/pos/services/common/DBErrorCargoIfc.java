/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/DBErrorCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:51 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:27:42 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:50 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:30 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:48:00  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Nov 04 2003 19:00:00   cdb
 * Initial revision.
 * Resolution for 3430: Sale Service Refactoring
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

// foundation imports
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

//-------------------------------------------------------------------------
/**
    This interface defines methods used when a cargo works with an db errors.
    <P>

    @version $Revision: /rgbustores_13.4x_generic_branch/1 $

**/
//-------------------------------------------------------------------------
public interface DBErrorCargoIfc extends CargoIfc
{
    //----------------------------------------------------------------------
    /**
        Returns the error code returned with a DataException.
        <P>
        @return the integer value
    **/
    //----------------------------------------------------------------------
    public int getDataExceptionErrorCode();

    //----------------------------------------------------------------------
    /**
        Sets the error code returned with a DataException.
        <P>
        @param  the integer value
    **/
    //----------------------------------------------------------------------
    public void setDataExceptionErrorCode(int value);

}
