package com.ulplanet.trip.modules.tim.entity;


import com.ulplanet.trip.common.persistence.DataEntity;

public class ScenicFile extends DataEntity<ScenicFile> {


	/**
	 *
	 */
	private static final long serialVersionUID = 9027459916420141974L;

	private String scenic;
	private String name;
	private String type;
	private String description;
	private String path;

    public ScenicFile() {
        super();
    }

    public ScenicFile(String id) {
        super(id);
    }

    public ScenicFile(String id, String scenic) {
        super(id);
        this.scenic = scenic;
    }

    public String getScenic() {
		return scenic;
	}

	public void setScenic(String scenic) {
		this.scenic = scenic;
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
