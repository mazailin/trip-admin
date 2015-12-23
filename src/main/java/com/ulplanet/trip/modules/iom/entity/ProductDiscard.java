package com.ulplanet.trip.modules.iom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ulplanet.trip.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 产品Entity
 * Created by zhangxd on 15/12/01.
 */
public class ProductDiscard extends DataEntity<ProductDiscard> {

	private static final long serialVersionUID = 1L;

    private String code;
    private Product product;
    private ProductDetail productDetail;
    private double amount;
    private String reasons;
    private Date disDate;

    public ProductDiscard() {
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

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Length(min=0, max=2000, message="报废原因长度必须介于 1 和 2000 之间")
    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getDisDate() {
        return disDate;
    }

    public void setDisDate(Date disDate) {
        this.disDate = disDate;
    }
}