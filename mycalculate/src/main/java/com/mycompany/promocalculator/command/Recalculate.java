package com.mycompany.promocalculator.command;

import com.mycompany.promocalculator.Context;

public class Recalculate implements Command {

	@Override
	public String execute(Context context, String[] args) {
		context.discountCalculate();
		return this.getClass().getName() +" executed";
	}
}
