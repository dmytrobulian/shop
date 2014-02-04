package com.mycompany.promocalculator;

import java.util.ArrayList;
import java.util.Iterator;

public class DiscountControl {

	public ArrayList<Product> discountApplyChanges(ConvertedInvoice invoice,
			Shop shop) {
		ArrayList<Product> resultArray = new ArrayList<Product>();
		Iterator<ProductWithChanges> i = invoice.getProducts();
		Float resultSum = new Float(0);
		while (i.hasNext()) {
			ProductWithChanges product = i.next();
			Iterator<DiscountActionDescription> discountdescription = product
					.getChanges().iterator();
			Product resultproduct = new Product();
			resultproduct.setName(product.getProductName());
			resultproduct.setPrice(shop.priceList.getPrice(product
					.getProductName()));

			while (discountdescription.hasNext()) {
				DiscountActionDescription description = discountdescription
						.next();
				Float discountvalue = new Float(0);
				description.getActionType();
				description.getAmountValue();
				if (description.getActionType().toString()
						.equalsIgnoreCase("percent")) {
					discountvalue = new Float(shop.priceList.getPrice(product
							.getProductName()))
							* new Float(description.getAmountValue().toString())
							/ 100;
				} else {
					discountvalue = new Float(description.getAmountValue()
							.toString());
				}
				System.out.print("discount value =" + discountvalue + "  ");
				resultproduct
						.setPrice(resultproduct.getPrice() - discountvalue);
			}
			resultArray.add(resultproduct);
			resultSum += resultproduct.getPrice();
			System.out.println(resultproduct);
		}
		Iterator<DiscountActionDescription> changesiteretor = invoice
				.getChanges().iterator();
		while (changesiteretor.hasNext()) {
			DiscountActionDescription description = changesiteretor.next();
			if ((!description.getDiscountActionTitle().equalsIgnoreCase(
					"percent discount") && (!description
					.getDiscountActionTitle()
					.equalsIgnoreCase("fixed discount")))) {// filter exclude
															// "discount bonus"
				Product resultproduct = new Product();
				resultproduct.setName(description.getDiscountActionTitle());
				resultproduct.setPrice(description.getAmountValue());
				resultArray.add(resultproduct);
				resultSum += resultproduct.getPrice();
				System.out.println("    " + resultproduct.getName()
						+ "  bonus price= " + resultproduct.getPrice()
						+ "   old price= "
						+ shop.priceList.getPrice(resultproduct.getName()));
			}
		}
		changesiteretor = invoice.getChanges().iterator();
		while (changesiteretor.hasNext()) {
			DiscountActionDescription description = changesiteretor.next();
			if ((description.getDiscountActionTitle().equalsIgnoreCase(
					"percent discount") || (description
					.getDiscountActionTitle()
					.equalsIgnoreCase("fixed discount")))) {// filter only
															// "discount bonus"
				Product resultproduct = new Product();
				resultproduct.setName(description.getDiscountActionTitle());
				if (description.getDiscountActionTitle().equalsIgnoreCase(
						"fixed discount")) {
					resultproduct.setPrice(-description.getAmountValue());
				} else {// percent
					resultproduct.setPrice(-description.getAmountValue()
							* resultSum / 100);
				}
				resultArray.add(resultproduct);
				System.out.println("    " + resultproduct.getName()
						+ "  bonus price= " + resultproduct.getPrice()
						+ "   old price= " + resultSum);
				resultSum += resultproduct.getPrice();
			}
		}
		System.out
				.println("DiscountFactory.discountApplyChanges() result summ for  invoice = "
						+ resultSum);
		return resultArray;

	}

	public ArrayList<ArrayList<Product>> discountCalculate(Shop shop) {
		ArrayList<ConvertedInvoice> convertedInvoiceList = shop
				.convertInvoice(shop.invoicesList);
		ArrayList<ArrayList<Product>> result = new ArrayList<ArrayList<Product>>();
		Iterator<ConvertedInvoice> ciIterator = convertedInvoiceList.iterator();
		while (ciIterator.hasNext()) {
			System.out
					.println("DiscountFactory.discountCalculate(===========start invoice)");
			ConvertedInvoice convertedInvoice = (ConvertedInvoice) ciIterator
					.next();
			Iterator<Discount> discountIterator = shop.discountList.iterator();
			while (discountIterator.hasNext()) {
				Discount discount = discountIterator.next();
				boolean validationResult = discount.getDiscountActive()
						&& discount.validation.validate(convertedInvoice, shop);
				if (validationResult) {
					int counter = discount.condition.count(convertedInvoice,
							shop);
					System.out.println("   " + discount.getDiscountName()
							+ "    action quantity " + counter);
					convertedInvoice = discount.condition.addDiscountToInvoice(
							convertedInvoice, shop, counter,
							discount.getDiscountName());
					System.out.println("invoice ");
					convertedInvoice.printAll();
				}
			}
			System.out
					.println("DiscountFactory.discountCalculate(===========finish invoice)");
			result.add(discountApplyChanges(convertedInvoice, shop));
		}
		return result;
	}

}
