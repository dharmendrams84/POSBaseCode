/* ===========================================================================
* Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/receipt/PrintableDocumentManagerIfc.java /main/11 2011/03/08 17:21:20 vtemker Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    vtemker   03/03/11 - Changes for Print Preview Reports Quickwin
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    cgreene   11/17/08 - deprecated isExchangeTransactionType method
 *    cgreene   11/13/08 - configure print beans into Spring context
 *
 * ===========================================================================
 * $Log:
 *  7    I18N_P2    1.5.1.0     12/18/2007 3:09:14 PM  Sandy Gu        static
 *       text fix for POS
 *  6    360Commerce 1.5         6/13/2007 12:12:08 PM  Alan N. Sinton  CR
 *       26485 - Changes per code review.
 *  5    360Commerce 1.4         6/4/2007 12:50:12 PM   Alan N. Sinton  CR
 *       26484 - Changes per review comments.
 *  4    360Commerce 1.3         5/22/2007 4:55:02 PM   Alan N. Sinton  CR
 *       26485 - PrintableDocumentManagerIfc is made to extend ManagerIfc.
 *  3    360Commerce 1.2         5/15/2007 4:03:09 PM   Alan N. Sinton  CR
 *       26481 - Phase one for VAT modifications to ORPOS <ARG> Summary
 *       Reports.
 *  2    360Commerce 1.1         4/30/2007 7:01:38 PM   Alan N. Sinton  CR
 *       26485 - Merge from v12.0_temp.
 *  1    360Commerce 1.0         4/30/2007 4:55:58 PM   Alan N. Sinton  CR
 *       26484 - Merge from v12.0_temp.
 * $
 * ===========================================================================
 */
package oracle.retail.stores.pos.receipt;

import oracle.retail.stores.common.context.BeanLocator;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.manager.ManagerIfc;
import oracle.retail.stores.foundation.tour.service.SessionBusIfc;
import oracle.retail.stores.pos.reports.ReportTypeConstantsIfc;
import oracle.retail.stores.pos.reports.SummaryReport;

/**
 * Manager for handling Printable Documents such as Receipts, Reports, etc.
 * $Revision: /main/11 $
 */
public interface PrintableDocumentManagerIfc extends ManagerIfc
{
    /**
     * Name used to access the parameter manager from within a bus.
     */
    public static final String TYPE = "PrintableDocumentManager";

    /*
     * Constants for Receipt Styles
     */
    /** Constant for normal style receipt. */
    public static final String STYLE_NORMAL = "Normal";

    /** Constant for VAT Type 1 style receipt. */
    public static final String STYLE_VAT_TYPE_1 = "VATType1";

    /** Constant for VAT Type 2 style receipt. */
    public static final String STYLE_VAT_TYPE_2 = "VATType2";

    /**
     * Prints the receipt for the transaction.
     * 
     * @param bus The service bus
     * @param parameterBean Receipt parameterBean
     * @throws PrintableDocumentException
     */
    public void printReceipt(SessionBusIfc bus, PrintableDocumentParameterBeanIfc parameterBean)
            throws PrintableDocumentException;
    
    /**
     * Gets the print text as String
     * 
     * @param bus
     * @param parameterBean
     * @return  
     * @throws PrintableDocumentException
     */
    public String getPreview(SessionBusIfc bus, PrintableDocumentParameterBeanIfc parameterBean)
    		throws PrintableDocumentException;
    /**
     * Prints the gift receipt for the given line item.
     * 
     * @param bus The session bus
     * @param parameterBean The Instance of the
     *            GiftReceiptParameterBeanIfc.
     * @throws PrintableDocumentException
     */
    public void printGiftReceipt(SessionBusIfc bus, GiftReceiptParameterBeanIfc parameterBean)
            throws PrintableDocumentException;

    /**
     * Prints the send gift receipts for the given parameter beans.
     * 
     * @param bus The session bus
     * @param parameterBean The Instance of the
     *            GiftReceiptParameterBeanIfc.
     * @throws PrintableDocumentException
     */
    public void printSendGiftReceipt(SessionBusIfc bus, GiftReceiptParameterBeanIfc[] beans)
            throws PrintableDocumentException;

    /**
     * Prints the shipping slip for the given line item.
     * 
     * @param bus The session bus
     * @param parameterBean The Instance of the
     *            GiftReceiptParameterBeanIfc.
     * @throws PrintableDocumentException
     */
    public void printShippingSlip(SessionBusIfc bus, PrintableDocumentParameterBeanIfc parameterBean)
            throws PrintableDocumentException;

    /**
     * Prints the order receipt for the given line item.
     * 
     * @param bus The session bus
     * @param parameterBean The Instance of the
     *            OrderReceiptParameterBeanIfc.
     * @throws PrintableDocumentException
     */
    public void printOrderReceipt(SessionBusIfc bus, OrderReceiptParameterBeanIfc parameterBean)
            throws PrintableDocumentException;

    /**
     * Retrieves receipt text.
     * 
     * @param propName property key
     * @param defaultValue default value
     * @return text from support facility
     */
    public String retrieveReceiptText(String propName, String defaultValue);

    /**
     * Returns the number of (sale) receipts printed since the last customer
     * survey.
     * 
     * @return The number of (sale) receipts printed since the last customer
     *         survey.
     */
    public int getPrintReceiptCount();

    /**
     * Sets the number of (sale) receipts printed since the last customer
     * survey.
     * 
     * @param count The new count.
     */
    public void setPrintReceiptCount(int count);

    /**
     * Returns an instance of the PrintableDocumentParameterBean from the Spring
     * context that matches the specified document type. Basically this should
     * prepend "application_" to the <code>documentType</code> and send to
     * {@link BeanLocator#getApplicationBean(String)}.
     * 
     * @param documentType see {@link ReceiptTypeConstantsIfc} and {@link ReportTypeConstantsIfc}
     * @return An instance of the ReceiptParameterBeanIfc.
     */
    public PrintableDocumentParameterBeanIfc getParameterBeanInstance(String documentType);

    /**
     * Returns an instance of the ReceiptParameterBean initialized for the given
     * transaciton.
     * 
     * @param bus
     * @param transaction
     * @return An instance of the ReceiptParameterBean initialized for the given
     *         transaciton.
     * @throws ParameterException
     */
    public ReceiptParameterBeanIfc getReceiptParameterBeanInstance(SessionBusIfc bus,
            TenderableTransactionIfc transaction) throws ParameterException;

    /**
     * Creates an instance of the SendGiftReceiptParameterBeanIfc.
     * 
     * @param bus
     * @param receiptParameters
     * @return An instance of the GiftReceiptParameterBeanIfc.
     */
    public GiftReceiptParameterBeanIfc getGiftReceiptParameterBeanInstance(SessionBusIfc bus,
            ReceiptParameterBeanIfc receiptParameters);

    /**
     * Returns whether the transaction type is an Exchange.
     * 
     * @param transaction The transaction
     * @return Whether the transaction type is an Exchange.
     * @deprecated as of 13.1 see ReceiptParameterBeanIfc#getTransactionType()
     */
    public boolean isExchangeTransactionType(TenderableTransactionIfc transaction);

    /**
     * Returns an instance of the GiftReceiptParameterBeanIfc for the given
     * arguments.
     * 
     * @param bus
     * @param transaction
     * @param lineItems
     * @return An instance of the GiftReceiptParameterBeanIfc for the given
     *         arguments.
     */
    public GiftReceiptParameterBeanIfc getGiftReceiptParameterBeanInstance(SessionBusIfc bus,
            SaleReturnTransactionIfc transaction, SaleReturnLineItemIfc[] lineItems);

    /**
     * Return a array of gift receipt params grouped by items that ship together
     * 
     * @param bus
     * @param srTrans
     * @return
     */
    public GiftReceiptParameterBeanIfc[] getSendGiftReceiptParameterBeanInstance(SessionBusIfc bus,
            SaleReturnTransactionIfc srTrans);

    /**
     * Returns an instance of the OrderReceiptParameterBeanIfc.
     * 
     * @param bus
     * @param order
     * @return An instance of the OrderReceiptParameterBeanIfc.
     * @throws ParameterException
     */
    public OrderReceiptParameterBeanIfc getOrderReceiptParameterBeanInstance(SessionBusIfc bus, OrderIfc order)
            throws ParameterException;

    /**
     * Method for returning the instance of the PrintableDocumentFactoryIfc.
     * 
     * @return An instance of the PrintableDocumentFactoryIfc.
     * @deprecated as of 13.1 since blueprints are used instead of printable documents.
     */
    public PrintableDocumentFactoryIfc getPrintableDocumentFactory();

    /**
     * Method to return an instance of the Report Summary.
     * 
     * @return An instance of the Report Summary.
     * @deprecated as of 13.1 use {@link #getParameterBeanInstance(String)} instead.
     */
    public SummaryReport getSummaryReportInstance();
}
