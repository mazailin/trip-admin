package com.ulplanet.trip.modules.tms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ulplanet.trip.common.persistence.DataEntity;

import java.util.Date;

/**
 * 位置Entity
 * Created by zhangxd on 15/12/20.
 */
public class Position extends DataEntity<Position> {

	private static final long serialVersionUID = 1L;
	private String userId;
	private double lng;
	private double lat;
	private Date timing;

    private String name;

	public Position() {
		super();
	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getTiming() {
        return timing;
    }

    public void setTiming(Date timing) {
        this.timing = timing;
    }
}