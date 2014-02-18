package com.mycompany.promocalculator;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class Product {
	private String name;
	private String group;
	private Float price;
	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@XmlTransient
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	@XmlElement
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		String s = "	" + name + "  " + price;
		if (group != null) {
			s = group + s;
		}
		return s;
	}
}
