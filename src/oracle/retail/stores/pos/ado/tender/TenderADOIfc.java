/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/tender/TenderADOIfc.java /rgbustores_13.4x_generic_branch/2 2011/07/12 15:58:31 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/12/11 - update generics
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    5    360Commerce 1.4         4/25/2007 8:52:55 AM   Anda D. Cadar   I18N
 *         merge
 *         
 *    4    360Commerce 1.3         12/13/2005 4:42:32 PM  Barry A. Pape
 *         Base-lining of 7.1_LA
 *    3    360Commerce 1.2         3/31/2005 4:30:21 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:25:52 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:14:47 PM  Robert Pearse   
 *
 *   Revision 1.5  2004/05/02 01:54:05  crain
 *   @scr 4553 Redeem Gift Certificate
 *
 *   Revision 1.4  2004/04/28 21:05:04  bwf
 *   @scr 3377 Debit Reversal Work
 *
 *   Revision 1.3  2004/03/16 18:30:45  cdb
 *   @scr 0 Removed tabs from all java source code.
 *
 *   Revision 1.2  2004/02/12 16:47:55  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.3   Dec 07 2003 18:48:40   crain
 * Added foreign amount
 * Resolution for 3421: Tender redesign
 * 
 *    Rev 1.2   Nov 19 2003 22:10:36   blj
 * added code for gift card tender using ado design
 * 
 *    Rev 1.1   Nov 05 2003 13:18:14   epd
 * updates for authorization
 * 
 *    Rev 1.0   Nov 04 2003 11:13:10   epd
 * Initial revision.
 * 
 *    Rev 1.0   Oct 17 2003 12:33:42   epd
 * Initial revision.
 *   
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.tender;

import java.util.HashMap;

import oracle.retail.stores.pos.ado.ADOIfc;
import oracle.retail.stores.pos.ado.journal.JournalableADOIfc;
import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;

/**
 *
 * 
 */
public interface TenderADOIfc extends JournalableADOIfc, ADOIfc
{
    /** PAT Cash Threshold for tenders that may already be reported.
     * We exclude PAT Cash exceeding this amount
     **/
    public static final String PAT_CASH_THRESHOLD = "10000.00";
    
    /**
     * Returns the tender type
     * @return tender type
     */
    public TenderTypeEnum getTenderType();
    
    /**
     * Gets the amount of this tender
     * @return tender amount
     */
    public CurrencyIfc getAmount();
    
    /**
     * Invokes validation logic on this tender
     * @throws TenderException Thrown when validation fails.
     */
    public void validate() throws TenderException;
    
    /**
     * return a Map of information that describes this tender.
     * Note, can be used to generate a new identical tender via the 
     * TenderFactory
     * @return Map of data describing this tender.
     */
    public HashMap<String,Object> getTenderAttributes();
    
    /**
     * This method  takes the tender attributes
     * and attempts to parse the information contained therein for the purposes of setting
     * the attribute data on this tender
     * @param tenderAttributes A Map of attributes used to set the state of this tender
     * @throws TenderException Thrown when an attribute cannot be set due to an error.
     */
    public void setTenderAttributes(HashMap<String,Object> tenderAttributes) throws TenderException;
    
    /**
     * This method is used to flag a tender as dirty.
     * @param dirty Set an attribute to signify that the tender changed(is dirty).
     */
    public void setDirtyFlag(boolean dirty);
    /**
     * Return the dirty flag
     * @return
     */
    public boolean isDirtyFlag();
    
    /**
     * Return if tender qualifies as PAT Cash
     * @return true if tender is PAT Cash
     */
    public boolean isPATCash();
 
}
