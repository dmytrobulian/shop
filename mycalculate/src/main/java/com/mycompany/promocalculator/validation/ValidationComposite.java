package com.mycompany.promocalculator.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.Shop;

public class ValidationComposite implements ValidationComponent {
	protected HashMap<String, Object> parameter;
	private List<ValidationComponent> componentList = new ArrayList<ValidationComponent>();

	@Override
	public void add(ValidationComponent validation) {
		componentList.add(validation);
	}

	@Override
	public boolean validate(ConvertedInvoice cinvoice, Shop shop) {
		return validateChilds(cinvoice, shop);
	}

	protected boolean validateChilds(ConvertedInvoice cinvoice, Shop shop) {
		boolean result = true;
		Iterator<ValidationComponent> i = componentList.iterator();
		while (i.hasNext() && result) {
			result = i.next().validate(cinvoice, shop);
		}
		return result;
	}

	@Override
	public void setParameter(HashMap<String, Object> obj) {
		parameter = obj;
	}
}
