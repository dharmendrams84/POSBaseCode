/* ===========================================================================
* Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/sale/CheckRelatedItemDeletableSite.java /main/11 2011/02/16 09:13:29 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 1    360Commerce 1.0         12/13/2005 4:47:03 PM  Barry A. Pape   
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.sale;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.pos.ui.beans.LineItemsModel;

/**
 * This site checks if a related item is deletable. If it is not, then we
 * display a message saying it is not.
 * 
 * @version $Revision: /main/11 $
 */
public class CheckRelatedItemDeletableSite extends PosSiteActionAdapter
{    
    private static final long serialVersionUID = 2073061673413249847L;
    /**
     * This defines the dialog screen to display from the bundles.
     */
    public static String DELETE_INVALID = "DeleteInvalid";
    
    /**
     * This method checks if any of the items are not deletable because
     * of the related item flag.
     *
     * @param bus
     * @see oracle.retail.stores.foundation.tour.ifc.SiteActionIfc#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    public void arrive(BusIfc bus)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        LineItemsModel beanModel                 = (LineItemsModel)ui.getModel();
        int[] allSelected                        = beanModel.getRowsToDelete();
        boolean deleteAllowed = true;
        SaleCargoIfc cargo = (SaleCargoIfc)bus.getCargo();
        SaleReturnTransactionIfc transaction = cargo.getTransaction();
        for(int i = 0;i < allSelected.length;i++)
        {
            AbstractTransactionLineItemIfc item = transaction.retrieveItemByIndex(allSelected[i]);
            if (item instanceof SaleReturnLineItemIfc)
            {
                if(!((SaleReturnLineItemIfc)item).isRelatedItemDeleteable())
                {
                    deleteAllowed = false;
                    break;
                }
            }
        }
        
        
        
        
        if (!deleteAllowed)
        {
            UIUtilities.setDialogModel(ui, 
                            DialogScreensIfc.ERROR, 
                            DELETE_INVALID, 
                            null, 
                            CommonLetterIfc.FAILURE);
            return;                
        }
        
        Letter letter = new Letter(CommonLetterIfc.CONTINUE);
        bus.mail(letter, BusIfc.CURRENT);                
    }
}
