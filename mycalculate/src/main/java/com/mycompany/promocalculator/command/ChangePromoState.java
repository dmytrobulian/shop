package com.mycompany.promocalculator.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import com.mycompany.promocalculator.Discount;
import com.mycompany.promocalculator.Shop;

public class ChangePromoState implements Command {

	@Override
	public boolean execute(Shop shop) {
		boolean successful_execution = false;
		System.out.print("Input discount name:");
		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			String promoname = bufferRead.readLine();
			System.out.print("Input active state (true/false)");
			String promostate = bufferRead.readLine();
			Iterator<Discount> i = shop.discountList.iterator();
			while (i.hasNext()) {
				Discount temp;
				temp = (Discount) i.next();
				if (temp.getDiscountName().equalsIgnoreCase(promoname)) {
					temp.setDiscountActive(new Boolean(promostate));
					System.out.println(temp.getDiscountName() + "  active "
							+ temp.getDiscountActive());
				}
			}
			successful_execution = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return successful_execution;
	}
}
