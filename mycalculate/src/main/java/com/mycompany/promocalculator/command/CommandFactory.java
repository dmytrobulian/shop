package com.mycompany.promocalculator.command;

import com.mycompany.promocalculator.command.Command;

public class CommandFactory {
	private static CommandFactory instance;

	private CommandFactory() {

	}

	public static CommandFactory getInstance() {
		if (instance == null) {
			instance = new CommandFactory();
		}
		return instance;
	}

	public Command createCommand(String commandName) {
		Object c = null;
		try {
			c = Class.forName("com.mycompany.promocalculator.command." + commandName)
					.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			c = new DoNothing();
			System.out.println("Unknown command ");
			// e.printStackTrace();
		}
		Command cmd = (Command) c;
		return cmd;
	}
}
