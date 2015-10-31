package com.ulplanet.trip.modules.tim.entity;

import com.ulplanet.trip.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 租车Entity
 * Created by zhangxd on 15/10/22.
 */
public class CarRental extends DataEntity<CarRental> {

	private static final long serialVersionUID = 1L;

    private Country country;
    private String type;
    private String phone;

    public CarRental() {
        super();
    }

    public CarRental(String id, String type) {
        super(id);
        this.type = type;
    }

    @NotNull(message="国家不能为空")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @NotNull(message="类型不能为空")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(min=0, max=64, message="描述长度必须介于 1 和 64 之间")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}