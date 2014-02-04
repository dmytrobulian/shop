package com.mycompany.promocalculator.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mycompany.promocalculator.Shop;
import com.mycompany.promocalculator.command.CommandFactory;

public class TestCommand {
	private Shop shop;

	@BeforeClass
	public void f() {
		String[] arg = new String[] { "src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount1.xml",
				"src\\test\\resources\\invoice1.xml" };
		shop = new Shop();
		shop.init(arg);
	}

	@Test
	public void commandsTest() {
		String[] commands = new String[] { "InvoiceCalculate", "DoNothing",
				"End", "PromoList", "Update", "Recalculate", "sdf" };

		for (int i = 0; i < commands.length; i++) {
			Assert.assertEquals(
					CommandFactory.getInstance().createCommand(commands[i])
							.execute(shop), true);

		}
	}
}
