package com.mycompany.promocalculator.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.command.CommandEnum;
import com.mycompany.promocalculator.command.CommandFactory;

public class TestCommand {
	private Context context;
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@BeforeMethod
	public void initTests() {
		String[] arg = new String[] { "src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount.xml", "src\\test\\resources\\invoices.xml" };
		context = new Context();
		context.init(arg);
	}

	@Test
	public void testTest() {
		try {
			logger.info("testTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.TEST.command()).execute(context, new String[] { "", ""}), CommandEnum.TEST.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\testTest.txt","useroutput.log","testTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void countTest() {
		try {
			logger.info("countTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.COUNT.command()).execute(context, new String[] { "", ""}), CommandEnum.COUNT.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\countTest.txt","useroutput.log","countTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void createinvoiceTest() {
		try {
			logger.info("createinvoiceTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.CREATEINVOICE.command()).execute(context, new String[] { "", "newtestinvoice"}), CommandEnum.CREATEINVOICE.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\createinvoiceTest.txt","useroutput.log","createinvoiceTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void createInvoiceWithExistingNameTest() {
		try {
			logger.info("createInvoiceWithExistingNameTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.CREATEINVOICE.command()).execute(context, new String[] { "", "invoice1"}), CommandEnum.CREATEINVOICE.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\createInvoiceWithExistingNameTest.txt","useroutput.log","createInvoiceWithExistingNameTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void editinvoiceTest() {
		try {
			logger.info("editinvoiceTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.EDITINVOICE.command()).execute(context, new String[] { "", "invoice2"}), CommandEnum.EDITINVOICE.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\editinvoiceTest.txt","useroutput.log","editinvoiceTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void editInvoiceNotExistingNameTest() {
		try {
			logger.info("editInvoiceNotExistingNameTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.EDITINVOICE.command()).execute(context, new String[] { "", "invo"}), CommandEnum.EDITINVOICE.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\editInvoiceNotExistingNameTest.txt","useroutput.log","editInvoiceNotExistingNameTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void deleteInvoiceTest() {
		try {
			logger.info("deleteInvoiceTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.DELETEINVOICE.command()).execute(context, new String[] { "", "invoice3"}), CommandEnum.DELETEINVOICE.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\deleteInvoiceTest.txt","useroutput.log","deleteInvoiceTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void deleteAllInvoiceAndAddProductToAutocreatedTest() {
		try {
			logger.info("deleteAllInvoiceAndAddProductToAutocreatedTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.DELETEINVOICE.command()).execute(context, new String[] { "", "invoice3"}), CommandEnum.DELETEINVOICE.className()+" executed");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.DELETEINVOICE.command()).execute(context, new String[] { "", "invoice1"}), CommandEnum.DELETEINVOICE.className()+" executed");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.DELETEINVOICE.command()).execute(context, new String[] { "", "invoice2"}), CommandEnum.DELETEINVOICE.className()+" executed");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.ADDPRODUCT.command()).execute(context, new String[] { "", "beer","1"}), CommandEnum.ADDPRODUCT.className()+" executed");
			
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\deleteAllInvoiceAndAddProductToAutocreatedTest.txt","useroutput.log","deleteAllInvoiceAndAddProductToAutocreatedTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void deleteNotExistingInvoiceTest() {
		try {
			logger.info("deleteNotExistingInvoiceTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.DELETEINVOICE.command()).execute(context, new String[] { "", "in3"}), CommandEnum.DELETEINVOICE.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\deleteNotExistingInvoiceTest.txt","useroutput.log","deleteNotExistingInvoiceTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void qTest() {
		try {
			logger.info("qTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.Q.command()).execute(context, new String[] { "", ""}), CommandEnum.Q.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\qTest.txt","useroutput.log","qTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void showCommandListTest() {
		try {
			logger.info("showCommandListTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.SHOWCOMMANDLIST.command()).execute(context, new String[] { "", ""}), CommandEnum.SHOWCOMMANDLIST.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\showCommandListTest.txt","useroutput.log","showCommandListTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void finishTest() {
		try {
			logger.info("finishTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.FINISH.command()).execute(context, new String[] { "", ""}), CommandEnum.FINISH.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\finishTest.txt","useroutput.log","finishTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void addproductTest() {
		try {
			logger.info("addproductTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.ADDPRODUCT.command()).execute(context, new String[] { "", "milk", "1", "" }), CommandEnum.ADDPRODUCT.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\addproductTest.txt","useroutput.log","addproductTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void addproductNotExistingNameTest() {
		try {
			logger.info("addproductNotExistingNameTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.ADDPRODUCT.command()).execute(context, new String[] { "", "milka", "1", "" }), CommandEnum.ADDPRODUCT.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\addproductNotExistingNameTest.txt","useroutput.log","addproductNotExistingNameTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void addproductIncorrectQuantityTest() {
		try {
			logger.info("addproductIncorrectQuantityTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.ADDPRODUCT.command()).execute(context, new String[] { "", "milk", "a1", "" }), CommandEnum.ADDPRODUCT.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\addproductIncorrectQuantityTest.txt","useroutput.log","addproductIncorrectQuantityTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void removeproductTest() {
		try {
			logger.info("removeproductTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.REMOVEPRODUCT.command()).execute(context, new String[] { "", "milk"}), CommandEnum.REMOVEPRODUCT.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\removeproductTest.txt","useroutput.log","removeproductTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void removeproductNotExistingTest() {
		try {
			logger.info("removeproductNotExistingTest");
			CommandFactory.getInstance().createCommand(CommandEnum.EDITINVOICE.command()).execute(context, new String[] { "", "invoice3"});
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.REMOVEPRODUCT.command()).execute(context, new String[] { "", "milk"}), CommandEnum.REMOVEPRODUCT.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\removeproductNotExistingTest.txt","useroutput.log","removeproductNotExistingTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void removeproductNotExistingInPriceTest() {
		try {
			logger.info("removeproductNotExistingInPriceTest");
			CommandFactory.getInstance().createCommand(CommandEnum.EDITINVOICE.command()).execute(context, new String[] { "", "invoice3"});
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.REMOVEPRODUCT.command()).execute(context, new String[] { "", "milko"}), CommandEnum.REMOVEPRODUCT.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\removeproductNotExistingInPriceTest.txt","useroutput.log","removeproductNotExistingInPriceTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void recountTest() {
		try {
			logger.info("recountTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.RECOUNT.command()).execute(context, new String[] { "", ""}), CommandEnum.RECOUNT.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\recountTest.txt","useroutput.log","recountTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void updateTest() {
		try {			
			logger.info("updateTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.UPDATE.command()).execute(context, new String[] {"","src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount.xml", "src\\test\\resources\\invoices.xml"}), CommandEnum.UPDATE.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\updateTest.txt","useroutput.log","updateTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void updateTestBadFileNameInvoice() {
		try {			
			logger.info("updateTestBadFileNameInvoice");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.UPDATE.command()).execute(context, new String[] {"","src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount.xml", "src\\test\\resources\\ices.xml"}), CommandEnum.UPDATE.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\updateTestBadFileNameInvoice.txt","useroutput.log","updateTestBadFileNameInvoice"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void updateTestCorruptedinvoiceInvoice() {
		try {			
			logger.info("updateTestCorruptedinvoiceInvoice");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.UPDATE.command()).execute(context, new String[] {"","src\\test\\resources\\pricelist.xml", "src\\test\\resources\\discount.xml", "src\\test\\resources\\corruptedinvoice.xml"}), CommandEnum.UPDATE.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\updateTestCorruptedinvoiceInvoice.txt","useroutput.log","updateTestCorruptedinvoiceInvoice"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void showOneInvoiceTest() {
		try {
			logger.info("showOneInvoiceTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.SHOWONEINVOICE.command()).execute(context, new String[] { "", ""}), CommandEnum.SHOWONEINVOICE.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\showOneInvoiceTest.txt","useroutput.log","showOneInvoiceTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void showPriceListTest() {
		try {
			logger.info("showPriceListTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.SHOWPRICELIST.command()).execute(context, new String[] { "", ""}), CommandEnum.SHOWPRICELIST.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\showPriceListTest.txt","useroutput.log","showPriceListTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void changeDiscountStateFromFileTest() {
		try {
			logger.info("changeDiscountStateFromFileTest");
			context.changeInput(new FileInputStream("src\\test\\resources\\changestatetest.txt"));
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.CHANGEDISCOUNTSTATE.command()).execute(context, new String[] { "", ""}), CommandEnum.CHANGEDISCOUNTSTATE.className()+" executed");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.CHANGEDISCOUNTSTATE.command()).execute(context, new String[] { "", ""}), CommandEnum.CHANGEDISCOUNTSTATE.className()+" executed");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.CHANGEDISCOUNTSTATE.command()).execute(context, new String[] { "", ""}), "Wrong discount name");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\changeDiscountStateFromFileTest.txt","useroutput.log","changeDiscountStateFromFileTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void showNotExistingdiscountTest() {
		try {
			logger.info("showNotExistingdiscountTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.SHOWDISCOUNT.command()).execute(context, new String[] { "", "qwe"}), CommandEnum.SHOWDISCOUNT.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\showNotExistingdiscountTest.txt","useroutput.log","showNotExistingdiscountTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	@Test
	public void showdiscountTest() {
		try {			
			logger.info("showdiscountTest");
			Assert.assertEquals(CommandFactory.getInstance().createCommand(CommandEnum.SHOWDISCOUNT.command()).execute(context, new String[] { "", "drink"}), CommandEnum.SHOWDISCOUNT.className()+" executed");
			Assert.assertEquals(compareLogFiles("src\\test\\resources\\showdiscountTest.txt","useroutput.log","showdiscountTest"),true);
		} catch (ParserConfigurationException |TransformerException |IOException e) {
			logger.error("Test error",e);
		}
	}
	private boolean compareLogFiles(String expectedTestFile, String actualLogFile, String methodName){
		int stringNumber =0;
		try {
			BufferedReader expectedFile = new BufferedReader(new InputStreamReader(new FileInputStream(expectedTestFile)));			
			BufferedReader actualFile = new BufferedReader(new InputStreamReader(new FileInputStream(actualLogFile)));
			String expected="";
			String actual="";
			boolean equal = true;
			while (actual!=null&&!actual.endsWith(methodName)){
				actual = actualFile.readLine();
			}
			while (expectedFile.ready()&&actualFile.ready()&&equal){				
				expected = expectedFile.readLine();
				actual = actualFile.readLine();
				equal = expected.equals(actual);
				stringNumber++;
			}
			expectedFile.close();
			actualFile.close();
			if (!equal||actual==null){
				logger.info("Files are different: line number {}",stringNumber);
				logger.info("expected:{}",expected);
				logger.info("actual:{}",actual);
				return false;
			}	
		} catch (IOException e) {
			logger.info("Error during compare files",e);
			return false;						
		}
		return true;		
	}
}
