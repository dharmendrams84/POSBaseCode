/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/signaturecapture/SignatureCaptureCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:29 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:30:05 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:20 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:15 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:51:59  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:30  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:20  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:07:10   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:02:30   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:47:52   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:25:54   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:13:36   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.signaturecapture;

// Java imports
import java.io.Serializable;

import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

//------------------------------------------------------------------------------
/**
    Interface to access signature capture data among common cargos.

    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public interface SignatureCaptureCargoIfc extends CargoIfc, Serializable
{
    /**
        revision number supplied by source-code-control system
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
 
    //--------------------------------------------------------------------------
    /**
        Sets the flag that tells the signature capture service whether to 
        display the signature verification screen.
        
        @param boolean verification flag
    **/
    //--------------------------------------------------------------------------
    public void setVerifySignature(boolean verify);
    
    
    //--------------------------------------------------------------------------
    /**
        Returns the flag that tells the signature capture service whether to 
        display the signature verification screen.
        
        @return boolean
    **/
    //--------------------------------------------------------------------------
    public boolean verifySignature();
    
    /**
        Sets the signature data.
        
        @param Serializable signature
    **/
    //--------------------------------------------------------------------------
    public void setSignature(Serializable signature);
    
    
    //--------------------------------------------------------------------------
    /**
        Returns the signature data.
        
        @return Serializable signature
    **/
    //--------------------------------------------------------------------------
    public Serializable getSignature();
    
} ///:~ end SignatureCaptureCargoIfc
