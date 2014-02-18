package com.mycompany.promocalculator.command;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.mycompany.promocalculator.Context;

public interface Command {
	public String execute(Context context, String []args) throws ParserConfigurationException, TransformerException, IOException;																  
}
