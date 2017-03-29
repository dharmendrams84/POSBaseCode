/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/ValidatingComboBoxModel.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:56 mszekely Exp $
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
 *    ranojha   11/24/08 - Fixed for POS crash due to NPE.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;

import oracle.retail.stores.foundation.utility.Util;

//---------------------------------------------------------------------
/**
 *  This class adds the ComboBoxModel interface to the DefaultListModel
    @version $KW=@(#); $Ver=pos_4.5.0:2; $EKW;
 */
//---------------------------------------------------------------------
public class ValidatingComboBoxModel extends DefaultListModel implements ComboBoxModel
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -4131674710215897519L;

    /** revision number **/
    public static final String revisionNumber = "$KW=@(#); $Ver=pos_4.5.0:2; $EKW;";

    /** The selected item */
    protected String selectedItem = null;
    
    /** Data */
    protected Object[] listData = null;

    //---------------------------------------------------------------------
    /**
     * ValidatingComboBoxModel constructor.  It creates the model elements.
     * @param listVector Vector containing list elements
     */
    //---------------------------------------------------------------------
    public ValidatingComboBoxModel(Vector listVector) 
    {
        super();
        listData = listVector.toArray();
        addVector2Model(listVector);
    }

    //---------------------------------------------------------------------
    /**
     * ValidatingComboBoxModel constructor.  It creates the model elements.
     * @param listArray Array containing list elements
     */
    //---------------------------------------------------------------------
    public ValidatingComboBoxModel(Object[] listArray) 
    {
        super();
        listData = listArray;
        addArray2Model(listArray);
    }
    //---------------------------------------------------------------------
    /**
     * ValidatingComboBoxModel constructor. 
     */
    //---------------------------------------------------------------------
    public ValidatingComboBoxModel() 
    {
        super();
    }

    //---------------------------------------------------------------------
    /**
        Converts a Vector contents into a DefaultListModel
        <p>
       @param listVector Vector containing list elements
     **/
    //---------------------------------------------------------------------
    public void addVector2Model(Vector listVector)
    {
        if(listVector != null)
        {
            for(int i=0; i<listVector.size(); i++)
            {
                addElement(listVector.elementAt(i));
            }
        }
    }

      //---------------------------------------------------------------------
    /**
        Converts a array contents into a DefaultListModel
        <p>
       @param listArray Array containing list elements
    **/
    //---------------------------------------------------------------------
    public void addArray2Model(Object[] listArray)
    {
    	if (listArray!=null)
    	{
	        int n=listArray.length;
	        clear();
	        for(int i=0;i<n;i++)
	        {
	            add(i,listArray[i]);
	        }
    	}
    }
    	
    //---------------------------------------------------------------------
    /**
     * Sets the selected item.
     * @param item java.lang.Object
     */
    //---------------------------------------------------------------------
    public void setSelectedItem(Object item)
    {
        if (contains(item))
        {
            if (!item.equals(selectedItem))
            {
                selectedItem = (String) item;
                fireContentsChanged(this, -1, -1);
            }
        }
    }
    //---------------------------------------------------------------------
    /**
     * Returns the selected item.
     * @return java.lang.Object
     */
    //---------------------------------------------------------------------
    public Object getSelectedItem() 
    {
        return selectedItem;
    }
    //---------------------------------------------------------------------
    /**
        Returns default display string. <P>
        @return String representation of object
    **/
    //---------------------------------------------------------------------
    public String toString()
    {
        String strResult = new String("Class: ValidatingComboBoxModel (Revision " +
                                      getRevisionNumber() + ") @" +
                                      hashCode());
        return(strResult);
    }

    //---------------------------------------------------------------------
    /**
        Returns the revision number. <P>
        @return String representation of revision number
    **/
    //---------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return(Util.parseRevisionNumber(revisionNumber));
    }

    /**
     * @return the listData
     */
    public Object[] getListData()
    {
        return listData;
    }

    /**
     * @param listData the listData to set
     */
    public void setListData(Object[] listData)
    {
        this.listData = listData;
    }
    
    
}
