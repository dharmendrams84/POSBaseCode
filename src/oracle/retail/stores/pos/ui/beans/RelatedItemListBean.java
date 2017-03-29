/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/RelatedItemListBean.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:58 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    cgreen 05/28/10 - convert to oracle packaging
 *    cgreen 05/27/10 - convert to oracle packaging
 *    cgreen 05/27/10 - convert to oracle packaging
 *    cgreen 05/26/10 - convert to oracle packaging
 *    cgreen 04/17/09 - fix item image painting in lists
 *    cgreen 04/10/09 - fix possible npe when checking for blob by adding
 *                      hasImageBlob to ItemImageIfc
 *    cgreen 03/30/09 - removed item name column from item image table
 *    cgreen 03/19/09 - refactoring changes
 *    ddbake 02/02/09 - Removing line that should never have been added.
 *                      Invalid code would override any attempts to customize
 *                      the column layouts.
 *    atirke 12/02/08 - Item Image CR
 *    atirke 11/14/08 - image resizing
 *    atirke 10/23/08 - changes for error logging
 *    atirke 10/23/08 -
 *    atirke 10/01/08 - adde image path
 *    atirke 10/01/08 -
 *    atirke 10/01/08 - modified for item images
 *    atirke 09/30/08 -
 *    atirke 09/29/08 - added logic to set related item images
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import oracle.retail.stores.domain.stock.RelatedItemSummaryIfc;
import oracle.retail.stores.foundation.manager.gui.UIModelIfc;
import oracle.retail.stores.pos.ui.POSListModel;

/**
 * This class is the datamodel for the RELATED_ITEMS and PICK_ONE_RELATED_ITEM
 * screens.
 * 
 * @author jdeleau $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public class RelatedItemListBean extends ItemListBean
{
    private static final long serialVersionUID = 993868884872426495L;

    protected static final int WAIT_TIME = 100;

    protected RelatedItemListBeanModel beanModel = new RelatedItemListBeanModel();

    /**
     * Default constructor.
     * 
     * @since NEP67
     */
    public RelatedItemListBean()
    {
        super();

    }

    /**
     * Sets the information to be shown by this bean.
     * 
     * @param model the model to be shown. The runtime type should be
     *            RelatedItemBeanModel
     * @since NEP67
     */
    @Override
    public void setModel(UIModelIfc model)
    {
        if (model == null)
        {
            throw new NullPointerException("Attempt to set RelatedItemListBean " + "model to null");
        }
        if (model instanceof RelatedItemListBeanModel)
        {
            beanModel = (RelatedItemListBeanModel)model;

        }

        updateBean();
    }

    /**
     * Updates the model for the current settings of this bean.
     * 
     * @since NEP67
     */
    @Override
    public void updateModel()
    {
        Object[] selectedObjects = list.getSelectedValues();
        beanModel.setSelectedItems(selectedObjects);
    }

    /**
     * Updates the model if It's been changed
     */
    @Override
    protected void updateBean()
    {
        POSListModel posListModel = new POSListModel(beanModel.getItemList());
        getList().setModel(posListModel);

        Object item = beanModel.getSelectedItem();
        if (item != null)
        {
            list.setSelectedValue(item, true);
        }

        // mark all the images as loading if they have not been loaded yet
        RelatedItemSummaryIfc[] items = beanModel.getItemList();
        for (int i = 0; i < items.length; i++)
        {
            loadImage(items[i].getItemImage(), i);
        }
    }

    /**
     * Set the label weights for both the header and the main list.
     * 
     * @see oracle.retail.stores.pos.ui.beans.ListBean#setLabelWeights(java.lang.String)
     * @since NEP67
     * @deprecated as of 13.1. Use
     *             {@link oracle.retail.stores.pos.ui.beans.ListBean#setLabelWeights(String)}
     *             instead
     */
    public void setLabelWeights(String text)
    {
        super.setLabelWeights(text);
    }
}
