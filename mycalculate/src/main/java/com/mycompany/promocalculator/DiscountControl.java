package com.mycompany.promocalculator;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class DiscountControl {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	public ArrayList<Product> discountApplyChanges(ConvertedInvoice invoice, Context context) {
		ArrayList<Product> resultArray = new ArrayList<Product>();
		Iterator<ProductWithChanges> i = invoice.getProducts();
		Float resultSum = new Float(0);
		ApplicationContext appContext = new FileSystemXmlApplicationContext("/src/main/java/com/mycompany/promocalculator/beans/beans.xml");
		logger.info("=====invoice name ===== {}",invoice.getName());
		while (i.hasNext()) {
			ProductWithChanges product = i.next();
			Iterator<DiscountActionDescription> discountdescription = product.getChanges().iterator();
			Product resultproduct = (Product) appContext.getBean("product");
			
			resultproduct.setName(product.getProductName());
			resultproduct.setPrice(context.priceList.getPrice(product.getProductName()));

			while (discountdescription.hasNext()) {
				DiscountActionDescription description = discountdescription.next();
				Float discountvalue = new Float(0);
				description.getActionType();
				description.getAmountValue();
				if (description.getActionType().toString().equalsIgnoreCase("percent")) {
					discountvalue = new Float(context.priceList.getPrice(product.getProductName())) * new Float(description.getAmountValue().toString()) / 100;
				} else {
					discountvalue = new Float(description.getAmountValue().toString());
				}
				logger.debug("discount value ={}", discountvalue);
				resultproduct.setPrice(resultproduct.getPrice() - discountvalue);
			}
			resultArray.add(resultproduct);
			resultSum += resultproduct.getPrice();
			logger.info(resultproduct.toString());
		}
		Iterator<DiscountActionDescription> changesiteretor = invoice.getChanges().iterator();
		while (changesiteretor.hasNext()) {
			DiscountActionDescription description = changesiteretor.next();
			if ((!description.getDiscountActionTitle().equalsIgnoreCase("percent discount") && (!description.getDiscountActionTitle().equalsIgnoreCase("fixed discount")))) {// filter
																																												// "discount bonus"
				Product resultproduct = (Product) appContext.getBean("product");
				resultproduct.setName(description.getDiscountActionTitle());
				resultproduct.setPrice(description.getAmountValue());
				resultArray.add(resultproduct);
				resultSum += resultproduct.getPrice();
				logger.info("  {}   bonus price= {}  usual price={}",
						new Object[] { resultproduct.getName(), resultproduct.getPrice(), context.priceList.getPrice(resultproduct.getName()) });
			}
		}
		changesiteretor = invoice.getChanges().iterator();
		while (changesiteretor.hasNext()) {
			DiscountActionDescription description = changesiteretor.next();
			if ((description.getDiscountActionTitle().equalsIgnoreCase("percent discount") || (description.getDiscountActionTitle().equalsIgnoreCase("fixed discount")))) {
				// filter only "discount bonus"
				Product resultproduct = (Product) appContext.getBean("product");
				resultproduct.setName(description.getDiscountActionTitle());
				if (description.getDiscountActionTitle().equalsIgnoreCase("fixed discount")) {
					resultproduct.setPrice(-description.getAmountValue());
				} else {// percent
					resultproduct.setPrice(-description.getAmountValue() * resultSum / 100);
				}
				resultArray.add(resultproduct);
				logger.info("  {}   bonus price= {}  usual price={}",
						new Object[] { resultproduct.getName(), resultproduct.getPrice(), context.priceList.getPrice(resultproduct.getName()) });
				resultSum += resultproduct.getPrice();
			}
		}
		((FileSystemXmlApplicationContext) appContext).close();
		logger.info(" result summ for  invoice = {}", resultSum);
		return resultArray;

	}

	public ArrayList<ArrayList<Product>> discountCalculate(Context context) {
		ArrayList<ConvertedInvoice> convertedInvoiceList = context.convertInvoice(context.invoicesList);
		ArrayList<ArrayList<Product>> result = new ArrayList<ArrayList<Product>>();
		Iterator<ConvertedInvoice> ciIterator = convertedInvoiceList.iterator();
		while (ciIterator.hasNext()) {
			logger.debug("===========start invoice");
			ConvertedInvoice convertedInvoice = (ConvertedInvoice) ciIterator.next();
			Iterator<Discount> discountIterator = context.discountList.iterator();
			while (discountIterator.hasNext()) {
				Discount discount = discountIterator.next();
				boolean validationResult = discount.getDiscountActive() && discount.validation.validate(convertedInvoice, context);
				if (validationResult) {
					int counter = discount.condition.count(convertedInvoice, context);
					logger.debug("   {}     action quantity ", discount.getDiscountName(), counter);
					convertedInvoice = discount.condition.addDiscountToInvoice(convertedInvoice, context, counter, discount.getDiscountName());
					logger.debug("invoice ");
					convertedInvoice.printAll();
				}
			}
			logger.debug("===========finish invoice");
			result.add(discountApplyChanges(convertedInvoice, context));
		}
		return result;
	}

}
