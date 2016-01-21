package com.ulplanet.trip.modules.tms.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tms.entity.Position;

/**
 * 位置DAO接口
 * Created by zhangxd on 15/12/20.
 */
@MyBatisDao
public interface PositionDao extends CrudDao<Position> {

}
