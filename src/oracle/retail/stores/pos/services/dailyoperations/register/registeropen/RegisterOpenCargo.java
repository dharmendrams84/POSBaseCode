/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/dailyoperations/register/registeropen/RegisterOpenCargo.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:17 mszekely Exp $
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
 *    4    360Commerce 1.3         4/1/2008 2:29:33 PM    Deepti Sharma   CR
 *         31016 forward port from v12x -> trunk
 *    3    360Commerce 1.2         3/31/2005 4:29:37 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:24:38 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:13:38 PM  Robert Pearse   
 *
 *   Revision 1.4  2004/09/27 22:32:05  bwf
 *   @scr 7244 Merged 2 versions of abstractfinancialcargo.
 *
 *   Revision 1.3  2004/02/12 16:49:52  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:46:34  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:15  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:57:12   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   May 23 2003 06:52:36   jgs
 * Modified to delay the end of transaction journal entry.
 * Resolution for 2543: Modify EJournal to put entries into a JMS Queue on the store server.
 * 
 *    Rev 1.0   Apr 29 2002 15:29:40   msg
 * Initial revision.
 * 
 *    Rev 1.1   Mar 18 2002 23:15:12   msg
 * - updated copyright
 * 
 *    Rev 1.0   Mar 18 2002 11:27:54   msg
 * Initial revision.
 * 
 *    Rev 1.1   14 Nov 2001 11:51:30   epd
 * Added Security Access code and flow
 * Resolution for POS SCR-216: Making POS changes to accommodate OnlineOffice
 * 
 *    Rev 1.0   Sep 21 2001 11:17:42   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:12:06   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.dailyoperations.register.registeropen;

// foundation imports
import oracle.retail.stores.pos.services.common.AbstractFinancialCargo;
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.employee.RoleFunctionIfc;
import oracle.retail.stores.domain.financial.RegisterIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;

//--------------------------------------------------------------------------
/**
    This cargo holds the information necessary to the Register Open service.
    <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class RegisterOpenCargo extends AbstractFinancialCargo
{                                       // begin class RegisterOpenCargo
    /**
        revision number
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
        Holds the open register object in case the db update fails.
    **/
    protected RegisterIfc holdRegister = null;

    /**
        Flag to indicate whether a warning message should be presented
        before opening.
    **/
    protected boolean showWarning = false;
   
    /**
        Employee attempting Access
    **/
    protected EmployeeIfc accessEmployee;

    /**
       Contains transaction info; saved for journaling.
    **/
    protected TransactionIfc transaction = null;
   
    //----------------------------------------------------------------------
    /**
     * Sets the access employee.
     * @param value EmployeeIfc
    **/
    //----------------------------------------------------------------------
    public void setAccessEmployee(EmployeeIfc value)
    {
        accessEmployee = value;
    }
    //----------------------------------------------------------------------
    /**
        Returns the access employee.
        @return the EmployeeIfc value
    **/
    //----------------------------------------------------------------------
    public EmployeeIfc getAccessEmployee()
    {
        return accessEmployee;
    }
    //----------------------------------------------------------------------
    /**
        Returns a the open register object. <P>
        @return RegisterIfc
    **/
    //----------------------------------------------------------------------
    public RegisterIfc getHoldRegister()
    {
        return holdRegister;
    }

    //----------------------------------------------------------------------
    /**
        Sets the open register object. <P>
        @param RegisterIfc
    **/
    //----------------------------------------------------------------------
    public void setHoldRegister(RegisterIfc value)
    {
        holdRegister = value;
    }

    //----------------------------------------------------------------------
    /**
        Returns whether a warning should be displayed before opening. <P>
        @return whether a warning should be displayed before opening.
    **/
    //----------------------------------------------------------------------
    public boolean getShowWarning()
    {
        return showWarning;
    }

    //----------------------------------------------------------------------
    /**
        Sets whether a warning should be displayed before opening. <P>
        @param value true, if a warning should be displayed, false otherwise
    **/
    //----------------------------------------------------------------------
    public void setShowWarning(boolean value)
    {
        showWarning = value;
    }

    //----------------------------------------------------------------------
    /**
     * Returns the transaction.
     * @return TransactionIfc
     */
    //----------------------------------------------------------------------
    public TransactionIfc getTransaction()
    {
        return transaction;
    }

    //----------------------------------------------------------------------
    /**
     * Sets the transaction.
     * @param transaction The transaction to set
     */
    //----------------------------------------------------------------------
    public void setTransaction(TransactionIfc transaction)
    {
        this.transaction = transaction;
    }

    //----------------------------------------------------------------------
    /**
        Returns a string representation of this object. <P>
        @return String representation of object
    **/
    //----------------------------------------------------------------------
    public String toString()
    {
        String strResult = new String("Class:  RegisterOpenCargo (Revision " +
                                      getRevisionNumber() +
                                      ") @" + hashCode());
        strResult += "\n" + abstractToString();
        return(strResult);
    }

    //----------------------------------------------------------------------
    /**
        Returns the revision number of the class. <P>
        @return String representation of revision number
    **/
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return(revisionNumber);
    }
}                                       // end class RegisterOpenCargo
