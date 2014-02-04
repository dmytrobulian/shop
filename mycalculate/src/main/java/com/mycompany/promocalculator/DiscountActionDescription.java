package com.mycompany.promocalculator;

public class DiscountActionDescription {
	private String discountActionTitle;
	private String actionType;
	private Float amountValue;

	public DiscountActionDescription(String title, String type, Float amount) {
		setDiscountActionTitle(title);
		setActionType(type);
		setAmountValue(amount);
	}

	public void print() {
		System.out.println("DiscountActionDescription    "
				+ discountActionTitle + "   type=" + actionType + "  amount="
				+ amountValue);
	}

	public String getDiscountActionTitle() {
		return discountActionTitle;
	}

	public void setDiscountActionTitle(String discountActionTitle) {
		this.discountActionTitle = discountActionTitle;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Float getAmountValue() {
		return amountValue;
	}

	public void setAmountValue(Float amountValue) {
		this.amountValue = amountValue;
	}

}
