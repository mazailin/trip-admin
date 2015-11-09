package com.ulplanet.trip.modules.tms.bo;

/**
 * Created by makun on 2015/9/21.
 */
public class JourneyPlans{

    private String userId;//用户ID
    private String dayNumber;//第几天
    private String dayTitle;//每天的标题
    private Integer type;//类型 1 行程  2 集合  3 航班 4 交通 5 住宿  6 餐饮 7 景点 8 购物
    private String name;//每个行程名
    private Integer hasTime;//是否有时间
    private String time;//时间
    private String description;//描述
    private String hasMessage;//是否有备注
    private String message;//备注
    private Integer sort;//排序
    private String longitude;
    private String latitude;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getDayTitle() {
        return dayTitle;
    }

    public void setDayTitle(String dayTitle) {
        this.dayTitle = dayTitle;
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

    public Integer getHasTime() {
        return hasTime;
    }

    public void setHasTime(Integer hasTime) {
        this.hasTime = hasTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHasMessage() {
        return hasMessage;
    }

    public void setHasMessage(String hasMessage) {
        this.hasMessage = hasMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
