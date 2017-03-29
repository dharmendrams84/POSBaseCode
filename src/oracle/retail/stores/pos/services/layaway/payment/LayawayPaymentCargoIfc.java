/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/layaway/payment/LayawayPaymentCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:13 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:28:50 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:03 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:16 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:50:53  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:22  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:17  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:00:50   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:20:10   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:35:30   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:21:38   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:08:44   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.layaway.payment; 

// foundation imports

// domain imports
import oracle.retail.stores.domain.financial.LayawayIfc;
import oracle.retail.stores.domain.financial.PaymentIfc;

// quarry imports

//--------------------------------------------------------------------------
/**
    Interface for properties required for FindLayawayCargo.
    <p>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface LayawayPaymentCargoIfc
{
    /**
        revision number supplied by source-code-control system
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //--------------------------------------------------------------------------
    /**
        Gets the current layaway.  <p>
        @return layaway
    **/
    //--------------------------------------------------------------------------
    public LayawayIfc getLayaway();

    //----------------------------------------------------------------------
    /**
        Sets the current layaway. <P>
        @param layaway
    **/
    //--------------------------------------------------------------------------
    public void setLayaway(LayawayIfc value);

    //--------------------------------------------------------------------------
    /**
        Gets the current payment object.  <p>
        @return payment
    **/
    //--------------------------------------------------------------------------
    public PaymentIfc getPayment();

    //----------------------------------------------------------------------
    /**
        Sets the payment object. <P>
        @param payment
    **/
    //--------------------------------------------------------------------------
    public void setPayment(PaymentIfc value);
}
