/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/tender/group/TenderGroupADOIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:43 mszekely Exp $
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
 *    5    360Commerce 1.4         3/31/2008 1:46:50 PM   Mathews Kochummen
 *         forward port from v12x to trunk
 *    4    360Commerce 1.3         4/25/2007 8:52:52 AM   Anda D. Cadar   I18N
 *         merge
 *         
 *    3    360Commerce 1.2         3/31/2005 4:30:24 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:59 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:53 PM  Robert Pearse   
 *
 *   Revision 1.6  2004/08/31 19:12:35  blj
 *   @scr 6855 - cleanup gift card credit code and fix defects found by PBY
 *
 *   Revision 1.5  2004/07/21 22:55:33  bwf
 *   @scr 5963 (ServicesImpact) Moved getChangeOptions and calculateMaxCashChange out of
 *                     abstractRetailTransaction and into TenderUtility.  Also made calculateMaxCashChange
 *                     more polymorphic.
 *
 *   Revision 1.4  2004/04/07 20:19:10  epd
 *   @scr 4322 Updates for tender invariant work
 *
 *   Revision 1.3  2004/03/16 18:30:46  cdb
 *   @scr 0 Removed tabs from all java source code.
 *
 *   Revision 1.2  2004/02/12 16:47:56  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.3   Feb 05 2004 13:20:38   rhafernik
 * No change.
 * 
 *    Rev 1.2   Nov 14 2003 16:44:40   epd
 * new dev
 * 
 *    Rev 1.1   Nov 14 2003 11:09:38   epd
 * refactored some void functionality to be more general.
 * 
 *    Rev 1.0   Nov 04 2003 11:13:54   epd
 * Initial revision.
 * 
 *    Rev 1.2   Oct 21 2003 10:28:40   epd
 * Added functionality for Delete Tender functionality from Tender Options screens
 * 
 *    Rev 1.1   Oct 20 2003 15:20:24   epd
 * Added code to check for overtender
 * 
 *    Rev 1.0   Oct 17 2003 12:34:30   epd
 * Initial revision.
 *   
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.tender.group;

import java.util.HashMap;
import java.util.List;

import oracle.retail.stores.pos.ado.security.OverridableIfc;
import oracle.retail.stores.pos.ado.tender.TenderADOIfc;
import oracle.retail.stores.pos.ado.tender.TenderException;
import oracle.retail.stores.pos.ado.tender.TenderTypeEnum;
import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;

/**
 *
 * 
 */
public interface TenderGroupADOIfc extends OverridableIfc
{
    /**
     * Attempts to add a tender to the tender group.  This method should
     * invoke validation on the tender that we're trying to add.
     * @param tender The tender to add to the group.
     * @throws TenderException Thrown when we cannot add the tender to the group
     */
    public void addTender(TenderADOIfc tender) throws TenderException;
    
    /**
     * Adds a tender without validation.  This is ONLY intended for use
     * when the tender has already been validated and an ADO tender
     * needs to be constructed from an RDO.  For example, in VOID, an ADO txn
     * is constructed from a retrieved RDO txn.  Validation should not be invoked
     * in this case.
     * @param tender The tender to add to the group.
     */
    public void addTenderNoValidation(TenderADOIfc tender);
    
    /**
     * Each tender group must implement this in it's own specific manner. 
     * All tender limit validation is intended to take place here.
     * Note: The limits should be checked for the tender in the context
     *       with the other like tenders in this group.
     * @param HashMap the tender attributes map
     * @param balanceDue The balance due for the transaction to be used to check against max
     *                   change limits
     * @throws TenderException
     */
    public void validateLimits(HashMap tenderAttributes, CurrencyIfc balanceDue) throws TenderException;    
    
    /**
     * This method tests for an invalid overtender and throws an exception if
     * it occurs.  If the current tender type is invalid for overtender, it compares 
     * the current amount to the balance due.
     * @param tenderAttributes The information about the current tender being tested
     * @param balanceDue The balance due on the transaction.
     * @param overtenderLimit Amount that a normally non-overtenderable tender can be overtendered.
     * @throws TenderException thrown when an illegal overtender occurs.
     */
    public void validateOvertender(HashMap tenderAttributes, 
                                   CurrencyIfc balanceDue,
                                   CurrencyIfc overtenderLimit) throws TenderException;    
    
    /**
     * Each tender group must implement this in it's own specific manner. 
     * All tender limit validation is intended to take place here.
     * Note: The limits should be checked for the tender in the context
     *       with the other like tenders in this group.
     * @param HashMap the tender attributes map
     * @param hasReceipt Indicates whether the transaction for this tender
     *                   is a return with a receipt or not
     * @parm retrieved Indicates whether the transaction of this tender is
     * 					a return with its original transaction retrieved or not                  
     * @throws TenderException
     */
    public void validateRefundLimits(HashMap tenderAttributes, boolean hasReceipt, boolean retrieved) throws TenderException;    
    
    /**
     * Attempts to remove a tender from the tender group.
     * @param tenderToRemove The tender to remove to the group.
     * @throws TenderException Thrown when we cannot remove this tender
     */
    public void removeTender(TenderADOIfc tenderToRemove);
    
    /**
     * Returns an array of tenders for this group
     * @return The tenders belonging to this group
     */
    public TenderADOIfc[] getTenders(); 
    
    /**
     * Get the type of tender a particular group represents.
     * @return The tender type a group aggregates.
     */
    public TenderTypeEnum getGroupType();
    
    /**
     * Get the type of tender a particular group should be voided with.
     * @return The tender type a group aggregates.
     */
    public TenderTypeEnum getVoidType();
    
    /**
     * Retrieves the number of tenders in the group.
     * @return The number of tenders in this group.
     */
    public int getTenderCount();
    
    /**
     * Retrieves the tender total for the group.
     * @return 
     */
    public CurrencyIfc getTenderTotal();
    
    /**
     * Retrives a new tender group containing voided/reversed tenders
     * for the current group.
     * @return a tenderGroup containing voided tenders
     */
    public TenderGroupADOIfc processVoid();
    
    /**
     * There are times (Void, for example) where it
     * may be necessary to combine tender groups of like type.
     * This method takes the tenders from the tender group argument
     * and adds them to this tender group
     * @param tenderGroup The group to be added to the current group
     */
    public void combineGroups(TenderGroupADOIfc tenderGroup);
    
    /**
     * Accumulates overtender limit for each individual tender
     * and returns the total overtender limit value for this 
     * particular group type.
     * @return Accumulated overtender limit for this tender group.
     */
    public CurrencyIfc getOvertenderLimit();
    
    //----------------------------------------------------------------------
    /**
        This method checks if the tender is overtenderable and is a cash
        equivalent tender.  Non cash equivalent tender will either not
        be used in calculations or will override this method. 
        @return
    **/
    //----------------------------------------------------------------------
    public CurrencyIfc getMaxCashChange();
    
    //----------------------------------------------------------------------
    /**
        This method determines if you should use the full amount for cash
        change.  Override this method for tenders where you want to use the
        full amount.
        @return
    **/
    //----------------------------------------------------------------------
    public boolean isCashEquivalentTender();
    
    /**
     * Pulls all the tenders from a named from and adds them to a supplied list.
     * 
     * @param tenderList
     * @param group
     */
    public List pullAuthPendingTendersFromGroup(List tenderList, TenderGroupADOIfc group);
}
