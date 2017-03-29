/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/context/ContextFactory.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:42 mszekely Exp $
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
 *  3    360Commerce 1.2         3/31/2005 4:27:30 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:20:23 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:10:11 PM  Robert Pearse   
 *
 * Revision 1.3  2004/04/08 20:33:03  cdb
 * @scr 4206 Cleaned up class headers for logs and revisions.
 *
 * 
 * Rev 1.0 Nov 04 2003 11:10:02 epd Initial revision.
 * 
 * Rev 1.0 Oct 17 2003 12:30:00 epd Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.context;

/**
 * Singleton class to contain cached context. It is intended that this context
 * will be updated by Launch Shuttles with the bus from the new service.
 */
public class ContextFactory
{
    /**
     * Singleton instance
     */
    private static ContextFactory instance;

    /**
     * Cached context.
     */
    protected ADOContextIfc context;

    /**
     * Protected to enforce singleton pattern
     */
    protected ContextFactory()
    {
    }

    public static ContextFactory getInstance()
    {
        synchronized (ContextFactory.class)
        {
            if (instance == null)
            {
                instance = new ContextFactory();
            }
            return instance;
        }
    }
    /**
     * @return
     */
    public synchronized ADOContextIfc getContext()
    {
        return context;
    }

    /**
     * @param context
     */
    public synchronized void setContext(ADOContextIfc context)
    {
        this.context = context;
    }

}
