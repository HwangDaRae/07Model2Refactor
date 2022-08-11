package com.model2.mvc.service.domain;
/*
	prod_no				NUMBER(20)			NOT NULL REFERENCES product(prod_no),
	user_id				VARCHAR2(20)		NOT NULL REFERENCES users(user_id),
	image				VARCHAR(50),
	product_name		VARCHAR2(100),
	product_detail		VARCHAR2(200),
	amount				NUMBER(10),
	price				NUMBER(10)
*/
public class Cart {
	
	private int prod_no;
	private String user_id;
	private String image;
	private String prod_name;
	private String prod_detail;
	private int amount;
	private int price;
	private int prod_amount;
	
	public Cart() {
	}

	public Cart(int prod_no, String user_id, String image, String prod_name, String prod_detail, int amount, int price,
			int prod_amount) {
		super();
		this.prod_no = prod_no;
		this.user_id = user_id;
		this.image = image;
		this.prod_name = prod_name;
		this.prod_detail = prod_detail;
		this.amount = amount;
		this.price = price;
		this.prod_amount = prod_amount;
	}

	public int getProd_no() {
		return prod_no;
	}

	public void setProd_no(int prod_no) {
		this.prod_no = prod_no;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getProd_detail() {
		return prod_detail;
	}

	public void setProd_detail(String prod_detail) {
		this.prod_detail = prod_detail;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getProd_amount() {
		return prod_amount;
	}

	public void setProd_amount(int prod_amount) {
		this.prod_amount = prod_amount;
	}

	@Override
	public String toString() {
		return "Cart [prod_no=" + prod_no + ", user_id=" + user_id + ", image=" + image + ", prod_name=" + prod_name
				+ ", prod_detail=" + prod_detail + ", amount=" + amount + ", price=" + price + ", prod_amount="
				+ prod_amount + "]";
	}

}
