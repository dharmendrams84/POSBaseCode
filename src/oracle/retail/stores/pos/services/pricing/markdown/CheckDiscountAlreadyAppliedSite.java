/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/pricing/markdown/CheckDiscountAlreadyAppliedSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:00 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - updating deprecated names
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:24 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:07 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:09:54 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/03/17 23:03:10  dcobb
 *   @scr 3911 Feature Enhancement: Markdown
 *   Code review modifications.
 *
 *   Revision 1.1  2004/03/03 23:10:45  dcobb
 *   @scr 3911 Feature Enhancement: Markdown
 *
 *   Revision 1.2  2004/02/26 18:26:20  cdb
 *   @scr 3588 Item Discounts no longer have the Damage
 *   selection. Use the Damage Discount flow to apply Damage
 *   Discounts.
 *
 *   Revision 1.1  2004/02/24 22:36:30  cdb
 *   @scr 3588 Added ability to check for previously existing
 *   discounts of the same type and capture the prorate user
 *   selection. Also migrated item discounts to validate in
 *   the percent and amount entered aisle to be consistent
 *   with employee discounts.
 *
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.pricing.markdown;

import oracle.retail.stores.domain.discount.DiscountRuleConstantsIfc;
import oracle.retail.stores.domain.discount.ItemDiscountStrategyIfc;
import oracle.retail.stores.pos.services.pricing.AbstractCheckDiscountAlreadyAppliedSite;

//--------------------------------------------------------------------------
/**
    This site determines if discounts of this type have already been applied.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class CheckDiscountAlreadyAppliedSite extends AbstractCheckDiscountAlreadyAppliedSite
{
    /** revision number supplied by version control **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
     *   Returns true if item discount array has a non-damage item discount.
     *   @param sgyArray The array of ItemDiscountStrategy's to search
     *   @return true if item discount array has a non-damage item discount.
     */
    //----------------------------------------------------------------------
    public boolean hasSameTypeDiscount(ItemDiscountStrategyIfc[] sgyArray)
    {
        boolean hasSameType = false;
        
        // retrieve the discount that is not an advanced pricing discount
        if (sgyArray != null && sgyArray.length >0)
        {
            for (int i = 0; i < sgyArray.length; i++)
            {
                ItemDiscountStrategyIfc discount = sgyArray[i];
                // checks that the discount is not APR and
                // that the discount matches the operation.
                if (!discount.isAdvancedPricingRule() 
                    && discount.getTypeCode() == DiscountRuleConstantsIfc.DISCOUNT_APPLICATION_TYPE_ITEM 
                    && discount.getAccountingMethod() == DiscountRuleConstantsIfc.ACCOUNTING_METHOD_MARKDOWN 
                    && discount.getAssignmentBasis() == DiscountRuleConstantsIfc.ASSIGNMENT_MANUAL  
                    && !discount.isDamageDiscount())
                {
                    hasSameType = true;
                    break;
                }
            }
        }
        return hasSameType;
    }
}
