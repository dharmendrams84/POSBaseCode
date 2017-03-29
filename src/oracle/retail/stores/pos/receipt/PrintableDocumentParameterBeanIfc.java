/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/receipt/PrintableDocumentParameterBeanIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:40 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    acadar    02/12/09 - use default locale for date/time printing in the
 *                         receipts
 *    glwang    02/09/09 - changes per code review
 *    glwang    02/06/09 - add isTrainingMode into
 *                         PrintableDocumentParameterBeanIfc
 *
 * ===========================================================================
 * $Log:
 *  1    360Commerce 1.0         4/30/2007 7:00:39 PM   Alan N. Sinton  CR
 *       26485 - Merge from v12.0_temp.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.receipt;

import java.io.Serializable;
import java.util.Locale;

/**
 * Interface for the PrintableDocumentParametersBean. Implementations must be
 * {@link Serializable}.
 *
 * $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface PrintableDocumentParameterBeanIfc extends Serializable
{
    /**
     * Returns the document type. Types are defined in
     * {@link ReceiptTypeConstantsIfc}
     *
     * @return
     */
    public String getDocumentType();

    /**
     * Sets the document type.
     *
     * @param type from {@link ReceiptTypeConstantsIfc}
     */
    public void setDocumentType(String type);

    /**
     * Get the locale used to print the receipt.
     *
     * @return
     */
    public Locale getLocale();

    /**
     * Get the locale used to print the date and time on the receipt.
     *
     * @return
     */
    public Locale getDefaultLocale();

    /**
     * Set the locale used to print the receipt.
     *
     * @param locale
     */
    public void setLocale(Locale locale);

    /**
     * Set the locale used to print the date and time on the receipt.
     *
     * @param locale
     */
    public void setDefaultLocale(Locale locale);

    /**
     *
     * Set the training mode flag.
     * @param flag
     */
    public void setTrainingMode(boolean flag);

    /**
     * return the training mode flag.
     * @return
     */
    public boolean isTrainingMode();

}
