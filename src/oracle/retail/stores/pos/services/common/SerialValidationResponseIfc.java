/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/SerialValidationResponseIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:54 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    abondala  01/03/10 - update header date
 *    nkgautam  12/15/09 - Response classIfc for Serial Validation tour
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Base Interface for serial validation
 * response object
 * @author nkgautam
 *
 */
public interface SerialValidationResponseIfc extends Serializable
{
    /**
     * Gets the response map.
     * @return
     */
    public HashMap<String, String> getResponseMap();
    /**
     * Sets the Response Map
     * @param responseMap
     */
    public void setResponseMap(HashMap<String, String> responseMap);
    /**
     *
     * @return list of items in response
     */
    public ArrayList getCompleteItemListFromResponse();

    /**
     *
     * @return list of sellable items in response
     */
    public ArrayList getSellableItemListFromResponse();

}
