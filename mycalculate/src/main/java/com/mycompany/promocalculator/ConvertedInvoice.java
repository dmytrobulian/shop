package com.mycompany.promocalculator;

import java.util.ArrayList;
import java.util.Iterator;

public class ConvertedInvoice {
	private ArrayList<ProductWithChanges> invoice;
	private ArrayList<DiscountActionDescription> changes = new ArrayList<DiscountActionDescription>();
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<DiscountActionDescription> getChanges() {
		return changes;
	}

	public void setChanges(ArrayList<DiscountActionDescription> changes) {
		this.changes = changes;
	}

	public void addProduct(String pName) {
		ProductWithChanges product = new ProductWithChanges();
		product.setProductName(pName);
		invoice.add(product);
	}

	public boolean removeProduct(String pName) {
		Iterator<ProductWithChanges> i = invoice.iterator();
		while (i.hasNext()) {
			ProductWithChanges product = i.next();
			if (product.getProductName().equals(pName)) {
				invoice.remove(product);
				return invoice.remove(product);
			}
		}
		return false;
	}

	public Iterator<ProductWithChanges> getProducts() {
		return invoice.iterator();
	}

	public ConvertedInvoice(ConvertedInvoice cI) {
		invoice = new ArrayList<ProductWithChanges>();
		this.name = cI.getName();
		Iterator<ProductWithChanges> i = cI.getProducts();
		while (i.hasNext()) {
			invoice.add(i.next().clone());
		}
		changes = (ArrayList<DiscountActionDescription>) cI.getChanges().clone();
	}

	public ConvertedInvoice() {
		invoice = new ArrayList<ProductWithChanges>();
	}

	public int size() {
		return invoice.size();
	}

	public void printAll() {
		Iterator<ProductWithChanges> i = invoice.iterator();
		while (i.hasNext()) {
			i.next().printAll();
		}
		Iterator<DiscountActionDescription> ic = changes.iterator();
		while (ic.hasNext()) {
			DiscountActionDescription dd = ic.next();
			logger.debug("invoice changes {} {} {}",new Object[]{dd.getDiscountActionTitle(), dd.getActionType(), dd.getAmountValue()});
		}
	}

}
