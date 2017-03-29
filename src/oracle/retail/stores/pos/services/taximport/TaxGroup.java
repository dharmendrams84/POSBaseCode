/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/taximport/TaxGroup.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:10 mszekely Exp $
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
    This class serves as a Tax Group container. <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @deprecated as of 13.3. Tax import is done through DIMP.
 */
//-------------------------------------------------------------------------
public class TaxGroup extends TaxBaseObject
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -2359654645108118577L;


    /**
      revision number of this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    private ArrayList itemList;


    /**
     * Class constructor.
     */
    public TaxGroup()
    {
    }

    /**
     *  Sets the object id.
     *  @param String object id.
     */
    public TaxGroup(String id)
    {
        super(id);
    }

    /**
     *  Gets the Items.
     *  @return ArrayList containing Item objects.
     */
    public ArrayList getItems()
    {
        return itemList;
    }


    /**
     *  Sets the Items.
     *  @param ArrayList containing Item objects.
     */
    public synchronized void setItems(ArrayList l)
    {
        itemList = l;
    }


}

