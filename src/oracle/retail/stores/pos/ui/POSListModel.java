/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/POSListModel.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:36 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   10/22/10 - added generics templating
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *   3    360Commerce 1.2         3/31/2005 4:29:23 PM   Robert Pearse   
 *   2    360Commerce 1.1         3/10/2005 10:24:13 AM  Robert Pearse   
 *   1    360Commerce 1.0         2/11/2005 12:13:08 PM  Robert Pearse   
 *
 *  Revision 1.4  2004/09/23 00:07:10  kmcbride
 *  @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *  Revision 1.3  2004/02/12 16:52:11  mcs
 *  Forcing head revision
 *
 *  Revision 1.2  2004/02/11 21:52:28  rhafernik
 *  @scr 0 Log4J conversion and code cleanup
 *
 *  Revision 1.1.1.1  2004/02/11 01:04:21  cschellenger
 *  updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   Dec 17 2003 11:21:36   baa
 * return enhancements
 * Resolution for 3561: Feature Enhacement: Return Search by Tender
 * 
 *    Rev 1.0   Aug 29 2003 16:09:26   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Mar 20 2003 18:18:54   baa
 * customer screens refactoring
 * Resolution for POS SCR-2098: Refactoring of Customer Service Screens
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;

/**
 * This is a utility class that extends {@link DefaultListModel}. The added
 * functionality lets you construct the object with a {@link Vector}, an array
 * or an {@link List}. It also lets you convert the object to a Vector. It also
 * wraps the list selection for passing back and forth to the bean model.
 */
public class POSListModel extends DefaultListModel implements ComboBoxModel
{
    // This id is used to tell the compiler not to generate a new serialVersionUID.
    static final long serialVersionUID = -6546934533838419717L;

    /** the selected Value */
    protected Object selectedValue;

    /** the index of the selected value */
    protected int selectedIndex;

    /**
     * Default constructor.
     */
    public POSListModel()
    {
    }

    /**
     * Construct a POSListModel (and a DefaultListModel by proxy) with an object
     * array.
     * 
     * @param values and array used to populate the POSListModel
     */
    public POSListModel(Object[] values)
    {
        if (values != null)
        {
            ensureCapacity(values.length);
            for (int i = 0; i < values.length; i++)
            {
                addElement(values[i]);
            }
        }
    }

    /**
     * Construct a POSListModel (and a DefaultListModel by proxy) with an object
     * array.
     * 
     * @param values and array used to populate the POSListModel
     */
    public POSListModel(List<? extends Object> values)
    {
        if (values != null)
        {
            ensureCapacity(values.size());
            for (int i = 0; i < values.size(); i++)
            {
                addElement(values.get(i));
            }
        }
    }

    /**
     * This method returns the selected value on the list model
     * 
     * @return Object the selected value
     */
    public Object getSelectedValue()
    {
        return selectedValue;
    }

    /**
     * This method returns the selected value on the list model This method was
     * added to implement the ComboBoxModel interface.
     * 
     * @return Object the selected value
     */
    public Object getSelectedItem()
    {
        return getSelectedValue();
    }

    /**
     * This method sets the selected value on the list model
     * 
     * @param propValue the selected value
     */
    public void setSelectedValue(Object propValue)
    {
        selectedValue = propValue;
    }

    /**
     * This method sets the selected value on the list model. This method was
     * added to implement the ComboBoxModel interface.
     * 
     * @param propValue the selected value
     */
    public void setSelectedItem(Object propValue)
    {
        setSelectedValue(propValue);
    }

    /**
     * This method returns the selected index on the list model
     * 
     * @return int the selected index
     */
    public int getSelectedIndex()
    {
        return selectedIndex;
    }

    /**
     * This method sets the selected index on the list model
     * 
     * @param index the selected index
     */
    public void setSelectedIndex(int index)
    {
        selectedIndex = index;
    }

    /**
     * This method converts the POSListModel to a {@link Vector}.
     * This is not the underlying vector that backs the {@link DefaultListModel}.
     * 
     * @return a Vector or the items contained in the POSListModel
     */
    @SuppressWarnings("unchecked")
    public Vector toVector()
    {
        Vector v = new Vector(getSize());
        for (Enumeration<?> e = elements(); e.hasMoreElements();)
        {
            v.add(e.nextElement());
        }
        return v;
    }
}
