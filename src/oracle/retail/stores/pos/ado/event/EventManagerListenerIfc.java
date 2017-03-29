/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/event/EventManagerListenerIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:40 mszekely Exp $
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
 *  3    360Commerce 1.2         3/31/2005 4:28:07 PM   Robert Pearse   
 *  2    360Commerce 1.1         3/10/2005 10:21:31 AM  Robert Pearse   
 *  1    360Commerce 1.0         2/11/2005 12:10:57 PM  Robert Pearse   
 *
 * Revision 1.3  2004/04/08 20:33:03  cdb
 * @scr 4206 Cleaned up class headers for logs and revisions.
 *
 * 
 * Rev 1.0 Nov 04 2003 11:10:36 epd Initial revision.
 * 
 * Rev 1.0 Oct 17 2003 12:30:28 epd Initial revision.
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.event;

/**
 * Defines the contract for classes that are intended to be registered as
 * listeners with the EventManager.
 */
public interface EventManagerListenerIfc
{
    /**
     * Called by the EventManager when an event occurs.
     * 
     * @param event
     *            The event that is being broadcast.
     */
    public void eventNotification(ManagedEvent event);
}
