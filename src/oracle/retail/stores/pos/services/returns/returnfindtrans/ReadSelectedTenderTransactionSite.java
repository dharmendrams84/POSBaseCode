/* ===========================================================================
* Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returns/returnfindtrans/ReadSelectedTenderTransactionSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:56 mszekely Exp $
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
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         11/9/2006 6:55:26 PM   Jack G. Swan
 *         Initial XMl Replication check-in.
 *    3    360Commerce 1.2         3/31/2005 4:29:33 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:24:31 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:13:33 PM  Robert Pearse
 *
 *   Revision 1.12  2004/08/23 21:04:12  jriggins
 *   @scr 6652 Added a business date reference to the readTransactionsByID() call to avoid collisions when all other transaction ID elements match
 *
 *   Revision 1.11  2004/06/03 14:47:42  epd
 *   @scr 5368 Update to use of DataTransactionFactory
 *
 *   Revision 1.10  2004/04/20 13:17:05  tmorris
 *   @scr 4332 -Sorted imports
 *
 *   Revision 1.9  2004/04/14 20:50:01  tfritz
 *   @scr 4367 - Renamed moveTransactionToOrigninal() method to moveTransactionToOriginal() method and added a call to setOriginalTransactionId() in this method.
 *
 *   Revision 1.8  2004/04/14 15:17:09  pkillick
 *   @scr 4332 -Replaced direct instantiation(new) with Factory call.
 *
 *   Revision 1.7  2004/03/03 23:15:09  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.6  2004/02/23 14:58:52  baa
 *   @scr 0 cleanup javadocs
 *
 *   Revision 1.5  2004/02/17 20:40:28  baa
 *   @scr 3561 returns
 *
 *   Revision 1.4  2004/02/13 22:46:22  baa
 *   @scr 3561 Returns - capture tender options on original trans.
 *
 *   Revision 1.3  2004/02/12 16:51:48  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:28  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:20  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.1   Dec 30 2003 16:58:06   baa
 * cleanup for return feature
 * Resolution for 3561: Feature Enhacement: Return Search by Tender
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returnfindtrans;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.TransactionReadDataTransaction;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionSummaryIfc;
import oracle.retail.stores.domain.utility.EYSDate;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;

/**
 * Reads the select return transaction from the database repository.
 */
public class ReadSelectedTenderTransactionSite extends PosSiteActionAdapter
{

    /**
     * Raw revision number string for the site.
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
     * Reads transactions from the database based on the specified/selected transaction.
     * @param bus  provides the cargo & managers to lookup the selected return transaction
     */
    public void arrive(BusIfc bus)
    {
    	//get utility manager
    	UtilityManagerIfc utility = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        ReturnFindTransCargo cargo = (ReturnFindTransCargo) bus.getCargo();

        // Lookup the transaction from the ID stored in the cargo.
        TransactionSummaryIfc summary = cargo.getSelectedTransactionSummary();
        String letterName = CommonLetterIfc.SUCCESS;

        try
        {
            boolean trainingMode = cargo.getRegister().getWorkstation().isTrainingMode();
            String transactionID = summary.getTransactionID().getTransactionIDString();
            EYSDate businessDate = summary.getBusinessDate();

            TransactionReadDataTransaction dt = null;

            dt = (TransactionReadDataTransaction) DataTransactionFactory.create(DataTransactionKeys.READ_TRANSACTIONS_FOR_RETURN);

            TransactionIfc[] transactions = dt.readTransactionsByID(transactionID, businessDate, trainingMode, utility.getRequestLocales());
            cargo.moveTransactionToOriginal((SaleReturnTransactionIfc) transactions[0]);


        }
        catch (DataException e)
        {
            logger.error( "Can't find Transaction for ID=" + summary.getTransactionID() + "");

            logger.error( "" + e + "");
            cargo.setDataExceptionErrorCode(e.getErrorCode());

            letterName = CommonLetterIfc.DB_ERROR;
        }

        bus.mail(letterName);
    }


}
