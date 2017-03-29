/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/ImageBeanIfc.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:45 mszekely Exp $
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
package oracle.retail.stores.pos.ui.beans;

import java.awt.Image;

//------------------------------------------------------------------------------
/**
 *    Interface for panels that will implement the background
 *    image feature. Used by component look and feel objects
 *    to render images.
 */
//------------------------------------------------------------------------------
public interface ImageBeanIfc
{

//------------------------------------------------------------------------------
/**
 *  Gets the background image associated with a component.
 *  @return the background image
 */
    public Image getBackgroundImage();

//------------------------------------------------------------------------------
/**
 *  Sets the background image for a component.
 *  @param propValue the name of the image resource file
 */
    public void setBackgroundImage(String imageName);
    
}
