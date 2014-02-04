package com.mycompany.promocalculator.validation;

import java.util.HashMap;

import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.Shop;

public interface ValidationComponent {
	public void add(ValidationComponent component);

	public void setParameter(HashMap<String, Object> obj);

	public boolean validate(ConvertedInvoice cinvoice, Shop shop);
}