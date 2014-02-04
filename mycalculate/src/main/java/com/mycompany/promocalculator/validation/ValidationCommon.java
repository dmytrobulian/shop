package com.mycompany.promocalculator.validation;

import java.util.Iterator;

import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.ProductWithChanges;
import com.mycompany.promocalculator.Shop;

public class ValidationCommon extends ValidationComposite {

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
		Integer quantity = new Integer(parameter.get("quantity").toString());
		Integer counter = new Integer(0);
		Float sum = new Float(0);
		Iterator<ProductWithChanges> ci = cinvoice.getProducts();
		if (amount > 0) {
			while (ci.hasNext()) {
				String pName = ci.next().getProductName();
				sum += shop.priceList.getPrice(pName);
				if (sum >= amount) {
					return validateChilds(cinvoice, shop);
				}
			}
		} else {
			while (ci.hasNext()) {
				ci.next();
				counter++;
				if (counter >= quantity) {
					return validateChilds(cinvoice, shop);
				}
			}

		}
		return result;
	}
}
