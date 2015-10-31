package com.ulplanet.trip.modules.ims.entity;


import com.ulplanet.trip.common.persistence.DataEntity;

public class PhoneInfo extends DataEntity<PhoneInfo> implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	private String code;
	private Integer status;
	private String leaseholder;//租用人
	private String stockOrderId;
	private String comment;
	private String refundFlag;

	public PhoneInfo(){
	}



	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return this.code;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	public void setLeaseholder(String value) {
		this.leaseholder = value;
	}
	
	public String getLeaseholder() {
		return this.leaseholder;
	}

	public void setStockOrderId(String value) {
		this.stockOrderId = value;
	}
	
	public String getStockOrderId() {
		return this.stockOrderId;
	}

	public void setComment(String value) {
		this.comment = value;
	}
	
	public String getComment() {
		return this.comment;
	}

	public String getRefundFlag() {
		return refundFlag;
	}

	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}
}

