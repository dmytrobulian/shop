package com.mycompany.promocalculator.command;

import java.util.Iterator;

import com.mycompany.promocalculator.Discount;
import com.mycompany.promocalculator.Shop;

public class PromoList implements Command {

	@Override
	public boolean execute(Shop shop) {
		boolean successful_execution = false;
		Iterator<Discount> i = shop.discountList.iterator();
		while (i.hasNext()) {
			Discount temp;
			temp = (Discount) i.next();
			System.out.println(temp.getDiscountName() + "  active "
					+ temp.getDiscountActive());
		}
		successful_execution = true;
		return successful_execution;
	}
}
