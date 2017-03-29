/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/ado/tender/TenderTypeEnum.java /rgbustores_13.4x_generic_branch/7 2011/10/13 15:15:28 sgu Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    sgu       10/12/11 - create house account tender correctly from legacy
 *                         tender line item
 *    sgu       09/08/11 - add house account as a refund tender
 *    ohorne    06/08/11 - added HOUSE_ACCOUNT enum
 *    cgreene   05/27/11 - move auth response objects into domain
 *    blarsen   05/20/11 - Changed TenderType from int constants to a real
 *                         enum.
 *    cgreene   05/27/10 - convert to oracle packaging
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *    4    360Commerce 1.3         9/20/2007 12:09:12 PM  Rohit Sachdeva
 *         28813: Initial Bulk Migration for Java 5 Source/Binary
 *         Compatibility of All Products
 *    3    360Commerce 1.2         3/31/2005 4:30:26 PM   Robert Pearse
 *    2    360Commerce 1.1         3/10/2005 10:26:05 AM  Robert Pearse
 *    1    360Commerce 1.0         2/11/2005 12:14:58 PM  Robert Pearse
 *
 *   Revision 1.5.2.1  2004/11/15 22:27:36  bwf
 *   @scr 7671 Create tender from rdo instead of class.  This is necessary because ADO's are not 1:1 with RDOs.
 *
 *   Revision 1.5  2004/09/23 00:07:13  kmcbride
 *   @scr 7211: Adding static serialVersionUIDs to all POS Serializable objects, minus the JComponents
 *
 *   Revision 1.4  2004/05/21 20:27:59  crain
 *   @scr 5108 Tender Redeem_Redeem Foreign Gift Certificate Receipt Incorrect
 *
 *   Revision 1.3  2004/05/20 19:48:52  crain
 *   @scr 5108 Tender Redeem_Redeem Foreign Gift Certificate Receipt Incorrect
 *
 *   Revision 1.2  2004/02/12 16:47:55  mcs
 *   Forcing head revision
 *
 *   Revision 1.1.1.1  2004/02/11 01:04:11  cschellenger
 *   updating to pvcs 360store-current
 *
 *
 *
 *    Rev 1.1   Feb 05 2004 13:46:46   rhafernik
 * log4j changes
 *
 *    Rev 1.0   Nov 04 2003 11:13:20   epd
 * Initial revision.
 *
 *    Rev 1.6   Oct 30 2003 20:35:04   epd
 * added Alternate as valid tender type
 *
 *    Rev 1.5   Oct 25 2003 16:07:10   blj
 * added Money Order Tender
 *
 *    Rev 1.1   Oct 20 2003 16:30:18   epd
 * added definition for Mall Certificate
 *
 *    Rev 1.0   Oct 17 2003 12:33:52   epd
 * Initial revision.
 *
 * ===========================================================================
 */
package oracle.retail.stores.pos.ado.tender;

import java.util.HashMap;
import java.util.Iterator;

import oracle.retail.stores.domain.manager.payment.AuthorizationConstantsIfc.TenderType;
import oracle.retail.stores.domain.tender.TenderCashIfc;
import oracle.retail.stores.domain.tender.TenderChargeIfc;
import oracle.retail.stores.domain.tender.TenderCheckIfc;
import oracle.retail.stores.domain.tender.TenderCouponIfc;
import oracle.retail.stores.domain.tender.TenderDebitIfc;
import oracle.retail.stores.domain.tender.TenderGiftCardIfc;
import oracle.retail.stores.domain.tender.TenderGiftCertificateIfc;
import oracle.retail.stores.domain.tender.TenderLineItemIfc;
import oracle.retail.stores.domain.tender.TenderMailBankCheckIfc;
import oracle.retail.stores.domain.tender.TenderMoneyOrderIfc;
import oracle.retail.stores.domain.tender.TenderPurchaseOrderIfc;
import oracle.retail.stores.domain.tender.TenderStoreCreditIfc;
import oracle.retail.stores.domain.tender.TenderTravelersCheckIfc;
import oracle.retail.stores.domain.utility.CardTypeCodesIfc;
import oracle.retail.stores.pos.ado.utility.TypesafeEnumIfc;

/**
 *    This class enumerates all the tender types
 */
public class TenderTypeEnum implements TypesafeEnumIfc
{
    // This id is used to tell
    // the compiler not to generate a
    // new serialVersionUID.
    //
    static final long serialVersionUID = -7309360815538797559L;

    /** The internal representation of an enumeration instance */
    private String enumer;

    /** Description */
    private String description;

    /** The map containing the singleton enumeration instances */
    protected static final HashMap map = new HashMap();

    /** The RDO tender types that map to an ADO tender type */
    protected Class rdoType;

    protected TenderType tenderType;

    /////////////////////////////////////////////////////////////////
    // Tender definitions
    /////////////////////////////////////////////////////////////////
    /**
     * CASH
     */
    public static final TenderTypeEnum CASH             = new TenderTypeEnum("Cash",
                                                                       TenderCashIfc.class,
                                                                       TenderType.CASH);
    /**
     * CHECK
     */
    public static final TenderTypeEnum CHECK            = new TenderTypeEnum("Check",
                                                                      TenderCheckIfc.class,
                                                                      TenderType.CHECK);
    /**
     * COUPON
     */
    public static final TenderTypeEnum COUPON           = new TenderTypeEnum("Coupon",
                                                                      TenderCouponIfc.class,
                                                                      TenderType.COUPON);
    /**
     * CREDIT
     */
    public static final TenderTypeEnum CREDIT           = new TenderTypeEnum("Credit",
                                                                             TenderChargeIfc.class,
                                                                             TenderType.CREDIT);

    /**
     * DEBIT
     */
    public static final TenderTypeEnum DEBIT            = new TenderTypeEnum("Debit",
                                                                             TenderDebitIfc.class,
                                                                             TenderType.DEBIT);

    /**
     * GIFT_CARD
     */
    public static final TenderTypeEnum GIFT_CARD        = new TenderTypeEnum("GiftCard",
                                                                             TenderGiftCardIfc.class,
                                                                             TenderType.GIFT_CARD);

    /**
     * GIFT_CERT
     */
    public static final TenderTypeEnum GIFT_CERT        = new TenderTypeEnum("GiftCert",
                                                                             TenderGiftCertificateIfc.class, "GiftCertificate",
                                                                             TenderType.GIFT_CERT);

    /**
     * MAIL_CHECK
     */
    public static final TenderTypeEnum MAIL_CHECK       = new TenderTypeEnum("MailCheck",
                                                                             TenderMailBankCheckIfc.class,
                                                                             TenderType.MAIL_CHECK);

    /**
     * PURCHASE_ORDER
     */
    public static final TenderTypeEnum PURCHASE_ORDER   = new TenderTypeEnum("PurchaseOrder",
                                                                             TenderPurchaseOrderIfc.class,
                                                                             TenderType.PURCHASE_ORDER);

    /**
     * STORE_CREDIT
     */
    public static final TenderTypeEnum STORE_CREDIT     = new TenderTypeEnum("StoreCredit",
                                                                             TenderStoreCreditIfc.class,
                                                                             TenderType.STORE_CREDIT);

    /**
     * TRAVELERS_CHECK
     */
    public static final TenderTypeEnum TRAVELERS_CHECK  = new TenderTypeEnum("TravCheck",
                                                                             TenderTravelersCheckIfc.class,
                                                                             TenderType.TRAVELERS_CHECK);

    /**
     * MALL_CERT
     */
    public static final TenderTypeEnum MALL_CERT        = new TenderTypeEnum("MallCert",
                                                                             TenderGiftCertificateIfc.class,
                                                                             TenderType.MALL_CERT);

    /**
     * MONEY_ORDER
     */
    public static final TenderTypeEnum MONEY_ORDER         = new TenderTypeEnum("MoneyOrder",
                                                                                TenderMoneyOrderIfc.class,
                                                                                TenderType.MONEY_ORDER);

    /**
     * HOUSE_ACCOUNT
     */
    public static final TenderTypeEnum HOUSE_ACCOUNT        = new TenderTypeEnum("HouseAccount",
                                                                             TenderChargeIfc.class,
                                                                             TenderType.HOUSE_ACCOUNT);

   
    /**
     * Alternate requires special handling.  There is no RDO type associated with the Alternate button
     */
    public static final TenderTypeEnum ALTERNATE        = new TenderTypeEnum("Alternate",
                                                                             null,
                                                                             TenderType.ALTERNATE);



    /////////////////////////////////////////////////////////////////


    //----------------------------------------------------------------------
    /**
        TenderTypeEnum constructor
        @param tenderName String
        @param rdoType Class
        @param tenderType
    **/
    //----------------------------------------------------------------------
    protected TenderTypeEnum(String tenderName, Class rdoType,TenderType tenderType)
    {
        this.enumer = tenderName;
        map.put(enumer, this);

        this.rdoType = rdoType;
        this.tenderType = tenderType;
    }

    //----------------------------------------------------------------------
    /**
        TenderTypeEnum constructor
        @param tenderName String
        @param rdoType Class
        @param value String
        @param tenderType
    **/
    //----------------------------------------------------------------------
    protected TenderTypeEnum(String tenderName, Class rdoType, String value, TenderType tenderType)
    {
        this.enumer = tenderName;
        map.put(enumer, this);
        this.rdoType = rdoType;
        description = value;
        this.tenderType = tenderType;
    }

    //----------------------------------------------------------------------
    /**
        Factory method to return a TenderTypeEnum instance given an RDO.
        @param rdoObject The RDO object.
        @return An enumerated instance of the tender type.
    **/
    //----------------------------------------------------------------------
    public static TenderTypeEnum makeTenderTypeEnumFromRDO(TenderLineItemIfc rdoObject)
    {
        Iterator iter = map.values().iterator();
        TenderTypeEnum result = null;

        // Since there is not a 1:1 relationship between TenderTypEnum and Domain Objects, must make this check.
        if (rdoObject.getTypeCode() == TenderLineItemIfc.TENDER_TYPE_MALL_GIFT_CERTIFICATE)
        {
            result = MALL_CERT;
        }
        else if (rdoObject.getTypeCode() == TenderLineItemIfc.TENDER_TYPE_GIFT_CERTIFICATE)
        {
            result = GIFT_CERT;
        }
        else if (rdoObject.getTypeCode() == TenderLineItemIfc.TENDER_TYPE_CHARGE)
        {
            TenderChargeIfc tenderCharge = (TenderChargeIfc)rdoObject;
            if (CardTypeCodesIfc.HOUSE_CARD.equals(tenderCharge.getCardType()))
            {
                result = HOUSE_ACCOUNT;
            }
            else
            {
                result = CREDIT;
            }
        }
        else
        {
            loop:
                while (iter.hasNext())
                {
                    TenderTypeEnum typeEnum = (TenderTypeEnum)iter.next();

                    // if the test type is equal to or is a subclass of
                    // the current RDO type, return true;
                    // first check the type itself
                    if (rdoObject.getClass().equals(typeEnum.rdoType))
                    {
                        result = typeEnum;
                        break loop;
                    }

                    // next check the interfaces
                    Class[] interfaces = rdoObject.getClass().getInterfaces();
                    for (int i=0; i<interfaces.length; i++)
                    {
                        if (interfaces[i].equals(typeEnum.rdoType))
                        {
                            result = typeEnum;
                            break loop;
                        }
                    }
                }
        }
        return result;
    }

    protected static boolean typeMatchesMyType(Class testType, Class rdoType)
    {
        // test for degenerate case
        if (testType == null)
        {
            return false;
        }

        boolean result = false;
        if (testType.equals(rdoType))
        {
            result = true;
        }
        else
        {
            result = typeMatchesMyType(testType.getSuperclass(), rdoType);
        }
        return result;
    }

    /** get internal representation */
    public String toString()
    {
        return enumer;
    }

    //----------------------------------------------------------------------
    /**
        Gets the description
        @return String
    **/
    //----------------------------------------------------------------------
    public String getDescription()
    {
        return description;
    }

    /** factory method.  May return null */
    public static TenderTypeEnum makeEnumFromString(String enumer)
    {
        return (TenderTypeEnum)map.get(enumer);
    }

    /** fix deserialization */
    public Object readResolve()
    throws java.io.ObjectStreamException
    {
        return map.get(enumer);
    }

    /**
     * Returns the "real" enum value for this TenderTypeEnum (from the java enum perspective).
     * Note that this class was crated befor real enums were available.
     * Note that this class was not refactored/converted since its days are numbered.
     * It will be removed as part of the configurable tenders feature.
     *
     * @return the tender type's "real" enum value (from the java enum perspective)
     */
    public TenderType getTenderType()
    {
        return tenderType;
    }
}
