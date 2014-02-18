package com.mycompany.promocalculator.calculation;

import java.util.Iterator;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.DiscountActionDescription;
import com.mycompany.promocalculator.ProductWithChanges;

public class DiscountCommon extends DiscountComposite {

	@Override
	public Integer count(ConvertedInvoice cinvoice, Context context) {
		Integer allProductQuantity = new Integer(cinvoice.size());
		String discountType = parameter.get("type").toString();
		Integer quantity = new Integer(parameter.get("quantity").toString());
		Integer result = null;
		if (discountType.equalsIgnoreCase("bonus")) {
			if (quantity >= 0) {
				result = cinvoice.size();
				parameter.put("counter", result);
				return result;
			} else if (quantity < 0) {
				result = 1;
				parameter.put("counter", result);
				return result;
			}
		}
		/*
		 * Float amount = new Float(parameter.get("amount").toString()); if
		 * (amount > 0) { ConvertedInvoice updateInvoice = new
		 * ConvertedInvoice(cinvoice); Iterator<ProductWithChanges> ci =
		 * updateInvoice.getProducts(); Float sum = new Float(0); while
		 * (ci.hasNext()) {
		 * sum+=context.getPrice(ci.next().getProductName()); } result =
		 * (int) (sum/amount); parameter.put("counter", result); return result;
		 * }
		 */
		result = allProductQuantity / quantity;
		parameter.put("counter", result);
		return result;
	}

	@Override
	public ConvertedInvoice addDiscountToInvoice(ConvertedInvoice cinvoice, Context context, Integer counter, String discountName) {
		Integer productInInvoiceQuantity = new Integer(0);
		ConvertedInvoice updateInvoice = new ConvertedInvoice(cinvoice);
		Iterator<ProductWithChanges> ci = updateInvoice.getProducts();
		String discountType = parameter.get("type").toString();

		Integer quantity = new Integer(parameter.get("quantity").toString());
		Float discountAmount = new Float(parameter.get("amount").toString());
		if (!discountType.equalsIgnoreCase("bonus")) {
			while (ci.hasNext()) {
				ProductWithChanges product = ci.next();
				productInInvoiceQuantity++;
				if ((productInInvoiceQuantity % quantity == 0) && ((productInInvoiceQuantity / quantity) <= counter)) {
					if (discountAmount >= 0) {
						// apply bonus for specific product
						product.addDiscount(new DiscountActionDescription(discountName, discountType, discountAmount));
					}
				}
			}
		} else {
			// add bonus counter time for invoice
			for (int i = 0; i < counter; i++) {
				Iterator<ProductWithChanges> ip = updateInvoice.getProducts();
				while (ip.hasNext()) {
					updateInvoice.getChanges().add(new DiscountActionDescription(ip.next().getProductName(), discountType, discountAmount));
				}
			}
		}
		updateInvoice = addDiscountToChildInvoice(updateInvoice, context, counter, discountName);
		return updateInvoice;
	}
}