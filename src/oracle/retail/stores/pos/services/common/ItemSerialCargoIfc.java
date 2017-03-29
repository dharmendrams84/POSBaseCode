/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/ItemSerialCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:52 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:28:33 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:22:31 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:11:41 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:49:08  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:54:34   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:34:46   msg
 * Initial revision.
 * 
 *    Rev 1.1   Mar 18 2002 23:09:42   msg
 * - updated copyright
 * 
 *    Rev 1.0   Mar 18 2002 11:22:56   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:14:04   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:06:14   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

//-------------------------------------------------------------------------
/**
    This interface defines methods used when a cargo works with a Serialized
    Item.
    <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $

**/
//-------------------------------------------------------------------------
public interface ItemSerialCargoIfc extends PLUItemCargoIfc
{
    //----------------------------------------------------------------------
    /**
        Returns the item serial number.

        @return Item serial number
    **/
    //----------------------------------------------------------------------
    public String getItemSerial();

    //----------------------------------------------------------------------
    /**
        Sets the item serial number.

        @param  The item serial number String
    **/
    //----------------------------------------------------------------------
    public void setItemSerial(String itemSerial);

}
