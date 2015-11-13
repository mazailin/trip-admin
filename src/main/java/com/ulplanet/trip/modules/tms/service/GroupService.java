package com.ulplanet.trip.modules.tms.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.crm.dao.CustomerDao;
import com.ulplanet.trip.modules.crm.entity.Customer;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.tms.dao.GroupDao;
import com.ulplanet.trip.modules.tms.entity.Group;
import io.rong.ApiHttpClient;
import io.rong.models.SdkHttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private CustomerDao customerDao;

    public ResponseBo addGroup(Group group) {
        group.preInsert();
        return ResponseBo.getResult(this.groupDao.insert(group));
    }

    public ResponseBo updateGroup(Group group) {
        group.preUpdate();
        try {
            ApiHttpClient.refreshGroupInfo(group.getId(), group.getName());
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

//    Map<String, Object> listGroup(int page, int size, String searchValue);
//
}
