/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/CashDrawerCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:52 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:27:21 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:01 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:09:50 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:49:08  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:54:06   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:34:28   msg
 * Initial revision.
 * 
 *    Rev 1.1   Mar 18 2002 23:08:18   msg
 * - updated copyright
 * 
 *    Rev 1.0   Mar 18 2002 11:22:06   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:13:42   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:06:32   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

// foundation imports
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

//-------------------------------------------------------------------------
/**
    This interface defines methods used when a cargo works with a cash drawer.

     <P>
     @version $Revision: /rgbustores_13.4x_generic_branch/1 $

**/
//-------------------------------------------------------------------------
public interface CashDrawerCargoIfc extends CargoIfc
{
    //----------------------------------------------------------------------
    /**
        Returns the cash drawer online status
        <P>
        @return the cash drawer status
    **/
    //----------------------------------------------------------------------
    public boolean getCashDrawerOnline();

    //----------------------------------------------------------------------
    /**
        Sets the cash drawer online status
        <P>
        @param  the cash drawer status
    **/
    //----------------------------------------------------------------------
    public void setCashDrawerOnline(boolean value);

    //----------------------------------------------------------------------
    /**
        Returns the cash drawer open status
        <P>
        @return the cash drawer status
    **/
    //----------------------------------------------------------------------
    public boolean getCashDrawerHasBeenClosed();

    //----------------------------------------------------------------------
    /**
        Sets the cash drawer open status
        <P>
        @param  the cash drawer status
    **/
    //----------------------------------------------------------------------
    public void setCashDrawerHasBeenClosed(boolean value);

    //----------------------------------------------------------------------
    /**
        Returns the cash drawer retry flag
        <P>
        @return true if the retry is on cash drawer; false is for printer
    **/
    //----------------------------------------------------------------------
    public boolean getDeviceIsCashDrawer();

    //----------------------------------------------------------------------
    /**
        Sets the cash drawer retry flag
        <P>
        @param boolean value of the flag
    **/
    //----------------------------------------------------------------------
    public void setDeviceIsCashDrawer(boolean value);
}
