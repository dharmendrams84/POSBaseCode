/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header:
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED (MM/DD/YY)
 *    cgreen 12/09/11 - do not add/subtract bold from totalField
 *    rrkohl 05/31/11 - increase total label font
 *    cgreen 05/26/11 - Cleaned up documentation, formatting and cell layout
 *    rrkohl 05/20/11 - alignment of fields for pos ui quick win
 *    rrkohl 05/19/11 - pos ui quickwin
 *    rrkohl 05/06/11 - Oracle formatting
 *    rrkohl 05/06/11 - POS UI quickwin
 *    rrkohl 05/05/11 - POS UI quickwin

 */
package oracle.retail.stores.pos.ui.beans;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import oracle.retail.stores.domain.utility.LocaleUtilities;
import oracle.retail.stores.foundation.manager.gui.UIModelIfc;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.pos.ui.UIUtilities;

/**
 * This bean is the presentation of sales totals; including subtotal, tax,
 * total discount, and grand total. The fields are presented in a vertical
 * manner so that the totals add vertically. This bean attempts to align
 * the colon of totals' label text.
 * 
 * @since 13.4
 */
public class StackedTotalBean extends BaseBeanAdapter
{
    private static final long serialVersionUID = 4683294318354700480L;

    /** flag to indicate tax inclusive */
    protected static boolean taxInclusiveFlag = Gateway.getBooleanProperty("application", "InclusiveTaxEnabled", false);

    /** Displays the subTotal */
    protected JLabel subTotalStaticLabel = null;
    protected JLabel subTotalField = null;
    /** Displays Discount */
    protected JLabel discountStaticLabel = null;
    protected JLabel discountField = null;
    protected JLabel taxStaticLabel = null;
    protected JLabel taxField = null;
    protected JLabel totalStaticLabel = null;
    protected JLabel totalField = null;
    protected JLabel qtyStaticLabel = null;
    protected JLabel qtyField = null;

    /**
     * Constructor
     */
    public StackedTotalBean()
    {
        super();
        UI_PREFIX = "StackedTotalBean";
        initialize();
    }

    /**
     * Initialize the class.
     */
    protected void initialize()
    {
        setName("StackedTotalBean");
        initComponents();
        initLayout();
    }

    /**
     * create all the fields and widgets
     */
    protected void initComponents()
    {
        String prefix = UI_PREFIX + ".field";
        qtyStaticLabel = uiFactory.createLabel("qtyStaticLabel", "Qty", null, prefix);
        qtyField = uiFactory.createLabel("qtyField", "3", null, prefix);
        subTotalStaticLabel = uiFactory.createLabel("subTotalStaticLabel", "Subtotal :", null, prefix);
        subTotalField = uiFactory.createLabel("subTotalField", "30.00", null, prefix);
        discountStaticLabel = uiFactory.createLabel("discountStaticLabel", "Discount :", null, prefix);
        discountField = uiFactory.createLabel("discountField", "5.00", null, prefix);
        taxStaticLabel = uiFactory.createLabel("taxStaticLabel", "Tax :", null, prefix);
        taxField = uiFactory.createLabel("taxField", "2.06", null, prefix);
        totalStaticLabel = uiFactory.createLabel("totalStaticLabel", "TOTAL :", null, prefix);
        Font f = totalStaticLabel.getFont();
        totalStaticLabel.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
        totalStaticLabel.setFont(f.deriveFont(18f));
        totalField = uiFactory.createLabel("totalField", "27.05", null, prefix);
        uiFactory.configureUIComponent(this, UI_PREFIX);
    }

    /**
     * Lays out this bean's components. Create 4 row and 3 column grid each
     * widget that is a direct child will have to be placed into a transparent
     * sub-panel for painting the grid lines.
     */
    protected void initLayout()
    {
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints;

        // start in upper left corner moving right and layout children

        // add quantity panel
        JPanel qtyPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        qtyPanel.setOpaque(false);
        qtyPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, getForeground()));
        qtyPanel.add(qtyStaticLabel);
        qtyPanel.add(qtyField);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(qtyPanel, gridBagConstraints);

        // add subtotal label
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        JPanel cell_1_0 = new JPanel(new GridBagLayout());
        cell_1_0.setOpaque(false);
        cell_1_0.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, getForeground()));
        cell_1_0.add(subTotalStaticLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.EAST, 0, new Insets(0, 2, 0, 2), 0, 0));
        add(cell_1_0, gridBagConstraints);

        // add subtotal field
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        JPanel cell_2_0 = new JPanel(new GridBagLayout());
        cell_2_0.setOpaque(false);
        cell_2_0.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, getForeground()));
        cell_2_0.add(subTotalField, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.EAST, 0, new Insets(0, 2, 0, 2), 0, 0));
        add(cell_2_0, gridBagConstraints);

        // add discount label
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        JPanel cell_1_1 = new JPanel(new GridBagLayout());
        cell_1_1.setOpaque(false);
        cell_1_1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, getForeground()));
        cell_1_1.add(discountStaticLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.EAST, 0, new Insets(0, 2, 0, 2), 0, 0));
        add(cell_1_1, gridBagConstraints);

        // add discount field
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        JPanel cell_2_1 = new JPanel(new GridBagLayout());
        cell_2_1.setOpaque(false);
        cell_2_1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, getForeground()));
        cell_2_1.add(discountField, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.EAST, 0, new Insets(0, 2, 0, 2), 0, 0));
        add(cell_2_1, gridBagConstraints);

        // add tax label
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        JPanel cell_1_2 = new JPanel(new GridBagLayout());
        cell_1_2.setOpaque(false);
        cell_1_2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, getForeground()));
        cell_1_2.add(taxStaticLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.EAST, 0, new Insets(0, 2, 0, 2), 0, 0));
        add(cell_1_2, gridBagConstraints);

        // add tax field
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        JPanel cell_2_2 = new JPanel(new GridBagLayout());
        cell_2_2.setOpaque(false);
        cell_2_2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, getForeground()));
        cell_2_2.add(taxField, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.EAST, 0, new Insets(0, 2, 0, 2), 0, 0));
        add(cell_2_2, gridBagConstraints);

        // the bottom row doesn't need any extra grid painting

        // add total label
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(0, 2, 0, 2);
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        add(totalStaticLabel, gridBagConstraints);

        // add total field
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(0, 2, 0, 2);
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        add(totalField, gridBagConstraints);
    }

    /**
     * Sets the bean fields from the model
     * 
     * @param model UIModelIfc
     */
    @Override
    public void setModel(UIModelIfc model)
    {
        if (model == null)
        {
            throw new NullPointerException("Attempt to set EmployeeMasterBeanModel to null");
        }

        if (model instanceof POSBaseBeanModel)
        {
            TotalsBeanModel tModel = null;
            if (model instanceof LineItemsModel)
            {
                LineItemsModel lmodel = (LineItemsModel)model;
                tModel = lmodel.getTotalsBeanModel();
            }

            // Update status text for change of language
            if (tModel != null)
            {
                subTotalField.setText(tModel.getSubtotal());
                if (tModel.getQuantityTotal().signum() > 0)
                {
                    if (tModel.isAllItemUOMQtyDisplay())
                    {
                        // since all items having unit of measure as units,
                        // so displayed as a number
                        qtyField.setText(LocaleUtilities.formatNumber(tModel.getQuantityTotal(), getLocale()));

                    }
                    else
                    {
            // since at least one item not having unit of measure as units,
                        // so displayed as decimal number
                        qtyField.setText(LocaleUtilities.formatDecimal(tModel.getQuantityTotal(), getLocale()));
                    }
                }
                else
                {
                    qtyField.setText("");
                }

                discountField.setText(tModel.getDiscountTotal());
                if (!taxInclusiveFlag)
                {
                    taxField.setText(tModel.getTaxTotal());
                }
                else
                {
                    taxField.setText("");
                }
                // totalField.setHorizontalAlignment(JLabel.RIGHT);
                totalField.setText(tModel.getGrandTotal());
            }

        }
    }

    /**
     * Sets the properties object.
     * 
     * @param props the properties object.
     */
    public void setProps(Properties props)
    {
        this.props = props;
        updatePropertyFields();
    }

    /**
     * Update property fields.
     */
    protected void updatePropertyFields()
    {
        qtyStaticLabel.setText(retrieveText("QtyStaticLabel", qtyStaticLabel));
        subTotalStaticLabel.setText(retrieveText("SubTotalStaticLabel", subTotalStaticLabel));
        discountStaticLabel.setText(retrieveText("DiscountStaticLabel", discountStaticLabel));
        taxStaticLabel.setText(retrieveText("TaxStaticLabel", taxStaticLabel));
        totalStaticLabel.setText(retrieveText("TotalStaticLabel", totalStaticLabel));
    }

    /**
     * main entrypoint - starts the part when it is run as an application
     * 
     * @param args java.lang.String[]
     */
    public static void main(String[] args)
    {
        UIUtilities.setUpTest();
        StackedTotalBean bean = new StackedTotalBean();
        UIUtilities.doBeanTest(bean);
        System.out.println(bean);
    }

}
