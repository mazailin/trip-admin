package com.ulplanet.trip.modules.tim.entity;

import com.ulplanet.trip.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 城市Entity
 * Created by zhangxd on 15/10/22.
 */
public class City extends DataEntity<City> {

	private static final long serialVersionUID = 1L;

    private String name;
    private Country country;
    private String description;

    public City() {
        super();
    }

    public City(String id, String name) {
        super(id);
        this.name = name;
    }

    @Length(min=1, max=64, message="名称长度必须介于 1 和 64 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min=0, max=255, message="描述长度必须介于 1 和 255 之间")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message="国家不能为空")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}