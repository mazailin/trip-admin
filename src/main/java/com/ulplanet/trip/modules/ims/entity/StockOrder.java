package com.ulplanet.trip.modules.ims.entity;


import com.ulplanet.trip.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

public class StockOrder  extends DataEntity<StockOrder> implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String orderId;
	private Double unitPrice;
	private Integer quantity;
	private Double totalPrice;
	private String provider;
	private String model;
	private String buyer;
	private String buyingTime;
	private String comment;
	private Integer status;
	private String email;

	public StockOrder(){
	}

	public StockOrder(
		String id
	){
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public void setOrderId(String value) {
		this.orderId = value;
	}

	@NotNull
	public String getOrderId() {
		return this.orderId;
	}


	public void setQuantity(Integer value) {
		this.quantity = value;
	}

	@NotNull
	public Integer getQuantity() {
		return this.quantity;
	}

	@NotNull
	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@NotNull
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setProvider(String value) {
		this.provider = value;
	}
	
	public String getProvider() {
		return this.provider;
	}

	public void setModel(String value) {
		this.model = value;
	}

	@NotNull
	public String getModel() {
		return this.model;
	}

	public void setBuyer(String value) {
		this.buyer = value;
	}
	
	public String getBuyer() {
		return this.buyer;
	}

	public String getBuyingTime() {
		return buyingTime;
	}

	public void setBuyingTime(String buyingTime) {
		this.buyingTime = buyingTime;
	}

	public void setComment(String value) {
		this.comment = value;
	}
	
	public String getComment() {
		return this.comment;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

