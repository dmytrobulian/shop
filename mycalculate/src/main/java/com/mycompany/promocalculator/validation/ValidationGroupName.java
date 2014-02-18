package com.mycompany.promocalculator.validation;

import java.util.ArrayList;
import java.util.Iterator;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.ProductWithChanges;

public class ValidationGroupName extends ValidationComposite {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/*
	 * product from group exist in invoice
	 * 
	 * @see
	 * promocalculator.ValidationComponent#validate(promocalculator.ConvertedInvoice
	 * )
	 */
	@Override
	public boolean validate(ConvertedInvoice cinvoice,  Context context) {

		boolean result = false;
		ArrayList<String> groupname = (ArrayList<String>) parameter.get("groupname");
		int quantity = new Integer(parameter.get("quantity").toString()).intValue();
		int counter = 0;
		Iterator<ProductWithChanges> ci = cinvoice.getProducts();
		while (ci.hasNext() && !result) {
			String pName = ci.next().getProductName();
			if (groupname.contains(context.priceList.getGroup(pName))) {
				counter++;
				if (counter >= quantity) {
					logger.debug(" name={}   group={}", pName, context.priceList.getGroup(pName));
					return validateChilds(cinvoice, context);
				}
			}
		}
		return result;
	}

}
