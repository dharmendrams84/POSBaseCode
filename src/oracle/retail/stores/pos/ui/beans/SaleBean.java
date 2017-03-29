/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/SaleBean.java /rgbustores_13.4x_generic_branch/2 2011/05/23 11:58:42 rrkohli Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    rrkohli   05/06/11 - pos ui quickwin
 *    kelesika  10/04/10 - Addressing disabled Enter btn
 *    kelesika  10/04/10 - Addressing disabled Enter btn on Sell Item screen
 *    blarsen   08/17/10 - Fixed various warnings.
 *    blarsen   08/17/10 - Adding check to prevent enablement of scanner data
 *                         at inappropriate times. Data manager status changes
 *                         perform an asynchronous refresh/update which caused
 *                         lost scans. The scan data was recieved prematurely
 *                         and the NEXT letter was sent when *not* at the show
 *                         sale screen site.
 *    abondala  08/09/10 - disable delete button for external order items
 *    abondala  06/22/10 - Disable Clear Button If ExternalOrder item exists in
 *                         the Transaction
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *
 * ===========================================================================
 * $Log:
 *   3    360Commerce 1.2         3/31/2005 4:29:48 PM   Robert Pearse
 *   2    360Commerce 1.1         3/10/2005 10:24:58 AM  Robert Pearse
 *   1    360Commerce 1.0         2/11/2005 12:14:00 PM  Robert Pearse
 *
 *  Revision 1.5  2004/07/19 21:42:00  cdb
 *  @scr 3511 Removed assertion error.
 *
 *  Revision 1.4  2004/05/11 14:33:00  jlemieux
 *  @scr
 *  270 Fixed by adding a veto mechanism to the lifting of the GlassComponent. In particular, the GlassComponent lift is now vetoed when the scanner's scan queue contains 1 or more items and we are on a multiscan screen in POS. This effectively makes POS "prefer" to drain scan queues rather than service user input, which is what we want.
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
 *    Rev 1.3   28 Jan 2004 20:32:28   baa
 * set focus index
 *
 *    Rev 1.2   26 Jan 2004 00:56:24   baa
 * allow for multiple selections
 *
 *    Rev 1.1   Dec 30 2003 16:59:12   baa
 * cleanup for return feature
 * Resolution for 3561: Feature Enhacement: Return Search by Tender
 *
 *    Rev 1.0   Aug 29 2003 16:11:54   CSchellenger
 * Initial revision.
 *
 *    Rev 1.4   Nov 21 2002 10:19:50   mpb
 * SCR #2857
 * In updateBean()  and updateModel(), verify the class of the object before casting it.
 * Resolution for kbpos SCR-2857: ClassCastException during receipt printing
 *
 *    Rev 1.3   Aug 14 2002 18:18:30   baa
 * format currency
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.2   05 Jun 2002 22:02:46   baa
 * support for  opendrawerfortrainingmode parameter
 * Resolution for POS SCR-1645: Training Mode Enhancements
 *
 *    Rev 1.1   14 May 2002 18:30:08   baa
 * training mode enhancements
 * Resolution for POS SCR-1645: Training Mode Enhancements
 *
 *    Rev 1.0   Apr 29 2002 14:57:22   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:57:18   msg
 * Initial revision.
 *
 *    Rev 1.5   Feb 27 2002 21:25:54   mpm
 * Continuing work on internationalization
 * Resolution for POS SCR-228: Merge VABC, Pier 1 changes
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.4   Feb 25 2002 10:51:16   mpm
 * Internationalization
 * Resolution for POS SCR-228: Merge VABC, Pier 1 changes
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.3   30 Jan 2002 16:42:52   baa
 * ui fixes
 * Resolution for POS SCR-965: Add Customer screen UI defects
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;

import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.foundation.manager.gui.*;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.pos.ui.OnlineStatusContainer;
import oracle.retail.stores.pos.ui.POSListModel;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.UIUtilities;
import oracle.retail.stores.pos.ui.behavior.ClearActionListener;
import oracle.retail.stores.pos.ui.behavior.EnableButtonListener;
import oracle.retail.stores.pos.ui.behavior.GlobalButtonListener;
import oracle.retail.stores.pos.ui.behavior.LocalButtonListener;

// ------------------------------------------------------------------------------
/**
 * The sale bean presents the functionality of the sale screen.
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
// ------------------------------------------------------------------------------
public class SaleBean extends ListBean implements DocumentListener, ClearActionListener, EnableUIEventsVetoer
{

    private static final long serialVersionUID = -9115713935468176035L;

    /** revision number * */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    public static final int H_GAP = 1;

    public static final int V_GAP = 1;

    /** Totals header * */
  //  protected AbstractTotalsBean totalsBean = null; commented for pos ui quickwin 

    /** The local enable button listener * */
    protected EnableButtonListener localButtonListener = null;

    /** The global enable button listener * */
    protected EnableButtonListener globalButtonListener = null;

    /** array of rows to delete * */
    protected int[] rowsToDelete = null;

    /** Constant for the clear action name * */
    protected static final String CLEAR_ACTION = "Clear";

    /** Constant for the next action name * */
    protected static final String NEXT_ACTION = "Next";

    /** Handle for a BeanChangeListener * */
    protected BeanChangeListener beanChangeListener = null;

    private boolean itemIdEntered = true;

    // --------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public SaleBean()
    {
        super();
        setName("SaleBean");
    }

    // --------------------------------------------------------------------------
    /**
     * Sets the totals bean.
     *
     * @param propValue the class name of the totals bean
     */
/*    public void setTotalsBean(String propValue) commented for pos ui quickwin 
    {
        if (propValue != null)
        {
            if (totalsBean == null || !totalsBean.getClass().getName().equals(propValue))
            {
                totalsBean = (AbstractTotalsBean)UIUtilities.getNamedClass(propValue);
            }
        }

        if (totalsBean == null)
        {
            totalsBean = new SaleTotalsBean();
        }

        add(totalsBean, BorderLayout.SOUTH);
    }*/

    // --------------------------------------------------------------------------
    /**
     * Configures the list.
     */
    protected void configureList()
    {
        super.configureList();
        list.setAutoscrolls(true);
    }

    // ---------------------------------------------------------------------
    /**
     * Gets the totals bean.
     */
    // ---------------------------------------------------------------------
/*    protected AbstractTotalsBean getTotalsBean() commented for pos ui quickwin 
    {
        if (totalsBean == null)
        {
            // Initialize the totals panel
            setTotalsBean(null);
        }
        return totalsBean;
    }*/

    // ---------------------------------------------------------------------
    /**
     * Activate this screen.
     */
    // ---------------------------------------------------------------------
    public void activate()
    {
        super.activate();
        // getTotalsBean().activate(); commented for pos ui quickwin 
        list.setEnabled(true);
    }

    // ---------------------------------------------------------------------
    /**
     * Deactivate this screen.
     */
    // ---------------------------------------------------------------------
    public void deactivate()
    {
        super.deactivate();
        list.setEnabled(false);
    }

    // ---------------------------------------------------------------------
    /**
     * Updates the model if It's been changed
     */
    // ---------------------------------------------------------------------
    public void updateBean()
    {

        // verify that the beanModel is of the expected type
        if (LineItemsModel.class.isInstance(beanModel))
        { // Begin bean model is expected type
            LineItemsModel model = (LineItemsModel)beanModel;
            StatusBeanModel statusBeanModel = model.getStatusBeanModel();

            if (statusBeanModel != null)
            {
                OnlineStatusContainer onlineStatusContainer = statusBeanModel.getStatusContainer();

                if (onlineStatusContainer != null)
                {
                    Hashtable<Integer, Boolean> hashtable = onlineStatusContainer.getStatusHash();
                    Object object = hashtable.get(new Integer(POSUIManagerIfc.TRAINING_MODE_STATUS));
                    if (object != null)
                    {
                        Boolean trainingMode = (Boolean)object;
                        setApplicationBackground(trainingMode.booleanValue());
                    }
                }
            }

            POSListModel listModel = model.getListModel();
            getList().setModel(listModel);

            // If there is a line item ...
            if (listModel != null && listModel.getSize() > 0)
            {
                list.clearSelection();

                // Check for highlight position
                if (model.getMoveHighlightToTop())
                {
                    list.setSelectedIndex(0);
                }
                else
                {
                    if (model.getSelectedRows() != null && (model.getSelectedRows().length > 0))
                    {
                        list.setSelectedIndices(model.getSelectedRows());
                    }
                    else
                    {
                        list.setSelectedIndex(model.getSelectedRow());
                    }

                }
            }
            else
            {
                getList().setSelectedIndex(EYSList.NO_SELECTION);
            }
            // If there is a bean model available, update the totals
           /* if (model.getTotalsBeanModel() != null)
            {
                getTotalsBean().setModel(model.getTotalsBeanModel());
            }*/
            // Do not use this bean to update the screen again.
            model.setBeanHasBeenUpdated(true);

            // Tell the Scanner Session that we're ready to handle scanner data
            if (isStatusChangeUpdate())
            {
                logger
                        .debug("Update/refresh requested.  But, update is for data manager status change.  "
                                + "Status change is asynchronous.  Sale may not be ready for scanner data.  So, not calling notifyReadyForData()");
            }
            else
            {
                notifyReadyForData();
            }

            // After bean has been updated, set "clear" button appropriately
            manageClearButton();

        } // End bean model is expected type

    }

    /*
     * Is the update caused by a change to the data manager status?
     *
     * Typically the refresh/update is caused by the tour's return to the
     * ShowSaleScreenSite.
     *
     * However, status changes can occur asynchronously and during the middle of
     * the sale tour.
     *
     * You do not want to enable scanner event data in the middle of a tour
     * flow.
     *
     * When scanner data is received, the prompt and response bean receives a
     * scanner event and sends a NEXT letter. This letter is only appropriate
     * when in the ShowSaleScreenSite.
     *
     * @return true if the update is caused by a data manager status change
     */
    private boolean isStatusChangeUpdate()
    {
        boolean isStatusChange = false;

        LineItemsModel model = (LineItemsModel)beanModel;
        StatusBeanModel statusBeanModel = model.getStatusBeanModel();
        if (statusBeanModel != null)
        {
            OnlineStatusContainer onlineStatusContainer = statusBeanModel.getStatusContainer();

            if (onlineStatusContainer != null)
            {
                Hashtable<Integer, Boolean> hashtable = onlineStatusContainer.getStatusHash();
                Object object = hashtable.get(POSUIManagerIfc.DATA_MANAGER_STATUS);

                if (object != null)
                {
                    isStatusChange = true;
                }
            }
        }
        return isStatusChange;
    }

    // ---------------------------------------------------------------------
    /**
     * Ordinarily the method would update the LineItemsModel for return to the
     * business logic. However, in this bean the happens in the actionPerfomed()
     * method in response to the NEXT and CLEAR actions.
     */
    // ---------------------------------------------------------------------
    public void updateModel()
    {

        // verify that the beanModel is of the expected type
        if (LineItemsModel.class.isInstance(beanModel))
        { // Begin bean model is expected type

            LineItemsModel model = (LineItemsModel)beanModel;

            if (rowsToDelete != null)
            {
                model.setRowsToDelete(rowsToDelete);
                rowsToDelete = null;
            }
            else
            {
                model.setSelectedRows(list.getAllSelectedRows());
            }

        } // End bean model is expected type

    }

    // ---------------------------------------------------------------------
    /**
     * Deletes items from the model.
     * <p>
     *
     * @param index The array of indices of items to delete
     */
    // ---------------------------------------------------------------------
    public void deleteItems(int[] index)
    {
        int newIndex = 0;
        DefaultListModel model = (DefaultListModel)list.getModel();
        for (int i = index.length - 1; i > -1; i--)
        {
            model.remove(index[i]);
            newIndex = index[i];
        }
        // Adjust index based on the current cursor position.
        int row = list.getSelectedRow();

        if (newIndex < row)
        {
            newIndex = row - 1;
        }
        else if (newIndex > row)
        {
            newIndex = row;
        }
        int size = model.getSize();

        if (newIndex >= size)
        {
            newIndex = size - 1;
        }

        // if selected items remain in the list, set a new selected row
        if (list.isSelectionEmpty() == false)
        {
            list.setSelectedIndex(newIndex);
        }
        else
        {
            list.setSelectedIndex(EYSList.NO_SELECTION);
        }
    }

    // ---------------------------------------------------------------------
    /**
     * Modifies an item in the model.
     * <p>
     *
     * @param index The index to modify.
     * @param item the item to replace with.
     */
    // ---------------------------------------------------------------------
    public void modifyItem(int index, Object item)
    {
        ((DefaultListModel)list.getModel()).setElementAt(item, index);
    }

    // ---------------------------------------------------------------------
    /**
     * Implemented for the DocumentListener interface.
     *
     * @Param evt the cocument event
     */
    // ---------------------------------------------------------------------
    public void changedUpdate(DocumentEvent evt)
    {
        checkAndEnableButtons(evt);
    }

    // ---------------------------------------------------------------------
    /**
     * Implemented for the DocumentListener interface.
     *
     * @Param evt the document event
     */
    // ---------------------------------------------------------------------
    public void insertUpdate(DocumentEvent evt)
    {
        checkAndEnableButtons(evt);
    }

    // ---------------------------------------------------------------------
    /**
     * Implemented for the DocumentListener interface.
     *
     * @Param evt the document event
     */
    // ---------------------------------------------------------------------
    public void removeUpdate(DocumentEvent evt)
    {
        checkAndEnableButtons(evt);
    }

    // ---------------------------------------------------------------------
    /**
     * Determines if the response field has text and sets the "Next" and "Clear"
     * buttons appropriately.
     *
     * @Param evt the document event
     */
    // ---------------------------------------------------------------------
    public void checkAndEnableButtons(DocumentEvent evt)
    {
        if (evt.getDocument().getLength() > 0)
        {
            globalButtonListener.enableButton(NEXT_ACTION, true);
            globalButtonListener.enableButton(CLEAR_ACTION, true);
            itemIdEntered = true;
        }
        else
        {
            itemIdEntered = false;
            globalButtonListener.enableButton(NEXT_ACTION, false);
            if (list.getModel().getSize() > 0)
            {
                globalButtonListener.enableButton(CLEAR_ACTION, true);
            }
            else
            {
                globalButtonListener.enableButton(CLEAR_ACTION, false);
            }
        }
    }

    // ---------------------------------------------------------------------
    /**
     * Determines if the "Clear" button should be enabled.
     */
    // ---------------------------------------------------------------------
    public void manageClearButton()
    {
        if (globalButtonListener != null)
        {
            if (list.getModel().getSize() > 0)
            {
                if (!checkIfExternalOrderItemExists(list))
                {
                    globalButtonListener.enableButton(CLEAR_ACTION, true);
                }
                else
                {
                    globalButtonListener.enableButton(CLEAR_ACTION, false);
                }
            }
            else
            {
                globalButtonListener.enableButton(CLEAR_ACTION, false);
            }
        }
    }

    // ---------------------------------------------------------------------
    /**
     * If the Item is from ExternalOrder, disable the Clear button.
     *
     * If multiple items are selected and at least one of them is from
     * ExternalOrder, disable the Clear button.
     */
    // ---------------------------------------------------------------------
    protected boolean checkIfExternalOrderItemExists(EYSList multiList)
    {

        // get the selection
        int listLength = multiList.getSelectedIndices().length;
        int[] selectedIndices = multiList.getSelectedIndices();
        int currentItem = multiList.getSelectedRow();
        int selection = list.getSelectedIndex();

        try
        {
            // check for disabling selection
            if (selection != -1)
            {
                if (listLength <= 1)
                {
                    // single item selected
                    if (multiList.getModel().getElementAt(currentItem) instanceof SaleReturnLineItemIfc)
                    {
                        SaleReturnLineItemIfc srli = (SaleReturnLineItemIfc)multiList.getModel().getElementAt(
                                currentItem);
                        if (srli.isFromExternalOrder())
                        {
                            return true;
                        }
                    }
                }

                // does list contain any External Orders
                // Same item can exist as an external order as well as local
                // item with different prices
                for (int i = 0; i < selectedIndices.length; i++)
                {
                    if (multiList.getModel().getElementAt(selectedIndices[i]) instanceof SaleReturnLineItemIfc)
                    {
                        SaleReturnLineItemIfc srli = (SaleReturnLineItemIfc)multiList.getModel().getElementAt(
                                selectedIndices[i]);
                        if (srli.isFromExternalOrder())
                        {
                            return true;
                        }
                    }
                }

                if (currentItem != -1)
                {
                    if (multiList.getModel().getElementAt(currentItem) instanceof SaleReturnLineItemIfc)
                    {
                        SaleReturnLineItemIfc srli = (SaleReturnLineItemIfc)multiList.getModel().getElementAt(
                                currentItem);
                        if (srli.isFromExternalOrder())
                        {
                            return true;
                        }
                    }
                }

            }
            else if (currentItem != -1 && listLength != -1)
            {
                // no selection but is current item external order?
                Object obj = (Object)multiList.getModel().getElementAt(currentItem);
                if (obj instanceof SaleReturnLineItemIfc)
                {
                    SaleReturnLineItemIfc srli = (SaleReturnLineItemIfc)multiList.getModel().getElementAt(currentItem);
                    if (srli.isFromExternalOrder())
                    {
                        return true;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            // when an item is deleted from the list, this may throw exception
            // which can be ignored.
        }

        return false;
    }

    // ---------------------------------------------------------------------
    /**
     * This event is called when the user presses "clear".
     *
     * @param evt the action event
     */
    // ---------------------------------------------------------------------
    public void actionPerformed(ActionEvent evt)
    {
        // Delete all selected rows from the list, then mail the letter to
        // the business logic.
        if (evt.getActionCommand().equalsIgnoreCase(CLEAR_ACTION))
        {
            rowsToDelete = list.getAllSelectedRows();
            deleteItems(rowsToDelete);
            UISubsystem.getInstance().mail(new Letter(CLEAR_ACTION), true);
            globalButtonListener.enableButton(CLEAR_ACTION, false);
        }
    }

    // ---------------------------------------------------------------------
    /**
     * Adds (actually sets) the enable button listener on the bean.
     *
     * @param listener
     */
    // ---------------------------------------------------------------------
    public void addGlobalButtonListener(GlobalButtonListener listener)
    {
        globalButtonListener = listener;
    }

    // ---------------------------------------------------------------------
    /**
     * Removes the enable button listener from the bean.
     *
     * @param listener
     */
    // ---------------------------------------------------------------------
    public void removeGlobalButtonListener(GlobalButtonListener listener)
    {
        globalButtonListener = null;
    }

    // ---------------------------------------------------------------------
    /**
     * Gets the enable button listener from the bean.
     *
     * @return listener
     */
    // ---------------------------------------------------------------------
    public EnableButtonListener getGlobalButtonListener()
    {
        return globalButtonListener;
    }

    // ---------------------------------------------------------------------
    /**
     * Adds (actually sets) the enable button listener on the bean.
     *
     * @param listener
     */
    // ---------------------------------------------------------------------
    public void addLocalButtonListener(LocalButtonListener listener)
    {
        localButtonListener = listener;
    }

    // ---------------------------------------------------------------------
    /**
     * Removes the enable button listener from the bean.
     *
     * @param listener
     */
    // ---------------------------------------------------------------------
    public void removeLocalButtonListener(LocalButtonListener listener)
    {
        localButtonListener = null;
    }

    // ---------------------------------------------------------------------
    /**
     * Gets the enable button listener from the bean.
     *
     * @return listener
     */
    // ---------------------------------------------------------------------
    public EnableButtonListener getLocalButtonListener()
    {
        return localButtonListener;
    }

    // ---------------------------------------------------------------------
    /**
     * Adds (actually sets) a BeanChangeListener.
     *
     * @param listener BeanChangeListener
     */
    // ---------------------------------------------------------------------
    public void addBeanChangeListener(BeanChangeListener listener)
    {
        beanChangeListener = listener;
    }

    // ---------------------------------------------------------------------
    /**
     * Removess (actually unsets) a BeanChangeListener.
     *
     * @param listener BeanChangeListener
     */
    // ---------------------------------------------------------------------
    public void removeBeanChangeListener(BeanChangeListener listener)
    {
        if (listener != null && listener == beanChangeListener)
        {
            beanChangeListener = null;
        }
    }

    // ------------------------------------------------------------------------------
    /**
     * Adds a list selection listener to the internal list.
     *
     * @param l the listener
     */
    public void addListSelectionListener(ListSelectionListener l)
    {
        list.addListSelectionListener(l);
    }

    // ------------------------------------------------------------------------------
    /**
     * Removes a list selection listener to the internal list.
     *
     * @param l the listener
     */
    public void removeListSelectionListener(ListSelectionListener l)
    {
        list.removeListSelectionListener(l);
    }

    // ---------------------------------------------------------------------
    /**
     * Notify bean change listener that bean has changed... and we're ready for
     * data.
     */
    // ---------------------------------------------------------------------
    protected void notifyReadyForData()
    {
        if (beanChangeListener != null)
        {
            beanChangeListener.beanChanged(new BeanChangeEvent(this));
        }
    }

    // --------------------------------------------------------------------------
    /**
     * Override JPanel set Visible to request focus.
     *
     * @param aFlag indicates if the component should be visible or not.
     */
    // --------------------------------------------------------------------------
    public void setVisible(boolean aFlag)
    {
        super.setVisible(aFlag);

        if (aFlag)
        {
            if (list.getModel().getSize() > 0 && globalButtonListener != null)
            {
                if (!checkIfExternalOrderItemExists(list))
                {
                    globalButtonListener.enableButton(CLEAR_ACTION, true);
                }
                else
                {
                    globalButtonListener.enableButton(CLEAR_ACTION, false);
                }
                if ((beanModel.getPromptAndResponseModel() != null)
                        && (beanModel.getPromptAndResponseModel().getResponseText() != null) && (itemIdEntered))
                {
                    globalButtonListener.enableButton(NEXT_ACTION, true);
                }
            }
        }
    }

    // ---------------------------------------------------------------------
    /**
     * Set the properties to be used by this bean
     *
     * @param props the propeties object
     */
    // ---------------------------------------------------------------------
    public void setProps(Properties props)
    {
        super.setProps(props);

       // getTotalsBean().setProps(props); commented for pos ui quickwin 
        if (renderer != null && renderer instanceof AbstractListRenderer)
        {
            ((AbstractListRenderer)renderer).setProps(props);
        }
    }

    /**
     * Returns <tt>true</tt> to veto the enabling of UI events if the
     * underlying scanner reports items in its scan queue; <tt>false</tt> if
     * there is no attached scanner, or if the scanner is attached but its scan
     * queue is empty.
     *
     * @return <tt>true</tt> to veto the enabling of UI events if the
     *         underlying scanner reports items in its scan queue;
     *         <tt>false</tt> otherwise
     */
    public boolean vetoEnableOfUIEvents()
    {
        if (beanChangeListener != null)
        {
            // we expect beanChangeListener to be an instance of
            // EnableUIEventsVetoer
            // (Specifically, it should be a ScannerSessionAdapter)
            // if it's not, then we are in trouble because we are attempting to
            // delegate the decision of whether to veto the enabling of UI
            // events
            // all the way down to the scanner
            if (beanChangeListener instanceof EnableUIEventsVetoer)
            {
                // delegate the decision of whether to veto the enabling of UI
                // events
                // to the object listening to this bean (should be the
                // ScannerSessionAdapter)
                return ((EnableUIEventsVetoer)beanChangeListener).vetoEnableOfUIEvents();
            }
            else
            {
                final String msg = "We expect beanChangeListener to also be an instanceof EnableUIEventsVetoer. "
                        + "If it is not, then we cannot query the attached scanner for the number of items in the scan queue. "
                        + "See Foundation SCR 270 for the details of why that's a bad thing. In short, we must lock the "
                        + "UI down while the scanner is allowed to drain its scan queue, otherwise the user may preempt the "
                        + "scanner and navigate to a screen where scans no longer make sense and we'll ignore the remaining "
                        + "scans in the scan queue.";

                // for good measure, we log this important message
                logger.error(msg);

                // and then conjure up a failed Assertion to aid people in
                // detecting
                // this problem.
                assert beanChangeListener instanceof EnableUIEventsVetoer : msg;
            }
        }
        return false;
    }

    // ---------------------------------------------------------------------
    /**
     * Starts the part when it is run as an application
     * <p>
     *
     * @param args command line parameters
     */
    // ---------------------------------------------------------------------
    public static void main(java.lang.String[] args)
    {
        UIUtilities.setUpTest();

        SaleBean bean = new SaleBean();
        bean.setLabelText("Description/Item #,Qty,Price,Discount,Ext Price,Tax");
        bean.setLabelWeights("40,13,13,13,13,8");
        bean.setRenderer("oracle.retail.stores.pos.ui.beans.SaleLineItemRenderer");

        LineItemsModel model = new LineItemsModel();
        AbstractTransactionLineItemIfc[] items = new AbstractTransactionLineItemIfc[3];
        AbstractListRenderer r = (AbstractListRenderer)bean.getRenderer();
        items[0] = (AbstractTransactionLineItemIfc)r.createPrototype();
        items[1] = (AbstractTransactionLineItemIfc)r.createPrototype();
        items[2] = (AbstractTransactionLineItemIfc)r.createPrototype();
        model.setLineItems(items);
        bean.setModel(model);
        bean.activate();

        UIUtilities.doBeanTest(bean);
    }
}
