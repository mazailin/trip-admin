package com.ulplanet.trip.modules.ims.bo;

import com.ulplanet.trip.modules.ims.entity.PhoneInfo;

/**
 * Created by Administrator on 2015/10/23.
 */
public class PhoneInfoBo extends PhoneInfo implements java.io.Serializable{
    private static final long serialVersionUID = 141499175831668570L;
    private String statusValue;

    public PhoneInfoBo(){}
    public PhoneInfoBo(PhoneInfo phoneInfo){
        this.setCode(phoneInfo.getCode());
        this.setId(phoneInfo.getId());
        this.setStatus(phoneInfo.getStatus());
        this.setStockOrderId(phoneInfo.getStockOrderId());
        this.setComment(phoneInfo.getComment());
        this.setRefundFlag(phoneInfo.getRefundFlag());
        this.setPage(phoneInfo.getPage());
        this.setStatusValue(phoneInfo.getStatus());
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(int status) {
        switch (status){
            case 1 : this.statusValue = "待测试";break;
            case 2 : this.statusValue = "已测试";break;
            case 3 : this.statusValue = "待租";break;
            case 4 : this.statusValue = "已租";break;
            case 5 : this.statusValue = "维修";break;
            case 9000 : this.statusValue = "报废";break;
            case 9999 : this.statusValue = "退货";break;
            default:break;
        }
    }
}
