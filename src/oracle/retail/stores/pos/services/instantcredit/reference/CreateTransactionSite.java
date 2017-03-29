/* ===========================================================================
* Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/instantcredit/reference/CreateTransactionSite.java /rgbustores_13.4x_generic_branch/2 2011/06/03 10:23:37 sgu Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    sgu       05/20/11 - refactor instant credit inquiry flow
 *    cgreene   02/15/11 - move constants into interfaces and refactor
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 * 3    360Commerce 1.2         3/31/2005 4:27:32 PM   Robert Pearse
 * 2    360Commerce 1.1         3/10/2005 10:20:26 AM  Robert Pearse
 * 1    360Commerce 1.0         2/11/2005 12:10:14 PM  Robert Pearse
 *
 *Revision 1.3  2004/02/12 16:50:44  mcs
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
 *    Rev 1.1   Nov 24 2003 19:53:04   nrao
 * Changed copyright message.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.instantcredit.reference;

import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.transaction.InstantCreditTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.application.SiteActionAdapter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.pos.services.instantcredit.InstantCreditCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.InstantCreditInquiryCriteriaBeanModel;

/**
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public class CreateTransactionSite extends SiteActionAdapter
{
    private static final long serialVersionUID = 7896473803256079547L;

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.application.SiteActionAdapter#arrive(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void arrive(BusIfc bus)
    {
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);
        InstantCreditCargo cargo = (InstantCreditCargo) bus.getCargo();

        // get the inputs from the ui and set in cargo
        InstantCreditInquiryCriteriaBeanModel model = (InstantCreditInquiryCriteriaBeanModel)ui.getModel(POSUIManagerIfc.INSTANT_CREDIT_INQUIRY_CRITERIA);
        cargo.setReferenceNumber(model.getReferenceNumber());
        cargo.setSsn(model.getSsn());
        cargo.setHomePhone(model.getHomePhone());
        cargo.setZipCode(model.getPostalCode());

        // use the utility manager to initialize a transaction
        UtilityManagerIfc utility = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        InstantCreditTransactionIfc trans = DomainGateway.getFactory().getInstantCreditTransactionInstance();
        utility.initializeTransaction(trans, bus, -1);
        trans.setTransactionType(TransactionIfc.TYPE_INSTANT_CREDIT_ENROLLMENT);
        cargo.setTransaction(trans);

        bus.mail(new Letter(CommonLetterIfc.CONTINUE), BusIfc.CURRENT);
    }
}
