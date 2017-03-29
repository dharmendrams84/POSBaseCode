/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/receipt/ReceiptConstantsIfc.java /rgbustores_13.4x_generic_branch/2 2011/06/03 09:46:42 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   06/02/11 - Tweaks to support Servebase chipnpin
 *    cgreene   05/26/10 - convert to oracle packaging
 *    jswan     02/15/10 - Make all receipts go with the email.
 *    jswan     01/29/10 - Additional modifications for attaching rebate and
 *                         gift reciepts to the EReceipt.
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *  1    360Commerce 1.0         6/13/2007 12:12:18 PM  Alan N. Sinton  CR
 *       26485 - Changes per code review.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.receipt;

import oracle.retail.stores.common.parameter.ParameterConstantsIfc;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;

/**
 * Constants for receipt parameter names.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public class ReceiptConstantsIfc
{
    /**
     * @deprecated as of 13.4 use {@link ParameterConstantsIfc#PRINTING_AutoPrintCustomerCopy} instead.
     */
    public static final String AUTO_PRINT_CUSTOMER_COPY = ParameterConstantsIfc.PRINTING_AutoPrintCustomerCopy;

    /**
     * @deprecated as of 13.4 use {@link ParameterConstantsIfc#PRINTING_GroupLikeItemsOnReceipt} instead.
     */
    public static final String GROUP_LIKE_ITEMS_ON_RECEIPT = ParameterConstantsIfc.PRINTING_GroupLikeItemsOnReceipt;

    /**
     * receipt bundles
     */
    public static final String[] RECEIPT_BUNDLES = { BundleConstantsIfc.COMMON_BUNDLE_NAME,
            BundleConstantsIfc.RECEIPT_BUNDLE_NAME };

    /**
     * Customer copy index in blueprint file.
     */
    public static final int CUSTOMER_COPY_INDEX = 1;

    /**
     * Constant for Sale E Receipt file name addition
     */
    public static final String NO_FILE_NAME_ADDITION = "NONE";

    /**
     * Constant for Sale E Receipt file name addition
     */
    public static final String SALE_RETURN_FILE_NAME_ADDITION = "SaleReturn";

    /**
     * Constant for Gift E Receipt file name addition
     */
    public static final String GIFT_FILE_NAME_ADDITION = "Gift";

    /**
     * Constant for Rebate E Receipt file name addition
     */
    public static final String REBATE_FILE_NAME_ADDITION = "Rebate";

    /**
     * Constant for Rebate E Alteration file name addition
     */
    public static final String ALTERATION_FILE_NAME_ADDITION = "Alteration";
}
