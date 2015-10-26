package com.ulplanet.trip.modules.crm.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.modules.crm.dao.AppUserDao;
import com.ulplanet.trip.modules.crm.entity.AppUser;
import org.springframework.stereotype.Service;

/**
 * 客户Service
 * Created by zhangxd on 15/10/20.
 */
@Service
public class AppUserService extends CrudService<AppUserDao, AppUser> {

}
