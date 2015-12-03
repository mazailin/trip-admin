package com.ulplanet.trip.modules.tim.entity;


import com.ulplanet.trip.common.persistence.DataEntity;

public class ShopFile extends DataEntity<ShopFile> {


	/**
	 *
	 */
	private static final long serialVersionUID = 9027459916420141974L;

	private String shop;
	private String name;
	private String type;
	private String description;
	private String path;

    public ShopFile() {
        super();
    }

    public ShopFile(String id) {
        super(id);
    }

    public ShopFile(String id, String shop) {
        super(id);
        this.shop = shop;
    }

    public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
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
