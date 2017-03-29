/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifytransaction/retrieve/SetTransactionSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:30 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:29:57 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:25:15 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:14:12 PM  Robert Pearse
 *
 *   Revision 1.7  2004/06/03 14:47:43  epd
 *   @scr 5368 Update to use of DataTransactionFactory
 *
 *   Revision 1.6  2004/04/20 13:17:06  tmorris
 *   @scr 4332 -Sorted imports
 *
 *   Revision 1.5  2004/04/14 15:17:10  pkillick
 *   @scr 4332 -Replaced direct instantiation(new) with Factory call.
 *
 *   Revision 1.4  2004/02/24 16:21:29  cdb
 *   @scr 0 Remove Deprecation warnings. Cleaned code.
 *
 *   Revision 1.3  2004/02/12 16:51:12  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:51:45  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:18  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Jan 20 2004 16:25:08   DCobb
 * Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifytransaction.retrieve;

// Foundation imports
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.arts.DataTransactionFactory;
import oracle.retail.stores.domain.arts.DataTransactionKeys;
import oracle.retail.stores.domain.arts.TransactionReadDataTransaction;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionSummaryIfc;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;

//------------------------------------------------------------------------------
/**
    Sets the selected suspended transaction in the cargo.<P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public class SetTransactionSite extends PosSiteActionAdapter
{                                       // begin class SetTransactionSite

    /**
       revision number supplied by source-code control system
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
       site name constant
    **/
    public static final String SITENAME = "SetTransactionSite";

    //--------------------------------------------------------------------------
    /**
       Sets the selected suspended transaction in the cargo. <P>
       @param bus the bus arriving at this site
    **/
    //--------------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {                                   // begin arrive()
        String letterName = CommonLetterIfc.CONTINUE;

        // get utility manager
        UtilityManagerIfc utility =
            (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);


        // get transaction ID from cargo
        ModifyTransactionRetrieveCargo cargo = (ModifyTransactionRetrieveCargo) bus.getCargo();
        TransactionSummaryIfc selected = cargo.getSelectedSummary();
        String transactionID = selected.getTransactionID().getTransactionIDString();

        // initialize the transaction
        TransactionIfc searchTransaction =
          DomainGateway.getFactory().getTransactionInstance();
        searchTransaction.initialize(transactionID);
        searchTransaction.setBusinessDay(selected.getBusinessDate());
        searchTransaction.setTransactionStatus(TransactionIfc.STATUS_SUSPENDED);
        searchTransaction.setLocaleRequestor(utility.getRequestLocales());

        //just to make sure operator can't cancel real trans. while being in training mode.
        boolean trainingModeOn = cargo.getRegister().getWorkstation().isTrainingMode();
        searchTransaction.setTrainingMode(trainingModeOn);

        // read transaction from database and set in the cargo
        try
        {
            TransactionReadDataTransaction readTransaction = null;

            readTransaction = (TransactionReadDataTransaction) DataTransactionFactory.create(DataTransactionKeys.TRANSACTION_READ_DATA_TRANSACTION);

            RetailTransactionIfc retrieveTransaction =
                (RetailTransactionIfc) readTransaction.readTransaction
                  (searchTransaction);
            cargo.setTransaction(retrieveTransaction);
        }
        catch (DataException e)
        {
            cargo.setDataExceptionErrorCode(e.getErrorCode());
            letterName = CommonLetterIfc.DB_ERROR;
        }

        bus.mail(new Letter(letterName), BusIfc.CURRENT);
    }                                   // end arrive()


}                                       // end class SetTransactionSite


