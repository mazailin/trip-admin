package com.ulplanet.trip.modules.tms.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.modules.tms.dao.JourneyPlanDao;
import com.ulplanet.trip.modules.tms.entity.JourneyPlan;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by makun on 2015/10/28.
 */
@Service("journeyPlan")
public class JourneyPlanService extends CrudService<JourneyPlanDao,JourneyPlan> {
    @Resource
    private JourneyPlanDao journeyPlanDao;

}
