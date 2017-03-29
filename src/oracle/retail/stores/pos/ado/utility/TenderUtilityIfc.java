/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/utility/TenderUtilityIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:41 mszekely Exp $
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
 *    4    360Commerce 1.3         4/25/2007 8:52:47 AM   Anda D. Cadar   I18N
 *         merge
 *         
 *    3    360Commerce 1.2         3/31/2005 4:30:27 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:26:05 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:58 PM  Robert Pearse   
 *
 *   Revision 1.1  2004/07/21 22:55:33  bwf
 *   @scr 5963 (ServicesImpact) Moved getChangeOptions and calculateMaxCashChange out of
 *                     abstractRetailTransaction and into TenderUtility.  Also made calculateMaxCashChange
 *                     more polymorphic.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.utility;

import java.util.Map;

import oracle.retail.stores.pos.ado.tender.TenderTypeEnum;
import oracle.retail.stores.pos.ado.transaction.RetailTransactionADOIfc;
import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;

//--------------------------------------------------------------------------
/**

     $Revision: /rgbustores_13.4x_generic_branch/1 $
 **/
//--------------------------------------------------------------------------
public interface TenderUtilityIfc
{
    //----------------------------------------------------------------------
    /**
        This method calculates the maximum cash change amount based on the
        tender group rules.  We add up all cash equivalent tenders and then
        take the greatest of the parameter values of the non cash equivalent
        tenders and then add them.
        @param tenderGroupMap
        @return
    **/
    //----------------------------------------------------------------------
    public CurrencyIfc calculateMaxAllowableCashChange(Map tenderGroupMap);
    
    //----------------------------------------------------------------------
    /**
        Given the internal state of the current transaction, return an array of
        tenders which are valid for change.
        @param trans
        @return
    **/
    //----------------------------------------------------------------------
    public TenderTypeEnum[] getEnabledChangeOptions(RetailTransactionADOIfc trans);
}
