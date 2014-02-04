package com.mycompany.promocalculator.calculation;

import java.util.HashMap;

import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.Shop;

public interface DiscountComponent {
	public void setParameter(HashMap<String, Object> obj);

	public void add(DiscountComponent component);

	public Integer count(ConvertedInvoice cInvoice, Shop shop);

	public ConvertedInvoice addDiscountToInvoice(ConvertedInvoice cinvoice,
			Shop shop, Integer counter, String discountName);
}
