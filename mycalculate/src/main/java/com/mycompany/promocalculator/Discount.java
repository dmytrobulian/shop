package com.mycompany.promocalculator;

import com.mycompany.promocalculator.calculation.DiscountComponent;
import com.mycompany.promocalculator.validation.ValidationComponent;

public class Discount {
	private String discountName;
	private Boolean discountActive;
	public ValidationComponent validation;
	public DiscountComponent condition;

	public Boolean getDiscountActive() {
		return discountActive;
	}

	public void setDiscountActive(Boolean discountActive) {
		this.discountActive = discountActive;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
}
