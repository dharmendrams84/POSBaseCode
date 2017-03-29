/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/specialorder/SpecialOrderCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:02 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:30:07 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:24 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:20 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/02/12 16:52:00  mcs
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
 *    Rev 1.0   Aug 29 2003 16:07:16   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:01:38   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:48:00   msg
 * Initial revision.
 * 
 *    Rev 1.4   Dec 10 2001 18:44:50   cir
 * Added order as OrderIfc
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * 
 *    Rev 1.3   Dec 07 2001 16:41:54   dfh
 * added getaccessfunctionid for security access override
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * 
 *    Rev 1.2   Dec 06 2001 17:26:26   dfh
 * updates to prepare for security override, cleanup
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * 
 *    Rev 1.1   Dec 04 2001 16:11:58   dfh
 * No change.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * 
 *    Rev 1.0   Dec 04 2001 14:48:22   dfh
 * Initial revision.
 * Resolution for POS SCR-260: Special Order feature for release 5.0
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.specialorder;

// domain imports
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.order.OrderIfc;
import oracle.retail.stores.domain.transaction.OrderTransactionIfc;

//------------------------------------------------------------------------------
/**
    Interface defines constants and method signatures for processing search criteria
    and operations common to the special order services. This interface is provided  
    for all special order services.
    <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------
public interface SpecialOrderCargoIfc
{
    // revision number
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //--------------------------------------------------------------------------
    /**
        Gets the special order customer.   <P>
        @return Customer
    **/
    //--------------------------------------------------------------------------
    public CustomerIfc getCustomer();
    
    //----------------------------------------------------------------------
    /**
        Sets the special order customer.  <P>
        @param Customer
    **/
    //--------------------------------------------------------------------------
    public void setCustomer(CustomerIfc value);
           
    //--------------------------------------------------------------------------
    /**
        Gets the current order transaction.  <p>
        @return order transaction
    **/
    //--------------------------------------------------------------------------
    public OrderTransactionIfc getOrderTransaction();

    //----------------------------------------------------------------------
    /**
        Sets the order transaction. <P>
        @param order transaction
    **/
    //--------------------------------------------------------------------------
    public void setOrderTransaction(OrderTransactionIfc value);

    //--------------------------------------------------------------------------
    /**
        Gets the current order.  <p>
        @return OrderIfc 
    **/
    //--------------------------------------------------------------------------
    public OrderIfc getOrder();

    //----------------------------------------------------------------------
    /**
        Sets the order. <P>
        @param value as OrderIfc 
    **/
    //--------------------------------------------------------------------------
    public void setOrder(OrderIfc value);

    //----------------------------------------------------------------------
    /**
        Gets the sales associate. <P>
        @return EmployeeIfc sales associate
    **/
    //----------------------------------------------------------------------
    public EmployeeIfc getSalesAssociate();
       
    //----------------------------------------------------------------------
    /**
        Returns the function ID whose access is to be checked.
        @return int Role Function ID
    **/
    //----------------------------------------------------------------------
    public int getAccessFunctionID();

} ///:~ end SpecialOrderCargoIfc
