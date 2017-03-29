/* ===========================================================================
* Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/externalorder/processordersend/GetTaxRulesByPostalCodeSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:58 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    sgu       06/23/10 - do not set transaction level send tax rules
 *    sgu       06/23/10 - no need to set transaction level send tax since
 *                         shipping from exteranl order is treated as line item
 *                         level shipping
 *    sgu       06/22/10 - added the logic to process multiple send package
 *                         instead of just on per order
 *    sgu       06/10/10 - donot fail the flow if the destination zipcode has
 *                         no tax rule associated
 *    cgreene   05/26/10 - convert to oracle packaging
 *    acadar    05/17/10 - incorporated feedback from code review
 *    acadar    05/14/10 - initial version for external order processing
 *    acadar    05/14/10 - initial version
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.externalorder.processordersend;


import java.util.List;

import oracle.retail.stores.domain.arts.ReadNewTaxRuleTransaction;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.tax.SendTaxUtil;
import oracle.retail.stores.domain.tax.TaxRulesVO;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;

/**
 *
 * Retrieves the tax rules when there is shipping
 * done from the warehouse
 */
public class GetTaxRulesByPostalCodeSite extends PosSiteActionAdapter
{
    /**
     *  Serial version UID
     */
    private static final long serialVersionUID = -2448850741863674494L;

    /**
    * The system searches for a Geo code associated with the zip code in the shipping address.
    */
    public void arrive(BusIfc bus)
    {
        Letter letter = new Letter(CommonLetterIfc.NEXT);
        ProcessOrderSendCargo cargo = (ProcessOrderSendCargo) bus.getCargo();

        if(cargo.getShippingMethod() == null)
        {
            letter = new Letter(CommonLetterIfc.DONE);
        }
        else
        {
            ReadNewTaxRuleTransaction tx = new ReadNewTaxRuleTransaction();
            List <SaleReturnLineItemIfc> lineItems = cargo.getSaleReturnSendLineItems();

            SendTaxUtil util = new SendTaxUtil();

            // Get the postal code..
            String postalCode = cargo.getShippingPostalCode();
            try
            {
                // Try to get tax rules off of postal code... Get all the tax rules,
                // because if this is for a transaction level send we dont know what tax group ids
                // are going to be entered in the future.
                TaxRulesVO taxRulesVO = tx.getTaxRulesByPostalCode(postalCode, null);

                // If this was successful, set the tax rules..
                if(taxRulesVO.hasTaxRules())
                {
                    util.setTaxRulesForLineItems(taxRulesVO, lineItems);
                }
                // If it was not successful, then the user must select a GeoCode, tell the tour
                // to show the screen that makes the user select...
                else if(taxRulesVO.getGeoCodes().length > 1)
                {
                    // Set the letter so that the  DisplayMultipleGeoCodesSite is shown.
                    letter = new Letter("MultipleGeoCodes");
                    cargo.setGeoCodes(taxRulesVO.getGeoCodes());

                }
                else if (taxRulesVO.getGeoCodes().length == 0)
                {
                    logger.error("Could not retrieve tax rules, DB is most likely configured badly.");
                }
            }
            catch(DataException de)
            {
                // Database problems...
                logger.error("Error getting tax rules "+ de);
            }

        }

        bus.mail(letter, BusIfc.CURRENT);
    }


}
