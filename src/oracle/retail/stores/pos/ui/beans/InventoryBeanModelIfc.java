/* ===========================================================================
* Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/InventoryBeanModelIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:58 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/28/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    1    360Commerce 1.0         11/27/2006 5:37:44 PM  Charles D. Baker 
 *
 *   Revision 1.1.2.1  2004/10/18 15:08:27  kmcbride
 *   Adding files from trunk as part of merge
 *
 *   Revision 1.1  2004/10/12 18:53:52  mweis
 *   @scr 7012 Consolodate inventory UI model work under InventoryBeanModelIfc.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import java.util.Vector;

//--------------------------------------------------------------------------
/**
    Interface for UI bean models that need to carry inventory-related information. 

    $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface InventoryBeanModelIfc
{
    /**
     * Sets the available inventory location ids.
     * The vector is a set of Integer objects.
     * <p>
     * The order of the names is expected to match the order 
     * of the {@link #setInvLocationNames(Vector) location names}.
     * 
     * @param vectorOfIntegers Vector of Integer inventory location ids.
     */
    public void setInvLocationIds(Vector vectorOfIntegers);
    
    /**
     * Returns the available inventory location ids.
     * The vector is a set of Integer objects.
     * 
     * @return The available inventory location ids.
     */
    public Vector getInvLocationIds();
    
    
    /**
     * Sets the inventory location id that is, or has been, chosen.
     * 
     * @param locationId The selected inventory location id.
     */
    public void setSelectedInvLocationId(Integer locationId);
    
    /**
     * Returns the selected inventory location id.
     * 
     * @return The selected inventory location id.
     */
    public Integer getSelectedInvLocationId();
    

    /**
     * Set the names of the available inventory locations.
     * The vector is a set of String objects.
     * <p>
     * The order of the names is expected to match the order 
     * of the {@link #setInvLocationId(Vector) location ids}.
     * 
     * @param vectorOfStrings Names of the inventory locations. 
     */
    public void setInvLocationNames(Vector vectorOfStrings);
    
    /**
     * Returns the names of the available inventory locations.
     * The vector is a set of String objects.
     * 
     * @return The names of the available inventory locations.
     */
    public Vector getInvLocationNames();
}
