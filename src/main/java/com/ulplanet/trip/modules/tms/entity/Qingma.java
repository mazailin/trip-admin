package com.ulplanet.trip.modules.tms.entity;

import com.ulplanet.trip.common.persistence.DataEntity;

/**
 * Created by makun on 2016/1/15.
 */
public class Qingma {
    private String id;
    private String code;
    private String clientNumber;
    private String clientPwd;
    private String createTime;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getClientPwd() {
        return clientPwd;
    }

    public void setClientPwd(String clientPwd) {
        this.clientPwd = clientPwd;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
