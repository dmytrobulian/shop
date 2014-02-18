package com.mycompany.promocalculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Invoice {
	private HashMap<String, Integer> invoice = new HashMap<String, Integer>();
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private String invoiceName = "";
	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public Integer addProduct(String pName, Integer quantity) {
		logger.debug("added {} quantity {} ",pName,quantity);
		return invoice.put(pName, quantity);
	}

	public Integer getProductQuantity(String pName) {
		return invoice.get(pName);
	}

	public Iterator<String> getProductNames() {
		Set<String> keys = invoice.keySet();
		return keys.iterator();
	}
	public void removeProduct(String name){
		invoice.remove(name);
	}
}
