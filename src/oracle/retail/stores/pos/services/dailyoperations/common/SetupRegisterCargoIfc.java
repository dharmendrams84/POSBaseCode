/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/dailyoperations/common/SetupRegisterCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:16 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/27/10 - XbranchMerge cgreene_refactor-duplicate-pos-classes
 *                         from st_rgbustores_techissueseatel_generic_branch
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:29:58 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:16 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:12 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:49:36  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:40:02  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:15  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Nov 03 2003 15:11:06   cdb
 * Initial revision.
 * Resolution for 3430: Sale Service Refactoring
 * 
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.dailyoperations.common;

// foundation imports
import oracle.retail.stores.pos.services.common.DBErrorCargoIfc;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.financial.StoreStatusIfc;

//-------------------------------------------------------------------------
/**
 * This interface defines methods used when a cargo works with an db errors.
 * <P>
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 **/
// -------------------------------------------------------------------------
public interface SetupRegisterCargoIfc extends DBErrorCargoIfc
{
    /**
     * Returns the list of store statuses.
     * <P>
     * 
     * @return The list of store statuses.
     **/
    public StoreStatusIfc[] getStoreStatusList();

    /**
     * Sets the list of store statuses.
     * <P>
     * 
     * @param value The list of store statuses.
     **/
    public void setStoreStatusList(StoreStatusIfc[] value);

    /**
     * Returns the store status.
     * <P>
     * 
     * @return The store status.
     **/
    public StoreStatusIfc getStoreStatus();

    /**
     * Sets the store status.
     * <P>
     * 
     * @param value The store status.
     **/
    public void setStoreStatus(StoreStatusIfc value);

    /**
     * Returns the register object.
     * <P>
     * 
     * @return The register object.
     **/
    public RegisterIfc getRegister();

    /**
     * Sets the register object.
     * <P>
     * 
     * @param value The register object.
     **/
    public void setRegister(RegisterIfc value);

}
