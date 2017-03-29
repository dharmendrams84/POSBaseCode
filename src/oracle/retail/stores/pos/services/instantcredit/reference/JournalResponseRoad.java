/* ===========================================================================
* Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/instantcredit/reference/JournalResponseRoad.java /rgbustores_13.4x_generic_branch/3 2011/08/09 12:06:39 sgu Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    sgu       08/09/11 - handle the case that status is null
 *    sgu       05/16/11 - move instant credit approval status to its own class
 *    sgu       05/11/11 - define approval status for instant credit as enum
 *                         type
 *    sgu       05/11/11 - fix instant credit cargo to use the new reponse
 *                         object
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    kulu      01/27/09 - minor modification based on review
 *    kulu      01/26/09 - Guard against null
 *    kulu      01/26/09 - Minor correction
 *    kulu      01/26/09 - Fix the bug that House Account enroll response data
 *                         don't have padding translation at enroll franking
 *                         slip
 *    kulu      01/22/09 - Switch to use response text instead of use status
 *                         string based on status.
 *
 * ===========================================================================
 * $Log:
 * 7    360Commerce 1.6         5/1/2008 6:08:59 PM    Sameer Thajudin Changed
 *      the applyTruncation method. A new copy of the input parameter clear is
 *       created.
 * 6    360Commerce 1.5         4/9/2008 11:40:33 AM   Kun Lu          Change
 *      according to code review of Ed Thorne
 * 5    360Commerce 1.4         4/8/2008 3:49:15 PM    Kun Lu          Utilize
 *      EncipheredCardData to get the trancated account number
 * 4    360Commerce 1.3         4/4/2008 6:08:08 PM    Kun Lu          The
 *      account number should be converted to String and truncated
 * 3    360Commerce 1.2         3/31/2005 4:28:48 PM   Robert Pearse
 * 2    360Commerce 1.1         3/10/2005 10:22:58 AM  Robert Pearse
 * 1    360Commerce 1.0         2/11/2005 12:12:11 PM  Robert Pearse
 *
 *Revision 1.3  2004/02/12 16:50:45  mcs
 *Forcing head revision
 *
 *Revision 1.2  2004/02/11 21:51:22  rhafernik
 *@scr 0 Log4J conversion and code cleanup
 *
 *Revision 1.1.1.1  2004/02/11 01:04:17  cschellenger
 *updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.1   Nov 24 2003 19:56:36   nrao
 * Code Review Changes.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.instantcredit.reference;

// foundation imports
import oracle.retail.stores.common.utility.Util;
import oracle.retail.stores.domain.manager.payment.AuthorizeInstantCreditResponseIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.domain.utility.InstantCreditApprovalStatus;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.device.EncipheredCardData;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.manager.utility.UtilityManager;
import oracle.retail.stores.pos.services.PosLaneActionAdapter;
import oracle.retail.stores.pos.services.instantcredit.InstantCreditCargo;
import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.utility.JournalConstantsIfc;


//------------------------------------------------------------------------------
/**

    @version $Revision: /rgbustores_13.4x_generic_branch/3 $
**/
//------------------------------------------------------------------------------

public class JournalResponseRoad extends PosLaneActionAdapter
{
    //--------------------------------------------------------------------------
    /**
            @param bus the bus traversing this lane
    **/
    //--------------------------------------------------------------------------

    public void traverse(BusIfc bus)
    {
        InstantCreditCargo    cargo       = (InstantCreditCargo) bus.getCargo();
        AuthorizeInstantCreditResponseIfc             response    = cargo.getInstantCreditResponse();
        UtilityManagerIfc utility = (UtilityManager) bus.getManager(UtilityManagerIfc.TYPE);

        //String s = "\nInstant Credit Enroll";
        StringBuffer sb = new StringBuffer();
        Object[] dataArgs = new Object[1];
        sb.append(Util.EOL  + I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.INSTANT_CREDIT_ENROLL_LABEL, null));

        InstantCreditApprovalStatus status = response.getApprovalStatus();
        if (InstantCreditApprovalStatus.APPROVED.equals(status))
        {
        	dataArgs[0] = applyTruncation(response.getAccountNumber().getBytes());
        	sb.append(Util.EOL  + I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
					JournalConstantsIfc.ACCOUNT_NUMBER_LABEL, dataArgs));
        	//s += "\nAccount Number: " + applyTruncation(response.getAccountNumber());
        }

        dataArgs[0] = "";
        if (status != null)
        {
            String approvalStatusKey = status.getResourceKey();
            dataArgs[0] = utility.retrieveCommonText(approvalStatusKey, approvalStatusKey, LocaleConstantsIfc.JOURNAL);
        }

        sb.append(Util.EOL  + I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE,
					JournalConstantsIfc.RESPONSE_LABEL, dataArgs));
        //s += "\nResponse: " + response.getApprovalStatusString(response.getApprovalStatus());


        JournalManagerIfc journal =
                            (JournalManagerIfc)
                                Gateway.getDispatcher().getManager(JournalManagerIfc.TYPE);

        TransactionIfc trans = cargo.getTransaction();

        journal.journal(trans.getCashier().getLoginID(),
                        trans.getTransactionID(), sb.toString());

    }

    /**
     *
     * @param clear - Account number in an array of bytes
     * @return truncated card number with masks
     */
    protected String applyTruncation(byte[] clear)
    {
    	String s1 = "";
    	if (clear != null)
        {
    	   	byte[] copy = new byte[clear.length];
    	   	for (int index = 0; index < copy.length - 4; index++)
    	   	{
    	   		copy[index] = (byte)EncipheredCardData.getMaskChar();
    	   	}
    	   	System.arraycopy(clear, clear.length - 4, copy, clear.length-4, 4);
    	   	s1 = new String(copy);
        }

    	return s1;

    }

}
