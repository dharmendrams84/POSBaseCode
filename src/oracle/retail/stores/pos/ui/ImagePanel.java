/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/ImagePanel.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:37 mszekely Exp $
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
package oracle.retail.stores.pos.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;

import javax.swing.JPanel;

import oracle.retail.stores.pos.ui.beans.ImageBeanIfc;

public class ImagePanel extends JPanel implements ImageBeanIfc
{  
    /** the background image */
    protected Image backgroundImage;
    
    //---------------------------------------------------------------------------
    /**
     *    Default constructor.
     */
    public ImagePanel()
    {
        super();
        setLayout(new BorderLayout() {
            /* This BorderLayout subclass maps a null constraint to CENTER.
             * Although the reference BorderLayout also does this, some VMs
             * throw an IllegalArgumentException.
             */
            public void addLayoutComponent(Component comp, Object constraints) {
                if (constraints == null) {
                    constraints = BorderLayout.CENTER;
                }
                super.addLayoutComponent(comp, constraints);
            }
        });
        setOpaque(true);
    }

    //--------------------------------------------------------------------------
    /**
     *  Gets the background image for this pane.
     *  @return the background image
     */
    public Image getBackgroundImage()
    {
        return backgroundImage;
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Sets the background image for this pane.
     *  @param propValue the name of the image resource file
     */
    public void setBackgroundImage(String propValue)
    {
        backgroundImage = UIUtilities.getImage(propValue, this);
    }

}
