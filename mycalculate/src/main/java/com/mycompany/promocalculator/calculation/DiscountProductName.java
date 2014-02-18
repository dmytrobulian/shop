package com.mycompany.promocalculator.calculation;

import java.util.ArrayList;
import java.util.Iterator;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.DiscountActionDescription;
import com.mycompany.promocalculator.ProductWithChanges;

public class DiscountProductName extends DiscountComposite {
	@Override
	public Integer count(ConvertedInvoice cinvoice, Context context) {
		Integer productInProductListQuantity = new Integer(0);
		ArrayList<String> productname = (ArrayList<String>) parameter.get("productname");
		Iterator<ProductWithChanges> ci = cinvoice.getProducts();
		while (ci.hasNext()) {
			String pName = ci.next().getProductName();
			if (productname.contains(pName)) {
				productInProductListQuantity++;
			}
		}
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
		result = productInProductListQuantity / quantity;
		parameter.put("counter", result);

		return result;
	}

	@Override
	public ConvertedInvoice addDiscountToInvoice(ConvertedInvoice cinvoice, Context context, Integer counter, String discountName) {
		Integer productInProductListQuantity = new Integer(0);
		ConvertedInvoice updateInvoice = new ConvertedInvoice(cinvoice);
		Iterator<ProductWithChanges> ci = updateInvoice.getProducts();
		ArrayList<String> productname = (ArrayList<String>) parameter.get("productname");
		Integer quantity = new Integer(parameter.get("quantity").toString());
		String discountType = parameter.get("type").toString();
		Float discountAmount = new Float(parameter.get("amount").toString());
		if (!discountType.equalsIgnoreCase("bonus")) {
			while (ci.hasNext()) {
				ProductWithChanges product = ci.next();
				if (productname.contains(product.getProductName())) {
					productInProductListQuantity++;
					if ((productInProductListQuantity % quantity == 0) && ((productInProductListQuantity / quantity) <= counter)) {
						if (discountAmount >= 0) {
							// apply bonus for specific product
							product.addDiscount(new DiscountActionDescription(discountName, discountType, discountAmount));
						}
					}
				}
			}
		} else {// apply bonus "counter" time for invoice
			for (int i = 0; i < counter; i++) {
				Iterator<String> ip = productname.iterator();
				while (ip.hasNext()) {
					updateInvoice.getChanges().add(new DiscountActionDescription(ip.next(), discountType, discountAmount));
				}
			}
		}
		updateInvoice = addDiscountToChildInvoice(updateInvoice, context, counter, discountName);
		return updateInvoice;
	}
}
