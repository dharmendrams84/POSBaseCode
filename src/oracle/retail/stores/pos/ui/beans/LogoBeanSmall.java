/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/LogoBeanSmall.java /rgbustores_13.4x_generic_branch/2 2011/11/17 14:44:53 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   11/17/11 - fix LogoBean so that its subclass can have its image
 *                         in a labal and not painted.
 *    cgreene   05/28/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import java.util.Properties;

/**
 * Bean for displaying the small logo in the bottom right corner.
 * 
 * @version $Revision: /rgbustores_13.4x_generic_branch/2 $
 */
public class LogoBeanSmall extends LogoBean
{
    private static final long serialVersionUID = 7764529585308024013L;
    /** revision number supplied by Team Connection */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

    /**
     * Default Constructor for the LogoBeanSmall. If there is no image name
     * given, default to properties or cornerstone logo or null.
     */
    public LogoBeanSmall()
    {
        this("logo_small.gif");
    }

    /**
     * Default Constructor for the LogoBeanSmall. Creates a panel from an image.
     * 
     * @param imgSrc The filename of the image.
     */
    public LogoBeanSmall(String inputImageName)
    {
        super(inputImageName, false);
        LOG_ENTRY = "LogoBeanSmall";
        UI_PREFIX = "LogoBeanSmall";
    }

    /**
     * Set the properties object on the class.
     * 
     * @param props a properties object.
     */
    public void setProps(Properties props)
    {
        if (props != null)
        {
            imageSource = props.getProperty("logoSmallImg");
        }
        if (imageSource != null)
        {
            configure();
        }
    }
}
