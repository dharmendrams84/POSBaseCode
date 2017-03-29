/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returns/returncustomer/ReadSelectedTransactionSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:55 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *    mahising  03/06/09 - fixed issue for suspended transaction
 *    mahising  02/17/09 - fixed personal id business customer issue
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         11/9/2006 6:55:26 PM   Jack G. Swan
 *         Initial XMl Replication check-in.
 *    3    360Commerce 1.2         3/31/2005 4:29:34 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:24:31 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:13:33 PM  Robert Pearse
 *
 *   Revision 1.9  2004/06/03 14:47:45  epd
 *   @scr 5368 Update to use of DataTransactionFactory
 *
 *   Revision 1.8  2004/04/20 13:17:06  tmorris
 *   @scr 4332 -Sorted imports
 *
 *   Revision 1.7  2004/04/14 20:50:01  tfritz
 *   @scr 4367 - Renamed moveTransactionToOrigninal() method to moveTransactionToOriginal() method and added a call to setOriginalTransactionId() in this method.
 *
 *   Revision 1.6  2004/04/14 15:17:10  pkillick
 *   @scr 4332 -Replaced direct instantiation(new) with Factory call.
 *
 *   Revision 1.5  2004/03/10 14:16:46  baa
 *   @scr 0 fix javadoc warnings
 *
 *   Revision 1.4  2004/03/03 23:15:16  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.3  2004/02/12 16:51:47  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:29  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:20  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:05:56   CSchellenger
 * Initial revision.
 *
 *    Rev 1.2   Apr 08 2003 10:23:42   bwf
 * Deprecation Fix
 * Resolution for 2103: Remove uses of deprecated items in POS.
 *
 *    Rev 1.1   Feb 24 2003 13:31:56   RSachdeva
 * Database Internationalization
 * Resolution for POS SCR-1866: I18n Database  support
 *
 *    Rev 1.0   Apr 29 2002 15:06:18   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:45:32   msg
 * Initial revision.
 *
 *    Rev 1.0   Sep 21 2001 11:24:46   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:12:34   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returncustomer;

//java imports
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.TransactionReadDataTransaction;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionSummaryIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.returns.returncommon.NoTransactionsErrorSite;
import oracle.retail.stores.pos.ui.DialogScreensIfc;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DialogBeanModel;

//--------------------------------------------------------------------------
/**
    This site reads transactions from the DB based on the entered Trans
    number/id.
    <p>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class ReadSelectedTransactionSite extends NoTransactionsErrorSite
{

    /**
       revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
       Reads transactions from the DB based on the entered Trans
       number/id.
       <P>
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        // flag for no data found
        boolean noData = false;
        // flag for invalid transaction
        boolean invalidTrans = false;

        // get utility manager
        UtilityManagerIfc utility = (UtilityManagerIfc)bus.getManager(UtilityManagerIfc.TYPE);

        ReturnCustomerCargo cargo = (ReturnCustomerCargo)bus.getCargo();

        /*
         * Lookup the transaction from the ID stored in the cargo.
         */
        TransactionSummaryIfc[] summaries = cargo.getTransactionSummary();
        TransactionSummaryIfc summary = summaries[cargo.getSelectedIndex()];
        Letter letter = new Letter(CommonLetterIfc.SUCCESS);

        try
        {
            String transactionID = summary.getTransactionID().getTransactionIDString();
            boolean trainingMode = cargo.getRegister().getWorkstation().isTrainingMode();

            TransactionReadDataTransaction dt = null;

            dt = (TransactionReadDataTransaction)DataTransactionFactory
                    .create(DataTransactionKeys.READ_TRANSACTIONS_FOR_RETURN);

            TransactionIfc[] transactions = dt.readTransactionsByID(transactionID, null, trainingMode, utility
                    .getRequestLocales());
            cargo.moveTransactionToOriginal((SaleReturnTransactionIfc)transactions[0]);
            cargo.displayCustomer(bus);
        }
        catch (DataException e)
        {
            logger.error("Can't find Transaction for ID=" + summary.getTransactionID() + "");
            logger.error("" + e + "");
            if (e.getErrorCode() == DataException.NO_DATA)
            {
                noData = true;
            }
            else
            {
                cargo.setDataExceptionErrorCode(e.getErrorCode());
                letter = new Letter(CommonLetterIfc.DB_ERROR);
            }

        }

        catch (IllegalArgumentException iae)
        {
            noData = true;
            invalidTrans = true;
        }

        if (noData)
        {
            if (invalidTrans)
            {
                displayInvalidTransactionNoSellItems(bus);
            }
            else
            {
                displayNoTransactionsForNumber(bus);
                cargo.setTransactionFound(false);
            }
        }

        else
        {
            bus.mail(letter, BusIfc.CURRENT);
        }

    }
  
    // ----------------------------------------------------------------------
    /**
     * Show the NO TRANSACTIONS FOR NUMBER ERROR SCREEN
     * <P>
     * 
     * @param bus Service Bus
     **/
    // ----------------------------------------------------------------------
    public void displayNoTransactionsForNumber(BusIfc bus)
    {
        // Get the ui manager
        POSUIManagerIfc ui = (POSUIManagerIfc)bus.getManager(UIManagerIfc.TYPE);
        DialogBeanModel dialogModel = new DialogBeanModel();
        dialogModel.setResourceID("RetrieveTransactionNotFound");
        dialogModel.setType(DialogScreensIfc.CONFIRMATION);
        dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_YES, "Retry");
        dialogModel.setButtonLetter(DialogScreensIfc.BUTTON_NO, "ReturnItem");
        // display the screen
        ui.showScreen(POSUIManagerIfc.DIALOG_TEMPLATE, dialogModel);
    }
     
    //----------------------------------------------------------------------
    /**
       Returns the revision number of the class.
       <P>
       @return String representation of revision number
    **/
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return(revisionNumber);
    }
}
