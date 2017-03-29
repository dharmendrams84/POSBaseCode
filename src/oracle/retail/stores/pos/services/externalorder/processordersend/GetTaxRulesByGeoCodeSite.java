/* ===========================================================================
* Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/externalorder/processordersend/GetTaxRulesByGeoCodeSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:59 mszekely Exp $
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
 *    acadar    05/17/10 - temporarily rename the package
 *    acadar    05/17/10 - incorporated feedback from code review
 *    acadar    05/14/10 - initial version for external order processing
 *    acadar    05/14/10 - initial version
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.externalorder.processordersend;


import java.util.List;

import oracle.retail.stores.domain.arts.ReadNewTaxRuleTransaction;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.tax.GeoCodeVO;
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
public class GetTaxRulesByGeoCodeSite extends PosSiteActionAdapter
{
    /**
     *  Serial version UID
     */
    private static final long serialVersionUID = -2656390234103714708L;

    /**
    * The system searches for a Geo code associated with the zip code in the shipping address.
    */
    public void arrive(BusIfc bus)
    {
        Letter letter = new Letter(CommonLetterIfc.NEXT);
        ProcessOrderSendCargo cargo = (ProcessOrderSendCargo) bus.getCargo();

        GeoCodeVO geoCodeVO = cargo.getGeoCodes()[0];

        try
        {
            ReadNewTaxRuleTransaction tx = new ReadNewTaxRuleTransaction();
            List <SaleReturnLineItemIfc> lineItems = cargo.getSaleReturnSendLineItems();

            SendTaxUtil util = new SendTaxUtil();
            // Get all tax rules for the tax Groups I care about at this geoCode.
            TaxRulesVO taxRulesVO = tx.getTaxRulesByGeoCode(geoCodeVO.getGeoCode(), util.getUniqueTaxGroups(lineItems));

            // Set the tax rules on the line items..
            util.setTaxRulesForLineItems(taxRulesVO, lineItems);
        }
        catch(DataException de)
        {
            // Database errors... use default rule
            logger.error("Error retrieving tax rules "+de);
        }


        bus.mail(letter, BusIfc.CURRENT);
    }



}
