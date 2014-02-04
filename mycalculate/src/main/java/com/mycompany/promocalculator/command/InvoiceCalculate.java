package com.mycompany.promocalculator.command;

import com.mycompany.promocalculator.Shop;

public class InvoiceCalculate implements Command {

	@Override
	public boolean execute(Shop shop) {
		boolean successful_execution = false;
		shop.invoiceCalculate();
		successful_execution = true;
		return successful_execution;
	}
}
