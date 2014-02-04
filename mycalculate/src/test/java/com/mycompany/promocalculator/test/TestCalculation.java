package com.mycompany.promocalculator.test;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mycompany.promocalculator.Product;
import com.mycompany.promocalculator.Shop;

public class TestCalculation {
	private Shop shop;

	private void discountInit(String[] filelist) {
		shop = new Shop();
		shop.init(filelist);
	}

	@Test
	public void test1condition() {		
		String[] arg = new String[] { "src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount1.xml",
				"src\\test\\resources\\invoice1.xml" };
		discountInit(arg);
		ArrayList<ArrayList<Product>> responseFromShop = new ArrayList<ArrayList<Product>>();
		responseFromShop = shop.discountCalculate(shop);
		Iterator<ArrayList<Product>> i = responseFromShop.iterator();
		String expectedString = "	pen  0.2	pen  0.2	milk  1.2	milk  1.08	sugar  7.58	sugar  6.822	sugar  7.58	sugar  6.822	sugar  7.58	sugar  6.822	sugar  7.58\r\n	pen  0.2	pen  0.2\r\n	beer  9.0\r\n	beer  9.0	beer  8.1	beer  9.0	beer  8.1\r\n";
		String resultString = "";
		while (i.hasNext()) {
			ArrayList<Product> invoiceResult = i.next();
			Iterator<Product> j = invoiceResult.iterator();
			while (j.hasNext()) {
				resultString += j.next().toString();
			}
			resultString += "\r\n";
		}
		Assert.assertEquals(resultString, expectedString);
	}

	@Test
	public void test2condition() {
		String[] arg = new String[] { "src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount2.xml",
				 "src\\test\\resources\\invoice2.xml" };
		discountInit(arg);
		ArrayList<ArrayList<Product>> responseFromShop = new ArrayList<ArrayList<Product>>();
		responseFromShop = shop.discountCalculate(shop);
		Iterator<ArrayList<Product>> i = responseFromShop.iterator();
		String expectedString = "	pen  0.2	pen  0.2	milk  1.2	milk  1.2	milk  1.2	milk  1.2	milk  1.2	milk  0.0	milk  0.0\r\n	milk  1.2	milk  1.2	milk  0.0\r\n	milk  1.2\r\n	beer  9.0	beer  9.0	beer  9.0	beer  9.0	beer  9.0\r\n";
		String resultString = "";
		while (i.hasNext()) {
			ArrayList<Product> invoiceResult = i.next();
			Iterator<Product> j = invoiceResult.iterator();
			while (j.hasNext()) {
				resultString += j.next().toString();
			}
			resultString += "\r\n";
		}
		Assert.assertEquals(resultString, expectedString);
	}

	@Test
	public void test3condition() {
		String[] arg = new String[] { "src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount3.xml",
				 "src\\test\\resources\\invoice3.xml" };
		discountInit(arg);
		ArrayList<ArrayList<Product>> responseFromShop = new ArrayList<ArrayList<Product>>();
		responseFromShop = shop.discountCalculate(shop);
		Iterator<ArrayList<Product>> i = responseFromShop.iterator();
		String expectedString = "	pen  0.2	beer  7.2	needle  7.2	needle  7.2	gloves  110.0	gloves  110.0\r\n	needle  7.2	needle  7.2	sugar  7.58\r\n	needle  7.2	needle  7.2	gloves  110.0	gloves  110.0	gloves  110.0\r\n	wine  48.4	wine  48.4	wine  48.4	wine  48.4\r\n";
		String resultString = "";
		while (i.hasNext()) {
			ArrayList<Product> invoiceResult = i.next();
			Iterator<Product> j = invoiceResult.iterator();
			while (j.hasNext()) {
				resultString += j.next().toString();
			}
			resultString += "\r\n";
		}
		Assert.assertEquals(resultString, expectedString);
	}

	@Test
	public void test4condition() {
		String[] arg = new String[] { "src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount4.xml",
				 "src\\test\\resources\\invoice4.xml" };
		discountInit(arg);
		ArrayList<ArrayList<Product>> responseFromShop = new ArrayList<ArrayList<Product>>();
		responseFromShop = shop.discountCalculate(shop);
		Iterator<ArrayList<Product>> i = responseFromShop.iterator();
		String expectedString = "	beer  9.0	pen  0.0	pen  0.0\r\n	pen  0.2	needle  7.2	needle  7.2	gloves  110.0	gloves  110.0\r\n	milk  1.2	milk  1.2	milk  1.2	milk  1.2	milk  1.2	pen  0.0	pen  0.0	pen  0.0	pen  0.0	pen  0.0	pen  0.0\r\n";
		String resultString = "";
		while (i.hasNext()) {
			ArrayList<Product> invoiceResult = i.next();
			Iterator<Product> j = invoiceResult.iterator();
			while (j.hasNext()) {
				resultString += j.next().toString();
			}
			resultString += "\r\n";
		}
		Assert.assertEquals(resultString, expectedString);
	}

	@Test
	public void test5condition() {
		String[] arg = new String[] { "src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount5.xml",
				 "src\\test\\resources\\invoice5.xml" };
		discountInit(arg);
		ArrayList<ArrayList<Product>> responseFromShop = new ArrayList<ArrayList<Product>>();
		responseFromShop = shop.discountCalculate(shop);
		Iterator<ArrayList<Product>> i = responseFromShop.iterator();
		String expectedString = "	beer  9.0	beer  9.0	beer  9.0	pen  0.0\r\n	bicycle  781.2	fixed discount  -10.0	percent discount  -77.12\r\n	paper notes  4.0	milk  1.2	milk  1.2	milk  1.2	milk  1.2	milk  1.2	turn-screw  20.0	turn-screw  20.0	hammer  50.0	hammer  50.0	hammer  50.0	pen  0.0	pen  0.0	pen  0.0	percent discount  -20.0\r\n";
		String resultString = "";
		while (i.hasNext()) {
			ArrayList<Product> invoiceResult = i.next();
			Iterator<Product> j = invoiceResult.iterator();
			while (j.hasNext()) {
				resultString += j.next().toString();
			}
			resultString += "\r\n";
		}
		Assert.assertEquals(resultString, expectedString);
	}

	@Test
	public void test6condition() {
		String[] arg = new String[] { "src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount6.xml",
				 "src\\test\\resources\\invoice6.xml" };
		discountInit(arg);
		ArrayList<ArrayList<Product>> responseFromShop = new ArrayList<ArrayList<Product>>();
		responseFromShop = shop.discountCalculate(shop);
		Iterator<ArrayList<Product>> i = responseFromShop.iterator();
		String expectedString = "	paper notes  4.0	milk  1.08	milk  1.2	milk  1.08	milk  1.2	milk  1.08	turn-screw  20.0	turn-screw  18.0	hammer  50.0	hammer  45.0	hammer  50.0\r\n";
		String resultString = "";
		while (i.hasNext()) {
			ArrayList<Product> invoiceResult = i.next();
			Iterator<Product> j = invoiceResult.iterator();
			while (j.hasNext()) {
				resultString += j.next().toString();
			}
			resultString += "\r\n";
		}
		Assert.assertEquals(resultString, expectedString);
	}

	@Test
	public void test7condition() {
		String[] arg = new String[] { "src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount7.xml",
				 "src\\test\\resources\\invoice7.xml" };
		discountInit(arg);
		ArrayList<ArrayList<Product>> responseFromShop = new ArrayList<ArrayList<Product>>();
		responseFromShop = shop.discountCalculate(shop);
		Iterator<ArrayList<Product>> i = responseFromShop.iterator();
		String expectedString = "	paper notes  4.0	milk  1.2	milk  0.0	milk  1.2	milk  1.2	milk  0.0	turn-screw  20.0	turn-screw  20.0	hammer  0.0	hammer  50.0	hammer  50.0\r\n";
		String resultString = "";
		while (i.hasNext()) {
			ArrayList<Product> invoiceResult = i.next();
			Iterator<Product> j = invoiceResult.iterator();
			while (j.hasNext()) {
				resultString += j.next().toString();
			}
			resultString += "\r\n";
		}
		Assert.assertEquals(resultString, expectedString);
	}
}
