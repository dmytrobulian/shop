package com.mycompany.promocalculator.command;

import java.util.Iterator;

import com.mycompany.promocalculator.Context;

public class ShowPriceList implements Command {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public String execute(Context context, String[] args) {
		Iterator<String> i = context.priceList.getProductsName();
		while (i.hasNext()) {
			String temp;
			temp = i.next();
			logger.info("group={}   product={}  price={}", new Object[]{context.priceList.getGroup(temp), temp, context.priceList.getPrice(temp)});
		}
		return this.getClass().getName() +" executed";
	}
}
