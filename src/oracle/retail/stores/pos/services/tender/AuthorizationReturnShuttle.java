/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/AuthorizationReturnShuttle.java /rgbustores_13.4x_generic_branch/7 2011/08/31 13:06:26 sgu Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/11 - move auth response objects into domain
 *    ohorne    05/09/11 - added journaling
 *    asinton   03/31/11 - adding authorization builder utility.
 *    asinton   03/25/11 - Moved APF request and response objects to common
 *                         module.
 *    asinton   03/21/11 - creating new tender authorization service
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender;

import java.util.HashMap;
import java.util.List;

import oracle.retail.stores.commerceservices.audit.AuditLoggerServiceIfc;
import oracle.retail.stores.commerceservices.audit.AuditLoggingUtils;
import oracle.retail.stores.commerceservices.audit.event.AuditLogEventEnum;
import oracle.retail.stores.commerceservices.audit.event.AuthorizableTenderEvent;
import oracle.retail.stores.common.context.BeanLocator;
import oracle.retail.stores.domain.manager.payment.AuthorizeTransferResponseIfc;
import oracle.retail.stores.domain.tender.AuthorizableTenderIfc;
import oracle.retail.stores.domain.utility.EntryMethod;
import oracle.retail.stores.foundation.manager.device.EncipheredCardDataIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.ado.ADOException;
import oracle.retail.stores.pos.ado.context.ContextFactory;
import oracle.retail.stores.pos.ado.context.TourADOContext;
import oracle.retail.stores.pos.ado.journal.JournalActionEnum;
import oracle.retail.stores.pos.ado.journal.JournalFactory;
import oracle.retail.stores.pos.ado.journal.JournalFactoryIfc;
import oracle.retail.stores.pos.ado.journal.JournalFamilyEnum;
import oracle.retail.stores.pos.ado.journal.RegisterJournalIfc;
import oracle.retail.stores.pos.ado.tender.AuthorizedTenderADOBuilderIfc;
import oracle.retail.stores.pos.ado.tender.TenderADOIfc;
import oracle.retail.stores.pos.ado.tender.TenderConstants;
import oracle.retail.stores.pos.ado.tender.TenderException;
import oracle.retail.stores.pos.ado.tender.TenderTypeEnum;
import oracle.retail.stores.pos.ado.transaction.RetailTransactionADOIfc;
import oracle.retail.stores.pos.services.common.CheckTrainingReentryMode;
import oracle.retail.stores.pos.services.common.EventOriginatorInfoBean;
import oracle.retail.stores.pos.services.tender.authorization.AuthorizationCargo;

import org.apache.log4j.Logger;

/**
 * Receives the responses from the Tender Authorization service.
 * @author asinton
 * @since 13.4
 */
@SuppressWarnings("serial")
public class AuthorizationReturnShuttle implements ShuttleIfc
{
    /** Logger */
    public static final Logger logger = Logger.getLogger(AuthorizationReturnShuttle.class);

    /** handle to the list of response objects */
    protected List<AuthorizeTransferResponseIfc> responseList;

    /*
     * (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#load(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    public void load(BusIfc bus)
    {
        // save the response list from the authorization cargo
        AuthorizationCargo authorizationCargo = (AuthorizationCargo)bus.getCargo();
        responseList = authorizationCargo.getResponseList();
    }

    /*
     * (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#unload(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    public void unload(BusIfc bus)
    {
        // convert the responses into tenders
        AuthorizedTenderADOBuilderIfc builder = (AuthorizedTenderADOBuilderIfc)BeanLocator.getBean(BeanLocator.APPLICATION_CONTEXT_KEY, AuthorizedTenderADOBuilderIfc.BEAN_KEY);
        List<TenderADOIfc> tenders = builder.buildTenderADOs(responseList);

        // get local cargo and transaction
        TenderCargo tenderCargo = (TenderCargo)bus.getCargo();
        RetailTransactionADOIfc transaction = tenderCargo.getCurrentTransactionADO();

        // iterate through the tenders and add them to the transaction and the cargo's
        RegisterJournalIfc registerJournal = getRegisterJournal();
        for(TenderADOIfc tender : tenders)
        {
            try
            {
                Integer status =  (Integer) tender.getTenderAttributes().get(TenderConstants.AUTH_STATUS);
                if (status != null)
                {
                    switch (status.intValue())
                    {
                        case AuthorizableTenderIfc.AUTHORIZATION_STATUS_APPROVED:

                            //add to transaction and journal
                            tender.setDirtyFlag(true);
                            transaction.addTender(tender);
                            tenderCargo.setLineDisplayTender(tender);

                            registerJournal.journal(tender, JournalFamilyEnum.TENDER, JournalActionEnum.AUTHORIZATION);

                            // add audit log event
                            addAuditLogEvent(bus, tender, AuthorizableTenderEvent.AuthorizationStatus.APPROVED);
                            break;

                        default:

                            //journal only
                            registerJournal.journal(tender, JournalFamilyEnum.TENDER, JournalActionEnum.AUTHORIZATION_DECLINED);

                            // add audit log event
                            addAuditLogEvent(bus, tender, AuthorizableTenderEvent.AuthorizationStatus.DECLINED);
                            break;
                    }
                }
            }
            catch (TenderException te)
            {
                logger.error("TenderException caught while adding tender to the transaction", te);
            }
        }
        // create TourADOContext and set it on the context factory
        TourADOContext context = new TourADOContext(bus);
        context.setApplicationID(tenderCargo.getAppID());
        ContextFactory.getInstance().setContext(context);
    }

    /**
     * Get Register journal
     * @return the RegisterJournal
     */
    protected RegisterJournalIfc getRegisterJournal()
    {
        RegisterJournalIfc registerJournal = null;
        try
        {
            JournalFactoryIfc jrnlFact = JournalFactory.getInstance();
            registerJournal = jrnlFact.getRegisterJournal();
        }
        catch (ADOException e)
        {
            logger.error(JournalFactoryIfc.INSTANTIATION_ERROR, e);
            throw new RuntimeException(JournalFactoryIfc.INSTANTIATION_ERROR, e);
        }
        return registerJournal;
    }

    /**
     * Add auditlog entry for tender authorization
     * @param bus the bus
     * @param tender the tender
     * @param eventAuthStatus authorization status approved or declined
     */
    protected void addAuditLogEvent(BusIfc bus, TenderADOIfc tender, AuthorizableTenderEvent.AuthorizationStatus authStatus)
    {
        TenderCargo cargo = (TenderCargo) bus.getCargo();
        if (!CheckTrainingReentryMode.isTrainingOn(cargo.getRegister()))
        {
            HashMap<String,Object> tenderAttributes = tender.getTenderAttributes();
            AuthorizableTenderEvent event = (AuthorizableTenderEvent) AuditLoggingUtils.createLogEvent(
                    AuthorizableTenderEvent.class, AuditLogEventEnum.TRANSACTION_TENDERED_WITH_AUTHORIZABLE_TENDER);

            event.setStoreId(cargo.getRegister().getWorkstation().getStoreID());
            event.setRegisterID(cargo.getRegister().getWorkstation().getWorkstationID());
            event.setTillID(cargo.getRegister().getCurrentTillID());
            event.setUserId(cargo.getOperator().getLoginID());
            if (tenderAttributes.get(TenderConstants.TENDER_TYPE) != null)
            {
                TenderTypeEnum tenderType = (TenderTypeEnum)tenderAttributes.get(TenderConstants.TENDER_TYPE);
                event.setTenderType(tenderType.toString());
            }
            if (tenderAttributes.get(TenderConstants.ENCIPHERED_CARD_DATA) != null)
            {
                EncipheredCardDataIfc cardData = (EncipheredCardDataIfc)tenderAttributes.get(TenderConstants.ENCIPHERED_CARD_DATA);
                event.setCardNumber(cardData.getMaskedAcctNumber());
                event.setCardType(cardData.getCardName());
            }
            if (tenderAttributes.get(TenderConstants.ENTRY_METHOD) != null)
            {
                EntryMethod entryMethod = (EntryMethod)tenderAttributes.get(TenderConstants.ENTRY_METHOD);
                event.setEntryMethod(entryMethod.toString());
                event.setMsrSwipeIndicator(EntryMethod.Swipe.equals(entryMethod));
            }

            if (tenderAttributes.get(TenderConstants.AUTH_AMOUNT) != null)
            {
                String amount = (String)tenderAttributes.get(TenderConstants.AUTH_AMOUNT);
                event.setAmount(amount);
            }

            event.setEventOriginator(EventOriginatorInfoBean.getEventOriginator());
            event.setAuthStatus(authStatus);

            // log the event
            AuditLoggerServiceIfc auditService = AuditLoggingUtils.getAuditLogger();
            auditService.logStatusSuccess(event);
        }
    }
}
