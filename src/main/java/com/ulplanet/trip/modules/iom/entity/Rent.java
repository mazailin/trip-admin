package com.ulplanet.trip.modules.iom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ulplanet.trip.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 出租Entity
 * Created by zhangxd on 15/12/17.
 */
public class Rent extends DataEntity<Rent> {

	private static final long serialVersionUID = 1L;

    private String code;
    private String renter;
    private String operator;
    private double deposit;
    private int status;
    private Date rentDate;
    private Date returnDate;
    private String comment;

    public Rent() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min=0, max=255, message="租赁人长度必须介于 1 和 255 之间")
    public String getRenter() {
        return renter;
    }

    public void setRenter(String renter) {
        this.renter = renter;
    }

    @Length(min=0, max=255, message="租赁人长度必须介于 1 和 255 之间")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Length(min=0, max=2000, message="备注长度必须介于 1 和 2000 之间")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}