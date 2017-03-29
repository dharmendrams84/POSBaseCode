/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/ItemSizeCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:54 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 * 3    360Commerce 1.2         3/31/2005 4:28:34 PM   Robert Pearse   
 * 2    360Commerce 1.1         3/10/2005 10:22:32 AM  Robert Pearse   
 * 1    360Commerce 1.0         2/11/2005 12:11:42 PM  Robert Pearse   
 *
 *Revision 1.1  2004/02/20 15:51:42  baa
 *@scr 3561  size enhancements
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;
// java imports

//-------------------------------------------------------------------------
/**
    This interface defines methods used when a cargo works with an Itemsize.
    <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $

**/
//-------------------------------------------------------------------------
public interface ItemSizeCargoIfc  
{
    //----------------------------------------------------------------------
    /**
        Returns the item's Size code
        <P>
        @return The  item's Size code
    **/
    //----------------------------------------------------------------------
    public String getItemSizeCode();

    //----------------------------------------------------------------------
    /**
        Sets the item's Size code
        <P>
        @param  code item's Size code
    **/
    //----------------------------------------------------------------------
    public void setItemSizeCode(String code);

 
}
