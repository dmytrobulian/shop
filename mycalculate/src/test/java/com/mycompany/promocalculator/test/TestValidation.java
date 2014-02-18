package com.mycompany.promocalculator.test;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.ConvertedInvoice;
import com.mycompany.promocalculator.validation.ValidationCommon;
import com.mycompany.promocalculator.validation.ValidationComponent;
import com.mycompany.promocalculator.validation.ValidationGroupAmount;
import com.mycompany.promocalculator.validation.ValidationGroupName;
import com.mycompany.promocalculator.validation.ValidationProductAmount;
import com.mycompany.promocalculator.validation.ValidationProductName;

public class TestValidation {
	private Context context;

	@BeforeMethod
	public void f() {
		String[] arg = new String[] { "src\\main\\resources\\pricelist.xml", "src\\main\\resources\\discount.xml", "src\\main\\resources\\invoices.xml" };
		context = new Context();
		context.init(arg);
	}

	@Test
	public void checkValidationAmount() {
		f();
		ValidationComponent va = new ValidationCommon();
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("amount", "0.09");
		parameter.put("quantity", 0);
		va.setParameter(parameter);
		ConvertedInvoice cinvoice = new ConvertedInvoice();
		cinvoice.addProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, context), true);
		parameter.put("amount", "0.9");
		Assert.assertEquals(va.validate(cinvoice, context), false);
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
		Assert.assertEquals(va.validate(cinvoice, context), false);
		cinvoice.addProduct("pen");
		cinvoice.addProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, context), true);
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
		Assert.assertEquals(va.validate(cinvoice, context), true);
		// increase amount
		parameter.put("amount", "0.9");
		Assert.assertEquals(va.validate(cinvoice, context), false);
		// change invoice
		cinvoice.removeProduct("pen");
		cinvoice.addProduct("milk");
		Assert.assertEquals(va.validate(cinvoice, context), false);
		// add corresponded group
		group.add("a");
		Assert.assertEquals(va.validate(cinvoice, context), true);
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
		Assert.assertEquals(va.validate(cinvoice, context), true);
		// increase amount
		parameter.put("amount", "0.9");
		Assert.assertEquals(va.validate(cinvoice, context), false);
		// change invoice
		cinvoice.removeProduct("pen");
		cinvoice.addProduct("milk");
		Assert.assertEquals(va.validate(cinvoice, context), false);
		// add corresponded product
		product.add("milk");
		Assert.assertEquals(va.validate(cinvoice, context), true);
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
		Assert.assertEquals(va.validate(cinvoice, context), false);

		group.add("b");
		Assert.assertEquals(va.validate(cinvoice, context), true);

		group.remove("a");
		Assert.assertEquals(va.validate(cinvoice, context), true);

		cinvoice.removeProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, context), false);

		cinvoice.addProduct("paper notes");
		Assert.assertEquals(va.validate(cinvoice, context), true);
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
		Assert.assertEquals(va.validate(cinvoice, context), false);

		product.add("pen");
		cinvoice.addProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, context), true);

		product.remove("pen");
		Assert.assertEquals(va.validate(cinvoice, context), false);

		cinvoice.removeProduct("pen");
		Assert.assertEquals(va.validate(cinvoice, context), false);

		cinvoice.addProduct("paper notes");
		cinvoice.addProduct("paper notes");
		product.add("paper notes");
		Assert.assertEquals(true, va.validate(cinvoice, context));
	}
}
