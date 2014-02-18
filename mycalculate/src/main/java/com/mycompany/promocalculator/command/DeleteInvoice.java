package com.mycompany.promocalculator.command;

import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.Invoice;

public class DeleteInvoice implements Command {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	@Override
	public String execute(Context context, String[] args) throws ParserConfigurationException, TransformerException, IOException {
		if (args.length>1){
			Iterator<Invoice> i =context.invoicesList.iterator();
			int index = 0;
			while(i.hasNext()){				
				if(i.next().getInvoiceName().equalsIgnoreCase(args[1])){
					context.invoicesList.remove(index);
					if (context.getCurrentInvoice().getInvoiceName().equalsIgnoreCase(args[1])){
						context.setCurrentInvoice(null);
					}
					logger.info("Invoice {} have been removed",args[1]);
					CommandFactory.getInstance().createCommand(CommandEnum.SHOWINVOICESLIST.command()).execute(context, null);
					return this.getClass().getName() +" executed";
				}
				index++;
			}
			logger.info("Does not have invoice with provided name");
		}else{
			logger.info("Not enough parameter,example: deleteinvoice invoicename");		
		}
		return this.getClass().getName() +" executed";
	}
}
