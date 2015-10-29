package com.ulplanet.trip.modules.tms.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.modules.tms.dao.FlightDao;
import com.ulplanet.trip.modules.tms.entity.Flight;

import javax.annotation.Resource;

/**
 * Created by makun on 2015/10/28.
 */
public class FlightService extends CrudService<FlightDao,Flight> {

    @Resource
    private FlightDao flightDao;


}
