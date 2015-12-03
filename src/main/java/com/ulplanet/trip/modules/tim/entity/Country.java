package com.ulplanet.trip.modules.tim.entity;

import com.ulplanet.trip.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 国家Entity
 * Created by zhangxd on 15/10/22.
 */
public class Country extends DataEntity<Country> {

	private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private String zcode;
    private String cnzcode;
    private String ambulance;
    private String police;
    private String fire;
    private String seaEmerg;
    private String roadEmerg;
    private String unionpayCall;
    private String embassyCall;
    private String embassyTime;
    private String embassyAddr;
    private String embassyCity;

    public Country() {
        super();
    }

    public Country(String id, String name) {
        super(id);
        this.name = name;
    }

    @Length(min=1, max=64, message="名称长度必须介于 1 和 64 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min=0, max=255, message="描述长度必须介于 1 和 255 之间")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Length(min=0, max=16, message="国家区号长度必须介于 1 和 16 之间")
    public String getZcode() {
        return zcode;
    }

    public void setZcode(String zcode) {
        this.zcode = zcode;
    }

    @Length(min=0, max=16, message="国家区号长度必须介于 1 和 16 之间")
    public String getCnzcode() {
        return cnzcode;
    }

    public void setCnzcode(String cnzcode) {
        this.cnzcode = cnzcode;
    }

    @Length(min=0, max=64, message="急救电话长度必须介于 1 和 64 之间")
    public String getAmbulance() {
        return ambulance;
    }

    public void setAmbulance(String ambulance) {
        this.ambulance = ambulance;
    }

    @Length(min=0, max=64, message="匪警长度必须介于 1 和 64 之间")
    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    @Length(min=0, max=64, message="火警长度必须介于 1 和 64 之间")
    public String getFire() {
        return fire;
    }

    public void setFire(String fire) {
        this.fire = fire;
    }

    @Length(min=0, max=64, message="海上急救长度必须介于 1 和 64 之间")
    public String getSeaEmerg() {
        return seaEmerg;
    }

    public void setSeaEmerg(String seaEmerg) {
        this.seaEmerg = seaEmerg;
    }

    @Length(min=0, max=64, message="公路抢险长度必须介于 1 和 64 之间")
    public String getRoadEmerg() {
        return roadEmerg;
    }

    public void setRoadEmerg(String roadEmerg) {
        this.roadEmerg = roadEmerg;
    }

    @Length(min=0, max=64, message="银联境外客服电话长度必须介于 1 和 64 之间")
    public String getUnionpayCall() {
        return unionpayCall;
    }

    public void setUnionpayCall(String unionpayCall) {
        this.unionpayCall = unionpayCall;
    }

    @Length(min=0, max=64, message="大使馆电话长度必须介于 1 和 64 之间")
    public String getEmbassyCall() {
        return embassyCall;
    }

    public void setEmbassyCall(String embassyCall) {
        this.embassyCall = embassyCall;
    }

    @Length(min=0, max=64, message="大使馆工作时间长度必须介于 1 和 64 之间")
    public String getEmbassyTime() {
        return embassyTime;
    }

    public void setEmbassyTime(String embassyTime) {
        this.embassyTime = embassyTime;
    }

    @Length(min=0, max=64, message="大使馆地址长度必须介于 1 和 64 之间")
    public String getEmbassyAddr() {
        return embassyAddr;
    }

    public void setEmbassyAddr(String embassyAddr) {
        this.embassyAddr = embassyAddr;
    }

    @Length(min=0, max=64, message="大使馆城市长度必须介于 1 和 64 之间")
    public String getEmbassyCity() {
        return embassyCity;
    }

    public void setEmbassyCity(String embassyCity) {
        this.embassyCity = embassyCity;
    }
}