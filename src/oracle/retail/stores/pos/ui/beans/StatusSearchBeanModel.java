/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/StatusSearchBeanModel.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:41 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *   3    360Commerce 1.2         3/31/2005 4:30:10 PM   Robert Pearse   
 *   2    360Commerce 1.1         3/10/2005 10:25:29 AM  Robert Pearse   
 *   1    360Commerce 1.0         2/11/2005 12:14:24 PM  Robert Pearse   
 *
 *  Revision 1.3  2004/03/16 17:15:18  build
 *  Forcing head revision
 *
 *  Revision 1.2  2004/02/11 20:56:27  rhafernik
 *  @scr 0 Log4J conversion and code cleanup
 *
 *  Revision 1.1.1.1  2004/02/11 01:04:22  cschellenger
 *  updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:12:30   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Mar 14 2003 09:03:02   RSachdeva
 * getStatusIndex/setStatusIndex and getOrderStatusDescriptors methods 
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.0   Apr 29 2002 14:51:46   msg
 * Initial revision.
 * 
 *    Rev 1.1   05 Apr 2002 16:01:30   dfh
 * cleanup
 * Resolution for POS SCR-178: CR/Order, incomplete date range search, dialog text erroneous
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

//java imports
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.order.OrderConstantsIfc;
import oracle.retail.stores.domain.utility.EYSDate;

//----------------------------------------------------------------------------
/** This bean model is used by OrderStatusSearch bean.
*   @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//----------------------------------------------------------------------------
public class StatusSearchBeanModel extends POSBaseBeanModel
{
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    
    /**  Indicates whether to clear the date fields, default true. **/
    protected boolean clearUIFields = true;

    protected boolean emptyDate = true;
    protected EYSDate startDateField = null;
    protected EYSDate endDateField   = null;
    protected String statusField = null;
    /** 
       Status Field Index for ValidatingComboBox Selected Item
    **/
    protected int statusFieldIndex = 0;
    //----------------------------------------------------------------------------
    /**
        Get the value of the StartDate field and append 00:00:00 to it.
        @return the value of StartDate
    **/
    //----------------------------------------------------------------------------
    public EYSDate getStartDate()
    {
        EYSDate startDateTime = DomainGateway.getFactory().getEYSDateInstance();
        if (startDateField != null)
        {
            startDateTime.initialize(startDateField.getYear(),
                                     startDateField.getMonth(),
                                     startDateField.getDay(),
                                     0,
                                     0,
                                     0);
            startDateField = startDateTime;
        }
        return startDateField;
    }
    //----------------------------------------------------------------------------
    /**
        Get the value of the EndDate field with 23:59:59 appended to it.
        @return the value of EndDate
    **/
    //----------------------------------------------------------------------------
    public EYSDate getEndDate()
    {
        EYSDate endDateTime = DomainGateway.getFactory().getEYSDateInstance();
        if (endDateField != null)
        {
            endDateTime.initialize(endDateField.getYear(),
                                   endDateField.getMonth(),
                                   endDateField.getDay(),
                                   23,
                                   59,
                                   59);
            endDateField = endDateTime;
            if (startDateField != null)
            {
                setEmptyDate(false);
            }
        }
        return endDateField;
    }
    //----------------------------------------------------------------------------
    /**
    * Gets the order status property (java.lang.String) value.
    * @return order status property value.
    * @deprecated Deprecated as of 6.0. Replaced by getStatusIndex
    */
    //---------------------------------------------------------------------
    public String getStatus() 
    {
        return statusField;
    }
    //---------------------------------------------------------------------
    /**
    * Gets the order status property int value.
    * @return order status index
    */
    //---------------------------------------------------------------------
    public int getStatusIndex() 
    {
        return statusFieldIndex;
    }
    //---------------------------------------------------------------------
    /**
     * Returns the valid order status codes as a String array. <P>
     * @return java.lang.String[] An array of order status codes.
     */
    //---------------------------------------------------------------------
    public String[] getOrderStatusDescriptors()
    {
        return OrderConstantsIfc.ORDER_STATUS_DESCRIPTORS;
    }
    //----------------------------------------------------------------------------
    /**
        Sets the StartDate field
        @param the value to be set for StartDate
    **/
    //----------------------------------------------------------------------------
    public void setStartDate(EYSDate startDate)
    {
        startDateField = startDate;
    }
    //----------------------------------------------------------------------------
    /**
        Sets the EndDate field
        @param the value to be set for EndDate
    **/
    //----------------------------------------------------------------------------
    public void setEndDate(EYSDate EndDate)
    {
        endDateField = EndDate;
    }
    //----------------------------------------------------------------------------
    /**
    * Sets the status property (java.lang.String) value.
    * @param status The new value for the property.
    * @deprecated Deprecated as of 6.0. Replaced by setStatusIndex
    */
    //---------------------------------------------------------------------
    public void setStatus(String status)
    {
        statusField = status;
    }
    //---------------------------------------------------------------------
    /**
    * Sets the status index property
    * @param statusIndex Status Index 
    */
    //---------------------------------------------------------------------
    public void setStatusIndex(int statusIndex)
    {
        statusFieldIndex = statusIndex;
    }
    //--------------------------------------------------------------------------
    /**
    * Set the flag to represent whether dates were entered.
    * @param emptyDate
    */
    //--------------------------------------------------------------------------
    public void setEmptyDate(boolean emptyDate)
    {
        this.emptyDate = emptyDate;
    }
    //--------------------------------------------------------------------------
    /**
    * Get the flag to represent whether dates were entered.
    * @return boolean
    */
    //--------------------------------------------------------------------------
    public boolean getEmptyDate()
    {
        return emptyDate;
    }
    //--------------------------------------------------------------------------
    /**
        Set clearUIFields flag to determine whether to clear the date fields. <P>
        @param boolean.
    **/
    //---------------------------------------------------------------------
    public void setclearUIFields(boolean value)
    {                                  // begin setclearUIFields()
        clearUIFields = value;
    }                                  // end setclearUIFields()
    //---------------------------------------------------------------------
    /**
        Returns the current valud of clearUIFields.<P>
        @return value of clearUIFields flag.
    **/
    //---------------------------------------------------------------------
    public boolean getclearUIFields()
    {                                  // begin getclearUIFields()
        return(clearUIFields);
    }                                  // end getclearUIFields() 
    //----------------------------------------------------------------------------
    /**
        Converts to a String representing the data in this Object
        @returns String representing the data in this Object
    **/
    //----------------------------------------------------------------------------
    public String toString()
    {
        StringBuffer buff = new StringBuffer();

        buff.append("Class: StatusSearchBeanModel Revision: " + revisionNumber + "\n");
        return(buff.toString());
    }
} ///:~ end class StatusSearchBeanModel
