package com.mycompany.promocalculator;

import org.slf4j.MarkerFactory;


public class Shop {

	private static Context context;	
	// private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Shop.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.trace("logger trace");
		logger.debug("logger debug");
		logger.info("logger info");
		logger.warn("logger warn");
		logger.error("logger error");
		
		if (args.length<2){
			logger.error("please run programm with filename as a parameter");
			logger.info("EXAMPLE: java -jar mycalculate-1.0-SNAPSHOT.jar pricelits.xml discountlist.xml invoices.xml");
		} else {
			context = new Context();
			Context.state = Context.START;
			context.init(args);
			context.run();			
		}		
	}
	public Context getContext(){
		return context;
	}
}
