/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/DetailStatusBean.java /rgbustores_13.4x_generic_branch/2 2011/07/01 11:53:12 blarsen Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   06/30/11 - SIGNATURE_CAPTURE and PIN_PAD status were replaced
 *                         with FINANCIAL_NETWORK status as part of the advance
 *                         payment foundation feature.
 *    cgreene   05/28/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 * 3    360Commerce 1.2         3/31/2005 4:27:44 PM   Robert Pearse   
 * 2    360Commerce 1.1         3/10/2005 10:20:56 AM  Robert Pearse   
 * 1    360Commerce 1.0         2/11/2005 12:10:34 PM  Robert Pearse   
 *
 *Revision 1.4  2004/03/16 17:15:22  build
 *Forcing head revision
 *
 *Revision 1.3  2004/03/16 17:15:17  build
 *Forcing head revision
 *
 *Revision 1.2  2004/02/11 20:56:26  rhafernik
 *@scr 0 Log4J conversion and code cleanup
 *
 *Revision 1.1.1.1  2004/02/11 01:04:22  cschellenger
 *updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   Oct 21 2003 11:40:28   rsachdeva
 * Device Status
 * Resolution for POS SCR-3411: Feature Enhancement:  Device and Database Status
 * 
 *    Rev 1.0   Aug 29 2003 16:10:08   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.6   Jul 29 2003 14:51:06   baa
 * check for null model
 * Resolution for 2967: Device/Database status giving incorrect information
 * 
 *    Rev 1.5   Jul 29 2003 12:14:54   baa
 * offline/online status
 * Resolution for 2967: Device/Database status giving incorrect information
 * 
 *    Rev 1.4   12 Jul 2003 23:05:00   baa
 * Remove system outs
 * 
 *    Rev 1.3   25 Jun 2003 23:36:48   baa
 * uptate register/device status
 * 
 *    Rev 1.2   Apr 09 2003 17:50:28   baa
 * I18n database conversion
 * Resolution for POS SCR-1866: I18n Database  support
 * 
 *    Rev 1.1   Aug 14 2002 18:17:18   baa
 * format currency 
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.0   Apr 29 2002 14:53:16   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:54:04   msg
 * Initial revision.
 * 
 *    Rev 1.4   Feb 23 2002 15:04:12   mpm
 * Re-started internationalization initiative.
 * Resolution for POS SCR-351: Internationalization
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

// Java imports
import java.util.Enumeration;
import java.util.Hashtable;

import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.pos.ui.behavior.OnlineDeviceStatusListener;

//----------------------------------------------------------------------------
/**
   The detail status bean displays the list status items.
   @version $Revision: /rgbustores_13.4x_generic_branch/2 $
*/
//----------------------------------------------------------------------------
public class DetailStatusBean
extends ListBean
implements OnlineDeviceStatusListener
{
    /**
        revision number supplied by Team Connection
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";
    /**
        printer tag
    **/
    public static final String PRINTER_TAG = "Printer";
    /**
        cashdrawer tag
    **/
    public static final String CASHDRAWER_TAG = "CashDrawer";
    /**
        check label  
    **/
    public static final String CHECK_TAG = "Check";
    /**
        debit tag
    **/
    public static final String DEBIT_TAG = "Debit";
    /**
        credit tag
    **/
    public static final String CREDIT_TAG = "Credit";
    /**
        financial network capture tag
    **/
    public static final String FINANCIAL_NETWORK_TAG = "FinancialNetwork";
    
    //---------------------------------------------------------------------
    /**
     * Constructor
     */
    //---------------------------------------------------------------------
    public DetailStatusBean()
    {
        super();
        setName("DetailStatusBean");
    }


    //---------------------------------------------------------------------
    /**
     * This is the method implemented for OnlineDeviceStatusListener
     */
    //---------------------------------------------------------------------
    public void onlineDeviceStatusChanged(Hashtable onlineStatusHash)
    {
        if (beanModel == null)
        {
            beanModel = new DetailStatusBeanModel();
        }
        DetailStatusBeanModel model = (DetailStatusBeanModel)beanModel;
        
        // get the keys and the corresponding status'
        Enumeration idEnum = onlineStatusHash.keys();
        
        
        // iterate through the device IDs and convert them to device names
        while (idEnum.hasMoreElements())
        {
            Integer deviceId = (Integer)idEnum.nextElement();
            String deviceName = "";
            switch(deviceId.intValue())
            {
               //Devices Based on Last Known Status
               case POSUIManagerIfc.PRINTER_STATUS:
                    deviceName = PRINTER_TAG;
                    break;
               case POSUIManagerIfc.CASHDRAWER_STATUS:
                    deviceName = CASHDRAWER_TAG;
                    break;
               case POSUIManagerIfc.CHECK_STATUS:
                    deviceName = CHECK_TAG;
                    break;
               case POSUIManagerIfc.DEBIT_STATUS:
                    deviceName = DEBIT_TAG;
                    break;
               case POSUIManagerIfc.CREDIT_STATUS:
                    deviceName = CREDIT_TAG;
                    break;
               case POSUIManagerIfc.FINANCIAL_NETWORK_STATUS:
                    deviceName = FINANCIAL_NETWORK_TAG;
                    break;
            }
            Boolean tempStatus = (Boolean)onlineStatusHash.get(deviceId);
            int deviceStatus = StatusDisplayObject.STATUS_OFFLINE;
            if (tempStatus.booleanValue())
            {
                deviceStatus = StatusDisplayObject.STATUS_ONLINE;
            }
            model.setDeviceStatus(deviceName, deviceStatus);
        }
        updateBean();
    }
    
    

    //---------------------------------------------------------------------
    /**
        Method to default display string function. <P>
        @return String representation of object
    **/
    //---------------------------------------------------------------------
    public String toString()
    {                                   // begin toString()
       // result string
        String strResult = new String("Class: DetailStatusBean (Revision " +
                                      getRevisionNumber() +
                                      ")" +
                                      hashCode());
        // pass back result
        return(strResult);
    }                                  // end toString()

    //---------------------------------------------------------------------
    /**
        Retrieves the Team Connection revision number. <P>
        @return String representation of revision number
    **/
    //---------------------------------------------------------------------
    public String getRevisionNumber()
    {                                   // begin getRevisionNumber()
        // return string
        return(Util.parseRevisionNumber(revisionNumber));
    }                                  // end getRevisionNumber()

    //---------------------------------------------------------------------
    /**
     * main entrypoint - starts the part when it is run as an application
     * @param args java.lang.String[]
     */
    //---------------------------------------------------------------------
    public static void main(java.lang.String[] args)
    {
        UIUtilities.setUpTest();
        DetailStatusBean bean = new DetailStatusBean();
        UIUtilities.doBeanTest(bean);
    }
}
