/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/send/displaysendmethod/GetTaxRulesSite.java /rgbustores_13.4x_generic_branch/2 2011/10/05 20:16:18 mkutiana Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    mkutiana  10/05/11 - Missed out from earlier refactoring - using the
 *                         persistenceContext based DataTransactions
 *    sgu       06/22/10 - use type saft vector
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    6    360Commerce 1.5         4/4/2008 10:22:02 AM   Sharma Yanamandra
 *         removed setDefaultTaxRulesForLineItems
 *    5    360Commerce 1.4         4/3/2008 8:53:33 PM    Sharma Yanamandra
 *         removed the problematic method setLocalTaxRulesForLineItems
 *    4    360Commerce 1.3         1/22/2006 11:45:21 AM  Ron W. Haight
 *         removed references to com.ibm.math.BigDecimal
 *    3    360Commerce 1.2         3/31/2005 4:28:15 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:21:51 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:11:12 PM  Robert Pearse
 *
 *   Revision 1.10  2004/09/13 21:58:29  jdeleau
 *   @scr 6791 Transaction Level Send in regard to taxes
 *
 *   Revision 1.9  2004/08/10 16:50:30  rsachdeva
 *   @scr 6791 Send Level In Progress
 *
 *   Revision 1.8  2004/07/31 16:32:14  jdeleau
 *   @scr 6632 Make sure send tax always overrides other tax ruels
 *
 *   Revision 1.7  2004/07/16 19:14:24  jdeleau
 *   @scr 6310 All items in the transaction were having the send tax rules applied to them
 *   instead of just the selected ones.
 *
 *   Revision 1.6  2004/06/25 21:00:18  jdeleau
 *   @scr 5849 Tax for send item was not propogating through the various
 *   cargos correctly.  Now it is.
 *
 *   Revision 1.5  2004/06/18 18:51:59  jdeleau
 *   @scr 2775 Further updates to the way tax is calculated, correcting table tax calculation errors.
 *
 *   Revision 1.4  2004/06/10 20:25:38  jdeleau
 *   @scr 2775 Handle kits in sent items
 *
 *   Revision 1.3  2004/06/10 18:05:18  jdeleau
 *   @scr 2775 Use Default Tax Rule if it can't be retrieved from DB
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

import java.util.Arrays;
import java.util.Vector;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.arts.AlertDataTransaction;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.ReadNewTaxRuleTransaction;
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.tax.GeoCodeVO;
import oracle.retail.stores.domain.tax.NewTaxRuleIfc;
import oracle.retail.stores.domain.tax.SendTaxUtil;
import oracle.retail.stores.domain.tax.TaxRulesVO;
import oracle.retail.stores.domain.utility.AddressIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.send.address.SendCargo;
/**
 *
 * $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public class GetTaxRulesSite extends PosSiteActionAdapter
{
    /**
       done letter
    **/
    public static final String DONE = "Done";

    //----------------------------------------------------------------------
    /**
     * Arrival at the site.  Try to get the tax rules site.  If its not possible
     * because there are multiple geo codes, then call the displayMultipleGeoCodesSIte.
     *
     * @param bus
     * @see oracle.retail.stores.foundation.tour.ifc.SiteActionIfc#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        SendCargo cargo = (SendCargo) bus.getCargo();
        Letter letter = new Letter(CommonLetterIfc.NEXT);
        if (cargo.isTransactionLevelSendInProgress())
        {
             letter = new Letter(DONE);
        }
        ReadNewTaxRuleTransaction tx = (ReadNewTaxRuleTransaction) DataTransactionFactory.create(DataTransactionKeys.READ_NEW_TAX_RULE_TRANSACTION);
        // Vector lineItems = cargo.getRetailTransactionIfc().getLineItemsVector();
        Vector<SaleReturnLineItemIfc> lineItems = new Vector<SaleReturnLineItemIfc>(Arrays.asList(cargo.getLineItems()));
        CustomerIfc shipToCustomer = cargo.getShipToInfo();
        SendTaxUtil util = new SendTaxUtil();


        // If the GeoCode is not known...
        if(cargo.getGeoCodes().length < 1)
        {
            // Get the postal code..
            String postalCode = ((AddressIfc) shipToCustomer.getAddresses().elementAt(0)).getPostalCode();
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
                    cargo.getTransaction().setSendTaxRules(taxRulesVO);
                    setRulesForCargo(cargo, taxRulesVO);

                }
                // If it was not successful, then the user must select a GeoCode, tell the tour
                // to show the screen that makes the user select...
                else if(taxRulesVO.getGeoCodes().length > 0)
                {
                    // Set the letter so that the  DisplayMultipleGeoCodesSite is shown.
                    letter = new Letter("MultipleGeoCodes");
                    cargo.setGeoCodes(taxRulesVO.getGeoCodes());
                }
                else
                {
                    logger.error("Could not retrieve tax rules, DB is most likely configured badly.");
                }
            }
            catch(DataException de)
            {
                // Database problems...
                logger.error("Error getting tax rules "+de);
            }

        }
        //The geoCode is known
        else if(cargo.getGeoCodes().length == 1)
        {
            GeoCodeVO geoCodeVO = cargo.getGeoCodes()[0];

            try
            {
                // Get all tax rules for the tax Groups I care about at this geoCode.
                TaxRulesVO taxRulesVO = tx.getTaxRulesByGeoCode(geoCodeVO.getGeoCode(), util.getUniqueTaxGroups(lineItems));

                // Set the tax rules on the line items..
                util.setTaxRulesForLineItems(taxRulesVO, lineItems);
                cargo.getTransaction().setSendTaxRules(taxRulesVO);
                setRulesForCargo(cargo, taxRulesVO);
            }
            catch(DataException de)
            {
                // Database errors... use default rule
                logger.error("Error retrieving tax rules "+de);
            }
        }
        else
        {
            //This should never happen
            letter = new Letter("MultipleGeoCodes");
            logger.warn("GetTaxRulesSite received multiple GeoCodes");
        }
        bus.mail(letter, BusIfc.CURRENT);
    }

    /**
     * Set the tax rules in the cargo object
     *
     * @param cargo
     * @param taxRulesVO
     */
    private void setRulesForCargo(SendCargo cargo, TaxRulesVO taxRulesVO)
    {
        SaleReturnLineItemIfc[] items = cargo.getLineItems();
        for(int i=0; i<items.length; i++)
        {
            NewTaxRuleIfc[] otherTaxRules = taxRulesVO.getTaxRules(items[i].getTaxGroupID());
            items[i].getItemPrice().getItemTax().setSendTaxRules(otherTaxRules);
        }
        cargo.setLineItems(items);
    }
}
