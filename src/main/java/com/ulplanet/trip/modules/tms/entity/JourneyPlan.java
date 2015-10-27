package com.ulplanet.trip.modules.tms.entity;


import com.ulplanet.trip.common.persistence.DataEntity;

public class JourneyPlan  extends DataEntity<JourneyPlan> implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	

	

	private String dayId;
	private Integer type;
	private String typeValue;
	private String name;
	private String time;
	private Integer timeFlag;
	private String description;
	private String message;
	private Integer messageFlag;
	private Integer sort;
	private String longitude;
	private String latitude;

	public JourneyPlan(){
	}

	public JourneyPlan(
		String id
	){
		this.id = id;
	}

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

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}


	public void setDayId(String value) {
		this.dayId = value;
	}
	
	public String getDayId() {
		return this.dayId;
	}

	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}

	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setTimeFlag(Integer value) {
		this.timeFlag = value;
	}
	
	public Integer getTimeFlag() {
		return this.timeFlag;
	}

	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setMessage(String value) {
		this.message = value;
	}
	
	public String getMessage() {
		return this.message;
	}

	public void setMessageFlag(Integer value) {
		this.messageFlag = value;
	}
	
	public Integer getMessageFlag() {
		return this.messageFlag;
	}

	public void setSort(Integer value) {
		this.sort = value;
	}
	
	public Integer getSort() {
		return this.sort;
	}




	

	

}

