package com.mycompany.promocalculator;

import java.util.ArrayList;
import java.util.Iterator;

public class ProductWithChanges {
	private String productName;
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private ArrayList<DiscountActionDescription> changes = new ArrayList<DiscountActionDescription>();

	public void addDiscount(DiscountActionDescription arg0) {
		changes.add(arg0);
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		return productName.toString();
	}

	public ArrayList<DiscountActionDescription> getChanges() {
		return changes;
	}

	public void setChanges(ArrayList<DiscountActionDescription> list) {
		changes = list;
	}

	public void printAll() {
		logger.debug("product={}",productName);
		Iterator<DiscountActionDescription> i = changes.iterator();
		while (i.hasNext()) {
			logger.debug(i.next().print());
		}
	}

	public ProductWithChanges clone() {
		ProductWithChanges product = new ProductWithChanges();
		product.setChanges((ArrayList<DiscountActionDescription>) this.getChanges().clone());
		product.setProductName(this.getProductName());
		return product;
	}

}
