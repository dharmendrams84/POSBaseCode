/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returns/returncommon/ReturnableItemCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:58 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:29:44 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:50 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:52 PM  Robert Pearse   
 *
 *   Revision 1.5  2004/03/15 15:16:52  baa
 *   @scr 3561 refactor/clean item size code, search by tender changes
 *
 *   Revision 1.4  2004/02/27 19:51:16  baa
 *   @scr 3561 Return enhancements
 *
 *   Revision 1.3  2004/02/12 16:51:46  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:30  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:20  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.1   05 Feb 2004 23:16:28   baa
 * returs - multi items
 * 
 *    Rev 1.0   Aug 29 2003 16:05:50   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:06:46   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:45:14   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:24:32   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:12:26   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returncommon;

// java imports
import java.io.Serializable;

import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.SearchCriteriaIfc;

//--------------------------------------------------------------------------
/**
    This interface defines a common way for the CustomerIfc and FindTrans
    cargos to provide the current selected original transaction and the 
    customer linked to the current transaction.  A common method determines
    if this transaction is a valid candidate for returning its items.
    <p>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface ReturnableItemCargoIfc extends CargoIfc, Serializable
{
    //----------------------------------------------------------------------
    /**
        Get the original transaction
        <P>
        @return SaleReturnTransactionIfc
    **/
    //----------------------------------------------------------------------
        public SaleReturnTransactionIfc getOriginalTransaction();

    //----------------------------------------------------------------------
    /**
        Get the customer associated with the current transaction.
        <P>
        @return  CustomerIfc
    **/
    //----------------------------------------------------------------------
        public CustomerIfc getCustomer();

    /**
     * Gets flag that indicates if search was done by tender
     * @return Returns the isSearchByTender.
     */
    public boolean isSearchByTender();
    
    /**
     * Sets flag that indicates if search was done by tender
     * @param value The isSearchByTender to set.
     */
    public void setSearchByTender(boolean value);
    
    //----------------------------------------------------------------------
    /**
        Returns the search criteria to  retrieve transactions
        @return SearchCriteriaIfc  the search criteria
    **/
    //----------------------------------------------------------------------
    public SearchCriteriaIfc getSearchCriteria();

    //----------------------------------------------------------------------
    /**
        Sets the search criteria to  retrieve transactions
        @param criteria  the search criteria
    **/
    //----------------------------------------------------------------------
    public void setSearchCriteria(SearchCriteriaIfc criteria);
}
