/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/taximport/TaxBaseObject.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:10 mszekely Exp $
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
package oracle.retail.stores.pos.services.taximport;

import java.io.Serializable;

//-------------------------------------------------------------------------
/**
    This abstract class represents a Tax Base Object. <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @deprecated as of 13.3. Tax import is done through DIMP.
 */
//-------------------------------------------------------------------------
public abstract class TaxBaseObject implements Serializable {
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = 1275792669436327676L;


    /**
      revision number of this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    protected String id;
    protected String name;

    /**
     *  Class constructor.
     */
    public TaxBaseObject() {}

    /**
     *  Creates a Tax Base Object and sets the object id.
     *  @param String id of object.
     */
    public TaxBaseObject(String id) {
        setID(id);
    }

    /**
     *  Shows the id for the object name.
     *  This is used by the JTree to display the node names.
     *  @return String id and name concatenated together.
     */
    public String toString() {
        return id;
    }

    /**
     *  Gets the object ID.
     *  @return String object id.
     */
    public String getID()
    {
        return id;
    }

    /**
     *  Gets the object name.
     *  @return String object name.
     */
    public String getName()
    {
        return name;
    }

    /**
     *  Sets the object ID.
     *  @param String object id.
     */
    public synchronized void setID(String s)
    {
        id = s;
    }

    /**
     *  Sets the object name.
     *  @param String object name.
     */
    public synchronized void setName(String s)
    {
        name = s;
    }


}

