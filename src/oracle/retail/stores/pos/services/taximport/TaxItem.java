/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/taximport/TaxItem.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:10 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.taximport;


//-------------------------------------------------------------------------
/**
    This class serves as a Tax Item container. <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @deprecated as of 13.3. Tax import is done through DIMP.
**/
//-------------------------------------------------------------------------
public class TaxItem extends TaxBaseObject {
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = 7983133138746160281L;


    /**
      revision number of this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    private String taxGroupID;
    private String description;
    private float price;

    /**
     * Class constructor.
     */
    public TaxItem()
    {
    }

    /**
     * Creates a Tax Item object and sets the object id.
     * @param String id.
     */
    public TaxItem(String id)
    {
        setID(id);
    }

    /**
     * Gets the Tax Group ID.
     * @return String Tax Group ID.
     */
    public String getTaxGroupID()
    {
        return taxGroupID;
    }

    /**
     * Gets the item description.
     * @return String item description.
     */
    public String getItemDescription()
    {
        return description;
    }

    /**
     * Gets the item price.
     * @return float item price.
     */
    public float getPrice()
    {
        return price;
    }

    /**
     * Sets the Tax Group ID.
     * @param String Tax Group ID.
     */
    public synchronized void setTaxGroupID(String s)
    {
        taxGroupID = s;
    }

    /**
     * sets the item description.
     * @param String item description.
     */
    public synchronized void setItemDescription(String s)
    {
        description = s;
    }

    /**
     * Sets the item price.
     * @param float item price.
     */
    public synchronized void setPrice(float f)
    {
        price = f;
    }

}

