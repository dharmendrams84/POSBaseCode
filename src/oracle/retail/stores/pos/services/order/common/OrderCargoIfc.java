/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/order/common/OrderCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:33 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:29:13 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:51 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:51 PM  Robert Pearse   
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
 *    Rev 1.0   Aug 29 2003 16:03:30   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.2   Jul 01 2003 11:53:20   bwf
 * Set correct tags in tag array.
 * Resolution for 2914: Order List message has tag around word <View>
 * 
 *    Rev 1.1   Aug 28 2002 10:08:20   jriggins
 * Introduced the OrderCargo.serviceType property complete with accessor and mutator methods.  Replaced places where service names were being compared (via String.equals()) to String constants in OrderCargoIfc with comparisons to the newly-created serviceType constants which are ints.
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.0   Apr 29 2002 15:13:02   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:41:06   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 24 2001 13:01:04   MPM
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:10:26   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.order.common;

// Java imports
import java.io.Serializable;

import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

//------------------------------------------------------------------------------
/**
    Interface to access Order data common to order services.

    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public interface OrderCargoIfc extends CargoIfc, Serializable
{
    /**
        revision number supplied by source-code-control system
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //These constants are used to identify the order sub-services.
    //Their values are used to set prompt arguments for the UI and 
    //also to determine printer text.  They no longer determine program
    //flow.  Please use the "_TYPE" contstants below.
    public static final String SERVICE_FILL     = "Fill";
    public static final String SERVICE_PICKUP   = "Pickup";
    public static final String SERVICE_CANCEL   = "Cancel";
    public static final String SERVICE_VIEW     = "View";
    public static final String SERVICE_PRINT    = "Print";

    //These constants are used to identify the order sub-services.
    //Their values are used to set the I18N service names as well as 
    //determine program flow.
    public static final int SERVICE_TYPE_NOT_SET  = -1;
    public static final int SERVICE_FILL_TYPE     = 0;
    public static final int SERVICE_PICKUP_TYPE   = 1;
    public static final int SERVICE_CANCEL_TYPE   = 2;
    public static final int SERVICE_VIEW_TYPE     = 3;
    public static final int SERVICE_PRINT_TYPE    = 4;
    
    // These constants are used to retrieve the text from the bundles
    public static final String SERVICE_FILL_TAG     = "ServiceFill";
    public static final String SERVICE_PICKUP_TAG   = "ServicePickup";
    public static final String SERVICE_CANCEL_TAG   = "ServiceCancel";
    public static final String SERVICE_VIEW_TAG     = "ServiceView";
    public static final String SERVICE_PRINT_TAG    = "ServicePrint";

    /**
        Array of Strings containing the list of bundle tags for the various 
        service names. Use the "_TYPE" constants to retrieve the appropriate tags.

        String servicePickupTag = SERVICE_NAME_TAG_LIST[SERVICE_PICKUP_TYPE];
    **/
    static final String SERVICE_NAME_TAG_LIST[] = 
      { SERVICE_FILL_TAG, SERVICE_PICKUP_TAG, SERVICE_CANCEL_TAG, SERVICE_VIEW_TAG, SERVICE_PRINT_TAG };

    /**
        Array of containing the list of default text for the various 
        service names. Use the "_TYPE" constants to retrieve the appropriate text.

        String serviceViewTag = SERVICE_NAME_TEXT_LIST[SERVICE_VIEW_TYPE];
    **/
    static final String SERVICE_NAME_TEXT_LIST[] = 
        { SERVICE_FILL, SERVICE_PICKUP, SERVICE_CANCEL, SERVICE_VIEW, SERVICE_PRINT };
        
    //--------------------------------------------------------------------------
    /**
        Adds a single OrderIfc reference to the cargo.

        @param the OrderIfc to add
    **/
    //--------------------------------------------------------------------------
    public void setOrder(OrderIfc order);
    
    //--------------------------------------------------------------------------
    /**
        Returns the OrderIfc reference held in cargo.

        @return OrderIfc
    **/
    //--------------------------------------------------------------------------
    public OrderIfc getOrder();
    
} ///:~ end OrderCargoIfc
