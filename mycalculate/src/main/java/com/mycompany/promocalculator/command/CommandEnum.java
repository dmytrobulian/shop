package com.mycompany.promocalculator.command;

public enum CommandEnum {
	TEST("test","com.mycompany.promocalculator.command.DoNothing"),
	Q("q", "com.mycompany.promocalculator.command.End"),
	FINISH("finish","com.mycompany.promocalculator.command.End"),		
	END("end", "com.mycompany.promocalculator.command.End"),
	SHOWCOMMANDLIST("showcommand", "com.mycompany.promocalculator.command.ShowAvailableCommand"),
	SHOWPRICELIST("showprice", "com.mycompany.promocalculator.command.ShowPriceList"),
	SHOWINVOICESLIST("showinvoices", "com.mycompany.promocalculator.command.ShowInvoices"),
	SHOWONEINVOICE("invoice", "com.mycompany.promocalculator.command.ShowOneInvoice"),
	COUNT("count", "com.mycompany.promocalculator.command.InvoiceCalculate"),
	SHOWDISCOUNT("showdiscount", "com.mycompany.promocalculator.command.DiscountList"),		
	RECOUNT("recount", "com.mycompany.promocalculator.command.Recalculate"),
	CHANGEDISCOUNTSTATE("discountstate", "com.mycompany.promocalculator.command.ChangePromoState"),
	UPDATE("update", "com.mycompany.promocalculator.command.Update"),		
	CREATEINVOICE("createinvoice", "com.mycompany.promocalculator.command.CreateInvoice"),		
	EDITINVOICE("editinvoice", "com.mycompany.promocalculator.command.EditInvoice"),
	DELETEINVOICE("deleteinvoice", "com.mycompany.promocalculator.command.DeleteInvoice"),
	ADDPRODUCT("addproduct", "com.mycompany.promocalculator.command.AddProductToInvoice"),
	REMOVEPRODUCT("removeproduct", "com.mycompany.promocalculator.command.RemoveProductFromInvoice");		

	private final String className;
	private final String command;

	private CommandEnum(String commandString, String classString) {			
		command = commandString;
		className = classString;
	}

	public String className() {
		return className;
	}

	public String command() {
		return command;
	}
	public static String getCommandClassName(String commandString){
		CommandEnum []commandsEnum =  CommandEnum.values();
		for (int i = 0; i < commandsEnum.length; i++) {
			if (commandString.equalsIgnoreCase(commandsEnum[i].command())){
				return commandsEnum[i].className();
			}
		}
		return commandString;
	}
	
}
