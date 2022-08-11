package com.model2.mvc.service.domain;

import java.sql.Date;

public class Purchase {

	private int tranNo;
	private String tranId;
	private Product purchaseProd;
	private User buyer;
	private String paymentOption;
	private String receiverName;
	private String receiverPhone;
	private String divyAddr;
	private String divyRequest;
	private String tranCode;
	private Date orderDate;
	private String divyDate;
	private int amount;
	private int totalPrice;

	public Purchase() {
	}

	public Purchase(int tranNo, Product purchaseProd, User buyer, String paymentOption, String receiverName,
			String receiverPhone, String divyAddr, String divyRequest, String tranCode, Date orderDate, String divyDate,
			int amount) {
		super();
		this.tranNo = tranNo;
		this.purchaseProd = purchaseProd;
		this.buyer = buyer;
		this.paymentOption = paymentOption;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.divyAddr = divyAddr;
		this.divyRequest = divyRequest;
		this.tranCode = tranCode;
		this.orderDate = orderDate;
		this.divyDate = divyDate;
		this.amount = amount;
	}

	public Purchase(int tranNo, String tranId, Product purchaseProd, User buyer, String paymentOption,
			String receiverName, String receiverPhone, String divyAddr, String divyRequest, String tranCode,
			Date orderDate, String divyDate, int amount, int totalPrice) {
		super();
		this.tranNo = tranNo;
		this.tranId = tranId;
		this.purchaseProd = purchaseProd;
		this.buyer = buyer;
		this.paymentOption = paymentOption;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.divyAddr = divyAddr;
		this.divyRequest = divyRequest;
		this.tranCode = tranCode;
		this.orderDate = orderDate;
		this.divyDate = divyDate;
		this.amount = amount;
		this.totalPrice = totalPrice;
	}

	public int getTranNo() {
		return tranNo;
	}

	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}

	public String getTranId() {
		return tranId;
	}

	public void setTranId(String tranId) {
		this.tranId = tranId;
	}

	public Product getPurchaseProd() {
		return purchaseProd;
	}

	public void setPurchaseProd(Product purchaseProd) {
		this.purchaseProd = purchaseProd;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public String getPaymentOption() {
		return paymentOption;
	}

	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getDivyAddr() {
		return divyAddr;
	}

	public void setDivyAddr(String divyAddr) {
		this.divyAddr = divyAddr;
	}

	public String getDivyRequest() {
		return divyRequest;
	}

	public void setDivyRequest(String divyRequest) {
		this.divyRequest = divyRequest;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDivyDate() {
		return divyDate;
	}

	public void setDivyDate(String divyDate) {
		this.divyDate = divyDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Purchase [tranNo=");
		builder.append(tranNo);
		builder.append(", tranId=");
		builder.append(tranId);
		builder.append(", purchaseProd=");
		builder.append(purchaseProd);
		builder.append(", buyer=");
		builder.append(buyer);
		builder.append(", paymentOption=");
		builder.append(paymentOption);
		builder.append(", receiverName=");
		builder.append(receiverName);
		builder.append(", receiverPhone=");
		builder.append(receiverPhone);
		builder.append(", divyAddr=");
		builder.append(divyAddr);
		builder.append(", divyRequest=");
		builder.append(divyRequest);
		builder.append(", tranCode=");
		builder.append(tranCode);
		builder.append(", orderDate=");
		builder.append(orderDate);
		builder.append(", divyDate=");
		builder.append(divyDate);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", totalPrice=");
		builder.append(totalPrice);
		builder.append("]");
		return builder.toString();
	}

}