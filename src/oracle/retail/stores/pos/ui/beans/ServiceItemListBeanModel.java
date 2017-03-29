/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/ServiceItemListBeanModel.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:41 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    acadar    06/10/10 - use default locale for currency display
 *    acadar    06/09/10 - XbranchMerge acadar_tech30 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    acadar    04/01/10 - use default locale for currency display
 *    abondala  01/03/10 - update header date
 *    ddbaker   11/20/08 - Updates for clipping problems
 *
 * ===========================================================================
 * $Log:
 *   5    360Commerce 1.4         7/20/2007 12:53:45 PM  Anda D. Cadar
 *        formatting the price
 *   4    360Commerce 1.3         5/21/2007 9:16:22 AM   Anda D. Cadar   EJ
 *        changes
 *   3    360Commerce 1.2         3/31/2005 4:29:56 PM   Robert Pearse
 *   2    360Commerce 1.1         3/10/2005 10:25:12 AM  Robert Pearse
 *   1    360Commerce 1.0         2/11/2005 12:14:10 PM  Robert Pearse
 *
 *  Revision 1.3  2004/03/16 17:15:18  build
 *  Forcing head revision
 *
 *  Revision 1.2  2004/02/11 20:56:26  rhafernik
 *  @scr 0 Log4J conversion and code cleanup
 *
 *  Revision 1.1.1.1  2004/02/11 01:04:22  cschellenger
 *  updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:12:10   CSchellenger
 * Initial revision.
 *
 *    Rev 1.0   Apr 29 2002 14:48:30   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:57:38   msg
 * Initial revision.
 *
 *    Rev 1.3   22 Feb 2002 14:02:16   baa
 * remove duplicate items from servicelist
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 *
 *    Rev 1.2   18 Feb 2002 17:28:46   baa
 * fix duplicate items list
 * Resolution for POS SCR-1292: Tab not functioning on Non Merchandise item drop down box
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

// java imports
import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.domain.stock.PLUItemIfc;

//--------------------------------------------------------------------------
/**
    This is the bean model used by the ServiceItemListBean. <P>

    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
    @see oracle.retail.stores.pos.ui.beans.ServiceItemListBean
**/
//--------------------------------------------------------------------------
public class ServiceItemListBeanModel extends POSBaseBeanModel
{
    /** revision number **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
        constant for list view.
        this constant defines the length of the pull-down list
    **/
    protected static final String SPACES = "                                              "; // 46 spaces

    /**
        the list of service item values, business defined
    **/
    protected String[] nonMerchandiseList = null;

    /**
        the selected item
    **/
    protected int selectedIndex = -1;

    /**
        the list of service items
    **/
    protected PLUItemIfc[] serviceItemList = null;

    //----------------------------------------------------------------------
    /**
        Class Constructor.
    **/
    //----------------------------------------------------------------------
    public ServiceItemListBeanModel()
    {
    }

    //----------------------------------------------------------------------
    /**
        Sets the Non-Merchandise item drop down list
        @param the Non-Merchandise item list container
    **/
    //----------------------------------------------------------------------
    public void setNonMerchandiseList(String[] value)
    {
        nonMerchandiseList = value;
    }

    //----------------------------------------------------------------------
    /**
        Returns the values for the Non-Merchandise item drop down list
        @return the Non-Merchandise item list container
    **/
    //----------------------------------------------------------------------
    public String[] getNonMerchandiseList()
    {
        return nonMerchandiseList;
    }

    //----------------------------------------------------------------------
    /**
        Sets the Service Item list
        @param the service item list
    **/
    //----------------------------------------------------------------------
    public void setServiceItemList(PLUItemIfc[] value)
    {
        serviceItemList = value;
        nonMerchandiseList = new String[value.length];
        /*
         * Build service item list to be displayed
         */
        for (int i = 0; i < value.length; ++i)
        {
            PLUItemIfc serviceItem = value[i];
            StringBuffer sb = new StringBuffer(SPACES);
            sb.insert(0, serviceItem.getDescription(LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE)));
            if (!serviceItem.getItemClassification().isPriceEntryRequired())
            {
                //use default locale for currency display
                String price = serviceItem.getPrice().toFormattedString();
                int offset = SPACES.length() - price.length();

                sb.insert(offset, price);
            }
            sb.setLength(SPACES.length());
            nonMerchandiseList[i] = sb.toString().trim();
       }
    }

    //----------------------------------------------------------------------
    /**
        Returns the values for the Service Item drop down list
        @return the Service Item list container
    **/
    //----------------------------------------------------------------------
    public PLUItemIfc[] getServiceItemList()
    {
        return serviceItemList;
    }

    //----------------------------------------------------------------------
    /**
        Returns the Selected Item Index.
        <p>
        @return the Selected Item Index.
    **/
    //----------------------------------------------------------------------
    public int getSelectedIndex()
    {
        return(selectedIndex);
    }

    //----------------------------------------------------------------------
    /**
        Sets the Selected Item Index.
        <p>
        @param value the value to be set for selectedIndex
    **/
    //----------------------------------------------------------------------
    public void setSelectedIndex(int value)
    {
        selectedIndex = value;
    }

    //----------------------------------------------------------------------
    /**
        Returns the Selected Item field.
        <p>
        @return the value of selectedItem
    **/
    //----------------------------------------------------------------------
    public PLUItemIfc getSelectedItem()
    {
        PLUItemIfc pluItem = null;
        if (getSelectedIndex() >= 0)
        {
            pluItem = getServiceItemList()[getSelectedIndex()];
        }
        return pluItem;
    }
}
