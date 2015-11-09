package com.ulplanet.trip.modules.tms.bo;

import java.util.List;

/**
 * Created by makun on 2015/11/2.
 */
public class JourneyBo {
    private String groupId;
    private SortBo[] list;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public SortBo[] getList() {
        return list;
    }

    public void setList(SortBo[] list) {
        this.list = list;
    }
}
