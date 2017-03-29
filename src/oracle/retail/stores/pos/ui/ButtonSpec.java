/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/ButtonSpec.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:37 mszekely Exp $
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
 *    3    360Commerce 1.2         3/31/2005 4:27:18 PM   Robert Pearse   
 *    2    360Commerce 1.1         3/10/2005 10:19:55 AM  Robert Pearse   
 *    1    360Commerce 1.0         2/11/2005 12:09:43 PM  Robert Pearse   
 *
 *   Revision 1.4  2004/09/23 00:07:10  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.3  2004/02/12 16:52:11  mcs
 *   Forcing head revision
 *
 *   Revision 1.2  2004/02/11 21:52:28  rhafernik
 *   @scr 0 Log4J conversion and code cleanup
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:21  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 * 
 *    Rev 1.0   Aug 29 2003 16:09:18   CSchellenger
 * Initial revision.
 * 
 *    Rev 1.1   Aug 15 2002 17:55:38   baa
 * apply foundation  updates to UISubsystem
 *  
 * Resolution for POS SCR-1769: 5.2 UI defects resulting from change to java 1.4
 * 
 *    Rev 1.0   Apr 29 2002 14:44:50   msg
 * Initial revision.
 * 
 *    Rev 1.0   Mar 18 2002 11:51:34   msg
 * Initial revision.
 * 
 *    Rev 1.2   Feb 23 2002 15:04:08   mpm
 * Re-started internationalization initiative.
 * Resolution for POS SCR-351: Internationalization
 *
 *    Rev 1.1   Jan 19 2002 10:28:40   mpm
 * Initial implementation of pluggable-look-and-feel user interface.
 * Resolution for POS SCR-798: Implement pluggable-look-and-feel user interface
 *
 *    Rev 1.0   Sep 21 2001 11:33:42   msg
 * Initial revision.
 *
 *    Rev 1.1   Sep 17 2001 13:16:06   msg
 * header update
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui;
// Java imports
import java.io.Serializable;

import oracle.retail.stores.foundation.utility.Util;

//--------------------------------------------------------------------------
/**
    The assignment specification class contains all of the
    data necessary to add a bean to an area of the display.
    <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
    @deprecated as of release 5.5 replaced by @linkoracle.retail.stores.foundation.manager.gui.ButtonSpec
**/
//--------------------------------------------------------------------------
public class ButtonSpec implements Serializable
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -1749861553175070584L;

    /**
       revision number supplied by Team Connection
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";
    /**
        Store the action name
    **/
    protected String actionName = null;
    /**
        Store the action listener
    **/
    protected String actionListenerName = null;
    /**
        Store key name
    **/
    protected String keyName = null;
    /**
        Store enabled flag
    **/
    protected Boolean enabledFlag = null;
    /**
        Store label
    **/
    protected String label = null;
    /**
        label tag
    **/
    protected String labelTag = null;
    /**
        Store icon
    **/
    protected String icon = null;

    //--------------------------------------------------------------------------
    /**
        Clones the object. <P>

        @return ButtonSpec copy of button
    **/
    //--------------------------------------------------------------------------
    public Object clone()
    {
        ButtonSpec newButton = new ButtonSpec();
        newButton.setActionName(this.getActionName());
        newButton.setEnabled(enabledFlag.booleanValue());
        newButton.setKeyName(this.getKeyName());
        newButton.actionListenerName = this.actionListenerName;
        newButton.setLabel(this.getLabel());
        newButton.setLabelTag(this.getLabelTag());
        newButton.setIconName(this.getIconName());
        return newButton;
    }

    //--------------------------------------------------------------------------
    /**
        Gets the action name.
        <P>
        @return String action name
    **/
    //--------------------------------------------------------------------------
        public String getActionName()
        {
                return actionName;
        }

    //--------------------------------------------------------------------------
    /**
        Gets the action listener name.
        <P>
        @return String action name
    **/
    //--------------------------------------------------------------------------
        public String getActionListenerName()
        {
                return actionListenerName;
        }

    //--------------------------------------------------------------------------
    /**
        Gets the enabled flag. <P>

        @return String enabled
    **/
    //--------------------------------------------------------------------------

        public boolean getEnabled()
        {
            if (enabledFlag == null)
            {
                return false;
            }
            else
            {
            return enabledFlag.booleanValue();
        }
        }

    //--------------------------------------------------------------------------
    /**
        Gets the enabled flag. <P>

        @return Boolean class
    **/
    //--------------------------------------------------------------------------

        public Boolean getEnabledFlag()
        {
        return enabledFlag;
        }

    //--------------------------------------------------------------------------
    /**
        Gets the icon name

        @return String icon name
    **/
    //--------------------------------------------------------------------------
        public String getIconName()
        {
        return icon;
        }

    //--------------------------------------------------------------------------
    /**
        Gets the key name

        @return String key name
    **/
    //--------------------------------------------------------------------------
        public String getKeyName()
        {
        return keyName;
        }

    //--------------------------------------------------------------------------
    /**
        Gets the label
        @return String label name
    **/
    //--------------------------------------------------------------------------
    public String getLabel()
    {
        return label;
    }

    //--------------------------------------------------------------------------
    /**
        Gets the label tag,
        @return String label tag name
    **/
    //--------------------------------------------------------------------------
    public String getLabelTag()
    {
        return labelTag;
    }

    //--------------------------------------------------------------------------
    /**
        Tests for icon type. <P>

        @return boolean true if icon is set.
    **/
    //--------------------------------------------------------------------------
        public boolean isIcon()
        {
            return (icon != null);
        }

    //--------------------------------------------------------------------------
    /**
        Tests for label type. <P>

        @return boolean true if label is set.
    **/
    //--------------------------------------------------------------------------
        public boolean isLabel()
        {
            return (label != null);
        }

    //--------------------------------------------------------------------------
    /**
        Sets the action name. <P>

        @param propValue String action name
    **/
    //--------------------------------------------------------------------------
        public void setActionName(String propValue)
        {
            actionName = propValue;
        }

    //--------------------------------------------------------------------------
    /**
        Sets the action listener name.
        <P>
        @param propValue String action listener name
    **/
    //--------------------------------------------------------------------------
        public void setActionListenerName(String propValue)
        {
                actionListenerName = propValue;
        }

    //--------------------------------------------------------------------------
    /**
        Sets the icon name.

        @param propValue String icon name
    **/
    //--------------------------------------------------------------------------
        public void setIconName(String propValue)
        {
            icon = propValue;
        }

    //--------------------------------------------------------------------------
    /**
        Sets the key name.

        @param propValue String key name
    **/
    //--------------------------------------------------------------------------
        public void setKeyName(String propValue)
        {
            keyName = propValue;
        }

    //--------------------------------------------------------------------------
    /**
        Sets the enabled flag. <P>

        @param propValue String enabled flag
    **/
    //--------------------------------------------------------------------------

        public void setEnabled(String propValue)
        {
        enabledFlag = new Boolean(propValue);
        }

    //--------------------------------------------------------------------------
    /**
        Sets the enabled flag. <P>

        @param enabled true of false
    **/
    //--------------------------------------------------------------------------

        public void setEnabled(boolean enabled)
        {
        enabledFlag = new Boolean(enabled);
        }

    //--------------------------------------------------------------------------
    /**
        Sets the label. <P>
        @param propValue String label
    **/
    //--------------------------------------------------------------------------
    public void setLabel(String propValue)
    {
        label = propValue;
    }

    //--------------------------------------------------------------------------
    /**
        Sets the label tag. <P>
        @param propValue String label tag
    **/
    //--------------------------------------------------------------------------
    public void setLabelTag(String propValue)
    {
        labelTag = propValue;
    }

    //---------------------------------------------------------------------
    /**
       Retrieves the Team Connection revision number. <P>
       @return String representation of revision number
    */
    //---------------------------------------------------------------------
    public String getRevisionNumber()
    {
        return(revisionNumber);
    }

    //---------------------------------------------------------------------
    /**
        @return String a representation of the class.
    **/
    //---------------------------------------------------------------------
    public String toString()
    {
        StringBuffer tmpString = new StringBuffer("Class:  Register (Revision ");
        tmpString.append(getRevisionNumber());
        tmpString.append(") @");
        tmpString.append(hashCode());
        tmpString.append(Util.EOL);

        // Display area spec name and bean spec name
        tmpString.append("[");
        tmpString.append(actionName);
        tmpString.append("-");
        tmpString.append(keyName);
        tmpString.append("-");
        tmpString.append(enabledFlag);
        if (label != null)
        {
            tmpString.append("-");
            tmpString.append(label);
        }

        if (icon != null)
        {
            tmpString.append("-");
            tmpString.append(icon);
        }

        tmpString.append("]");
        return(tmpString.toString());
    }
}
