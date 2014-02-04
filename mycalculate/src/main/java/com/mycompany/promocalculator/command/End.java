package com.mycompany.promocalculator.command;

import com.mycompany.promocalculator.Shop;

public class End implements Command {

	@Override
	public boolean execute(Shop shop) {
		boolean successful_execution = false;
		shop.state = Shop.END;
		successful_execution = true;
		return successful_execution;
	}
}
