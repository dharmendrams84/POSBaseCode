/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/taximport/TaxAuthority.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:10 mszekely Exp $
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

import java.util.ArrayList;

//-------------------------------------------------------------------------
/**
    This class serves as a Tax Authority container. <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @deprecated as of 13.3. Tax import is done through DIMP.
**/
//-------------------------------------------------------------------------
public class TaxAuthority extends TaxBaseObject {
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -1339117069206362913L;


    /**
      revision number of this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    private ArrayList taxGroupList;
    private ArrayList postalCodes;

    /**
     * Class constructor.
     */
    public TaxAuthority()
    {
    }

    /**
     * Creates a Tax Authority object and sets the object id.
     * @param String id.
     */
    public TaxAuthority(String id)
    {
        setID(id);
    }

    /**
     * Gets the Associated Postal Codes for this Tax Authority.
     * @return ArrayList contains postal codes.
     */
    public ArrayList getPostalCodes()
    {
        return postalCodes;
    }

    /**
     * Gets the Tax Groups.
     * @return ArrayList contains Tax Group objects.
     */
    public ArrayList getTaxGroupRules()
    {
        return taxGroupList;
    }

    /**
     * Sets the Postal Codes for this Tax Authority.
     * @param ArrayList contains postal codes.
     */
    public synchronized void setPostalCodes(ArrayList l)
    {
        postalCodes = l;
    }

    /**
     * Sets the Tax Groups.
     * @param ArrayList contains Tax Group objects.
     */
    public synchronized void setTaxGroupRules(ArrayList l)
    {
        taxGroupList = l;
    }

}

