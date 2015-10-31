package com.ulplanet.trip.modules.tim.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tim.entity.CarRental;

/**
 * 租车管理Dao
 * Created by zhangxd on 15/10/22.
 */
@MyBatisDao
public interface CarRentalDao extends CrudDao<CarRental> {

}
