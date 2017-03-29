/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/dailyoperations/till/tillopen/UpdateStatusSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:21 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    abhayg    08/13/10 - STOPPING POS TRANSACTION IF REGISTER HDD IS FULL
 *    acadar    06/10/10 - use default locale for currency display
 *    acadar    06/09/10 - XbranchMerge acadar_tech30 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    acadar    04/09/10 - optimize calls to LocaleMAp
 *    acadar    04/08/10 - merge to tip
 *    acadar    04/06/10 - use default locale for currency, date and time
 *                         display
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *    nganesh   03/26/09 - Modified code to handle denomination
 *                         internationalization for Auditlog and EJournal
 *    nganesh   03/20/09 - Internationalized EJ for Denomination
 *                         internationalization refactoring
 *    deghosh   02/02/09 - EJ i18n defect fixes
 *    deghosh   11/25/08 - EJ I18n
 *
 * ===========================================================================
 * $Log:
 *    10   360Commerce 1.9         2/29/2008 5:28:13 AM   Chengegowda Venkatesh
 *          fix for CR : 30345
 *    9    360Commerce 1.8         1/10/2008 7:54:21 AM   Manas Sahu      Event
 *          Originator Changes
 *    8    360Commerce 1.7         1/7/2008 8:32:24 AM    Chengegowda Venkatesh
 *          PABP FR40 : Changes for AuditLog incorporation
 *    7    360Commerce 1.6         8/13/2007 3:01:32 PM   Charles D. Baker CR
 *         27803 - Remove unused domain property.
 *    6    360Commerce 1.5         7/10/2007 4:51:51 PM   Charles D. Baker CR
 *         27506 - Updated to remove old fix for truncating extra decimal
 *         places that are used for accuracy. Truncating is no longer
 *         required.
 *    5    360Commerce 1.4         5/18/2007 9:18:13 AM   Anda D. Cadar   EJ
 *         and currency UI changes
 *    4    360Commerce 1.3         4/25/2007 8:52:29 AM   Anda D. Cadar   I18N
 *         merge
 *
 *    3    360Commerce 1.2         3/31/2005 4:30:40 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:26:37 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:15:26 PM  Robert Pearse
 *
 *   Revision 1.5  2004/07/07 18:27:11  dcobb
 *   @scr 1734 Wrong error message when attempt to open another till in reg acct.
 *   Fixed in CheckTillStatusSite. Moved deprecated TillOpenCargo to the deprecation tree and imported new TillCargo from _360commerce tree..
 *
 *   Revision 1.4  2004/03/03 23:15:09  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.3  2004/02/12 16:50:00  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:46:45  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:15  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 15:58:04   CSchellenger
 * Initial revision.
 *
 *    Rev 1.4   Jan 10 2003 13:36:34   sfl
 * Shorted the printed float amount to be two digits after
 * decimal point.
 * Resolution for POS SCR-1749: POS 6.0 Tax Package
 *
 *    Rev 1.3   Dec 20 2002 11:32:06   DCobb
 * Add floating till.
 * Resolution for POS SCR-1867: POS 6.0 Floating Till
 *
 *    Rev 1.2   Sep 03 2002 16:03:44   baa
 * externalize domain  constants and parameter values
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.1   May 13 2002 19:36:06   mpm
 * Added support for till open/close and register open/close transactions.
 * Resolution for POS SCR-1630: Make changes to support TLog facility.
 *
 *    Rev 1.0   Apr 29 2002 15:27:54   msg
 * Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.dailyoperations.till.tillopen;

import java.util.Hashtable;
import java.util.Locale;


import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.utility.JournalConstantsIfc;

import oracle.retail.stores.commerceservices.audit.AuditLoggerConstants;
import oracle.retail.stores.commerceservices.audit.AuditLoggerServiceIfc;
import oracle.retail.stores.commerceservices.audit.AuditLoggingUtils;
import oracle.retail.stores.commerceservices.audit.event.AuditLogEventEnum;
import oracle.retail.stores.commerceservices.audit.event.TillOpenEvent;
import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.dailyoperations.till.tillopen.TillOpenCargo;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.financial.AbstractFinancialEntityIfc;
import oracle.retail.stores.domain.financial.AbstractStatusEntityIfc;
import oracle.retail.stores.domain.financial.DrawerIfc;
import oracle.retail.stores.domain.financial.FinancialCountIfc;
import oracle.retail.stores.domain.financial.FinancialCountTenderItemIfc;
import oracle.retail.stores.domain.financial.FinancialTotalsIfc;
import oracle.retail.stores.domain.financial.ReconcilableCountIfc;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.financial.TillIfc;
import oracle.retail.stores.domain.transaction.TillOpenCloseTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CheckTrainingReentryMode;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;
//--------------------------------------------------------------------------
/**
    Updates the existing till status and totals or inserts the till status
    and totals.
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class UpdateStatusSite extends PosSiteActionAdapter
{
    /**
     *
     */
    private static final long serialVersionUID = 4999363114232915964L;
    /**
       revision number for this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
       Attempts to update the existing till status and till totals. If this
       fails, then inserts the till status and till totals.
       <P>
       @param bus the bus arriving at this site
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        UtilityManagerIfc utility =
          (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
        TillOpenCargo cargo = (TillOpenCargo) bus.getCargo();
        
        boolean saveTransSuccess = true;
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);

        Locale defaultLocale = LocaleMap.getLocale(LocaleConstantsIfc.DEFAULT_LOCALE);

        // for Auditlogging
        TillOpenEvent ev = (TillOpenEvent)AuditLoggingUtils.createLogEvent(TillOpenEvent.class, AuditLogEventEnum.TILL_OPEN);
        AuditLoggerServiceIfc auditService = AuditLoggingUtils.getAuditLogger();
        Hashtable denominationQuantities = new Hashtable();
        String logTotalFloatAmount = AuditLoggerConstants.FLOAT_AMOUNT;

        // Local copy of register. Update if everything ok
        RegisterIfc register = (RegisterIfc) cargo.getRegister().clone();
        TillIfc till = cargo.getTill();
        String letterName = CommonLetterIfc.SUCCESS;

        // Set till status to OPEN
        till.setStatus(AbstractFinancialEntityIfc.STATUS_OPEN);
        till.setDrawerID("");
        till.setBusinessDate(register.getBusinessDate());
        till.setOpenTime();
        till.setSignOnOperator(cargo.getOperator());
        till.setRegisterAccountability(register.getAccountability());

        // create till open/close transaction
        TillOpenCloseTransactionIfc transaction =
          DomainGateway.getFactory().getTillOpenCloseTransactionInstance();

        // set count for safe
        transaction.setStartingFloatCount(till.getTotals().getStartingFloatCount().getEntered());
        transaction.setTenderDescriptorArrayList(cargo.getStoreStatus().getSafeTenderTypeDescList());

        // set drawer status as occupied in register
         //Set current till id to this till
        // Assume only one drawer in this register
        register.setCurrentTillID(cargo.getTillID());
        register.getDrawer(DrawerIfc.DRAWER_PRIMARY)
                .setDrawerStatus(AbstractStatusEntityIfc.DRAWER_STATUS_OCCUPIED, cargo.getTillID());

        // Add opened till to register
        register.addTill(till);

        // Set register in cargo
        cargo.setRegister(register);

        // successfully added/updated the till
        transaction.setTransactionType(TransactionIfc.TYPE_OPEN_TILL);
        utility.initializeTransaction(transaction, bus, -1);
        transaction.setTimestampEnd();
        transaction.setTransactionStatus(TransactionIfc.STATUS_COMPLETED);
        transaction.setTill(till);
        transaction.setRegister(register);

        // get the Journal manager
        JournalManagerIfc jmi =
            (JournalManagerIfc) Gateway.getDispatcher().getManager(JournalManagerIfc.TYPE);

        // journal the till status
        if (jmi != null)
        {
            StringBuffer sb = new StringBuffer();

            // default journal Title, TillID for Parameter 'No', 'Summary', 'Detail'.
            Object[] dataArgs = new Object[2];
			dataArgs[0] = cargo.getTillID();
			sb.append(
					I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
							JournalConstantsIfc.OPEN_TILL_LABEL, null)).append(
					Util.EOL).append(
					I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
							JournalConstantsIfc.HEADER_LINE_LABEL, null))
					.append(Util.EOL).append(Util.EOL).append(
							I18NHelper
									.getString(I18NConstantsIfc.EJOURNAL_TYPE,
											JournalConstantsIfc.TILL_ID_LABEL,
											dataArgs));


            // additional journal output for both Parameter 'Summary' and 'Detail.
            if (cargo.getFloatCountType() == FinancialCountIfc.COUNT_TYPE_DETAIL
                || cargo.getFloatCountType() == FinancialCountIfc.COUNT_TYPE_SUMMARY)
            {
                FinancialTotalsIfc fti = cargo.getFloatTotals();
                ReconcilableCountIfc rci = fti.getStartingFloatCount();
                FinancialCountIfc fci = rci.getEntered();
                FinancialCountTenderItemIfc[] fcti = fci.getTenderItems();
                CurrencyIfc ci = fci.getAmount();


                ParameterManagerIfc pm = (ParameterManagerIfc)
                    bus.getManager(ParameterManagerIfc.TYPE);

                // journal output for 'Unexpected Float Amount Accepted' condition
                String floatAmount = register.getTillFloatAmount().toFormattedString();

                if (!ci.getStringValue().equals(floatAmount))
                {
                	dataArgs[0] = floatAmount;
                	sb.append(Util.EOL)
                        .append(Util.EOL)
                        .append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.EXPECTED_FLOAT_AMOUNT_LABEL, dataArgs));
                }

                // check to see if TillCountTillOpen parameter is set to 'Detail'
                // journal output for Parameter 'Detail'.
                if (cargo.getFloatCountType() == FinancialCountIfc.COUNT_TYPE_DETAIL)
                {
                    sb.append(Util.EOL);

                    for (int i = 0; i < fcti.length; i++)
                    {
                        if (fcti[i].isSummary() == false)
                        {
                            String desc = fcti[i].getDescription();
                            String i18nDesc = fcti[i].getTenderDescriptor().getDenomination().getDenominationDisplayName(LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL));

                            String i18nAuditDesc = fcti[i].getTenderDescriptor().getDenomination().getDenominationDisplayName(defaultLocale);
                            int num = fcti[i].getNumberItemsIn();
                            dataArgs = new Object []{i18nDesc,num};
                            String trCurrDesc =I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.TILL_CURR_DETAIL,dataArgs);
                            sb.append(Util.EOL)
                                .append(trCurrDesc);

                            // for Auditlogging

                            //desc = i18nAuditDesc + "_"+fcti[i].getCurrencyCode();
                            denominationQuantities.put(i18nAuditDesc,String.valueOf(num));
                        }
                    }
                }

                // journal Total Float for Parameter 'Detail', 'Summary'
                String totalFloatString = ci.toFormattedString();
                dataArgs[0] = totalFloatString;
                sb.append(Util.EOL)
                    .append(Util.EOL)
                    .append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.TOTAL_FLOAT_LABEL, dataArgs)) ;

                    sb.append(Util.EOL)
                    .append(Util.EOL)
                    .append(Util.EOL);

                    // for Auditlogging
                    logTotalFloatAmount=totalFloatString;
            }

            jmi.journal(cargo.getOperator().getEmployeeID(),
                        transaction.getTransactionID(),
                        sb.toString());

    		// for Auditlogging
        ev.setUserId(transaction.getCashier().getLoginID());
		ev.setStoreId(transaction.getFormattedStoreID());
		ev.setRegisterID(transaction.getWorkstation().getWorkstationID());
		ev.setBusinessDate(transaction.getBusinessDay().dateValue());
		ev.setTillID(transaction.getTillID());
		ev.setOperatorID(transaction.getCashier().getEmployeeID());
		ev.setTransactionNumber(transaction.getTransactionID());
        ev.addDenomination(denominationQuantities,null);
        ev.setFloatAmount(logTotalFloatAmount);
        ev.setEventOriginator("LookupStoreStatusSite.arrive");
        }
        else
        {
            logger.warn( "No journal manager found.");
        }

        // Write a transaction to the DB
        try
        {
            utility.saveTransaction(transaction);

            // for Auditlogging
            if(!CheckTrainingReentryMode.isTrainingRetryOn(cargo.getRegister()))
            {
            	auditService.logStatusSuccess(ev);
            }
        }
        catch (DataException se)
        {
            // for Auditlogging
            if(!CheckTrainingReentryMode.isTrainingRetryOn(cargo.getRegister()))
            {
            	auditService.logStatusFailure(ev);
            }
            logger.error( "" + se + "");
            if (se.getErrorCode() == DataException.QUEUE_FULL_ERROR ||
        			se.getErrorCode() == DataException.STORAGE_SPACE_ERROR ||
        			se.getErrorCode() == DataException.QUEUE_OP_FAILED)
        	{
        		saveTransSuccess = false;
        		DialogBeanModel dialogModel = utility.createErrorDialogBeanModel(se, false);
        		// display dialog
        		ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE,dialogModel);
        	}
        	else
        	{
        		// set error code
        		cargo.setDataExceptionErrorCode(se.getErrorCode());
        		letterName = CommonLetterIfc.UPDATE_ERROR;
        	}
        }
        if (saveTransSuccess)
        {
        	bus.mail(new Letter(letterName), BusIfc.CURRENT);
        }
    }  // end arrive

}
