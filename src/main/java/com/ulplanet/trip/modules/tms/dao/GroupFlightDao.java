package com.ulplanet.trip.modules.tms.dao;


import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tms.entity.GroupFlight;

/**
 * Created by zhangxd on 15/8/11.
 */
@MyBatisDao
public interface GroupFlightDao extends CrudDao<GroupFlight> {
}
