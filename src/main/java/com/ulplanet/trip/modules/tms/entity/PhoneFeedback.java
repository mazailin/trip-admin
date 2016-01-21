package com.ulplanet.trip.modules.tms.entity;

import com.ulplanet.trip.common.persistence.DataEntity;
import com.ulplanet.trip.common.utils.DateUtils;

import java.util.Date;

/**
 * Created by makun on 2015/12/15.
 */
public class PhoneFeedback extends DataEntity<PhoneFeedback> implements java.io.Serializable {


    private static final long serialVersionUID = 1823335923543908063L;
    private String id;
    private String name;
    private String functionId;
    private String userCode;
    private Integer score;
    private String feedback;
    private Integer type;
    private String date;

    public String getDate() {
        this.date = DateUtils.formatDate(this.createDate,"yyyy-MM-dd");
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}
