package com.mycompany.promocalculator.calculation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.Shop;

public class DiscountComposite implements DiscountComponent {
	List<DiscountComponent> component = new ArrayList<DiscountComponent>();
	protected HashMap<String, Object> parameter = new HashMap<String, Object>();

	public void add(DiscountComponent calculation) {
		component.add(calculation);
	}

	protected Integer counterChilds(ConvertedInvoice cInvoice, Shop shop) {
		Iterator<DiscountComponent> i = component.iterator();
		Integer result = (Integer) parameter.get("counter");
		while (i.hasNext()) {
			DiscountComponent child = i.next();
			Integer childResultValue = child.count(cInvoice, shop);
			result = result < childResultValue ? result : childResultValue;
		}
		parameter.put("counter", result);
		return result;
	}

	protected ConvertedInvoice addDiscountToChildInvoice(
			ConvertedInvoice cinvoice, Shop shop, Integer counter,
			String discountName) {
		Iterator<DiscountComponent> i = component.iterator();
		ConvertedInvoice updateInvoice = new ConvertedInvoice(cinvoice);
		while (i.hasNext()) {
			updateInvoice = i.next().addDiscountToInvoice(updateInvoice, shop,
					counter, discountName);
		}
		return updateInvoice;
	}

	@Override
	public void setParameter(HashMap<String, Object> obj) {
		parameter = obj;
	}

	@Override
	public Integer count(ConvertedInvoice cInvoice, Shop shop) {
		Integer allProductQuantity = new Integer(cInvoice.size());
		parameter.get("quantity");
		Integer result = allProductQuantity
				/ new Integer(parameter.get("quantity").toString());
		parameter.put("counter", result);
		return counterChilds(cInvoice, shop);
	}

	@Override
	public ConvertedInvoice addDiscountToInvoice(ConvertedInvoice cinvoice,
			Shop shop, Integer counter, String discountName) {
		return addDiscountToChildInvoice(cinvoice, shop, counter, discountName);
	}

}