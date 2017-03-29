/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/inquiry/iteminquiry/ItemInquiryCargo.java /rgbustores_13.4x_generic_branch/1 2011/06/01 12:05:45 mchellap Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    mchellap  06/01/11 - Fixed item inquiry using manufacturer
 *    ohorne    02/22/11 - ItemNumber can be ItemID or PosItemID
 *    sgu       06/08/10 - fix tab
 *    sgu       06/08/10 - rename mandatoryPrice to externalPrice to be
 *                         consistent
 *    sgu       06/08/10 - add item # & desc to the screen prompt. fix unknow
 *                         item screen to disable price and quantity for
 *                         external item
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    nkgautam  01/22/10 - Added Item IMEI Attribute to the cargo
 *    abondala  01/03/10 - update header date
 *    aariyer   04/01/09 - Checked in files for not performing UOM Check and
 *                         indented code/added comments
 *    mchellap  01/05/09 - Department search field changes
 *
 * ===========================================================================
 * $Log:
 *      5    360Commerce 1.4         1/22/2006 11:45:10 AM  Ron W. Haight
 *           removed references to com.ibm.math.BigDecimal
 *      4    360Commerce 1.3         12/13/2005 4:42:41 PM  Barry A. Pape
 *           Base-lining of 7.1_LA
 *      3    360Commerce 1.2         3/31/2005 4:28:31 PM   Robert Pearse
 *      2    360Commerce 1.1         3/10/2005 10:22:27 AM  Robert Pearse
 *      1    360Commerce 1.0         2/11/2005 12:11:38 PM  Robert Pearse
 *     $
 *     Revision 1.10  2004/09/27 22:32:05  bwf
 *     @scr 7244 Merged 2 versions of abstractfinancialcargo.
 *
 *     Revision 1.9  2004/08/23 16:16:01  cdb
 *    @scr 4204 Removed tab characters
 *
 *     Revision 1.8  2004/05/27 17:12:48  mkp1
 *     @scr 2775 Checking in first revision of new tax engine.
 *
 *     Revision 1.7  2004/05/03 18:30:29  lzhao
 *     @scr 4544, 4556: keep user entered info when back to the page.
 *
 *     Revision 1.6  2004/04/28 22:51:29  lzhao
 *     @scr 4081,4084: roll item info to inventory screen.
 *
 *     Revision 1.5  2004/04/09 16:55:59  cdb
 *     @scr 4302 Removed double semicolon warnings.
 *
 *     Revision 1.4  2004/03/11 14:32:10  baa
 *     @scr 3561 Add itemScanned get/set methods to PLUItemCargoIfc and add support for changing type of quantity based on the uom
 *
 *     Revision 1.3  2004/02/12 16:50:30  mcs
 *     Forcing head revision
 *
 *     Revision 1.2  2004/02/11 21:51:11  rhafernik
 *     @scr 0 Log4J conversion and code cleanup
 *
 *     Revision 1.1.1.1  2004/02/11 01:04:16  cschellenger
 *     updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.2   Nov 13 2003 13:08:22   jriggins
 * assigning SCR
 * Resolution for 3430: Sale Service Refactoring
 *
 *    Rev 1.1   Nov 13 2003 13:06:00   jriggins
 * refactoring the item inquiry service so that plu lookups can be performed without having to go through the entire item inquiry flow
 *
 *    Rev 1.0   Aug 29 2003 16:00:14   CSchellenger
 * Initial revision.
 *
 *    Rev 1.4   Jan 12 2003 16:03:54   pjf
 * Remove deprecated calls to AbstractFinancialCargo.getCodeListMap(), setCodeListMap().
 * Resolution for 1907: Remove deprecated calls to AbstractFinancialCargo.getCodeListMap()
 *
 *    Rev 1.3   Jan 02 2003 15:04:58   crain
 * Deprecated code list map methods
 * Resolution for 1875: Adding a business customer offline crashes the system
 *
 *    Rev 1.2   Oct 14 2002 16:10:06   DCobb
 * Added alterations service to item inquiry service.
 * Resolution for POS SCR-1753: POS 5.5 Alterations Package
 *
 *    Rev 1.1   21 May 2002 16:55:24   baa
 * externalized hard coded strings
 * Resolution for POS SCR-1624: Spanish translation
 *
 *    Rev 1.0   08 May 2002 19:15:18   baa
 * Initial revision.
 * Resolution for POS SCR-1624: Spanish translation
 *
 *    Rev 1.0   08 May 2002 19:10:06   baa
 * Initial revision.
 *
 *    Rev 1.1   10 Apr 2002 17:21:44   baa
 * get department list from reason codes
 * Resolution for POS SCR-1562: Get Department list from Reason Codes, not separate Dept. list.
 *
 *    Rev 1.0   Mar 18 2002 11:33:56   msg
 * Initial revision.
 *
 *    Rev 1.5   Feb 05 2002 16:42:32   mpm
 * Modified to use IBM BigDecimal.
 * Resolution for POS SCR-1121: Employ IBM BigDecimal
 *
 *    Rev 1.4   28 Jan 2002 22:44:10   baa
 * ui fixes
 * Resolution for POS SCR-824: Application crashes on Customer Add screen after selecting Enter
 *
 *    Rev 1.3   Dec 11 2001 20:51:16   dfh
 * added transaction type
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.2   05 Nov 2001 17:37:36   baa
 * Code Review changes. Customer, Customer history Inquiry Options
 * Resolution for POS SCR-244: Code Review  changes
 *
 *    Rev 1.1   25 Oct 2001 17:42:44   baa
 * cross store inventory feature
 * Resolution for POS SCR-230: Cross Store Inventory
 *
 *    Rev 1.0   Sep 21 2001 11:29:54   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:08:10   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.inquiry.iteminquiry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Vector;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;
import oracle.retail.stores.domain.stock.ProductGroupConstantsIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.SearchCriteriaIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.domain.utility.EYSDate;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.tour.application.tourcam.ObjectRestoreException;
import oracle.retail.stores.foundation.tour.application.tourcam.SnapshotIfc;
import oracle.retail.stores.foundation.tour.application.tourcam.TourCamIfc;
import oracle.retail.stores.foundation.tour.application.tourcam.TourCamSnapshot;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;
import oracle.retail.stores.pos.services.common.AbstractItemInquiryCargo;
import oracle.retail.stores.pos.services.common.ItemSerialCargoIfc;
import oracle.retail.stores.pos.services.common.PLUItemCargoIfc;
import oracle.retail.stores.pos.utility.QuarrySnapshot;

import org.apache.log4j.Logger;

/**
 * This cargo contains the persistent data needed in the Item Inquiry service.
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public class ItemInquiryCargo extends AbstractItemInquiryCargo
                              implements ProductGroupConstantsIfc,
                                         PLUItemCargoIfc,
                                         CargoIfc, TourCamIfc,
                                         ItemSerialCargoIfc,
                                         Serializable
{
    private static final long serialVersionUID = -244493714274043261L;

    /**
        revision number.
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
     * The logger to which log messages will be sent.
     */
    private static final Logger logger = Logger.getLogger(ItemInquiryCargo.class);

    /**
     * The selected item.
     */
    protected PLUItemIfc pluItem = null;

    /**
     * The item that we are looking for
     */
    protected SearchCriteriaIfc inquiryItem = null;

    /**
     * The list of matching item(s).
     */
    protected PLUItemIfc[] itemList = null;

    /**
     * The vector to keep track of the fields with invalid data
     */
    protected Vector<Integer> fields = new Vector<Integer>(2);

    /**
     * The serial number of item to be added to current transaction.
     */
    protected String itemSerial = null;

    /**
     * Item IMEI Number
     */
    protected String itemIMEINumber = null;

    /**
     * This flag indicates whether the item should be added to the transaction.
     * True, if the item should be added to the transaction. False otherwise.
     * Default is true.
     */
    protected boolean modifiedFlag = true;

    /**
     * Item Quantity
     */
    protected BigDecimal itemQuantity = new BigDecimal("1.00");

    /**
     * The result of an interaction with the data manager
     */
    protected int dataExceptionErrorCode;

    /**
     * Flag to determine if a list of items is display
     */
    protected boolean showListFlag = false;

    /** The current register */
    protected RegisterIfc register = null;

    /** transaction type of current transaction, if one in progress */
    protected int transType = TransactionIfc.TYPE_UNKNOWN;

    /** transaction */
    protected RetailTransactionIfc transaction = null;

    /**
     * Flag for showing the item inquiry screen. Other services may only want
     * the item lookup feature.
     */
    protected boolean isRequestForItemLookup = false;

    protected boolean skipUOMCheckFlag = false;

    /**
     * item size
     */
    protected String itemSize = null;

    /**
     * This flag indicates whether the item that was entered was scanned or
     * typed.
     */
    protected boolean itemScanned = false;

    /**
     * This flag indicates whether the item can be searched by Manufacturer.
     */
    protected boolean searchItemByManufacturer = false;

    /**
     * This date is used to store the dob of the customer for age restricted
     * items.
     */
    protected EYSDate restrictedDOB = null;

    /**
     * This flag indicates whether or not the item being lookedup is a related
     * item or not. If it is, dont care about nonsaleable.
     */
    protected boolean relatedItem = false;

    /**
     * This flag indicates whether PlanogramID should be displayed.
     */
    protected boolean usePlanogramID = false;

    /**
     * This flag indicates whether to skip price inquiry and directly goto
     * advaced inquiry
     */
    protected boolean skipPriceInquiryFlag = false;

    /**
     * The external price to use for this item. If it is not null, use this one
     * instead of the plu item pirce.
     */
    protected CurrencyIfc externalPrice = null;

    /**
     * Constructs ItemInquiryCargo object.
     */
    public ItemInquiryCargo()
    {
    }

    /**
     * Returns the flag that indicates wether to show item detail or a list of
     * items.
     *
     * @return boolean showListFlag
     */
    public boolean getShowListFlag()
    {
        return showListFlag;
    }

    /**
     * Sets the flag that indicates wether to show item detail or a list of
     * items.
     *
     * @param boolean showListFlag
     */
    public void setShowListFlag(boolean value)
    {
        showListFlag = value;
    }

    /**
     * Returns the item number.
     *
     * @return String item number.
     */
    public SearchCriteriaIfc getInquiry()
    {
        return (inquiryItem);
    }

    /**
     * Sets the search criteria.
     *
     * @param value search certeria.
     */
    public void setInquiry(SearchCriteriaIfc inquiry)
    {
        inquiryItem = inquiry;
    }

    /**
     * Sets inquiry object
     *
     * @param itemNo
     * @param itemDesc
     * @param deptID
     */
    public void setInquiry(Locale searchLocale, String itemNo, String itemDesc, String deptID)
    {
        setInquiry(searchLocale, itemNo, itemDesc, deptID, null);
    }

    /**
     * Sets inquiry object
     *
     * @param itemNo
     * @param itemDesc
     * @param deptID
     * @deprecated as of 13.1. Use {@link #setInquiry(Locale,String,String,String)}
     */
    public void setInquiry(String itemNo, String itemDesc, String deptID)
    {
        setInquiry(LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE), itemNo, itemDesc, deptID, null);
    }

    /**
     * Adds manufacturer name to cargo
     *
     * @param itemNo
     * @param itemDesc
     * @param manufacturer
     * @param deptID
     */
    public void setInquiry(Locale searchLocale, String itemNo, String itemDesc, String deptID, String geoCode)
    {
        setInquiry(searchLocale, itemNo, itemDesc, deptID, geoCode, null, "-1", "-1", "-1", "-1", "-1");
    }

    /**
     * Adds manufacturer name to cargo
     *
     * @param itemNo
     * @param itemDesc
     * @param manufacturer
     * @param deptID
    * @deprecated as of 13.1. Use {@link #setInquiry(Locale,String,String,String,String)}
     */
    public void setInquiry(String itemNo, String itemDesc, String deptID, String geoCode)
    {
        setInquiry(LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE), itemNo, itemDesc, deptID, geoCode, null,
                "-1", "-1", "-1", "-1", "-1");
    }

    /**
     * Sets the inquiry item.
     *
     * @param value the new item number.
     * @deprecated as of 13.1. Use
     *             {@link #setInquiry(Locale,String,String,String,String,String, String,String,String,String,String)}
     */
    public void setInquiry(String itemNo, String itemDesc, String deptID, String geoCode, String manufacturer,
            String itemTypeCode, String uomID, String itemStyleCode, String itemColorCode, String itemSizeCode)
    {
        setInquiry(LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE), itemNo, itemDesc, deptID, geoCode,
                manufacturer, itemTypeCode, uomID, itemStyleCode, itemColorCode, itemSizeCode);
    }

    /**
     * Sets the inquiry item.
     *
     * @param value the new item number.
     */
    public void setInquiry(Locale searchLocale, String itemNo, String itemDesc, String deptID, String geoCode,
            String manufacturer, String itemTypeCode, String uomID, String itemStyleCode, String itemColorCode,
            String itemSizeCode)
    {
        if (inquiryItem == null)
        {
            inquiryItem = DomainGateway.getFactory().getSearchCriteriaInstance();
        }
        //an Item Number can represent an ItemID or PosItemID
        inquiryItem.setItemNumber(itemNo);
        // Set search flag if itemNo or PosItemId is entered
        if (itemNo != null)
        {
            inquiryItem.setSearchItemByItemNumber(true);
        }

        // The locale of the description and manufacturer search text
        inquiryItem.setSearchLocale(searchLocale);
        inquiryItem.setDescription(itemDesc);
        inquiryItem.setManufacturer(manufacturer);
        inquiryItem.setGeoCode(geoCode);
        inquiryItem.setDepartmentID(deptID);
        if (searchItemByDepartment)
            setDept(deptID);
        setShowListFlag(false);
        setModifiedFlag(true);
        inquiryItem.setUsePlanogramID(usePlanogramID);
        inquiryItem.setSearchForItemByManufacturer(searchItemByManufacturer);

        inquiryItem.setSearchItemByType(searchItemByType);
        if (searchItemByType)
            setItemType(itemTypeCode);

        inquiryItem.setSearchItemByUOM(searchItemByUOM);
        if (searchItemByUOM)
            setUom(uomID);

        inquiryItem.setSearchItemByStyle(searchItemByStyle);
        if (searchItemByStyle)
            setStyle(itemStyleCode);

        inquiryItem.setSearchItemByColor(searchItemByColor);
        if (searchItemByColor)
            setColor(itemColorCode);

        inquiryItem.setSearchItemBySize(searchItemBySize);
        if (searchItemBySize)
            setSize(itemSizeCode);

        inquiryItem.setItemTypeCode(itemTypeCode);
        inquiryItem.setItemUOMCode(uomID);
        inquiryItem.setItemStyleCode(itemStyleCode);
        inquiryItem.setItemColorCode(itemColorCode);
        inquiryItem.setItemSizeCode(itemSizeCode);
    }

    public void setMaxMatches(int maxMatches)
    {
        inquiryItem.setMaximumMatches(maxMatches);
    }

    /**
     * Returns list of invalid fields.
     *
     * @return The list of invalid fields.
     */
    public Vector<Integer> getInvalidFields()
    {
        return fields;
    }

    /**
     * Resets invalid fields buffer.
     */
    public void resetInvalidFieldCounter()
    {
        fields = null;
    }

    /**
     * Sets the list of invalid fields
     *
     * @param fieldId the id of the field with invalid data.
     */
    public void setInvalidField(int fieldId)
    {
        // this should be an enumeration
        if (fields == null)
        {
            fields = new Vector<Integer>(2);
        }
        fields.add(fieldId);
    }

    /**
     * Returns the item desc.
     *
     * @return String The item desc.
     */
    public String getItemDesc()
    {
        return (inquiryItem.getDescription());
    }

    /**
     * Sets the item number.
     *
     * @param itemID The new item number.
     */
    public void setItemDesc(String desc)
    {
        inquiryItem.setDescription(desc);
    }

    /**
     * Returns the item.
     *
     * @return PLUItemIfc The item.
     */
    public PLUItemIfc getPLUItem()
    {
        return (pluItem);
    }

    /**
     * Returns the items list.
     *
     * @return PLUItemIfc[] The item list.
     */
    public PLUItemIfc[] getItemList()
    {
        return (itemList);
    }

    /**
     * Sets the item selected.
     *
     * @param itemID The selected item.
     */
    public void setPLUItem(PLUItemIfc item)
    {
        pluItem = item;

    }

    /**
     * Returns the item serial number.
     *
     * @return The item serial number.
     */
    public String getItemSerial()
    {
        return (itemSerial);
    }

    /**
     * Sets the item serial number.
     *
     * @param addedItemSerial The new item serial number.
     */
    public void setItemSerial(String addedItemSerial)
    {
        itemSerial = addedItemSerial;
    }

    /**
     * Sets the itemlist.
     *
     * @param items The new list of items.
     */
    public void setItemList(PLUItemIfc[] items)
    {
        if (items == null)
            return;

        // update list
        itemList = new PLUItemIfc[items.length];
        System.arraycopy(items, 0, itemList, 0, items.length);
        setShowListFlag(true);
    }

    /**
     * Returns true if the item should be added to the current transaction.
     * False otherwise.
     *
     * @return boolean True if the item should be added to the current
     *         transaction. False otherwise.
     */
    public boolean getModifiedFlag()
    {
        return (modifiedFlag);
    }

    /**
     * Sets the flag that determines whether the item should be added to the
     * current transaction.
     *
     * @param value True if the item should be added to the current transaction.
     *            False otherwise.
     */
    public void setModifiedFlag(boolean value)
    {
        modifiedFlag = value;
    }

    /**
     * Gets the item quantity value.
     *
     * @return BigDecimal value
     */
    public BigDecimal getItemQuantity()
    {
        return itemQuantity;
    }

    /**
     * Sets the Item quantity value.
     *
     * @param value the item quantity
     */
    public void setItemQuantity(BigDecimal value)
    {
        itemQuantity = value;
    }

    /**
     * Returns the error code returned with a DataException.
     *
     * @return int the integer value
     */
    public int getDataExceptionErrorCode()
    {
        return dataExceptionErrorCode;
    }

    /**
     * Sets the error code returned with a DataException.
     *
     * @param value the integer value
     */
    public void setDataExceptionErrorCode(int value)
    {
        dataExceptionErrorCode = value;
    }

    /**
     * Returns the register object.
     * <P>
     *
     * @return The register object.
     */
    public RegisterIfc getRegister()
    { // begin getRegister()
        return register;
    } // end getRegister()

    /**
     * Sets the register object.
     * <P>
     *
     * @param value The register object.
     */
    public void setRegister(RegisterIfc value)
    { // begin setRegister()
        register = value;
    } // end setRegister()

    /**
     * Returns the transaction type or TYPE_UNKNOWN, if no transaction in
     * progress.
     * <P>
     *
     * @return The transaction type or TYPE_UNKNOWN.
     */
    public int getTransactionType()
    { // begin getTransactionType()
        return transType;
    } // end getTransactionType()

    /**
     * Sets the transaction type for the current transaction.
     * <P>
     *
     * @param value The transaction type.
     */
    public void setTransactionType(int value)
    { // begin setTransactionType()
        transType = value;
    } // end setTransactionType()

    /**
     * Returns the transaction.
     * <P>
     *
     * @return The transaction.
     */
    public RetailTransactionIfc getTransaction()
    { // begin getTransaction()
        return transaction;
    } // end getTransaction()

    /**
     * Sets the transaction.
     * <P>
     *
     * @param value The transaction.
     */
    public void setTransaction(RetailTransactionIfc value)
    { // begin setTransaction()
        transaction = value;
    } // end setTransaction()

    /**
     * Creates a snapshot of the cargo
     *
     * @return SnapshotIfc A snapshot of the cargo
     */
    public SnapshotIfc makeSnapshot()
    {
        return new TourCamSnapshot(this);
    }

    /**
     * Restores the cargo from a snapshot
     *
     * @param s The snapshot of the cargo to restore from.
     */
    public void restoreSnapshot(SnapshotIfc s) throws ObjectRestoreException
    {
        /*
         * Get a copy of the cargo from the snapshot
         */
        QuarrySnapshot snapshot;
        snapshot = (QuarrySnapshot) s;
        try
        {
            ItemInquiryCargo cargo = (ItemInquiryCargo) snapshot.restoreObject();

            /*
             * Copy elements back
             */
            this.inquiryItem = cargo.getInquiry();
            this.dept = cargo.getDept();
            this.itemList = cargo.getItemList();
            this.deptNames = cargo.getDeptListHash();
            this.pluItem = cargo.getPLUItem();
            this.itemQuantity = cargo.getItemQuantity();
            this.modifiedFlag = cargo.getModifiedFlag();
            this.transType = cargo.getTransactionType();

        }
        catch (ObjectRestoreException e)
        {
            logger.error("Can't restore snapshot. " + e + "");
            throw e;
        }
    }

    /**
     * Returns the isRequestForItemLookup flag
     *
     * @return true if set false otherwise. Set to true if you only need to do a
     *         PLU lookup and not go through the entire item inquiry flow.
     */
    public boolean isRequestForItemLookup()
    {
        return isRequestForItemLookup;
    }

    /**
     * Sets the isRequestForItemLookup flag
     *
     * @param isRequestForItemLookup. Set to true if you only need to do a PLU
     *            lookup and not go through the entire item inquiry flow.
     */
    public void setIsRequestForItemLookup(boolean isRequestForItemLookup)
    {
        this.isRequestForItemLookup = isRequestForItemLookup;
    }

    /**
     * Sets the item size.
     *
     * @param value String
     */
    public void setItemSize(String size)
    {
        itemSize = size;
    }

    /**
     * Gets the item size.
     *
     * @return String
     */
    public String getItemSize()
    {
        return itemSize;
    }

    /**
     * Sets the itemScanned flag.
     *
     * @param value boolean
     */
    public void setItemScanned(boolean value)
    {
        itemScanned = value;
    }

    /**
     * Returns the itemScanned flag.
     *
     * @return boolean
     */
    public boolean isItemScanned()
    {
        return itemScanned;
    }

    /**
     * This method returns the restricted dob.
     *
     * @return Returns the restrictedDOB.
     */
    public EYSDate getRestrictedDOB()
    {
        return restrictedDOB;
    }

    /**
     * This method sets the restricted dob.
     *
     * @param restrictedDOB The restrictedDOB to set.
     */
    public void setRestrictedDOB(EYSDate restrictedDOB)
    {
        this.restrictedDOB = restrictedDOB;
    }

    /**
     * Get whether or not this is a related item lookup.
     *
     * @return Returns the relatedItem.
     */
    public boolean isRelatedItem()
    {
        return relatedItem;
    }

    /**
     * Set whether or not this is a related item lookup.
     *
     * @param relatedItem The relatedItem to set.
     */
    public void setRelatedItem(boolean relatedItem)
    {
        this.relatedItem = relatedItem;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return String representation of object
     */
    public String toString()
    {
        return "Class:  ItemInquiryCargo (Revision " + getRevisionNumber() + ")" + hashCode();

    }

    /**
     * Returns the revision number of the class.
     * <P>
     *
     * @return String representation of revision number
     */
    public String getRevisionNumber()
    { // begin getRevisionNumber()
        // return string
        return (revisionNumber);
    } // end getRevisionNumber()

    /**
     * To confirm if the item can be searched using the Manufacturer search
     * criteria.
     * <P>
     *
     * @return boolean value of the parameter " SearchItemByManufacturer"
     */
    public boolean isSearchItemByManufacturer()
    {
        return searchItemByManufacturer;
    }

    /**
     * To set the boolean value confirming if the item can be searched using the
     * Manufacturer criteria.
     * <P>
     *
     * @param boolean value of the parameter " SearchItemByManufacturer"
     */
    public void setSearchItemByManufacturer(boolean searchItemByManufacturer)
    {
        this.searchItemByManufacturer = searchItemByManufacturer;
    }

    // Configurable Search
    /**
     * Returns the manufacturer.
     *
     * @return String manufacturer.
     */
    public String getManufacturer()
    {
        return (inquiryItem.getManufacturer());
    }

    /**
     * Sets the manufacturer.
     *
     * @param manufacturer manufacturer.
     */
    public void setManufacturer(String manufacturer)
    {
        inquiryItem.setManufacturer(manufacturer);
    }

    /**
     * If planogramID needs to be displayed.
     *
     * @return String usePlanogramID.
     */
    public boolean isUsePlanogramID()
    {
        return usePlanogramID;
    }

    /**
     * Sets the parameter usePlanogramID.
     *
     * @param usePlanogramID usePlanogramID.
     */
    public void setUsePlanogramID(boolean usePlanogramID)
    {
        this.usePlanogramID = usePlanogramID;
    }

    /**
     * Gets the skipPriceInquiryFlag flag.
     *
     * @return boolean The skipPriceInquiryFlag flag.
     */
    public boolean isSkipPriceInquiryFlag()
    {
        return skipPriceInquiryFlag;
    }

    public boolean isSkipUOMCheck()
    {
        return skipUOMCheckFlag;
    }

    /**
     * Sets the skipPriceInquiryFlag flag.
     *
     * @param boolean skipPriceInquiryFlag.
     */
    public void setSkipPriceInquiryFlag(boolean skipPriceInquiryFlag)
    {
        this.skipPriceInquiryFlag = skipPriceInquiryFlag;
    }

    public void skipUOMCheck(boolean skipUOMCheckFlag)
    {
        this.skipUOMCheckFlag = skipUOMCheckFlag;
    }

    /**
     * Gets the IMEI Scanned Number
     *
     * @return String
     */
    public String getItemIMEINumber()
    {
        return itemIMEINumber;
    }

    /**
     * Sets the IMEI Scanned Number
     *
     * @param itemIMEI
     */
    public void setItemIMEINumber(String itemIMEINumber)
    {
        this.itemIMEINumber = itemIMEINumber;
    }

    /**
     * @return the external price to use for this item
     */
    public CurrencyIfc getExternalPrice()
    {
        return externalPrice;
    }

    /**
     * Sets the external price to use for this item
     * @param externalPrice the mandatory item price
     */
    public void setExternalPrice(CurrencyIfc externalPrice)
    {
        this.externalPrice = externalPrice;
    }
}
