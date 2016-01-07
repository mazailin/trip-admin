package com.ulplanet.trip.modules.tms.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tms.entity.PhoneFeedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by makun on 2015/12/15.
 */
@MyBatisDao
public interface PhoneFeedbackDao extends CrudDao<PhoneFeedback> {
    List<PhoneFeedback> getFunction();
    List<PhoneFeedback> getScore();
    List<PhoneFeedback> getJourney(@Param("groupId")String groupId);
}
