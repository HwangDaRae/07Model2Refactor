package com.model2.mvc.common;

public class CookieInfo {
	
	private int prod_no;
	private int amount;
	
	public CookieInfo(int prod_no, int amount) {
		this.prod_no = prod_no;
		this.amount = amount;
	}

	public int getProd_no() {
		return prod_no;
	}

	public void setProd_no(int prod_no) {
		this.prod_no = prod_no;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cookie [prod_no=");
		builder.append(prod_no);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}

}
