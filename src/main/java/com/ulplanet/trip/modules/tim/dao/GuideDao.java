package com.ulplanet.trip.modules.tim.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tim.entity.Guide;
import com.ulplanet.trip.modules.tim.entity.GuideFile;

import java.util.List;

/**
 * 导购管理Dao
 * Created by zhangxd on 15/11/08.
 */
@MyBatisDao
public interface GuideDao extends CrudDao<Guide> {

    int insertFile(GuideFile guideFile);

    List<GuideFile> findGuideFiles(GuideFile guideFile);

    int deleteGuideFileById(String id);

    int deleteGuideFileByGuide(String guide);

    GuideFile getFileById(String fileId);

    int updateGuideFile(GuideFile guideFile);
}
