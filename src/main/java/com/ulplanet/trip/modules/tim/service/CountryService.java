package com.ulplanet.trip.modules.tim.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.tim.dao.CountryDao;
import com.ulplanet.trip.modules.tim.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 国家Service
 * Created by zhangxd on 15/10/23.
 */
@Service
public class CountryService extends CrudService<CountryDao, Country> {

    @Autowired
    private CountryDao countryDao;

    public Country getCountryByName(String name) {
        return countryDao.findByName(new Country(null, name));
    }

    public void saveCountry(Country country) {
        if (StringUtils.isBlank(country.getId())){
            country.preInsert();
            countryDao.insert(country);
        }else{
            country.preUpdate();
            countryDao.update(country);
        }
    }
}
