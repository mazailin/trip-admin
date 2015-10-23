package com.ulplanet.trip.modules.crm.entity;

import com.ulplanet.trip.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 客户Entity
 * Created by zhangxd on 15/10/22.
 */
public class Customer extends DataEntity<Customer> {

	private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private String active;

    public Customer() {
        super();
    }

    public Customer(String id, String name) {
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

    @NotNull
    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}