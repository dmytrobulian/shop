package com.mycompany.promocalculator.command;

import com.mycompany.promocalculator.Context;

public class ShowAvailableCommand implements Command {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public String execute(Context context, String[] args) {
		logger.info("List of available commands:");
		CommandEnum []commandsEnum =  CommandEnum.values();
		for (int i = 0; i < commandsEnum.length; i++) {
			logger.info("	{}", commandsEnum[i].command());
		}		
		return this.getClass().getName() +" executed";
	}
}
