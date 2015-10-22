package com.ulplanet.trip.modules.crm.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.crm.entity.AppUser;

/**
 * 移动用户Dao
 * Created by zhangxd on 15/10/22.
 */
@MyBatisDao
public interface AppUserDao extends CrudDao<AppUser> {
}
