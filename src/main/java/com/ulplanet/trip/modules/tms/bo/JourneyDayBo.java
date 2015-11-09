package com.ulplanet.trip.modules.tms.bo;


import com.ulplanet.trip.modules.tms.entity.JourneyDay;
import com.ulplanet.trip.modules.tms.entity.JourneyPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/22.
 */
public class JourneyDayBo {
    private String id;
    private String groupId;
    private Integer dayNumber;
    private String title;
    private String cityIds;
    private List<JourneyPlan> list = new ArrayList<>();

    public JourneyDayBo(){}

    public JourneyDayBo(JourneyDay journeyDay){
        this.id = journeyDay.getId();
        this.groupId = journeyDay.getGroupId();
        this.dayNumber = journeyDay.getDayNumber();
        this.title = journeyDay.getTitle();
        this.cityIds = journeyDay.getCityIds();
    }

    public String getCityIds() {
        return cityIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<JourneyPlan> getList() {
        return list;
    }

    public void setList(List<JourneyPlan> list) {
        this.list = list;
    }
}
