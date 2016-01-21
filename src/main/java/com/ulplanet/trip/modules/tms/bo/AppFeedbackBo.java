package com.ulplanet.trip.modules.tms.bo;

import java.util.List;

/**
 * Created by makun on 2016/1/5.
 */
public class AppFeedbackBo {
    private String id;
    private String name;
    private List<AppFeedbackBo> list;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AppFeedbackBo> getList() {
        return list;
    }

    public void setList(List<AppFeedbackBo> list) {
        this.list = list;
    }
}
