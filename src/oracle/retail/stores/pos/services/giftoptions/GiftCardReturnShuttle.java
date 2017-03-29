/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/giftoptions/GiftCardReturnShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:59 mszekely Exp $
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
 *    6    360Commerce 1.5         5/12/2006 5:25:29 PM   Charles D. Baker
 *         Merging with v1_0_0_53 of Returns Managament
 *    5    360Commerce 1.4         2/10/2006 11:06:43 AM  Deepanshu       CR
 *         6092: Sales Assoc sould be last 4 digits of Sales Assoc ID and not
 *         of Cashier ID on the recipt
 *    4    360Commerce 1.3         1/22/2006 11:45:07 AM  Ron W. Haight
 *         removed references to com.ibm.math.BigDecimal
 *    3    360Commerce 1.2         3/31/2005 4:28:17 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:21:56 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:11:15 PM  Robert Pearse   
 *
 *   Revision 1.7  2004/09/23 00:07:13  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.6  2004/04/09 16:56:01  cdb
 *   @scr 4302 Removed double semicolon warnings.
 *
 *   Revision 1.5  2004/03/16 18:30:47  cdb
 *   @scr 0 Removed tabs from all java source code.
 *
 *   Revision 1.4  2004/02/20 14:31:39  crain
 *   @scr 3814 Issue Gift Certificate
 *
 *   Revision 1.3  2004/02/12 16:50:25  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:11  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:16  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.2   Dec 12 2003 14:18:36   lzhao
 * add check quantity.
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 * 
 *    Rev 1.1   Nov 26 2003 09:28:10   lzhao
 * cleanup, use the methods in gift card utilities.
 * Resolution for 3371: Feature Enhancement:  Gift Card Enhancement
 * 
 *    Rev 1.0   Nov 21 2003 15:09:20   lzhao
 * Initial revision.
 * 
 *    Rev 1.0   Oct 30 2003 09:39:06   lzhao
 * Initial revision.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.giftoptions;

import org.apache.log4j.Logger;

import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.common.utility.BigDecimalConstants;
import oracle.retail.stores.pos.services.sale.SaleCargoIfc;

//--------------------------------------------------------------------------
/**
    This shuttle copies information from the cargo used
    in the GiftCardActivation service to the cargo used in the POS service. <p>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class GiftCardReturnShuttle implements ShuttleIfc
{                                       // begin class GiftCardReturnShuttle()
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -4539800079441600713L;

    /** 
        The logger to which log messages will be sent.
    **/
    protected static Logger logger = Logger.getLogger(oracle.retail.stores.pos.services.giftoptions.GiftCardReturnShuttle.class);
    /**
       revision number supplied by Team Connection
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * gift card cargo
     */
    protected SaleCargoIfc saleCargo = null;
    //----------------------------------------------------------------------
    /**
       Loads cargo from GiftCardActivation service. <P>
       <B>Pre-Condition(s)</B>
       <UL>
       <LI>Cargo will contain the retail transaction
       </UL>
       <B>Post-Condition(s)</B>
       <UL>
       <LI>
       </UL>
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void load(BusIfc bus)
    {                                   // begin load()

        saleCargo = (SaleCargoIfc) bus.getCargo();

    }                                   // end load()

    //----------------------------------------------------------------------
    /**
       Loads cargo for POS service. <P>
       <B>Pre-Condition(s)</B>
       <UL>
       <LI>Cargo will contain the retail transaction
       </UL>
       <B>Post-Condition(s)</B>
       <UL>
       <LI>
       </UL>
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void unload(BusIfc bus)
    {                                   // begin unload()
        SaleCargoIfc cargo = (SaleCargoIfc) bus.getCargo();
        if ( saleCargo.getItemQuantity().compareTo(BigDecimalConstants.ONE)==0 )
        {
            cargo.setTransaction(saleCargo.getTransaction());
            cargo.setPLUItem(saleCargo.getPLUItem());
            cargo.setItemQuantity(saleCargo.getItemQuantity());
            cargo.setItemScanned(saleCargo.isItemScanned());
            cargo.setSalesAssociate(saleCargo.getSalesAssociate());
        }
    }                                   // end unload()

    //----------------------------------------------------------------------
    /**
       Returns a string representation of this object.
       <P>
       @return String representation of object
    **/
    //----------------------------------------------------------------------
    public String toString()
    {                                   // begin toString()
        // result string
        String strResult = new String("Class:  GiftCardReloadReturnShuttle (Revision " +
                                      revisionNumber + ") @" + hashCode());
        // pass back result
        return(strResult);
    }                                   // end toString()

}                                       // end class GiftCardReturnShuttle
