package com.mycompany.promocalculator.command;

import com.mycompany.promocalculator.Shop;

public class Recalculate implements Command {

	@Override
	public boolean execute(Shop shop) {
		boolean successful_execution = false;
		shop.discountCalculate(shop);
		successful_execution = true;
		return successful_execution;
	}
}
