package com.mycompany.promocalculator.command;

import java.util.Iterator;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.Invoice;

public class ShowOneInvoice implements Command {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public String execute(Context context, String[] args) {
		if (args.length > 1) {
			boolean containInvoice = false;
			Iterator<Invoice> i = context.invoicesList.iterator();
			while (i.hasNext()) {
				Invoice temp = i.next();
				if (temp.getInvoiceName().equalsIgnoreCase(args[1])) {
					containInvoice = true;
					logger.info("invoice name={}", temp.getInvoiceName());
					Iterator<String> p = temp.getProductNames();
					while (p.hasNext()) {
						String productname = p.next();
						logger.info("		product name={} quantity={}", productname, temp.getProductQuantity(productname));
					}
				}
			}
			if (!containInvoice){
				logger.info("Does not have invoice with provided name");
				
			}
		}else{
			logger.info("Not enough parameter,example: invoice invoicename");
		}
		return this.getClass().getName() + " executed";
	}
}
