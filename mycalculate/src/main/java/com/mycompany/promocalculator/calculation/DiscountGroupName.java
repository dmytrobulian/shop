package com.mycompany.promocalculator.calculation;

import java.util.ArrayList;
import java.util.Iterator;

import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.DiscountActionDescription;
import com.mycompany.promocalculator.ProductWithChanges;
import com.mycompany.promocalculator.Shop;

public class DiscountGroupName extends DiscountComposite {
	@Override
	public Integer count(ConvertedInvoice cinvoice, Shop shop) {
		Integer productInGroupListQuantity = new Integer(0);
		ArrayList<String> groupname = (ArrayList<String>) parameter
				.get("groupname");
		Iterator<ProductWithChanges> ci = cinvoice.getProducts();
		while (ci.hasNext()) {
			String pName = ci.next().getProductName();
			if (groupname.contains(shop.priceList.getGroup(pName))) {
				productInGroupListQuantity++;
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
		result = productInGroupListQuantity / quantity;
		parameter.put("counter", result);

		return result;
	}

	@Override
	public ConvertedInvoice addDiscountToInvoice(ConvertedInvoice cinvoice,
			Shop shop, Integer counter, String discountName) {
		Integer productInGroupListQuantity = new Integer(0);
		ConvertedInvoice updateInvoice = new ConvertedInvoice(cinvoice);
		Iterator<ProductWithChanges> ci = updateInvoice.getProducts();
		ArrayList<String> groupname = (ArrayList<String>) parameter
				.get("groupname");
		String discountType = parameter.get("type").toString();
		Integer quantity = new Integer(parameter.get("quantity").toString());
		Float discountAmount = new Float(parameter.get("amount").toString());
		if (!discountType.equalsIgnoreCase("bonus")) {
			while (ci.hasNext()) {
				ProductWithChanges product = ci.next();
				if (groupname.contains(shop.priceList.getGroup(product
						.getProductName()))) {
					productInGroupListQuantity++;
					if ((productInGroupListQuantity % quantity == 0)
							&& ((productInGroupListQuantity / quantity) <= counter)) {
						if (discountAmount >= 0) {
							// add bonus for specific product
							product.addDiscount(new DiscountActionDescription(
									discountName, discountType, discountAmount));
						}
					}
				}
			}
		} else {
			for (int i = 0; i < counter; i++) {
				Iterator<String> ip = groupname.iterator();
				while (ip.hasNext()) {
					updateInvoice.getChanges().add(
							new DiscountActionDescription(ip.next(),
									discountType, discountAmount));
				}
			}
		}
		updateInvoice = addDiscountToChildInvoice(updateInvoice, shop, counter,
				discountName);
		return updateInvoice;
	}
}
