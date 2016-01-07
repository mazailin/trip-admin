package com.ulplanet.trip.modules.tms.service;

import com.google.common.collect.Lists;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.crm.dao.CustomerDao;
import com.ulplanet.trip.modules.crm.entity.Customer;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.sys.entity.VersionTag;
import com.ulplanet.trip.modules.sys.service.VersionTagService;
import com.ulplanet.trip.modules.tms.dao.GroupDao;
import com.ulplanet.trip.modules.tms.dao.GroupUserDao;
import com.ulplanet.trip.modules.tms.entity.Group;
import com.ulplanet.trip.modules.tms.entity.GroupUser;
import io.rong.ApiHttpClient;
import io.rong.models.SdkHttpResult;
import io.rong.models.TxtMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxd on 15/8/11.
 */
@Service("groupService")
public class GroupService extends CrudService<GroupDao,Group> {

    Logger logger = LoggerFactory.getLogger(GroupService.class);
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private GroupUserDao groupUserDao;
    @Autowired
    private CustomerDao customerDao;
    @Resource
    private GroupUserService groupUserService;
    @Resource
    private VersionTagService versionTagService;

    public ResponseBo addGroup(Group group) {
        group.preInsert();
        return ResponseBo.getResult(this.groupDao.insert(group));
    }

    public ResponseBo updateGroup(Group group) {
        group.preUpdate();
        try {
            ApiHttpClient.refreshGroupInfo(group.getId(), group.getName());
            versionTagService.save(new VersionTag(group.getId(),1));
            return ResponseBo.getResult(this.groupDao.update(group));
        } catch (Exception e) {
            logger.error("更新群组 " + group.getName() + " 失败，", e);
            return new ResponseBo(0,"更新群组 " + group.getName() + " 失败，");
        }

    }

    public ResponseBo deleteGroup(Group group) {
        try {
            SdkHttpResult sdkHttpResult = ApiHttpClient.dismissGroup("", group.getId());
            if (sdkHttpResult.getHttpCode() == 200) {
                groupUserService.deleteByGroup(group.getId());
                return ResponseBo.getResult(this.groupDao.delete(group));
            } else {
                logger.error(sdkHttpResult.getResult());
                throw new RuntimeException("接口调用失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBo(0,"调用接口失败");
        }
    }


    public List<Customer> getCustomer(){
        Customer customer = new Customer();
        customer.setActive("1");
        List<Customer> list = customerDao.findList(customer);
        return list;
    }

    public List<Group> getGroupList(Group group){
        List<Group> list = groupDao.findList(group);
        return list;
    }

    public List<Map<String, Object>> getGroupUserTree() {
        List<Map<String, Object>> groupUserList = new ArrayList<>();

        List<Group> groups = groupDao.findList(new Group());
        for (Group group : groups) {
            Map<String, Object> treeMap = new HashMap<>();
            treeMap.put("id", group.getId());
            treeMap.put("pid", "");
            treeMap.put("name", group.getName());
            groupUserList.add(treeMap);
        }

        List<GroupUser> groupUsers = groupUserDao.findAllList(new GroupUser());
        for (GroupUser groupUser : groupUsers) {
            Map<String, Object> treeMap = new HashMap<>();
            treeMap.put("id", groupUser.getId());
            treeMap.put("pid", groupUser.getGroup());
            treeMap.put("name", groupUser.getName());
            groupUserList.add(treeMap);
        }

        return groupUserList;
    }

    public String sendNotice(String menuIds, String comment) {
        String msg;
        if (StringUtils.isBlank(menuIds)) {
            msg = "发送失败，请选择要发送的用户";
        } else if (StringUtils.isBlank(comment)) {
            msg= "发送失败，发送内容不能为空";
        } else {
            String[] ids = StringUtils.split(menuIds, ",");
            List<String> toUserIds = Lists.newArrayList(ids);
            try {
                SdkHttpResult result = ApiHttpClient.publishSystemMessage("",
                        toUserIds, new TxtMessage(comment), "", "");
                if (result.getHttpCode() == 200) {
                    msg = "发送成功！";
                } else {
                    logger.error(result.toString());
                    msg = "发送失败,请重新发送";
                }
            } catch (Exception e) {
                logger.error("发送系统通知失败！", e);
                msg = "发送失败,请重新发送";
            }

        }

        return msg;
    }

}
