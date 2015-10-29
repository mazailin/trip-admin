package com.ulplanet.trip.modules.tms.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tms.entity.Hotel;

/**
 * Created by makun on 2015/10/28.
 */
@MyBatisDao
public interface HotelDao  extends CrudDao<Hotel> {
}
