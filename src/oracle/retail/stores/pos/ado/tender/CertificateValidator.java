/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/tender/CertificateValidator.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:43 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    npoola    12/30/09 - added the transaction re entry mode condition to not
 *                         validate the gift certificate and store credit
 *    jswan     11/18/09 - Forward to fix use of gift cerificate more than once
 *                         in a transaction and making change to gift
 *                         certificate which already been redeemed.
 *    jswan     11/17/09 - XbranchMerge shagoyal_bug-8553074 from
 *                         rgbustores_13.0x_branch
 *
 * ===========================================================================
 * $Log:
 *  5    360Commerce 1.4         5/19/2008 2:33:32 AM   ASHWYN TIRKEY   Updated
 *        lookupStoreCredit() to throw key violation error in in case the
 *       store credit is present in the database. In addition to this, no
 *       exception is thrown back when no data is found for issue 31453
 *  4    360Commerce 1.3         7/24/2006 8:27:40 PM   Keith L. Lesikar Merge
 *       effort.
 *  3    360Commerce 1.2         3/31/2005 4:27:22 PM   Robert Pearse
 *  2    360Commerce 1.1         3/10/2005 10:20:03 AM  Robert Pearse
 *  1    360Commerce 1.0         2/11/2005 12:09:51 PM  Robert Pearse
 * $
 * Revision 1.30.2.1  2004/10/27 18:27:24  bwf
 * @scr 7443 We were checking less than or equal to, but should have been checking greater than.
 *
 * Revision 1.30  2004/07/27 21:08:21  crain
 * @scr 4184 Using ZZZZZ as the value for the Validate Store Number parameter crashes POS during Gift Cert Validation
 *
 * Revision 1.29  2004/07/23 22:17:25  epd
 * @scr 5963 (ServicesImpact) Major update.  Lots of changes to fix RegisterADO singleton references and fix training mode
 *
 * Revision 1.28  2004/07/18 18:46:20  cdb
 * @scr 5421 Removed unused imports.
 *
 * Revision 1.27  2004/07/17 21:14:34  jriggins
 * @scr 6026 Added logic for checking to see if the transaction for an issued gift certificate has been post voided
 *
 * Revision 1.26  2004/07/15 16:13:22  kmcbride
 * @scr 5954 (Services Impact): Adding logging to these ADOs, also fixed some exception handling issues.
 *
 * Revision 1.25  2004/07/14 18:47:08  epd
 * @scr 5955 Addressed issues with Utility class by making constructor protected and changing all usages to use factory method rather than direct instantiation
 *
 * Revision 1.24  2004/06/17 16:26:17  blj
 * @scr 5678 - code cleanup
 *
 * Revision 1.23  2004/06/03 14:47:42  epd
 * @scr 5368 Update to use of DataTransactionFactory
 *
 * Revision 1.22  2004/05/16 20:52:59  blj
 * @scr 4476 - updated and code cleanup
 *
 * Revision 1.21  2004/05/11 16:08:47  blj
 * @scr 4476 - more rework for store credit tender.
 *
 * Revision 1.20  2004/05/10 19:08:08  crain
 * @scr 4182 Able to use a Issued Gift Cert over and over, Tender Redeemed never appears
 *
 * Revision 1.19  2004/05/05 23:28:04  crain
 * @scr 4182 Able to use a Issued Gift Cert over and over, Tender Redeemed never appears
 *
 * Revision 1.18  2004/05/05 15:18:15  epd
 * @scr 4609 Reversing previous code change
 *
 * Revision 1.17  2004/05/03 19:54:01  epd
 * @scr 4513 Not found Gift certs and Store Credits now assumed valid
 *
 * Revision 1.16  2004/04/26 20:28:31  crain
 * @scr 4553 Redeem Gift Certificate
 *
 * Revision 1.15  2004/04/21 22:09:02  epd
 * @scr 4513 fixing code for certificate validation
 *
 * Revision 1.14  2004/04/21 15:08:12  blj
 * @scr 4476 - fixed crashes, foreign currency, validation, etc for store credit rework.
 *
 * Revision 1.13  2004/04/20 13:05:34  tmorris
 * @scr 4332 -Sorted imports
 *
 * Revision 1.12  2004/04/15 20:52:03  blj
 * @scr 3872 - updated validation
 *
 * Revision 1.11  2004/04/13 17:19:31  crain
 * @scr 4206 Updating Javadoc
 *
 * Revision 1.10  2004/04/09 16:55:59  cdb
 * @scr 4302 Removed double semicolon warnings.
 *
 * Revision 1.9  2004/04/09 12:55:03  pkillick
 * @scr 4332 -Replaced direct instantiation(new) with Factory call.
 *
 * Revision 1.8  2004/04/06 18:58:56  blj
 * @scr 4301 - Fix for TenderStoreCreditADOTest and TenderGiftCertificateADOTest
 *
 * Revision 1.7  2004/04/01 15:58:17  blj
 * @scr 3872 Added training mode, toggled the redeem button based
 * on transaction==null and fixed post void problems.
 *
 * Revision 1.6  2004/03/26 21:32:18  cdb
 * @scr 4204 Removing Tabs.
 *
 * Revision 1.5  2004/03/26 20:48:45  bjosserand
 * @scr 4093 Transaction Reentry
 * Revision 1.4 2004/03/23 21:56:19 crain @scr 4082 Remove Enter Date flow
 *
 * Revision 1.3 2004/03/08 23:32:12 blj @scr 3871 - no changes
 *
 * Revision 1.2 2004/02/12 16:47:55 mcs Forcing head revision
 *
 * Revision 1.1.1.1 2004/02/11 01:04:11 cschellenger updating to pvcs 360store-current
 *
 *
 *
 * Rev 1.4 Feb 04 2004 17:24:00 crain Used check digit utility Resolution for 3421: Tender redesign
 *
 * Rev 1.3 Dec 12 2003 13:29:38 blj initial "happy" path for store credit
 *
 * Rev 1.2 Nov 25 2003 16:11:26 cdb Modified to check that the store number is less than or equal to parameter, per
 * requirements. Resolution for 3421: Tender redesign
 *
 * Rev 1.1 Nov 25 2003 14:38:28 cdb Modified to be safe if store number is non-numeric. Resolution for 3421: Tender
 * redesign
 *
 * Rev 1.0 Nov 20 2003 15:40:12 crain Initial revision. Resolution for 3421: Tender redesign
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.tender;

import org.apache.log4j.Logger;

import oracle.retail.stores.pos.ado.ADOException;
import oracle.retail.stores.pos.ado.context.ADOContextIfc;
import oracle.retail.stores.pos.ado.context.ContextFactory;
import oracle.retail.stores.pos.ado.store.RegisterADO;
import oracle.retail.stores.pos.ado.store.RegisterMode;
import oracle.retail.stores.pos.ado.utility.Utility;
import oracle.retail.stores.pos.ado.utility.UtilityIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.arts.CertificateTransaction;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.tender.TenderCertificateIfc;
import oracle.retail.stores.domain.tender.TenderGiftCertificateIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.tour.manager.ManagerIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.utility.CheckDigitUtility;

/**
 *
 */
public class CertificateValidator implements CertificateValidatorIfc
{
    /** The RDO tender */
    protected TenderCertificateIfc tenderRDO;

    /** Transaction Reentry status */
    protected boolean transactionReentryMode = false;

    /** The Training Mode status */
    protected boolean trainingModeIndicator = false;

    /** The logger to which log messages will be sent.  **/
    static Logger logger = Logger.getLogger(oracle.retail.stores.pos.ado.tender.CertificateValidator.class);

    /**
     * No-arg constructor
     * Note: the constructor is protected by design.
     */
    protected CertificateValidator()
    {

    }

    /**
     * Note: the constructor is protected by design.
     * @param tender
     */
    protected CertificateValidator(TenderCertificateIfc tender)
    {
        tenderRDO = tender;
    }

    /**
     * The CertificateValidator needs to know if the transaction is in training
     * mode. Validation should not occur if training mode is on.
     * @throws TenderException
     */
    public void validate() throws TenderException
    {
        if(logger.isInfoEnabled())
        {
            logger.info("Validating ceritifcate...");
        }

        if(!inTrainingMode())
        {
            // Check the gift certificate number
            validateNumber();

            // Search if gift certificate is valid
            lookupCertificate();
        }
    }

    /**
     * This method checks to see if the register is in training mode.
     * @ return trainingModeIndicator
     */
    protected boolean inTrainingMode()
    {
        RegisterADO registerADO = getContext().getRegisterADO();
        trainingModeIndicator = registerADO.isInMode(RegisterMode.TRAINING);
        return trainingModeIndicator;
    }

    /**
     * Validate the gift certificate number using MOD10 algorithm.
     *
     * @throws TenderException
     *             Thrown when card fails MOD10 check.
     */
    public void validateNumber() throws TenderException
    {
        if(logger.isInfoEnabled())
        {
            logger.info("Validating ceritifcate number...");
        }

        if(!inTrainingMode() && !transactionReentryMode)
        {
            // get certificate number
            String number = getTenderID();

           UtilityManagerIfc utilityManager =
                //(UtilityManagerIfc) ContextFactory.getInstance().getContext().getManager(UtilityManagerIfc.TYPE);
               (UtilityManagerIfc) getManager(UtilityManagerIfc.TYPE);
            if (!utilityManager.validateCheckDigit(CheckDigitUtility.CHECK_DIGIT_FUNCTION_CERTIFICATE_MOD9, number)
                && !utilityManager.validateCheckDigit(CheckDigitUtility.CHECK_DIGIT_FUNCTION_CERTIFICATE_MOD10, number))
            {
                throw new TenderException("Invalid Number", TenderErrorCodeEnum.INVALID_NUMBER);
            }
        }
    }

    /**
     * @return
     */
    protected String getTenderID()
    {
        return tenderRDO.getNumber();
    }

    /**
     * Search for certificate number.
     *
     * @throws TenderException
     *             thrown when certificate not found.
     */
    public void lookupCertificate() throws TenderException
    {
        if(logger.isInfoEnabled())
        {
            logger.info("Looking up ceritifcate...");
        }

//        if(!inTrainingMode())
//        {
            CertificateTransaction dataTransaction = null;

            dataTransaction = (CertificateTransaction) DataTransactionFactory.create(DataTransactionKeys.CERTIFICATE_TRANSACTION);

            try
            {
                tenderRDO = dataTransaction.readCertificate(tenderRDO);
                if (tenderRDO.getTendered())
                {
                    throw new TenderException("Certificate Tendered", TenderErrorCodeEnum.CERTIFICATE_TENDERED, tenderRDO);
                }

                if(!inTrainingMode() && !transactionReentryMode)
                {
	                // We've gotten pass the lookup of tendered and issued store credit.
	                // At this point we know the certificate has been issued, we need
	                // to see if its been redeemed.
	                tenderRDO = dataTransaction.checkIfRedeemed(tenderRDO);
	                if (tenderRDO.getTendered())
	                {
	                    throw new TenderException("Certificate Tendered", TenderErrorCodeEnum.CERTIFICATE_TENDERED, tenderRDO);
	                }

	            	UtilityIfc utility;
	                try
	                {
	                    utility = Utility.createInstance();
	                }
	                catch (ADOException e)
	                {
	                    String message = "Configuration problem: could not instantiate UtilityIfc instance";
	                    logger.error(message, e);
	                    throw new RuntimeException(message, e);
	                }

	                if(!tenderRDO.getCertificateType().equals("Corporate") ||
	                		( tenderRDO.getCertificateType().equals("Corporate")
	                				&& utility.getParameterValue("CorporateStoreID", "CORP").equals(tenderRDO.getStoreNumber())))
	                {
		                // Check to see if the previous transaction had been post voided
		                tenderRDO = dataTransaction.checkIfPostVoided(tenderRDO, inTrainingMode());
		                if (tenderRDO.getPostVoided())
		                {
		                    throw new TenderException("Certificate Voided", TenderErrorCodeEnum.CERTIFICATE_VOIDED, tenderRDO);
		                }
	                }
                }
            }
            catch (TenderException te)
            {
                if (te.getErrorCode() == TenderErrorCodeEnum.CERTIFICATE_TENDERED ||
                        te.getErrorCode() == TenderErrorCodeEnum.CERTIFICATE_VOIDED)
                {
                    throw te;
                }
                // KLM: Otherwise do nothing?
            }
            catch (DataException de)
            {
                if (de.getErrorCode() == DataException.CONNECTION_ERROR)
                {
                    throw new TenderException("ValidationOffline", TenderErrorCodeEnum.VALIDATION_OFFLINE, de);
                }
                else
                {
                    throw new TenderException("Invalid certificate", TenderErrorCodeEnum.INVALID_CERTIFICATE, de);
                }
            }
            catch (Exception e)
            {
                if (!transactionReentryMode)
                {
                    throw new TenderException("Invalid certificate", TenderErrorCodeEnum.INVALID_CERTIFICATE, e);
                }
            }
//        }
    }


    /**
     * Search for certificate number in the master table
     * whether it has been already issued or not.
     *
     * @throws TenderException
     *             thrown when certificate is found.
     */
    public void lookupIssuedCertificate() throws TenderException
    {
    	if(logger.isInfoEnabled())
        {
            logger.info("Looking up ceritificate...");
        }

            CertificateTransaction dataTransaction = null;

            dataTransaction = (CertificateTransaction) DataTransactionFactory.create(DataTransactionKeys.CERTIFICATE_TRANSACTION);

            	TenderGiftCertificateIfc certificate =
                    DomainGateway.getFactory().getTenderGiftCertificateInstance();
            	certificate.setGiftCertificateNumber(tenderRDO.getNumber());

            	try
                {
                    certificate = dataTransaction.readGiftCertificateIssued(certificate);

                }
                catch(DataException de)
                {
                    if(de.getErrorCode() != DataException.NO_DATA)
                    {
                    	throw new TenderException("ValidationOffline", TenderErrorCodeEnum.VALIDATION_OFFLINE, de);
                    }
                }

                if(certificate != null)
                {
                    throw new TenderException("Certificate Issued", TenderErrorCodeEnum.GIFT_CERTIFICATE_NUMBER_ALREADY_USED, tenderRDO);
                }
      }


    /**
     * Checks if store number is greater than store number parameter.
     */
    public void checkStoreNumber()
    {
        UtilityIfc utility;
        try
        {
            utility = Utility.createInstance();
        }
        catch (ADOException e)
        {
            String message = "Configuration problem: could not instantiate UtilityIfc instance";

            // KLM: No need to log if rethrowing
            //logger.error(message, e);

            throw new RuntimeException(message, e);
        }
        String validateStoreNumber = utility.getParameterValue("ValidateStoreNumber", "000");
        String  storeID = tenderRDO.getStoreNumber();
        if (validateStoreNumber.compareToIgnoreCase(storeID) > 0)
        {
            tenderRDO.setValidateByStoreNumber(false);
        }
        else
        {
            tenderRDO.setValidateByStoreNumber(true);
        }
    }

    /**
     * Checks if store number is greater than store number parameter for gift certificate.
     */
    public void checkGiftCertificateStoreNumber()
    {
            tenderRDO.setValidateByStoreNumber(true);
    }

    /**
     * @param transactionReentryMode
     *            The transactionReentryMode to set.
     */
    public void setTransactionReentryMode(boolean transactionReentryMode)
    {
        this.transactionReentryMode = transactionReentryMode;
    }

    /**
     * Returns the context for this ADO
     *
     * @return
     */
    protected ADOContextIfc getContext()
    {
        return ContextFactory.getInstance().getContext();
    }

    /**
     * Returns the desired Manager
     *
     * @param managerType
     *            The desired Manager TYPE
     * @return the requested Manager
     */
    protected ManagerIfc getManager(String managerType)
    {
        return getContext().getManager(managerType);
    }

    /**
     * Method checks whether storecredit has alrady been issued.
     *
     *@throws TenderException when store credit has already been issued.
     */
	public void lookupStoreCredit() throws TenderException{
        if(logger.isInfoEnabled())
        {
            logger.info("Looking up store credit...");
        }

        if(!inTrainingMode())
        {
            CertificateTransaction dataTransaction = null;

            dataTransaction = (CertificateTransaction) DataTransactionFactory.create(DataTransactionKeys.CERTIFICATE_TRANSACTION);

            try
            {
                tenderRDO = dataTransaction.readStoreCredit(tenderRDO);
                throw new DataException(DataException.KEY_VIOLATION_ERROR,"lookupStoreCredit");
            }
            catch (DataException de)
            {
                if (de.getErrorCode() == DataException.CONNECTION_ERROR)
                {
                    throw new TenderException("ValidationOffline", TenderErrorCodeEnum.VALIDATION_OFFLINE, de);
                }
                else if(de.getErrorCode() == DataException.KEY_VIOLATION_ERROR)
                {
                    throw new TenderException("Already Issued", TenderErrorCodeEnum.ISSUE_STORE_CREDIT, de);
                }
                else if(de.getErrorCode() != DataException.NO_DATA)
                {
                    throw new TenderException("Invalid Card Number", TenderErrorCodeEnum.INVALID_CARD_NUMBER, de);
                }
            }
            catch (Exception e)
            {
                if (!transactionReentryMode)
                {
                    throw new TenderException("Invalid certificate", TenderErrorCodeEnum.INVALID_CERTIFICATE, e);
                }
            }
        }
	}
}
