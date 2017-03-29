/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/ShippingMethodDeliveryBeanModel.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:44 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    aphulamb  11/22/08 - Checking files after code review by Naga
 *    aphulamb  11/13/08 - Check in all the files for Pickup Delivery Order
 *                         functionality
 *    aphulamb  11/13/08 - shipping method delivery bean model
 *
 * ===========================================================================
 */

package oracle.retail.stores.pos.ui.beans;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.financial.ShippingMethodConstantsIfc;
import oracle.retail.stores.domain.financial.ShippingMethodIfc;

// ----------------------------------------------------------------------------
/**
 * This is the model used to pass customer information
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
// ----------------------------------------------------------------------------
public class ShippingMethodDeliveryBeanModel extends MailBankCheckInfoDeliveryBeanModel
{
    /**
     * revision number
     */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    // list of available shipping methods
    protected ShippingMethodIfc methodsList[];

    // indicate wether the db is offline
    protected boolean offline = false;

    // Special Instructions
    protected String instructions = "";

    protected int selectedIndex = 0;

    protected String calculationType = "";

    protected CurrencyIfc shippingCharge = null;

    protected CurrencyIfc itemsCharge;

    // -------------------------------------------------------------------------
    /**
     * Get the calculation type from the parameter value
     *
     * @return the value of offline
     */
    // -------------------------------------------------------------------------
    public String getCalculationType()
    {
        return calculationType;
    }

    public void setCalculationType(String value)
    {
        calculationType = value;
    }

    // -------------------------------------------------------------------------
    /**
     * Get the calculated shipcharge
     *
     * @return the value of offline
     */
    // -------------------------------------------------------------------------
    public CurrencyIfc getItemsShippingCharge()
    {
        return itemsCharge;
    }

    // -------------------------------------------------------------------------
    /**
     * Get the value of offline
     *
     * @return the value of offline
     */
    // -------------------------------------------------------------------------
    public boolean isOffline()
    {
        return offline;
    }

    // -------------------------------------------------------------------------
    /**
     * Get the value of the Selected Shipping method
     *
     * @return the value of methodsList
     */
    // -------------------------------------------------------------------------
    public ShippingMethodIfc getSelectedShipMethod()
    {
        return methodsList[selectedIndex];
    }

    // -------------------------------------------------------------------------
    /**
     * Get the value of the Special Instructions field
     *
     * @return the value of instructions
     */
    // -------------------------------------------------------------------------
    public String getInstructions()
    {
        return instructions;
    }

    // -------------------------------------------------------------------------
    /**
     * Get the value of the shipVia field
     *
     * @return the value of shipMethod
     */
    // ----------------------------------------------------------------------------
    public ShippingMethodIfc[] getShipMethodsList()
    {
        return methodsList;
    }

    // ----------------------------------------------------------------------------
    /**
     * Get the value of the ShippingCharge field
     *
     * @return the value of shippingCharge
     */
    // ----------------------------------------------------------------------------
    public CurrencyIfc getShippingCharge()
    {

        // calculate shiping charge base on parameter
        CurrencyIfc baseCharge = methodsList[selectedIndex].getBaseShippingCharge();
        CurrencyIfc weightRate = methodsList[selectedIndex].getShippingChargeRateByWeight();
        if (!offline)
        {
            if (calculationType.compareTo(ShippingMethodConstantsIfc.FLAT_RATE) == 0)
            {
                shippingCharge = methodsList[selectedIndex].getFlatRate();
            }
            else if (calculationType.compareTo(ShippingMethodConstantsIfc.WEIGHT) == 0)
            {
                // shippingCharge = baseCharge + (itemsWeight * weightRate)
                shippingCharge = baseCharge.add(itemsCharge.multiply(weightRate));
            }
            else if (calculationType.compareTo(ShippingMethodConstantsIfc.DOLLAR_AMOUNT) == 0)
            {
                // shippingCharge = baseCharge + itemsCharge;
                shippingCharge = baseCharge.add(itemsCharge);
            }
            else
            {
                shippingCharge = DomainGateway.getBaseCurrencyInstance("0.00");
            }
        }
        else
        {
            shippingCharge = DomainGateway.getBaseCurrencyInstance("0.00");
        }
        return shippingCharge;
    }

    // -------------------------------------------------------------------------
    /**
     * Sets the itemSum shipping charge
     *
     * @param value Currency
     */
    // -------------------------------------------------------------------------
    public void setItemsShippingCharge(CurrencyIfc value)
    {
        itemsCharge = value;
    }

    // -------------------------------------------------------------------------
    /**
     * Sets offline status
     *
     * @param offline boolean
     */
    // -------------------------------------------------------------------------
    public void setOffline(boolean value)
    {
        offline = value;
    }

    // -------------------------------------------------------------------------
    /**
     * Set the index of the selected ship method
     *
     * @param int the index value
     */
    // -------------------------------------------------------------------------
    public void setSelectedShipMethod(int value)
    {
        selectedIndex = value;
    }

    // -------------------------------------------------------------------------
    /**
     * Sets the value of the Special Instructions field
     *
     * @param String instructions
     */
    // -------------------------------------------------------------------------
    public void setInstructions(String value)
    {
        instructions = value;
        methodsList[selectedIndex].setShippingInstructions(instructions);
    }

    // -------------------------------------------------------------------------
    /**
     * Sets the value of the shipVia field
     *
     * @param String shipMethod
     */
    // ----------------------------------------------------------------------------
    public void setShipMethodsList(ShippingMethodIfc value[])
    {
        methodsList = value;
    }

    // ----------------------------------------------------------------------------
    /**
     * Sets the value of the ShippingCharge field
     *
     * @param String shippingCharge
     */
    // ----------------------------------------------------------------------------
    public void setShippingCharge(CurrencyIfc value)
    {
        if (value == null)
        {
            shippingCharge = DomainGateway.getBaseCurrencyInstance("0.00");
        }
        else
        {
            shippingCharge = value;
        }
        methodsList[selectedIndex].setCalculatedShippingCharge(shippingCharge);
    }

    // ----------------------------------------------------------------------------
    /**
     * Converts to a string representing the data in this Object
     *
     * @returns string representing the data in this Object
     */
    // ----------------------------------------------------------------------------
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("Class: ShippingMethodBeanModel Revision: " + revisionNumber + "\n");
        if (methodsList != null && selectedIndex < methodsList.length
                && methodsList[selectedIndex].getBaseShippingCharge() != null)
        {
            buff.append("ShippingCharge [" + methodsList[selectedIndex].getBaseShippingCharge().toString() + "]\n");
        }
        else
        {
            buff.append("ShippingCharge [null]\n");
        }
        buff.append("Offline [" + offline + "]\n");
        buff.append("Special Instructions[" + instructions + "]\n");
        return (buff.toString());
    }
}
