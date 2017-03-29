/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/ValidatingFieldIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:41 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/28/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *   3    360Commerce 1.2         3/31/2005 4:30:43 PM   Robert Pearse   
 *   2    360Commerce 1.1         3/10/2005 10:26:43 AM  Robert Pearse   
 *   1    360Commerce 1.0         2/11/2005 12:15:30 PM  Robert Pearse   
 *
 *  Revision 1.3  2004/03/16 18:30:41  cdb
 *  @scr 0 Removed tabs from all java source code.
 *
 *  Revision 1.2  2004/03/16 17:15:18  build
 *  Forcing head revision
 *
 *  Revision 1.1.1.1  2004/02/11 01:04:23  cschellenger
 *  updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:13:00   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 14:56:20   msg
 * Initial revision.
 * 
 *    Rev 1.1   15 Apr 2002 09:36:46   baa
 * make call to setLabel() from the updatePropertyFields() method
 * Resolution for POS SCR-1599: Field name labels on dialog screens use default text instead of text from bundles
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import javax.swing.JLabel;

//-------------------------------------------------------------------------------
/**
   This interface is implemented by fields that can be validated.
   @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//-------------------------------------------------------------------------------
public interface ValidatingFieldIfc
{
    //---------------------------------------------------------------------------
    /**
     *    Returns the flag for allowing empty to be valid. <p>
     *    @return true if empty field is valid, false otherwise
     */
    public boolean isEmptyAllowed();

    //---------------------------------------------------------------------------
    /**
     *    Sets the flag for allowing an empty string to be valid. <p>
     *    @param allowEmpty true if empty field is valid, false otherwise
     */
    public void setEmptyAllowed(boolean allowEmpty);

    //---------------------------------------------------------------------------
    /**
     *    Gets the label associated with this component.
     *    @return the label
     */
    public JLabel getLabel();

    //---------------------------------------------------------------------------
    /**
     *    Sets the label associated with this component.
     *    @param label the label
     */
    public void setLabel(JLabel label);

    //---------------------------------------------------------------------------
    /**
     *    Returns whether the field is required.
     *    @return true if required, false otherwise
     */
    public boolean isRequired();

    //---------------------------------------------------------------------------
    /**
     *    Sets whether the field is required.
     *    @param required true if required false if not
     */
    public void setRequired(boolean required);

    //---------------------------------------------------------------------------
    /**
        Determines whether the current field information is valid and
        returns the result. <p>
        @return true if the current field entry is valid, false otherwise
    **/
    public boolean isInputValid();

    //---------------------------------------------------------------------------
    /**
     *    Returns the error message of a field. <p>
     *    @return String the error message
     */
    public String getErrorMessage();

    //---------------------------------------------------------------------------
    /**
     *    Sets the error message of a field. <p>
     *    @param msg the error message
     */
    public void setErrorMessage(String msg);
}
