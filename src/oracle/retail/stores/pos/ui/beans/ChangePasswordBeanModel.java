/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/ChangePasswordBeanModel.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:59 mszekely Exp $
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
 *  1    360Commerce 1.0         10/4/2006 10:50:53 AM  Rohit Sachdeva  
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

//----------------------------------------------------------------------------
/**
 * This is model for Change Password Screen.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
//----------------------------------------------------------------------------
public class ChangePasswordBeanModel extends POSBaseBeanModel
{
    /**
     * Revision number
     */
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
     * employee login id 
     */
    protected String fieldLoginID = "";
    /**
     * Password
     */
    protected String fieldPassword = "";
    /**
     * New Password
     */
    protected String fieldNewPassword = "";
    /**
     * Verify to Re-enter Password
     */
    protected String fieldVerifyPassword = "";
   

  
    //----------------------------------------------------------------------------
    /**
     * Get the value of the LoginID field
     * 
     * @return the value of LoginID
     */
    //----------------------------------------------------------------------------
    public String getLoginID()
    {
        return fieldLoginID;
    }
    //----------------------------------------------------------------------------
    /**
     * Get the value of the Password field
     * 
     * @return the value of Password
     */
    //----------------------------------------------------------------------------
    public String getPassword()
    {
        return fieldPassword;
    }
    
    //----------------------------------------------------------------------------
    /**
     * Get the value of the Password field
     * 
     * @return the value of Password
     */
    //----------------------------------------------------------------------------
    public String getNewPassword()
    {
        return fieldNewPassword;
    }
    //----------------------------------------------------------------------------
    /**
     * Get the value of the Verify Re-enter Password field
     * 
     * @return the value of Verify Password
     */
    //----------------------------------------------------------------------------
    public String getVerifyPassword()
    {
        return fieldVerifyPassword;
    }

    //----------------------------------------------------------------------------
    /**
     * Sets the LoginID field
     * 
     * @param loginID
     *            the value to be set for loginID
     */
    //----------------------------------------------------------------------------
    public void setLoginID(String loginID)
    {
        fieldLoginID = loginID;
    }

    //----------------------------------------------------------------------------
    /**
     * Sets the Password field
     * 
     * @param password
     *            the value to be set for Password
     */
    //----------------------------------------------------------------------------
    public void setPassword(String password)
    {
        fieldPassword = password;
    }
    
    //----------------------------------------------------------------------------
    /**
     * Sets the Password field
     * 
     * @param newPassword
     *            the value to be set for Password
     */
    //----------------------------------------------------------------------------
    public void setNewPassword(String newPassword)
    {
        fieldNewPassword = newPassword;
    }
    
    //----------------------------------------------------------------------------
    /**
     * Sets the VerifyPassword field
     * 
     * @param verifyPassword
     *            the value to be set for VerifyPassword
     */
    //----------------------------------------------------------------------------
    public void setVerifyPassword(String verifyPassword)
    {
        fieldVerifyPassword = verifyPassword;
    }

    //---------------------------------------------------------------------
    /**
     * Converts to a string representing the data in this Object @returns
     * string representing the data in this Object
     * @return String 
     */
    //----------------------------------------------------------------------
    public String toString()
    {
        StringBuffer buff = new StringBuffer();

        buff.append(
            "Class: ChangePasswordBeanModel Revision: "
                + revisionNumber
                + "\n");
        buff.append("LoginID [" + fieldLoginID + "]\n");
        buff.append("Password [" + fieldPassword + "]\n");
        buff.append("NewPassword [" + fieldNewPassword + "]\n");
        buff.append("VerifyPassword [" + fieldVerifyPassword + "]\n");
        return (buff.toString());
    }
}
