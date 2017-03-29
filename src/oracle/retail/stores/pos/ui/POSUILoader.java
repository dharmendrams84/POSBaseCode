/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/POSUILoader.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:07:00 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:29:27 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:18 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:21 PM  Robert Pearse   
 *
 *   Revision 1.5  2004/09/23 00:07:10  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.4  2004/04/09 16:56:00  cdb
 *   @scr 4302 Removed double semicolon warnings.
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
 *    Rev 1.0   Aug 29 2003 16:09:26   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Aug 15 2002 17:55:46   baa
 * apply foundation  updates to UISubsystem
 *  
 * Resolution for POS SCR-1769: 5.2 UI defects resulting from change to java 1.4
 * 
 *    Rev 1.0   Apr 29 2002 14:45:12   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:51:48   msg
 * Initial revision.
 * 
 *    Rev 1.1   Jan 19 2002 10:28:50   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 * 
 *    Rev 1.0   Sep 21 2001 11:33:28   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:16:02   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui;

// foundation imports
import org.apache.log4j.Logger;

import oracle.retail.stores.foundation.manager.gui.UIConstantsIfc;
import oracle.retail.stores.foundation.manager.gui.loader.LoaderIfc;
import oracle.retail.stores.foundation.manager.gui.loader.NullBuilder;
import oracle.retail.stores.foundation.manager.gui.loader.UILoaderIfc;
import oracle.retail.stores.foundation.manager.gui.loader.UIXMLLoader;

//--------------------------------------------------------------------------
/**    
    The POSUILoader extends the UIXMLLoader and specifies a builder for the
    ButtonSpec object.
    <P>
    Once the data source has been read in, then the UIXMLLoader
    processes each of the UI XML elements.
    <P> 
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
    @deprecated as of release 5.5 replaced by @linkoracle.retail.stores.foundation.manager.gui.loader.UIXMLLoader
**/
//--------------------------------------------------------------------------
public class POSUILoader extends UIXMLLoader implements LoaderIfc, 
                                                        UILoaderIfc, 
                                                        UIConstantsIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -829397998039679042L;

    /** revision number supplied by Team Connection */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    
    /** Store instance of logger here */
    protected static Logger logger = Logger.getLogger(oracle.retail.stores.pos.ui.POSUILoader.class);
    
    /** Define button spec name here */
    protected static final String BUTTON_SPEC_NAME = "BUTTON";
    
    /** field spec name */
    protected static final String FIELD_SPEC_NAME = "FIELD";
    
    //--------------------------------------------------------------------------
    /**
       Default constructor
       
       Sets up builder table and element groups.
    **/
    //--------------------------------------------------------------------------
    public POSUILoader()
    {
        super();
    }
    
    //--------------------------------------------------------------------------
    /**
       Assigns builder classes to element names for all of the known
       UI xml elements
    **/
    //--------------------------------------------------------------------------
    protected void setupBuilderTable()
    {
        // This will setup all of the default assignments
        super.setupBuilderTable();
        
        // Add or override any assignments that are specific to POS
        NullBuilder nullBuilder = new NullBuilder();
        
        builderTable.put(BUTTON_SPEC_NAME, new XMLButtonSpecBuilder());
        builderTable.put(FIELD_SPEC_NAME, new XMLFieldSpecBuilder());
        builderTable.put(BEAN_SPEC_NAME, new POSBeanSpecBuilder());
        builderTable.put("P", nullBuilder);
        builderTable.put("ARG", nullBuilder);
        
    }
}
