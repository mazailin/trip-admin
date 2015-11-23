package com.ulplanet.trip.modules.tms.entity;


import com.alibaba.fastjson.JSONObject;
import com.ulplanet.trip.common.persistence.DataEntity;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.crm.entity.AppUser;

import java.util.List;

/**
 * Created by zhangxd on 15/8/11.
 */
public class GroupUser extends DataEntity<GroupUser> {

    private String group;
    private String user;
    private String type;
    private String code;
    private String name;
    private String weChat;
    private String qq;
    private String birth;
    private String birthPlace;
    private String issuePlace;
    private String issueDate;
    private String expiryDate;
    private String gender;
    private String photo;
    private String identityCard;
    private String passport;
    private String phone;
    private String email;
    private String toDate;
    private String fromDate;
    private String imToken;
    private List<AppUser> list;


    public List<AppUser> getList() {
        return list;
    }

    public void setList(List<AppUser> list) {
        this.list = list;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getBirth() {
        return StringUtils.isBlank(birth)?null:birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public String getIssueDate() {
        return StringUtils.isBlank(issueDate)?null:issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpiryDate() {
        return StringUtils.isBlank(expiryDate)?null:expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void preInsert() {
        super.preInsert();
    }
}
