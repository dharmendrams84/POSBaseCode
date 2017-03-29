/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/taximport/XMLFileFilter.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:10 mszekely Exp $
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

// java imports
import java.io.File;

import javax.swing.filechooser.FileFilter;

//--------------------------------------------------------------------------
/**
     This class provides file filter for XML Files only.
     <P>
     @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @deprecated as of 13.3. Tax import is done through DIMP.
**/
//--------------------------------------------------------------------------
public class XMLFileFilter extends FileFilter
{

    public boolean accept(File f)
    {
        if (f.isDirectory())
        {
            return true;
        }

        if (f.getName().indexOf(".xml") != -1 ||
            f.getName().indexOf(".XML") != -1)
        {
            return true;
        }

        return false;

    }

    public String getDescription()
    {
        return "XML Files";
    }

}


