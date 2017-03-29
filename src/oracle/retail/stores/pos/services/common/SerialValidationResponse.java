/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/SerialValidationResponse.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:53 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    abondala  01/03/10 - update header date
 *    nkgautam  12/15/09 - Response class of Serial Validation tour
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Serial Validation Response Object will hold the Response information
 * @author nkgautam
 */
public class SerialValidationResponse implements SerialValidationResponseIfc, Serializable
{

    /**
     * Hashmap to contain the item number and status
     */
    protected HashMap<String, String> responseMap;

    /**
     * Gets the Hashmap
     * @return
     */
    public HashMap<String, String> getResponseMap()
    {
        return responseMap;
    }

    /**
     * Sets the Response Hashmap.
     * @param responseMap
     */
    public void setResponseMap(HashMap<String, String> responseMap)
    {
        this.responseMap = responseMap;
    }

    /**
     * @return List of items in response
     */
    public ArrayList getCompleteItemListFromResponse()
    {
        String itemId = null;
        ArrayList itemList = new ArrayList();
        Iterator it = responseMap.keySet().iterator();
        while(it.hasNext())
        {
            itemId = (String)it.next();
            itemList.add(itemId);
        }
        return itemList;
    }

    /**
     *
     * @return list of sellable items in response
     */
    public ArrayList getSellableItemListFromResponse()
    {
        String itemId = null;
        String itemStatus = null;
        ArrayList sellableList = new ArrayList();
        Iterator it = responseMap.keySet().iterator();
        while(it.hasNext())
        {
            itemId = (String)it.next();
            itemStatus = responseMap.get(itemId);
            if(itemStatus.equalsIgnoreCase("Sellable"))
            {
                sellableList.add(itemId);
            }
        }
        return sellableList;
    }

}
