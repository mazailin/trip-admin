package com.ulplanet.trip.modules.tms.entity;


import com.ulplanet.trip.common.persistence.DataEntity;

import java.util.Date;

/**
 * Created by zhangxd on 15/8/11.
 */
public class GroupHotel extends DataEntity<GroupHotel> {

    private String flightId;
    private String hotelId;
    private Date checkDate;
    private String common;

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }
}
