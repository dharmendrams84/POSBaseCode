/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/ADO.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:40 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado;

import java.util.Locale;

import org.apache.log4j.Logger;

import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.pos.ado.context.ADOContextIfc;
import oracle.retail.stores.pos.ado.context.ContextFactory;
import oracle.retail.stores.pos.ado.journal.JournalFactory;
import oracle.retail.stores.pos.ado.journal.JournalFactoryIfc;
import oracle.retail.stores.pos.ado.utility.TenderUtility;
import oracle.retail.stores.pos.ado.utility.TenderUtilityIfc;
import oracle.retail.stores.pos.ado.utility.Utility;
import oracle.retail.stores.pos.ado.utility.UtilityIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.tour.manager.ManagerIfc;

/**
 * Provides some basic functionality that is common to all ADO objects.
 */
public abstract class ADO implements ADOIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -3186211529044819664L;

    /** The ADO logger */
    protected transient Logger logger =
        Logger.getLogger(oracle.retail.stores.pos.ado.ADO.class);

    /**
	 * Locale for journal information
	 */
    protected static Locale journalLocale = LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL);
    /**
     * Handle to the ParameterManagerIfc.
     */
    protected ParameterManagerIfc parameterManager;

    /**
     * Default constructor for ADO
     *
     */
    public ADO()
    {
        super();
    }

    /**
     * Returns the context for this ADO
     *
     * @return
     */
    protected ADOContextIfc getContext()
    {
        return ContextFactory.getInstance().getContext();
    }

    /**
     * Returns the desired Manager
     *
     * @param managerType
     *            The desired Manager TYPE
     * @return the requested Manager
     */
    protected ManagerIfc getManager(String managerType)
    {
        return getContext().getManager(managerType);
    }

    /**
     * Returns the retrieved property value Note: To retrieve a parameter from
     * application.properties, pass in the String "application" as the value
     * for the propertyGroup.
     *
     * @param propertyGroup
     *            The name of the property group containing the desired
     *            property
     * @param propertyName
     *            The keyname to the desired property
     * @param defaultValue
     *            A default value in the case there is a problem retrieving the
     *            property
     * @return The retrieved property value.
     */
    protected String getProperty(
        String propertyGroup,
        String propertyName,
        String defaultValue)
    {
        return Gateway.getProperty(propertyGroup, propertyName, defaultValue);
    }

    /**
     * Returns the appropriate UtilityIfc implementation
     *
     * @return
     */
    protected UtilityIfc getUtility()
    {
        UtilityIfc util = null;
        try
        {
            util = Utility.createInstance();
        }
        catch (ADOException e)
        {
            logger.error(e);
            throw new RuntimeException("Configuration problem: could not create instance of UtilityIfc");
        }
        return util;
    }

    //----------------------------------------------------------------------
    /**
        Returns the appropriate TenderUtilityIfc implementation
        @return
    **/
    //----------------------------------------------------------------------
    protected TenderUtilityIfc getTenderUtility()
    {
        TenderUtilityIfc util = null;
        try
        {
            util = TenderUtility.createInstance();
        }
        catch (ADOException e)
        {
            logger.error(e);
            throw new RuntimeException("Configuration problem: could not create instance of TenderUtilityIfc");
        }
        return util;
    }

    //----------------------------------------------------------------------
    /**
        Returns the appropriate TenderUtilityIfc implementation
        @return
    **/
    //----------------------------------------------------------------------
    protected JournalFactoryIfc getJournalFactory()
    {
        JournalFactoryIfc journalFactory = null;
        try
        {
            journalFactory = JournalFactory.getInstance();
        }
        catch (ADOException e)
        {
            logger.error(e);
            throw new RuntimeException("Configuration problem: could not create instance of JournalFactoryIfc");
        }
        return journalFactory;
    }

    public void setParameterManager(ParameterManagerIfc pm)
    {
        parameterManager = pm;
    }
}
