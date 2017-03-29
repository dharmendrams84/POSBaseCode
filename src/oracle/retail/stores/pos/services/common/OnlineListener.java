/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/common/OnlineListener.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:05:53 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    blarsen   03/09/10 - Since the offline/online condition was detected
 *                         outside of the normal tour processing, do not unlock
 *                         the container for the status update. This can cause
 *                         premature unlocking of user input and cause pos
 *                         crashes when the tour gets confused by unexpected
 *                         uer input.
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 * $ 7    .v12x      1.4.1.1     2/26/2008 1:39:09 PM   Christian Greene Added
 * $      functionality to DataManager to skip RemoteDT when it is offline.
 * $      Ensured that OnlineListener notifies UI every time. Restructured
 * $      joins in PLU SQL to perform better.
 * $ 6    .v12x      1.4.1.0     1/13/2008 1:58:51 AM   Brett J. Larsen
 * $      CR 29867 - non-transactional e-journal entries causing problems
 * $
 * $      Reincluding the online state change e-jouirnal entry.
 * $      This entry was removed in v7x to avoid a non-unique key vioilation.
 * $      However the root cause was not invistigate.
 * $      The fix for this CR shold prevent the non-unique condition from
 * $      occurring.
 * $
 * $
 * $ 5    360Commerce 1.4         6/27/2007 9:43:29 AM   Naveen Ganesh   Added
 * $      space between Date and Time while displaying System is Offline
 * $      status in EJournal
 * $ 4    360Commerce 1.3         4/25/2007 8:52:34 AM   Anda D. Cadar   I18N
 * $      merge
 * $
 * $ 3    360Commerce 1.2         3/31/2005 4:29:11 PM   Robert Pearse
 * $ 2    360Commerce 1.1         3/10/2005 10:23:45 AM  Robert Pearse
 * $ 1    360Commerce 1.0         2/11/2005 12:12:49 PM  Robert Pearse
 * $$$
 * ===========================================================================
 */
package oracle.retail.stores.pos.services.common;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import oracle.retail.stores.utility.I18NConstantsIfc;
import oracle.retail.stores.utility.I18NHelper;
import oracle.retail.stores.utility.JournalConstantsIfc;

import org.apache.log4j.Logger;

import oracle.retail.stores.commerceservices.common.datetime.DateTimeServiceIfc;
import oracle.retail.stores.commerceservices.common.datetime.DateTimeServiceLocator;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.foundation.manager.ifc.JournalManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.data.OnlineListenerIfc;
import oracle.retail.stores.foundation.tour.gate.Gateway;
import oracle.retail.stores.foundation.utility.LocaleMap;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;

//--------------------------------------------------------------------------
/**
    This class registers with the DataManager to get an event when any of the
    transactions goes off line.

    It keeps a reference to the UI Manager and calls it to change the value
    of the offline status on the screen when it receives the online/offline
    event.

    <p>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public class OnlineListener implements OnlineListenerIfc
{
    /**
        The logger to which log messages will be sent.
    **/
    protected static Logger logger = Logger.getLogger(oracle.retail.stores.pos.services.common.OnlineListener.class);

    /**
       revision number of this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    /**
       A reference to the UI manager
    **/
    protected POSUIManagerIfc ui = null;

    /**
       A reference to the journal manager
    **/
    protected JournalManagerIfc journal = null;

    /**
       A reference to the UI manager
    **/
    protected boolean lastOnlineStatus = true;

    //----------------------------------------------------------------------
    /**
       Constructor
       <p>
       @param  POSUIManagerIfc ui manager reference
    **/
    //----------------------------------------------------------------------
    public OnlineListener(POSUIManagerIfc value)
    {
        ui = value;
        journal = (JournalManagerIfc) Gateway.getDispatcher().getManager(JournalManagerIfc.TYPE);
    }

    //----------------------------------------------------------------------
    /**
       This method is the callback for the DataManager to indicate
       that at least one transaction is offline.
       <p>
       @param  Boolean false if at least one transaction is offline.
    **/
    //----------------------------------------------------------------------
    public void onlineStateChange(boolean online)
    {
    	// always notify the UI.
        //ui.statusChanged(POSUIManagerIfc.DATA_MANAGER_STATUS, online);

        if(lastOnlineStatus != online)
        {
        	ui.statusChanged(POSUIManagerIfc.DATA_MANAGER_STATUS, online, POSUIManagerIfc.DO_NOT_UNLOCK_CONTAINER);
            if(journal != null)
            {
            	Locale journalLocale = LocaleMap.getLocale(LocaleConstantsIfc.JOURNAL);
                DateTimeServiceIfc dateTimeService = DateTimeServiceLocator.getDateTimeService();
                Date now = new Date();
                String datetime = dateTimeService.formatDate(now, journalLocale, DateFormat.SHORT) + " " +
                                     dateTimeService.formatTime(now, journalLocale, DateFormat.SHORT);
                String status = online ? I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.ONLINE_LABEL, null) : I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.OFFLINE_LABEL, null);
                journal.journal(datetime + I18NHelper.getString(I18NConstantsIfc.EJOURNAL_TYPE, JournalConstantsIfc.SYSTEM_IS_LABEL, null) + status);
            }
            else
            {
                logger.error(
                "No JournalManager found");
            }
        }
        lastOnlineStatus = online;
    }

    //----------------------------------------------------------------------
    /**
       Returns a string representation of the object.
       <P>
       @return String representation of object
    **/
    //----------------------------------------------------------------------
    public String toString()
    {
    	StringBuffer buff = new StringBuffer("OnlineListener@");
        buff.append(hashCode());
        buff.append("[revisionNumber=");
        buff.append(revisionNumber);
        buff.append("lastOnlineStatus,=");
        buff.append(lastOnlineStatus);
        buff.append("]");
        return buff.toString();
    }

    //----------------------------------------------------------------------
    /**
       Returns the revision number of the class.
       <P>
       @return String representation of revision number
    **/
    //----------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return(revisionNumber);
    }
}
