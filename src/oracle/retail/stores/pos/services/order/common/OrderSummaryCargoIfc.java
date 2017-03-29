/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/common/OrderSummaryCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:33 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:29:14 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:53 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:54 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:51:22  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:45  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:03:32   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:13:08   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:41:10   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 24 2001 13:01:04   MPM
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:10:24   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.common;

// Java imports
import java.io.Serializable;

import oracle.retail.stores.domain.order.OrderSummaryEntryIfc;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

//------------------------------------------------------------------------------
/**
    Defines the interface for accessing OrderSummmaryEntry data
    common to the order services.

    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public interface OrderSummaryCargoIfc extends CargoIfc, Serializable

{
    /**
        revision number supplied by source-code-control system
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    
    //----------------------------------------------------------------------------
    /**
        Sets the OrderSummaries.
        @param OrderSummaryEntryIfc[] - the new value for the property.
    */
    //----------------------------------------------------------------------------
    public void setOrderSummaries(OrderSummaryEntryIfc[] summaries);
    
    //----------------------------------------------------------------------------
    /**
        Returns the OrderSummaries.
        @return OrderSummaryEntryIfc[] property.
    */
    //----------------------------------------------------------------------------
    public OrderSummaryEntryIfc[]  getOrderSummaries();
 
    //--------------------------------------------------------------------------
    /**
        Removes an OrderSummaryEntryIfc from the cargo.

        @param OrderSummaryEntryIfc to remove
        @return true if object was in the cargo
    **/
    //--------------------------------------------------------------------------    
    public boolean removeSummary(OrderSummaryEntryIfc summary);
    
    //--------------------------------------------------------------------------
    /**
        Indicates whether cargo contains a given order summary.

        @param OrderSummaryEntryIfc to test
        @return true if summary was in the cargo
    **/
    //--------------------------------------------------------------------------        
    public boolean containsSummary(OrderSummaryEntryIfc summary);
        
    //--------------------------------------------------------------------------
    /**
        Removes all OrderSummaryEntryIfc objects from the cargo.
    **/
    //--------------------------------------------------------------------------
    public void clearSummaries();

    //--------------------------------------------------------------------------
    /**
        Indicates whether cargo contains one or more order summary entries.

        @return true if cargo contains summaries
    **/
    //--------------------------------------------------------------------------
    public boolean hasSummaries();

    //--------------------------------------------------------------------------
    /**
        Returns the number of order summaries held in the cargo.

        @return integer # of order summaries held by the cargo
    **/
    //--------------------------------------------------------------------------
    public int countSummaries();
                    
    //--------------------------------------------------------------------------
    /**
        Gets the selected OrderSummaryEntry.   
    **/
    //--------------------------------------------------------------------------
    public OrderSummaryEntryIfc getSelectedSummary();

    //----------------------------------------------------------------------
    /**
        Sets the selected OrderSummaryEntry.
    **/
    //--------------------------------------------------------------------------
    public void setSelectedSummary(OrderSummaryEntryIfc value);
    
} ///:~ end OrderSummaryCargoIfc
