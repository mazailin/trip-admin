package com.ulplanet.trip.modules.tms.bo;


import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.tms.entity.Flight;
import com.ulplanet.trip.modules.tms.entity.JourneyPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/22.
 */
public class JourneyPlanBo {
    private String id;
    private String dayId;
    private String infoId;
    private String cityIds;
    private Integer type;
    private String typeValue;
    private String name;
    private String time;
    private Integer timeFlag;
    private String description;
    private String message;
    private Integer messageFlag;
    private Integer sort;
    private String hour;
    private String minute;
    private String longitude;
    private String latitude;
    private List<InfoBo> infos = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();

    public JourneyPlanBo(){}

    public JourneyPlanBo(JourneyPlan journeyPlan){
        this.id = journeyPlan.getId();
        this.dayId = journeyPlan.getDayId();
        this.type = journeyPlan.getType();
        this.cityIds = journeyPlan.getCityIds();
        this.infoId = journeyPlan.getInfoId();
        this.typeValue = journeyPlan.getTypeValue();
        this.name = journeyPlan.getName();
        setTime(journeyPlan.getTime());
        this.timeFlag = journeyPlan.getTimeFlag();
        this.description = journeyPlan.getDescription();
        this.message = journeyPlan.getMessage();
        this.messageFlag = journeyPlan.getMessageFlag();
        this.sort = journeyPlan.getSort();
        this.longitude = journeyPlan.getLongitude();
        this.latitude = journeyPlan.getLatitude();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public List<InfoBo> getInfos() {
        return infos;
    }

    public void setInfos(List<InfoBo> infos) {
        this.infos = infos;
    }

    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        if(timeFlag != null && timeFlag == 1 && StringUtils.isNotBlank(this.hour) && StringUtils.isNotBlank(this.minute)){
            this.time = this.hour + ":" + this.minute;
        }
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        if(StringUtils.isNotBlank(time) && time.indexOf(":") > 0){
            String [] strs = time.split(":");
            this.hour = strs[0];
            this.minute = strs[1];
        }
    }

    public Integer getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(Integer timeFlag) {
        this.timeFlag = timeFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMessageFlag() {
        return messageFlag;
    }

    public void setMessageFlag(Integer messageFlag) {
        this.messageFlag = messageFlag;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }
}
