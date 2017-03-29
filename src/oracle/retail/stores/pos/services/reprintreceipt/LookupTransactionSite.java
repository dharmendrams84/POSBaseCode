/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/reprintreceipt/LookupTransactionSite.java /rgbustores_13.4x_generic_branch/2 2011/06/29 16:08:36 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   06/28/11 - rename hashed credit card field to token
 *    npoola    09/24/10 - Fixed the sorting order of the parameters display
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *  6    360Commerce 1.5         3/26/2008 1:41:45 AM   VIVEKANAND KINI Porting
 *        the changed made for 30397 from GAP defects. The code was changed to
 *        make sure, that a sale once post voided should not be re-printable.
 *  5    360Commerce 1.4         4/25/2007 8:52:15 AM   Anda D. Cadar   I18N
 *       merge
 *
 *  4    360Commerce 1.3         1/25/2006 4:11:30 PM   Brett J. Larsen merge
 *       7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
 *  3    360Commerce 1.2         3/31/2005 4:28:59 PM   Robert Pearse
 *  2    360Commerce 1.1         3/10/2005 10:23:22 AM  Robert Pearse
 *  1    360Commerce 1.0         2/11/2005 12:12:29 PM  Robert Pearse
 * $:
 *  4    .v700     1.2.1.0     11/10/2005 10:36:09    Rohit Sachdeva  6429:
 *       Optional date in the transaction ID should be taken up as business
 *       date when entered as per format specified
 *  3    360Commerce1.2         3/31/2005 15:28:59     Robert Pearse
 *  2    360Commerce1.1         3/10/2005 10:23:22     Robert Pearse
 *  1    360Commerce1.0         2/11/2005 12:12:29     Robert Pearse
 * $
 * Revision 1.10  2004/06/03 14:47:44  epd
 * @scr 5368 Update to use of DataTransactionFactory
 *
 * Revision 1.9  2004/04/26 19:51:14  dcobb
 * @scr 4452 Feature Enhancement: Printing
 * Add Reprint Select flow.
 *
 * Revision 1.8  2004/04/22 21:26:38  dcobb
 * @scr 4452 Feature Enhancement: Printing
 * Only completed sale, return or exchange transactions are displayed in REPRINT_SELECT.
 *
 * Revision 1.7  2004/04/22 17:39:00  dcobb
 * @scr 4452 Feature Enhancement: Printing
 * Added REPRINT_SELECT screen and flow to Reprint Receipt use case..
 *
 * Revision 1.6  2004/04/20 13:17:06  tmorris
 * @scr 4332 -Sorted imports
 *
 * Revision 1.5  2004/04/14 15:17:10  pkillick
 * @scr 4332 -Replaced direct instantiation(new) with Factory call.
 *
 * Revision 1.4  2004/03/03 23:15:09  bwf
 * @scr 0 Fixed CommonLetterIfc deprecations.
 *
 * Revision 1.3  2004/02/12 16:51:42  mcs
 * Forcing head revision
 *
 * Revision 1.2  2004/02/11 21:52:29  rhafernik
 * @scr 0 Log4J conversion and code cleanup
 * Revision 1.1.1.1 2004/02/11 01:04:20
 * cschellenger updating to pvcs 360store-current
 *
 *
 *
 * Rev 1.1 Feb 03 2004 09:28:52 kll account for CreationFee in the BalanceDue
 * Resolution for 3795: Balance Due on Reprinted Receipts don't include the
 * Layaway Fee
 *
 * Rev 1.0 Aug 29 2003 16:05:36 CSchellenger Initial revision.
 *
 * Rev 1.3 Jul 25 2003 12:13:04 sfl Make sure to include the layaway fee in the
 * total when doing the layaway pickup receipt reprint. Resolution for POS
 * SCR-3245: Layaway Fee is not included in the total on layaway pickup reprint
 * receipt
 *
 * Rev 1.2 24 Jul 2003 20:00:46 mpm Tweaked parameter name.
 *
 * Rev 1.1 24 Jul 2003 19:52:44 mpm Added checking for
 * verify-register-ID-on-reprint-receipt.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.reprintreceipt;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.common.parameter.ParameterConstantsIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.TransactionReadDataTransaction;
import oracle.retail.stores.domain.transaction.LayawayTransactionIfc;
import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
import oracle.retail.stores.domain.transaction.TillAdjustmentTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionConstantsIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.domain.utility.EYSDate;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;

/**
 * This site checks to see that the transaction exists, was created on the same
 * day and at the same register and is reprintable.
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public class LookupTransactionSite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = 3702792350566278971L;
    /** revision number of this class */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    /**
     * Retrieves the transaction and confirms that the transaction may be
     * reprinted.
     *
     * @param bus
     *            Service Bus
     */
    @Override
    public void arrive(BusIfc bus)
    {
        ReprintReceiptCargo cargo = (ReprintReceiptCargo) bus.getCargo();

        //get utility manager
        UtilityManagerIfc utility =
            (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        // Lookup the transaction ID stored in the cargo.
        TransactionIfc transaction = null;
        String transactionID = cargo.getTransactionID();
        EYSDate today = cargo.getBusinessDate();

        TransactionIfc searchTransaction = null;

        TransactionReadDataTransaction trans = null;

        trans = (TransactionReadDataTransaction) DataTransactionFactory.create(DataTransactionKeys.TRANSACTION_READ_DATA_TRANSACTION);

        String letterName = null;
        boolean notFound = true;

        try
        {
            searchTransaction =
                DomainGateway.getFactory().getTransactionInstance();
            searchTransaction.initialize(transactionID);
            if (searchTransaction.getBusinessDay() == null)
            {
                searchTransaction.setBusinessDay(today);
            }
            searchTransaction.setTrainingMode(cargo.isTrainingMode());
            searchTransaction.setTransactionStatus(
                TransactionIfc.STATUS_COMPLETED);
            searchTransaction.setLocaleRequestor(utility.getRequestLocales());
            transaction = trans.readTransaction(searchTransaction);
            if (transaction instanceof LayawayTransactionIfc)
            {
                if (!(transaction.getTransactionType()
                    == TransactionIfc.TYPE_LAYAWAY_DELETE))
                {
                    CurrencyIfc LayawayFee =
                        ((LayawayTransactionIfc) transaction)
                            .getLayaway()
                            .getCreationFee();
                    CurrencyIfc grandTotal =
                        ((TenderableTransactionIfc) transaction)
                            .getTransactionTotals()
                            .getGrandTotal();
                    ((TenderableTransactionIfc) transaction)
                        .getTransactionTotals()
                        .setGrandTotal(
                        grandTotal.add(LayawayFee));

                    CurrencyIfc balanceDue =
                        ((TenderableTransactionIfc) transaction)
                            .getTransactionTotals()
                            .getBalanceDue();
                    ((TenderableTransactionIfc) transaction)
                        .getTransactionTotals()
                        .setBalanceDue(
                        balanceDue.add(LayawayFee));
                }
            }

            if (logger.isInfoEnabled())
                logger.info(
                    "Training mode: " + transaction.isTrainingMode());

            notFound = false;
        }
        catch (DataException de)
        {
            if (de.getErrorCode() == DataException.NO_DATA)
            {
                notFound = true;
            }
            else
            {
                logger.error("Can't find transaction for ID " + transactionID, de);
                letterName = CommonLetterIfc.DB_ERROR;
                notFound = true;
                cargo.setDataExceptionErrorCode(de.getErrorCode());
            }
        }
        catch (Exception e)
        {
            logger.error("Can't find transaction for ID " + transactionID, e);
            letterName = CommonLetterIfc.DB_ERROR;
            cargo.setDataExceptionErrorCode(DataException.UNKNOWN);
            notFound = true;
        }

        // look for suspended transaction
        if (notFound
            && !Util.isObjectEqual(letterName, CommonLetterIfc.DB_ERROR))
        {
            try
            {
                searchTransaction.setTransactionStatus(TransactionIfc.STATUS_SUSPENDED);
                transaction = trans.readTransaction(searchTransaction);
                notFound = false;
            }
            catch (DataException de)
            {
                if (de.getErrorCode() == DataException.NO_DATA)
                {
                    notFound = true;
                    logger.warn("Can't find transaction for ID " + transactionID, de);
                    letterName = CommonLetterIfc.NOT_FOUND;
                }
                else
                {
                    logger.error("Can't find transaction for ID " + transactionID, de);
                    letterName = CommonLetterIfc.DB_ERROR;
                    cargo.setDataExceptionErrorCode(de.getErrorCode());
                    notFound = true;
                }
            }
            catch (Exception e)
            {
                logger.error("Can't find transaction for ID " + transactionID, e);
                letterName = CommonLetterIfc.DB_ERROR;
                cargo.setDataExceptionErrorCode(DataException.UNKNOWN);
                notFound = true;
            }
        }

        // check to determine if transaction can be re-printed
        if (notFound == false)
        {
            if (transaction instanceof TenderableTransactionIfc
                && allowReprint(bus, transaction, cargo))
            {
            	if (isTransactionPrintable(transaction, cargo))
            	{
                    // retrieve Enable Reprint Original Receipt parameter
                    boolean reprintOriginalReceiptAllowed = true;
                    ParameterManagerIfc pm =
                        (ParameterManagerIfc) bus.getManager(ParameterManagerIfc.TYPE);
                    try
                    {
                        reprintOriginalReceiptAllowed =
                            pm.getBooleanValue(ParameterConstantsIfc.PRINTING_EnableReprintOriginalReceipt);
                    }
                    catch (ParameterException pe)
                    {
                        logger.warn(
                        "Parameter unavailable, Reprint original receipt will be allowed.");
                    }

                    if (reprintOriginalReceiptAllowed)
                    {
                        letterName = CommonLetterIfc.SUCCESS;
                        cargo.setTransaction((TenderableTransactionIfc) transaction);
                        if (cargo.isCompletedSaleReturnExchange(transaction))
                        {
                            letterName = ReprintReceiptCargo.SUCCESS_SRE_LETTER;
                        }
                        else if (transaction instanceof TillAdjustmentTransactionIfc){
                            letterName = ReprintReceiptCargo.SUCCESS_TILL_ADJUSTMENT_LETTER;
                        }
                    }
                    else
                    {
                        if (cargo.isCompletedSaleReturnExchange(transaction))
                        {
                            letterName = ReprintReceiptCargo.SUCCESS_SRE_LETTER;
                            cargo.setTransaction((TenderableTransactionIfc) transaction);
                        }
                        else
                        {
                            if (logger.isInfoEnabled())
                                logger.info(bus.getServiceName() + "Reprint original transaction is not enabled.");

                            letterName = ReprintReceiptCargo.NOT_REPRINTABLE_ERROR_LETTER;
                            cargo.setNonReprintableErrorID(ReprintReceiptCargo.NOT_REPRINTABLE_ERROR_TRANSACTION_NOT_PRINTABLE);
                        }
                    }
            	}
            	else
            	{
            		if (logger.isInfoEnabled())
                        logger.info(bus.getServiceName() + "Transaction " + transactionID
                                + " is not reprintable; it is of type "
                                + Integer.toString(transaction.getTransactionType()));

                    letterName = ReprintReceiptCargo.NOT_REPRINTABLE_ERROR_LETTER;
                    cargo.setNonReprintableErrorID(
                        ReprintReceiptCargo
                            .NOT_REPRINTABLE_ERROR_TRANSACTION_NOT_PRINTABLE);
            	}

            }
            else if (transaction instanceof TenderableTransactionIfc)
            {
                if (logger.isInfoEnabled())
                    logger.info("Transaction " + transactionID + " register ID "
                            + transaction.getWorkstation().getWorkstationID() + " does not match current register ID "
                            + cargo.getRegisterID() + ".");
                letterName = ReprintReceiptCargo.NOT_REPRINTABLE_ERROR_LETTER;
                cargo.setNonReprintableErrorID(ReprintReceiptCargo.NOT_REPRINTABLE_ERROR_DIFFERENT_REGISTER);
            }
            else
            // cannot reprint - wrong type
            {
                if (logger.isInfoEnabled())
                    logger.info(bus.getServiceName() + "Transaction " + transactionID
                            + " is not reprintable; it is of type "
                            + Integer.toString(transaction.getTransactionType()));

                letterName = ReprintReceiptCargo.NOT_REPRINTABLE_ERROR_LETTER;
                cargo.setNonReprintableErrorID(ReprintReceiptCargo.NOT_REPRINTABLE_ERROR_TRANSACTION_NOT_PRINTABLE);
            }
        }

        bus.mail(new Letter(letterName), BusIfc.CURRENT);

    } // end arrive()

    /**
     * Determine if the reprint request should be allowed. The parameters
     * <code>ReprintVerifyRegisterID</code> and <code>ReprintVerifyStoreID</code>
     * are retrieved via the ParameterManager. If the parameters are set to
     * <code>"Y"</code> then the workstation id and store id of the
     * transaction, must be the same as the requesting register id and store
     * id, respectively. If the parameter is set to <code>"N"</code> then the
     * ids can be different and any receipt can be printed.
     *
     * @param bus
     *            Transporter of the reprint request.
     * @param trans
     *            Information retrieved containing the receipt data.
     * @param cargo
     *            The request information concerning the reprint.
     * @return true = allow reprint, false = do not allow reprint
     *
     */
    protected boolean allowReprint(BusIfc bus, TransactionIfc trans, ReprintReceiptCargo cargo)
    {
        boolean requireWorkstationIDVerification = false;
        boolean returnValue = false;
        ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);

        // retrieve ReprintReceiptVerifyRegisterID parameter
        try
        {
            requireWorkstationIDVerification = pm.getBooleanValue(ParameterConstantsIfc.PRINTING_VerifyRegisterIDOnReprintReceipt);
        }
        catch (ParameterException pe)
        {
            logger.warn("Parameter unavailable, Register and store ID will be verified.");
        }

        // check against cargo register ID, if required
        if (requireWorkstationIDVerification)
        {
            returnValue = Util.isObjectEqual(trans.getWorkstation().getWorkstationID(), cargo.getRegisterID());
        }
        else
        {
            returnValue = true;
        }

        return (returnValue);
    } // end allowReprint()

    /**
     * This method is used to check whether the receipt can be printed or not
     * for a post voided transaction.
     * 
     * @param trans
     * @param cargo
     * @return boolean
     */
    private boolean isTransactionPrintable(TransactionIfc trans, ReprintReceiptCargo cargo)
    {
        if (cargo.getDuplicateReceipt() && trans.getTransactionType() == TransactionConstantsIfc.TYPE_VOID)
        {
            return false;
        }
        
        return true;
    }

}