/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/receipt/AlterationReceiptParameterBeanIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:39 mszekely Exp $
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
 *  1    360Commerce 1.0         4/30/2007 7:00:39 PM   Alan N. Sinton  CR
 *       26485 - Merge from v12.0_temp.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.receipt;

import java.util.Locale;

import oracle.retail.stores.domain.utility.AlterationIfc;

/**
 * Receipt parameter bean for alteration receipts.
 * $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface AlterationReceiptParameterBeanIfc extends ReceiptParameterBeanIfc
{
    /**
     * Sets the User Locale.
     *
     * @param locale
     */
    public void setUserLocale(Locale locale);

    /**
     * Returns the User Locale.
     *
     * @return
     */
    public Locale getUserLocale();

    /**
     * Get the alterations from the transaction's line items.
     * 
     * @return
     */
    public AlterationIfc[] getAlterations();
}
