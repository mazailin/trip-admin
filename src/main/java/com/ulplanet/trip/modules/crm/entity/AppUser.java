package com.ulplanet.trip.modules.crm.entity;

import com.ulplanet.trip.common.persistence.DataEntity;

/**
 * 移动用户Entity
 * Created by zhangxd on 15/10/20.
 */
public class AppUser extends DataEntity<AppUser> {

	private static final long serialVersionUID = 1L;
    private String name;
    private String gender;
    private String identityCard;
    private String passport;
    private String phone;
    private String email;
	
	public AppUser(){
		super();
	}
	
	public AppUser(String id){
		super(id);
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

}