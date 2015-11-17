package com.ulplanet.trip.modules.sys.entity;

import com.ulplanet.trip.common.utils.IdGen;

/**
 * Created by makun on 2015/11/17.
 */
public class VersionTag {

    private String id;
    private int type;
    private String tag;


    public VersionTag(){}

    public VersionTag(String id,int type){
        this(id,type,IdGen.uuid());
    }
    public VersionTag(String id,int type,String tag){
        this.id = id;
        this.type = type;
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
