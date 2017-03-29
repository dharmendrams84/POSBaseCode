/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/KitComponentRenderer.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:43 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/28/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    ddbaker   11/10/08 - Updated based on new requirements
 *    ddbaker   11/06/08 - Update due to merges.
 *
 * ===========================================================================
 * $Log:
 *    6    360Commerce 1.5         5/8/2007 11:32:27 AM   Anda D. Cadar
 *         currency changes for I18N
 *    5    360Commerce 1.4         4/25/2007 8:51:32 AM   Anda D. Cadar   I18N
 *         merge
 *    4    360Commerce 1.3         1/22/2006 11:45:27 AM  Ron W. Haight
 *         removed references to com.ibm.math.BigDecimal
 *    3    360Commerce 1.2         3/31/2005 4:28:49 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:00 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:13 PM  Robert Pearse   
 *
 *   Revision 1.5  2004/04/09 16:56:00  cdb
 *   @scr 4302 Removed double semicolon warnings.
 *
 *   Revision 1.4  2004/04/08 20:33:02  cdb
 *   @scr 4206 Cleaned up class headers for logs and revisions.
 *
 *   Revision 1.3  2004/03/16 17:15:17  build
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 20:56:27  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:22  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:11:04   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.4   Apr 18 2003 12:46:00   bwf
 * Make sure using correct bundle name and id for tax status.
 * Resolution for 1866: I18n Database  support
 * 
 *    Rev 1.3   Apr 16 2003 16:34:28   RSachdeva
 * Label Internationalization
 * Resolution for POS SCR-2159: Serial # in Component Options not internationalized
 * 
 *    Rev 1.2   Sep 06 2002 17:25:26   baa
 * allow for currency to be display using groupings
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.1   Aug 14 2002 18:17:56   baa
 * format currency 
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.0   Apr 29 2002 14:50:38   msg
 * Initial revision.
 * 
 *    Rev 1.1   27 Mar 2002 17:34:26   dfh
 * removed Dollar Sign from currency format
 * Resolution for POS SCR-365:  appears on several screens, not to specification
 * Resolution for POS SCR-1445: Dollar signs are showing up on the receipt
 * 
 *    Rev 1.0   Mar 18 2002 11:55:54   msg
 * Initial revision.
 * 
 *    Rev 1.5   13 Mar 2002 17:07:56   pdd
 * Modified to use the domain object factory and ifcs.
 * Resolution for POS SCR-1332: Ensure domain objects are created through factory
 * 
 *    Rev 1.4   Feb 05 2002 16:43:54   mpm
 * Modified to use IBM BigDecimal.
 * Resolution for POS SCR-1121: Employ IBM BigDecimal
 * 
 *    Rev 1.3   31 Jan 2002 15:56:44   pjf
 * added getSaleInfoText()
 * Resolution for POS SCR-859: Serialized kit items are not displaying serial #'s on the component screen
 *
 *    Rev 1.2   28 Jan 2002 12:43:18   pjf
 * Updated to call SaleReturnLineItem.getTaxStatusDescriptor() for display of tax information.
 * Resolution for POS SCR-838: Kit Header tax flag displayed incorrectly on sell item screen.
 *
 *    Rev 1.1   Jan 19 2002 10:30:50   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 *
 *    Rev 1.0   30 Oct 2001 11:47:10   pjf
 * Initial revision.
 * Resolution for POS SCR-8: Item Kits
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

// Java imports
import java.awt.Frame;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.lineitem.ItemPriceIfc;
import oracle.retail.stores.domain.lineitem.KitComponentLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.stock.GiftCardPLUItemIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.domain.utility.GiftCardIfc;
import oracle.retail.stores.foundation.utility.LocaleMap;
import oracle.retail.stores.pos.ui.UIUtilities;
import java.math.BigDecimal;
//-------------------------------------------------------------------------
/**
   This is the renderer for Kit Components
   @version $Revision: /rgbustores_13.4x_generic_branch/1 $
*/
//-------------------------------------------------------------------------
public class KitComponentRenderer extends LineItemRenderer
{
    /** revision number supplied by version control    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /** the property for the quantity format.    **/
    public static final String QUANTITY_FORMAT = "0.00;(0.00)";

    public static final int DESCRIPTION = 0;
    public static final int SALE_INFO   = 1;
    public static final int TAX         = 2;

    public static final int STOCK_NUM   = 3;
    public static final int QUANTITY    = 4;
    public static final int PRICE       = 5;
    public static final int DISCOUNT    = 6;
    public static final int EXT_PRICE   = 7;

    public static final int MAX_FIELDS  = 8;
    
    /**
       Tax Mode Tag     
     */
    protected String taxModeTag = "TaxModeChar.";

    //--------------------------------------------------------------------------
    /**
     *  Default constructor.
     */
    public KitComponentRenderer()
    {
        super();
        setName("KitComponentRenderer");
    }

    //--------------------------------------------------------------------------
    /**
     *  Extracts and addition data that is optionally displayed.
     *  @param data Object
     */
    public void setOptionalData(Object value)
    {
        SaleReturnLineItemIfc lineItem = (SaleReturnLineItemIfc)value;
        ItemPriceIfc itemPrice = lineItem.getItemPrice();
        String taxStatus = lineItem.getTaxStatusDescriptor(); 

        labels[TAX].setText(UIUtilities.retrieveCommonText(taxModeTag + taxStatus,taxStatus));
        labels[SALE_INFO].setText(getSaleInfoText(lineItem));
    }
    //---------------------------------------------------------------------
    /**
       Returns the description text that goes in the description field. <P>
       @return String The description text that goes into the description field.
       @param item
    */
    //---------------------------------------------------------------------
    protected String getSaleInfoText(SaleReturnLineItemIfc item)
    {
        //first do the SalesAssociate
        StringBuffer buf = new StringBuffer();
        //If the item is a gift card, then sale info field within the line item should
        //display "Gift Card ID: card #"
        if(item.getPLUItem() instanceof GiftCardPLUItemIfc)
        {
            GiftCardIfc giftCard = ((GiftCardPLUItemIfc)(item.getPLUItem())).getGiftCard();
            if(giftCard != null && giftCard.getCardNumber() != null &&
              !(giftCard.getCardNumber().equals("")))
            {
                buf.append(giftCardLabel + giftCard.getCardNumber());
            }
        }

        //If an item has a serial number associated with it, then the sale info field
        //within the line item should display 'Serialized'
        if(item.getItemSerial() != null)
        {
            buf.append(serialLabel).append(item.getItemSerial());
        }

        // now do the gift registry
        buf.append("  ").append(getGiftRegistryText(item));
        return buf.toString();
    }
    //---------------------------------------------------------------------
    /**
     * creates the prototype cell to speed updates
     * @return SaleReturnLineItem the prototype renderer
     */
    //---------------------------------------------------------------------
    public Object createPrototype()
    {
        KitComponentLineItemIfc cell = DomainGateway.getFactory().getKitComponentLineItemInstance();
        PLUItemIfc plu = DomainGateway.getFactory().getPLUItemInstance();
        plu.getLocalizedDescriptions().initialize(LocaleMap.getSupportedLocales(), "XXXXXXXXXXXXXXX");
        plu.setItemID("12345678901234");
        cell.setPLUItem(plu);
        CurrencyIfc testPrice = DomainGateway.getBaseCurrencyInstance("888888.88");
        ItemPriceIfc price = DomainGateway.getFactory().getItemPriceInstance();
        price.setSellingPrice(testPrice);
        price.setItemQuantity(new BigDecimal("888.88"));
        price.setItemDiscountTotal(testPrice);
        price.setExtendedSellingPrice(testPrice);
        cell.setItemPrice(price);
        EmployeeIfc emp = DomainGateway.getFactory().getEmployeeInstance();
        cell.modifyItemSalesAssociate(emp);

       return((Object)cell);
    }

    //---------------------------------------------------------------------
    /**
       Sets the format for printing out currency and quantities.
    */
    //---------------------------------------------------------------------
    protected void setPropertyFields()
    {
        // Get the format string spec from the UI model properties.
      /*  if (props != null)
        {
            currencyFormat =
              props.getProperty(CURRENCYIFC_DISPLAYFORMAT, CURRENCY_FORMAT);
        }
*/
    //    currencyFormat = DomainGateway.getBaseCurrencyType().getFormattingMask();
        //Labels that need to be displayed in User Interface Locale
        salesAssocLabel =
          UIUtilities.retrieveCommonText(RENDERER_SALE_ASSOC_LABEL);

        giftRegLabel =
          UIUtilities.retrieveCommonText(RENDERER_GIFT_REGISTRY_LABEL);

        serialLabel =
          UIUtilities.retrieveCommonText(RENDERER_SERIAL_LABEL);

        giftCardLabel =
          UIUtilities.retrieveCommonText(RENDERER_GIFT_CARD_LABEL);
    }
    
    //---------------------------------------------------------------------
    /**
       Retrieves the Team Connection revision number. <P>
       @return String representation of revision number
    */
    //---------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return revisionNumber;
    }

    //---------------------------------------------------------------------
    /**
       main entrypoint - starts the part when it is run as an application
       @param args String[]
     */
    //---------------------------------------------------------------------
    public static void main(String[] args)
    {
        Frame frame = new Frame();
        KitComponentRenderer aKitComponentRenderer;
        aKitComponentRenderer = new KitComponentRenderer();
        frame.add("Center", aKitComponentRenderer);
        frame.setSize(aKitComponentRenderer.getSize());
        frame.setVisible(true);
    }
}
