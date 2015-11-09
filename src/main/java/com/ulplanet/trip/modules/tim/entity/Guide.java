package com.ulplanet.trip.modules.tim.entity;

import com.ulplanet.trip.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 导购Entity
 * Created by zhangxd on 15/11/08.
 */
public class Guide extends DataEntity<Guide> {

	private static final long serialVersionUID = 1L;

    private String name;
    private City city;
    private String description;
    private String published;

    public Guide() {
        super();
    }

    public Guide(String id, String name) {
        super(id);
        this.name = name;
    }

    @Length(min=1, max=255, message="名称长度必须介于 1 和 255 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min=0, max=2000, message="描述长度必须介于 1 和 2000 之间")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message="城市不能为空")
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }
}