package com.ulplanet.trip.modules.tms.utils;

import com.ulplanet.trip.common.utils.SpringContextHolder;
import com.ulplanet.trip.modules.tms.bo.JourneyDayBo;
import com.ulplanet.trip.modules.tms.dao.JourneyDayDao;
import com.ulplanet.trip.modules.tms.dao.JourneyPlanDao;
import com.ulplanet.trip.modules.tms.entity.JourneyDay;
import com.ulplanet.trip.modules.tms.entity.JourneyPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makun on 2015/10/29.
 */
public class JourneyUtils {
    private static JourneyPlanDao journeyPlanDao = SpringContextHolder.getBean(JourneyPlanDao.class);
    private static JourneyDayDao journeyDayDao = SpringContextHolder.getBean(JourneyDayDao.class);

    public List<JourneyDayBo> getJourneyTemplate(String groupId){
        JourneyDay j = new JourneyDay();
        j.setGroupId(groupId);
        List<JourneyDay> list = journeyDayDao.findList(j);
        List<JourneyDayBo> journeyDayBos = new ArrayList<>();
        for(JourneyDay journeyDay : list){
            JourneyPlan journeyPlan = new JourneyPlan();
            journeyPlan.setDayId(journeyDay.getId());
            List<JourneyPlan> plans = journeyPlanDao.findList(journeyPlan);
            JourneyDayBo journeyDayBo = new JourneyDayBo(journeyDay);
            journeyDayBo.setList(plans);
            journeyDayBos.add(journeyDayBo);
        }
        return journeyDayBos;
    }

}
