package com.mycompany.promocalculator.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mycompany.promocalculator.Shop;
import com.mycompany.promocalculator.command.CommandFactory;

public class TestCommand {
	private Shop shop;

	@BeforeMethod
	public void f() {
		String[] arg = new String[] { "src\\main\\resources\\pricelist.xml",
				"src\\main\\resources\\discount.xml",
				"src\\main\\resources\\invoices.xml" };
		shop = new Shop();
		shop.init(arg);
	}

	@Test
	public void commandsTest() {
		String[] commands = new String[] { "InvoiceCalculate", "DoNothing",
				"End", "PromoList", "Update", "Recalculate", "sdf" };

		for (int i = 0; i < commands.length; i++) {
			Assert.assertEquals(
					CommandFactory
							.getInstance()
							.createCommand(commands[i])
							.execute(shop),
					true);

		}
	}
}
