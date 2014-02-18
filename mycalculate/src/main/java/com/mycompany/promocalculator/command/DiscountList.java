package com.mycompany.promocalculator.command;

import java.util.Iterator;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.Discount;

public class DiscountList implements Command {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public String execute(Context context, String[] args) {
		Iterator<Discount> i = context.discountList.iterator();
		while (i.hasNext()) {
			Discount temp;
			temp = (Discount) i.next();
			logger.info("{}   active state={}", temp.getDiscountName(), temp.getDiscountActive());
		}
		return this.getClass().getName() +" executed";
	}
}
