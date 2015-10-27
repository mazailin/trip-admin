package com.ulplanet.trip.modules.tms.dao;


import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tms.entity.JourneyPlan;
import com.ulplanet.trip.modules.tms.entity.JourneyPlans;

import java.util.List;

@MyBatisDao
public interface  JourneyPlanDao extends CrudDao<JourneyPlan> {
	
    List<JourneyPlans> queryAllPlanByJourneyId(String id);
    List<JourneyPlan> findPage(JourneyPlan journeyPlan);
    List<JourneyPlan> findAllType();
}
