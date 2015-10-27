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
import com.ulplanet.trip.modules.tms.utils.ChatIdMaker;
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
        String userCode = this.getUserCode(groupUser.getGroup());
        Group group = groupService.get(groupUser.getGroup());
        String chatId = group.getChatId();
        if (ChatIdMaker.inviteJoinGroup(chatId, userCode)) {
            groupUser.preInsert();
            groupUser.setCode(userCode);
            if(StringUtils.isNotBlank(groupUser.getId())){
                return ResponseBo.getResult(groupUserDao.insertGroupUser(groupUser));
            }else {
                groupUser.setUser(IdGen.uuid());
                this.groupUserDao.insertUser(groupUser);
                this.groupUserDao.insertGroupUser(groupUser);
                return ResponseBo.getResult(groupUserDao.insertGroupUser(groupUser));
            }
        }
        return new ResponseBo(0,"新增用户失败");
    }

    public ResponseBo updateUser(GroupUser groupUser){
        groupUser.preUpdate();
        return ResponseBo.getResult(groupUserDao.updateUser(groupUser));
    }

    public ResponseBo deleteUser(GroupUser groupUser){
        return ResponseBo.getResult(groupUserDao.deleteGroupUser(groupUser));
    }

    public GroupUser getPassport(String passport) {
        Map<String, Object> result = new HashMap<>();
        GroupUser groupUser;
        List<GroupUser> list = groupUserDao.findUserByPassport(passport,null);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(list.size()==0){
            return new GroupUser();
        }
        groupUser = list.get(0);
        return groupUser;
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

}
