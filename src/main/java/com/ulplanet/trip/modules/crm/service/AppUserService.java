package com.ulplanet.trip.modules.crm.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.EhCacheUtils;
import com.ulplanet.trip.common.utils.FileManager;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.utils.fservice.FileIndex;
import com.ulplanet.trip.modules.crm.dao.AppUserDao;
import com.ulplanet.trip.modules.crm.entity.AppUser;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户Service
 * Created by zhangxd on 15/10/20.
 */
@Service
public class AppUserService extends CrudService<AppUserDao, AppUser> {

    @Resource
    private AppUserDao appUserDao;

    public ResponseBo saveAppUser(AppUser user,MultipartFile file){
        if(file.getSize()>0){
            FileIndex ufi = new FileIndex();
            ufi.setmUpfile(file);
            ufi.setTruename(file.getOriginalFilename());
            ufi.setMcode("passport");
            ufi = FileManager.save(ufi);
            user.setPassportPhoto(ufi.getPath());
        }
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
