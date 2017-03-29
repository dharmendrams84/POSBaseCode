/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/manager/ifc/CodeListManagerIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:35 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *    mdecama   10/16/08 - CodeListManager Interface
 * =========================================================================== */

package oracle.retail.stores.pos.manager.ifc;

import java.util.List;

import oracle.retail.stores.common.utility.LocalizedCodeIfc;
import oracle.retail.stores.domain.utility.CodeListIfc;
import oracle.retail.stores.domain.utility.CodeListSearchCriteriaIfc;
import oracle.retail.stores.domain.utility.CodeSearchCriteriaIfc;
import oracle.retail.stores.foundation.tour.manager.ManagerIfc;

/**
 * This interface provides a single point of access to load CodeLists
 */
public interface CodeListManagerIfc extends ManagerIfc
{
    /** Manager Type */
    public static final String TYPE = "CodeListManager";

    /**
     * Returns a localized CodeListIfc based on the search criteria
     *
     * @param criteria
     * @return
     */
    CodeListIfc getCodeList(CodeListSearchCriteriaIfc criteria);

    /**
     * Retrieves a localized code based on the search criteria
     *
     * @param criteria
     * @return
     */
    LocalizedCodeIfc getCode(CodeSearchCriteriaIfc criteria);

    /**
     * Retrieves a list of CodeList Ids
     *
     * @param criteria
     * @return
     */
    List<String> getCodeListIDs(CodeListSearchCriteriaIfc criteria);
}
