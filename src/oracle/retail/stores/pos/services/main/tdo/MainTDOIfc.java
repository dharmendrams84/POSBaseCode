/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/main/tdo/MainTDOIfc.java /main/11 2011/01/27 19:03:03 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   01/27/11 - refactor creation of data transactions to use spring
 *                         context
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.main.tdo;

import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.tdo.TDOException;
import oracle.retail.stores.pos.tdo.TDOIfc;

/**
 * @author rwh
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface MainTDOIfc extends TDOIfc
{
	
	/** 
     * Spring Key used to load this bean
     */ 
    public static final String MAIN_TDO_BEAN_KEY = "application_MainTDO";
    
    /**
     * Perform application initialization operations
     * @param bus
     * @throws TDOException on error
     */
    public void initApplication(BusIfc bus) throws TDOException;
}
