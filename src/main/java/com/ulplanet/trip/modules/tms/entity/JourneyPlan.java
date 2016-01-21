package com.ulplanet.trip.modules.tms.entity;


import com.ulplanet.trip.common.persistence.DataEntity;
import com.ulplanet.trip.common.utils.StringUtils;

public class JourneyPlan  extends DataEntity<JourneyPlan> implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	

	

	private String dayId;
	private Integer type;
	private String typeValue;
	private String name;
	private String time;
	private String infoId;
	private String cityIds;
	private Integer timeFlag = 0;
	private String description;
	private String message;
	private Integer messageFlag = 0;
	private Integer sort;
	private String longitude;
	private String latitude;
	private Integer feedbackFlag = 0;


	public Integer getFeedbackFlag() {
		return feedbackFlag;
	}

	public void setFeedbackFlag(Integer feedbackFlag) {
		this.feedbackFlag = feedbackFlag;
	}

	public JourneyPlan(){
	}

	public JourneyPlan(
		String id
	){
		this.id = id;
	}

	public String getCityIds() {
		return cityIds;
	}

	public void setCityIds(String cityIds) {
		this.cityIds = cityIds;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getLongitude() {
		if(StringUtils.isBlank(longitude)){
			longitude = null;
		}
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		if(StringUtils.isBlank(latitude)){
			latitude = null;
		}
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
		if(StringUtils.isBlank(time)){
			time = null;
		}
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

