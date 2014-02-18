package com.mycompany.promocalculator.command;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.mycompany.promocalculator.Context;
public class AddProductToInvoice implements Command {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public String execute(Context context, String[] args) throws ParserConfigurationException, TransformerException, IOException {
		if (args.length > 2){
			if (context.priceList.getPrice(args[1])!=null){
				Integer quantity = new Integer(1);
				try{
					quantity = new Integer(args[2]);
				} catch (NumberFormatException e){
					logger.info("Problem parsing quantity");
					logger.debug("",e);
//					quantity = new Integer(1);
				}
				if (quantity>0){
					context.getCurrentInvoice().addProduct(args[1], new Integer(quantity));
					logger.info("product {} added  quantity={}",args[1],quantity);
					CommandFactory.getInstance().createCommand(CommandEnum.SHOWONEINVOICE.command()).execute(context, new String[]{"",context.getCurrentInvoice().getInvoiceName()});
				}else{
					logger.warn("Wrong product quantity, example: addproduct milk 1");
				}
			} else{
				logger.warn("Wrong product name, example: addproduct milk 1");
			}
		} else{
			logger.warn("Not enough parameter, example: addproduct milk 1");
		}
		
		return this.getClass().getName() +" executed";
	}

}
