package com.mycompany.promocalculator.validation;

import java.util.ArrayList;
import java.util.Iterator;

import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.ProductWithChanges;
import com.mycompany.promocalculator.Shop;

public class ValidationProductName extends ValidationComposite {
	/*
	 * needed product exist in invoice
	 * 
	 * @see
	 * promocalculator.ValidationComponent#validate(promocalculator.ConvertedInvoice
	 * )
	 */
	@Override
	public boolean validate(ConvertedInvoice cinvoice, Shop shop) {

		boolean result = false;
		ArrayList<String> productname = (ArrayList<String>) parameter
				.get("productname");
		int quantity = new Integer(parameter.get("quantity").toString())
				.intValue();
		int counter = 0;
		Iterator<ProductWithChanges> ci = cinvoice.getProducts();
		while (ci.hasNext() && !result) {
			String pName = ci.next().toString();
			if (productname.contains(pName)) {
				counter++;
				if (counter >= quantity) {
					System.out.println("ValidationProductName.validate() name="
							+ pName);
					return validateChilds(cinvoice, shop);
				}
			}
		}
		return result;
	}
}
