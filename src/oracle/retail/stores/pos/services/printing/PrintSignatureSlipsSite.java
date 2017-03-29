/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/printing/PrintSignatureSlipsSite.java /rgbustores_13.4x_generic_branch/6 2011/10/06 15:53:42 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    asinton   10/06/11 - print signature slip when credit disclosure data is
 *                         present.
 *    rrkohli   08/19/11 - fix to print the bank copy in tender check flow
 *    blarsen   08/11/11 - Added support for scaled-down sig cap images to
 *                         match parameters.
 *    jswan     06/22/11 - Modified to support signature capture in APF.
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    asinton   09/20/10 - Changes to incorporate new MinimumSigCapFor+<credit
 *                         type> parameter.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    vapartha  01/28/10 - Added code to write space in the signature image
 *                         files when deletion fails.
 *    abondala  01/03/10 - update header date
 *    djenning  03/24/09 - if parameter is set to print signature or if
 *                         signature hasn't been captured, then print signature
 *                         slip. only skip the step if the parameter is set to
 *                         'no' and we have the signature.
 *    cgreene   03/16/09 - add code to save the sigcap images for printing
 *    cgreene   11/17/08 - * @deprecated as of 13.1 see
 *                         ReceiptParameterBeanIfc#getTransactionType()
 *    cgreene   11/13/08 - configure print beans into Spring context
 *
 * ===========================================================================
 * $Log:
 *    5    360Commerce 1.4         8/3/2007 10:29:47 AM   Michael P. Barnett
 *         Removed code to set expiration date to "N/A" since that was causing
 *          an SQL error.
 *    4    360Commerce 1.3         7/20/2007 10:41:01 AM  Ashok.Mondal    CR
 *         27722 :Store copy receipt will show the expiration date as N/A for
 *         House Card credit when the HouseCardExpiration parameter is set to
 *         No.
 *    3    360Commerce 1.2         3/31/2005 4:29:31 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:24:25 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:13:27 PM  Robert Pearse
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
 *    Rev 1.5   Feb 10 2004 14:41:28   bwf
 * Refactor Echeck.
 *
 *    Rev 1.4   Jan 08 2004 15:08:20   DCobb
 * Changed printer offline behavior to proceed.
 * Resolution for 3502: Remove "Printer Offline Behavior" parameter
 *
 *    Rev 1.3   Jan 07 2004 18:09:32   nrao
 * Fix for SCR 3652. Checked for null value.
 *
 *    Rev 1.2   Dec 08 2003 16:48:36   bwf
 * Update per code review.
 *
 *    Rev 1.1   Nov 25 2003 15:33:20   bwf
 * Added echeck printing.
 * Resolution for 3429: Check/ECheck Tender
 *
 *    Rev 1.0   Aug 29 2003 16:05:32   CSchellenger
 * Initial revision.
 *
 *    Rev 1.6   May 09 2003 07:54:40   KLL
 * new request made to parameter manager
 * Resolution for POS SCR-1853: Credit Signature Slip prints when the Credit Signature Slip parameter is set to NO
 *
 *    Rev 1.5   Mar 11 2003 08:49:08   KLL
 * integrating Code Review results
 * Resolution for POS SCR-2061: Printing: Manual Capture of Credit Imprint
 *
 *    Rev 1.4   Feb 26 2003 08:00:08   KLL
 * Text Update
 * Resolution for POS SCR-2061: Printing: Manual Capture of Credit Imprint
 *
 *    Rev 1.3   Feb 25 2003 09:44:46   KLL
 * check parameter in PrintSignatureSlipsASite
 * Resolution for POS SCR-2061: Printing: Manual Capture of Credit Imprint
 *
 *    Rev 1.2   Sep 16 2002 08:45:42   jriggins
 * Retrieving legal statment from the bundles.
 * Resolution for POS SCR-1740: Code base Conversions
 *
 *    Rev 1.1   24 May 2002 18:54:42   vxs
 * Removed unncessary concatenations from log statements.
 * Resolution for POS SCR-1632: Updates for Gap - Logging
 *
 *    Rev 1.0   Apr 29 2002 15:07:40   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:44:36   msg
 * Initial revision.
 *
 *    Rev 1.7   Mar 12 2002 14:09:30   mpm
 * Externalized text in receipts and documents.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.6   Mar 10 2002 18:01:10   mpm
 * Externalized text in dialog messages.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.5   25 Feb 2002 15:35:00   pdd
 * Made legal statement multi-line.
 * Resolution for POS SCR-35: Changing any Sig Slip Legal Stmt does not show on receipt
 *
 *    Rev 1.4   18 Feb 2002 10:33:58   jbp
 * DetermineTransaction Type correction.
 * Resolution for POS SCR-1333: 'Unknown' prints as transaction type on Credit Slip
 *
 *    Rev 1.3   09 Feb 2002 13:15:46   jbp
 * determine the transaction type and print the proper legal statement
 * Resolution for POS SCR-1060: ExchangeSignatureSlipLegalStmt does not print on sig slip, Sale legal stmt does
 *
 *    Rev 1.2   Feb 05 2002 16:43:10   mpm
 * Modified to use IBM BigDecimal.
 * Resolution for POS SCR-1121: Employ IBM BigDecimal
 *
 *    Rev 1.1   26 Oct 2001 14:57:34   jbp
 * Implement new reciept printing methodology
 * Resolution for POS SCR-221: Receipt Design Changes
 *
 *    Rev 1.0   Sep 21 2001 11:22:42   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:11:50   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.printing;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.lang.StringUtils;

import oracle.retail.stores.common.context.BeanLocator;
import oracle.retail.stores.common.parameter.ParameterConstantsIfc;
import oracle.retail.stores.domain.lineitem.AbstractTransactionLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.tender.AuthorizableTenderIfc;
import oracle.retail.stores.domain.tender.TenderChargeIfc;
import oracle.retail.stores.domain.tender.TenderCheckIfc;
import oracle.retail.stores.domain.tender.TenderLineItemConstantsIfc;
import oracle.retail.stores.domain.tender.TenderLineItemIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.manager.parameter.ParameterException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.service.SessionBusIfc;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.image.ImageUtilityIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.receipt.PrintableDocumentException;
import oracle.retail.stores.pos.receipt.PrintableDocumentManagerIfc;
import oracle.retail.stores.pos.receipt.ReceiptParameterBeanIfc;
import oracle.retail.stores.pos.receipt.ReceiptTypeConstantsIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

/**
 * Print credit signature slips if configured and needed.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/6 $
 */
public class PrintSignatureSlipsSite extends PosSiteActionAdapter
{
    private static final long serialVersionUID = 1L;

    public static final String SITENAME = "PrintSignatureSlipsSite";

    /**
     * If credit signature slips are configured and there are approved credit
     * tenders, print the slips for them.
     *
     * @param bus the bus arriving at this site
     */
    @Override
    public void arrive(BusIfc bus)
    {
        PrintingCargo cargo = (PrintingCargo)bus.getCargo();
        boolean sendMail = true;
        TenderableTransactionIfc trans = cargo.getTransaction();

        if (trans.getTransactionType() != TransactionIfc.TYPE_VOID)
        {
            PrintableDocumentManagerIfc printableDocumentManager = (PrintableDocumentManagerIfc)bus.getManager(PrintableDocumentManagerIfc.TYPE);
            POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
            ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);

            // Get credit tenders
            try
            {
                Boolean printECheckSignatureCopy = pm.getBooleanValue(ParameterConstantsIfc.PRINTING_PrintECheckSignatureSlips);
                Boolean printCreditSignatureCopy = pm.getBooleanValue(ParameterConstantsIfc.PRINTING_PrintCreditSignatureSlips);
                // set signature height and width for printed receipt in each
                // charge tender that has signature data
                TenderLineItemIfc[] tenders = trans.getTenderLineItems();
                for (int i = 0; i < tenders.length; i++)
                {
                    if (tenders[i] instanceof TenderCheckIfc
                            && ((TenderCheckIfc)tenders[i]).getTypeCode() == TenderLineItemConstantsIfc.TENDER_TYPE_E_CHECK)

                    {
                        TenderCheckIfc tcheck = (TenderCheckIfc)tenders[i];
                        if ((tcheck.getAuthorizationStatus() == AuthorizableTenderIfc.AUTHORIZATION_STATUS_APPROVED))
                        {
                            if (printECheckSignatureCopy.booleanValue())
                            {
                                ReceiptParameterBeanIfc bean = printableDocumentManager
                                        .getReceiptParameterBeanInstance((SessionBusIfc)bus, trans);
                                bean.setDocumentType(ReceiptTypeConstantsIfc.ECHECK_SIGNATURE);
                                bean.setTender(tcheck);
                                printableDocumentManager.printReceipt((SessionBusIfc)bus, bean);
                            }
                        } // END: If E-Check Approval Print Signature Copy Of Receipt
                    } // END: TENDER_TYPE_CHECK

                    else if (tenders[i].getTypeCode() == TenderLineItemIfc.TENDER_TYPE_CHARGE)
                    {
                        TenderChargeIfc charge = (TenderChargeIfc)tenders[i];
                    	boolean hasSignature=(charge.getSignatureData() != null);
                    	boolean hasPromotionalData = StringUtils.isNotEmpty(charge.getPromotionDescription());
                        if ((charge.isSignatureRequired() && (printCreditSignatureCopy.booleanValue() || !hasSignature)) || hasPromotionalData)
                        {
                            ReceiptParameterBeanIfc bean = printableDocumentManager.getReceiptParameterBeanInstance(
                                    (SessionBusIfc)bus, trans);
                            bean.setDocumentType(ReceiptTypeConstantsIfc.CREDIT_SIGNATURE);
                            bean.setTender(tenders[i]);
                            if (hasSignature)
                            {
                                boolean success = persistSignatureCaptureImage(bus, (Point[])charge.getSignatureData());
                                bean.setSignatureCaptureImage(success);
                                bean.setCreditSignatureLineRequired(!success);
                            }
                            // print the receipt
                            printableDocumentManager.printReceipt((SessionBusIfc)bus, bean);
                        }
                    }
                }
                trans.setTenderLineItems(tenders);
                // Update printer status
                ui.statusChanged(POSUIManagerIfc.PRINTER_STATUS, POSUIManagerIfc.ONLINE);
            }
            catch (Exception e)
            {
                logger.warn("Unable to print signature slip. " + e.getMessage());
                // Update printer status
                ui.statusChanged(POSUIManagerIfc.PRINTER_STATUS, POSUIManagerIfc.OFFLINE);

                if (e instanceof PrintableDocumentException && ((PrintableDocumentException)e).getCause() != null)
                {
                    logger.warn(Util.throwableToString(((PrintableDocumentException)e).getCause()));
                }
                else if (e instanceof ParameterException)
                {
                    logger.error("The requested parameters could not be retrieved. " + e.getMessage(), e);
                }

                String msg[] = new String[1];
                UtilityManagerIfc utility = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);
                msg[0] = utility.retrieveDialogText("RetryContinue.PrinterOffline", "Printer is offline.");

                DialogBeanModel model = new DialogBeanModel();
                model.setResourceID("RetryContinue");
                model.setType(DialogScreensIfc.RETRY_CONTINUE);
                model.setArgs(msg);
                // display dialog
                ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, model);

                sendMail = false;
            }
            finally
            {
                deleteSignatureCaptureFiles();
            }
        } // end if not trans type = void

        if (sendMail)
        {
            bus.mail(new Letter(CommonLetterIfc.CONTINUE), BusIfc.CURRENT);
        }
    }

    /**
     * Lazily creates the file-based jpg of the signature data
     * specified in <code>data</code> and returns true if successful. Uses
     * "sigcap.bmp" as the file name.
     *
     * @param data
     */
    protected boolean persistSignatureCaptureImage(BusIfc bus, Point[] data)
    {
        // get size
        try
        {
            ParameterManagerIfc pm = (ParameterManagerIfc)bus.getManager(ParameterManagerIfc.TYPE);
            int height = pm.getIntegerValue(ParameterConstantsIfc.PRINTING_SignatureBitmapHeight);
            int width = pm.getIntegerValue(ParameterConstantsIfc.PRINTING_SignatureBitmapWidth);

            ImageUtilityIfc imageUtility = (ImageUtilityIfc)BeanLocator.getServiceBean(ImageUtilityIfc.SERVICE_IMAGE);
            imageUtility.convertPoints2JpegAndSave("sigcap.jpg", width, height, data);
            return true;
        }
        catch (Exception e)
        {
            logger.error("could not create signature capture image for printing", e);
        }
        return false;
    }

    /**
     * Performs any deleting of files created by
     * {@link #persistSignatureCaptureImage(Point[])}
     */
    protected void deleteSignatureCaptureFiles()
    {
        File jpg = new File("sigcap.jpg");

        if (jpg.exists())
        {
            boolean success = jpg.delete();
            try
            {
             if (!success)
             {
             	logger.debug("Deletion of temporary signature jpg file failed. Clearing contents. ");
                 char space = ' ';
                 FileOutputStream os = new FileOutputStream(jpg);
                 os.write(space);
                 os.close();

             }
            }catch(Exception e )
            {
         	   logger.debug("Exception thrown trying to delete signature jpg file exception = " + e.getMessage());
                e.printStackTrace();
            }
        }
        else
        {
            logger.debug("sigcap.jpg file for receipt printing does not exist in bin directory");
        }
    }

    /**
     * Determines the type of transaction passed in.
     *
     * @param transaction
     * @return int Transaction Type
     * @deprecated as of 13.1 see ReceiptParameterBeanIfc#getTransactionType()
     */
    protected int determineTransType(TenderableTransactionIfc trans)
    {
        int tt = TransactionIfc.TYPE_UNKNOWN;

        // if the transaction is a sale or return transaction,
        // check to see if it really is an exchange.
        if (trans.getTransactionType() == TransactionIfc.TYPE_SALE
                || trans.getTransactionType() == TransactionIfc.TYPE_RETURN)
        {
            AbstractTransactionLineItemIfc[] lineItems = ((RetailTransactionIfc)trans).getLineItems();
            boolean saleItems = false;
            boolean returnItems = false;

            // loop through line items
            for (int i = 0; i < lineItems.length; i++)
            {
                if (((SaleReturnLineItemIfc)lineItems[i]).isReturnLineItem())
                {
                    returnItems = true;
                }
                else
                {
                    saleItems = true;
                }
            }

            // if there are sale and return line items
            // the transaction is an exchange.
            if (saleItems && returnItems)
            {
                tt = TransactionIfc.TYPE_EXCHANGE;
            }
            else if (saleItems)
            {
                tt = TransactionIfc.TYPE_SALE;
            }
            else if (returnItems)
            {
                tt = TransactionIfc.TYPE_RETURN;
            }
        }
        else
        {
            tt = trans.getTransactionType();
        }
        return tt;
    }
}
