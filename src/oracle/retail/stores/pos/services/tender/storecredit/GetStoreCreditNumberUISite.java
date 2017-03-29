/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/storecredit/GetStoreCreditNumberUISite.java /rgbustores_13.4x_generic_branch/3 2011/07/12 15:58:32 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/12/11 - update generics
 *    cgreene   07/07/11 - convert entryMethod to an enum
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    ohorne    03/24/09 - setting setMaxLength(14) instead of setMinLength(14)
 *                         in PrePrintedStoreCredit block
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender.storecredit;

import java.util.HashMap;

import oracle.retail.stores.common.parameter.ParameterConstantsIfc;
import oracle.retail.stores.domain.utility.EntryMethod;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.LetterIfc;
import oracle.retail.stores.pos.ado.tender.TenderConstants;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.tender.TenderCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.POSBaseBeanModel;
import oracle.retail.stores.pos.ui.beans.PromptAndResponseModel;

/**
 * @author blj
 */
@SuppressWarnings("serial")
public class GetStoreCreditNumberUISite extends PosSiteActionAdapter
{
    /**
     * commonSpec
     */
    public static final String COMMON = "Common";
    /**
     * default
     */
    public static final String DEFAULT = "";

    /**
     * This is the arrive method which will display the screen.
     * 
     * @param bus BusIfc
     */
    @Override
    public void arrive(BusIfc bus)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        UtilityManagerIfc utilityMgrIfc = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);
        TenderCargo cargo = (TenderCargo)bus.getCargo();
        POSBaseBeanModel beanModel = new POSBaseBeanModel();
        PromptAndResponseModel promptModel = new PromptAndResponseModel();
        HashMap<String,Object> hashMapKey = cargo.getTenderAttributes();
        String selectedVal = (String)hashMapKey.get(TenderConstants.ID_TYPE);
        String idType = utilityMgrIfc.retrieveCommonText(selectedVal, DEFAULT);
        hashMapKey.put(TenderConstants.ID_TYPE, idType);
        cargo.setTenderAttributes(hashMapKey);
        ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
        boolean preprintedStoreCredit;
        try
        {
            preprintedStoreCredit = pm.getBooleanValue(ParameterConstantsIfc.TENDER_PrePrintedStoreCredit);
            if (preprintedStoreCredit)
            {
                promptModel.setMaxLength("12");
            }
            else
            {
                promptModel.setMaxLength("14");
            }

            beanModel.setPromptAndResponseModel(promptModel);

            ui.showScreen(POSUIManagerIfc.STORE_CREDIT_TENDER_NUMBER, beanModel);
        }
        catch (ParameterException e)
        {
            logger.error("Unable to determine PrePrintedStoreCredit parameter.", e);
        }
    }

    /**
     * Depart method retrieves input.
     * 
     * @param bus Service Bus
     */
    @Override
    public void depart(BusIfc bus)
    {
        LetterIfc letter = bus.getCurrentLetter();
        TenderCargo cargo = (TenderCargo)bus.getCargo();

        // If the user entered a gift certificate number
        if (letter.getName().equals(CommonLetterIfc.NEXT))
        {
            POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
            boolean isScanned = ((POSBaseBeanModel)ui.getModel()).getPromptAndResponseModel().isScanned();

            // Get the gift certificate number and put in the cargo
            cargo.getTenderAttributes().put(TenderConstants.NUMBER, ui.getInput().trim());

            if (isScanned)
            {
                cargo.getTenderAttributes().put(TenderConstants.ENTRY_METHOD, EntryMethod.Automatic);
            }
            else
            {
                cargo.getTenderAttributes().put(TenderConstants.ENTRY_METHOD, EntryMethod.Manual);
            }
        }
    }
}
