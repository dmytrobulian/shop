package com.mycompany.promocalculator.calculation;

import java.util.HashMap;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.ConvertedInvoice;

public interface DiscountComponent {
	public void setParameter(HashMap<String, Object> obj);

	public void add(DiscountComponent component);

	public Integer count(ConvertedInvoice cInvoice, Context context);

	public ConvertedInvoice addDiscountToInvoice(ConvertedInvoice cinvoice, Context context, Integer counter, String discountName);
}
