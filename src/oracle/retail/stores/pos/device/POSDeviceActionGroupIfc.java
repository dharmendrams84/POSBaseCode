/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/device/POSDeviceActionGroupIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:38 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:29:22 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:12 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:07 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:48:34  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:30:29  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:13  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:51:22   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.4   Jan 15 2003 16:31:44   vxs
 * Relocated HardTotals methods to newly created action group files for HardTotals
 * Resolution for POS SCR-1901: Pos Device Action/ActionGroup refactoring
 * 
 *    Rev 1.3   Jan 13 2003 15:42:38   vxs
 * Relocated majority of methods into specific device action group files.
 * Resolution for POS SCR-1901: Pos Device Action/ActionGroup refactoring
 * 
 *    Rev 1.2   Sep 10 2002 09:47:24   jriggins
 * Added get/set for new receiptLineSize property
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.1   Aug 14 2002 18:16:00   baa
 * format currency 
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.0   Apr 29 2002 15:44:22   msg
 * Initial revision.
 * 
 *    Rev 1.1   Mar 18 2002 22:59:04   msg
 * - updated copyright
 * 
 *    Rev 1.0   Mar 18 2002 11:14:30   msg
 * Initial revision.
 * 
 *    Rev 1.8   12 Mar 2002 17:55:12   jbp
 * deprecate methods for EYSPrintableDocument
 * Resolution for POS SCR-1553: cleanup dead code
 *
 *    Rev 1.7   04 Mar 2002 18:16:10   vxs
 * Added set/get for printBufferSize
 * Resolution for POS SCR-1442: Suspending a transaction with 123 chess sets causes the printer to stop responding, application stops as well
 *
 *    Rev 1.6   Feb 21 2002 08:37:06   mpm
 * Added ability to designate printer as franking-capable
 * Resolution for POS SCR-1134: Bypass franking when printer is not franking-capable
 *
 *    Rev 1.5   Feb 06 2002 07:43:50   mpm
 * Modified to detect simulated PIN pad session.
 * Resolution for POS SCR-1132: Disable Debit button when no PIN pad present
 *
 *    Rev 1.4   Dec 18 2001 10:47:14   mpm
 * Added support for receipt-printer-buffering setting.
 * Resolution for POS SCR-452: Make receipt-printer-buffering setting accessible from device script
 *
 *    Rev 1.3   Nov 07 2001 15:50:02   vxs
 * Modified LineDisplayItem() in POSDeviceActionGroup, so accommodating changes for other files as well.
 * Resolution for POS SCR-208: Line Display
 *
 *    Rev 1.2   26 Oct 2001 14:56:08   jbp
 * New receipt printing methodology
 * Resolution for POS SCR-221: Receipt Design Changes
 *
 *    Rev 1.1   Oct 09 2001 17:39:00   vxs
 * Added methods that support line display device.
 * Resolution for POS SCR-208: Line Display
 *
 *    Rev 1.0   Sep 21 2001 11:09:50   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:05:04   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.device;

//java imports
import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.DeviceTechnicianIfc;
import oracle.retail.stores.foundation.manager.ifc.device.DeviceActionGroupIfc;

//--------------------------------------------------------------------------
/**
    The <code>POSDeviceActionGroupIfc</code> defines the POS specific
    device operations available to POS applications.
    <p>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface POSDeviceActionGroupIfc extends DeviceActionGroupIfc
{

    //--------------------------------------------------------------------------
    /**
        Name to use in the DeviceScript.
     **/
    //--------------------------------------------------------------------------
    public static final String TYPE = "POSDeviceActionGroupIfc";

    public static final int LINE_DISPLAY_SIZE = 20;

    //---------------------------------------------------------------------
    /**
       Sets device technician reference.
       @see oracle.retail.stores.foundation.manager.ifc.device.DeviceActionGroupIfc#setDeviceTechnician
    **/
    //---------------------------------------------------------------------
    public void setDeviceTechnician(DeviceTechnicianIfc dt) throws DeviceException;

    //---------------------------------------------------------------------
    /**
       Gets device technician reference.
       @see oracle.retail.stores.foundation.manager.ifc.device.DeviceActionGroupIfc#getDeviceTechnician
       @exception DeviceException thrown if DeviceTechnician is null
    **/
    //---------------------------------------------------------------------
    public DeviceTechnicianIfc getDeviceTechnician() throws DeviceException;

    //---------------------------------------------------------------------
    /**
       @see oracle.retail.stores.foundation.manager.ifc.device.DeviceActionGroupIfc#setName
    **/
    //---------------------------------------------------------------------
    public void setName(String name);

    //---------------------------------------------------------------------
    /**
       @see oracle.retail.stores.foundation.manager.ifc.device.DeviceActionGroupIfc#getName
    **/
    //---------------------------------------------------------------------
    public String getName();

  
}
