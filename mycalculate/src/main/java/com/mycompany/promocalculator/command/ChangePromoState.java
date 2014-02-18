package com.mycompany.promocalculator.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.mycompany.promocalculator.Context;
import com.mycompany.promocalculator.Discount;

public class ChangePromoState implements Command {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public String execute(Context context, String[] args) throws ParserConfigurationException, TransformerException, IOException {
		boolean discountExist = false;
		BufferedReader bufferRead = context.getBufferedReader();
		String promoname = "";
		// find discount
		while (!discountExist) {
			logger.info("Input discount name:");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				logger.debug("sleep error", e);
			}
			promoname = bufferRead.readLine();
			logger.info("discountname=\"{}\"",promoname);
			Iterator<Discount> i = context.discountList.iterator();
			while (i.hasNext() && (!discountExist)) {
				Discount temp;
				temp = (Discount) i.next();
				if (temp.getDiscountName().equalsIgnoreCase(promoname)) {					
					discountExist = true;
				}
			}
			if (!discountExist) {
				CommandFactory.getInstance().createCommand(CommandEnum.SHOWDISCOUNT.command()).execute(context, new String[] {});
				logger.info("Wrong discount name");
				return "Wrong discount name";
			}
		}
		// find out state
		String promostate = "";
		while (!(promostate.equalsIgnoreCase("true") || promostate.equalsIgnoreCase("false"))) {
			logger.info("Input active state (true/false)");
			promostate = bufferRead.readLine();
			logger.info("state=\"{}\"",promostate);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				logger.debug("sleep error", e);
			}
		}
		Iterator<Discount> di = context.discountList.iterator();
		Discount temp;
		while (di.hasNext()) {			
			temp = (Discount) di.next();
			if (temp.getDiscountName().equalsIgnoreCase(promoname)) {
				temp.setDiscountActive(new Boolean(promostate));
				logger.debug("{}  active {}", temp.getDiscountName(), temp.getDiscountActive());
			}
		}
		CommandFactory.getInstance().createCommand(CommandEnum.SHOWDISCOUNT.command()).execute(context, new String[]{"",""});
		return this.getClass().getName() +" executed";
	}
}
