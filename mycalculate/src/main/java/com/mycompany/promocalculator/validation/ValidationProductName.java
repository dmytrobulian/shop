package com.mycompany.promocalculator.validation;

import java.util.ArrayList;
import java.util.Iterator;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.ProductWithChanges;

public class ValidationProductName extends ValidationComposite {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	/*
	 * needed product exist in invoice
	 * 
	 * @see
	 * promocalculator.ValidationComponent#validate(promocalculator.ConvertedInvoice
	 * )
	 */
	@Override
	public boolean validate(ConvertedInvoice cinvoice,  Context context) {

		boolean result = false;
		ArrayList<String> productname = (ArrayList<String>) parameter.get("productname");
		int quantity = new Integer(parameter.get("quantity").toString()).intValue();
		int counter = 0;
		Iterator<ProductWithChanges> ci = cinvoice.getProducts();
		while (ci.hasNext() && !result) {
			String pName = ci.next().toString();
			if (productname.contains(pName)) {
				counter++;
				if (counter >= quantity) {
					logger.debug("name ={}",pName);
					return validateChilds(cinvoice, context);
				}
			}
		}
		return result;
	}
}
