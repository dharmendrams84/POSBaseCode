/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifyitem/ModifyItemTaxReturnShuttle.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:25 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *  3    360Commerce 1.2         3/31/2005 4:29:04 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:23:34 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:12:40 PM  Robert Pearse   
 * $
 * Revision 1.12  2004/09/23 00:07:12  kmcbride
 * @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 * Revision 1.11  2004/05/11 22:43:47  dcobb
 * @scr 4922 Update service to work with Tax Override multi-item select.
 *
 * Revision 1.10  2004/05/05 22:18:53  dcobb
 * @scr 4389 Tax Override multiitem select non-taxable & taxable items, then turn off tax on these items: tax is not turned off
 *
 * Revision 1.9  2004/03/16 18:30:46  cdb
 * @scr 0 Removed tabs from all java source code.
 *
 * Revision 1.8  2004/03/12 19:22:15  fkane
 * @scr 3977
 * Changed the logging line to make sure it had an item in hte cargo so when
 * multiselect option is chosen from taxmodify it didnt null pointer
 *
 * Revision 1.4  2004/03/05 00:41:52  bjosserand
 * @scr 3954 Tax Override
 * Revision 1.3 2004/02/12 16:51:03 mcs Forcing head revision
 * 
 * Revision 1.2 2004/02/11 21:39:28 rhafernik @scr 0 Log4J conversion and code cleanup
 * 
 * Revision 1.1.1.1 2004/02/11 01:04:18 cschellenger updating to pvcs 360store-current
 * 
 * 
 * 
 * Rev 1.0 Aug 29 2003 16:01:46 CSchellenger Initial revision.
 * 
 * Rev 1.1 24 May 2002 18:54:36 vxs Removed unncessary concatenations from log statements. Resolution for POS SCR-1632:
 * Updates for Gap - Logging
 * 
 * Rev 1.0 Apr 29 2002 15:17:44 msg Initial revision.
 * 
 * Rev 1.0 Mar 18 2002 11:37:32 msg Initial revision.
 * 
 * Rev 1.1 06 Mar 2002 16:29:38 baa Replace get/setAccessEmployee with get/setOperator Resolution for POS SCR-802:
 * Security Access override for Reprint Receipt does not journal to requirements
 * 
 * Rev 1.0 Sep 21 2001 11:29:10 msg Initial revision.
 * 
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifyitem;

import org.apache.log4j.Logger;

import oracle.retail.stores.domain.lineitem.ItemPriceIfc;
import oracle.retail.stores.domain.lineitem.KitComponentLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.lineitem.TaxableLineItemIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.ShuttleIfc;
import oracle.retail.stores.pos.services.modifyitem.tax.ModifyItemTaxCargo;

//------------------------------------------------------------------------------
/**
 * Return shuttle class for ModifyItemTax service.
 * <P>
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
//------------------------------------------------------------------------------
public class ModifyItemTaxReturnShuttle implements ShuttleIfc
{ // begin class ModifyItemTaxReturnShuttle
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -8041275429488022463L;

    /**
     * The logger to which log messages will be sent.
     */
    protected static Logger logger =
        Logger.getLogger(oracle.retail.stores.pos.services.modifyitem.ModifyItemTaxReturnShuttle.class);
    ;

    /**
     * revision number supplied by Team Connection
     */
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
     * vector of items from sale return item
     */
    //protected Vector lineItems;
    /**
     * dirty flag (indicates update needs to be performed)
     */
    protected boolean dirtyFlag = false;
    /**
     * new item tax object
     */
    //protected ItemTaxIfc newTax;
    /**
     * incoming cargo object
     */
    protected ModifyItemTaxCargo taxCargo;

    //---------------------------------------------------------------------
    /**
     * Load from child (ModifyItemTax) cargo class.
     * <P>
     * <B>Pre-Condition</B>
     * <UL>
     * <LI>Cargo in bus is instance of ModifyItemTaxCargo class
     * </UL>
     * <B>Post-Condition</B>
     * <UL>
     * <LI>Cargo loaded
     * </UL>
     * 
     * @param bus  The service bus
     */
    //---------------------------------------------------------------------
    public void load(BusIfc bus)
    { // begin load()
        // retrieve cargo
        taxCargo = (ModifyItemTaxCargo) bus.getCargo();
    } // end load()

    //---------------------------------------------------------------------
    /**
     * Unload to parent (ModifyItem) cargo class.
     * <P>
     * <B>Pre-Condition</B>
     * <UL>
     * <LI>Cargo in bus is instance of ModifyItem class
     * </UL>
     * <B>Post-Condition</B>
     * <UL>
     * <LI>Cargo unloaded
     * </UL>
     * 
     * @param bus  The service bus
     */
    //---------------------------------------------------------------------
    public void unload(BusIfc bus)
    { // begin unload()
        // Text string for journal
        String text = null;
        // log entry
        // retrieve cargo
        ItemCargo cargo = (ItemCargo) bus.getCargo();

        // if dirty flag set, perform updates
        TaxableLineItemIfc[] items = taxCargo.getItems();
        dirtyFlag = taxCargo.getDirtyFlag();

        if (dirtyFlag)
        {
            cargo.updateItemTax(taxCargo.getItemTax());
            
            // Set the tax modification data for Kit Component item
            if (items != null && items.length == 1 && items[0] instanceof KitComponentLineItemIfc)
            {
                ItemPriceIfc price = ((KitComponentLineItemIfc)items[0]).getItemPrice();
                SaleReturnLineItemIfc theItem = cargo.getItem();
                theItem.setItemPrice(price);
                theItem.setTaxable(items[0].getTaxable());
            }
            
            if ((logger.isInfoEnabled() ) && ( cargo.getItem()!= null ))
                logger.info(
                    "ModifyItemTaxReturnShuttle unload:  ItemTaxIfc:" + cargo.getItems()[0].getItemTax().toString() + "");
        }

    } // end unload()

    //---------------------------------------------------------------------
    /**
     * Method to default display string function.
     * <P>
     * 
     * @return String representation of object
     */
    //---------------------------------------------------------------------
    public String toString()
    { // begin toString()
        // result string
        String strResult =
            new String("Class:  ModifyItemTaxReturnShuttle (Revision " + getRevisionNumber() + ")" + hashCode());
        // pass back result
        return (strResult);
    } // end toString()

    //---------------------------------------------------------------------
    /**
     * Retrieves the Team Connection revision number.
     * <P>
     * 
     * @return String representation of revision number
     */
    //---------------------------------------------------------------------
    public String getRevisionNumber()
    { // begin getRevisionNumber()
        // return string
        return (revisionNumber);
    } // end getRevisionNumber()

} // end class ModifyItemTaxReturnShuttle
