/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/send/displaysendmethod/SendMethodSelectedRoad.java /rgbustores_13.4x_generic_branch/2 2011/05/19 10:07:33 rsnayak Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    rsnayak   05/12/11 - APf changes for send
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   02/18/09 - do not process selected shipping method if it is
 *                         null
 *    deghosh   02/10/09 - EJ i18n defect fixes
 *
 * ===========================================================================
 * $Log:
 * 14   360Commerce 1.13        5/21/2007 9:16:22 AM   Anda D. Cadar   EJ
 *      changes
 * 13   360Commerce 1.12        5/8/2007 5:22:00 PM    Alan N. Sinton  CR 26486
 *       - Refactor of some EJournal code.
 * 12   360Commerce 1.11        5/1/2007 12:15:40 PM   Brett J. Larsen CR 26474
 *       - Tax Engine Enhancements for Shipping Carge Tax (for VAT feature)
 *
 * 11   360Commerce 1.10        4/25/2007 8:51:34 AM   Anda D. Cadar   I18N
 *      merge
 * 10   360Commerce 1.9         3/29/2007 7:21:14 PM   Michael Boyd    CR 26172
 *       - v8x merge to trunk
 *
 *      11   .v8x      1.8.1.1     3/3/2007 2:32:20 PM    Maisa De Camargo
 *      Replaced
 *      "Sub-Total" to "Subtotal" to match receipt
 *      10   .v8x      1.8.1.0     3/3/2007 1:59:11 PM    Maisa De Camargo
 *      Replaced
 *      "Sub-Total" to "Subtotal" to match receipt
 * 9    360Commerce 1.8         8/9/2006 9:00:36 PM    Robert Zurga    Merge
 *      4159 Country Name appearing incorrectly, defect fixed.
 * 8    360Commerce 1.7         3/16/2006 5:54:30 AM   Akhilashwar K. Gupta
 *      CR-3995: Updated "getCustomerInfo()" method as per Code review
 *      comment.
 * 7    360Commerce 1.6         3/2/2006 4:08:44 AM    Akhilashwar K. Gupta
 *      CR-3995: Updated to remove duplicate setting of Customer Name
 * 6    360Commerce 1.5         2/24/2006 2:10:40 PM   Brett J. Larsen CR 10575
 *       - incorrect tax amount in e-journal for tax exempt transactions
 *
 *      replaced faulty code w/ new helper method in JournalUtilities
 *
 * 5    360Commerce 1.4         1/25/2006 4:11:46 PM   Brett J. Larsen merge
 *      7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
 * 4    360Commerce 1.3         1/22/2006 11:45:21 AM  Ron W. Haight   removed
 *      references to com.ibm.math.BigDecimal
 * 3    360Commerce 1.2         3/31/2005 4:29:55 PM   Robert Pearse
 * 2    360Commerce 1.1         3/10/2005 10:25:11 AM  Robert Pearse
 * 1    360Commerce 1.0         2/11/2005 12:14:09 PM  Robert Pearse
 *:
 * 4    .v700     1.2.1.0     12/23/2005 17:17:52    Rohit Sachdeva  8203: Null
 *      Pointer Fix for Business Customer Info
 * 3    360Commerce1.2         3/31/2005 15:29:55     Robert Pearse
 * 2    360Commerce1.1         3/10/2005 10:25:11     Robert Pearse
 * 1    360Commerce1.0         2/11/2005 12:14:09     Robert Pearse
 *
 *Revision 1.16.2.1  2004/10/18 18:20:54  jdeleau
 *@scr 7381 Correct printing of tax in the e-journal for when printItemTax
 *is turned off.
 *
 *
 *Revision 1.16  2004/09/30 20:21:51  jdeleau
 *@scr 7263 Make printItemTax apply to e-journal as well as receipts.
 *
 *Revision 1.15  2004/09/03 14:30:44  rsachdeva
 *@scr  6791 Transaction Level Send
 *
 *Revision 1.14  2004/09/01 15:34:38  rsachdeva
 *@scr 6791 Transaction Level Send
 *
 *Revision 1.13  2004/08/27 14:30:08  rsachdeva
 *@scr 6791 Item Level Send  to Transaction Level Send Update Flow
 *
 *Revision 1.12  2004/08/10 16:58:21  rsachdeva
 *@scr 6791 Transaction Level Send Journal
 *
 *Revision 1.11  2004/06/21 13:16:07  lzhao
 *@scr 4670: cleanup
 *
 *Revision 1.10  2004/06/19 14:06:14  lzhao
 *@scr 4670: integrate with capture customer
 *
 *Revision 1.9  2004/06/17 14:26:14  rsachdeva
 *@scr 4670 Send: Multiple Sends Journal Customer
 *
 *Revision 1.8  2004/06/11 19:10:34  lzhao
 *@scr 4670: add customer present feature
 *
 *Revision 1.7  2004/06/04 20:23:44  lzhao
 *@scr 4670: add Change send functionality.
 *
 *Revision 1.6  2004/06/03 13:29:21  lzhao
 *@scr 4670: delete send item.
 *
 *Revision 1.5  2004/06/02 19:06:51  lzhao
 *@scr 4670: add ability to delete send items, modify shipping and display shipping method.
 *
 *Revision 1.4  2004/05/28 20:10:07  lzhao
 *@scr 4670: shippingMethod is deprecated.
 *
 *Revision 1.3  2004/05/27 14:37:06  rsachdeva
 *@scr 4670 Send: Multiple Sends
 *
 *Revision 1.2  2004/05/26 19:28:52  lzhao
 *@scr 4670: clean up send.
 *
 *Revision 1.1  2004/05/26 16:37:47  lzhao
 *@scr 4670: add capture customer and bill addr. same as shipping for send
 *
 *Revision 1.5  2004/05/13 19:47:24  rsachdeva
 *@scr 4670 Send: Journal Send Information for Each Send
 *
 *Revision 1.4  2004/05/05 16:13:53  rsachdeva
 *@scr 4670 Send: Multiple Sends
 *
 *Revision 1.3  2004/02/12 16:51:55  mcs
 *Forcing head revision
 *
 *Revision 1.2  2004/02/11 21:52:29  rhafernik
 *@scr 0 Log4J conversion and code cleanup
 *
 *Revision 1.1.1.1  2004/02/11 01:04:20  cschellenger
 *updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:06:46   CSchellenger
 * Initial revision.
 *
 *    Rev 1.2   13 Nov 2002 17:31:14   sfl
 * Adjust the tax amount decimal point digit length before
 * sending it for display.
 * Resolution for POS SCR-1749: POS 5.5 Tax Package
 *
 *    Rev 1.1   04 Nov 2002 15:21:34   sfl
 * Make sure the tax is re-calculated after manual
 * tax rate override during send.
 * Resolution for POS SCR-1749: POS 5.5 Tax Package
 *
 *    Rev 1.0   Apr 29 2002 15:04:10   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:47:10   msg
 * Initial revision.
 *
 *    Rev 1.13   13 Feb 2002 09:22:44   sfl
 * Added shipping method information right after
 * the shipping charge in the E-Journal.
 * Resolution for POS SCR-1264: Send - ejournal entry for send item not complete
 *
 *    Rev 1.12   Feb 05 2002 16:43:28   mpm
 * Modified to use IBM BigDecimal.
 * Resolution for POS SCR-1121: Employ IBM BigDecimal
 *
 *    Rev 1.11   28 Jan 2002 11:25:34   sfl
 * Make an error message in E-journal more concise.
 * Resolution for POS SCR-879: Send - item weight is not returned with the item, an error should be written to the ejournal
 *
 *    Rev 1.10   25 Jan 2002 19:04:54   sfl
 * Fix for SCR879. Added error message logging in
 * E-Journal when the item weigh is zero during
 * the shipping charge calculation basted on weight.
 * Resolution for POS SCR-879: Send - item weight is not returned with the item, an error should be written to the ejournal
 *
 *    Rev 1.9   21 Jan 2002 09:42:20   sfl
 * Removed the extra "r" from the special instruction
 * header printing in the E-Journal.
 * Resolution for POS SCR-787: Send - ejournal entry correction
 *
 *    Rev 1.8   14 Jan 2002 19:45:22   sfl
 * E-Journal fixing for send items.
 * Resolution for POS SCR-287: Send Transaction
 *
 *    Rev 1.7   14 Jan 2002 12:59:22   baa
 * updates from code review
 * Resolution for POS SCR-520: Prepare Send code for review
 *
 *    Rev 1.6   11 Jan 2002 12:12:20   sfl
 * Took away one extra space before the second address line
 * display in E-journal.
 * Resolution for POS SCR-287: Send Transaction
 *
 *    Rev 1.5   09 Jan 2002 10:26:02   baa
 * fix totals
 * Resolution for POS SCR-520: Prepare Send code for review
 *
 *    Rev 1.4   03 Jan 2002 14:23:24   baa
 * cleanup code
 * Resolution for POS SCR-520: Prepare Send code for review
 *
 *    Rev 1.3   20 Dec 2001 18:31:12   baa
 * fix sale type when balance is + after returns
 * Resolution for POS SCR-287: Send Transaction
 *
 *    Rev 1.2   12 Dec 2001 17:25:42   baa
 * updates for  journaling send feature
 * Resolution for POS SCR-287: Send Transaction
 *
 *    Rev 1.1   07 Dec 2001 18:56:44   sfl
 * Added the shipping charge to transaction totals so that
 * the tender, receipt, and e-journal will have the new grandtotal
 * = calculatedShippingCharge + normal grandtotal.
 * Resolution for POS SCR-287: Send Transaction
 *
 *    Rev 1.0   06 Dec 2001 18:52:32   baa
 * Initial revision.
 * Resolution for POS SCR-287: Send Transaction
 *
 *    Rev 1.0   04 Dec 2001 17:23:02   baa
 * Initial revision.
 * Resolution for POS SCR-287: Send Transaction
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.send.displaysendmethod;

// java imports
import java.math.BigDecimal;

import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.utility.JournalConstantsIfc;

import org.apache.log4j.Logger;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.pos.journal.JournalFormatterManagerIfc;
import oracle.retail.stores.pos.journal.TransactionJournalFormatterIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.financial.ShippingMethodConstantsIfc;
import oracle.retail.stores.domain.financial.ShippingMethodIfc;
import oracle.retail.stores.domain.lineitem.KitComponentLineItemIfc;
import oracle.retail.stores.domain.lineitem.KitHeaderLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionTotalsIfc;
import oracle.retail.stores.domain.utility.AddressIfc;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.services.send.address.SendCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.ShippingMethodBeanModel;

/**
 * Retrieves send method selected, adds to transaction totals, journals current
 * send information
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public class SendMethodSelectedRoad extends PosLaneActionAdapter
{
    private static final long serialVersionUID = 6546630921720179222L;

    /**
     * revision number for this class
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    /**
     * The logger to which log messages will be sent.
     */
    private static final Logger logger = Logger.getLogger(SendMethodSelectedRoad.class);

    /**
     * zip code extension separator text
     */
    protected static final String ZIP_EXT_SEPARATOR_TEXT = "-";

    /**
     * special instruction label text
     */
    public static final String SPECIAL_INSTRUCTION_LABEL = "Sp. Inst.:";

    /**
     * shipping charge text
     */
    public static final String SHIPPING_CHARGE_LABEL = "Shipping Charge";

    /**
     * non taxable indicator tag
     */
    public static final String NON_TAXABLE_INDICATOR = "N";

    /**
     * shipping to label count text
     */
    public static final String SHIP_TO_LABEL = "Ship-To ";

    /**
     * zero item weight value found
     */
    public static final String ERROR_ZERO_ITEM_WEIGHT_VALUE_FOUND = " Error: Zero item weight value found";

    /**
     * send label
     */
    public static final String SEND_LABEL = " Send";

    /**
     * item label
     */
    public static final String ITEM_LABEL = "ITEM: ";

    /**
     * Yes label
     */
    public static final String YES = "Yes";

    /**
     * No label
     */
    public static final String NO = "No";

    /**
     * total shipping charge
     */
    public static final String TOTAL_SHIPPING_CHARGE = "Total Shipping Charge ";

    /**
     * Retrieves selected shipping method and calculate totals.
     * 
     * @param bus the bus arriving at this site
     */
    @Override
    public void traverse(BusIfc bus)
    {
       SendCargo cargo = (SendCargo) bus.getCargo();
       POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);

       // Initialize bean model values
       ShippingMethodBeanModel model = (ShippingMethodBeanModel) ui.getModel();
       // if this is null, nothing should happen
       ShippingMethodIfc selectedMethodOfShipping = model.getSelectedShipMethod();
       if (selectedMethodOfShipping != null)
       {
          applyShippingMethod(bus, cargo, selectedMethodOfShipping);
       }
    }

    /**
     * Take the specified shipping method and apply it to the transaction in the
     * cargo.
     * 
     * @param bus
     * @param cargo
     * @param selectedMethodOfShipping
     */
    protected void applyShippingMethod(BusIfc bus, SendCargo cargo, ShippingMethodIfc selectedMethodOfShipping )
    {
       SaleReturnTransactionIfc transaction = cargo.getTransaction();

       TransactionTotalsIfc totals = transaction.getTransactionTotals();

       if ( cargo.isItemUpdate() )
       {
           transaction.updateSendPackageInfo(cargo.getSendIndex()-1,
                   selectedMethodOfShipping,
                   cargo.getShipToInfo());
       }
       else
       {
           //Add send packages info
           transaction.addSendPackageInfo(selectedMethodOfShipping,
           							cargo.getShipToInfo());
           //Assign Send label count on Sale Return Line Items
           SaleReturnLineItemIfc[] items = cargo.getLineItems();
           for (int i = 0; i < items.length; i++)
           {
               items[i].setItemSendFlag(true);
               items[i].setSendLabelCount(totals.getItemSendPackagesCount());

               // set send flag for all kit components as well
               if (items[i] instanceof KitHeaderLineItemIfc)
               {
            	   KitHeaderLineItemIfc kitHeader = (KitHeaderLineItemIfc)items[i];
            	   KitComponentLineItemIfc[] kitComponents = kitHeader.getKitComponentLineItemArray();
            	   for (int j = 0; j < kitComponents.length; j++)
                   {
            		   kitComponents[j].setItemSendFlag(true);
            		   kitComponents[j].setSendLabelCount(totals.getItemSendPackagesCount());
                   }
               }
           }
       }

       transaction.updateTransactionTotals();	// Must do this to force tax recalculation
       totals.setBalanceDue(totals.getGrandTotal());

       if ( cargo.getOperator() == null )
           journalCurrentSend(bus, transaction, transaction.getSalesAssociateID());
       else
           journalCurrentSend(bus, transaction, cargo.getOperator().getLoginID());
    }

    /**
     * Journal current send information
     * 
     * @param bus service bus
     * @param transaction sale return transaction reference
     * @param loginID login id
     */
    public void journalCurrentSend(BusIfc bus, SaleReturnTransactionIfc transaction, String loginID)
    {
       // print journal
       JournalManagerIfc journal = (JournalManagerIfc)Gateway.getDispatcher().getManager(JournalManagerIfc.TYPE);

       if (journal != null)
       {
    	   journal.journal(loginID, transaction.getTransactionID(), journalShippingInfo(bus, transaction));
           if (logger.isInfoEnabled()) logger.info( "Journal Send msg");
       }
       else
       {
           logger.error( "No JournalManager found");
       }
   }

   /**
     * Prints ship to info for the current send
     * 
     * @param bus service bus
     * @param transaction sale return transaction reference
     * @return String shipping to info
     */
    protected String journalShippingInfo(BusIfc bus,
                                         SaleReturnTransactionIfc transaction)
    {
        JournalFormatterManagerIfc formatterManager =
            (JournalFormatterManagerIfc)Gateway.getDispatcher().getManager(JournalFormatterManagerIfc.TYPE);

        SendCargo cargo = (SendCargo) bus.getCargo();
        ParameterManagerIfc parameterManager = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
        SaleReturnLineItemIfc[] items = cargo.getLineItems();
        // Retrieve shipping charge parameter
        StringBuffer journalBuffer =
            new StringBuffer(formatterManager.journalShippingInfo(transaction, items, parameterManager));

        //journal sub totals for transaction level send
        if (transaction.getTransactionTotals().isTransactionLevelSendAssigned())
        {
            journalBuffer.append(journalSubTotalsForTransactionLevelSend(transaction,
                                                                         bus));
        }
        return journalBuffer.toString();
    }

    /**
     * Journals shipping items for current send
     * 
     * @param currentSendCount current send count
     * @param bus service bus
     * @return String shipping items labeled
     * @deprecated as of 12.0. Use {@link JournalFormatterManagerIfc#journalShippingInfo(SaleReturnTransactionIfc, SaleReturnLineItemIfc[], ParameterManagerIfc)}
     */
    protected String journalShippingItems(int currentSendCount,
                                          BusIfc bus)
    {
        StringBuffer journalBuffer = new StringBuffer("");
        SendCargo cargo = (SendCargo) bus.getCargo();
        SaleReturnLineItemIfc[] items = cargo.getLineItems();

        // Retrieve shipping charge parameter
        String shippingCalculation = getShippingCalculationType(bus);

        for (int i = 0; i < items.length; i++)
        {

            Object dataObject[]={items[i].getItemID().trim()};


            String itemInfo = I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ITEM, dataObject);


            Object dataSendCountObject[] = {Integer.toString(currentSendCount)};


            String sendInfo = I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.SEND_COUNT, dataSendCountObject);

            journalBuffer.append(itemInfo)
                         .append(Util.EOL)
                         .append(sendInfo)
                         .append(Util.EOL);
            if (shippingCalculation.equals(ShippingMethodConstantsIfc.WEIGHT) &&
                (items[i].getPLUItem().getItemWeight().doubleValue() <= 0.00))
            {

                journalBuffer.append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ZERO_ITEM_WEIGHT, null))
                .append(Util.EOL);
            }
         }
        return journalBuffer.toString();
    }

    /**
     * Returns as String, the shipping calculation method from the parameter
     * file.
     * 
     * @param bus service
     * @return String calculation type
     */
    private String getShippingCalculationType(BusIfc bus)
    {
        // get paramenter manager
        ParameterManagerIfc pm  = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
        String  type = new String(ShippingMethodConstantsIfc.WEIGHT);
        try
        {
            type = pm.getStringValue(ShippingMethodConstantsIfc.SHIPPING_CALCULATION);
            type.trim();
            if (logger.isInfoEnabled()) logger.info("Parameter read: " + ShippingMethodConstantsIfc.SHIPPING_CALCULATION + " = [" + type + "]");
        }
        catch (ParameterException e)
        {
            logger.error( "" + Util.throwableToString(e) + "");
        }
        return(type);
    }

    /**
     * Formats a postal code based on the data provided in the AddressIfc
     * parameter
     * 
     * @param addr AddressIfc object that provides the data needed to format the
     *            postal code.
     * @return String that represents the formatted zip code.
     * @deprecated as of 12.0. This is moved to {@link TransactionJournalFormatterIfc}.
     */
    protected String formatZipCode(AddressIfc addr)
    {
        // Get the zip code extension if available
        String zipExtension = addr.getPostalCodeExtension();
        String zipExtSeparator = "";
        if (zipExtension != null && zipExtension.trim().length() > 0)
        {
            zipExtSeparator = ZIP_EXT_SEPARATOR_TEXT;
        }
        // Put it all together
        String cityStateZip =
          addr.getCity() + ", "+  addr.getState()
            +" "+ addr.getPostalCode() +" "+zipExtSeparator +" "+ zipExtension + Util.EOL + "  "+ addr.getCountry();

        return cityStateZip;

    }

    /**
     * Journal shipping charge
     * 
     * @param shippingChargeString shipping charge string
     * @return String the shipping charge journal entry
     * @deprecated as of 12.0, use {@link JournalFormatterManagerIfc#journalShippingInfo(SaleReturnTransactionIfc, SaleReturnLineItemIfc[], ParameterManagerIfc)} instead.
     */
    public String journalShippingCharge(String shippingChargeString)
    {
        StringBuffer journalBuffer = new StringBuffer("");
        // Journal the shipping charge
        if (shippingChargeString != null)
        {

            Object dataObject[]={shippingChargeString};


        	String shippingCharge = I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.SHIPPING_CHARGE, dataObject);

        	journalBuffer.append(Util.EOL)
        	.append(shippingCharge);

        }

        return (journalBuffer.toString());
    }

    /**
     * Journal sub totals for transaction level send. Journalling for Subtotals
     * is being done now since now we have the total shipping charges for
     * transaction level send
     * 
     * @param transaction sale return transaction reference
     * @param bus service bus reference
     * @return String journal string
     */
    public String journalSubTotalsForTransactionLevelSend(SaleReturnTransactionIfc transaction,
                                                         BusIfc bus)
    {
        JournalFormatterManagerIfc formatter =
            (JournalFormatterManagerIfc)bus.getManager(JournalFormatterManagerIfc.TYPE);
        ParameterManagerIfc parameterManager =
            (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
        return formatter.journalSubTotalsForTransactionLevelSend(transaction, parameterManager);
   }

    /**
     * Builds a line with the left and right strings separated by spaces.
     * 
     * @param left StringBuffer
     * @param right StringBuffer
     * @return StrinBuffer formatted line
     */
    protected StringBuffer blockLine(StringBuffer left, StringBuffer right)
    {
        int lineLength = 35;
        StringBuffer s = new StringBuffer(lineLength);
        s.append(left);

        // pad with spaces
        for (int i = left.length(); i < lineLength - right.length(); i++)
        {
            s.append(" ");
        }

        s.append(right);

        return s;
    }

    /**
     * Round the 5 decimal digit currency value to 2 decimal digit precision.
     *
     * @param input
     *            currency value to be rounded
     * @return CurrencyIfc Rounded currency value
     */
    protected CurrencyIfc roundCurrency(CurrencyIfc input)
    {
        // Adjust the precision Need to do rounding in two steps, starting from
        // the 3rd decimal digit first, then round again at the 2nd decimal
        // digit.
        BigDecimal bd = new BigDecimal(input.getStringValue());
        BigDecimal bOne = new BigDecimal(1);
        bd = bd.divide(bOne, 3, BigDecimal.ROUND_HALF_UP);
        CurrencyIfc roundedCurrency = DomainGateway.getBaseCurrencyInstance(bd);

        BigDecimal bd2 = new BigDecimal(roundedCurrency.getStringValue());
        bd2 = bd2.divide(bOne, TransactionTotalsIfc.UI_PRINT_TAX_DISPLAY_SCALE, BigDecimal.ROUND_HALF_UP);

        roundedCurrency = DomainGateway.getBaseCurrencyInstance(bd2);
        return (roundedCurrency);
    }
}
