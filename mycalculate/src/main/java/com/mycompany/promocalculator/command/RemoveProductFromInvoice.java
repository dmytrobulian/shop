package com.mycompany.promocalculator.command;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.mycompany.promocalculator.Context;

public class RemoveProductFromInvoice implements Command {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	@Override
	public String execute(Context context, String[] args) throws ParserConfigurationException, TransformerException, IOException {
		if (args.length > 1) {
			logger.info("Attempt to remove {}",args[1]);
			context.getCurrentInvoice().removeProduct(args[1]);
			CommandFactory.getInstance().createCommand(CommandEnum.SHOWONEINVOICE.command()).execute(context, new String[]{"",context.getCurrentInvoice().getInvoiceName()});
		}
		return this.getClass().getName() +" executed";
		
	}

}
