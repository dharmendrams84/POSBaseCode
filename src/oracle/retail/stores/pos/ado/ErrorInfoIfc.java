/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/ErrorInfoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:40 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado;

/**
 * @author bjosserand
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface ErrorInfoIfc
{
   /**
   Returns the error text default. <P>
   @return The error text default
   **/
   public String getErrorTextDefault();
    
   /**
   Sets the error text default. <P>
   @param int the error text default
   **/
   public void setErrorTextDefault(String errorTextDefault);
    
   /**
   Returns the error text resource name. <P>
   @return The error text resource name
   **/
   public String getErrorTextResourceName();
     
   /**
   Sets the error text resource name. <P>
   @param int the error text resource name
   **/
   public void setErrorTextResourceName(String errorTextResourceName);
}
