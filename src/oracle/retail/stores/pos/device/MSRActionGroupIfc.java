/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/device/MSRActionGroupIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:38 mszekely Exp $
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
 *   3    360Commerce 1.2         3/31/2005 4:29:06 PM   Robert Pearse   
 *   2    360Commerce 1.1         3/10/2005 10:23:38 AM  Robert Pearse   
 *   1    360Commerce 1.0         2/11/2005 12:12:42 PM  Robert Pearse   
 *
 *  Revision 1.4  2004/03/16 18:30:47  cdb
 *  @scr 0 Removed tabs from all java source code.
 *
 *  Revision 1.3  2004/02/12 16:48:34  mcs
 *  Forcing head revision
 *
 *  Revision 1.2  2004/02/11 21:30:29  rhafernik
 *  @scr 0 Log4J conversion and code cleanup
 *
 *  Revision 1.1.1.1  2004/02/11 01:04:13  cschellenger
 *  updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   Oct 20 2003 09:40:38   rsachdeva
 * isMSROnline
 * Resolution for POS SCR-3411: Feature Enhancement:  Device and Database Status
 * 
 *    Rev 1.0   Aug 29 2003 15:51:20   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Jan 16 2003 18:08:36   vxs
 * Added variable string TYPE 
 * Resolution for POS SCR-1901: Pos Device Action/ActionGroup refactoring
 * 
 *    Rev 1.0   Jan 15 2003 16:32:42   vxs
 * Initial revision.
 * Resolution for POS SCR-1901: Pos Device Action/ActionGroup refactoring
 *Revision: /main/7 $
 * ===========================================================================
 */
package oracle.retail.stores.pos.device;

//foundation imports
import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.device.MSRModel;
import oracle.retail.stores.foundation.manager.ifc.device.DeviceActionGroupIfc;

//--------------------------------------------------------------------------
/**
The <code>MSRActionGroupIfc</code> defines the MSR specific
device operations available to POS applications.
<p>
@version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface MSRActionGroupIfc extends DeviceActionGroupIfc
{
    /**
       type of action group 
    **/
    public static final String TYPE = "MSRActionGroupIfc";
    //---------------------------------------------------------------------
    /**
        Activate the MSR session <P>
    **/
    //---------------------------------------------------------------------
    public void beginMSRSwipe() throws DeviceException;
    
    //---------------------------------------------------------------------
    /**
        Deactivate the MSR session <P>
    **/
    //---------------------------------------------------------------------
    public void endMSRSwipe() throws DeviceException;
    
    //---------------------------------------------------------------------
    /**
        Get the MSR model
        @return a MSR model
    **/
    //---------------------------------------------------------------------
    public MSRModel getMSRModel() throws DeviceException;
    //--------------------------------------------------------------------------
    /**
        Determines if MSR is online. <P>
        @return Boolean indicator that MSR is online.
    **/
    //--------------------------------------------------------------------------
    public Boolean isMSROnline() throws DeviceException;
}


