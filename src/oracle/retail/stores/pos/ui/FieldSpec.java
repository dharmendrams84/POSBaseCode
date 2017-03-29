/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/FieldSpec.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:07:01 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:28:10 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:21:37 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:11:02 PM  Robert Pearse   
 *
 *   Revision 1.5  2004/09/23 00:07:10  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.4  2004/04/08 22:14:55  cdb
 *   @scr 4206 Cleaned up class headers for logs and revisions.
 *
 * 
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui;

// Java imports
import java.io.Serializable;

//------------------------------------------------------------------------------
/**
 *    The field specification class contains all of the
 *    data necessary to create a data entry component.
 * @deprecated as of release 5.5 replaced by @linkoracle.retail.stores.foundation.manager.gui.FieldSpec
 */
//------------------------------------------------------------------------------

public class FieldSpec implements Serializable
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -6676852090796312082L;

    /** revision number supplied by Team Connection */
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    
    /** the name of this component's class */
    protected String className = "";
    
    /** whether or not this component is enabled */
    protected Boolean enabled = null;
    
    /** the identifying name of this component */
    protected String fieldName = "";
    
    /** the type of this component */
    protected String fieldType = "";
    
    /** the property tag that represents the label text */
    protected String labelTag = "";
    
    /** the actual text or this component's label */
    protected String labelText = "";
    
    /** a comma delimited list of parameters for the field */
    protected String paramList = "";

    /** whether or not this component is required */
    protected Boolean required = null;
    
    //--------------------------------------------------------------------------
    /**
     *  Creates a clone of this object.
     *  @return a copy of this field spec
     */
    public Object clone()
    {
        FieldSpec newSpec = new FieldSpec();
        
        newSpec.setClassName(this.getClassName());
        newSpec.setEnabled(enabled.booleanValue());
        newSpec.setFieldName(this.getFieldName());
        newSpec.setFieldType(this.getFieldType());
        newSpec.setLabelTag(this.getLabelTag());
        newSpec.setLabelText(this.getLabelText());
        newSpec.setRequired(required.booleanValue());
        
        return newSpec;
    }

    //--------------------------------------------------------------------------
    /**
     *  Gets the name of the class to instantiate for this component. This 
     *  is used only for components that are not created by the fieldType
     *  attribute and the UIFactory.
     *  @return the class name
     */
    public String getClassName()
    {
        return className;
    }

    //--------------------------------------------------------------------------
    /**
     *  Gets the identifier name of this component. 
     *  @return the name of this component
     */
    public String getFieldName()
    {
        return fieldName;
    }

    //--------------------------------------------------------------------------
    /**
     *  Gets the type of this component. This is used by the UIFactory to
     *  create and configure the component.
     *  @return the type of this component
     */
    public String getFieldType()
    {
        return fieldType;
    }

    //--------------------------------------------------------------------------
    /**
     *  Gets the property bundle tag that will be used to look up
     *  the localized text for this component's label. 
     *  @return the label tag
     */
    public String getLabelTag()
    {
        return labelTag;
    }

    //--------------------------------------------------------------------------
    /**
     *  Gets the plain text for this component's label. This method should
     *  not be used after the POS application is internationalized.
     *  @return the label text
     */
    public String getLabelText()
    {
        return labelText;
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Gets the comma-delimited list of parameters for this component. 
     *  This list is parsed into method parameters for the UIFactory.
     *  @return the list of parameters
     */
    public String getParamList()
    {
        return paramList;
    }

    //--------------------------------------------------------------------------
    /**
     *  Gets the enabled flag for this component. 
     *  @return true if component is enabled, false if not
     */
    public boolean isEnabled()
    {
        return enabled.booleanValue();
    }

    //--------------------------------------------------------------------------
    /**
     *  Gets the required flag for this component. Note that this value 
     *  will only have an effect on components that implement 
     *  ValidatingFieldIfc.
     *  @return true if component is required, false if not
     */
    public boolean isRequired()
    {
        return required.booleanValue();
    }

    //--------------------------------------------------------------------------
    /**
     *  Sets the name of the class to instantiate for this component. This 
     *  is used only for components that are not created by the fieldType
     *  attribute and the UIFactory.
     *  @param propValue the class name
     */
    public void setClassName(String propValue)
    {
        className = propValue;
    }

    //--------------------------------------------------------------------------
    /**
     *  Sets the enabled flag for this component. 
     *  @param propValue string value that can convert into a boolean
     */
    public void setEnabled(String propValue)
    {
        enabled = new Boolean(propValue);
    }

    //--------------------------------------------------------------------------
    /**
     *  Sets the enabled flag for this component. 
     *  @param propValue a boolean value
     */
    public void setEnabled(boolean propValue)
    {
        enabled = new Boolean(propValue);
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Sets the identifier name of this component. 
     *  @param propValue the name of this component
     */
    public void setFieldName(String propValue)
    {
        fieldName = propValue;
    }

    //--------------------------------------------------------------------------
    /**
     *  Sets the type of this component. This is used by the UIFactory to
     *  create and configure the component.
     *  @param propValue the type of this component
     */
    public void setFieldType(String propValue)
    {
        fieldType = propValue;
    }

    //--------------------------------------------------------------------------
    /**
     *  Sets the property bundle tag that will be used to look up
     *  the localized text for this component's label. 
     *  @param propValue the label tag
     */
    public void setLabelTag(String propValue)
    {
        labelTag = propValue;
    }

    //--------------------------------------------------------------------------
    /**
     *  Sets the plain text for this component's label. This method should
     *  not be used after the POS application is internationalized.
     *  @param propValue the label text
     */
    public void setLabelText(String propValue)
    {
        labelText = propValue;
    }

    //--------------------------------------------------------------------------
    /**
     *  Sets the comma-delimited list of parameters for this component. 
     *  This list is parsed into method parameters for the UIFactory.
     *  @param propValue the list of parameters
     */
    public void setParamList(String propValue)
    {
        paramList = propValue;
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Sets the required flag for this component. Note that this value 
     *  will only have an effect on components that implement 
     *  ValidatingFieldIfc.
     *  @param propValue string value that can convert into a boolean
     */
    public void setRequired(String propValue)
    {
        required = new Boolean(propValue);
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Sets the required flag for this component.
     *  @param propValue a boolean value
     */
    public void setRequired(boolean propValue)
    {
        required = new Boolean(propValue);
    }

}
