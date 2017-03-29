/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/dailyoperations/till/tillresume/TillLetterIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:21 mszekely Exp $
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
 *    2    360Commerce 1.1         3/10/2005 10:26:12 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:15:05 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:50:08  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:16  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:58:36   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:25:58   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:31:00   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:20:14   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:15:08   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.dailyoperations.till.tillresume;


public interface TillLetterIfc

{
    static final public String SUCCESS = "Success";
    static final public String FAILURE = "Failure";
    static final public String YES = "Yes";
    static final public String NO = "No";
    static final public String OK = "Ok";
    static final public String CANCEL = "Cancel";
    static final public String UNDO = "Undo";
    static final public String CONTINUE = "Continue";
    static final public String CASHIER_ERROR = "CashierError";
    static final public String PARAMETER_ERROR = "ParameterError";
    static final public String TILL_RESUME_ERROR = "TillNotSuspendedError";
    static final public String LOGIN_ERROR = "IDError";
    static final public String NO_TILLS_OPEN_ERROR = "NoTillsOpenError";
    static final public String REGISTER_CLOSED_ERROR = "RegisterClosedError";
    static final public String UPDATE_ERROR = "UpdateError";
}
