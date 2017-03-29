/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/ListBeanModel.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:52 mszekely Exp $
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
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

// java imports
import java.util.Vector;

import oracle.retail.stores.pos.ui.POSListModel;

//------------------------------------------------------------------------------
/**
 *  Standard model for list beans.
 */
//------------------------------------------------------------------------------
public class ListBeanModel extends POSBaseBeanModel
{
    /** the list model */
    protected POSListModel listModel;
    
    /** the selected item in the list */
    protected int selectedRow;
    
    /** array of selected rows for multiple selections */
    protected int[] selectedRows;
    
    /** the selected value from the list */
    protected Object selectedValue;
    //--------------------------------------------------------------------------
    /**
     *  Default constructor.
     */
    public ListBeanModel()
    {
        listModel = new POSListModel();
        selectedRow = -1;
        selectedRows = new int[0];
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Gets the data in the list model as a POSListModel.
     *  @return the list model
     */
    public POSListModel getListModel()
    {
        return listModel;
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Gets the data in the list model as a vector of objects.
     *  @return a vector of objects
     */
    public Vector getListVector()
    {
        return listModel.toVector();
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Gets the data in the list model as an array of objects.
     *  @return an object array
     */
    public Object[] getListArray()
    {
        return listModel.toArray();
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Gets the index of the selected row in the list.
     *  @return the selected row index
     */
    public int getSelectedRow()
    {
        return selectedRow;
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Gets an array of the selected indices if the list is in multiple
     *  selection mode.
     *  @return an array of the selected row indices
     */
    public int[] getSelectedRows()
    {
        return selectedRows;
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Gets the value in the model that corresponds to the selected row.
     *  @return the selected value, or null if nothing is selected
     */
    public Object getSelectedValue()
    {
        return selectedValue;
    }
      
    //--------------------------------------------------------------------------
    /**
     *  Sets the list model using a POSListModel as the data source.
     *  @param newList a POSListModel
     */
    public void setListModel(POSListModel newList)
    {
        listModel = newList;
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Sets the list model using an object array as the data source.
     *  @param newList an object array
     */
    public void setListModel(Object[] newList)
    {
        listModel = new POSListModel(newList);
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Sets the list model using a vector as the data source.
     *  @param newList a vector
     */
    public void setListModel(Vector newList)
    {
        listModel = new POSListModel(newList);
    }
    
    public void setSelectedRow(int row)
    {
        selectedRow = row;
    }
    
    public void setSelectedRows(int[] rows)
    {
        selectedRows = rows;
    }
    
    public void setSelectedValue(Object aValue)
    {
        selectedValue = aValue;
    }
}
