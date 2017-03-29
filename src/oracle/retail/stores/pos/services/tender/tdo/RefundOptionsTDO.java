/* =============================================================================
* Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved. 
 * =============================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/tdo/RefundOptionsTDO.java /rgbustores_13.4x_generic_branch/4 2011/10/06 17:13:50 blarsen Exp $
 * =============================================================================
 * NOTES
 *
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   10/06/11 - Should no longer refund to original debit card.
 *                         Changing prompt to EnterAmountAndChoose.
 *    abondala  09/28/11 - add house account option in RM application
 *    sgu       09/08/11 - add house account as a refund tender
 *    rrkohli   05/09/11 - adding getStatusBean() method in utility class for
 *                         POS UI quickwin
 *    rrkohli   05/06/11 - pos ui quickwin
 *    npoola    12/20/10 - action button texts are moved to CommonActionsIfc
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *    nkgautam  04/11/09 - Fix for able to return deniable items for RM-POS
 *    vikini    04/10/09 - Checking if Manager OverRide Enabled.
 *    mdecama   12/22/08 - Adjusted Mapping of tender types from RM to POS.
 *    cgreene   12/17/08 - Use TenderTypeEnums for constants
 *    rkar      11/12/08 - Adds/changes for POS-RM integration
 *    rkar      11/07/08 - Additions/changes for POS-RM integration
 *
 * =============================================================================
     $Log:
      5    360Commerce 1.4         4/30/2008 1:54:32 PM   Maisa De Camargo CR
           31328 - Added a new scenario to the Refund Options Screen.   The
           scenarios are described in the ORPOS_Tender.doc. In order to
           maintain the priority, I have shifted the values of the
           refundOptionsRow. Code Reviewed by Jack Swan.
      4    360Commerce 1.3         3/24/2008 12:38:44 PM  Deepti Sharma   merge
            from v12.x to trunk
      3    360Commerce 1.2         3/31/2005 4:29:37 PM   Robert Pearse
      2    360Commerce 1.1         3/10/2005 10:24:37 AM  Robert Pearse
      1    360Commerce 1.0         2/11/2005 12:13:37 PM  Robert Pearse
     $
     Revision 1.8.2.1  2004/10/22 22:08:47  bwf
     @scr 7486, 7488 Made sure to get refund tenders during retrieve by customer.

     Revision 1.8  2004/08/12 20:46:36  bwf
     @scr 6567, 6069 No longer have to swipe debit or credit for return if original
                                 transaction tendered with one debit or credit.

     Revision 1.7  2004/06/15 16:46:02  awilliam
     @scr 5415 remove refund tenders parameter

     Revision 1.6  2004/05/06 14:53:43  awilliam
     @scr 4515 parameter for refund tender types

     Revision 1.5  2004/03/18 18:56:32  bwf
     @scr 3956 Update Refund Options Buttons.

     Revision 1.4  2004/03/17 19:24:50  bwf
     @scr 3956 Update Refund Options Buttons.

     Revision 1.3  2004/03/09 20:10:23  bwf
     @scr 3956 General Tenders work.

     Revision 1.2  2004/02/12 16:48:25  mcs
     Forcing head revision

     Revision 1.1.1.1  2004/02/11 01:04:12  cschellenger
     updating to pvcs 360store-current
 *
 *    Rev 1.3   Jan 28 2004 13:52:42   epd
 * Updated UI configuration
 *
 *    Rev 1.2   Nov 19 2003 15:59:14   cdb
 * Added verification of buttons to be enabled and disabled.
 * Resolution for 3465: House Account Enrollment during Return By Item Hangs App
 *
 *    Rev 1.1   Nov 19 2003 14:11:04   epd
 * TDO refactoring to use factory
 *
 *    Rev 1.0   Nov 04 2003 11:19:12   epd
 * Initial revision.
 *
 *    Rev 1.0   Oct 17 2003 12:45:26   epd
 * Initial revision.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.tdo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.manager.rm.RPIItemResponse;
import oracle.retail.stores.domain.manager.rm.RPIResponseIfc;
import oracle.retail.stores.domain.manager.rm.RPITenderType;
import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.domain.utility.LocaleUtilities;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.ado.ADO;
import oracle.retail.stores.pos.ado.ADOException;
import oracle.retail.stores.pos.ado.tender.TenderTypeEnum;
import oracle.retail.stores.pos.ado.transaction.RetailTransactionADOIfc;
import oracle.retail.stores.pos.ado.transaction.ReturnableTransactionADOIfc;
import oracle.retail.stores.pos.ado.utility.Utility;
import oracle.retail.stores.pos.ado.utility.UtilityIfc;
import oracle.retail.stores.pos.config.bundles.BundleConstantsIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.common.AbstractFinancialCargo;
import oracle.retail.stores.pos.services.common.CommonActionsIfc;
import oracle.retail.stores.pos.services.common.TagConstantsIfc;
import oracle.retail.stores.pos.tdo.TDOAdapter;
import oracle.retail.stores.pos.tdo.TDOUIIfc;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.pos.ui.beans.NavigationButtonBeanModel;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;
import oracle.retail.stores.pos.ui.beans.StatusBeanModel;
import oracle.retail.stores.pos.ui.beans.TenderBeanModel;

import org.apache.log4j.Logger;

/**
 *
 */
public class RefundOptionsTDO extends TDOAdapter
                                      implements TDOUIIfc
{
    // attributeMap constants
    public static final String BUS = "Bus";
    public static final String TRANSACTION = "Transaction";
    public static final String ORIG_RETURN_TXNS = "OriginalReturnTransactions";
    public static final String RETURN_RESPONSE = "ReturnResponse";
    /**
    The logger to which log messages will be sent.
     **/
    protected static Logger logger = Logger.getLogger(oracle.retail.stores.pos.services.tender.tdo.RefundOptionsTDO.class);


    /* (non-Javadoc)
     * @see oracle.retail.stores.tdo.TDOIfc#buildBeanModel(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    public POSBaseBeanModel buildBeanModel(HashMap attributeMap)
    {
        BusIfc bus = (BusIfc)attributeMap.get(BUS);
        RetailTransactionADOIfc txnADO = (RetailTransactionADOIfc)attributeMap.get(TRANSACTION);
        // Get RDO version of transaction for use in some processing
        TenderableTransactionIfc txnRDO = (TenderableTransactionIfc)((ADO)txnADO).toLegacy();

        // get new tender bean model
        TenderBeanModel model = new TenderBeanModel();
        // populate tender bean model w/ tender and totals info
        model.setTenderLineItems(txnRDO.getTenderLineItemsVector());
        model.setTransactionTotals(txnRDO.getTenderTransactionTotals());

        // set customer information
        StatusBeanModel sModel = getStatusBean(bus, txnRDO.getCustomer());
        if (sModel != null)
        {
            model.setStatusBeanModel(sModel);
        }

        // set prompt not editable if this return was NOT with a receipt
        if (txnADO instanceof ReturnableTransactionADOIfc &&
            !((ReturnableTransactionADOIfc)txnADO).isReturnWithReceipt())
        {
            PromptAndResponseModel parModel = new PromptAndResponseModel();
            model.setPromptAndResponseModel(parModel);
        }


        // set the local navigation button bean model
        RPIResponseIfc returnResponse = (RPIResponseIfc)attributeMap.get(RETURN_RESPONSE);
        if ( returnResponse == null )
        {
            model.setLocalButtonBeanModel(getNavigationBeanModel(txnADO.getEnabledRefundOptions()));

            model = setCorrectPromptAndButtons(model, txnADO, bus);
        }
        else
        {
            TenderTypeEnum[] tenderTypes = getEnabledRmRefundOptions(returnResponse, txnADO.getEnabledRefundOptions());
            model.setLocalButtonBeanModel(getRefundNavigationBeanModel(tenderTypes));

            model = setCorrectPromptAndButtonsForRmRefund(model, tenderTypes, bus);
        }

        // This is a return
        model.setReturn(true);

        return model;
    }

    //----------------------------------------------------------------------
    /**
     * Given the internal state of this transaction return an array of tenders which are valid for accepting as a
     * refund tenders.
     *
     * @return @see oracle.retail.stores.pos.ado.transaction.RetailTransactionADOIfc#getEnabledRefundOptions()
     */
    //----------------------------------------------------------------------
    private TenderTypeEnum[] getEnabledRmRefundOptions(RPIResponseIfc rpiReturnResponse, TenderTypeEnum[] enabledTypes)
    {
        List<RPIItemResponse> itemResponses = rpiReturnResponse.getItemRefundResponse();
        int numberOfReturnItems = itemResponses.size();

        List<SelectedTenderType> selectedTenderTypes = new ArrayList<SelectedTenderType>();

        Iterator<RPIItemResponse> iter = itemResponses.iterator();
        while ( iter.hasNext() )
        {
            RPIItemResponse rpiItemResponse = iter.next();
            List<RPITenderType> returnTenderTypes = rpiItemResponse.getRefundTenderTypes();
            if ( returnTenderTypes==null || returnTenderTypes.size()==0 )
            {
                //RM returns maintain tenders
                for (int i=0; i<enabledTypes.length; i++)
                {
                    SelectedTenderType selectedTenderType = getSelectedTenderType(selectedTenderTypes, enabledTypes[i]);
                    if ( selectedTenderType == null  )
                    {
                        selectedTenderType = new SelectedTenderType();
                        selectedTenderType.setTenderType(enabledTypes[i]);
                        selectedTenderType.setSelected(1); //the first return item select
                    }
                    else
                    {
                        selectedTenderType.setSelected(selectedTenderType.getSelected()+1);
                    }
                    selectedTenderTypes.add(selectedTenderType);
                }
            }
            else
            {
                //use the original transaction's tender types
                Iterator<RPITenderType> tenderTypeIter = returnTenderTypes.iterator();
                while ( tenderTypeIter.hasNext() )
                {
                    RPITenderType rpiTenderType = tenderTypeIter.next();
                    String tenderType = rpiTenderType.getType();
                    TenderTypeEnum tenderTypeEnum = mapTOTenderTypeEnum(tenderType);
                    SelectedTenderType selectedTenderType = getSelectedTenderType(selectedTenderTypes, tenderTypeEnum);
                    if ( selectedTenderType == null  )
                    {
                        //first time find the tender type as a refund tender
                        selectedTenderType = new SelectedTenderType();
                        selectedTenderType.setTenderType(tenderTypeEnum);
                        selectedTenderType.setSelected(1);
                    }
                    else
                    {
                        //find one more time.
                        selectedTenderType.setSelected(selectedTenderType.getSelected()+1);
                    }
                    selectedTenderTypes.add(selectedTenderType);
                 }
            }
        }
        List<TenderTypeEnum> tenderList = new ArrayList<TenderTypeEnum>(6);
        Iterator<SelectedTenderType> selectedTenderIter = selectedTenderTypes.iterator();
        while ( selectedTenderIter.hasNext() )
        {
          SelectedTenderType selectedTenderType = selectedTenderIter.next();
          tenderList.add(selectedTenderType.getTenderType());
        }

        // convert list to array
        TenderTypeEnum[] tenderTypeArray = new TenderTypeEnum[tenderList.size()];
        tenderTypeArray = tenderList.toArray(tenderTypeArray);
        return tenderTypeArray;
    }

    /**
     * This method sets the correct buttons and the prompt.
     *
     * @param model
     * @param txnADO
     * @param bus
     * @return
     */
    protected TenderBeanModel setCorrectPromptAndButtonsForRmRefund(TenderBeanModel model, TenderTypeEnum[] tenderTypes, BusIfc bus)
    {
        PromptAndResponseModel prModel = model.getPromptAndResponseModel();
        // if we havent created one yet, then make a new one
        if (prModel == null)
        {
            prModel = new PromptAndResponseModel();
        }

        UtilityManagerIfc util = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        // get the correct prompt args based on the refund options row
        // this row corresponds to the refund options requirements for button availability and prompt text
        if (tenderTypes != null)
        {
            prModel.setArguments(util.retrieveText("RefundOptionsSpec", "tenderText", "EnterAmountAndChoose", "EnterAmountAndChoose"));
        }

        // set prompt and response
        model.setPromptAndResponseModel(prModel);
        return model;
    }


    /**
     * This method sets the correct buttons and the message in the
     * PromptAndResponse area. The Message is based on the Refund Options
     * scenarios described in the ORPOS_Tender.doc
     *
     * @param model
     * @param txnADO
     * @param bus
     * @return
     */
    protected TenderBeanModel setCorrectPromptAndButtons(TenderBeanModel model, RetailTransactionADOIfc txnADO, BusIfc bus)
    {
        PromptAndResponseModel prModel = model.getPromptAndResponseModel();
        // if we havent created one yet, then make a new one
        if (prModel == null)
        {
            prModel = new PromptAndResponseModel();
        }
        boolean setNext = false;
        UtilityManagerIfc util = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        // get the correct prompt args based on the refund options row
        // this row corresponds to the refund options requirements for button availability and prompt text
        switch (txnADO.getRefundOptionsRow())
        {
            case 1:
            case 2:
            case 3:
            case 8:
            case 10:
                prModel.setArguments(util.retrieveText("RefundOptionsSpec", "tenderText", "EnterAmountAndChoose", "EnterAmountAndChoose"));
                break;
            case 4:
                prModel.setArguments(util.retrieveText("RefundOptionsSpec", "tenderText", "NextForCreditRefund", "NextForCreditRefund"));
                setNext=true;
                break;
            case 5:
            case 6:
            case 7:
                prModel.setArguments(util.retrieveText("RefundOptionsSpec", "tenderText", "NextForCash", "NextForCash"));
                setNext=true;
                // set local buttons disabled
                model.setLocalButtonBeanModel(getNavigationBeanModel(new TenderTypeEnum[0]));
                break;
            case 11:
                prModel.setArguments(util.retrieveText("RefundOptionsSpec", "tenderText", "NextForCashOrChoose", "NextForCashOrChoose"));
                setNext=true;
                break;
            case 12:
                prModel.setArguments(util.retrieveText("RefundOptionsSpec", "tenderText", "NextForGiftCard", "NextForGiftCard"));
                setNext=true;
                break;
            case 13:
                prModel.setArguments(util.retrieveText("RefundOptionsSpec", "tenderText", "NextForStoreCredit", "NextForStoreCredit"));
                setNext=true;
                break;
            case 0:
                // set local buttons disabled
                model.setLocalButtonBeanModel(getNavigationBeanModel(new TenderTypeEnum[0]));
                // fall through here on purpose
            case 9:
                prModel.setArguments(util.retrieveText("RefundOptionsSpec", "tenderText", "NextForStoreCredit", "NextForStoreCredit"));
                setNext=true;
                break;
            case 14:
                prModel.setArguments(util.retrieveText("RefundOptionsSpec", "tenderText", "EnterAmountAndChoose", "EnterAmountAndChoose"));
                break;
            case 15:
                prModel.setArguments(util.retrieveText("RefundOptionsSpec", "tenderText", "NextForHouseAccountRefund", "NextForHouseAccountRefund"));
                setNext=true;
                break;
        }

        // set next button
        if (setNext)
        {
            NavigationButtonBeanModel navModel = new NavigationButtonBeanModel();
            navModel.setButtonEnabled(CommonActionsIfc.NEXT, true);
            model.setGlobalButtonBeanModel(navModel);
        }
        // set prompt and response
        model.setPromptAndResponseModel(prModel);
        return model;
    }

    /**
     * builds status bean based on customer information
     * @param bus
     * @param customer
     * @return
     */
    protected StatusBeanModel getStatusBean(BusIfc bus, CustomerIfc customer)
    {
        StatusBeanModel sModel = null;
        sModel = UIUtilities.getStatusBean((AbstractFinancialCargo) bus.getCargo());
        if (customer != null)
        {
            String[] vars = { customer.getFirstName(), customer.getLastName() };
            UtilityManagerIfc utility = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);
            String pattern = utility.retrieveText("CustomerAddressSpec",
                                                  BundleConstantsIfc.CUSTOMER_BUNDLE_NAME,
                                                  TagConstantsIfc.CUSTOMER_NAME_TAG,
                                                  TagConstantsIfc.CUSTOMER_NAME_PATTERN_TAG,
                                                  LocaleConstantsIfc.USER_INTERFACE);
            String customerName = LocaleUtilities.formatComplexMessage(pattern,vars);
            sModel.setCustomerName(customerName);
        }
        return sModel;
    }

    /**
     * enables/disables tender buttons as they exist in enabledTypes array.
     * @param enabledTypes
     * @return
     */
    protected NavigationButtonBeanModel getNavigationBeanModel(TenderTypeEnum[] enabledTypes)
    {
        // convert to list
        ArrayList<TenderTypeEnum> typeList = new ArrayList<TenderTypeEnum>(enabledTypes.length);
        for (int i = 0; i < enabledTypes.length; i++)
        {
            typeList.add(enabledTypes[i]);
        }
        NavigationButtonBeanModel navModel = new NavigationButtonBeanModel();
        navModel.setButtonEnabled(CommonActionsIfc.CASH, typeList.contains(TenderTypeEnum.CASH));
        navModel.setButtonEnabled(CommonActionsIfc.MAIL_CHECK, typeList.contains(TenderTypeEnum.MAIL_CHECK));
        navModel.setButtonEnabled(CommonActionsIfc.CREDIT, typeList.contains(TenderTypeEnum.CREDIT));
        navModel.setButtonEnabled(CommonActionsIfc.DEBIT, typeList.contains(TenderTypeEnum.DEBIT));
        navModel.setButtonEnabled(CommonActionsIfc.GIFT_CARD, typeList.contains(TenderTypeEnum.GIFT_CARD));
        navModel.setButtonEnabled(CommonActionsIfc.STORE_CREDIT, typeList.contains(TenderTypeEnum.STORE_CREDIT));
        navModel.setButtonEnabled(CommonActionsIfc.HOUSEACCOUNT, typeList.contains(TenderTypeEnum.HOUSE_ACCOUNT));

        return navModel;
    }

    protected NavigationButtonBeanModel getRefundNavigationBeanModel(TenderTypeEnum[] enabledTypes)
    {
        // convert to list
        List<TenderTypeEnum> typeList = new ArrayList<TenderTypeEnum>(enabledTypes.length);
        boolean isManagerOverrideEnabled = false;

        for (int i = 0; i < enabledTypes.length; i++)
        {
            typeList.add(enabledTypes[i]);
        }

        try
        {
          UtilityIfc utility = Utility.createInstance();

          String[] paramValues = utility.getParameterValueList("ManagerOverrideForSecurityAccess");

          for(int ctr = 0 ; ctr < paramValues.length; ctr++)
          {
            if(paramValues[ctr].equals("RefundTenderOverride"))
            {
              isManagerOverrideEnabled = true;
              break;
            }

          }

        }
        catch (ADOException e)
        {
          isManagerOverrideEnabled = true;
        }

        NavigationButtonBeanModel navModel = new NavigationButtonBeanModel();
        navModel.setButtonEnabled(CommonActionsIfc.CASH, typeList.contains(TenderTypeEnum.CASH));
        navModel.setButtonEnabled(CommonActionsIfc.MAIL_CHECK, typeList.contains(TenderTypeEnum.MAIL_CHECK));
        navModel.setButtonEnabled(CommonActionsIfc.CREDIT, typeList.contains(TenderTypeEnum.CREDIT));
        navModel.setButtonEnabled(CommonActionsIfc.DEBIT, typeList.contains(TenderTypeEnum.DEBIT));
        navModel.setButtonEnabled(CommonActionsIfc.GIFT_CARD, typeList.contains(TenderTypeEnum.GIFT_CARD));
        navModel.setButtonEnabled(CommonActionsIfc.STORE_CREDIT, typeList.contains(TenderTypeEnum.STORE_CREDIT));
        navModel.setButtonEnabled(CommonActionsIfc.MANAGER_OVERRIDE, isManagerOverrideEnabled);
        return navModel;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.tdo.TDOUIIfc#formatPoleDisplayLine1(oracle.retail.stores.pos.ado.transaction.RetailTransactionADOIfc)
     */
    public String formatPoleDisplayLine1(RetailTransactionADOIfc txnADO)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.pos.tdo.TDOUIIfc#formatPoleDisplayLine2(oracle.retail.stores.pos.ado.transaction.RetailTransactionADOIfc)
     */
    public String formatPoleDisplayLine2(RetailTransactionADOIfc txnADO)
    {
        return null;
    }

    protected TenderTypeEnum mapTOTenderTypeEnum(String tenderType)
    {
        TenderTypeEnum tenderTypeEnum = null;

        if ( tenderType.equals("Gift_Card"))
        {
            tenderTypeEnum = TenderTypeEnum.GIFT_CARD;
        }
        else if ( tenderType.equals("House_Account"))
        {
            tenderTypeEnum = TenderTypeEnum.HOUSE_ACCOUNT;
        }
        else if ( tenderType.equals("Mail_Bank_Check"))
        {
            tenderTypeEnum = TenderTypeEnum.MAIL_CHECK;
        }
        else if ( tenderType.equals("Cash"))
        {
            tenderTypeEnum = TenderTypeEnum.CASH;
        }
        else if ( tenderType.equals("Check"))
        {
            tenderTypeEnum = TenderTypeEnum.MAIL_CHECK;
        }
        else if ( tenderType.equals("Credit"))
        {
            tenderTypeEnum = TenderTypeEnum.CREDIT;
        }
        else if ( tenderType.equals("Store_Credit") || tenderType.equals("StoreCredit") )
        {
            tenderTypeEnum = TenderTypeEnum.STORE_CREDIT;
        }
        return tenderTypeEnum;
    }

    /**
     *
     * @param tenderTypes
     * @param tenderTypeEnum
     * @return
     */
    protected SelectedTenderType getSelectedTenderType(List<SelectedTenderType> tenderTypes, TenderTypeEnum tenderTypeEnum)
    {
        SelectedTenderType selectedTenderType = null;

        Iterator<SelectedTenderType> iter = tenderTypes.iterator();
        while ( iter.hasNext() )
        {
            SelectedTenderType tenderType = iter.next();
            if (tenderType.getTenderType().toString().equals(tenderTypeEnum.toString()))
            {
                selectedTenderType = tenderType;
                break;
            }
        }
        return selectedTenderType;
    }

    /**
     * The inner class is for finding out the return refund tender.
     * A tender type will be selected as refund tender only when all the return items has
     * specified the tender type as a refund tender.
     *
     */
    class SelectedTenderType
    {
        protected TenderTypeEnum tenderType;

        /** number of times selected for all the refund items. */
        protected int selected;

        public SelectedTenderType() {}

        public TenderTypeEnum getTenderType() {
            return tenderType;
        }

        public void setTenderType(TenderTypeEnum tenderType) {
            this.tenderType = tenderType;
        }

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }

    }
}
