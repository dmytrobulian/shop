package com.mycompany.promocalculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mycompany.promocalculator.command.CommandFactory;

public class Shop {

	public PriceList priceList;
	public ArrayList<Discount> discountList;
	public ArrayList<Invoice> invoicesList;

	public static final int END = 0;
	public static final int START = 1;
	private static Shop shop;
	public int state;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		shop = new Shop();
		shop.init(args);
		shop.state = Shop.START;
		while (shop.state != Shop.END) {
			shop.readCommand();
		}
	}

	public void init(String[] args) {
		priceList = getPriceList(args[0]);
		discountList = getDiscountList(args[1]);
		invoicesList = getInvoiceList(args[2]);
	}

	private void readCommand() {
		System.out.print("Input command:");
		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			String s = bufferRead.readLine();
			System.out.println(s);
			CommandFactory.getInstance().createCommand(s).execute(shop);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void invoiceCalculate() {

		Iterator<Invoice> i = invoicesList.iterator();
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("list");
			doc.appendChild(rootElement);

			while (i.hasNext()) {
				Float resultSum = new Float(0);
				Element newInvoice = doc.createElement("invoice");
				rootElement.appendChild(newInvoice);

				Invoice invoice = i.next();
				Iterator<String> producNames = invoice.getProductNames();
				while (producNames.hasNext()) {
					String pName = producNames.next().toString();
					Integer productQuantity = new Integer(
							invoice.getProductQuantity(pName));
					System.out.print("Shop.invoiceCalculate() " + pName + " "
							+ productQuantity + " " + priceList.getPrice(pName)
							+ "  ");
					System.out.format(" %1$.2f %n", priceList.getPrice(pName)
							* productQuantity);
					Element newProduct = doc.createElement("product");
					newInvoice.appendChild(newProduct);
					Element name = doc.createElement("name");
					name.appendChild(doc.createTextNode(pName));
					newProduct.appendChild(name);
					Element quantity = doc.createElement("quantity");
					quantity.appendChild(doc.createTextNode(productQuantity
							.toString()));
					newProduct.appendChild(quantity);

					Element oldPrice = doc.createElement("oldprice");
					oldPrice.appendChild(doc.createTextNode(""
							+ priceList.getPrice(pName) * productQuantity));
					newProduct.appendChild(oldPrice);
					resultSum += (priceList.getPrice(pName) * productQuantity);
				}
				System.out.println(" result sum=" + resultSum);

			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("result.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private PriceList getPriceList(String priceFile) {
		File fXmlFile = new File(priceFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		PriceList prList = new PriceList();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			// doc.getDocumentElement().normalize();
			NodeList productList = doc.getElementsByTagName("product");
			for (int i = 0; i < productList.getLength(); i++) {
				Node nNode = productList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Product p = new Product();
					p.setName(eElement.getElementsByTagName("name").item(0)
							.getTextContent());
					p.setGroup(eElement.getElementsByTagName("group").item(0)
							.getTextContent());
					p.setPrice(new Float(eElement.getElementsByTagName("price")
							.item(0).getTextContent()));
					prList.addProduct(p.getName(), p.getPrice(), p.getGroup());
				}
				;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prList;
	}

	private ArrayList<Discount> getDiscountList(String promoFile) {
		File fXmlFile = new File(promoFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		ArrayList<Discount> promList = new ArrayList<Discount>();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList promoNodeList = doc.getElementsByTagName("promotion");
			for (int i = 0; i < promoNodeList.getLength(); i++) {
				Node nNode = promoNodeList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					promList.add(DiscountFactory.getInstance().discountParser(
							eElement));
				}
				;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return promList;
	}

	private ArrayList<Invoice> getInvoiceList(String invoicesFile) {
		File fXmlFile = new File(invoicesFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		ArrayList<Invoice> invList = new ArrayList<Invoice>();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			// doc.getDocumentElement().normalize();
			NodeList productList = doc.getElementsByTagName("invoice");
			System.out.println("invoices  " + productList.getLength());
			for (int i = 0; i < productList.getLength(); i++) {
				Node nNode = productList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Invoice temp = new Invoice();
					for (int k = 0; k < eElement.getElementsByTagName("name")
							.getLength(); k++) {
						temp.addProduct(eElement.getElementsByTagName("name")
								.item(k).getTextContent(), new Integer(eElement
								.getElementsByTagName("quantity").item(k)
								.getTextContent()));
					}
					invList.add(temp);

				}
				;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return invList;
	}

	public ArrayList<ArrayList<Product>> discountCalculate(Shop s) {
		return DiscountFactory.getInstance().getControl().discountCalculate(s);
	}

	public ArrayList<ConvertedInvoice> convertInvoice(ArrayList<Invoice> iList) {
		Iterator<Invoice> i = iList.iterator();
		ArrayList<ConvertedInvoice> ciList = new ArrayList<ConvertedInvoice>();
		while (i.hasNext()) {
			Invoice temp = i.next();
			ConvertedInvoice ci = new ConvertedInvoice();
			Iterator<String> pi = temp.getProductNames();
			while (pi.hasNext()) {
				String pName = pi.next();
				int n = temp.getProductQuantity(pName);
				for (int j = 0; j < n; j++) {
					ci.addProduct(pName);
				}
			}
			ciList.add(ci);
		}
		return ciList;
	}
}
