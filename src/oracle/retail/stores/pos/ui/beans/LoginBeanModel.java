/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/LoginBeanModel.java /main/1 2011/01/24 17:29:40 blarsen Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   10/21/10 - login bean model
 *    blarsen   05/11/10 - login bean model
 *    
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;


public class LoginBeanModel extends POSBaseBeanModel
{
    private static final long serialVersionUID = -3543360034693390013L;
    
    /** 
        Indicates whether to clear the date fields, default true.
    **/
    private boolean clearUIFields = true;

    String loginID = "";
    String password = "";
 
    /**
        Set clearUIFields flag to determine whether to clear the date fields. <P>
        @param boolean.
    **/
    //--------------------------------------------------------------------- 
    public void setclearUIFields(boolean value)
    {
        clearUIFields = value;
    }

    //---------------------------------------------------------------------
    /**
        Returns the current valud of clearUIFields.<P>
        @return value of clearUIFields flag.
    **/
    //--------------------------------------------------------------------- 
    public boolean getclearUIFields()
    {
        return(clearUIFields);
    }
    
    public String getLoginID()
    {
        return loginID;
    }

    public void setLoginID(String loginID)
    {
        this.loginID = loginID;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
