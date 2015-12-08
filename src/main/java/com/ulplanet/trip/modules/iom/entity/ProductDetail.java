package com.ulplanet.trip.modules.iom.entity;

import com.ulplanet.trip.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 产品Entity
 * Created by zhangxd on 15/12/01.
 */
public class ProductDetail extends DataEntity<ProductDetail> {

	private static final long serialVersionUID = 1L;

    private Product product;
    private String code;
    private String device;
    private String status;
    private String comment;

    public ProductDetail() {
        super();
    }

    @NotNull
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min=0, max=64, message="设备号长度必须介于 1 和 64 之间")
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @NotNull
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Length(min=0, max=2000, message="备注长度必须介于 1 和 2000 之间")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}