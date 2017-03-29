/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/pricing/DiscountCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:01 mszekely Exp $
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
 *    5    360Commerce 1.4         3/18/2008 2:06:34 PM   Siva Papenini
 *         updated comments
 *    4    360Commerce 1.3         3/18/2008 2:03:19 PM   Siva Papenini   CR
 *         30596 : Added constants for invalid price overrride
 *    3    360Commerce 1.2         3/31/2005 4:27:45 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:58 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:35 PM  Robert Pearse   
 *
 *   Revision 1.6  2004/06/08 17:10:09  dfierling
 *   @scr 4411 - rollback changes
 *
 *   Revision 1.5  2004/05/28 19:00:43  dfierling
 *   @scr 4411 - Fix for misleading error Dialog msg
 *
 *   Revision 1.4  2004/03/17 23:03:11  dcobb
 *   @scr 3911 Feature Enhancement: Markdown
 *   Code review modifications.
 *
 *   Revision 1.3  2004/03/03 23:06:30  dcobb
 *   @scr 3911 Feature Enhancement: Markdown
 *   Add markdown parameter.
 *
 *   Revision 1.2  2004/03/03 23:00:12  cdb
 *   @scr 3588 Added tags for externalization. Added new 
 *   Maximum Employee Transaction Discount parameter.
 *
 *   Revision 1.1  2004/03/03 21:03:45  cdb
 *   @scr 3588 Added employee transaction discount service.
 *
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.pricing;

import oracle.retail.stores.domain.utility.CodeConstantsIfc;

//--------------------------------------------------------------------------
/**
    This class represents the main service cargo. <>P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface DiscountCargoIfc extends CodeConstantsIfc
{
    /** revision number supplied by version control **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     length of available space for discount value
     **/
    public static int AVAIL_DISCOUNT_LENGTH = 23;

    /**
     constant for maximum number of discount allowed parameter name
     **/
    public static final String MAX_DISCOUNTS_ALLOWED = "MaxDiscountsAllowed";
    /**
     constant for parameter value representing only one discount allowed
     **/
    public static final String ONE_TOTAL = "OneTotal";
    /**
     constant for parameter value representing one of each type of discount allowed
     **/
    public static final String ONE_OF_EACH_TYPE = "OneOfEachType";
    /**
     constant for maximum employee discount amount/percent parameter name
     **/
    public static final String MAX_EMPLOYEE_DISC_PCT = "MaximumEmployeeDiscountAmountPercent";    
    /**
     constant for parameter name
     **/
    public static final String MAX_DISC_PCT = "MaximumItemDiscountAmountPercent";
    /**
     constant for maximum markdwon amount/percent
     **/ 
    public static final String MAX_MARKDOWN_PCT = "MaximumItemMarkdownAmountPercent";
    /**
     constant for maximum damage discount amount/percent parameter name
     **/
    public static final String MAX_DAMAGE_DISC_PCT = "MaximumDamageDiscountAmountPercent";        
    /**
     constant for multiple selection with some invalid discounts confirmation dialog screen
     **/
    public static final String MULTI_ITEM_INVALID_DISC = "MultiItemInvalidDiscount";
    /**
     constant for no valid discounts error dialog screen
     **/
    public static final String INVALID_DISC = "InvalidDiscount";
    /**
     constant for amount exceeds maximum amount error dialog screen
     **/
    public static final String INVALID_ITEM_DISC = "InvalidItemDiscount";
    /**
     constant for discount already applied confirmation dialog screen
     **/
    public static final String DISCOUNT_ALREADY_APPLIED = "DiscountAlreadyApplied";
    /**
     constant for error dialog screen
     **/
    public static final String EMPLOYEE_DISCOUNT_NOT_ALLOWED = "EmployeeDiscountNotAllowed";
    /**
     constant for error dialog screen
     **/
    public static final String DISCOUNT_NOT_ALLOWED = "DiscountNotAllowed";
    /**
     constant for error dialog screen
     **/
    public static final String MULTIPLE_DISCOUNT_NOT_ALLOWED = "MultipleDiscountsNotAllowed";
    /**
     constant for error dialog screen
     **/
    public static final String ITEM_NOT_DAMAGE_DISCOUNTABLE = "ItemNotDamageDiscountable";    
    /**
     constant for error dialog screen
     **/
    public static final String INVALID_REASON_CODE = "InvalidReasonCode";
    /**
     constant for parameter name
     **/
    public static final String MAX_TRANS_DISC_PCT = "MaximumTransactionDiscountAmountPercent";
    /**
     constant for parameter name
     **/
    public static final String MAX_EMPLOYEE_TRANS_DISC_PCT = "MaximumEmployeeTransactionDiscountAmountPercent";
    /**
     resource id for invalid transaction discount dialog
     **/
    public static final String INVALID_TRANSACTION_DISCOUNT_DIALOG = "InvalidTransactionDiscountPercent";
    /**
     * Invalid Price override  dialog
     */
    public static final String INVALID_PRICE_OVERRIDE_DIALOG = "InvalidPriceOverride";
  
    
    //---------------------------------------------------------------------
    /**
       Returns discountType
       @return  discountType discount type
    **/
    //---------------------------------------------------------------------
    public int getDiscountType();

    //---------------------------------------------------------------------
    /**
       Sets discountType.
       @param  discountType the discount type
    **/
    //---------------------------------------------------------------------
    public void setDiscountType(int discountType);

}
