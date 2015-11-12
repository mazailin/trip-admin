package com.ulplanet.trip.modules.sys.entity;


import com.ulplanet.trip.common.persistence.DataEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by makun on 2015/9/16.
 */
public class Apk extends DataEntity<Apk> {

    private static final long serialVersionUID = -3073516058680130229L;
    private String name;
    private String description;
    private String version;
    private String packageName;
    private String url;
    private String soUrls;
    private String key;
    private String md5;
    private String size;

    public String getSoUrls() {
        return soUrls;
    }

    public void setSoUrls(String soUrls) {
        this.soUrls = soUrls;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
