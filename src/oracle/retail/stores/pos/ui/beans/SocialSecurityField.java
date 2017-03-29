/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ui/beans/SocialSecurityField.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:44 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ui.beans;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

//-------------------------------------------------------------------------
/**
   This field allows Social Security numbers, XXX-XX-XXXX, to be input.
   @author  $KW=@(#); $Own=mheath; $EKW;
   @version $KW=@(#); $Ver=rapp.q_2.5:2; $EKW;
*/
//-------------------------------------------------------------------------
public class SocialSecurityField extends ValidatingTextField
{
    /** revision number supplied by Team Connection */
    public static final String revisionNumber = "$KW=@(#); $Ver=rapp.q_2.5:2; $EKW;";

    //---------------------------------------------------------------------
    /**
       Constructor.
    */
    //---------------------------------------------------------------------
    public SocialSecurityField()
    {
        this("");
    }

    //---------------------------------------------------------------------
    /**
       Constructor.
       @param number the Social Security
    */
    //---------------------------------------------------------------------
    public SocialSecurityField(String number)
    {
        super(number);
        if (getDocument() instanceof NaPhoneNumDocument)
        {
            SocialSecurityDocument doc = (SocialSecurityDocument)getDocument();
            ActionListener l = new ActionListener()
            {
                public void actionPerformed(ActionEvent evt)
                {
                    int pos = getCaretPosition();
                    if (evt.getID() == SocialSecurityDocument.PART1)
                    {
                        if (pos < getText().length())
                        {
                            setCaretPosition(pos + 1);
                        }
                    }
                    else if (evt.getID() == SocialSecurityDocument.PART2)
                    {
                        if (pos < getText().length())
                        {
                            setCaretPosition(pos + 1);
                        }
                    }  
                }
            };
            doc.addFieldListener(l);
        }
    }

    //---------------------------------------------------------------------
    /**
       Gets the default model for the decimal text field
       @return the model for length constrained decimal fields
    */
    //---------------------------------------------------------------------
    protected Document createDefaultModel()
    {
        return new SocialSecurityDocument();
    }

    //---------------------------------------------------------------------
    /**
       Gets the Social Security without the delimeters.
       @return the phone number without the delimeters
    */
    //---------------------------------------------------------------------
    public String getSocialSecurityNumber()
    {
        String soc = "";
        if (getDocument() instanceof SocialSecurityDocument)
        {
            SocialSecurityDocument doc = (SocialSecurityDocument)getDocument();
            soc = doc.getPart1() + doc.getPart2() + doc.getPart3();
        }
        return soc;
    }

    //---------------------------------------------------------------------
    /**
       Determines whether the current field information is valid and
       returns the result.
       @return true if the current field entry is valid, false otherwise
    */
    //---------------------------------------------------------------------
    public boolean isInputValid()
    {
        boolean rv = false;
        if (getSocialSecurityNumber().length() == 9)
        {
            rv = true;
        }
        return rv;
    }

    //---------------------------------------------------------------------
    /**
       Sets the Social Security number without the delimeters.
       @param socialSecurity the Social Security number without the delimeters
    */
    //---------------------------------------------------------------------
    public void setSocialSecurity(String socialSecurity)
    {
        setText(socialSecurity);
    }

    //---------------------------------------------------------------------
    /**
       Sets the text of the field.
       @param socialSecurity the Social Security number
    */
    //---------------------------------------------------------------------
    public void setText(String socialSecurity)
    {
        try
        {
            getDocument().remove(0, getText().length());
        }
        catch (BadLocationException excp)
        {
            super.setText("");
        }
        super.setText(socialSecurity);
    }
}
