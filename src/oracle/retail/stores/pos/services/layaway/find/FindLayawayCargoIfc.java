/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/layaway/find/FindLayawayCargoIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:13 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:28:11 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:21:42 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:11:06 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:50:49  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:17  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:00:40   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   Apr 29 2002 15:20:36   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:35:10   msg
 * Initial revision.
 * 
 *    Rev 1.0   Sep 21 2001 11:21:36   msg
 * Initial revision.
 * 
 *    Rev 1.1   Sep 17 2001 13:08:36   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.layaway.find; 

// foundation imports

// domain imports
import oracle.retail.stores.domain.customer.CustomerIfc;
import oracle.retail.stores.domain.financial.LayawayIfc;
import oracle.retail.stores.domain.financial.LayawaySummaryEntryIfc;
import oracle.retail.stores.domain.transaction.LayawayTransactionIfc;
import oracle.retail.stores.domain.transaction.TenderableTransactionIfc;
import oracle.retail.stores.domain.transaction.TransactionIfc;

// quarry imports

//--------------------------------------------------------------------------
/**
    Interface for properties required for FindLayawayCargo.
    <p>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface FindLayawayCargoIfc
{
    /**
        revision number supplied by source-code-control system
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //------- Constants for defining search type ---------//
    public static final int   LAYAWAY_SEARCH_BY_ID        = 0;
    public static final int   LAYAWAY_SEARCH_BY_CUSTOMER  = 1;
    
    //------- Constants for defining layaway operation -------//
    public static final int   LAYAWAY_CREATE         = 0;
    public static final int   LAYAWAY_PAYMENT        = 1;
    public static final int   LAYAWAY_PICKUP         = 2;
    public static final int   LAYAWAY_DELETE         = 3;
    
    
    //--------------------------------------------------------------------------
    /**
        Gets the list of LayawaySummaryEntryIfcs.  <p>
        @return list of LayawaySummaryEntryIfcs
    **/
    //--------------------------------------------------------------------------
    public LayawaySummaryEntryIfc[] getLayawaySummaryEntryList();

    //----------------------------------------------------------------------
    /**
        Sets the list of LayawaySummaryEntryIfcs. <P>
        @param values list of LayawaySummaryEntryIfcs
    **/
    //--------------------------------------------------------------------------
    public void setLayawaySummaryEntryList(LayawaySummaryEntryIfc[] values);

    //--------------------------------------------------------------------------
    /**
        Gets the selected customer.  This is the customer whose orders we 
        wish to query.      <p>
        @return Customer
    **/
    //--------------------------------------------------------------------------
    public CustomerIfc getCustomer();
    
    //----------------------------------------------------------------------
    /**
        Sets the customer selected via Customer Find.  
        This is the customer whose orders we wish to query. <p>
        @param Customer
    **/
    //--------------------------------------------------------------------------
    public void setCustomer(CustomerIfc value);
    
    //----------------------------------------------------------------------
    /**
        Sets the layaway search method.
        @param name int value of the searchMethod
    **/
    //--------------------------------------------------------------------------
    public void setLayawaySearch(int name);

    //--------------------------------------------------------------------------
    /**
        Gets the layaway search method.
        @return int representation of the searchMethod
    **/
    //--------------------------------------------------------------------------
    public int getLayawaySearch();
    
    //----------------------------------------------------------------------
    /**
        Sets the layaway operation which the cargo will be used for.
        @param name int value of the layaway operation
    
    **/
    //--------------------------------------------------------------------------
    public void setLayawayOperation(int name);

    //--------------------------------------------------------------------------
    /**
        Gets the name of the layaway operation which the cargo will be used for.
        @return int representation of the layaway operation
    
    **/
    //--------------------------------------------------------------------------
    public int getLayawayOperation();


    //--------------------------------------------------------------------------
    /**
        Gets the index of the selected LayawaySummaryEntryIfc.  <p>
        @return index of the selected LayawaySummaryEntryIfc
    **/
    //--------------------------------------------------------------------------
    public int getSelectedLayawayIndex();
    
    //----------------------------------------------------------------------
    /**
        Sets the index of the selected LayawaySummaryEntryIfc. <P>
        @param value index of the selected LayawaySummaryEntryIfc
    **/
    //--------------------------------------------------------------------------
    public void setSelectedLayawayIndex(int value);

    //--------------------------------------------------------------------------
    /**
        Gets the current layaway.  <p>
        @return layaway
    **/
    //--------------------------------------------------------------------------
    public LayawayIfc getLayaway();

    //----------------------------------------------------------------------
    /**
        Sets the current layaway. <P>
        @param layaway
    **/
    //--------------------------------------------------------------------------
    public void setLayaway(LayawayIfc value);

    //----------------------------------------------------------------------
    /**
        Sets the initial Layaway Transaction. <P>
        @param LayawayTransactionIfc
    **/
    //--------------------------------------------------------------------------
    public void setInitialLayawayTransaction(LayawayTransactionIfc value);
    
    //--------------------------------------------------------------------------
    /**
        Retrieves the initial layaway transactoin
        @return LayawayTransactoinIfc
    **/
    //--------------------------------------------------------------------------
    public LayawayTransactionIfc getInitialLayawayTransaction();

    //--------------------------------------------------------------------------
    /**
        Retrieves the seed transactoin
        @return TransactionIfc
    **/
    //--------------------------------------------------------------------------
    public TransactionIfc getSeedLayawayTransaction();
    
    //----------------------------------------------------------------------
    /**
        Sets the seed Transaction. <P>
        @param LayawayTransactionIfc
    **/
    //--------------------------------------------------------------------------
    public void setSeedLayawayTransaction(TransactionIfc value);
    
    //----------------------------------------------------------------------
    /**
        Sets the tenderable transaction. <P>
        @param tenderable transaction
    **/
    //--------------------------------------------------------------------------
    public void setTenderableTransaction(TenderableTransactionIfc value);
    
    //--------------------------------------------------------------------------
    /**
        Gets the current tenderable tansaction.  <p>
        @return tenderable transaction
    **/
    //--------------------------------------------------------------------------
    public TenderableTransactionIfc getTenderableTransaction();

    //--------------------------------------------------------------------------
    /**
        Gets the layaway number for searches.  <p>
        @return layaway search number
    **/
    //--------------------------------------------------------------------------
    public String getLayawaySearchID();

    //----------------------------------------------------------------------
    /**
        Sets the layaway number for searches. <P>
        @param layaway number
    **/
    //--------------------------------------------------------------------------
    public void setLayawaySearchID(String value);
    
    //----------------------------------------------------------------------
    /**
        Returns the error code returned with a DataException.
        <P>
        @return the integer value
    **/
    //----------------------------------------------------------------------
    public int getDataExceptionErrorCode();
    
    //----------------------------------------------------------------------
    /**
        Sets the error code returned with a DataException.
        <P>
        @param  the integer value
    **/
    //----------------------------------------------------------------------
    public void setDataExceptionErrorCode(int value);

}
