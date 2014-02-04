package com.mycompany.promocalculator.validation;

import java.util.ArrayList;
import java.util.Iterator;

import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.ProductWithChanges;
import com.mycompany.promocalculator.Shop;

public class ValidationGroupAmount extends ValidationComposite {

	/*
	 * products are in the group obj
	 * 
	 * @see
	 * promocalculator.ValidationComponent#validate(promocalculator.ConvertedInvoice
	 * )
	 */
	@Override
	public boolean validate(ConvertedInvoice cinvoice, Shop shop) {

		boolean result = false;
		Float amount = new Float(parameter.get("amount").toString());
		Float summ = new Float(0);
		ArrayList<String> group = (ArrayList<String>) (parameter
				.get("groupname"));
		Iterator<ProductWithChanges> ci = cinvoice.getProducts();
		while (ci.hasNext() && !result) {
			String pName = ci.next().getProductName();
			if (group.contains(shop.priceList.getGroup(pName))) {
				summ += shop.priceList.getPrice(pName);
				if (summ >= amount) {
					return validateChilds(cinvoice, shop);
				}
			}
		}
		return result;
	}
}