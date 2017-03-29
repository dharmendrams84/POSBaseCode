/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package oracle.retail.stores.domain.stock;

import java.math.BigDecimal;
import java.util.Iterator;
import oracle.retail.stores.commerceservices.common.currency.CurrencyIfc;
import oracle.retail.stores.commerceservices.externalorder.ExternalOrderItemIfc;
import oracle.retail.stores.domain.discount.AdvancedPricingRuleIfc;
import oracle.retail.stores.domain.event.PriceChangeIfc;
import oracle.retail.stores.domain.tax.NewTaxRuleIfc;
import oracle.retail.stores.domain.utility.EYSDate;

public abstract interface PLUItemIfc extends ItemIfc {
	public static final String revisionNumber = "$Revision: /rgbustores_13.4x_generic_branch/2 $";

	public abstract void addAdvancedPricingRule(
			AdvancedPricingRuleIfc paramAdvancedPricingRuleIfc);

	public abstract void addAdvancedPricingRules(
			AdvancedPricingRuleIfc[] paramArrayOfAdvancedPricingRuleIfc);

	public abstract void addPermanentPriceChange(
			PriceChangeIfc paramPriceChangeIfc);

	public abstract void addPermanentPriceChanges(
			PriceChangeIfc[] paramArrayOfPriceChangeIfc);

	public abstract void addTemporaryPriceChange(
			PriceChangeIfc paramPriceChangeIfc);

	public abstract void addTemporaryPriceChangeForReturns(
			PriceChangeIfc paramPriceChangeIfc);

	public abstract void addTemporaryPriceChanges(
			PriceChangeIfc[] paramArrayOfPriceChangeIfc);

	public abstract void addTaxRule(NewTaxRuleIfc paramNewTaxRuleIfc);

	public abstract void addTaxRules(NewTaxRuleIfc[] paramArrayOfNewTaxRuleIfc);

	public abstract Iterator<AdvancedPricingRuleIfc> advancedPricingRules();

	public abstract void clearAdvancedPricingRules();

	public abstract void clearPermanentPriceChanges();

	public abstract void clearTemporaryPriceChanges();

	public abstract void clearTaxRules();

	public abstract AdvancedPricingRuleIfc[] getAdvancedPricingRules();

	public abstract CurrencyIfc getCompareAtPrice();

	public abstract String getDepartmentID();

	/** @deprecated */
	public abstract String getDescriptionForCustomerLocale();

	/** @deprecated */
	public abstract String getDescriptionForDefaultLocale();

	public abstract ItemIfc getItem();

	/** @deprecated */
	public abstract String getManufacturerForCustomerLocale();

	/** @deprecated */
	public abstract String getManufacturerForDefaultLocale();

	public abstract int getManufacturerID();

	public abstract String getManufacturerItemUPC();

	public abstract String getMerchandiseCodesString();

	public abstract CurrencyIfc getPermanentPrice(EYSDate paramEYSDate);

	public abstract PriceChangeIfc[] getPermanentPriceChanges();

	public abstract String getPosItemID();

	public abstract CurrencyIfc getPrice();

	public abstract CurrencyIfc getPrice(EYSDate paramEYSDate, int paramInt);

	public abstract PriceChangeIfc[] getTemporaryPriceChanges();

	public abstract PriceChangeIfc getEffectiveTemporaryPriceChange();

	public abstract PriceChangeIfc getEffectiveTemporaryPriceChange(
			EYSDate paramEYSDate);

	public abstract PriceChangeIfc getEffectiveTemporaryPriceChange(
			EYSDate paramEYSDate, int paramInt);

	public abstract RelatedItemContainerIfc getRelatedItemContainer();

	public abstract StockItemIfc getStockItem();

	public abstract String getStoreID();

	public abstract String getTaxGroupName();

	public abstract NewTaxRuleIfc[] getTaxRules();

	public abstract boolean hasAdvancedPricingRules();

	public abstract boolean hasRelatedItems();

	public abstract boolean hasPermanentPriceChanges();

	public abstract boolean hasTemporaryPriceChanges();

	public abstract boolean hasTaxRules();

	public abstract boolean isAlterationItem();

	public abstract boolean isSerializedItem();

	public abstract Iterator<PriceChangeIfc> priceChangesPermanent();

	public abstract Iterator<PriceChangeIfc> priceChangesTemporary();

	public abstract void setAdvancedPricingRules(
			AdvancedPricingRuleIfc[] paramArrayOfAdvancedPricingRuleIfc);

	public abstract void setCloneAttributes(PLUItem paramPLUItem);

	public abstract void setCompareAtPrice(CurrencyIfc paramCurrencyIfc);

	public abstract void setDepartmentID(String paramString);

	/** @deprecated */
	public abstract void setDescriptionForCustomerLocale(String paramString);

	/** @deprecated */
	public abstract void setDescriptionForDefaultLocale(String paramString);

	public abstract void setDiscountEligible(boolean paramBoolean);

	public abstract void setItem(ItemIfc paramItemIfc);

	/** @deprecated */
	public abstract void setManufacturerForCustomerLocale(String paramString);

	/** @deprecated */
	public abstract void setManufacturerForDefaultLocale(String paramString);

	public abstract void setManufacturerID(int paramInt);

	public abstract void setManufacturerItemUPC(String paramString);

	public abstract void setPosItemID(String paramString);

	public abstract void setPrice(CurrencyIfc paramCurrencyIfc);

	public abstract void setPermanentPriceChanges(
			PriceChangeIfc[] paramArrayOfPriceChangeIfc);

	public abstract void setTemporaryPriceChanges(
			PriceChangeIfc[] paramArrayOfPriceChangeIfc);

	public abstract void setRelatedItemContainer(
			RelatedItemContainerIfc paramRelatedItemContainerIfc);

	public abstract void setStoreID(String paramString);

	public abstract void setTaxGroupName(String paramString);

	public abstract void setTaxRules(NewTaxRuleIfc[] paramArrayOfNewTaxRuleIfc);

	public abstract Iterator<NewTaxRuleIfc> taxRules();

	public abstract int getReturnPriceDays();

	public abstract void setReturnPriceDays(int paramInt);

	public abstract void setTemporaryPriceChangesAndTemporaryPriceChangesForReturns(
			PriceChangeIfc[] paramArrayOfPriceChangeIfc);

	public abstract CurrencyIfc getReturnPrice(int paramInt);

	public abstract ExternalOrderItemIfc getReturnExternalOrderItem();

	public abstract void setReturnExternalOrderItem(
			ExternalOrderItemIfc paramExternalOrderItemIfc);
	
	public void setItemDivision(String itemDivision);
	    
	public String getItemDivision();
	
	public abstract boolean isDiscountConsidered();
	
	public void setDiscountConsidered(boolean isDiscountConsidered);
	
	public abstract BigDecimal getMaxSpendLimit();

	public abstract void setMaxSpendLimit(BigDecimal maxSpendLimit);

}