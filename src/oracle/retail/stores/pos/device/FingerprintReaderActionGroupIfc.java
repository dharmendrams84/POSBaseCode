/* ===========================================================================
* Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/device/FingerprintReaderActionGroupIfc.java /rgbustores_13.4x_generic_branch/1 2012/02/22 11:55:44 mkutiana Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    mkutiana  02/21/12 - disable the fingerprintreader (at login) when
 *                         parameter is set to NoFingerprint
 *    blarsen   02/24/11 - Fingerprint reader action group
 * 
 * 
 * ===========================================================================
 */
package oracle.retail.stores.pos.device;

import oracle.retail.stores.foundation.manager.device.DeviceException;
import oracle.retail.stores.foundation.manager.ifc.device.DeviceActionGroupIfc;

//--------------------------------------------------------------------------
/**
The <code>FingerprintActionGroupIfc</code> defines the FingerprintReader specific
device operations available to POS applications.
<p>
@version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface FingerprintReaderActionGroupIfc extends DeviceActionGroupIfc
{
    /**
       type of action group 
    **/
    public static final String TYPE = "FingerprintReaderActionGroupIfc";

    //--------------------------------------------------------------------------
    /**
        Determines if FingerprintReader is online. <P>
        @return Boolean indicator that FingerprintReader is online.
    **/
    //--------------------------------------------------------------------------
    public Boolean isFingerprintReaderOnline() throws DeviceException;

    //--------------------------------------------------------------------------
    /**
        Determines if fingerprint matches the supplied enrolled fingerprint template. <P>
        @return Boolean indicator that fingerprint is a match.
    **/
    //--------------------------------------------------------------------------
    public Boolean verifyFingerprintMatch(byte[] enrolledFingerprintTemplate, byte[] fingerprint) throws DeviceException;
    

    /**
     * Deactivates the FingerprintReader session
     */
    public void deactivateFingerprintReader();
}


