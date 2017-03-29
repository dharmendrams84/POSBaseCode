/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/printing/PrintingCargo.java /rgbustores_13.4x_generic_branch/3 2011/07/07 09:07:46 icole Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    icole     07/07/11 - Corrected ClassCastException Bug_ID 322, Bug
 *                         12690086
 *    cgreene   06/02/11 - Tweaks to support Servebase chipnpin
 *    mchellap  10/15/10 - BUG#10182513 Fixed storecoupon franking for
 *                         Pickup/Delivery orders
 *    mchellap  09/29/10 - BUG#10153387 Fixed giftcertificate franking after
 *                         printer timeout
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/27/10 - updating deprecated names
 *    abondala  01/03/10 - update header date
 *    acadar    03/27/09 - added additional instanceof check
 *    acadar    03/27/09 - franking of void redeem gift certificate
 *    arathore  11/20/08 - updated for ereceipt.
 *    ranojha   11/11/08 - Fixed expiration date for StoreCredit Issuance from
 *                         EJournal
 *
 * ===========================================================================
 * $Log:
 *    6    360Commerce 1.5         4/30/2007 7:01:38 PM   Alan N. Sinton  CR
 *         26485 - Merge from v12.0_temp.
 *    5    360Commerce 1.4         4/30/2007 4:56:45 PM   Alan N. Sinton  CR
 *         26484 - Merge from v12.0_temp.
 *    4    360Commerce 1.3         4/25/2007 8:52:16 AM   Anda D. Cadar   I18N
 *         merge
 *
 *    3    360Commerce 1.2         3/31/2005 4:29:29 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:24:23 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:13:26 PM  Robert Pearse
 *
 *   Revision 1.11  2004/07/13 16:01:50  jdeleau
 *   @scr 5841 Make sure already franked gift certificates dont
 *   get refranked on a printer error.
 *
 *   Revision 1.10  2004/05/25 15:12:39  blj
 *   @scr 5117 - fixed printing issues for redeem store credit
 *
 *   Revision 1.9  2004/04/30 13:48:11  aschenk
 *   @scr 4615 - When ever there was pre-printed store credit all tenders were Franked, even when they were not in the franking tender list.
 *
 *   Revision 1.8  2004/04/27 18:45:40  lzhao
 *   @scr 4553: Gift Certificate Franking
 *
 *   Revision 1.7  2004/04/26 19:51:14  dcobb
 *   @scr 4452 Feature Enhancement: Printing
 *   Add Reprint Select flow.
 *
 *   Revision 1.6  2004/04/22 17:39:00  dcobb
 *   @scr 4452 Feature Enhancement: Printing
 *   Added REPRINT_SELECT screen and flow to Reprint Receipt use case..
 *
 *   Revision 1.5  2004/04/15 20:56:18  blj
 *   @scr 3871 - updated to fix problems with void and offline.
 *
 *   Revision 1.4  2004/04/09 16:56:01  cdb
 *   @scr 4302 Removed double semicolon warnings.
 *
 *   Revision 1.3  2004/02/12 16:51:40  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:28  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:19  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.3   Feb 10 2004 14:41:28   bwf
 * Refactor Echeck.
 *
 *    Rev 1.2   Dec 16 2003 15:06:56   blj
 * added franking for store credit tenders
 *
 *    Rev 1.1   Nov 20 2003 18:22:06   bwf
 * Check franking
 * Resolution for 3429: Check/ECheck Tender
 *
 *    Rev 1.0   Aug 29 2003 16:05:30   CSchellenger
 * Initial revision.
 *
 *    Rev 1.8   Jul 23 2003 14:49:24   vxs
 * getting discount total from DISCOUNT_SCOPE_ITEM as well to determine if store coupons are used in the transaction
 * Resolution for POS SCR-2917: All Store Coupons should require Franking
 *
 *    Rev 1.7   Jul 08 2003 17:50:30   vxs
 * removed addTenderForFranking(tli) in various places because now that method is called within FrankTendersSite
 *
 *    Rev 1.6   Jun 30 2003 15:59:32   vxs
 * removed break; inside includesStoreCreditRedeem()
 *
 *    Rev 1.5   Apr 17 2003 17:48:04   KLL
 * code review mods
 * Resolution for POS SCR-2084: Franking Functional Enhancements
 *
 *    Rev 1.4   Mar 26 2003 14:34:58   DCobb
 * Removed developer note from method documentation.
 * Resolution for POS SCR-1821: POS 6.0 Mall Gift Certificates
 *
 *    Rev 1.3   Mar 12 2003 11:22:34   KLL
 * mall certificates, gift certificates and store credit franking
 * Resolution for POS SCR-2084: Franking Functional Enhancements
 *
 *    Rev 1.2   Jan 02 2003 15:05:32   crain
 * Deprecated code list map methods
 * Resolution for 1875: Adding a business customer offline crashes the system
 *
 *    Rev 1.1   Nov 04 2002 11:42:00   DCobb
 * Add Mall Gift Certificate.
 * Resolution for POS SCR-1821: POS 6.0 Mall Gift Certificates
 *
 *    Rev 1.0   Apr 29 2002 15:07:34   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:44:32   msg
 * Initial revision.
 *
 *    Rev 1.4   01 Mar 2002 19:06:04   baa
 * fix franking multiple checks, halt /proceed options
 * Resolution for POS SCR-1362: Selecting No on Slip Printer Timeout retries franking, should not
 *
 *    Rev 1.3   Feb 27 2002 17:27:30   mpm
 * Restructured end-of-transaction processing.
 * Resolution for POS SCR-1440: Enhance end-of-transaction processing for performance reasons
 *
 *    Rev 1.2   06 Feb 2002 20:47:30   baa
 * defect fix
 * Resolution for POS SCR-641: Any franking problem with multi Check tenders do not frank the 2nd check
 *
 *    Rev 1.1   23 Jan 2002 13:08:46   pdd
 * Added includesStoreCredit().
 * Resolution for POS SCR-138: Printer never shows offline on Device Status screen
 *
 *    Rev 1.0   Sep 21 2001 11:22:52   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:11:52   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.printing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import org.apache.log4j.Logger;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.domain.tender.TenderLineItemConstantsIfc;
import oracle.retail.stores.pos.services.common.RetailTransactionCargoIfc;
import oracle.retail.stores.domain.discount.DiscountRuleConstantsIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderConstantsIfc;
import oracle.retail.stores.domain.stock.GiftCertificateItemIfc;
import oracle.retail.stores.domain.tender.TenderCheckIfc;
import oracle.retail.stores.domain.tender.TenderGiftCertificateIfc;
import oracle.retail.stores.domain.tender.TenderLineItemIfc;
import oracle.retail.stores.domain.tender.TenderStoreCreditIfc;
import oracle.retail.stores.domain.tender.TenderTravelersCheckIfc;
import oracle.retail.stores.domain.transaction.OrderTransactionIfc;
import oracle.retail.stores.domain.transaction.RedeemTransactionIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransaction;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
import oracle.retail.stores.domain.transaction.VoidTransactionIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.application.tourcam.ObjectRestoreException;
import oracle.retail.stores.foundation.tour.application.tourcam.SnapshotIfc;
import oracle.retail.stores.foundation.tour.application.tourcam.TourCamIfc;
import oracle.retail.stores.foundation.tour.application.tourcam.TourCamSnapshot;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.receipt.PrintableDocumentException;
import oracle.retail.stores.pos.receipt.PrintableDocumentManagerIfc;

/**
 * Data and methods common to the sites in Printing Service.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/3 $
 */
public class PrintingCargo implements CargoIfc, RetailTransactionCargoIfc, TourCamIfc
{
    /**
     * revision number of this class
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/3 $";

    /**
     * The logger to which log messages will be sent.
     */
    private static final Logger logger = Logger.getLogger(PrintingCargo.class);
    /**
     * The transaction in which tender line items are added.
     */
    protected TenderableTransactionIfc transaction = null;
    /**
     * This Vector holds the list of tender line items that need to be franked.
     */
    protected Vector<TenderLineItemIfc> tendersToFrank = null;

    /**
     * This Vector holds the list of processed tender line items.
     */
    protected Vector<TenderLineItemIfc> processedTenders = null;

    /**
     * The index into the tendersToFrank Vector that indicates the tender
     * currently being franked.
     */
    protected int currentTenderIndex = 0;

    /**
     * The counter for keeping track of pending checks
     */
    protected int pendingCheckCnt = 0;

    /**
     * The Till ID is used in the header on the receipt and the endorsements
     */
    String tillID = new String("");
    /**
     * flag indicating printing request will be a duplicate receipt
     */
    protected boolean duplicateReceipt = false;
    /**
     * flag indicating receipt was printed
     */
    protected boolean receiptPrinted = false;
    /**
     * The number of receipts reprinted
     */
    protected int reprintReceiptCount = 0;

    /**
     * The index from the giftCertificatesForFranking ArrayList which indicates
     * the NonTender Document currently being franked.
     */
    protected int currentNonTenderIndex = 0;

    /**
     * This Vector holds the list of processed NonTender Documents.
     */
    protected ArrayList<SaleReturnLineItemIfc> processedNonTenderDocuments = null;

    /**
     * List of issued gift certificates that need to be franked
     */
    protected ArrayList<SaleReturnLineItemIfc> giftCertificatesForFranking = new ArrayList<SaleReturnLineItemIfc>();

    /**
     * Receipt Style. Can be one of ReceiptStyleConstantsIfc.STYLE_NORMAL,
     * ReceiptStyleConstantsIfc.STYLE_VAT_TYPE_1,
     * ReceiptStyleConstantsIfc.STYLE_VAT_TYPE_2
     */
    protected String receiptStyle = PrintableDocumentManagerIfc.STYLE_NORMAL;

    /**
     * Indicates if eReceipt to be printed.
     */
    protected boolean printEreceipt = false;

    /**
     * Indicates if paper receipt to be printed.
     */
    protected boolean printPaperReceipt = true;

    /**
     * An error that occurred while printing.
     */
    protected PrintableDocumentException e;

    /**
     * This is the constructor.
     */
    public PrintingCargo()
    {
    }

    /**
     * Saves the transaction in the cargo.
     * 
     * @param trans is the RetailTransactionIfc being tendered
     */
    public void setRetailTransaction(RetailTransactionIfc trans)
    {
        transaction = trans;
    }

    /**
     * Retrieves the saved transaction.
     * 
     * @return the RetailTransactionIfc that is being printed
     * @deprecated As of release 4.5.0, use #getRetailTransaction()
     */
    public RetailTransactionIfc getRetailTransactionIfc()
    {
        return getRetailTransaction();
    }

    /**
     * Saves the index into the Tenders Vector to be franked.
     * 
     * @param index
     */
    public void setCurrentTender(int index)
    {
        currentTenderIndex = index;
    }

    /**
     * Retrieves the index of the tender to be franked.
     * 
     * @return int that index
     */
    public int getCurrentTender()
    {
        return currentTenderIndex;
    }

    /**
     * Saves the index into the Tenders Vector to be franked.
     * 
     * @param index
     */
    public void setPendingCheckCount(int index)
    {
        pendingCheckCnt = index;
    }

    /**
     * Retrieves the index of the tender to be franked.
     * 
     * @return int that index
     */
    public int getPendingCheckCount()
    {
        return pendingCheckCnt;
    }

    /**
     * Retrieves the saved transaction.
     * 
     * @return the RetailTransactionIfc that is being printed
     */
    public RetailTransactionIfc getRetailTransaction()
    {
        return (RetailTransactionIfc)transaction;
    }

    /**
     * Retrieves the transaction type (Sale, Return, etc...)
     * 
     * @return the int representation of the transaction type.
     */
    public int getTransType()
    {
        return (transaction.getTransactionType());
    }

    /**
     * @return the error that occurred while printing
     */
    public PrintableDocumentException getPrinterError()
    {
        return e;
    }

    /**
     * @param e the error that occurred while printing
     */
    public void setPrinterError(PrintableDocumentException e)
    {
        this.e = e;
    }

    /**
     * Retrieves a single string value from the config file. This method
     * retrieves the parameter manager reference and issues call to
     * getParameterValue method which uses the ParameterManager reference as an
     * argument.
     * 
     * @param bus is the service bus
     * @param paramName is the String name of the parameter in the config file
     *            for which we want the value
     * @return the String value of the paramName parameter
     */
    public String getParameterValue(BusIfc bus, String paramName)
    {
        ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
        return (getParameterValue(pm, paramName));
    }

    /**
     * Retrieves a single string value from the config file.
     * 
     * @param pm is the ParameterManagerIfc
     * @param paramName is the String name of the parameter in the config file
     *            for which we want the value
     * @return the String value of the paramName parameter
     */
    public String getParameterValue(ParameterManagerIfc pm, String paramName)
    {
        Serializable[] values = null;
        String returnValue = null;

        try
        {
            values = pm.getParameterValues(paramName);
            returnValue = (String)values[0];
            if (logger.isInfoEnabled())
                logger.info("Parameter read: " + paramName + " =[" + returnValue + "]");
        }
        catch (ParameterException e)
        {
            logger.error(Util.throwableToString(e));
        }

        return returnValue;
    }

    /**
     * Retrieves a list of values from the config file. Used for parameters that
     * specify a list of values (ex: drop down list).
     * 
     * @param pm is the ParameterManagerIfc
     * @param paramName is the String name of the parameter in the config file
     *            for which we want the value list
     * @return the Serializable array of values for the paramName parameter
     */
    public Serializable[] getParameterValueList(ParameterManagerIfc pm, String paramName)
    {
        Serializable[] values = null;

        try
        {
            values = pm.getParameterValues(paramName);
        }
        catch (ParameterException e)
        {
            logger.error("" + Util.throwableToString(e) + "");
        }

        return values;
    }

    /**
     * Returns the requested text for a section of the receipt.
     * 
     * @param bus service bus
     * @param param the String name of the parameter in the config file for
     *            which we want the text
     * @return String[] configured text lines
     */
    public String[] getReceiptText(BusIfc bus, String param)
    {
        ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
        return (getReceiptText(pm, param));
    }

    /**
     * Returns the requested text for a section of the receipt.
     * 
     * @param pm is the ParameterManagerIfc
     * @param param is the String name of the parameter in the config file for
     *            which we want the text
     * @return String[] configured text lines
     */
    public String[] getReceiptText(ParameterManagerIfc pm, String param)
    {
        Serializable[] temp = null;
        String[] receiptText = null;

        temp = getParameterValueList(pm, param);
        if (temp != null)
        {
            receiptText = new String[temp.length];
            System.arraycopy(temp, 0, receiptText, 0, temp.length);
        }
        return receiptText;
    }

    /**
     * Determines whether the transaction includes credit. Used to determine
     * whether to print a signature slip.
     * 
     * @return the boolean flag
     */
    public boolean includesCredit()
    {
        Vector<TenderLineItemIfc> tenderLineItems = transaction.getTenderLineItemsVector();
        boolean includesCredit = false;

        // Make sure we have tender items
        if (tenderLineItems != null)
        {
            Enumeration<TenderLineItemIfc> e = tenderLineItems.elements();
            TenderLineItemIfc tli = null;

            // See if there are any charges
            while (e.hasMoreElements())
            {
                tli = e.nextElement();

                if (tli.getTypeCode() == TenderLineItemIfc.TENDER_TYPE_CHARGE)
                {
                    includesCredit = true;
                    break;
                }
            }
        }
        return includesCredit;
    }

    /**
     * Determines whether the transaction includes travel checks. Also add the
     * travelers check to a list of items to frank. Used to determine whether to
     * go to franking.
     * 
     * @return the boolean flag
     */
    public boolean includesTravelerChecks()
    {
        Vector<TenderLineItemIfc> tenderLineItems = transaction.getTenderLineItemsVector();
        boolean inclChecks = false;

        // Make sure we have tender items
        if (tenderLineItems != null)
        {
            Enumeration<TenderLineItemIfc> e = tenderLineItems.elements();
            TenderLineItemIfc tli = null;

            // See if there are any charges
            while (e.hasMoreElements())
            {
                tli = e.nextElement();
                if (tli instanceof TenderTravelersCheckIfc)
                {
                    inclChecks = true;
                    break;
                }
            }
        }

        return (inclChecks);
    }

    /**
     * Determines whether the transaction includes a check. Also add the check
     * to a list of items to frank. Used to determine whether to go to franking.
     * 
     * @return the boolean flag
     */
    public boolean includesCheck()
    {
        Vector<TenderLineItemIfc> tenderLineItems = transaction.getTenderLineItemsVector();
        boolean inclCheck = false;
        // Make sure we have tender items
        if (tenderLineItems != null)
        {
            Enumeration<TenderLineItemIfc> e = tenderLineItems.elements();
            TenderLineItemIfc tli = null;

            // See if there are any checks
            while (e.hasMoreElements())
            {
                tli = e.nextElement();
                if (tli instanceof TenderCheckIfc)
                {
                    if (((TenderCheckIfc)tli).getTypeCode() == TenderLineItemConstantsIfc.TENDER_TYPE_CHECK)
                    {
                        inclCheck = true;
                        break;
                    }
                }
            }
        }

        return inclCheck;
    }

    /**
     * Determines whether the transaction includes an echeck. Also add the
     * e-check to a list of items to frank. Used to determine whether to go to
     * franking.
     * 
     * @return the boolean flag
     */
    public boolean includesECheck()
    {
        Vector<TenderLineItemIfc> tenderLineItems = transaction.getTenderLineItemsVector();
        boolean inclECheck = false;
        // Make sure we have tender items
        if (tenderLineItems != null)
        {
            Enumeration<TenderLineItemIfc> e = tenderLineItems.elements();
            TenderLineItemIfc tli = null;

            // See if there are any checks
            while (e.hasMoreElements())
            {
                tli = e.nextElement();
                if (tli instanceof TenderCheckIfc)
                {
                    if (((TenderCheckIfc)tli).getTypeCode() == TenderLineItemConstantsIfc.TENDER_TYPE_E_CHECK)
                    {
                        inclECheck = true;
                        break;
                    }
                }
            }
        }

        return inclECheck;
    }

    /**
     * Determines whether the transaction includes new store credits. Used to
     * determine whether to print store credits.
     * 
     * @return the boolean flag
     */
    public boolean includesStoreCredit()
    {
        Vector<TenderLineItemIfc> tenderLineItems = transaction.getTenderLineItemsVector();
        boolean includesStCr = false;

        // Make sure we have tender items
        if (tenderLineItems != null)
        {
            Enumeration<TenderLineItemIfc> e = tenderLineItems.elements();
            TenderLineItemIfc tli = null;

            // See if there are any store credits
            while (e.hasMoreElements())
            {
                tli = e.nextElement();

                if (tli.getTypeCode() == TenderLineItemIfc.TENDER_TYPE_STORE_CREDIT)
                {
                    includesStCr = true;
                    break;
                }
            }
        }

        return (includesStCr);
    }

    /**
     * Determines whether the transaction includes a mall certificate. Also add
     * the mall certificate to a list of items to frank. Used to determine
     * whether to go to franking.
     * 
     * @return the boolean flag
     */
    public boolean includesMallCertificate()
    {
        Vector<TenderLineItemIfc> tenderLineItems = transaction.getTenderLineItemsVector();
        boolean inclMallCert = false;
        // Make sure we have tender items
        if (tenderLineItems != null)
        {
            Enumeration<TenderLineItemIfc> e = tenderLineItems.elements();
            TenderLineItemIfc tli = null;

            // See if there are any mall certificates
            while (e.hasMoreElements())
            {
                tli = e.nextElement();
                if (tli.getTypeCode() == TenderLineItemIfc.TENDER_TYPE_MALL_GIFT_CERTIFICATE)
                {
                    inclMallCert = true;
                    break;
                }
            }

        }
        return inclMallCert;
    }

    /**
     * Determines whether the transaction includes a store coupon in order to
     * initiate the franking of all applied store coupons to a transaction.
     * 
     * @param trans is the tenderable transaction
     * @return the boolean flag
     */
    public boolean includesStoreCoupon(TenderableTransactionIfc trans)
    {
        boolean inclStoreCoupon = false;
        SaleReturnTransaction srTrans = (SaleReturnTransaction)trans;
        boolean frankPDOStoreCoupon = true;

        // BUG#10182513 Store coupon franking is only required for new
        // Pickup/Delivery Order
        if (srTrans instanceof OrderTransactionIfc)
        {
            if (((OrderTransactionIfc)srTrans).getOrderStatus().getStatus().getStatus() != OrderConstantsIfc.ORDER_STATUS_NEW)
            {
                frankPDOStoreCoupon = false;
            }
        }

        CurrencyIfc storeCouponDiscount = srTrans
                .getStoreCouponDiscountTotal(DiscountRuleConstantsIfc.DISCOUNT_SCOPE_TRANSACTION);
        storeCouponDiscount = storeCouponDiscount.add(srTrans
                .getStoreCouponDiscountTotal(DiscountRuleConstantsIfc.DISCOUNT_SCOPE_ITEM));
        if (storeCouponDiscount.signum() > 0 && frankPDOStoreCoupon)
        {
            inclStoreCoupon = true;
        }

        return inclStoreCoupon;
    }

    /**
     * Adds the new Tender Line Item to the list of tenders to be franked.
     * 
     * @param tli TenderLineItemIfc new tender item
     */
    protected void addTenderForFranking(TenderLineItemIfc tli)
    {
        if (tendersToFrank == null)
        {
            tendersToFrank = new Vector<TenderLineItemIfc>();
        }
        tendersToFrank.addElement(tli);
    }

    /**
     * Retrieves the list of processed Tender Line Items.
     * 
     * @return Vector of TenderLineItemIfc
     */
    public Vector<TenderLineItemIfc> getProcessedTenders()
    {
        return processedTenders;
    }

    /**
     * Adds the new Tender Line Item to the list of processed tenders.
     * 
     * @param tli TenderLineItemIfc new tender item
     */
    protected void addProcessedTender(TenderLineItemIfc tli)
    {
        if (processedTenders == null)
        {
            processedTenders = new Vector<TenderLineItemIfc>();
        }
        processedTenders.addElement(tli);
    }

    /**
     * Retrieves the list of Tender Line Items to be franked.
     * 
     * @return Vector of TenderLineItemIfc
     */
    public Vector<TenderLineItemIfc> getTendersToFrank()
    {
        return tendersToFrank;
    }

    /**
     * Set the till ID.
     * 
     * @param id String till ID
     */
    public void setTillID(String id)
    {
        tillID = id;
    }

    /**
     * Retrieve the till ID.
     * 
     * @return String till ID
     */
    public String getTillID()
    {
        return tillID;
    }

    /**
     * Set the duplicate receipt indicator.
     * 
     * @param value boolean duplicate receipt indicator
     */
    public void setDuplicateReceipt(boolean value)
    {
        duplicateReceipt = value;
    }

    /**
     * Retrieve the duplicate receipt indicator.
     * 
     * @return boolean duplicate receipt indicator
     */
    public boolean getDuplicateReceipt()
    {
        return isDuplicateReceipt();
    }

    /**
     * Retrieve the duplicate receipt indicator.
     * 
     * @return boolean duplicate receipt indicator
     */
    public boolean isDuplicateReceipt()
    {
        return duplicateReceipt;
    }

    /**
     * Set the receipt-printed indicator.
     * 
     * @param value boolean receipt-printed indicator
     */
    public void setReceiptPrinted(boolean value)
    {
        receiptPrinted = value;
    }

    /**
     * Retrieve the receipt-printed indicator.
     * 
     * @return boolean receipt-printed indicator
     */
    public boolean getReceiptPrinted()
    {
        return isReceiptPrinted();
    }

    /**
     * Retrieve the receipt-printed indicator.
     * 
     * @return boolean receipt-printed indicator
     */
    public boolean isReceiptPrinted()
    {
        return receiptPrinted;
    }

    /**
     * Get the number of receipts reprinted
     * 
     * @return the number of original receipts reprinted
     */
    public int getReprintReceiptCount()
    {
        return reprintReceiptCount;
    }

    /**
     * Set the number of receipts reprinted
     * 
     * @param value The number of original receipts reprinted
     */
    public void setReprintReceiptCount(int value)
    {
        reprintReceiptCount = value;
    }

    /**
     * Retrieve the array of transactions on which items have been returned.
     * This cargo does not track this data.
     * 
     * @return SaleReturnTransactionIfc[]
     */
    public SaleReturnTransactionIfc[] getOriginalReturnTransactions()
    {
        return null;
    }

    /**
     * Saves the transaction in the cargo.
     * 
     * @param trans is the RetailTransactionIfc being tendered
     */
    public void setTransaction(TenderableTransactionIfc trans)
    {
        transaction = trans;
    }

    /**
     * Retrieves the saved transaction.
     * 
     * @return the TenderableTransactionIfc that is being printed
     */
    public TenderableTransactionIfc getTenderableTransaction()
    {
        return transaction;
    }

    /**
     * Retrieves the saved transaction.
     * 
     * @return the RetailTransactionIfc that is being printed
     */
    public TenderableTransactionIfc getTransaction()
    {
        return getTenderableTransaction();
    }

    /**
     * General toString function
     * 
     * @return the String representation of this class
     */
    public String toString()
    {
        // result string
        String strResult = new String("Class:  PrintingCargo (Revision " + getRevisionNumber() + ") @" + hashCode());
        return strResult;

    }

    /**
     * Retrieves the source-code-control system revision number.
     * 
     * @return String representation of revision number
     */
    public String getRevisionNumber()
    { // begin getRevisionNumber()
        // return string
        return (revisionNumber);
    } // end getRevisionNumber()

    /**
     * Create a SnapshotIfc which can subsequently be used to restore the cargo
     * to its current state.
     * 
     * @return an object which stores the current state of the cargo.
     * @see oracle.retail.stores.foundation.tour.application.tourcam.SnapshotIfc
     */
    public SnapshotIfc makeSnapshot()
    {
        return new TourCamSnapshot(this);
    }

    /**
     * Reset the cargo data using the snapshot passed in.
     * 
     * @param snapshot is the SnapshotIfc which contains the desired state of
     *            the cargo.
     * @exception ObjectRestoreException is thrown when the cargo cannot be
     *                restored with this snapshot
     */
    public void restoreSnapshot(SnapshotIfc snapshot) throws ObjectRestoreException
    {
    }

    /**
     * Determines whether store credit is being redeemed during the transaction.
     * 
     * @return the boolean flag
     */
    public boolean includesStoreCreditRedeem()
    {
        boolean includesStCr = false;

        if (transaction instanceof RedeemTransactionIfc)
        {

            TenderLineItemIfc redeemTender = ((RedeemTransactionIfc)transaction).getRedeemTender();
            if (redeemTender != null && redeemTender instanceof TenderStoreCreditIfc)
            {
                includesStCr = true;
            }
        }
        // TODO: add a check for voidtransactions where the original transaction
        // was a redeem.
        else if (transaction instanceof VoidTransactionIfc)
        {
            TenderableTransactionIfc origTrans = ((VoidTransactionIfc)transaction).getOriginalTransaction();
            // int origTransType = origTrans.getTransactionType();
            // if (origTransType == TransactionIfc.TYPE_REDEEM)
            if (origTrans instanceof RedeemTransactionIfc)
            {
                TenderLineItemIfc redeemTender = ((RedeemTransactionIfc)origTrans).getRedeemTender();
                if (redeemTender != null && redeemTender instanceof TenderStoreCreditIfc)
                {
                    includesStCr = true;
                }
            }
        }
        else
        {
            Vector<TenderLineItemIfc> tenderLineItems = transaction.getTenderLineItemsVector();
            TenderStoreCreditIfc tsc = null;
            // See if there are any store credits

            if (tenderLineItems != null)
            {
                Enumeration<TenderLineItemIfc> e = tenderLineItems.elements();
                TenderLineItemIfc tli = null;

                // See if there are any store credits
                while (e.hasMoreElements())
                {
                    tli = e.nextElement();

                    if (tli.getTypeCode() == TenderLineItemIfc.TENDER_TYPE_STORE_CREDIT)
                    {
                        tsc = (TenderStoreCreditIfc)tli;

                        // See if the store credit is being issued.
                        if (tsc.getState().equals(TenderStoreCreditIfc.REDEEM))
                        {
                            includesStCr = true;
                            break;
                        }
                    }
                }
            }
        }

        return (includesStCr);
    }

    /**
     * Determines whether the transaction includes a gift certificate. Also add
     * the gift certificate to a list of items to frank. Used to determine
     * whether to go to franking.
     * 
     * @return the boolean flag
     */
    public boolean includesGiftCertificate()
    {
        Vector<TenderLineItemIfc> tenderLineItems = transaction.getTenderLineItemsVector();
        boolean inclGiftCert = false;
        // Make sure we have tender items
        if (tenderLineItems != null)
        {
            Enumeration<TenderLineItemIfc> e = tenderLineItems.elements();
            TenderLineItemIfc tli = null;

            // See if there are any mall certificates
            while (e.hasMoreElements())
            {
                tli = e.nextElement();
                if (tli.getTypeCode() == TenderLineItemIfc.TENDER_TYPE_GIFT_CERTIFICATE)
                {
                    inclGiftCert = true;
                    break;
                }
            }

        }
        return inclGiftCert;
    }

    /**
     * Determines whether store credit is being redeemed during the transaction.
     * 
     * @return the boolean flag
     */
    public boolean includesGiftCertificateRedeem()
    {
        boolean includesGiftCert = false;
        TenderLineItemIfc redeemTender = null;
        if (transaction instanceof RedeemTransactionIfc)
        {
            redeemTender = ((RedeemTransactionIfc)transaction).getRedeemTender();

        }
        else if (transaction instanceof VoidTransactionIfc)
        {
            TenderableTransactionIfc trn = ((VoidTransactionIfc)transaction).getOriginalTransaction();
            if (trn instanceof RedeemTransactionIfc)
            {
                redeemTender = ((RedeemTransactionIfc)trn).getRedeemTender();
            }
        }

        if (redeemTender != null && redeemTender instanceof TenderGiftCertificateIfc)
        {
            includesGiftCert = true;
        }

        return (includesGiftCert);
    }

    /**
     * Keep track of what gift certificates need to be franked
     * 
     * @param lineItem
     */
    public void addGiftCertificateForFranking(SaleReturnLineItemIfc lineItem)
    {
        if (lineItem.getPLUItem() instanceof GiftCertificateItemIfc)
        {
            giftCertificatesForFranking.add(lineItem);
        }
    }

    /**
     * Once a gift certificate is franked, this method should be called to
     * remove it from the list.
     * 
     * @param lineItem
     */
    public void setGiftCertificateFranked(SaleReturnLineItemIfc lineItem)
    {
        giftCertificatesForFranking.remove(lineItem);
    }

    /**
     * Get a copy of the list used for gift certificate franking
     * 
     * @return list of SaleReturnLineItemIfc objects containing PLUItems that
     *         are gift certs, which need to be franked.
     */
    public ArrayList<SaleReturnLineItemIfc> getGiftCertificatesForFranking()
    {
        return new ArrayList<SaleReturnLineItemIfc>(this.giftCertificatesForFranking);
    }

    /**
     * Returns the receiptStyle value.
     * 
     * @return Returns the receiptStyle.
     */
    public String getReceiptStyle()
    {
        return receiptStyle;
    }

    /**
     * Sets the receiptStyle value.
     * 
     * @param receiptStyle The receiptStyle to set.
     */
    public void setReceiptStyle(String receiptStyle)
    {
        this.receiptStyle = receiptStyle;
    }

    /**
     * Returns the printEreceipt value.
     * 
     * @return Returns the printEreceipt.
     */
    public boolean isPrintEreceipt()
    {
        return printEreceipt;
    }

    /**
     * Sets the printEreceipt value.
     * 
     * @param printEreceipt The printEreceipt to set.
     */
    public void setPrintEreceipt(boolean printEreceipt)
    {
        this.printEreceipt = printEreceipt;
    }

    /**
     * Returns the printPaperReceipt value.
     * 
     * @return Returns the printPaperReceipt.
     */
    public boolean isPrintPaperReceipt()
    {
        return printPaperReceipt;
    }

    /**
     * Sets the printPaperReceipt value.
     * 
     * @param printPaperReceipt The printPaperReceipt to set.
     */
    public void setPrintPaperReceipt(boolean printPaperReceipt)
    {
        this.printPaperReceipt = printPaperReceipt;
    }

    /**
     * Saves the index of the NonTender document currently being franked. param
     * index
     */
    public void setCurrentNonTenderDocument(int index)
    {
        currentNonTenderIndex = index;
    }

    /**
     * Retrieves the index of the NonTender document currently being franked.
     * return int that index
     */
    public int getCurrentNonTenderDocument()
    {
        return currentNonTenderIndex;
    }

    /**
     * Retrieves the list of processed NonTender Documents. return Vector of
     * SaleReturnLineItemIfc
     */
    public ArrayList<SaleReturnLineItemIfc> getProcessedNonTenderDocuments()
    {
        return processedNonTenderDocuments;
    }

    /**
     * Adds the new SaleReturnLineItem to the list of processed NonTender
     * Documents. param sli SaleReturnLineItemIfc object containing PLUItem that
     * is a non tender item
     */
    protected void addProcessedNonTenderDocuments(SaleReturnLineItemIfc sli)
    {
        if (processedNonTenderDocuments == null)
        {
            processedNonTenderDocuments = new ArrayList<SaleReturnLineItemIfc>();
        }
        processedNonTenderDocuments.add(sli);
    }

}
