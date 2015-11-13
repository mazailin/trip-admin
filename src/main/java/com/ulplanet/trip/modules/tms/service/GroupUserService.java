package com.ulplanet.trip.modules.tms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.common.utils.IdGen;
import com.ulplanet.trip.common.utils.JedisUtils;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.tms.dao.GroupUserDao;
import com.ulplanet.trip.modules.tms.entity.Group;
import com.ulplanet.trip.modules.tms.entity.GroupUser;
import io.rong.ApiHttpClient;
import io.rong.models.SdkHttpResult;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

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
        groupUserDao.updateGroupUser(groupUser);
        return ResponseBo.getResult(groupUserDao.updateUser(groupUser));
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

    public List<JSONObject> getPassportList(String group) {
        if(JedisUtils.exists("userPassportList")){
            String str = JedisUtils.get("userPassportList");
            List<JSONObject> list = new ArrayList<>();
            list = JSON.parseObject(str,list.getClass());
            return list;
        }
        List<GroupUser> list = groupUserDao.findUserByPassport(null,null);

        List<JSONObject> userList = new ArrayList<>();
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
                        }else{
                            g.setCode("1");
                            JSONObject groupUser = new JSONObject();
                            groupUser.put("label",g.getPassport());
                            groupUser.put("value",g.getPassport()+":"+g.getName());
                            userList.add(groupUser);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        JedisUtils.set("userPassportList", JSON.toJSONString(userList), 36000);
        return userList;

    }

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
        if(oldSDate.before(newSDate) && oldEDate.after(newSDate))return true;
        if(oldSDate.before(newEDate) && oldEDate.after(newEDate))return true;
        if(oldSDate.after(newSDate) && oldEDate.before(newEDate))return true;
        return false;
    }

}
