package com.mycompany.promocalculator.command;

import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.Invoice;

public class CreateInvoice implements Command {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	@Override
	public String execute(Context context, String[] args) throws ParserConfigurationException, TransformerException, IOException {
		if (args.length>1){			
			Iterator<Invoice> i =context.invoicesList.iterator();
			boolean containInvoice = false;
			while(i.hasNext()){
				if(i.next().getInvoiceName().equalsIgnoreCase(args[1])){
					containInvoice=true;
				};
			}
			if (containInvoice){
				logger.info("Already contain invoice with same name");
			}else{
				ApplicationContext appContext = new FileSystemXmlApplicationContext("/src/main/java/com/mycompany/promocalculator/beans/beans.xml");				
				Invoice invoice = (Invoice)appContext.getBean("invoice");
				((FileSystemXmlApplicationContext)appContext).close();
				invoice.setInvoiceName(args[1]);
				context.invoicesList.add(invoice);
				context.setCurrentInvoice(invoice);
				logger.info("Invoices have been created");
				CommandFactory.getInstance().createCommand(CommandEnum.SHOWINVOICESLIST.command()).execute(context, null);
			}			
		}
		return this.getClass().getName() +" executed";
	}

}
