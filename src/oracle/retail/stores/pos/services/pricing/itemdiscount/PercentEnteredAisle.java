/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/pricing/itemdiscount/PercentEnteredAisle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:00 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    blarse 12/21/10 - Changed to use an updated (and consistent) method in
 *                      DiscountUtility.
 *    cgreen 05/26/10 - convert to oracle packaging
 *    cgreen 04/27/10 - updating deprecated names
 *    abonda 01/03/10 - update header date
 *    acadar 11/03/08 - localization of reason codes for discounts and merging
 *                      to tip
 *    acadar 10/31/08 - removed commented out/deprecated code
 *    acadar 10/30/08 - localization of reason codes for item and transaction
 *                      discounts
 * ===========================================================================

     $Log:
      6    360Commerce 1.5         3/13/2008 11:58:44 PM  Vikram Gopinath
           Removed code to ROUND_HALF_UP.
      5    360Commerce 1.4         1/25/2006 4:11:35 PM   Brett J. Larsen merge
            7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
      4    360Commerce 1.3         1/22/2006 11:45:16 AM  Ron W. Haight
           removed references to com.ibm.math.BigDecimal
      3    360Commerce 1.2         3/31/2005 4:29:20 PM   Robert Pearse
      2    360Commerce 1.1         3/10/2005 10:24:02 AM  Robert Pearse
      1    360Commerce 1.0         2/11/2005 12:13:02 PM  Robert Pearse
     $:
      4    .v700     1.2.1.0     11/20/2005 15:44:38    Deepanshu       CR
           6127: Check each item for return before applying discount
      3    360Commerce1.2         3/31/2005 15:29:20     Robert Pearse
      2    360Commerce1.1         3/10/2005 10:24:02     Robert Pearse
      1    360Commerce1.0         2/11/2005 12:13:02     Robert Pearse
     $
     Revision 1.12  2004/03/22 18:35:05  cdb
     @scr 3588 Corrected some javadoc

     Revision 1.11  2004/03/19 23:27:50  dcobb
     @scr 3911 Feature Enhancement: Markdown
     Code review cleanup.

     Revision 1.10  2004/03/17 23:03:11  dcobb
     @scr 3911 Feature Enhancement: Markdown
     Code review modifications.

     Revision 1.9  2004/03/15 20:28:35  cdb
     @scr 3588 Updated ItemPrice test. Removed ItemPrice deprecated (2 release) methods.

     Revision 1.8  2004/03/12 23:12:53  dcobb
     @scr 3911 Feature Enhancement: Markdown
     Extracted common code to abstract class.

     Revision 1.7  2004/02/26 20:32:38  cdb
     @scr 3588 Corrected error - was using maximum
     employee discount parameter instead of maximum
     discount parameter.

     Revision 1.6  2004/02/26 18:26:20  cdb
     @scr 3588 Item Discounts no longer have the Damage
     selection. Use the Damage Discount flow to apply Damage
     Discounts.

     Revision 1.5  2004/02/24 22:36:29  cdb
     @scr 3588 Added ability to check for previously existing
     discounts of the same type and capture the prorate user
     selection. Also migrated item discounts to validate in
     the percent and amount entered aisle to be consistent
     with employee discounts.

     Revision 1.4  2004/02/24 16:21:30  cdb
     @scr 0 Remove Deprecation warnings. Cleaned code.

     Revision 1.3  2004/02/12 16:51:36  mcs
     Forcing head revision

     Revision 1.2  2004/02/11 21:52:05  rhafernik
     @scr 0 Log4J conversion and code cleanup

     Revision 1.1.1.1  2004/02/11 01:04:19  cschellenger
     updating to pvcs 360store-current


 *
 *    Rev 1.3   Feb 09 2004 15:33:26   cdb
 * Updated to treate damage and employee discounts as separate "types" than item discounts.
 * Resolution for 3588: Discounts/MUPS - Gap Rollback
 *
 *    Rev 1.2   Dec 23 2003 17:43:54   cdb
 * Pending requirements finalization, removing functionality to prompt for user ID when a given Reason Code is selected.
 * Resolution for 3588: Discounts/MUPS - Gap Rollback
 *
 *    Rev 1.1   Oct 17 2003 10:43:04   bwf
 * Added check to see if employee discount called.
 * Resolution for 3412: Feature Enhancement: Employee Discount
 *
 *    Rev 1.0   Aug 29 2003 16:05:22   CSchellenger
 * Initial revision.
 *
 *    Rev 1.2   Jul 29 2003 14:47:30   DCobb
 * Use cargo.getItem() when cargo.getItems() is null.
 * Resolution for POS SCR-3280: When a user with no access attempts to apply a discount amount to a kit component, selecting yes on the security error screen results in a loop.
 *
 *    Rev 1.1   Jul 17 2003 06:59:10   jgs
 * Modifed journaling for item discounts.
 * Resolution for 3037: The ejournal for a transaction with multiple (3) % discounts applies and removes the first two discounts on the ejournal.
 *
 *    Rev 1.0   02 May 2002 17:41:38   jbp
 * Initial revision.
 * Resolution for POS SCR-1626: Pricing Feature

* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package oracle.retail.stores.pos.services.pricing.itemdiscount;

import oracle.retail.stores.common.utility.LocalizedCodeIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.discount.DiscountRuleConstantsIfc;

import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.utility.CodeConstantsIfc;
import oracle.retail.stores.domain.utility.CodeEntryIfc;
import oracle.retail.stores.domain.utility.CodeListIfc;
import oracle.retail.stores.domain.utility.DiscountUtility;

import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.pricing.AbstractPercentEnteredAisle;
import oracle.retail.stores.pos.services.pricing.PricingCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DecimalWithReasonBeanModel;
import java.math.BigDecimal;

//--------------------------------------------------------------------------
/**
    This aisle validates the percent entered to make sure it doesn't
    exceed the maximum discount amount/percent parameter and
    that there are no items that would have a negative price after the
    discount is applied without a warning or error dialog.
    <P>
    Can show one of three dialogs:
    MultiItemInvalidDiscount - If more than one item has been selected and
        at least one of the item's prices would be negative after the
        discount. Allows you to apply the discount to only the items
        that don't go negative.
    InvalidDiscount - If there was only one item selected and it's
        price would be negative after the discount. Returns to entry screen.
    InvalidItemDiscount - if at least one item would violate the maximum
        discount amount parameter. Returns to entry screen.
    <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class PercentEnteredAisle extends AbstractPercentEnteredAisle
{
    private static final long serialVersionUID = 6917439999001566632L;

    /** Revision Number */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /** Discount tag */
    public static final String DISCOUNT_TAG = "Discount";
    /** Discount text */
    public static final String DISCOUNT_TEXT = "discount";

    //----------------------------------------------------------------------
    /**
     *   This aisle validates the percent entered to make sure it doesn't
     *   exceed the maximum discount amount/percent parameter and
     *   that there are no items that would have a negative price after the
     *   discount is applied without a warning or error dialog.
     *   <P>
     *   Can show one of three dialogs:
     *   MultiItemInvalidDiscount - If more than one item has been selected and
     *      at least one of the item's prices would be negative after the
     *      discount. Allows you to apply the discount to only the items
     *      that don't go negative.
     *   InvalidDiscount - If there was only one item selected and it's
     *      price would be negative after the discount. Returns to entry screen.
     *   InvalidItemDiscount - if at least one item would violate the maximum
     *      discount amount parameter. Returns to entry screen.
     *   <P>
     *   @param  bus     Service Bus
     */
    //----------------------------------------------------------------------
    public void traverse(BusIfc bus)
    {
        PricingCargo cargo = (PricingCargo) bus.getCargo();
        POSUIManagerIfc ui= (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        String dialog = null;
        String letter = CommonLetterIfc.CONTINUE;

        DecimalWithReasonBeanModel beanModel =
            (DecimalWithReasonBeanModel) ui.getModel(POSUIManagerIfc.ITEM_DISC_PCNT);
        BigDecimal  response     = beanModel.getValue();


        SaleReturnLineItemIfc[] lineItems = cargo.getItems();

        if (lineItems == null)
        {
            lineItems = new SaleReturnLineItemIfc[1];
            lineItems[0] = cargo.getItem();
        }

        // Chop off the potential long values caused by BigDecimal.
        if (response.toString().length() > 5)
        {
            BigDecimal scaleOne = new BigDecimal(1);
            response = response.divide(scaleOne, 2);
        }

        LocalizedCodeIfc localizedCode = DomainGateway.getFactory().getLocalizedCode();
        CodeListIfc rcl = cargo.getLocalizedDiscountPercentCodeList();
        String  reason      = beanModel.getSelectedReasonKey();

        if (rcl != null)
        {
            CodeEntryIfc entry = rcl.findListEntryByCode(reason);
            localizedCode.setCode(reason);
            localizedCode.setText(entry.getLocalizedText());

        }
        else
        {
            localizedCode.setCode(CodeConstantsIfc.CODE_UNDEFINED);
        }
        dialog = validateLineItemDiscounts(bus, lineItems, response, localizedCode);

        // No dialogs required
        if (dialog == null)
        {
            bus.mail(letter, BusIfc.CURRENT);
        }
        // show dialog screen
        else
        {
            showDialog(dialog, DISCOUNT_TAG, DISCOUNT_TEXT, bus);
        }
    }

    //----------------------------------------------------------------------
    /**
     * Determines if the item is eligible for the discount
     * @param srli  The line item
     * @return true if the item is eligible for the discount
     */
    //----------------------------------------------------------------------
    protected boolean isEligibleForDiscount(SaleReturnLineItemIfc srli)
    {
    	return DiscountUtility.isDiscountEligible(srli);
    }

    //----------------------------------------------------------------------
    /**
     * Returns the parameter name for the maximum discount percent
     * @return PricingCargo.MAX_DISC_PCT
     */
    //----------------------------------------------------------------------
    protected String getMaxPercentParameterName()
    {
        return PricingCargo.MAX_DISC_PCT;
    }

    //----------------------------------------------------------------------
    /**
     * Clears the discounts by percentage from the line item
     * @param srli    The line item
     */
    //----------------------------------------------------------------------
    protected void clearDiscountsByPercentage(SaleReturnLineItemIfc srli)
    {
        srli.clearItemDiscountsByPercentage(DiscountRuleConstantsIfc.ASSIGNMENT_MANUAL, false);
    }




}
