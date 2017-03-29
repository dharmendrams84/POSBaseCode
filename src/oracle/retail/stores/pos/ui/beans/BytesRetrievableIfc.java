/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/BytesRetrievableIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:58 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *
 * ===========================================================================
 * $Log:
 *  2    360Commerce 1.1         11/29/2007 5:15:58 PM  Alan N. Sinton  CR
 *       29677: Protect user entry fields of PAN data.
 *  1    360Commerce 1.0         11/13/2007 2:40:38 PM  Jack G. Swan    Added
 *       to support retrieving card numbers from UI as a byte array instead of
 *        a String object.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

//-------------------------------------------------------------------------
/**
   This field allows input to be valid if it meets max and min lenght
   requirements.
*/
//-------------------------------------------------------------------------
public interface BytesRetrievableIfc
{
    //---------------------------------------------------------------------
    /**
       Gets the entered text as bytes
       @return byte array
    */
    //---------------------------------------------------------------------
    public byte[] getTextBytes();

    //---------------------------------------------------------------------
    /**
       Sets the text as bytes
       @param byte array
    */
    //---------------------------------------------------------------------
    public void setTextBytes(byte[] value);

    //---------------------------------------------------------------------
    /**
       Clears the content of currently held values.
    */
    //---------------------------------------------------------------------
    public void clearTextBytes();
}
