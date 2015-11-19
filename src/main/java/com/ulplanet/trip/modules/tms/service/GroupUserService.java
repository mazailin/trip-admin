package com.ulplanet.trip.modules.tms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.*;
import com.ulplanet.trip.modules.crm.entity.AppUser;
import com.ulplanet.trip.modules.crm.service.AppUserService;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.tms.dao.GroupUserDao;
import com.ulplanet.trip.modules.tms.entity.Group;
import com.ulplanet.trip.modules.tms.entity.GroupUser;
import io.rong.ApiHttpClient;
import io.rong.models.SdkHttpResult;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2015/10/27.
 */
@Service("groupUserService")
public class GroupUserService extends CrudService<GroupUserDao,GroupUser> {
    Logger logger = org.slf4j.LoggerFactory.getLogger(GroupUserService.class);
    @Resource
    private GroupUserDao groupUserDao;
    @Resource
    private GroupService groupService;
    @Resource
    private AppUserService appUserService;

    public ResponseBo addUser(GroupUser groupUser){
        groupUser.preInsert();
        groupUser.setUser(IdGen.uuid());
        return ResponseBo.getResult(groupUserDao.insertUser(groupUser));
    }

    public ResponseBo saveGroupUser(GroupUser groupUser){
        ResponseBo  responseBo = new ResponseBo();

        if(StringUtils.isBlank(groupUser.getId())){//添加用户
            Group group = groupService.get(groupUser.getGroup());
            groupUser.preInsert();
            String code =  this.getUserCode(groupUser.getGroup());
            groupUser.setCode(code);
            try {
                SdkHttpResult sdkHttpResult1 = ApiHttpClient.getToken(groupUser.getId(), groupUser.getName(), "");
                SdkHttpResult sdkHttpResult2 = ApiHttpClient.joinGroup(groupUser.getId(), group.getId(), group.getName());
                if (sdkHttpResult1.getHttpCode() == 200 && sdkHttpResult2.getHttpCode() == 200) {
                    Map<String, Object> tokenMap = new Gson().fromJson(sdkHttpResult1.getResult(),
                            new TypeToken<Map<String, Object>>() {
                            }.getType());
                    groupUser.setImToken(Objects.toString(tokenMap.get("token")));
                    responseBo = addGroupUser(groupUser);
                } else {
                    logger.error(sdkHttpResult1.getResult()  + sdkHttpResult2.getResult());
                    throw new RuntimeException("接口调用失败!");
                }
            } catch (Exception e) {
                logger.error("用户创建失败", e);
                responseBo.setMsg("用户创建失败");
                responseBo.setStatus(0);
                return responseBo;
            }
        }else{
            updateUser(groupUser);
            responseBo = updateUser(groupUser);
        }
        return responseBo;
    }

    public ResponseBo addGroupUser(GroupUser groupUser){
        groupUser.preInsert();
        return ResponseBo.getResult(groupUserDao.insertGroupUser(groupUser));
    }

    public ResponseBo updateUser(GroupUser groupUser){
        groupUser.preUpdate();
        return ResponseBo.getResult(groupUserDao.updateGroupUser(groupUser));
    }

    public ResponseBo deleteUser(GroupUser groupUser){
        try {
            SdkHttpResult sdkHttpResult = ApiHttpClient.quitGroup(groupUser.getId(), groupUser.getGroup());
            if (sdkHttpResult.getHttpCode() == 200) {
                return ResponseBo.getResult(groupUserDao.deleteGroupUser(groupUser));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBo(0,"聊天组删除用户失败");
        }
        return new ResponseBo(0,"删除用户失败");
    }

    public GroupUser getPassport(String passport,String group) {
        GroupUser groupUser;
        List<GroupUser> list = groupUserDao.findUserByPassport(passport,null);
        if(list.size()>0){
            Group group1 = groupService.get(group);
            Date toDate = group1.getToDate();
            Date fromDate = group1.getFromDate();

            for(GroupUser g : list){
                if(StringUtils.isNotBlank(g.getToDate())){
                    try {
                        Date fDate = DateUtils.parseDate(g.getFromDate(),"yyyy-MM-dd");
                        Date tDate = DateUtils.parseDate(g.getToDate(),"yyyy-MM-dd");
                        if(isIn(fDate,tDate,fromDate,toDate)){
                            g.setCode("0");
                            return g;
                        }else{
                            g.setCode("1");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            groupUser = list.get(0);
            return groupUser;
        }
        return new GroupUser();

    }

    public List<AppUser> getPassportList(String group) {
        if(EhCacheUtils.get("userPassportList", "userPassportList")!=null){
            List<AppUser> list = (List<AppUser>)EhCacheUtils.get("userPassportList","userPassportList");
            return list;
        }
        List<AppUser> list = appUserService.findList(new AppUser());
        EhCacheUtils.put("userPassportList", "userPassportList",list);
        return list;

    }

//    public ResponseBo importExcel(MultipartFile multipartFile){
//
//    }

    private String getUserCode(String groupid) {
        String lock;
        synchronized (this) {
            lock = groupid.intern();
        }

        synchronized (lock) {
            return _getUserCode();
        }
    }

    private String _getUserCode() {
        String date = DateUtils.formatDate(new Date(), "yyyyMMdd");
        Parameter parameter = new Parameter(new Object[][]{
                {"code", date}
        });
        String code = this.groupUserDao.findMaxCode(parameter);
        if (StringUtils.isEmpty(code)) {
            code = date + "001";
        } else {
            code = NumberUtils.toLong(code) + 1 + "";
        }
        return code;
    }

    private boolean isIn(Date oldSDate,Date oldEDate,Date newSDate,Date newEDate){
        if(oldSDate==null||oldEDate==null){
            return false;
        }
        if(oldSDate.before(newSDate) && oldEDate.after(newSDate))return true;
        if(oldSDate.before(newEDate) && oldEDate.after(newEDate))return true;
        if(oldSDate.after(newSDate) && oldEDate.before(newEDate))return true;
        if(oldEDate.compareTo(newEDate)==0 || oldSDate.compareTo(newSDate)==0)return true;
        return false;
    }

}
