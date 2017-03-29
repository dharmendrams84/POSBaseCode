/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/send/displaysendmethod/DisplayMultipleGeoCodesSite.java /rgbustores_13.4x_generic_branch/2 2011/08/09 11:31:52 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   08/09/11 - formatting and removed deprecated code
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:48 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:21:04 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:39 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/06/07 18:28:37  jdeleau
 *   @scr 2775 Support multiple GeoCodes tax screen
 *
 *   Revision 1.1  2004/06/03 22:49:27  jdeleau
 *   @scr 2775 For tax on send Item, prepare for the upcoming requirement 
 *   to put a screen up for the user to select GeoCode.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.send.displaysendmethod;

import java.util.Vector;

import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.tax.GeoCodeVO;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.send.address.SendCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.ListBeanModel;

/**
 *
 * $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
@SuppressWarnings("serial")
public class DisplayMultipleGeoCodesSite extends PosSiteActionAdapter
{
    /**
     * Arrive at the site, and display all the geoCodes, requesting that the user select one.
     *  
     * @param bus
     * @see oracle.retail.stores.foundation.tour.ifc.SiteActionIfc#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void arrive(BusIfc bus)
    {
        SendCargo cargo = (SendCargo) bus.getCargo();
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        
        ListBeanModel beanModel = new ListBeanModel();
        beanModel.setListModel(cargo.getGeoCodes());
        ui.setModel(POSUIManagerIfc.MULTIPLE_GEO_CODES, beanModel);
        ui.showScreen(POSUIManagerIfc.MULTIPLE_GEO_CODES, beanModel);
    }
    
    /**
     * Leaving the site.  Someone did something on the MultipleGeoCode UI that
     * is causing us to leave.
     *  
     * @param bus
     * @see oracle.retail.stores.foundation.tour.ifc.SiteActionIfc#depart(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void depart(BusIfc bus)
    {
        SendCargo cargo = (SendCargo) bus.getCargo();
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        
        if(bus.getCurrentLetter().getName().equals(CommonLetterIfc.UNDO))
        {
            // User changed their mind.  Cancel out the sendTax rules. This
            // is just a sanity check, this should not be populated at this point,
            // to begin with.  But just in case it is...
            Vector<AbstractTransactionLineItemIfc> lineItems = cargo.getRetailTransaction().getLineItemsVector();
            for(int i=0; i<lineItems.size(); i++)
            {
                SaleReturnLineItemIfc lineItem = (SaleReturnLineItemIfc) lineItems.elementAt(i);
                lineItem.getItemPrice().getItemTax().setSendTaxRules(null);
            }
        }
        else
        {
            // Set the selected GeoCode for the GetTaxRulesSite to use to perform its calculations.
            ListBeanModel beanModel = (ListBeanModel)ui.getModel();
            GeoCodeVO selectedGeoCode = (GeoCodeVO) beanModel.getSelectedValue();
        
            cargo.setGeoCodes(new GeoCodeVO[] {selectedGeoCode});
        }
    }
}
