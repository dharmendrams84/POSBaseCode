/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/FinancialTotalsSummaryEntryIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:49 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/28/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *   4    360Commerce 1.3         4/25/2007 8:51:33 AM   Anda D. Cadar   I18N
 *        merge
 *   3    360Commerce 1.2         3/31/2005 4:28:11 PM   Robert Pearse   
 *   2    360Commerce 1.1         3/10/2005 10:21:42 AM  Robert Pearse   
 *   1    360Commerce 1.0         2/11/2005 12:11:05 PM  Robert Pearse   
 *
 *  Revision 1.5  2004/04/09 13:59:07  cdb
 *  @scr 4206 Cleaned up class headers for logs and revisions.
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;
// java imports
import java.io.Serializable;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;

//----------------------------------------------------------------------------
/**
     This is the interface for FinancialTotalsSummaryEntry. <P>
     @see FinancialTotalsSummaryEntry, FinancialTotals, CurrencyIfc, FinancialTotalsSummaryBean.
     @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//----------------------------------------------------------------------------
public interface FinancialTotalsSummaryEntryIfc extends Serializable
{                                       // begin interface FinancialTotalsSummaryEntryIfc
    /**
        revision number supplied by source-code-control system
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------------
    /**
        Retrieves type of entry. <P>
        <B>Pre-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        <B>Post-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        @return type of entry
    **/
    //----------------------------------------------------------------------------
    public String getType();

    //----------------------------------------------------------------------------
    /**
        Sets type of entry. <P>
        <B>Pre-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        <B>Post-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        @param value  type of entry
    **/
    //----------------------------------------------------------------------------
    public void setType(String value);

    //----------------------------------------------------------------------------
    /**
        Retrieves entered count amount. <P>
        <B>Pre-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        <B>Post-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        @return entered count amount
    **/
    //----------------------------------------------------------------------------
    public CurrencyIfc getEntered();

    //----------------------------------------------------------------------------
    /**
        Sets entered count amount. <P>
        <B>Pre-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        <B>Post-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        @param value  entered count amount
    **/
    //----------------------------------------------------------------------------
    public void setEntered(CurrencyIfc value);

    //----------------------------------------------------------------------------
    /**
        Retrieves expected count amount. <P>
        <B>Pre-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        <B>Post-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        @return expected count amount
    **/
    //----------------------------------------------------------------------------
    public CurrencyIfc getExpected();

    //----------------------------------------------------------------------------
    /**
        Sets expected count amount. <P>
        <B>Pre-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        <B>Post-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        @param value  expected count amount
    **/
    //----------------------------------------------------------------------------
    public void setExpected(CurrencyIfc value);

    //----------------------------------------------------------------------------
    /**
        Retrieves display expected indicator. <P>
        <B>Pre-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        <B>Post-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        @return display expected indicator
    **/
    //----------------------------------------------------------------------------
    public boolean getDisplayExpected();

    //----------------------------------------------------------------------------
    /**
        Sets display expected indicator. <P>
        <B>Pre-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        <B>Post-Condition(s)</B>
        <UL>
        <LI>none
        </UL>
        @param value  display expected indicator
    **/
    //----------------------------------------------------------------------------
    public void setDisplayExpected(boolean value);

}                                       // end interface FinancialTotalsSummaryEntryIfc
