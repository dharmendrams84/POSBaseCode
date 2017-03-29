/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/TransactionCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:52 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:30:33 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:26:20 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:15:12 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:49:08  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:54:52   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:35:22   msg
 * Initial revision.
 * 
 *    Rev 1.1   Mar 18 2002 23:10:38   msg
 * - updated copyright
 * 
 *    Rev 1.0   Mar 18 2002 11:23:28   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:14:08   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:06:02   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

// domain imports
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.transaction.RetailTransactionIfc;

//--------------------------------------------------------------------------
/**
    Methods common to the TransactionCargo's
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface TransactionCargoIfc
{
    /**
        revision number supplied by source-code-control system
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
        Returns the operator. <P>
        @return The operator.
    **/
    //----------------------------------------------------------------------
    public EmployeeIfc getOperator();

    //----------------------------------------------------------------------
    /**
        Returns the transaction
        <p>
        @return the transaction
    **/
    //----------------------------------------------------------------------
    public RetailTransactionIfc getTransaction();

    //----------------------------------------------------------------------
    /**
        Sets the transaction.
        <p>
        @param transaction  the transaction
    **/
    //----------------------------------------------------------------------
    public void setTransaction(RetailTransactionIfc transaction);

    //----------------------------------------------------------------------
    /**
        Returns the sales associate
        <p>
        @return the sales associate
    **/
    //----------------------------------------------------------------------
    public EmployeeIfc getSalesAssociate();

    //----------------------------------------------------------------------
    /**
        Sets the sales associate
        <p>
        @param  associate   the sales associate
    **/
    //----------------------------------------------------------------------
    public void setSalesAssociate(EmployeeIfc associate);

    //----------------------------------------------------------------------
    /**
        Returns whether the transaction was created.
        <p>
        @return whether the transaction was created.
    **/
    //----------------------------------------------------------------------
        public boolean getTransactionCreated();

    //----------------------------------------------------------------------
    /**
        Sets whether the transaction was created.
        <p>
        @param  value   true if the transaction was created, false otherwise
    **/
    //----------------------------------------------------------------------
        public void setTransactionCreated(boolean value);

    //----------------------------------------------------------------------
    /**
        Returns whether the transaction should be created, if needed.
        <p>
        @return whether the transaction should be created.
    **/
    //----------------------------------------------------------------------
        public boolean createTransaction();

    //----------------------------------------------------------------------
    /**
        Sets whether the transaction should be created, if needed.
        <p>
        @param  value   whether the transaction should be created.
    **/
    //----------------------------------------------------------------------
        public void setCreateTransaction(boolean value);
}
