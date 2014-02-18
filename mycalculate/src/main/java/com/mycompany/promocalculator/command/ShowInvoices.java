package com.mycompany.promocalculator.command;

import java.util.Iterator;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.Invoice;

public class ShowInvoices implements Command {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public String execute(Context context, String[] args) {
		Iterator<Invoice> i =context.invoicesList.iterator();		
		logger.info("List of invoices:");
		while (i.hasNext()) {
			Invoice temp = i.next();
			logger.info("invoice name={}", temp.getInvoiceName());
			Iterator <String> p = temp.getProductNames();
			while(p.hasNext()){
				String productname = p.next();
				logger.info("		product name={} quantity={}", productname,temp.getProductQuantity(productname));
			}
		}
		return this.getClass().getName() +" executed";
	}
}
