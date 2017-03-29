/* ===========================================================================
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/tender/AuthorizedTenderADOBuilderIfc.java /rgbustores_13.4x_generic_branch/5 2011/10/13 13:23:09 asinton Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   07/28/11 - added non-list oriented method
 *    cgreene   05/27/11 - move auth response objects into domain
 *    asinton   04/05/11 - Add tender builder for authorized tenders.
 *    asinton   03/31/11 - Add tender builder for authorized tenders.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.tender;

import java.util.List;

import oracle.retail.stores.domain.manager.payment.AuthorizeTransferResponseIfc;

/**
 * This interface provides a conversion from a list of {@link AuthoriazeTransferResponseIfc}
 * to a list of {@link TenderADOIfc} objects.
 * @author asinton
 * @since 13.4
 */
public interface AuthorizedTenderADOBuilderIfc
{
    /**
     * Lookup name for the authorization builder utility implementation.
     */
    public static final String BEAN_KEY = "application_AuthorizedTenderADOBuilder";

    /**
     * Tender factory name
     * Used by {@link BuildAuthorizedTenderADO#buildTenderADOs(List)} to load the TenderADO factory.
     */
    public static final String TENDER_FACTORY = "factory.tender";

    /**
     * Method buildTenderADOs will:
     * <ul>
     * <li>1. Determine which AuthorizableADOIfc implementation to instantiate (TenderCreditADO, TenderDebitADO, TenderGiftCardADO or TenderCheckADO).
     * <li>2. Load the data from the Tender Authorization Response object into the AuthorizableADOIfc object.
     * <li>3. Create a TenderCashADO if there is a cash back amount available in the Tender Authorization Response object.
     * <li>4. Load the TenderADOIfc object into the return list.
     * </ul>
     *
     * @param responses
     * @return list of TenderADOs based on the above.
     */
    public TenderADOIfc buildTenderADO(AuthorizeTransferResponseIfc response);

    /**
     * Same as {@link #buildTenderADO(AuthorizeTransferResponseIfc)} but with a
     * list.
     *
     * @param responses
     * @return
     */
    public List<TenderADOIfc> buildTenderADOs(List<AuthorizeTransferResponseIfc> responses);
}
