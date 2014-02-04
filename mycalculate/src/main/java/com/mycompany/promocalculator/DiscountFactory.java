package com.mycompany.promocalculator;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mycompany.promocalculator.calculation.DiscountCommon;
import com.mycompany.promocalculator.calculation.DiscountComponent;
import com.mycompany.promocalculator.calculation.DiscountComposite;
import com.mycompany.promocalculator.calculation.DiscountGroupName;
import com.mycompany.promocalculator.calculation.DiscountProductName;
import com.mycompany.promocalculator.validation.ValidationCommon;
import com.mycompany.promocalculator.validation.ValidationComponent;
import com.mycompany.promocalculator.validation.ValidationComposite;
import com.mycompany.promocalculator.validation.ValidationGroupAmount;
import com.mycompany.promocalculator.validation.ValidationGroupName;
import com.mycompany.promocalculator.validation.ValidationProductAmount;
import com.mycompany.promocalculator.validation.ValidationProductName;

public class DiscountFactory {

	private static DiscountFactory instance;
	private static DiscountControl control;

	public static DiscountFactory getInstance() {
		if (instance == null) {
			instance = new DiscountFactory();
			control = new DiscountControl();
		}
		return instance;
	}

	public DiscountControl getControl() {
		if (control == null) {
			instance = new DiscountFactory();
			control = new DiscountControl();
		}
		return control;
	}

	public Discount discountParser(Element element) {
		Discount temp = new Discount();
		temp.setDiscountName(element.getElementsByTagName("discountName")
				.item(0).getTextContent());
		ValidationComponent rootValidation = new ValidationComposite();
		temp.validation = rootValidation;

		// ///////////
		DiscountComposite rootCalculation = new DiscountComposite();
		temp.condition = rootCalculation;

		Element condition = (Element) element
				.getElementsByTagName("validation").item(0);
		parseValidation(condition, rootValidation);

		Element calculation = (Element) element.getElementsByTagName(
				"discountCondition").item(0);
		parseCalculation(calculation, rootCalculation);

		HashMap<String, Object> rootParameter = new HashMap<>();
		rootParameter.put("quantity", 1);
		rootCalculation.setParameter(rootParameter);

		temp.setDiscountActive(new Boolean(true));
		return temp;
	}

	private void parseValidation(Element condition, ValidationComponent parent) {
		NodeList grouplist = condition.getElementsByTagName("grouplist");
		if (grouplist.item(0).hasChildNodes()) {
			for (int i = 0; i < grouplist.item(0).getChildNodes().getLength(); i++) {
				ArrayList<String> groupNameList = new ArrayList<String>();
				if (grouplist.item(0).getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element group = (Element) grouplist.item(0).getChildNodes()
							.item(i);
					String s1 = group.getElementsByTagName("name").item(0)
							.getTextContent();
					groupNameList.add(s1);
					int quantity = new Integer(group
							.getElementsByTagName("quantity").item(0)
							.getTextContent()).intValue();
					String amount = group.getElementsByTagName("amount")
							.item(0).getTextContent();
					createValidation(parent, "groupname", quantity,
							groupNameList, new Float(amount));
				}
			}
		}
		NodeList productlist = condition.getElementsByTagName("productlist");
		if (productlist.item(0).hasChildNodes()) {
			for (int i = 0; i < productlist.item(0).getChildNodes().getLength(); i++) {
				ArrayList<String> productNameList = new ArrayList<String>();
				if (productlist.item(0).getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element product = (Element) productlist.item(0)
							.getChildNodes().item(i);
					String s1 = product.getElementsByTagName("name").item(0)
							.getTextContent();
					productNameList.add(s1);
					int quantity = new Integer(product
							.getElementsByTagName("quantity").item(0)
							.getTextContent()).intValue();
					String amount = product.getElementsByTagName("amount")
							.item(0).getTextContent();
					createValidation(parent, "productname", quantity,
							productNameList, new Float(amount));
				}
			}
		}
		NodeList commonList = condition.getElementsByTagName("common");
		if (commonList.item(0).hasChildNodes()) {
			for (int i = 0; i < commonList.item(0).getChildNodes().getLength(); i++) {
				ArrayList<String> validationList = new ArrayList<String>();
				if (commonList.item(0).getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element common = (Element) commonList.item(0)
							.getChildNodes().item(i);
					if (common.hasChildNodes()) {
						Integer quantity = new Integer(common
								.getElementsByTagName("quantity").item(0)
								.getTextContent());
						String amount = common.getElementsByTagName("amount")
								.item(0).getTextContent();
						createValidation(parent, "common", quantity,
								validationList, new Float(amount));
					}
				}
			}
		}
	}

	private void parseCalculation(Element condition, DiscountComponent parent) {
		NodeList grouplist = condition.getElementsByTagName("grouplist");
		if (grouplist.item(0).hasChildNodes()) {
			for (int i = 0; i < grouplist.item(0).getChildNodes().getLength(); i++) {
				ArrayList<String> groupNameList = new ArrayList<String>();
				if (grouplist.item(0).getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element group = (Element) grouplist.item(0).getChildNodes()
							.item(i);
					String s1 = group.getElementsByTagName("name").item(0)
							.getTextContent();
					groupNameList.add(s1);
					int quantity = new Integer(group
							.getElementsByTagName("quantity").item(0)
							.getTextContent()).intValue();
					String amount = group.getElementsByTagName("amount")
							.item(0).getTextContent();
					String type = group.getElementsByTagName("type").item(0)
							.getTextContent();
					createCalculation(parent, "groupname", quantity,
							groupNameList, new Float(amount), type);
				}
			}
		}
		NodeList productlist = condition.getElementsByTagName("productlist");
		if (productlist.item(0).hasChildNodes()) {
			for (int i = 0; i < productlist.item(0).getChildNodes().getLength(); i++) {
				ArrayList<String> productNameList = new ArrayList<String>();
				if (productlist.item(0).getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element product = (Element) productlist.item(0)
							.getChildNodes().item(i);
					String s1 = product.getElementsByTagName("name").item(0)
							.getTextContent();
					productNameList.add(s1);
					int quantity = new Integer(product
							.getElementsByTagName("quantity").item(0)
							.getTextContent()).intValue();
					String amount = product.getElementsByTagName("amount")
							.item(0).getTextContent();
					String type = product.getElementsByTagName("type").item(0)
							.getTextContent();
					createCalculation(parent, "productname", quantity,
							productNameList, new Float(amount), type);
				}
			}
		}
		NodeList commonlist = condition.getElementsByTagName("common");
		if (commonlist.item(0).hasChildNodes()) {
			Element element = (Element) commonlist.item(0);
			NodeList conditionList = element.getElementsByTagName("condition");
			for (int j = 0; j < conditionList.getLength(); j++) {
				if (conditionList.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if (conditionList.item(j).hasChildNodes()) {
						Element counter = (Element) conditionList.item(j);
						Integer quantity = new Integer(counter
								.getElementsByTagName("quantity").item(0)
								.getTextContent());
						String amount = counter.getElementsByTagName("amount")
								.item(0).getTextContent();
						String type = counter.getElementsByTagName("type")
								.item(0).getTextContent();
						createCalculation(parent, "condition", quantity, null,
								new Float(amount), type);
					}
				}
			}
		}

	}

	private void createValidation(ValidationComponent parent, String name,
			Integer quantity, ArrayList<String> items, Float amount) {
		ValidationComponent validation = null;
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		if (amount >= 0) {
			switch (name) {
			case "productname":
				validation = new ValidationProductAmount();
				parameter.put("productname", items);
				break;
			case "groupname":
				validation = new ValidationGroupAmount();
				parameter.put("groupname", items);
				break;
			default:
				validation = new ValidationCommon();
				break;
			}
			parameter.put("amount", amount);
			parameter.put("quantity", quantity);
			validation.setParameter(parameter);
			parent.add(validation);
		} else {
			if (name.equals("productname")) {
				validation = new ValidationProductName();
			} else if (name.equals("groupname")) {
				validation = new ValidationGroupName();
			} else {
				validation = new ValidationCommon();
			}
			parameter.put("amount", amount);
			parameter.put("quantity", new Integer(quantity));
			parameter.put(name, items);
			validation.setParameter(parameter);
			parent.add(validation);
		}
	}

	private void createCalculation(DiscountComponent root, String name,
			Integer quantity, ArrayList<String> items, Float amount, String type) {
		DiscountComponent counter = null;
		if (name.equals("productname")) {
			counter = new DiscountProductName();
		} else if (name.equals("groupname")) {
			counter = new DiscountGroupName();
		} else {
			counter = new DiscountCommon();
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put(name, items);
		param.put("type", type);
		param.put("amount", amount);
		param.put("quantity", new Integer(quantity));
		counter.setParameter(param);
		root.add(counter);

	}

}
