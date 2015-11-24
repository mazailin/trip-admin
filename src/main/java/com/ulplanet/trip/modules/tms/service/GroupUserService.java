package com.ulplanet.trip.modules.tms.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.common.utils.EhCacheUtils;
import com.ulplanet.trip.common.utils.IdGen;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.crm.entity.AppUser;
import com.ulplanet.trip.modules.crm.service.AppUserService;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.sys.service.CodeService;
import com.ulplanet.trip.modules.tms.dao.GroupUserDao;
import com.ulplanet.trip.modules.tms.entity.Group;
import com.ulplanet.trip.modules.tms.entity.GroupUser;
import com.ulplanet.trip.modules.tms.utils.ExcelReader;
import io.rong.ApiHttpClient;
import io.rong.models.SdkHttpResult;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.ParseException;
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
    @Resource
    private CodeService codeService;

    private static List<String> title;


    public ResponseBo saveGroupUser(GroupUser groupUser){
        ResponseBo  responseBo = new ResponseBo();

        if(StringUtils.isBlank(groupUser.getId())){//添加用户
            Group group = groupService.get(groupUser.getGroup());
            String code =  codeService.getCode(CodeService.CODE_TYPE_GROUP_USER);
            groupUser.setCode(code);
            try {
                SdkHttpResult sdkHttpResult1 = ApiHttpClient.getToken(groupUser.getPassport(), groupUser.getName(), "");
                SdkHttpResult sdkHttpResult2 = ApiHttpClient.joinGroup(groupUser.getPassport(), group.getId(), group.getName());
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


    public ResponseBo saveGroupUsers(List<GroupUser> list,Group group){
        ResponseBo  responseBo = new ResponseBo();
        for(GroupUser groupUser : list) {
            groupUser.setId(IdGen.uuid());
            String code = codeService.getCode(CodeService.CODE_TYPE_GROUP_USER);
            groupUser.setCode(code);
            try {
                SdkHttpResult sdkHttpResult1 = ApiHttpClient.getToken(groupUser.getPassport(), groupUser.getName(), "");
                SdkHttpResult sdkHttpResult2 = ApiHttpClient.joinGroup(groupUser.getPassport(), group.getId(), group.getName());
                if (sdkHttpResult1.getHttpCode() == 200 && sdkHttpResult2.getHttpCode() == 200) {
                    Map<String, Object> tokenMap = new Gson().fromJson(sdkHttpResult1.getResult(),
                            new TypeToken<Map<String, Object>>() {
                            }.getType());
                    groupUser.setImToken(Objects.toString(tokenMap.get("token")));
                } else {
                    logger.error(sdkHttpResult1.getResult() + sdkHttpResult2.getResult());
                    throw new RuntimeException("接口调用失败!");
                }
            } catch (Exception e) {
                logger.error("用户创建失败", e);
                responseBo.setMsg("用户创建失败");
                responseBo.setStatus(0);
                return responseBo;
            }
        }
        responseBo.setStatus(groupUserDao.insertGroupUsers(list));
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

    @SuppressWarnings("unchecked")
    public List<AppUser> getPassportList(String group) {
        if(EhCacheUtils.get("userPassportList", "userPassportList")!=null){
            return (List<AppUser>)EhCacheUtils.get("userPassportList","userPassportList");
        }
        List<AppUser> list = appUserService.findList(new AppUser());
        EhCacheUtils.put("userPassportList", "userPassportList",list);
        return list;

    }

    @SuppressWarnings("unchecked")
    public ResponseBo importExcel(MultipartFile multipartFile,String groupId){
        ExcelReader excelReader = new ExcelReader();
        String error;
        try {
            InputStream in = multipartFile.getInputStream();
            error = excelReader.checkExcelTitle(in, title(),multipartFile.getOriginalFilename());
            if(error.length()>0){
                return new ResponseBo(0,error);
            }
            Class<GroupUser> clazz = GroupUser.class;
            Map<String,Object> map = excelReader.readExcelContent(in, title(), clazz);
            error = map.get("error").toString();
            if(error.length()>0){
                return new ResponseBo(0,error);
            }
            List<GroupUser> list = (List<GroupUser>)map.get("data");
            List<GroupUser> addList = new ArrayList<>();
            List<GroupUser> addGroupList = new ArrayList<>();
            int i = 0;
            for(GroupUser groupUser : list){
                if(StringUtils.isBlank(groupUser.getPassport())){
                    continue;
                }
                i++;
                String id = groupUserDao.hasPassport(groupUser.getPassport());
                groupUser.preInsert();
                groupUser.setGroup(groupId);
                if(StringUtils.isNotBlank(id)){
                    groupUser.setUser(id);
                    groupUserDao.updateUser(groupUser);
                }else {
                    groupUser.setUser(groupUser.getId());
                    addList.add(groupUser);
                }
                addGroupList.add(groupUser);
            }
            if(addList.size()>0){
                groupUserDao.insertUsers(addList);
            }

            Group group = groupService.get(groupId);
            if(addGroupList.size()>0) {
                saveGroupUsers(addGroupList, group);
            }
            return new ResponseBo(1,"导入成功,共导入"+i+"条数据");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBo(0,"系统异常");
        }
    }

    private static List<String> title(){
        if(title!=null)return title;
        title = new ArrayList<>();
        title.add("passport");
        title.add("name");
        title.add("phone");
        title.add("gender");
        title.add("type");
        title.add("birth");
        title.add("birthPlace");
        title.add("issueDate");
        title.add("issuePlace");
        title.add("expiryDate");
        title.add("email");
        return title;
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
