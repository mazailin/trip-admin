package com.ulplanet.trip.modules.tim.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tim.entity.Scenic;
import com.ulplanet.trip.modules.tim.entity.ScenicFile;

import java.util.List;

/**
 * 景点管理Dao
 * Created by zhangxd on 15/11/08.
 */
@MyBatisDao
public interface ScenicDao extends CrudDao<Scenic> {

    int insertFile(ScenicFile scenicFile);

    List<ScenicFile> findScenicFiles(ScenicFile scenicFile);

    int deleteScenicFileById(String id);

    int deleteScenicFileByScenic(String scenic);

    ScenicFile getFileById(String fileId);

    int updateScenicFile(ScenicFile scenicFile);
}
