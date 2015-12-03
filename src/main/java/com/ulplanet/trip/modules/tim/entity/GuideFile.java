package com.ulplanet.trip.modules.tim.entity;


import com.ulplanet.trip.common.persistence.DataEntity;

public class GuideFile extends DataEntity<GuideFile> {


	/**
	 *
	 */
	private static final long serialVersionUID = 9027459916420141974L;

	private String guide;
	private String name;
	private String type;
	private String description;
	private String path;

    public GuideFile() {
        super();
    }

    public GuideFile(String id) {
        super(id);
    }

    public GuideFile(String id, String guide) {
        super(id);
        this.guide = guide;
    }

    public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
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
