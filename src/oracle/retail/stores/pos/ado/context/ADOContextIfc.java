/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/context/ADOContextIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:42 mszekely Exp $
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
package oracle.retail.stores.pos.ado.context;

import oracle.retail.stores.pos.ado.store.RegisterADO;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.tour.manager.ManagerIfc;

/**
 * Defines the public contract for all ADO objects that need a context.
 */
public interface ADOContextIfc
{
    /**
     * Attempts to retrieve the desired manager.
     * 
     * @param managerType
     *            The manager type to retrieve.
     * @return A reference to the desired manager.
     */
    public ManagerIfc getManager(String managerType);

    /**
     * Each application has it's own ID String. This returns the application ID
     * for this context.
     * 
     * @return
     */
    public String getApplicationID();

    /**
     * Each application has it's own ID String. This sets the application ID
     * for this context.
     * 
     * @param ID
     */
    public void setApplicationID(String ID);

    /**
     * Returns a reference to the bus in the context
     * 
     * @return reference to bus
     */
    public BusIfc getBus();
    
    /**
     * Gets a configured RegisterADO instance
     * @return
     */
    public RegisterADO getRegisterADO();
}
