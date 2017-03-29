/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/taximport/TaxRateRule.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:10 mszekely Exp $
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

import java.sql.Timestamp;

//-------------------------------------------------------------------------
/**
    This class serves as a Tax Rate Rule container. <P>
    @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @deprecated as of 13.3. Tax import is done through DIMP.
**/
//-------------------------------------------------------------------------
public class TaxRateRule extends TaxBaseObject
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = 7464730276223995019L;


    /**
      revision number of this class
    **/
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

    private String ruleTypeCode;
    private String aboveThresholdFlag;
    private float thresholdAmount;
    private float percentageAmount;
    private float rateAmount;
    private float minimumAmount;
    private float maximumAmount;
    private Timestamp effectiveTimestamp;
    private Timestamp expirationTimestamp;

    /**
     * Class constructor.
     */
    public TaxRateRule()
    {
    }

    /**
     * Creates a Tax Rule object and sets the object id.
     * @param String id.
     */
    public TaxRateRule(String id)
    {
        super(id);
    }

    /**
     * Gets the type code.
     * @return String type code.
     */
    public String getRuleTypeCode()
    {
        return ruleTypeCode;
    }

    /**
     * Gets the percentage amount.
     * @return float percentage amount.
     */
    public float getPercentageAmount()
    {
        return percentageAmount;
    }

    /**
     * Gets the rate amount.
     * @return float rate amount.
     */
    public float getRateAmount()
    {
        return rateAmount;
    }

    /**
     * Gets the minimum amount.
     * @return float minimum amount.
     */
    public float getMinimumAmount()
    {
        return minimumAmount;
    }

    /**
     * Gets the maximum amount.
     * @return float maximum amount.
     */
    public float getMaximumAmount()
    {
        return maximumAmount;
    }

    /**
     * Gets the above threshold amount flag.
     * @return String above threshold amount flag.
     */
    public String getAboveThresholdFlag()
    {
        return aboveThresholdFlag;
    }

    /**
     * Gets the threshold amount.
     * @return float threshold amount.
     */
    public float getThresholdAmount()
    {
        return thresholdAmount;
    }

    /**
     * Gets the effective timestamp.
     * @return String effective timestamp.
     */
    public Timestamp getEffectiveTimestamp()
    {
        return effectiveTimestamp;
    }

    /**
     * Gets the expiration timestamp.
     * @return String expiration timestamp.
     */
    public Timestamp getExpirationTimestamp()
    {
        return expirationTimestamp;
    }

    /**
     * Sets the type code.
     * @param String type code.
     */
    public synchronized void setRuleTypeCode(String s)
    {
        ruleTypeCode = s;
    }

    /**
     * Sets the percentage amount.
     * @param float percentage amount.
     */
    public synchronized void setPercentageAmount(float f)
    {
        percentageAmount = f;
    }

    /**
     * Sets the rate amount.
     * @param float rate amount.
     */
    public synchronized void setRateAmount(float f)
    {
        rateAmount = f;
    }

    /**
     * Sets the minimum amount.
     * @param float minimum amount.
     */
    public synchronized void setMinimumAmount(float f)
    {
        minimumAmount = f;
    }

    /**
     * Sets the maximum amount.
     * @param float maximum amount.
     */
    public synchronized void setMaximumAmount(float f)
    {
        maximumAmount = f;
    }

    /**
     * Sets the above threshold amount flag.
     * @param String above threshold amount flag.
     */
    public synchronized void setAboveThresholdFlag(String s)
    {
        aboveThresholdFlag = s;
    }

    /**
     * Sets the threshold amount.
     * @param float threshold amount.
     */
    public synchronized void setThresholdAmount(float f)
    {
        thresholdAmount = f;
    }

    /**
     * Sets the effective timestamp
     * @param String effective timestamp.
     */
    public synchronized void setEffectiveTimestamp(Timestamp t)
    {
        effectiveTimestamp = t;
    }

    /**
     * Sets the expiration timestamp
     * @param String expiration timestamp.
     */
    public synchronized void setExpirationTimestamp(Timestamp t)
    {
        expirationTimestamp = t;
    }


}

