/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/dailyoperations/till/tillopen/TillOpenCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:21 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:30:30 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:26:13 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:15:06 PM  Robert Pearse   
 *
 *   Revision 1.4  2004/07/07 18:27:11  dcobb
 *   @scr 1734 Wrong error message when attempt to open another till in reg acct.
 *   Fixed in CheckTillStatusSite. Moved deprecated TillOpenCargo to the deprecation tree and imported new TillCargo from _360commerce tree..
 *
 *   Revision 1.3  2004/02/12 16:48:04  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:29:48  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   08 Nov 2003 01:11:26   baa
 * cleanup -sale refactoring
 * 
 *    Rev 1.0   Nov 05 2003 22:53:02   cdb
 * Initial revision.
 * Resolution for 3430: Sale Service Refactoring
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.dailyoperations.till.tillopen;

import oracle.retail.stores.pos.services.common.TillCargoIfc;
import oracle.retail.stores.domain.financial.FinancialTotalsIfc;

//--------------------------------------------------------------------------
/**
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface TillOpenCargoIfc extends TillCargoIfc
{
    // potential error codes
    public static final String TILL_REGISTER_ERROR      = "AnotherTillOpenError";
    public static final String TILL_DRAWER_ERROR        = "NoCashDrawersError";
    public static final String TILL_CASHIER_ERROR       = "TillOpenCashierHasTillError";
    public static final String TILL_OTHER_CASHIER_ERROR = "TillOpenErrorNotSuspended";

    /**
        Returns till ID verified flag
    **/
    public boolean isTillIdVerified();

    /**
        Sets till ID verified flag
    **/
    public void setTillIdVerified(boolean value);

    //----------------------------------------------------------------------
    /**
        Returns the float count type. <P>
        @return The float count type.
    **/
    //----------------------------------------------------------------------
    public int getFloatCountType();

    //----------------------------------------------------------------------
    /**
        Sets the float count type. <P>
        @param  value  The float count type.
    **/
    //----------------------------------------------------------------------
    public void setFloatCountType(int value);

    //----------------------------------------------------------------------
    /**
        Returns the float financial totals. <P>
        @return The float financial totals.
    **/
    //----------------------------------------------------------------------
        public FinancialTotalsIfc getFloatTotals();

    //----------------------------------------------------------------------
    /**
        Sets the float financial totals. <P>
        @param value financial totals
    **/
    //----------------------------------------------------------------------
    public void setFloatTotals(FinancialTotalsIfc value);

    //----------------------------------------------------------------------
    /**
        Returns the current error screen name. <P>
        @return the current error screen name.
    **/
    //----------------------------------------------------------------------
        public String getErrorScreenName();

    //----------------------------------------------------------------------
    /**
        Sets the current error screen name. <P>
        @param the current error screen name.
    **/
    //----------------------------------------------------------------------
    public void setErrorScreenName(String value);

    //----------------------------------------------------------------------
    /**
        Returns the current error screen args. <P>
        @return the current error screen args.
    **/
    //----------------------------------------------------------------------
        public String[] getErrorScreenArgs();

    //----------------------------------------------------------------------
    /**
        Sets the current error screen args. <P>
        @param the current error screen args.
    **/
    //----------------------------------------------------------------------
    public void setErrorScreenArgs(String[] args);

    //----------------------------------------------------------------------
    /**
        Returns the function ID whose access is to be checked.
        @return int function ID 
    **/
    //----------------------------------------------------------------------
    public int getAccessFunctionID();
    
}
