package com.ulplanet.trip.modules.tim.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.tms.bo.InfoBo;
import com.ulplanet.trip.modules.tim.dao.FlightDao;
import com.ulplanet.trip.modules.tim.entity.Flight;
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
        if(ids!=null){
            return flightDao.findListByCityIds(ids.split(","));
        }
        return new ArrayList<>();

    }

    public int saveFlight(Flight flight){
        if(StringUtils.isNotBlank(flight.getId())){
            flight.preUpdate();
            return flightDao.update(flight);
        }
        flight.preInsert();
        return flightDao.insert(flight);
    }


}
