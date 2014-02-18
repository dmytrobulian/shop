package com.mycompany.promocalculator.command;

import com.mycompany.promocalculator.Context;

public class Update implements Command {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	@Override
	public String execute(Context context, String[] args) {
		if (args.length>3){
			String [] removeCommandName = new String[args.length-1];
			for (int i = 1; i < args.length; i++) {
				removeCommandName[i-1] = args[i];
			}			
			context.init(removeCommandName);
		}else{
			logger.warn("Not enough parameters");
			//context.init(new String[] { "src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount.xml", "src\\test\\resources\\invoices.xml" });
		}
		return this.getClass().getName() +" executed";
	}
}
