package com.ulplanet.trip.modules.tim.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.tim.dao.HotelDao;
import com.ulplanet.trip.modules.tim.entity.Hotel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by makun on 2015/11/5.
 */
@Service("hotelService")
public class HotelService extends CrudService<HotelDao,Hotel> {

    @Resource
    private HotelDao hotelDao;

    public int saveHotel(Hotel hotel){
        if(StringUtils.isNotBlank(hotel.getId())){
            hotel.preUpdate();
            return hotelDao.update(hotel);
        }
        hotel.preInsert();
        return hotelDao.insert(hotel);
    }
}
