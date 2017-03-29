/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/StoreStatusCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:52 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:30:14 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:37 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:32 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:48:02  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:19:59  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   08 Nov 2003 01:00:28   baa
 * cleanup -sale refactoring
 * 
 *    Rev 1.0   Nov 05 2003 23:04:58   cdb
 * Initial revision.
 * Resolution for 3430: Sale Service Refactoring
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

// foundation imports
import oracle.retail.stores.domain.financial.StoreStatusIfc;
import oracle.retail.stores.pos.services.dailyoperations.common.SetupRegisterCargoIfc;

//--------------------------------------------------------------------------
/**
    This class represents the daily ops service cargo. <>P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface StoreStatusCargoIfc extends AbstractFinancialCargoIfc, SetupRegisterCargoIfc
{


    //----------------------------------------------------------------------
    /**
        Returns the list of store statuses. <P>
        @return The list of store statuses.
    **/
    //----------------------------------------------------------------------
    public StoreStatusIfc[] getStoreStatusList();

    //----------------------------------------------------------------------
    /**
        Sets the list of store statuses. <P>
        @param  value  The list of store statuses.
    **/
    //----------------------------------------------------------------------
    public void setStoreStatusList(StoreStatusIfc[] value);

}
