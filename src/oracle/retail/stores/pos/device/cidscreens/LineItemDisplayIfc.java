/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/device/cidscreens/LineItemDisplayIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:39 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:28:51 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:06 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:19 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/07/28 23:29:56  rzurga
 *   @scr 6545 Taxable status indicator is not displayed in Sale items screen on CPOI
 *   Added taxable status indicator to Sale items screen on CPOI
 *
 *   Revision 1.1  2004/03/25 20:25:15  jdeleau
 *   @scr 4090 Deleted items appearing on Ingenico, I18N, perf improvements.
 *   See the scr for more info.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.device.cidscreens;

import java.util.Locale;

/**
 * Any EYSDomainIfc object that wants to display things on
 * a CPOI ItemScreen as a line item must implement
 * this interface. Two classes in POS implement this interface:
 * <BR>
 * <UL><LI>oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc</LI>
 * <LI>oracle.retail.stores.domain.stock.PLUItemIfc</LI><UL>
 * $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface LineItemDisplayIfc
{
    /**
     * This returns the String for the price of an item to
     * be displayed in a CPOI type of device.
     *  
     *  @param locale Locale the device is operating in
     *  @return String representation of price
     */
    public String getCPOIDisplayPrice(Locale locale);
    
    /**
     * This returns the items description, for display
     * in a CPOI type of device
     *  
     *  @return description for CPOI device
     */
    public String getCPOIDisplayDescription();

    /**
     * This returns the items taxable indicator, for display
     * in a CPOI type of device
     *  
     *  @return description for CPOI device
     */
    public String getCPOIDisplayTaxableIndicator();

}
