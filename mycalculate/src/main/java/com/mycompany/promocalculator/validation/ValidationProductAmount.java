package com.mycompany.promocalculator.validation;

import java.util.ArrayList;
import java.util.Iterator;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.ProductWithChanges;

public class ValidationProductAmount extends ValidationComposite {

	/*
	 * products are in the group obj
	 * 
	 * @see
	 * promocalculator.ValidationComponent#validate(promocalculator.ConvertedInvoice
	 * )
	 */
	@Override
	public boolean validate(ConvertedInvoice cinvoice,  Context context) {

		boolean result = false;
		Float amount = new Float(parameter.get("amount").toString());
		Float summ = new Float(0);
		ArrayList<String> product = (ArrayList<String>) parameter.get("productname");
		Iterator<ProductWithChanges> ci = cinvoice.getProducts();
		while (ci.hasNext() && !result) {
			String pName = ci.next().getProductName();
			if (product.contains(pName)) {
				summ += context.priceList.getPrice(pName);
				if (summ >= amount) {
					return validateChilds(cinvoice, context);
				}
			}
		}
		return result;
	}
}
