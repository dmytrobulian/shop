package com.mycompany.promocalculator.command;

import com.mycompany.promocalculator.Shop;

public class Update implements Command {

	@Override
	public boolean execute(Shop shop) {
		boolean successful_execution = false;
		shop.init(new String[] { "pricelist.xml", "discount.xml",
				"invoices.xml" });
		successful_execution = true;
		return successful_execution;
	}
}
