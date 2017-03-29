/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/tender/ChosenTenderLaunchShuttle.java /rgbustores_13.4x_generic_branch/2 2011/08/29 10:56:04 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   08/29/11 - implement new access point function for house
 *                         account
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:27 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:15 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:00 PM  Robert Pearse   
 *
 *   Revision 1.5  2004/07/02 17:03:08  khassen
 *   @scr 5642 - checking transaction for customer info.
 *
 *   Revision 1.4  2004/06/15 22:57:00  bwf
 *   @scr 5000 Check to see if customer was captured before asking again.
 *
 *   Revision 1.3  2004/04/06 20:22:50  epd
 *   @scr 4263 Updates to move instant credit enroll to sub tour
 *
 *   Revision 1.2  2004/04/02 21:14:46  epd
 *   @scr 4263 Moved Money Order tender into new station
 *
 *   Revision 1.1  2004/04/02 20:17:27  epd
 *   @scr 4263 Refactored coupon tender into sub service
 *
 *   
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.tender;

import oracle.retail.stores.pos.ado.context.ContextFactory;
import oracle.retail.stores.pos.ado.context.TourADOContext;
import oracle.retail.stores.pos.services.common.FinancialCargoShuttle;
import oracle.retail.stores.domain.employee.RoleFunctionIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;

/**
 * Copy the chosen tender type and amount to the child service
 */
public class ChosenTenderLaunchShuttle extends FinancialCargoShuttle
{
    private static final long serialVersionUID = -5449669580695590508L;

    /** Service name of instant credit tour that requires function point access. */
    public static final String TENDER_INSTANTCREDIT = "TenderInstantCredit";

    protected TenderCargo callingCargo;

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#load(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    public void load(BusIfc bus)
    {
        super.load(bus);
        callingCargo = (TenderCargo)bus.getCargo();
    }

    /* (non-Javadoc)
     * @see oracle.retail.stores.foundation.tour.ifc.ShuttleIfc#unload(oracle.retail.stores.foundation.tour.ifc.BusIfc)
     */
    @Override
    public void unload(BusIfc bus)
    {
        super.unload(bus);
        TenderCargo childCargo = (TenderCargo)bus.getCargo();
        childCargo.setOperator(callingCargo.getOperator());
        childCargo.setCurrentTransactionADO(callingCargo.getCurrentTransactionADO());
        childCargo.setTenderAttributes(callingCargo.getTenderAttributes());
        if (callingCargo.getCustomer() != null)
        {
            childCargo.setCustomer(callingCargo.getCustomer());
        }
        else if ((callingCargo.getTransaction() != null) && (callingCargo.getTransaction().getCustomer() != null))
        {
            childCargo.setCustomer(callingCargo.getTransaction().getCustomer());
        }

        if (getInstantCreditTenderTourName().equals(bus.getServiceName()))
        {
            childCargo.setAccessFunctionID(RoleFunctionIfc.HOUSE_ACCOUNT);
        }

        // Reset ADO context for calling service
        TourADOContext context = new TourADOContext(bus);
        context.setApplicationID(childCargo.getAppID());
        ContextFactory.getInstance().setContext(context);
    }

    /**
     * Return the name of the tour that is traveled for creating "instant
     * credit" for the customer that wishes to apply for a new house account.
     * 
     * @return
     */
    protected String getInstantCreditTenderTourName()
    {
        return TENDER_INSTANTCREDIT;
    }

}