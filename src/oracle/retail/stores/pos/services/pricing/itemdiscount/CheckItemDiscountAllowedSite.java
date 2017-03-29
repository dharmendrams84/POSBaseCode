/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/pricing/itemdiscount/CheckItemDiscountAllowedSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:00 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   12/21/10 - Changed to use an updated (and consistent) method in
 *                         DiscountUtility.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         1/25/2006 4:10:52 PM   Brett J. Larsen merge
 *          7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
 *    3    360Commerce 1.2         3/31/2005 4:27:25 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:09 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:09:56 PM  Robert Pearse   
 *:
 *    4    .v700     1.2.1.0     11/20/2005 15:44:32    Deepanshu       CR
 *         6127: Check each item for return before applying discount
 *    3    360Commerce1.2         3/31/2005 15:27:25     Robert Pearse
 *    2    360Commerce1.1         3/10/2005 10:20:09     Robert Pearse
 *    1    360Commerce1.0         2/11/2005 12:09:56     Robert Pearse
 *
 *   Revision 1.3  2004/03/22 18:35:05  cdb
 *   @scr 3588 Corrected some javadoc
 *
 *   Revision 1.2  2004/03/22 03:49:27  cdb
 *   @scr 3588 Code Review Updates
 *
 *   Revision 1.1  2004/02/24 23:43:15  cdb
 *   @scr 3588 Corrected problem with CheckDiscountAllowedSite,
 *   was disallowing all items that were not damage discountable.
 *   Made it abstract with CheckemployeeDiscountAllowedSite
 *   and CheckItemDiscountAllowedSite extending it.
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.pricing.itemdiscount;

import java.util.Locale;

import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.utility.DiscountUtility;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.utility.LocaleMap;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.pricing.CheckDiscountAllowedSite;
import oracle.retail.stores.pos.services.pricing.PricingCargo;

//--------------------------------------------------------------------------
/**
    This site determines if the selected items are eligible for item discount.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class CheckItemDiscountAllowedSite extends CheckDiscountAllowedSite
{
    private static final long serialVersionUID = 4560636352509784505L;

    /** revision number supplied by version control **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
     *   Returns true if the discount is allowed on this line item. <P>
     *   @param  srli       The SaleReturnLineItemIfc to check
     *   @return true if the discount is allowed on this line item
     */
    //----------------------------------------------------------------------
    public boolean isDiscountAllowed(SaleReturnLineItemIfc srli)
    {
    	return DiscountUtility.isDiscountEligible(srli);
    }

    //----------------------------------------------------------------------
    /**
     *   Returns ARG of the MultipleInvalidDiscountDialog. <P>
     *   @param  utility       The UtilityManagerIfc with the bundle resources
     *   @return The ARG of the MultipleInvalidDiscountDialog
     */
    //----------------------------------------------------------------------
    public String getMultipleInvalidDiscountDialogArg(UtilityManagerIfc utility)
    {
        // Get the locale for the user interface.
        Locale uiLocale = LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE);

        return utility.retrieveCommonText("Discount", "1Discount", uiLocale);
    }

    //----------------------------------------------------------------------
    /**
     *   Returns the Invalid Discount Dialog resource ID. <P>
     *   @return The ARG of the MultipleInvalidDiscountDialog
     */
    //----------------------------------------------------------------------
    public String getInvalidDiscountDialogResourceID()
    {
        return PricingCargo.DISCOUNT_NOT_ALLOWED;
    }
}
