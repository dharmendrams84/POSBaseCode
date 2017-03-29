/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/WriteHardTotalsCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:52 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:30:49 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:26:53 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:15:40 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:49:08  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 20:56:28  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:54:54   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:35:30   msg
 * Initial revision.
 * 
 *    Rev 1.1   Mar 18 2002 23:10:48   msg
 * - updated copyright
 * 
 *    Rev 1.0   Mar 18 2002 11:23:34   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:14:00   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:06:00   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

//foundation imports
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.financial.StoreStatusIfc;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

//------------------------------------------------------------------------------
/**
    This interface defines the methods a class must implement in order to be
    used by the WriteHardTotals site.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public interface WriteHardTotalsCargoIfc extends CargoIfc
{
    /**
        revision number supplied by source-code-control system
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
        Returns the store status. <P>
        @return The store status.
    **/
    //----------------------------------------------------------------------
    public StoreStatusIfc getStoreStatus();

    //----------------------------------------------------------------------
    /**
        Returns the register object. <P>
        @return The register object.
    **/
    //----------------------------------------------------------------------
    public RegisterIfc getRegister();
}

