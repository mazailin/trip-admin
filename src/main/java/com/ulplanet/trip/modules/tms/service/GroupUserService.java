package com.ulplanet.trip.modules.tms.service;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.EhCacheUtils;
import com.ulplanet.trip.common.utils.IdGen;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.crm.entity.AppUser;
import com.ulplanet.trip.modules.crm.service.AppUserService;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.sys.entity.VersionTag;
import com.ulplanet.trip.modules.sys.service.CodeService;
import com.ulplanet.trip.modules.sys.service.VersionTagService;
import com.ulplanet.trip.modules.tms.dao.GroupDao;
import com.ulplanet.trip.modules.tms.dao.GroupUserDao;
import com.ulplanet.trip.modules.tms.dao.QingmaDao;
import com.ulplanet.trip.modules.tms.entity.Group;
import com.ulplanet.trip.modules.tms.entity.GroupUser;
import com.ulplanet.trip.modules.tms.entity.Qingma;
import com.ulplanet.trip.modules.tms.utils.ExcelReader;
import io.qingmayun.QingHttpClient;
import io.qingmayun.modules.QingResultInfo;
import io.rong.ApiHttpClient;
import io.rong.models.SdkHttpResult;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2015/10/27.
 */
@Service("groupUserService")
public class GroupUserService extends CrudService<GroupUserDao,GroupUser> {
    Logger logger = org.slf4j.LoggerFactory.getLogger(GroupUserService.class);
    @Resource
    private GroupUserDao groupUserDao;
    @Resource
    private GroupDao groupDao;
    @Resource
    private AppUserService appUserService;
    @Resource
    private CodeService codeService;
    @Resource
    private VersionTagService versionTagService;
    @Resource
    private QingmaDao qingmaDao;

    private static List<String> title;



    public ResponseBo saveGroupUser(GroupUser groupUser){
        ResponseBo  responseBo;

        if(StringUtils.isBlank(groupUser.getId())){//添加用户
            Group group = groupDao.get(groupUser.getGroup());
            String code =  codeService.getCode(CodeService.CODE_TYPE_GROUP_USER, "");
            groupUser.setCode(code);
            groupUser.preInsert();

            /**判断该团是否有轻码云通话**/
            if(StringUtils.isNotEmpty(group.getTelFunction())) {
                String[] arr = group.getTelFunction().split(",");
                for(String s : arr){
                    if(s.equals("2")){
                        responseBo = addQingmayun(groupUser);
                        if(responseBo.getStatus() == 0){
                            return responseBo;
                        }
                        break;
                    }
                }
            }
            /**判断融云创建是否成功**/
            if(addChat(groupUser, group).getStatus() == 0){
                return new ResponseBo(0,"创建用户组失败");
            }
            responseBo = ResponseBo.getResult(groupUserDao.insertGroupUser(groupUser));
        }else{
            responseBo = updateUser(groupUser);
        }
        if(responseBo.getStatus()==0){
            return responseBo;
        }

        if(responseBo.getStatus() == 1) {
            versionTagService.save(new VersionTag(groupUser.getGroup(),1));
        }
        return responseBo;
    }


    public ResponseBo saveGroupUsers(List<GroupUser> list,Group group){
        ResponseBo  responseBo = new ResponseBo();
        for(GroupUser groupUser : list) {
            GroupUser g = getByPassport(groupUser.getPassport(), group.getId());
            if("0".equals(g.getCode())){
                continue;
            }
            groupUser.setId(IdGen.uuid());
            String code = codeService.getCode(CodeService.CODE_TYPE_GROUP_USER, "");
            groupUser.setCode(code);
            /**判断该团是否有轻码云通话**/
            if(StringUtils.isNotEmpty(group.getTelFunction())) {
                String[] arr = group.getTelFunction().split(",");
                for(String s : arr){
                    if(s.equals("2")){
                        responseBo = addQingmayun(groupUser);
                        if(responseBo.getStatus() == 0){
                            return responseBo;
                        }
                        break;
                    }
                }
            }

            responseBo = addChat(groupUser,group);
            if(responseBo.getStatus()==0){
                return responseBo;
            }
        }

        responseBo.setStatus(groupUserDao.insertGroupUsers(list));
        if(responseBo.getStatus() > 0){
            versionTagService.save(new VersionTag(group.getId(), 1));
        }
        return responseBo;
    }


    /**
     * 轻码云添加用户
     * @param groupUser
     */
    public ResponseBo addQingmayun(GroupUser groupUser){
        Qingma qingma = qingmaDao.get(groupUser.getUser());
        if(qingma!=null){
            return ResponseBo.getResult(1);
        }
        QingHttpClient qingHttpClient = new QingHttpClient();
        QingResultInfo q = qingHttpClient.create(groupUser.getPhone());
        if(q != null && "00000".equals(q.getRespCode())){
            qingma = new Qingma();
            qingma.setClientNumber(q.getClientNumber());
            qingma.setClientPwd(q.getClientPwd());
            qingma.setId(groupUser.getUser());
            qingma.setCreateTime(q.getCreateTime());
            qingmaDao.insert(qingma);
            return ResponseBo.getResult(1);
        }else{
            logger.error("创建用户：" + groupUser.getUser() + " 失败，" + JSON.toJSONString(q));
            return new ResponseBo(0,"创建轻码云失败");
        }
    }

    /**
     * 添加融云用户
     * @param groupUser
     * @param group
     * @return
     */
    private ResponseBo addChat(GroupUser groupUser,Group group){
        ResponseBo responseBo = new ResponseBo();
        try {
            //获取用户token
            SdkHttpResult sdkHttpResult1 = ApiHttpClient.getToken(groupUser.getId(), groupUser.getName(), "");
            //添加用户到组
            SdkHttpResult sdkHttpResult2 = ApiHttpClient.joinGroup(groupUser.getId(), group.getId(), group.getName());
            //添加导游通知用户组
            SdkHttpResult sdkHttpResult3 = null;
            if (StringUtils.isNotEmpty(group.getChatId())) {
                sdkHttpResult3  = ApiHttpClient.joinGroup(groupUser.getId(), group.getChatId(), group.getChatName());
            }

            if (sdkHttpResult1.getHttpCode() == 200 && sdkHttpResult2.getHttpCode() == 200 && (sdkHttpResult3 == null || sdkHttpResult3.getHttpCode() == 200) ) {
                Map<String, Object> tokenMap = new Gson().fromJson(sdkHttpResult1.getResult(),
                        new TypeToken<Map<String, Object>>() {
                        }.getType());
                groupUser.setImToken(Objects.toString(tokenMap.get("token")));
                responseBo.setStatus(1);
            } else {
                responseBo.setStatus(0);
                responseBo.setMsg("接口调用失败!");
                logger.error(sdkHttpResult1.getResult() + sdkHttpResult2.getResult() + sdkHttpResult3.getResult());
                throw new RuntimeException("接口调用失败!");
            }
        } catch (Exception e) {
            responseBo.setStatus(0);
            responseBo.setMsg("用户创建失败!");
            logger.error("用户创建失败", e);
        }
        return responseBo;
    }

    public ResponseBo updateUser(GroupUser groupUser){
        groupUser.preUpdate();
        return ResponseBo.getResult(groupUserDao.updateGroupUser(groupUser));
    }



    public ResponseBo deleteUser(GroupUser groupUser){
        try {
            SdkHttpResult sdkHttpResult = ApiHttpClient.quitGroup(groupUser.getId(), groupUser.getGroup());
            Group group = groupDao.get(groupUser.getGroup());
            SdkHttpResult sdkHttpResult3 = null;
            if (StringUtils.isNotEmpty(group.getChatId())) {
                sdkHttpResult3  = ApiHttpClient.quitGroup(groupUser.getId(), group.getChatId());
            }
            if (sdkHttpResult.getHttpCode() == 200 &&  (sdkHttpResult3 == null || sdkHttpResult3.getHttpCode() == 200)) {
                versionTagService.save(new VersionTag(groupUser.getGroup(), 1));
                return ResponseBo.getResult(groupUserDao.deleteGroupUser(groupUser));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBo(0,"聊天组删除用户失败");
        }
        return new ResponseBo(0,"删除用户失败");
    }

    public void deleteByGroup(String groupId){
        List<String> list = groupUserDao.findListByGroup(groupId);
        for(String s : list){
            GroupUser groupUser = new GroupUser();
            groupUser.setId(s);
            groupUser.setGroup(groupId);
            deleteUser(groupUser);
        }
    }

    /**
     * 根据护照获取用户信息
     * @param passport
     * @param group
     * @return
     */
    public GroupUser getByPassport(String passport,String group) {
        List<GroupUser> list = groupUserDao.findUserByPassport(passport,null);
        if(list.size()>0){
            for(GroupUser groupUser1 : list){
                if(group.equals(groupUser1.getGroup())){
                    groupUser1.setCode("0");
                    return groupUser1;
                }
            }
            return list.get(0);
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

    /**
     * 批量导入用户
     * @param multipartFile
     * @param groupId
     * @return
     */
    @SuppressWarnings("unchecked")
    public ResponseBo importExcel(MultipartFile multipartFile,String groupId){
        ExcelReader excelReader = new ExcelReader();
        ResponseBo responseBo = new ResponseBo();
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
                String id = groupUserDao.hasPassport(groupUser.getPassport(),groupId);
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

            Group group = groupDao.get(groupId);
            if(addGroupList.size()>0) {
                responseBo = saveGroupUsers(addGroupList, group);
                if(responseBo.getStatus() == 0){
                    return responseBo;
                }
                versionTagService.save(new VersionTag(groupId, 1));
            }
            return new ResponseBo(1,"导入成功,共导入"+i+"条数据");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBo(0,"系统异常");
        }
    }

    public GroupUser getByUserCode(String usercode){
        return groupUserDao.getByUserCode(usercode);
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

//    private boolean isIn(Date oldSDate,Date oldEDate,Date newSDate,Date newEDate){
//        if(oldSDate==null||oldEDate==null){
//            return false;
//        }
//        if(oldSDate.before(newSDate) && oldEDate.after(newSDate))return true;
//        if(oldSDate.before(newEDate) && oldEDate.after(newEDate))return true;
//        if(oldSDate.after(newSDate) && oldEDate.before(newEDate))return true;
//        if(oldEDate.compareTo(newEDate)==0 || oldSDate.compareTo(newSDate)==0)return true;
//        return false;
//    }

}
