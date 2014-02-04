package com.mycompany.promocalculator.test;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.Shop;
import com.mycompany.promocalculator.validation.ValidationCommon;
import com.mycompany.promocalculator.validation.ValidationComponent;
import com.mycompany.promocalculator.validation.ValidationGroupAmount;
import com.mycompany.promocalculator.validation.ValidationGroupName;
import com.mycompany.promocalculator.validation.ValidationProductAmount;
import com.mycompany.promocalculator.validation.ValidationProductName;

public class TestValidations {
	private Shop shop;

	@BeforeClass
	public void f() {
		String[] arg = new String[] { "src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount1.xml",
				"src\\test\\resources\\invoice1.xml" };
		shop = new Shop();
		shop.init(arg);
	}

	@Test
	public void checkValidationAmount() {
		ValidationComponent va = new ValidationCommon();
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("amount", "0.09");
		parameter.put("quantity", 0);
		va.setParameter(parameter);
		ConvertedInvoice cinvoice = new ConvertedInvoice();
		cinvoice.addProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, shop), true);
		parameter.put("amount", "0.9");
		Assert.assertEquals(va.validate(cinvoice, shop), false);
	}

	@Test
	public void checkValidationCommonCount() {
		ValidationComponent va = new ValidationCommon();
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("amount", 0);
		parameter.put("quantity", 5);
		va.setParameter(parameter);
		ConvertedInvoice cinvoice = new ConvertedInvoice();
		cinvoice.addProduct("pen");
		cinvoice.addProduct("pen");
		cinvoice.addProduct("pen");
		cinvoice.addProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, shop), false);
		cinvoice.addProduct("pen");
		cinvoice.addProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, shop), true);
	}

	@Test
	public void checkValidationGroupAmount() {
		ValidationComponent va = new ValidationGroupAmount();
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		ArrayList<String> group = new ArrayList<>();
		group.add("b");
		parameter.put("amount", "0.09");
		parameter.put("groupname", group);
		va.setParameter(parameter);
		ConvertedInvoice cinvoice = new ConvertedInvoice();
		cinvoice.addProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, shop), true);
		// increase amount
		parameter.put("amount", "0.9");
		Assert.assertEquals(va.validate(cinvoice, shop), false);
		// change invoice
		cinvoice.removeProduct("pen");
		cinvoice.addProduct("milk");
		Assert.assertEquals(va.validate(cinvoice, shop), false);
		// add corresponded group
		group.add("a");
		Assert.assertEquals(va.validate(cinvoice, shop), true);
	}

	@Test
	public void checkValidationProductAmount() {
		ValidationComponent va = new ValidationProductAmount();
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		ArrayList<String> product = new ArrayList<>();
		product.add("pen");
		parameter.put("amount", "0.09");
		parameter.put("productname", product);
		va.setParameter(parameter);
		ConvertedInvoice cinvoice = new ConvertedInvoice();
		cinvoice.addProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, shop), true);
		// increase amount
		parameter.put("amount", "0.9");
		Assert.assertEquals(va.validate(cinvoice, shop), false);
		// change invoice
		cinvoice.removeProduct("pen");
		cinvoice.addProduct("milk");
		Assert.assertEquals(va.validate(cinvoice, shop), false);
		// add corresponded product
		product.add("milk");
		Assert.assertEquals(va.validate(cinvoice, shop), true);
	}

	@Test
	public void checkValidationGroupName() {
		ValidationComponent va = new ValidationGroupName();
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		ArrayList<String> group = new ArrayList<>();
		group.add("a");
		parameter.put("groupname", group);
		parameter.put("quantity", 2);
		va.setParameter(parameter);
		ConvertedInvoice cinvoice = new ConvertedInvoice();
		cinvoice.addProduct("pen");
		cinvoice.addProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, shop), false);

		group.add("b");
		Assert.assertEquals(va.validate(cinvoice, shop), true);

		group.remove("a");
		Assert.assertEquals(va.validate(cinvoice, shop), true);

		cinvoice.removeProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, shop), false);

		cinvoice.addProduct("paper notes");
		Assert.assertEquals(va.validate(cinvoice, shop), true);
	}

	@Test
	public void checkValidationProductName() {
		ValidationComponent va = new ValidationProductName();
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		ArrayList<String> product = new ArrayList<>();
		product.add("milk");
		parameter.put("productname", product);
		parameter.put("quantity", 2);
		va.setParameter(parameter);
		ConvertedInvoice cinvoice = new ConvertedInvoice();
		cinvoice.addProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, shop), false);

		product.add("pen");
		cinvoice.addProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, shop), true);

		product.remove("pen");
		Assert.assertEquals(va.validate(cinvoice, shop), false);

		cinvoice.removeProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, shop), false);

		cinvoice.addProduct("paper notes");
		cinvoice.addProduct("paper notes");
		product.add("paper notes");
		Assert.assertEquals(true, va.validate(cinvoice, shop));
	}
}
