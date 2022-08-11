package com.model2.mvc.service.domain;

import java.sql.Date;

/*
CREATE TABLE product ( 
	prod_no 			NUMBER 			NOT NULL,
	prod_name 			VARCHAR2(100) 	NOT NULL,
	prod_detail 		VARCHAR2(200),
	manufacture_day		VARCHAR2(8),
	price 				NUMBER(10),
	image_file 			VARCHAR2(100),
	reg_date 			DATE,
	amount				NUMBER(10),
	PRIMARY KEY(prod_no)
);
*/

public class Product {

	private int prodNo;
	private String prodName;
	private String prodDetail;
	private String manuDate;
	private int price;
	private String fileName;
	private Date regDate;
	private int amount;
	private String proTranCode;

	public Product() {
	}

	public Product(int prodNo, String prodName, String prodDetail, String manuDate, int price, String fileName,
			Date regDate, int amount) {
		super();
		this.prodNo = prodNo;
		this.prodName = prodName;
		this.prodDetail = prodDetail;
		this.manuDate = manuDate;
		this.price = price;
		this.fileName = fileName;
		this.regDate = regDate;
		this.amount = amount;
	}

	public Product(int prodNo, String prodName, String prodDetail, String manuDate, int price, String fileName,
			Date regDate, int amount, String proTranCode) {
		super();
		this.prodNo = prodNo;
		this.prodName = prodName;
		this.prodDetail = prodDetail;
		this.manuDate = manuDate;
		this.price = price;
		this.fileName = fileName;
		this.regDate = regDate;
		this.amount = amount;
		this.proTranCode = proTranCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getManuDate() {
		return manuDate;
	}

	public void setManuDate(String manuDate) {
		this.manuDate = manuDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getProdDetail() {
		return prodDetail;
	}

	public void setProdDetail(String prodDetail) {
		this.prodDetail = prodDetail;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public int getProdNo() {
		return prodNo;
	}

	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getProTranCode() {
		return proTranCode;
	}

	public void setProTranCode(String proTranCode) {
		this.proTranCode = proTranCode;
	}

	@Override
	public String toString() {
		return "Product [prodNo=" + prodNo + ", prodName=" + prodName + ", prodDetail=" + prodDetail + ", manuDate="
				+ manuDate + ", price=" + price + ", fileName=" + fileName + ", regDate=" + regDate + ", amount="
				+ amount + ", proTranCode=" + proTranCode + "]";
	}
	
}