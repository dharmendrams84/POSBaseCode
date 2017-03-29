/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/CreateRetailTransactionSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:53 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    3    360Commerce 1.2         3/31/2005 4:27:32 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:20:25 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:10:14 PM  Robert Pearse   
 *
 *   Revision 1.5  2004/04/07 14:36:08  jdeleau
 *   @scr 4090 Set up the LocaleMaps for DEVICES where necessary
 *
 *   Revision 1.4  2004/03/03 23:15:06  bwf
 *   @scr 0 Fixed CommonLetterIfc deprecations.
 *
 *   Revision 1.3  2004/02/12 16:49:08  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 20:56:28  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:14  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:54:18   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Jan 31 2003 17:35:10   baa
 * change pole display locale and receipt to match link customer locale preferences
 * Resolution for POS SCR-1843: Multilanguage support
 * 
 *    Rev 1.0   Apr 29 2002 15:36:00   msg
 * Initial revision.
 * 
 *    Rev 1.1   Mar 18 2002 23:08:48   msg
 * - updated copyright
 * 
 *    Rev 1.0   Mar 18 2002 11:22:26   msg
 * Initial revision.
 * 
 *    Rev 1.1   27 Feb 2002 15:47:44   pjf
 * Set salesAssociate to cashier if input cargo contains null salesAssociate reference.
 * Resolution for POS SCR-1436: Null pointer exception when saving transaction created via modify transaction service.
 *
 *    Rev 1.0   Sep 21 2001 11:13:38   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:06:24   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;
import java.util.Locale;

import oracle.retail.stores.pos.services.common.CommonLetterIfc;
import oracle.retail.stores.domain.DomainGateway;
import oracle.retail.stores.domain.transaction.SaleReturnTransactionIfc;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.tour.application.Letter;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.foundation.utility.LocaleMap;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;

import oracle.retail.stores.pos.ui.UIUtilities;

//--------------------------------------------------------------------------
/**
    This site creates a new transaction and writes a journal entry.
    <p>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class CreateRetailTransactionSite extends PosSiteActionAdapter
{
    /**
       revision number of this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    //----------------------------------------------------------------------
    /**
       Creates a new transaction. <P>
       @param  bus     Service Bus
    **/
    //----------------------------------------------------------------------
    public void arrive(BusIfc bus)
    {
        // Get the ui manager
        UtilityManagerIfc utility =
          (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);

        String letterName = CommonLetterIfc.FAILURE;
        TransactionCargoIfc cargo = (TransactionCargoIfc) bus.getCargo();

        if (cargo.createTransaction())
        {
             // Create a new transaction
            SaleReturnTransactionIfc transaction =
                DomainGateway.getFactory().getSaleReturnTransactionInstance();

            // Initialize fields specific to SaleReturnTransaction
            transaction.setCashier(cargo.getOperator());
            if (cargo.getSalesAssociate() != null)
            {
                transaction.setSalesAssociate(cargo.getSalesAssociate());
            }
            else
            {
                transaction.setSalesAssociate(cargo.getOperator());
            }
            // Initializes the fields common to all transactions.
            utility.initializeTransaction(transaction, bus, -1);

            // Store the transaction
            cargo.setTransactionCreated(true);
            
             // Set up default locales for pole display and receipt
            Locale defaultLocale = LocaleMap.getLocale(LocaleConstantsIfc.DEFAULT_LOCALE);
            
            UIUtilities.setUILocaleForCustomer(defaultLocale);
            
            
            cargo.setTransaction(transaction);
            letterName = CommonLetterIfc.SUCCESS;
        }

        bus.mail(new Letter(letterName), BusIfc.CURRENT);
    }

    //----------------------------------------------------------------------
    /**
       Returns a string representation of this object. <P>
       @return String representation of object
    **/
    //----------------------------------------------------------------------
    public String toString()
    {
        String strResult = new String("Class:  CreateRetailTransactionSite (Revision " +
                                      getRevisionNumber() +
                                      ")" + hashCode());
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
}
