package com.ulplanet.trip.modules.crm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.JedisUtils;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.crm.dao.AppUserDao;
import com.ulplanet.trip.modules.crm.entity.AppUser;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
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

    public ResponseBo hasPassport(String passport){
        AppUser appUser = new AppUser();
        appUser.setPassport(passport);
        List<AppUser> appUsers = appUserDao.findList(appUser);
        if(appUsers.size()>0){
            return new ResponseBo(0,"护照号已存在");
        }
        return new ResponseBo(1,"SUCCESS");
    }

    public void refresh(){
        List<AppUser> list = appUserDao.findList(new AppUser());
        List<JSONObject> jsonObjects = new ArrayList<>();
        for(AppUser a : list){
            JSONObject groupUser = new JSONObject();
            groupUser.put("label",a.getPassport());
            groupUser.put("value",a.getPassport()+":"+a.getName());
            jsonObjects.add(groupUser);
        }
        JedisUtils.set("userPassportList", JSON.toJSONString(jsonObjects), 36000);
    }
}
