package com.ulplanet.trip.modules.sys.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.sys.entity.VersionTag;

/**
 * Created by makun on 2015/11/17.
 */
@MyBatisDao
public interface VersionTagDao{
    VersionTag get(VersionTag versionTag);
    int insert(VersionTag versionTag);
}
