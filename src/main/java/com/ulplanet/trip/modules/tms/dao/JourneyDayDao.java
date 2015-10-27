package com.ulplanet.trip.modules.tms.dao;


import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tms.entity.JourneyDay;

import java.util.List;

@MyBatisDao
public interface  JourneyDayDao extends CrudDao<JourneyDay> {
	
    List<JourneyDay> findPage(JourneyDay journeyDay);

}
