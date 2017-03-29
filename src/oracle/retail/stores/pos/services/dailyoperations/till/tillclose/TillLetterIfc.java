/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/dailyoperations/till/tillclose/TillLetterIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:19 mszekely Exp $
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
 *    4    360Commerce 1.3         8/16/2007 5:08:43 PM   Ashok.Mondal    CR
 *         19548 : Merge from V7x to trunk.
 *    3    360Commerce 1.2         3/31/2005 4:30:29 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:26:12 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:15:05 PM  Robert Pearse   
 *
 *   Revision 1.3  2004/04/15 18:57:00  dcobb
 *   @scr 4205 Feature Enhancement: Till Options
 *   Till reconcile service is now separate from till close.
 *
 *   Revision 1.2  2004/02/12 16:49:58  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:15  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:57:44   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:28:40   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:28:42   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:18:16   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:14:14   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.dailyoperations.till.tillclose;

/**
        This is an interface for Till Letters
 */
public interface TillLetterIfc
{
    public static final String TILL_RECONCILE = "TillReconcile";
    public static final String TILL_CLOSE = "TillClose";
    public static final String TILL_CLOSED_ERROR = "TillAlreadyClosedError";
    public static final String LOGIN_ERROR = "IDError";
    public static final String NO_TILLS_OPEN_ERROR = "NoTillsOpenError";
    public static final String TILL_ERROR = "TillError";
    public static final String TILL_OPEN = "TillOpen";
    public static final String NO_TILL_OPEN = "";
}

