package com.mycompany.promocalculator.command;

import com.mycompany.promocalculator.Context;

public class End implements Command {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	@Override
	public String execute(Context context, String[] args) {
		logger.info("Ending program");
		Context.state = Context.END;		
		return this.getClass().getName() +" executed";
	}

}
