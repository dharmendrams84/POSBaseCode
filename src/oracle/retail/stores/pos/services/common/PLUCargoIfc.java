/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/PLUCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:53 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    sgu       06/08/10 - add item # & desc to the screen prompt. fix unknow
 *                         item screen to disable price and quantity for
 *                         external item
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    6    360Commerce 1.5         3/25/2008 6:21:36 AM   Vikram Gopinath CR
 *         #30683, porting changes from v12x. Save the correct pos department
 *         id for an unknown item.
 *    5    360Commerce 1.4         3/10/2008 3:51:48 PM   Sandy Gu
 *         Specify store id for non receipted return item query.
 *    4    360Commerce 1.3         1/22/2006 11:45:01 AM  Ron W. Haight
 *         removed references to com.ibm.math.BigDecimal
 *    3    360Commerce 1.2         3/31/2005 4:29:21 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:24:06 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:13:04 PM  Robert Pearse
 *
 *   Revision 1.6  2004/07/29 19:10:14  rsachdeva
 *   @scr 5442 Item Not Found Cancel for Returns
 *
 *   Revision 1.5  2004/06/07 19:59:00  mkp1
 *   @scr 2775 Put correct header on files
 *
 *   Revision 1.4  2004/03/11 14:32:10  baa
 *   @scr 3561 Add itemScanned get/set methods to PLUItemCargoIfc and add support for changing type of quantity based on the uom
 *
 *   Revision 1.3  2004/02/12 16:48:02  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:19:59  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.1   08 Nov 2003 01:00:22   baa
 * cleanup -sale refactoring
 *
 *    Rev 1.0   Nov 04 2003 19:00:10   cdb
 * Initial revision.
 * Resolution for 3430: Sale Service Refactoring
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;
// java imports
import java.math.BigDecimal;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;

//-------------------------------------------------------------------------
/**
    This interface defines methods used when a cargo works with a PLU.
    <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $

**/
//-------------------------------------------------------------------------
public interface PLUCargoIfc extends DBErrorCargoIfc, ItemSerialCargoIfc
{
    //----------------------------------------------------------------------
    /**
        Returns the user's login ID.
        <P>
        @return The user's login ID.
    **/
    //----------------------------------------------------------------------
    public String getPLUItemID();

    //----------------------------------------------------------------------
    /**
        Sets the user's login ID.
        <P>
        @param  employeeID   The user's login ID.
    **/
    //----------------------------------------------------------------------
    public void setPLUItemID(String PLU);

    //----------------------------------------------------------------------
    /**
        Returns the logged-in employee
        <P>
        @return The logged-in employee
    **/
    //----------------------------------------------------------------------
    public PLUItemIfc getPLUItem();

    //----------------------------------------------------------------------
    /**
        Sets the logged-in employee.
        <P>
        @param  employee   The logged-in employee.
    **/
    //----------------------------------------------------------------------
    public void setPLUItem(PLUItemIfc pluItem);

    //----------------------------------------------------------------------
    /**
        Returns the item quantity.
        <P>
        @return the BigDecimal value
    **/
    //----------------------------------------------------------------------
    public BigDecimal getItemQuantity();

    //----------------------------------------------------------------------
    /**
        Sets the item quantity.
        <P>
        @param the BigDecimal value
    **/
    //----------------------------------------------------------------------
    public void setItemQuantity(BigDecimal value);

    //----------------------------------------------------------------------
    /**
        Returns the department name.
        <P>
        @return The department name.
    **/
    //----------------------------------------------------------------------
    public String getDepartmentName();

    //----------------------------------------------------------------------
    /**
        Sets the department name.
        <P>
        @param  dept   The department name.
    **/
    //----------------------------------------------------------------------
    public void setDepartmentName(String dept);

    //----------------------------------------------------------------------
    /**
        Returns the department id.
        <P>
        @return The department id.
    **/
    //----------------------------------------------------------------------
    public String getDepartmentID();

    //----------------------------------------------------------------------
    /**
        Sets the department id.
        <P>
        @param  deptID   The department id.
    **/
    //----------------------------------------------------------------------
    public void setDepartmentID(String deptID);

    //----------------------------------------------------------------------
    /**
        Some classes have some clean up to do.
        <P>
    **/
    //----------------------------------------------------------------------
    public void completeItemNotFound();

    /**
     * Get the GeoCode used for taxes
     * @return geoCode
     */
    public String getGeoCode();

    /**
     * Set the geoCode used for taxes
     * @param value the geoCode
     */
    public void setGeoCode(String value);

    /**
     * Get the store ID
     * @return store ID
     */
    public String getStoreID();

    /**
     * Set the store ID
     * @param value the store ID
     */
    public void setStoreID(String value);

    /**
     * Checks if Enable Cancel for ItemNotFound
     * @return Returns the enableCancel.
     */
    public boolean isEnableCancelItemNotFoundFromReturns() ;
    /**
     * Enable Cancel for ItemNotFound when coming From Returns
     * @param enableCancel The enableCancel to set.
     */
    public void setEnableCancelItemNotFoundFromReturns(boolean enableCancel) ;

    //----------------------------------------------------------------------
    /**
     * Returns a flag indicating if the plu is from an external order
     * @return the boolean flag
     */
    //----------------------------------------------------------------------
    public boolean isExternalOrder();

    //----------------------------------------------------------------------
    /**
        Returns the item price.
        <P>
        @return the CurrencyIfc value
    **/
    //----------------------------------------------------------------------
    public CurrencyIfc getItemPrice();

    //----------------------------------------------------------------------
    /**
     * Return the item description
     * @return the String value
     */
    //----------------------------------------------------------------------
    public String getItemDescription();

}
