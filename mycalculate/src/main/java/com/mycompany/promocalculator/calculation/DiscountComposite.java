package com.mycompany.promocalculator.calculation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.ConvertedInvoice;

public class DiscountComposite implements DiscountComponent {
	List<DiscountComponent> component = new ArrayList<DiscountComponent>();
	protected HashMap<String, Object> parameter = new HashMap<String, Object>();

	public void add(DiscountComponent calculation) {
		component.add(calculation);
	}

	protected Integer counterChilds(ConvertedInvoice cInvoice, Context context) {
		Iterator<DiscountComponent> i = component.iterator();
		Integer result = (Integer) parameter.get("counter");
		while (i.hasNext()) {
			DiscountComponent child = i.next();
			Integer childResultValue = child.count(cInvoice, context);
			result = result < childResultValue ? result : childResultValue;
		}
		parameter.put("counter", result);
		return result;
	}

	protected ConvertedInvoice addDiscountToChildInvoice(ConvertedInvoice cinvoice, Context context, Integer counter, String discountName) {
		Iterator<DiscountComponent> i = component.iterator();
		ConvertedInvoice updateInvoice = new ConvertedInvoice(cinvoice);
		while (i.hasNext()) {
			updateInvoice = i.next().addDiscountToInvoice(updateInvoice, context, counter, discountName);
		}
		return updateInvoice;
	}

	@Override
	public void setParameter(HashMap<String, Object> obj) {
		parameter = obj;
	}

	@Override
	public Integer count(ConvertedInvoice cInvoice, Context context) {
		Integer allProductQuantity = new Integer(cInvoice.size());
		parameter.get("quantity");
		Integer result = allProductQuantity / new Integer(parameter.get("quantity").toString());
		parameter.put("counter", result);
		return counterChilds(cInvoice, context);
	}

	@Override
	public ConvertedInvoice addDiscountToInvoice(ConvertedInvoice cinvoice, Context context, Integer counter, String discountName) {
		return addDiscountToChildInvoice(cinvoice, context, counter, discountName);
	}

}