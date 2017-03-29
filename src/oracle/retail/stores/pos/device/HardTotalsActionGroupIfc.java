/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/device/HardTotalsActionGroupIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:38 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:28:19 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:22:00 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:11:17 PM  Robert Pearse   
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
 *    Rev 1.0   Aug 29 2003 15:51:16   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Jan 15 2003 16:32:40   vxs
 * Initial revision.
 * Resolution for POS SCR-1901: Pos Device Action/ActionGroup refactoring
 *Revision: /main/7 $
 * ===========================================================================
 */
package oracle.retail.stores.pos.device;

//java imports
import java.io.Serializable;

import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.device.DeviceActionGroupIfc;

public interface HardTotalsActionGroupIfc extends DeviceActionGroupIfc
{

    public static final String TYPE = "HardTotalsActionGroupIfc";

    //---------------------------------------------------------------------
    /**
       Writes Hard Totals <P>
       @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#writeHardTotals
    **/
    //---------------------------------------------------------------------
    public void writeHardTotals(Serializable htObj) throws DeviceException;

    //--------------------------------------------------------------------------
    /**
        This operation writes to hard totals.
        <P>
       <B>Pre-conditions</B>
       <UL>
       <LI>DeviceTechnician is configured.
       <LI>Hard totals are available.
       <LI>A financial totals domain object is passed in.
       </UL>
       <B>Post-conditions</B>
       <UL>
       <LI>The contents of the object have been written to hard totals
       </UL>
       @param  htObj what will be written out
       @exception DeviceException is thrown if the hard totals cannot
       be written.  Nested error codes allow the application developer detect
       the specific fault.
       @see oracle.retail.stores.domain.transaction.RetailTransactionIfc
    **/
    //--------------------------------------------------------------------------
    public void writeHardTotals(Serializable htObj, String nameBase) throws DeviceException;

    //--------------------------------------------------------------------------
    /**
        This operation reads from the hard totals.
        <P>
       <B>Pre-conditions</B>
       <UL>
       <LI>DeviceTechnician is configured.
       <LI>Hard totals are available.
       <LI>A financial totals domain object is passed in.
       </UL>
       <B>Post-conditions</B>
       <UL>
       <LI>The contents of the object have been read from hard totals
       </UL>
       @param  none
       @return hard totals object
       @exception DeviceException is thrown if the hard totals cannot
       be read.  Nested error codes allow the application developer detect
       the specific fault.
       @see oracle.retail.stores.domain.transaction.RetailTransactionIfc
    **/
    //--------------------------------------------------------------------------
    public Serializable readHardTotals() throws DeviceException;

    //--------------------------------------------------------------------------
    /**
        This operation reads from the hard totals.
        <P>
       <B>Pre-conditions</B>
       <UL>
       <LI>DeviceTechnician is configured.
       <LI>Hard totals are available.
       <LI>A financial totals domain object is passed in.
       </UL>
       <B>Post-conditions</B>
       <UL>
       <LI>The contents of the object have been read from hard totals
       </UL>
       @param  none
       @return hard totals object
       @exception DeviceException is thrown if the hard totals cannot
       be read.  Nested error codes allow the application developer detect
       the specific fault.
       @see oracle.retail.stores.domain.transaction.RetailTransactionIfc
    **/
    //--------------------------------------------------------------------------
    public Serializable readHardTotals(String nameBase) throws DeviceException;

    //---------------------------------------------------------------------
    /**
        Gets hard-totals device name.  This should only be used when
        operating with a file-based device. <P>
        @param none
        @return Serializable hard-totals device name
        @exception DeviceException is thrown if error occurs
    **/
    //---------------------------------------------------------------------
    public Serializable getHardTotalsDeviceName() throws DeviceException;

    //---------------------------------------------------------------------
    /**
        Sets hard-totals device name.  This should only be used when
        operating with a file-based device. If null or empty value
        is passed, it is ignored. <P>
        @param value new device name
        @exception DeviceException is thrown if error occurs
    **/
    //---------------------------------------------------------------------
    public void setHardTotalsDeviceName(Serializable value) throws DeviceException;
}
