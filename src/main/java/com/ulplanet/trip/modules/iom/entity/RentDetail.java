package com.ulplanet.trip.modules.iom.entity;

import com.ulplanet.trip.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 出租明细Entity
 * Created by zhangxd on 15/12/17.
 */
public class RentDetail extends DataEntity<RentDetail> {

	private static final long serialVersionUID = 1L;

    private Rent rent;
    private Product product;
    private ProductDetail productDetail;
    private double amount;

    public RentDetail() {
        super();
    }

    public RentDetail(String id, Rent rent) {
        super(id);
        this.rent = rent;
    }

    @NotNull
    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    @NotNull
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

}
