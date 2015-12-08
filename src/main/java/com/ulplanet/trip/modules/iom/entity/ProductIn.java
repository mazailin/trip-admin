package com.ulplanet.trip.modules.iom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ulplanet.trip.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 产品入库Entity
 * Created by zhangxd on 15/12/01.
 */
public class ProductIn extends DataEntity<ProductIn> {

	private static final long serialVersionUID = 1L;

    private String code;
    private Date inDate;
    private Integer inType;
    private String operator;
    private String bills;
    private String insure;
    private String comment;

    public ProductIn() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Integer getInType() {
        return inType;
    }

    public void setInType(Integer inType) {
        this.inType = inType;
    }

    @Length(min=0, max=64, message="经办人长度必须介于 1 和 64 之间")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Length(min=0, max=255, message="采购单据长度必须介于 1 和 255 之间")
    public String getBills() {
        return bills;
    }

    public void setBills(String bills) {
        this.bills = bills;
    }

    public String getInsure() {
        return insure;
    }

    public void setInsure(String insure) {
        this.insure = insure;
    }

    @Length(min=0, max=2000, message="备注长度必须介于 1 和 2000 之间")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}