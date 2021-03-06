package com.mycompany.promocalculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class PriceList {
	private HashMap<String, Float> price = new HashMap<String, Float>();
	private HashMap<String, String> group = new HashMap<String, String>();
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	public void addProduct(String pName, Float pPrice, String pGroup) {
		logger.debug("added product	group={}  name={}  price={}", new Object[]{pGroup, pName, pPrice});
		price.put(pName, pPrice);
		group.put(pName, pGroup);
	}

	public Float getPrice(String pName) {
		return price.get(pName);
	}

	public String getGroup(String pName) {
		return group.get(pName);
	}

	public Iterator<String> getProductsName() {
		Set<String> keys = price.keySet();
		return keys.iterator();
	}
}
