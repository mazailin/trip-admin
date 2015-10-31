package com.ulplanet.trip.modules.tim.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.tim.dao.CarRentalDao;
import com.ulplanet.trip.modules.tim.entity.CarRental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 租车Service
 * Created by zhangxd on 15/10/23.
 */
@Service
public class CarRentalService extends CrudService<CarRentalDao, CarRental> {

    @Autowired
    private CarRentalDao carRentalDao;

    public void saveCarRental(CarRental carRental) {
        if (StringUtils.isBlank(carRental.getId())){
            carRental.preInsert();
            carRentalDao.insert(carRental);
        } else {
            carRental.preUpdate();
            carRentalDao.update(carRental);
        }
    }
}
