package com.ulplanet.trip.modules.tms.dao;


import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tms.entity.JourneyDay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface  JourneyDayDao extends CrudDao<JourneyDay> {
	
    List<JourneyDay> findPage(JourneyDay journeyDay);
    int updates(@Param("list") List<JourneyDay> list);
    int inserts(@Param("list") List<JourneyDay> list);
    int deleteByGroupId(@Param("groupId")String groupId);

}
