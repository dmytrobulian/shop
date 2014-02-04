package com.mycompany.promocalculator;

public class Product {
	private String name;
	private String group;
	private Float price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

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
