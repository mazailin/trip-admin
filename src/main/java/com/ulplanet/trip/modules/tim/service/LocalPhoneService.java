package com.ulplanet.trip.modules.tim.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.tim.dao.LocalPhoneDao;
import com.ulplanet.trip.modules.tim.entity.LocalPhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 当地电话Service
 * Created by zhangxd on 15/10/23.
 */
@Service
public class LocalPhoneService extends CrudService<LocalPhoneDao, LocalPhone> {

    @Autowired
    private LocalPhoneDao localPhoneDao;

    public void saveLocalPhone(LocalPhone LocalPhone) {
        if (StringUtils.isBlank(LocalPhone.getId())){
            LocalPhone.preInsert();
            localPhoneDao.insert(LocalPhone);
        } else {
            LocalPhone.preUpdate();
            localPhoneDao.update(LocalPhone);
        }
    }
}
