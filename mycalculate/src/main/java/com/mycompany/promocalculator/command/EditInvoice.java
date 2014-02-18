package com.mycompany.promocalculator.command;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.Invoice;

public class EditInvoice implements Command {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	@Override
	public String execute(Context context, String[] args) throws ParserConfigurationException, TransformerException, IOException {
		if (args.length > 1) {
			Invoice invoice = context.getInvoiceByName(args[1]);
			if (invoice != null) {
				context.setCurrentInvoice(invoice);
				logger.info("Invoice ready for edit, use command addproduct or removeproduct");
				return this.getClass().getName() + " executed";
			}else{
				logger.info("Cannot find invoice with provided name");
			}			
		} else{
			logger.info("Not enough parameter,example: editinvoice invoicename");
		}
		CommandFactory.getInstance().createCommand(CommandEnum.SHOWINVOICESLIST.command()).execute(context, null);
		return this.getClass().getName() + " executed";
	}

}
