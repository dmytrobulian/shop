package com.mycompany.promocalculator.command;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.mycompany.promocalculator.Context;

public class InvoiceCalculate implements Command {

	@Override
	public String execute(Context context, String[] args) throws ParserConfigurationException, TransformerException {
		context.invoiceCalculate();
		return this.getClass().getName() +" executed";
	}
}
