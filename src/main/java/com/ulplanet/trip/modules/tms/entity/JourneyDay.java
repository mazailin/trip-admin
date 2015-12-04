package com.ulplanet.trip.modules.tms.entity;


import com.ulplanet.trip.common.persistence.DataEntity;

public class JourneyDay extends DataEntity<JourneyDay> implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	

	
	private String cityIds;
	private String groupId;
	private Integer dayNumber;
	private String title;
	private String cityNames;

	public JourneyDay(){
	}

	public JourneyDay(
		String id
	){
		this.id = id;
	}

	public String getCityNames() {
		return cityNames;
	}

	public void setCityNames(String cityNames) {
		this.cityNames = cityNames;
	}

	public String getCityIds() {
		return cityIds;
	}

	public void setCityIds(String cityIds) {
		this.cityIds = cityIds;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setDayNumber(Integer value) {
		this.dayNumber = value;
	}
	
	public Integer getDayNumber() {
		return this.dayNumber;
	}

	public void setTitle(String value) {
		this.title = value;
	}
	
	public String getTitle() {
		return this.title;
	}






	

	

}

