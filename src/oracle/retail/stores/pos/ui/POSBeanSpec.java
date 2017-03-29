/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/POSBeanSpec.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:07:00 mszekely Exp $
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
 *    4    360Commerce 1.3         4/12/2008 5:44:57 PM   Christian Greene
 *         Upgrade StringBuffer to StringBuilder
 *    3    360Commerce 1.2         3/31/2005 4:29:22 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:10 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:06 PM  Robert Pearse   
 *
 *   Revision 1.4  2004/09/23 00:07:10  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.3  2004/02/12 16:52:11  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:28  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:21  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:09:22   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Aug 15 2002 17:55:44   baa
 * apply foundation  updates to UISubsystem
 *  
 * Resolution for POS SCR-1769: 5.2 UI defects resulting from change to java 1.4
 * 
 *    Rev 1.0   Apr 29 2002 14:45:06   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:51:44   msg
 * Initial revision.
 * 
 *    Rev 1.4   Jan 19 2002 10:28:46   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 * 
 *    Rev 1.3   13 Nov 2001 15:56:18   mpm
 * Cleaned up toString method.
 *
 *    Rev 1.0   Sep 21 2001 11:33:44   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:16:04   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui;

// java imports
import oracle.retail.stores.foundation.manager.gui.BeanSpec;
import oracle.retail.stores.foundation.manager.gui.loader.SpecIfc;
import oracle.retail.stores.foundation.utility.Util;

//--------------------------------------------------------------------------
/**
    The bean specification class contains all of the
    data necessary to create an instance of the bean and
    set its text and properties.
    <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
    @deprecated as of release 5.5 replaced by @linkoracle.retail.stores.foundation.manager.gui.BeanSpec
**/
//--------------------------------------------------------------------------
public class POSBeanSpec extends BeanSpec implements SpecIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -8929379547913338633L;

    /** revision number supplied by Team Connection */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //--------------------------------------------------------------------------
    /**
     *  Default constructor.
     */
    public POSBeanSpec()
    {
        super();
    }


    //---------------------------------------------------------------------
    /**
        @return String a representation of the class.
    **/
    //---------------------------------------------------------------------
    public String toString()
    {
        StringBuilder tmpString =
          Util.classToStringHeader("POSBeanSpec", revisionNumber, hashCode());
        tmpString.append("[")
                 .append(specName)
                 .append("-")
                 .append(buttonList)
                 .append("]");

        return(tmpString.toString());
    }

    protected void finalize() throws Throwable
    {
        super.finalize();
    }
}
