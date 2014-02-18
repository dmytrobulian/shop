package com.mycompany.promocalculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.MarkerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mycompany.promocalculator.command.CommandEnum;
import com.mycompany.promocalculator.command.CommandFactory;

public class Context {
	public PriceList priceList;
	public ArrayList<Discount> discountList;
	public ArrayList<Invoice> invoicesList;
	public static int state;
	public static final int END = 0;
	public static final int START = 1;
	public static final int INIT = 2;
	public static final int PRICELIST = 4;
	public static final int DISCOUNTLIST = 5;
	public static final int RUN = 6;
	public static final int GATHERING = 7;
	private Invoice currentinvoice;
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	//private InputStream contextInput = System.in;
	private BufferedReader contextBufferedReader= new BufferedReader(new InputStreamReader(System.in));;
	private void readCommand(BufferedReader bufferRead) {
		logger.info("Input command:");
		String commandresult = "";
		try {
			//BufferedReader bufferRead = new BufferedReader(new InputStreamReader(input));
			Thread.sleep(2000);
			String s = bufferRead.readLine();
			String[] commandline = s.split(" ");			
			commandresult = CommandFactory.getInstance().createCommand(commandline[0]).execute(this, commandline);
			logger.debug(commandresult);
		} catch (IOException | ParserConfigurationException | TransformerException | InterruptedException e) {
			logger.error(commandresult);
			logger.error(e.getMessage(), e);
		}
	}

	public void init(String[] args) {
		logger.info("Context initialization");
		try {
			priceList = getPriceList(args[0]);
			discountList = getDiscountList(args[1]);
			invoicesList = getInvoiceList(args[2]);
			state = Context.INIT;
		} catch (ParserConfigurationException | SAXException | IOException| NullPointerException e) {
			initError(e,args);
			
		}
	}

	public void run() {
		if (Context.state == Context.DISCOUNTLIST || Context.state == Context.INIT) {
			Context.state = Context.RUN;
			try {
				CommandFactory.getInstance().createCommand(CommandEnum.SHOWCOMMANDLIST.command()).execute(this, null);
			} catch (ParserConfigurationException | TransformerException | IOException e) {
				Context.state = Context.END;
				logger.debug("", e);
			}
			while (Context.state != Context.END) {
				this.readCommand(contextBufferedReader);
			}
		}
	}

	private void initError(Exception e, String []args) {
		switch (Context.state) {
		case Context.INIT:
			logger.warn("Error processing price list \"{}\"",args[0]);
			break;
		case Context.PRICELIST:
			logger.warn("Error processing discount list \"{}\"",args[1]);
			break;
		case Context.DISCOUNTLIST:
			logger.warn("Error processing invoice list \"{}\"",args[2]);						
			break;
		case Context.RUN:
			logger.info("Type command UPDATE with filename as parameters");
			logger.info("EXAMPLE:update pricelist.xml discount.xml invoices.xml");
			logger.info("or");
			break;
		}
		logger.info("Run programm with parameter");
		logger.info("EXAMPLE:java -jar mycalculate-1.0-SNAPSHOT.jar pricelist.xml discount.xml invoices.xml");		
		logger.debug("{}", e);
	}

	public void invoiceCalculate() throws ParserConfigurationException, TransformerException {
		Iterator<Invoice> i = invoicesList.iterator();

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
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
				Integer productQuantity = new Integer(invoice.getProductQuantity(pName));
				logger.info("{}  {}*{} ={} ", new Object[] { pName, productQuantity, priceList.getPrice(pName), priceList.getPrice(pName) * productQuantity });
				Element newProduct = doc.createElement("product");
				newInvoice.appendChild(newProduct);
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(pName));
				newProduct.appendChild(name);
				Element quantity = doc.createElement("quantity");
				quantity.appendChild(doc.createTextNode(productQuantity.toString()));
				newProduct.appendChild(quantity);

				Element oldPrice = doc.createElement("oldprice");
				oldPrice.appendChild(doc.createTextNode("" + priceList.getPrice(pName) * productQuantity));
				newProduct.appendChild(oldPrice);
				resultSum += (priceList.getPrice(pName) * productQuantity);
			}
			logger.info("============== result sum={}", resultSum);

		}
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("result.xml"));
		transformer.transform(source, result);
	}

	private PriceList getPriceList(String priceFile) throws ParserConfigurationException, SAXException, IOException {
		PriceList prList = new PriceList();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = null;
		File fXmlFile = new File(priceFile);
		doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		NodeList productList = doc.getElementsByTagName("product");		
		ApplicationContext appContext = new FileSystemXmlApplicationContext("/src/main/java/com/mycompany/promocalculator/beans/beans.xml");				
		for (int i = 0; i < productList.getLength(); i++) {
			Node nNode = productList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				Product p = (Product)appContext.getBean("product");
				p.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
				p.setGroup(eElement.getElementsByTagName("group").item(0).getTextContent());
				p.setPrice(new Float(eElement.getElementsByTagName("price").item(0).getTextContent()));
				prList.addProduct(p.getName(), p.getPrice(), p.getGroup());
			}
		}
		((FileSystemXmlApplicationContext)appContext).close();
		state = Context.PRICELIST;
		return prList;
	}

	private ArrayList<Discount> getDiscountList(String promoFile) throws SAXException, IOException, ParserConfigurationException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		ArrayList<Discount> promList = new ArrayList<Discount>();
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = null;
		File fXmlFile = new File(promoFile);
		doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();
		NodeList promoNodeList = doc.getElementsByTagName("promotion");
		for (int i = 0; i < promoNodeList.getLength(); i++) {
			Node nNode = promoNodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				promList.add(DiscountFactory.getInstance().discountParser(eElement));
			}
			;
		}
		state = Context.DISCOUNTLIST;
		return promList;
	}

	private ArrayList<Invoice> getInvoiceList(String invoicesFile) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		ArrayList<Invoice> invList = new ArrayList<Invoice>();
		if (invoicesFile == null) {
			logger.info("Create empty invoices list");
			return invList;
		}
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = null;
		File fXmlFile = new File(invoicesFile);
		doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		NodeList productList = doc.getElementsByTagName("invoice");
		logger.debug("invoices quantity {}", productList.getLength());
		for (int i = 0; i < productList.getLength(); i++) {
			Node nNode = productList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				Invoice temp = new Invoice();
				temp.setInvoiceName(eElement.getElementsByTagName("invoicename").item(0).getTextContent());
				for (int k = 0; k < eElement.getElementsByTagName("name").getLength(); k++) {
					temp.addProduct(eElement.getElementsByTagName("name").item(k).getTextContent(), new Integer(eElement.getElementsByTagName("quantity").item(k).getTextContent()));
				}
				invList.add(temp);
			}
		}
		return invList;
	}

	public ArrayList<ArrayList<Product>> discountCalculate() {
		return DiscountFactory.getInstance().getControl().discountCalculate(this);
	}

	public ArrayList<ConvertedInvoice> convertInvoice(ArrayList<Invoice> iList) {
		Iterator<Invoice> i = iList.iterator();
		ArrayList<ConvertedInvoice> ciList = new ArrayList<ConvertedInvoice>();
		while (i.hasNext()) {			
			Invoice temp = i.next();			
			ConvertedInvoice ci = new ConvertedInvoice();
			ci.setName(temp.getInvoiceName());
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

	public Invoice getCurrentInvoice() {
		if (currentinvoice == null) {
			if (invoicesList.size() > 0) {
				currentinvoice = invoicesList.get(0);
			} else {
				ApplicationContext appContext = new FileSystemXmlApplicationContext("/src/main/java/com/mycompany/promocalculator/beans/beans.xml");
				currentinvoice  = (Invoice) appContext.getBean("invoice");
				currentinvoice.setInvoiceName("autoInvoice");
				invoicesList.add(currentinvoice);
				((FileSystemXmlApplicationContext)appContext).close();
			}
		}
		return currentinvoice;
	}

	public void setCurrentInvoice(Invoice invoice) {
		currentinvoice = invoice;
	}

	public Invoice getInvoiceByName(String name) {
		Iterator<Invoice> i = invoicesList.iterator();
		Invoice result = null;
		while (i.hasNext()) {
			result = i.next();
			if (result.getInvoiceName().equalsIgnoreCase(name)) {
				return result;
			}
		}
		return null;
	}
	public void changeInput(InputStream input){
		contextBufferedReader= new BufferedReader(new InputStreamReader(input));;		
		//contextInput = input;
	}
	public BufferedReader  getBufferedReader(){
		return contextBufferedReader;
	}
}
