/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/EYSPOSBeanIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:43 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:28:09 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:21:34 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:59 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/03/16 17:15:17  build
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 20:56:26  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:22  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:10:32   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 14:50:36   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:53:18   msg
 * Initial revision.
 * 
 *    Rev 1.2   Jan 20 2002 12:36:36   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 * 
 *    Rev 1.0.1.0   Jan 19 2002 10:30:20   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 * 
 *    Rev 1.1   11 Dec 2001 14:02:14   dwt
 * internationalization support
 * 
 *    Rev 1.0   Sep 21 2001 11:37:24   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:17:26   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

// Java imports
import java.util.Properties;

import oracle.retail.stores.foundation.manager.gui.UIBeanIfc;
import oracle.retail.stores.foundation.manager.gui.UIModelIfc;

//---------------------------------------------------------------------------
/**
   This interface defines the methods that POS Beans must implement.
   
   @version $Revision: /rgbustores_13.4x_generic_branch/1 $
*/
//---------------------------------------------------------------------------
public interface EYSPOSBeanIfc extends UIBeanIfc
{
    //--------------------------------------------------------------------------
    /**
     *  Gets the name of the bean spec that defines this bean.
     *  @return the bean spec name
     */
    public String getBeanSpecName();
    
    //--------------------------------------------------------------------------
    /**
     *  Gets this bean's properties object that contains localized text.
     *  @return the properties object
     */
    public Properties getProps();
    
    //--------------------------------------------------------------------------
    /**
     *  Gets the name of the resource bundle that contains any
     *  localized text that this bean might need.
     *  @return the resource bundle filename
     */
    public String getResourceBundleFilename();
    
    //--------------------------------------------------------------------------
    /**
     *  Sets the name of the bean spec that defines this bean.
     *  @param aValue the bean spec name
     */
    public void setBeanSpecName(String aValue);
    
    //---------------------------------------------------------------------
    /**
     *  Sets the properties object.
     *  @param props the properties object.
     */
    //---------------------------------------------------------------------
    public void setProps(Properties props);
    
    //--------------------------------------------------------------------------
    /**
     *  Sets the name of the resource bundle that populated the
     *  properties object.
     *  @param aValue the bundle filename
     */
    public void setResourceBundleFilename(String aValue);
    
    //-----------------------------------------------------------------------
    /**
       Performs start up configuration.
    */
    //-----------------------------------------------------------------------
    public void configure();

    //-----------------------------------------------------------------------
    /**
       Sets business data on the bean.
       @param beanModel contains data from the business logic
    */
    //-----------------------------------------------------------------------
    public void setModel(UIModelIfc beanModel);
    
    //-----------------------------------------------------------------------
    /**
       Updates the model from the bean in prepration for sending the model
       back to business logic.
    */
    //-----------------------------------------------------------------------
    public void updateModel();
}
