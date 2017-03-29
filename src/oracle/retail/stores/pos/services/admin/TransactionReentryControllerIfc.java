/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/admin/TransactionReentryControllerIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:08 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *  1    360Commerce 1.0         11/22/2007 10:57:23 PM Naveen Ganesh   
 * $
 * Revision 1.1  2004/03/24 20:09:54  bjosserand
 * @scr 4093 Transaction Reentry
 *
 * Revision 1.1  2004/03/21 19:24:47  bjosserand
 * @scr 4093 Transaction Reentry
 *
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.admin;

import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;

/**
 * Create a journal entry for turning transaction reentry on or off.
 * 
 * @param bus
 * @param reentryMode
 * @param operator
 */
public interface TransactionReentryControllerIfc
{
    public void journalTransaction(BusIfc bus, boolean reentryMode, EmployeeIfc operator);
}
