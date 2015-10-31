package com.ulplanet.trip.modules.tms.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tms.bo.InfoBo;
import com.ulplanet.trip.modules.tms.entity.Flight;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by makun on 2015/10/28.
 */
@MyBatisDao
public interface FlightDao extends CrudDao<Flight> {
    List<InfoBo> findList(@Param(value = "ids")String []ids);
}
