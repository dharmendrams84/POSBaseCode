/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/ServiceNameCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:52 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:29:56 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:13 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:11 PM  Robert Pearse   
 *
 *   Revision 1.4  2004/07/22 13:05:56  khassen
 *   @scr 2473 When Filling an  Order, the Location is missing on E journal
 *
 *   Revision 1.3  2004/02/12 16:49:08  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:38:50  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:54:48   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:35:50   msg
 * Initial revision.
 * 
 *    Rev 1.1   Mar 18 2002 23:10:24   msg
 * - updated copyright
 * 
 *    Rev 1.0   Mar 18 2002 11:23:22   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:13:52   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:06:04   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

// Java imports
import java.io.Serializable;

import oracle.retail.stores.foundation.tour.application.tourcam.TourCamIfc;

//------------------------------------------------------------------------------
/**
    Data common to order services.

    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public interface ServiceNameCargoIfc extends TourCamIfc, Serializable

{
    /**
        revision number supplied by source-code-control system
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    
    //----------------------------------------------------------------------------
    /**
        Sets the Service name property.
        @param name - the new value for the property.
    */
    //----------------------------------------------------------------------------
    public void setServiceName(String name);
    
    //----------------------------------------------------------------------------
    /**
        Returns the Service name.
        @return service name property.
    */
    //----------------------------------------------------------------------------
    public String getServiceName();
    
    //----------------------------------------------------------------------------
    /**
        Sets the Service type property.
        @param type - the new value for the property.
    */
    //----------------------------------------------------------------------------
    public void setServiceType(int type);
    
    //----------------------------------------------------------------------------
    /**
        Returns the Service type.
        @return service type property.
    */
    //----------------------------------------------------------------------------
    public int getServiceType();    
        
    
} ///:~ end ServiceNameCargoIfc
