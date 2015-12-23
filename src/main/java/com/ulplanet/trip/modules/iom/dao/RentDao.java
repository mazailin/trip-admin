package com.ulplanet.trip.modules.iom.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.iom.entity.Rent;

/**
 * 产品租赁Dao
 * Created by zhangxd on 15/12/01.
 */
@MyBatisDao
public interface RentDao extends CrudDao<Rent> {

}
