package com.ulplanet.trip.modules.tms.bo;

/**
 * Created by makun on 2015/11/2.
 */
public class SortBo {
    private String id;
    private String groupId;
    private SortBo[] children;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SortBo[] getChildren() {
        return children;
    }

    public void setChildren(SortBo[] children) {
        this.children = children;
    }
}
