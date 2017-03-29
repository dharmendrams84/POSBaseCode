/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/modifyitem/tax/ItemTaxModControllerIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:24 mszekely Exp $
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
 *  3    360Commerce 1.2         3/31/2005 4:28:34 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:22:33 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:11:42 PM  Robert Pearse   
 * $
 * Revision 1.9  2004/05/07 20:09:09  dcobb
 * @scr 4654 Added On/Off with Ineligible Items alternate flow.
 *
 * Revision 1.8  2004/05/07 01:51:57  dcobb
 * @scr 4702 Tax Override - When selecting multiple items and some but not all are non-taxable, the wrong message appears
 *
 * Revision 1.7  2004/03/16 18:30:41  cdb
 * @scr 0 Removed tabs from all java source code.
 *
 * Revision 1.6  2004/03/11 23:10:27  bjosserand
 * @scr 3954 Tax Override
 *
 * Revision 1.5  2004/03/09 21:46:08  bjosserand
 * @scr 3954 Tax Override
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.modifyitem.tax;

import java.io.Serializable;

import oracle.retail.stores.domain.lineitem.TaxableLineItemIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;

/**
 * @author bjosserand
 * 
 * This interface defines methods used to control the processes of tax override.
 */
public interface ItemTaxModControllerIfc extends Serializable
{
    /**
     * Determines whether any items invalid for override are in the array. If the list consists of 
     * a single non-taxable item, a TaxErrprExce[topm os thrown, otherwise if the list contains any 
     * items invalid for tax override, a TaxWarningException is thrown.
     * 
     * @param items
     * @throws TaxErrorException
     * @throws TaxWarningException
     */
    public void validateItemsForOverride(SaleReturnLineItemIfc[] items) throws TaxErrorException, TaxWarningException;
    /**
     * Validate whether any items are invalid for tax toggle
     * 
     * @param items  The item selection list
     * @throws TaxWarningException
     */
    public void validateItemsForToggle(SaleReturnLineItemIfc[] items) throws TaxWarningException;
    /**
     * Determines if any of the items in the array have been already tax overridden (for this transaction). If so,
     * gives TaxWarningException.
     * 
     * @param items
     * @throws TaxWarningException
     */
    public void validateOverride(TaxableLineItemIfc[] items) throws TaxWarningException;
    /**
     * Determines if the entered tax rate is valid. Gives TaxErrorException if not valid.
     * 
     * @param bus
     * @throws TaxErrorException
     */
    public void validateTaxRate(BusIfc bus) throws TaxErrorException;
    /**
     * Determines if the entered tax amount is valid. Gives TaxErrorException if not valid.
     * 
     * @param bus
     * @throws TaxErrorException
     */
    public void validateTaxAmount(BusIfc bus) throws TaxErrorException;
    /**
     * Final processing for the user entered tax rate.
     * 
     * @param bus
     */
    public void processTaxRate(BusIfc bus);
    /**
     * Final processing for the user entered tax amount.
     * 
     * @param bus
     */
    public void processTaxAmount(BusIfc bus);
}
