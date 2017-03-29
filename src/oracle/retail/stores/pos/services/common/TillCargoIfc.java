/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/TillCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:53 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:30:29 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:26:10 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:15:03 PM  Robert Pearse   
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
 *    Rev 1.1   08 Nov 2003 01:08:00   baa
 * cleanup -sale refactoring
 * 
 *    Rev 1.0   Nov 05 2003 23:03:54   cdb
 * Initial revision.
 * Resolution for 3430: Sale Service Refactoring
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

// Bedrock imports
import oracle.retail.stores.domain.employee.EmployeeIfc;
import oracle.retail.stores.domain.financial.TillIfc;
import oracle.retail.stores.domain.transaction.TillAdjustmentTransactionIfc;
import oracle.retail.stores.foundation.tour.application.tourcam.ObjectRestoreException;
import oracle.retail.stores.foundation.tour.application.tourcam.SnapshotIfc;
import oracle.retail.stores.foundation.tour.application.tourcam.TourCamIfc;

//------------------------------------------------------------------------------
/**


    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//------------------------------------------------------------------------------

public interface TillCargoIfc extends StoreStatusCargoIfc, TourCamIfc
{

    /**
        current till open error tag
    **/
    public static String TILL_OPEN_ERROR_TAG_LINE1 = "NoOpenCashDrawersError.TillOpenLine1";
    public static String TILL_OPEN_ERROR_TAG_LINE2 = "NoOpenCashDrawersError.TillOpenLine2";
    /**
        current till open error
    **/
    public static String TILL_OPEN_ERROR_LINE1 = "The current till must be closed or suspended";
    public static String TILL_OPEN_ERROR_LINE2 = "and removed from the drawer before another till can be opened.";
    /**
        current till suspended error tag
    **/
    public static String TILL_SUSPENDED_ERROR_TAG_LINE1 = "NoOpenCashDrawersError.TillSuspendedLine1";
    public static String TILL_SUSPENDED_ERROR_TAG_LINE2 = "NoOpenCashDrawersError.TillSuspendedLine2";
    /**
        current till suspended error
    **/
    public static String TILL_SUSPENDED_ERROR_LINE1 = "The suspended till must be removed from the drawer";
    public static String TILL_SUSPENDED_ERROR_LINE2 = "before another till can be opened.";


    //----------------------------------------------------------------------
    /**
        Returns the temporary till. <P>
        @return The temporary till.
    **/
    //----------------------------------------------------------------------
    public TillIfc getTill();

    //----------------------------------------------------------------------
    /**
        Sets the temporary till. <P>
        @param  value  The temporary till.
    **/
    //----------------------------------------------------------------------
    public void setTill(TillIfc value);

    //----------------------------------------------------------------------
    /**
        Returns the till id. <P>
        @return The till id.
    **/

    //----------------------------------------------------------------------
    public String getTillID();

    //----------------------------------------------------------------------
    /**
        Sets the till id. <P>
        @param  value  till id.
    **/
    //----------------------------------------------------------------------
    public void setTillID(String value);

    //----------------------------------------------------------------------
    /**
        Returns the data exception error code. <P>
        @return The data exception error code.
    **/
    //----------------------------------------------------------------------
    public int getDataExceptionErrorCode();

    //----------------------------------------------------------------------
    /**
        Sets the data exception error code. <P>
        @param  value  The data exception error code.
    **/
    //----------------------------------------------------------------------
    public void setDataExceptionErrorCode(int value);
    
    //----------------------------------------------------------------------
    /**
        Returns true if till fatal error. <P>
        @return true if error is fatal
    **/
    //----------------------------------------------------------------------
    public boolean isTillFatalError();

    //----------------------------------------------------------------------
    /**
        Sets the till fatal error code <P>
    **/
    //----------------------------------------------------------------------
    public void setTillFatalError();

    //----------------------------------------------------------------------
    /**
        Returns the transaction. <P>
        @return the transaction.
    **/
    //----------------------------------------------------------------------
        public TillAdjustmentTransactionIfc getTransaction();

    //----------------------------------------------------------------------
    /**
        Sets the transaction. <P>
        @param vale the name of the transaction.
    **/
    //----------------------------------------------------------------------
    public void setTransaction(TillAdjustmentTransactionIfc value);

    //--------------------------------------------------------------------------
    /**
        Create a SnapshotIfc which can subsequently be used to restore
            the cargo to its current state. <P>
        <B>Pre-Condition(s)</B>
        <UL>
        <LI>The cargo is able to make a snapshot.
        </UL>
        <B>Post-Condition(s)</B>
        <UL>
        <LI>A snapshot is returned which contains enough data to restore the
            cargo to its current state.
        </UL>
        @return an object which stores the current state of the cargo.
        @see oracle.retail.stores.foundation.tour.application.tourcam.SnapshotIfc
    */
    //--------------------------------------------------------------------------

    public SnapshotIfc makeSnapshot();


    //--------------------------------------------------------------------------
    /**
        Reset the cargo data using the snapshot passed in. <P>
        <B>Pre-Condition(s)</B>
        <UL>
        <LI>The snapshot represents the state of the cargo, possibly relative
        to the existing state of the cargo.
        </UL>
        <B>Post-Condition(s)</B>
        <UL>
        <LI>The cargo state has been restored with the contents of the snapshot.
        </UL>
        @param snapshot is the SnapshotIfc which contains the desired state
            of the cargo.
        @exception ObjectRestoreException is thrown when the cargo cannot
            be restored with this snapshot
    */
    //--------------------------------------------------------------------------

    public void restoreSnapshot(SnapshotIfc snapshot) throws ObjectRestoreException;
    
    //--------------------------------------------------------------------------
    /**
        Returns the securityOverrideFlag boolean. <P>
        @return The securityOverrideFlag boolean.
    **/
    //----------------------------------------------------------------------
    public boolean getSecurityOverrideFlag();
    
    //----------------------------------------------------------------------
    /**
        Sets the securityOverrideFlag boolean. <P>
        @param  value  The ssecurityOverrideFlag boolean.
    **/
    //----------------------------------------------------------------------
    public void setSecurityOverrideFlag(boolean value);
    
    //----------------------------------------------------------------------
    /**
        Returns the securityOverrideEmployee object. <P>
        @return The securityOverrideEmployee object.
    **/
    //----------------------------------------------------------------------
    public EmployeeIfc getSecurityOverrideEmployee();

    //----------------------------------------------------------------------
    /**
        Sets the security override employee object. <P>
        @param  value  The security override employee object.
    **/
    //----------------------------------------------------------------------
    public void setSecurityOverrideEmployee(EmployeeIfc value);
    
    //----------------------------------------------------------------------
    /**
        Returns the securityOverrideRequestEmployee object. <P>
        @return The securityOverrideRequestEmployee object.
    **/
    //----------------------------------------------------------------------
    public EmployeeIfc getSecurityOverrideRequestEmployee();

    //----------------------------------------------------------------------
    /**
        Sets the securityOverrideRequestEmployee object. <P>
        @param  value  securityOverrideRequestEmployee object.
    **/
    //----------------------------------------------------------------------
    public void setSecurityOverrideRequestEmployee(EmployeeIfc value);
    
    //----------------------------------------------------------------------
    /**
        The securityOverrideReturnLetter returned by this cargo is to indecated
        where the security override will return
        <P>
        @return the void
    **/
    //----------------------------------------------------------------------
    public void setSecurityOverrideReturnLetter(String value);
    
    //----------------------------------------------------------------------
    /**
        The securityOverrideReturnLetter returned by this cargo is to indecated
        where the security override will return
        <P>
        @return the String value
    **/
    //----------------------------------------------------------------------
    public String getSecurityOverrideReturnLetter();

    //----------------------------------------------------------------------
    /**
        Returns whether a warning should be displayed before opening or
        resuming a till. <P>
        @return whether a warning should be displayed.
    **/
    //----------------------------------------------------------------------
    public boolean getShowWarning();

    //----------------------------------------------------------------------
    /**
        Sets whether a warning should be displayed before opening or
        resuming a till. <P>
        @param value true, if a warning should be displayed, false otherwise
    **/
    //----------------------------------------------------------------------
    public void setShowWarning(boolean value);
}
