/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/journal/JournalFactoryIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:42 mszekely Exp $
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
 *3    360Commerce 1.2         3/31/2005 4:28:48 PM   Robert Pearse   
 *2    360Commerce 1.1         3/10/2005 10:22:57 AM  Robert Pearse   
 *1    360Commerce 1.0         2/11/2005 12:12:10 PM  Robert Pearse   
 *
 Revision 1.1.2.1  2004/11/12 14:28:53  kll
 @scr 7337: JournalFactory extensibility initiative
 *
 Revision 1.1  2004/10/18 01:11:52  kll
 @scr 7337: JournalFactory extensibility
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.journal;

//--------------------------------------------------------------------------
/**
     Journal factory interface.
     $Revision: /rgbustores_13.4x_generic_branch/1 $
 **/
//--------------------------------------------------------------------------
public interface JournalFactoryIfc
{
    /**
     * Journal factory method
     */
    public static String INSTANTIATION_ERROR =  "Configuration problem: could not instantiate JournalFactoryIfc instance";
    /**
     * Journal factory method
     */
    public RegisterJournalIfc getRegisterJournal();
}
