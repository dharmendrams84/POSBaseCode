/* ===========================================================================
* Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/giftcard/EnterGiftCardNumberUISite.java /rgbustores_13.4x_generic_branch/5 2011/07/12 15:58:32 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/12/11 - update generics
 *    cgreene   07/07/11 - convert entryMethod to an enum
 *    asinton   06/29/11 - Refactored to use EntryMethod and
 *                         AuthorizationMethod enums.
 *    cgreene   06/07/11 - update to first pass of removing pospal project
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.giftcard;

import java.util.HashMap;

import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.utility.EntryMethod;
import oracle.retail.stores.domain.utility.GiftCardIfc;
import oracle.retail.stores.foundation.factory.FoundationObjectFactory;
import oracle.retail.stores.foundation.manager.device.EncipheredCardDataIfc;
import oracle.retail.stores.foundation.manager.device.MSRModel;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.keystoreencryption.EncryptionServiceException;
import oracle.retail.stores.pos.ado.tender.TenderConstants;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.tender.TenderCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;

/**
 * @author blj
 */
public class EnterGiftCardNumberUISite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = -4582187633786155629L;

    /**
     * Displays the GIFT_CARD screen.
     * 
     * @param bus Service Bus
     */
    @Override
    public void arrive(BusIfc bus)
    {
        // Check first for eventual card already swiped
        TenderCargo cargo = (TenderCargo)bus.getCargo();
        MSRModel msr = cargo.getPreTenderMSRModel();
        if (msr != null)
        {
            cargo.getTenderAttributes().put(TenderConstants.MSR_MODEL, msr);
            // remove manually entered number
            cargo.getTenderAttributes().remove(TenderConstants.NUMBER);
            cargo.getTenderAttributes().put(TenderConstants.ENCIPHERED_CARD_DATA, msr.getEncipheredCardData());
            bus.mail(new Letter(CommonLetterIfc.NEXT), BusIfc.CURRENT);
        }
        else
        {
            POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
            // show the screen
            ui.showScreen(POSUIManagerIfc.GIFT_CARD, new POSBaseBeanModel());
        }
    }

    /**
     * Get the card number from the ui and put into the tenderAttributes
     * 
     * @param BusIfc bus
     * @see oracle.retail.stores.foundation.tour.application.SiteActionAdapter#depart(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void depart(BusIfc bus)
    {
        // Get information from UI
        TenderCargo cargo = (TenderCargo)bus.getCargo();
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        POSBaseBeanModel model = (POSBaseBeanModel) ui.getModel();
        PromptAndResponseModel parModel = model.getPromptAndResponseModel();
        HashMap<String,Object> tenderAttributes = cargo.getTenderAttributes();
        
        if (parModel != null) 
        {
        
            if (parModel.isSwiped())
            {
                // Put the model in tenderAttributes
                tenderAttributes.put(TenderConstants.MSR_MODEL, parModel.getMSRModel());
                tenderAttributes.put(TenderConstants.NUMBER, parModel.getMSRModel().getEncipheredCardData().getTruncatedAcctNumber());
                tenderAttributes.put(TenderConstants.ENCIPHERED_CARD_DATA, parModel.getMSRModel().getEncipheredCardData());
                tenderAttributes.put(TenderConstants.ENTRY_METHOD, EntryMethod.Swipe);
            }
            else if (parModel.isScanned())
            {
            	// encrypt the card number
                try
                {
                    EncipheredCardDataIfc cardData =
                        FoundationObjectFactory.getFactory().createEncipheredCardDataInstance(ui.getInput().getBytes());
                    tenderAttributes.put(TenderConstants.NUMBER, cardData.getTruncatedAcctNumber());
                    tenderAttributes.put(TenderConstants.ENCIPHERED_CARD_DATA, cardData);
                    tenderAttributes.put(TenderConstants.ENTRY_METHOD, EntryMethod.Automatic);
                }
                catch(EncryptionServiceException e)
                {
            		String message = "unable to decrypt the text";
                    throw new RuntimeException(message, e);
                }
            }
            else if (cargo.getPreTenderMSRModel() == null)
            {
                // if manually entered, we only have the card number.
            	// encrypt the card number
                try
                {
                    EncipheredCardDataIfc cardData =
                        FoundationObjectFactory.getFactory().createEncipheredCardDataInstance(ui.getInput().getBytes());
                    tenderAttributes.put(TenderConstants.NUMBER, cardData.getTruncatedAcctNumber());
                    tenderAttributes.put(TenderConstants.ENCIPHERED_CARD_DATA, cardData);
                    tenderAttributes.put(TenderConstants.ENTRY_METHOD, EntryMethod.Manual);
                    // remove MSR (may have been previously swiped)
                    tenderAttributes.remove(TenderConstants.MSR_MODEL);
                }
                catch(EncryptionServiceException e)
                {
            		String message = "unable to decrypt the text";
                    throw new RuntimeException(message, e);
                }
            }
            else
            {  
                MSRModel msr = (MSRModel)cargo.getTenderAttributes().get(TenderConstants.MSR_MODEL);
                tenderAttributes.put(TenderConstants.NUMBER, msr.getEncipheredCardData().getTruncatedAcctNumber());
                tenderAttributes.put(TenderConstants.ENCIPHERED_CARD_DATA, msr.getEncipheredCardData());
                
            }
            GiftCardIfc giftCard = DomainGateway.getFactory().getGiftCardInstance();
            giftCard.setEncipheredCardData((EncipheredCardDataIfc)tenderAttributes.get(TenderConstants.ENCIPHERED_CARD_DATA));

            giftCard.setRequestType(GiftCardIfc.GIFT_CARD_INQUIRY);
            cargo.setGiftCard(giftCard);

            // This was added to force processing past the off-line condition when performing
            // an inquiry during tendering.  The current tender amount is being set on the available
            giftCard.setInquireAmountForTender(true);
            String tenderAmount = tenderAttributes.get(TenderConstants.AMOUNT).toString();
            giftCard.setBalanceForInquiryFailure(DomainGateway.getBaseCurrencyInstance(tenderAmount));
        }
        
        // set pre tender msr model to null
        // we dont want to come back into credit/debit automatically if we have problems.
        cargo.setPreTenderMSRModel(null);            
     }

}
