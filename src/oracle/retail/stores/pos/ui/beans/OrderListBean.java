/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/OrderListBean.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:58 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/28/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/28/10 - updating deprecated names
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:29:14 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:23:52 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:53 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/03/16 17:15:18  build
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 20:56:26  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:22  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   Sep 16 2003 17:52:48   dcobb
 * Migrate to JVM 1.4.1
 * Resolution for 3361: New Feature:  JVM 1.4.1_03 (Windows) Migration
 * 
 *    Rev 1.0   Aug 29 2003 16:11:24   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Aug 14 2002 18:18:08   baa
 * format currency 
 * Resolution for POS SCR-1740: Code base Conversions
 * 
 *    Rev 1.0   Apr 29 2002 14:54:50   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:56:36   msg
 * Initial revision.
 * 
 *    Rev 1.6   13 Mar 2002 17:08:06   pdd
 * Modified to use the domain object factory and ifcs.
 * Resolution for POS SCR-1332: Ensure domain objects are created through factory
 * 
 *    Rev 1.5   16 Feb 2002 18:15:46   baa
 * more ui fixes
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 *
 *    Rev 1.4   Jan 19 2002 10:31:14   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 *
 *    Rev 1.3   11 Jan 2002 16:58:12   jbp
 * added column for order channel.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.2   27 Oct 2001 13:00:18   jbp
 * Remove white space from under list
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *    Rev 1.1   Dec 21 2001 09:29:52   dfh
 * enlarged order # column, shortened date column
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 *
 *
 *    Rev 1.0   Sep 24 2001 11:19:24   MPM
 *
 * Initial revision.
 *
 *
 *    Rev 1.1   Sep 17 2001 13:16:56   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.order.OrderConstantsIfc;
import oracle.retail.stores.domain.order.OrderSummaryEntryIfc;
import oracle.retail.stores.domain.utility.EYSDate;
import oracle.retail.stores.foundation.manager.gui.UIModelIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.ui.UIUtilities;

//----------------------------------------------------------------------------
/**
   The OrderListBean presents the functionality of the OrderList screen.
   @version $KW=@(#); $Ver=010905:3; $EKW;
   @deprecated as of release 5.0.0
**/
//----------------------------------------------------------------------------
public class OrderListBean extends POSJTableHandlerBean
{
    /**
        revision number supplied by Team Connection
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
        constant for class name
    **/
    public static final String CLASSNAME = "OrderListBean";

    /**
        constant for number of table columns
    **/
    public static final int MAX_COLUMNS = 6;

    /**
        constant for default number of table rows
    **/
    public static final int DEFAULT_ROWS = 16;

    // values required to ensure JTable fits in POS screen
    public static final int column0Width = 110;
    public static final int column1Width = 105;
    public static final int column2Width = 90;
    public static final int column3Width = 80;
    public static final int column4Width = 50;
    public static final int column5Width = 90;

    private OrderListBeanModel  beanModel       = null;
    private JTable              table           = null;
    private OrderListTableModel tableModel      = null;
    private TableColumn         column          = null;
    private JScrollPane         orderListPane   = null;
    private OrderSummaryEntryIfc[] orderList    = null;
    private boolean             dirtyModel      = false;

    //---------------------------------------------------------------------
    /**
       Constructor
    */
    //---------------------------------------------------------------------
    public OrderListBean()
    {
        initialize();
    }


    //---------------------------------------------------------------------
    /**
     * Initialize the class.
     */
    //---------------------------------------------------------------------
    private void initialize()
    {
        setName("OrderListBean");
        uiFactory.configureUIComponent(this, UI_PREFIX);

        setLayout(new BorderLayout());

        // add the new table into a JScrollPane
        // 1) setup the table model with the maximum number of columns
        tableModel = new OrderListTableModel(DEFAULT_ROWS, MAX_COLUMNS);

        // 2) create the actual JTable
        table = new JTable(tableModel);
        table.setShowGrid(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // this will remove any gaps between cells
        table.setIntercellSpacing(new Dimension(0,0));

        orderListPane = new JScrollPane(table);

        add(orderListPane, "Center");
    }

    //---------------------------------------------------------------------
    /**
        Activate this screen.
     */
    //---------------------------------------------------------------------

    public void activate()
    {
        disableKeys( new int[]{KeyEvent.VK_ENTER} );
        super.activate();
        OrderSummaryEntryIfc[] entries = null;
        if (beanModel != null)
        {
            entries = beanModel.getOrderList();
        }
        // update the model first
        updateBean();

        // important to disable auto column creation in order to set their width
        table.setAutoCreateColumnsFromModel(false);

        // Jerry Rightmer contributed code.
        table.getColumnModel().getColumn(0).setPreferredWidth(column0Width);
        table.getColumnModel().getColumn(1).setPreferredWidth(column1Width);
        table.getColumnModel().getColumn(2).setPreferredWidth(column2Width);
        table.getColumnModel().getColumn(3).setPreferredWidth(column3Width);
        table.getColumnModel().getColumn(4).setPreferredWidth(column4Width);
        table.getColumnModel().getColumn(5).setPreferredWidth(column5Width);
        table.setAutoResizeMode(table.AUTO_RESIZE_ALL_COLUMNS);
        // end Jerry's.

        table.revalidate();

        setupHeader(table);
    }

    //---------------------------------------------------------------------
    /**
       Converts an Array's contents into a OrderListTableModel
       @param orderList an array containing items from the OrderIfc
       @return tableModel model to be used by the JTable
     */
    //---------------------------------------------------------------------
    static OrderListTableModel array2Model(OrderSummaryEntryIfc[] orderList)
    {
        int listSize = orderList.length;

        // create a new table model with a default of 16 rows
        // and columns = 6
        String[] columnName = {"Order #", "Customer", "Order Date",
                                "Status", "Type", "Total"};

        CustomerIfc customer = null;
        String customerName = "";
        String total = "";

        OrderListTableModel tableModel = null;
        tableModel = new OrderListTableModel(columnName, listSize);

        // loop through the Order List array and
        // retrieve the order #, customer, order date, status and
        // total values
        for (int i = 0; i < listSize; i++)
        {
            // retrieve the Order # and
            // add it to the first table column
            tableModel.setValueAt(orderList[i].getOrderID(), i, 0);

            // retrieve the Customer name value and
            // add it to the second table column
            customerName = orderList[i].getCustomerName();
            tableModel.setValueAt(customerName, i, 1);

            // retrieve the Order Date value and
            // add it to the third table column as an EYSDate
            tableModel.setValueAt(getDateString(
                                    orderList[i].getTimestampCreated()), i, 2);

            // retrieve the Status value and
            // add it to the fourth table column as a String
            tableModel.setValueAt(
                orderList[i].statusToString(
                            orderList[i].getOrderStatus()), i, 3);

            // retrieve the Total value and
            // add it to the fifth table column as a String
            total = orderList[i].getOrderTotal().getStringValue();
            tableModel.setValueAt(total, i, 4);

            // retrieve the Type value and
            // add it to the fifth table column as a String
            tableModel.setValueAt(OrderConstantsIfc.ORDER_CHANNEL_DESCRIPTORS[
                orderList[i].getInitiatingChannel()], i, 4);

            // retrieve the Total value and
            // add it to the sixth table column as a String
            total = orderList[i].getOrderTotal().getStringValue();
            tableModel.setValueAt(total, i, 5);

        }
        return tableModel;
    }

    //---------------------------------------------------------------------
    /**
       Formats an EYSDate value for the JTable display
       @param date an EYSDate value
       @return String to be used by the JTable
     */
    //---------------------------------------------------------------------
    private static String getDateString(EYSDate date)
    {
            if ((date.getMonth() < 10) &&
                    (date.getDay() < 10))
            {
                return date.toFormattedString("M/d/yyyy");
            }
            else if (date.getMonth() < 10)
            {
                return date.toFormattedString("M/dd/yyyy");
            }
            else if (date.getDay() < 10)
            {
                return date.toFormattedString("MM/d/yyyy");
            }
            else
            {
                return date.toFormattedString("MM/dd/yyyy");
            }
    }

    //---------------------------------------------------------------------
    /**
        This method is used to setup the JTable headers.
        @param table reference to the JTable
    **/
    //---------------------------------------------------------------------
    private void setupHeader(JTable table)
    {
        String prefix = "Table.header.label";

        JLabel[] labels = new JLabel[MAX_COLUMNS];

        String[] labelText =
            {"Order #", "Customer", "Order Date",
             "Status", "Type", "Total"};

        TableColumn[] column = new TableColumn[MAX_COLUMNS];

        for (int cnt = 0; cnt < MAX_COLUMNS; cnt++)
        {
            labels[cnt] =
                uiFactory.createLabel(labelText[cnt], null, prefix);

            column[cnt] = table.getColumnModel().getColumn(cnt);
            column[cnt].setHeaderRenderer(new JComponentCellRenderer());
            column[cnt].setHeaderValue(labels[cnt]);
        }
        table.getTableHeader().revalidate();
        // End modify JTable header
    }

    //---------------------------------------------------------------------
    /**
     * Gets the model property (java.lang.Object) value.
     * @return The model property value.
     * @see #setModel
     * @see oracle.retail.stores.pos.ui.beans.OrderListBeanModel
     */
    //---------------------------------------------------------------------
    public void updateModel()
    {
        OrderSummaryEntryIfc[] entries = beanModel.getOrderList();
        beanModel.setSelectedOrder(entries[table.getSelectedRow()]);
    }

    //---------------------------------------------------------------------
    /**
       Sets the model property (java.lang.Object) value.
       @param model The new value for the property.
       @see #updateModel
       @see oracle.retail.stores.pos.ui.beans.OrderListBeanModel
     */
    //---------------------------------------------------------------------
    public void setModel(UIModelIfc model)
    {
        if (model == null)
        {
            throw new NullPointerException("Attempt to set OrderListBean " +
                "model to null");
        }
        if (model instanceof OrderListBeanModel)
        {
            beanModel  = (OrderListBeanModel)model;
            tableModel = array2Model(beanModel.getOrderList());
            dirtyModel = true;
        }
    }

    //---------------------------------------------------------------------
    /**
     * Update the model if it's been changed
     */
    //---------------------------------------------------------------------
    protected void updateBean()
    {
        if (dirtyModel)
        {
            // reset the table model
            OrderListTableModel tableModel = null;
            // convert the order list array to
            // a OrderListTableModel
            tableModel = array2Model(beanModel.getOrderList());
            table.setModel(tableModel);
            dirtyModel = false;
        }
    }

    //--------------------------------------------------------------------------
    /**
        Override JPanel set Visible to request focus.
        @param aFlag indicates if the component should be visible or not.
    **/
    //--------------------------------------------------------------------------
    public void setVisible(boolean visible)
    {
        super.setVisible(visible);

        if (visible)
        {
            table.changeSelection(0,0,false,false);
            table.scrollRectToVisible(table.getCellRect(0,1,false));
            table.requestFocusInWindow();
        }
    }

    //---------------------------------------------------------------------
    // Note: These next methods are empty implementations
    // for the TableBeanIfc. We are required to use the
    // TableBeanIfc for this particular screen.
    //---------------------------------------------------------------------
    //---------------------------------------------------------------------
    /**
     * This method is called everytime the length of the input area
     * on the QuarryTopPanel changes.
     * The highlight heuristics for this is as follows.
     * <OL>
     * <LI> If the length>0, then the clearAction is Enabled AND Highlight is OFF
     * <LI> If the length==0 AND the table is NOT EMPTY,
     *      then the clearAction is Enabled AND Highlight is ON
     * <LI> If the length==0 AND the table is EMPTY,
     *      then the clearAction is Disabled AND Highlight is OFF
     * </OL>
     * This method assumes that the postconditions for the regular clear action
     * has already been met.  For example, if the current input area is non-null, the
     * clear action is enabled, if the input area is null, the clear action is disabled.
     * @param length the new length of the string
     */
    //---------------------------------------------------------------------
    public void inputAreaLengthChanged(int length)
    {
    }

    //---------------------------------------------------------------------
    /**
        Adds an item to the tableBean's model.
        @param index row to add the item to
        @param item the item to add
    **/
    //---------------------------------------------------------------------
    public void addItem(int index,Object item)
    {
    }

    //---------------------------------------------------------------------
    /**
        Deletes an item from the tableBean's model.
        Highlight and selection heuristics are defferred to the implementation.
        @param index row to delete
    **/
    //---------------------------------------------------------------------
    public void deleteItem(int index)
    {
    }

    //---------------------------------------------------------------------
    /**
        Modifies the item at the given index.
        Highlight and selection heuristics are defferred to the implementation.
        @param index index of item to modify
        @param item the new item to replace the index with.
    **/
    //---------------------------------------------------------------------
    public void modifyItem(int index,Object item)
    {
    }

    //---------------------------------------------------------------------
    /**
        Returns the currently selected row in the tableBean.
        @return currently selected row.
    **/
    //---------------------------------------------------------------------
    public int getSelectedRow()
    {
        return table.getSelectedRow();
    }

    //---------------------------------------------------------------------
    /**
        Highlights the current row (if any). Otherwise has no effect.
        If the argument is set to true, then the selected row is highlighted.
        If the argument is false then no row is highlighted.
        @param highlighted Whether or not to highlight the current row.
    **/
    //---------------------------------------------------------------------
    public void setHighlight(boolean highlighted)
    {
    }
    // End of methods for the TableBeanIfc

    //---------------------------------------------------------------------
    /**
        Method to default display string function. <P>
        @return String representation of object
    **/
    //---------------------------------------------------------------------
    public String toString()
    {
        // result string
        String strResult = new String("Class: " + CLASSNAME + " (Revision " +
                                      getRevisionNumber() +
                                      ")" +
                                      hashCode());
        // pass back result
        return(strResult);
    }

    //---------------------------------------------------------------------
    /**
        Retrieves the Team Connection revision number. <P>
        @return String representation of revision number
    **/
    //---------------------------------------------------------------------
    public String getRevisionNumber()
    {
        // return string
        return(Util.parseRevisionNumber(revisionNumber));
    }

    //---------------------------------------------------------------------
    /**
     * main entrypoint - starts the part when it is run as an application
     * @param args java.lang.String[]
     */
    //---------------------------------------------------------------------
    public static void main(java.lang.String[] args)
    {
        UIUtilities.setUpTest();

        OrderListBean bean = new OrderListBean();

        JTable table = bean.table;
        OrderListTableModel tableModel = (OrderListTableModel)table.getModel();

        OrderSummaryEntryIfc[] summaries = new OrderSummaryEntryIfc[5];

        tableModel = array2Model(summaries);
        table.setModel(tableModel);

        bean.activate();
        UIUtilities.doBeanTest(bean);
    }
}
