package com.mycompany.promocalculator.command;

import com.mycompany.promocalculator.Context;

public class CommandFactory {

	private static CommandFactory instance;
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	//private HashMap<String, CommandEnum> commandNameContainer;

	

	private CommandFactory() {
		//commandNameContainer = new HashMap<String, CommandEnum>();
		//CommandEnum []commandsEnum =  CommandEnum.values();
		//for (int i = 0; i < commandsEnum.length; i++) {
			//commandNameContainer.put(commandsEnum[i].command(), commandsEnum[i]);
		//}
		/*
		commandNameContainer.put("test", "com.mycompany.promocalculator.command.DoNothing");
		commandNameContainer.put("finish", "com.mycompany.promocalculator.command.End");
		commandNameContainer.put("q", "com.mycompany.promocalculator.command.End");
		commandNameContainer.put("end", "com.mycompany.promocalculator.command.End");
		commandNameContainer.put("count", "com.mycompany.promocalculator.command.InvoiceCalculate");
		commandNameContainer.put("showdiscount", "com.mycompany.promocalculator.command.DiscountList");
		commandNameContainer.put("update", "com.mycompany.promocalculator.command.Update");
		commandNameContainer.put("recount", "com.mycompany.promocalculator.command.Recalculate");
		commandNameContainer.put("discountstate", "com.mycompany.promocalculator.command.ChangePromoState");
		commandNameContainer.put("showprice", "com.mycompany.promocalculator.command.ShowPriceList");
		commandNameContainer.put("showcommand", "com.mycompany.promocalculator.command.ShowAvailableCommand");
		commandNameContainer.put("editinvoice", "com.mycompany.promocalculator.command.EditInvoice");
		commandNameContainer.put("createinvoice", "com.mycompany.promocalculator.command.CreateInvoice");
		commandNameContainer.put("addproduct", "com.mycompany.promocalculator.command.AddProductToInvoice");
		commandNameContainer.put("removeproduct", "com.mycompany.promocalculator.command.RemoveProductFromInvoice");
		commandNameContainer.put("showinvoices", "com.mycompany.promocalculator.command.ShowInvoices");
		commandNameContainer.put("deleteinvoice", "com.mycompany.promocalculator.command.DeleteInvoice");
		commandNameContainer.put("invoice", "com.mycompany.promocalculator.command.ShowOneInvoice");
*/
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
			if (Context.state != Context.DISCOUNTLIST) {
				c = Class.forName(CommandEnum.getCommandClassName(commandName)).newInstance();
			} else
				throw new IllegalAccessException();

		} catch (InstantiationException | IllegalAccessException | NullPointerException | ClassNotFoundException e) {
			c = new ShowAvailableCommand();
			logger.info("\"{}\" - unknown command ", commandName);
			logger.debug(e.getMessage(), e);
		}
		Command cmd = (Command) c;
		return cmd;
	}
}