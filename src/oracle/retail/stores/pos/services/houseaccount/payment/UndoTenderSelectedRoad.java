/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/houseaccount/payment/UndoTenderSelectedRoad.java /rgbustores_13.4x_generic_branch/2 2011/07/26 16:57:40 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/26/11 - repacked into houseaccount.payment
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:30:39 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:26:31 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:15:22 PM  Robert Pearse
 *
 *   Revision 1.4  2004/09/23 00:07:13  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.3  2004/02/12 16:51:29  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:05  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:19  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.0   Aug 29 2003 16:04:04   CSchellenger
 * Initial revision.
 *
 *    Rev 1.0   Apr 29 2002 15:11:20   msg
 * Initial revision.
 *
 *    Rev 1.0   Mar 18 2002 11:42:14   msg
 * Initial revision.
 *
 *    Rev 1.0   Sep 21 2001 11:32:28   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:10:46   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.houseaccount.payment;

import oracle.retail.stores.domain.transaction.PaymentTransactionIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.ifc.LaneActionIfc;
import oracle.retail.stores.foundation.utility.LocaleMap;
import oracle.retail.stores.foundation.utility.Util;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.utility.JournalConstantsIfc;

/**
 * Process data while going from the tender service back to the
 * GetPaymentAmountSite
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public class UndoTenderSelectedRoad extends PosLaneActionAdapter implements LaneActionIfc
{
    // This id is used to tell the compiler not to generate a new serialVersionUID.
    static final long serialVersionUID = -5341869531319678877L;

    /**
     * revision number supplied by source-code control system
     */
    public static final String revisionNumber = "$KW=@(#); $Ver=pos_4.5.0:68; $EKW";
    /**
     * lane name constant
     */
    public static final String LANENAME = "UndoTenderSelectedRoad";

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.application.LaneActionAdapter#traverse(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void traverse(BusIfc bus)
    {
        //Get the user input
        PayHouseAccountCargo cargo = (PayHouseAccountCargo) bus.getCargo();
        //If a transaction number has not been designated then initialize a new transaction.
        PaymentTransactionIfc transaction = cargo.getTransaction();
        /*Operator went back from tender screen to payment entry screen. Since payment amount
        was already journalled, now have to state that it has been removed. If operator goes
        back to tender screen after entering a different amount, journalling elsewhere will
        take care of it.*/
        JournalManagerIfc journal = (JournalManagerIfc)Gateway.getDispatcher()
                                    .getManager(JournalManagerIfc.TYPE);
        StringBuffer sb = new StringBuffer();
//        sb.append(Util.EOL).append("** Payment Removed **");


        sb.append(Util.EOL)
          .append(I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.PAYMENT_REMOVED, null));



        String paymentJournalString = sb.toString()+transaction.journalLineItems(LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL))+sb.toString();

        if(journal != null)
        {
            journal.journal(transaction.getCashier().getLoginID(),
                            transaction.getTransactionID(),
                            paymentJournalString);
        }
        else
        {
            logger.error( "No JournalManager found");
        }
    }
}