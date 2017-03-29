/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/EMessageCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:53 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:27:56 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:21:16 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:47 PM  Robert Pearse   
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
 *    Rev 1.0   Aug 29 2003 15:54:22   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:34:58   msg
 * Initial revision.
 * 
 *    Rev 1.1   Mar 18 2002 23:09:04   msg
 * - updated copyright
 * 
 *    Rev 1.0   Mar 18 2002 11:22:34   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:14:10   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:06:22   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

// Java imports
import java.io.Serializable;

import oracle.retail.stores.domain.emessage.EMessageIfc;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

//------------------------------------------------------------------------------
/**
    Interface defines method signatures for accessing an EMessageIfc object.
    
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public interface EMessageCargoIfc extends CargoIfc,Serializable

{
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
        Sets the emessage reference.
        @param EMessageIfc object
    **/
    //--------------------------------------------------------------------------
    public void setEMessage(EMessageIfc emsg);
   
    //----------------------------------------------------------------------
    /**
        Returns the emessage reference, 
        @return EMessageIfc object
    **/
    //--------------------------------------------------------------------------
    public EMessageIfc getEMessage();
    
} ///:~ end EMessageCargoIfc
