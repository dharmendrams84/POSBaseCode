/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/register/VirtualRegisterADOFactoryIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:42 mszekely Exp $
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
package oracle.retail.stores.pos.ado.register;

import oracle.retail.stores.pos.ado.ADOException;
import oracle.retail.stores.pos.ado.factory.ADOFactoryIfc;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.financial.StoreStatusIfc;

/**
 * @author rwh
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface VirtualRegisterADOFactoryIfc extends ADOFactoryIfc
{
    /**
     * Factory method for creating a VirtualRegisterADO instance
     * 
     * @return VirtualRegisterADO
     * @throws ADOException
     */
    public VirtualRegisterADOIfc create() throws ADOException;

    /**
     * Alternate factory method for creating VirtualRegisterADO instance. This
     * method would be used to create a register when coming from a legacy
     * servier.
     * 
     * @param rdoRegister
     * @param rdoStore
     * @return VirutalRegisterADOIfc reference
     * @throws ADOException
     */
    public VirtualRegisterADOIfc create(
        RegisterIfc rdoRegister,
        StoreStatusIfc rdoStore)
        throws ADOException;
}
