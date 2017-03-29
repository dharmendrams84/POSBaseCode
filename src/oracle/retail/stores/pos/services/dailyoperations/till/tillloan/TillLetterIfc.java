/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/dailyoperations/till/tillloan/TillLetterIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:17 mszekely Exp $
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
 *   Revision 1.3  2004/04/30 18:16:03  dcobb
 *   @scr 4098 Open drawer before detail count screens.
 *   Loan changed to open drawer before detail count screens.
 *
 *   Revision 1.2  2004/02/12 16:49:59  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:15  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:57:50   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:28:08   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:29:14   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:18:34   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:14:30   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.dailyoperations.till.tillloan;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;

/**
        Inteface for Till Loan Letters
**/
public interface TillLetterIfc
{
    /** String for Count Type No letter */
    static final public String COUNT_TYPE_NONE = "CountTypeNone";
    /** String for Count Type Summary letter */
    static final public String COUNT_TYPE_SUMMARY = "CountTypeSummary";
    /** String for Count Type Detail letter */
    static final public String COUNT_TYPE_DETAIL = "CountTypeDetail";
    /** String for Till Not Open Error */
    static final public String TILL_NOT_OPEN_ERROR = "TillNotOpenError";
    /** String for No Tills Open Error */
    static final public String NO_TILLS_OPEN_ERROR = "NoTillsOpenError";

    /** String for Ok letter */
    static final public String OK = CommonLetterIfc.OK;
    
    /** String for Success letter
        @ deprecated as of Release 7.0. Use CommonLetterIfc.SUCCESS  
    **/
    static final public String SUCCESS = CommonLetterIfc.SUCCESS;
    /**  
        String for Failure letter
        @ deprecated as of Release 7.0. Use CommonLetterIfc.FAILURE 
    **/
    static final public String FAILURE = CommonLetterIfc.FAILURE;
    /**  
        String for Yes letter
        @ deprecated as of Release 7.0. Use CommonLetterIfc.YES 
    **/
    static final public String YES = CommonLetterIfc.YES;
    /**  
        String for No letter
        @ deprecated as of Release 7.0. Use CommonLetterIfc.NO 
    **/
    static final public String NO = CommonLetterIfc.NO;
    /**  
        String for Cancel letter
        @ deprecated as of Release 7.0. Use CommonLetterIfc.CANCEL 
    **/
    static final public String CANCEL = CommonLetterIfc.CANCEL;
    /**  
        String for Undo letter
        @ deprecated as of Release 7.0. Use CommonLetterIfc.UNDO 
    **/
    static final public String UNDO = CommonLetterIfc.UNDO;
    /**  
        String for Next letter
        @ deprecated as of Release 7.0. Use CommonLetterIfc.NEXT 
    **/
    static final public String NEXT = CommonLetterIfc.NEXT;
    /**  
        String for Continue letter
        @ deprecated as of Release 7.0. Use CommonLetterIfc.CONTINUE 
    **/
    static final public String CONTINUE = CommonLetterIfc.CONTINUE;
    /**  
        String for Cashier Error letter
        @ deprecated as of Release 7.0. Use CommonLetterIfc.CASHIER_ERROR 
    **/
    static final public String CASHIER_ERROR = CommonLetterIfc.CASHIER_ERROR;
    /**  
        String for Parameter Error letter
        @ deprecated as of Release 7.0. Use CommonLetterIfc.PARAMETER_ERROR 
    **/
    static final public String PARAMETER_ERROR = CommonLetterIfc.PARAMETER_ERROR;
    /**  
        String for Till Id Error letter
        @ deprecated as of Release 7.0. Use CommonLetterIfc.TILL_ID_ERROR 
    **/
    static final public String TILL_ID_ERROR = CommonLetterIfc.TILL_ID_ERROR;
    /**  
        String for Login Error letter
        @ deprecated as of Release 7.0. Use CommonLetterIfc.LOGIN_ERROR 
    **/
    static final public String LOGIN_ERROR = CommonLetterIfc.LOGIN_ERROR;
    /**  
        String for Register Closed Error letter
        @ deprecated as of Release 7.0. Use CommonLetterIfc.REGISTER_CLOSED_ERROR 
    **/
    static final public String REGISTER_CLOSED_ERROR = CommonLetterIfc.REGISTER_CLOSED_ERROR;
    /**
       String for Update Error letter
       @ deprecated as of Release 7.0. Use CommonLetterIfc.UPDATE_ERROR
    **/
    static final public String UPDATE_ERROR = CommonLetterIfc.UPDATE_ERROR;

}
