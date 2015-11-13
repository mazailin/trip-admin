package com.ulplanet.trip.modules.crm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.EhCacheUtils;
import com.ulplanet.trip.common.utils.JedisUtils;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.crm.dao.AppUserDao;
import com.ulplanet.trip.modules.crm.entity.AppUser;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.tms.entity.GroupUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户Service
 * Created by zhangxd on 15/10/20.
 */
@Service
public class AppUserService extends CrudService<AppUserDao, AppUser> {

    @Resource
    private AppUserDao appUserDao;

    public ResponseBo saveAppUser(AppUser user){
        if(StringUtils.isBlank(user.getId())) {
            user.preInsert();
            return ResponseBo.getResult(appUserDao.insert(user));
        }
        user.preUpdate();
        return ResponseBo.getResult(appUserDao.update(user));
    }

    public ResponseBo deleteUser(AppUser user){
        return ResponseBo.getResult(appUserDao.delete(user));
    }

    public AppUser hasPassport(String passport){
        AppUser appUser = new AppUser();
        appUser.setPassport(passport);
        List<AppUser> appUsers = appUserDao.findList(appUser);
        if(appUsers.size()>0){
            return appUsers.get(0);
        }
        return appUser;
    }

    public void refresh(){
        List<AppUser> list = appUserDao.findList(new AppUser());
        EhCacheUtils.put("userPassportList", "userPassportList", list);
    }
}
