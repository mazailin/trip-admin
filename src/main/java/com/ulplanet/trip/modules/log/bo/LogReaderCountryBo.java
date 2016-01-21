package com.ulplanet.trip.modules.log.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makun on 2015/12/7.
 */
public class LogReaderCountryBo {
    private String id;
    private String name;
    private List<LogReaderBo> list = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LogReaderBo> getList() {
        return list;
    }

    public void setList(List<LogReaderBo> list) {
        this.list = list;
    }
}
