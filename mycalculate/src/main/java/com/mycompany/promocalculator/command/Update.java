package com.mycompany.promocalculator.command;

import com.mycompany.promocalculator.Shop;

public class Update implements Command {

	@Override
	public boolean execute(Shop shop) {
		boolean successful_execution = false;
		shop.init(new String[] { "src\\main\\resources\\pricelist.xml", "src\\main\\resources\\discount.xml",
				"src\\main\\resources\\invoices.xml" });
		successful_execution = true;
		return successful_execution;
	}
}
