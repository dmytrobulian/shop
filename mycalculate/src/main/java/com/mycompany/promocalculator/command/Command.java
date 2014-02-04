package com.mycompany.promocalculator.command;

import com.mycompany.promocalculator.Shop;

public interface Command {
	public boolean execute(Shop shop);
}
