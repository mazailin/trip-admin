package com.ulplanet.trip.modules.tms.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.modules.tms.bo.InfoBo;
import com.ulplanet.trip.modules.tms.dao.FlightDao;
import com.ulplanet.trip.modules.tms.entity.Flight;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makun on 2015/10/28.
 */
@Service("flightService")
public class FlightService extends CrudService<FlightDao,Flight> {

    @Resource
    private FlightDao flightDao;


    public List<InfoBo> findListByIds(String ids){
        if(ids!=null&&ids.indexOf(",")>0){
            return flightDao.findList(ids.split(","));
        }
        return new ArrayList<>();

    }


}
