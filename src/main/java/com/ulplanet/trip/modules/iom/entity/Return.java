package com.ulplanet.trip.modules.iom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ulplanet.trip.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 出租Entity
 * Created by zhangxd on 15/12/17.
 */
public class Return extends DataEntity<Return> {

	private static final long serialVersionUID = 1L;

    private String code;
    private Rent rent;
    private String operator;
    private Date returnDate;
    private String comment;

    public Return() {
        super();
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min=0, max=255, message="租赁人长度必须介于 1 和 255 之间")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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