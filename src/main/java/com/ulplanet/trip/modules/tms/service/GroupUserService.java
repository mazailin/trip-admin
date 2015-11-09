package com.ulplanet.trip.modules.tms.service;

import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.common.utils.IdGen;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.tms.dao.GroupUserDao;
import com.ulplanet.trip.modules.tms.entity.Group;
import com.ulplanet.trip.modules.tms.entity.GroupUser;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/27.
 */
@Service("groupUserService")
public class GroupUserService extends CrudService<GroupUserDao,GroupUser> {
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
        ResponseBo responseBo;
        if(StringUtils.isBlank(groupUser.getCode())){//添加用户
            String code =  this.getUserCode(groupUser.getGroup());
            groupUser.setCode(code);
            if(StringUtils.isNotBlank(groupUser.getUser())){
                responseBo = updateUser(groupUser);
            }else {
                responseBo = addUser(groupUser);
            }
            if(responseBo.getStatus()==1){//添加用户到团队中
                responseBo = addGroupUser(groupUser);
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
        return ResponseBo.getResult(groupUserDao.deleteGroupUser(groupUser));
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
