package com.ulplanet.trip.modules.tim.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.tim.dao.CityDao;
import com.ulplanet.trip.modules.tim.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 城市Service
 * Created by zhangxd on 15/10/23.
 */
@Service
public class CityService extends CrudService<CityDao, City> {

    @Autowired
    private CityDao cityDao;

    public City getUserByName(City city) {
        return cityDao.findByName(city);
    }

    public void saveCity(City city) {
        if (StringUtils.isBlank(city.getId())){
            city.preInsert();
            cityDao.insert(city);
        } else {
            // 更新用户数据
            city.preUpdate();
            cityDao.update(city);
        }
    }
}
