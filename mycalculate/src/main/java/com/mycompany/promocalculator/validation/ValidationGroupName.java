package com.mycompany.promocalculator.validation;

import java.util.ArrayList;
import java.util.Iterator;

import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.ProductWithChanges;
import com.mycompany.promocalculator.Shop;

public class ValidationGroupName extends ValidationComposite {

	/*
	 * product from group exist in invoice
	 * 
	 * @see
	 * promocalculator.ValidationComponent#validate(promocalculator.ConvertedInvoice
	 * )
	 */
	@Override
	public boolean validate(ConvertedInvoice cinvoice, Shop shop) {

		boolean result = false;
		ArrayList<String> groupname = (ArrayList<String>) parameter
				.get("groupname");
		int quantity = new Integer(parameter.get("quantity").toString())
				.intValue();
		int counter = 0;
		Iterator<ProductWithChanges> ci = cinvoice.getProducts();
		while (ci.hasNext() && !result) {
			String pName = ci.next().getProductName();
			if (groupname.contains(shop.priceList.getGroup(pName))) {
				counter++;
				if (counter >= quantity) {
					System.out.println("ValidationGroupName.validate() name="
							+ pName + "   group="
							+ shop.priceList.getGroup(pName));
					return validateChilds(cinvoice, shop);
				}
			}
		}
		return result;
	}

}
