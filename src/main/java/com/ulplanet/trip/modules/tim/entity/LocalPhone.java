package com.ulplanet.trip.modules.tim.entity;

import com.ulplanet.trip.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 当地电话Entity
 * Created by zhangxd on 16/01/11.
 */
public class LocalPhone extends DataEntity<LocalPhone> {

	private static final long serialVersionUID = 1L;

    private Country country;
    private String name;
    private String phone;

    public LocalPhone() {
        super();
    }

    @NotNull(message="国家不能为空")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Length(min=1, max=255, message="名称长度必须介于 1 和 255 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min=1, max=255, message="电话长度必须介于 1 和 255 之间")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}