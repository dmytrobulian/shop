package com.mycompany.promocalculator.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.ConvertedInvoice;

public class ValidationComposite implements ValidationComponent {
	protected HashMap<String, Object> parameter;
	private List<ValidationComponent> componentList = new ArrayList<ValidationComponent>();

	@Override
	public void add(ValidationComponent validation) {
		componentList.add(validation);
	}

	@Override
	public boolean validate(ConvertedInvoice cinvoice, Context context) {
		return validateChilds(cinvoice, context);
	}

	protected boolean validateChilds(ConvertedInvoice cinvoice, Context context) {
		boolean result = true;
		Iterator<ValidationComponent> i = componentList.iterator();
		while (i.hasNext() && result) {
			result = i.next().validate(cinvoice, context);
		}
		return result;
	}

	@Override
	public void setParameter(HashMap<String, Object> obj) {
		parameter = obj;
	}
}
