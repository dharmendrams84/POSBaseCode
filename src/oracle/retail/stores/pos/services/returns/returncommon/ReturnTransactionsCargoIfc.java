/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/returns/returncommon/ReturnTransactionsCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:58 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:29:46 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:54 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:56 PM  Robert Pearse   
 *
 *   Revision 1.5  2004/03/15 15:16:51  baa
 *   @scr 3561 refactor/clean item size code, search by tender changes
 *
 *   Revision 1.4  2004/03/10 14:16:46  baa
 *   @scr 0 fix javadoc warnings
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
 *    Rev 1.1   Dec 19 2003 13:22:30   baa
 * more return enhancements
 * Resolution for 3561: Feature Enhacement: Return Search by Tender
 * 
 *    Rev 1.0   Aug 29 2003 16:05:50   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:06:52   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:45:18   msg
 * Initial revision.
 * 
 *    Rev 1.1   10 Dec 2001 12:28:42   jbp
 * Added getTransaction to ReturnTransactionCargoIfc and abstracted the functionalty to AbstractFindTransactionCargo
 * Resolution for POS SCR-418: Return Updates
 *
 *    Rev 1.0   Sep 21 2001 11:24:26   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:12:24   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.returns.returncommon;

// java imports
import java.io.Serializable;

import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionSummaryIfc;
import oracle.retail.stores.foundation.tour.ifc.CargoIfc;

//--------------------------------------------------------------------------
/**
    This interface defines a common way for the CustomerIfc and FindTrans
    cargos to provide the array of transactions the needed by the
    Select Transaction site.
    <p>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface ReturnTransactionsCargoIfc extends CargoIfc, Serializable
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
        This interface defines a common way for the CustomerIfc and FindTrans
        cargos to provide the array of transactions the needed by the
        Select Transaction site.
        @return TransactionSummaryIfc[] the list of transactions
    **/
    //----------------------------------------------------------------------

    public TransactionSummaryIfc[] getTransactionSummary();
    //----------------------------------------------------------------------
    /**
        Returns the Current transaction. <P>
        @return SaleReturnTransactionIfc
    **/
    //----------------------------------------------------------------------
    public SaleReturnTransactionIfc getTransaction();

    //----------------------------------------------------------------------
    /**
        Sets the Current transaction. <P>
        @param value The transaction
    **/
    //----------------------------------------------------------------------
    public void setTransaction(SaleReturnTransactionIfc value);
 
    //----------------------------------------------------------------------
    /**
        Sets flag to indicates if the original transaction was retrieved <P>
        @param value flag to indicate if trasaction was found
    **/
    //----------------------------------------------------------------------
    public void setTransactionFound(boolean value);
    
    //----------------------------------------------------------------------
    /**
        Gets flag that indicates if the original transaction was retrieved <P>
        @return boolean flag to indicate if trasaction was found
    **/
    //----------------------------------------------------------------------   
    public boolean isTransactionFound();
    
  
}
