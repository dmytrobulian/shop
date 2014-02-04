package com.mycompany.promocalculator.command;

import com.mycompany.promocalculator.Shop;

public class DoNothing implements Command {

	@Override
	public boolean execute(Shop shop) {
		return true;
	}
}
