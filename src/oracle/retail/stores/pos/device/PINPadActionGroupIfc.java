/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/device/PINPadActionGroupIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:38 mszekely Exp $
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
 *   4    360Commerce 1.3         12/14/2007 8:59:59 AM  Alan N. Sinton  CR
 *        29761: Removed non-PABP compliant methods and modified card RuleIfc
 *        to take an instance of EncipheredCardData.
 *   3    360Commerce 1.2         3/31/2005 4:29:21 PM   Robert Pearse   
 *   2    360Commerce 1.1         3/10/2005 10:24:05 AM  Robert Pearse   
 *   1    360Commerce 1.0         2/11/2005 12:13:03 PM  Robert Pearse   
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
 *    Rev 1.0   Aug 29 2003 15:51:22   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Jan 13 2003 15:38:56   vxs
 * Assigned value for String TYPE
 * Resolution for POS SCR-1901: Pos Device Action/ActionGroup refactoring
 * 
 *    Rev 1.0   Jan 08 2003 15:09:40   vxs
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
//--------------------------------------------------------------------------
/**
The <code>PINPadActionGroup</code> defines specific device operations
available to POS applications.
@version $Revision: /rgbustores_13.4x_generic_branch/1 $
@see oracle.retail.stores.pos.device.PosDeviceActionGroupIfc
**/
//--------------------------------------------------------------------------
public interface PINPadActionGroupIfc extends DeviceActionGroupIfc
{
    public static final String TYPE = "PINPadActionGroupIfc";
    //---------------------------------------------------------------------
    /**
       Initiates an EFT transaction  <P>
       @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#beginEFTTransaction
    **/
    //---------------------------------------------------------------------
    public void beginEFTTransaction(String accountNumber,long amount,
                                    String merchantID, String terminalID,
                                    byte[] track2data,
                                    int transactionType, String pinpadSystem, int transactionHost) throws DeviceException;
    //---------------------------------------------------------------------
    /**
       Enables PINEntry on the PINPad.  <P>
       @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#enablePINEntry
    **/
    //---------------------------------------------------------------------
    public void enablePINEntry() throws DeviceException;
    //---------------------------------------------------------------------
    /**
       Finalizes an EFT transaction.  <P>
       @see oracle.retail.stores.pos.device.POSDeviceActionGroupIfc#endEFTTransaction
    **/
    //---------------------------------------------------------------------
    public void endEFTTransaction(int completionCode) throws DeviceException;
    //---------------------------------------------------------------------
    /**
       Returns true if PINPad is simulated.   <P>
       @return Serializable Boolean true if PINPad is simulated, false otherwise
       @exception DeviceException is thrown if error occurs
    **/
    //---------------------------------------------------------------------
    public Serializable isPinPadSimulated() throws DeviceException;
}
