package com.ulplanet.trip.modules.tms.dao;


import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tms.bo.InfoBo;
import com.ulplanet.trip.modules.tms.entity.JourneyPlan;
import com.ulplanet.trip.modules.tms.bo.JourneyPlans;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface  JourneyPlanDao extends CrudDao<JourneyPlan> {
	
    List<JourneyPlans> queryAllPlanByJourneyId(String id);
    List<JourneyPlan> findPage(JourneyPlan journeyPlan);
    List<JourneyPlan> findAllType();
    List<InfoBo> findInfoByTableName(@Param(value = "table")String table,
                                     @Param(value = "ids")String[] ids,@Param(value = "infoId")String infoId,
                                     @Param(value = "tableCol")String tableCol);
    int updates(@Param("list") List<JourneyPlan> journeyPlans);

    int inserts(@Param("list") List<JourneyPlan> journeyPlans);
}
