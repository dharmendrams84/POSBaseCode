/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/taximport/TaxView.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:10 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/09/10 - updated to fix the LAT printing issue. Closed
 *                         connections and statements. Fix to resolve the ejb
 *                         passivate issues.
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *   8    360Commerce 1.7         4/8/2008 5:15:37 PM    Edward B. Thorne
 *        Updating file headers and copyright dates.  Reviewed by Owen.
 *   7    360Commerce 1.6         1/7/2008 4:12:36 PM    Tony Zgarba     CR
 *        29762:  Remediated password code scan failures.  Reviewed by Michael
 *         Barnett.
 *   6    360Commerce 1.5         11/15/2007 11:19:02 AM Christian Greene
 *        Belize merge - cannot get item price this way due to PLU changes
 *   5    360Commerce 1.4         6/1/2007 3:16:05 PM    Christian Greene
 *        Backing out PLU to pre-v1.0.0.414 version code
 *   4    360Commerce 1.3         6/1/2007 8:31:02 AM    Christian Greene
 *        comment out come usage of no-longer-available rp_sls_pos_crt column
 *   3    360Commerce 1.2         3/31/2005 4:30:21 PM   Robert Pearse
 *   2    360Commerce 1.1         3/10/2005 10:25:50 AM  Robert Pearse
 *   1    360Commerce 1.0         2/11/2005 12:14:45 PM  Robert Pearse
 *  $
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.taximport;

// java imports
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import oracle.retail.stores.persistence.utility.DBConstantsIfc;

/**
 *  This is a utility program used for viewing Tax Authorities, Tax Groups,
 *  and Tax Rules and Rates.  Tax information is read from the 360Commerce
 *  database and loaded into a hierarchical tree for viewing the individual
 *  tax structures.
 *  <P>
 *  @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @deprecated as of 13.3. Tax import is done through DIMP.
 */
public class TaxView
{
    /**
     * revision number of this class
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    // frame
    private static JFrame frame;

    // database connection
    private static java.sql.Connection con;

    // content pane
    private Container contentPane;

    // tree objects
    private JTree tree;
    private DefaultTreeModel model;
    private DefaultMutableTreeNode root;
    protected DefaultMutableTreeNode child;

    // work area - right panel
    private JPanel workPanel;
    private JTextArea workTextArea;
    private JScrollPane workSP;

    // results table and model
    private DefaultTableModel tableModel;
    private JTable outputTable;

    // container to hold all the Tax Authority Objects
    private ArrayList taxAuthList;

    // container to hold all the Tax Group Objects
    private ArrayList taxGroupList;

    // container to hold all the Tax Group Rule Objects
    private ArrayList taxGroupRuleList;

    // Tax Authority table query
    private static final String GET_TAX_AUTH_SQL = "select * from pa_athy_tx";

    // Tax Authority Postal Code table query
    private static final String GET_POSTAL_CODES_SQL =
            "select * from pa_athy_tx_pstl " +
            "where id_athy_tx = ? ";

    // Tax Group table query
    private static final String GET_TAX_GROUP_SQL = "select * from co_gp_tx_itm";

    // Item table query
    private static final String GET_ITEMS_SQL =
            "select id_itm, de_itm_shrt from as_itm " +
            "where id_gp_tx = ? ";

    // POS Identity table query
    private static final String GET_ITEM_PRICE_SQL =
            "select rp_sls_pos_crt from id_idn_ps " +
            "where id_itm_pos = ? ";

    // Tax Group Rule table query
    private static final String GET_TAX_GROUP_RULE_SQL = "select * from ru_tx_gp";

    // Tax Rate Rule table query
    private static final String GET_TAX_RATE_RULE_SQL =
            "select * from ru_tx_rt " +
            "where id_athy_tx = ? and id_gp_tx = ?";


    /**
     *   Constructor for the TaxView. <P>
     */
    public TaxView()
    {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println("Error loading LAF: " + ex);
        }

        frame = new JFrame("Tax View");
        frame.addWindowListener (new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }});
        contentPane = frame.getContentPane();
        frame.setSize(900, 500);

        contentPane.setLayout(new BorderLayout());
        contentPane.add(createPanel(), BorderLayout.CENTER);

        // establish a database connection
        boolean connectionOK = getPropertiesConnection();
        if (connectionOK)
        {
            taxAuthList = getTaxAuthorities();
            taxGroupList = getTaxGroups();
            taxGroupRuleList = getTaxGroupRules();
            buildTaxTree();
        }
        else
        {
            System.exit(0);
        }

        frame.setVisible(true);

    }

    /**
     *  Creates the split pane and adds the tree and work area to it. <P>
     *  @JSplitPane panel.
     */
    public JSplitPane createPanel()
    {

        JSplitPane sp = new JSplitPane();

        root = new DefaultMutableTreeNode("Tax Authorities");
        model = new DefaultTreeModel(root);
        model.setAsksAllowsChildren(true);

        // create and configure out tree
        tree = new JTree(model);
        tree.putClientProperty("JTree.lineStyle", "Angled");
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(new MyTreeSelectionListener());
        tree.addTreeExpansionListener(new MyTreeExpansionListener());
        tree.setScrollsOnExpand(true);
        ToolTipManager.sharedInstance().registerComponent(tree);

        // create a scrollpane for our tree
        JScrollPane treeSP = new JScrollPane();
        treeSP.setPreferredSize(new Dimension(200, 0));
        treeSP.setMinimumSize(new Dimension(0,0));
        treeSP.getViewport().add(tree);
        treeSP.setBorder(BorderFactory.createEmptyBorder(0,2,0,0));

        // create the work area (right) panel
        workTextArea = new JTextArea();
        workSP = new JScrollPane(workTextArea);

        // set up our splitpane
        sp.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        sp.setContinuousLayout(true);
        sp.setDividerSize(4);
        sp.setOneTouchExpandable(false);
        sp.setTopComponent(treeSP);
        sp.setBottomComponent(workSP);

        return sp;

    }

    /**
    *  This method populates the tax tree with objects built from
    *  the database queries.
    */
    public void buildTaxTree()
    {

        root = new DefaultMutableTreeNode("Tax Information");
        model.setRoot(root);

        DefaultMutableTreeNode taFolder = new DefaultMutableTreeNode("Tax Authorities");
        model.insertNodeInto(taFolder, root, root.getChildCount());
        DefaultMutableTreeNode tgFolder = new DefaultMutableTreeNode("Tax Groups");
        model.insertNodeInto(tgFolder, root, root.getChildCount());
        DefaultMutableTreeNode taxGroupRuleFolder = new DefaultMutableTreeNode("Tax Group Rules");
        model.insertNodeInto(taxGroupRuleFolder, root, root.getChildCount());

        // add the Postal Codes folder to the Tax Authority folder
        DefaultMutableTreeNode taNode = null;
        TaxAuthority ta = null;
        ListIterator taIterator = taxAuthList.listIterator();
        while (taIterator.hasNext())
        {
            ta = (TaxAuthority) taIterator.next();
            taNode = new DefaultMutableTreeNode(ta);
            model.insertNodeInto(taNode, taFolder, taFolder.getChildCount());
            DefaultMutableTreeNode postalFolder = new DefaultMutableTreeNode("Postal Codes");
            model.insertNodeInto(postalFolder, taNode, taNode.getChildCount());
        }

        // add the Tax Group nodes
        DefaultMutableTreeNode tgNode = null;
        TaxGroup tg = null;
        ListIterator tgIterator = taxGroupList.listIterator();
        while (tgIterator.hasNext())
        {
            tg = (TaxGroup) tgIterator.next();
            tgNode = new DefaultMutableTreeNode(tg);
            model.insertNodeInto(tgNode, tgFolder, tgFolder.getChildCount());
            DefaultMutableTreeNode itemFolder = new DefaultMutableTreeNode("Items");
            model.insertNodeInto(itemFolder, tgNode, tgNode.getChildCount());
        }

        // add the Tax Group Rule nodes
        DefaultMutableTreeNode groupRuleNode = null;
        TaxGroupRule taxGroupRule = null;
        ListIterator groupRuleIterator = taxGroupRuleList.listIterator();
        while (groupRuleIterator.hasNext())
        {
            taxGroupRule = (TaxGroupRule) groupRuleIterator.next();
            groupRuleNode = new DefaultMutableTreeNode(taxGroupRule);
            model.insertNodeInto(groupRuleNode, taxGroupRuleFolder, taxGroupRuleFolder.getChildCount());
            DefaultMutableTreeNode rateFolder = new DefaultMutableTreeNode("Tax Rate Rules");
            model.insertNodeInto(rateFolder, groupRuleNode, groupRuleNode.getChildCount());
        }

    }

    /**
    *  Retrieve the tax authorities from the db. <P>
    *  @return ArrayList containing the tax authorities.
    */
    public ArrayList getTaxAuthorities() {

        ArrayList list = new ArrayList();

        ResultSet rs = null;
        Statement stmt = null;

        PreparedStatement pstmt = null;
        ResultSet rs2 = null;

        try {

            stmt = con.createStatement();
            stmt.execute(GET_TAX_AUTH_SQL);
            rs = stmt.getResultSet();
            TaxAuthority ta = null;
            while ( rs.next() )
            {
                ta = new TaxAuthority();
                String taxAuthorityID = rs.getString("ID_ATHY_TX");
                ta.setID(taxAuthorityID);
                ta.setName(rs.getString("NM_ATHY_TX"));
                list.add(ta);

                // loop thru tax authorities and get associated postal codes.
                ArrayList postalCodes = new ArrayList();
                pstmt = con.prepareStatement(GET_POSTAL_CODES_SQL);
                pstmt.setString(1, ta.getID());
                rs2 = pstmt.executeQuery();
                while ( rs2.next() )
                {
                    postalCodes.add(rs2.getString("PC_CNCT"));
                }
                ta.setPostalCodes(postalCodes);
                rs2.close();
                pstmt.close();

            }
            rs.close();
            stmt.close();

        } catch(Exception e) {
            showErrorMessage("getTaxAuthorities: " + e.toString());
        }
        finally
        {
        	closeResultSet(rs2);
        	closeStatement(pstmt);

        	closeResultSet(rs);
        	closeStatement(stmt);
        }


        return list;
    }

    /**
    *  Retrieve the tax groups from the db. <P>
    *  @return ArrayList containing the Tax Group objects.
    */
    public ArrayList getTaxGroups() {

        ArrayList list = new ArrayList();
        ResultSet rs = null;
        Statement stmt = null;

        try {

            stmt = con.createStatement();
            stmt.execute(GET_TAX_GROUP_SQL);
            rs = stmt.getResultSet();
            TaxGroup tg = null;
            while ( rs.next() )
            {
                tg = new TaxGroup();
                tg.setID(rs.getString("ID_GP_TX"));
                tg.setName(rs.getString("NM_GP_TX"));
                tg.setItems(getItems(rs.getString("ID_GP_TX")));
                list.add(tg);
            }
            rs.close();
            stmt.close();

        } catch(Exception e) {
            showErrorMessage("getTaxGroups: " + e.toString());
        }
        finally
        {
        	closeResultSet(rs);
        	closeStatement(stmt);
        }


        return list;
    }

    /**
    *  Retrieve the items in a given tax group. <P>
    *  @param String Tax Group ID.
    *  @return ArrayList containing items.
    */
    public ArrayList getItems(String taxGroupID)
    {

        ArrayList list = new ArrayList();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {

            pstmt = con.prepareStatement(GET_ITEMS_SQL);
            pstmt.setString(1, taxGroupID);
            rs = pstmt.executeQuery();
            TaxItem ti = null;
//            PreparedStatement pstmt2 = con.prepareStatement(GET_ITEM_PRICE_SQL);
            while ( rs.next() )
            {
                ti = new TaxItem();
                ti.setTaxGroupID(taxGroupID);
                String itemID = rs.getString("ID_ITM");
                ti.setID(itemID);
                ti.setItemDescription(rs.getString("DE_ITM_SHRT"));
                // get the item price
//                pstmt2.setString(1, itemID);
//                ResultSet rs2 = pstmt2.executeQuery();
//                rs2.next();
//                ti.setPrice(rs2.getFloat("RP_SLS_POS_CRT"));
//                rs2.close();
                list.add(ti);
            }
            rs.close();
            pstmt.close();

        } catch(Exception e) {
            showErrorMessage("getItems: " + e.toString());
        }
        finally
        {
        	closeResultSet(rs);
        	closeStatement(pstmt);
        }


        return list;

    }

    /**
    *  Retrieve the tax groups for the given tax authority ID. <P>
    *  @param String Tax Authority ID.
    *  @return ArrayList containing the tax groups.
    */
    public ArrayList getTaxGroupRules()
    {

        ArrayList list = new ArrayList();
        ResultSet rs = null;
        Statement stmt = null;

        try {

            stmt = con.createStatement();
            stmt.execute(GET_TAX_GROUP_RULE_SQL);
            rs = stmt.getResultSet();
            TaxGroupRule tg = null;
            while ( rs.next() )
            {
                tg = new TaxGroupRule();
                String taxGroupID = rs.getString("ID_GP_TX");
                String taxAuthID = rs.getString("ID_ATHY_TX");
                tg.setID(taxGroupID);
                tg.setName(rs.getString("NM_RU_TX"));
                tg.setTaxAuthorityID(taxAuthID);
                tg.setCompoundSeq(rs.getString("AI_CMPND"));
                tg.setGrossAmountFlag(rs.getString("FL_TX_GS_AMT"));
                tg.setUsageCode(rs.getString("CD_TX_RT_RU_USG"));
                tg.setTaxRateRules(getTaxRateRules(taxAuthID, taxGroupID));
                list.add(tg);
            }
            rs.close();
            stmt.close();

        } catch(Exception e) {
            showErrorMessage("getTaxGroupRules: " + e.toString());
        }
        finally
        {
        	closeResultSet(rs);
        	closeStatement(stmt);
        }


        return list;

    }

    /**
    *  Retrieve the tax groups for the given tax authority ID and group ID. <P>
    *  @param String Tax Authority ID.
    *  @param String Tax Group ID.
    *  @return ArrayList containing the tax rules.
    */
    public ArrayList getTaxRateRules(String taxAuthorityID, String taxGroupID)
    {

        ArrayList list = new ArrayList();
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {

            pstmt = con.prepareStatement(GET_TAX_RATE_RULE_SQL);
            pstmt.setString(1, taxAuthorityID);
            pstmt.setString(2, taxGroupID);
            rs = pstmt.executeQuery();
            TaxRateRule tr = null;
            while ( rs.next() )
            {
                tr = new TaxRateRule();
                tr.setID(rs.getString("AI_TX_RT_RU"));
                tr.setRuleTypeCode(rs.getString("CD_TYP"));
                tr.setAboveThresholdFlag(rs.getString("FL_TX_ABV_TH_MO"));
                tr.setThresholdAmount(rs.getFloat("MO_TX_TH"));
                tr.setPercentageAmount(rs.getFloat("PE_TX"));
                tr.setRateAmount(rs.getFloat("MO_TX"));
                tr.setMinimumAmount(rs.getFloat("MO_TXBL_MIN"));
                tr.setMaximumAmount(rs.getFloat("MO_TXBL_MAX"));
                tr.setEffectiveTimestamp(rs.getTimestamp("TS_RT_TX_EF"));
                tr.setExpirationTimestamp(rs.getTimestamp("TS_RT_TX_EP"));
                list.add(tr);
            }
            rs.close();
            pstmt.close();

        } catch(Exception e) {
            showErrorMessage("getTaxRateRules: " + e.toString());
        }
        finally
        {
        	closeResultSet(rs);
        	closeStatement(pstmt);
        }

        return list;

    }

    /**
     *  This method is activated when the Tax Authority folder is selected in the
     *  tree.  It displays all Tax Authorities in a table format in the right pane.
     */
    private void showTaxAuthorityInfo()
    {

        Vector rows = new Vector();
        Vector rowData = null;
        TaxAuthority ta = null;

        Iterator iterator = taxAuthList.iterator();
        while ( iterator.hasNext() )
        {
            ta = (TaxAuthority)iterator.next();
            rowData = new Vector();
            rowData.addElement( ta.getID() );
            rowData.addElement( ta.getName() );
            rows.addElement(rowData);
        }

        // create the table headers
        String[] colnames = {"Tax Auth ID", "Tax Auth Name"};
        Vector header = new Vector(Arrays.asList(colnames));
        setWorkAreaTable(header, rows);

    }

    /**
     *  This method is activated when the Postal Code folder is selected in the
     *  tree.  It displays all Postal Codes associated with a Tax Authority in a table
     *  format in the right pane. <P>
     *  @param TaxAuthority tax authority object.
     */
    private void showPostalCodes(TaxAuthority ta)
    {

        Vector rows = new Vector();
        Vector rowData = null;
        String code = null;

        Iterator iterator = ta.getPostalCodes().iterator();
        while (iterator.hasNext())
        {
            code = (String)iterator.next();
            rowData = new Vector();
            rowData.addElement(ta.getID());
            rowData.addElement(ta.getName());
            rowData.addElement(code);
            rows.addElement(rowData);
        }

        // create the table headers
        String[] colnames = { "Tax Auth ID", "Tax Auth Name", "Postal Code" };
        Vector header = new Vector(Arrays.asList(colnames));
        setWorkAreaTable(header, rows);

    }

    /**
     *  This method is activated when the Tax Group folder is selected in the
     *  tree.  It displays all Tax Groups in a table format in the right pane.
     */
    private void showTaxGroupInfo()
    {

        Vector rows = new Vector();
        Vector rowData = null;
        TaxGroup tg = null;

        Iterator iterator = taxGroupList.iterator();
        while ( iterator.hasNext() )
        {
            tg = (TaxGroup)iterator.next();
            rowData = new Vector();
            rowData.addElement( tg.getID() );
            rowData.addElement( tg.getName() );
            rows.addElement(rowData);
        }

        // create the table headers
        String[] colnames = {"Tax Group ID", "Tax Group Name"};
        Vector header = new Vector(Arrays.asList(colnames));
        setWorkAreaTable(header, rows);

    }

    /**
     *  This method is activated when the Tax Group Rule folder is selected in the
     *  tree.  It displays all Tax Group Rules in a table format in the right pane.
     */
    private void showTaxGroupRuleInfo()
    {

        Vector rows = new Vector();
        Vector rowData = null;
        TaxGroupRule tg = null;

        Iterator iterator = taxGroupRuleList.iterator();
        while (iterator.hasNext())
        {
            tg = (TaxGroupRule)iterator.next();
            rowData = new Vector();
            rowData.addElement(tg.getTaxAuthorityID());
            rowData.addElement(tg.getID());
            rowData.addElement(tg.getName());

            if (tg.getUsageCode().equals("1"))
            {
                 rowData.addElement("1-PercentageOrAmount");
            }
            else if (tg.getUsageCode().equals("2"))
            {
                 rowData.addElement("2-DeriveFromTaxTable");
            }
            else if (tg.getUsageCode().equals("3"))
            {
                 rowData.addElement("3-UseThresholdAmount");
            }
            else
            {
                 rowData.addElement(tg.getUsageCode());
            }

            if (tg.getGrossAmountFlag().equals("0"))
            {
                rowData.addElement("0-applies to discounted price");
            }
            else if (tg.getGrossAmountFlag().equals("1"))
            {
                rowData.addElement("1-applies to original price");
            }
            else
            {
                rowData.addElement(tg.getGrossAmountFlag());
            }

            rowData.addElement(tg.getCompoundSeq());
            rows.addElement(rowData);
        }

        // create the table headers
        String[] colnames = { "Tax Auth ID", "Tax Group ID", "Name",
                              "Usage Code", "Gross Amt Flag", "Cmpnd" };
        Vector header = new Vector(Arrays.asList(colnames));
        setWorkAreaTable(header, rows);

    }

    /**
     *  This method is activated when the Tax Rule folder is selected in the
     *  tree.  It displays all Tax Rules in a table format in the right pane. <P>
     *  @param TaxGroupRule tax group rule object.
     */
    private void showTaxRateRuleInfo(TaxGroupRule tg)
    {

        Vector rows = new Vector();
        Vector rowData = null;
        TaxRateRule tr = null;

        String ruleUsageCode = tg.getUsageCode();
        Iterator iterator = tg.getTaxRateRules().iterator();
        while (iterator.hasNext())
        {
            tr = (TaxRateRule)iterator.next();
            rowData = new Vector();
            //rowData.addElement(tg.getTaxAuthorityID());
            //rowData.addElement(tg.getID());

            // the sybase jdbc driver screws up the timestamps, so this is a
            // hack to show the important digits.
            if (tr.getEffectiveTimestamp() != null ||
                tr.getExpirationTimestamp() != null ) {
                rowData.addElement(tr.getEffectiveTimestamp().toString().substring(0, 19));
                rowData.addElement(tr.getExpirationTimestamp().toString().substring(0, 19));
            } else {
                rowData.addElement("");
                rowData.addElement("");
            }

            if (tr.getRuleTypeCode().equals("1"))
            {
                rowData.addElement("1-Percentage Rate");
            }
            else if (tr.getRuleTypeCode().equals("2"))
            {
                rowData.addElement("2-Rate Amount");
            }

            if (ruleUsageCode.equals("1"))
            {
                if (tr.getRuleTypeCode().equals("1"))
                {
                    rowData.addElement(String.valueOf(tr.getPercentageAmount()));
                }
                else if (tr.getRuleTypeCode().equals("2"))
                {
                    rowData.addElement(String.valueOf(tr.getRateAmount()));
                }
            }
            else if (ruleUsageCode.equals("2"))
            {
                rowData.addElement(String.valueOf(tr.getRateAmount()));
                rowData.addElement(String.valueOf(tr.getMinimumAmount()));
                rowData.addElement(String.valueOf(tr.getMaximumAmount()));
            }
            else
            {
                rowData.addElement(String.valueOf(tr.getPercentageAmount()));
                rowData.addElement(String.valueOf(tr.getThresholdAmount()));
                if (tr.getAboveThresholdFlag().equals("0"))
                {
                    rowData.addElement("0-Tax entire base price");
                }
                else if (tr.getAboveThresholdFlag().equals("1"))
                {
                    rowData.addElement("1-Tax amt above thrshld");
                }
                else
                {
                    rowData.addElement(" ");
                }
            }

            rows.addElement(rowData);
        }

        // create the table headers
        Vector header = null;
        if (ruleUsageCode.equals("1"))
        {
            String[] colnames1 = { "Effective", "Expiration", "Type Code", "Rate" };
            header = new Vector(Arrays.asList(colnames1));
        }
        else if (ruleUsageCode.equals("2"))
        {
            String[] colnames2 = { "Effective", "Expiration", "Type Code", "Rate", "Min", "Max" };
            header = new Vector(Arrays.asList(colnames2));
        }
        else
        {
            String[] colnames3 = { "Effective", "Expiration", "Type Code", "Pct", "Thrshld Amt", "Thrshld Flag" };
            header = new Vector(Arrays.asList(colnames3));
        }
        setWorkAreaTable(header, rows);

    }

    /**
     *  This method is activated when the Tax Item folder is selected in the
     *  tree.  It displays all Tax Items in a table format in the right pane.
     */
    private void showItems(TaxGroup tg)
    {

        Vector rows = new Vector();
        Vector rowData = null;
        TaxItem ti = null;

        Iterator iterator = tg.getItems().iterator();
        while (iterator.hasNext())
        {
            ti = (TaxItem)iterator.next();
            rowData = new Vector();
            rowData.addElement(ti.getTaxGroupID());
            rowData.addElement(ti.getID());
            rowData.addElement(ti.getItemDescription());
            rowData.addElement(String.valueOf(ti.getPrice()));
            rows.addElement(rowData);
        }

        // create the table headers
        String[] colnames = { "Tax Group ID", "Item ID", "Description", "Price" };
        Vector header = new Vector(Arrays.asList(colnames));
        setWorkAreaTable(header, rows);

    }

    /**
     *  Creates the JTable and puts it in the work area. <P>
     *  @param Vector table header info.
     *  @param Vector rows table rows and columns.
     */
    private void setWorkAreaTable(Vector header, Vector rows)
    {
        // create the table model
        DefaultTableModel tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // create the table and set the model
        JTable table = new JTable(tableModel);
        table.setShowGrid(false);
        table.setRowSelectionAllowed(false);
        tableModel.setDataVector(rows, header);
        workSP.setViewportView(table);
    }

    /**
     *  Looks for a taxview.properties file to connect to the 360 database.
     *  @return boolean true when connection is established.
     */
    private boolean getPropertiesConnection()
    {
        try {

            File file = new File("taxview.properties");
            if (!file.exists())
            {
                showErrorMessage("taxview.properties file not found");
                return false;
            }

            Properties props = new Properties();
            props.load(new FileInputStream(file));
            String driver = (String)props.get("driver");
            String url = (String)props.get("url");
            String user = (String)props.get("userid");
            String password = (String)props.get("password");
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                return true;
            }

        } catch (Exception e) {
            StringBuffer msg = new StringBuffer();
            msg.append("Error connecting to the database!\n");
            msg.append("Make sure the database is running and the JDBC driver ");
            msg.append("is in the classpath.\n");
            msg.append(e.toString());
            showErrorMessage(msg.toString());
        }
        return false;

    }

    /**
     *  Listener class responds to tree selection events.
     */
    class MyTreeSelectionListener implements TreeSelectionListener
    {

        public void valueChanged(TreeSelectionEvent e)
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                            tree.getLastSelectedPathComponent();

            if (node == null) return;
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)node.getParent();

            // folder selection
            if (node.getUserObject().toString().equals("Tax Authorities")) {
                showTaxAuthorityInfo();
            }
            else if (node.getUserObject().toString().equals("Postal Codes")) {
                TaxAuthority ta = (TaxAuthority)parentNode.getUserObject();
                showPostalCodes(ta);
            }
            else if (node.getUserObject().toString().equals("Tax Groups")) {
                showTaxGroupInfo();
            }
            else if (node.getUserObject().toString().equals("Items")) {
                TaxGroup tg = (TaxGroup)parentNode.getUserObject();
                showItems(tg);
            }
            else if (node.getUserObject().toString().equals("Tax Group Rules")) {
                showTaxGroupRuleInfo();
            }
            else if (node.getUserObject().toString().equals("Tax Rate Rules")) {
                TaxGroupRule tg = (TaxGroupRule)parentNode.getUserObject();
                showTaxRateRuleInfo(tg);
            }

        }
    }

    /** Listens for expanding nodes and then expands the children nodes that do not
     *  have their own children.  This removes the unwanted plus signs from nodes that
     *  do not have any children.
     */
    class MyTreeExpansionListener implements TreeExpansionListener
    {

        public void treeExpanded( TreeExpansionEvent event )
        {
            TreePath treePath = event.getPath();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)treePath.getLastPathComponent();
            DefaultMutableTreeNode child = null;
            for ( int i = 0; i < node.getChildCount(); i++ )
            {
                child = (DefaultMutableTreeNode)node.getChildAt(i);
                if ( child.getChildCount() == 0 )
                {
                    tree.expandPath( new TreePath( child.getPath() ));
                }
            }
        }

        public void treeCollapsed( TreeExpansionEvent event )
        {
        }

    }

    /**
     * show information message dialog
     */
    public static void showInfoMessage( String msg )
    {
        JOptionPane.showMessageDialog(frame, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * show error message dialog
     */
    public static void showErrorMessage( String msg )
    {
        JOptionPane.showMessageDialog(frame, msg, "Error Message", JOptionPane.ERROR_MESSAGE);
    }

   /**
    * Access to the frame of this application
    * @return Frame the instance of the main application
    */
    public static Frame getFrame()
    {
        return frame;
    }

    /**
     * Main method.
     */
    public static void main( String args[] )
    {
        TaxView tv = new TaxView();
    }

    /**
     * Return the revisionNumber of the class. <P>
     * @return the string form of the revision number.
     */
    public String getRevisionNumber()
    {
        return revisionNumber;
    }

    /**
     * Return some information pertaining to the object. <P>
     * @return string of class name and revision number.
     */
    public String toString()
    {
        String strResult = "Class:  " + getClass().getName() +
                           " Revision: " + getRevisionNumber();
        return strResult;
    }

    /**
     * Attempts to close a JDBC ResultSet and logs any errors
     *
     * @param rs java.sql.ResultSet
     */
    public void closeResultSet(ResultSet rs)
    {
        if (rs != null)
        {
            try
            {
                rs.close();
                rs = null;
            }
            catch (SQLException e)
            {

            }
        }
    }

    /**
     * Attempts to close a JDBC Statement and logs any errors
     *
     * @param s java.sql.Statement
     */
    public void closeStatement(Statement s)
    {
        if (s != null)
        {
            try
            {
                s.close();
                s = null;
            }
            catch (SQLException e)
            {

            }
        }
    }


}

