/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/taximport/TaxGroupRule.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:10 mszekely Exp $
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

import java.util.ArrayList;


/**
 *   This class serves as a Tax Group Rule container. <P>
 *   @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @deprecated as of 13.3. Tax import is done through DIMP.
 */

public class TaxGroupRule extends TaxBaseObject
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = 7670616851762917145L;


    /**
      revision number of this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    private String taxAuthorityID;
    private ArrayList taxRuleList;
    private String grossAmountFlag;
    private String usageCode;
    private String compoundSeq;

    /**
     * Class constructor.
     */
    public TaxGroupRule()
    {
    }

    /**
     *  Sets the object id.
     *  @param String object id.
     */
    public TaxGroupRule(String id)
    {
        super(id);
    }

    /**
     *  Show the name of the object.
     *  @return String id and name concatenated together.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Auth: ");
        sb.append(getTaxAuthorityID());
        sb.append(" Group: ");
        sb.append(getID());
        return sb.toString();
    }

    /**
     *  Gets the Tax Authority ID.
     *  @return String Tax Authority id.
     */
    public String getTaxAuthorityID()
    {
        return taxAuthorityID;
    }

    /**
     *  Gets the Tax Rate Rules.
     *  @return ArrayList containing Tax Rate Rule objects.
     */
    public ArrayList getTaxRateRules()
    {
        return taxRuleList;
    }

    /**
     *  Gets the gross amount flag.
     *  @return String gross amount flag.
     */
    public String getGrossAmountFlag()
    {
        return grossAmountFlag;
    }

    /**
     *  Gets the rule usage code.
     *  @return String rule usage code.
     */
    public String getUsageCode()
    {
        return usageCode;
    }

    /**
     *  Gets the compound sequence number.
     *  @return String compound sequence number.
     */
    public String getCompoundSeq()
    {
        return compoundSeq;
    }

    /**
     *  Sets the Tax Authority id.
     *  @param String Tax Authority id.
     */
    public synchronized void setTaxAuthorityID(String s)
    {
        taxAuthorityID = s;
    }

    /**
     *  Sets the Tax Rate Rules.
     *  @param ArrayList containing Tax Rate Rule objects.
     */
    public synchronized void setTaxRateRules(ArrayList l)
    {
        taxRuleList = l;
    }

    /**
     *  Sets the gross amount flag.
     *  @param String gross amount flag.
     */
    public synchronized void setGrossAmountFlag(String s)
    {
        grossAmountFlag = s;
    }

    /**
     *  Sets the rule usage code.
     *  @param String rule usage code.
     */
    public synchronized void setUsageCode(String s)
    {
        usageCode = s;
    }

    /**
     *  Sets the compound sequence number.
     *  @param String compound sequence number.
     */
    public synchronized void setCompoundSeq(String s)
    {
        compoundSeq = s;
    }

}

