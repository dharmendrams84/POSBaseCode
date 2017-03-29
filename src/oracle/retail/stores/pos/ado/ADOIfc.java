/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/ADOIfc.java /rgbustores_13.4x_generic_branch/3 2011/07/28 21:02:31 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/27/11 - add generics definition
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado;

import java.io.Serializable;

import oracle.retail.stores.domain.utility.EYSDomainIfc;

/**
 * This interface defines the contract that all ADO objects
 * must implement
 */
public interface ADOIfc extends Serializable
{
    /**
     * Converts this ADO to an RDO equivalent object
     * for use in parts of the application which are not yet
     * equipped to use ADO class objects.
     * @return An RDO.
     */
    public EYSDomainIfc toLegacy();
    
    /**
     * If an ADO is composed of multiple RDO types,
     * it may make sense to request the particular type
     * that we need.
     * @param type The RDO object corresponding to the desired type.
     * @return an RDO.
     */
    public EYSDomainIfc toLegacy(Class<? extends EYSDomainIfc> type);
    
    /**
     * The given RDO is used to populate the current ADO with state
     * currently contained within the given RDO.
     * @param rdo The object from which the current ADO will be populated.
     */
    public void fromLegacy(EYSDomainIfc rdo);
    
}
