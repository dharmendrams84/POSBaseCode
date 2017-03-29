/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/tender/CertificateValidatorIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:43 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    jswan     11/18/09 - Forward to fix use of gift cerificate more than once
 *                         in a transaction and making change to gift
 *                         certificate which already been redeemed.
 *    jswan     11/17/09 - XbranchMerge shagoyal_bug-8553074 from
 *                         rgbustores_13.0x_branch
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         7/24/2006 8:27:40 PM   Keith L. Lesikar
 *         Merge effort.
 *    3    360Commerce 1.2         3/31/2005 4:27:22 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:03 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:09:51 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/05/16 20:52:59  blj
 *   @scr 4476 - updated and code cleanup
 *
 *   Revision 1.2  2004/04/06 20:45:10  blj
 *   @scr 4301 - cleaned up javadoc's.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.tender;

/**
 *
 * Certificate Validator interface created for unit testing.
 */
public interface CertificateValidatorIfc
{
    /**
     * The CertificateValidator needs to know if the transaction is in training
     * mode. Validation should not occur if training mode is on.
     * @throws TenderException
     */
    public abstract void validate() throws TenderException;
    /**
     * Checks if store number is less than store number parameter.
     */
    public abstract void checkStoreNumber();
    /**
     * Checks if store number is less than store number parameter for gift certificate.
     */
    public abstract void checkGiftCertificateStoreNumber();
    /**
     * @param transactionReentryMode
     *            The transactionReentryMode to set.
     */
    public abstract void setTransactionReentryMode(boolean transactionReentryMode);
    /**
     * Validate the gift certificate number using MOD10 algorithm.
     *
     * @throws TenderException
     *             Thrown when card fails MOD10 check.
     */
    public abstract void validateNumber() throws TenderException;

    /**
     * Search for certificate number.
     *
     * @throws TenderException
     *             thrown when certificate not found.
     */
    public void lookupCertificate() throws TenderException;

    /**
     * Search for certificate number in the master table
     * whether it has been already issued or not.
     *
     * @throws TenderException
     *             thrown when certificate is found.
     */
    public void lookupIssuedCertificate() throws TenderException;

    /**
     * Method checks whether storecredit has alrady been issued.
     *
     *@throws TenderException when store credit has already been issued.
     */
	public abstract void lookupStoreCredit() throws TenderException;
}
