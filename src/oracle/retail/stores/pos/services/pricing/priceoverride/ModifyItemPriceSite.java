/* ===========================================================================
* Copyright (c) 2000, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/applications/pos/src/oracle/retail/stores/pos/services/pricing/priceoverride/ModifyItemPriceSite.java /rgbustores_13.4x_generic_branch/1 2011/05/05 14:06:00 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    cgreene   04/26/10 - XbranchMerge cgreene_tech43 from
 *                         st_rgbustores_techissueseatel_generic_branch
 *    cgreene   04/02/10 - remove deprecated LocaleContantsIfc and currencies
 *    nganesh   01/28/10 - CaptureReasonCodeForPriceOverride parameter has been
 *                         removed as part of BugDB#9279097
 *    abondala  01/03/10 - update header date
 *    acadar    03/25/09 - force correct refresh by calling showScreen twice
 *    acadar    10/27/08 - fixes for localization of price override reason
 *                         codes
 *    acadar    10/27/08 - use localized price override reason codes
 *    acadar    10/25/08 - localization of price override reason codes
 *
 * ===========================================================================
   $Log:
    8    360Commerce 1.7         4/25/2007 8:52:16 AM   Anda D. Cadar   I18N
         merge

    7    360Commerce 1.6         3/29/2007 6:53:31 PM   Michael Boyd    CR
         26172 - v8x merge to trunk

         7    .v8x      1.5.1.0     3/10/2007 3:45:38 PM   Maisa De Camargo
         Updated
         Reason Code Selection.
    6    360Commerce 1.5         2/16/2006 7:32:21 AM   Dinesh Gautam
         Modified default reason codes.
    5    360Commerce 1.4         1/25/2006 4:11:31 PM   Brett J. Larsen merge
         7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
    4    360Commerce 1.3         1/22/2006 11:45:17 AM  Ron W. Haight   removed
          references to com.ibm.math.BigDecimal
    3    360Commerce 1.2         3/31/2005 4:29:04 PM   Robert Pearse
    2    360Commerce 1.1         3/10/2005 10:23:34 AM  Robert Pearse
    1    360Commerce 1.0         2/11/2005 12:12:40 PM  Robert Pearse
   $:
    4    .v700     1.2.1.0     11/17/2005 16:39:34    Jason L. DeLeau 4345:
         Replace any uses of Gateway.log() with the log4j.
    3    360Commerce1.2         3/31/2005 15:29:04     Robert Pearse
    2    360Commerce1.1         3/10/2005 10:23:34     Robert Pearse
    1    360Commerce1.0         2/11/2005 12:12:40     Robert Pearse
   $
   Revision 1.11  2004/09/15 16:34:22  kmcbride
   @scr 5881: Deprecating parameter retrieval logic in cargo classes and logging parameter exceptions

   Revision 1.10  2004/08/23 16:16:01  cdb
   @scr 4204 Removed tab characters

   Revision 1.9  2004/06/08 22:30:39  aschenk
   @scr 5417 - Changed override price text prompt when no reason code is asked for.

   Revision 1.8  2004/05/25 13:56:20  jeffp
   @scr 3743 - Added check if letter returned from invalid reason code . If returned from invalid reason code retrieved price from ui.

   Revision 1.7  2004/05/20 22:54:58  cdb
   @scr 4204 Removed tabs from code base again.

   Revision 1.6  2004/05/06 05:05:53  tfritz
   @scr 4605 Added new CaptureReasonCodeForPriceOverride parameter

     Revision 1.5  2004/05/05 00:42:08  tfritz
     @scr 4493 Show default reason code.

   Revision 1.4  2004/03/22 18:35:05  cdb
   @scr 3588 Corrected some javadoc

   Revision 1.3  2004/02/12 16:51:39  mcs
   Forcing head revision

   Revision 1.2  2004/02/11 21:52:06  rhafernik
   @scr 0 Log4J conversion and code cleanup

   Revision 1.1.1.1  2004/02/11 01:04:19  cschellenger
   updating to pvcs 360store-current


*
*    Rev 1.2   Jan 06 2004 11:02:10   cdb
* Enhanced configurability. When non-editable combo boxes are used, a default value is set if a previously existing reason code hasn't been selected.
* Resolution for 3588: Discounts/MUPS - Gap Rollback
*
*    Rev 1.1   Jan 05 2004 18:55:26   cdb
* Updated to use editable combo box for reason code selection.
* Resolution for 3588: Discounts/MUPS - Gap Rollback
*
*    Rev 1.0   Aug 29 2003 16:05:26   CSchellenger
* Initial revision.
*
*    Rev 1.2   Feb 13 2003 10:52:06   HDyer
* Changed method to set reason code for beanModel changes. Fixed deprecation warning.
* Resolution for POS SCR-2035: I18n Reason Code support
*
*    Rev 1.1   05 Jun 2002 17:13:22   jbp
* changes for pricing feature
* Resolution for POS SCR-1626: Pricing Feature
*
*    Rev 1.0   02 May 2002 17:42:56   jbp
* Initial revision.
* Resolution for POS SCR-1626: Pricing Feature

* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package oracle.retail.stores.pos.services.pricing.priceoverride;

//java imports
import java.math.BigDecimal;

import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.domain.utility.LocaleConstantsIfc;
import oracle.retail.stores.domain.lineitem.SaleReturnLineItemIfc;
import oracle.retail.stores.domain.utility.CodeConstantsIfc;
import oracle.retail.stores.domain.utility.CodeListIfc;
import oracle.retail.stores.foundation.manager.ifc.ParameterManagerIfc;
import oracle.retail.stores.foundation.manager.ifc.UIManagerIfc;
import oracle.retail.stores.foundation.tour.ifc.BusIfc;
import oracle.retail.stores.pos.manager.ifc.UtilityManagerIfc;
import oracle.retail.stores.pos.services.PosSiteActionAdapter;
import oracle.retail.stores.pos.services.pricing.PricingCargo;
import oracle.retail.stores.pos.ui.POSUIManagerIfc;
import oracle.retail.stores.pos.ui.beans.DecimalWithReasonBeanModel;

//--------------------------------------------------------------------------
/**
*   This shows the Modify Item Price screen.<P>
 *   @version $Revision: /rgbustores_13.4x_generic_branch/1 $
*/
//--------------------------------------------------------------------------

public class ModifyItemPriceSite extends PosSiteActionAdapter
{

  //--------------------------------------------------------------------------
  /**
   *   Revision Number furnished by TeamConnection. <P>
   */
  //--------------------------------------------------------------------------
    public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/1 $";

  //--------------------------------------------------------------------------
  /**
   *  shows the Modify Item Price screen
   *   @param  bus BusIfc
   */
  //--------------------------------------------------------------------------
  public void arrive(BusIfc bus)
  {
        POSUIManagerIfc ui = (POSUIManagerIfc) bus.getManager(UIManagerIfc.TYPE);

        ParameterManagerIfc pm = (ParameterManagerIfc) bus.getManager(ParameterManagerIfc.TYPE);

        // get price from cargo
        PricingCargo pc = (PricingCargo) bus.getCargo();

        // get capture reason code parameter value
        // CaptureReasonCodeForPriceOverride parameter has been removed as part of HPQC#539 / BugDB#9279097

          // get line item from cargo
          SaleReturnLineItemIfc lineitem  = pc.getItems()[0];

          // get old price, old reason code from item
          CurrencyIfc oldPrice =
              lineitem.getItemPrice().getSellingPrice();
          String oldReasonCodeKey = lineitem.getItemPrice().getItemPriceOverrideReason().getCode();
          String selectedReasonCodeKey = "";

        // set value in bean model
          DecimalWithReasonBeanModel beanModel = new DecimalWithReasonBeanModel();

        // display the old value on the screen
          beanModel.setValue(new BigDecimal(oldPrice.getStringValue()));




              String screenID = POSUIManagerIfc.PRICE_OVERRIDE;

              UtilityManagerIfc utility = (UtilityManagerIfc) bus.getManager(UtilityManagerIfc.TYPE);
              // set up reason code list
              CodeListIfc rcl = utility.getReasonCodes(pc.getOperator().getStoreID(), CodeListIfc.CODE_LIST_PRICE_OVERRIDE_REASON_CODES);
              pc.setPriceOverrideCodeList(rcl);

              // if doesn't exist
              if (!oldReasonCodeKey.equalsIgnoreCase(CodeConstantsIfc.CODE_UNDEFINED))
              {
                  selectedReasonCodeKey = oldReasonCodeKey;
              }

              beanModel.inject(rcl, selectedReasonCodeKey, LocaleMap.getLocale(LocaleConstantsIfc.USER_INTERFACE));
              DecimalWithReasonBeanModel oldModel = null;

              // if we returned to this site because of an invalid reason code
              // we must restore the value the user entered
              // if Letter 'Ok' we know user entered invalid reason code
              if (bus.getCurrentLetter().getName().equals("Ok"))
              {
                  try
                {
                      oldModel = (DecimalWithReasonBeanModel)ui.getModel(screenID);
                }

                  catch (Exception c)
                {
                      oldModel = null;
                }
              }

              if (oldModel != null)
              {
                  beanModel.setValue(oldModel.getValue());
              }


      // set up the ui model
      ui.showScreen(screenID, beanModel);
//    HPQC # 2834: System allows adding discount amt for an item without selecting reason code
      //force a correct refresh of the bean and model by calling twice showScreen
      ui.showScreen(screenID, beanModel);


  }

}
