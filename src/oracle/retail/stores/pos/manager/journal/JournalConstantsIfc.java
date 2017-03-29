/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/manager/journal/JournalConstantsIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:35 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:28:47 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:22:57 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:12:10 PM  Robert Pearse   
 *
 *   Revision 1.2  2004/02/12 16:48:38  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:13  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 15:51:36   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.0   May 22 2003 17:02:48   jgs
 * Initial revision.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.manager.journal;

//--------------------------------------------------------------------------
/**
    This interface lists the constants used for translating to and from
    the XML format. <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
**/
//--------------------------------------------------------------------------
public interface JournalConstantsIfc
{
    /**
       revision number supplied by source-code-control system
    **/
    public static String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    
    public static final String ATTRIBUTE_NO_NAMESPACE_SCHEMA_LOCATION_TAG = "xsi:noNamespaceSchemaLocation";
    public static final String JOURNAL_SCHEMA_LOCATION        = "ejournal.xsd";
    public static final String ELEMENT_EJOURNAL               = "ejournal";
    public static final String ELEMENT_EJOURNAL_ENTRY         = "ejournalEntry";
    public static final String ELEMENT_START_TIME             = "startTime";
    public static final String ELEMENT_END_TIME               = "endTime";
    public static final String ELEMENT_NORMAL_COMPLETION      = "normalCompletion";
    public static final String ELEMENT_TYPE                   = "type";
    public static final String ELEMENT_TAPE                   = "tape";
    public static final String ATTRIBUTE_TYPE_TRANSACTION     = "transaction";
    public static final String ATTRIBUTE_TYPE_NON_TRANSACTION = "nontransaction";

}
