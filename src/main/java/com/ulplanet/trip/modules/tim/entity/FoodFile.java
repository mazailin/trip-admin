package com.ulplanet.trip.modules.tim.entity;


import com.ulplanet.trip.common.persistence.DataEntity;

public class FoodFile extends DataEntity<FoodFile> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 9027459916420141974L;
	
	private String food;
	private String name;
	private String type;
	private String description;
	private String path;

    public FoodFile() {
        super();
    }

    public FoodFile(String id) {
        super(id);
    }

    public FoodFile(String id, String food) {
        super(id);
        this.food = food;
    }

    public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
