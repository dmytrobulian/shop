package com.mycompany.promocalculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Invoice {
	private HashMap<String, Integer> invoice = new HashMap<String, Integer>();

	public void addProduct(String pName, Integer quantity) {
		// System.out.println("Invoice.addProduct() "+pName+"  "+quantity);
		invoice.put(pName, quantity);
	}

	public Integer getProductQuantity(String pName) {
		// System.out.println("Invoice.getProduct() "+pName);
		return invoice.get(pName);
	}

	public Iterator<String> getProductNames() {
		Set<String> keys = invoice.keySet();
		return keys.iterator();
	}
}
