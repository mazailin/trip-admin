package com.ulplanet.trip.modules.tms.entity;

import com.ulplanet.trip.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 旅行团小组
 * Created by zhangxd on 15/8/11.
 */
public class Team extends DataEntity<Team> {

    private Group group;
    private String name;
    private String comment;

    private List<GroupUser> userList;
    private String selectIds;

    private String addUserIds;
    private String delUserIds;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Length(min=1, max=255, message="名称长度必须介于 1 和 255 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min=0, max=2000, message="描述长度必须介于 1 和 2000 之间")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<GroupUser> getUserList() {
        return userList;
    }

    public void setUserList(List<GroupUser> userList) {
        this.userList = userList;
    }

    public String getSelectIds() {
        return selectIds;
    }

    public void setSelectIds(String selectIds) {
        this.selectIds = selectIds;
    }

    public String getAddUserIds() {
        return addUserIds;
    }

    public void setAddUserIds(String addUserIds) {
        this.addUserIds = addUserIds;
    }

    public String getDelUserIds() {
        return delUserIds;
    }

    public void setDelUserIds(String delUserIds) {
        this.delUserIds = delUserIds;
    }
}
