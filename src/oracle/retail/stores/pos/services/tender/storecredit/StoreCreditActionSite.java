/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/storecredit/StoreCreditActionSite.java /rgbustores_13.4x_generic_branch/1 2011/04/01 14:55:04 jkoppolu Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    jkoppolu  04/01/11 - Added check to see if same storeCredit is being used
 *                         in a single transaction.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.storecredit;

import java.util.HashMap;

import oracle.retail.stores.pos.ado.ADOException;
import oracle.retail.stores.pos.ado.context.ContextFactory;
import oracle.retail.stores.pos.ado.factory.ADOFactoryComplex;
import oracle.retail.stores.pos.ado.factory.TenderFactoryIfc;
import oracle.retail.stores.pos.ado.journal.JournalActionEnum;
import oracle.retail.stores.pos.ado.journal.JournalFactory;
import oracle.retail.stores.pos.ado.journal.JournalFactoryIfc;
import oracle.retail.stores.pos.ado.journal.JournalFamilyEnum;
import oracle.retail.stores.pos.ado.journal.RegisterJournalIfc;
import oracle.retail.stores.pos.ado.tender.TenderConstants;
import oracle.retail.stores.pos.ado.tender.TenderErrorCodeEnum;
import oracle.retail.stores.pos.ado.tender.TenderException;
import oracle.retail.stores.pos.ado.tender.TenderStoreCreditADO;
import oracle.retail.stores.pos.ado.tender.TenderTypeEnum;
import oracle.retail.stores.pos.ado.transaction.RetailTransactionADOIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.tender.TenderCargo;
import oracle.retail.stores.domain.tender.TenderCertificateIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.UIUtilities;

/**
 * @author blj
 * 
 * To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code
 * Generation&gt;Code and Comments
 */
public class StoreCreditActionSite extends PosSiteActionAdapter
{
    /*
     * Add store credit tender to the transaction.
     * 
     * @see oracle.retail.stores.foundation.tour.application.SiteActionAdapter#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    public void arrive(BusIfc bus)
    {
        TenderCargo cargo = (TenderCargo) bus.getCargo();
        boolean transactionReentryMode = cargo.getRegister().getWorkstation().isTransReentryMode();

        // add tender type to attributes
        HashMap tenderAttributes = cargo.getTenderAttributes();
        tenderAttributes.put(TenderConstants.TENDER_TYPE, TenderTypeEnum.STORE_CREDIT);
        String storeID = ContextFactory.getInstance()
                                       .getContext()
                                       .getRegisterADO()
                                       .getStoreADO()
                                       .getStoreID();
        
        tenderAttributes.put(TenderConstants.STORE_NUMBER, storeID);
        tenderAttributes.put(TenderConstants.STATE, TenderCertificateIfc.REDEEMED);
        String storeCreditNumber = (String)tenderAttributes.get(TenderConstants.NUMBER);
        String storeCreditAmount = (String)tenderAttributes.get(TenderConstants.AMOUNT);

        // check if same storeCredit is being used in a single transaction
        if(cargo.isStoreCreditUsed(storeCreditNumber,storeCreditAmount))
        {
            displayDialog(bus, DialogScreensIfc.ERROR, "InvalidStoreCreditError", null, "Invalid");
            return;
        }


        // create the gift card tender
        TenderStoreCreditADO storeCreditTender = null;
        if (cargo.getTenderADO() == null)
        {
            try
            {
                TenderFactoryIfc factory = (TenderFactoryIfc) ADOFactoryComplex.getFactory("factory.tender");
                storeCreditTender = (TenderStoreCreditADO) factory.createTender(tenderAttributes);
            }
            catch (ADOException adoe)
            {
                adoe.printStackTrace();
            }
            catch (TenderException e)
            {
                TenderErrorCodeEnum error = e.getErrorCode();
                if (error == TenderErrorCodeEnum.INVALID_AMOUNT)
                {
                    assert(false) : "This should never happen, because UI enforces proper format";
                }
            }
        }
        else
        {
            storeCreditTender = (TenderStoreCreditADO) cargo.getTenderADO();
        }

        storeCreditTender.setTransactionReentryMode(transactionReentryMode);

        // add the tender to the transaction
        RetailTransactionADOIfc txnADO = cargo.getCurrentTransactionADO();
        try
        {
            // check the store number
            storeCreditTender.checkStoreNumber();

            // calculate base tender amount for foreign certificates
            // TODO: move after verification that this is correct
            // storeCreditTender.calculateBaseTenderAmount();

            storeCreditTender.setTenderAttributes(tenderAttributes);
            storeCreditTender.getTenderAttributes();
            txnADO.addTender(storeCreditTender);
            cargo.setLineDisplayTender(storeCreditTender);
            // journal the added tender

            JournalFactoryIfc jrnlFact = null;
            try
            {
                jrnlFact = JournalFactory.getInstance();
            }
            catch (ADOException e)
            {
                logger.error(JournalFactoryIfc.INSTANTIATION_ERROR, e);
                throw new RuntimeException(JournalFactoryIfc.INSTANTIATION_ERROR, e);
            }
            RegisterJournalIfc registerJournal = jrnlFact.getRegisterJournal();
            registerJournal.journal(storeCreditTender, JournalFamilyEnum.TENDER, JournalActionEnum.ADD);

            // mail a letter
            bus.mail(new Letter(CommonLetterIfc.SUCCESS), BusIfc.CURRENT);
        }
        catch (TenderException e)
        {
            TenderErrorCodeEnum error = e.getErrorCode();

            // save tender in cargo
            cargo.setTenderADO(storeCreditTender);
            
            UtilityManagerIfc utility = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

            if (error == TenderErrorCodeEnum.CERTIFICATE_TENDERED)
            {
                String args[] = new String[4];
                args[0] = utility.retrieveDialogText("StoreCredit", "StoreCredit");
                args[1] = (String) storeCreditTender.getTenderAttributes().get(TenderConstants.NUMBER);
                args[2] = (String) storeCreditTender.getTenderAttributes().get(TenderConstants.REDEEM_TRANSACTION_ID);
                args[3] = (String) storeCreditTender.getTenderAttributes().get(TenderConstants.REDEEM_DATE);
                
                displayDialog(bus, DialogScreensIfc.ACKNOWLEDGEMENT, "TenderRedeemed", args, "Invalid");
                return;
            }
            else if (error == TenderErrorCodeEnum.INVALID_NUMBER)
            {
                String[] args = {(String) tenderAttributes.get(TenderConstants.NUMBER)};
                displayDialog(bus, DialogScreensIfc.ACKNOWLEDGEMENT, "InvalidNumberError", args, "Invalid");
                return;
            }
            else if (error == TenderErrorCodeEnum.INVALID_CERTIFICATE)
            {
                //String args[] = new String[2];
                //args[0] = utility.retrieveDialogText("StoreCredit", "StoreCredit");
                displayDialog(bus, DialogScreensIfc.ERROR, "InvalidStoreCreditError", null, "Invalid");
                return;
            }
            else if (error == TenderErrorCodeEnum.VALIDATION_OFFLINE)
            {
                txnADO.addValidTender(storeCreditTender);
                cargo.setLineDisplayTender(storeCreditTender);
                // journal the added tender

                JournalFactoryIfc jrnlFact = null;
                try
                {
                    jrnlFact = JournalFactory.getInstance();
                }
                catch (ADOException adoExcep)
                {
                    String message = "Configuration problem: could not instantiate JournalFactoryIfc instance";
                    logger.error(message, adoExcep);
                    throw new RuntimeException(message, adoExcep);
                }
                RegisterJournalIfc registerJournal = jrnlFact.getRegisterJournal();
                registerJournal.journal(storeCreditTender, JournalFamilyEnum.TENDER, JournalActionEnum.ADD);
                String type = utility.retrieveDialogText("StoreCredit", "StoreCredit");
                String args[] = {type, type};
                
                displayDialog(bus, DialogScreensIfc.ERROR, "ValidationOffline", args, "Success");
                return;
            }
        }
    }

    protected void displayDialog(BusIfc bus, int screenType, String message, String[] args, String letter)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        if (letter != null)
        {
            UIUtilities.setDialogModel(ui, screenType, message, args, letter);
        }
        else
        {
            UIUtilities.setDialogModel(ui, screenType, message, args);
        }
    }
}
