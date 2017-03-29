/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/main/MainCargoShuttleReturn.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:12 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    npoola    08/11/10 - Actual register is used for training mode instead of
 *                         OtherRegister.
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *   3    360Commerce 1.2         3/31/2005 4:28:59 PM   Robert Pearse   
 *   2    360Commerce 1.1         3/10/2005 10:23:24 AM  Robert Pearse   
 *   1    360Commerce 1.0         2/11/2005 12:12:31 PM  Robert Pearse   
 *
 *  Revision 1.10  2004/09/23 00:07:13  kmcbride
 *  @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *  Revision 1.9  2004/08/10 21:50:10  dcobb
 *  @scr 6792 TillStatusSite - cargo contains outdated register/till data
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.main;

import org.apache.log4j.Logger;

import oracle.retail.stores.pos.ado.context.ContextFactory;
import oracle.retail.stores.pos.ado.context.TourADOContext;
import oracle.retail.stores.pos.ado.store.RegisterADO;
import oracle.retail.stores.pos.services.common.AbstractFinancialCargoIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;

//--------------------------------------------------------------------------
/**
     This shuttle copies the contents of one abstract financial cargo to
     another. <P>
     @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class MainCargoShuttleReturn implements ShuttleIfc
{                                       
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = 2681341970087502018L;

     /** 
          The logger to which log messages will be sent.
     **/
     protected Logger logger = Logger.getLogger(getClass());
     /**
         revision number supplied by Team Connection
     **/
     public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    
     protected AbstractFinancialCargoIfc savedCargo;
        
     //----------------------------------------------------------------------
     /**
         Copies information from the cargo used in the service.  <P>
         @param bus Service Bus
     **/
     //----------------------------------------------------------------------
     public void load(BusIfc bus)
     {          
        // get cargo reference and extract attributes
        savedCargo = (AbstractFinancialCargoIfc) bus.getCargo();
     }                                   

     //----------------------------------------------------------------------
     /**
         Copies information to the cargo used in the service. <P>
         @param bus Service Bus
     **/
     //----------------------------------------------------------------------
     public void unload(BusIfc bus)
     {                                   
        setupContext(bus);
                
        // get cargo reference and set attributes
        MainCargo cargo = (MainCargo) bus.getCargo();
        cargo.setCustomerInfo(savedCargo.getCustomerInfo());
        
        RegisterADO registerADO = cargo.getRegisterADO();

        registerADO.fromLegacy(savedCargo.getRegister());
        registerADO.getStoreADO().fromLegacy(savedCargo.getStoreStatus());
        
        cargo.setTenderLimits(savedCargo.getTenderLimits());
        cargo.setOperator(savedCargo.getOperator());
        cargo.setLastReprintableTransactionID(savedCargo.getLastReprintableTransactionID());
     }                                   

    /**
     * Updates the ContextFactory with the current bus
     * @param bus
     */
    protected void setupContext(BusIfc bus)
    {
        // Establish the ADOContext
        TourADOContext cxt = new TourADOContext(bus);
        ContextFactory.getInstance().setContext(cxt);
    }


     //----------------------------------------------------------------------
     /**
         Returns a string representation of this object. <P>
         @return String representation of object
     **/
     //----------------------------------------------------------------------
     public String toString()
     {                                   // begin toString()
          // result string
          String strResult = new String("Class:  MainCargoShuttleReturn (Revision " +
                                                  getRevisionNumber() +
                                                  ") @" + hashCode());
          // pass back result
          return(strResult);
     }                                   // end toString()

     //----------------------------------------------------------------------
     /**
         Returns the revision number of the class. <P>
         @return String representation of revision number
     **/
     //----------------------------------------------------------------------
     public String getRevisionNumber()
     {                                   // begin getRevisionNumber()
          // return string
          return(revisionNumber);
     }                                   // end getRevisionNumber()

}                                       // end class FinancialCargoShuttle
