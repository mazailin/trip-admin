package com.ulplanet.trip.modules.tms.entity;


import com.ulplanet.trip.common.persistence.DataEntity;

import java.util.Date;

/**
 * Created by zhangxd on 15/8/11.
 */
public class GroupFlight extends DataEntity<GroupFlight> {

    private String flightId;
    private String groupId;
    private Date flightDate;
    private String common;

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }
}
